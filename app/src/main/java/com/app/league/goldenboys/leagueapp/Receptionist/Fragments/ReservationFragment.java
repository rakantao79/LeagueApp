package com.app.league.goldenboys.leagueapp.Receptionist.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.league.goldenboys.leagueapp.R;
import com.app.league.goldenboys.leagueapp.Receptionist.Activity.AddReservationActivity;
import com.app.league.goldenboys.leagueapp.Receptionist.Data.DataReservation;
import com.app.league.goldenboys.leagueapp.Receptionist.ViewHolder.ReservationViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationFragment extends Fragment {

    private FloatingActionButton fbAddReservation;
    private RecyclerView mRecyclerview;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference mDatabaseUser;
    private LinearLayoutManager linearLayoutManager;
    private String Usertype;

    public ReservationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reservation, container, false);

        FirebaseUser mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();

        fbAddReservation = view.findViewById(R.id.fbAddReservation);
        mRecyclerview = view.findViewById(R.id.recyclerReservedList);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);

        mDatabaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Usertype = dataSnapshot.child("Usertype").getValue().toString();

                    if (Usertype.equals("receptionist")) {
                        fbAddReservation.setVisibility(View.VISIBLE);
                    } else if (Usertype.equals("admin")) {
                        fbAddReservation.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        fbAddReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddReservationActivity.class));
            }
        });

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Reservation");

        mRecyclerview.setLayoutManager(linearLayoutManager);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<DataReservation, ReservationViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<DataReservation, ReservationViewHolder>(

                DataReservation.class,
                R.layout.cardview_reservation,
                ReservationViewHolder.class,
                mDatabaseRef

        ) {
            @Override
            protected void populateViewHolder(ReservationViewHolder viewHolder, DataReservation model, int position) {
                viewHolder.tvReserveName.setText("Name : " + model.getReserveName());
                viewHolder.tvReserveTime.setText("Time : " + model.getReserveTimeStart() + " - " + model.getReserveTimeEnd());
                viewHolder.tvReserveDate.setText("Date : " + model.getReserveDate());
                viewHolder.tvReserveBookDate.setText(model.getReserveTransdate());
            }
        };
        mRecyclerview.setAdapter(firebaseRecyclerAdapter);

    }
}
