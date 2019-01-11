package com.artistic_talent.eatnow.eatnow.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.artistic_talent.eatnow.eatnow.Model.ItemClickListener;
import com.artistic_talent.eatnow.eatnow.R;

public class FoodItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtMenuItemName;
    public TextView txtMenuItemPrice;
    public Button btnAdd;

    private ItemClickListener itemClickListener;

    public FoodItemViewHolder(@NonNull View itemView) {
        super(itemView);

        txtMenuItemName = (TextView) itemView.findViewById(R.id.food_item_name);
        txtMenuItemPrice = (TextView) itemView.findViewById(R.id.food_item_price);
        btnAdd = (Button) itemView.findViewById(R.id.btnAdd);

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
