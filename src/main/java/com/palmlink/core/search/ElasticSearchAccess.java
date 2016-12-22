package com.palmlink.core.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.ClientConfig;
import io.searchbox.core.Bulk;
import io.searchbox.core.Delete;
import io.searchbox.core.DeleteByQuery;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.search.sort.Sort;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.IndicesExists;
import io.searchbox.indices.mapping.PutMapping;
import io.searchbox.params.SearchType;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import com.palmlink.core.page.PageModel;

public class ElasticSearchAccess extends JestHelper {
    
    private JestClient elasticSearchClient;

    private ElasticSearchSettings elasticSearchSettings;
    
    @PostConstruct
    public void initialize()  {
        ClientConfig clientConfig = new ClientConfig.Builder(elasticSearchSettings.getServers()).multiThreaded(true).build();
        JestClientFactory factory = new JestClientFactory();
        factory.setClientConfig(clientConfig);
        elasticSearchClient = factory.getObject();
    }
    
    @PreDestroy
    public void shutdown() {
        if (null != elasticSearchClient) elasticSearchClient.shutdownClient();
    }

    public void deleteIndex(String indexName, String indexType) throws Exception {
        DeleteIndex.Builder deleteBuilder = new DeleteIndex.Builder(indexName);
        if (null != indexType) {
            deleteBuilder.type(indexType);
        }
        elasticSearchClient.execute(deleteBuilder.build());
    }
    public boolean checkIndexExists(String indexName) throws Exception {
        IndicesExists.Builder existsBuilder = new IndicesExists.Builder(indexName);
        JestResult result = elasticSearchClient.execute(existsBuilder.build());
        return result.isSucceeded();
    }
    public void createEmptyIndex(String indexName, String indexType,  Map<String, String> settings) throws Exception {
        deleteIndex(indexName, null);
        CreateIndex.Builder createBuilder = new CreateIndex.Builder(indexName);
        if (null != settings && !settings.isEmpty()) {
            createBuilder.settings(settings);
        }
        elasticSearchClient.execute(createBuilder.build());
    }
    
    public void createIndex(List<?> data, String indexName, String indexType, Object mappingObj) throws Exception {
        if (data == null || data.isEmpty()) return;
        deleteIndex(indexName, indexType);
        createMapping(indexName, indexType, mappingObj);
        bulkPutData(data, indexName, indexType);
    }
    
    public void createMapping(String indexName, String indexType, Object mappingObj) throws Exception {
        PutMapping putMapping = new PutMapping.Builder(indexName, indexType, mappingObj).build();
        JestResult result = elasticSearchClient.execute(putMapping);
        if (result == null) throw new ElasticSearchException("Create mapping field.");
    }

    public void putData(Object data, String indexName, String indexType) throws Exception {
        Index dataIndex = new Index.Builder(data).index(indexName).type(indexType).build();
        elasticSearchClient.execute(dataIndex);
    }
    
    public void bulkPutData(List<?> data, String indexName, String indexType) throws Exception {
        List<Index> actions = new ArrayList<Index>();
        Index action = null;
        for (Object obj : data) {
            action = new Index.Builder(obj).index(indexName).type(indexType).build();
            actions.add(action);
        }
        Bulk bulk = new Bulk.Builder().defaultIndex(indexName).defaultType(indexType).addAction(actions).build();
        elasticSearchClient.execute(bulk);
    }
    
    public <T> PageModel<T> query(String[] keyWords, String[] fieldName, String indexName, String indexType, Class<T> type, Integer pageNo, Integer pageSize) throws Exception {
        Search.Builder search = new Search.Builder(getQueryBuilder().createMatchAllQuery(keyWords, fieldName));
        search = search.addIndex(indexName).addType(indexType);
        search = search.setParameter("from", (pageNo - 1) * pageSize).setParameter("size", pageSize);
        return getJestResultConvert().convert(elasticSearchClient.execute(search.build()), pageNo, pageSize, type);
    }
    
    public <T> T query(String keyWord, String field, String indexName, String indexType, Class<T> type) throws Exception {
        if (!checkIndexExists(indexName)) {
            return null;
        }
        Search.Builder search = new Search.Builder(getQueryBuilder().createIdQuery(keyWord, indexType));
        search = search.addIndex(indexName).addType(indexType);
        return getJestResultConvert().convert(elasticSearchClient.execute(search.build()), type);
    }

    public <T> List<T> query(String keyWork, Map<String, Float> fieldsMap, List<Sort> sorts, String indexName, String indexType, Class<T> type, boolean isFuzzy) throws Exception {
        Search.Builder search = new Search.Builder(getQueryBuilder().createMultiFieldQuery(keyWork, fieldsMap));
        search = search.addIndex(indexName).addType(indexType).setSearchType(SearchType.QUERY_THEN_FETCH);
        if (sorts != null && !sorts.isEmpty()) search = search.addSort(sorts);
        return getJestResultConvert().convertList(elasticSearchClient.execute(search.build()), type);
    }
    
    public <T> List<T> query(String keyWork, String[] fieldNames, String indexName, String indexType, Class<T> type) throws Exception {
        Search.Builder search = new Search.Builder(getQueryBuilder().createMultiFieldKeyQuery(keyWork, fieldNames));
        search = search.addIndex(indexName).addType(indexType).setParameter("from", 0).setParameter("size", 10);
        return getJestResultConvert().convertList(elasticSearchClient.execute(search.build()), type);
    }
    public <T> List<T> prefixQuery(String keyWork, String fieldName, String indexName, String indexType, Class<T> type) throws Exception {
        Search.Builder search = new Search.Builder(getQueryBuilder().createFieldPrefixKeyQuery(keyWork, fieldName));
        search = search.addIndex(indexName).addType(indexType).setParameter("from", 0).setParameter("size", 10);
        return getJestResultConvert().convertList(elasticSearchClient.execute(search.build()), type);
    }
    
    public <T> PageModel<T> query(String keyWork, Map<String, Float> fieldsMap, List<Sort> sorts, String indexName, String indexType, Class<T> type, boolean isFuzzy, Integer pageNo, Integer pageSize) throws Exception {
        Search.Builder search = new Search.Builder(getQueryBuilder().createMultiFieldQuery(keyWork, fieldsMap));
        search = search.addIndex(indexName).addType(indexType);
        if (sorts != null && !sorts.isEmpty()) search = search.addSort(sorts);
        search = search.setParameter("from", (pageNo - 1) * pageSize).setParameter("size", pageSize);
        return getJestResultConvert().convert(elasticSearchClient.execute(search.build()), pageNo, pageSize, type);
    }
    
    public boolean delete(String keyWork, String[] fieldName, String indexName, String indexType, boolean isFuzzy) throws Exception {
        DeleteByQuery query = new DeleteByQuery.Builder(getQueryBuilder().createBoolQuery(keyWork, fieldName, isFuzzy)).addIndex(indexName).addType(indexType).build();
        return getJestResultConvert().validate(elasticSearchClient.execute(query), indexName);
    }
    
    public boolean delete(String indexId, String indexName, String indexType) throws Exception {
        Delete.Builder deleteBuilder = new Delete.Builder(indexName, indexType, indexId);
        return elasticSearchClient.execute(deleteBuilder.build()).isSucceeded();
    }
    
    @Inject
    public void setElasticSearchSettings(ElasticSearchSettings elasticSearchSettings) {
        this.elasticSearchSettings = elasticSearchSettings;
    }

}