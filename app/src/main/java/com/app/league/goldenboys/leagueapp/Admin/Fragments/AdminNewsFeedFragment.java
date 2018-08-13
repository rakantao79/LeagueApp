package com.app.league.goldenboys.leagueapp.Admin.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.league.goldenboys.leagueapp.R;
import com.google.firebase.database.DatabaseReference;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminNewsFeedFragment extends Fragment {

    private RecyclerView mRecyclerview;
    private DatabaseReference mDatabaseRef;
    private LinearLayoutManager linearLayoutManager;

    public AdminNewsFeedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_news_feed, container, false);

        mRecyclerview = view.findViewById(R.id.recyclerViewAdminNewsFeed);
        linearLayoutManager = new LinearLayoutManager(getContext());

        mRecyclerview.setLayoutManager(linearLayoutManager);

        return view;
    }

}
