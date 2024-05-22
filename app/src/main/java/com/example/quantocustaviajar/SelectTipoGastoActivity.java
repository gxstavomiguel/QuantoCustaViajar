package com.example.quantocustaviajar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quantocustaviajar.db.Helper;

public class SelectTipoGastoActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private Button btnConfirmaTipoGasto, btnVoltar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_tipo_gasto);

        radioGroup = findViewById(R.id.radioGroup);
        btnConfirmaTipoGasto = findViewById(R.id.btnConfirmaTipoGasto);
        btnVoltar = findViewById(R.id.btnVoltar);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (!existeViagemAberta()) {
            btnConfirmaTipoGasto.setEnabled(false);
            Toast.makeText(this, "Não existe uma viagem aberta. Adicione uma viagem primeiro.", Toast.LENGTH_LONG).show();
        } else {
            btnConfirmaTipoGasto.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NonConstantResourceId")
                @Override
                public void onClick(View v) {
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    RadioButton selectedRadioButton = findViewById(selectedId);

                    if (selectedRadioButton != null) {
                        String tipoGasto = "";

                        if (selectedRadioButton.getId() == R.id.radioButtonGasolina) {
                            tipoGasto = "gasolina";
                        } else if (selectedRadioButton.getId() == R.id.radioButtonRefeicoes) {
                            tipoGasto = "refeicoes";
                        } else if (selectedRadioButton.getId() == R.id.radioButtonHospedagem) {
                            tipoGasto = "hospedagem";
                        } else if (selectedRadioButton.getId() == R.id.radioButtonEntretenimento) {
                            tipoGasto = "entretenimento";
                        } else if (selectedRadioButton.getId() == R.id.radioButtonTarifas) {
                            tipoGasto = "tarifas";
                        }

                        Intent intent = new Intent(SelectTipoGastoActivity.this, AddGastoActivity.class);
                        intent.putExtra("TIPO_GASTO", tipoGasto);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SelectTipoGastoActivity.this, "Selecione um tipo de gasto.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private boolean existeViagemAberta() {
        Helper dbHelper = new Helper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM viagem WHERE status = 'aberto'", null);
        boolean existeAberta = false;
        if (cursor.moveToFirst() && cursor.getInt(0) > 0) {
            existeAberta = true;
        }
        cursor.close();
        return existeAberta;
    }
}
