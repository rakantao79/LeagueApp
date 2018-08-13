package com.app.league.goldenboys.leagueapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tvNoAccount;
    private Button btnLogin;

    private EditText etLoginEmail;
    private EditText etLoginPassword;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private ProgressBar mProgressBar;

    String Usertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        changeStatusBarColor();

        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        tvNoAccount = findViewById(R.id.tvNoAccount);
        btnLogin = findViewById(R.id.btnLogin);

        mProgressBar= findViewById(R.id.progressBarLogin);
        mProgressBar.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        tvNoAccount.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tvNoAccount:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
                break;

            case R.id.btnLogin :
                //startActivity(new Intent(LoginActivity.this, NavigationActivity.class));
                login();
                break;
        }
    }

    private void notTouchable() {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void touchable(){
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void login() {
        notTouchable();
        mProgressBar.setVisibility(View.VISIBLE);
        String email = etLoginEmail.getText().toString().trim();
        String password = etLoginPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) ||  TextUtils.isEmpty(password)){
            touchable();
            mProgressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(LoginActivity.this, "Please Enter Login Details", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    FirebaseUser mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
                    String current_uid = mCurrentUser.getUid();
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);

                    mDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){
                                Usertype = dataSnapshot.child("Usertype").getValue().toString();
                                touchable();
                                mProgressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(LoginActivity.this, "Succesfully Login as " + Usertype, Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
                                intent.putExtra("Usertype", Usertype);
                                Log.d("usertype ", "onDataChange: " + Usertype);
                                startActivity(intent);

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

//                    Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
//                    intent.putExtra("Usertype", Usertype);
//                    startActivity(intent);

                }
            });
        }
    }
}
