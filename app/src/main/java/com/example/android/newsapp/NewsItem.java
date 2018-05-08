package com.example.android.newsapp;

public class NewsItem {
    String date;
    String title;
    String content;
    String thumb;
    String section;
    String url;

    public NewsItem(String title, String thumb, String date, String section, String url, String content) {
        this.date = date;
        this.title = title;
        this.section = section;
        this.url = url;
        this.content = content;
        this.thumb = thumb;
    }
}
