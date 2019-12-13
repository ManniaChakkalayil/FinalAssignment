package com.example.finalassignment;

import android.content.SharedPreferences;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ReadingList extends AppCompatActivity {

    RecyclerView recyclerView;
    ReadAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_list);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReadAdapter(getList("read"), getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    public List<Reading> getList(String key){
        SharedPreferences prefs = getSharedPreferences("reading",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Log.d("data",json);
        Type type = new TypeToken<List<Reading>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
