package com.example.quantocustaviajar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quantocustaviajar.db.Helper;

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
                String totalKm = String.valueOf(findViewById(R.id.contentTituloTotalkm));
                String mediaKmPorLitro = String.valueOf(findViewById(R.id.contentMediaKmLitro));
                String custoPorLitro = String.valueOf(findViewById(R.id.contentCustoLitro));
                String totalVeiculos = String.valueOf(findViewById(R.id.contentTotalVeiculos));

                ContentValues contentValues = new ContentValues();
                contentValues.put("gasolina_total_km", Double.parseDouble(totalKm));
                contentValues.put("gasolina_media_km_por_litro", Double.parseDouble(mediaKmPorLitro));
                contentValues.put("gasolina_custo_medio_por_litro", Double.parseDouble(custoPorLitro));
                contentValues.put("gasolina_total_veiculos", Integer.parseInt(totalVeiculos));

                SQLiteDatabase db = new Helper(AddGastoActivity.this).getWritableDatabase();
                long result = db.insert("viagem", null, contentValues);

                if (result != -1) {
                    Toast.makeText(AddGastoActivity.this, "Dados do gasto adicionados com sucesso", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddGastoActivity.this, "Erro ao adicionar os dados do gasto", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
