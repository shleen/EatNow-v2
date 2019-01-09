package com.example.eatnow.eatnow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.iid.FirebaseInstanceId;

public class ChefConfirmation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_confirmation);

    }

    private void addUser() {
        //String name = textEmail.getText().toString().trim();
        String device_token = FirebaseInstanceId.getInstance().getInstanceId().toString();

        String id = databaseUsers.push().getKey();

        Users users = new Users(id, name, device_token);

        databaseUsers.child(id).setValue(users);

        //Toast.makeText(getApplicationContext(), "User added", Toast.LENGTH_SHORT).show();
    }
}
