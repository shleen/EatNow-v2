package com.example.eatnow.eatnow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanFunctionActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView QRCodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QRCodeView = new ZXingScannerView( this);
        setContentView(QRCodeView);
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

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        Log.v("tag", rawResult.getText()); // Prints scan results
        Log.v("tag", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)
        QRActivity.displayResult.setText(rawResult.getText());
        onBackPressed();
        //If you would like to resume scanning, call this method below:
        QRCodeView.resumeCameraPreview(this);
    }
}
