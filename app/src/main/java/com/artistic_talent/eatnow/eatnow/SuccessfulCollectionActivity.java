package com.artistic_talent.eatnow.eatnow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SuccessfulCollectionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_successful_collection);
        super.onCreate(savedInstanceState);
    }

    public void backToHome(View view) {
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
    }
}
