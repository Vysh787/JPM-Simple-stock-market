package com.jpm.stockmarket.domainmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StockRequest {

	@JsonProperty("stockSymbol")
	private String stock;

	@JsonProperty("price")
	private String price;

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
}
