package com.jpm.stockmarket.dto;

import com.jpm.stockmarket.exception.StockServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Stock object with StockSymbol and utility methods.
 *
 * Created by vaisakh on 15/03/2016.
 */
public class Stock{

    private static final Logger LOGGER = LoggerFactory.getLogger(Stock.class);

    private StockSymbol stockSymbol;

    public Stock(StockSymbol stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public double calculateDividendYield(double price) throws StockServiceException {
        if(price == 0) {
            LOGGER.error("Invalid input. Price cannot be 0");
            throw new StockServiceException("Invalid input. Price cannot be 0");
        }

        double dividendYield;
        if (stockSymbol.stockType.equals(StockType.Preferred)) {
            dividendYield = (stockSymbol.getFixedDividend() * stockSymbol.getParValue()) / price;
        } else {
            dividendYield = stockSymbol.getLastDividend() / price;
        }

        return dividendYield;
    }

    public double calculatePERatio(double price) throws StockServiceException {
        double peRatio = price / calculateDividendYield(price);
        return peRatio;
    }

    public StockSymbol getStockSymbol() {
        return stockSymbol;
    }
}
