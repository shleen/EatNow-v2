package com.example.eatnow.eatnow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanFunctionActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView QRCodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QRCodeView = new ZXingScannerView( this);
        setContentView(QRCodeView);
    }

    @Override
    public void handleResult(Result result) {
        QRActivity.displayResult.setText(result.getText());
        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();

        QRCodeView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();

        QRCodeView.setResultHandler(this);
        QRCodeView.startCamera();
    }
}
