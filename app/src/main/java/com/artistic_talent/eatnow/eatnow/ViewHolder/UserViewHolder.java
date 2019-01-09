package com.artistic_talent.eatnow.eatnow.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.artistic_talent.eatnow.eatnow.R;

public class UserViewHolder extends RecyclerView.ViewHolder  {
    public TextView textUserEmail;
    public TextView lkConvertToStaff;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);

        this.textUserEmail = itemView.findViewById(R.id.user_email);
        this.lkConvertToStaff = itemView.findViewById(R.id.lk_convert_to_staff);
    }
}
