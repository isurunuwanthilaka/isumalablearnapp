package com.isumalab.learn.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.isumalab.learn.R;
import com.isumalab.learn.adapters.SearchSectionRecyclerviewAdapter;
import com.isumalab.learn.models.SearchCourseItem;
import com.isumalab.learn.models.SearchSection;

import java.util.ArrayList;

public class SearchCourseActivity extends AppCompatActivity {

    private Toolbar toolbar;


    ArrayList<SearchSection> allSampleData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_course);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        allSampleData = new ArrayList<SearchSection>();

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle("G PlayStore");

        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        createDummyData();


        RecyclerView my_recycler_view = (RecyclerView) findViewById(R.id.my_recycler_view);

        my_recycler_view.setHasFixedSize(true);

        SearchSectionRecyclerviewAdapter adapter = new SearchSectionRecyclerviewAdapter(this, allSampleData);

        my_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        my_recycler_view.setAdapter(adapter);


    }

    public void createDummyData() {
        for (int i = 1; i <= 5; i++) {

            SearchSection dm = new SearchSection();

            dm.setHeaderTitle("Section " + i);

            ArrayList<SearchCourseItem> singleItem = new ArrayList<SearchCourseItem>();
            for (int j = 0; j <= 5; j++) {
                singleItem.add(new SearchCourseItem("Item " + j, "URL " + j));
            }

            dm.setAllItemsInSection(singleItem);

            allSampleData.add(dm);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}