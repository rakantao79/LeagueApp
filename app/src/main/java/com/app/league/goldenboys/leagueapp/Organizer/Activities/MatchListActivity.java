package com.app.league.goldenboys.leagueapp.Organizer.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.app.league.goldenboys.leagueapp.Organizer.Data.DataMatchList;
import com.app.league.goldenboys.leagueapp.Organizer.ViewHolder.MatchListViewholder;
import com.app.league.goldenboys.leagueapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MatchListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerview;
    private DatabaseReference mDatabaseRef;
    private LinearLayoutManager linearLayoutManager;

    private String leagueTitle;
    private String pushkey;
    private String divisionCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_list);

        divisionCategory = getIntent().getStringExtra("divisionCategory");
        leagueTitle = getIntent().getStringExtra("leagueTitle");

        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();

        mRecyclerview = findViewById(R.id.recyclerView_MatchList);
        linearLayoutManager = new LinearLayoutManager(this);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("LeagueEntries").child(leagueTitle).child(divisionCategory).child("MatchList");

        mRecyclerview.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<DataMatchList, MatchListViewholder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<DataMatchList, MatchListViewholder>(

                DataMatchList.class,
                R.layout.cardview_matchlist,
                MatchListViewholder.class,
                mDatabaseRef

        ) {
            @Override
            protected void populateViewHolder(MatchListViewholder viewHolder, DataMatchList model, int position) {

                final String getKey = getRef(position).getKey();

                viewHolder.tvMatchListName.setText(model.getMatchName());
                viewHolder.tvMatchListTeams.setText(model.getMatchOpponentA() + " VS " + model.getMatchOpponentB());
                viewHolder.tvMatchListSchedule.setText("Schedule: " + model.getMatchSchedule());

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MatchListActivity.this, MatchListDetailedViewActivity.class);
                        intent.putExtra("leagueTitle", leagueTitle);
                        intent.putExtra("divisionCategory", divisionCategory);
                        intent.putExtra("getkey", getKey);
                        startActivity(intent);
                    }
                });

            }
        };
        mRecyclerview.setAdapter(firebaseRecyclerAdapter);
    }
}
