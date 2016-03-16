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
 *
 * The service class containing the root implementation for Stock Service capabilities.
 */
public class StockManager implements IStockManager {

    public TradeManager tradeManager = null;
    private int DURATION = 5;
    private static final Logger LOGGER = LoggerFactory.getLogger(StockManager.class);

    /**+
     * Calculates the Dividend yield for a given Stock and price
     *
     * @param stockSymbolStr
     * @param price
     * @return
     * @throws StockServiceException
     */
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

    /**+
     * Calculates PE Ratio for a given stock and price.
     *
     * @param stockSymbolStr
     * @param price
     * @return
     */
    @Override public double calculatePERatio(String stockSymbolStr, double price) {
        StockSymbol stockSymbol = getStockSymbol(stockSymbolStr);
        if (stockSymbol != null) {
            Stock stock = new Stock(stockSymbol);
            return stock.calculatePERatio(price);
        } else {
            throw new StockServiceException("Stock "+stockSymbol+"not supported.");
        }
    }

    /**+
     *
     * Records a trade with stock,buy/sell indicator,quantity and price.
     *
     * @param stockSymbolStr
     * @param stockIndicatorStr
     * @param quantity
     * @param price
     */
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

    /**+
     * Calculate volume weighted stock price for a givenb stock.
     * @param isWithDuration flag specifies if all the trades has to be considered for the calcualation or
     * a predefined duration of 5 minutes.
     *
     * @param stockSymbolStr
     * @param isWithDuration
     * @return
     */
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

    /**+
     *
     * Calculates GBCE All share index based on the volume weighted stock price for all the stocks.
     *
     * @return
     * @throws S    tockServiceException
     */
    @Override
    public double calculateGBCEAllShareIndex() throws StockServiceException{
        try {
            double volumeWeightedProduct = 1D;//--Initialize to 1 so that the product doesn't error out
            boolean isTradesPresent = false;

            for (StockSymbol s : StockSymbol.values()) {
                double tmpVWSP = calculateVolumeWeightedStockPrice(s.name(), false);
                if(tmpVWSP == 0)
                    continue;

                isTradesPresent = true; //--Set the flag if there are valid trades available.
                volumeWeightedProduct *=tmpVWSP;
            }

            //--If there are not trades available, return 0D
            if(!isTradesPresent) return 0D;

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
