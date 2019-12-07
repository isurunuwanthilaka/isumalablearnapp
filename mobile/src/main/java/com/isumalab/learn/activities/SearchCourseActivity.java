package com.isumalab.learn.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isumalab.learn.R;
import com.isumalab.learn.adapters.SearchSectionRecyclerviewAdapter;
import com.isumalab.learn.models.SearchCourseItem;
import com.isumalab.learn.models.SearchSection;

import java.util.ArrayList;

public class SearchCourseActivity extends AppCompatActivity {

    private Toolbar toolbar;
    DatabaseReference ref;
    SearchSectionRecyclerviewAdapter adapter;


    ArrayList<SearchSection> allSampleData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_course);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        allSampleData = new ArrayList<SearchSection>();

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle("IsumaSchool");

        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        bindCourseData();


        RecyclerView my_recycler_view = (RecyclerView) findViewById(R.id.my_recycler_view);

        my_recycler_view.setHasFixedSize(true);

        adapter = new SearchSectionRecyclerviewAdapter(this, allSampleData);

        my_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        my_recycler_view.setAdapter(adapter);


    }

    public void bindCourseData() {

        ref = FirebaseDatabase.getInstance().getReference("/Course");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allSampleData.clear();
                for (DataSnapshot sectionSnapShot : dataSnapshot.getChildren()) {
                    SearchSection searchSection = new SearchSection();
                    ArrayList<SearchCourseItem> singleSectionItemList = new ArrayList<SearchCourseItem>();
                    searchSection.setHeaderTitle(sectionSnapShot.getKey().toString());
                    for (DataSnapshot itemSnapShot : sectionSnapShot.getChildren()) {
                        String code = itemSnapShot.getKey().toString();
                        String name = itemSnapShot.child("/playListName").getValue(String.class);
                        SearchCourseItem searchCourseItem = new SearchCourseItem(name,code);
                        singleSectionItemList.add(searchCourseItem);
                    }

                    searchSection.setAllItemsInSection(singleSectionItemList);
                    allSampleData.add(searchSection);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}