package com.example.suelliton.sir.model;

import java.util.ArrayList;

/**
 * Created by suelliton on 22/11/2017.
 */

public class HistoricoUmidadeAr {

    private ArrayList<Integer> dia;
    private ArrayList<Integer> hora;
    private ArrayList<Integer> minuto;

    @Override
    public String toString() {
        return "Historico{" +
                "dia=" + dia +
                ", hora=" + hora +
                ", minuto=" + minuto +
                ", segundo=" +
                '}';
    }

    public HistoricoUmidadeAr() {
    }

    public HistoricoUmidadeAr(ArrayList<Integer> dia, ArrayList<Integer> hora, ArrayList<Integer> minuto) {
        this.dia = dia;
        this.hora = hora;
        this.minuto = minuto;

    }

    public ArrayList<Integer> getDia() {
        return dia;
    }

    public void setDia(ArrayList<Integer> dia) {
        this.dia = dia;
    }

    public ArrayList<Integer> getHora() {
        return hora;
    }

    public void setHora(ArrayList<Integer> hora) {
        this.hora = hora;
    }

    public ArrayList<Integer> getMinuto() {
        return minuto;
    }

    public void setMinuto(ArrayList<Integer> minuto) {
        this.minuto = minuto;
    }
}
