package it.therickys93.javabarorderapi;

import com.google.gson.JsonObject;

public class DeleteProduct implements Sendable {

	private String method;
	private String endpoint;
	
	public DeleteProduct(String product){
		this.method = "POST";
		this.endpoint = "/v1/deleteProduct/" + product;
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
