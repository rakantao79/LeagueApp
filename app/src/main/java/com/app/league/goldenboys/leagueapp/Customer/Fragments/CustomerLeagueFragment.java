package com.app.league.goldenboys.leagueapp.Customer.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.league.goldenboys.leagueapp.Organizer.Activities.LeagueDetailActivity;
import com.app.league.goldenboys.leagueapp.Organizer.Data.DataLeague;
import com.app.league.goldenboys.leagueapp.Organizer.Fragments.LeaguesFragment;
import com.app.league.goldenboys.leagueapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerLeagueFragment extends Fragment {

    private RecyclerView mRecyclerview;
    private DatabaseReference mDatabaseRef;
    private LinearLayoutManager linearLayoutManager;

    public CustomerLeagueFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_league, container, false);

        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();

        mRecyclerview = view.findViewById(R.id.recyclerViewCustomerLeagueList);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("League");
        mRecyclerview.setLayoutManager(linearLayoutManager);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<DataLeague, LeaguesFragment.ListOfLeaguesViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<DataLeague, LeaguesFragment.ListOfLeaguesViewHolder>(


                DataLeague.class,
                R.layout.cardvew_league,
                LeaguesFragment.ListOfLeaguesViewHolder.class,
                mDatabaseRef.orderByChild("count")

        ) {
            @Override
            protected void populateViewHolder(LeaguesFragment.ListOfLeaguesViewHolder viewHolder, DataLeague model, int position) {

                viewHolder.setLeagueTitle(model.getLeagueTitle());
                viewHolder.setLeagueDesc(model.getLeagueDesc());
                viewHolder.setImageLeague(model.getImageLeague(), getContext());

                final String pushkey = getRef(position).getKey();

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), LeagueDetailActivity.class);
                        intent.putExtra("pushkey", pushkey);
                        startActivity(intent);
                    }
                });
            }
        };
        mRecyclerview.setAdapter(firebaseRecyclerAdapter);

    }
}
