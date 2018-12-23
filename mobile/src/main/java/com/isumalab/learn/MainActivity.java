package com.isumalab.learn;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar mTopToolbar;
    private Button mButton;
    private MenuItem mSearch;
    private MenuItem mUser;
    private MenuItem mLearderboard;
    private FirebaseAuth mAuth;
    private List<Course> courseList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CourseAdapter mAdapter;
    private DatabaseReference ref;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(myIntent);
        } else {
            setContentView(R.layout.activity_main);

            //adding toolbar
            mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
            setSupportActionBar(mTopToolbar);

            recyclerView = (RecyclerView) findViewById(R.id.recycle_view_course);

            mAdapter = new CourseAdapter(courseList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);

            prepareCourseData();

//            //adding button for testing
//            mButton = (Button) findViewById(R.id.button);
//            mButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent myIntent = new Intent(view.getContext(), YoutubeCourse.class);
//                    startActivity(myIntent);
//                }
//            });
        }
    }

    private void prepareCourseData() {


        ref = FirebaseDatabase.getInstance().getReference("User/" + mAuth.getCurrentUser().getUid() + "/enrolled");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                courseList.clear();
                for (DataSnapshot oneSnapShot : dataSnapshot.getChildren()) {
                    Course course = new Course();
                    String courseName = oneSnapShot.child("/playListName").getValue(String.class);
                    course.setCourseName(courseName);
                    course.setCode(oneSnapShot.getKey().toString());
                    courseList.add(course);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
//            Toast.makeText(MainActivity.this, "Action : Search clicked", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, SearchCourse.class);
            this.startActivity(i);
            return true;
        }

        if (id == R.id.action_user) {
//            Toast.makeText(MainActivity.this, "Action : User clicked", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, EditUser.class);
            this.startActivity(i);
            return true;
        }


        if (id == R.id.action_leaderboard) {
//            Toast.makeText(MainActivity.this, "Action : Leaderboard clicked", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, Leaderboard.class);
            this.startActivity(i);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

}
