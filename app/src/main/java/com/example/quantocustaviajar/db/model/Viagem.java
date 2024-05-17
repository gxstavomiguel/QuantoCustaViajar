package com.example.quantocustaviajar.db.model;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Viagem {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String destino;
    public int totalViajantes;
    public int duracaoDias;
}
