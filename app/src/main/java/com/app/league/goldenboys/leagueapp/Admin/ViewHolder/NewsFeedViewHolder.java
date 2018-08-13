package com.app.league.goldenboys.leagueapp.Admin.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.app.league.goldenboys.leagueapp.R;

public class NewsFeedViewHolder extends RecyclerView.ViewHolder{

    private View view;

    public TextView tvNewsFeedTitle;
    public TextView tvNewsFeedContent;
    public TextView tvNewsFeedAuthor;
    public TextView tvNewsFeedDate;

    public NewsFeedViewHolder(View itemView) {
        super(itemView);

        view = itemView;

        tvNewsFeedTitle = itemView.findViewById(R.id.tvNewsFeedTitle);
        tvNewsFeedContent = itemView.findViewById(R.id.tvNewsFeedContent);
        tvNewsFeedAuthor = itemView.findViewById(R.id.tvNewsFeedAuthor);
        tvNewsFeedDate = itemView.findViewById(R.id.tvNewsFeedDate);
    }
}
