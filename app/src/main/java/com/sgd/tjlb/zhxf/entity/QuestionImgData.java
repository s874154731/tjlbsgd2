package com.sgd.tjlb.zhxf.entity;

public class QuestionImgData {

    public static final int Add_Gone = 0;
    public static final int Add_Visible = 1;
    public static final int Minus_Visible = 2;

    private Object url;
    private int type;//0 不显示 1 加号 2 减号

    public Object getUrl() {
        return url;
    }

    public void setUrl(Object url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
