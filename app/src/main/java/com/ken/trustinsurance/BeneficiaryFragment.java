package com.ken.trustinsurance;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jeevandeshmukh.glidetoastlib.GlideToast;
import com.ken.trustinsurance.adapters.UserAdapter;
import com.ken.trustinsurance.models.UsersModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class BeneficiaryFragment extends Fragment {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<UsersModel> userList;

    FloatingActionButton floatingActionButton;
    EditText searchText;
    TextView no_content_tv;
    View view;
    Button btn_register;
    String Plan;
    RadioGroup radioGroup1,radioGroup;
    RadioButton radioButton,bronze,silver,gold,plantinum;
    ProgressDialog progressDialog;

    EditText name,username,email,phone;
    String Name,Password,UserName,Email,Phone;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_beneficiaries, container, false);

        floatingActionButton = view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(false);
                ViewGroup viewGroup = view.findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.beneficiary_layout, viewGroup, false);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setCancelable(false);


                name = dialogView.findViewById(R.id.name);
                username = dialogView.findViewById(R.id.username);
                email = dialogView.findViewById(R.id.email);
                phone = dialogView.findViewById(R.id.phone);

                bronze= dialogView.findViewById(R.id.bronze);
                silver= dialogView.findViewById(R.id.silver);

                plantinum= dialogView.findViewById(R.id.platinum);
                gold= dialogView.findViewById(R.id.gold);

                TextView backBtn= dialogView.findViewById(R.id.backBtn);
                 btn_register= dialogView.findViewById(R.id.btn_register);
                radioGroup1 = dialogView.findViewById(R.id.radioGroup2);
                backBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

//                radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                        if (bronze.isChecked()){
//                            int selectId = radioGroup1.getCheckedRadioButtonId();
//                            radioButton = dialogView.findViewById(selectId);
//                            progressDialog.show();
//                            Plan="Bronze";
////                            checkData();
//
//                        }
//                        if (silver.isChecked()){
//                            progressDialog.show();
//                            Plan="Silver";
//                            //                            checkData();
//
//
//                        }
//                        if (gold.isChecked()){
//                            progressDialog.show();
//                            Plan="Platinum";
//                            //                            checkData();
//
//
//                        }
//                        if (plantinum.isChecked()){
//                            progressDialog.show();
//                            Plan="Gold";
//                            //                            checkData();
//
//                        }
//
//                        else {
//
//                            btn_register.setEnabled(false);
//                        }
//                    }
//                });



                btn_register.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Name= name.getText().toString();
                        UserName= username.getText().toString();
                        Email= email.getText().toString();
                        Phone= phone.getText().toString();
                        int selectId = radioGroup1.getCheckedRadioButtonId();
                            radioButton = dialogView.findViewById(selectId);

                        if (Name.isEmpty()){
                            Toast.makeText(getContext(), "Please enter your full name", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (UserName.isEmpty()){
                            Toast.makeText(getContext(), "Please enter username", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (Email.isEmpty()){
                            Toast.makeText(getContext(), "Please enter email", Toast.LENGTH_SHORT).show();

                        }
                        if (Phone.isEmpty()){
                            Toast.makeText(getContext(), "Please enter phone", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (radioButton==null) {
                            new GlideToast.makeToast(getActivity(),"Choose Subscription Plan!",GlideToast.LENGTHLONG,GlideToast.FAILTOAST,GlideToast.CENTER).show();

                            return;
                        }

                        progressDialog.setMessage("Adding "+Name.toUpperCase(Locale.ROOT)+".........");
                        btn_register.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_custom));
                        HashMap<String,Object> hashMap=new HashMap<>(  );
                        hashMap.put( "uid",""+ FirebaseAuth.getInstance().getCurrentUser().getUid());
                        hashMap.put("username",UserName);
                        hashMap.put("email",""+Email);
                        hashMap.put("name",""+Name);
                        hashMap.put("imageUrl","");
                        hashMap.put("phone",""+Phone);
                        hashMap.put("plan",radioButton.getText().toString());

                        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("pheels62");
                        reference.child("beneficiaries").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "Added", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            }
                        });
                    }
                });


            }
        });

        recyclerView = view.findViewById(R.id.recycler_view_for_users);
        searchText = view.findViewById(R.id.search_text);
        no_content_tv = view.findViewById(R.id.no_content_tv);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userList = new ArrayList<>();

        getUser();

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                searchUsers(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    private void checkData() {
        progressDialog.setMessage("Checking Subscription Plan......");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("pheels62");
        reference.child("subscribers").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).orderByChild("plan").equalTo(radioButton.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    progressDialog.dismiss();
                    btn_register.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_custom));
                    return;

                }
                 if (!snapshot.exists()){
                    progressDialog.dismiss();
                    new GlideToast.makeToast(getActivity(),"You are not subscribed to this plan!",GlideToast.LENGTHLONG,GlideToast.FAILTOAST,GlideToast.CENTER).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error"+ error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchUsers(String characters) {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Query query = FirebaseDatabase.getInstance().getReference("pheels62").child("beneficiaries").child(FirebaseAuth.getInstance().getUid()).orderByChild("name")
                .startAt(characters)
                .endAt(characters + "\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    UsersModel user = snapshot.getValue(UsersModel.class);

                    userList.add(user);

                }
                if (userList.isEmpty()){
                    String str = "Data not found";
                    no_content_tv.setText(str);
                    no_content_tv.setVisibility(View.VISIBLE);
                } else {
                    no_content_tv.setVisibility(View.GONE);
                }


                userAdapter = new UserAdapter(getContext(), userList, false);
                recyclerView.setAdapter(userAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getUser(){
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("pheels62").child("beneficiaries");

        reference.child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (searchText.getText() != null && searchText.getText().toString().equals("")) {
                    userList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        UsersModel user = snapshot.getValue(UsersModel.class);
                        userList.add(user);
                    }
                    if (userList.isEmpty()){
                        String str = "No Data found";
                        no_content_tv.setText(str);
                        no_content_tv.setVisibility(View.VISIBLE);
                    } else {
                        no_content_tv.setVisibility(View.GONE);
                    }
                    userAdapter = new UserAdapter(getContext(), userList, false);
                    recyclerView.setAdapter(userAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
