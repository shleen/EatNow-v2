package com.example.eatnow.eatnow;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.eatnow.eatnow.Model.ItemClickListener;
import com.example.eatnow.eatnow.Model.OrderItem;
import com.example.eatnow.eatnow.ViewHolder.OrderItemViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;

public class CartActivity extends BaseActivity {

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference pending_orders = database.getReference("orders/pending");

    RecyclerView recycler_order;
    RecyclerView.LayoutManager layout_manager;

    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cart);
        super.onCreate(savedInstanceState);

        // Load order items
        recycler_order = (RecyclerView) findViewById(R.id.recycler_order);
        recycler_order.setHasFixedSize(true);
        layout_manager = new LinearLayoutManager(this);
        recycler_order.setLayoutManager(layout_manager);

        loadOrder();
    }

    private void loadOrder()
    {
        DatabaseReference user_pending_order = pending_orders.child(Help.stripPath(auth.getCurrentUser().getEmail()));
        FirebaseRecyclerAdapter<OrderItem, OrderItemViewHolder> adapter = new FirebaseRecyclerAdapter<OrderItem, OrderItemViewHolder>(OrderItem.class, R.layout.order_item, OrderItemViewHolder.class, user_pending_order) {
            @Override
            protected void populateViewHolder(OrderItemViewHolder viewHolder, OrderItem model, int position) {
                int qty = model.getQty();

                viewHolder.txtOrderItemQty.setText(Integer.toString(qty));
                viewHolder.txtOrderItemName.setText(model.getName());

                NumberFormat formatter = NumberFormat.getCurrencyInstance();
                viewHolder.txtOrderItemPrice.setText(formatter.format(qty * model.getPrice()));

                final OrderItem clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(CartActivity.this, ""+clickItem.getName(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        recycler_order.setAdapter(adapter);
    }
}
