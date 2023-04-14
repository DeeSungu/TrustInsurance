package com.ken.trustinsurance;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.ken.trustinsurance.adapters.User1Adapter;
import com.ken.trustinsurance.adapters.UserAdapter;
import com.ken.trustinsurance.models.UsersModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class UsersFragment extends Fragment {
    private RecyclerView recyclerView;
    private User1Adapter userAdapter;
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
        view= inflater.inflate(R.layout.fragment_users, container, false);



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


    private void searchUsers(String characters) {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Query query = FirebaseDatabase.getInstance().getReference("pheels62").child("users").orderByChild("name")
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


                userAdapter = new User1Adapter(getContext(), userList, false);
                recyclerView.setAdapter(userAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getUser(){
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("pheels62").child("users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (searchText.getText() != null && searchText.getText().toString().equals("")) {
                    userList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        UsersModel user = snapshot.getValue(UsersModel.class);
                        userList.add(user);
                    }
                    if (userList.isEmpty()){
                        String str = "No users found";
                        no_content_tv.setText(str);
                        no_content_tv.setVisibility(View.VISIBLE);
                    } else {
                        no_content_tv.setVisibility(View.GONE);
                    }
                    userAdapter = new User1Adapter(getContext(), userList, false);
                    recyclerView.setAdapter(userAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}