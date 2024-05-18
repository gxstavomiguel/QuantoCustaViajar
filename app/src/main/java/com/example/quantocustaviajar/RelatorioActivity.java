package com.example.quantocustaviajar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quantocustaviajar.db.Helper;

public class RelatorioActivity extends AppCompatActivity {

    private TextView contentDestino, contentPeriodo, contentNPessoas, contentCustoPessoa, contentCustoTotal;
    private Button btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio);

        contentDestino = findViewById(R.id.contentDestino);
        contentPeriodo = findViewById(R.id.contentPeriodo);
        contentNPessoas = findViewById(R.id.contentNPessoas);
        contentCustoPessoa = findViewById(R.id.contentCustoPessoa);
        contentCustoTotal = findViewById(R.id.contentCustoTotal);
        btnVoltar = findViewById(R.id.btnVoltar);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RelatorioActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        loadRelatorio();
    }

    private void loadRelatorio() {
        Helper dbHelper = new Helper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Consultar a tabela viagem
        String query = "SELECT destino, data_inicio, data_fim, qt_pessoas, " +
                "(SELECT SUM(gasolina_total_km * gasolina_custo_medio_por_litro) + " +
                "SUM(tarifa_custo_total) + SUM(refeicao_custo_por_refeicao * refeicao_num_refeicoes_por_dia) + " +
                "SUM(hospedagem_custo_por_noite * hospedagem_total_noites) " +
                "FROM viagem) AS custo_total " +
                "FROM viagem ORDER BY id DESC LIMIT 1";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String destino = cursor.getString(cursor.getColumnIndex("destino"));
            @SuppressLint("Range") String dataInicio = cursor.getString(cursor.getColumnIndex("data_inicio"));
            @SuppressLint("Range") String dataFim = cursor.getString(cursor.getColumnIndex("data_fim"));
            @SuppressLint("Range") int qtPessoas = cursor.getInt(cursor.getColumnIndex("qt_pessoas"));
            @SuppressLint("Range") double custoTotal = cursor.getDouble(cursor.getColumnIndex("custo_total"));
            double custoPorPessoa = custoTotal / qtPessoas;

            contentDestino.setText(destino);
            contentPeriodo.setText(dataInicio + " - " + dataFim);
            contentNPessoas.setText(String.valueOf(qtPessoas));
            contentCustoPessoa.setText(String.format("%.2f", custoPorPessoa));
            contentCustoTotal.setText(String.format("%.2f", custoTotal));
        } else {
            Toast.makeText(this, "Nenhum relatório encontrado", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }
}
