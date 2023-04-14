package com.ken.trustinsurance;


import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ken.trustinsurance.Utilities.CustomLoading;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    EditText username, password;

    View view;
    AppCompatButton btn_login;
    CustomLoading customLoading;
    String admin = "admin";
    String adPas = "1234";
    String Pass, User, Email, user_name, AdminPass;
    FirebaseAuth fauth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
//Hide:
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
//Show
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);


        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        btn_login = view.findViewById(R.id.btn_login);

        username = view.findViewById(R.id.et_email);
        password = view.findViewById(R.id.password);

        customLoading = new CustomLoading(getContext());
        fauth = FirebaseAuth.getInstance();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pass = password.getText().toString().trim();
                Email = username.getText().toString().trim();
                if (Email.equals("")) {
                    username.setError("fill this field");
                    username.setEnabled(true);
                    username.requestFocus();
                    return;
                }

                if (Pass.equals("")) {
                    password.setError("fill this field");
                    password.setEnabled(true);
                    password.requestFocus();


                } else {
                    customLoading.startLoading(getLayoutInflater());
                    fauth.signInWithEmailAndPassword(Email, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                if (FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
                                    customLoading.dismissLoading();

                                    goToHomePage();
//


                                } else {

                                    FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> email) {
                                            if (email.isSuccessful()) {
                                                customLoading.dismissLoading();
                                                Toast.makeText(getContext(), "Please verify email", Toast.LENGTH_SHORT).show();
                                            } else {
                                                customLoading.dismissLoading();
                                                Toast.makeText(getContext(), "Error : " + email.getException(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            } else {
                                Toast.makeText(getContext(), "Wrong email Or Password", Toast.LENGTH_SHORT).show();
                                customLoading.dismissLoading();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            customLoading.dismissLoading();
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        return view;
    }

    private void goToHomePage() {
        Intent i = new Intent(getActivity(), MainActivity.class);
        startActivity(i);
        ((Activity) getActivity()).overridePendingTransition(0, 0);
    }

    public static void hideKeyboardFrom() {

    }

    private void Login() {

        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Logging in Wait.........");
        dialog.show();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        reference.child("pheel62").child("users").orderByChild("username").equalTo(User).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {

                        String User = "" + ds.child("username").getValue();
                        String pass = "" + ds.child("password").getValue();
                        String admin = "" + ds.child("admin").getValue();


                        if (!User.equals(User)) {
                            dialog.dismiss();
                            Toast.makeText(getContext(), "wrong user name! Try again...!", Toast.LENGTH_SHORT).show();
                        } else {
                            if (pass.equals(Pass)) {
                                Intent intent = new Intent(getContext(), MainActivity.class);
                                intent.putExtra("uid", User);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                dialog.dismiss();
                            } else if (admin.equals(Pass)) {

                                Intent intent = new Intent(getContext(), HomeActivity.class);
                                intent.putExtra("uid", User);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                dialog.dismiss();
                            } else {
                                dialog.dismiss();
                                Toast.makeText(getContext(), "wrong password! Try again...!", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                } else {
                    Toast.makeText(getContext(), "User does not exists", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onResume() {

        SharedPreferences sh = getContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);

        String s1 = sh.getString("phone", "");

        String s2 = sh.getString("password", "");


        // Setting the fetched data
        // in the EditTexts
        username.setText(s1);
        password.setText(s2);
        super.onResume();
    }

    @Override
    public void onPause() {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        // write all the data entered by the user in SharedPreference and apply
        myEdit.putString("phone", username.getText().toString());
        myEdit.putString("password", password.getText().toString());
        myEdit.apply();
        super.onPause();
    }
}
