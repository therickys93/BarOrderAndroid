package it.therickys93.barorder;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    private EditText editTextUrl;
    private EditText editTextToken;
    private EditText editTextTableMin;
    private EditText editTextTableMax;
    private CheckBox checkboxShowQRCode;
    private TextView appVersionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        this.editTextUrl = (EditText) findViewById(R.id.editTextUrl);
        this.editTextToken = (EditText) findViewById(R.id.editTextToken);
        this.editTextTableMin = (EditText) findViewById(R.id.editTextTableMin);
        this.editTextTableMax = (EditText) findViewById(R.id.editTextTableMax);
        this.checkboxShowQRCode = (CheckBox) findViewById(R.id.showQRCodeCheckBox);
        this.appVersionTextView = (TextView) findViewById(R.id.appVersion);

        this.appVersionTextView.setText("Versione applicazione: " + BuildConfig.VERSION_NAME + "." + BuildConfig.VERSION_CODE);

        SharedPreferences settings = getSharedPreferences(BarOrderConstants.BARORDER_SETTINGS, 0);
        String url = settings.getString(BarOrderConstants.BARORDER_URL_KEY, BarOrderConstants.BARORDER_URL_VALUE);
        String token = settings.getString(BarOrderConstants.BARORDER_TOKEN_KEY, BarOrderConstants.BARORDER_TOKEN_VALUE);
        String tableMax = settings.getString("BARORDER_TABLE_MAX", "20");
        String tableMin = settings.getString("BARORDER_TABLE_MIN", "0");
        boolean qrcode = settings.getBoolean(BarOrderConstants.BARORDER_SHOW_QR_CODE_KEY, BarOrderConstants.BARORDER_SHOW_QR_CODE_VALUE);

        this.editTextUrl.setText(url);
        this.editTextTableMin.setText(tableMin);
        this.editTextTableMax.setText(tableMax);
        this.editTextToken.setText(token);
        this.checkboxShowQRCode.setChecked(qrcode);
    }

    public void doneSettings(View view) {
        SharedPreferences settings = getSharedPreferences(BarOrderConstants.BARORDER_SETTINGS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(BarOrderConstants.BARORDER_URL_KEY, this.editTextUrl.getText().toString());
        editor.putString(BarOrderConstants.BARORDER_TOKEN_KEY, this.editTextToken.getText().toString());
        editor.putString("BARORDER_TABLE_MIN", this.editTextTableMin.getText().toString());
        editor.putString("BARORDER_TABLE_MAX", this.editTextTableMax.getText().toString());
        editor.putBoolean(BarOrderConstants.BARORDER_SHOW_QR_CODE_KEY, this.checkboxShowQRCode.isChecked());
        editor.commit();
        finish();
    }

}
