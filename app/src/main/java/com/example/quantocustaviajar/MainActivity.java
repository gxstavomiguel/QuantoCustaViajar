package com.example.quantocustaviajar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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


    //ENRICOOOOO
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "users-database").allowMainThreadQueries().build();

        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Registrar.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                Log.d("Login", "Tentando fazer login com: " + username);

                try {
                   // Usuario user = db.usuarioDao().findByEmail(username);
                    Usuario user = db.usuarioDao().findUserByUsernameAndPassword(username, password);
                    if (user != null) {
                        Log.d("Login", "Usuário encontrado: " + user.email);
                        if (user.password.equals(password)) {
                            Log.d("Login", "Senha correta. Redirecionando para HomeActivity.");
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(intent);
                        } else {
                            Log.d("Login", "Senha incorreta.");
                            Toast.makeText(MainActivity.this, "Usuário ou senha inválidos", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.d("Login", "Usuário não encontrado.");
                        Toast.makeText(MainActivity.this, "Usuário ou senha inválidos", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("Login", "Erro ao fazer login", e);
                    Toast.makeText(MainActivity.this, "Erro ao fazer login", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
