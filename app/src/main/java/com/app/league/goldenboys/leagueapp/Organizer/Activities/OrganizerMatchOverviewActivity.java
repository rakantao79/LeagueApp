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

public class OrganizerMatchOverviewActivity extends AppCompatActivity {

    private String divisionCategory;
    private String leagueTitle;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference mDatabaseUserNotification;
    private DatabaseReference mDatabaseRoundOne;
    private DatabaseReference mDatabaseMatchDetails;
    private DatabaseReference mDatabaseUserNotificationFinal;

    private TextView tvOraganizerMatchOverviewTeamA;
    private TextView tvOraganizerMatchOverviewTeamB;
    private TextView tvOraganizerMatchOverviewTeamC;
    private TextView tvOraganizerMatchOverviewTeamD;
    private TextView tvOraganizerMatchOverviewTeamE;
    private TextView tvOraganizerMatchOverviewTeamF;
    private TextView tvOraganizerMatchOverviewTeamG;
    private TextView tvOraganizerMatchOverviewTeamH;

    private TextView tvMatchOneSched;
    private TextView tvMatchTwoSched;
    private TextView tvMatchThreeSched;
    private TextView tvMatchFourSched;


    private EditText etMatch1SchedDate;
    private EditText etMatch1SchedTime;
    private EditText etMatch2SchedDate;
    private EditText etMatch2SchedTime;
    private EditText etMatch3SchedDate;
    private EditText etMatch3SchedTime;
    private EditText etMatch4SchedDate;
    private EditText etMatch4SchedTime;

    private EditText etMatch5SchedDate;
    private EditText etMatch5SchedTime;
    private EditText etMatch6SchedDate;
    private EditText etMatch6SchedTime;
    private EditText etMatch7SchedDate;
    private EditText etMatch7SchedTime;

    private long countpost;
    private long notifcount;

    private Button btnRoundOneSaveSched;
    private Button btnMach1SaveSchedule;
    private Button btnMach2SaveSchedule;
    private Button btnMach3SaveSchedule;
    private Button btnMach4SaveSchedule;
    private Button btnMach5SaveSchedule;
    private Button btnMach6SaveSchedule;
    private Button btnMach7SaveSchedule;

    private String date;
    private String pushkey;
    private String matchKey;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_match_overview);

        divisionCategory = getIntent().getStringExtra("divisionCategory");
        leagueTitle = getIntent().getStringExtra("leagueTitle");

        mDatabaseRoundOne = FirebaseDatabase.getInstance().getReference().child("LeagueEntries").child(leagueTitle).child(divisionCategory).child("RoundOneMatchDetails");
        mDatabaseUserNotification = FirebaseDatabase.getInstance().getReference().child("UserNotification").push();
        mDatabaseUserNotificationFinal = FirebaseDatabase.getInstance().getReference().child("UserNotificationFinal").push();
        pushkey = mDatabaseUserNotificationFinal.getKey();
        mDatabaseMatchDetails = FirebaseDatabase.getInstance().getReference().child("LeagueEntries").child(leagueTitle).child(divisionCategory).child("MatchList");


        DateFormat df = new SimpleDateFormat("yyyy/MM/dd h:mm a");
        date = df.format(Calendar.getInstance().getTime());

        tvOraganizerMatchOverviewTeamA = findViewById(R.id.tvOraganizerMatchOverviewTeamA);
        tvOraganizerMatchOverviewTeamB = findViewById(R.id.tvOraganizerMatchOverviewTeamB);
        tvOraganizerMatchOverviewTeamC = findViewById(R.id.tvOraganizerMatchOverviewTeamC);
        tvOraganizerMatchOverviewTeamD = findViewById(R.id.tvOraganizerMatchOverviewTeamD);
        tvOraganizerMatchOverviewTeamE = findViewById(R.id.tvOraganizerMatchOverviewTeamE);
        tvOraganizerMatchOverviewTeamF = findViewById(R.id.tvOraganizerMatchOverviewTeamF);
        tvOraganizerMatchOverviewTeamG = findViewById(R.id.tvOraganizerMatchOverviewTeamG);
        tvOraganizerMatchOverviewTeamH = findViewById(R.id.tvOraganizerMatchOverviewTeamH);

        etMatch1SchedDate = findViewById(R.id.etMatch1SchedDate);
        etMatch1SchedTime = findViewById(R.id.etMatch1SchedTime);
        etMatch2SchedDate = findViewById(R.id.etMatcH2SchedDate);
        etMatch2SchedTime = findViewById(R.id.etMatcH2SchedTime);
        etMatch3SchedDate = findViewById(R.id.etMatcH3SchedDate);
        etMatch3SchedTime = findViewById(R.id.etMatcH3SchedTime);
        etMatch4SchedDate = findViewById(R.id.etMatcH4SchedDate);
        etMatch4SchedTime = findViewById(R.id.etMatcH4SchedTime);

        etMatch5SchedDate = findViewById(R.id.etMatcH5SchedDate);
        etMatch5SchedTime = findViewById(R.id.etMatcH5SchedTime);
        etMatch6SchedDate = findViewById(R.id.etMatcH6SchedDate);
        etMatch6SchedTime = findViewById(R.id.etMatcH6SchedTime);
        etMatch7SchedDate = findViewById(R.id.etMatcH7SchedDate);
        etMatch7SchedTime = findViewById(R.id.etMatcH7SchedTime);


        tvMatchOneSched = findViewById(R.id.tvMatchOneSched);
        tvMatchTwoSched = findViewById(R.id.tvMatchTwoSched);
        tvMatchThreeSched = findViewById(R.id.tvMatchThreeSched);
        tvMatchFourSched = findViewById(R.id.tvMatchFourSched);

        mDatabaseUserNotification.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    notifcount = dataSnapshot.getChildrenCount();
                } else {
                    notifcount = 0;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabaseRoundOne.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    tvMatchOneSched.setText(" Schedule :" + dataSnapshot.child("match1Sched").getValue().toString());
                    tvMatchTwoSched.setText(" Schedule :" + dataSnapshot.child("match2Sched").getValue().toString());
                    tvMatchThreeSched.setText(" Schedule :" + dataSnapshot.child("match3Sched").getValue().toString());
                    tvMatchFourSched.setText(" Schedule :" + dataSnapshot.child("match4Sched").getValue().toString());
                } else {
                    tvMatchOneSched.setText(" Schedule not Assigned");
                    tvMatchTwoSched.setText(" Schedule not Assigned");
                    tvMatchThreeSched.setText(" Schedule not Assigned");
                    tvMatchFourSched.setText(" Schedule not Assigned");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //btnRoundOneSaveSched = findViewById(R.id.btnRoundOneSaveSched);
        btnMach1SaveSchedule = findViewById(R.id.btnMatch1SaveSchedule);
        btnMach2SaveSchedule = findViewById(R.id.btnMatch2SaveSchedule);
        btnMach3SaveSchedule = findViewById(R.id.btnMatch3SaveSchedule);
        btnMach4SaveSchedule = findViewById(R.id.btnMatch4SaveSchedule);
        btnMach5SaveSchedule = findViewById(R.id.btnMatch5SaveSchedule);
        btnMach6SaveSchedule = findViewById(R.id.btnMatch6SaveSchedule);
        btnMach7SaveSchedule = findViewById(R.id.btnMatch7SaveSchedule);

        btnMach1SaveSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                match1saveSched();
            }
        });

        btnMach2SaveSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //match2saveSched();
            }
        });

        btnMach3SaveSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //match3saveSched();
            }
        });

        btnMach4SaveSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //match4saveSched();
            }
        });

        btnMach5SaveSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //match5saveSched();
            }
        });

        btnMach6SaveSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //match6saveSched();
            }
        });

        btnMach7SaveSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //match7saveSched();
            }
        });



//        btnRoundOneSaveSched.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                saveSched();
//            }
//        });

        etMatch1SchedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

                //final String month_name = month_date.format(c.getTime());

                DatePickerDialog datePickerDialog = new DatePickerDialog(OrganizerMatchOverviewActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        //mSchedDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String date = dateFormat.format(c.getTime());

                        etMatch1SchedDate.setText(date);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        etMatch1SchedTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                final SimpleDateFormat timeformat = new SimpleDateFormat("h:mm a");
                //final String time = simpleDateFormat.format(c.getTime());


                TimePickerDialog timePickerDialog = new TimePickerDialog(OrganizerMatchOverviewActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        //mSchedTime.setText(hourOfDay + ":" + minute);
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.set(Calendar.SECOND, 0);
                        c.set(Calendar.MILLISECOND, 0);

                        String time = timeformat.format(c.getTime());

                        etMatch1SchedTime.setText(time);
                    }
                }, mHour, mMinute, false);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        etMatch2SchedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

                //final String month_name = month_date.format(c.getTime());

                DatePickerDialog datePickerDialog = new DatePickerDialog(OrganizerMatchOverviewActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        //mSchedDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String date = dateFormat.format(c.getTime());

                        etMatch2SchedDate.setText(date);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        etMatch3SchedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

                //final String month_name = month_date.format(c.getTime());

                DatePickerDialog datePickerDialog = new DatePickerDialog(OrganizerMatchOverviewActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        //mSchedDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String date = dateFormat.format(c.getTime());

                        etMatch3SchedDate.setText(date);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        etMatch4SchedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

                //final String month_name = month_date.format(c.getTime());

                DatePickerDialog datePickerDialog = new DatePickerDialog(OrganizerMatchOverviewActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        //mSchedDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String date = dateFormat.format(c.getTime());

                        etMatch4SchedDate.setText(date);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        etMatch5SchedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

                //final String month_name = month_date.format(c.getTime());

                DatePickerDialog datePickerDialog = new DatePickerDialog(OrganizerMatchOverviewActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        //mSchedDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String date = dateFormat.format(c.getTime());

                        etMatch5SchedDate.setText(date);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        etMatch6SchedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

                //final String month_name = month_date.format(c.getTime());

                DatePickerDialog datePickerDialog = new DatePickerDialog(OrganizerMatchOverviewActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        //mSchedDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String date = dateFormat.format(c.getTime());

                        etMatch6SchedDate.setText(date);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        etMatch7SchedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

                //final String month_name = month_date.format(c.getTime());

                DatePickerDialog datePickerDialog = new DatePickerDialog(OrganizerMatchOverviewActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        //mSchedDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String date = dateFormat.format(c.getTime());

                        etMatch7SchedDate.setText(date);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        etMatch2SchedTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                final SimpleDateFormat timeformat = new SimpleDateFormat("h:mm a");
                //final String time = simpleDateFormat.format(c.getTime());


                TimePickerDialog timePickerDialog = new TimePickerDialog(OrganizerMatchOverviewActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        //mSchedTime.setText(hourOfDay + ":" + minute);
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.set(Calendar.SECOND, 0);
                        c.set(Calendar.MILLISECOND, 0);

                        String time = timeformat.format(c.getTime());

                        etMatch2SchedTime.setText(time);
                    }
                }, mHour, mMinute, false);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        etMatch3SchedTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                final SimpleDateFormat timeformat = new SimpleDateFormat("h:mm a");
                //final String time = simpleDateFormat.format(c.getTime());


                TimePickerDialog timePickerDialog = new TimePickerDialog(OrganizerMatchOverviewActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        //mSchedTime.setText(hourOfDay + ":" + minute);
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.set(Calendar.SECOND, 0);
                        c.set(Calendar.MILLISECOND, 0);

                        String time = timeformat.format(c.getTime());

                        etMatch3SchedTime.setText(time);
                    }
                }, mHour, mMinute, false);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        etMatch4SchedTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                final SimpleDateFormat timeformat = new SimpleDateFormat("h:mm a");
                //final String time = simpleDateFormat.format(c.getTime());


                TimePickerDialog timePickerDialog = new TimePickerDialog(OrganizerMatchOverviewActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        //mSchedTime.setText(hourOfDay + ":" + minute);
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.set(Calendar.SECOND, 0);
                        c.set(Calendar.MILLISECOND, 0);

                        String time = timeformat.format(c.getTime());

                        etMatch4SchedTime.setText(time);
                    }
                }, mHour, mMinute, false);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        etMatch5SchedTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                final SimpleDateFormat timeformat = new SimpleDateFormat("h:mm a");
                //final String time = simpleDateFormat.format(c.getTime());


                TimePickerDialog timePickerDialog = new TimePickerDialog(OrganizerMatchOverviewActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        //mSchedTime.setText(hourOfDay + ":" + minute);
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.set(Calendar.SECOND, 0);
                        c.set(Calendar.MILLISECOND, 0);

                        String time = timeformat.format(c.getTime());

                        etMatch5SchedTime.setText(time);
                    }
                }, mHour, mMinute, false);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        etMatch6SchedTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                final SimpleDateFormat timeformat = new SimpleDateFormat("h:mm a");
                //final String time = simpleDateFormat.format(c.getTime());


                TimePickerDialog timePickerDialog = new TimePickerDialog(OrganizerMatchOverviewActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        //mSchedTime.setText(hourOfDay + ":" + minute);
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.set(Calendar.SECOND, 0);
                        c.set(Calendar.MILLISECOND, 0);

                        String time = timeformat.format(c.getTime());

                        etMatch6SchedTime.setText(time);
                    }
                }, mHour, mMinute, false);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        etMatch7SchedTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                final SimpleDateFormat timeformat = new SimpleDateFormat("h:mm a");
                //final String time = simpleDateFormat.format(c.getTime());


                TimePickerDialog timePickerDialog = new TimePickerDialog(OrganizerMatchOverviewActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        //mSchedTime.setText(hourOfDay + ":" + minute);
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.set(Calendar.SECOND, 0);
                        c.set(Calendar.MILLISECOND, 0);

                        String time = timeformat.format(c.getTime());

                        etMatch7SchedTime.setText(time);
                    }
                }, mHour, mMinute, false);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });



        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("LeagueEntries").child(leagueTitle).child(divisionCategory).child("EightTeamMatch");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    tvOraganizerMatchOverviewTeamA.setText(dataSnapshot.child("0").getValue().toString());
                    tvOraganizerMatchOverviewTeamB.setText(dataSnapshot.child("1").getValue().toString());
                    tvOraganizerMatchOverviewTeamC.setText(dataSnapshot.child("2").getValue().toString());
                    tvOraganizerMatchOverviewTeamD.setText(dataSnapshot.child("3").getValue().toString());
                    tvOraganizerMatchOverviewTeamE.setText(dataSnapshot.child("4").getValue().toString());
                    tvOraganizerMatchOverviewTeamF.setText(dataSnapshot.child("5").getValue().toString());
                    tvOraganizerMatchOverviewTeamG.setText(dataSnapshot.child("6").getValue().toString());
                    tvOraganizerMatchOverviewTeamH.setText(dataSnapshot.child("7").getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void match7saveSched() {

        final String match7schedDate = etMatch7SchedDate.getText().toString().trim();
        final String match7schedTime = etMatch7SchedTime.getText().toString().trim();

        if (TextUtils.isEmpty(match7schedDate) || TextUtils.isEmpty(match7schedTime)) {
            Toast.makeText(this, "Please don't leave fields empty", Toast.LENGTH_SHORT).show();
        } else {

            HashMap match7Map = new HashMap();
            match7Map.put("matchName", "Match 7");
            match7Map.put("matchOpponentA", "Winner Match 5");
            match7Map.put("matchOpponentB", "Winner Match 6");
            match7Map.put("matchSchedule", match7schedDate + " " + match7schedTime);
            match7Map.put("matchOpponentAScore", "0");
            match7Map.put("matchOpponentBScore", "0");
            match7Map.put("matchPlayerOfTheGame", "default");
            match7Map.put("matchWinner", "default");
            match7Map.put("matchPlayerPoints", "0");
            match7Map.put("matchPlayerRebounds", "0");
            match7Map.put("matchPlayerAssist", "0");
            match7Map.put("matchPlayerSteal", "0");
            match7Map.put("matchPlayerBlock", "0");

            mDatabaseMatchDetails.child("Match7").setValue(match7Map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    HashMap finalNotifMap = new HashMap();
                    finalNotifMap.put("notifTitle", "Match 5 Schedule has been set");
                    finalNotifMap.put("notifMatch", "Winner Match 5 VS Winner Match 6");
                    finalNotifMap.put("notfiDate", date);
                    finalNotifMap.put("notifKey", pushkey);

                    mDatabaseUserNotificationFinal.setValue(finalNotifMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(OrganizerMatchOverviewActivity.this, "Match 7 Schedule Save", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }

    }

    private void match6saveSched() {

        final String match6schedDate = etMatch6SchedDate.getText().toString().trim();
        final String match6schedTime = etMatch6SchedTime.getText().toString().trim();

        if (TextUtils.isEmpty(match6schedDate) || TextUtils.isEmpty(match6schedTime)) {
            Toast.makeText(this, "Please don't leave fields empty", Toast.LENGTH_SHORT).show();
        } else {

            HashMap match6Map = new HashMap();
            match6Map.put("matchName", "Match 6");
            match6Map.put("matchOpponentA", "Winner Match 3");
            match6Map.put("matchOpponentB", "Winner Match 4");
            match6Map.put("matchSchedule", match6schedDate + " " + match6schedTime);
            match6Map.put("matchOpponentAScore", "0");
            match6Map.put("matchOpponentBScore", "0");
            match6Map.put("matchPlayerOfTheGame", "default");
            match6Map.put("matchWinner", "default");
            match6Map.put("matchPlayerPoints", "0");
            match6Map.put("matchPlayerRebounds", "0");
            match6Map.put("matchPlayerAssist", "0");
            match6Map.put("matchPlayerSteal", "0");
            match6Map.put("matchPlayerBlock", "0");

            mDatabaseMatchDetails.child("Match6").setValue(match6Map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    HashMap finalNotifMap = new HashMap();
                    finalNotifMap.put("notifTitle", "Match 5 Schedule has been set");
                    finalNotifMap.put("notifMatch", "Winner Match 3 VS Winner Match 4");
                    finalNotifMap.put("notfiDate", date);
                    finalNotifMap.put("notifKey", pushkey);

                    mDatabaseUserNotificationFinal.setValue(finalNotifMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(OrganizerMatchOverviewActivity.this, "Match 6 Schedule Save", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }

    private void match5saveSched() {
        final String match5schedDate = etMatch5SchedDate.getText().toString().trim();
        final String match5schedTime = etMatch5SchedTime.getText().toString().trim();

        if (TextUtils.isEmpty(match5schedDate) || TextUtils.isEmpty(match5schedTime)) {
            Toast.makeText(this, "Please don't leave fields empty", Toast.LENGTH_SHORT).show();
        } else {

            HashMap match5Map = new HashMap();
            match5Map.put("matchName", "Match 5");
            match5Map.put("matchOpponentA", "Winner Match 1");
            match5Map.put("matchOpponentB", "Winner Match 2");
            match5Map.put("matchSchedule", match5schedDate + " " + match5schedTime);
            match5Map.put("matchOpponentAScore", "0");
            match5Map.put("matchOpponentBScore", "0");
            match5Map.put("matchPlayerOfTheGame", "default");
            match5Map.put("matchWinner", "default");
            match5Map.put("matchPlayerPoints", "0");
            match5Map.put("matchPlayerRebounds", "0");
            match5Map.put("matchPlayerAssist", "0");
            match5Map.put("matchPlayerSteal", "0");
            match5Map.put("matchPlayerBlock", "0");

            mDatabaseMatchDetails.child("Match5").setValue(match5Map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    HashMap finalNotifMap = new HashMap();
                    finalNotifMap.put("notifTitle", "Match 5 Schedule has been set");
                    finalNotifMap.put("notifMatch", "Winner Match 1 VS Winner Match 2");
                    finalNotifMap.put("notfiDate", date);
                    finalNotifMap.put("notifKey", pushkey);

                    mDatabaseUserNotificationFinal.setValue(finalNotifMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(OrganizerMatchOverviewActivity.this, "Match 5 Schedule Save", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }

    private void match4saveSched() {
        final String match4schedDate = etMatch4SchedDate.getText().toString().trim();
        final String match4schedTime = etMatch4SchedTime.getText().toString().trim();

        if (TextUtils.isEmpty(match4schedDate) || TextUtils.isEmpty(match4schedTime)) {
            Toast.makeText(this, "Please don't leave fields empty", Toast.LENGTH_SHORT).show();
        } else {

            HashMap match4Map = new HashMap();
            match4Map.put("matchName", "Match 4");
            match4Map.put("matchOpponentA", tvOraganizerMatchOverviewTeamG.getText().toString());
            match4Map.put("matchOpponentB", tvOraganizerMatchOverviewTeamH.getText().toString());
            match4Map.put("matchSchedule", match4schedDate + " " + match4schedTime);
            match4Map.put("matchOpponentAScore", "0");
            match4Map.put("matchOpponentBScore", "0");
            match4Map.put("matchPlayerOfTheGame", "default");
            match4Map.put("matchWinner", "default");
            match4Map.put("matchPlayerPoints", "0");
            match4Map.put("matchPlayerRebounds", "0");
            match4Map.put("matchPlayerAssist", "0");
            match4Map.put("matchPlayerSteal", "0");
            match4Map.put("matchPlayerBlock", "0");

            mDatabaseMatchDetails.child("Match4").setValue(match4Map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    HashMap finalNotifMap = new HashMap();
                    finalNotifMap.put("notifTitle", "Match 4 Schedule has been set");
                    finalNotifMap.put("notifMatch", tvOraganizerMatchOverviewTeamG.getText().toString() + " VS " + tvOraganizerMatchOverviewTeamH.getText().toString());
                    finalNotifMap.put("notfiDate", date);
                    finalNotifMap.put("notifKey", pushkey);

                    mDatabaseUserNotificationFinal.setValue(finalNotifMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(OrganizerMatchOverviewActivity.this, "Match 4 Schedule Save", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }

    private void match3saveSched() {
        final String match3schedDate = etMatch3SchedDate.getText().toString().trim();
        final String match3schedTime = etMatch3SchedTime.getText().toString().trim();

        if (TextUtils.isEmpty(match3schedDate) || TextUtils.isEmpty(match3schedTime)) {
            Toast.makeText(this, "Please don't leave fields empty", Toast.LENGTH_SHORT).show();
        } else {

            HashMap match3Map = new HashMap();
            match3Map.put("matchName", "Match 3");
            match3Map.put("matchOpponentA", tvOraganizerMatchOverviewTeamE.getText().toString());
            match3Map.put("matchOpponentB", tvOraganizerMatchOverviewTeamF.getText().toString());
            match3Map.put("matchSchedule", match3schedDate + " " + match3schedTime);
            match3Map.put("matchOpponentAScore", "0");
            match3Map.put("matchOpponentBScore", "0");
            match3Map.put("matchPlayerOfTheGame", "default");
            match3Map.put("matchWinner", "default");
            match3Map.put("matchPlayerPoints", "0");
            match3Map.put("matchPlayerRebounds", "0");
            match3Map.put("matchPlayerAssist", "0");
            match3Map.put("matchPlayerSteal", "0");
            match3Map.put("matchPlayerBlock", "0");

            mDatabaseMatchDetails.child("Match3").setValue(match3Map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    HashMap finalNotifMap = new HashMap();
                    finalNotifMap.put("notifTitle", "Match 3 Schedule has been set");
                    finalNotifMap.put("notifMatch", tvOraganizerMatchOverviewTeamE.getText().toString() + " VS " + tvOraganizerMatchOverviewTeamF.getText().toString());
                    finalNotifMap.put("notfiDate", date);
                    finalNotifMap.put("notifKey", pushkey);

                    mDatabaseUserNotificationFinal.setValue(finalNotifMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(OrganizerMatchOverviewActivity.this, "Match 3 Schedule Save", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }

    private void match2saveSched() {
        final String match2schedDate = etMatch2SchedDate.getText().toString().trim();
        final String match2schedTime = etMatch2SchedTime.getText().toString().trim();

        if (TextUtils.isEmpty(match2schedDate) || TextUtils.isEmpty(match2schedTime)) {
            Toast.makeText(this, "Please don't leave fields empty", Toast.LENGTH_SHORT).show();
        } else {

            HashMap match2Map = new HashMap();
            match2Map.put("matchName", "Match 2");
            match2Map.put("matchOpponentA", tvOraganizerMatchOverviewTeamC.getText().toString());
            match2Map.put("matchOpponentB", tvOraganizerMatchOverviewTeamD.getText().toString());
            match2Map.put("matchSchedule", match2schedDate + " " + match2schedTime);
            match2Map.put("matchOpponentAScore", "0");
            match2Map.put("matchOpponentBScore", "0");
            match2Map.put("matchPlayerOfTheGame", "default");
            match2Map.put("matchWinner", "default");
            match2Map.put("matchPlayerPoints", "0");
            match2Map.put("matchPlayerRebounds", "0");
            match2Map.put("matchPlayerAssist", "0");
            match2Map.put("matchPlayerSteal", "0");
            match2Map.put("matchPlayerBlock", "0");

            mDatabaseMatchDetails.child("Match2").setValue(match2Map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    HashMap finalNotifMap = new HashMap();
                    finalNotifMap.put("notifTitle", "Match 2 Schedule has been set");
                    finalNotifMap.put("notifMatch", tvOraganizerMatchOverviewTeamC.getText().toString() + " VS " + tvOraganizerMatchOverviewTeamD.getText().toString());
                    finalNotifMap.put("notfiDate", date);
                    finalNotifMap.put("notifKey", pushkey);

                    mDatabaseUserNotificationFinal.setValue(finalNotifMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(OrganizerMatchOverviewActivity.this, "Match 2 Schedule Save", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }

    private void match1saveSched() {

        final String match1schedDate = etMatch1SchedDate.getText().toString().trim();
        final String match1schedTime = etMatch1SchedTime.getText().toString().trim();
        final String match2schedDate = etMatch2SchedDate.getText().toString().trim();
        final String match2schedTime = etMatch2SchedTime.getText().toString().trim();
        final String match3schedDate = etMatch3SchedDate.getText().toString().trim();
        final String match3schedTime = etMatch3SchedTime.getText().toString().trim();
        final String match4schedDate = etMatch4SchedDate.getText().toString().trim();
        final String match4schedTime = etMatch4SchedTime.getText().toString().trim();
        final String match5schedDate = etMatch5SchedDate.getText().toString().trim();
        final String match5schedTime = etMatch5SchedTime.getText().toString().trim();
        final String match6schedDate = etMatch6SchedDate.getText().toString().trim();
        final String match6schedTime = etMatch6SchedTime.getText().toString().trim();
        final String match7schedDate = etMatch7SchedDate.getText().toString().trim();
        final String match7schedTime = etMatch7SchedTime.getText().toString().trim();

        if (TextUtils.isEmpty(match1schedDate) || TextUtils.isEmpty(match1schedTime) ||
                TextUtils.isEmpty(match2schedDate) || TextUtils.isEmpty(match2schedTime)||
                TextUtils.isEmpty(match3schedDate) || TextUtils.isEmpty(match3schedTime)||
                TextUtils.isEmpty(match4schedDate) || TextUtils.isEmpty(match4schedTime)||
                TextUtils.isEmpty(match5schedDate) || TextUtils.isEmpty(match5schedTime)||
                TextUtils.isEmpty(match6schedDate) || TextUtils.isEmpty(match6schedTime)||
                TextUtils.isEmpty(match7schedDate) || TextUtils.isEmpty(match7schedTime)) {

            Toast.makeText(this, "Please don't leave fields empty", Toast.LENGTH_SHORT).show();

        } else {

            HashMap match1Map = new HashMap();
            match1Map.put("matchName", "Match 1");
            match1Map.put("matchOpponentA", tvOraganizerMatchOverviewTeamA.getText().toString());
            match1Map.put("matchOpponentB", tvOraganizerMatchOverviewTeamB.getText().toString());
            match1Map.put("matchSchedule", match1schedDate + " " + match1schedTime);
            match1Map.put("matchOpponentAScore", "0");
            match1Map.put("matchOpponentBScore", "0");
            match1Map.put("matchPlayerOfTheGame", "default");
            match1Map.put("matchWinner", "default");
            match1Map.put("matchPlayerPoints", "0");
            match1Map.put("matchPlayerRebounds", "0");
            match1Map.put("matchPlayerAssist", "0");
            match1Map.put("matchPlayerSteal", "0");
            match1Map.put("matchPlayerBlock", "0");

            mDatabaseMatchDetails.child("Match1").setValue(match1Map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    HashMap finalNotifMap = new HashMap();
                    finalNotifMap.put("notifTitle", leagueTitle + " " + divisionCategory + " Schedule has been set" );
                    finalNotifMap.put("notifMatch", leagueTitle + " " + divisionCategory + " " + "Match\n\n"
                            + "Match 1 : " + tvOraganizerMatchOverviewTeamA.getText().toString() + " VS " + tvOraganizerMatchOverviewTeamB.getText().toString() + "\nScheduled : " + match1schedDate + ", " + match1schedTime + ".\n\n"
                            + "Match 2 : " + tvOraganizerMatchOverviewTeamC.getText().toString() + " VS " + tvOraganizerMatchOverviewTeamD.getText().toString() + "\nScheduled : " + match2schedDate + ", " + match2schedTime + ".\n\n"
                            + "Match 3 : " + tvOraganizerMatchOverviewTeamE.getText().toString() + " VS " + tvOraganizerMatchOverviewTeamF.getText().toString() + "\nScheduled : " + match3schedDate + ", " + match3schedTime + ".\n\n"
                            + "Match 4 : " + tvOraganizerMatchOverviewTeamG.getText().toString() + " VS " + tvOraganizerMatchOverviewTeamH.getText().toString() + "\nScheduled : " + match4schedDate + ", " + match4schedTime + ".\n\n"
                            + "Match 5 : " + "Winnder MATCH 1" + " VS " + "WINNER MATCH 2" + "\nScheduled : " + match5schedDate + ", " + match5schedTime + ".\n\n"
                            + "Match 6 : " + "Winnder MATCH 3" + " VS " + "WINNER MATCH 4" + "\nScheduled : " + match6schedDate + ", " + match6schedTime + ".\n\n"
                            + "Match 7 : " + "Winnder MATCH 5" + " VS " + "WINNER MATCH 6" + "\nScheduled : " + match7schedDate + ", " + match7schedTime + ".\n\n"
                    );
                    finalNotifMap.put("notfiDate", date);
                    finalNotifMap.put("notifKey", pushkey);

                    mDatabaseUserNotificationFinal.setValue(finalNotifMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(OrganizerMatchOverviewActivity.this, leagueTitle + " " + divisionCategory + " Schedule has been set" , Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

            //-------------------- para di kita malawa ------------------------//

            HashMap match2Map = new HashMap();
            match2Map.put("matchName", "Match 2");
            match2Map.put("matchOpponentA", tvOraganizerMatchOverviewTeamC.getText().toString());
            match2Map.put("matchOpponentB", tvOraganizerMatchOverviewTeamD.getText().toString());
            match2Map.put("matchSchedule", match2schedDate + " " + match2schedTime);
            match2Map.put("matchOpponentAScore", "0");
            match2Map.put("matchOpponentBScore", "0");
            match2Map.put("matchPlayerOfTheGame", "default");
            match2Map.put("matchWinner", "default");
            match2Map.put("matchPlayerPoints", "0");
            match2Map.put("matchPlayerRebounds", "0");
            match2Map.put("matchPlayerAssist", "0");
            match2Map.put("matchPlayerSteal", "0");
            match2Map.put("matchPlayerBlock", "0");

            mDatabaseMatchDetails.child("Match2").setValue(match2Map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {


                }
            });

            //-------------------- para di kita malawa ------------------------//

            HashMap match3Map = new HashMap();
            match3Map.put("matchName", "Match 3");
            match3Map.put("matchOpponentA", tvOraganizerMatchOverviewTeamE.getText().toString());
            match3Map.put("matchOpponentB", tvOraganizerMatchOverviewTeamF.getText().toString());
            match3Map.put("matchSchedule", match3schedDate + " " + match3schedTime);
            match3Map.put("matchOpponentAScore", "0");
            match3Map.put("matchOpponentBScore", "0");
            match3Map.put("matchPlayerOfTheGame", "default");
            match3Map.put("matchWinner", "default");
            match3Map.put("matchPlayerPoints", "0");
            match3Map.put("matchPlayerRebounds", "0");
            match3Map.put("matchPlayerAssist", "0");
            match3Map.put("matchPlayerSteal", "0");
            match3Map.put("matchPlayerBlock", "0");

            mDatabaseMatchDetails.child("Match3").setValue(match3Map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                }
            });

            //-------------------- para di kita malawa ------------------------//

            HashMap match4Map = new HashMap();
            match4Map.put("matchName", "Match 4");
            match4Map.put("matchOpponentA", tvOraganizerMatchOverviewTeamG.getText().toString());
            match4Map.put("matchOpponentB", tvOraganizerMatchOverviewTeamH.getText().toString());
            match4Map.put("matchSchedule", match4schedDate + " " + match4schedTime);
            match4Map.put("matchOpponentAScore", "0");
            match4Map.put("matchOpponentBScore", "0");
            match4Map.put("matchPlayerOfTheGame", "default");
            match4Map.put("matchWinner", "default");
            match4Map.put("matchPlayerPoints", "0");
            match4Map.put("matchPlayerRebounds", "0");
            match4Map.put("matchPlayerAssist", "0");
            match4Map.put("matchPlayerSteal", "0");
            match4Map.put("matchPlayerBlock", "0");

            mDatabaseMatchDetails.child("Match4").setValue(match4Map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    HashMap finalNotifMap = new HashMap();
                    finalNotifMap.put("notifTitle", "Match 4 Schedule has been set");
                    finalNotifMap.put("notifMatch", tvOraganizerMatchOverviewTeamG.getText().toString() + " VS " + tvOraganizerMatchOverviewTeamH.getText().toString());
                    finalNotifMap.put("notfiDate", date);
                    finalNotifMap.put("notifKey", pushkey);

                    mDatabaseUserNotificationFinal.setValue(finalNotifMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(OrganizerMatchOverviewActivity.this, "Match 4 Schedule Save", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

            //-------------------- para di kita malawa ------------------------//

            HashMap match5Map = new HashMap();
            match5Map.put("matchName", "Match 5");
            match5Map.put("matchOpponentA", "Winner Match 1");
            match5Map.put("matchOpponentB", "Winner Match 2");
            match5Map.put("matchSchedule", match5schedDate + " " + match5schedTime);
            match5Map.put("matchOpponentAScore", "0");
            match5Map.put("matchOpponentBScore", "0");
            match5Map.put("matchPlayerOfTheGame", "default");
            match5Map.put("matchWinner", "default");
            match5Map.put("matchPlayerPoints", "0");
            match5Map.put("matchPlayerRebounds", "0");
            match5Map.put("matchPlayerAssist", "0");
            match5Map.put("matchPlayerSteal", "0");
            match5Map.put("matchPlayerBlock", "0");

            mDatabaseMatchDetails.child("Match5").setValue(match5Map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                }
            });

            //-------------------- para di kita malawa ------------------------//

            HashMap match6Map = new HashMap();
            match6Map.put("matchName", "Match 6");
            match6Map.put("matchOpponentA", "Winner Match 3");
            match6Map.put("matchOpponentB", "Winner Match 4");
            match6Map.put("matchSchedule", match6schedDate + " " + match6schedTime);
            match6Map.put("matchOpponentAScore", "0");
            match6Map.put("matchOpponentBScore", "0");
            match6Map.put("matchPlayerOfTheGame", "default");
            match6Map.put("matchWinner", "default");
            match6Map.put("matchPlayerPoints", "0");
            match6Map.put("matchPlayerRebounds", "0");
            match6Map.put("matchPlayerAssist", "0");
            match6Map.put("matchPlayerSteal", "0");
            match6Map.put("matchPlayerBlock", "0");

            mDatabaseMatchDetails.child("Match6").setValue(match6Map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                }
            });

            //-------------------- para di kita malawa ------------------------//

            HashMap match7Map = new HashMap();
            match7Map.put("matchName", "Match 7");
            match7Map.put("matchOpponentA", "Winner Match 5");
            match7Map.put("matchOpponentB", "Winner Match 6");
            match7Map.put("matchSchedule", match7schedDate + " " + match7schedTime);
            match7Map.put("matchOpponentAScore", "0");
            match7Map.put("matchOpponentBScore", "0");
            match7Map.put("matchPlayerOfTheGame", "default");
            match7Map.put("matchWinner", "default");
            match7Map.put("matchPlayerPoints", "0");
            match7Map.put("matchPlayerRebounds", "0");
            match7Map.put("matchPlayerAssist", "0");
            match7Map.put("matchPlayerSteal", "0");
            match7Map.put("matchPlayerBlock", "0");

            mDatabaseMatchDetails.child("Match7").setValue(match7Map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                }
            });

            //-------------------- para di kita malawa ------------------------//
        }
    }

    private void saveSched() {

        final String match1schedDate = etMatch1SchedDate.getText().toString().trim();
        final String match1schedTime = etMatch1SchedTime.getText().toString().trim();
        final String match2schedDate = etMatch2SchedDate.getText().toString().trim();
        final String match2schedTime = etMatch2SchedTime.getText().toString().trim();
        final String match3schedDate = etMatch3SchedDate.getText().toString().trim();
        final String match3schedTime = etMatch3SchedTime.getText().toString().trim();
        final String match4schedDate = etMatch4SchedDate.getText().toString().trim();
        final String match4schedTime = etMatch4SchedTime.getText().toString().trim();


        if (TextUtils.isEmpty(match1schedDate) || TextUtils.isEmpty(match1schedTime) ||
                TextUtils.isEmpty(match2schedDate) || TextUtils.isEmpty(match2schedTime) ||
                TextUtils.isEmpty(match3schedDate) || TextUtils.isEmpty(match3schedTime) ||
                TextUtils.isEmpty(match4schedDate) || TextUtils.isEmpty(match4schedTime)) {

            Toast.makeText(this, "Please don't leave fields empty", Toast.LENGTH_SHORT).show();

        } else if (match1schedTime.equals(match2schedTime)) {
            Toast.makeText(this, "Conflict Schedule", Toast.LENGTH_SHORT).show();

        } else if (match1schedTime.equals(match3schedTime)) {
            Toast.makeText(this, "Conflict Schedule", Toast.LENGTH_SHORT).show();
        } else if (match1schedTime.equals(match4schedDate)) {
            Toast.makeText(this, "Conflict Schedule", Toast.LENGTH_SHORT).show();
        } else {

            mDatabaseUserNotification.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        countpost = dataSnapshot.getChildrenCount();
                    } else {
                        countpost = 0;
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            final HashMap roundOneSchedMap = new HashMap();
            roundOneSchedMap.put("Match 1", tvOraganizerMatchOverviewTeamA.getText().toString() + " VS " + tvOraganizerMatchOverviewTeamB.getText().toString());
            roundOneSchedMap.put("Match 2", tvOraganizerMatchOverviewTeamC.getText().toString() + " VS " + tvOraganizerMatchOverviewTeamD.getText().toString());
            roundOneSchedMap.put("Match 3", tvOraganizerMatchOverviewTeamE.getText().toString() + " VS " + tvOraganizerMatchOverviewTeamF.getText().toString());
            roundOneSchedMap.put("Match 4", tvOraganizerMatchOverviewTeamG.getText().toString() + " VS " + tvOraganizerMatchOverviewTeamH.getText().toString());
            roundOneSchedMap.put("match1Sched", match1schedDate + " " + match1schedTime);
            roundOneSchedMap.put("match2Sched", match2schedDate + " " + match2schedTime);
            roundOneSchedMap.put("match3Sched", match3schedDate + " " + match3schedTime);
            roundOneSchedMap.put("match4Sched", match4schedDate + " " + match4schedTime);
            roundOneSchedMap.put("countpost", countpost);

            mDatabaseRoundOne.setValue(roundOneSchedMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    String pushkey = mDatabaseUserNotification.getKey();

//                    notifMap.put("notifContent", leagueTitle + " " + divisionCategory + " "
//                            + "Match 1 " + tvOraganizerMatchOverviewTeamA.getText().toString() + " VS " + tvOraganizerMatchOverviewTeamB.getText().toString()
//                            + "Match 1 Sched " + match1schedDate + " " + match1schedTime
//                            + "Match 2 "+ tvOraganizerMatchOverviewTeamC.getText().toString() + " VS " + tvOraganizerMatchOverviewTeamD.getText().toString()
//                            + "Match 2 Sched " + match2schedDate + " " + match2schedTime
//                            + "Match 3 " + tvOraganizerMatchOverviewTeamE.getText().toString() + " VS " + tvOraganizerMatchOverviewTeamF.getText().toString()
//                            + "Match 3 Sched " + match3schedDate + " " + match3schedTime
//                            + "Match 4 " + tvOraganizerMatchOverviewTeamG.getText().toString() + " VS " + tvOraganizerMatchOverviewTeamH.getText().toString()
//                            + "Match 4 Sched" + match4schedDate + " " + match4schedTime);
                    HashMap notifMap = new HashMap();
                    notifMap.put("notifkey", pushkey);
                    notifMap.put("notifTitle", leagueTitle + " " + divisionCategory + " Schedule has been set");
                    notifMap.put("notifContent", leagueTitle + " " + divisionCategory + " Schedule has been set");
                    notifMap.put("match1", tvOraganizerMatchOverviewTeamA.getText().toString() + " VS " + tvOraganizerMatchOverviewTeamB.getText().toString());
                    notifMap.put("match2", tvOraganizerMatchOverviewTeamC.getText().toString() + " VS " + tvOraganizerMatchOverviewTeamD.getText().toString());
                    notifMap.put("match3", tvOraganizerMatchOverviewTeamE.getText().toString() + " VS " + tvOraganizerMatchOverviewTeamF.getText().toString());
                    notifMap.put("match4", tvOraganizerMatchOverviewTeamG.getText().toString() + " VS " + tvOraganizerMatchOverviewTeamH.getText().toString());
                    notifMap.put("match1Sched", match1schedDate + " " + match1schedTime);
                    notifMap.put("match2Sched", match2schedDate + " " + match2schedTime);
                    notifMap.put("match3Sched", match3schedDate + " " + match3schedTime);
                    notifMap.put("match4Sched", match4schedDate + " " + match4schedTime);
                    notifMap.put("notifCount", notifcount);
                    notifMap.put("notifDate", date);

                    mDatabaseUserNotification.setValue(notifMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(OrganizerMatchOverviewActivity.this, "Round 1 Schedule Save", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
            });
        }
    }
}
