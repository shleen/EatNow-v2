package com.example.eatnow.eatnow;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eatnow.eatnow.ViewHolder.UserViewHolder;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {
    private List<String> userList = new ArrayList<>();

    public UserAdapter() {
    }

    public UserAdapter(List<String> userList) {
        this.userList = userList;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user, parent, false);

        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final UserViewHolder holder, final int position) {
        holder.textUserEmail.setText(userList.get(position));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
