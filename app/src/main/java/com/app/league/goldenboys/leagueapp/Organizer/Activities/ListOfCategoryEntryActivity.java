package com.app.league.goldenboys.leagueapp.Organizer.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.app.league.goldenboys.leagueapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListOfCategoryEntryActivity extends AppCompatActivity {

    private RecyclerView mRecyclerview;
    private DatabaseReference mDatabaseRef;
    private LinearLayoutManager linearLayoutManager;

    private String leagueTitle;
    private String divisionCategory;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_category_entry);

        divisionCategory = getIntent().getStringExtra("divisionCategory");
        leagueTitle = getIntent().getStringExtra("leagueTitle");

        mToolbar = findViewById(R.id.toolbar_listOfCategoryEntry);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(leagueTitle + " " + divisionCategory + " Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();

        mRecyclerview = findViewById(R.id.recyclerView_listOfCategory);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("LeagueEntries").child(leagueTitle).child(divisionCategory);
        mRecyclerview.setLayoutManager(linearLayoutManager);



    }
}
