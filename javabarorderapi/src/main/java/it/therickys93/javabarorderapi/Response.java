package it.therickys93.javabarorderapi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Response {

	private static final String VERSION = "version";
	private static final String DATABASE = "database";
	private static final String SERVER = "server";
	private static final String PRICE = "price";
	private static final String QUANTITY = "quantity";
	private static final String NAME = "name";
	private static final String PRODUCTS = "products";
	private static final String DONE = "done";
	private static final String TABLE = "table";
	private static final String ID = "id";
	private static final String SUCCESS = "success";
	private boolean ok;
	private boolean server;
	private boolean database;
	private String version;
	
	public static Response parseSuccess(String string) {
		JsonParser parser = new JsonParser();
		JsonObject status = parser.parse(string).getAsJsonObject();
		boolean ok = status.get(SUCCESS).getAsBoolean();
		return new Response(ok);
	}
	
	private Response(boolean ok){
		this.ok = ok;
	}
	
	private Response(boolean server, boolean database, String version){
		this.server = server;
		this.database = database;
		this.version = version;
		this.ok = server && database;
	}

	public boolean ok() {
		return this.ok;
	}

	public static List<Order> parseOrders(String ordersToParse) {
		List<Order> orders = new ArrayList<Order>();
		JsonParser parser = new JsonParser();
		JsonArray ordini = parser.parse(ordersToParse).getAsJsonArray();
		Iterator<JsonElement> iteratorOrdini = ordini.iterator();
		while(iteratorOrdini.hasNext()){
			JsonObject ordine = iteratorOrdini.next().getAsJsonObject();
			int id = ordine.get(ID).getAsInt();
			int table = ordine.get(TABLE).getAsInt();
			boolean done = ordine.get(DONE).getAsBoolean();
			double price = 0.0;
			if(ordine.get(PRICE) != null){
				price = ordine.get(PRICE).getAsDouble();
			}
			JsonArray prodotti = ordine.get(PRODUCTS).getAsJsonArray();
			Iterator<JsonElement> iteratorProdotti = prodotti.iterator();
			Product[] products = new Product[prodotti.size()];
			int index = 0;
			while(iteratorProdotti.hasNext()){
				JsonObject prodotto = iteratorProdotti.next().getAsJsonObject();
				String name = prodotto.get(NAME).getAsString();
				int quantity = prodotto.get(QUANTITY).getAsInt();
				Product product = new Product(name, quantity);
				products[index] = product;
				index++;
			}
			Order order = new Order(id, table, done, products, price);
			orders.add(order);
		}
		return orders;
	}

	public static List<Product> parseProducts(String productsToParse) {
		List<Product> productsList = new ArrayList<Product>();
		JsonParser parser = new JsonParser();
		JsonArray array = parser.parse(productsToParse).getAsJsonArray();
		Iterator<JsonElement> iterator = array.iterator();
		String name;
		while(iterator.hasNext()){
			name = iterator.next().getAsString();
			productsList.add(new Product(name, 0));
		}
		return productsList;
	}

	public static Response parseStatus(String string) {
		if(string.contains(SUCCESS)){
			JsonParser parser = new JsonParser();
			JsonObject status = parser.parse(string).getAsJsonObject();
			boolean ok = status.get(SUCCESS).getAsBoolean();
			return new Response(ok);
		} else {
			JsonParser parser = new JsonParser();
			JsonObject object = parser.parse(string).getAsJsonObject();
			boolean server = object.get(SERVER).getAsBoolean();
			boolean database = object.get(DATABASE).getAsBoolean();
			String version = object.get(VERSION).getAsString();
			return new Response(server, database, version);
		}
	}

	public boolean server() {
		return this.server;
	}

	public boolean database() {
		return this.database;
	}

	public String version() {
		return this.version;
	}

	public static List<ProductWithPrice> parseProductsWithPrice(String productsWithPrice) {
		List<ProductWithPrice> productsList = new ArrayList<ProductWithPrice>();
		JsonParser parser = new JsonParser();
		JsonArray array = parser.parse(productsWithPrice).getAsJsonArray();
		Iterator<JsonElement> iterator = array.iterator();
		String name;
		double price;
		while(iterator.hasNext()){
			JsonObject product = iterator.next().getAsJsonObject();
			name = product.get("name").getAsString();
			price = product.get("price").getAsDouble();
			productsList.add(new ProductWithPrice(name, price));
		}
		return productsList;
	}

}
