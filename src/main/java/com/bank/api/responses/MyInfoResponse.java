package com.bank.api.responses;

public class MyInfoResponse {
    private String info;

    public MyInfoResponse(String info) {
        this.info = info;
    }

    public MyInfoResponse() {
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
