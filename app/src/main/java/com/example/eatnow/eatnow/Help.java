package com.example.eatnow.eatnow;

import android.widget.TextView;

import com.example.eatnow.eatnow.Model.OrderItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

public class Help {
    // Returns the textual value of the given TextView
    public static String getText(TextView tv) { return tv.getText().toString(); }

    // Strip database path
    public static String stripPath(String s) { return s.replaceAll("[\\[\\].#$]", ""); }

    // Get the next ID
    public static int getNextId(DataSnapshot ds)
    {
        Iterable<DataSnapshot> children = ds.getChildren();

        int last = -1;

        for (DataSnapshot c : children)
        { if (Integer.parseInt(c.getKey()) > last) last = Integer.parseInt(c.getKey()); }

        return last;
    }

    // Add a given OrderItem to the current user's pending order
    public static void addToOrder(OrderItem oi, DatabaseReference upo, DataSnapshot ds)
    {
        Iterable<DataSnapshot> children = ds.getChildren();

        for (DataSnapshot c : children)
        {
            if (c.child("name").getValue().toString().equals(oi.getName()))
            { upo.child(c.getKey()).child("qty").setValue(Integer.parseInt(c.child("qty").getValue().toString()) + oi.getQty()); }
            else
            { upo.child(Integer.toString(getNextId(ds) + 1)).setValue(oi); }
        }
    }
}
