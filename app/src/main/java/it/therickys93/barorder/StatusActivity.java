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

import java.io.IOException;

import it.therickys93.javabarorderapi.BarOrder;
import it.therickys93.javabarorderapi.Response;

public class StatusActivity extends AppCompatActivity {

    private TextView statusColorTextView;
    private TextView statusMessageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        this.statusColorTextView = (TextView) findViewById(R.id.status_color_textview);
        this.statusMessageTextView = (TextView) findViewById(R.id.status_message_textview);
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
        Intent intent = null;
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


    private class BarOrderStatus extends AsyncTask<Void, Void, Boolean>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            statusColorTextView.setText("CHECKING");
            statusMessageTextView.setText("Checking in progress...");
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            SharedPreferences settings = getSharedPreferences("MySettingsBarOrder", 0);
            String url = settings.getString("BARORDER_URL", "192.168.1.10");
            BarOrder barorder = new BarOrder("http://" + url);
            try {
                String response = barorder.execute(new it.therickys93.javabarorderapi.Status());
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
            if(aBoolean){
                statusColorTextView.setBackgroundColor(Color.parseColor("#00ff00"));
                statusColorTextView.setText("HEALTHY");
                statusMessageTextView.setText("System is online");
            } else {
                statusColorTextView.setBackgroundColor(Color.parseColor("#ff0000"));
                statusColorTextView.setText("NOT HEALTHY");
                statusMessageTextView.setText("System not available");
            }
        }
    }

}
