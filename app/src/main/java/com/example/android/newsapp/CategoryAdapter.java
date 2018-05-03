package com.example.android.newsapp;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

public class CategoryAdapter extends FragmentStatePagerAdapter {

    private String tabTitles[] = new String[] {"Science", "Tech", "Entertainment", "Politics", "Sports"};

    public CategoryAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new CategoryFragment(tabTitles[position].toLowerCase());
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

}
