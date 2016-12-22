package com.palmlink.core.search;

import io.searchbox.client.JestResult;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.IdsQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.highlight.HighlightBuilder;

import com.google.gson.JsonObject;
import com.palmlink.core.page.PageModel;
import com.palmlink.core.platform.exception.BusinessProcessException;

public class JestHelper {
    
    private  QueryBuilder queryBuilder;
    
    private  JestResultConvert jestResultConvert;

    public QueryBuilder getQueryBuilder() {
        if (null == queryBuilder) queryBuilder = new QueryBuilder();
        return queryBuilder;
    }
    
    public JestResultConvert getJestResultConvert() {
        if (null == jestResultConvert) jestResultConvert = new JestResultConvert();
        return jestResultConvert;
    }

    public static class QueryBuilder {

        public  String createMultiFieldKeyQuery(String key, String[] fieldNames) {
            BoolQueryBuilder query = QueryBuilders.boolQuery();
            String[] keywords = key.replaceAll("\\s+", " ").split(" ");
            for (String fieldName : fieldNames) {
                for (String keyword : keywords) {
                    query = query.should(QueryBuilders.fieldQuery(fieldName, keyword));
                }
            }
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(query);
            return searchSourceBuilder.toString();
        }
        
        public  String createMultiFieldQuery(String key, Map<String, Float> fieldsMap) {
            BoolQueryBuilder query = QueryBuilders.boolQuery();
            String[] keywords = key.replaceAll("\\s+", " ").split(" ");
            String[] fieldNames = fieldsMap.keySet().toArray(new String[fieldsMap.size()]);
            for (String fieldName : fieldNames) {
                for (String keyword : keywords) {
                    if (StringUtils.isBlank(keyword)) continue;
                    query = query.should(QueryBuilders.fieldQuery(fieldName, keyword).boost(fieldsMap.get(fieldName)))
                            .should(QueryBuilders.fieldQuery(fieldName, "*" + keyword + "*").boost(fieldsMap.get(fieldName)));
//                            .fuzzyLikeThisFieldQuery(fieldName).likeText(keyword).boost(fieldsMap.get(fieldName)));
                }
            }
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field("title");
            searchSourceBuilder.query(query).highlight(highlightBuilder);
            return searchSourceBuilder.toString();
        }

        public  String createFieldPrefixKeyQuery(String key, String fieldName) {
            BoolQueryBuilder query = QueryBuilders.boolQuery();
            query = query.should(QueryBuilders.matchPhrasePrefixQuery(fieldName, key));
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(query);
            return searchSourceBuilder.toString();
        }
        
        public  String createBoolQuery(String key, String[] fieldNames, boolean isFuzzy) {
            BoolQueryBuilder query = QueryBuilders.boolQuery();
            String[] keywords = key.replaceAll("\\s+", " ").split(" ");
            for (String fieldName : fieldNames) {
                for (String keyword : keywords) {
                    query = query.should(QueryBuilders.fieldQuery(fieldName, isFuzzy ? ("*" + keyword + "*") : keyword));
                }
            }
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(query);
            return searchSourceBuilder.toString();
        }
        
        public String createIdQuery(String keyWord, String indexType) {
            IdsQueryBuilder idQuery = QueryBuilders.idsQuery(indexType).addIds(keyWord);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(idQuery);
            return searchSourceBuilder.toString();
        }
        
        public String createMatchAllQuery(String[] keyWords, String[] fieldName) {
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            if ((keyWords[0] == null && keyWords[1] == null) || ("".equals(keyWords[0]) && "".equals(keyWords[1]))) {
                MatchAllQueryBuilder matchAllQuery = QueryBuilders.matchAllQuery();
                searchSourceBuilder.query(matchAllQuery);
                return searchSourceBuilder.toString();
            } else {
                BoolQueryBuilder queryError = QueryBuilders.boolQuery();
                BoolQueryBuilder queryRight = QueryBuilders.boolQuery();
                BoolQueryBuilder query = QueryBuilders.boolQuery();
                if (keyWords[0] != null && !"".equals(keyWords[0]) ) {
                    queryError.should(QueryBuilders.fieldQuery(fieldName[0], keyWords[0]));
                    queryError.should(QueryBuilders.fieldQuery(fieldName[0], "*" + keyWords[0] + "*"));
                }
                if (keyWords[1] != null && !"".equals(keyWords[1])) {
                    queryRight.should(QueryBuilders.fieldQuery(fieldName[1], keyWords[1]));
                    queryRight.should(QueryBuilders.fieldQuery(fieldName[1], "*" + keyWords[1] + "*"));
                }
                query.must(queryError);
                query.must(queryRight);
                searchSourceBuilder.query(query);
                return searchSourceBuilder.toString();
            }
            
        }
    } 
    
    public static class JestResultConvert {
        public <T> T convert(JestResult result, Class<T> type) {
            if (result == null || !StringUtils.isBlank(result.getErrorMessage())) throw new BusinessProcessException("搜索出错了！");
            if (!result.isSucceeded())  throw new BusinessProcessException("查询失败！");
            return result.getSourceAsObject(type);
        }
        
        public <T> List<T> convertList(JestResult result, Class<T> type) {
            if (result == null || !StringUtils.isBlank(result.getErrorMessage())) throw new BusinessProcessException("搜索出错了！ [" + (result == null ? "" : result.getErrorMessage()) + "]");
            if (!result.isSucceeded())  throw new BusinessProcessException("查询失败！");
            return result.getSourceAsObjectList(type);
        }
        
        public <T> PageModel<T> convert(JestResult result, Integer pageNo, Integer pageSize, Class<T> type) {
            if (result == null || !StringUtils.isBlank(result.getErrorMessage())) throw new BusinessProcessException("搜索出错了！");
            if (!result.isSucceeded())  throw new BusinessProcessException("查询失败！");
            PageModel<T> pageModel = new PageModel<T>();
            pageModel.setPageNo(pageNo);
            pageModel.setPageSize(pageSize);
            JsonObject res = result.getJsonObject().get("hits").getAsJsonObject();
            pageModel.setTotalRecords(res.get("total").getAsLong());
            if (pageModel.getTotalRecords() > 0) pageModel.setRecords(result.getSourceAsObjectList(type));
            return pageModel;
        }
        
        public boolean validate(JestResult result, String indexName) {
            if (result == null || !StringUtils.isBlank(result.getErrorMessage())) throw new BusinessProcessException("搜索出错了！");
            if (!result.isSucceeded())  throw new BusinessProcessException("操作执行失败！");
            JsonObject json = result.getJsonObject().get("_indices").getAsJsonObject().get(indexName).getAsJsonObject().get("_shards").getAsJsonObject();
            return json.get("total").getAsInt() == json.get("successful").getAsInt();
        }
    } 
}