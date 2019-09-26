package it.therickys93.javabarorderapi;


import com.google.gson.JsonObject;

public class InsertProduct implements Sendable {

	private String endpoint;
	private String method;
	
	public InsertProduct(String product){
		this.method = "POST";
		this.endpoint = "/v1/insertProduct/" + product;
	}
	
	public InsertProduct(String product, double price) {
		this.method = "POST";
		this.endpoint = "/v1/insertProduct/" + product + "/" + price;
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
