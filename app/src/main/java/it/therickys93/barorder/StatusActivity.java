package it.therickys93.barorder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;

import it.therickys93.javabarorderapi.BarOrder;
import it.therickys93.javabarorderapi.Response;

public class StatusActivity extends AppCompatActivity {

    private TextView statusColorTextView;
    private TextView statusMessageTextView;
    private TextView statusServerCheck;
    private TextView statusDatabaseCheck;
    private TextView statusVersionCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        this.statusColorTextView = (TextView) findViewById(R.id.status_color_textview);
        this.statusMessageTextView = (TextView) findViewById(R.id.status_message_textview);
        this.statusServerCheck = (TextView) findViewById(R.id.status_server_textview);
        this.statusDatabaseCheck = (TextView) findViewById(R.id.status_database_textView);
        this.statusVersionCheck = (TextView) findViewById(R.id.status_version_textview);
    }

    public void checkStatus(View view){
        new BarOrderStatus().execute();
    }

    public void goBack(View view){
        finish();
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
            case R.id.status:
                intent = new Intent(this, StatusActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private class BarOrderStatus extends AsyncTask<Void, Void, Response>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            statusColorTextView.setText("CHECKING");
            statusMessageTextView.setText("Checking in progress...");
            statusServerCheck.setText("");
            statusDatabaseCheck.setText("");
            statusVersionCheck.setText("");
        }

        @Override
        protected Response doInBackground(Void... voids) {
            SharedPreferences settings = getSharedPreferences("MySettingsBarOrder", 0);
            String url = settings.getString("BARORDER_URL", "192.168.1.10");
            BarOrder barorder = new BarOrder("http://" + url);
            try {
                String response = barorder.execute(new it.therickys93.javabarorderapi.Status());
                Response status = Response.parseStatus(response);
                return status;
            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            if(response == null){
                statusColorTextView.setBackgroundColor(Color.parseColor("#ff0000"));
                statusColorTextView.setText("NOT HEALTHY");
                statusMessageTextView.setText("System not available");
                return;
            }
            if(response.ok()) {
                statusColorTextView.setBackgroundColor(Color.parseColor("#00ff00"));
                statusColorTextView.setText("HEALTHY");
                statusMessageTextView.setText("System is online");
            } else {
                statusColorTextView.setBackgroundColor(Color.parseColor("#ff0000"));
                statusColorTextView.setText("NOT HEALTHY");
                statusMessageTextView.setText("System not available");
            }
            if(response.server()){
                statusServerCheck.setText("Server: HEALTHY");
                statusServerCheck.setBackgroundColor(Color.parseColor("#00ff00"));
            } else {
                statusServerCheck.setText("Server: NOT HEALTHY");
                statusServerCheck.setBackgroundColor(Color.parseColor("#ff0000"));
            }
            if(response.database()){
                statusDatabaseCheck.setText("Database: HEALTHY");
                statusDatabaseCheck.setBackgroundColor(Color.parseColor("#00ff00"));
            } else {
                statusDatabaseCheck.setText("Server: NOT HEALTHY");
                statusDatabaseCheck.setBackgroundColor(Color.parseColor("#ff0000"));
            }
            statusVersionCheck.setText("Server Version: " + response.version());
        }
    }

}
