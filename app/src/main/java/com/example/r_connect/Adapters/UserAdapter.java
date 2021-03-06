package com.example.r_connect.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.r_connect.R;
import com.example.r_connect.models.User;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> Users;
    private Context mcontext;

    public UserAdapter(List<User> Users, Context mcontext){
        this.Users = Users;
        this.mcontext = mcontext;
    }



    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        holder.bind(Users.get(position));

    }



    @Override
    public int getItemCount() {
        return Users.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        TextView name , mail;
        CircleImageView profile_image;
        User  user;

        public UserViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameOfUser);
            mail =  itemView.findViewById(R.id.mailOfUser);
            profile_image = itemView.findViewById(R.id.User_imageList);
        }

        public void bind(User user) {
            this.user= user;
            mail.setText(user.getEmail());
            name.setText(user.getName());

            Glide.with(mcontext).load(user.getImage()).into(profile_image);

        }
    }
}