package com.jpm.stockmarket.dto;

/**
 * Created by vaisakh on 14/03/2016.
 */
public enum StockSymbol {

    TEA (0D , null , 100D , StockType.Common),
    POP (8D , null , 100D , StockType.Common),
    ALE (23D , null , 100D , StockType.Common),
    GIN (8D , 2 , 100D , StockType.Preferred),
    JOE (13D , null , 250D , StockType.Common);

    private Double lastDividend;
    private Integer fixedDividend;
    private Double parValue;

    StockType stockType;

    StockSymbol(Double lastDividend,Integer fixedDividend , Double parValue , StockType stockType) {
        this.lastDividend = lastDividend;
        this.fixedDividend = fixedDividend;
        this.parValue = parValue;
        this.stockType = stockType;
    }

    public Double getLastDividend() {
        return lastDividend;
    }

    public Integer getFixedDividend() {
        return fixedDividend;
    }

    public Double getParValue() {
        return parValue;
    }
}
