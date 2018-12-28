package com.example.eatnow.eatnow;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eatnow.eatnow.Model.FoodItem;
import com.example.eatnow.eatnow.Model.ItemClickListener;
import com.example.eatnow.eatnow.Model.OrderItem;
import com.example.eatnow.eatnow.ViewHolder.FoodItemViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;

public class MenuActivity extends BaseActivity {

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference menu  = database.getReference("menu");
    DatabaseReference pending_orders = database.getReference("orders/pending");

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layout_manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_menu);
        super.onCreate(savedInstanceState);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(i);
            }
        });

        // Load menu items
        recycler_menu = (RecyclerView) findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        layout_manager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layout_manager);

        loadMenu();
    }

    private void loadMenu() {
        FirebaseRecyclerAdapter<FoodItem, FoodItemViewHolder> adapter = new FirebaseRecyclerAdapter<FoodItem, FoodItemViewHolder>(FoodItem.class, R.layout.food_item, FoodItemViewHolder.class, menu) {
            @Override
            protected void populateViewHolder(FoodItemViewHolder viewHolder, FoodItem model, int position) {
                viewHolder.txtMenuItemName.setText(model.getName());

                NumberFormat formatter = NumberFormat.getCurrencyInstance();
                viewHolder.txtMenuItemPrice.setText(formatter.format(model.getPrice()));

                final FoodItem clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(MenuActivity.this, ""+clickItem.getName(), Toast.LENGTH_SHORT).show();
                    }
                });

                viewHolder.btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LayoutInflater inflater = getLayoutInflater();
                        View alertLayout = inflater.inflate(R.layout.add_to_cart_dialog, null);

                        final TextView txtQty = (TextView) alertLayout.findViewById(R.id.txtQty);

                        new AlertDialog.Builder(MenuActivity.this)
                                .setTitle("Add "+clickItem.getName())
                                .setView(alertLayout)
                                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        final OrderItem oi = new OrderItem(clickItem.getName(), clickItem.getPrice(), clickItem.getStall_id(), Integer.parseInt(txtQty.getText().toString()));

                                        // Get the next ID
                                        final DatabaseReference user_pending_orders = pending_orders.child(Help.stripPath(auth.getCurrentUser().getEmail()));
                                        user_pending_orders.addListenerForSingleValueEvent(
                                                new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        // Add item to order
                                                        Help.addToOrder(oi, user_pending_orders, dataSnapshot);
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {
                                                        //handle databaseError
                                                    }
                                                });
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                    }
                });
            }
        };
        recycler_menu.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}