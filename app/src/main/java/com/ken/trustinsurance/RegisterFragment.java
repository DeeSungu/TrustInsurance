package com.ken.trustinsurance;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ken.trustinsurance.Utilities.CustomLoading;


import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private FirebaseAuth mAuth;
   EditText name,username,email,phone;
   String Name,Password,UserName,Email,Phone;
   TextInputLayout password;
   TextView verify;
    CustomLoading customLoading;
   AppCompatButton register;
   View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
//Hide:
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
//Show
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
        // Inflate the layout for this fragment

        view= inflater.inflate(R.layout.fragment_register, container, false);
        name = view.findViewById(R.id.name);
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.et_password);
        register = view.findViewById(R.id.btn_register);
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone);
        verify = view.findViewById(R.id.verify);



        customLoading= new CustomLoading(getContext());
        mAuth = FirebaseAuth.getInstance();
        String i = email.getText().toString().trim();

        if (i.isEmpty()){
            verify.setVisibility(View.VISIBLE);

        }
        else
        {
            verify.setVisibility(View.GONE);
        }
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name= name.getText().toString();
                Password= password.getEditText().getText().toString();
                UserName= username.getText().toString();
                Email= email.getText().toString();
                Phone= phone.getText().toString();

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
                if (Password.isEmpty()){
                    Toast.makeText(getContext(), "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Password.length() < 4){

                    Toast.makeText(getContext(), "Password must be at least 4 character long!", Toast.LENGTH_SHORT).show();
                    return;
                }

                else {
                    customLoading.startLoading(getLayoutInflater());
                    mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                HashMap<String,Object> hashMap=new HashMap<>(  );
                                hashMap.put( "uid",""+ FirebaseAuth.getInstance().getCurrentUser().getUid());
                                hashMap.put("username",UserName);
                                hashMap.put("email",""+Email);
                                hashMap.put("name",""+Name);
                                hashMap.put("imageUrl","");
                                hashMap.put("phone",""+Phone);
                                hashMap.put("account","user");
                                hashMap.put("password",""+Password);
                                DatabaseReference reference=FirebaseDatabase.getInstance().getReference("pheels62");
                                reference.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()){
                                            FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    customLoading.dismissLoading();
                                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                                                    builder1.setMessage("Email verification link has been sent to the email you provided above!");
                                                    builder1.setCancelable(true);

                                                    builder1.setPositiveButton(
                                                            "Ok",
                                                            new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int id) {
                                                                    startActivity(new Intent(getContext(),loginActivity.class));
                                                                    dialog.cancel();
                                                                }
                                                            });

                                                    AlertDialog alert11 = builder1.create();
                                                    alert11.show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    customLoading.dismissLoading();
                                                    Toast.makeText(getContext(), "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        customLoading.dismissLoading();
                                        Toast.makeText(getContext(), "Failed to create account"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            customLoading.dismissLoading();
                            Toast.makeText(getContext(), "Failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

        return view;
    }

    private void saveData() {

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("loading.......");
        progressDialog.show();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        final String time= String.valueOf(System.currentTimeMillis());
        HashMap<String,String>hashMap= new HashMap<>();
        hashMap.put("username",UserName);
        hashMap.put("email",""+Email);
        hashMap.put("name",""+Name);
        hashMap.put("phone",""+Phone);
        hashMap.put("account","user");
        hashMap.put("password",""+Password);

        reference.child("pheels62").child("users").child(UserName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Toast.makeText(getContext(), "Failed to create account.....an account with similar username already exists", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    return;
                }
                if (!snapshot.exists()){
                    reference.child("pheels62").child("users").child(UserName).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Account created....Swipe right to Login ", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }

}
