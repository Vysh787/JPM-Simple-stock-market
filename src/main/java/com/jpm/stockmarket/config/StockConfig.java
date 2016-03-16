package com.jpm.stockmarket.config;

import com.jpm.stockmarket.service.StockManager;
import com.jpm.stockmarket.service.TradeManager;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;

import java.io.FileNotFoundException;

@Profile("test")
@EnableAspectJAutoProxy(proxyTargetClass = false)
@ComponentScan(basePackages = {
        "com.jpm.stockmarket.service"},
        scopedProxy = ScopedProxyMode.INTERFACES,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class),
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Configuration.class)})
@Import({PropertyPlaceholderConfig.class})
@Configuration
public class StockConfig {

    @Bean(name = "stockService")
    public StockManager stocksService() throws FileNotFoundException {
        StockManager stockManager = new StockManager();
        stockManager.setTradeManager(TradeManager.getInstance());
        return stockManager;
    }
}
