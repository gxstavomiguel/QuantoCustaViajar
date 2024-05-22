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
        String query = "SELECT destino, \n" +
                "       data_inicio, \n" +
                "       data_fim, \n" +
                "       qt_pessoas,\n" +
                "       ((gasolina_total_km / (CASE WHEN gasolina_media_km_por_litro = 0 THEN 1 ELSE gasolina_media_km_por_litro END) * gasolina_custo_medio_por_litro / CASE WHEN gasolina_total_veiculos = 0 THEN 1 ELSE gasolina_total_veiculos END) +   \n" +
                "       (tarifa_custo_total * QT_PESSOAS + TARIFA_ALUGUEL_VEICULO) + \n" +
                "       (refeicao_custo_por_refeicao * refeicao_num_refeicoes_por_dia * REFEICAO_CUSTO_POR_REFEICAO * ((julianday(strftime('%Y-%m-%d', substr(data_fim, 7, 4) || '-' || substr(data_fim, 4, 2) || '-' || substr(data_fim, 1, 2))) - \n" +
                "        julianday(strftime('%Y-%m-%d', substr(data_inicio, 7, 4) || '-' || substr(data_inicio, 4, 2) || '-' || substr(data_inicio, 1, 2)))))) + \n" +
                "       (hospedagem_custo_por_noite * hospedagem_total_noites * HOSPEDAGEM_TOTAL_QUARTOS) +\n" +
                "       (SELECT COALESCE(sum(custo), 0) FROM ENTRETENIMENTO E WHERE E.IDVIAGEM = VIAGEM.ID)) AS custo_total \n" +
                "       FROM viagem \n" +
                "       WHERE status = 'aberto'\n" +
                "       ORDER BY id DESC LIMIT 1;";

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
