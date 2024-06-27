package com.example.quantocustaviajar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quantocustaviajar.db.Helper;
import com.example.quantocustaviajar.db.api.ViagemAPI;
import com.example.quantocustaviajar.db.model.Resposta;
import com.example.quantocustaviajar.db.model.TB_VIAGEM;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddViagemActivity extends AppCompatActivity {

    private EditText etTotalViajantes, etDataInicio;
    private Button btnVoltar, btnConfirmAddViagem;
    private Calendar calendar;

    private AutoCompleteTextView etDestino;
    private String[] destinos;
    private ViagemAPI viagemAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_viagem);

        etDestino = findViewById(R.id.etDestino);
        destinos = getResources().getStringArray(R.array.destinos_array);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, destinos);
        etDestino.setAdapter(adapter);

        etTotalViajantes = findViewById(R.id.etTotalViajantes);
        etDataInicio = findViewById(R.id.etDataInicio);
        btnVoltar = findViewById(R.id.btnVoltar);
        btnConfirmAddViagem = findViewById(R.id.btnConfirmAddViagem);
        calendar = Calendar.getInstance();

        etDataInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddViagemActivity.this, dateSetListener,
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.genialsaude.com.br/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        viagemAPI = retrofit.create(ViagemAPI.class);
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        etDataInicio.setText(sdf.format(calendar.getTime()));
    }

    private void addViagem(String destino, String totalViajantes, String dataInicio) {
        // Construir o objeto TB_VIAGEM com os dados fornecidos
        TB_VIAGEM viagem = new TB_VIAGEM();

        // Definir o total de viajantes e verificar se é um número válido
        int totalViajantesInt = Integer.parseInt(totalViajantes);
        viagem.setTotalViajantes(totalViajantesInt);

        // Supondo que o custo total da viagem já foi definido em algum ponto do seu código
        double custoTotalViagem = viagem.getCustoTotalViagem();

        // Calcular o custo por pessoa
        double custoPorPessoa = custoTotalViagem / totalViajantesInt;
        viagem.setCustoPorPessoa(custoPorPessoa);

        // Definir o local da viagem
        viagem.setLocal(destino);

        // Enviar a requisição de cadastro de viagem
        Call<Resposta> call = viagemAPI.cadastrarViagem(viagem);
        call.enqueue(new Callback<Resposta>() {
            @Override
            public void onResponse(Call<Resposta> call, Response<Resposta> response) {
                if (response.isSuccessful()) {
                    Resposta resposta = response.body();
                    if (resposta != null && resposta.isSucesso()) {
                        Toast.makeText(AddViagemActivity.this, "Viagem adicionada com sucesso", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddViagemActivity.this, "Erro ao adicionar viagem: " + resposta.getMensagem(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddViagemActivity.this, "Erro ao adicionar viagem", Toast.LENGTH_SHORT).show();
                }

                // Navegar de volta para HomeActivity
                Intent intent = new Intent(AddViagemActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<Resposta> call, Throwable t) {
                Toast.makeText(AddViagemActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Helper dbHelper = new Helper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM viagem WHERE status = 'aberto'", null);
        if (cursor.moveToFirst() && cursor.getInt(0) > 0) {
            Toast.makeText(this, "Já existe uma viagem em andamento. Finalize-a antes de criar uma nova.", Toast.LENGTH_SHORT).show();
        } else {
            String insertQuery = "INSERT INTO viagem (destino, qt_pessoas, data_inicio, status) VALUES (?, ?, ?, ?)";
            db.execSQL(insertQuery, new String[]{destino, totalViajantes, dataInicio, "aberto"});

            Toast.makeText(this, "Viagem adicionada com sucesso", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(AddViagemActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
        cursor.close();
    }
}
