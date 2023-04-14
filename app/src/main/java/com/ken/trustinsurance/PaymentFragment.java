package com.ken.trustinsurance;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ken.trustinsurance.adapters.Transaction1Adapter;
import com.ken.trustinsurance.adapters.TransactionAdapter;
import com.ken.trustinsurance.models.TransactionModel;

import java.util.ArrayList;
import java.util.List;


public class PaymentFragment extends Fragment {


    private RecyclerView recyclerView;
    private Transaction1Adapter massageAdapter;
    private List<TransactionModel> userList;
    FloatingActionButton floatingActionButton;
    String category;
    EditText filter;
    TextView txtStatus,add;
    ProgressBar progress_circular;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_payment, container, false);

        recyclerView =view.findViewById( R.id.recyclerView );
        txtStatus = view.findViewById(R.id.txtStatus);
        filter = view.findViewById(R.id.filter);
        progress_circular= view.findViewById(R.id.progress_circular);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        userList = new ArrayList<>();


        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                String seach= s.toString();
                if (seach.equals("")){
                    loadChecklist();
                }else {
                    searchUsers(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        loadChecklist();

        return view;
    }

    private void searchUsers(String category) {

        Query query = FirebaseDatabase.getInstance().getReference("pheels62").child("subscribers").orderByChild("name")
                .startAt(category)
                .endAt(category + "\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    TransactionModel user = snapshot.getValue(TransactionModel.class);

                    userList.add(user);

                }
                if (userList.isEmpty()){
                    progress_circular.setVisibility(View.GONE);
                    txtStatus.setVisibility(View.VISIBLE);
                } else {
                    progress_circular.setVisibility(View.GONE);
                    txtStatus.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }

                massageAdapter = new Transaction1Adapter(getActivity(), userList, false);
                recyclerView.setAdapter(massageAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadChecklist() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("pheels62").child("subscribers");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                userList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    TransactionModel user = snapshot.getValue(TransactionModel.class);

                    userList.add(user);

                }
                if (userList.isEmpty()){
                    progress_circular.setVisibility(View.GONE);
                    txtStatus.setVisibility(View.VISIBLE);
                }
                else {
                    progress_circular.setVisibility(View.GONE);
                    txtStatus.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
                massageAdapter = new Transaction1Adapter(getActivity(), userList, false);
                recyclerView.setAdapter(massageAdapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}