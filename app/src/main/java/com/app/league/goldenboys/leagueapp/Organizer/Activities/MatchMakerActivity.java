package com.app.league.goldenboys.leagueapp.Organizer.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MatchMakerActivity extends AppCompatActivity {

    private String leagueTitle;
    private String pushkey;
    private String divisionCategory;

    private Button btnSlotA;
    private Button btnSlotB;
    private Button btnSlotC;
    private Button btnSlotD;
    private Button btnSlotE;
    private Button btnSlotF;
    private Button btnSlotG;
    private Button btnSlotH;
    private Button btnSlotI;
    private Button btnSlotJ;
    private Button btnSlotK;
    private Button btnSlotL;
    private Button btnSlotM;
    private Button btnSlotN;
    private Button btnSlotO;

    private Button btnDeleteMatch;
    private Button btnMatchOverview;
    private Button btnViewMatchList;


    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseMatch;
    private DatabaseReference mDatabaseUser;
    private DatabaseReference mDatabaseTournament;
    private String usertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_maker);

        pushkey = getIntent().getExtras().getString("pushkey");
        divisionCategory = getIntent().getStringExtra("divisionCategory");
        leagueTitle = getIntent().getStringExtra("leagueTitle");

        btnSlotA = findViewById(R.id.btnSlotA);
        btnSlotB = findViewById(R.id.btnSlotB);
        btnSlotC = findViewById(R.id.btnSlotC);
        btnSlotD = findViewById(R.id.btnSlotD);
        btnSlotE = findViewById(R.id.btnSlotE);
        btnSlotF = findViewById(R.id.btnSlotF);
        btnSlotG = findViewById(R.id.btnSlotG);
        btnSlotH = findViewById(R.id.btnSlotH);
        btnSlotI = findViewById(R.id.btnSlotI);
        btnSlotJ = findViewById(R.id.btnSlotJ);
        btnSlotK = findViewById(R.id.btnSlotK);
        btnSlotL = findViewById(R.id.btnSlotL);
        btnSlotM = findViewById(R.id.btnSlotM);
        btnSlotN = findViewById(R.id.btnSlotN);
        btnSlotO = findViewById(R.id.btnSlotO);
        btnMatchOverview = findViewById(R.id.btnMatchOverview);
        btnViewMatchList = findViewById(R.id.btnViewMatchList);

        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

        mDatabaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usertype = dataSnapshot.child("Usertype").getValue().toString();
                Log.d("matchmaker", "onCreate: " + usertype);
                if (usertype.equals("oraganizer")) {

                    btnMatchOverview.setVisibility(View.VISIBLE);
                    btnViewMatchList.setVisibility(View.VISIBLE);
                } else if (usertype.equals("customer")) {
                    btnMatchOverview.setVisibility(View.INVISIBLE);
                    btnViewMatchList.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        if (usertype.equals("customer")){
//            btnMatchOverview.setVisibility(View.VISIBLE);
//        }

//        btnDeleteMatch = findViewById(R.id.btnDeleteMatch);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("LeagueEntries").child(leagueTitle).child(divisionCategory).child("ApprovedEntries");
        mDatabaseMatch = FirebaseDatabase.getInstance().getReference().child("LeagueEntries").child(leagueTitle).child(divisionCategory).child("EightTeamMatch");
        mDatabaseTournament = FirebaseDatabase.getInstance().getReference().child("LeagueEntries").child(leagueTitle).child(divisionCategory);

//        btnDeleteMatch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDatabaseMatch.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(MatchMakerActivity.this, "Match Deleted", Toast.LENGTH_SHORT).show();
//                        finish();
//                    }
//                });
//            }
//        });


//        if (usertype.equals("organizer")){
//            Log.d("matchmaker", "onCreate: " + usertype);
//            btnMatchOverview.setVisibility(View.VISIBLE);
//        }else if (usertype.equals("customer")){
//            btnMatchOverview.setVisibility(View.INVISIBLE);
//        }
        btnViewMatchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatchMakerActivity.this, MatchListActivity.class);
                intent.putExtra("pushkey", pushkey);
                intent.putExtra("leagueTitle", leagueTitle);
                intent.putExtra("divisionCategory", divisionCategory);
                startActivity(intent);
            }
        });

        btnMatchOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatchMakerActivity.this, OrganizerMatchOverviewActivity.class);
                intent.putExtra("pushkey", pushkey);
                intent.putExtra("leagueTitle", leagueTitle);
                intent.putExtra("divisionCategory", divisionCategory);
                startActivity(intent);
            }
        });

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> member = new ArrayList<String>();

                if (dataSnapshot.exists()) {

                    for (DataSnapshot slotSnapshot : dataSnapshot.getChildren()) {
                        String teamNameList = slotSnapshot.child("teamName").getValue(String.class);
                        member.add(teamNameList);

                        Collections.shuffle(member);

                        for (int i = 0; i < member.size(); i++) {
                            Log.d("teams", "onDataChange: " + member.get(i));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabaseMatch.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    btnSlotA.setText(dataSnapshot.child("0").getValue().toString());
                    btnSlotB.setText(dataSnapshot.child("1").getValue().toString());
                    btnSlotC.setText(dataSnapshot.child("2").getValue().toString());
                    btnSlotD.setText(dataSnapshot.child("3").getValue().toString());
                    btnSlotE.setText(dataSnapshot.child("4").getValue().toString());
                    btnSlotF.setText(dataSnapshot.child("5").getValue().toString());
                    btnSlotG.setText(dataSnapshot.child("6").getValue().toString());
                    btnSlotH.setText(dataSnapshot.child("7").getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabaseTournament.child("MatchList").child("Match1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    btnSlotI.setText(dataSnapshot.child("matchWinner").getValue().toString());
                } else {
                    btnSlotI.setText("TBD");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabaseTournament.child("MatchList").child("Match2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    btnSlotJ.setText(dataSnapshot.child("matchWinner").getValue().toString());
                } else {
                    btnSlotJ.setText("TBD");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabaseTournament.child("MatchList").child("Match3").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    btnSlotK.setText(dataSnapshot.child("matchWinner").getValue().toString());
                } else {
                    btnSlotK.setText("TBD");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabaseTournament.child("MatchList").child("Match4").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    btnSlotL.setText(dataSnapshot.child("matchWinner").getValue().toString());
                } else {
                    btnSlotL.setText("TBD");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabaseTournament.child("MatchList").child("Match5").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    btnSlotM.setText(dataSnapshot.child("matchWinner").getValue().toString());
                } else {
                    btnSlotM.setText("TBD");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabaseTournament.child("MatchList").child("Match6").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    btnSlotN.setText(dataSnapshot.child("matchWinner").getValue().toString());
                } else {
                    btnSlotN.setText("TBD");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabaseTournament.child("MatchList").child("Match7").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    btnSlotO.setText(dataSnapshot.child("matchWinner").getValue().toString());
                } else {
                    btnSlotO.setText("TBD");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void getRandomItem(List<String> member) {

        //int index = random.nextInt(member.size());
        int index = (int) (Math.random() * member.size());
        //Log.d("teams", "getRandomItem: " + member.get(index));
    }
}
