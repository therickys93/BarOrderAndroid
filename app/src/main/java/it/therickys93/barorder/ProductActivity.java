package it.therickys93.barorder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.therickys93.javabarorderapi.BarOrder;
import it.therickys93.javabarorderapi.Product;
import it.therickys93.javabarorderapi.Products;
import it.therickys93.javabarorderapi.Response;

public class ProductActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ListView listView;
    private List<Product> prodotti;
    private ProductAdapter adapterss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        this.listView = (ListView) findViewById(R.id.listView);
        new BarOrderProduct().execute();
        this.listView.setOnItemClickListener(this);
        this.listView.setOnItemLongClickListener(this);
    }

    public void doneProducts(View view){
        Intent intent = new Intent();
        String response = convertToJson(this.prodotti);
        if(response != null){
            intent.putExtra("PRODUCTS", response);
        } else {
            intent.putExtra("PRODUCTS", "NONE");
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    public static String convertToJson(List<Product> prodotti){
        if(prodotti == null){
            return null;
        }
        JsonArray array = new JsonArray();
        for(Product p : prodotti){
            if(p.quantity() > 0){
                array.add(p.toJson());
            }
        }
        return array.toString();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
        if(prodotti == null){

        } else {
            Product product = this.prodotti.get(index);
            product = new Product(product.name(), product.quantity() + 1);
            this.prodotti.remove(index);
            this.prodotti.add(index, product);
            this.adapterss.updateProdotti(this.prodotti);
            this.adapterss.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int index, long l) {
        if(prodotti == null){

        } else {
            Product product = this.prodotti.get(index);
            product = new Product(product.name(), 0);
            this.prodotti.remove(index);
            this.prodotti.add(index, product);
            this.adapterss.updateProdotti(this.prodotti);
            this.adapterss.notifyDataSetChanged();
        }
        return true;
    }

    private class BarOrderProduct extends AsyncTask<Void, Void, List<Product>> {

        @Override
        protected List<Product> doInBackground(Void... voids) {
            SharedPreferences settings = getSharedPreferences("MySettingsBarOrder", 0);
            String url = settings.getString("BARORDER_URL", "192.168.1.10");
            BarOrder barorder = new BarOrder("http://" + url);
            try {
                String response = barorder.execute(new Products());
                List<Product> products = Response.parseProducts(response);
                return products;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Exception: " + e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            super.onPostExecute(products);
            prodotti = products;
            adapterss = new ProductAdapter(ProductActivity.this, prodotti);
            listView.setAdapter(adapterss);
        }
    }
}
