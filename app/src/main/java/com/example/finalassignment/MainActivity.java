package com.example.finalassignment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



import java.lang.reflect.Type;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity implements SensorEventListener, View.OnClickListener {


    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    TextView gx,gy,indicator;
    Button ball,ball_right,ball_top,save,list_button;
    float crx,cry;
    float gxv;
    float gyv;
    float gzv;
    String timestamp;
    List<Reading> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        setContentView(R.layout.activity_main);
        getList("read");
        gx = findViewById(R.id.gx);
        gy = findViewById(R.id.gy);
        indicator  =findViewById(R.id.indicator);
        ball = findViewById(R.id.ball);
        ball_right = findViewById(R.id.ball_right);
        ball_top = findViewById(R.id.ball_top);
        save = findViewById(R.id.save);
        list_button = findViewById(R.id.list);
        list_button.setOnClickListener(this);
        save.setOnClickListener(this);
        crx = ball.getX()+320;
        cry = ball.getY()+325;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        gxv  = event.values[0];
        gyv  = event.values[1];
        gzv  = event.values[2];
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm dd/MM/yyyy");
        String strDate= formatter.format(date);
        timestamp = strDate;
        gx.setText("X: "+String.valueOf(gxv));
        gy.setText("Y: "+String.valueOf(gyv));
        show_indicator(gxv,gyv);
        ball.animate()
                .x(crx + gxv*50)
                .y(cry + gyv*50)
                .setDuration(500)
                .start();
        ball_top.animate()
                .x(crx + gxv*50)
                .setDuration(500)
                .start();
        ball_right.animate()
                .y(cry + gyv*50)
                .setDuration(500)
                .start();

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    void show_indicator(float x, float y){
        if( x<=.5 && x>=-.5 && y<=.5 && y>=-.5  ){
            indicator.setText("Almost Reached");
        }else{
            indicator.setText("");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save:
                Reading r = new Reading(gxv,gyv,timestamp);
                list.add(r);
                saveList(list,"read");
                break;
            case R.id.list:
                startActivity(new Intent(this,ReadingList.class));
                break;
        }
    }

    public void saveList(List<Reading> list, String key){
        SharedPreferences prefs = getSharedPreferences("reading",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        Log.d("sentData",json);
        editor.putString(key, json);
        editor.apply();// This line is IMPORTANT !!!
    }

    public void getList(String key){
        SharedPreferences prefs = getSharedPreferences("reading",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        if (json == null){
            list = new ArrayList<>();
        }else{
            Type type = new TypeToken<List<Reading>>() {}.getType();
            list = gson.fromJson(json, type);
        }

    }

}
