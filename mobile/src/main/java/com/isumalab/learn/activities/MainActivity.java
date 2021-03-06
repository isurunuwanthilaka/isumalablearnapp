package com.isumalab.learn.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isumalab.learn.R;
import com.isumalab.learn.adapters.CourseAdapter;
import com.isumalab.learn.models.Course;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final String TAG = "MAIN ACTIVITY";

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

        //
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
        }

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
//                    Intent myIntent = new Intent(view.getContext(), YoutubeCourseActivity.class);
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Log.d(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
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
            Intent i = new Intent(this, SearchCourseActivity.class);
            this.startActivity(i);
            return true;
        }

        if (id == R.id.action_user) {
//            Toast.makeText(MainActivity.this, "Action : User clicked", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, EditUserActivity.class);
            this.startActivity(i);
            return true;
        }


        if (id == R.id.action_leaderboard) {
//            Toast.makeText(MainActivity.this, "Action : LeaderboardActivity clicked", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, LeaderboardActivity.class);
            this.startActivity(i);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

}
