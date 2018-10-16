package com.app.league.goldenboys.leagueapp.Admin.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.app.league.goldenboys.leagueapp.R;

public class AdminNotifViewHolder extends RecyclerView.ViewHolder{

    private View view;

    public TextView tvAdminNotifContent;
    public TextView tvAdminNotifDate;
    public TextView tvAdminNotifTitle;

    public AdminNotifViewHolder(View itemView) {
        super(itemView);

        tvAdminNotifContent = itemView.findViewById(R.id.tvAdminNotifContent);
        tvAdminNotifDate = itemView.findViewById(R.id.tvAdminNotifDate);
        tvAdminNotifTitle = itemView.findViewById(R.id.tvAdminNotifTitle);

    }
}
