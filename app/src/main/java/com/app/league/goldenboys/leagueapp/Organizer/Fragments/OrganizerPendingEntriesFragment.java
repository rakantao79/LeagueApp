package com.app.league.goldenboys.leagueapp.Organizer.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.league.goldenboys.leagueapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrganizerPendingEntriesFragment extends Fragment {

    private String divisionCategory;
    private String leagueTitle;


    public OrganizerPendingEntriesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_organizer_pending_entries, container, false);

//        divisionCategory = getArguments().getString("divisionCategory");
//        leagueTitle = getArguments().getString("leagueTitle");

        Log.d("fragmentsss", "pending entris fragmentonCreateView: " + divisionCategory + leagueTitle);

        return view;
    }

}
