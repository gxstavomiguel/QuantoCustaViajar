package com.example.quantocustaviajar.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Helper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "myapp.db";
    private static final int DATABASE_VERSION = 1;

    public Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Criação da tabela viagem (adapte conforme sua necessidade)
        String createTableViagem = "CREATE TABLE viagem \n" +
                "\t(id INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "\tdestino TEXT,\n" +
                "\tqt_pessoas INTEGER,\n" +
                "\tdata_inicio DATE,\n" +
                "\tdata_fim DATE,\n" +
                "\tstatus TEXT,\n" +
                "\tgasolina_total_km REAL,\n" +
                "\tgasolina_media_km_por_litro REAL,\n" +
                "\tgasolina_custo_medio_por_litro REAL,\n" +
                "\tgasolina_total_veiculos INTEGER,\n" +
                "\ttarifa_custo_total REAL,\n" +
                "\ttarifa_aluguel_veiculo REAL,\n" +
                "\trefeicao_custo_por_refeicao REAL,\n" +
                "\trefeicao_num_refeicoes_por_dia INTEGER,\n" +
                "\thospedagem_custo_por_noite REAL, \n" +
                "\thospedagem_total_noites INTEGER, \n" +
                "\thospedagem_total_quartos INTEGER) ";
        db.execSQL(createTableViagem);

        String createTableEntretenimento = "CREATE TABLE entretenimento \n" +
                "\t(id INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "\tidviagem integer,\n" +
                "\tdescricao TEXT,\n" +
                "\tcusto REAL)";
        db.execSQL(createTableEntretenimento);


        String createTableUsuario = "CREATE TABLE usuario \n" +
                "\t(id INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "\tusername TEXT,\n" +
                "\tpassword TEXT, \n" +
                "\tphone TEXT, \n" +
                "\temail TEXT)";
        db.execSQL(createTableUsuario);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS viagem");
        onCreate(db);
    }
}
