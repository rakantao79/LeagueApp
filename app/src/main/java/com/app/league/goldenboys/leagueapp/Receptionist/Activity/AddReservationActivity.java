package com.app.league.goldenboys.leagueapp.Receptionist.Activity;

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

import com.app.league.goldenboys.leagueapp.Organizer.Activities.RoundRobinScheduleActivity;
import com.app.league.goldenboys.leagueapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static java.util.Calendar.YEAR;

public class AddReservationActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etRCName;
    private EditText etRCDate;
    private EditText etRCTimeStart;
    private EditText etRCTimeEnd;
    private Button btnRCSaveSched;
    private Button btnRCCancel;
    private String reservedate;

    private DatabaseReference mDatabaseRef;
    private DatabaseReference mDatabaseNotifAdmin;

    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reservation);

        DateFormat df = new SimpleDateFormat("yyyy/MM/dd h:mm a");
        reservedate = df.format(Calendar.getInstance().getTime());

        etRCName = findViewById(R.id.etRCName);
        etRCDate = findViewById(R.id.etRCDate);
        etRCTimeStart = findViewById(R.id.etRCTimeStart);
        etRCTimeEnd = findViewById(R.id.etRCTimeEnd);
        btnRCSaveSched = findViewById(R.id.btnRCSaveSched);
        btnRCCancel = findViewById(R.id.btnRCCancel);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Reservation");
        mDatabaseNotifAdmin = FirebaseDatabase.getInstance().getReference().child("NotificationAdmin");

        btnRCSaveSched.setOnClickListener(this);
        btnRCCancel.setOnClickListener(this);

        etRCDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddReservationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String date = dateFormat.format(c.getTime());

                        etRCDate.setText(date);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        etRCTimeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                final SimpleDateFormat timeformat = new SimpleDateFormat("kk:mm a");

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddReservationActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.set(Calendar.SECOND, 0);
                        c.set(Calendar.MILLISECOND, 0);

                        String time = timeformat.format(c.getTime());

                        etRCTimeStart.setText(time);
                    }
                }, mHour, mMinute, true);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        etRCTimeEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                final SimpleDateFormat timeformat = new SimpleDateFormat("kk:mm a");

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddReservationActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.set(Calendar.SECOND, 0);
                        c.set(Calendar.MILLISECOND, 0);

                        String time = timeformat.format(c.getTime());

                        etRCTimeEnd.setText(time);
                    }
                }, mHour, mMinute, true);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRCSaveSched:
                try {
                    saveSched();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.btnRCCancel:
                finish();
                break;
        }
    }

    private void saveSched() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("kk:mm");
        SimpleDateFormat currendateFormat = new SimpleDateFormat("yyyy-MM-dd");

        final String name = etRCName.getText().toString().trim();
        final String date = etRCDate.getText().toString().trim();
        final String timeStart = etRCTimeStart.getText().toString().trim();
        final String timeEnd = etRCTimeEnd.getText().toString().trim();

        Date inTime = sdf.parse(timeStart);
        Date outTime = sdf.parse(timeEnd);
        Date validDate = currendateFormat.parse(date);


        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(date) || TextUtils.isEmpty(timeStart) || TextUtils.isEmpty(timeEnd)) {
            Toast.makeText(this, "Please do not leave empty fields", Toast.LENGTH_SHORT).show();
        } else if (inTime.after(outTime)){
            Toast.makeText(this, "Time out is invalid", Toast.LENGTH_SHORT).show();
        } else if (inTime.equals(outTime)){
            Toast.makeText(this, "Same time", Toast.LENGTH_SHORT).show();
        } else if (inTime.before(outTime)){

            final HashMap reserveMap = new HashMap();
            reserveMap.put("reserveName", name);
            reserveMap.put("reserveDate", date);
            reserveMap.put("reserveTimeStart", timeStart);
            reserveMap.put("reserveTimeEnd", timeEnd);
            reserveMap.put("reserveTransDate", reservedate);

            mDatabaseRef.push().setValue(reserveMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    HashMap nottifMap = new HashMap();
                    nottifMap.put("notifTitle", "Reserved on " + date + " by " + name);
                    nottifMap.put("notifContent", name + " " + "Reserved details : " + name + ", " + "\n Time start : " + timeStart + "\n Time end : " + timeEnd + "\n Date : " + date);
                    nottifMap.put("notifDate", reservedate);

                    mDatabaseNotifAdmin.push().setValue(nottifMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(AddReservationActivity.this, "Successfully Save", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
            });
        }
    }
}
