package com.artistic_talent.eatnow.eatnow;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.iid.InstanceIdResult;

public class SignInActivity extends AppCompatActivity {

    EditText textEmail, textPassword;
    FirebaseAuth auth;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseUsers, ChatRequestRef;


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


    }

    public void signInUser(View v)
    {
        final String email = textEmail.getText().toString();
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
                                Toast.makeText(getApplicationContext(), "Logged in successfully.", Toast.LENGTH_SHORT).show();
                                finish();

                                // Update device token
                                updateDeviceToken();

                                // Redirect user to appropriate page
                                redirectSignIn();

                                //Send email over to Order activity
                                MyApplication.someVariable = textEmail.getText().toString();
                            }
                            else
                            { Toast.makeText(getApplicationContext(), "Incorrect credentials. Please try again.", Toast.LENGTH_SHORT).show(); }
                        }
                    });
        }
    }


    private void redirectSignIn()
    {
        // Get user role & redirect accordingly
        auth.getCurrentUser().getIdToken(false).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
            @Override
            public void onSuccess(GetTokenResult getTokenResult) {
                String role = "user";
                if (getTokenResult.getClaims().get("role") != null ) { role = getTokenResult.getClaims().get("role").toString(); }

                Intent i = new Intent();

                switch (role)
                {
                    // TODO: case "staff"
                    case "user":
                        i = new Intent(getApplicationContext(), MenuActivity.class);
                        break;
                    case "staff":
                        i = new Intent(getApplicationContext(), ChefActivity.class);
                        break;
                    case "superadmin":
                        i = new Intent(getApplicationContext(), AdminActivity.class);
                        break;
                }

                startActivity(i);
            }
        });
    }

    private void updateDeviceToken()
    {
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String device_token = instanceIdResult.getToken();

                DatabaseReference database_users = database.getReference("users");

                String email = Help.stripPath(textEmail.getText().toString());

                database_users.child(email).child("userDeviceToken").setValue(device_token);
            }
        });
    }
}
