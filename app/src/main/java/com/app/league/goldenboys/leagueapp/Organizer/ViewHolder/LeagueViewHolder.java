package com.app.league.goldenboys.leagueapp.Organizer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.app.league.goldenboys.leagueapp.R;


public class LeagueViewHolder extends RecyclerView.ViewHolder{

    private View view;

    public TextView tvLeagueTitle;
    public TextView tvLeagueDescription;

    public LeagueViewHolder(View itemView) {

        super(itemView);

        view = itemView;

        tvLeagueTitle = itemView.findViewById(R.id.tvLeagueTitle);
        tvLeagueDescription = itemView.findViewById(R.id.tvLeagueDescription);

    }
}
