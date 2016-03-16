package com.jpm.stockmarket.dto;

import java.util.Calendar;

/**
 * Created by vaisakh on 15/03/2016.
 */
public class Trade {

    private Stock stock;
    private StockIndicator stockIndicator;
    private double price;
    private int quantiy;
    private Calendar currentDate;

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public StockIndicator getStockIndicator() {
        return stockIndicator;
    }

    public void setStockIndicator(StockIndicator stockIndicator) {
        this.stockIndicator = stockIndicator;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantiy() {
        return quantiy;
    }

    public void setQuantiy(int quantiy) {
        this.quantiy = quantiy;
    }

    public Calendar getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Calendar currentDate) {
        this.currentDate = currentDate;
    }

    @Override public String toString() {
        return "Trade{" +
            "stock=" + stock +
            ", stockIndicator=" + stockIndicator +
            ", price=" + price +
            ", quantiy=" + quantiy +
            ", currentDate=" + currentDate +
            '}';
    }
}
