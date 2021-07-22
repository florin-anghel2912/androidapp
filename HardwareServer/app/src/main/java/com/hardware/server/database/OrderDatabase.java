package com.hardware.server.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Order.class}, version = 1, exportSchema = false)
abstract class OrderDatabase extends RoomDatabase {
    private static OrderDatabase INSTANCE;

    abstract OrderDAO getOrderDAO();

    static OrderDatabase getItemDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), OrderDatabase.class, "db")
                    .allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}
