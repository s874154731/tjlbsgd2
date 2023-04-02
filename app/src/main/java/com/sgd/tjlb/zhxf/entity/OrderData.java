package com.sgd.tjlb.zhxf.entity;

public class OrderData {

    private String name;
    private int type;
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String showType() {
        return type == 1 ? "维修" : "安装";
    }

}
