package com.example.eatnow.eatnow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide app bar
        getSupportActionBar().hide();

        TextView lkSignIn = (TextView) findViewById(R.id.lkSignIn);
        lkSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(i);
            }
        });

    }

    public void openSignUp(View v)
    {
        Intent i = new Intent(this, SignUpActivity.class);
        startActivity(i);
    }
}
