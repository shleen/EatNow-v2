package com.example.eatnow.eatnow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    TextView lkConvertToStaff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        lkConvertToStaff = (TextView) findViewById(R.id.lkConvertToStaff);
    }
}
