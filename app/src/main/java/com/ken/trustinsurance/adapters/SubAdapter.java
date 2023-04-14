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

public class SubAdapter extends RecyclerView.Adapter<SubAdapter.ViewHolder> {
    private Context context;
    private List<UsersModel> userList;
    private boolean isInChat;
    private String lastMessage;
    String Phone;

    public SubAdapter(Context context, List<UsersModel> userList, Boolean isInChat){
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

        viewHolder.tv_user_about_or_last_message.setText(user.getPlan());

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
