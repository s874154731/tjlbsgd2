package com.sgd.tjlb.zhxf.entity;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * 银行卡信息
 */
public class BankInfo implements Serializable {

    private int bank_id;//银行id
    private String bank_name;//银行名称
    private String bank_url;//银行URL
    private String create_time;//申请时间
    private String bank_user_id;// 银行id。。。。。。。。。。。。。。
    private String bank_user;//开户名称
    private String bank_no;//卡号
    private String bank_address;//地址

    public String getBank_user_id() {
        return TextUtils.isEmpty(bank_user_id) ? "" : bank_user_id;
    }

    public void setBank_user_id(String bank_user_id) {
        this.bank_user_id = bank_user_id;
    }

    public String getBank_user() {
        return TextUtils.isEmpty(bank_user) ? "" : bank_user;
    }

    public void setBank_user(String bank_user) {
        this.bank_user = bank_user;
    }

    public String getBank_no() {
        return TextUtils.isEmpty(bank_no) ? "" : bank_no;
    }

    public void setBank_no(String bank_no) {
        this.bank_no = bank_no;
    }

    public String getBank_address() {
        return TextUtils.isEmpty(bank_address) ? "" : bank_address;
    }

    public void setBank_address(String bank_address) {
        this.bank_address = bank_address;
    }

    public String getBankName() {
        return TextUtils.isEmpty(bank_name) ? "" : bank_name;
    }

    public void setBankName(String bankName) {
        this.bank_name = bankName;
    }

    public int getBank_id() {
        return bank_id;
    }

    public void setBank_id(int bank_id) {
        this.bank_id = bank_id;
    }

    public String getBank_url() {
        return bank_url;
    }

    public void setBank_url(String bank_url) {
        this.bank_url = bank_url;
    }

    public String getCreate_time() {
        return TextUtils.isEmpty(create_time) ? "1900-01-01 00:00:00" : create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
