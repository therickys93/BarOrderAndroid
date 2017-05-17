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

public class ProductActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private List<Map<String, String>> prodotti;
    private SimpleAdapter adapterss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        this.listView = (ListView) findViewById(R.id.listView);
        new BarOrderProduct().execute();
        this.listView.setOnItemClickListener(this);
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

    private String convertToJson(List<Map<String, String>> prodotti){
        JsonArray array = new JsonArray();
        for(Map<String, String> map : prodotti){
            JsonObject prod = new JsonObject();
            String key = map.get("name");
            int value = Integer.parseInt(map.get("quantity"));
            if(value > 0) {
                prod.addProperty("name", key);
                prod.addProperty("quantity", value);
                array.add(prod);
            }
        }
        return array.toString();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
        Map<String, String> prodotto = this.prodotti.get(index);
        Set<String> key = prodotto.keySet();
        int oldValue = Integer.parseInt(prodotto.get(key.toArray()[0]));
        prodotto.put((String) key.toArray()[0], String.valueOf(oldValue + 1));
        this.prodotti.set(index, prodotto);
        this.adapterss = new SimpleAdapter(ProductActivity.this, this.prodotti,
                android.R.layout.simple_list_item_2,
                new String[] {"name", "quantity"},
                new int[] {android.R.id.text1,
                        android.R.id.text2});
        this.listView.setAdapter(this.adapterss);
        this.listView.deferNotifyDataSetChanged();
    }

    private class BarOrderProduct extends AsyncTask<Void, Void, List<Map<String, String>>> {

        @Override
        protected List<Map<String, String>> doInBackground(Void... voids) {
            SharedPreferences settings = getSharedPreferences("MySettingsBarOrder", 0);
            String url = settings.getString("BARORDER_URL", "192.168.1.10");
            BarOrder barorder = new BarOrder("http://" + url);
            try {
                String response = barorder.execute(new Products());
                List<Product> products = Response.parseProducts(response);
                List<Map<String, String>> risultato = new ArrayList<>();
                for(int index = 0; index < products.size(); index++){
                    Product p = products.get(index);
                    Map<String, String> prods = new HashMap<>();
                    prods.put("name", p.name());
                    prods.put("quantity", String.valueOf(p.quantity()));
                    risultato.add(prods);
                }
                return risultato;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Map<String, String>> products) {
            super.onPostExecute(products);
            prodotti = products;
            adapterss = new SimpleAdapter(ProductActivity.this, prodotti,
                    android.R.layout.simple_list_item_2,
                    new String[] {"name", "quantity"},
                    new int[] {android.R.id.text1,
                            android.R.id.text2});
            listView.setAdapter(adapterss);
        }
    }
}
