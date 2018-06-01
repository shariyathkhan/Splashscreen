package com.example.shariyath.splashscreen.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.shariyath.splashscreen.Home;
import com.example.shariyath.splashscreen.MainActivity;
import com.example.shariyath.splashscreen.R;

import java.util.ArrayList;
import java.util.HashMap;

public class CityAdapter extends RecyclerView.Adapter {

    public static String str_h,str_w,str_p;
    public interface PlayPauseClick {
        void imageButtonOnClick();  // Delete icon
    }

    private PlayPauseClick callback;

    public void setPlayPauseClickListener(PlayPauseClick listener) {
        this.callback = listener;
    }

    Context context;
    ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
    public static String str_city_name;

    public CityAdapter(Context context, ArrayList<HashMap<String,String>> arrayList) {
        this.context = context;
        this.data = arrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.city_items, parent, false);
        return new CityAdapter.UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        HashMap<String, String> result = new HashMap<String, String>();
        result = data.get(position);

        CityAdapter.UserViewHolder userViewHolder = (CityAdapter.UserViewHolder) holder;



        userViewHolder.txt_cloud.setText(result.get("all")+"%");
        userViewHolder.city_name.setText(result.get("dt_txt"));

        String temp_min = result.get("temp_min") ;
        String temp_max = result.get("temp_max") ;

        String lon = result.get("lon") ;
        String lat = result.get("lat") ;

        userViewHolder.temp.setText(result.get("temp"));
        userViewHolder.txt_temp.setText("temperature from "+ temp_min +" to "+ temp_max +" °С");

        userViewHolder.txt_latitude.setText("Longitude :"+lon+ " latitude :"+lat);

        userViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, String> hm = data.get(position);
                str_h = (String) hm.get("humidity");
                str_w = (String) hm.get("speed");
                str_p = (String) hm.get("pressure");


                if (callback != null) {
                    callback.imageButtonOnClick();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private class UserViewHolder extends RecyclerView.ViewHolder {

        TextView txt_cloud,city_name,temp,txt_temp,txt_latitude,txt_view_more;

        public UserViewHolder(View view) {
            super(view);

            txt_cloud = (TextView) view.findViewById(R.id.txt_cloud);
            city_name = (TextView) view.findViewById(R.id.city_name);
            temp = (TextView) view.findViewById(R.id.temp);
            txt_temp = (TextView) view.findViewById(R.id.txt_temp);
            txt_latitude = (TextView) view.findViewById(R.id.txt_latitude);
            txt_view_more = (TextView) view.findViewById(R.id.txt_view_more);

        }
    }

}

