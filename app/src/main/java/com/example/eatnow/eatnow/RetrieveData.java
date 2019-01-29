package com.artistic_talent.eatnow.eatnow;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RetrieveData extends AppCompatActivity {

    FirebaseAuth FBAuth;
    FirebaseDatabase FBDatabase;
    TextView userInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_data);

        FBAuth = FirebaseAuth.getInstance();
        userInfo = (TextView)findViewById(R.id.txt_userInfo);
        String email = FBAuth.getCurrentUser().getEmail();


    }
}
