package com.example.android.newsapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {
    @BindView(R.id.search_edittext)
    EditText searchBox;

    @BindView(R.id.search_go)
    ImageView searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String queryText = searchBox.getText().toString();
                Fragment search = newCategoryInstance(queryText);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frag_container,search);
                fragmentTransaction.commit();
            }
        });

    }

    private static CategoryFragment newCategoryInstance(String searchTerm) {
        CategoryFragment f = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString("searchTerm", searchTerm);
        f.setArguments(args);
        return f;
    }
}
