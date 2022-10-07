package com.Ali.PharmacistsApp.Database.ModelDB;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Cart")
public class Cart {
    @NonNull
    @PrimaryKey()
    @ColumnInfo(name="id")
    public String id;

    @ColumnInfo(name="companyId")
    public int companyId;

    @ColumnInfo(name="name")
    public String name;

    @ColumnInfo(name="amount")
    public int amount;

    @ColumnInfo(name="price")
    public double price;

    @ColumnInfo(name="company")
    public String company;

    @ColumnInfo(name="link")
    public String link;
}
