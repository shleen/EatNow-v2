package com.artistic_talent.eatnow.eatnow;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.artistic_talent.eatnow.eatnow.Model.CategoryItem;;
import com.artistic_talent.eatnow.eatnow.ViewHolder.OrderItemViewHolder;

import java.util.ArrayList;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemViewHolder>
    implements ItemTouchHelperAdapter {
    Context mContext;
    ArrayList<CategoryItem> mArray;

    public OrderItemAdapter(ArrayList<CategoryItem> mData, Context mContext) {
        this.mContext = mContext;
        this.mArray = mData;
    }

    @Override
    public OrderItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_item, parent, false);

        return new OrderItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrderItemViewHolder holder, int position) {
        holder.txtOrderItemName.setText(mArray.get(position).name);
        holder.txtOrderItemPrice.setVisibility(View.INVISIBLE);
        holder.txtOrderItemQty.setText(Integer.toString(mArray.get(position).qty));
    }

    @Override
    public int getItemCount() {

        return mArray.size();
    }

    @Override
    public void onItemDismiss(int position) {
        mArray.remove(position);
        notifyItemRemoved(position);

        // TODO: Handle item completed
    }
}
