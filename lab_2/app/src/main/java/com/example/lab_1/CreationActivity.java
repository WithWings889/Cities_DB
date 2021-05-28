package com.example.lab_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class CreationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);
        TextInputEditText cityText, distanceText, citizensText;
        EditText bodyType = new EditText(this);
        DatabaseHelper db = new DatabaseHelper(this);

        Button backButton = (Button) findViewById(R.id.button6);
        Button addButton = (Button) findViewById(R.id.button5);

        cityText = (TextInputEditText) findViewById(R.id.amountInput2);
        distanceText = (TextInputEditText) findViewById(R.id.amountInput3);
        citizensText = (TextInputEditText) findViewById(R.id.amountInput4);

        addButton.setOnClickListener(v -> {
            boolean isInserted = db.insertData(cityText.getText().toString(),
                    Float.parseFloat(distanceText.getText().toString()),
                    Integer.parseInt(citizensText.getText().toString()));
            if(isInserted == true) {
                Toast.makeText(getBaseContext(), "Data inserted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getBaseContext(), "Data not inserted", Toast.LENGTH_LONG).show();
            }
        });

        backButton.setOnClickListener(v -> {
            startActivity(new Intent(CreationActivity.this, MainActivity.class));
        });
    }
}
