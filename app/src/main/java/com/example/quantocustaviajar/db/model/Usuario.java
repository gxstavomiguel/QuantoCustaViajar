package com.example.quantocustaviajar.db.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Usuario {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String email;
    public String phone;
    public String password;
}