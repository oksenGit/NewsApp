package com.example.android.newsapp;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import org.sufficientlysecure.htmltextview.HtmlTextView;
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
        holder.section.setText(N.section);
        if(N.thumb != null){
            Glide.with(context).load(N.thumb).into(holder.thumb);
        }
        holder.readmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra(DetailsActivity.EXTRA_TITLE, N.title);
                intent.putExtra(DetailsActivity.EXTRA_DATE, N.date);
                intent.putExtra(DetailsActivity.EXTRA_CONTENT, N.content);
                intent.putExtra(DetailsActivity.EXTRA_THUMB, N.thumb);
                intent.putExtra(DetailsActivity.EXTRA_SECTION, N.section);
                intent.putExtra(DetailsActivity.EXTRA_URL, N.url);
                context.startActivity(intent);
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
        @BindView(R.id.newsitem_section)
        TextView section;
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
