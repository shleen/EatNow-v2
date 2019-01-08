package com.example.eatnow.eatnow;

import android.content.DialogInterface;
import android.graphics.ColorSpace;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eatnow.eatnow.Model.CategoryItem;
import com.example.eatnow.eatnow.Model.ItemClickListener;
import com.example.eatnow.eatnow.Model.OrderItem;
import com.example.eatnow.eatnow.ViewHolder.ChefItemViewHolder;
import com.example.eatnow.eatnow.ViewHolder.FoodItemViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChefActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    //FirebaseRecyclerOptions<CategoryItem> options;
    FirebaseRecyclerAdapter<CategoryItem,ChefItemViewHolder> adapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    //FirebaseAuth auth = FirebaseAuth.getInstance();
    //FirebaseDatabase database = FirebaseDatabase.getInstance();
    //DatabaseReference ref  = database.getReference("orders/completed/test@tcom/0");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef);
        databaseReference = FirebaseDatabase.getInstance().getReference("orders/processing/test@tcom/0");
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layout_manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layout_manager);


        loadOrders();


    }
    private void loadOrders(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("test@tcom");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        collectNamenQty((Map<Integer,Object>) dataSnapshot.getValue());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
        FirebaseRecyclerAdapter<CategoryItem, ChefItemViewHolder> adapter = new FirebaseRecyclerAdapter<CategoryItem, ChefItemViewHolder>(CategoryItem.class, R.layout.layout_chef_item, ChefItemViewHolder.class, databaseReference) {
            @Override
            protected void populateViewHolder(ChefItemViewHolder viewHolder, CategoryItem model, int position) {
                viewHolder.t1.setText(model.getName());
                viewHolder.t2.setText(Integer.toString(model.getQuantity()));

            }
        };
        recyclerView.setAdapter(adapter);




    }
    private void collectNamenQty(Map<Integer,Object> users) {

        ArrayList<Integer> ItemList = new ArrayList<>();

        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<Integer> qtyList = new ArrayList<>();

        //iterate through each user, ignoring their UID
        for (Map.Entry<Integer,Object> entry : users.entrySet()){

            //Get user map
            Map singleUser = (Map)entry.getValue();
            //Get phone field and append to list
            nameList.add((String) singleUser.get("name"));
            qtyList.add((Integer) singleUser.get("qty"));
        }

        System.out.println(nameList.toString());
        System.out.println(qtyList.toString());
    }

}
