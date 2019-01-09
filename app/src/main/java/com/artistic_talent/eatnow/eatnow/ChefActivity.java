package com.artistic_talent.eatnow.eatnow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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
import java.util.Map;

public class ChefActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    RecyclerView recyclerView;
    DatabaseReference ref;
    FirebaseRecyclerAdapter<CategoryItem,ChefItemViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_chef);
        super.onCreate(savedInstanceState);

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
                        Iterable<DataSnapshot> users_processing_orders = dataSnapshot.getChildren();

                        for (DataSnapshot c : users_processing_orders)
                        {
                            // c = a specific user's processing orders
                            //Iterable<DataSnapshot>
                        }
                        Log.i("StrvvddWWtCpQmYnNkn4v7g", dataSnapshot.toString());
                        //Get map of users in datasnapshot
                        collectNamenQty((Map<Integer,Object>) dataSnapshot.getValue());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
        FirebaseRecyclerAdapter<CategoryItem, ChefItemViewHolder> adapter = new FirebaseRecyclerAdapter<CategoryItem, ChefItemViewHolder>(CategoryItem.class, R.layout.layout_chef_item, ChefItemViewHolder.class, ref) {
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
