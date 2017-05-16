package it.therickys93.barorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class TableActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int TABLE_COUNT = 20;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        this.spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, createTables());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinner.setAdapter(adapter);
        this.spinner.setOnItemSelectedListener(this);
    }


    private List<String> createTables() {
        List<String> tables = new ArrayList<String>();
        for(int index = 0; index <= TABLE_COUNT; index++){
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
