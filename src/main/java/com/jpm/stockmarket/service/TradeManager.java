package com.jpm.stockmarket.service;

import com.jpm.stockmarket.dto.Stock;
import com.jpm.stockmarket.dto.StockSymbol;
import com.jpm.stockmarket.dto.Trade;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

/**
 * Created by vaisakh on 14/03/2016.
 */
public class TradeManager implements ITradeManager{

    private List<Trade> trades = new ArrayList<Trade>();
    private static TradeManager tm = null;

    private static final Logger LOGGER = LoggerFactory.getLogger(TradeManager.class);

    private TradeManager() {}

    public static TradeManager getInstance() {
        if(tm == null){
            synchronized (TradeManager.class) {
                if(tm == null) {
                    tm = new TradeManager();
                }
            }
        }
        return tm;
    }

    public void addTrade(Trade trade) {
        synchronized (this.trades) {
            this.trades.add(trade);
            LOGGER.info("Trade recorded successfully for stock " +trade.getStock().getStockSymbol());
            LOGGER.debug(trade.toString());
        }
    }

    public int getTradeCount() {
        return this.trades.size();
    }

    public Collection<Trade> getTradesForDuration(Stock stock,int duration){
        synchronized (this.trades) {
            Collection<Trade> trades = CollectionUtils.select(this.trades, new TradeDurationPredicate(duration , stock.getStockSymbol()));
            return trades;
        }
    }

    private class TradeDurationPredicate implements Predicate {

        StockSymbol stockSymbol;
        Calendar currentTime = null;

        public TradeDurationPredicate(int duration,StockSymbol stockSymbol) {
            this.stockSymbol = stockSymbol;
            if(duration > 0) {
                this.currentTime = Calendar.getInstance();
                currentTime.add(Calendar.MINUTE, -duration);
            }
        }

        @Override public boolean evaluate(Object o) {
            Trade t = (Trade)o;
            Calendar tradeTime = t.getCurrentDate();

            if(currentTime != null) {
                if((tradeTime.compareTo(currentTime) > 0 ) && t.getStock().getStockSymbol().equals(stockSymbol)) {
                    return true;
                }else{
                    return false;
                }
            }  else if(t.getStock().getStockSymbol().equals(stockSymbol)){
                return true;
            } else {
                return false;
            }
        }
    }
}
