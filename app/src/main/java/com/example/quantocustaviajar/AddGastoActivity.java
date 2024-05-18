package com.example.quantocustaviajar;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quantocustaviajar.db.Helper;

public class AddGastoActivity extends AppCompatActivity {

    private GridLayout gridGasolina, gridTarifa, gridRefeicoes, gridHospedagem, gridEntretenimento;
    private Button btnVoltar, btnAddGasto;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gasto);

        gridGasolina = findViewById(R.id.gridGasolina);
        gridTarifa = findViewById(R.id.gridTarifa);
        gridRefeicoes = findViewById(R.id.gridRefeicoes);
        gridHospedagem = findViewById(R.id.gridHospedagem);
        gridEntretenimento = findViewById(R.id.gridEntretenimento);
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
                long result;
                ContentValues contentValues = new ContentValues();

                try {

                    SQLiteDatabase db = new Helper(AddGastoActivity.this).getWritableDatabase();

                    // Obter o id da viagem com status "aberto"
                    int viagemId = -1;
                    Cursor cursor = db.rawQuery("SELECT id FROM viagem WHERE status = 'aberto'", null);
                    if (cursor.moveToFirst()) {
                        viagemId = cursor.getInt(0);
                    }
                    cursor.close();

                    if (viagemId == -1) {
                        Toast.makeText(AddGastoActivity.this, "Não foi encontrada uma viagem com status 'aberto'", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    switch (tipoGasto) {
                        case "gasolina":
                            double totalKm = Double.parseDouble(((EditText) findViewById(R.id.contentTituloTotalkm)).getText().toString());
                            double mediaKmPorLitro = Double.parseDouble(((EditText) findViewById(R.id.contentMediaKmLitro)).getText().toString());
                            double custoPorLitro = Double.parseDouble(((EditText) findViewById(R.id.contentCustoLitro)).getText().toString());
                            int totalVeiculos = Integer.parseInt(((EditText) findViewById(R.id.contentTotalVeiculos)).getText().toString());

                            contentValues.put("gasolina_total_km", totalKm);
                            contentValues.put("gasolina_media_km_por_litro", mediaKmPorLitro);
                            contentValues.put("gasolina_custo_medio_por_litro", custoPorLitro);
                            contentValues.put("gasolina_total_veiculos", totalVeiculos);
                            break;
                        case "tarifas":
                            double custoTotal = Double.parseDouble(((EditText) findViewById(R.id.contentCustoTotal)).getText().toString());
                            double aluguelVeiculo = Double.parseDouble(((EditText) findViewById(R.id.contentAluguelVeiculo)).getText().toString());

                            contentValues.put("tarifa_custo_total", custoTotal);
                            contentValues.put("tarifa_aluguel_veiculo", aluguelVeiculo);
                            break;
                        case "refeicoes":
                            double custoRefeicao = Double.parseDouble(((EditText) findViewById(R.id.contentCustoRefeicao)).getText().toString());
                            int numRefeicoesDia = Integer.parseInt(((EditText) findViewById(R.id.contentRefeicoesPorDia)).getText().toString());

                            contentValues.put("refeicao_custo_por_refeicao", custoRefeicao);
                            contentValues.put("refeicao_num_refeicoes_por_dia", numRefeicoesDia);
                            break;
                        case "hospedagem":
                            double custoPorNoite = Double.parseDouble(((EditText) findViewById(R.id.contentCustoPorNoite)).getText().toString());
                            int totalNoites = Integer.parseInt(((EditText) findViewById(R.id.contentTotalNoites)).getText().toString());
                            int totalQuartos = Integer.parseInt(((EditText) findViewById(R.id.contentTotalQuartos)).getText().toString());

                            contentValues.put("hospedagem_custo_por_noite", custoPorNoite);
                            contentValues.put("hospedagem_total_noites", totalNoites);
                            contentValues.put("hospedagem_total_quartos", totalQuartos);
                            break;
                        case "entretenimento":
                            String descricao = ((EditText) findViewById(R.id.contentDescricaoEntretenimento)).getText().toString();
                            double custoEntretenimento = Double.parseDouble(((EditText) findViewById(R.id.contentCustoEntretenimento)).getText().toString());

                            contentValues.put("idviagem", viagemId);
                            contentValues.put("descricao", descricao);
                            contentValues.put("custo", custoEntretenimento);

                            // Inserir na tabela "entretenimento" e não na tabela "viagem"
                            result = db.insert("entretenimento", null, contentValues);
                            if (result != -1) {
                                Toast.makeText(AddGastoActivity.this, "Dados do gasto de entretenimento adicionados com sucesso", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AddGastoActivity.this, "Erro ao adicionar os dados do gasto de entretenimento", Toast.LENGTH_SHORT).show();
                            }
                            return;
                    }

                    result = db.update("viagem", contentValues, "id = ?", new String[]{String.valueOf(viagemId)});
                    if (result != -1) {
                        Toast.makeText(AddGastoActivity.this, "Dados do gasto adicionados com sucesso", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddGastoActivity.this, "Erro ao adicionar os dados do gasto", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(AddGastoActivity.this, "Erro ao processar os dados: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
