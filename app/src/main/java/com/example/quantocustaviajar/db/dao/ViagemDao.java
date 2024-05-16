package com.example.quantocustaviajar.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.quantocustaviajar.db.model.Viagem;

import java.util.List;

@Dao
public interface ViagemDao {
    @Insert
    void insert(Viagem viagem);

    @Query("SELECT * FROM Viagem")
    List<Viagem> getAll();
}

