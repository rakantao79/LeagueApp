package com.app.league.goldenboys.leagueapp.Admin.Activities;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.league.goldenboys.leagueapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddNewsActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar mToolbar;

    private EditText etNewsTitle;
    private EditText etNewsContent;
    private Button btnAddNews;
    private Button btnAddNewsCancel;

    private DatabaseReference mDatabase;
    private DatabaseReference mCountDatabase;
    private FirebaseAuth mAuth;

    private String pushKey;
    private String uid;
    private long countpost = 0;

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);

        mAuth = FirebaseAuth.getInstance();

        mToolbar = findViewById(R.id.toolbar_addnews);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mProgressBar= findViewById(R.id.progressBarNewsFeed);
        mProgressBar.setVisibility(View.INVISIBLE);

        etNewsTitle = findViewById(R.id.etNewsTitle);
        etNewsContent = findViewById(R.id.etNewsContent);
        btnAddNews = findViewById(R.id.btnAddNewsFeed);
        btnAddNewsCancel = findViewById(R.id.btnAddNewsCancel);

        btnAddNews.setOnClickListener(this);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("NewsFeed").push();
        mCountDatabase = FirebaseDatabase.getInstance().getReference().child("NewsFeed");

        mCountDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    countpost = dataSnapshot.getChildrenCount();
                } else {
                    countpost = 0;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAddNewsFeed:
                addNews();
                break;

            case R.id.btnAddNewsCancel:
                finish();
                break;
        }
    }

    private void addNews() {

        mProgressBar.setVisibility(View.VISIBLE);

        DateFormat df = new SimpleDateFormat("yyyy/MM/dd h:mm a");
        final String date = df.format(Calendar.getInstance().getTime());

        String title = etNewsTitle.getText().toString().trim();
        String content = etNewsContent.getText().toString().trim();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content) ){
            mProgressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "Fields must not be empty", Toast.LENGTH_SHORT).show();
        } else {

            FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
            uid = current_user.getUid();

            pushKey = mDatabase.getKey();

            HashMap putMap = new HashMap();

            putMap.put("title", title);
            putMap.put("content", content );
            putMap.put("pushkey", pushKey);
            putMap.put("datePosted", date);
            putMap.put("user_id", uid);
            putMap.put("counter", countpost);

            mDatabase.setValue(putMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(AddNewsActivity.this, "Posted", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }
    }
}
