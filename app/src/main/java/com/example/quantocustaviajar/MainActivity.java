package com.example.quantocustaviajar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.quantocustaviajar.db.database.AppDatabase;
import com.example.quantocustaviajar.db.model.Viagem;

public class MainActivity extends AppCompatActivity {
    private EditText etDestino, etTotalViajantes, etDuracaoDias, etCustoTotal;
    private Button btnAddViagem;
    private TextView tvRelatorio;

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_viagem);

        etDestino = findViewById(R.id.etDestino);
        etTotalViajantes = findViewById(R.id.etTotalViajantes);
//        etDuracaoDias = findViewById(R.id.etDuracaoDias);
//        etCustoTotal = findViewById(R.id.etCustoTotal);
//        btnAddViagem = findViewById(R.id.btnEndViagem);
//        tvRelatorio = findViewById(R.id.tvRelatorio);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "viagens-database").allowMainThreadQueries().build();

        btnAddViagem.setOnClickListener(v -> addViagem());
    }

    private void addViagem() {
        String destino = etDestino.getText().toString();
        int totalViajantes = Integer.parseInt(etTotalViajantes.getText().toString());
        int duracaoDias = Integer.parseInt(etDuracaoDias.getText().toString());
        double custoTotal = Double.parseDouble(etCustoTotal.getText().toString());
        double custoPorPessoa = custoTotal / totalViajantes;





        Viagem viagem = new Viagem();
        viagem.destino = destino;
        viagem.totalViajantes = totalViajantes;
        viagem.duracaoDias = duracaoDias;
//        viagem.custoTotal = custoTotal;
//        viagem.custoPorPessoa = custoPorPessoa;

        db.viagemDao().insert(viagem);

        Toast.makeText(this, "Viagem adicionada", Toast.LENGTH_SHORT).show();
        gerarRelatorio();
    }

    private void gerarRelatorio() {
        StringBuilder relatorio = new StringBuilder();
        for (Viagem viagem : db.viagemDao().getAll()) {
            relatorio.append("Destino: ").append(viagem.destino).append("\n")
                    .append("Total de Viajantes: ").append(viagem.totalViajantes).append("\n")
                    .append("Duração: ").append(viagem.duracaoDias).append(" dias\n");
//                    .append("Custo Total: R$ ").append(viagem.custoTotal).append("\n")
//                    .append("Custo por Pessoa: R$ ").append(viagem.custoPorPessoa).append("\n\n");
        }
        tvRelatorio.setText(relatorio.toString());
    }
}
