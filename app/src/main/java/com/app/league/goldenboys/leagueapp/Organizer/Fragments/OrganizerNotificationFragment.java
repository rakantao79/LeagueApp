package com.app.league.goldenboys.leagueapp.Organizer.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.league.goldenboys.leagueapp.Customer.Activities.UserNotificationDetailActivity;
import com.app.league.goldenboys.leagueapp.Customer.Data.DataUserNotificationFinal;
import com.app.league.goldenboys.leagueapp.Customer.ViewHolder.UserNotificationViewHolder;
import com.app.league.goldenboys.leagueapp.Organizer.Activities.OrganizerNotificationDetailedActivity;
import com.app.league.goldenboys.leagueapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrganizerNotificationFragment extends Fragment {

    private RecyclerView mRecyclerview;
    private DatabaseReference mDatabaseRef;
    private LinearLayoutManager linearLayoutManager;

    public OrganizerNotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_organizer_notification, container, false);

        mRecyclerview = view.findViewById(R.id.recyclerview_OrganizerNotification);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("OrganizerNotification");

        mRecyclerview.setLayoutManager(linearLayoutManager);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<DataUserNotificationFinal, UserNotificationViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<DataUserNotificationFinal, UserNotificationViewHolder>(

                DataUserNotificationFinal.class,
                R.layout.cardview_user_notification,
                UserNotificationViewHolder.class,
                mDatabaseRef

        ) {
            @Override
            protected void populateViewHolder(UserNotificationViewHolder viewHolder, DataUserNotificationFinal model, int position) {

                final String getKey = getRef(position).getKey();

                viewHolder.tvNotifTitle.setText(model.getNotifTitle());
                viewHolder.tvNotifDate.setText(model.getNotfiDate());

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getActivity(), OrganizerNotificationDetailedActivity.class);
                        intent.putExtra("getkey", getKey);
                        startActivity(intent);
                    }
                });
            }
        };
        mRecyclerview.setAdapter(firebaseRecyclerAdapter);
    }
}
