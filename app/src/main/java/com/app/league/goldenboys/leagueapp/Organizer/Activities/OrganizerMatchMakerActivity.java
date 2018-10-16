package com.app.league.goldenboys.leagueapp.Organizer.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.league.goldenboys.leagueapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class OrganizerMatchMakerActivity extends AppCompatActivity implements View.OnClickListener {

    private String divisionCategory;
    private String leagueTitle;
    private DatabaseReference mDatabaseRef;

    private Button btnCreateEightTeamElimination;
    private Button btnCreateSixTeamDoubleElimination;
    private Button btnCreateSixTeamRoundRobin;

    private Button btnViewEigthTeamSingleEliminationMatch;
    private Button btnViewSixTeamDoublEliminationMatch;
    private Button btnViewSixTeamRoundRobinMatch;
    private TextView tvNumberOfApprovedTeams;

    private long countTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_match_maker);

        divisionCategory = getIntent().getStringExtra("divisionCategory");
        leagueTitle = getIntent().getStringExtra("leagueTitle");

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("LeagueEntries").child(leagueTitle).child(divisionCategory).child("ApprovedEntries");

        btnCreateEightTeamElimination = findViewById(R.id.btnCreateEightTeamSingleElimination);
        btnCreateSixTeamDoubleElimination = findViewById(R.id.btnCreateSixTeamDoubleElimination);
        btnCreateSixTeamRoundRobin = findViewById(R.id.btnCreateSixTeamRoundRobin);
        btnViewEigthTeamSingleEliminationMatch = findViewById(R.id.btnViewEigthTeamSingleEliminationMatch);
        btnViewSixTeamDoublEliminationMatch = findViewById(R.id.btnViewSixTeamDoubleElimination);
        btnViewSixTeamRoundRobinMatch = findViewById(R.id.btnViewSixTeamRoundRobin);

        tvNumberOfApprovedTeams = findViewById(R.id.tvNumberOfApprovedTeams);

        btnViewEigthTeamSingleEliminationMatch.setOnClickListener(this);
        btnViewSixTeamRoundRobinMatch.setOnClickListener(this);
        btnViewSixTeamDoublEliminationMatch.setOnClickListener(this);

        btnCreateEightTeamElimination.setOnClickListener(this);
        btnCreateSixTeamDoubleElimination.setOnClickListener(this);
        btnCreateSixTeamRoundRobin.setOnClickListener(this);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    countTeam = dataSnapshot.getChildrenCount();
                    Log.d("number of Team", "onDataChange: " + countTeam);
                    tvNumberOfApprovedTeams.setText("Number of Approved Team in this Division : " + countTeam);
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
            case R.id.btnCreateEightTeamSingleElimination:
                createSixTeamSingleElimination();
                break;

            case R.id.btnCreateSixTeamDoubleElimination:
                createSixTeamDoubleElimination();
                break;

            case R.id.btnCreateSixTeamRoundRobin:
                createSixTeamRoundRobin();
                break;

            case R.id.btnViewEigthTeamSingleEliminationMatch:
                viewEigthTeamMatch();
                break;

            case R.id.btnViewSixTeamDoubleElimination:

                break;

            case R.id.btnViewSixTeamRoundRobin:
                viewFourTeamRoundRobin();
                break;
        }
    }

    private void viewFourTeamRoundRobin() {
        Intent intent = new Intent(this, FourTeamRoundRobinActivity.class);
        intent.putExtra("leagueTitle", leagueTitle);
        intent.putExtra("divisionCategory", divisionCategory);
        startActivity(intent);
    }

    private void viewEigthTeamMatch() {
        Intent intent = new Intent(this, MatchMakerActivity.class);
        intent.putExtra("leagueTitle", leagueTitle);
        intent.putExtra("divisionCategory", divisionCategory);
        startActivity(intent);
    }

    private void createSixTeamRoundRobin() {
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> listOfTeams = new ArrayList<String>();

                if (dataSnapshot.exists()) {

                    for (DataSnapshot teamsSnapshot : dataSnapshot.getChildren()) {
                        String teamNameList = teamsSnapshot.child("teamName").getValue(String.class);
                        listOfTeams.add(teamNameList);
                        Collections.shuffle(listOfTeams);

                        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("LeagueEntries").child(leagueTitle).child(divisionCategory).child("FourTeamRoundRobin");

                        if (countTeam < 5) {
                            Toast.makeText(OrganizerMatchMakerActivity.this, "Insufficient Team Number", Toast.LENGTH_SHORT).show();
                        } else if (countTeam > 5) {
                            Toast.makeText(OrganizerMatchMakerActivity.this, "Number of Team is greater that 5", Toast.LENGTH_SHORT).show();
                        } else {
                            database.setValue(listOfTeams).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(OrganizerMatchMakerActivity.this, "Match Created", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void createSixTeamDoubleElimination() {

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> listOfTeams = new ArrayList<String>();

                if (dataSnapshot.exists()) {

                    for (DataSnapshot teamsSnapshot : dataSnapshot.getChildren()) {
                        String teamNameList = teamsSnapshot.child("teamName").getValue(String.class);
                        listOfTeams.add(teamNameList);
                        Collections.shuffle(listOfTeams);

                        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("LeagueEntries").child(leagueTitle).child(divisionCategory).child("SixTeamDoubleElimination");

                        if (countTeam < 6) {
                            Toast.makeText(OrganizerMatchMakerActivity.this, "Insufficient Team Number", Toast.LENGTH_SHORT).show();
                        } else if (countTeam > 6) {
                            Toast.makeText(OrganizerMatchMakerActivity.this, "Number of Team is greater that 6", Toast.LENGTH_SHORT).show();
                        } else {
                            database.setValue(listOfTeams).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(OrganizerMatchMakerActivity.this, "Match Created", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void createSixTeamSingleElimination() {

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> listOfTeams = new ArrayList<String>();

                if (dataSnapshot.exists()) {

                    for (DataSnapshot teamsSnapshot : dataSnapshot.getChildren()) {
                        String teamNameList = teamsSnapshot.child("teamName").getValue(String.class);
                        listOfTeams.add(teamNameList);
                        Collections.shuffle(listOfTeams);

                        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("LeagueEntries").child(leagueTitle).child(divisionCategory).child("EightTeamMatch");

                        if (countTeam < 8) {
                            Toast.makeText(OrganizerMatchMakerActivity.this, "Insufficient Team Number", Toast.LENGTH_SHORT).show();
                        } else if (countTeam > 8) {
                            Toast.makeText(OrganizerMatchMakerActivity.this, "Number of Team is greater that 8", Toast.LENGTH_SHORT).show();
                        } else {
                            database.setValue(listOfTeams).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(OrganizerMatchMakerActivity.this, "Match Created", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
