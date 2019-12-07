package com.isumalab.learn.activities;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;

import com.isumalab.learn.R;

public class EditCourseActivity extends AppCompatActivity {

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
