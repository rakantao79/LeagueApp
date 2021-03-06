package com.app.league.goldenboys.leagueapp.Organizer.Fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.league.goldenboys.leagueapp.Fragments.NewsFeedFragment;
import com.app.league.goldenboys.leagueapp.Fragments.NotificationsFragment;
import com.app.league.goldenboys.leagueapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabHostOrganizerModulesFragment extends Fragment {

    private String divisionCategory;
    private String leagueTitle;

    public TabHostOrganizerModulesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_host_organizer_modules, container, false);

        divisionCategory = getArguments().getString("divisionCategory");
        leagueTitle = getArguments().getString("leagueTitle");

        Log.d("fragdata", "onCreateView: " + divisionCategory + leagueTitle);

        OrganizerPendingEntriesFragment organizerPendingEntriesFragment = new OrganizerPendingEntriesFragment();
        Bundle pendingArgs = new Bundle();
        pendingArgs.putString("divisionCategory", divisionCategory);
        pendingArgs.putString("leagueTitle", leagueTitle);

        ViewPager viewPager = view.findViewById(R.id.viewpager_organizer_modules);
        setupViewPager(viewPager);

        TabLayout tabs = view.findViewById(R.id.tab_organizer_modules);
        tabs.setupWithViewPager(viewPager);

        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(new OrganizerPendingEntriesFragment(), "Pending Entries");
        adapter.addFragment(new OrganizerApprovedEntriesFragment(), "Approved Entries");
        viewPager.setAdapter(adapter);
    }


    public static class Adapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
