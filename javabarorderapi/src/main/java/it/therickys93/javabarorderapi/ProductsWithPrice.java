package it.therickys93.javabarorderapi;

import com.google.gson.JsonObject;

public class ProductsWithPrice implements Sendable {

	private String method;
	private String endpoint;
	
	public ProductsWithPrice() {
		this.method = "GET";
		this.endpoint = "/v1/productsWithPrice";
	}
	
	@Override
	public String endpoint() {
		return this.endpoint;
	}

	@Override
	public JsonObject toJson() {
		return null;
	}

	@Override
	public String method() {
		return this.method;
	}

}
