package com.example.eatnow.eatnow;

import android.graphics.ColorSpace;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eatnow.eatnow.Model.CategoryItem;
import com.example.eatnow.eatnow.ViewHolder.ChefItemViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChefActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<CategoryItem> options;
    FirebaseRecyclerAdapter<CategoryItem,ChefItemViewHolder> adapter;

    //FirebaseAuth auth = FirebaseAuth.getInstance();
    //FirebaseDatabase database = FirebaseDatabase.getInstance();
    //DatabaseReference ref  = database.getReference("orders/completed/test@tcom/0");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("orders/completed/test@tcom/0");
        options = new FirebaseRecyclerOptions.Builder<CategoryItem>()
                .setQuery(databaseReference,CategoryItem.class).build();


        adapter = new FirebaseRecyclerAdapter<CategoryItem, ChefItemViewHolder>(options) {

            @Override
            public void onBindViewHolder(ChefItemViewHolder holder, int position,CategoryItem model) {
                holder.t1.setText(model.getName());
                holder.t2.setText(model.getQuantity());



            }

            @Override
            public ChefItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_chef_item,parent,false);
                return new ChefItemViewHolder(view);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);



    }

}
