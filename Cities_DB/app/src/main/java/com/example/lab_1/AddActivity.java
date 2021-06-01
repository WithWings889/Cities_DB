package com.example.lab_1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {
    EditText name_input, number_input, distance_input;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        name_input = findViewById(R.id.name_input);
        number_input = findViewById(R.id.number_input);
        distance_input = findViewById(R.id.distance_input);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper myDB = new DatabaseHelper(AddActivity.this);
                myDB.addCity(name_input.getText().toString().trim(),
                        Float.valueOf(number_input.getText().toString().trim()),
                        Integer.valueOf(distance_input.getText().toString().trim()));
            }
        });
    }
}