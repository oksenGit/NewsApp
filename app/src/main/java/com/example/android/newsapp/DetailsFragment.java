package com.example.android.newsapp;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("ValidFragment")
public class DetailsFragment extends Fragment {

    String title;
    String date;
    String content;
    Bitmap btm;

    @BindView(R.id.details_content)
    HtmlTextView contentView;
    @BindView(R.id.details_date)
    TextView dateView;
    @BindView(R.id.details_title)
    TextView titleView;
    @BindView(R.id.details_image)
    ImageView imageView;

    public DetailsFragment(String title, String date, String content, Bitmap btm){
        this.title = title;
        this.date = date;
        this.content = content;
        this.btm = btm;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.news_item_details, container, false);
        ButterKnife.bind(this,rootView);
        imageView.setImageBitmap(btm);
        contentView.setHtml(content);
        dateView.setText(date);
        titleView.setText(title);
        return rootView;
    }
}
