package com.app.league.goldenboys.leagueapp.Organizer.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.app.league.goldenboys.leagueapp.Organizer.Data.DataTeamEntry;
import com.app.league.goldenboys.leagueapp.Organizer.ViewHolder.TeamEntryViewHolder;
import com.app.league.goldenboys.leagueapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class PendingEntryActivity extends AppCompatActivity {

    private RecyclerView mRecyclerview;
    private DatabaseReference mDatabaseRef;
    private LinearLayoutManager linearLayoutManager;

    private String leagueTitle;
    private String divisionCategory;
    private String leagueDivision;
    private String pushkey;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_entry);

        divisionCategory = getIntent().getStringExtra("divisionCategory");
        leagueTitle = getIntent().getStringExtra("leagueTitle");

        mToolbar = findViewById(R.id.toolbar_listPendingEntry);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(leagueTitle + " " + divisionCategory + " Pending Entries");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();

        mRecyclerview = findViewById(R.id.listOfPendingEntries);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        Log.d("league", "onCreate: " + leagueDivision + leagueTitle);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("LeagueEntries").child(leagueTitle).child(divisionCategory).child("PendingEntries");
        mRecyclerview.setLayoutManager(linearLayoutManager);

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

                final String pushkey = getRef(position).getKey();

                viewHolder.tvTeamName.setText(model.getTeamName());

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(PendingEntryActivity.this, DetailTeamEntryActivity.class);
                        intent.putExtra("pushkey", pushkey);
                        intent.putExtra("divisionCategory", divisionCategory);
                        intent.putExtra("leagueTitle", leagueTitle);
                        startActivity(intent);
                    }
                });
            }
        };

        mRecyclerview.setAdapter(firebaseRecyclerAdapter);

    }
}