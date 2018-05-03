package com.example.android.newsapp;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {

    Context context;
    ArrayList<NewsItem> news;

    NewsAdapter(Context context, ArrayList<NewsItem> news){
        this.context = context;
        this.news = news;
    }

    @NonNull
    @Override
    public NewsAdapter.NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item,parent,false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsAdapter.NewsHolder holder, int position) {
        final NewsItem N = news.get(position);
        holder.title.setText(N.title);
        holder.content.setHtml(N.content);
        holder.date.setText(N.date);
        if(N.thumb != null){
            holder.thumb.setImageBitmap(N.thumb);
        }
        holder.readmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment details = new DetailsFragment(N.title,N.date,N.content,N.thumb);
                FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frag_container,details);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public class NewsHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.newsitem_thumb)
        ImageView thumb;
        @BindView(R.id.newsitem_title)
        TextView title;
        @BindView(R.id.newsitem_date)
        TextView date;
        @BindView(R.id.newsitem_content)
        HtmlTextView content;
        @BindView(R.id.newsitem_readmore)
        TextView readmore;

        public NewsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
