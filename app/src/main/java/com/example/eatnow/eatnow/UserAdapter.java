package com.example.eatnow.eatnow;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eatnow.eatnow.ViewHolder.UserViewHolder;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {
    private List<List<String>> userList = new ArrayList<>();

    public UserAdapter() {
    }

    public UserAdapter(List<List<String>> userList) {
        this.userList = userList;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user, parent, false);

        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final UserViewHolder holder, final int position) {
        Log.i("StrvvddWWtCpQmYnNkn4v7g", userList.get(position).get(0));
        holder.textUserEmail.setText(userList.get(position).get(0));

        holder.lkConvertToStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // Convert the selected user to staff
                FirebaseFunctions functions = FirebaseFunctions.getInstance();

                Map<String, Object> data = new HashMap<>();
                data.put("email", holder.textUserEmail.getText());

                functions.getHttpsCallable("grantStaff")
                        .call(data)
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
                                Toast.makeText(v.getContext(), "uuuu", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
