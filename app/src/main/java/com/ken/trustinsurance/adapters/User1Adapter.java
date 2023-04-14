package com.ken.trustinsurance.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ken.trustinsurance.R;
import com.ken.trustinsurance.models.UsersModel;

import java.util.List;

public class User1Adapter extends RecyclerView.Adapter<User1Adapter.ViewHolder> {
    private Context context;
    private List<UsersModel> userList;
    private boolean isInChat;
    private String lastMessage;
    String Phone;

    public User1Adapter(Context context, List<UsersModel> userList, Boolean isInChat){
        this.context = context;
        this.userList = userList;
        this.isInChat = isInChat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final UsersModel user = userList.get(i);
        viewHolder.tv_username.setText(user.getName());


//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("pheels62").child(user.getUid());
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                UsersModel user = snapshot.getValue(UsersModel.class);
//
//                try {
//                    if (user.getImageUrl().equals("")){
//                        viewHolder.profile_pic.setImageResource(R.drawable.profileicon);
//                    }else {
//                        Glide.with(context).load(user.getImageUrl()).into(viewHolder.profile_pic);
//
//                    }
//
//                } catch (Exception e) {
//                    viewHolder.profile_pic.setImageResource(R.drawable.profileicon);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });



        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(v.getContext(), viewHolder.itemView);

                // Inflating popup menu from popup_menu.xml file
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        // Toast message on menu item clicked
                        int id=menuItem.getItemId();
                        if (id==R.id.delete)
                        {
                            ProgressDialog progressDialog = new ProgressDialog(context);
                            progressDialog.setMessage("Deleting........");
                            progressDialog.setCancelable(false);
                            progressDialog.setCanceledOnTouchOutside(false);
                            progressDialog.show();
                            DatabaseReference reference1=FirebaseDatabase.getInstance().getReference();
                            reference1.child("pheels62").child("beneficiaries").child(user.getPhone())
                                    .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    progressDialog.dismiss();
                                    Toast toast = Toast.makeText(context, "Deleted Successfully!!", Toast.LENGTH_LONG);
                                }
                            });
                            return true;

                        }

                        return true;
                    }
                });
                // Showing the popup menu
                popupMenu.show();

            }
        });
        viewHolder.tv_user_about_or_last_message.setText(user.getPhone());

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_username;
        TextView tv_user_about_or_last_message;
        ImageView profile_pic;


        ViewHolder(View itemView){
            super(itemView);
            tv_username = itemView.findViewById(R.id.username);
            tv_user_about_or_last_message = itemView.findViewById(R.id.phone);
            profile_pic = itemView.findViewById(R.id.profile_pic);
        }
    }


}
