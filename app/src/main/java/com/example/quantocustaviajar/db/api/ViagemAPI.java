package com.example.quantocustaviajar.db.api;

import com.example.quantocustaviajar.db.model.Resposta;
import com.example.quantocustaviajar.db.model.TB_VIAGEM;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ViagemAPI {

    @POST("/api/cadastro/viagem")
    Call<Resposta> cadastrarViagem(@Body TB_VIAGEM viagem);

    @GET("/api/listar/viagem/{viagemId}")
    Call<TB_VIAGEM> listarViagem(@Path("viagemId") Long viagemId);

    @GET("/api/listar/viagem/conta/{contaId}")
    Call<List<TB_VIAGEM>> listarViagemPorConta(@Path("contaId") Long contaId);
}
