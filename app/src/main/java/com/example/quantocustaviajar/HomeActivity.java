package com.example.quantocustaviajar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private Button btnAddViagem;
    private Button btnAddGasto;
    private Button btnVerRelatorio;
    private Button btnEndViagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnAddViagem = findViewById(R.id.btnAddViagem);
        btnAddGasto = findViewById(R.id.btnAddGasto);
        btnVerRelatorio = findViewById(R.id.btnVerRelatorio);
        btnEndViagem = findViewById(R.id.btnEndViagem);

        btnAddViagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AddViagemActivity.class);
                startActivity(intent);
            }
        });

        btnAddGasto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SelectTipoGastoActivity.class);
                startActivity(intent);
            }
        });

        btnVerRelatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, RelatorioActivity.class);
                startActivity(intent);
            }
        });

        btnEndViagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, EndViagemActivity.class);
                startActivity(intent);
            }
        });
    }
}