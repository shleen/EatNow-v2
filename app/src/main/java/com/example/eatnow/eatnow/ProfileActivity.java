package com.example.eatnow.eatnow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;

    TextView textUserDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textUserDetails = (TextView) findViewById(R.id.textUserDetails);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        textUserDetails.setText(user.getEmail());
    }

    public void signOut(View v)
    {
        auth.signOut();
        finish();

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
