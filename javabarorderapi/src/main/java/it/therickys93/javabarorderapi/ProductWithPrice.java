package it.therickys93.javabarorderapi;

public class ProductWithPrice {

	private String name;
	private double price;
	
	public ProductWithPrice(String name, double price) {
		this.name = name;
		this.price = price;
	}

	public String name() {
		return this.name;
	}

	public double price() {
		return this.price;
	}

}
