package it.therickys93.barorder;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    private EditText editTextUrl;
    private EditText editTextTableMin;
    private EditText editTextTableMax;
    private TextView appVersionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        this.editTextUrl = (EditText) findViewById(R.id.editTextUrl);
        this.editTextTableMin = (EditText) findViewById(R.id.editTextTableMin);
        this.editTextTableMax = (EditText) findViewById(R.id.editTextTableMax);
        this.appVersionTextView = (TextView) findViewById(R.id.appVersion);

        this.appVersionTextView.setText("Versione applicazione: " + BuildConfig.VERSION_NAME + "." + BuildConfig.VERSION_CODE);

        SharedPreferences settings = getSharedPreferences("MySettingsBarOrder", 0);
        String url = settings.getString("BARORDER_URL", "http://192.168.1.10");
        String tableMax = settings.getString("BARORDER_TABLE_MAX", "20");
        String tableMin = settings.getString("BARORDER_TABLE_MIN", "0");

        this.editTextUrl.setText(url);
        this.editTextTableMin.setText(tableMin);
        this.editTextTableMax.setText(tableMax);
    }

    public void doneSettings(View view) {
        SharedPreferences settings = getSharedPreferences("MySettingsBarOrder", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("BARORDER_URL", this.editTextUrl.getText().toString());
        editor.putString("BARORDER_TABLE_MIN", this.editTextTableMin.getText().toString());
        editor.putString("BARORDER_TABLE_MAX", this.editTextTableMax.getText().toString());
        editor.commit();
        finish();
    }

}
