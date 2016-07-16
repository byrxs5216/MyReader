package com.bupt.read.model.entity;

import java.io.Serializable;

/**
 * Created by xs on 2016/6/20.
 */
public class BaseBody implements Serializable{
    protected int allNum;
    protected int allPages;
    //protected List<T> contentlist;
    protected int currentPage;
    protected int maxResult;

    public int getAllNum() {
        return allNum;
    }

    public void setAllNum(int allNum) {
        this.allNum = allNum;
    }

    public int getAllPages() {
        return allPages;
    }

    public void setAllPages(int allPages) {
        this.allPages = allPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }
}
