package com.example.android.newsapp;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<NewsItem>> {

    private String url;

    public NewsLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        Log.i("Tager","onStartLoading");
        forceLoad();
    }

    @Override
    public List<NewsItem> loadInBackground() {
        Log.i("Tager","loadInBackground");
        // Don't perform the request if there are no URLs, or the first URL is null.
        if (url == null)
            return null;

        return QueryUtils.fetchNewsData(url);
    }
}