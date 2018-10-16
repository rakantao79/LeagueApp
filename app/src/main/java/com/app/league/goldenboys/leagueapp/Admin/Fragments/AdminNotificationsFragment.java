package com.app.league.goldenboys.leagueapp.Admin.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.league.goldenboys.leagueapp.Admin.Data.DataAdminNotification;
import com.app.league.goldenboys.leagueapp.Admin.ViewHolder.AdminNotifViewHolder;
import com.app.league.goldenboys.leagueapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminNotificationsFragment extends Fragment {

    private RecyclerView mRecyclerview;
    private DatabaseReference mDatabaseRef;
    private LinearLayoutManager linearLayoutManager;


    public AdminNotificationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_notifications, container, false);

        mRecyclerview = view.findViewById(R.id.recyclerview_AdminNotif);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("NotificationAdmin");

        mRecyclerview.setLayoutManager(linearLayoutManager);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter <DataAdminNotification, AdminNotifViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<DataAdminNotification, AdminNotifViewHolder>(

                DataAdminNotification.class,
                R.layout.cardview_admin_notif,
                AdminNotifViewHolder.class,
                mDatabaseRef

        ) {
            @Override
            protected void populateViewHolder(AdminNotifViewHolder viewHolder, DataAdminNotification model, int position) {
                viewHolder.tvAdminNotifTitle.setText(model.getNotifTitle());
                viewHolder.tvAdminNotifDate.setText(model.getNotifDate());
            }
        };
        mRecyclerview.setAdapter(firebaseRecyclerAdapter);
    }
}
