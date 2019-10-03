package it.therickys93.barorder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import it.therickys93.javabarorderapi.BarOrder;
import it.therickys93.javabarorderapi.Order;
import it.therickys93.javabarorderapi.PayOrder;
import it.therickys93.javabarorderapi.Payments;
import it.therickys93.javabarorderapi.Response;

public class PaymentsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private List<Order> orders;
    private OrderAdapter adapter;
    private EditText inputSearch;
    private SwipeRefreshLayout pullToRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);
        this.pullToRefresh = (SwipeRefreshLayout)findViewById(R.id.pullToRefresh);
        this.listView = (ListView) findViewById(R.id.paymentsListView);
        this.inputSearch = (EditText)findViewById(R.id.inputSearch);
        new BarOrderGetPayments().execute();
        this.listView.setOnItemClickListener(this);
        this.pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new BarOrderGetPayments().execute();
                pullToRefresh.setRefreshing(false);
            }
        });

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Nothing to do here...
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // nothing to do here...
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
        Order order = this.adapter.getItem(index);
        this.inputSearch.setText("");
        new BarOrderPayOrder().execute(order);
        new BarOrderGetPayments().execute();
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

    private class BarOrderGetPayments extends AsyncTask<Void, Void, List<Order>> {

        @Override
        protected List<Order> doInBackground(Void... voids) {
            SharedPreferences settings = getSharedPreferences(BarOrderConstants.BARORDER_SETTINGS, 0);
            String url = settings.getString(BarOrderConstants.BARORDER_URL_KEY, BarOrderConstants.BARORDER_URL_VALUE);
            String token = settings.getString(BarOrderConstants.BARORDER_TOKEN_KEY, BarOrderConstants.BARORDER_TOKEN_VALUE);
            BarOrder barorder = new BarOrder(url, token);
            try {
                String response = barorder.execute(new Payments());
                List<Order> ordini = Response.parseOrders(response);
                return ordini;
            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Order> ordini) {
            super.onPostExecute(ordini);
            orders = ordini;
            adapter = new OrderAdapter(PaymentsActivity.this, orders);
            listView.setAdapter(adapter);
        }
    }

    private class BarOrderPayOrder extends AsyncTask<Order, Void, Boolean>{
        @Override
        protected Boolean doInBackground(Order... orders) {
            SharedPreferences settings = getSharedPreferences(BarOrderConstants.BARORDER_SETTINGS, 0);
            String url = settings.getString(BarOrderConstants.BARORDER_URL_KEY, BarOrderConstants.BARORDER_URL_VALUE);
            String token = settings.getString(BarOrderConstants.BARORDER_TOKEN_KEY, BarOrderConstants.BARORDER_TOKEN_VALUE);
            BarOrder barorder = new BarOrder(url, token);
            try {
                String response = barorder.execute(new PayOrder(orders[0]));
                Response status = Response.parseSuccess(response);
                return status.ok();
            } catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean) {
                Toast.makeText(PaymentsActivity.this, "Ordine pagato con successo!!!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(PaymentsActivity.this, "Riprova!! Ordine non effettuato!!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
