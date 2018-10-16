package com.app.league.goldenboys.leagueapp.Admin.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.league.goldenboys.leagueapp.Admin.Activities.AddNewsActivity;
import com.app.league.goldenboys.leagueapp.Admin.Data.DataNewsFeed;
import com.app.league.goldenboys.leagueapp.Admin.ViewHolder.NewsFeedViewHolder;
import com.app.league.goldenboys.leagueapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;



/**
 * A simple {@link Fragment} subclass.
 */
public class AdminNewsFeedFragment extends Fragment {

    private RecyclerView mRecyclerview;
    private DatabaseReference mDatabaseRef;
    private LinearLayoutManager linearLayoutManager;
    private FloatingActionButton fbAddNews;

    public AdminNewsFeedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_news_feed, container, false);

        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();

        fbAddNews = view.findViewById(R.id.fbAdminAddNews);

        mRecyclerview = view.findViewById(R.id.recyclerViewAdminNewsFeed);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("NewsFeed");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Log.d("news feed", "onDataChange: " + dataSnapshot.getValue());
                    Log.d("news feed", "onDataChange: " + dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        fbAddNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddNewsActivity.class));
            }
        });

        mRecyclerview.setLayoutManager(linearLayoutManager);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<DataNewsFeed, TryNewsFeedViewholder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<DataNewsFeed, TryNewsFeedViewholder>(


                DataNewsFeed.class,
                R.layout.cardview_newsfeed,
                TryNewsFeedViewholder.class,
                mDatabaseRef.orderByChild("counter")

        ) {
            @Override
            protected void populateViewHolder(TryNewsFeedViewholder viewHolder, DataNewsFeed model, int position) {

                final String pushkey = getRef(position).getKey();

                viewHolder.setNewstitle(model.getNewstitle());
                viewHolder.setNewscontent(model.getNewscontent());

                viewHolder.setDatePosted(model.getDatePosted());
                viewHolder.setImageUrl(model.getImageUrl(), getContext());
            }
        };
        mRecyclerview.setAdapter(firebaseRecyclerAdapter);

    }

    public static class TryNewsFeedViewholder extends RecyclerView.ViewHolder{

        View mView;

        public TryNewsFeedViewholder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setNewstitle (String newstitle) {
            TextView mTitle = mView.findViewById(R.id.tvNewsFeedTitle);
            mTitle.setText(newstitle);
        }

        public void setNewscontent(String newscontent){
            TextView mContent = mView.findViewById(R.id.tvNewsFeedContent);
            mContent.setText(newscontent);
        }

        public void setDatePosted(String datePosted){
            TextView mDatePosted = mView.findViewById(R.id.tvNewsFeedDate);
            mDatePosted.setText(datePosted);
        }

        public void setImageUrl(String imageUrl, Context mContext){
            ImageView mImage = mView.findViewById(R.id.imgNewsImage);
            Picasso.with(mContext).load(imageUrl).placeholder(R.drawable.img_default).into(mImage);

        }
    }
}
