package com.example.android.newsapp;

public class NewsItem {
    String date;
    String title;
    String content;
    String thumb;
    String section;
    String url;
    String author;
    String authorImage;

    public NewsItem(String title, String thumb, String date, String author, String authorImage, String section, String url, String content) {
        this.date = date;
        this.title = title;
        this.authorImage = authorImage;
        this.section = section;
        this.url = url;
        this.content = content;
        this.author = author;
        this.thumb = thumb;
    }
}
