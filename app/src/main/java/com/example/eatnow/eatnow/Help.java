package com.example.eatnow.eatnow;

import android.widget.TextView;

import com.example.eatnow.eatnow.Model.OrderItem;
import com.google.common.collect.Iterables;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Help {
    // Returns the textual value of the given TextView
    public static String getText(TextView tv) {
        return tv.getText().toString();
    }

    // Strip database path
    public static String stripPath(String s) {
        return s.replaceAll("[\\[\\].#$]", "");
    }

    // Get the next ID
    public static int getNextId(DataSnapshot ds) {
        Iterable<DataSnapshot> children = ds.getChildren();

        int last = -1;

        for (DataSnapshot c : children) {
            if (Integer.parseInt(c.getKey()) > last) last = Integer.parseInt(c.getKey());
        }

        return last + 1;
    }

    // Add a given OrderItem to the current user's pending order
    public static void addToOrder(OrderItem oi, DatabaseReference upo, DataSnapshot ds) {

        // Pending order is empty
        if (Iterables.size(ds.getChildren()) == 0) {
            upo.child("0").setValue(oi);
        }

        for (DataSnapshot c : ds.getChildren()) {
            if (c.child("name").getValue().toString().equals(oi.getName())) {
                upo.child(c.getKey()).child("qty").setValue(Integer.parseInt(c.child("qty").getValue().toString()) + oi.getQty());
                return;
            }
        }

        upo.child(Integer.toString(getNextId(ds))).setValue(oi);
    }

    // Returns the total of a given order
    public static double calculateTotal(DataSnapshot ds) {
        double total = 0;

        Iterable<DataSnapshot> children = ds.getChildren();

        for (DataSnapshot c : children) {
            total += Double.parseDouble(c.child("price").getValue().toString()) * Integer.parseInt(c.child("qty").getValue().toString());
        }

        return total;
    }

    // Pending order to processing
    public static void moveToProcessing(final DatabaseReference fromPath, final DatabaseReference toPath, final String key) {
        fromPath.child(key).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    // dataSnapshot holds the key and the value at the "fromPath".
                    // Let's move it to the toPath. This operation duplicates the key/value pair at the fromPath to the toPath
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                        for (DataSnapshot c : children) {
                            toPath.child(c.getKey())
                                    .setValue(c.getValue(), new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                            if (databaseError == null) {
                                                // Success!

                                                // Remove the original value
                                                fromPath.child(key).setValue(null);
                                            } else {
                                                // Operation failed
                                            }
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Database error
                    }
                });
    }
}