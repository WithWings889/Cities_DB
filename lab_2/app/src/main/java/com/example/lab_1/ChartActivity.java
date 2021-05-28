package com.example.lab_1;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.activity_chart);

        PieChart pieChart = findViewById(R.id.pieChart);
        ArrayList<PieEntry> visitors = new ArrayList<>();
        double[] data;
        Bundle bundle = getIntent().getExtras();
        data = bundle.getDoubleArray("data");
        for(int i = 0; i < data.length; i++) {
            String label = "";
            if(i == 0) {
                label = "Rest";
            } else {
                label = "Paid";
            }
            visitors.add(new PieEntry((float)data[i], label));
        }

        PieDataSet pieDataSet = new PieDataSet(visitors, "");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Loan amounts");
        pieChart.animate();

    }
}
