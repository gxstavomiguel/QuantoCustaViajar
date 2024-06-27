package com.example.quantocustaviajar.db.model;

public class TB_VIAGEM {
    private Long id;
    private int totalViajantes;
    private int duracaoViagem;
    private double custoTotalViagem;
    private double custoPorPessoa;
    private String local;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTotalViajantes(int i) {
        this.totalViajantes = i;
    }

    public Object getCustoTotalViagem() {
        return this.custoTotalViagem;
    }

    public void setLocal(String destino) {
        this.local = destino;
    }

    public Object getTotalViajantes() {
        return this.totalViajantes;
    }

    public void setCustoPorPessoa(int i) {
        this.custoPorPessoa = i;
    }
}
