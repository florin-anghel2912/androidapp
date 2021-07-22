package com.hardware.store.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Item.class}, version = 1, exportSchema = false)
abstract class ItemDatabase extends RoomDatabase {
    private static ItemDatabase INSTANCE;

    abstract ItemDAO getItemDAO();

    static ItemDatabase getItemDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ItemDatabase.class, "db")
                    .allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}
