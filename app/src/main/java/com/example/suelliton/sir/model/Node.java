package com.example.suelliton.sir.model;

/**
 * Created by Aluno on 17/11/2017.
 */

public class Node {
    private int id;
    private String nome;
    private String temperatura;
    private String umidade_ar;
    private String umidade_solo;
    private String status;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Node(int id, String nome, String temperatura, String umidade_ar, String umidade_solo, String status) {
        this.id = id;
        this.nome = nome;
        this.temperatura = temperatura;
        this.umidade_ar = umidade_ar;
        this.umidade_solo = umidade_solo;
        this.status = status;
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

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public String getUmidade_ar() {
        return umidade_ar;
    }

    public void setUmidade_ar(String umidade_ar) {
        this.umidade_ar = umidade_ar;
    }

    public String getUmidade_solo() {
        return umidade_solo;
    }

    public void setUmidade_solo(String umidade_solo) {
        this.umidade_solo = umidade_solo;
    }
}
