package com.example.eatnow.eatnow;

import android.content.DialogInterface;
import android.graphics.ColorSpace;
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
import com.example.eatnow.eatnow.Model.FoodItem;
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

import java.text.NumberFormat;

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

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layout_manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layout_manager);
        databaseReference = database.getReference();

//        options = new FirebaseRecyclerOptions.Builder<CategoryItem>()
//                .setQuery(databaseReference,CategoryItem.class).build();
//
//
//        adapter = new FirebaseRecyclerAdapter<CategoryItem, ChefItemViewHolder>(options) {
//
//            @Override
//            public void onBindViewHolder(ChefItemViewHolder holder, int position,CategoryItem model) {
//                holder.t1.setText(model.getName());
//                holder.t2.setText(model.getQuantity());
//
//
//
//            }
//
//            @Override
//            public ChefItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_chef_item,parent,false);
//                return new ChefItemViewHolder(view);
//            }
//        };

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean v=true;
                Log.i("ldsknvskjbv", dataSnapshot.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FirebaseRecyclerAdapter<CategoryItem, ChefItemViewHolder> adapter = new FirebaseRecyclerAdapter<CategoryItem, ChefItemViewHolder>(CategoryItem.class, R.layout.layout_chef_item, ChefItemViewHolder.class, databaseReference) {
            @Override
            protected void populateViewHolder(ChefItemViewHolder viewHolder, CategoryItem model, int position) {
                viewHolder.t1.setText(model.getName());
                viewHolder.t2.setText(model.getQuantity());

                Log.i("ldsknvskjbv", "hell yeah");
                Log.i("ldsknvskjbv", model.getName());
            }
        };
        recyclerView.setAdapter(adapter);
    }

}
