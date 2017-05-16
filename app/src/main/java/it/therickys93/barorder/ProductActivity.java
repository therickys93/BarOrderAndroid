package it.therickys93.barorder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;

import it.therickys93.javabarorderapi.BarOrder;
import it.therickys93.javabarorderapi.Product;
import it.therickys93.javabarorderapi.Products;
import it.therickys93.javabarorderapi.Response;

public class ProductActivity extends AppCompatActivity {

    private ListView listView;
    private List<Product> productsActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        this.listView = (ListView) findViewById(R.id.listView);
        new BarOrderProduct().execute();
    }

    public void doneProducts(View view){
        Intent intent = new Intent();
        intent.putExtra("PRODUCTS", false);
        setResult(RESULT_OK, intent);
        finish();
    }

    private class BarOrderProduct extends AsyncTask<Void, Void, List<String>> {

        @Override
        protected List<String> doInBackground(Void... voids) {
            SharedPreferences settings = getSharedPreferences("MySettingsBarOrder", 0);
            String url = settings.getString("BARORDER_URL", "192.168.1.10");
            BarOrder barorder = new BarOrder("http://" + url);
            try {
                String response = barorder.execute(new Products());
                List<String> results = new ArrayList<String>();
                List<Product> products = Response.parseProducts(response);
                for(int index = 0; index < products.size(); index++) {
                    results.add(products.get(index).name());
                }
                productsActivity = products;
                return results;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<String> products) {
            super.onPostExecute(products);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProductActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, products);
            listView.setAdapter(adapter);
        }
    }
}
