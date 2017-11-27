package com.example.suelliton.sir.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aluno on 17/11/2017.
 */

public class Node {
    private int id;
    private String nome;
    private Integer temperatura;
    private Integer umidade_ar;
    private Integer umidade_solo;
    private String status;
    private String key;
    private HistoricoTemperatura historicoTemperatura;
    private HistoricoUmidadeAr historicoUmidadeAr;
    private HistoricoUmidadeSolo historicoUmidadeSolo;
    private Integer tempoAtivo;

    public Node(int id, String nome, int temperatura, int umidade_ar, int umidade_solo, String status, HistoricoTemperatura historicoTemperatura, HistoricoUmidadeAr historicoUmidadeAr, HistoricoUmidadeSolo historicoUmidadeSolo) {
        this.id = id;
        this.nome = nome;
        this.temperatura = temperatura;
        this.umidade_ar = umidade_ar;
        this.umidade_solo = umidade_solo;
        this.status = status;
        this.tempoAtivo = 0;
        this.historicoTemperatura = historicoTemperatura;
        this.historicoUmidadeAr = historicoUmidadeAr;
        this.historicoUmidadeSolo = historicoUmidadeSolo;
    }

    public Integer getTempoAtivo() {
        return tempoAtivo;
    }

    public void setTempoAtivo(Integer tempoAtivo) {
        this.tempoAtivo = tempoAtivo;
    }

    public HistoricoTemperatura getHistoricoTemperatura() {
        return historicoTemperatura;
    }

    public void setHistoricoTemperatura(HistoricoTemperatura historicoTemperatura) {
        this.historicoTemperatura = historicoTemperatura;
    }

    public HistoricoUmidadeAr getHistoricoUmidadeAr() {
        return historicoUmidadeAr;
    }

    public void setHistoricoUmidadeAr(HistoricoUmidadeAr historicoUmidadeAr) {
        this.historicoUmidadeAr = historicoUmidadeAr;
    }

    public HistoricoUmidadeSolo getHistoricoUmidadeSolo() {
        return historicoUmidadeSolo;
    }

    public void setHistoricoUmidadeSolo(HistoricoUmidadeSolo historicoUmidadeSolo) {
        this.historicoUmidadeSolo = historicoUmidadeSolo;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", temperatura='" + temperatura + '\'' +
                ", umidade_ar='" + umidade_ar + '\'' +
                ", umidade_solo='" + umidade_solo + '\'' +
                ", status='" + status + '\'' +
                ", key='" + key + '\'' +
                '}';
    }

    public Node(){

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Integer temperatura) {
        this.temperatura = temperatura;
    }

    public Integer getUmidade_ar() {
        return umidade_ar;
    }

    public void setUmidade_ar(Integer umidade_ar) {
        this.umidade_ar = umidade_ar;
    }

    public Integer getUmidade_solo() {
        return umidade_solo;
    }

    public void setUmidade_solo(Integer umidade_solo) {
        this.umidade_solo = umidade_solo;
    }
}
