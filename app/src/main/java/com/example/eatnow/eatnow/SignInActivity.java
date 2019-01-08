package com.example.eatnow.eatnow;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class SignInActivity extends AppCompatActivity {

    EditText textEmail, textPassword;
    FirebaseAuth auth;

    DatabaseReference databaseUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");


        // Hide app bar
        // getSupportActionBar().hide();

        textEmail = (EditText) findViewById(R.id.textEmail);
        textPassword = (EditText) findViewById(R.id.textPassword);

        auth = FirebaseAuth.getInstance();

        // TODO: Remove pre-filled fields
        textEmail.setText("test@t.com");
        textPassword.setText("pass123");
    }

    public void signInUser(View v)
    {
        String email = textEmail.getText().toString();
        String password = textPassword.getText().toString();

        // Validate empty inputs
        if (email.isEmpty() || password.isEmpty())
        { Toast.makeText(getApplicationContext(), "Please fill in all fields.", Toast.LENGTH_SHORT).show(); }
        else
        {
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                addUser();
                                Toast.makeText(getApplicationContext(), "Logged in successfully.", Toast.LENGTH_SHORT).show();
                                finish();

                                Intent i = new Intent(getApplicationContext(), MenuActivity.class);
                                startActivity(i);
                            }
                            else
                            { Toast.makeText(getApplicationContext(), "Incorrect credentials. Please try again.", Toast.LENGTH_SHORT).show(); }
                        }
                    });
        }
    }

    private void addUser(){
        String name = textEmail.getText().toString().trim();
        String device_token = FirebaseInstanceId.getInstance().getInstanceId().toString();

        String id = databaseUsers.push().getKey();

        Users users = new Users(id, name, device_token);

        databaseUsers.child(id).setValue(users);

        //Toast.makeText(getApplicationContext(), "User added", Toast.LENGTH_SHORT).show();

    }
}
