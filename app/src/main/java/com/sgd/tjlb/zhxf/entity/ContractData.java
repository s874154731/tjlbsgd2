package com.sgd.tjlb.zhxf.entity;

import java.math.BigDecimal;

/**
 * 合同管理 Data
 */
public class ContractData {

    private String imageUrl;
    private String name;
    private int buyNum;
    private BigDecimal contractMoney;
    private BigDecimal totalMoney;
    private int isBuy;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }

    public BigDecimal getContractMoney() {
        return contractMoney;
    }

    public void setContractMoney(BigDecimal contractMoney) {
        this.contractMoney = contractMoney;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public boolean isBuy() {
        return isBuy == 1;
    }

    public void setBuy(int buy) {
        isBuy = buy;
    }
}
