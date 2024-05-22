package com.example.quantocustaviajar.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.quantocustaviajar.db.model.Usuario;
import java.util.List;

@Dao
public interface UsuarioDao {
    @Insert
    void insert(Usuario usuario);

    @Query("SELECT * FROM Usuario WHERE email = :email AND password = :password")
    Usuario getUser(String email, String password);

    @Query("SELECT * FROM Usuario")
    List<Usuario> getAllUsers();

    @Query("SELECT * FROM Usuario WHERE name = :username AND password = :password")
    Usuario findUserByUsernameAndPassword(String username, String password);

}
