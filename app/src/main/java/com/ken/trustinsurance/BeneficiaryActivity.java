package com.ken.trustinsurance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BeneficiaryActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private static final int GALARY_PICK = 100;
    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String myuid;
    ActionBar actionBar;
    BottomNavigationView navigationView;

    Fragment fragment;

    Uri uriImage;

    String Type, downloadImageUrl;

    EditText massageType;
    ImageView image;
    Button save;
    ProgressDialog progressDialog;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_beneficiary);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Users");
        firebaseAuth = FirebaseAuth.getInstance();

        navigationView = findViewById(R.id.navigation);


        fragment = null;

        loadFragment(new UsersFragment());


        navigationView.setOnNavigationItemSelectedListener(this);


        navigationView.setSelectedItemId(R.id.users);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {
            case R.id.users:
                actionBar.setTitle("Uses");
                fragment = new UsersFragment();
                break;
            case R.id.subscribe:
                actionBar.setTitle("Subscribers");
                fragment = new SubscribersFragment();
                break;
            case R.id.payment:
                actionBar.setTitle("Payment");
                fragment = new PaymentFragment();
                break;
            case R.id.chat:
                actionBar.setTitle("Chats");
                fragment = new ChatFragment();

                break;
        }
        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}