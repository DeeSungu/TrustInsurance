package com.ken.trustinsurance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ken.trustinsurance.models.UsersModel;

import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    private TextView TVusername,TVemail,TVmobile,back;
    Button logout;
    private final String TAG = this.getClass().getName().toUpperCase();
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private Map<String, String> userMap;
    private String email;
    ProgressBar progressBar;
    private String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        getSupportActionBar().setTitle("Profile Account");


        TVemail=findViewById(R.id.TVemail);
        TVusername=findViewById(R.id.TVusername);
        back=findViewById(R.id.back);
        TVmobile=findViewById(R.id.TVmobile);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = rootRef.child("pheels62").child("users");
        /*Log.v("USERID", userRef.getKey());*/
        userRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            String mobile,name;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    UsersModel userModel= dataSnapshot.getValue(UsersModel.class);
                    TVusername.setText(userModel.getName());
                    TVemail.setText(userModel.getEmail());
                    TVmobile.setText(userModel.getPhone());
                }

                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                progressBar.setVisibility(View.INVISIBLE);
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }
}