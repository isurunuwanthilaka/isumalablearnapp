package com.isumalab.learn.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;

import com.isumalab.learn.R;

public class LeaderboardActivity extends AppCompatActivity {

    private Toolbar mTopToolbar1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        //adding toolbar
        mTopToolbar1 = (Toolbar) findViewById(R.id.my_back_toolbar);
        setSupportActionBar(mTopToolbar1);

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
