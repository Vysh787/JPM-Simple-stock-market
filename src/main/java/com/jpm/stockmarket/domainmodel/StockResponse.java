package com.jpm.stockmarket.domainmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StockResponse {

	@JsonProperty("dividendYield")
	private String dividendYield;

	@JsonProperty("peRatio")
	private String peRatio;

	@JsonProperty("volumeWeightedStockPrice")
	private double volumeWeightedStockPrice;

	@JsonProperty("GBCEAllShareIndex")
	private double GBCEAllShareIndex;

	public String getDividendYield() {
		return dividendYield;
	}

	public void setDividendYield(String dividendYield) {
		this.dividendYield = dividendYield;
	}

	public String getPeRatio() {
		return peRatio;
	}

	public void setPeRatio(String peRatio) {
		this.peRatio = peRatio;
	}

	public double getVolumeWeightedStockPrice() {
		return volumeWeightedStockPrice;
	}

	public void setVolumeWeightedStockPrice(double volumeWeightedStockPrice) {
		this.volumeWeightedStockPrice = volumeWeightedStockPrice;
	}

	public double getGBCEAllShareIndex() {
		return GBCEAllShareIndex;
	}

	public void setGBCEAllShareIndex(double GBCEAllShareIndex) {
		this.GBCEAllShareIndex = GBCEAllShareIndex;
	}
}
