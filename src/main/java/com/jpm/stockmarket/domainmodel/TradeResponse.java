package com.jpm.stockmarket.domainmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vaisakh on 15/03/2016.
 */
public class TradeResponse {

    @JsonProperty("result")
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
