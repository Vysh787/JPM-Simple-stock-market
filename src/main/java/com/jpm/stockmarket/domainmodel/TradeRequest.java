package com.jpm.stockmarket.domainmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by vaisakh on 15/03/2016.
 */
public class TradeRequest {
    @JsonProperty("stockSymbol")
    private String stock;

    @JsonProperty("price")
    private String price;

    @JsonProperty("quantity")
    private String quantity;

    @JsonProperty("tradeIndicator")
    private String tradeIndicator;
}
