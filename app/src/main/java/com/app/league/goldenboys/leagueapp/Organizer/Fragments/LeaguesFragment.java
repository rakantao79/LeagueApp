package com.app.league.goldenboys.leagueapp.Organizer.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.league.goldenboys.leagueapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeaguesFragment extends Fragment {



    public LeaguesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leagues, container, false);
    }

}
