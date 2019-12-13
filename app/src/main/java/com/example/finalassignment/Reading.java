package com.example.finalassignment;

public class Reading {
    float x,y;
    String timestamp;

    Reading(float x, float y, String timestamp){
        this.x = x;
        this.y = y;
        this.timestamp = timestamp;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public String getTimestamp() {
        return timestamp;
    }
}

