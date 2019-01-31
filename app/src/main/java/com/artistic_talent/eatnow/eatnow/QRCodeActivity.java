package com.artistic_talent.eatnow.eatnow;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.Iterator;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class QRCodeActivity extends AppCompatActivity {

    SurfaceView ScannerPreview;
    TextView ScanResult;
    BarcodeDetector QRCodeDetector;
    CameraSource CameraView;
    final int RequestCameraPermissionID = 1001;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference completed_orders = database.getReference("orders/completed");

    static int done = 0;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        CameraView.start(ScannerPreview.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        ScannerPreview = (SurfaceView) findViewById(R.id.scannerView);
        ScanResult = (TextView) findViewById(R.id.txt_scanResult);
        QRCodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        CameraView = new CameraSource
                .Builder(this, QRCodeDetector)
                .setRequestedPreviewSize(640, 480)
                .build();
        // Add Event
        ScannerPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //Request permission
                    ActivityCompat.requestPermissions(QRCodeActivity.this,
                            new String[]{Manifest.permission.CAMERA}, RequestCameraPermissionID);
                    return;
                }
                try {
                    CameraView.start(ScannerPreview.getHolder());
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                CameraView.stop();
            }
        });

        QRCodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrCode = detections.getDetectedItems();

                if(qrCode.size() != 0)
                {
                    if (done == 0)
                    {
                        done = 1;

                        String orderID = "123";
                        String collectionPointID = qrCode.valueAt(0).displayValue;

                        // Send HTTP Request to robot
                        RequestBody body = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("order_id", orderID)
                                .addFormDataPart("collection_point_id", collectionPointID)
                                .build();

                        Help.pingRobot(body);

                        // Move from completed to collected
//                        DatabaseReference collected_orders = database.getReference("orders/collected");
//                        Help.moveToCollected(user_completed_orders.child(lastChild.getKey()),
//                                collected_orders.child(Help.stripPath(auth.getCurrentUser().getEmail())),
//                                Integer.toString(Help.getNextId(dataSnapshot)));

                        finish();

                        Intent i = new Intent(getApplicationContext(), SuccessfulCollectionActivity.class);
                        startActivity(i);
                    }
                }
            }
        });
    }

}
