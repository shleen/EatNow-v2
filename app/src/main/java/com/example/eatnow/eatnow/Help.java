package com.example.eatnow.eatnow;

import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;

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
}
