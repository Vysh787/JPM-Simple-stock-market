package com.jpm.stockmarket.service;

import com.jpm.stockmarket.dto.Stock;
import com.jpm.stockmarket.dto.Trade;

import java.util.Collection;

/**
 * Created by vaisakh on 15/03/2016.
 */
public interface ITradeManager {
    public void addTrade(Trade trade);
    public Collection<Trade> getTradesForDuration(Stock stock,int duration);
    public int getTradeCount();
}
