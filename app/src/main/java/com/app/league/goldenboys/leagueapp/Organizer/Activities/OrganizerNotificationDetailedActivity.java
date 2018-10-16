package com.app.league.goldenboys.leagueapp.Organizer.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.app.league.goldenboys.leagueapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrganizerNotificationDetailedActivity extends AppCompatActivity {

    private String getkey;
    private TextView tvOrganizerNotificationDetailTitle;
    private TextView tvOrganizerNotificationDetailContent;
    private TextView tvOrganizerNotificationDetailDate;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_notification_detailed);

        getkey = getIntent().getStringExtra("getkey");

        tvOrganizerNotificationDetailTitle = findViewById(R.id.tvOrganizerNotificationDetailTitle);
        tvOrganizerNotificationDetailContent = findViewById(R.id.tvOrganizerNotificationDetailContent);
        tvOrganizerNotificationDetailDate = findViewById(R.id.tvOrganizerNotificationDetailDate);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("OrganizerNotification").child(getkey);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    tvOrganizerNotificationDetailTitle.setText(dataSnapshot.child("notifTitle").getValue().toString());
                    tvOrganizerNotificationDetailContent.setText(dataSnapshot.child("notifMatch").getValue().toString());
                    tvOrganizerNotificationDetailDate.setText(dataSnapshot.child("notfiDate").getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
