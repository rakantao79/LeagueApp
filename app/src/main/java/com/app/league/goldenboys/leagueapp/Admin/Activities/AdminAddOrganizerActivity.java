package com.app.league.goldenboys.leagueapp.Admin.Activities;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.league.goldenboys.leagueapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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

public class AdminAddOrganizerActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private RadioGroup rgOrganizerGender;
    private String mGender = null;

    private EditText etLastName;
    private EditText etFirstName;
    private EditText etEmail;
    private EditText etPassword;
    private Button btnRegisterOrganizer;
    private EditText etAge;

    private ProgressBar mProgressBar;

    private long countUsers = 0;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_organizer);

        changeStatusBarColor();

        mToolbar = findViewById(R.id.toolbar_addorganizeradmin);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add Organizer");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etFirstName = findViewById(R.id.etRegisterOrganizerFirstName);
        etLastName = findViewById(R.id.etRegisterOrganizerLastName);
        etPassword = findViewById(R.id.etRegisterOrganizerPassword);
        etEmail = findViewById(R.id.etRegisterOrganizerEmail);
        etAge = findViewById(R.id.etRegisterOrganizerAge);
        btnRegisterOrganizer = findViewById(R.id.btnRegisterOrganizer);

        mProgressBar= findViewById(R.id.progressBarOrganizer);
        mProgressBar.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        rgOrganizerGender = findViewById(R.id.rgOrganizerGender);

        selectGender();

        btnRegisterOrganizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerOrganizer();
            }
        });
    }

    private void selectGender() {

        rgOrganizerGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId){
                    case R.id.rbOrganizerMale:
                        mGender = "Male";
                        break;
                    case R.id.rbOrganizerFemale:
                        mGender = "Female";
                        break;
                }
            }
        });
    }

    private void changeStatusBarColor() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    private void registerOrganizer() {

        mProgressBar.setVisibility(View.VISIBLE);

        final String lastname = etLastName.getText().toString().trim();
        final String firstname = etFirstName.getText().toString().trim();
        final String email = etEmail.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();
        final String age = etAge.getText().toString().trim();

        if (TextUtils.isEmpty(lastname) ||
                TextUtils.isEmpty(firstname) ||
                TextUtils.isEmpty(age) ||
                TextUtils.isEmpty(email) ||
                TextUtils.isEmpty(password)) {

            mProgressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "Fields must not be empty", Toast.LENGTH_SHORT).show();

        } else if (mGender.isEmpty()){

            touchable();
            mProgressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "Select Gender", Toast.LENGTH_SHORT).show();

        } else {

            notTouchable();
            counterAccounts();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()){


                        DateFormat df = new SimpleDateFormat("yyyy/MM/dd h:mm a");
                        final String date = df.format(Calendar.getInstance().getTime());

                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        String user_id = currentUser.getUid();

                        HashMap User = new HashMap();
                        User.put("UserID", user_id);
                        User.put("FirstName", firstname);
                        User.put("LastName", lastname);
                        User.put("Gender", mGender);
                        User.put("Email", email);
                        User.put("Password", password);
                        User.put("Usertype", "organizer");
                        User.put("count", countUsers);
                        User.put("age", age);
                        User.put("dateRegistered", date);
                        User.put("status", "pending");

                        mDatabase.child("Users").child(user_id).setValue(User).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                mProgressBar.setVisibility(View.INVISIBLE);
                                touchable();
                                Toast.makeText(AdminAddOrganizerActivity.this, "Succesfully Registered", Toast.LENGTH_SHORT).show();
                                mAuth.signOut();
                                finish();

                            }
                        });
                    } else {

                        touchable();
                        mProgressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(AdminAddOrganizerActivity.this, task.getException().toString(), Toast.LENGTH_LONG).show();

                    }
                }
            });
        }

    }

    private void notTouchable() {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void touchable(){
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void counterAccounts() {

        mDatabase.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    countUsers = dataSnapshot.getChildrenCount();
                    Log.d("count users : ", String.valueOf(countUsers));
                } else {
                    countUsers = 0;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
