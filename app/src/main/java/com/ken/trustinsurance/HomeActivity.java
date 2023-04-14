package com.ken.trustinsurance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ken.trustinsurance.models.UsersModel;

public class HomeActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FirebaseAuth Mauth;
    private FrameLayout frameLayout;
    ActionBar actionBar;
    String sub;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        frameLayout = findViewById(R.id.FrameLayoutID);
        dialog= new Dialog(HomeActivity.this);
        Mauth = FirebaseAuth.getInstance();
        drawerLayout = findViewById(R.id.DrawerLayoutID);
        navigationView = findViewById(R.id.NavagationID);
        navigationView.getMenu().getItem(0).setCheckable(true);
        navigationView.getMenu().getItem(0).setChecked(true);


        toolbar = findViewById(R.id.HomeToolbarID);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Our Plans");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu_icon);
        View mnavigationview = navigationView.getHeaderView(0);
        TextView back = mnavigationview.findViewById(R.id.back);

        setHomeFragement(new HomeFragment());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.logout) {


                    AlertDialog.Builder checkAlert = new AlertDialog.Builder(HomeActivity.this);
                    checkAlert.setMessage("Do you want to Logout?")
                            .setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Mauth.signOut();

                            Intent intent = new Intent(getApplicationContext(), loginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            drawerLayout.closeDrawer(Gravity.LEFT);
                            finish();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            menuItem.setChecked(true);
                            menuItem.setCheckable(true);
                            dialog.cancel();
                        }
                    });

                    AlertDialog alert = checkAlert.create();
                    alert.show();


                }
                if (menuItem.getItemId()==R.id.account){
                    menuItem.setChecked(true);
                    menuItem.setCheckable(true);
                    Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    drawerLayout.closeDrawer(Gravity.LEFT);

                }

                if (menuItem.getItemId()==R.id.beneficiary){
                    menuItem.setChecked(true);
                    menuItem.setCheckable(true);
                   setHomeFragement(new BeneficiaryFragment());
                    actionBar.setTitle("Beneficiaries");
                    drawerLayout.closeDrawer(Gravity.LEFT);

                }

                if (menuItem.getItemId()==R.id.payment){
                    menuItem.setChecked(true);
                    menuItem.setCheckable(true);
                    Intent intent = new Intent(getApplicationContext(), ActivePlanActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    drawerLayout.closeDrawer(Gravity.LEFT);

                }

                if (menuItem.getItemId()==R.id.about){
                    menuItem.setChecked(true);
                    menuItem.setCheckable(true);
                    Intent intent = new Intent(getApplicationContext(), AboutUsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    drawerLayout.closeDrawer(Gravity.LEFT);

                }

                if (menuItem.getItemId()==R.id.support){
                    menuItem.setChecked(true);
                    menuItem.setCheckable(true);
                    ShowDialog();
                    drawerLayout.closeDrawer(Gravity.LEFT);

                }

                if (menuItem.getItemId() == R.id.plans) {
                    menuItem.setChecked(true);
                    menuItem.setCheckable(true);
                    setHomeFragement(new HomeFragment());
                    drawerLayout.closeDrawer(Gravity.LEFT);
                }

                return true;
            }
        });
    }

    private void ShowDialog() {
        dialog.setContentView(R.layout.email_layout);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        dialog.show();

        EditText name = dialog.findViewById(R.id.name);
        TextView backBtn = dialog.findViewById(R.id.backBtn);
        TextView text_to = dialog.findViewById(R.id.text_to);
        EditText edit_text_message= dialog.findViewById(R.id.edit_text_message);
        Button send= dialog.findViewById(R.id.send);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("pheels62").child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    UsersModel usersModel = snapshot.getValue(UsersModel.class);
                     sub= usersModel.getName();
//                    name.setText(usersModel.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String Message= edit_text_message.toString().trim();

                if (Message.isEmpty()){
                    edit_text_message.setError("Message Required");
                    edit_text_message.requestFocus();

                }

                else {
                    String recipientList = text_to.getText().toString();
                    String[] recipients = recipientList.split(",");
                    String subject = sub;
                    String message = edit_text_message.getText().toString();
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                    intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                    intent.putExtra(Intent.EXTRA_TEXT, message);
                    intent.setType("message/rfc822");
                    startActivity(Intent.createChooser(intent, "Choose an email client"));
                }

            }
        });
    }


    private void setHomeFragement(Fragment fragement) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(), fragement);
        fragmentTransaction.commit();

    }
    @Override
    protected void onStart() {
        FirebaseUser Muser = Mauth.getCurrentUser();
        if (Muser == null) {
            Intent intent = new Intent(getApplicationContext(), loginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(Gravity.LEFT);
        }
        if (item.getItemId() == R.id.adminPanel) {

            startActivity(new Intent(HomeActivity.this, BeneficiaryActivity.class));

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}