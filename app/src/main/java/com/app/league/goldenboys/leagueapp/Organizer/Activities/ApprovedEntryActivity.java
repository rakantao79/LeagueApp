package com.app.league.goldenboys.leagueapp.Organizer.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.app.league.goldenboys.leagueapp.Organizer.Data.DataTeamEntry;
import com.app.league.goldenboys.leagueapp.Organizer.ViewHolder.TeamEntryViewHolder;
import com.app.league.goldenboys.leagueapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ApprovedEntryActivity extends AppCompatActivity {

    private RecyclerView mRecyclerview;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference mDatabaseUser;
    private LinearLayoutManager linearLayoutManager;

    private String userType;

    private String leagueTitle;
    private String leagueDivision;
    private String pushkey;
    private String divisionCategory;
    private Button btnCallMatchMaker;

    private FloatingActionButton fbSubmitEntry;
    private FloatingActionButton fbViewMatch;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved_entry);

        pushkey = getIntent().getExtras().getString("pushkey");
        divisionCategory = getIntent().getStringExtra("divisionCategory");
        leagueTitle = getIntent().getStringExtra("leagueTitle");

        mToolbar = findViewById(R.id.toolbar_listApprovedEntry);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(leagueTitle + " " + divisionCategory + " Approved Entries");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

        mDatabaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userType = dataSnapshot.child("Usertype").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        btnCallMatchMaker = findViewById(R.id.btnCallMatchMaker);
        fbSubmitEntry = findViewById(R.id.fbSubmitEntry);
        fbViewMatch = findViewById(R.id.fbViewMatch);

        mRecyclerview = findViewById(R.id.listOfApprovedEntries);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("LeagueEntries").child(leagueTitle).child(divisionCategory).child("ApprovedEntries");

        Log.d("league", "onCreate: " + divisionCategory + " " + leagueTitle);

        if (divisionCategory.equals("JuniorDivision")){

            mDatabaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        long numberOfEntries = dataSnapshot.getChildrenCount();

                        Log.d("numberOfEntries", "onDataChange: " + numberOfEntries);

                        if (numberOfEntries > 4) {
                            fbSubmitEntry.setEnabled(false);
                            fbSubmitEntry.setVisibility(View.GONE);

                        }

                        fbViewMatch.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(ApprovedEntryActivity.this, FourTeamRoundRobinActivity.class);
                                intent.putExtra("leagueTitle", leagueTitle);
                                intent.putExtra("divisionCategory", divisionCategory);
                                startActivity(intent);
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        } else if (divisionCategory.equals("SeniorDivision")){

            mDatabaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        long numberOfEntries = dataSnapshot.getChildrenCount();

                        Log.d("numberOfEntries", "onDataChange: " + numberOfEntries);

                        if (numberOfEntries > 7) {
                            fbSubmitEntry.setEnabled(false);
                            fbSubmitEntry.setVisibility(View.GONE);
                        }

                        fbViewMatch.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(ApprovedEntryActivity.this, MatchMakerActivity.class);
                                intent.putExtra("leagueTitle", leagueTitle);
                                intent.putExtra("divisionCategory", divisionCategory);
                                startActivity(intent);
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        mRecyclerview.setLayoutManager(linearLayoutManager);

//        btnCallMatchMaker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                submitArraylist();
//                Intent intent = new Intent(ApprovedEntryActivity.this, MatchMakerActivity.class);
//                intent.putExtra("divisionCategory", divisionCategory);
//                intent.putExtra("leagueTitle", leagueTitle);
//                startActivity(intent);
//                finish();
//            }
//        });

        fbSubmitEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ApprovedEntryActivity.this, SubmitEntryActivity.class);
                intent.putExtra("leagueTitle", leagueTitle);
                intent.putExtra("divisionCategory", divisionCategory);
                startActivity(intent);
            }
        });

//        fbViewMatch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ApprovedEntryActivity.this, MatchMakerActivity.class);
//                intent.putExtra("leagueTitle", leagueTitle);
//                intent.putExtra("divisionCategory", divisionCategory);
//                startActivity(intent);
//            }
//        });
    }

    private void submitArraylist() {
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> member = new ArrayList<String>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot teamsSnapshot : dataSnapshot.getChildren()) {
                        String teamNameList = teamsSnapshot.child("teamName").getValue(String.class);
                        member.add(teamNameList);
                        Collections.shuffle(member);

                        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("LeagueEntries").child(leagueTitle).child(divisionCategory).child("LeagueMatch");

                        database.setValue(member).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(ApprovedEntryActivity.this, "Match Made", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_approved_entry, menu);

        //MenuItem menuAdminAddOrganizer = menu.findItem(R.id.admin_menu_register_organizer);
        MenuItem menuOrganizer = menu.findItem(R.id.menu_view_organizer_module);

        menuOrganizer.setVisible(false);

        if (userType.equals("organizer")) {
            menuOrganizer.setVisible(true);
        } else if (userType.equals("customer")){
            menuOrganizer.setVisible(false);
        }

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_view_organizer_module) {

            //startActivity(new Intent(getApplicationContext(), OraganizerModulesActivity.class));

//            Intent intent = new Intent(this, OraganizerModulesActivity.class);
//            intent.putExtra("leagueTitle", leagueTitle);
//            intent.putExtra("divisionCategory", divisionCategory);
//            startActivity(intent);

            Intent intent = new Intent(this, OrganizerMatchMakerActivity.class);
            intent.putExtra("leagueTitle", leagueTitle);
            intent.putExtra("divisionCategory", divisionCategory);
            startActivity(intent);

            return true;
        } else if (id == R.id.menu_view_pending_entries) {
            Intent intent = new Intent(this, PendingEntryActivity.class);
            intent.putExtra("leagueTitle", leagueTitle);
            intent.putExtra("divisionCategory", divisionCategory);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<DataTeamEntry, TeamEntryViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<DataTeamEntry, TeamEntryViewHolder>(

                DataTeamEntry.class,
                R.layout.cardview_team_entry,
                TeamEntryViewHolder.class,
                mDatabaseRef

        ) {
            @Override
            protected void populateViewHolder(TeamEntryViewHolder viewHolder, DataTeamEntry model, int position) {

                viewHolder.tvTeamName.setText(model.getTeamName());

            }
        };
        mRecyclerview.setAdapter(firebaseRecyclerAdapter);
    }
}
