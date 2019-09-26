package it.therickys93.javabarorderapi;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class BarOrderTest {
	
	@Test
	public void testOne() {
		assertEquals(1,1);
	}
	
	public void testTwo() throws IOException {
		BarOrder barorder = new BarOrder("http://localhost:8080");
		String response = barorder.execute(new Status());
		assertEquals("", response);
	}
	
	public void testThree() throws IOException {
		BarOrder barorder = new BarOrder("http://localhost:8080");
		String response = barorder.execute(new DeleteProductAll());
		assertEquals("", response);
	}
	
	public void testFour() throws IOException {
		BarOrder barorder = new BarOrder("http://localhost:8080");
		String response = barorder.execute(new InsertProduct("cioccolata con panna"));
		assertEquals("", response);
	}
	
	public void testInsertProductWithPrice() throws IOException {
		BarOrder barorder = new BarOrder("http://localhost:8080");
		String response = barorder.execute(new InsertProduct("cioccolata con panna", 2.5));
		assertEquals("", response);
	}
	
	public void testFive() throws IOException {
		BarOrder barorder = new BarOrder("http://localhost:8080");
		String response = barorder.execute(new DeleteProduct("cioccolata con panna"));
		assertEquals("", response);
	}
	
	public void testSix() throws IOException {
		BarOrder barorder = new BarOrder("http://localhost:8080");
		String response = barorder.execute(new Products());
		assertEquals("", response);
	}
	
	public void testProductsWithPrice() throws IOException {
		BarOrder barorder = new BarOrder("http://localhost:8080");
		String response = barorder.execute(new ProductsWithPrice());
		assertEquals("", response);
	}
	
	public void testSeven() throws IOException {
		Product[] products = {new Product("acqua", 2), new Product("bibite", 3)};
		Order order = new Order(104, 20, false, products);
		BarOrder barorder = new BarOrder("http://localhost:8080");
		String response = barorder.execute(new InsertOrder(order));
		assertEquals("", response);
	}
	
	public void testEight() throws IOException {
		Product[] products = {new Product("acqua", 2), new Product("bibite", 3)};
		Order order = new Order(104, 20, false, products);
		BarOrder barorder = new BarOrder("http://localhost:8080");
		String response = barorder.execute(new CompleteOrder(order));
		assertEquals("", response);
	}
	
	public void testNine() throws IOException {
		Product[] products = {new Product("acqua", 2), new Product("bibite", 3)};
		Order order = new Order(102, 20, false, products);
		BarOrder barorder = new BarOrder("http://localhost:8080");
		String response = barorder.execute(new PayOrder(order));
		assertEquals("", response);
	}
	
	public void testTen() throws IOException {
		BarOrder barorder = new BarOrder("http://localhost:8080");
		String response = barorder.execute(new Orders());
		assertEquals("", response);
	}
	
	public void testEleven() throws IOException {
		BarOrder barorder = new BarOrder("http://localhost:8080");
		String response = barorder.execute(new Payments());
		assertEquals("", response);
	}
	
}
