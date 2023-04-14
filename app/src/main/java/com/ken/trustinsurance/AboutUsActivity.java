package com.ken.trustinsurance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ken.trustinsurance.databinding.ActivityAboutUsBinding;
import com.ken.trustinsurance.models.UsersModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AboutUsActivity extends AppCompatActivity {
    private ActivityAboutUsBinding binding;
    String sub;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        getSupportActionBar().setTitle("     About Us");

        dialog= new Dialog(AboutUsActivity.this);
        binding = ActivityAboutUsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);

        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String date = formatter.format(today);
        binding.day.setText(dayOfTheWeek+"-"+date);

        CountDownTimer newtimer = new CountDownTimer(1000000000, 1000) {

            public void onTick(long millisUntilFinished) {
                Calendar c = Calendar.getInstance();
                binding.time.setText(c.get(Calendar.HOUR)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND));
                String Time= binding.time.getText().toString().trim();

                if (Time.equals("17:00:0")){
                    binding.open.setText("Closed Now");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        binding.open.setTextColor(getColor(R.color.red));
                    }
                }else if (Time.equals("09:0:0")){
                    binding.open.setText("Open Now");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        binding.open.setTextColor(getColor(R.color.Python));
                    }

                }

            }
            public void onFinish() {

            }
        };
        newtimer.start();



        binding.instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Instagram.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        binding.facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), Facebook.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialog();
            }
        });

        binding.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0720709156"));
                startActivity(intent);
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
}