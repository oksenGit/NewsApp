package com.example.android.newsapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<NewsItem>> {

    private static String API_KEY = "4e57bf51-7c41-4fcb-b2a0-de62b2002c5d";
    private static final int LOADER_ID = 0;
    String GuardianURL = "https://content.guardianapis.com/search";
    private String newsSection;
    private ArrayList<NewsItem> newsItems;
    private NewsAdapter newsAdapter;
    private int currentPage = 1;
    private SharedPreferences sharedPrefs;

    @BindView(R.id.list)
    RecyclerView recyclerView;
    @BindView(R.id.loading_indicator)
    View loadingIndicator;
    @BindView(R.id.empty_view)
    TextView emptyView;
    @BindView(R.id.page_current)
    TextView pageCurrent;
    @BindView(R.id.page_left)
    TextView pageLeft;
    @BindView(R.id.page_right)
    TextView pageRight;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.news_list, container, false);
        ButterKnife.bind(this, rootView);
        Bundle args = getArguments();
        newsSection = args.getString("searchTerm");
        sharedPrefs = android.preference.PreferenceManager.getDefaultSharedPreferences(getContext());
        pageCurrent.setText("1");
        navVisable(false);
        newsItems = new ArrayList<>();
        newsAdapter = new NewsAdapter(getContext(), newsItems);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(newsAdapter);
        pageRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allGone();
                loadingIndicator.setVisibility(View.VISIBLE);
                currentPage++;
                pageCurrent.setText(currentPage+"");
                getLoaderManager().restartLoader(LOADER_ID, null, CategoryFragment.this);
            }
        });
        pageLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentPage>1) {
                    allGone();
                    loadingIndicator.setVisibility(View.VISIBLE);
                    currentPage--;
                    pageCurrent.setText(currentPage+"");
                    getLoaderManager().restartLoader(LOADER_ID, null, CategoryFragment.this);
                }
            }
        });
        getLoaderManager().initLoader(LOADER_ID, null, this);

        return rootView;
    }

    @NonNull
    @Override
    public Loader<List<NewsItem>> onCreateLoader(int id, @Nullable Bundle args) {
        String fromDate = sharedPrefs.getString("from-date","");
        String toDate = sharedPrefs.getString("to-date","");
        String orderBy = sharedPrefs.getString("order-by",getString(R.string.order_by_default));
        String pageSize = sharedPrefs.getString("page-size",getString(R.string.item_per_page_default));

        Uri baseUri = Uri.parse(GuardianURL);

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("q",newsSection);
        uriBuilder.appendQueryParameter("format","json");
        uriBuilder.appendQueryParameter("page-size",pageSize);
        uriBuilder.appendQueryParameter("page",currentPage+"");
        uriBuilder.appendQueryParameter("show-fields","headline,thumbnail");
        uriBuilder.appendQueryParameter("show-tags","contributor");
        uriBuilder.appendQueryParameter("show-blocks","body");
        uriBuilder.appendQueryParameter("order-by",orderBy);
        if(!fromDate.equals("")){
            uriBuilder.appendQueryParameter("from-date",fromDate);
        }
        if(!toDate.equals("")){
            uriBuilder.appendQueryParameter("to-date",toDate);
        }
        uriBuilder.appendQueryParameter("api-key",API_KEY);


        Log.i("QUERY",uriBuilder.toString());
        //"https://content.guardianapis.com/search?q=" + newsSection + "&format=json&page-size=50&show-fields=headline,thumbnail&show-tags=contributor&show-blocks=all&order-by=newest&api-key=" + API_KEY
        return new NewsLoader(this.getContext(), uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<NewsItem>> loader, List<NewsItem> newsItems) {
        loadingIndicator.setVisibility(View.GONE);

        Log.i("Tager", "onLoadFinished");
        if (newsItems != null && !newsItems.isEmpty()) {
            navVisable(true);
            newsAdapter.news.clear();
            newsAdapter.news.addAll(newsItems);
            navState();
            newsAdapter.notifyDataSetChanged();
            recyclerView.scrollToPosition(0);
        } else {
            allGone();
            if (!isNetworkAvailable())
                emptyView.setText(R.string.no_internet);
            else
                emptyView.setText(R.string.nothing_found);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<NewsItem>> loader) {
        newsAdapter.news.clear();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    void allGone(){
        newsAdapter.news.clear();
        newsAdapter.notifyDataSetChanged();
        pageRight.setVisibility(View.GONE);
        pageLeft.setVisibility(View.GONE);
        pageCurrent.setVisibility(View.GONE);
        loadingIndicator.setVisibility(View.GONE);
    }

    void navVisable(boolean visable){
        if(visable){
            pageCurrent.setVisibility(View.VISIBLE);
            pageLeft.setVisibility(View.VISIBLE);
            pageRight.setVisibility(View.VISIBLE);
        }
        else{
            pageCurrent.setVisibility(View.GONE);
            pageLeft.setVisibility(View.GONE);
            pageRight.setVisibility(View.GONE);
        }
    }

    void navState(){
        int pageSize =  Integer.valueOf(sharedPrefs.getString("item_per_page", getString(R.string.item_per_page_default)));
        if(newsAdapter.news.size()< pageSize)
            pageRight.setVisibility(View.GONE);
        else
            pageRight.setVisibility(View.VISIBLE);
        if(currentPage>1){
            pageLeft.setVisibility(View.VISIBLE);
        }
        else
            pageLeft.setVisibility(View.GONE);
    }
}
