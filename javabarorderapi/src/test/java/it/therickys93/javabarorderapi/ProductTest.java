package it.therickys93.javabarorderapi;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProductTest {

	@Test
	public void testOne() {
		assertEquals(1,1);
	}
	
	@Test
	public void testTwo() {
		Product product = new Product("cioccolata con panna", 2);
		assertEquals("cioccolata con panna", product.name());
		assertEquals(2, product.quantity());
	}
	
	@Test
	public void testThree() {
		Product product = new Product("cioccolata con panna", 2);
		assertEquals("Product={name=cioccolata con panna, quantity=2}", product.toString());
	}
	
	@Test
	public void testFour() {
		Product product = new Product("acqua", 2);
		assertEquals("{\"name\":\"acqua\",\"quantity\":2}", product.toJson().toString());
	}
	
	@Test
	public void testFive() {
		Product product = new Product("acqua", 3);
		assertEquals("3 acqua", product.prettyToString());
	}
	
	private static final double DELTA = 1e-15;
	
	@Test
	public void testProductWithPrice() {
		ProductWithPrice pwp = new ProductWithPrice("cioccolata con panna", 2.5);
		assertEquals("cioccolata con panna", pwp.name());
		assertEquals(2.5, pwp.price(), DELTA);
	}
	
}
