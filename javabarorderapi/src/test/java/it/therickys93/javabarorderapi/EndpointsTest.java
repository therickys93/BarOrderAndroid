package it.therickys93.javabarorderapi;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class EndpointsTest {

	@Test
	public void testOne() {
		assertEquals(1, 1);
	}
	
	@Test
	public void testTwo() {
		Status status = new Status();
		assertEquals("GET", status.method());
		assertEquals("/v1/status", status.endpoint());
		assertNull(status.toJson());
	}
	
	@Test
	public void testThree() {
		Response status = Response.parseSuccess("{\"success\":true}");
		assertTrue(status.ok());
		status = Response.parseSuccess("{\"success\":false}");
		assertFalse(status.ok());
	}
	
	@Test
	public void testFour() {
		DeleteProductAll product = new DeleteProductAll();
		assertEquals("POST", product.method());
		assertEquals("/v1/deleteProductAll", product.endpoint());
		assertNull(product.toJson());
	}
	
	@Test
	public void testFive() {
		InsertProduct product = new InsertProduct("cioccolata con panna");
		assertEquals("POST", product.method());
		assertEquals("/v1/insertProduct/cioccolata con panna", product.endpoint());
		assertNull(product.toJson());
	}
	
	@Test
	public void testInsertProductWithPrice() {
		InsertProduct ipwp = new InsertProduct("cioccolata con panna", 2.5);
		assertEquals("POST", ipwp.method());
		assertEquals("/v1/insertProduct/cioccolata con panna/2.5", ipwp.endpoint());
		assertNull(ipwp.toJson());
	}
	
	@Test
	public void testSix() {
		DeleteProduct product = new DeleteProduct("cioccolata con panna");
		assertEquals("POST", product.method());
		assertEquals("/v1/deleteProduct/cioccolata con panna", product.endpoint());
		assertNull(product.toJson());
	}
	
	@Test
	public void testSeven() {
		Products products = new Products();
		assertEquals("GET", products.method());
		assertEquals("/v1/products", products.endpoint());
		assertNull(products.toJson());
	}
	
	@Test
	public void testEight() {
		List<Product> products = Response.parseProducts(productsJson());
		assertEquals(32, products.size());
	}
	
	private String productsJson(){
		return "[\"acqua\",\"bibite\",\"caffe al ginseng\",\"caffe americano\",\"caffe d\'orzo\",\"caffe decaffeinato\",\"caffe decaffeinato shakerato\",\"caffe espresso\",\"caffe freddo\",\"caffe latte\",\"caffe shakerato\",\"cappuccino\",\"cappuccino al ginseng\",\"cappuccino d\'orzo\",\"cappuccino decaffeinato\",\"cappuccino freddo\",\"ciambella\",\"cioccolata\",\"cioccolata con panna\",\"crema di caffe\",\"crostata\",\"frullati vari\",\"insalatone\",\"latte bianco\",\"latte macchiato\",\"panini assortiti\",\"spremuta\",\"succhi di frutta\",\"the ed infusi\",\"the freddo\",\"toast\",\"tramezzini assortiti\"]";
	}
	
	@Test
	public void testProductsWithPrice() {
		ProductsWithPrice pswp = new ProductsWithPrice();
		assertEquals("GET", pswp.method());
		assertEquals("/v1/productsWithPrice", pswp.endpoint());
		assertNull(pswp.toJson());
	}
	
	@Test
	public void testNine() {
		Product[] products = {new Product("acqua", 2), new Product("bibite", 3)};
		Order order = new Order(102, 20, false, products);
		InsertOrder insertOrder = new InsertOrder(order);
		assertEquals("POST", insertOrder.method());
		assertEquals("/v1/insertOrder", insertOrder.endpoint());
		assertNotNull(insertOrder.toJson());
	}
	
	@Test
	public void testTen() {
		Product[] products = {new Product("acqua", 2), new Product("bibite", 3)};
		Order order = new Order(102, 20, false, products);
		CompleteOrder completeOrder = new CompleteOrder(order);
		assertEquals("POST", completeOrder.method());
		assertEquals("/v1/completeOrder", completeOrder.endpoint());
		assertNotNull(completeOrder.toJson());
	}
	
	@Test
	public void testEleven() {
		Product[] products = {new Product("acqua", 2), new Product("bibite", 3)};
		Order order = new Order(102, 20, false, products);
		PayOrder payOrder = new PayOrder(order);
		assertEquals("POST", payOrder.method());
		assertEquals("/v1/payOrder", payOrder.endpoint());
		assertNotNull(payOrder.toJson());
	}
	
	@Test
	public void testTwelve() {
		Orders orders = new Orders();
		assertEquals("GET", orders.method());
		assertEquals("/v1/orders", orders.endpoint());
		assertNull(orders.toJson());
	}
	
	@Test
	public void testThirteen() {
		Payments payments = new Payments();
		assertEquals("GET", payments.method());
		assertEquals("/v1/payments", payments.endpoint());
		assertNull(payments.toJson());
	}
	
	@Test
	public void testFourteen() {
		List<Order> orders =  Response.parseOrders(orders());
		assertEquals(3, orders.size());
	}
	
	@Test
	public void testFifteen() {
		Response response = Response.parseStatus(statusNotOk());
		assertFalse(response.ok());
	}
	
	@Test
	public void testSixteen() {
		Response response = Response.parseStatus(statusOk());
		assertTrue(response.server());
		assertTrue(response.database());
		assertTrue(response.ok());
		assertEquals("1.0.0", response.version());
	}
	
	@Test
	public void improveCodeCoverageOne() {
		Response response = Response.parseStatus(statusOkTwo());
		assertFalse(response.server());
		assertFalse(response.database());
		assertFalse(response.ok());
		assertEquals("1.0.0", response.version());
	}
	
	@Test
	public void improveCodeCoverageTwo() {
		Response response = Response.parseStatus(statusOkThree());
		assertFalse(response.server());
		assertTrue(response.database());
		assertFalse(response.ok());
		assertEquals("1.0.0", response.version());
	}
	
	@Test
	public void improveCodeCoverageThree() {
		Response response = Response.parseStatus(statusOkFour());
		assertTrue(response.server());
		assertFalse(response.database());
		assertFalse(response.ok());
		assertEquals("1.0.0", response.version());
	}
	
	private static final double DELTA = 1e-15;
	
	@Test
	public void parseOrderWithPrice() {
		List<Order> orders = Response.parseOrders(ordersWithPrice());
		assertEquals(1, orders.size());
		Order order = orders.get(0);
		assertEquals(102, order.id());
		assertEquals(20, order.table());
		assertFalse(order.done());
		assertEquals("acqua", order.products()[0].name());
		assertEquals(2, order.products()[0].quantity());
		assertEquals(1.5, order.price(), DELTA);
	}
	
	@Test
	public void parseProductsWithPrice() {
		List<ProductWithPrice> products = Response.parseProductsWithPrice(productsWithPrice());
		assertEquals(2, products.size());
		ProductWithPrice product = products.get(0);
		assertEquals("toast", product.name());
		assertEquals(5.0, product.price(), DELTA);
	}
	
	private String statusNotOk(){
		return "{\"success\":false}";
	}
	
	private String statusOk(){
		return "{\"server\":true,\"database\":true,\"version\":\"1.0.0\"}";
	}
	
	private String statusOkTwo(){
		return "{\"server\":false,\"database\":false,\"version\":\"1.0.0\"}";
	}
	
	private String statusOkThree(){
		return "{\"server\":false,\"database\":true,\"version\":\"1.0.0\"}";
	}
	
	private String statusOkFour(){
		return "{\"server\":true,\"database\":false,\"version\":\"1.0.0\"}";
	}
	
	private String orders(){
		return "[{\"id\":102,\"table\":20,\"done\":false,\"products\":"
				+ "[{\"name\":\"acqua\",\"quantity\":2},{\"name\":\"bibite\",\"quantity\":3}]},"
				+ "{\"id\":103,\"table\":20,\"done\":false,\"products\":"
				+ "[{\"name\":\"acqua\",\"quantity\":2},{\"name\":\"bibite\",\"quantity\":3}]},"
				+ "{\"id\":104,\"table\":20,\"done\":false,\"products\":"
				+ "[{\"name\":\"acqua\",\"quantity\":2},{\"name\":\"bibite\",\"quantity\":3}]}]";
	}
	
	private String ordersWithPrice() {
		return "[{\"id\":102,\"table\":20,\"done\":false,\"products\":"
				+ "[{\"name\":\"acqua\",\"quantity\":2},{\"name\":\"bibite\",\"quantity\":3}], \"price\":1.5}]";
	}
	
	private String productsWithPrice() {
		return "[{\"name\": \"toast\",\"price\": 5},{\"name\": \"tramezzini assortiti\",\"price\": 4.5}]";
	}
}
