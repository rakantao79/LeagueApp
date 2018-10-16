package com.app.league.goldenboys.leagueapp.Admin.Activities;


import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.league.goldenboys.leagueapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddNewsActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;

    private EditText etNewsTitle;
    private EditText etNewsContent;
    private Button btnAddNews;
    private Button btnAddNewsCancel;
    private ImageView imgAddNewsImage;
    private Uri imageUri = null;

    private StorageReference mStorage;

    private DatabaseReference UserDatabase;
    private DatabaseReference mDatabase;
    private DatabaseReference mCountDatabase;
    private FirebaseAuth mAuth;

    private String pushKey;
    private String uid;
    private String fullname;
    private long countpost = 0;

    private static final int GALLERY_REQUEST = 1;

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        uid = current_user.getUid();


        mToolbar = findViewById(R.id.toolbar_addnews);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mProgressBar = findViewById(R.id.progressBarNewsFeed);
        mProgressBar.setVisibility(View.INVISIBLE);

        etNewsTitle = findViewById(R.id.etNewsTitle);
        etNewsContent = findViewById(R.id.etNewsContent);
        btnAddNews = findViewById(R.id.btnAddNewsFeed);
        btnAddNewsCancel = findViewById(R.id.btnAddNewsCancel);
        imgAddNewsImage = findViewById(R.id.imgAddNewsImage);

        btnAddNews.setOnClickListener(this);
        imgAddNewsImage.setOnClickListener(this);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("NewsFeed").push();
        mCountDatabase = FirebaseDatabase.getInstance().getReference().child("NewsFeed");
        mStorage = FirebaseStorage.getInstance().getReference("NewsImages");

        mCountDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
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
        switch (v.getId()) {
            case R.id.btnAddNewsFeed:
                addNews();
                break;

            case R.id.btnAddNewsCancel:
                finish();
                break;

            case R.id.imgAddNewsImage:
                selectImage();
                break;
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {

            imageUri = data.getData();

            //imgAddNewsImage.setImageURI(imageUri);
            Picasso.with(this).load(imageUri).into(imgAddNewsImage);

        }

    }

    private void selectImage() {

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GALLERY_REQUEST);

    }


    private void addNews() {

        mProgressBar.setVisibility(View.VISIBLE);

        DateFormat df = new SimpleDateFormat("yyyy/MM/dd h:mm a");
        final String date = df.format(Calendar.getInstance().getTime());

        final String title = etNewsTitle.getText().toString().trim();
        final String content = etNewsContent.getText().toString().trim();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {

            mProgressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "Fields must not be empty", Toast.LENGTH_SHORT).show();

        } else {

            if (imageUri != null) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mProgressBar.setProgress(0);
                    }
                }, 500);

                StorageReference filereference = mStorage.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));

                filereference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {

                        UserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

                        UserDatabase.child(uid).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    String firstname = dataSnapshot.child("FirstName").getValue().toString();
                                    String lastname = dataSnapshot.child("LastName").getValue().toString();

                                    fullname = firstname + " " + lastname;
                                    pushKey = mDatabase.getKey();

                                    DatabaseReference newsfeed = mDatabase;

                                    newsfeed.child("newstitle").setValue(title);
                                    newsfeed.child("newscontent").setValue(content);
                                    newsfeed.child("pushKey").setValue(pushKey);
                                    newsfeed.child("datePosted").setValue(date);
                                    newsfeed.child("user_id").setValue(uid);
                                    newsfeed.child("author").setValue(fullname);
                                    newsfeed.child("imageUrl").setValue(taskSnapshot.getDownloadUrl().toString());

                                    finish();
//                                    HashMap putMap = new HashMap();
//                                    putMap.put("newstitle", title);
//                                    putMap.put("newscontent", content);
//                                    putMap.put("pushKey", pushKey);
//                                    putMap.put("datePosted", date);
//                                    putMap.put("user_id", uid);
//                                    putMap.put("counter", countpost);
//                                    putMap.put("author", fullname);
//                                    putMap.put("imageUrl", imageUrl);
//
//                                    mDatabase.setValue(putMap);

//                                    mDatabase.setValue(putMap).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void aVoid) {
//                                            mProgressBar.setVisibility(View.INVISIBLE);
//                                            Toast.makeText(AddNewsActivity.this, "Posted", Toast.LENGTH_SHORT).show();
//                                            finish();
//                                        }
//                                    });
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        mProgressBar.setProgress((int) progress);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddNewsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

        }
    }
}
