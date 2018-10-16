package com.app.league.goldenboys.leagueapp.Organizer.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.app.league.goldenboys.leagueapp.R;

public class TeamEntryViewHolder extends RecyclerView.ViewHolder{

    private View view;
    public TextView tvTeamName;

    public TeamEntryViewHolder(View itemView) {
        super(itemView);

        view = itemView;

        tvTeamName = itemView.findViewById(R.id.tvEntryTeamName);

    }
}
