package it.therickys93.barorder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import it.therickys93.javabarorderapi.BarOrder;
import it.therickys93.javabarorderapi.DeleteProductAll;
import it.therickys93.javabarorderapi.Product;
import it.therickys93.javabarorderapi.Products;
import it.therickys93.javabarorderapi.Response;

public class ProductsListActivity extends AppCompatActivity {

    private ListView listView;
    private List<Product> prodotti;
    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);

        this.listView = (ListView) findViewById(R.id.productsList);
        new BarOrderGetProducts().execute();
    }

    public void deleteProductsAll(View view) {
        new BarOrderDeleteProductsAll().execute();
        new BarOrderGetProducts().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.status:
                intent = new Intent(this, StatusActivity.class);
                startActivity(intent);
                return true;
            case R.id.order:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.orders:
                intent = new Intent(this, OrdersActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.payments:
                intent = new Intent(this, PaymentsActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.prodotti:
                intent = new Intent(this, ProductsListActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class BarOrderGetProducts extends AsyncTask<Void, Void, List<Product>> {

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
            adapter = new ProductAdapter(ProductsListActivity.this, prodotti);
            listView.setAdapter(adapter);
        }
    }

    private class BarOrderDeleteProductsAll extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            SharedPreferences settings = getSharedPreferences("MySettingsBarOrder", 0);
            String url = settings.getString("BARORDER_URL", "192.168.1.10");
            BarOrder barorder = new BarOrder("http://" + url);
            try {
                String response = barorder.execute(new DeleteProductAll());
                Response responseObj = Response.parseSuccess(response);
                return responseObj.ok();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Exception: " + e.getMessage());
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean) {
                Toast.makeText(ProductsListActivity.this, "Prodotti eliminati con successo!!!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ProductsListActivity.this, "Riprova!! Prodotti non eliminati!!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
