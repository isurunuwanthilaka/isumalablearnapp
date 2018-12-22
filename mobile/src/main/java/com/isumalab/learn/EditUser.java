package com.isumalab.learn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EditUser extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbarEditUser;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        //adding toolbar
        toolbarEditUser = (Toolbar) findViewById(R.id.my_back_toolbar);
        findViewById(R.id.sign_out).setOnClickListener(this);

        setSupportActionBar(toolbarEditUser);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mAuth = FirebaseAuth.getInstance();
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
        switch (view.getId()) {
            case R.id.sign_out:
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    mAuth.signOut();
                    Toast.makeText(this, user.getEmail()+ " Sign out!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this,MainActivity.class));
                }
                break;
        }
    }
}
