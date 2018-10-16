package com.app.league.goldenboys.leagueapp.Organizer.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.league.goldenboys.leagueapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SubmitEntryActivity extends AppCompatActivity {

    private CircleImageView cimvTeamLogo;
    private EditText etTeamName;
    private EditText etTeamMemberA;
    private EditText etPositionA;
    private EditText etTeamMemberB;
    private EditText etPositionB;
    private EditText etTeamMemberC;
    private EditText etPositionC;
    private EditText etTeamMemberD;
    private EditText etPositionD;
    private EditText etTeamMemberE;
    private EditText etPositionE;
    private EditText etTeamMemberF;
    private EditText etPositionF;
    private EditText etTeamMemberG;
    private EditText etPositionG;
    private EditText etTeamMemberH;
    private EditText etPositionH;
    private EditText etTeamMemberI;
    private EditText etPositionI;
    private EditText etTeamMemberJ;
    private EditText etPositionJ;
    private EditText etTeamMemberK;
    private EditText etPositionK;
    private EditText etTeamMemberL;
    private EditText etPositionL;

    private TextView tvSubmitLeagueTitleAndDivision;

    private Button btnSubmitTeamEntry;
    private Button btnListOfPendingEntriesForLeagueTitleDivision;
    private Button btnListOfApprovedEntriesForLeagueTitleDivision;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseLeagueEntries;
    private DatabaseReference mDatabaseOrganizerNotification;

    private String leagueTitle;
    private String leagueDivision;
    private String pushkey;
    private String divisionCategory;

    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_entry);

        cimvTeamLogo = findViewById(R.id.cimvTeamLogo);
        etTeamName = findViewById(R.id.etTeamName);

        divisionCategory = getIntent().getStringExtra("divisionCategory");
        leagueTitle = getIntent().getStringExtra("leagueTitle");

        DateFormat df = new SimpleDateFormat("yyyy/MM/dd h:mm a");
        date = df.format(Calendar.getInstance().getTime());

        etTeamMemberA = findViewById(R.id.etTeamMemberA);
        etTeamMemberB = findViewById(R.id.etTeamMemberB);
        etTeamMemberC = findViewById(R.id.etTeamMemberC);
        etTeamMemberD = findViewById(R.id.etTeamMemberD);
        etTeamMemberE = findViewById(R.id.etTeamMemberE);
        etTeamMemberF = findViewById(R.id.etTeamMemberF);
        etTeamMemberG = findViewById(R.id.etTeamMemberG);
        etTeamMemberH = findViewById(R.id.etTeamMemberH);
        etTeamMemberI = findViewById(R.id.etTeamMemberI);
        etTeamMemberJ = findViewById(R.id.etTeamMemberJ);
        etTeamMemberK = findViewById(R.id.etTeamMemberK);
        etTeamMemberL = findViewById(R.id.etTeamMemberL);

        etPositionA = findViewById(R.id.etPositionA);
        etPositionB = findViewById(R.id.etPositionB);
        etPositionC = findViewById(R.id.etPositionC);
        etPositionD = findViewById(R.id.etPositionD);
        etPositionE = findViewById(R.id.etPositionE);
        etPositionF = findViewById(R.id.etPositionF);
        etPositionG = findViewById(R.id.etPositionG);
        etPositionH = findViewById(R.id.etPositionH);
        etPositionI = findViewById(R.id.etPositionI);
        etPositionJ = findViewById(R.id.etPositionJ);
        etPositionK = findViewById(R.id.etPositionK);
        etPositionL = findViewById(R.id.etPositionL);

        btnSubmitTeamEntry = findViewById(R.id.btnSubmitTeamEntry);
        tvSubmitLeagueTitleAndDivision = findViewById(R.id.tvSubmitLeagueTitleAndDivision);
//        btnListOfPendingEntriesForLeagueTitleDivision = findViewById(R.id.btnListOfPendingEntriesForLeagueTitleDivision);
//        btnListOfApprovedEntriesForLeagueTitleDivision = findViewById(R.id.btnListOfApprovedEntriesForLeagueTitleDivision);

        tvSubmitLeagueTitleAndDivision.setText("Submit Entry for " + leagueTitle + " " + divisionCategory);
        Log.d("division", "onCreate: " + divisionCategory);
        Log.d("title", "onCreate: " + leagueTitle);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabaseLeagueEntries = FirebaseDatabase.getInstance().getReference("LeagueEntries");
        mDatabaseOrganizerNotification = FirebaseDatabase.getInstance().getReference().child("OrganizerNotification");

        btnSubmitTeamEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitEntry();
            }
        });

//        btnListOfPendingEntriesForLeagueTitleDivision.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intentListPendingEntries = new Intent(SubmitEntryActivity.this, PendingEntryActivity.class);
//                intentListPendingEntries.putExtra("divisionCategory", divisionCategory);
//                intentListPendingEntries.putExtra("leagueTitle", leagueTitle);
//                startActivity(intentListPendingEntries);
//                finish();
//            }
//        });
//
//        btnListOfApprovedEntriesForLeagueTitleDivision.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intentListApprovedEntries = new Intent(SubmitEntryActivity.this, ApprovedEntryActivity.class);
//                intentListApprovedEntries.putExtra("divisionCategory", divisionCategory);
//                intentListApprovedEntries.putExtra("leagueTitle", leagueTitle);
//                startActivity(intentListApprovedEntries);
//                finish();
//            }
//        });

        selectPositions();
        selectMembers();
    }

    private void submitEntry() {

        final String teamMemberName = etTeamName.getText().toString().trim();
        String teamMemberA = etTeamMemberA.getText().toString().trim();
        String teamMemberB = etTeamMemberB.getText().toString().trim();
        String teamMemberC = etTeamMemberC.getText().toString().trim();
        String teamMemberD = etTeamMemberD.getText().toString().trim();
        String teamMemberE = etTeamMemberE.getText().toString().trim();
        String teamMemberF = etTeamMemberF.getText().toString().trim();
        String teamMemberG = etTeamMemberG.getText().toString().trim();
        String teamMemberH = etTeamMemberH.getText().toString().trim();
        String teamMemberI = etTeamMemberI.getText().toString().trim();
        String teamMemberJ = etTeamMemberJ.getText().toString().trim();
        String teamMemberK = etTeamMemberK.getText().toString().trim();
        String teamMemberL = etTeamMemberL.getText().toString().trim();

        String positionA = etPositionA.getText().toString().trim();
        String positionB = etPositionB.getText().toString().trim();
        String positionC = etPositionC.getText().toString().trim();
        String positionD = etPositionD.getText().toString().trim();
        String positionE = etPositionE.getText().toString().trim();
        String positionF = etPositionF.getText().toString().trim();
        String positionG = etPositionG.getText().toString().trim();
        String positionH = etPositionH.getText().toString().trim();
        String positionI = etPositionI.getText().toString().trim();
        String positionJ = etPositionJ.getText().toString().trim();
        String positionK = etPositionK.getText().toString().trim();
        String positionL = etPositionL.getText().toString().trim();

        if (TextUtils.isEmpty(teamMemberName) ||
                TextUtils.isEmpty(teamMemberA) ||
                TextUtils.isEmpty(teamMemberB) ||
                TextUtils.isEmpty(teamMemberC) ||
                TextUtils.isEmpty(teamMemberD) ||
                TextUtils.isEmpty(teamMemberE) ||
                TextUtils.isEmpty(teamMemberF) ||
                TextUtils.isEmpty(teamMemberG) ||
                TextUtils.isEmpty(teamMemberH) ||
                TextUtils.isEmpty(teamMemberI) ||
                TextUtils.isEmpty(teamMemberJ) ||
                TextUtils.isEmpty(teamMemberK) ||
                TextUtils.isEmpty(teamMemberL) ||
                TextUtils.isEmpty(positionA) ||
                TextUtils.isEmpty(positionB) ||
                TextUtils.isEmpty(positionC) ||
                TextUtils.isEmpty(positionD) ||
                TextUtils.isEmpty(positionE) ||
                TextUtils.isEmpty(positionF) ||
                TextUtils.isEmpty(positionG) ||
                TextUtils.isEmpty(positionH) ||
                TextUtils.isEmpty(positionI) ||
                TextUtils.isEmpty(positionJ) ||
                TextUtils.isEmpty(positionK) ||
                TextUtils.isEmpty(positionL)) {

            Toast.makeText(this, "Please don't leave fields empty", Toast.LENGTH_SHORT).show();

        } else if (teamMemberA.equals(teamMemberB) ||
                teamMemberA.equals(teamMemberC) ||
                teamMemberA.equals(teamMemberD) ||
                teamMemberA.equals(teamMemberE) ||
                teamMemberA.equals(teamMemberF) ||
                teamMemberA.equals(teamMemberG) ||
                teamMemberA.equals(teamMemberH) ||
                teamMemberA.equals(teamMemberI) ||
                teamMemberA.equals(teamMemberJ) ||
                teamMemberA.equals(teamMemberK) ||
                teamMemberA.equals(teamMemberL)){

            Toast.makeText(this, "Fields has same data", Toast.LENGTH_SHORT).show();

        } else if (teamMemberB.equals(teamMemberA) ||
                teamMemberB.equals(teamMemberC) ||
                teamMemberB.equals(teamMemberD) ||
                teamMemberB.equals(teamMemberE) ||
                teamMemberB.equals(teamMemberF) ||
                teamMemberB.equals(teamMemberG) ||
                teamMemberB.equals(teamMemberH) ||
                teamMemberB.equals(teamMemberI) ||
                teamMemberB.equals(teamMemberJ) ||
                teamMemberB.equals(teamMemberK) ||
                teamMemberB.equals(teamMemberL)){

            Toast.makeText(this, "Fields has same data", Toast.LENGTH_SHORT).show();

        } else if (teamMemberC.equals(teamMemberA) ||
                teamMemberC.equals(teamMemberB) ||
                teamMemberC.equals(teamMemberD) ||
                teamMemberC.equals(teamMemberE) ||
                teamMemberC.equals(teamMemberF) ||
                teamMemberC.equals(teamMemberG) ||
                teamMemberC.equals(teamMemberH) ||
                teamMemberC.equals(teamMemberI) ||
                teamMemberC.equals(teamMemberJ) ||
                teamMemberC.equals(teamMemberK) ||
                teamMemberC.equals(teamMemberL)){

            Toast.makeText(this, "Fields has same data", Toast.LENGTH_SHORT).show();

        } else if (teamMemberD.equals(teamMemberA) ||
                teamMemberD.equals(teamMemberB) ||
                teamMemberD.equals(teamMemberC) ||
                teamMemberD.equals(teamMemberE) ||
                teamMemberD.equals(teamMemberF) ||
                teamMemberD.equals(teamMemberG) ||
                teamMemberD.equals(teamMemberH) ||
                teamMemberD.equals(teamMemberI) ||
                teamMemberD.equals(teamMemberJ) ||
                teamMemberD.equals(teamMemberK) ||
                teamMemberD.equals(teamMemberL)){

            Toast.makeText(this, "Fields has same data", Toast.LENGTH_SHORT).show();

        } else if (teamMemberE.equals(teamMemberA) ||
                teamMemberE.equals(teamMemberB) ||
                teamMemberE.equals(teamMemberC) ||
                teamMemberE.equals(teamMemberD) ||
                teamMemberE.equals(teamMemberF) ||
                teamMemberE.equals(teamMemberG) ||
                teamMemberE.equals(teamMemberH) ||
                teamMemberE.equals(teamMemberI) ||
                teamMemberE.equals(teamMemberJ) ||
                teamMemberE.equals(teamMemberK) ||
                teamMemberE.equals(teamMemberL)){

            Toast.makeText(this, "Fields has same data", Toast.LENGTH_SHORT).show();

        } else if (teamMemberF.equals(teamMemberA) ||
                teamMemberF.equals(teamMemberB) ||
                teamMemberF.equals(teamMemberC) ||
                teamMemberF.equals(teamMemberD) ||
                teamMemberF.equals(teamMemberE) ||
                teamMemberF.equals(teamMemberG) ||
                teamMemberF.equals(teamMemberH) ||
                teamMemberF.equals(teamMemberI) ||
                teamMemberF.equals(teamMemberJ) ||
                teamMemberF.equals(teamMemberK) ||
                teamMemberF.equals(teamMemberL)){

            Toast.makeText(this, "Fields has same data", Toast.LENGTH_SHORT).show();

        } else if (teamMemberG.equals(teamMemberA) ||
                teamMemberG.equals(teamMemberB) ||
                teamMemberG.equals(teamMemberC) ||
                teamMemberG.equals(teamMemberD) ||
                teamMemberG.equals(teamMemberE) ||
                teamMemberG.equals(teamMemberF) ||
                teamMemberG.equals(teamMemberH) ||
                teamMemberG.equals(teamMemberI) ||
                teamMemberG.equals(teamMemberJ) ||
                teamMemberG.equals(teamMemberK) ||
                teamMemberG.equals(teamMemberL)){

            Toast.makeText(this, "Fields has same data", Toast.LENGTH_SHORT).show();

        } else if (teamMemberH.equals(teamMemberA) ||
                teamMemberH.equals(teamMemberB) ||
                teamMemberH.equals(teamMemberC) ||
                teamMemberH.equals(teamMemberD) ||
                teamMemberH.equals(teamMemberE) ||
                teamMemberH.equals(teamMemberF) ||
                teamMemberH.equals(teamMemberG) ||
                teamMemberH.equals(teamMemberI) ||
                teamMemberH.equals(teamMemberJ) ||
                teamMemberH.equals(teamMemberK) ||
                teamMemberH.equals(teamMemberL)){

            Toast.makeText(this, "Fields has same data", Toast.LENGTH_SHORT).show();

        } else if (teamMemberI.equals(teamMemberA) ||
                teamMemberI.equals(teamMemberB) ||
                teamMemberI.equals(teamMemberC) ||
                teamMemberI.equals(teamMemberD) ||
                teamMemberI.equals(teamMemberE) ||
                teamMemberI.equals(teamMemberF) ||
                teamMemberI.equals(teamMemberG) ||
                teamMemberI.equals(teamMemberH) ||
                teamMemberI.equals(teamMemberJ) ||
                teamMemberI.equals(teamMemberK) ||
                teamMemberI.equals(teamMemberL)){

            Toast.makeText(this, "Fields has same data", Toast.LENGTH_SHORT).show();

        } else if (teamMemberJ.equals(teamMemberA) ||
                teamMemberJ.equals(teamMemberB) ||
                teamMemberJ.equals(teamMemberC) ||
                teamMemberJ.equals(teamMemberD) ||
                teamMemberJ.equals(teamMemberE) ||
                teamMemberJ.equals(teamMemberF) ||
                teamMemberJ.equals(teamMemberG) ||
                teamMemberJ.equals(teamMemberH) ||
                teamMemberJ.equals(teamMemberI) ||
                teamMemberJ.equals(teamMemberK) ||
                teamMemberJ.equals(teamMemberL)){

            Toast.makeText(this, "Fields has same data", Toast.LENGTH_SHORT).show();

        } else if (teamMemberK.equals(teamMemberA) ||
                teamMemberK.equals(teamMemberB) ||
                teamMemberK.equals(teamMemberC) ||
                teamMemberK.equals(teamMemberD) ||
                teamMemberK.equals(teamMemberE) ||
                teamMemberK.equals(teamMemberF) ||
                teamMemberK.equals(teamMemberG) ||
                teamMemberK.equals(teamMemberH) ||
                teamMemberK.equals(teamMemberI) ||
                teamMemberK.equals(teamMemberJ) ||
                teamMemberK.equals(teamMemberL)){

            Toast.makeText(this, "Fields has same data", Toast.LENGTH_SHORT).show();

        } else if (teamMemberL.equals(teamMemberA) ||
                teamMemberL.equals(teamMemberB) ||
                teamMemberL.equals(teamMemberC) ||
                teamMemberL.equals(teamMemberD) ||
                teamMemberL.equals(teamMemberE) ||
                teamMemberL.equals(teamMemberF) ||
                teamMemberL.equals(teamMemberG) ||
                teamMemberL.equals(teamMemberH) ||
                teamMemberL.equals(teamMemberI) ||
                teamMemberL.equals(teamMemberJ) ||
                teamMemberL.equals(teamMemberK)){

            Toast.makeText(this, "Fields has same data", Toast.LENGTH_SHORT).show();

        } else {

            HashMap teamMap = new HashMap();
            teamMap.put("teamName", teamMemberName);
            teamMap.put("teamMemberA", teamMemberA + ", " + positionA);
            teamMap.put("teamMemberB", teamMemberB + ", " + positionB);
            teamMap.put("teamMemberC", teamMemberC + ", " + positionC);
            teamMap.put("teamMemberD", teamMemberD + ", " + positionD);
            teamMap.put("teamMemberE", teamMemberE + ", " + positionE);
            teamMap.put("teamMemberF", teamMemberF + ", " + positionF);
            teamMap.put("teamMemberG", teamMemberG + ", " + positionG);
            teamMap.put("teamMemberH", teamMemberH + ", " + positionH);
            teamMap.put("teamMemberI", teamMemberI + ", " + positionI);
            teamMap.put("teamMemberJ", teamMemberJ + ", " + positionJ);
            teamMap.put("teamMemberK", teamMemberK + ", " + positionK);
            teamMap.put("teamMemberL", teamMemberL + ", " + positionL);
            teamMap.put("teamEntryStatus", "pending");
//            teamMap.put("positionA", positionA);
//            teamMap.put("positionB", positionB);
//            teamMap.put("positionC", positionC);
//            teamMap.put("positionD", positionD);
//            teamMap.put("positionE", positionE);
//            teamMap.put("positionF", positionF);
//            teamMap.put("positionG", positionG);
//            teamMap.put("positionH", positionH);
//            teamMap.put("positionI", positionI);
//            teamMap.put("positionJ", positionJ);
//            teamMap.put("positionK", positionK);

            mDatabaseLeagueEntries.child(leagueTitle).child(divisionCategory).child("PendingEntries").push().setValue(teamMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    HashMap organizerNotifmap = new HashMap();
                    organizerNotifmap.put("notifTitle", leagueTitle + " " + divisionCategory + " " + " team submitted an entry");
                    organizerNotifmap.put("notifMatch", teamMemberName + " submitted an entry");
                    organizerNotifmap.put("notfiDate", date);

                    mDatabaseOrganizerNotification.push().setValue(organizerNotifmap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(SubmitEntryActivity.this, "Your Entry has been submitted and will be verified by Organizer.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SubmitEntryActivity.this, e.getMessage() + "Please Try Again", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    private void selectPositions() {

        etPositionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items2 = {
                        "Coach", "PG", "SG", "SF", "PF", "C"
                };
                AlertDialog.Builder builder2 = new AlertDialog.Builder(SubmitEntryActivity.this);
                builder2.setTitle("Make your selection");
                builder2.setItems(items2, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        etPositionA.setText(items2[item]);
                    }
                });
                AlertDialog alert2 = builder2.create();
                alert2.show();
            }
        });

        etPositionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items2 = {
                        "Coach", "PG", "SG", "SF", "PF", "C"
                };
                AlertDialog.Builder builder2 = new AlertDialog.Builder(SubmitEntryActivity.this);
                builder2.setTitle("Make your selection");
                builder2.setItems(items2, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        etPositionB.setText(items2[item]);
                    }
                });
                AlertDialog alert2 = builder2.create();
                alert2.show();
            }
        });

        etPositionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items2 = {
                        "Coach", "PG", "SG", "SF", "PF", "C"
                };
                AlertDialog.Builder builder2 = new AlertDialog.Builder(SubmitEntryActivity.this);
                builder2.setTitle("Make your selection");
                builder2.setItems(items2, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        etPositionC.setText(items2[item]);
                    }
                });
                AlertDialog alert2 = builder2.create();
                alert2.show();
            }
        });

        etPositionD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items2 = {
                        "Coach", "PG", "SG", "SF", "PF", "C"
                };
                AlertDialog.Builder builder2 = new AlertDialog.Builder(SubmitEntryActivity.this);
                builder2.setTitle("Make your selection");
                builder2.setItems(items2, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        etPositionD.setText(items2[item]);
                    }
                });
                AlertDialog alert2 = builder2.create();
                alert2.show();
            }
        });

        etPositionE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items2 = {
                        "Coach", "PG", "SG", "SF", "PF", "C"
                };
                AlertDialog.Builder builder2 = new AlertDialog.Builder(SubmitEntryActivity.this);
                builder2.setTitle("Make your selection");
                builder2.setItems(items2, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        etPositionE.setText(items2[item]);
                    }
                });
                AlertDialog alert2 = builder2.create();
                alert2.show();
            }
        });

        etPositionF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items2 = {
                        "Coach", "PG", "SG", "SF", "PF", "C"
                };
                AlertDialog.Builder builder2 = new AlertDialog.Builder(SubmitEntryActivity.this);
                builder2.setTitle("Make your selection");
                builder2.setItems(items2, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        etPositionF.setText(items2[item]);
                    }
                });
                AlertDialog alert2 = builder2.create();
                alert2.show();
            }
        });

        etPositionG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items2 = {
                        "Coach", "PG", "SG", "SF", "PF", "C"
                };
                AlertDialog.Builder builder2 = new AlertDialog.Builder(SubmitEntryActivity.this);
                builder2.setTitle("Make your selection");
                builder2.setItems(items2, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        etPositionG.setText(items2[item]);
                    }
                });
                AlertDialog alert2 = builder2.create();
                alert2.show();
            }
        });

        etPositionH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items2 = {
                        "Coach", "PG", "SG", "SF", "PF", "C"
                };
                AlertDialog.Builder builder2 = new AlertDialog.Builder(SubmitEntryActivity.this);
                builder2.setTitle("Make your selection");
                builder2.setItems(items2, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        etPositionH.setText(items2[item]);
                    }
                });
                AlertDialog alert2 = builder2.create();
                alert2.show();
            }
        });

        etPositionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items2 = {
                        "Coach", "PG", "SG", "SF", "PF", "C"
                };
                AlertDialog.Builder builder2 = new AlertDialog.Builder(SubmitEntryActivity.this);
                builder2.setTitle("Make your selection");
                builder2.setItems(items2, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        etPositionA.setText(items2[item]);
                    }
                });
                AlertDialog alert2 = builder2.create();
                alert2.show();
            }
        });

        etPositionI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items2 = {
                        "Coach", "PG", "SG", "SF", "PF", "C"
                };
                AlertDialog.Builder builder2 = new AlertDialog.Builder(SubmitEntryActivity.this);
                builder2.setTitle("Make your selection");
                builder2.setItems(items2, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        etPositionI.setText(items2[item]);
                    }
                });
                AlertDialog alert2 = builder2.create();
                alert2.show();
            }
        });

        etPositionJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items2 = {
                        "Coach", "PG", "SG", "SF", "PF", "C"
                };
                AlertDialog.Builder builder2 = new AlertDialog.Builder(SubmitEntryActivity.this);
                builder2.setTitle("Make your selection");
                builder2.setItems(items2, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        etPositionJ.setText(items2[item]);
                    }
                });
                AlertDialog alert2 = builder2.create();
                alert2.show();
            }
        });

        etPositionK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items2 = {
                        "Coach", "PG", "SG", "SF", "PF", "C"
                };
                AlertDialog.Builder builder2 = new AlertDialog.Builder(SubmitEntryActivity.this);
                builder2.setTitle("Make your selection");
                builder2.setItems(items2, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        etPositionK.setText(items2[item]);
                    }
                });
                AlertDialog alert2 = builder2.create();
                alert2.show();
            }
        });

        etPositionL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items2 = {
                        "Coach", "PG", "SG", "SF", "PF", "C"
                };
                AlertDialog.Builder builder2 = new AlertDialog.Builder(SubmitEntryActivity.this);
                builder2.setTitle("Make your selection");
                builder2.setItems(items2, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        etPositionL.setText(items2[item]);
                    }
                });
                AlertDialog alert2 = builder2.create();
                alert2.show();
            }
        });

    }

    private void selectMembers() {

        etTeamMemberA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        final List<String> member = new ArrayList<String>();

                        if (dataSnapshot.exists()) {

                            for (DataSnapshot memberSnap : dataSnapshot.getChildren()) {
                                String firstname = memberSnap.child("FirstName").getValue(String.class);
                                String lastname = memberSnap.child("LastName").getValue(String.class);
                                String age = memberSnap.child("age").getValue(String.class);

                                int ageValue = Integer.parseInt(age);
                                //member.add(lastname + ", " + firstname + " (" + age + ")");

                                if (divisionCategory.equals("MasterDivision")) {
                                    if (ageValue >= 35) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                } else if (divisionCategory.equals("SeniorDivision")) {
                                    if (ageValue >= 25 && ageValue <= 34) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                } else if (divisionCategory.equals("JuniorDivision")) {
                                    if (ageValue < 24 && ageValue >= 18) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                } else if (divisionCategory.equals("MidietDivision")) {
                                    if (ageValue < 14 && ageValue > 8) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                }
                            }

                            final CharSequence[] teamMember = member.toArray(new CharSequence[member.size()]);
                            AlertDialog.Builder builderz = new AlertDialog.Builder(SubmitEntryActivity.this);
                            builderz.setTitle("Add members to your team");
                            builderz.setItems(teamMember, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

//                                    if (etTeamMemberA.getText().toString().equals(teamMember[which])) {
//                                        etTeamMemberA.setText(teamMember[which]);
//                                        member.remove(teamMember[which]);
//                                    }
                                    etTeamMemberA.setText(teamMember[which]);
                                }
                            });
                            AlertDialog alertDialogz = builderz.create();
                            alertDialogz.show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        etTeamMemberB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final List<String> member = new ArrayList<String>();

                        if (dataSnapshot.exists()) {

                            for (DataSnapshot memberSnap : dataSnapshot.getChildren()) {
                                String firstname = memberSnap.child("FirstName").getValue(String.class);
                                String lastname = memberSnap.child("LastName").getValue(String.class);
                                String age = memberSnap.child("age").getValue(String.class);

                                int ageValue = Integer.parseInt(age);
                                //member.add(lastname + ", " + firstname + " (" + age + ")");

                                if (divisionCategory.equals("MasterDivision")) {
                                    if (ageValue >= 35) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                } else if (divisionCategory.equals("SeniorDivision")) {
                                    if (ageValue >= 25 && ageValue <= 34) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                } else if (divisionCategory.equals("JuniorDivision")) {
                                    if (ageValue < 24 && ageValue >= 18) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                } else if (divisionCategory.equals("MidietDivision")) {
                                    if (ageValue < 14 && ageValue > 8) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                }
                            }

                            final CharSequence[] teamMember = member.toArray(new CharSequence[member.size()]);
                            AlertDialog.Builder builderz = new AlertDialog.Builder(SubmitEntryActivity.this);
                            builderz.setTitle("Add members to your team");
                            builderz.setItems(teamMember, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (teamMember[which] == teamMember[which]) {
                                        member.remove(teamMember[which]);
                                        etTeamMemberB.setText(teamMember[which]);
                                    }
                                    //etTeamMemberB.setText(teamMember[which]);
                                }
                            });
                            AlertDialog alertDialogz = builderz.create();
                            alertDialogz.show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        etTeamMemberC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final List<String> member = new ArrayList<String>();

                        if (dataSnapshot.exists()) {

                            for (DataSnapshot memberSnap : dataSnapshot.getChildren()) {
                                String firstname = memberSnap.child("FirstName").getValue(String.class);
                                String lastname = memberSnap.child("LastName").getValue(String.class);
                                String age = memberSnap.child("age").getValue(String.class);

                                int ageValue = Integer.parseInt(age);
                                //member.add(lastname + ", " + firstname + " (" + age + ")");

                                if (divisionCategory.equals("MasterDivision")) {
                                    if (ageValue >= 35) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                } else if (divisionCategory.equals("SeniorDivision")) {
                                    if (ageValue >= 25 && ageValue <= 34) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                } else if (divisionCategory.equals("JuniorDivision")) {
                                    if (ageValue < 24 && ageValue >= 18) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                } else if (divisionCategory.equals("MidietDivision")) {
                                    if (ageValue < 14 && ageValue > 8) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                }
                            }

                            final CharSequence[] teamMember = member.toArray(new CharSequence[member.size()]);
                            AlertDialog.Builder builderz = new AlertDialog.Builder(SubmitEntryActivity.this);
                            builderz.setTitle("Add members to your team");
                            builderz.setItems(teamMember, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    etTeamMemberC.setText(teamMember[which]);

                                    //etTeamMemberC.setText(teamMember[which]);
                                }
                            });
                            AlertDialog alertDialogz = builderz.create();
                            alertDialogz.show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        etTeamMemberD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        final List<String> member = new ArrayList<String>();

                        if (dataSnapshot.exists()) {

                            for (DataSnapshot memberSnap : dataSnapshot.getChildren()) {
                                String firstname = memberSnap.child("FirstName").getValue(String.class);
                                String lastname = memberSnap.child("LastName").getValue(String.class);
                                String age = memberSnap.child("age").getValue(String.class);

                                int ageValue = Integer.parseInt(age);
                                //member.add(lastname + ", " + firstname + " (" + age + ")");

                                if (divisionCategory.equals("MasterDivision")) {
                                    if (ageValue >= 35) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                } else if (divisionCategory.equals("SeniorDivision")) {
                                    if (ageValue >= 25 && ageValue <= 34) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                } else if (divisionCategory.equals("JuniorDivision")) {
                                    if (ageValue < 24 && ageValue >= 18) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                } else if (divisionCategory.equals("MidietDivision")) {
                                    if (ageValue < 14 && ageValue > 8) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                }
                            }

                            final CharSequence[] teamMember = member.toArray(new CharSequence[member.size()]);
                            AlertDialog.Builder builderz = new AlertDialog.Builder(SubmitEntryActivity.this);
                            builderz.setTitle("Add members to your team");
                            builderz.setItems(teamMember, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (teamMember[which] == teamMember[which]) {
                                        member.remove(which);
                                        etTeamMemberD.setText(teamMember[which]);
                                    }
                                    //etTeamMemberD.setText(teamMember[which]);
                                }
                            });
                            AlertDialog alertDialogz = builderz.create();
                            alertDialogz.show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        etTeamMemberE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final List<String> member = new ArrayList<String>();

                        if (dataSnapshot.exists()) {

                            for (DataSnapshot memberSnap : dataSnapshot.getChildren()) {
                                String firstname = memberSnap.child("FirstName").getValue(String.class);
                                String lastname = memberSnap.child("LastName").getValue(String.class);
                                String age = memberSnap.child("age").getValue(String.class);

                                int ageValue = Integer.parseInt(age);
                                //member.add(lastname + ", " + firstname + " (" + age + ")");

                                if (divisionCategory.equals("MasterDivision")) {
                                    if (ageValue >= 35) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                } else if (divisionCategory.equals("SeniorDivision")) {
                                    if (ageValue >= 25 && ageValue <= 34) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                } else if (divisionCategory.equals("JuniorDivision")) {
                                    if (ageValue < 24 && ageValue >= 18) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                } else if (divisionCategory.equals("MidietDivision")) {
                                    if (ageValue < 14 && ageValue > 8) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                }
                            }

                            final CharSequence[] teamMember = member.toArray(new CharSequence[member.size()]);
                            AlertDialog.Builder builderz = new AlertDialog.Builder(SubmitEntryActivity.this);
                            builderz.setTitle("Add members to your team");
                            builderz.setItems(teamMember, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    etTeamMemberE.setText(teamMember[which]);
                                }
                            });
                            AlertDialog alertDialogz = builderz.create();
                            alertDialogz.show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        etTeamMemberF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final List<String> member = new ArrayList<String>();

                        if (dataSnapshot.exists()) {

                            for (DataSnapshot memberSnap : dataSnapshot.getChildren()) {
                                String firstname = memberSnap.child("FirstName").getValue(String.class);
                                String lastname = memberSnap.child("LastName").getValue(String.class);
                                String age = memberSnap.child("age").getValue(String.class);

                                int ageValue = Integer.parseInt(age);
                                //member.add(lastname + ", " + firstname + " (" + age + ")");

                                if (divisionCategory.equals("MasterDivision")) {
                                    if (ageValue >= 35) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                } else if (divisionCategory.equals("SeniorDivision")) {
                                    if (ageValue >= 25 && ageValue <= 34) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                } else if (divisionCategory.equals("JuniorDivision")) {
                                    if (ageValue < 24 && ageValue >= 18) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                } else if (divisionCategory.equals("MidietDivision")) {
                                    if (ageValue < 14 && ageValue > 8) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                }
                            }

                            final CharSequence[] teamMember = member.toArray(new CharSequence[member.size()]);
                            AlertDialog.Builder builderz = new AlertDialog.Builder(SubmitEntryActivity.this);
                            builderz.setTitle("Add members to your team");
                            builderz.setItems(teamMember, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    etTeamMemberF.setText(teamMember[which]);
                                }
                            });
                            AlertDialog alertDialogz = builderz.create();
                            alertDialogz.show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        etTeamMemberG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final List<String> member = new ArrayList<String>();

                        if (dataSnapshot.exists()) {

                            for (DataSnapshot memberSnap : dataSnapshot.getChildren()) {
                                String firstname = memberSnap.child("FirstName").getValue(String.class);
                                String lastname = memberSnap.child("LastName").getValue(String.class);
                                String age = memberSnap.child("age").getValue(String.class);

                                int ageValue = Integer.parseInt(age);
                                //member.add(lastname + ", " + firstname + " (" + age + ")");

                                if (divisionCategory.equals("MasterDivision")) {
                                    if (ageValue >= 35) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                } else if (divisionCategory.equals("SeniorDivision")) {
                                    if (ageValue >= 25 && ageValue <= 34) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                } else if (divisionCategory.equals("JuniorDivision")) {
                                    if (ageValue < 24 && ageValue >= 18) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                } else if (divisionCategory.equals("MidietDivision")) {
                                    if (ageValue < 14 && ageValue > 8) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                }
                            }

                            final CharSequence[] teamMember = member.toArray(new CharSequence[member.size()]);
                            AlertDialog.Builder builderz = new AlertDialog.Builder(SubmitEntryActivity.this);
                            builderz.setTitle("Add members to your team");
                            builderz.setItems(teamMember, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    etTeamMemberG.setText(teamMember[which]);
                                }
                            });
                            AlertDialog alertDialogz = builderz.create();
                            alertDialogz.show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        etTeamMemberH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final List<String> member = new ArrayList<String>();

                        if (dataSnapshot.exists()) {

                            for (DataSnapshot memberSnap : dataSnapshot.getChildren()) {
                                String firstname = memberSnap.child("FirstName").getValue(String.class);
                                String lastname = memberSnap.child("LastName").getValue(String.class);
                                String age = memberSnap.child("age").getValue(String.class);

                                int ageValue = Integer.parseInt(age);
                                //member.add(lastname + ", " + firstname + " (" + age + ")");

                                if (divisionCategory.equals("MasterDivision")) {
                                    if (ageValue >= 35) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                } else if (divisionCategory.equals("SeniorDivision")) {
                                    if (ageValue >= 25 && ageValue <= 34) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                } else if (divisionCategory.equals("JuniorDivision")) {
                                    if (ageValue < 24 && ageValue >= 18) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                } else if (divisionCategory.equals("MidietDivision")) {
                                    if (ageValue < 14 && ageValue > 8) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                }
                            }

                            final CharSequence[] teamMember = member.toArray(new CharSequence[member.size()]);
                            AlertDialog.Builder builderz = new AlertDialog.Builder(SubmitEntryActivity.this);
                            builderz.setTitle("Add members to your team");
                            builderz.setItems(teamMember, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    etTeamMemberH.setText(teamMember[which]);
                                }
                            });
                            AlertDialog alertDialogz = builderz.create();
                            alertDialogz.show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        etTeamMemberI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final List<String> member = new ArrayList<String>();

                        if (dataSnapshot.exists()) {

                            for (DataSnapshot memberSnap : dataSnapshot.getChildren()) {
                                String firstname = memberSnap.child("FirstName").getValue(String.class);
                                String lastname = memberSnap.child("LastName").getValue(String.class);
                                String age = memberSnap.child("age").getValue(String.class);

                                int ageValue = Integer.parseInt(age);
                                //member.add(lastname + ", " + firstname + " (" + age + ")");

                                if (divisionCategory.equals("MasterDivision")) {
                                    if (ageValue >= 35) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                } else if (divisionCategory.equals("SeniorDivision")) {
                                    if (ageValue >= 25 && ageValue <= 34) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                } else if (divisionCategory.equals("JuniorDivision")) {
                                    if (ageValue < 24 && ageValue >= 18) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                } else if (divisionCategory.equals("MidietDivision")) {
                                    if (ageValue < 14 && ageValue > 8) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                }
                            }

                            final CharSequence[] teamMember = member.toArray(new CharSequence[member.size()]);
                            AlertDialog.Builder builderz = new AlertDialog.Builder(SubmitEntryActivity.this);
                            builderz.setTitle("Add members to your team");
                            builderz.setItems(teamMember, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    etTeamMemberI.setText(teamMember[which]);
                                }
                            });
                            AlertDialog alertDialogz = builderz.create();
                            alertDialogz.show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        etTeamMemberJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final List<String> member = new ArrayList<String>();

                        if (dataSnapshot.exists()) {

                            for (DataSnapshot memberSnap : dataSnapshot.getChildren()) {
                                String firstname = memberSnap.child("FirstName").getValue(String.class);
                                String lastname = memberSnap.child("LastName").getValue(String.class);
                                String age = memberSnap.child("age").getValue(String.class);

                                int ageValue = Integer.parseInt(age);
                                //member.add(lastname + ", " + firstname + " (" + age + ")");

                                if (divisionCategory.equals("MasterDivision")) {
                                    if (ageValue >= 35) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                } else if (divisionCategory.equals("SeniorDivision")) {
                                    if (ageValue >= 25 && ageValue <= 34) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                } else if (divisionCategory.equals("JuniorDivision")) {
                                    if (ageValue < 24 && ageValue >= 18) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                } else if (divisionCategory.equals("MidietDivision")) {
                                    if (ageValue < 14 && ageValue > 8) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                }
                            }

                            final CharSequence[] teamMember = member.toArray(new CharSequence[member.size()]);
                            AlertDialog.Builder builderz = new AlertDialog.Builder(SubmitEntryActivity.this);
                            builderz.setTitle("Add members to your team");
                            builderz.setItems(teamMember, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    etTeamMemberJ.setText(teamMember[which]);
                                }
                            });
                            AlertDialog alertDialogz = builderz.create();
                            alertDialogz.show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        etTeamMemberK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final List<String> member = new ArrayList<String>();

                        if (dataSnapshot.exists()) {

                            for (DataSnapshot memberSnap : dataSnapshot.getChildren()) {
                                String firstname = memberSnap.child("FirstName").getValue(String.class);
                                String lastname = memberSnap.child("LastName").getValue(String.class);
                                String age = memberSnap.child("age").getValue(String.class);

                                int ageValue = Integer.parseInt(age);
                                //member.add(lastname + ", " + firstname + " (" + age + ")");

                                if (divisionCategory.equals("MasterDivision")) {
                                    if (ageValue >= 35) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                } else if (divisionCategory.equals("SeniorDivision")) {
                                    if (ageValue >= 25 && ageValue <= 34) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                } else if (divisionCategory.equals("JuniorDivision")) {
                                    if (ageValue < 24 && ageValue >= 18) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                } else if (divisionCategory.equals("MidietDivision")) {
                                    if (ageValue < 14 && ageValue > 8) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                }
                            }

                            final CharSequence[] teamMember = member.toArray(new CharSequence[member.size()]);
                            AlertDialog.Builder builderz = new AlertDialog.Builder(SubmitEntryActivity.this);
                            builderz.setTitle("Add members to your team");
                            builderz.setItems(teamMember, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    etTeamMemberK.setText(teamMember[which]);
                                }
                            });
                            AlertDialog alertDialogz = builderz.create();
                            alertDialogz.show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        etTeamMemberL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final List<String> member = new ArrayList<String>();

                        if (dataSnapshot.exists()) {

                            for (DataSnapshot memberSnap : dataSnapshot.getChildren()) {
                                String firstname = memberSnap.child("FirstName").getValue(String.class);
                                String lastname = memberSnap.child("LastName").getValue(String.class);
                                String age = memberSnap.child("age").getValue(String.class);

                                int ageValue = Integer.parseInt(age);
                                //member.add(lastname + ", " + firstname + " (" + age + ")");

                                if (divisionCategory.equals("MasterDivision")) {
                                    if (ageValue >= 35) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                } else if (divisionCategory.equals("SeniorDivision")) {
                                    if (ageValue >= 25 && ageValue <= 34) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                } else if (divisionCategory.equals("JuniorDivision")) {
                                    if (ageValue < 24 && ageValue >= 18) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                } else if (divisionCategory.equals("MidietDivision")) {
                                    if (ageValue < 14 && ageValue > 8) {
                                        member.add(lastname + ", " + firstname);
                                    }
                                }
                            }

                            final CharSequence[] teamMember = member.toArray(new CharSequence[member.size()]);
                            AlertDialog.Builder builderz = new AlertDialog.Builder(SubmitEntryActivity.this);
                            builderz.setTitle("Add members to your team");
                            builderz.setItems(teamMember, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    etTeamMemberL.setText(teamMember[which]);
                                }
                            });
                            AlertDialog alertDialogz = builderz.create();
                            alertDialogz.show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}