package it.therickys93.barorder;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import it.therickys93.javabarorderapi.Product;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void parseProductsNone() {
        Product[] products = MainActivity.parseProducts(noProducts());
        assertEquals(products.length, 0);
    }

    @Test
    public void parseProductsOne() {
        Product[] products = MainActivity.parseProducts(oneProduct());
        assertEquals(products.length, 1);
        assertEquals(products[0].name(), "cioccolata con panna");
        assertEquals(products[0].quantity(), 2);
    }

    @Test
    public void parseMoreThanOneProducts() {
        Product[] products = MainActivity.parseProducts(moreThanOneProducts());
        assertEquals(products.length, 2);
        assertEquals(products[0].name(), "cioccolata con panna");
        assertEquals(products[0].quantity(), 2);
        assertEquals(products[1].name(), "brioches");
        assertEquals(products[1].quantity(), 2);
    }

    private String noProducts() {
        return "NONE";
    }

    private String oneProduct() {
        return "[{\"name\":\"cioccolata con panna\",\"quantity\":2}]";
    }

    private String moreThanOneProducts() {
        return "[{\"name\":\"cioccolata con panna\",\"quantity\":2},{\"name\":\"brioches\",\"quantity\":2}]";
    }

    @Test
    public void convertToJsonNoProducts() {
        String json = ProductActivity.convertToJson(noProductsList());
        assertNull(json);
    }

    @Test
    public void convertToJsonOneProduct() {
        String json = ProductActivity.convertToJson(oneProductList());
        assertEquals(json, oneProduct());
    }

    @Test
    public void convertToJsonMoreThanOneProducts() {
        String json = ProductActivity.convertToJson(moreThanOneProductsList());
        assertEquals(json, moreThanOneProducts());
    }

    private List<Product> noProductsList() {
        return null;
    }

    private List<Product> oneProductList() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("cioccolata con panna", 2));
        return products;
    }

    private List<Product> moreThanOneProductsList() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("cioccolata con panna", 2));
        products.add(new Product("brioches", 2));
        return products;
    }

}