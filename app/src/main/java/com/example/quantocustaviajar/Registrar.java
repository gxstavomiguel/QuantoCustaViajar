package com.example.quantocustaviajar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import com.example.quantocustaviajar.db.database.AppDatabase;
import com.example.quantocustaviajar.db.model.Usuario;

public class Registrar extends AppCompatActivity {
    private EditText etName, etEmail, etPhone, etNewPassword;
    private Button btnCreateAccount, btnLogin;
    private AppDatabase db;

    //enricuuuu
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        btnLogin = findViewById(R.id.btnLogin);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etNewPassword = findViewById(R.id.etPassword);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "users-database").allowMainThreadQueries().build();

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });
    }

    private void registrar() {
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String phone = etPhone.getText().toString();
        String password = etNewPassword.getText().toString();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
        } else {
            // Criação de novo usuário
            Usuario newUser = new Usuario();
            newUser.name = name;
            newUser.email = email;
            newUser.phone = phone;
            newUser.password = password;

            db.usuarioDao().insert(newUser);
            Toast.makeText(this, "Conta criada com sucesso", Toast.LENGTH_SHORT).show();

            // Redirecionar para a tela de login após a criação da conta
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(Registrar.this, MainActivity.class);
                startActivity(intent);
                finish();
            }, 1000);
        }
    }
}
