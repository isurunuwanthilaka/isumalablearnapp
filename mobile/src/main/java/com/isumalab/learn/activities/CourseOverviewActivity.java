package com.isumalab.learn.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isumalab.learn.DTO.enrollCourseToUserDTO;
import com.isumalab.learn.R;
import com.isumalab.learn.adapters.CourseOverviewAdapter;
import com.isumalab.learn.fragments.ContinueDialogFragment;
import com.isumalab.learn.models.Lesson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CourseOverviewActivity extends AppCompatActivity implements View.OnClickListener,ContinueDialogFragment.ContinueDialogListener {

    private Toolbar mTopToolbar;
    private String name, code;
    private FirebaseAuth mAuth;
    private List<Lesson> lessonList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CourseOverviewAdapter mAdapter;
    private DatabaseReference ref;
    private Button enroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.course_lesson_overview_page);

        //adding toolbar
        mTopToolbar = (Toolbar) findViewById(R.id.my_back_toolbar);
        setSupportActionBar(mTopToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(myIntent);
        } else {

            findViewById(R.id.enroll).setOnClickListener(this);
            recyclerView = (RecyclerView) findViewById(R.id.recycle_view_lesson);

            mAdapter = new CourseOverviewAdapter(lessonList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);

            Intent intent = getIntent();
            Bundle extras = intent.getExtras();

            name = extras.getString("name");
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
                    String lessonDescription = oneSnapShot.child("/videoDescription").getValue(String.class);

                    lesson.setName(lessonName);
                    lesson.setDescription(lessonDescription);
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

    public void showNoticeDialog() {
        DialogFragment newFragment = new ContinueDialogFragment();
        newFragment.show(getSupportFragmentManager(), "enroll");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        enroll();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    public void enroll(){
        ref = FirebaseDatabase.getInstance().getReference("User/" + mAuth.getCurrentUser().getUid()+ "/enrolled");
        ref.child(code).setValue(new enrollCourseToUserDTO("", new Date(System.currentTimeMillis()),name));
        Toast.makeText(this, "Enrolled to module",
                Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.enroll:
                showNoticeDialog();
                break;
        }

    }
}
