package com.app.league.goldenboys.leagueapp.Organizer.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FourTeamRoundRobinActivity extends AppCompatActivity {

    private String leagueTitle;
    private String pushkey;
    private String divisionCategory;

    private TextView tvRoundRobinTeamAName;
    private TextView tvRoundRobinTeamBName;
    private TextView tvRoundRobinTeamCName;
    private TextView tvRoundRobinTeamDName;
    private TextView tvRoundRobinTeamEName;
    private TextView tvRoundRobinTeamAWinNumber;
    private TextView tvRoundRobinTeamBWinNumber;
    private TextView tvRoundRobinTeamCWinNumber;
    private TextView tvRoundRobinTeamDWinNumber;
    private TextView tvRoundRobinTeamEWinNumber;
    private TextView tvRoundRobinTeamALostNumber;
    private TextView tvRoundRobinTeamBLostNumber;
    private TextView tvRoundRobinTeamCLostNumber;
    private TextView tvRoundRobinTeamDLostNumber;
    private TextView tvRoundRobinTeamELostNumber;
    private TextView tvRoundRobinMatch1;
    private TextView tvRoundRobinMatch2;
    private TextView tvRoundRobinMatch3;
    private TextView tvRoundRobinMatch4;
    private TextView tvRoundRobinMatch5;
    private TextView tvRoundRobinMatch6;
    private TextView tvRoundRobinMatch7;
    private TextView tvRoundRobinMatch8;
    private TextView tvRoundRobinMatch9;
    private TextView tvRoundRobinMatch10;

    private TextView tvRoundRobinRound1Bye;
    private TextView tvRoundRobinRound2Bye;
    private TextView tvRoundRobinRound3Bye;
    private TextView tvRoundRobinRound4Bye;
    private TextView tvRoundRobinRound5Bye;

    private Button btnRoundRobinSetSchedule;
    private Button btnRoundRobinViewList;
    private Button btnRoundRobinInitiateStats;
    private Button btnRoundRobinStartPlayoffs;

    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseMatch;
    private DatabaseReference mDatabaseUser;
    private DatabaseReference mDatabaseTournament;
    private DatabaseReference mDatabaseRoundRobinStats;
    private DatabaseReference mDatabaseMatchDetails;

    private TextView tvRBFirst;
    private TextView tvRBSecond;
    private TextView tvRBThird;
    private TextView tvRBFourth;

    int numberOfWinA;
    int numberOfWinB;
    int numberOfWinC;
    int numberOfWinD;
    int numberOfWinE;

    String teamAWinNumber;
    String teamBWinNumber;
    String teamCWinNumber;
    String teamDWinNumber;
    String teamEWinNumber;

    String teamALossNumber;
    String teamBLossNumber;
    String teamCLossNumber;
    String teamDLossNumber;
    String teamELossNumber;

    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four_team_round_robin);

        pushkey = getIntent().getExtras().getString("pushkey");
        divisionCategory = getIntent().getStringExtra("divisionCategory");
        leagueTitle = getIntent().getStringExtra("leagueTitle");

        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

        tvRoundRobinTeamAName = findViewById(R.id.tvRoundRobinTeamAName);
        tvRoundRobinTeamBName = findViewById(R.id.tvRoundRobinTeamBName);
        tvRoundRobinTeamCName = findViewById(R.id.tvRoundRobinTeamCName);
        tvRoundRobinTeamDName = findViewById(R.id.tvRoundRobinTeamDName);
        tvRoundRobinTeamEName = findViewById(R.id.tvRoundRobinTeamEName);
        tvRoundRobinTeamAWinNumber = findViewById(R.id.tvRoundRobinTeamAWinNumber);
        tvRoundRobinTeamBWinNumber = findViewById(R.id.tvRoundRobinTeamBWinNumber);
        tvRoundRobinTeamCWinNumber = findViewById(R.id.tvRoundRobinTeamCWinNumber);
        tvRoundRobinTeamDWinNumber = findViewById(R.id.tvRoundRobinTeamDWinNumber);
        tvRoundRobinTeamEWinNumber = findViewById(R.id.tvRoundRobinTeamEWinNumber);
        tvRoundRobinTeamALostNumber = findViewById(R.id.tvRoundRobinTeamALostNumber);
        tvRoundRobinTeamBLostNumber = findViewById(R.id.tvRoundRobinTeamBLostNumber);
        tvRoundRobinTeamCLostNumber = findViewById(R.id.tvRoundRobinTeamCLostNumber);
        tvRoundRobinTeamDLostNumber = findViewById(R.id.tvRoundRobinTeamDLostNumber);
        tvRoundRobinTeamELostNumber = findViewById(R.id.tvRoundRobinTeamELostNumber);
        tvRoundRobinMatch1 = findViewById(R.id.tvRoundRobinMatch1);
        tvRoundRobinMatch2 = findViewById(R.id.tvRoundRobinMatch2);
        tvRoundRobinMatch3 = findViewById(R.id.tvRoundRobinMatch3);
        tvRoundRobinMatch4 = findViewById(R.id.tvRoundRobinMatch4);
        tvRoundRobinMatch5 = findViewById(R.id.tvRoundRobinMatch5);
        tvRoundRobinMatch6 = findViewById(R.id.tvRoundRobinMatch6);
        tvRoundRobinMatch7 = findViewById(R.id.tvRoundRobinMatch7);
        tvRoundRobinMatch8 = findViewById(R.id.tvRoundRobinMatch8);
        tvRoundRobinMatch9 = findViewById(R.id.tvRoundRobinMatch9);
        tvRoundRobinMatch10 = findViewById(R.id.tvRoundRobinMatch10);

        tvRoundRobinRound1Bye = findViewById(R.id.tvRoundRobinRound1Bye);
        tvRoundRobinRound2Bye = findViewById(R.id.tvRoundRobinRound2Bye);
        tvRoundRobinRound3Bye = findViewById(R.id.tvRoundRobinRound3Bye);
        tvRoundRobinRound4Bye = findViewById(R.id.tvRoundRobinRound4Bye);
        tvRoundRobinRound5Bye = findViewById(R.id.tvRoundRobinRound5Bye);

        tvRBFirst = findViewById(R.id.tvRBFirst);
        tvRBSecond = findViewById(R.id.tvRBSecond);
        tvRBThird = findViewById(R.id.tvRBThird);
        tvRBFourth = findViewById(R.id.tvRBFourth);

        btnRoundRobinSetSchedule = findViewById(R.id.btnRoundRobinSetSchedule);
        btnRoundRobinViewList = findViewById(R.id.btnRoundRobinViewListMatch);
        btnRoundRobinInitiateStats = findViewById(R.id.btnRoundRobinInitiateStats);
        btnRoundRobinStartPlayoffs = findViewById(R.id.btnRoundRobinStartPlayoffs);

        numberOfWinA = Integer.parseInt(tvRoundRobinTeamAWinNumber.getText().toString());
        numberOfWinB = Integer.parseInt(tvRoundRobinTeamBWinNumber.getText().toString());
        numberOfWinC = Integer.parseInt(tvRoundRobinTeamCWinNumber.getText().toString());
        numberOfWinD = Integer.parseInt(tvRoundRobinTeamDWinNumber.getText().toString());
        numberOfWinE = Integer.parseInt(tvRoundRobinTeamEWinNumber.getText().toString());

        Log.d("wina", "onCreate: " + numberOfWinA);

        mDatabaseUser.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userType = dataSnapshot.child("Usertype").getValue().toString();
                if (userType.equals("organizer")) {
                    btnRoundRobinSetSchedule.setVisibility(View.VISIBLE);
                    btnRoundRobinViewList.setVisibility(View.VISIBLE);
                    btnRoundRobinStartPlayoffs.setVisibility(View.VISIBLE);
                } else if (userType.equals("customer")){
                    btnRoundRobinSetSchedule.setVisibility(View.GONE);
                    btnRoundRobinViewList.setVisibility(View.GONE);
                    btnRoundRobinStartPlayoffs.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mDatabase = FirebaseDatabase.getInstance().getReference().child("LeagueEntries").child(leagueTitle).child(divisionCategory).child("ApprovedEntries");
        mDatabaseMatch = FirebaseDatabase.getInstance().getReference().child("LeagueEntries").child(leagueTitle).child(divisionCategory).child("FourTeamRoundRobin");
        mDatabaseTournament = FirebaseDatabase.getInstance().getReference().child("LeagueEntries").child(leagueTitle).child(divisionCategory);
        mDatabaseRoundRobinStats = FirebaseDatabase.getInstance().getReference().child("LeagueEntries").child(leagueTitle).child(divisionCategory).child("RoundRobinStats");
        mDatabaseMatchDetails = FirebaseDatabase.getInstance().getReference().child("LeagueEntries").child(leagueTitle).child(divisionCategory).child("RoundRobinMatchList");

        mDatabaseMatch.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                    String team1 = dataSnapshot.child("0").getValue().toString();
                    String team2 = dataSnapshot.child("1").getValue().toString();
                    String team3 = dataSnapshot.child("2").getValue().toString();
                    String team4 = dataSnapshot.child("3").getValue().toString();
                    String team5 = dataSnapshot.child("4").getValue().toString();

                    tvRoundRobinTeamAName.setText(team1);
                    tvRoundRobinTeamBName.setText(team2);
                    tvRoundRobinTeamCName.setText(team3);
                    tvRoundRobinTeamDName.setText(team4);
                    tvRoundRobinTeamEName.setText(team5);

                    tvRoundRobinRound1Bye.setText(team5 + " - bye");
                    tvRoundRobinRound2Bye.setText(team2 + " - bye");
                    tvRoundRobinRound3Bye.setText(team4 + " - bye");
                    tvRoundRobinRound4Bye.setText(team1 + " - bye");
                    tvRoundRobinRound5Bye.setText(team3 + " - bye");

                    tvRoundRobinMatch1.setText(team1 + " VS " + team4);
                    tvRoundRobinMatch2.setText(team2 + " VS " + team3);
                    tvRoundRobinMatch3.setText(team3 + " VS " + team1);
                    tvRoundRobinMatch4.setText(team4 + " VS " + team5);
                    tvRoundRobinMatch5.setText(team5 + " VS " + team3);
                    tvRoundRobinMatch6.setText(team1 + " VS " + team2);
                    tvRoundRobinMatch7.setText(team2 + " VS " + team5);
                    tvRoundRobinMatch8.setText(team3 + " VS " + team4);
                    tvRoundRobinMatch9.setText(team4 + " VS " + team2);
                    tvRoundRobinMatch10.setText(team5 + " VS " + team1);

                    Log.d("winnumber", team1 + " : " + teamAWinNumber);
                    Log.d("winnumber", team2 + " : " + teamBWinNumber);
                    Log.d("winnumber", team3 + " : " + teamCWinNumber);
                    Log.d("winnumber", team4 + " : " + teamDWinNumber);
                    Log.d("winnumber", team5 + " : " + teamEWinNumber);

                    mDatabaseRoundRobinStats.child(team1).child("Win").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){

                                teamAWinNumber = String.valueOf(dataSnapshot.getChildrenCount());

                                tvRoundRobinTeamAWinNumber.setText(teamAWinNumber);
                                sendData(teamAWinNumber);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    mDatabaseRoundRobinStats.child(team1).child("Loss").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){

                                teamALossNumber = String.valueOf(dataSnapshot.getChildrenCount());

                                tvRoundRobinTeamALostNumber.setText(teamALossNumber);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    mDatabaseRoundRobinStats.child(team2).child("Win").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){

                                teamBWinNumber = String.valueOf(dataSnapshot.getChildrenCount());

                                tvRoundRobinTeamBWinNumber.setText(teamBWinNumber);
                                //sendData(teamBWinNumber);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    mDatabaseRoundRobinStats.child(team2).child("Loss").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){

                                teamBLossNumber = String.valueOf(dataSnapshot.getChildrenCount());

                                tvRoundRobinTeamBLostNumber.setText(teamBLossNumber);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    mDatabaseRoundRobinStats.child(team3).child("Win").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){

                                teamCWinNumber = String.valueOf(dataSnapshot.getChildrenCount());

                                tvRoundRobinTeamCWinNumber.setText(teamCWinNumber);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    mDatabaseRoundRobinStats.child(team3).child("Loss").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){

                                teamCLossNumber = String.valueOf(dataSnapshot.getChildrenCount());

                                tvRoundRobinTeamCLostNumber.setText(teamCLossNumber);
                                //sendData(teamCLossNumber);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    mDatabaseRoundRobinStats.child(team4).child("Win").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){

                                teamDWinNumber= String.valueOf(dataSnapshot.getChildrenCount());

                                tvRoundRobinTeamDWinNumber.setText(teamDWinNumber);
                                //sendData(teamDWinNumber);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    mDatabaseRoundRobinStats.child(team4).child("Loss").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){

                                teamDLossNumber = String.valueOf(dataSnapshot.getChildrenCount());

                                tvRoundRobinTeamDLostNumber.setText(teamDLossNumber);
                                //sendData(teamDLossNumber);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    mDatabaseRoundRobinStats.child(team5).child("Win").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){

                                teamEWinNumber = String.valueOf(dataSnapshot.getChildrenCount());

                                tvRoundRobinTeamEWinNumber.setText(teamEWinNumber);
                                //sendData(teamEWinNumber);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    mDatabaseRoundRobinStats.child(team5).child("Loss").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){

                                teamELossNumber = String.valueOf(dataSnapshot.getChildrenCount());

                                tvRoundRobinTeamELostNumber.setText(teamELossNumber);
                                //sendData(teamELossNumber);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnRoundRobinSetSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FourTeamRoundRobinActivity.this, RoundRobinScheduleActivity.class);
                intent.putExtra("leagueTitle", leagueTitle);
                intent.putExtra("divisionCategory", divisionCategory);
                startActivity(intent);
            }
        });

        btnRoundRobinViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FourTeamRoundRobinActivity.this, RoundRobinMatchListActivity.class);
                intent.putExtra("leagueTitle", leagueTitle);
                intent.putExtra("divisionCategory", divisionCategory);
                startActivity(intent);
            }
        });

        btnRoundRobinInitiateStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseRoundRobinStats.child(tvRoundRobinTeamAName.getText().toString()).child("Win").setValue(0);
                mDatabaseRoundRobinStats.child(tvRoundRobinTeamAName.getText().toString()).child("Loss").setValue(0);

                mDatabaseRoundRobinStats.child(tvRoundRobinTeamBName.getText().toString()).child("Win").setValue(0);
                mDatabaseRoundRobinStats.child(tvRoundRobinTeamBName.getText().toString()).child("Loss").setValue(0);

                mDatabaseRoundRobinStats.child(tvRoundRobinTeamCName.getText().toString()).child("Win").setValue(0);
                mDatabaseRoundRobinStats.child(tvRoundRobinTeamCName.getText().toString()).child("Loss").setValue(0);

                mDatabaseRoundRobinStats.child(tvRoundRobinTeamDName.getText().toString()).child("Win").setValue(0);
                mDatabaseRoundRobinStats.child(tvRoundRobinTeamDName.getText().toString()).child("Loss").setValue(0);

                mDatabaseRoundRobinStats.child(tvRoundRobinTeamEName.getText().toString()).child("Win").setValue(0);
                mDatabaseRoundRobinStats.child(tvRoundRobinTeamEName.getText().toString()).child("Loss").setValue(0).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(FourTeamRoundRobinActivity.this, "Stats Initiated", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnRoundRobinStartPlayoffs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first = tvRBFirst.getText().toString();
                String second = tvRBSecond.getText().toString();
                String third = tvRBThird.getText().toString();
                String fourth = tvRBFourth.getText().toString();

                mDatabaseMatchDetails.child("Match K").child("matchOpponentA").setValue(first);
                mDatabaseMatchDetails.child("Match K").child("matchOpponentB").setValue(fourth);
                mDatabaseMatchDetails.child("Match L").child("matchOpponentA").setValue(second);
                mDatabaseMatchDetails.child("Match L").child("matchOpponentB").setValue(third).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(FourTeamRoundRobinActivity.this, "Semi finals updated", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        mDatabaseRoundRobinStats.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    btnRoundRobinInitiateStats.setVisibility(View.INVISIBLE);
                } else {
                    btnRoundRobinInitiateStats.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void sendData(String teamAWinNumber) {

        teamAWinNumber = this.teamAWinNumber;
        teamBWinNumber = this.teamBWinNumber;
        teamCWinNumber = this.teamCWinNumber;
        teamDWinNumber = this.teamDWinNumber;
        teamEWinNumber = this.teamEWinNumber;
        teamALossNumber = this.teamALossNumber;
        teamBLossNumber = this.teamBLossNumber;
        teamCLossNumber = this.teamCLossNumber;
        teamDLossNumber = this.teamDLossNumber;
        teamELossNumber = this.teamELossNumber;


        final String finalTeamAWinNumber = teamAWinNumber;
        mDatabaseMatch.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    String team1 = dataSnapshot.child("0").getValue().toString();
                    String team2 = dataSnapshot.child("1").getValue().toString();
                    String team3 = dataSnapshot.child("2").getValue().toString();
                    String team4 = dataSnapshot.child("3").getValue().toString();
                    String team5 = dataSnapshot.child("4").getValue().toString();

                    Log.d("winnumber", team1 + " : " + finalTeamAWinNumber);
                    Log.d("winnumber", team2 + " : " + teamBWinNumber);
                    Log.d("winnumber", team3 + " : " + teamCWinNumber);
                    Log.d("winnumber", team4 + " : " + teamDWinNumber);
                    Log.d("winnumber", team5 + " : " + teamEWinNumber);

                    HashMap<String, Integer> map = new HashMap<String, Integer>();
                    map.put(team1, Integer.valueOf(finalTeamAWinNumber));
                    map.put(team2, Integer.valueOf(teamBWinNumber));
                    map.put(team3, Integer.valueOf(teamCWinNumber));
                    map.put(team4, Integer.valueOf(teamDWinNumber));
                    map.put(team5, Integer.valueOf(teamEWinNumber));

                    Object[] a = map.entrySet().toArray();
                    Arrays.sort(a, new Comparator() {
                        public int compare(Object o1, Object o2) {
                            return ((Map.Entry<String, Integer>) o2).getValue()
                                    .compareTo(((Map.Entry<String, Integer>) o1).getValue());
                        }
                    });

                    for (Object e : a) {
                        System.out.println(((Map.Entry<String, Integer>) e).getKey() + " : "
                                + ((Map.Entry<String, Integer>) e).getValue() + "\n\n");
                    }

                    String first = a[0].toString();
                    String second = a[1].toString();
                    String third = a[2].toString();
                    String fourth = a[3].toString();

                    tvRBFirst.setText(first.substring(0, first.length() -2));
                    tvRBSecond.setText(second.substring(0, second.length() -2));
                    tvRBThird.setText(third.substring(0, third.length() -2));
                    tvRBFourth.setText(fourth.substring(0, fourth.length() -2));

                } else {

                    tvRBFirst.setText("");
                    tvRBSecond.setText("");
                    tvRBThird.setText("");
                    tvRBFourth.setText("");
                    

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.d("sendData", "sendData: " + teamEWinNumber);
    }
}
