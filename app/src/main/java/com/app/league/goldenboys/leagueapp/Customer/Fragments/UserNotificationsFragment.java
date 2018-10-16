package com.app.league.goldenboys.leagueapp.Customer.Fragments;


import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.league.goldenboys.leagueapp.Customer.Activities.UserNotificationDetailActivity;
import com.app.league.goldenboys.leagueapp.Customer.Data.DataUserNotification;
import com.app.league.goldenboys.leagueapp.Customer.Data.DataUserNotificationFinal;
import com.app.league.goldenboys.leagueapp.Customer.ViewHolder.UserNotificationViewHolder;
import com.app.league.goldenboys.leagueapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tapadoo.alerter.Alerter;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserNotificationsFragment extends Fragment {

    private RecyclerView mRecyclerview;
    private DatabaseReference mDatabaseRef;
    private LinearLayoutManager linearLayoutManager;
    private long countnotif = 0;
    private long totalnotif = 0;
    private String leagueTitle;
    private String divisionCategory;

    public UserNotificationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view  =  inflater.inflate(R.layout.fragment_user_notifications, container, false);

        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();

//        divisionCategory = getArguments().getString("divisionCategory");
//        leagueTitle = getArguments().getString("leagueTitle");

        mRecyclerview = view.findViewById(R.id.recyclerViewUserNotification);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);


        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("UserNotificationFinal");

        mRecyclerview.setLayoutManager(linearLayoutManager);

        return view ;
    }



    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter <DataUserNotificationFinal, UserNotificationViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<DataUserNotificationFinal, UserNotificationViewHolder>(

                        DataUserNotificationFinal.class,
                        R.layout.cardview_user_notification,
                        UserNotificationViewHolder.class,
                        mDatabaseRef

                ) {
            @Override
            protected void populateViewHolder(UserNotificationViewHolder viewHolder, final DataUserNotificationFinal model, int position) {

                final String getKey = getRef(position).getKey();

                viewHolder.tvNotifTitle.setText(model.getNotifTitle());
                viewHolder.tvNotifDate.setText(model.getNotfiDate());

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), UserNotificationDetailActivity.class);
//                        intent.putExtra("leagueTitle", leagueTitle);
//                        intent.putExtra("divisionCategory", divisionCategory);
                        intent.putExtra("getkey", getKey);
                        startActivity(intent);
                    }
                });


                mDatabaseRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                        Alerter.create(getActivity())
//                                .setTitle("League Notification")
//                                .setIcon(R.drawable.logo_ball_white)
//                                .setBackgroundColorRes(R.color.colorPrimary)
//                                .show();
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        if (dataSnapshot.exists()){
//                            Alerter.create(getActivity())
//                                    .setTitle("League Notification")
//                                    .setIcon(R.drawable.logo_ball_white)
//                                    .setBackgroundColorRes(R.color.colorPrimary)
//                                    .show();
                        }
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        };
        mRecyclerview.setAdapter(firebaseRecyclerAdapter);
    }
}
