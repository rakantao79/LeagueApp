package com.app.league.goldenboys.leagueapp.Organizer.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

public class DetailTeamEntryActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvTeamName;
    private TextView tvTeamMemberA;
    private TextView tvTeamMemberB;
    private TextView tvTeamMemberC;
    private TextView tvTeamMemberD;
    private TextView tvTeamMemberE;
    private TextView tvTeamMemberF;
    private TextView tvTeamMemberG;
    private TextView tvTeamMemberH;
    private TextView tvTeamMemberI;
    private TextView tvTeamMemberJ;
    private TextView tvTeamMemberK;
    private TextView tvTeamMemberL;

    private Button btnAcceptEntry;
    private Button btnDeclineEntry;

    private String pushkey;
    private String divisionCategory;
    private String leagueTitle;

    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseApprovedEntries;
    private String usertype;
    private DatabaseReference mDatabaseUser;
    private DatabaseReference mDatabaseOrganizerNotification;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_team_entry);

        pushkey = getIntent().getExtras().getString("pushkey");
        leagueTitle = getIntent().getExtras().getString("leagueTitle");
        divisionCategory = getIntent().getExtras().getString("divisionCategory");

        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

        DateFormat df = new SimpleDateFormat("yyyy/MM/dd h:mm a");
        date = df.format(Calendar.getInstance().getTime());

        tvTeamName = findViewById(R.id.tvDetailTeamEntryName);
        tvTeamMemberA = findViewById(R.id.tvDetailTeamEntryMemberA);
        tvTeamMemberB = findViewById(R.id.tvDetailTeamEntryMemberB);
        tvTeamMemberC = findViewById(R.id.tvDetailTeamEntryMemberC);
        tvTeamMemberD = findViewById(R.id.tvDetailTeamEntryMemberD);
        tvTeamMemberE = findViewById(R.id.tvDetailTeamEntryMemberE);
        tvTeamMemberF = findViewById(R.id.tvDetailTeamEntryMemberF);
        tvTeamMemberG = findViewById(R.id.tvDetailTeamEntryMemberG);
        tvTeamMemberH = findViewById(R.id.tvDetailTeamEntryMemberH);
        tvTeamMemberI = findViewById(R.id.tvDetailTeamEntryMemberI);
        tvTeamMemberJ = findViewById(R.id.tvDetailTeamEntryMemberJ);
        tvTeamMemberK = findViewById(R.id.tvDetailTeamEntryMemberK);
        tvTeamMemberL = findViewById(R.id.tvDetailTeamEntryMemberL);


        btnAcceptEntry = findViewById(R.id.btnAcceptEntry);
        btnDeclineEntry = findViewById(R.id.btnDeclineEntry);

        btnAcceptEntry.setOnClickListener(this);
        btnDeclineEntry.setOnClickListener(this);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("LeagueEntries").child(leagueTitle).child(divisionCategory).child("PendingEntries").child(pushkey);
        mDatabaseApprovedEntries = FirebaseDatabase.getInstance().getReference().child("LeagueEntries").child(leagueTitle).child(divisionCategory).child("ApprovedEntries").child(pushkey);
        mDatabaseOrganizerNotification = FirebaseDatabase.getInstance().getReference().child("UserNotificationFinal");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    tvTeamMemberA.setText(dataSnapshot.child("teamMemberA").getValue().toString());
                    tvTeamMemberB.setText(dataSnapshot.child("teamMemberB").getValue().toString());
                    tvTeamMemberC.setText(dataSnapshot.child("teamMemberC").getValue().toString());
                    tvTeamMemberD.setText(dataSnapshot.child("teamMemberD").getValue().toString());
                    tvTeamMemberE.setText(dataSnapshot.child("teamMemberE").getValue().toString());
                    tvTeamMemberF.setText(dataSnapshot.child("teamMemberF").getValue().toString());
                    tvTeamMemberG.setText(dataSnapshot.child("teamMemberG").getValue().toString());
                    tvTeamMemberH.setText(dataSnapshot.child("teamMemberH").getValue().toString());
                    tvTeamMemberI.setText(dataSnapshot.child("teamMemberI").getValue().toString());
                    tvTeamMemberJ.setText(dataSnapshot.child("teamMemberJ").getValue().toString());
                    tvTeamMemberK.setText(dataSnapshot.child("teamMemberK").getValue().toString());
                    tvTeamMemberL.setText(dataSnapshot.child("teamMemberL").getValue().toString());
                    tvTeamName.setText(dataSnapshot.child("teamName").getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    usertype = dataSnapshot.child("Usertype").getValue().toString();

                    if (usertype.equals("customer")){
                        btnAcceptEntry.setVisibility(View.GONE);
                        btnDeclineEntry.setVisibility(View.GONE);
                    } else if (usertype.equals("organizer")){
                        btnAcceptEntry.setVisibility(View.VISIBLE);
                        btnDeclineEntry.setVisibility(View.VISIBLE);
                    }
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
            case R.id.btnAcceptEntry:
                acceptEntry();
                break;
            case R.id.btnDeclineEntry:
                declineEntry();
                break;
        }
    }

    private void acceptEntry() {

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mDatabaseApprovedEntries.setValue(dataSnapshot.getValue(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError != null){
                            Toast.makeText(DetailTeamEntryActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        } else {
                            mDatabase.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    HashMap organizerNotifmap = new HashMap();
                                    organizerNotifmap.put("notifTitle", leagueTitle + " " + divisionCategory + " " + tvTeamName.getText().toString() + " entry has approved");
                                    organizerNotifmap.put("notifMatch", "submitted an entry");
                                    organizerNotifmap.put("notfiDate", date);
                                    mDatabaseOrganizerNotification.push().setValue(organizerNotifmap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(DetailTeamEntryActivity.this, "Team Entry Approved", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    });
                                }
                            });
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(DetailTeamEntryActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void declineEntry() {
        mDatabase.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                HashMap organizerNotifmap = new HashMap();
                organizerNotifmap.put("notifTitle", leagueTitle + " " + divisionCategory + " " + tvTeamName.getText().toString() + " entry has declined");
                organizerNotifmap.put("notifMatch", "submitted an entry");
                organizerNotifmap.put("notfiDate", date);
                mDatabaseOrganizerNotification.push().setValue(organizerNotifmap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(DetailTeamEntryActivity.this, "Team Declined", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

            }
        });
    }
}
