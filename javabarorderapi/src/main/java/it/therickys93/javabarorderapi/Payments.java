package it.therickys93.javabarorderapi;

import com.google.gson.JsonObject;

public class Payments implements Sendable {

	private String method;
	private String endpoint;
	
	public Payments() {
		this.method = "GET";
		this.endpoint = "/v1/payments";
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
