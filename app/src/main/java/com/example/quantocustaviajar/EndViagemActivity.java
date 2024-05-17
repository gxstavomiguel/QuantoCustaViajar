package com.example.quantocustaviajar;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class EndViagemActivity extends AppCompatActivity {

    private EditText etDataFim;
    private Button btnVoltar, btnEndViagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_viagem);

        etDataFim = findViewById(R.id.etDataFim);
        btnVoltar = findViewById(R.id.btnVoltar);
        btnEndViagem = findViewById(R.id.btnEndViagem);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EndViagemActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnEndViagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dataFim = etDataFim.getText().toString().trim();
                if (!dataFim.isEmpty()) {
                    // Update the database
                    updateViagemStatus(dataFim);
                } else {
                    Toast.makeText(EndViagemActivity.this, "Por favor, insira a data de fim", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateViagemStatus(String dataFim) {
        com.example.myapp.Helper dbHelper = new com.example.myapp.Helper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String updateQuery = "UPDATE viagem SET status = 'finalizado', data_fim = ? WHERE id = ?";
        db.execSQL(updateQuery, new String[]{dataFim, "ID_DA_VIAGEM"});

        Toast.makeText(this, "Viagem finalizada com sucesso", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(EndViagemActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
