package com.isumalab.learn.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isumalab.learn.R;
import com.isumalab.learn.adapters.LessonAdapter;
import com.isumalab.learn.models.Lesson;

import java.util.ArrayList;
import java.util.List;

public class CourseListActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar mTopToolbar1;
    private String name, code;
    private FirebaseAuth mAuth;
    private List<Lesson> lessonList = new ArrayList<>();
    private RecyclerView recyclerView;
    private LessonAdapter mAdapter;
    private DatabaseReference ref;
    private TextView title;
    private Button unenroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.course_lesson_page);

        //adding toolbar
        mTopToolbar1 = (Toolbar) findViewById(R.id.my_back_toolbar);
        setSupportActionBar(mTopToolbar1);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        findViewById(R.id.unenroll).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(myIntent);
        } else {

            recyclerView = (RecyclerView) findViewById(R.id.recycle_view_lesson);

            mAdapter = new LessonAdapter(lessonList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);

            Intent intent = getIntent();
            Bundle extras = intent.getExtras();

            name = extras.getString("playListName");
            code = extras.getString("code");

            title = (TextView) findViewById(R.id.title_lesson_page);
            title.setText(name);

            prepareLessonData();
        }

    }

    private void unenroll(){
        Toast.makeText(this, "Unenroll", Toast.LENGTH_LONG).show();
    }

    private void prepareLessonData() {


        ref = FirebaseDatabase.getInstance().getReference("Course/" + code.substring(0, 2) + "/" + code + "/video");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lessonList.clear();
                for (DataSnapshot oneSnapShot : dataSnapshot.getChildren()) {
                    Lesson lesson = new Lesson();
                    lesson.setCategory(code);
                    String lessonKey = oneSnapShot.getKey();
                    String lessonName = oneSnapShot.child("/videoName").getValue(String.class);
                    String lessonUrl = oneSnapShot.child("/videoID").getValue(String.class);
                    String lessonCourseID = oneSnapShot.child("/courseID").getValue(String.class);

                    lesson.setName(lessonName);
                    lesson.setUrl(lessonUrl);
                    lesson.setCategory(code);
                    lesson.setCourseID(lessonCourseID);
                    lesson.setKey(lessonKey);

                    lessonList.add(lesson);
                    mAdapter.notifyDataSetChanged();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.unenroll:
                unenroll();
                break;
        }
    }
}