package com.artistic_talent.eatnow.eatnow;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RobotActivity extends BaseActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();

    EditText textIP;
    EditText textOrderID;
    EditText textCollectionPointID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_robot);
        super.onCreate(savedInstanceState);

        textIP = (EditText) findViewById(R.id.textIP);

        textOrderID = (EditText) findViewById(R.id.textOrderID);
        textCollectionPointID = (EditText) findViewById(R.id.textCollectionPointID);

        // Populate the TextView
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String ip = dataSnapshot.child("robot/ip").getValue().toString();
                textIP.setText(ip);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // TODO: Handle database error
            }
        });
    }

    public void updateIP(View v)
    {
        ref.child("robot/ip").setValue(textIP.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Robot's IP updated!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void pingRobot(final View v)
    {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            OkHttpClient client = new OkHttpClient();
            public final MediaType JSON = MediaType.get("application/json; charset=utf-8");

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get the robot's IP from Firebase
                String ip = dataSnapshot.child("robot/ip").getValue().toString();

                // Get the parameters to send to the robot
                String orderID = textOrderID.getText().toString();
                String collectionPointID = textCollectionPointID.getText().toString();

                // send the httprequest
                RequestBody body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("order_id", orderID)
                        .addFormDataPart("collection_point_id", collectionPointID)
                        .build();

                try {
                    post("http://" + ip, body, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            // TODO: Handle failure
                            Log.i("StrvvddWWtCpQmYnNkn4v7g", e.getMessage());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.isSuccessful()) {
                                String responseStr = response.body().string();
                                // Do what you want to do with the response.
                                Log.i("StrvvddWWtCpQmYnNkn4v7g", responseStr);

                            } else {
                                // TODO: Handle request not successful
                                Log.i("StrvvddWWtCpQmYnNkn4v7g", "failed!");
                            }
                        }
                    });
                } catch (IOException e) {
                    // TODO: Handle exception
                    Log.i("StrvvddWWtCpQmYnNkn4v7g", e.getMessage());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

            Call post(String url, RequestBody body, Callback callback) throws IOException {
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();

            Call call = client.newCall(request);
            call.enqueue(callback);
            return call;
            }
        });
    }
}
