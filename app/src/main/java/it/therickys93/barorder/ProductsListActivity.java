package it.therickys93.barorder;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import it.therickys93.javabarorderapi.BarOrder;
import it.therickys93.javabarorderapi.DeleteProduct;
import it.therickys93.javabarorderapi.DeleteProductAll;
import it.therickys93.javabarorderapi.InsertProduct;
import it.therickys93.javabarorderapi.Product;
import it.therickys93.javabarorderapi.ProductWithPrice;
import it.therickys93.javabarorderapi.Products;
import it.therickys93.javabarorderapi.ProductsWithPrice;
import it.therickys93.javabarorderapi.Response;

public class ProductsListActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {

    private ListView listView;
    private List<ProductWithPrice> prodotti;
    private ProductsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);

        this.listView = (ListView) findViewById(R.id.productsList);
        new BarOrderGetProductsWithPrice().execute();
        this.listView.setOnItemLongClickListener(this);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int index, long l) {
        ProductWithPrice product = this.prodotti.get(index);
        String name = product.name();
        new BarOrderDeleteProduct().execute(name);
        return true;
    }

    public void insertProduct(View view){
        showDialog();
    }

    public void showDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText nameEditText = (EditText) dialogView.findViewById(R.id.edit1);
        final EditText priceEditText = (EditText) dialogView.findViewById(R.id.edit2);

        dialogBuilder.setTitle("Nuovo Prodotto");
        dialogBuilder.setMessage("Inserisci nome nuovo prodotto");
        dialogBuilder.setPositiveButton("Fatto", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String name = nameEditText.getText().toString();
                String price = priceEditText.getText().toString();
                new BarOrderInsertProduct().execute(name, price);
            }
        });
        dialogBuilder.setNegativeButton("Cancella", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // non fare nulla
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public void deleteProductsAll(View view) {
        new BarOrderDeleteProductsAll().execute();
        new BarOrderGetProductsWithPrice().execute();
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

    private class BarOrderDeleteProduct extends AsyncTask<String, Void, Boolean>{

        @Override
        protected Boolean doInBackground(String... strings) {
            SharedPreferences settings = getSharedPreferences("MySettingsBarOrder", 0);
            String url = settings.getString("BARORDER_URL", "http://192.168.1.10");
            String token = settings.getString("BARORDER_TOKEN", "");
            BarOrder barorder = new BarOrder(url, token);
            try {
                String response = barorder.execute(new DeleteProduct(strings[0]));
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
                Toast.makeText(ProductsListActivity.this, "Prodotto eliminato con successo!!!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ProductsListActivity.this, "Riprova!! Prodotto non eliminato!!", Toast.LENGTH_SHORT).show();
            }
            new BarOrderGetProductsWithPrice().execute();
        }
    }

    private class BarOrderInsertProduct extends AsyncTask<String, Void, Boolean>{

        @Override
        protected Boolean doInBackground(String... strings) {
            SharedPreferences settings = getSharedPreferences("MySettingsBarOrder", 0);
            String url = settings.getString("BARORDER_URL", "http://192.168.1.10");
            String token = settings.getString("BARORDER_TOKEN", "");
            BarOrder barorder = new BarOrder(url, token);
            try {
                String response = barorder.execute(new InsertProduct(strings[0], Double.parseDouble(strings[1])));
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
                Toast.makeText(ProductsListActivity.this, "Prodotto aggiunto con successo!!!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ProductsListActivity.this, "Riprova!! Prodotto non aggiunto!!", Toast.LENGTH_SHORT).show();
            }
            new BarOrderGetProductsWithPrice().execute();
        }
    }

    private class BarOrderGetProductsWithPrice extends AsyncTask<Void, Void, List<ProductWithPrice>> {

        @Override
        protected List<ProductWithPrice> doInBackground(Void... voids) {
            SharedPreferences settings = getSharedPreferences("MySettingsBarOrder", 0);
            String url = settings.getString("BARORDER_URL", "http://192.168.1.10");
            String token = settings.getString("BARORDER_TOKEN", "");
            BarOrder barorder = new BarOrder(url, token);
            try {
                String response = barorder.execute(new ProductsWithPrice());
                List<ProductWithPrice> products = Response.parseProductsWithPrice(response);
                return products;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Exception: " + e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<ProductWithPrice> products) {
            super.onPostExecute(products);
            prodotti = products;
            adapter = new ProductsListAdapter(ProductsListActivity.this, prodotti);
            listView.setAdapter(adapter);
        }
    }

    private class BarOrderDeleteProductsAll extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            SharedPreferences settings = getSharedPreferences("MySettingsBarOrder", 0);
            String url = settings.getString("BARORDER_URL", "http://192.168.1.10");
            String token = settings.getString("BARORDER_TOKEN", "");
            BarOrder barorder = new BarOrder(url, token);
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
