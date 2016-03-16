package com.jpm.stockmarket.service;

import com.jpm.stockmarket.dto.StockIndicator;
import com.jpm.stockmarket.dto.StockSymbol;
import com.jpm.stockmarket.exception.StockServiceException;

/**
 * Created by vaisakh on 15/03/2016.
 */
public interface IStockManager {
    public double calculateDividendYield(String stock, double price) throws StockServiceException;
    public double calculatePERatio(String stock, double price);
    public void recordTrade(String stock, String stockIndicator, int quantity,
        double price);
    public double calculateVolumeWeightedStockPrice(String stock , boolean isWithDuration);
    public double calculateGBCEAllShareIndex();
}
