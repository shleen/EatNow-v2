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

import org.apache.commons.validator.routines.EmailValidator;

public class SignUpActivity extends AppCompatActivity {

    EditText textEmail, textPassword, textCfmPassword;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Hide app bar
        // getSupportActionBar().hide();

        textEmail = (EditText) findViewById(R.id.textEmail);
        textPassword = (EditText) findViewById(R.id.textPassword);
        textCfmPassword = (EditText) findViewById(R.id.textCfmPassword);

        auth = FirebaseAuth.getInstance();
    }

    public void createUser(View v)
    {
        String email = Help.getText(textEmail);
        String password = Help.getText(textPassword);
        String cfm_password = Help.getText(textCfmPassword);

        // Validate empty inputs
        if (email.isEmpty() || password.isEmpty() || cfm_password.isEmpty())
        { Toast.makeText(getApplicationContext(), "Please fill in all fields.", Toast.LENGTH_SHORT).show(); }
        else
        {
            // Validate email
            if (!EmailValidator.getInstance().isValid(email))
            { Toast.makeText(getApplicationContext(), "Invalid email.", Toast.LENGTH_SHORT).show(); }
            else
            {
                // Validate password match
                if (!password.equals(cfm_password))
                { Toast.makeText(getApplicationContext(), "Passwords don't match. Try again.", Toast.LENGTH_SHORT).show(); }
                else
                {
                    // Validate password length
                    if (password.length() < 6)
                    { Toast.makeText(getApplicationContext(), "Please use a longer password.", Toast.LENGTH_SHORT).show(); }
                    else
                    {
                        // Create user
                        auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful())
                                        {
                                            Toast.makeText(getApplicationContext(), "User created successfully!", Toast.LENGTH_SHORT).show();

                                            finish();

                                            Intent i = new Intent(getApplicationContext(), MenuActivity.class);
                                            startActivity(i);
                                        }
                                        else
                                        { Toast.makeText(getApplicationContext(), "An error occurred. Please try again.", Toast.LENGTH_SHORT).show(); }
                                    }
                                });
                    }
                }
            }
        }
    }
}
