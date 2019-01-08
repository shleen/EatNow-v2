package com.example.eatnow.eatnow;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RobotActivity extends BaseActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();

    EditText textIP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_robot);
        super.onCreate(savedInstanceState);

        textIP = (EditText) findViewById(R.id.textIP);

        // Populate the TextView
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String ip = dataSnapshot.child("robot/ip").getValue().toString();
                textIP.setText(ip);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // TODO: Handle database error
            }
        });
    }

    public void updateIP(View v)
    {
        ref.child("robot/ip").setValue(textIP.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Robot's IP updated!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
