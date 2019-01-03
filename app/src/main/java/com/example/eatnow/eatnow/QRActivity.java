package com.example.eatnow.eatnow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class QRActivity extends BaseActivity {

    public static TextView displayResult;
    Button QRScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_qr);
        super.onCreate(savedInstanceState);

        displayResult = (TextView)findViewById(R.id.txt_displayResult);
        QRScanner = (Button)findViewById(R.id.btn_QRScanner);

        QRScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ScanFunctionActivity.class);
                startActivity(i);
            }
        });
    }
}
