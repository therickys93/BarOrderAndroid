package it.therickys93.javabarorderapi;

import com.google.gson.JsonObject;

public class InsertOrder implements Sendable {

	private String method;
	private String endpoint;
	private Order order;
	
	public InsertOrder(Order order){
		this.method = "POST";
		this.endpoint = "/v1/insertOrder";
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
