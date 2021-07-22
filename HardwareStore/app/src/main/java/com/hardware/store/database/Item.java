package com.hardware.store.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Items")
public class Item {
    @NonNull
    @PrimaryKey
    private String name;
    private int type;
    private int price;
    private int cores;
    private int threads;
    private int baseFreq;
    private int boostFreq;
    private int memory;

    Item(@NonNull String name, int type, int price, int cores, int threads, int baseFreq, int boostFreq, int memory) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.cores = cores;
        this.threads = threads;
        this.baseFreq = baseFreq;
        this.boostFreq = boostFreq;
        this.memory = memory;
    }

    @NonNull
    public String getName() {
        return this.name;
    }

    public int getType() {
        return this.type;
    }

    public int getPrice() {
        return this.price;
    }

    public int getCores() {
        return this.cores;
    }

    public int getThreads() {
        return this.threads;
    }

    public int getBaseFreq() {
        return this.baseFreq;
    }

    public int getBoostFreq() {
        return this.boostFreq;
    }

    public int getMemory() {
        return this.memory;
    }
}
