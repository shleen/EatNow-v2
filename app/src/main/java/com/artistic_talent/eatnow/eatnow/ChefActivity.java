package com.artistic_talent.eatnow.eatnow;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;

import com.artistic_talent.eatnow.eatnow.Model.CategoryItem;
import com.artistic_talent.eatnow.eatnow.ViewHolder.ChefItemViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChefActivity extends BaseActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference ref;

    RecyclerView recyclerView;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_chef);
        super.onCreate(savedInstanceState);

        // Configure navigation
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    public boolean onNavigationItemSelected(MenuItem item) {
                        // Handle navigation view item clicks here.
                        int id = item.getItemId();

                        Intent i = new Intent();

                        if (id == R.id.nav_orders) {
                            i = new Intent(getApplicationContext(), ChefActivity.class);
                        } else if (id == R.id.nav_logout) {
                            signOut();
                            i = new Intent(getApplicationContext(), MainActivity.class);
                        }

                        startActivity(i);

                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });

        ref = database.getReference("orders/processing");
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layout_manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layout_manager);

        loadOrders();
    }

    private void loadOrders(){
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList<CategoryItem> item_list = new ArrayList<>();

                        Iterable<DataSnapshot> users_processing_orders = dataSnapshot.getChildren();

                        for (DataSnapshot all_orders : users_processing_orders)
                        {
                            Iterable<DataSnapshot> user_orders = all_orders.getChildren();

                            for (DataSnapshot order : user_orders)
                            {
                                Iterable<DataSnapshot> order_items = order.getChildren();

                                for (DataSnapshot order_item : order_items)
                                {
                                    item_list.add(new CategoryItem(order_item.child("name").getValue().toString(),
                                                                   Integer.parseInt(order_item.child("qty").getValue().toString()),
                                                                   Double.parseDouble(order_item.child("price").getValue().toString()),
                                                                   Integer.parseInt(order_item.child("stall_id").getValue().toString())));

                                }
                            }
                        }

                        OrderItemAdapter order_item_adapter = new OrderItemAdapter(item_list, getApplicationContext());
                        recyclerView.setAdapter(order_item_adapter);

                        SwipeController swipeController = new SwipeController();

                        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
                        itemTouchhelper.attachToRecyclerView(recyclerView);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }
}
