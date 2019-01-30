package com.artistic_talent.eatnow.eatnow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class OrderActivity extends AppCompatActivity {
    TextView txt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        //Display shit
        txt1 = findViewById(R.id.textView);
        //Get user email

        String email = MyApplication.someVariable;
        txt1.setText(email);
        //Search firebase for this shit

        //Display



    }
}
