package com.example.eatnow.eatnow.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.eatnow.eatnow.Model.ItemClickListener;
import com.example.eatnow.eatnow.R;

public class FoodItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtMenuItemName;

    private ItemClickListener itemClickListener;

    public FoodItemViewHolder(@NonNull View itemView) {
        super(itemView);

        txtMenuItemName = (TextView) itemView.findViewById(R.id.menu_item_name);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}
