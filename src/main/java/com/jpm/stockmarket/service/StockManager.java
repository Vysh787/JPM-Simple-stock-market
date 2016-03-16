package com.jpm.stockmarket.service;

import com.jpm.stockmarket.dto.Stock;
import com.jpm.stockmarket.dto.StockIndicator;
import com.jpm.stockmarket.dto.StockSymbol;
import com.jpm.stockmarket.dto.Trade;
import com.jpm.stockmarket.exception.StockServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Collection;

/**
 * Created by vaisakh on 15/03/2016.
 */
public class StockManager implements IStockManager {

    public TradeManager tradeManager = null;
    private int DURATION = 5;
    private static final Logger LOGGER = LoggerFactory.getLogger(StockManager.class);


    @Override public double calculateDividendYield(String stockSymbolStr, double price)
        throws StockServiceException{

        StockSymbol stockSymbol = getStockSymbol(stockSymbolStr);
        if (stockSymbol != null) {
            Stock stock = new Stock(stockSymbol);
            return stock.calculateDividendYield(price);
        } else {
            throw new StockServiceException("Stock "+stockSymbol+"not supported.");
        }
    }

    @Override public double calculatePERatio(String stockSymbolStr, double price) {
        StockSymbol stockSymbol = getStockSymbol(stockSymbolStr);
        if (stockSymbol != null) {
            Stock stock = new Stock(stockSymbol);
            return stock.calculatePERatio(price);
        } else {
            throw new StockServiceException("Stock "+stockSymbol+"not supported.");
        }
    }

    @Override
    public void recordTrade(String stockSymbolStr, String stockIndicatorStr, int quantity, double price) {
        try {
            StockSymbol stockSymbol = getStockSymbol(stockSymbolStr);
            StockIndicator stockIndicator = getStockIndicator(stockIndicatorStr);

            if(quantity == 0) throw new StockServiceException("Quantity cannot be 0");
            if (stockSymbol != null && stockIndicator != null) {

                Stock stock = new Stock(stockSymbol);

                Trade trade = new Trade();
                trade.setStock(stock);
                trade.setPrice(price);
                trade.setQuantiy(quantity);
                trade.setStockIndicator(stockIndicator);
                trade.setCurrentDate(Calendar.getInstance());

                tradeManager.addTrade(trade);
            } else {
                throw new StockServiceException("Trade not supported.");
            }
        }catch (Exception e) {
            throw new StockServiceException("Trade not supported. Invalid input");
        }
    }

    @Override public double calculateVolumeWeightedStockPrice(String stockSymbolStr ,boolean isWithDuration) {
        try {
            StockSymbol stockSymbol = getStockSymbol(stockSymbolStr);
            if (stockSymbol != null) {

                Stock stock = new Stock(stockSymbol);
                Collection<Trade> tradesForDuration = tradeManager
                    .getTradesForDuration(stock, (isWithDuration)?DURATION:0);

                double tradedPrice = 0D;
                int tradedQuantity = 0;
                double volumeWeightedStockPrice = 0D;

                for(Trade trade: tradesForDuration) {
                    tradedPrice+=trade.getPrice();
                    tradedQuantity+=trade.getQuantiy();
                }

                if(tradedQuantity > 0) volumeWeightedStockPrice = (tradedPrice * tradedQuantity)/tradedQuantity;

                return volumeWeightedStockPrice;
            } else {
                throw new StockServiceException("Stock "+stockSymbolStr +" not supported.");
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw new StockServiceException("Exception occurred while calculating volume weighted stock price.");
        }
    }

    @Override
    public double calculateGBCEAllShareIndex() throws StockServiceException{
        try {
            double volumeWeightedProduct = 1D;
            for (StockSymbol s : StockSymbol.values()) {
                double tmpVWSP = calculateVolumeWeightedStockPrice(s.name(), false);
                volumeWeightedProduct *= (tmpVWSP > 0)?tmpVWSP:1D/*Default*/;
            }

            double geoMean = Math.pow(volumeWeightedProduct, 1.0 / StockSymbol.values().length);
            return geoMean;
        }catch (ArithmeticException ae) {
            throw new StockServiceException("Error calculating GBCE All share Index. \n"+ ae
                .getMessage());
        }catch (Exception e){
            throw new StockServiceException("Error calculating GBCE All share Index. \n"+ e
                .getMessage());
        }
    }

    private StockIndicator getStockIndicator(String stockIndicator) {
        for (StockIndicator s : StockIndicator.values()) {
            if (s.name().equals(stockIndicator)) {
                return s;
            }
        }
        return null;
    }

    private StockSymbol getStockSymbol(String stockSymb) {
        for (StockSymbol s : StockSymbol.values()) {
            if (s.name().equals(stockSymb)) {
                return s;
            }
        }

        return null;
    }

    public void setTradeManager(TradeManager tm) {
        this.tradeManager = tm;
    }

    public TradeManager getTradeManager() {
        return tradeManager;
    }

    public void setDuration(int duration) {
        this.DURATION = duration;
    }
}
