package com.app.league.goldenboys.leagueapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.app.league.goldenboys.leagueapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.awt.font.TextAttribute;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    private TextView tvProfileFirstName;
    private TextView tvProfileLastName;
    private TextView tvProfileAge;
    private TextView tvProfileEmail;
    private CircleImageView cimvProfileImage;

    private Button btnCustomerJoinLeague;

    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;
    private FirebaseAuth mAuth;
    private String current_uid;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvProfileLastName = view.findViewById(R.id.tvProfileLastName);
        tvProfileFirstName = view.findViewById(R.id.tvProfileFirstName);
        tvProfileAge = view.findViewById(R.id.tvProfileAge);
        tvProfileEmail = view.findViewById(R.id.tvProfileEmail);

        mAuth = FirebaseAuth.getInstance();

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        current_uid = mCurrentUser.getUid();

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    tvProfileLastName.setText("Last Name : " + dataSnapshot.child("LastName").getValue().toString());
                    tvProfileFirstName.setText("First Name : " + dataSnapshot.child("FirstName").getValue().toString());
                    tvProfileAge.setText("Age : " + dataSnapshot.child("age").getValue().toString());
                    tvProfileEmail.setText("Email : " + dataSnapshot.child("Email").getValue().toString());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {

    }

    private void joinLeague() {

        HashMap joinLeagueMap = new HashMap();

        joinLeagueMap.put("firstname", tvProfileFirstName.getText().toString());
        joinLeagueMap.put("lastname", tvProfileLastName.getText().toString());
        joinLeagueMap.put("uid", current_uid);
        joinLeagueMap.put("age", tvProfileAge.getText().toString());
        joinLeagueMap.put("screeningResult", "default");

    }
}
