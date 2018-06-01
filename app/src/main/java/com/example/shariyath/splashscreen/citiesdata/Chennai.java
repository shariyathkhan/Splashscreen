package com.example.shariyath.splashscreen.citiesdata;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.shariyath.splashscreen.R;
import com.example.shariyath.splashscreen.adapter.CityAdapter;
import com.example.shariyath.splashscreen.common.AppController;
import com.example.shariyath.splashscreen.common.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static com.android.volley.VolleyLog.TAG;

public class Chennai extends Fragment implements CityAdapter.PlayPauseClick{

    LinearLayoutManager mLayoutManager;
    RecyclerView recycler_view;
    CityAdapter cityAdapter;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<HashMap<String, String>>();
    int limit_count  = 1;
    ProgressDialog pDialog;
    String url_weather;
    String id,lon,lat,dt_txt;
    AlertDialog alertDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.listed, container, false);
        return view;

    }
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);


        recycler_view = (RecyclerView) getView().findViewById(R.id.recycler_view_details);

        recycler_view.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        recycler_view.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recycler_view.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recycler_view.setLayoutManager(mLayoutManager);


        WeatherLoaded();


    }

    //===========================================================>ProductsList WEB SERVICES CALLING.............
    private void WeatherLoaded() {

        String tag_json_obj = "json_obj_req";

            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Loading...");
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.show();

        //http://api.openweathermap.org/data/2.5/weather?q=Mumbai,in&APPID=c22ce917e53c30a79860941c8cf54484

        url_weather = Constants.WEATHER_URL+"?q=Chennai,in"+"&APPID=c22ce917e53c30a79860941c8cf54484";

        Log.i("url_weather","" + url_weather);


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url_weather, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());

                        if(response!=null) {

                            try {

                                String name = response.getString("name");
                                id = response.getString("id");
                                dt_txt = "Today";

                                Log.i("arrayList=====","" + arrayList);

                                JSONObject jsonObject_Main = response.getJSONObject("main");
                                JSONObject jsonObject_Cloud = response.getJSONObject("clouds");
                                JSONObject jsonObject_Lon = response.getJSONObject("coord");

                                JSONObject jsonObject_Wind = response.getJSONObject("wind");

                                String temp = jsonObject_Main.getString("temp");
                                String pressure = jsonObject_Main.getString("pressure");
                                String humidity = jsonObject_Main.getString("humidity");
                                String temp_min = jsonObject_Main.getString("temp_min");
                                String temp_max = jsonObject_Main.getString("temp_max");

                                String all = jsonObject_Cloud.getString("all");

                                 lon = jsonObject_Lon.getString("lon");
                                 lat = jsonObject_Lon.getString("lat");

                                String speed = jsonObject_Wind.getString("speed");
                                //String deg = jsonObject_Wind.getString("deg");


                                HashMap<String,String> map = new HashMap<String, String>();
                                map.put("temp",temp);
                                map.put("pressure",pressure);
                                map.put("humidity",humidity);
                                map.put("temp_min",temp_min);
                                map.put("temp_max",temp_max);

                                map.put("all",all);

                                map.put("lon",lon);
                                map.put("lat",lat);

                                map.put("speed",speed);
                                //map.put("deg",deg);
                                map.put("dt_txt",dt_txt);

                                arrayList.add(map);

                                if(!arrayList.isEmpty()) {

                                    Log.i("arrayList=====","" + arrayList);

                                    WeatherLoaded_forecast();

                                 /*   cityAdapter = new CityAdapter(getContext(), arrayList);
                                    recycler_view.setAdapter(cityAdapter);*/

                                }else {

                                }// arrayList_products

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                if(limit_count==1) {
                                    pDialog.dismiss();
                                }
                            }// try
                               // pDialog.dismiss();

                        }else {
                            Toast.makeText(getContext(), "Json error: ", Toast.LENGTH_LONG).show();
                            pDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

                    pDialog.dismiss();
            }
        });

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }// Details for ProductsList

    private void WeatherLoaded_forecast() {

        String tag_json_obj = "json_obj_req";

      /*  pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading...");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();*/

        //http://api.openweathermap.org/data/2.5/weather?q=Mumbai,in&APPID=c22ce917e53c30a79860941c8cf54484
        // http://api.openweathermap.org/data/2.5/forecast?id=1275339&APPID=c22ce917e53c30a79860941c8cf54484

        url_weather = Constants.FORECAST_URL+"?id="+id+"&APPID=c22ce917e53c30a79860941c8cf54484";

        Log.i("url_weather","===========" + Constants.FORECAST_URL+"?id="+id+"&APPID=c22ce917e53c30a79860941c8cf54484");


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url_weather, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());

                        if(response!=null) {

                            try {

                                JSONArray jsonArray = response.getJSONArray("list");

                                for(int i=0;i<3;i++){
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    dt_txt = jsonObject.getString("dt_txt");

                                    JSONObject jsonObject_Main = jsonObject.getJSONObject("main");
                                    JSONObject jsonObject_Cloud = jsonObject.getJSONObject("clouds");
                                    //JSONObject jsonObject_Lon = jsonObject.getJSONObject("coord");

                                    JSONObject jsonObject_Wind = jsonObject.getJSONObject("wind");


                                    String temp = jsonObject_Main.getString("temp");
                                    String pressure = jsonObject_Main.getString("pressure");
                                    String humidity = jsonObject_Main.getString("humidity");
                                    String temp_min = jsonObject_Main.getString("temp_min");
                                    String temp_max = jsonObject_Main.getString("temp_max");

                                    String all = jsonObject_Cloud.getString("all");

                                    //String lon = jsonObject_Lon.getString("lon");
                                    //String lat = jsonObject_Lon.getString("lat");

                                    String speed = jsonObject_Wind.getString("speed");
                                    //String deg = jsonObject_Wind.getString("deg");


                                    HashMap<String,String> map = new HashMap<String, String>();
                                    map.put("temp",temp);
                                    map.put("pressure",pressure);
                                    map.put("humidity",humidity);
                                    map.put("temp_min",temp_min);
                                    map.put("temp_max",temp_max);

                                    map.put("all",all);

                                    map.put("lon",lon);
                                    map.put("lat",lat);

                                    map.put("speed",speed);
                                    //map.put("deg",deg);

                                    map.put("dt_txt",dt_txt);

                                    arrayList.add(map);

                                }


                                if(!arrayList.isEmpty()) {

                                    cityAdapter = new CityAdapter(getContext(), arrayList);
                                    recycler_view.setAdapter(cityAdapter);
                                    cityAdapter.setPlayPauseClickListener(Chennai.this);


                                }else {

                                }// arrayList_products

                                pDialog.dismiss();

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                if(limit_count==1) {
                                    pDialog.dismiss();
                                }
                            }// try
                            pDialog.dismiss();

                        }else {
                            Toast.makeText(getContext(), "Json error: ", Toast.LENGTH_LONG).show();
                            pDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

                pDialog.dismiss();
            }
        });

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }// Details for ProductsList


    @Override
    public void imageButtonOnClick() {
        Log.i("imageButtonOnClick","11111Test is done=======================================================================>");

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
            LayoutInflater inflater = this.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.alert, null);
            dialogBuilder.setView(dialogView);


            dialogBuilder.setTitle("Weather Details");
            dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.dismiss();
                }
            });
        dialogBuilder.setPositiveButton("Share", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Humidity: " + CityAdapter.str_h+"Wind: "+ CityAdapter.str_w+"Pressure: "+ CityAdapter.str_p);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });



        TextView txt_humidity = (TextView) dialogView.findViewById(R.id.txt_humidity);
        TextView txt_wind = (TextView) dialogView.findViewById(R.id.txt_wind);
        TextView txt_pressure = (TextView) dialogView.findViewById(R.id.txt_pressure);

        txt_humidity.setText("Humidity: " + CityAdapter.str_h);
        txt_wind.setText("Wind: "+ CityAdapter.str_w);
        txt_pressure.setText("Pressure: "+ CityAdapter.str_p);

            alertDialog = dialogBuilder.create();
            alertDialog.show();


        }


}

