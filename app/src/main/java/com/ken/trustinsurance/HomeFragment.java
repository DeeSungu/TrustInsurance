package com.ken.trustinsurance;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.flutterwave.raveandroid.RaveConstants;
import com.flutterwave.raveandroid.RavePayActivity;
import com.flutterwave.raveandroid.RavePayManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jeevandeshmukh.glidetoastlib.GlideToast;
import com.ken.trustinsurance.models.UsersModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;


public class HomeFragment extends Fragment {
    ProgressDialog progressDialog;
CardView bronze,silver,platinum,gold;
View view;
Dialog dialog;
String amount,plan;
    RadioGroup radioGroup1,radioGroup;
    RadioButton radioButton,radioButton1,months,year;
    LinearLayout layout,layout1;
    private String saveCurrentDate;
    private String saveCurrentTime,name,email,phone;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_home, container, false);
        bronze=view.findViewById(R.id.bronze);
        silver=view.findViewById(R.id.silver);
        platinum=view.findViewById(R.id.platinum);
        gold=view.findViewById(R.id.gold);
        dialog= new Dialog(getContext());

        getUserInf();

        bronze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(false);
                ViewGroup viewGroup = view.findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.bronze_layout, viewGroup, false);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                TextView cancel= dialogView.findViewById(R.id.cancel);
                 layout= dialogView.findViewById(R.id.monthly);
                layout1= dialogView.findViewById(R.id.yearly);
                TextView cancel2= dialogView.findViewById(R.id.cancel1);
                 months= dialogView.findViewById(R.id.months);
                 year= dialogView.findViewById(R.id.year);
                TextView subscribe= dialogView.findViewById(R.id.subscribe);
                TextView subscribe1= dialogView.findViewById(R.id.subscribe1);


                radioGroup1 = dialogView.findViewById(R.id.radioGroup2);

                radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        if (year.isChecked()){
                            layout.setVisibility(View.GONE);
                            layout1.setVisibility(View.VISIBLE);

                        }
                        else {

                            layout1.setVisibility(View.GONE);
                            layout.setVisibility(View.VISIBLE);
                        }
                    }
                });
                
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                cancel2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                subscribe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int selectId = radioGroup1.getCheckedRadioButtonId();
                        radioButton = dialogView.findViewById(selectId);
                        if (radioButton==null) {
                            new GlideToast.makeToast(getActivity(),"Choose Subscription period!",GlideToast.LENGTHLONG,GlideToast.FAILTOAST,GlideToast.CENTER).show();

                            return;
                        }
                        int cost=1;
                        amount= String.valueOf(cost);
                        plan="Bronze";
                        makePayment();
                        alertDialog.dismiss();
                    }
                });
                subscribe1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int selectId = radioGroup1.getCheckedRadioButtonId();
                        radioButton = dialogView.findViewById(selectId);
                        if (radioButton==null) {
                            new GlideToast.makeToast(getActivity(),"Choose Subscription period!",GlideToast.LENGTHLONG,GlideToast.FAILTOAST,GlideToast.CENTER).show();

                            return;
                        }
                        int cost=1*12;
                        amount= String.valueOf(cost);
                        plan="Bronze";
                        makePayment();
                        alertDialog.dismiss();

                    }
                });

            }
        });
        silver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(false);
                ViewGroup viewGroup = view.findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.silver_layout, viewGroup, false);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                TextView cancel= dialogView.findViewById(R.id.cancel);
                layout= dialogView.findViewById(R.id.monthly);
                layout1= dialogView.findViewById(R.id.yearly);
                TextView cancel2= dialogView.findViewById(R.id.cancel1);
                months= dialogView.findViewById(R.id.months);
                year= dialogView.findViewById(R.id.year);
                TextView subscribe= dialogView.findViewById(R.id.subscribe);
                TextView subscribe1= dialogView.findViewById(R.id.subscribe1);


                radioGroup1 = dialogView.findViewById(R.id.radioGroup2);

                radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        if (year.isChecked()){
                            layout.setVisibility(View.GONE);
                            layout1.setVisibility(View.VISIBLE);

                        }
                        else {

                            layout1.setVisibility(View.GONE);
                            layout.setVisibility(View.VISIBLE);
                        }
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                cancel2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                subscribe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int selectId = radioGroup1.getCheckedRadioButtonId();
                        radioButton = dialogView.findViewById(selectId);
                        if (radioButton==null) {
                            new GlideToast.makeToast(getActivity(),"Choose Subscription period!",GlideToast.LENGTHLONG,GlideToast.FAILTOAST,GlideToast.CENTER).show();

                            return;
                        }
                        int cost=8000;
                        amount= String.valueOf(cost);
                        plan="Silver";
                        makePayment();
                        alertDialog.dismiss();
                    }
                });
                subscribe1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int selectId = radioGroup1.getCheckedRadioButtonId();
                        radioButton = dialogView.findViewById(selectId);
                        if (radioButton==null) {
                            new GlideToast.makeToast(getActivity(),"Choose Subscription period!",GlideToast.LENGTHLONG,GlideToast.FAILTOAST,GlideToast.CENTER).show();

                            return;
                        }
                        int cost=8000*12;
                        amount= String.valueOf(cost);
                        plan="Silver";
                        makePayment();
                        alertDialog.dismiss();

                    }
                });

            }
        });
        platinum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(false);
                ViewGroup viewGroup = view.findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.platinum_layout, viewGroup, false);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                TextView cancel= dialogView.findViewById(R.id.cancel);
                layout= dialogView.findViewById(R.id.monthly);
                layout1= dialogView.findViewById(R.id.yearly);
                TextView cancel2= dialogView.findViewById(R.id.cancel1);
                months= dialogView.findViewById(R.id.months);
                year= dialogView.findViewById(R.id.year);
                TextView subscribe= dialogView.findViewById(R.id.subscribe);
                TextView subscribe1= dialogView.findViewById(R.id.subscribe1);


                radioGroup1 = dialogView.findViewById(R.id.radioGroup2);

                radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        if (year.isChecked()){
                            layout.setVisibility(View.GONE);
                            layout1.setVisibility(View.VISIBLE);

                        }
                        else {

                            layout1.setVisibility(View.GONE);
                            layout.setVisibility(View.VISIBLE);
                        }
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                cancel2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                subscribe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int selectId = radioGroup1.getCheckedRadioButtonId();
                        radioButton = dialogView.findViewById(selectId);
                        if (radioButton==null) {
                            new GlideToast.makeToast(getActivity(),"Choose Subscription period!",GlideToast.LENGTHLONG,GlideToast.FAILTOAST,GlideToast.CENTER).show();

                            return;
                        }
                        int cost=15000;
                        amount= String.valueOf(cost);
                        plan="Platinum";
                        makePayment();
                        alertDialog.dismiss();
                    }
                });
                subscribe1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int selectId = radioGroup1.getCheckedRadioButtonId();
                        radioButton = dialogView.findViewById(selectId);
                        if (radioButton==null) {
                            new GlideToast.makeToast(getActivity(),"Choose Subscription period!",GlideToast.LENGTHLONG,GlideToast.FAILTOAST,GlideToast.CENTER).show();

                            return;
                        }
                        int cost=15000*12;
                        amount= String.valueOf(cost);
                        plan="Platinum";
                        makePayment();
                        alertDialog.dismiss();

                    }
                });


            }
        });
        gold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(false);
                ViewGroup viewGroup = view.findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.gold_layout, viewGroup, false);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                TextView cancel= dialogView.findViewById(R.id.cancel);
                layout= dialogView.findViewById(R.id.monthly);
                layout1= dialogView.findViewById(R.id.yearly);
                TextView cancel2= dialogView.findViewById(R.id.cancel1);
                months= dialogView.findViewById(R.id.months);
                year= dialogView.findViewById(R.id.year);
                TextView subscribe= dialogView.findViewById(R.id.subscribe);
                TextView subscribe1= dialogView.findViewById(R.id.subscribe1);


                radioGroup1 = dialogView.findViewById(R.id.radioGroup2);

                radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        if (year.isChecked()){
                            layout.setVisibility(View.GONE);
                            layout1.setVisibility(View.VISIBLE);

                        }
                        else {

                            layout1.setVisibility(View.GONE);
                            layout.setVisibility(View.VISIBLE);
                        }
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                cancel2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                subscribe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int selectId = radioGroup1.getCheckedRadioButtonId();
                        radioButton = dialogView.findViewById(selectId);
                        if (radioButton==null) {
                            new GlideToast.makeToast(getActivity(),"Choose Subscription period!",GlideToast.LENGTHLONG,GlideToast.FAILTOAST,GlideToast.CENTER).show();

                            return;
                        }
                        int cost=12000;
                        amount= String.valueOf(cost);
                        plan="Gold";
                        makePayment();
                        alertDialog.dismiss();
                    }
                });
                subscribe1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int selectId = radioGroup1.getCheckedRadioButtonId();
                        radioButton = dialogView.findViewById(selectId);
                        if (radioButton==null) {
                            new GlideToast.makeToast(getActivity(),"Choose Subscription period!",GlideToast.LENGTHLONG,GlideToast.FAILTOAST,GlideToast.CENTER).show();

                            return;
                        }
                        int cost=12000*12;
                        amount= String.valueOf(cost);
                        plan="Gold";
                        makePayment();
                        alertDialog.dismiss();

                    }
                });
            }
        });
        return view;
    }

    private void getUserInf() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("pheels62").child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    UsersModel usersModel= snapshot.getValue(UsersModel.class);
                   email= usersModel.getEmail();
                   name= usersModel.getName();
                   phone= usersModel.getPhone();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void makePayment() {
        new RavePayManager(this)
                .setAmount(Double.parseDouble(amount))
                .setEmail(email)
                .setCountry("KE")
                .setPhoneNumber("")
                .setCurrency("KES")
                .setfName(name)
                .setNarration("PAY FOR SERVICE")
                .setPublicKey("FLWPUBK-2724ed3ad8ac94334ff12424b8ba03c4-X")
                .setEncryptionKey("a27dcd782d32dc177a924139")
                .setTxRef(System.currentTimeMillis() + "Ref")
                .acceptAccountPayments(true)
                .acceptCardPayments(true)
                .acceptMpesaPayments(true)
                .onStagingEnv(false)
                .shouldDisplayFee(true)
                .showStagingLabel(true)
                .initialize();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RaveConstants.RAVE_REQUEST_CODE && data != null) {
            String message = data.getStringExtra("response");
            if (resultCode == RavePayActivity.RESULT_SUCCESS) {
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please wait......");
                progressDialog.show();

                Calendar calendar = Calendar.getInstance();

                SimpleDateFormat currentDate = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    currentDate = new SimpleDateFormat("MMM dd, YYYY");
                }
                saveCurrentDate = currentDate.format((calendar.getTime()));

                SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                saveCurrentTime = currentTime.format((calendar.getTime()));
                
                final String time = String.valueOf(System.currentTimeMillis());
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("uid", "" + FirebaseAuth.getInstance().getCurrentUser().getUid());
                hashMap.put("name", "" + name);
                hashMap.put("plan", "" + plan);
                hashMap.put("time", "" + saveCurrentTime);
                hashMap.put("cost", "" + amount);
                hashMap.put("subscription", "" + radioButton.getText());
                hashMap.put("id", "" + time);
                hashMap.put("date", "" + saveCurrentDate);
                hashMap.put("phone", "" + phone);

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("pheels62");
                reference.child("subscribers").child(FirebaseAuth.getInstance().getUid()).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Your Subscription was successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else if (resultCode == RavePayActivity.RESULT_ERROR) {

                Toast.makeText(getActivity(), "ERROR " + message, Toast.LENGTH_LONG).show();
            }
            else if (resultCode == RavePayActivity.RESULT_CANCELLED) {

                Toast.makeText(getContext(), "CANCELLED " + message, Toast.LENGTH_LONG).show();
            }
        }
    }
}