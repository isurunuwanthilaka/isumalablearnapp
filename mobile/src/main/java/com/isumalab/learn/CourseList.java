package com.isumalab.learn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CourseList extends AppCompatActivity {

    private Toolbar mTopToolbar1;
    private String name, code;
    private FirebaseAuth mAuth;
    private List<Lesson> lessonList = new ArrayList<>();
    private RecyclerView recyclerView;
    private LessonAdapter mAdapter;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(myIntent);
        } else {
            setContentView(R.layout.course_lesson_page);

            //adding toolbar
            mTopToolbar1 = (Toolbar) findViewById(R.id.my_toolbar);
            setSupportActionBar(mTopToolbar1);

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

            prepareLessonData();
        }

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
                    String lessonName = oneSnapShot.child("/videoName").getValue(String.class);
                    String lessonUrl = oneSnapShot.child("/videoID").getValue(String.class);
                    System.out.println(lessonName);
                    System.out.println(lessonUrl);
                    lesson.setName(lessonName);
                    lesson.setUrl(lessonUrl);
                    lesson.setCategory(code);
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

}