package com.app.league.goldenboys.leagueapp.Organizer.Activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.league.goldenboys.leagueapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
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

import java.util.HashMap;

public class CreateLeagueActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;

    private ImageView imgCreateLeague;
    private EditText etCreateLeagueTitle;
    private EditText etCreateLeagueDescription;
    private CheckedTextView ctvJuniorDivision;
    private CheckedTextView ctvSeniorDivision;
    private CheckedTextView ctvMasterDivision;
    private CheckedTextView ctvMedietDivision;
    private Button btnCreateLeague;
    private Button btnCreateLeagueCancel;
    private ProgressBar mProgressBar;

    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private FirebaseAuth mAuth;

    private String juniorDivision = "not available";
    private String seniorDivision = "not available";
    private String masterDivision = "not available";
    private String medietDivision = "not available";

    private String pushKey;
    private String uid;
    private String fullname;
    private long countpost = 0;
    private Uri imageUri = null;

    private static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_league);

        mToolbar = findViewById(R.id.toolbar_createleague);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Create League");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etCreateLeagueTitle = findViewById(R.id.etLeageTitle);
        etCreateLeagueDescription = findViewById(R.id.etLeagueDescription);

        ctvMedietDivision = findViewById(R.id.ctvMedietDivision);
        ctvJuniorDivision = findViewById(R.id.ctvJuniorDivision);
        ctvSeniorDivision = findViewById(R.id.ctvSeniorDivision);
        ctvMasterDivision = findViewById(R.id.ctvMasterDivision);
        btnCreateLeague = findViewById(R.id.btnCreateLeague);
        btnCreateLeagueCancel = findViewById(R.id.btnCreateLeagueCancel);
        imgCreateLeague = findViewById(R.id.imgCreateLeague);
        mProgressBar = findViewById(R.id.pbProgressBarCreateLeague);
        mProgressBar.setVisibility(View.INVISIBLE);

        btnCreateLeague.setOnClickListener(this);
        btnCreateLeagueCancel.setOnClickListener(this);
        imgCreateLeague.setOnClickListener(this);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("League").push();
        mStorage = FirebaseStorage.getInstance().getReference("LeagueImages");
        mAuth = FirebaseAuth.getInstance();

        mDatabase.child("League").addValueEventListener(new ValueEventListener() {
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

        selectDivision();

    }

    private void selectDivision() {

        ctvMedietDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ctvMedietDivision.isChecked()) {
                    ctvMedietDivision.setChecked(false);
                    medietDivision = "unavailable";
                } else {
                    ctvMedietDivision.setChecked(true);
                    medietDivision = "available";
                }
            }
        });

        ctvJuniorDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ctvJuniorDivision.isChecked()) {

                    ctvJuniorDivision.setChecked(false);
                    juniorDivision = "unavailable";

                } else {

                    ctvJuniorDivision.setChecked(true);
                    Toast.makeText(CreateLeagueActivity.this, "Junior Division Selected", Toast.LENGTH_SHORT).show();
                    juniorDivision = "available";
                }
            }
        });

        ctvSeniorDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ctvSeniorDivision.isChecked()) {

                    ctvSeniorDivision.setChecked(false);
                    seniorDivision = "unavailable";

                } else {

                    ctvSeniorDivision.setChecked(true);
                    Toast.makeText(CreateLeagueActivity.this, "Senior Division Selected", Toast.LENGTH_SHORT).show();
                    seniorDivision = "available";
                }
            }
        });

        ctvMasterDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ctvMasterDivision.isChecked()) {

                    ctvMasterDivision.setChecked(false);
                    masterDivision = "unavailable";

                } else {

                    ctvMasterDivision.setChecked(true);
                    Toast.makeText(CreateLeagueActivity.this, "Open Division Selected", Toast.LENGTH_SHORT).show();
                    masterDivision = "available";

                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCreateLeague:
                createLeague();
                break;
            case R.id.btnCreateLeagueCancel:
                finish();
                break;

            case R.id.imgCreateLeague:
                selectImage();
                break;
        }
    }

    private void selectImage() {

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GALLERY_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {

            imageUri = data.getData();

            //imgAddNewsImage.setImageURI(imageUri);
            Picasso.with(this).load(imageUri).into(imgCreateLeague);

        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void createLeague() {

        final String leagueTitle = etCreateLeagueTitle.getText().toString();
        final String leagueDesc = etCreateLeagueDescription.getText().toString();

        if (leagueTitle.isEmpty() || leagueDesc.isEmpty()) {

            Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();

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
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        pushKey = mDatabase.getKey();

                        HashMap createLeagueMap = new HashMap();

                        createLeagueMap.put("leagueTitle", leagueTitle);
                        createLeagueMap.put("leagueDesc", leagueDesc);
                        createLeagueMap.put("divisionJunior", juniorDivision);
                        createLeagueMap.put("divisionSenior", seniorDivision);
                        createLeagueMap.put("divisionMaster", masterDivision);
                        createLeagueMap.put("divisionMediet", medietDivision);
                        createLeagueMap.put("leagueStatus", "open");
                        createLeagueMap.put("leagueStartDate", "datestart");
                        createLeagueMap.put("leagueEndDate", "dateend");
                        createLeagueMap.put("count", countpost);
                        createLeagueMap.put("pushKey", pushKey);
                        createLeagueMap.put("imageLeague", taskSnapshot.getDownloadUrl().toString());

                        mDatabase.setValue(createLeagueMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(CreateLeagueActivity.this, "Successfully Created League", Toast.LENGTH_SHORT).show();
                                finish();
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
                        Toast.makeText(CreateLeagueActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }
    }
}