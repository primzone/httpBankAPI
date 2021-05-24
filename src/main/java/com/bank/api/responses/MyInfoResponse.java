package com.bank.api.responses;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MyInfoResponse {
    private String info;

    public  static String getMyInfoResponseJSON(String s) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(new MyInfoResponse(s));
    }

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
