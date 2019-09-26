package it.therickys93.javabarorderapi;

import com.google.gson.JsonObject;

public class CompleteOrder implements Sendable {

	private String method;
	private String endpoint;
	private Order order;
	
	public CompleteOrder(Order order){
		this.method = "POST";
		this.endpoint = "/v1/completeOrder";
		this.order = order;
	}
	
	@Override
	public String endpoint() {
		return this.endpoint;
	}

	@Override
	public JsonObject toJson() {
		return this.order.toJson();
	}

	@Override
	public String method() {
		return this.method;
	}

}
