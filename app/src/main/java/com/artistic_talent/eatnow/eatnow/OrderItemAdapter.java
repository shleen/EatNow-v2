package com.artistic_talent.eatnow.eatnow;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.artistic_talent.eatnow.eatnow.Model.CategoryItem;;
import com.artistic_talent.eatnow.eatnow.ViewHolder.OrderItemViewHolder;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemViewHolder>
    implements ItemTouchHelperAdapter {

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();

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
        CategoryItem completed = mArray.get(position);

        mArray.remove(position);
        notifyItemRemoved(position);

        // TODO: Handle item completed
        final String[] order_id = completed.getOrder_id().split("_");
        final DatabaseReference user_processing_order = ref.child("orders/processing")
                                                     .child(order_id[0])
                                                     .child(order_id[1]);

        user_processing_order.child(completed.getItem_id())
                             .child("completed")
                             .setValue(true);

        user_processing_order.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot c : children)
                {
                    if (!c.child("completed").exists())
                    {
                        Log.i("StrvvddWWtCpQmYnNkn4v7g", c.child("name").getValue().toString() + " not completed!");
                        return;
                    }
                }

                // Preparation complete. Move order to completed
                final DatabaseReference completed_orders = database.getReference("orders/completed");

                completed_orders.child(Help.stripPath(order_id[0])).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Help.moveToCompleted(user_processing_order,
                                completed_orders.child(Help.stripPath(order_id[0])),
                                Integer.toString(Help.getNextId(dataSnapshot)),
                                order_id[1]);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                // TODO: Notify user
                // Get device token
                DatabaseReference user = database.getReference("users/" + order_id[0]);
                user.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String device_token = dataSnapshot.child("userDeviceToken").getValue().toString();

                        // Send notification
                        FirebaseFunctions functions = FirebaseFunctions.getInstance();

                        Map<String, Object> data = new HashMap<>();
                        data.put("email", order_id[0]);
                        data.put("device_token", device_token);

                        functions.getHttpsCallable("sendNotification")
                                .call(data)
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // TODO: Handle failure
                                        Log.i("StrvvddWWtCpQmYnNkn4v7g", e.getLocalizedMessage());
                                    }
                                })
                                .addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
                                    @Override
                                    public void onSuccess(HttpsCallableResult httpsCallableResult) {
                                        Toast.makeText(mContext, "Notified!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // TODO: Check if order is completed
    }
}
