package it.therickys93.barorder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class TableActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        this.spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner, createTables());
        adapter.setDropDownViewResource(R.layout.spinner);
        this.spinner.setAdapter(adapter);
        this.spinner.setOnItemSelectedListener(this);
    }


    private List<String> createTables() {
        SharedPreferences settings = getSharedPreferences("MySettingsBarOrder", 0);
        String table = settings.getString("BARORDER_TABLE", "20");
        int count = Integer.parseInt(table);
        List<String> tables = new ArrayList<String>();
        for(int index = 0; index <= count; index++){
            tables.add(String.valueOf(index));
        }
        return tables;
    }

    public void done(View view){
        Intent intent = new Intent();
        intent.putExtra("TABLE", this.spinner.getSelectedItem().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
