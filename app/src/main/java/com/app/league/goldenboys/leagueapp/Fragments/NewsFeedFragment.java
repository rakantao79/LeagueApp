package com.app.league.goldenboys.leagueapp.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
public class NewsFeedFragment extends Fragment {

    private RecyclerView mRecyclerview;
    private DatabaseReference mDatabaseRef;
    private LinearLayoutManager linearLayoutManager;

    public NewsFeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_feed, container, false);

        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();

        mRecyclerview = view.findViewById(R.id.recyclerViewNewsFeed);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("NewsFeed");

        mRecyclerview.setLayoutManager(linearLayoutManager);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<DataNewsFeed, TryRegularNewsFeedViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<DataNewsFeed, TryRegularNewsFeedViewHolder>(

                DataNewsFeed.class,
                R.layout.cardview_newsfeed,
                TryRegularNewsFeedViewHolder.class,
                mDatabaseRef.orderByChild("counter")

        ) {
            @Override
            protected void populateViewHolder(TryRegularNewsFeedViewHolder viewHolder, DataNewsFeed model, int position) {
                viewHolder.setNewstitle(model.getNewstitle());
                viewHolder.setNewscontent(model.getNewscontent());
                viewHolder.setDatePosted(model.getDatePosted());
                viewHolder.setImageUrl(model.getImageUrl(), getContext());
            }
        };

        mRecyclerview.setAdapter(firebaseRecyclerAdapter);

    }

    public static class TryRegularNewsFeedViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public TryRegularNewsFeedViewHolder(View itemView) {
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
            Picasso.with(mContext).load(imageUrl).placeholder(R.drawable.logo_ball_white).into(mImage);

        }
    }
}
