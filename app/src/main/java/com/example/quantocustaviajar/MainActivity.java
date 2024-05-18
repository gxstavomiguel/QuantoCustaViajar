package com.example.quantocustaviajar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.quantocustaviajar.db.database.AppDatabase;
import com.example.quantocustaviajar.db.model.Usuario;

public class MainActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btnLogin, btnRegister;

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "viagens-database").allowMainThreadQueries().build();

        btnLogin.setOnClickListener(v -> login());
        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Registrar.class);
            startActivity(intent);
        });
    }

    private void login() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
        } else {
            Usuario user = db.usuarioDao().findUserByUsernameAndPassword(username, password);
            if (user != null) {
                Toast.makeText(this, "Login bem-sucedido", Toast.LENGTH_SHORT).show();
                try {
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "Credenciais inválidas", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
