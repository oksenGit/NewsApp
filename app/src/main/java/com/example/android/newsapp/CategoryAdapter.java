package com.example.android.newsapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class CategoryAdapter extends FragmentStatePagerAdapter {

    private String tabTitles[] = new String[] {"All" ,"Science", "Tech", "Entertainment", "Politics", "Sports"};

    public CategoryAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return newCategoryInstance(tabTitles[position].toLowerCase());
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

    private static CategoryFragment newCategoryInstance(String searchTerm) {
        CategoryFragment f = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString("searchTerm", searchTerm);
        f.setArguments(args);
        return f;
    }

}
