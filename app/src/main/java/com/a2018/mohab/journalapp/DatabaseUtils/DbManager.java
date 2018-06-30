package com.a2018.mohab.journalapp.DatabaseUtils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DbManager {
    public DatabaseReference db ;
    public DbManager(String ref) {
        db = FirebaseDatabase.getInstance().getReference(ref);
    }
}
