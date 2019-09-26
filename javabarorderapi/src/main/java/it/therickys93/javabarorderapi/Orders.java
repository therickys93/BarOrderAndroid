package it.therickys93.javabarorderapi;

import com.google.gson.JsonObject;

public class Orders implements Sendable {

	private String method;
	private String endpoint;
	
	public Orders() {
		this.method = "GET";
		this.endpoint = "/v1/orders";
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
