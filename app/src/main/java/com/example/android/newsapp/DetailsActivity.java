package com.example.android.newsapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailsActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_DATE = "date";
    public static final String EXTRA_CONTENT = "content";
    public static final String EXTRA_THUMB = "thumb";
    public static final String EXTRA_AUTHOR = "author";
    public static final String EXTRA_AUTHORIMAGE = "authorImage";
    public static final String EXTRA_SECTION = "section";
    public static final String EXTRA_URL = "url";

    @BindView(R.id.details_content)
    TextView contentView;
    @BindView(R.id.details_date)
    TextView dateView;
    @BindView(R.id.details_title)
    TextView titleView;
    @BindView(R.id.details_image)
    ImageView imageView;
    @BindView(R.id.details_author_image)
    ImageView authorImage;
    @BindView(R.id.details_author)
    TextView author;
    @BindView(R.id.details_section)
    TextView section;
    @BindView(R.id.details_link)
    TextView openInBrowser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_item_details);
        ButterKnife.bind(this);

        contentView.setText(getIntent().getStringExtra(EXTRA_CONTENT));
        dateView.setText(getIntent().getStringExtra(EXTRA_DATE));
        titleView.setText(getIntent().getStringExtra(EXTRA_TITLE));
        section.setText(getIntent().getStringExtra(EXTRA_SECTION));
        author.setText(getIntent().getStringExtra(EXTRA_AUTHOR));
        if(!getIntent().getStringExtra(EXTRA_THUMB).equals(""))
            Glide.with(this).load(getIntent().getStringExtra(EXTRA_THUMB)).into(imageView);
        else
            imageView.setImageResource(R.drawable.ic_launcher_background);
        Glide.with(this).load(getIntent().getStringExtra(EXTRA_AUTHORIMAGE)).apply(RequestOptions.circleCropTransform()).into(authorImage);

        openInBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(getIntent().getStringExtra(EXTRA_URL)));

                // Verify it resolves
                PackageManager packageManager = getPackageManager();
                List<ResolveInfo> activities = packageManager.queryIntentActivities(browserIntent, 0);
                boolean isIntentSafe = activities.size() > 0;

                if (isIntentSafe) {
                    startActivity(browserIntent);
                }
                else{
                    Toast.makeText(getApplicationContext(),R.string.no_browser,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}