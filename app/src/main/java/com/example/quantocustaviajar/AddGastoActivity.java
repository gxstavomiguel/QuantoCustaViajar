package com.example.quantocustaviajar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import androidx.appcompat.app.AppCompatActivity;

public class AddGastoActivity extends AppCompatActivity {

    private GridLayout gridGasolina, gridTarifa, gridRefeicoes, gridHospedagem, gridEntretenimento;
    private Button btnVoltar, btnAddGasto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gasto);

        gridGasolina = findViewById(R.id.gridGasolina);
        gridTarifa = findViewById(R.id.gridTarifa);
        gridRefeicoes = findViewById(R.id.gridRefeicoes);
        gridHospedagem = findViewById(R.id.gridHospedagem);
        gridEntretenimento = findViewById(R.id.gridentretenimento);
        btnVoltar = findViewById(R.id.btnVoltar);
        btnAddGasto = findViewById(R.id.btnAddGasto);

        Intent intent = getIntent();
        String tipoGasto = intent.getStringExtra("TIPO_GASTO");

        if (tipoGasto != null) {
            switch (tipoGasto) {
                case "gasolina":
                    gridGasolina.setVisibility(View.VISIBLE);
                    break;
                case "tarifas":
                    gridTarifa.setVisibility(View.VISIBLE);
                    break;
                case "refeicoes":
                    gridRefeicoes.setVisibility(View.VISIBLE);
                    break;
                case "hospedagem":
                    gridHospedagem.setVisibility(View.VISIBLE);
                    break;
                case "entretenimento":
                    gridEntretenimento.setVisibility(View.VISIBLE);
                    break;
            }
        }

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAddGasto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implementar lógica para adicionar gasto
            }
        });
    }
}
