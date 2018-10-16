package com.app.league.goldenboys.leagueapp.Organizer.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

public class MatchListDetailedViewActivity extends AppCompatActivity {

    private String leagueTitle;
    private String getkey;
    private String divisionCategory;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference mDatabaseMatch5;
    private DatabaseReference mDatabaseTeams;
    private DatabaseReference mDatabaseUserNotificationFinal;
    private DatabaseReference mDatabaseNewsFeed;
    private DatabaseReference mCountDatabase;
    private String pushkey;

    private TextView tvDetailViewMatchName;
    private TextView tvDetailViewMatchSchedule;
    private TextView tvDetailViewMatchTeamA;
    private TextView tvDetailViewMatchTeamB;
    private EditText etDetailViewMatchOpponentScoreA;
    private EditText etDetailViewMatchOpponentScoreB;
    private TextView tvDetailViewMatchWinner;
    private TextView tvDetailViewMatchPlayerOfTheGame;

    private EditText etPlayerPoints;
    private EditText etPlayerRebounds;
    private EditText etPlayerSteal;
    private EditText etPlayerAssist;
    private EditText etPlayerBlock;

    private Button btnUpdateMatchScore;
    private Button btnUpdateMatchDetails;
    private String winner;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_list_detailed_view);

        divisionCategory = getIntent().getStringExtra("divisionCategory");
        leagueTitle = getIntent().getStringExtra("leagueTitle");
        getkey = getIntent().getStringExtra("getkey");

        DateFormat df = new SimpleDateFormat("yyyy/MM/dd h:mm a");
        date = df.format(Calendar.getInstance().getTime());

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("LeagueEntries").child(leagueTitle).child(divisionCategory).child("MatchList").child(getkey);
        mDatabaseMatch5 = FirebaseDatabase.getInstance().getReference().child("LeagueEntries").child(leagueTitle).child(divisionCategory).child("MatchList");
        mDatabaseTeams = FirebaseDatabase.getInstance().getReference().child("LeagueEntries").child(leagueTitle).child(divisionCategory).child("ApprovedEntries");
        mDatabaseUserNotificationFinal = FirebaseDatabase.getInstance().getReference().child("UserNotificationFinal");
        pushkey = mDatabaseUserNotificationFinal.push().getKey();

        mDatabaseNewsFeed = FirebaseDatabase.getInstance().getReference().child("NewsFeed");

        tvDetailViewMatchName = findViewById(R.id.tvDetailViewMatchName);
        tvDetailViewMatchSchedule = findViewById(R.id.tvDetailViewMatchSchedule);
        tvDetailViewMatchTeamA = findViewById(R.id.tvDetailViewMatchTeamAName);
        tvDetailViewMatchTeamB = findViewById(R.id.tvDetailViewMatchTeamBName);
        etDetailViewMatchOpponentScoreA = findViewById(R.id.etDetailViewMatchOpponentScoreA);
        etDetailViewMatchOpponentScoreB = findViewById(R.id.etDetailViewMatchOpponentScoreB);
        tvDetailViewMatchWinner = findViewById(R.id.tvDetailViewMatchWinner);
        tvDetailViewMatchPlayerOfTheGame = findViewById(R.id.tvDetailViewMatchPlayerOfTheGame);

        etPlayerPoints = findViewById(R.id.etPlayerPoints);
        etPlayerRebounds = findViewById(R.id.etPlayerRebounds);
        etPlayerAssist = findViewById(R.id.etPlayerAssist);
        etPlayerSteal = findViewById(R.id.etPlayerSteal);
        etPlayerBlock = findViewById(R.id.etPlayerBlock);

        btnUpdateMatchScore = findViewById(R.id.btnUpdateMatchScore);
        btnUpdateMatchDetails = findViewById(R.id.btnUpdateMatchDetails);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    tvDetailViewMatchName.setText(dataSnapshot.child("matchName").getValue().toString());
                    tvDetailViewMatchSchedule.setText(dataSnapshot.child("matchSchedule").getValue().toString());
                    tvDetailViewMatchTeamA.setText(dataSnapshot.child("matchOpponentA").getValue().toString());
                    tvDetailViewMatchTeamB.setText(dataSnapshot.child("matchOpponentB").getValue().toString());
                    etDetailViewMatchOpponentScoreA.setText(dataSnapshot.child("matchOpponentAScore").getValue().toString());
                    etDetailViewMatchOpponentScoreB.setText(dataSnapshot.child("matchOpponentBScore").getValue().toString());
                    tvDetailViewMatchWinner.setText(dataSnapshot.child("matchWinner").getValue().toString());
                    tvDetailViewMatchPlayerOfTheGame.setText(dataSnapshot.child("matchPlayerOfTheGame").getValue().toString());

                    etPlayerPoints.setText(dataSnapshot.child("matchPlayerPoints").getValue().toString());
                    etPlayerRebounds.setText(dataSnapshot.child("matchPlayerRebounds").getValue().toString());
                    etPlayerAssist.setText(dataSnapshot.child("matchPlayerAssist").getValue().toString());
                    etPlayerBlock.setText(dataSnapshot.child("matchPlayerBlock").getValue().toString());
                    etPlayerSteal.setText(dataSnapshot.child("matchPlayerSteal").getValue().toString());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        tvDetailViewMatchPlayerOfTheGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseTeams.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {

                            final List<String> players = new ArrayList<String>();

                            for (DataSnapshot playerSnapShot : dataSnapshot.getChildren()) {
                                String teamName = playerSnapShot.child("teamName").getValue(String.class);

                                if (tvDetailViewMatchWinner.getText().toString().equals(teamName)) {
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
                            AlertDialog.Builder builderz = new AlertDialog.Builder(MatchListDetailedViewActivity.this);
                            builderz.setTitle("Add members to your team");
                            builderz.setItems(teamMember, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    tvDetailViewMatchPlayerOfTheGame.setText(teamMember[which]);
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

        btnUpdateMatchScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveScore();
            }
        });

        btnUpdateMatchDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMatchDetails();
            }
        });
    }

    private void saveMatchDetails() {
        final String matchName = tvDetailViewMatchName.getText().toString().trim();
        final String scoreA = etDetailViewMatchOpponentScoreA.getText().toString().trim();
        final String scoreB = etDetailViewMatchOpponentScoreB.getText().toString().trim();
        winner = tvDetailViewMatchWinner.getText().toString().trim();
        final String playerOfTheGame = tvDetailViewMatchPlayerOfTheGame.getText().toString();
        final String points = etPlayerPoints.getText().toString();
        final String rebounds = etPlayerRebounds.getText().toString();
        final String assists = etPlayerAssist.getText().toString();
        final String steal = etPlayerSteal.getText().toString();
        final String block = etPlayerBlock.getText().toString();

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

            mCountDatabase = FirebaseDatabase.getInstance().getReference().child("NewsFeed");


            final long[] countpost = new long[1];
            mCountDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        countpost[0] = dataSnapshot.getChildrenCount();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            if (matchName.equals("Match 1")) {
                mDatabaseMatch5.child("Match5").child("matchOpponentA").setValue(winner).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        HashMap finalNotifMap = new HashMap();
                        finalNotifMap.put("notifTitle", winner + " advances to next round " + leagueTitle + " " + divisionCategory);
                        finalNotifMap.put("notifMatch", "Winner Match 1 VS Winner Match 2");
                        finalNotifMap.put("notfiDate", date);
                        finalNotifMap.put("notifKey", pushkey);

                        mDatabaseUserNotificationFinal.push().setValue(finalNotifMap).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                                        Toast.makeText(MatchListDetailedViewActivity.this, winner + " advances to next round", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                });
            } else if (matchName.equals("Match 2")) {
                mDatabaseMatch5.child("Match5").child("matchOpponentB").setValue(winner).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        HashMap finalNotifMap = new HashMap();
                        finalNotifMap.put("notifTitle", winner + " advances to next round " + leagueTitle + " " + divisionCategory);
                        finalNotifMap.put("notifMatch", "Winner Match 1 VS Winner Match 2");
                        finalNotifMap.put("notfiDate", date);
                        finalNotifMap.put("notifKey", pushkey);

                        mDatabaseUserNotificationFinal.push().setValue(finalNotifMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MatchListDetailedViewActivity.this, winner + " advances to next round", Toast.LENGTH_SHORT).show();

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
                                        Toast.makeText(MatchListDetailedViewActivity.this, winner + " advances to next round", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });
                    }
                });
            } else if (matchName.equals("Match 3")) {
                mDatabaseMatch5.child("Match6").child("matchOpponentA").setValue(winner).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        HashMap finalNotifMap = new HashMap();
                        finalNotifMap.put("notifTitle", winner + " advances to next round " + leagueTitle + " " + divisionCategory);
                        finalNotifMap.put("notifMatch", "Winner Match 1 VS Winner Match 2");
                        finalNotifMap.put("notfiDate", date);
                        finalNotifMap.put("notifKey", pushkey);

                        mDatabaseUserNotificationFinal.push().setValue(finalNotifMap).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                                        Toast.makeText(MatchListDetailedViewActivity.this, winner + " advances to next round", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                });
            } else if (matchName.equals("Match 4")) {
                mDatabaseMatch5.child("Match6").child("matchOpponentB").setValue(winner).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        HashMap finalNotifMap = new HashMap();
                        finalNotifMap.put("notifTitle", winner + " advances to next round " + leagueTitle + " " + divisionCategory);
                        finalNotifMap.put("notifMatch", "Winner Match 1 VS Winner Match 2");
                        finalNotifMap.put("notfiDate", date);
                        finalNotifMap.put("notifKey", pushkey);

                        mDatabaseUserNotificationFinal.push().setValue(finalNotifMap).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                                        Toast.makeText(MatchListDetailedViewActivity.this, winner + " advances to next round", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });
                    }
                });
            } else if (matchName.equals("Match 5")) {
                mDatabaseMatch5.child("Match7").child("matchOpponentA").setValue(winner).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        HashMap finalNotifMap = new HashMap();
                        finalNotifMap.put("notifTitle", winner + " advances to next round " + leagueTitle + " " + divisionCategory);
                        finalNotifMap.put("notifMatch", "Winner Match 1 VS Winner Match 2");
                        finalNotifMap.put("notfiDate", date);
                        finalNotifMap.put("notifKey", pushkey);

                        mDatabaseUserNotificationFinal.push().setValue(finalNotifMap).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                                        Toast.makeText(MatchListDetailedViewActivity.this, winner + " advances to next round", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                });
            } else if (matchName.equals("Match 6")) {
                mDatabaseMatch5.child("Match7").child("matchOpponentB").setValue(winner).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        HashMap finalNotifMap = new HashMap();
                        finalNotifMap.put("notifTitle", winner + " advances to next round " + leagueTitle + " " + divisionCategory);
                        finalNotifMap.put("notifMatch", "Winner Match 1 VS Winner Match 2");
                        finalNotifMap.put("notfiDate", date);
                        finalNotifMap.put("notifKey", pushkey);

                        mDatabaseUserNotificationFinal.push().setValue(finalNotifMap).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                                        Toast.makeText(MatchListDetailedViewActivity.this, winner + " advances to next round", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                });
            } else if (matchName.equals("Match 7")) {
                mDatabaseRef.child("matchWinner").setValue(winner).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {


                        HashMap finalNotifMap = new HashMap();
                        finalNotifMap.put("notifTitle", winner + " is the Champion of " + leagueTitle + " " + divisionCategory);
                        finalNotifMap.put("notifMatch", "Winner Match 1 VS Winner Match 2");
                        finalNotifMap.put("notfiDate", date);
                        finalNotifMap.put("notifKey", pushkey);

                        mDatabaseUserNotificationFinal.push().setValue(finalNotifMap).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                                        Toast.makeText(MatchListDetailedViewActivity.this, winner + " is the Champion of " + leagueTitle + " " + divisionCategory, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        }
    }

    private void saveScore() {
        String scoreA = etDetailViewMatchOpponentScoreA.getText().toString().trim();
        String scoreB = etDetailViewMatchOpponentScoreB.getText().toString().trim();

        if (TextUtils.isEmpty(scoreA) || TextUtils.isEmpty(scoreB)) {
            Toast.makeText(this, "Please Update Score", Toast.LENGTH_SHORT).show();
        } else {

            int scoreAInt = Integer.parseInt(scoreA);
            int scoreBInt = Integer.parseInt(scoreB);

            if (scoreAInt > scoreBInt) {
                tvDetailViewMatchWinner.setText(tvDetailViewMatchTeamA.getText().toString());
            } else if (scoreAInt == scoreBInt){
                Toast.makeText(this, "Score must not same", Toast.LENGTH_SHORT).show();
            } else {
                tvDetailViewMatchWinner.setText(tvDetailViewMatchTeamB.getText().toString());
            }
        }
    }
}
