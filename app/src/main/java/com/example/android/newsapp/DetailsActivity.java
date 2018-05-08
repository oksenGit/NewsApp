package com.example.android.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailsActivity extends AppCompatActivity{
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_DATE = "date";
    public static final String EXTRA_CONTENT = "content";
    public static final String EXTRA_THUMB = "thumb";
    public static final String EXTRA_SECTION = "section";
    public static final String EXTRA_URL = "url";

    @BindView(R.id.details_content)
    HtmlTextView contentView;
    @BindView(R.id.details_date)
    TextView dateView;
    @BindView(R.id.details_title)
    TextView titleView;
    @BindView(R.id.details_image)
    ImageView imageView;
    @BindView(R.id.details_section)
    TextView section;
    @BindView(R.id.details_link)
    TextView openInBrowser;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_item_details);
        ButterKnife.bind(this);

        contentView.setHtml(getIntent().getStringExtra(EXTRA_CONTENT));
        dateView.setText(getIntent().getStringExtra(EXTRA_DATE));
        titleView.setText(getIntent().getStringExtra(EXTRA_TITLE));
        section.setText(getIntent().getStringExtra(EXTRA_SECTION));
        Glide.with(this).load(getIntent().getStringExtra(EXTRA_THUMB)).into(imageView);

        openInBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(getIntent().getStringExtra(EXTRA_URL)));
                startActivity(i);
            }
        });
    }
}