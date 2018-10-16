package com.app.league.goldenboys.leagueapp.Organizer.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.app.league.goldenboys.leagueapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import static java.util.Calendar.YEAR;

public class RoundRobinScheduleActivity extends AppCompatActivity {

    private TextView tvRBMatchName1;
    private TextView tvRBMatchName2;
    private TextView tvRBMatchName3;
    private TextView tvRBMatchName4;
    private TextView tvRBMatchName5;
    private TextView tvRBMatchName6;
    private TextView tvRBMatchName7;
    private TextView tvRBMatchName8;
    private TextView tvRBMatchName9;
    private TextView tvRBMatchName10;
    private TextView tvRBMatchName11;
    private TextView tvRBMatchName12;
    private TextView tvRBMatchName13;

    private TextView tvRBMatch1OpponentA;
    private TextView tvRBMatch2OpponentA;
    private TextView tvRBMatch3OpponentA;
    private TextView tvRBMatch4OpponentA;
    private TextView tvRBMatch5OpponentA;
    private TextView tvRBMatch6OpponentA;
    private TextView tvRBMatch7OpponentA;
    private TextView tvRBMatch8OpponentA;
    private TextView tvRBMatch9OpponentA;
    private TextView tvRBMatch10OpponentA;
    private TextView tvRBMatch11OpponentA;
    private TextView tvRBMatch12OpponentA;
    private TextView tvRBMatch13OpponentA;

    private TextView tvRBMatch1OpponentB;
    private TextView tvRBMatch2OpponentB;
    private TextView tvRBMatch3OpponentB;
    private TextView tvRBMatch4OpponentB;
    private TextView tvRBMatch5OpponentB;
    private TextView tvRBMatch6OpponentB;
    private TextView tvRBMatch7OpponentB;
    private TextView tvRBMatch8OpponentB;
    private TextView tvRBMatch9OpponentB;
    private TextView tvRBMatch10OpponentB;
    private TextView tvRBMatch11OpponentB;
    private TextView tvRBMatch12OpponentB;
    private TextView tvRBMatch13OpponentB;

    private EditText tvRBMatch1Date;
    private EditText tvRBMatch2Date;
    private EditText tvRBMatch3Date;
    private EditText tvRBMatch4Date;
    private EditText tvRBMatch5Date;
    private EditText tvRBMatch6Date;
    private EditText tvRBMatch7Date;
    private EditText tvRBMatch8Date;
    private EditText tvRBMatch9Date;
    private EditText tvRBMatch10Date;
    private EditText tvRBMatch11Date;
    private EditText tvRBMatch12Date;
    private EditText tvRBMatch13Date;

    private EditText tvRBMatch1Time;
    private EditText tvRBMatch2Time;
    private EditText tvRBMatch3Time;
    private EditText tvRBMatch4Time;
    private EditText tvRBMatch5Time;
    private EditText tvRBMatch6Time;
    private EditText tvRBMatch7Time;
    private EditText tvRBMatch8Time;
    private EditText tvRBMatch9Time;
    private EditText tvRBMatch10Time;
    private EditText tvRBMatch11Time;
    private EditText tvRBMatch12Time;
    private EditText tvRBMatch13Time;

    private Button btnRBMatch1Save;
    private Button btnRBMatch2Save;
    private Button btnRBMatch3Save;
    private Button btnRBMatch4Save;
    private Button btnRBMatch5Save;
    private Button btnRBMatch6Save;
    private Button btnRBMatch7Save;
    private Button btnRBMatch8Save;
    private Button btnRBMatch9Save;
    private Button btnRBMatch10Save;
    private Button btnRBMatch11Save;
    private Button btnRBMatch12Save;
    private Button btnRBMatch13Save;

    private String date;
    private String pushkey;
    private String matchKey;
    private int mYear, mMonth, mDay, mHour, mMinute;

    private String leagueTitle;
    private String divisionCategory;

    private DatabaseReference mDatabaseMatchDetails;
    private DatabaseReference mDatabaseMatch;
    private DatabaseReference mDatabaseUserNotificationFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_robin_schedule);

        divisionCategory = getIntent().getStringExtra("divisionCategory");
        leagueTitle = getIntent().getStringExtra("leagueTitle");

        DateFormat df = new SimpleDateFormat("yyyy/MM/dd h:mm a");
        date = df.format(Calendar.getInstance().getTime());

        tvRBMatchName1 = findViewById(R.id.tvRBMatchName1);
        tvRBMatchName2 = findViewById(R.id.tvRBMatchName2);
        tvRBMatchName3 = findViewById(R.id.tvRBMatchName3);
        tvRBMatchName4 = findViewById(R.id.tvRBMatchName4);
        tvRBMatchName5 = findViewById(R.id.tvRBMatchName5);
        tvRBMatchName6 = findViewById(R.id.tvRBMatchName6);
        tvRBMatchName7 = findViewById(R.id.tvRBMatchName7);
        tvRBMatchName8 = findViewById(R.id.tvRBMatchName8);
        tvRBMatchName9 = findViewById(R.id.tvRBMatchName9);
        tvRBMatchName10 = findViewById(R.id.tvRBMatchName10);
        tvRBMatchName11 = findViewById(R.id.tvRBMatchName11);
        tvRBMatchName12 = findViewById(R.id.tvRBMatchName12);
        tvRBMatchName13 = findViewById(R.id.tvRBMatchName13);

        tvRBMatch1OpponentA = findViewById(R.id.tvRBMatch1OpponentA);
        tvRBMatch2OpponentA = findViewById(R.id.tvRBMatch2OpponentA);
        tvRBMatch3OpponentA = findViewById(R.id.tvRBMatch3OpponentA);
        tvRBMatch4OpponentA = findViewById(R.id.tvRBMatch4OpponentA);
        tvRBMatch5OpponentA = findViewById(R.id.tvRBMatch5OpponentA);
        tvRBMatch6OpponentA = findViewById(R.id.tvRBMatch6OpponentA);
        tvRBMatch7OpponentA = findViewById(R.id.tvRBMatch7OpponentA);
        tvRBMatch8OpponentA = findViewById(R.id.tvRBMatch8OpponentA);
        tvRBMatch9OpponentA = findViewById(R.id.tvRBMatch9OpponentA);
        tvRBMatch10OpponentA = findViewById(R.id.tvRBMatch10OpponentA);
        tvRBMatch11OpponentA = findViewById(R.id.tvRBMatch11OpponentA);
        tvRBMatch12OpponentA = findViewById(R.id.tvRBMatch12OpponentA);
        tvRBMatch13OpponentA = findViewById(R.id.tvRBMatch13OpponentA);

        tvRBMatch1OpponentB = findViewById(R.id.tvRBMatch1OpponentB);
        tvRBMatch2OpponentB = findViewById(R.id.tvRBMatch2OpponentB);
        tvRBMatch3OpponentB = findViewById(R.id.tvRBMatch3OpponentB);
        tvRBMatch4OpponentB = findViewById(R.id.tvRBMatch4OpponentB);
        tvRBMatch5OpponentB = findViewById(R.id.tvRBMatch5OpponentB);
        tvRBMatch6OpponentB = findViewById(R.id.tvRBMatch6OpponentB);
        tvRBMatch7OpponentB = findViewById(R.id.tvRBMatch7OpponentB);
        tvRBMatch8OpponentB = findViewById(R.id.tvRBMatch8OpponentB);
        tvRBMatch9OpponentB = findViewById(R.id.tvRBMatch9OpponentB);
        tvRBMatch10OpponentB = findViewById(R.id.tvRBMatch10OpponentB);
        tvRBMatch11OpponentB = findViewById(R.id.tvRBMatch11OpponentB);
        tvRBMatch12OpponentB = findViewById(R.id.tvRBMatch12OpponentB);
        tvRBMatch13OpponentB = findViewById(R.id.tvRBMatch13OpponentB);

        tvRBMatch1Date = findViewById(R.id.tvRBMatch1Date);
        tvRBMatch2Date = findViewById(R.id.tvRBMatch2Date);
        tvRBMatch3Date = findViewById(R.id.tvRBMatch3Date);
        tvRBMatch4Date = findViewById(R.id.tvRBMatch4Date);
        tvRBMatch5Date = findViewById(R.id.tvRBMatch5Date);
        tvRBMatch6Date = findViewById(R.id.tvRBMatch6Date);
        tvRBMatch7Date = findViewById(R.id.tvRBMatch7Date);
        tvRBMatch8Date = findViewById(R.id.tvRBMatch8Date);
        tvRBMatch9Date = findViewById(R.id.tvRBMatch9Date);
        tvRBMatch10Date = findViewById(R.id.tvRBMatch10Date);
        tvRBMatch11Date = findViewById(R.id.tvRBMatch11Date);
        tvRBMatch12Date = findViewById(R.id.tvRBMatch12Date);
        tvRBMatch13Date = findViewById(R.id.tvRBMatch13Date);

        tvRBMatch1Time = findViewById(R.id.tvRBMatch1Time);
        tvRBMatch2Time = findViewById(R.id.tvRBMatch2Time);
        tvRBMatch3Time = findViewById(R.id.tvRBMatch3Time);
        tvRBMatch4Time = findViewById(R.id.tvRBMatch4Time);
        tvRBMatch5Time = findViewById(R.id.tvRBMatch5Time);
        tvRBMatch6Time = findViewById(R.id.tvRBMatch6Time);
        tvRBMatch7Time = findViewById(R.id.tvRBMatch7Time);
        tvRBMatch8Time = findViewById(R.id.tvRBMatch8Time);
        tvRBMatch9Time = findViewById(R.id.tvRBMatch9Time);
        tvRBMatch10Time = findViewById(R.id.tvRBMatch10Time);
        tvRBMatch11Time = findViewById(R.id.tvRBMatch11Time);
        tvRBMatch12Time = findViewById(R.id.tvRBMatch12Time);
        tvRBMatch13Time = findViewById(R.id.tvRBMatch13Time);

        btnRBMatch1Save = findViewById(R.id.btnRBMatch1Save);

        mDatabaseMatchDetails = FirebaseDatabase.getInstance().getReference().child("LeagueEntries").child(leagueTitle).child(divisionCategory).child("RoundRobinMatchList");
        mDatabaseMatch = FirebaseDatabase.getInstance().getReference().child("LeagueEntries").child(leagueTitle).child(divisionCategory).child("FourTeamRoundRobin");
        mDatabaseUserNotificationFinal = FirebaseDatabase.getInstance().getReference().child("UserNotificationFinal");

        retrieveTeamName();
        matchDate();
        matchTime();
        saveMatchDetails();

    }

    private void saveMatchDetails() {
        btnRBMatch1Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String match1Time = tvRBMatch1Time.getText().toString();
                final String match1Date = tvRBMatch1Date.getText().toString();
                final String match2Time = tvRBMatch2Time.getText().toString();
                final String match2Date = tvRBMatch2Date.getText().toString();
                final String match3Time = tvRBMatch3Time.getText().toString();
                final String match3Date = tvRBMatch3Date.getText().toString();
                final String match4Time = tvRBMatch4Time.getText().toString();
                final String match4Date = tvRBMatch4Date.getText().toString();
                final String match5Time = tvRBMatch5Time.getText().toString();
                final String match5Date = tvRBMatch5Date.getText().toString();
                final String match6Time = tvRBMatch6Time.getText().toString();
                final String match6Date = tvRBMatch6Date.getText().toString();
                final String match7Time = tvRBMatch7Time.getText().toString();
                final String match7Date = tvRBMatch7Date.getText().toString();
                final String match8Time = tvRBMatch8Time.getText().toString();
                final String match8Date = tvRBMatch8Date.getText().toString();
                final String match9Time = tvRBMatch9Time.getText().toString();
                final String match9Date = tvRBMatch9Date.getText().toString();
                final String match10Time = tvRBMatch10Time.getText().toString();
                final String match10Date = tvRBMatch10Date.getText().toString();
                final String match11Time = tvRBMatch11Time.getText().toString();
                final String match11Date = tvRBMatch11Date.getText().toString();
                final String match12Time = tvRBMatch12Time.getText().toString();
                final String match12Date = tvRBMatch12Date.getText().toString();
                final String match13Time = tvRBMatch13Time.getText().toString();
                final String match13Date = tvRBMatch13Date.getText().toString();

                if (TextUtils.isEmpty(match1Time) || TextUtils.isEmpty(match1Date)
                        || TextUtils.isEmpty(match2Time) || TextUtils.isEmpty(match2Date)
                        || TextUtils.isEmpty(match3Time) || TextUtils.isEmpty(match3Date)
                        || TextUtils.isEmpty(match4Time) || TextUtils.isEmpty(match4Date)
                        || TextUtils.isEmpty(match5Time) || TextUtils.isEmpty(match5Date)
                        || TextUtils.isEmpty(match6Time) || TextUtils.isEmpty(match6Date)
                        || TextUtils.isEmpty(match7Time) || TextUtils.isEmpty(match7Date)
                        || TextUtils.isEmpty(match8Time) || TextUtils.isEmpty(match8Date)
                        || TextUtils.isEmpty(match9Time) || TextUtils.isEmpty(match9Date)
                        || TextUtils.isEmpty(match10Time) || TextUtils.isEmpty(match10Date)
                        || TextUtils.isEmpty(match11Time) || TextUtils.isEmpty(match11Date)
                        || TextUtils.isEmpty(match12Time) || TextUtils.isEmpty(match12Date)
                        || TextUtils.isEmpty(match13Time) || TextUtils.isEmpty(match13Date)) {

                    Toast.makeText(RoundRobinScheduleActivity.this, "Please don't leave empty fields", Toast.LENGTH_SHORT).show();

                } else {

                    final HashMap match1Map = new HashMap();
                    match1Map.put("matchName", "Match 1");
                    match1Map.put("matchOpponentA", tvRBMatch1OpponentA.getText().toString());
                    match1Map.put("matchOpponentB", tvRBMatch1OpponentB.getText().toString());
                    match1Map.put("matchSchedule", match1Time + " " + match1Date);
                    match1Map.put("matchOpponentAScore", "0");
                    match1Map.put("matchOpponentBScore", "0");
                    match1Map.put("matchPlayerOfTheGame", "default");
                    match1Map.put("matchWinner", "default");
                    match1Map.put("matchPlayerPoints", "0");
                    match1Map.put("matchPlayerRebounds", "0");
                    match1Map.put("matchPlayerAssist", "0");
                    match1Map.put("matchPlayerSteal", "0");
                    match1Map.put("matchPlayerBlock", "0");

                    mDatabaseMatchDetails.child("Match A").setValue(match1Map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

//                            String notifkey = mDatabaseUserNotificationFinal.push().getKey();
//
//                            HashMap finalNotifMap = new HashMap();
//                            finalNotifMap.put("notifTitle", "Match 1 Schedule has been set");
//                            finalNotifMap.put("notifMatch", tvRBMatch1OpponentA.getText().toString() + " VS " + tvRBMatch1OpponentB.getText().toString());
//                            finalNotifMap.put("notfiDate", date);
//                            finalNotifMap.put("notifKey", notifkey);
//
//                            mDatabaseUserNotificationFinal.push().setValue(finalNotifMap).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    Toast.makeText(RoundRobinScheduleActivity.this, "Match 1 Schedule Saved", Toast.LENGTH_SHORT).show();
//                                }
//                            });
                        }
                    });

                    HashMap match2Map = new HashMap();
                    match2Map.put("matchName", "Match 2");
                    match2Map.put("matchOpponentA", tvRBMatch2OpponentA.getText().toString());
                    match2Map.put("matchOpponentB", tvRBMatch2OpponentB.getText().toString());
                    match2Map.put("matchSchedule", match2Time + " " + match2Date);
                    match2Map.put("matchOpponentAScore", "0");
                    match2Map.put("matchOpponentBScore", "0");
                    match2Map.put("matchPlayerOfTheGame", "default");
                    match2Map.put("matchWinner", "default");
                    match2Map.put("matchPlayerPoints", "0");
                    match2Map.put("matchPlayerRebounds", "0");
                    match2Map.put("matchPlayerAssist", "0");
                    match2Map.put("matchPlayerSteal", "0");
                    match2Map.put("matchPlayerBlock", "0");

                    mDatabaseMatchDetails.child("Match B").setValue(match2Map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(RoundRobinScheduleActivity.this, "Round Robin Schedule Save", Toast.LENGTH_SHORT).show();
                        }
                    });

                    HashMap match3Map = new HashMap();
                    match3Map.put("matchName", "Match 3");
                    match3Map.put("matchOpponentA", tvRBMatch3OpponentA.getText().toString());
                    match3Map.put("matchOpponentB", tvRBMatch3OpponentB.getText().toString());
                    match3Map.put("matchSchedule", match3Time + " " + match3Date);
                    match3Map.put("matchOpponentAScore", "0");
                    match3Map.put("matchOpponentBScore", "0");
                    match3Map.put("matchPlayerOfTheGame", "default");
                    match3Map.put("matchWinner", "default");
                    match3Map.put("matchPlayerPoints", "0");
                    match3Map.put("matchPlayerRebounds", "0");
                    match3Map.put("matchPlayerAssist", "0");
                    match3Map.put("matchPlayerSteal", "0");
                    match3Map.put("matchPlayerBlock", "0");

                    mDatabaseMatchDetails.child("Match C").setValue(match3Map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });

                    HashMap match4Map = new HashMap();
                    match4Map.put("matchName", "Match 4");
                    match4Map.put("matchOpponentA", tvRBMatch4OpponentA.getText().toString());
                    match4Map.put("matchOpponentB", tvRBMatch4OpponentB.getText().toString());
                    match4Map.put("matchSchedule", match4Time + " " + match4Date);
                    match4Map.put("matchOpponentAScore", "0");
                    match4Map.put("matchOpponentBScore", "0");
                    match4Map.put("matchPlayerOfTheGame", "default");
                    match4Map.put("matchWinner", "default");
                    match4Map.put("matchPlayerPoints", "0");
                    match4Map.put("matchPlayerRebounds", "0");
                    match4Map.put("matchPlayerAssist", "0");
                    match4Map.put("matchPlayerSteal", "0");
                    match4Map.put("matchPlayerBlock", "0");

                    mDatabaseMatchDetails.child("Match D").setValue(match4Map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });

                    HashMap match5Map = new HashMap();
                    match5Map.put("matchName", "Match 5");
                    match5Map.put("matchOpponentA", tvRBMatch5OpponentA.getText().toString());
                    match5Map.put("matchOpponentB", tvRBMatch5OpponentB.getText().toString());
                    match5Map.put("matchSchedule", match5Time + " " + match5Date);
                    match5Map.put("matchOpponentAScore", "0");
                    match5Map.put("matchOpponentBScore", "0");
                    match5Map.put("matchPlayerOfTheGame", "default");
                    match5Map.put("matchWinner", "default");
                    match5Map.put("matchPlayerPoints", "0");
                    match5Map.put("matchPlayerRebounds", "0");
                    match5Map.put("matchPlayerAssist", "0");
                    match5Map.put("matchPlayerSteal", "0");
                    match5Map.put("matchPlayerBlock", "0");

                    mDatabaseMatchDetails.child("Match E").setValue(match5Map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });

                    HashMap match6Map = new HashMap();
                    match6Map.put("matchName", "Match 6");
                    match6Map.put("matchOpponentA", tvRBMatch6OpponentA.getText().toString());
                    match6Map.put("matchOpponentB", tvRBMatch6OpponentB.getText().toString());
                    match6Map.put("matchSchedule", match6Time + " " + match6Date);
                    match6Map.put("matchOpponentAScore", "0");
                    match6Map.put("matchOpponentBScore", "0");
                    match6Map.put("matchPlayerOfTheGame", "default");
                    match6Map.put("matchWinner", "default");
                    match6Map.put("matchPlayerPoints", "0");
                    match6Map.put("matchPlayerRebounds", "0");
                    match6Map.put("matchPlayerAssist", "0");
                    match6Map.put("matchPlayerSteal", "0");
                    match6Map.put("matchPlayerBlock", "0");

                    mDatabaseMatchDetails.child("Match F").setValue(match6Map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });

                    HashMap match7Map = new HashMap();
                    match7Map.put("matchName", "Match 7");
                    match7Map.put("matchOpponentA", tvRBMatch7OpponentA.getText().toString());
                    match7Map.put("matchOpponentB", tvRBMatch7OpponentB.getText().toString());
                    match7Map.put("matchSchedule", match7Time + " " + match7Date);
                    match7Map.put("matchOpponentAScore", "0");
                    match7Map.put("matchOpponentBScore", "0");
                    match7Map.put("matchPlayerOfTheGame", "default");
                    match7Map.put("matchWinner", "default");
                    match7Map.put("matchPlayerPoints", "0");
                    match7Map.put("matchPlayerRebounds", "0");
                    match7Map.put("matchPlayerAssist", "0");
                    match7Map.put("matchPlayerSteal", "0");
                    match7Map.put("matchPlayerBlock", "0");

                    mDatabaseMatchDetails.child("Match G").setValue(match7Map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });

                    HashMap match8Map = new HashMap();
                    match8Map.put("matchName", "Match 8");
                    match8Map.put("matchOpponentA", tvRBMatch8OpponentA.getText().toString());
                    match8Map.put("matchOpponentB", tvRBMatch8OpponentB.getText().toString());
                    match8Map.put("matchSchedule", match8Time + " " + match8Date);
                    match8Map.put("matchOpponentAScore", "0");
                    match8Map.put("matchOpponentBScore", "0");
                    match8Map.put("matchPlayerOfTheGame", "default");
                    match8Map.put("matchWinner", "default");
                    match8Map.put("matchPlayerPoints", "0");
                    match8Map.put("matchPlayerRebounds", "0");
                    match8Map.put("matchPlayerAssist", "0");
                    match8Map.put("matchPlayerSteal", "0");
                    match8Map.put("matchPlayerBlock", "0");

                    mDatabaseMatchDetails.child("Match H").setValue(match8Map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });

                    HashMap match9Map = new HashMap();
                    match9Map.put("matchName", "Match 9");
                    match9Map.put("matchOpponentA", tvRBMatch9OpponentA.getText().toString());
                    match9Map.put("matchOpponentB", tvRBMatch9OpponentB.getText().toString());
                    match9Map.put("matchSchedule", match9Time + " " + match9Date);
                    match9Map.put("matchOpponentAScore", "0");
                    match9Map.put("matchOpponentBScore", "0");
                    match9Map.put("matchPlayerOfTheGame", "default");
                    match9Map.put("matchWinner", "default");
                    match9Map.put("matchPlayerPoints", "0");
                    match9Map.put("matchPlayerRebounds", "0");
                    match9Map.put("matchPlayerAssist", "0");
                    match9Map.put("matchPlayerSteal", "0");
                    match9Map.put("matchPlayerBlock", "0");

                    mDatabaseMatchDetails.child("Match I").setValue(match9Map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });

                    HashMap match10Map = new HashMap();
                    match10Map.put("matchName", "Match 10");
                    match10Map.put("matchOpponentA", tvRBMatch10OpponentA.getText().toString());
                    match10Map.put("matchOpponentB", tvRBMatch10OpponentB.getText().toString());
                    match10Map.put("matchSchedule", match10Time + " " + match10Date);
                    match10Map.put("matchOpponentAScore", "0");
                    match10Map.put("matchOpponentBScore", "0");
                    match10Map.put("matchPlayerOfTheGame", "default");
                    match10Map.put("matchWinner", "default");
                    match10Map.put("matchPlayerPoints", "0");
                    match10Map.put("matchPlayerRebounds", "0");
                    match10Map.put("matchPlayerAssist", "0");
                    match10Map.put("matchPlayerSteal", "0");
                    match10Map.put("matchPlayerBlock", "0");

                    mDatabaseMatchDetails.child("Match J").setValue(match10Map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });

                    HashMap match11Map = new HashMap();
                    match11Map.put("matchName", "Match 11");
//                    match11Map.put("matchOpponentA", tvRBMatch11OpponentA.getText().toString());
//                    match11Map.put("matchOpponentB", tvRBMatch11OpponentB.getText().toString());
                    match11Map.put("matchOpponentA", "Rank 1");
                    match11Map.put("matchOpponentB", "Rank 4");
                    match11Map.put("matchSchedule", match11Time + " " + match11Date);
                    match11Map.put("matchOpponentAScore", "0");
                    match11Map.put("matchOpponentBScore", "0");
                    match11Map.put("matchPlayerOfTheGame", "default");
                    match11Map.put("matchWinner", "default");
                    match11Map.put("matchPlayerPoints", "0");
                    match11Map.put("matchPlayerRebounds", "0");
                    match11Map.put("matchPlayerAssist", "0");
                    match11Map.put("matchPlayerSteal", "0");
                    match11Map.put("matchPlayerBlock", "0");

                    mDatabaseMatchDetails.child("Match K").setValue(match11Map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });

                    HashMap match12Map = new HashMap();
                    match12Map.put("matchName", "Match 12");
//                    match12Map.put("matchOpponentA", tvRBMatch12OpponentA.getText().toString());
//                    match12Map.put("matchOpponentB", tvRBMatch12OpponentB.getText().toString());
                    match12Map.put("matchOpponentA", "Rank 2");
                    match12Map.put("matchOpponentB", "Rank 3");
                    match12Map.put("matchSchedule", match12Time + " " + match12Date);
                    match12Map.put("matchOpponentAScore", "0");
                    match12Map.put("matchOpponentBScore", "0");
                    match12Map.put("matchPlayerOfTheGame", "default");
                    match12Map.put("matchWinner", "default");
                    match12Map.put("matchPlayerPoints", "0");
                    match12Map.put("matchPlayerRebounds", "0");
                    match12Map.put("matchPlayerAssist", "0");
                    match12Map.put("matchPlayerSteal", "0");
                    match12Map.put("matchPlayerBlock", "0");

                    mDatabaseMatchDetails.child("Match L").setValue(match12Map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });

                    HashMap match13Map = new HashMap();
                    match13Map.put("matchName", "Match 13");
                    match13Map.put("matchOpponentA", "Winner of Match 11");
                    match13Map.put("matchOpponentB", "Winner of Match 12");
                    match13Map.put("matchSchedule", match13Time + " " + match13Date);
                    match13Map.put("matchOpponentAScore", "0");
                    match13Map.put("matchOpponentBScore", "0");
                    match13Map.put("matchPlayerOfTheGame", "default");
                    match13Map.put("matchWinner", "default");
                    match13Map.put("matchPlayerPoints", "0");
                    match13Map.put("matchPlayerRebounds", "0");
                    match13Map.put("matchPlayerAssist", "0");
                    match13Map.put("matchPlayerSteal", "0");
                    match13Map.put("matchPlayerBlock", "0");

                    mDatabaseMatchDetails.child("Match M").setValue(match13Map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            HashMap finalNotifMap = new HashMap();
                            finalNotifMap.put("notifTitle", leagueTitle + " " + divisionCategory + " " + " Schedule has been set.");
                            finalNotifMap.put("notifMatch", leagueTitle + " " + divisionCategory + " " + "Match\n\n"
                                    + "Match 1 " + tvRBMatch1OpponentA.getText().toString() + " VS " + tvRBMatch1OpponentA.getText().toString() + "\n Scheduled : " + match1Date + ", " + match1Time + ".\n\n"
                                    + "Match 2 " + tvRBMatch2OpponentA.getText().toString() + " VS " + tvRBMatch2OpponentA.getText().toString() + "\n Scheduled : " + match2Date + ", " + match2Time + ".\n\n"
                                    + "Match 3 " + tvRBMatch3OpponentA.getText().toString() + " VS " + tvRBMatch3OpponentA.getText().toString() + "\n Scheduled : " + match3Date + ", " + match3Time + ".\n\n"
                                    + "Match 4 " + tvRBMatch4OpponentA.getText().toString() + " VS " + tvRBMatch4OpponentA.getText().toString() + "\n Scheduled : " + match4Date + ", " + match4Time + ".\n\n"
                                    + "Match 5 " + tvRBMatch5OpponentA.getText().toString() + " VS " + tvRBMatch5OpponentA.getText().toString() + "\n Scheduled : " + match5Date + ", " + match5Time + ".\n\n"
                                    + "Match 6 " + tvRBMatch6OpponentA.getText().toString() + " VS " + tvRBMatch6OpponentA.getText().toString() + "\n Scheduled : " + match6Date + ", " + match6Time + ".\n\n"
                                    + "Match 7 " + tvRBMatch7OpponentA.getText().toString() + " VS " + tvRBMatch7OpponentA.getText().toString() + "\n Scheduled : " + match7Date + ", " + match7Time + ".\n\n"
                                    + "Match 8 " + tvRBMatch8OpponentA.getText().toString() + " VS " + tvRBMatch8OpponentA.getText().toString() + "\n Scheduled : " + match8Date + ", " + match8Time + ".\n\n"
                                    + "Match 9 " + tvRBMatch9OpponentA.getText().toString() + " VS " + tvRBMatch9OpponentA.getText().toString() + "\n Scheduled : " + match9Date + ", " + match9Time + ".\n\n"
                                    + "Match 10 " + tvRBMatch10OpponentA.getText().toString() + " VS " + tvRBMatch10OpponentA.getText().toString() + "\n Scheduled : " + match10Date + ", " + match10Time + ".\n\n"
                                    + "Match 11 " + "Rank 1" + " VS " + "Rank 4" + "\n Scheduled : " + match11Date + ", " + match11Time + ".\n\n"
                                    + "Match 12 " + "Rank 2" + " VS " + "Rank 3" + "\n Scheduled : " + match12Date + ", " + match12Time + ".\n\n"
                                    + "Match 13 " + "Winner of Match 11" + " VS " + "Winner of Match 12" + "\n Scheduled : " + match13Date + ", " + match13Time + ".\n\n");
                            finalNotifMap.put("notfiDate", date);
                            finalNotifMap.put("notifKey", pushkey);

                            mDatabaseUserNotificationFinal.push().setValue(finalNotifMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(RoundRobinScheduleActivity.this, leagueTitle + " " + divisionCategory + " Schedule Saved", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });

                }// limit here
            }
        });

//        btnRBMatch2Save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        btnRBMatch3Save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        btnRBMatch4Save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        btnRBMatch5Save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        btnRBMatch6Save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        btnRBMatch7Save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        btnRBMatch8Save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        btnRBMatch9Save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        btnRBMatch10Save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    private void retrieveTeamName() {
        mDatabaseMatch.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String team1 = dataSnapshot.child("0").getValue().toString();
                    String team2 = dataSnapshot.child("1").getValue().toString();
                    String team3 = dataSnapshot.child("2").getValue().toString();
                    String team4 = dataSnapshot.child("3").getValue().toString();
                    String team5 = dataSnapshot.child("4").getValue().toString();

                    tvRBMatch1OpponentA.setText(team1);
                    tvRBMatch1OpponentB.setText(team4);

                    tvRBMatch2OpponentA.setText(team2);
                    tvRBMatch2OpponentB.setText(team3);

                    tvRBMatch3OpponentA.setText(team3);
                    tvRBMatch3OpponentB.setText(team1);

                    tvRBMatch4OpponentA.setText(team4);
                    tvRBMatch4OpponentB.setText(team5);

                    tvRBMatch5OpponentA.setText(team5);
                    tvRBMatch5OpponentB.setText(team3);

                    tvRBMatch6OpponentA.setText(team1);
                    tvRBMatch6OpponentB.setText(team2);

                    tvRBMatch7OpponentA.setText(team2);
                    tvRBMatch7OpponentB.setText(team5);

                    tvRBMatch8OpponentA.setText(team3);
                    tvRBMatch8OpponentB.setText(team4);

                    tvRBMatch9OpponentA.setText(team4);
                    tvRBMatch9OpponentB.setText(team2);

                    tvRBMatch10OpponentA.setText(team5);
                    tvRBMatch10OpponentB.setText(team1);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void matchTime() {

        tvRBMatch1Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                final SimpleDateFormat timeformat = new SimpleDateFormat("h:mm a");

                TimePickerDialog timePickerDialog = new TimePickerDialog(RoundRobinScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.set(Calendar.SECOND, 0);
                        c.set(Calendar.MILLISECOND, 0);

                        String time = timeformat.format(c.getTime());

                        tvRBMatch1Time.setText(time);
                    }
                }, mHour, mMinute, false);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        tvRBMatch2Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                final SimpleDateFormat timeformat = new SimpleDateFormat("h:mm a");

                TimePickerDialog timePickerDialog = new TimePickerDialog(RoundRobinScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.set(Calendar.SECOND, 0);
                        c.set(Calendar.MILLISECOND, 0);

                        String time = timeformat.format(c.getTime());

                        tvRBMatch2Time.setText(time);
                    }
                }, mHour, mMinute, false);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        tvRBMatch3Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                final SimpleDateFormat timeformat = new SimpleDateFormat("h:mm a");

                TimePickerDialog timePickerDialog = new TimePickerDialog(RoundRobinScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.set(Calendar.SECOND, 0);
                        c.set(Calendar.MILLISECOND, 0);

                        String time = timeformat.format(c.getTime());

                        tvRBMatch3Time.setText(time);
                    }
                }, mHour, mMinute, false);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        tvRBMatch4Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                final SimpleDateFormat timeformat = new SimpleDateFormat("h:mm a");

                TimePickerDialog timePickerDialog = new TimePickerDialog(RoundRobinScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.set(Calendar.SECOND, 0);
                        c.set(Calendar.MILLISECOND, 0);

                        String time = timeformat.format(c.getTime());

                        tvRBMatch4Time.setText(time);
                    }
                }, mHour, mMinute, false);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        tvRBMatch5Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                final SimpleDateFormat timeformat = new SimpleDateFormat("h:mm a");

                TimePickerDialog timePickerDialog = new TimePickerDialog(RoundRobinScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.set(Calendar.SECOND, 0);
                        c.set(Calendar.MILLISECOND, 0);

                        String time = timeformat.format(c.getTime());

                        tvRBMatch5Time.setText(time);
                    }
                }, mHour, mMinute, false);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        tvRBMatch6Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                final SimpleDateFormat timeformat = new SimpleDateFormat("h:mm a");

                TimePickerDialog timePickerDialog = new TimePickerDialog(RoundRobinScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.set(Calendar.SECOND, 0);
                        c.set(Calendar.MILLISECOND, 0);

                        String time = timeformat.format(c.getTime());

                        tvRBMatch6Time.setText(time);
                    }
                }, mHour, mMinute, false);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        tvRBMatch7Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                final SimpleDateFormat timeformat = new SimpleDateFormat("h:mm a");

                TimePickerDialog timePickerDialog = new TimePickerDialog(RoundRobinScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.set(Calendar.SECOND, 0);
                        c.set(Calendar.MILLISECOND, 0);

                        String time = timeformat.format(c.getTime());

                        tvRBMatch7Time.setText(time);
                    }
                }, mHour, mMinute, false);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        tvRBMatch8Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                final SimpleDateFormat timeformat = new SimpleDateFormat("h:mm a");

                TimePickerDialog timePickerDialog = new TimePickerDialog(RoundRobinScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.set(Calendar.SECOND, 0);
                        c.set(Calendar.MILLISECOND, 0);

                        String time = timeformat.format(c.getTime());

                        tvRBMatch8Time.setText(time);
                    }
                }, mHour, mMinute, false);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        tvRBMatch9Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                final SimpleDateFormat timeformat = new SimpleDateFormat("h:mm a");

                TimePickerDialog timePickerDialog = new TimePickerDialog(RoundRobinScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.set(Calendar.SECOND, 0);
                        c.set(Calendar.MILLISECOND, 0);

                        String time = timeformat.format(c.getTime());

                        tvRBMatch9Time.setText(time);
                    }
                }, mHour, mMinute, false);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        tvRBMatch10Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                final SimpleDateFormat timeformat = new SimpleDateFormat("h:mm a");

                TimePickerDialog timePickerDialog = new TimePickerDialog(RoundRobinScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.set(Calendar.SECOND, 0);
                        c.set(Calendar.MILLISECOND, 0);

                        String time = timeformat.format(c.getTime());

                        tvRBMatch10Time.setText(time);
                    }
                }, mHour, mMinute, false);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        tvRBMatch11Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                final SimpleDateFormat timeformat = new SimpleDateFormat("h:mm a");

                TimePickerDialog timePickerDialog = new TimePickerDialog(RoundRobinScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.set(Calendar.SECOND, 0);
                        c.set(Calendar.MILLISECOND, 0);

                        String time = timeformat.format(c.getTime());

                        tvRBMatch11Time.setText(time);
                    }
                }, mHour, mMinute, false);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        tvRBMatch12Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                final SimpleDateFormat timeformat = new SimpleDateFormat("h:mm a");

                TimePickerDialog timePickerDialog = new TimePickerDialog(RoundRobinScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.set(Calendar.SECOND, 0);
                        c.set(Calendar.MILLISECOND, 0);

                        String time = timeformat.format(c.getTime());

                        tvRBMatch12Time.setText(time);
                    }
                }, mHour, mMinute, false);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        tvRBMatch13Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                final SimpleDateFormat timeformat = new SimpleDateFormat("h:mm a");

                TimePickerDialog timePickerDialog = new TimePickerDialog(RoundRobinScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.set(Calendar.SECOND, 0);
                        c.set(Calendar.MILLISECOND, 0);

                        String time = timeformat.format(c.getTime());

                        tvRBMatch13Time.setText(time);
                    }
                }, mHour, mMinute, false);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

    }

    private void matchDate() {

        tvRBMatch1Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

                DatePickerDialog datePickerDialog = new DatePickerDialog(RoundRobinScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String date = dateFormat.format(c.getTime());

                        tvRBMatch1Date.setText(date);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        tvRBMatch2Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

                DatePickerDialog datePickerDialog = new DatePickerDialog(RoundRobinScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String date = dateFormat.format(c.getTime());

                        tvRBMatch2Date.setText(date);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        tvRBMatch3Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

                DatePickerDialog datePickerDialog = new DatePickerDialog(RoundRobinScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String date = dateFormat.format(c.getTime());

                        tvRBMatch3Date.setText(date);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        tvRBMatch4Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

                DatePickerDialog datePickerDialog = new DatePickerDialog(RoundRobinScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String date = dateFormat.format(c.getTime());

                        tvRBMatch4Date.setText(date);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        tvRBMatch5Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

                DatePickerDialog datePickerDialog = new DatePickerDialog(RoundRobinScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String date = dateFormat.format(c.getTime());

                        tvRBMatch5Date.setText(date);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        tvRBMatch6Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

                DatePickerDialog datePickerDialog = new DatePickerDialog(RoundRobinScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String date = dateFormat.format(c.getTime());

                        tvRBMatch6Date.setText(date);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        tvRBMatch7Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

                DatePickerDialog datePickerDialog = new DatePickerDialog(RoundRobinScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String date = dateFormat.format(c.getTime());

                        tvRBMatch7Date.setText(date);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        tvRBMatch8Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

                DatePickerDialog datePickerDialog = new DatePickerDialog(RoundRobinScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String date = dateFormat.format(c.getTime());

                        tvRBMatch8Date.setText(date);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        tvRBMatch9Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

                DatePickerDialog datePickerDialog = new DatePickerDialog(RoundRobinScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String date = dateFormat.format(c.getTime());

                        tvRBMatch9Date.setText(date);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        tvRBMatch10Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

                DatePickerDialog datePickerDialog = new DatePickerDialog(RoundRobinScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String date = dateFormat.format(c.getTime());

                        tvRBMatch10Date.setText(date);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        tvRBMatch11Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

                DatePickerDialog datePickerDialog = new DatePickerDialog(RoundRobinScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String date = dateFormat.format(c.getTime());

                        tvRBMatch11Date.setText(date);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        tvRBMatch12Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

                DatePickerDialog datePickerDialog = new DatePickerDialog(RoundRobinScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String date = dateFormat.format(c.getTime());

                        tvRBMatch12Date.setText(date);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        tvRBMatch13Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

                DatePickerDialog datePickerDialog = new DatePickerDialog(RoundRobinScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String date = dateFormat.format(c.getTime());

                        tvRBMatch13Date.setText(date);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

    }
}
