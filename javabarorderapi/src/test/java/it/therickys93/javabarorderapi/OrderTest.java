package it.therickys93.javabarorderapi;

import static org.junit.Assert.*;

import org.junit.Test;

public class OrderTest {

	@Test
	public void testOne() {
		assertEquals(1,1);
	}
	
	@Test
	public void testTwo() {
		Product[] products = {new Product("brioches", 2), new Product("cappuccino", 3)};
		Order order = new Order(102, 20, false, products);
		assertEquals(102, order.id());
		assertEquals(20, order.table());
		assertFalse(order.done());
		assertEquals("brioches", order.products()[0].name());
		assertEquals(3, order.products()[1].quantity());
	}
	
	@Test
	public void testThree() {
		Product[] products = {new Product("brioches", 2), new Product("cappuccino", 3)};
		Order order = new Order(102, 20, false, products);
		assertEquals("Order={id=102, table=20, done=false, products="
				+ "[Product={name=brioches, quantity=2}, Product={name=cappuccino, quantity=3}]}", order.toString());
	}
	
	@Test
	public void testFour() {
		Product[] products = {new Product("acqua", 2), new Product("bibite", 3)};
		Order order = new Order(102, 20, false, products);
		assertEquals("{\"id\":102,\"table\":20,\"done\":false,"
				+ "\"products\":[{\"name\":\"acqua\",\"quantity\":2},"
				+ "{\"name\":\"bibite\",\"quantity\":3}]}", order.toJson().toString());
	}
	
	@Test
	public void testFive() {
		Product[] products = {new Product("brioches", 2), new Product("cappuccino", 3)};
		Order order = new Order(102, 20, false, products);
		assertEquals(102, order.id());
		order.setId(30493);
		assertEquals(30493, order.id());
	}
	
	@Test
	public void testSix() {
		Product[] products = {new Product("brioches", 2), new Product("cappuccino", 3)};
		Order order = new Order(102, 20, false, products);
		assertEquals(20, order.table());
		order.setTable(44);
		assertEquals(44, order.table());
	}
	
	@Test
	public void testSeven() {
		Product[] products = {new Product("brioches", 2), new Product("cappuccino", 3)};
		Order order = new Order(102, 20, false, products);
		assertFalse(order.done());
		order.setDone(true);
		assertTrue(order.done());
	}
	
	@Test
	public void testEight() {
		Product[] products = {new Product("brioches", 2), new Product("cappuccino", 3)};
		Product[] newProducts = {new Product("acqua", 4), new Product("bibite", 8)};
		Order order = new Order(102, 20, false, products);
		assertEquals("brioches", order.products()[0].name());
		assertEquals(2, order.products()[0].quantity());
		assertEquals("cappuccino", order.products()[1].name());
		assertEquals(3, order.products()[1].quantity());
		order.setProducts(newProducts);
		assertEquals("acqua", order.products()[0].name());
		assertEquals(4, order.products()[0].quantity());
		assertEquals("bibite", order.products()[1].name());
		assertEquals(8, order.products()[1].quantity());
	}
	
	@Test
	public void testNine() {
		Product[] products = {new Product("brioches", 2), new Product("cappuccino", 3)};
		Order order = new Order(102, 20, false, products);
		assertEquals("Dettagli Ordine:\ntavolo: 20\nprodotti:\n2 brioches\n3 cappuccino", order.prettyToString());
		order.setTable(0);
		order.setProducts(new Product[0]);
		assertEquals("Dettagli Ordine:\ntavolo: Nessun Tavolo Selezionato\nprodotti:\nNessun Prodotto Selezionato", order.prettyToString());
	}
	
	@Test
	public void testTen() {
		Order order = new Order();
		assertEquals(0, order.id());
		assertEquals(0, order.table());
		assertFalse(order.done());
		assertEquals(0, order.products().length);
	}
	
	private static final double DELTA = 1e-15;
	
	@Test
	public void testEleven() {
		Product[] products = {new Product("brioches", 2), new Product("cappuccino", 3)};
		Order order = new Order(102, 20, false, products, 1.5);
		assertEquals(102, order.id());
		assertEquals(20, order.table());
		assertFalse(order.done());
		assertEquals("brioches", order.products()[0].name());
		assertEquals(2, order.products()[0].quantity());
		assertEquals(1.5, order.price(), DELTA);
	}
	
}
