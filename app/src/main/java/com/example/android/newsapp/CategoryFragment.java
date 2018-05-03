package com.example.android.newsapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("ValidFragment")
public class  CategoryFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<NewsItem>>{

    String newsSection;
    ArrayList<NewsItem> newsItems;
    NewsAdapter newsAdapter;
    boolean visited;

    @BindView(R.id.list)
    RecyclerView recyclerView;

    public CategoryFragment(String newsSection){
        this.newsSection = newsSection;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.news_list, container, false);
        ButterKnife.bind(this,rootView);
        newsItems = new ArrayList<>();
        newsAdapter = new NewsAdapter(getContext(),newsItems);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(newsAdapter);
        getLoaderManager().initLoader(0, null, this);
        visited = true;
        return rootView;
    }

    @NonNull
    @Override
    public Loader<List<NewsItem>> onCreateLoader(int id, @Nullable Bundle args) {
        return new NewsLoader(this.getContext(),"https://content.guardianapis.com/search?q="+newsSection+"&format=json&show-fields=headline,thumbnail&show-blocks=all&order-by=newest&api-key=test");
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<NewsItem>> loader, List<NewsItem> newsItems) {
        Log.i("Tager","onLoadFinished");
        newsAdapter.news = (ArrayList<NewsItem>)newsItems;
        newsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<NewsItem>> loader) {
        newsAdapter.news.clear();
    }

}
