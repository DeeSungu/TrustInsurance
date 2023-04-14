package com.ken.trustinsurance.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import com.ken.trustinsurance.R;
import com.ken.trustinsurance.models.TransactionModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    private Context context;
    private List<TransactionModel> userList;
    private boolean isInChat;
    private String lastMessage;
    private AlertDialog alertDialog;

    public TransactionAdapter(Context context, List<TransactionModel> userList, Boolean isInChat){
        this.context = context;
        this.userList = userList;
        this.isInChat = isInChat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.stores_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final TransactionModel user = userList.get(i);


        viewHolder.name.setText(user.getPlan());


        if (user.getSubscription().equals("Monthly")){
            viewHolder.balance.setText("1 Month");
        }else if (user.getSubscription().equals("12 Months")){
            viewHolder.balance.setText("1 Year");
        }





        NumberFormat formatter = new DecimalFormat("#,###");
        double myNumber = Double.parseDouble(user.getCost());
        String formattedNumber = formatter.format(myNumber);
        viewHolder.amount.setText("Ksh. "+formattedNumber);



//        try {
//            Glide.with(context).load(user.getImageIcon()).into(viewHolder.post_Icon);
//
//
//        } catch (Exception e) {
//            viewHolder.post_Icon.setImageResource(R.drawable.trm_logo);
//
//        }

//        if (status.equals("Occupied"))
//        {
//
//            viewHolder.available.setTextColor( context.getResources().getColor( R.color.cpb_red ) );
//        }
//        if (status.equals("Vacant"))
//        {
//            viewHolder.available.setTextColor( context.getResources().getColor(R.color.colorPrimary) );
//        }




        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




            }
        });


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,amount,balance,payment,date,sub;


        ViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.name);
            amount = itemView.findViewById(R.id.amount);
            balance = itemView.findViewById(R.id.balance);
            payment = itemView.findViewById(R.id.payment);
            date = itemView.findViewById(R.id.date);




        }
    }


}
