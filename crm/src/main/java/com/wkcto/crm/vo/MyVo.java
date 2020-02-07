package com.wkcto.crm.vo;

import java.util.List;

/**
 * 动力节点7777
 */
public class MyVo<T>{
    private List<T> alist;
    private Integer total;

    public List<T> getAlist() {
        return alist;
    }

    public void setAlist(List<T> alist) {
        this.alist = alist;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
