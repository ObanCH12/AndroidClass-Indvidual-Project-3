package com.example.individualproject3;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ParentAccount.class, ChildAccount.class },version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DataOrgDAO dataOrgDAO();
}
