package it.therickys93.barorder;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    private EditText editTextUrl;
    private EditText editTextTable;
    private TextView appVersionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        this.editTextUrl = (EditText) findViewById(R.id.editTextUrl);
        this.editTextTable = (EditText) findViewById(R.id.editTextTable);
        this.appVersionTextView = (TextView) findViewById(R.id.appVersion);

        this.appVersionTextView.setText("Versione applicazione: " + BuildConfig.VERSION_NAME + "." + BuildConfig.VERSION_CODE);

        SharedPreferences settings = getSharedPreferences("MySettingsBarOrder", 0);
        String url = settings.getString("BARORDER_URL", "192.168.1.10");
        String table = settings.getString("BARORDER_TABLE", "20");

        this.editTextUrl.setText(url);
        this.editTextTable.setText(table);
    }

    public void doneSettings(View view) {
        SharedPreferences settings = getSharedPreferences("MySettingsBarOrder", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("BARORDER_URL", this.editTextUrl.getText().toString());
        editor.putString("BARORDER_TABLE", this.editTextTable.getText().toString());
        editor.commit();
        finish();
    }

}
