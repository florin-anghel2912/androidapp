package com.hardware.server.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface OrderDAO {
    @Query("SELECT * FROM orders")
    List<Order> getOrders();
    @Insert
    void insert(Order... orders);
    @Delete
    void delete(Order... orders);
}
