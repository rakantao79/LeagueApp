package com.app.league.goldenboys.leagueapp.Organizer.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.google.android.gms.tasks.OnSuccessListener;
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

public class RoundRobinMatchDetailActivity extends AppCompatActivity {

    private String leagueTitle;
    private String getkey;
    private String divisionCategory;

    private DatabaseReference mDatabaseRef;
    private DatabaseReference mDatabaseMatch;
    private DatabaseReference mDatabaseTeams;
    private DatabaseReference mDatabaseRoundRobinStats;
    private DatabaseReference mDatabaseUserNotificationFinal;
    private DatabaseReference mDatabaseNewsFeed;
    private DatabaseReference mCountDatabase;

    private TextView tvRBDetailViewMatchName;
    private TextView tvRBDetailViewMatchSchedule;
    private TextView tvRBDetailViewMatchTeamA;
    private TextView tvRBDetailViewMatchTeamB;
    private EditText etRBDetailViewMatchOpponentScoreA;
    private EditText etRBDetailViewMatchOpponentScoreB;
    private TextView tvRBDetailViewMatchWinner;
    private TextView tvRBDetailViewMatchPlayerOfTheGame;
    private TextView tvRBDetailViewMatchLosser;

    private EditText etRBPlayerPoints;
    private EditText etRBPlayerRebounds;
    private EditText etRBPlayerSteal;
    private EditText etRBPlayerAssist;
    private EditText etRBPlayerBlock;

    private Button btnRBUpdateMatchScore;
    private Button btnRBUpdateMatchDetails;
    private String winner;
    private String date;
    private long totalwin = 0;
    private long currentwin = 1;
    private long totalLoss = 0;
    private long currentLoss = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_robin_match_detail);

        divisionCategory = getIntent().getStringExtra("divisionCategory");
        leagueTitle = getIntent().getStringExtra("leagueTitle");
        getkey = getIntent().getStringExtra("getkey");

        DateFormat df = new SimpleDateFormat("yyyy/MM/dd h:mm a");
        date = df.format(Calendar.getInstance().getTime());

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("LeagueEntries").child(leagueTitle).child(divisionCategory).child("RoundRobinMatchList").child(getkey);
        mDatabaseMatch = FirebaseDatabase.getInstance().getReference().child("LeagueEntries").child(leagueTitle).child(divisionCategory).child("RoundRobinMatchList");
        mDatabaseTeams = FirebaseDatabase.getInstance().getReference().child("LeagueEntries").child(leagueTitle).child(divisionCategory).child("ApprovedEntries");
        mDatabaseRoundRobinStats = FirebaseDatabase.getInstance().getReference().child("LeagueEntries").child(leagueTitle).child(divisionCategory).child("RoundRobinStats");
        mDatabaseNewsFeed = FirebaseDatabase.getInstance().getReference().child("NewsFeed");

        tvRBDetailViewMatchName = findViewById(R.id.tvRBDetailViewMatchName);
        tvRBDetailViewMatchSchedule = findViewById(R.id.tvRBDetailViewMatchSchedule);
        tvRBDetailViewMatchTeamA = findViewById(R.id.tvRBDetailViewMatchTeamAName);
        tvRBDetailViewMatchTeamB = findViewById(R.id.tvRBDetailViewMatchTeamBName);
        etRBDetailViewMatchOpponentScoreA = findViewById(R.id.etRBDetailViewMatchOpponentScoreA);
        etRBDetailViewMatchOpponentScoreB = findViewById(R.id.etRBDetailViewMatchOpponentScoreB);
        tvRBDetailViewMatchWinner = findViewById(R.id.tvRBDetailViewMatchWinner);
        tvRBDetailViewMatchPlayerOfTheGame = findViewById(R.id.tvRBDetailViewMatchPlayerOfTheGame);
        tvRBDetailViewMatchLosser = findViewById(R.id.tvRBDetailViewMatchLosser);

        etRBPlayerPoints = findViewById(R.id.etRBPlayerPoints);
        etRBPlayerRebounds = findViewById(R.id.etRBPlayerRebounds);
        etRBPlayerAssist = findViewById(R.id.etRBPlayerAssist);
        etRBPlayerSteal = findViewById(R.id.etRBPlayerSteal);
        etRBPlayerBlock = findViewById(R.id.etRBPlayerBlock);

        btnRBUpdateMatchScore = findViewById(R.id.btnRBUpdateMatchScore);
        btnRBUpdateMatchDetails = findViewById(R.id.btnRBUpdateMatchDetails);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    tvRBDetailViewMatchName.setText(dataSnapshot.child("matchName").getValue().toString());
                    tvRBDetailViewMatchSchedule.setText(dataSnapshot.child("matchSchedule").getValue().toString());
                    tvRBDetailViewMatchTeamA.setText(dataSnapshot.child("matchOpponentA").getValue().toString());
                    tvRBDetailViewMatchTeamB.setText(dataSnapshot.child("matchOpponentB").getValue().toString());
                    etRBDetailViewMatchOpponentScoreA.setText(dataSnapshot.child("matchOpponentAScore").getValue().toString());
                    etRBDetailViewMatchOpponentScoreB.setText(dataSnapshot.child("matchOpponentBScore").getValue().toString());
                    tvRBDetailViewMatchWinner.setText(dataSnapshot.child("matchWinner").getValue().toString());
                    tvRBDetailViewMatchPlayerOfTheGame.setText(dataSnapshot.child("matchPlayerOfTheGame").getValue().toString());

                    etRBPlayerPoints.setText(dataSnapshot.child("matchPlayerPoints").getValue().toString());
                    etRBPlayerRebounds.setText(dataSnapshot.child("matchPlayerRebounds").getValue().toString());
                    etRBPlayerAssist.setText(dataSnapshot.child("matchPlayerAssist").getValue().toString());
                    etRBPlayerBlock.setText(dataSnapshot.child("matchPlayerBlock").getValue().toString());
                    etRBPlayerSteal.setText(dataSnapshot.child("matchPlayerSteal").getValue().toString());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        tvRBDetailViewMatchPlayerOfTheGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseTeams.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {

                            final List<String> players = new ArrayList<String>();

                            for (DataSnapshot playerSnapShot : dataSnapshot.getChildren()) {
                                String teamName = playerSnapShot.child("teamName").getValue(String.class);

                                if (tvRBDetailViewMatchWinner.getText().toString().equals(teamName)) {

                                    players.add(playerSnapShot.child("teamMemberA").getValue(String.class));
                                    players.add(playerSnapShot.child("teamMemberB").getValue(String.class));
                                    players.add(playerSnapShot.child("teamMemberC").getValue(String.class));
                                    players.add(playerSnapShot.child("teamMemberD").getValue(String.class));
                                    players.add(playerSnapShot.child("teamMemberE").getValue(String.class));
                                    players.add(playerSnapShot.child("teamMemberF").getValue(String.class));
                                    players.add(playerSnapShot.child("teamMemberG").getValue(String.class));
                                    players.add(playerSnapShot.child("teamMemberH").getValue(String.class));
                                    players.add(playerSnapShot.child("teamMemberI").getValue(String.class));
                                    players.add(playerSnapShot.child("teamMemberJ").getValue(String.class));
                                    players.add(playerSnapShot.child("teamMemberK").getValue(String.class));
                                    players.add(playerSnapShot.child("teamMemberL").getValue(String.class));

                                }
                            }
                            final CharSequence[] teamMember = players.toArray(new CharSequence[players.size()]);
                            AlertDialog.Builder builderz = new AlertDialog.Builder(RoundRobinMatchDetailActivity.this);
                            builderz.setTitle("SELECT BEST PLAYER");
                            builderz.setItems(teamMember, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    tvRBDetailViewMatchPlayerOfTheGame.setText(teamMember[which]);
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

        btnRBUpdateMatchScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveScore();
            }
        });

        btnRBUpdateMatchDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMatchDetails();
            }
        });

    }

    private void saveMatchDetails() {
        final String matchName = tvRBDetailViewMatchName.getText().toString().trim();
        final String scoreA = etRBDetailViewMatchOpponentScoreA.getText().toString().trim();
        final String scoreB = etRBDetailViewMatchOpponentScoreB.getText().toString().trim();
        winner = tvRBDetailViewMatchWinner.getText().toString().trim();
        final String playerOfTheGame = tvRBDetailViewMatchPlayerOfTheGame.getText().toString();
        final String points = etRBPlayerPoints.getText().toString();
        final String rebounds = etRBPlayerRebounds.getText().toString();
        final String assists = etRBPlayerAssist.getText().toString();
        final String steal = etRBPlayerSteal.getText().toString();
        final String block = etRBPlayerBlock.getText().toString();

        if (scoreA.equals("0") || scoreB.equals("0") || winner.equals("default")) {

            Toast.makeText(this, "Please update match details", Toast.LENGTH_SHORT).show();

        } else if (scoreA.equals(scoreB) || scoreB.equals(scoreA)){

            Toast.makeText(this, "Score must not be same", Toast.LENGTH_SHORT).show();

        } else {

            mDatabaseRef.child("matchOpponentAScore").setValue(scoreA);
            mDatabaseRef.child("matchOpponentBScore").setValue(scoreB);
            mDatabaseRef.child("matchWinner").setValue(winner);
            mDatabaseRef.child("matchPlayerOfTheGame").setValue(playerOfTheGame);
            mDatabaseRef.child("matchPlayerPoints").setValue(points);
            mDatabaseRef.child("matchPlayerBlock").setValue(rebounds);
            mDatabaseRef.child("matchPlayerAssist").setValue(assists);
            mDatabaseRef.child("matchPlayerSteal").setValue(steal);
            mDatabaseRef.child("matchPlayerBlock").setValue(block);

            mDatabaseRoundRobinStats.child(tvRBDetailViewMatchWinner.getText().toString()).child("Win").push().setValue("defeated " + tvRBDetailViewMatchLosser.getText().toString() + " during " + matchName).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    mDatabaseRoundRobinStats.child(tvRBDetailViewMatchLosser.getText().toString()).child("Loss").push().setValue("lost to " + tvRBDetailViewMatchWinner.getText().toString()  + " during " + matchName).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(RoundRobinMatchDetailActivity.this, "Stats updated", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });

                }
            });

//            mDatabaseRoundRobinStats.child(tvRBDetailViewMatchWinner.getText().toString()).addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    if (dataSnapshot.exists()){
//
//                        totalwin = Long.parseLong(dataSnapshot.child("Win").getValue().toString());
//                        totalwin = totalwin + 1;
//
//                        Log.d("winer" + tvRBDetailViewMatchWinner.getText().toString(), "onDataChange: " + totalwin);
//
//                        mDatabaseRoundRobinStats.child(tvRBDetailViewMatchWinner.getText().toString()).child("Win").setValue(totalwin).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                mDatabaseRoundRobinStats.child(tvRBDetailViewMatchLosser.getText().toString()).addValueEventListener(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(DataSnapshot dataSnapshot) {
//                                        totalLoss = Long.parseLong(dataSnapshot.child("Loss").getValue().toString());
//                                        Log.d("totalLoss", "onDataChange: " + totalLoss);
//                                        totalLoss = totalLoss + 1;
//
//                                        mDatabaseRoundRobinStats.child(tvRBDetailViewMatchLosser.getText().toString()).child("Loss").setValue(totalLoss).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void aVoid) {
//                                                finish();
//                                            }
//                                        });
//                                    }
//
//                                    @Override
//                                    public void onCancelled(DatabaseError databaseError) {
//
//                                    }
//                                });
//                            }
//                        });
//                    }
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });


            mDatabaseUserNotificationFinal = FirebaseDatabase.getInstance().getReference().child("UserNotificationFinal");

            if (matchName.equals("Match 1")){

                HashMap notifMap = new HashMap();

                notifMap.put("notifTitle", winner + " wins " + " " + matchName + " " + leagueTitle + " - " + divisionCategory);
                notifMap.put("notifMatch", winner + " wins " + " " + matchName + " " + leagueTitle + " - " + divisionCategory);
                notifMap.put("notfiDate", date);

                mDatabaseUserNotificationFinal.push().setValue(notifMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        String pushkey = mDatabaseNewsFeed.push().getKey();

                        HashMap newsFeedMap = new HashMap();
                        newsFeedMap.put("newstitle", winner + " advances to next round of " + leagueTitle + "-" + divisionCategory);
                        newsFeedMap.put("newscontent", winner + " won the " + matchName + " " + leagueTitle + "-" + divisionCategory
                                + " with final Score of " + scoreA + "-" + scoreB + ". "
                                + "\n\nPlayer of the game is " + playerOfTheGame + " with " + points + " Points, " + rebounds + " Rebounds, "
                                + assists + " Assists, " + steal + " Steals and " + block + " Blocks.");
                        newsFeedMap.put("pushKey", pushkey);
                        newsFeedMap.put("datePosted", date);
                        newsFeedMap.put("user_id", "");
                        newsFeedMap.put("author", "");
                        newsFeedMap.put("imageUrl", "default");

                        mDatabaseNewsFeed.push().setValue(newsFeedMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(RoundRobinMatchDetailActivity.this,  winner + " wins", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

            } else if (matchName.equals("Match 2")){

                HashMap notifMap = new HashMap();

                notifMap.put("notifTitle", winner + " wins " + " " + matchName + " " + leagueTitle + " - " + divisionCategory);
                notifMap.put("notifMatch", winner + " wins " + " " + matchName + " " + leagueTitle + " - " + divisionCategory);
                notifMap.put("notfiDate", date);

                mDatabaseUserNotificationFinal.push().setValue(notifMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        String pushkey = mDatabaseNewsFeed.push().getKey();

                        HashMap newsFeedMap = new HashMap();
                        newsFeedMap.put("newstitle", winner + " advances to next round of " + leagueTitle + "-" + divisionCategory);
                        newsFeedMap.put("newscontent", winner + " won the " + matchName + " " + leagueTitle + "-" + divisionCategory
                                + " with final Score of " + scoreA + "-" + scoreB + ". "
                                + "\n\nPlayer of the game is " + playerOfTheGame + " with " + points + " Points, " + rebounds + " Rebounds, "
                                + assists + " Assists, " + steal + " Steals and " + block + " Blocks.");
                        newsFeedMap.put("pushKey", pushkey);
                        newsFeedMap.put("datePosted", date);
                        newsFeedMap.put("user_id", "");
                        newsFeedMap.put("author", "");
                        newsFeedMap.put("imageUrl", "default");

                        mDatabaseNewsFeed.push().setValue(newsFeedMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(RoundRobinMatchDetailActivity.this,  winner + " wins", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

            } else if (matchName.equals("Match 3")){

                HashMap notifMap = new HashMap();

                notifMap.put("notifTitle", winner + " wins " + " " + matchName + " " + leagueTitle + " - " + divisionCategory);
                notifMap.put("notifMatch", winner + " wins " + " " + matchName + " " + leagueTitle + " - " + divisionCategory);
                notifMap.put("notfiDate", date);

                mDatabaseUserNotificationFinal.push().setValue(notifMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        String pushkey = mDatabaseNewsFeed.push().getKey();

                        HashMap newsFeedMap = new HashMap();
                        newsFeedMap.put("newstitle", winner + " advances to next round of " + leagueTitle + "-" + divisionCategory);
                        newsFeedMap.put("newscontent", winner + " won the " + matchName + " " + leagueTitle + "-" + divisionCategory
                                + " with final Score of " + scoreA + "-" + scoreB + ". "
                                + "\n\nPlayer of the game is " + playerOfTheGame + " with " + points + " Points, " + rebounds + " Rebounds, "
                                + assists + " Assists, " + steal + " Steals and " + block + " Blocks.");
                        newsFeedMap.put("pushKey", pushkey);
                        newsFeedMap.put("datePosted", date);
                        newsFeedMap.put("user_id", "");
                        newsFeedMap.put("author", "");
                        newsFeedMap.put("imageUrl", "default");

                        mDatabaseNewsFeed.push().setValue(newsFeedMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(RoundRobinMatchDetailActivity.this,  winner + " wins", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

            } else if (matchName.equals("Match 4")){

                HashMap notifMap = new HashMap();

                notifMap.put("notifTitle", winner + " wins " + " " + matchName + " " + leagueTitle + " - " + divisionCategory);
                notifMap.put("notifMatch", winner + " wins " + " " + matchName + " " + leagueTitle + " - " + divisionCategory);
                notifMap.put("notfiDate", date);

                mDatabaseUserNotificationFinal.push().setValue(notifMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        String pushkey = mDatabaseNewsFeed.push().getKey();

                        HashMap newsFeedMap = new HashMap();
                        newsFeedMap.put("newstitle", winner + " advances to next round of " + leagueTitle + "-" + divisionCategory);
                        newsFeedMap.put("newscontent", winner + " won the " + matchName + " " + leagueTitle + "-" + divisionCategory
                                + " with final Score of " + scoreA + "-" + scoreB + ". "
                                + "\n\nPlayer of the game is " + playerOfTheGame + " with " + points + " Points, " + rebounds + " Rebounds, "
                                + assists + " Assists, " + steal + " Steals and " + block + " Blocks.");
                        newsFeedMap.put("pushKey", pushkey);
                        newsFeedMap.put("datePosted", date);
                        newsFeedMap.put("user_id", "");
                        newsFeedMap.put("author", "");
                        newsFeedMap.put("imageUrl", "default");

                        mDatabaseNewsFeed.push().setValue(newsFeedMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(RoundRobinMatchDetailActivity.this,  winner + " wins", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

            } else if (matchName.equals("Match 5")){

                HashMap notifMap = new HashMap();

                notifMap.put("notifTitle", winner + " wins " + " " + matchName + " " + leagueTitle + " - " + divisionCategory);
                notifMap.put("notifMatch", winner + " wins " + " " + matchName + " " + leagueTitle + " - " + divisionCategory);
                notifMap.put("notfiDate", date);

                mDatabaseUserNotificationFinal.push().setValue(notifMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        String pushkey = mDatabaseNewsFeed.push().getKey();

                        HashMap newsFeedMap = new HashMap();
                        newsFeedMap.put("newstitle", winner + " advances to next round of " + leagueTitle + "-" + divisionCategory);
                        newsFeedMap.put("newscontent", winner + " won the " + matchName + " " + leagueTitle + "-" + divisionCategory
                                + " with final Score of " + scoreA + "-" + scoreB + ". "
                                + "\n\nPlayer of the game is " + playerOfTheGame + " with " + points + " Points, " + rebounds + " Rebounds, "
                                + assists + " Assists, " + steal + " Steals and " + block + " Blocks.");
                        newsFeedMap.put("pushKey", pushkey);
                        newsFeedMap.put("datePosted", date);
                        newsFeedMap.put("user_id", "");
                        newsFeedMap.put("author", "");
                        newsFeedMap.put("imageUrl", "default");

                        mDatabaseNewsFeed.push().setValue(newsFeedMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(RoundRobinMatchDetailActivity.this,  winner + " wins", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

            } else if (matchName.equals("Match 6")){

                HashMap notifMap = new HashMap();

                notifMap.put("notifTitle", winner + " wins " + " " + matchName + " " + leagueTitle + " - " + divisionCategory);
                notifMap.put("notifMatch", winner + " wins " + " " + matchName + " " + leagueTitle + " - " + divisionCategory);
                notifMap.put("notfiDate", date);

                mDatabaseUserNotificationFinal.push().setValue(notifMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        String pushkey = mDatabaseNewsFeed.push().getKey();

                        HashMap newsFeedMap = new HashMap();
                        newsFeedMap.put("newstitle", winner + " advances to next round of " + leagueTitle + "-" + divisionCategory);
                        newsFeedMap.put("newscontent", winner + " won the " + matchName + " " + leagueTitle + "-" + divisionCategory
                                + " with final Score of " + scoreA + "-" + scoreB + ". "
                                + "\n\nPlayer of the game is " + playerOfTheGame + " with " + points + " Points, " + rebounds + " Rebounds, "
                                + assists + " Assists, " + steal + " Steals and " + block + " Blocks.");
                        newsFeedMap.put("pushKey", pushkey);
                        newsFeedMap.put("datePosted", date);
                        newsFeedMap.put("user_id", "");
                        newsFeedMap.put("author", "");
                        newsFeedMap.put("imageUrl", "default");

                        mDatabaseNewsFeed.push().setValue(newsFeedMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(RoundRobinMatchDetailActivity.this,  winner + " wins", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

            } else if (matchName.equals("Match 7")){

                HashMap notifMap = new HashMap();

                notifMap.put("notifTitle", winner + " wins " + " " + matchName + " " + leagueTitle + " - " + divisionCategory);
                notifMap.put("notifMatch", winner + " wins " + " " + matchName + " " + leagueTitle + " - " + divisionCategory);
                notifMap.put("notfiDate", date);

                mDatabaseUserNotificationFinal.push().setValue(notifMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        String pushkey = mDatabaseNewsFeed.push().getKey();

                        HashMap newsFeedMap = new HashMap();
                        newsFeedMap.put("newstitle", winner + " advances to next round of " + leagueTitle + "-" + divisionCategory);
                        newsFeedMap.put("newscontent", winner + " won the " + matchName + " " + leagueTitle + "-" + divisionCategory
                                + " with final Score of " + scoreA + "-" + scoreB + ". "
                                + "\n\nPlayer of the game is " + playerOfTheGame + " with " + points + " Points, " + rebounds + " Rebounds, "
                                + assists + " Assists, " + steal + " Steals and " + block + " Blocks.");
                        newsFeedMap.put("pushKey", pushkey);
                        newsFeedMap.put("datePosted", date);
                        newsFeedMap.put("user_id", "");
                        newsFeedMap.put("author", "");
                        newsFeedMap.put("imageUrl", "default");

                        mDatabaseNewsFeed.push().setValue(newsFeedMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(RoundRobinMatchDetailActivity.this,  winner + " wins", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

            } else if (matchName.equals("Match 8")){

                HashMap notifMap = new HashMap();

                notifMap.put("notifTitle", winner + " wins " + " " + matchName + " " + leagueTitle + " - " + divisionCategory);
                notifMap.put("notifMatch", winner + " wins " + " " + matchName + " " + leagueTitle + " - " + divisionCategory);
                notifMap.put("notfiDate", date);

                mDatabaseUserNotificationFinal.push().setValue(notifMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        String pushkey = mDatabaseNewsFeed.push().getKey();

                        HashMap newsFeedMap = new HashMap();
                        newsFeedMap.put("newstitle", winner + " advances to next round of " + leagueTitle + "-" + divisionCategory);
                        newsFeedMap.put("newscontent", winner + " won the " + matchName + " " + leagueTitle + "-" + divisionCategory
                                + " with final Score of " + scoreA + "-" + scoreB + ". "
                                + "\n\nPlayer of the game is " + playerOfTheGame + " with " + points + " Points, " + rebounds + " Rebounds, "
                                + assists + " Assists, " + steal + " Steals and " + block + " Blocks.");
                        newsFeedMap.put("pushKey", pushkey);
                        newsFeedMap.put("datePosted", date);
                        newsFeedMap.put("user_id", "");
                        newsFeedMap.put("author", "");
                        newsFeedMap.put("imageUrl", "default");

                        mDatabaseNewsFeed.push().setValue(newsFeedMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(RoundRobinMatchDetailActivity.this,  winner + " wins", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

            } else if (matchName.equals("Match 9")){

                HashMap notifMap = new HashMap();

                notifMap.put("notifTitle", winner + " wins " + " " + matchName + " " + leagueTitle + " - " + divisionCategory);
                notifMap.put("notifMatch", winner + " wins " + " " + matchName + " " + leagueTitle + " - " + divisionCategory);
                notifMap.put("notfiDate", date);

                mDatabaseUserNotificationFinal.push().setValue(notifMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        String pushkey = mDatabaseNewsFeed.push().getKey();

                        HashMap newsFeedMap = new HashMap();
                        newsFeedMap.put("newstitle", winner + " advances to next round of " + leagueTitle + "-" + divisionCategory);
                        newsFeedMap.put("newscontent", winner + " won the " + matchName + " " + leagueTitle + "-" + divisionCategory
                                + " with final Score of " + scoreA + "-" + scoreB + ". "
                                + "\n\nPlayer of the game is " + playerOfTheGame + " with " + points + " Points, " + rebounds + " Rebounds, "
                                + assists + " Assists, " + steal + " Steals and " + block + " Blocks.");
                        newsFeedMap.put("pushKey", pushkey);
                        newsFeedMap.put("datePosted", date);
                        newsFeedMap.put("user_id", "");
                        newsFeedMap.put("author", "");
                        newsFeedMap.put("imageUrl", "default");

                        mDatabaseNewsFeed.push().setValue(newsFeedMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(RoundRobinMatchDetailActivity.this,  winner + " wins", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

            }else if (matchName.equals("Match 10")){

                HashMap notifMap = new HashMap();

                notifMap.put("notifTitle", winner + " wins " + " " + matchName + " " + leagueTitle + " - " + divisionCategory);
                notifMap.put("notifMatch", winner + " wins " + " " + matchName + " " + leagueTitle + " - " + divisionCategory);
                notifMap.put("notfiDate", date);

                mDatabaseUserNotificationFinal.push().setValue(notifMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        String pushkey = mDatabaseNewsFeed.push().getKey();

                        HashMap newsFeedMap = new HashMap();
                        newsFeedMap.put("newstitle", winner + " advances to next round of " + leagueTitle + "-" + divisionCategory);
                        newsFeedMap.put("newscontent", winner + " won the " + matchName + " " + leagueTitle + "-" + divisionCategory
                                + " with final Score of " + scoreA + "-" + scoreB + ". "
                                + "\n\nPlayer of the game is " + playerOfTheGame + " with " + points + " Points, " + rebounds + " Rebounds, "
                                + assists + " Assists, " + steal + " Steals and " + block + " Blocks.");
                        newsFeedMap.put("pushKey", pushkey);
                        newsFeedMap.put("datePosted", date);
                        newsFeedMap.put("user_id", "");
                        newsFeedMap.put("author", "");
                        newsFeedMap.put("imageUrl", "default");

                        mDatabaseNewsFeed.push().setValue(newsFeedMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(RoundRobinMatchDetailActivity.this,  winner + " wins", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

            } else if (matchName.equals("Match 11")){
                mDatabaseMatch.child("Match M").child("matchOpponentA").setValue(winner).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        HashMap notifMap = new HashMap();

                        notifMap.put("notifTitle", winner + " wins " + " " + matchName + " " + leagueTitle + " - " + divisionCategory);
                        notifMap.put("notifMatch", winner + " wins " + " " + matchName + " " + leagueTitle + " - " + divisionCategory);
                        notifMap.put("notfiDate", date);

                        mDatabaseUserNotificationFinal.push().setValue(notifMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                String pushkey = mDatabaseNewsFeed.push().getKey();

                                HashMap newsFeedMap = new HashMap();
                                newsFeedMap.put("newstitle", winner + " advances to next round of " + leagueTitle + "-" + divisionCategory);
                                newsFeedMap.put("newscontent", winner + " won the " + matchName + " " + leagueTitle + "-" + divisionCategory
                                        + " with final Score of " + scoreA + "-" + scoreB + ". "
                                        + "\n\nPlayer of the game is " + playerOfTheGame + " with " + points + " Points, " + rebounds + " Rebounds, "
                                        + assists + " Assists, " + steal + " Steals and " + block + " Blocks.");
                                newsFeedMap.put("pushKey", pushkey);
                                newsFeedMap.put("datePosted", date);
                                newsFeedMap.put("user_id", "");
                                newsFeedMap.put("author", "");
                                newsFeedMap.put("imageUrl", "default");

                                mDatabaseNewsFeed.push().setValue(newsFeedMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(RoundRobinMatchDetailActivity.this,  winner + " wins", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });

                    }
                });
            } else if (matchName.equals("Match 12")){
                mDatabaseMatch.child("Match M").child("matchOpponentB").setValue(winner).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        HashMap notifMap = new HashMap();

                        notifMap.put("notifTitle", winner + " wins " + " " + matchName + " " + leagueTitle + " - " + divisionCategory);
                        notifMap.put("notifMatch", winner + " wins " + " " + matchName + " " + leagueTitle + " - " + divisionCategory);
                        notifMap.put("notfiDate", date);

                        mDatabaseUserNotificationFinal.push().setValue(notifMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                String pushkey = mDatabaseNewsFeed.push().getKey();

                                HashMap newsFeedMap = new HashMap();
                                newsFeedMap.put("newstitle", winner + " advances to next round of " + leagueTitle + "-" + divisionCategory);
                                newsFeedMap.put("newscontent", winner + " won the " + matchName + " " + leagueTitle + "-" + divisionCategory
                                        + " with final Score of " + scoreA + "-" + scoreB + ". "
                                        + "\n\nPlayer of the game is " + playerOfTheGame + " with " + points + " Points, " + rebounds + " Rebounds, "
                                        + assists + " Assists, " + steal + " Steals and " + block + " Blocks.");
                                newsFeedMap.put("pushKey", pushkey);
                                newsFeedMap.put("datePosted", date);
                                newsFeedMap.put("user_id", "");
                                newsFeedMap.put("author", "");
                                newsFeedMap.put("imageUrl", "default");

                                mDatabaseNewsFeed.push().setValue(newsFeedMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(RoundRobinMatchDetailActivity.this,  winner + " wins", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                });
            } else if (matchName.equals("Match 13")){

                HashMap notifMap = new HashMap();
                notifMap.put("notifTitle", winner + " wins " + " " + matchName + " " + leagueTitle + " - " + divisionCategory);
                notifMap.put("notifMatch", winner + " wins " + " " + matchName + " " + leagueTitle + " - " + divisionCategory);
                notifMap.put("notfiDate", date);

                mDatabaseUserNotificationFinal.push().setValue(notifMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        String pushkey = mDatabaseNewsFeed.push().getKey();

                        HashMap newsFeedMap = new HashMap();
                        newsFeedMap.put("newstitle", winner + " won the championships of " + leagueTitle + "-" + divisionCategory);
                        newsFeedMap.put("newscontent", winner + " won the championships " + leagueTitle + "-" + divisionCategory
                                + " with final Score of " + scoreA + "-" + scoreB + ". "
                                + "\n\nPlayer of the game is " + playerOfTheGame + " with " + points + " Points, " + rebounds + " Rebounds, "
                                + assists + " Assists, " + steal + " Steals and " + block + " Blocks.");
                        newsFeedMap.put("pushKey", pushkey);
                        newsFeedMap.put("datePosted", date);
                        newsFeedMap.put("user_id", "");
                        newsFeedMap.put("author", "");
                        newsFeedMap.put("imageUrl", "default");

                        mDatabaseNewsFeed.push().setValue(newsFeedMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(RoundRobinMatchDetailActivity.this, winner + " is the Champion of " + leagueTitle + " " + divisionCategory, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }

        }
    }

    private void saveScore() {
        String scoreA = etRBDetailViewMatchOpponentScoreA.getText().toString().trim();
        String scoreB = etRBDetailViewMatchOpponentScoreB.getText().toString().trim();

        if (TextUtils.isEmpty(scoreA) || TextUtils.isEmpty(scoreB)) {
            Toast.makeText(this, "Please Update Score", Toast.LENGTH_SHORT).show();
        } else {

            int scoreAInt = Integer.parseInt(scoreA);
            int scoreBInt = Integer.parseInt(scoreB);

            if (scoreAInt > scoreBInt) {
                tvRBDetailViewMatchWinner.setText(tvRBDetailViewMatchTeamA.getText().toString());
                tvRBDetailViewMatchLosser.setText(tvRBDetailViewMatchTeamB.getText().toString());


            } else if (scoreAInt == scoreBInt){
                Toast.makeText(this, "Score must not same", Toast.LENGTH_SHORT).show();
            } else {
                tvRBDetailViewMatchWinner.setText(tvRBDetailViewMatchTeamB.getText().toString());
                tvRBDetailViewMatchLosser.setText(tvRBDetailViewMatchTeamA.getText().toString());
            }
        }
    }
}
