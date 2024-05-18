package com.example.quantocustaviajar;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quantocustaviajar.db.Helper;

public class AddViagemActivity extends AppCompatActivity {

    private EditText etDestino, etTotalViajantes, etDataInicio;
    private Button btnVoltar, btnConfirmAddViagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_viagem);

        etDestino = findViewById(R.id.etDestino);
        etTotalViajantes = findViewById(R.id.etTotalViajantes);
        etDataInicio = findViewById(R.id.etDataInicio);
        btnVoltar = findViewById(R.id.btnVoltar);
        btnConfirmAddViagem = findViewById(R.id.btnConfirmAddViagem);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddViagemActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnConfirmAddViagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String destino = etDestino.getText().toString().trim();
                String totalViajantes = etTotalViajantes.getText().toString().trim();
                String dataInicio = etDataInicio.getText().toString().trim();

                if (!destino.isEmpty() && !totalViajantes.isEmpty() && !dataInicio.isEmpty()) {
                    addViagem(destino, totalViajantes, dataInicio);
                } else {
                    Toast.makeText(AddViagemActivity.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addViagem(String destino, String totalViajantes, String dataInicio) {
        Helper dbHelper = new Helper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String insertQuery = "INSERT INTO viagem (destino, qt_pessoas, data_inicio, status) VALUES (?, ?, ?, ?)";
        db.execSQL(insertQuery, new String[]{destino, totalViajantes, dataInicio, "em andamento"});

        Toast.makeText(this, "Viagem adicionada com sucesso", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(AddViagemActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
