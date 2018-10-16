package com.app.league.goldenboys.leagueapp.Organizer.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.league.goldenboys.leagueapp.Organizer.Activities.CreateLeagueActivity;
import com.app.league.goldenboys.leagueapp.Organizer.Activities.LeagueDetailActivity;
import com.app.league.goldenboys.leagueapp.Organizer.Data.DataLeague;
import com.app.league.goldenboys.leagueapp.Organizer.ViewHolder.LeagueViewHolder;
import com.app.league.goldenboys.leagueapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeaguesFragment extends Fragment {

    private FloatingActionButton fbCreateLeague;

    private RecyclerView mRecyclerview;
    private DatabaseReference mDatabaseRef;
    private LinearLayoutManager linearLayoutManager;

    public LeaguesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leagues, container, false);

        fbCreateLeague = view.findViewById(R.id.fbCreateLeague);

        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();

        mRecyclerview = view.findViewById(R.id.recyclerViewOrganizerLeagueList);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("League");

        mRecyclerview.setLayoutManager(linearLayoutManager);

        fbCreateLeague.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CreateLeagueActivity.class));
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<DataLeague, ListOfLeaguesViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<DataLeague, ListOfLeaguesViewHolder>(

                DataLeague.class,
                R.layout.cardvew_league,
                ListOfLeaguesViewHolder.class,
                mDatabaseRef.orderByChild("count")

        ) {
            @Override
            protected void populateViewHolder(ListOfLeaguesViewHolder viewHolder, DataLeague model, int position) {

                viewHolder.setLeagueTitle(model.getLeagueTitle());
                viewHolder.setLeagueDesc(model.getLeagueDesc());
                viewHolder.setImageLeague(model.getImageLeague(), getContext());

                final String pushkey = getRef(position).getKey();
                final String leagueTitle = model.getLeagueTitle();

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), LeagueDetailActivity.class);
                        intent.putExtra("pushkey", pushkey);
                        intent.putExtra("leagueTitle", leagueTitle);
                        startActivity(intent);
                    }
                });
            }
        };
        mRecyclerview.setAdapter(firebaseRecyclerAdapter);

    }

    public static class ListOfLeaguesViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public ListOfLeaguesViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setLeagueTitle(String leagueTitle) {
            TextView title = mView.findViewById(R.id.tvLeagueTitle);
            title.setText(leagueTitle);
        }

        public void setLeagueDesc(String leagueDesc) {
            TextView desc = mView.findViewById(R.id.tvLeagueDescription);
            desc.setText(leagueDesc);
        }

        public void setImageLeague(String imageLeague, Context context) {
            ImageView imgLeague = mView.findViewById(R.id.imgListLeague);
            Picasso.with(context).load(imageLeague).placeholder(R.drawable.img_default).into(imgLeague);
        }
    }
}
