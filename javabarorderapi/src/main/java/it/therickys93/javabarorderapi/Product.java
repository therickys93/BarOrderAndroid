package it.therickys93.javabarorderapi;

import com.google.gson.JsonObject;

public class Product {

	private static final String QUANTITY = "quantity";
	private static final String NAME = "name";
	private String name;
	private int quantity;
	
	public Product(String name, int quantity) {
		this.name = name;
		this.quantity = quantity;
	}

	public String name() {
		return this.name;
	}

	public int quantity() {
		return this.quantity;
	}
	
	@Override
	public String toString() {
		String response = "";
		response += "Product={name="+ this.name + ", quantity=" + this.quantity + "}";
		return response;
	}

	public JsonObject toJson() {
		JsonObject product = new JsonObject();
		product.addProperty(NAME, this.name);
		product.addProperty(QUANTITY, this.quantity);
		return product;
	}

	public String prettyToString() {
		return this.quantity + " " + this.name;
	}

}
