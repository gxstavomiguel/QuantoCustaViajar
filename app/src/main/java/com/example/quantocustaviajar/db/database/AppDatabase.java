package com.example.quantocustaviajar.db.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.quantocustaviajar.db.dao.ViagemDao;
import com.example.quantocustaviajar.db.dao.UsuarioDao;
import com.example.quantocustaviajar.db.model.Viagem;
import com.example.quantocustaviajar.db.model.Usuario;

@Database(entities = {Viagem.class, Usuario.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ViagemDao viagemDao();
    public abstract UsuarioDao usuarioDao();
}
