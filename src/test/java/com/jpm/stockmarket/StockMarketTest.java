package com.jpm.stockmarket;

import com.jpm.stockmarket.exception.StockServiceException;
import com.jpm.stockmarket.service.StockManager;
import com.jpm.stockmarket.service.TradeManager;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by vaisakh on 15/03/2016.
 */
public class StockMarketTest {
    private static final String STOCK_POP = "POP";
    private static final String STOCK_ALE = "ALE";
    private static final String STOCK_GIN = "GIN";
    private static final String STOCK_JOE = "JOE";
    private static final String STOCK_TEA = "TEA";

    private static final String STOCK_IND_BUY = "BUY";
    private static final String STOCK_IND_SELL = "SELL";

    private static final String TRADE_ERROR =  "Trade not supported. Invalid input";

    StockManager stockManager = null;

    @Before
    public void setup() {
        stockManager = new StockManager();
        stockManager.setTradeManager(TradeManager.getInstance());
    }

    @Test
    public void calculateDividendYieldPOPTest() {
        double price = 100;
        String stockSymbol = STOCK_POP;
        double dividendYield = stockManager.calculateDividendYield(stockSymbol,price);
        assertEquals(0.08D,dividendYield,0D);
    }

    @Test
    public void calculateDividendYieldGINTest() {
        double price = 100;
        String stockSymbol = STOCK_GIN;
        double dividendYield = stockManager.calculateDividendYield(stockSymbol,price);
        assertEquals(2.0D,dividendYield,0D);
    }

    @Test
    public void calculateDividendYieldALETest() {
        double price = 100;
        String stockSymbol = STOCK_ALE;
        double dividendYield = stockManager.calculateDividendYield(stockSymbol,price);
        assertEquals(0.23D,dividendYield,0D);
    }

    @Test
    public void calculatePERatioPOPTest(){
        double price = 100;
        String stockSymbol = STOCK_POP;
        double peRatio = stockManager.calculatePERatio(stockSymbol,price);
        assertEquals(1250.0D,peRatio,0D);
    }

    @Test
    public void calculatePERatioJOETest(){
        double price = 100;
        String stockSymbol = STOCK_JOE;
        double peRatio = stockManager.calculatePERatio(stockSymbol,price);
        assertEquals(769.2307692307692,peRatio,0D);
    }


    @Test
    public void recordTradeTest() {
        String stockSymbolStr = STOCK_POP;
        String stockIndicatorStr = STOCK_IND_BUY;
        int quantity = 25;
        double price = 100D;
        recordTrade(stockSymbolStr,stockIndicatorStr,quantity,price);

        stockSymbolStr = STOCK_ALE;
        stockIndicatorStr = STOCK_IND_SELL;
        quantity = 25;
        price = 100D;
        recordTrade(stockSymbolStr,stockIndicatorStr,quantity,price);

        //Total count includes trades created by previous tests, since TradeManager is a singleton instance.
        assertEquals(57,stockManager.getTradeManager().getTradeCount());
    }

    @Test
    public void recordTradeWithInvalidStock() {
        try {
            String stockSymbolStr = "PPO";
            String stockIndicatorStr = STOCK_IND_SELL;
            int quantity = 25;
            double price = 100D;
            recordTrade(stockSymbolStr, stockIndicatorStr, quantity, price);
        }catch (StockServiceException ste) {
            assertEquals(TRADE_ERROR,ste.getMessage());
        }
    }

    @Test
    public void calculateVolumeWeightedStockPrice(){
        try{
            stockManager.setDuration(1);
            for(int i=0; i<5 ;i++) {
                recordTradeWithDefValues();
            }
            double volumeWeightedStockPrice =  stockManager.calculateVolumeWeightedStockPrice(STOCK_POP,true);
            assertEquals(500.0D,volumeWeightedStockPrice,0D);
        }catch (Exception e){}
    }

    //@Test - Enabling the test will add 1 minute to the total test execution time.
    public void calculateVolumeWeightedStockPriceWithDelay(){
        try{
            stockManager.setDuration(1);
            for(int i=0; i<10 ;i++) {
                if(i==5) Thread.sleep(60000);
                recordTradeWithDefValues();
            }
            double volumeWeightedStockPrice =  stockManager.calculateVolumeWeightedStockPrice(STOCK_POP,true);
            assertEquals(500.0D,volumeWeightedStockPrice,0D);
        }catch (InterruptedException ie) {

        }catch (Exception e){

        }
    }

    private void recordTrade(String stockSymbolStr, String stockIndicatorStr, int quantity, double price) {
        stockManager.recordTrade(stockSymbolStr,stockIndicatorStr,quantity,price);
    }

    private void recordTradeWithDefValues() {
        String stockSymbolStr = STOCK_POP;
        String stockIndicatorStr = STOCK_IND_BUY;
        int quantity = 25;
        double price = 100D;

        stockManager.recordTrade(stockSymbolStr,stockIndicatorStr,quantity,price);
    }

    @Test
    public void calculateGBCEAllShareIndex(){

        String[] arr = {STOCK_ALE,STOCK_GIN,STOCK_JOE,STOCK_POP,STOCK_TEA};
        for(String stock: arr) {
            for(int i=0;i<10;i++) {
                String stockSymbolStr = stock;
                String stockIndicatorStr = STOCK_IND_BUY;
                int quantity = 25;
                double price = 100D;
                recordTrade(stockSymbolStr,stockIndicatorStr,quantity,price);
            }
        }

        double allShareIndex = stockManager.calculateGBCEAllShareIndex();
        assertEquals(1084.4717711976991D,allShareIndex,0D);
    }


    //@Test
    //Enabling the test will add 10 seconds to the test Execution time.
    //recordTradeTest() may fail as the  actual trade count will 107 instead of 57.
    public void testMultiThreading() {
        try {
            for (int i = 0; i < 50; i++) {
                new Thread(new Runnable() {
                    @Override public void run() {
                        try {
                            Thread.sleep(5100);
                            recordTradeWithDefValues();
                        } catch (InterruptedException e) {

                        }
                    }
                }).start();
            }

            for (int i = 0; i < 50; i++) {
                new Thread(new Runnable() {
                    @Override public void run() {
                        try {
                            Thread.sleep(5000);
                            stockManager.calculateGBCEAllShareIndex();
                        } catch (InterruptedException e) {
                        }
                    }
                }).start();
            }

            try{
                Thread.sleep(10000);
            }catch (Exception e){

            }
        }catch (StockServiceException cme) {
            assertFalse(true); //TODO - Check for interrupted exception type.
        }
    }
}
