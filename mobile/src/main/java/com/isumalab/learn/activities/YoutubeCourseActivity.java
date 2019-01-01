package com.isumalab.learn.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isumalab.learn.R;
import com.isumalab.learn.models.Lesson;

public class YoutubeCourseActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, OnClickListener {

    public static final String API_KEY = "AIzaSyC_RFAFLiThN0eKeJNWxxt2YFahJiIDIDU";
    public String videoID, videoName, courseID, key;
    private Toolbar mTopToolbar1;
    private TextView title, note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        findViewById(R.id.btn_pdf_download).setOnClickListener(this);
        findViewById(R.id.btn_test).setOnClickListener(this);

        //adding toolbar
//        mTopToolbar1 = (Toolbar) findViewById(R.id.my_back_toolbar);
//        setSupportActionBar(mTopToolbar1);
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        videoID = extras.getString("url");
        videoName = extras.getString("title");
        courseID = extras.getString("courseID");
        key = extras.getString("key");

        title = (TextView) findViewById(R.id.course_title);
        title.setText(videoName);

        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player);
        youTubePlayerView.initialize(API_KEY, this);

        setNote();

    }

    private void setNote() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Course/" + courseID.substring(0, 2) + "/" + courseID + "/video/" + key);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String lessonNote = dataSnapshot.child("/videoNote").getValue(String.class);

                System.out.println(lessonNote);

                note = (TextView) findViewById(R.id.textView_note);
                note.setText(lessonNote);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Failed to initialize...", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
        youTubePlayer.setPlaybackEventListener(playbackEventListener);

        if (!wasRestored) {
            youTubePlayer.cueVideo(videoID);
        }

    }

    YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {

        }

        @Override
        public void onVideoEnded() {

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };

    YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {

        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onStopped() {

        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_pdf_download:
                Toast.makeText(this, "downloading pdf...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_test:
                Toast.makeText(this, "test link", Toast.LENGTH_SHORT).show();

                break;
        }

    }
}
