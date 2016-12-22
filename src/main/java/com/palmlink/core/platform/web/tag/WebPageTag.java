package com.palmlink.core.platform.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import org.springframework.util.StringUtils;

import com.palmlink.core.util.I18nUtil;

public class WebPageTag extends TagSupport {

    private static final int STEP = 3;

    private String url;

    private Integer pageNo;

    private Integer pageSize;

    private Integer totalRecords;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }

    private Integer getTotalPages() {
        return (totalRecords + pageSize - 1) / pageSize;
    }

    private String createPageNav() {
        String recourdUnit = ctx.getBean(I18nUtil.class).getMessage("nstechs.i18n.jsp.page.recordUnit");
        String prevPage = ctx.getBean(I18nUtil.class).getMessage("nstechs.i18n.jsp.page.prePage");
        String nextPage = ctx.getBean(I18nUtil.class).getMessage("nstechs.i18n.jsp.page.nextPage");
        String showUrl = url + (url.contains("?") ? "&" : "?");
        StringBuilder div = new StringBuilder();
        div.append("<span class=\"totalCounter\"><i>" + (totalRecords == null ? 0 : totalRecords) + "</i>&nbsp;" + recourdUnit + "</span>");
        if (pageNo <= 1)
            div.append("<span class=\"prev\">" + prevPage + "</span>");
        else {
            div.append("<a href=\"" + showUrl + "pageNo=" + (pageNo - 1) + "&pageSize=" + pageSize + "\" class=\"prev\">" + prevPage + "</a>");
            if (pageNo - STEP > 1)
                div.append("<a href=\"" + showUrl + "pageNo=" + 1 + "&pageSize=" + pageSize + "\">1</a><span class=\"page__\">...</span>");
        }

        for (int i = STEP; i > 0; i--) {
            if (pageNo - i > 0)
                div.append("<a href=\"" + showUrl + "pageNo=" + (pageNo - i) + "&pageSize=" + pageSize + "\">" + (pageNo - i) + "</a>");
            else
                continue;
        }
        div.append("<strong>" + pageNo + "</strong>");
        Integer totalPages = getTotalPages();
        for (int i = 1; i <= STEP; i++) {
            if (pageNo + i <= totalPages)
                div.append("<a href=\"" + showUrl + "pageNo=" + (pageNo + i) + "&pageSize=" + pageSize + "\">" + (pageNo + i) + "</a>");
            else
                continue;
        }

        if (pageNo >= totalPages)
            div.append("<span class=\"next\">" + nextPage + "</span>");
        else {
            if (pageNo + STEP < totalPages)
                div.append("<span class=\"page__\">...</span><a href=\"" + showUrl + "pageNo=" + totalPages + "&pageSize=" + pageSize + "\">" + totalPages + "</a>");
            div.append("<a href=\"" + showUrl + "pageNo=" + (pageNo + 1) + "&pageSize=" + pageSize + "\" class=\"next\">" + nextPage + "</a>");
        }
        return div.toString();
    }

    @Override
    protected int startTag() throws Exception {
        if (!StringUtils.hasText(url))
            return SKIP_BODY;
        try {
            pageContext.getOut().print(createPageNav());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }
    
    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

}
