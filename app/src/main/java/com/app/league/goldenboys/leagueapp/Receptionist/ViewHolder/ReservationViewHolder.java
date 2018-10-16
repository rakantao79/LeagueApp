package com.app.league.goldenboys.leagueapp.Receptionist.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.app.league.goldenboys.leagueapp.R;

public class ReservationViewHolder extends RecyclerView.ViewHolder{

    View view;
    public TextView tvReserveName;
    public TextView tvReserveDate;
    public TextView tvReserveTime;
    public TextView tvReserveBookDate;

    public ReservationViewHolder(View itemView) {
        super(itemView);

        view = itemView;

        tvReserveName = itemView.findViewById(R.id.tvReserveName);
        tvReserveDate = itemView.findViewById(R.id.tvReserveDate);
        tvReserveTime = itemView.findViewById(R.id.tvReserveTime);
        tvReserveBookDate = itemView.findViewById(R.id.tvReserveBookDate);


    }
}
