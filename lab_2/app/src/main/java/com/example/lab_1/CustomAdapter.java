package com.example.lab_1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList city_id, city_number, city_distance, city_name;

    CustomAdapter(Activity activity, Context context, ArrayList city_id, ArrayList city_name, ArrayList city_number,
                  ArrayList city_distance){
        this.activity = activity;
        this.context = context;
        this.city_id = city_id;
        this.city_name = city_name;
        this.city_number = city_number;
        this.city_distance = city_distance;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.city_id_txt.setText(String.valueOf(city_id.get(position)));
        holder.city_name_txt.setText(String.valueOf(city_name.get(position)));
        holder.city_number_txt.setText(String.valueOf(city_number.get(position)));
        holder.city_distance_txt.setText(String.valueOf(city_distance.get(position)));
        //Recyclerview onClickListener
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(city_id.get(position)));
                intent.putExtra("name", String.valueOf(city_name.get(position)));
                intent.putExtra("number", String.valueOf(city_number.get(position)));
                intent.putExtra("distance", String.valueOf(city_distance.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });


    }

    @Override
    public int getItemCount() {
        return city_id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView city_id_txt, city_name_txt, city_number_txt, city_distance_txt;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            city_id_txt = itemView.findViewById(R.id.city_id_text);
            city_name_txt = itemView.findViewById(R.id.city_name_text);
            city_number_txt = itemView.findViewById(R.id.city_number_text);
            city_distance_txt = itemView.findViewById(R.id.city_distance_text);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            //Animate Recyclerview
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }

    }

}
