package com.example.eatnow.eatnow.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.eatnow.eatnow.Model.ItemClickListener;
import com.example.eatnow.eatnow.R;

public class OrderItemViewHolder extends RecyclerView.ViewHolder {
    public TextView txtOrderItemQty;
    public TextView txtOrderItemName;
    public TextView txtOrderItemPrice;

    private ItemClickListener itemClickListener;

    public OrderItemViewHolder(@NonNull View itemView) {
        super(itemView);

        txtOrderItemQty = (TextView) itemView.findViewById(R.id.order_item_qty);
        txtOrderItemName = (TextView) itemView.findViewById(R.id.order_item_name);
        txtOrderItemPrice = (TextView) itemView.findViewById(R.id.order_item_price);
    }
}
