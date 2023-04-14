package com.ken.trustinsurance.Utilities;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;


import com.ken.trustinsurance.R;


public class CustomWelcome {

    Context context;

    AlertDialog alertDialog;

    public CustomWelcome(Context context){
        this.context = context;
    }

    public void startLoading(LayoutInflater inflater){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setView(inflater.inflate(R.layout.custom_register, null));
        builder.setCancelable(false);

        alertDialog = builder.create();
        alertDialog.show();
    }

    public void dismissLoading(){
        alertDialog.dismiss();
    }
}
