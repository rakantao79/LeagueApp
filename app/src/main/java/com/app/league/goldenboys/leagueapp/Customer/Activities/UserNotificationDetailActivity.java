package com.app.league.goldenboys.leagueapp.Customer.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.app.league.goldenboys.leagueapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserNotificationDetailActivity extends AppCompatActivity {

    private String pushkey;
    private TextView tvUserNotificationDetailTitle;
    private TextView tvUserNotificationDetailContent;
    private TextView tvUserNotificationDetailDate;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_notification_detail);

        pushkey = getIntent().getStringExtra("getkey");

        tvUserNotificationDetailTitle = findViewById(R.id.tvUserNotificationDetailTitle);
        tvUserNotificationDetailContent = findViewById(R.id.tvUserNotificationDetailContent);
        tvUserNotificationDetailDate = findViewById(R.id.tvUserNotificationDetailDate);


        mDatabase = FirebaseDatabase.getInstance().getReference().child("UserNotificationFinal").child(pushkey);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    tvUserNotificationDetailTitle.setText(dataSnapshot.child("notifTitle").getValue().toString());
                    tvUserNotificationDetailContent.setText(dataSnapshot.child("notifMatch").getValue().toString());
                    tvUserNotificationDetailDate.setText(dataSnapshot.child("notfiDate").getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
