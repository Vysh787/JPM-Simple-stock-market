package com.jpm.stockmarket.controller;

import com.jpm.stockmarket.domainmodel.StockRequest;
import com.jpm.stockmarket.domainmodel.StockResponse;
import com.jpm.stockmarket.domainmodel.TradeResponse;
import com.jpm.stockmarket.exception.StockServiceException;
import com.jpm.stockmarket.service.StockManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by vaisakh on 14/03/2016.
 * Spring MVC controller class where all the service endpoints are defined.
 * Corresponding business logic is invoked from the service methods.
 * The “StockManager” instance is a singleton bean injected to the Controller.
 */

@Api("Stocks service controller")
@Controller
@RequestMapping("/api/v1.0")

public class StockServiceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockServiceController.class);

    @Autowired
    private StockManager stockManager;

    @ApiOperation(nickname = "Calculate Dividend Yield", consumes = "application/json",
        produces = "application/json", value = "For a given stock, given any price as input, calculate the dividend yield",
        notes = "API for For a given stock, given any price as input, calculate the dividend yield",
        httpMethod = "POST", response = StockResponse.class)
    @RequestMapping(value = {"/stocks/calculateDividendYield"}, method = {RequestMethod.POST})
    public ResponseEntity<StockResponse> calculateDividendYield(@RequestBody StockRequest stockRequest) throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        if (stockRequest != null && StringUtils.isNotBlank(stockRequest.getStock())
            && StringUtils.isNotBlank(stockRequest.getPrice())) {

            try {
                StockResponse response = new StockResponse();
                double dividendYield = stockManager
                    .calculateDividendYield(stockRequest.getStock(), Double.parseDouble(stockRequest.getPrice()));
                response.setDividendYield(String.valueOf(dividendYield));
                response.setPeRatio(null);
                return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
            }catch (StockServiceException se) {
                LOGGER.error("Error processing request" + se.getMessage());
                return new ResponseEntity<>(null, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            LOGGER.error("Invalid request");
            return new ResponseEntity<>(null, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(nickname = "Calculate P/E Ratio", consumes = "application/json",
        produces = "application/json", value = "For a given stock, given any price as input, calculate the P/E Ratio",
        notes = "For a given stock, given any price as input, calculate the P/E Ratio",
        httpMethod = "POST", response = StockResponse.class)
    @RequestMapping(value = {"/stocks/calculatePERatio"}, method = {RequestMethod.POST})
    public ResponseEntity<StockResponse> calculatePERatio(@RequestBody StockRequest stockRequest) throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        if (stockRequest != null && StringUtils.isNotBlank(stockRequest.getStock())
            && StringUtils.isNotBlank(stockRequest.getPrice())) {

            try {
                StockResponse response = new StockResponse();
                Double peRatio = stockManager
                    .calculatePERatio(stockRequest.getStock(), Double.parseDouble(stockRequest.getPrice()));
                response.setPeRatio(String.valueOf(peRatio));
                response.setDividendYield(null);
                return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
            }catch (StockServiceException se) {
                LOGGER.error("Error processing request." + se.getMessage());
                return new ResponseEntity<>(null, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            LOGGER.error("Invalid request");
            return new ResponseEntity<>(null, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(nickname = "Record a trade, with timestamp, quantity, buy or sell indicator and price", consumes = "application/json",
        produces = "application/json", value = "For a given stock,Record a trade, with timestamp, quantity, buy or sell indicator and price",
        notes = "For a given stock, Record a trade, with timestamp, quantity, buy or sell indicator and price",
        httpMethod = "POST", response = TradeResponse.class)
    @RequestMapping(value = {"/stocks/recordTrade"}, method = {RequestMethod.POST})
    public ResponseEntity<TradeResponse> recordTrade( @RequestParam("stockSymbol") String stock,
        @RequestParam("price") Double price, @RequestParam("quantity") Integer quantity,
        @RequestParam("tradeIndicator") String tradeIndicator) throws Exception {

        HttpHeaders httpHeaders = new HttpHeaders();
        if ((stock != null && StringUtils.isNotBlank(stock)) &&
            (price != null) && (quantity != null) &&
            (tradeIndicator != null && StringUtils.isNotBlank(tradeIndicator))) {

            try {
                TradeResponse response = new TradeResponse();
                stockManager.recordTrade(stock,tradeIndicator,quantity,price);
                response.setResult("success");
                return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
            }catch (StockServiceException se) {
                LOGGER.error("Error processing request." + se.getMessage());
                return new ResponseEntity<>(null, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            LOGGER.error("Invalid request");
            return new ResponseEntity<>(null, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(nickname = "For a given stock , calculate Volume Weighted Stock Price based on trades in past 5 minutes", consumes = "application/json",
        produces = "application/json", value = "For a given stock,calculate Volume Weighted Stock Price based on trades in past 5 minutes",
        notes = "For a given stock, calculate Volume Weighted Stock Price based on trades in past 5 minutes",
        httpMethod = "POST", response = StockResponse.class)
    @RequestMapping(value = {"/stocks/volumeWeightedStockPrice"}, method = {RequestMethod.POST})
    public ResponseEntity<StockResponse> calculateVolumeWeightedStockPrice( @RequestParam("stockSymbol") String stock) throws Exception {

        HttpHeaders httpHeaders = new HttpHeaders();
        if ((stock != null && StringUtils.isNotBlank(stock))) {
            try {
                StockResponse response = new StockResponse();
                double volumeWeightedStockPrice = stockManager
                    .calculateVolumeWeightedStockPrice(stock , true /*isForDuration*/);
                response.setVolumeWeightedStockPrice(volumeWeightedStockPrice);
                return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
            }catch (StockServiceException se) {
                LOGGER.error("Error processing request." + se.getMessage());
                return new ResponseEntity<>(null, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            LOGGER.error("Invalid request");
            return new ResponseEntity<>(null, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(nickname = "Calculate the GBCE All Share Index using the geometric mean of the Volume Weighted Stock Price for all stocks", consumes = "application/json",
        produces = "application/json", value = "Calculate the GBCE All Share Index using the geometric mean of the Volume Weighted Stock Price for all stocks",
        notes = "Calculate the GBCE All Share Index using the geometric mean of the Volume Weighted Stock Price for all stocks",
        httpMethod = "POST", response = StockResponse.class)
    @RequestMapping(value = {"/stocks/calculateGBCEAllShareIndex"}, method = {RequestMethod.POST})
    public ResponseEntity<StockResponse> calculateGBCEAllShareIndex() throws Exception {

        HttpHeaders httpHeaders = new HttpHeaders();
        try {
            StockResponse response = new StockResponse();
            double GBCEAllShareIndex = stockManager.calculateGBCEAllShareIndex();
            response.setGBCEAllShareIndex(GBCEAllShareIndex);
            return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
        }catch (StockServiceException se) {
            LOGGER.error("Error processing request." + se.getMessage());
            return new ResponseEntity<>(null, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}