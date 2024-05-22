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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddViagemActivity extends AppCompatActivity {

    private EditText  etTotalViajantes, etDataInicio;
    private Button btnVoltar, btnConfirmAddViagem;
    private Calendar calendar;

    private AutoCompleteTextView etDestino;
    private String[] destinos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_viagem);

        etDestino = findViewById(R.id.etDestino);
        destinos = getResources().getStringArray(R.array.destinos_array);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, destinos);
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
        String myFormat = "dd/MM/yyyy"; // In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etDataInicio.setText(sdf.format(calendar.getTime()));
    }

    private void addViagem(String destino, String totalViajantes, String dataInicio) {
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
