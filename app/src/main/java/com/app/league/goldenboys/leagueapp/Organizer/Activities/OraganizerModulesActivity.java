package com.app.league.goldenboys.leagueapp.Organizer.Activities;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.app.league.goldenboys.leagueapp.Organizer.Fragments.TabHostOrganizerModulesFragment;
import com.app.league.goldenboys.leagueapp.R;

public class OraganizerModulesActivity extends AppCompatActivity {

    private String divisionCategory;
    private String leagueTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oraganizer_modules);

        divisionCategory = getIntent().getStringExtra("divisionCategory");
        leagueTitle = getIntent().getStringExtra("leagueTitle");

        Bundle bundle = new Bundle();
        bundle.putString("divisionCategory", divisionCategory);
        bundle.putString("leagueTitle",leagueTitle);
        TabHostOrganizerModulesFragment tabHostOrganizerModulesFragment = new TabHostOrganizerModulesFragment();
        tabHostOrganizerModulesFragment.setArguments(bundle);

//        Bundle args = new Bundle();
//        args.putString("divisionCategory", divisionCategory);
//        args.putString("leagueTitle", leagueTitle);
//        tabHostOrganizerModulesFragment.setArguments(args);
//
//        getFragmentManager().beginTransaction().add(R.id.container_organizer_modules, tabHostOrganizerModulesFragment).commit();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_organizer_modules, tabHostOrganizerModulesFragment)
                .commit();

    }
}
