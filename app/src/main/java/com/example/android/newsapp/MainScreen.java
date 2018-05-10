package com.example.android.newsapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainScreen extends Fragment {
    @BindView(R.id.feed_viewpager)
    ViewPager viewPager;

    @BindView(R.id.feed_tablayout)
    TabLayout tabLayout;

    @BindView(R.id.feed_searchButton)
    ImageView searchImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_screen,container, false);
        ButterKnife.bind(this, rootView);
        CategoryAdapter categoryAdapter = new CategoryAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(categoryAdapter);
        tabLayout.setupWithViewPager(viewPager);

        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchIntent = new Intent(getActivity(), SearchActivity.class);
                startActivity(searchIntent);
            }
        });

        return rootView;
    }
}
