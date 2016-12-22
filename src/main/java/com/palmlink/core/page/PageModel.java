package com.palmlink.core.page;

import java.io.Serializable;
import java.util.List;

public class PageModel<T>  implements Serializable {

    /**   
     * @Fields serialVersionUID : TODO  
     */
    private static final long serialVersionUID = 1L;

    //total records
    private long totalRecords;

    //results
    private List<T> records;

    //current page no
    private long pageNo;

    //page size
    private long pageSize; 

    public long getTotalRecords() {
        return totalRecords;
    }

    /**
     * get total pages
     *
     * @return
     */
    public long getTotalPages() {
        return (totalRecords + pageSize - 1) / pageSize;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getPageNo() {
        return pageNo;
    }

    public void setPageNo(long pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * get first page
     *
     * @return
     */
    public long getTopPageNo() {
        return 1;
    }

    /**
     * get previous page
     *
     * @return
     */
    public long getPreviousPageNo() {
        if (pageNo <= 1) {
            return 1;
        }
        return pageNo - 1;
    }

    /**
     * get next page
     *
     * @return
     */
    public long getNextPageNo() {
        if (pageNo >= getTotalPages()) {
            return getTotalPages() == 0 ? 1 : getTotalPages();
        }
        return pageNo + 1;
    }

    /**
     * get last page
     *
     * @return
     */
    public long getBottomPageNo() {
        return getTotalPages() == 0 ? 1 : getTotalPages();
    }
}
