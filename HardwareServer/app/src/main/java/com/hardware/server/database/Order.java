package com.hardware.server.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Orders")
public class Order {
    @NonNull
    @PrimaryKey
    private Integer id;
    private String text;

    public Order(@NonNull Integer id, String text) {
        this.id = id;
        this.text = text;
    }

    @NonNull
    public Integer getId() {
        return this.id;
    }

    public String getText() {
        return this.text;
    }
}
