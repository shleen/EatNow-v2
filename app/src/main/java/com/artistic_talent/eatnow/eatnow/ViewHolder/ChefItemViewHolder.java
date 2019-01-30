package com.artistic_talent.eatnow.eatnow.ViewHolder;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.artistic_talent.eatnow.eatnow.R;

public class ChefItemViewHolder extends RecyclerView.ViewHolder {

    public TextView t1;
    public TextView t2;

    public ChefItemViewHolder(View itemView) {
        super(itemView);
        t1 = (TextView)itemView.findViewById(R.id.name);
        t2 = (TextView)itemView.findViewById(R.id.quantity);

    }
}
