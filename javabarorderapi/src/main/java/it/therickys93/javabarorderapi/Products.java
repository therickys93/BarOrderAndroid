package it.therickys93.javabarorderapi;

import com.google.gson.JsonObject;

public class Products implements Sendable {

	private String method;
	private String endpoint;
	
	public Products(){
		this.method = "GET";
		this.endpoint = "/v1/products";
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
