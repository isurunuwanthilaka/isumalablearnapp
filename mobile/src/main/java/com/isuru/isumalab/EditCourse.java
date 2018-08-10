package com.isuru.isumalab;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class EditCourse extends AppCompatActivity {

    private Toolbar toolbarEditCourse;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        //adding toolbar
        toolbarEditCourse = (Toolbar) findViewById(R.id.my_back_toolbar);
        setSupportActionBar(toolbarEditCourse);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home) {

            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
