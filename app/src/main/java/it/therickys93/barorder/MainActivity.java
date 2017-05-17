package it.therickys93.barorder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import it.therickys93.javabarorderapi.BarOrder;
import it.therickys93.javabarorderapi.InsertOrder;
import it.therickys93.javabarorderapi.Order;
import it.therickys93.javabarorderapi.Product;
import it.therickys93.javabarorderapi.Products;
import it.therickys93.javabarorderapi.Response;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_TABLE_ACTIVITY_CODE = 123;
    public static final int REQUEST_PRODUCT_ACTIVITY_CODE = 234;

    private Button tableButton;
    private Button orderButton;
    public Order order;
    private int id;
    private int table = 0;
    private Product[] products = new Product[0];
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.tableButton = (Button) findViewById(R.id.tableButton);
        this.orderButton = (Button) findViewById(R.id.orderButton);

        this.textView = (TextView)findViewById(R.id.orderInfo);
        updateTextView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Settings");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            default:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
        }
    }

    public void tableButtonPressed(View view){
        Intent intent = new Intent(getApplicationContext(), TableActivity.class);
        startActivityForResult(intent, REQUEST_TABLE_ACTIVITY_CODE);
    }

    public void updateTextView() {
        String text = "Order Details:\ntable: ";
        if(this.table == 0){
            text += "No Table Selected";
        } else {
            text += this.table;
        }
        text += "\n";
        if(this.products.length == 0){
            text += "No Products Selected";
        }
        else {
            for(int index = 0; index < this.products.length; index++){
                Product prod = this.products[index];
                text += prod.quantity() + " " + prod.name() + "\n";
            }
        }
        this.textView.setText(text);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_TABLE_ACTIVITY_CODE){
            // dalla table activity
            this.table = Integer.parseInt(data.getStringExtra("TABLE"));
        } else if(requestCode == REQUEST_PRODUCT_ACTIVITY_CODE) {
            // dalla product activity
            String result = data.getStringExtra("PRODUCTS");
            this.products = parseProducts(result);
        }
        updateTextView();
    }

    private Product[] parseProducts(String result){
        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(result).getAsJsonArray();
        Product[] prodotti = new Product[array.size()];
        for(int index = 0; index < array.size(); index++){
            JsonObject object = array.get(index).getAsJsonObject();
            String name = object.get("name").getAsString();
            int value = object.get("quantity").getAsInt();
            prodotti[index] = new Product(name, value);
        }
        return prodotti;
    }

    public void resetUI() {
        this.table = 0;
        this.id = 0;
        this.products = new Product[0];
        updateTextView();
    }

    public void orderButtonPressed(View view){
        this.id = (int)(System.currentTimeMillis() / 1000);
        if(this.table == 0 || this.products.length == 0){
            Toast.makeText(this, "Fill all the data", Toast.LENGTH_SHORT).show();
        } else {
            this.order = new Order(this.id, this.table, false, this.products);
            new BarOrderAsyncTask().execute(this.order);
        }
        resetUI();
    }

    public void productsButtonPressed(View view){
        Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
        startActivityForResult(intent, REQUEST_PRODUCT_ACTIVITY_CODE);
    }

    private class BarOrderAsyncTask extends AsyncTask<Order, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Order... orders) {
            SharedPreferences settings = getSharedPreferences("MySettingsBarOrder", 0);
            String url = settings.getString("BARORDER_URL", "192.168.1.10");
            BarOrder barorder = new BarOrder("http://" + url);
            try {
                String response = barorder.execute(new InsertOrder(orders[0]));
                Response status = Response.parseSuccess(response);
                return status.ok();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean) {
                Toast.makeText(MainActivity.this, "Success!!!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Retry! Something goes wrong!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
