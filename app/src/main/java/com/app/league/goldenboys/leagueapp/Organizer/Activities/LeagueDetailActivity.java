package com.app.league.goldenboys.leagueapp.Organizer.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;


import com.app.league.goldenboys.leagueapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class LeagueDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private String user_id;
    private String pushkey;
    private FirebaseAuth mAuth;

    private ImageView imgLeagueDetail;
    private TextView tvLeagDetailTitle;
    private TextView tvLeagDetailDesc;
    private Button btnMasterDivision;
    private Button btnSeniorDivision;
    private Button btnJuniorDivision;
    private Button btnMedietDivision;

    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseUser;

    private int divisionAgeValue = 0;
    private String divisionCategory;
    private String leagueTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league_detail);
        Toolbar toolbar = findViewById(R.id.toolbar_leaguedetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("League Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pushkey = getIntent().getExtras().getString("pushkey");
        leagueTitle = getIntent().getExtras().getString("leagueTitle");

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("League").child(pushkey);
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);

        mDatabaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String usertype = dataSnapshot.child("Usertype").getValue().toString();
                    Log.d("usertype", "onDataChange: " + usertype);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        imgLeagueDetail = findViewById(R.id.imgLeagueDetail);
        tvLeagDetailTitle = findViewById(R.id.tvLeagueDetailTitle);
        tvLeagDetailDesc = findViewById(R.id.tvLeagueDetailDescription);
        btnMasterDivision = findViewById(R.id.btnMasterDivision);
        btnSeniorDivision = findViewById(R.id.btnSeniorDivision);
        btnJuniorDivision = findViewById(R.id.btnJuniorDivision);
        btnMedietDivision = findViewById(R.id.btnMedietDivision);

        btnMedietDivision.setOnClickListener(this);
        btnJuniorDivision.setOnClickListener(this);
        btnSeniorDivision.setOnClickListener(this);
        btnMasterDivision.setOnClickListener(this);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    final String image = dataSnapshot.child("imageLeague").getValue().toString();
                    String title = dataSnapshot.child("leagueTitle").getValue().toString();
                    String desc = dataSnapshot.child("leagueDesc").getValue().toString();
                    String divMaster = dataSnapshot.child("divisionMaster").getValue().toString();
                    String divSenior = dataSnapshot.child("divisionSenior").getValue().toString();
                    String divJunior = dataSnapshot.child("divisionJunior").getValue().toString();
                    String divMediet = dataSnapshot.child("divisionMediet").getValue().toString();

                    Picasso.with(getApplicationContext())
                            .load(image)
                            .networkPolicy(NetworkPolicy.OFFLINE)
                            .placeholder(R.drawable.img_default)
                            .into(imgLeagueDetail, new Callback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onError() {
                                    Picasso.with(getApplicationContext())
                                            .load(image)
                                            .placeholder(R.drawable.img_default)
                                            .into(imgLeagueDetail);
                                }
                            });

                    tvLeagDetailTitle.setText(title);
                    tvLeagDetailDesc.setText(desc);

                    if (divMaster.equals("unavailable")) {
                        //btnMasterDivision.setVisibility(View.INVISIBLE);
                        btnMasterDivision.setEnabled(false);
                        btnMasterDivision.setText("Master Division Unavailable");
                    } else {
                        //btnMasterDivision.setVisibility(View.VISIBLE);
                        btnMasterDivision.setEnabled(true);
                        btnMasterDivision.setText("View Master Division Entries");
                    }

                    if (divSenior.equals("unavailable")) {
                        //btnSeniorDivision.setVisibility(View.INVISIBLE);
                        btnSeniorDivision.setEnabled(false);
                        btnSeniorDivision.setText("Senior Division Unavailable");
                    } else {
                        //btnSeniorDivision.setVisibility(View.VISIBLE);
                        btnSeniorDivision.setEnabled(true);
                        btnSeniorDivision.setText("View Senior Division Entries");
                    }

                    if (divJunior.equals("unavailable")) {
                        //btnJuniorDivision.setVisibility(View.INVISIBLE);
                        btnJuniorDivision.setEnabled(false);
                        btnJuniorDivision.setText("Junior Division Unavailable");
                    } else {
                        //btnJuniorDivision.setVisibility(View.VISIBLE);
                        btnJuniorDivision.setEnabled(true);
                        btnJuniorDivision.setText("View Junior Division Entries");
                    }

                    if (divMediet.equals("unavailable")) {
                        //btnMedietDivision.setVisibility(View.INVISIBLE);
                        btnMedietDivision.setEnabled(false);
                        btnMedietDivision.setText("Midget Division Unavailable");
                    } else {
                        //btnMedietDivision.setVisibility(View.VISIBLE);
                        btnMedietDivision.setEnabled(true);
                        btnMedietDivision.setText("View Midget Division Entries");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMasterDivision:
                divisionCategory = "MasterDivision";
                divisionAgeValue = 18;
                Intent intentMaster = new Intent(this, ApprovedEntryActivity.class);
                intentMaster.putExtra("pushkey", pushkey);
                intentMaster.putExtra("divisionAge", divisionAgeValue);
                intentMaster.putExtra("divisionCategory", divisionCategory);
                intentMaster.putExtra("leagueTitle", tvLeagDetailTitle.getText().toString());
                startActivity(intentMaster);
                finish();
                break;
            case R.id.btnSeniorDivision:
                divisionCategory = "SeniorDivision";
                divisionAgeValue = 18;
                Intent intentSenior = new Intent(this, ApprovedEntryActivity.class);
                intentSenior.putExtra("pushkey", pushkey);
                intentSenior.putExtra("divisionAge", divisionAgeValue);
                intentSenior.putExtra("divisionCategory", divisionCategory);
                intentSenior.putExtra("leagueTitle", tvLeagDetailTitle.getText().toString());
                startActivity(intentSenior);
                finish();
                break;
            case R.id.btnJuniorDivision:
                divisionCategory = "JuniorDivision";
                divisionAgeValue = 17;
                Intent intentJunior = new Intent(this, ApprovedEntryActivity.class);
                intentJunior.putExtra("pushkey", pushkey);
                intentJunior.putExtra("divisionAge", divisionAgeValue);
                intentJunior.putExtra("divisionCategory", divisionCategory);
                intentJunior.putExtra("leagueTitle", tvLeagDetailTitle.getText().toString());
                startActivity(intentJunior);
                finish();
                break;
            case R.id.btnMedietDivision:
                divisionCategory = "MidietDivision";
                divisionAgeValue = 10;
                Intent intentMediet = new Intent(this, ApprovedEntryActivity.class);
                intentMediet.putExtra("pushkey", pushkey);
                intentMediet.putExtra("divisionAge", divisionAgeValue);
                intentMediet.putExtra("divisionCategory", divisionCategory);
                intentMediet.putExtra("leagueTitle", tvLeagDetailTitle.getText().toString());
                startActivity(intentMediet);
                finish();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_league, menu);

        MenuItem menuOrganizerViewSubmittedEntries = menu.findItem(R.id.menu_view_submitted_entries);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_view_submitted_entries){

//            Intent intent = new Intent(this, ListOfCategoryEntryActivity.class);
//            intent.putExtra("divisionCategory", divisionCategory);
//            intent.putExtra("leagueTitle", tvLeagDetailTitle.getText().toString());
//            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
