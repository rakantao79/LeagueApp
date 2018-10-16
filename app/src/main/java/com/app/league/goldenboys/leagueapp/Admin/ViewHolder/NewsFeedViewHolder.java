package com.app.league.goldenboys.leagueapp.Admin.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.league.goldenboys.leagueapp.R;
import com.squareup.picasso.Picasso;

public class NewsFeedViewHolder extends RecyclerView.ViewHolder{

    private View view;

    public TextView tvNewsFeedTitle;
    public TextView tvNewsFeedContent;
    public TextView tvNewsFeedDate;
    public TextView tvNewsFeedAuthor;
    public ImageView imgNews;

    public NewsFeedViewHolder(View itemView) {
        super(itemView);

        view = itemView;

        tvNewsFeedTitle = itemView.findViewById(R.id.tvNewsFeedTitle);
        tvNewsFeedContent = itemView.findViewById(R.id.tvNewsFeedContent);
        tvNewsFeedDate = itemView.findViewById(R.id.tvNewsFeedDate);
        tvNewsFeedAuthor = itemView.findViewById(R.id.tvNewsFeedAuthor);
        imgNews = itemView.findViewById(R.id.imgNewsImage);

    }
}
