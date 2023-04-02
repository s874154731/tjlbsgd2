package com.sgd.tjlb.zhxf.entity;

import java.math.BigDecimal;

/**
 * 已购买流量bean
 */
public class PurchasedTrafficfessBean {

    private int product_id;//产品ID
    private String product_title;//产品标题
    private BigDecimal product_total;//产品总价格
    private String start_time;//开始时间
    private String end_time;//结束时间

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }

    public BigDecimal getProduct_total() {
        return product_total;
    }

    public void setProduct_total(BigDecimal product_total) {
        this.product_total = product_total;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }
}
