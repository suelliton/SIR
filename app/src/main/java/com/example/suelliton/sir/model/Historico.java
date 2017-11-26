package com.example.suelliton.sir.model;

import java.util.ArrayList;

/**
 * Created by suelliton on 26/11/2017.
 */

public abstract class Historico {

    private ArrayList<Integer> dia;
    private ArrayList<Integer> hora;
    private ArrayList<Integer> minuto;

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
