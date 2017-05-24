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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import it.therickys93.javabarorderapi.BarOrder;
import it.therickys93.javabarorderapi.CompleteOrder;
import it.therickys93.javabarorderapi.Order;
import it.therickys93.javabarorderapi.Orders;
import it.therickys93.javabarorderapi.Response;

public class OrdersActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private List<Order> orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        this.listView = (ListView) findViewById(R.id.ordersListView);
//        new BarOrderGetOrders().execute();
//        this.listView.setOnItemClickListener(this);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
        Toast.makeText(this, "Click item at " + index, Toast.LENGTH_SHORT).show();
        // new BarOrderCompleteOrder().execute(null);
    }

    private class BarOrderGetOrders extends AsyncTask<Void, Void, List<Order>> {

        @Override
        protected List<Order> doInBackground(Void... voids) {
            SharedPreferences settings = getSharedPreferences("MySettingsBarOrder", 0);
            String url = settings.getString("BARORDER_URL", "192.168.1.10");
            BarOrder barorder = new BarOrder("http://" + url);
            try {
                String response = barorder.execute(new Orders());
                List<Order> ordini = Response.parseOrders(response);
                return orders;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Order> ordini) {
            super.onPostExecute(orders);
        }
    }

    private class BarOrderCompleteOrder extends AsyncTask<Order, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Order... orders) {
            SharedPreferences settings = getSharedPreferences("MySettingsBarOrder", 0);
            String url = settings.getString("BARORDER_URL", "192.168.1.10");
            BarOrder barorder = new BarOrder("http://" + url);
            try {
                String response = barorder.execute(new CompleteOrder(orders[0]));
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
                Toast.makeText(OrdersActivity.this, "Success!!!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(OrdersActivity.this, "Retry! Something goes wrong!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}