package com.app.league.goldenboys.leagueapp.Organizer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.app.league.goldenboys.leagueapp.R;

public class MatchListViewholder extends RecyclerView.ViewHolder{

    private View view;

    public TextView tvMatchListName;
    public TextView tvMatchListTeams;
    public TextView tvMatchListSchedule;

    public MatchListViewholder(View itemView) {
        super(itemView);

        view = itemView;

        tvMatchListName = itemView.findViewById(R.id.tvMatchListName);
        tvMatchListTeams = itemView.findViewById(R.id.tvMatchListTeams);
        tvMatchListSchedule = itemView.findViewById(R.id.tvMatchListSchedule);

    }
}
