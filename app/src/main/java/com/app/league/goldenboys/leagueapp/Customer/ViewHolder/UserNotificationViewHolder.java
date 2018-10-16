package com.app.league.goldenboys.leagueapp.Customer.ViewHolder;

        import android.support.v7.widget.RecyclerView;
        import android.view.View;
        import android.widget.TextView;

        import com.app.league.goldenboys.leagueapp.R;

public class UserNotificationViewHolder extends RecyclerView.ViewHolder{

    private View view;

    public TextView tvNotifContent;
    public TextView tvNotifDate;
    public TextView tvNotifTitle;

    public UserNotificationViewHolder(View itemView) {
        super(itemView);

        view = itemView;

        tvNotifContent = itemView.findViewById(R.id.tvNotifContent);
        tvNotifDate = itemView.findViewById(R.id.tvNotifDate);
        tvNotifTitle = itemView.findViewById(R.id.tvNotifTitle);

    }
}
