package com.artistic_talent.eatnow.eatnow;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {
    //RecyclerView diule;

    //FirebaseDatabase database = FirebaseDatabase.getInstance();
    //FirebaseAuth auth = FirebaseAuth.getInstance();
    //FirebaseListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inputdialog);

        ImageView img1,img2,img3;
        TextView txt1,txt2,txt3,txt4,txt5,txt6,txt7,txt8,txt9;

        txt1 = (TextView)findViewById(R.id.textView1);
        txt2 = (TextView)findViewById(R.id.textView2);
        txt3 = (TextView)findViewById(R.id.textView3);
        txt4 = (TextView)findViewById(R.id.textView4);
        txt5 = (TextView)findViewById(R.id.textView5);
        txt6 = (TextView)findViewById(R.id.textView6);
        txt7 = (TextView)findViewById(R.id.textView7);
        txt8 = (TextView)findViewById(R.id.textView8);
        txt9 = (TextView)findViewById(R.id.textView9);

        img1 = (ImageView)findViewById(R.id.imageView);
        img2 = (ImageView)findViewById(R.id.imageView1);
        img3 = (ImageView)findViewById(R.id.imageView2);

        img1.setImageResource(R.drawable.wtf);
        txt1.setText("Name: Roasted Chicken Rice");
        txt2.setText("Quantity: 1");
        txt3.setText("Stall_id: 1");

        img2.setImageResource(R.drawable.wtf1);
        txt4.setText("Name: Ban Mian (dry)");
        txt5.setText("Quantity: 2");
        txt6.setText("Stall_id: 2");

        img3.setImageResource(R.drawable.wtf2);
        txt7.setText("Name: Ban Mian (soup)");
        txt8.setText("Quantity: 1");
        txt9.setText("Stall_id: 2");





        //Display shit
        //diule = (RecyclerView)findViewById(R.id.diulelomo);
        //Get user email
        //String email = MyApplication.someVariable;
        //String strNew = email.replace(".", "");
        //Search firebase for this shit
        /*
        //Set path
        DatabaseReference ref;
        ref = database.getReference("orders/processing"+strNew+"0");

        Query query = ref;
        FirebaseListOptions<Stuff> options = new FirebaseListOptions.Builder<Stuff>()
                                            .setLayout(R.layout.inputdialog)
                                            .setQuery(query,Stuff.class)
                                            .build();


        //Display

        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                TextView name = v.findViewById(R.id.name1);
                TextView qty = v.findViewById(R.id.qty1);
                TextView stall_id = v.findViewById(R.id.stallid1);

                Stuff std = (Stuff) model;
                name.setText(std.getName().toString());
                qty.setText(String.valueOf(std.getQty()));
                stall_id.setText(String.valueOf(std.getStall_id()));
            }
        };


        listview.setAdapter(adapter);



    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }*/
    }
}
