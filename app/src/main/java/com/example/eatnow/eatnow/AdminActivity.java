/*

Displays all regular users & all staff accounts in separate RecyclerViews.
Allows the superadmin to grantStaff/ grantUser

 */

package com.example.eatnow.eatnow;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminActivity extends AppCompatActivity {

    FirebaseAuth auth = FirebaseAuth.getInstance();

    public static UserAdapter user_adapter;
    public static UserAdapter staff_adapter;
    public static RecyclerView recycler_users;
    public static RecyclerView recycler_staff;
    RecyclerView.LayoutManager layout_manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Display users
        recycler_users = (RecyclerView) findViewById(R.id.recycler_users);
        recycler_users.setHasFixedSize(true);
        layout_manager = new LinearLayoutManager(this);
        recycler_users.setLayoutManager(layout_manager);
        user_adapter = new UserAdapter();
        recycler_users.setAdapter(user_adapter);

        recycler_staff = (RecyclerView) findViewById(R.id.recycler_staff);
        recycler_staff.setHasFixedSize(true);
        layout_manager = new LinearLayoutManager(this);
        recycler_staff.setLayoutManager(layout_manager);
        staff_adapter = new UserAdapter();
        recycler_staff.setAdapter(staff_adapter);

        loadUsers();
    }

    public static void loadUsers() {
        FirebaseFunctions functions = FirebaseFunctions.getInstance();

        functions.getHttpsCallable("getAllUsers")
                .call()
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // TODO: Handle failure
                        Log.i("StrvvddWWtCpQmYnNkn4v7g", "sumthin died");
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
                    @Override
                    public void onSuccess(HttpsCallableResult httpsCallableResult) {
                        List<List<String>> all_users = (List<List<String>>) httpsCallableResult.getData();

                        List<List<String>> users = new ArrayList<>();
                        List<List<String>> staff = new ArrayList<>();

                        for (List<String> u : all_users)
                        {
                            switch (u.get(1))
                            {
                                case "user":
                                    users.add(u);
                                    break;
                                case "staff":
                                    staff.add(u);
                                    break;
                                default:
                                    break;
                            }
                        }

                        user_adapter = new UserAdapter(users);
                        recycler_users.setAdapter(user_adapter);

                        staff_adapter = new UserAdapter(staff);
                        recycler_staff.setAdapter(staff_adapter);
                    }
                });
    }
}
