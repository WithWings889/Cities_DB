package com.example.lab_1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.profile_button).setOnClickListener(vvv -> {
            startActivity(new Intent(getApplicationContext(), InfoActivity.class));
        });

        Button viewAllDataButton = (Button) findViewById(R.id.all_data_button);
        viewAllDataButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CitiesActivity.class);
            intent.putExtra("flag", 0);
            startActivity(intent);
        });

        Button closestAndFeterestButton = (Button) findViewById(R.id.closest_and_fethest_button);
        closestAndFeterestButton.setOnClickListener( v -> {
            Intent intent = new Intent(MainActivity.this, CitiesActivity.class);
            intent.putExtra("flag", 2);
            startActivity(intent);
        });

        Button bigCityButton = (Button) findViewById(R.id.big_cities_near_kyiv_button);
        bigCityButton.setOnClickListener( v -> {
            Intent intent = new Intent(MainActivity.this, CitiesActivity.class);
            intent.putExtra("flag", 1);
            startActivity(intent);
        });

        Button contactsButton = (Button) findViewById(R.id.button8);
        contactsButton.setOnClickListener( v -> {
            startActivity(new Intent(MainActivity.this, ContactsActivity.class));
        });

    }


}