package com.example.android.newsapp;

import android.graphics.Bitmap;

public class NewsItem {
    String date;
    String title;
    String content;
    Bitmap thumb;

    public NewsItem(String title, Bitmap thumb, String date, String content) {
        this.date = date;
        this.title = title;
        this.content = content;
        this.thumb = thumb;
    }
}
