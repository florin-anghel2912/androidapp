package com.hardware.store.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ItemDAO {
    @Query("SELECT * FROM items")
    List<Item> getItems();
    @Insert
    void insert(Item... items);
    @Delete
    void delete(Item... items);
}
