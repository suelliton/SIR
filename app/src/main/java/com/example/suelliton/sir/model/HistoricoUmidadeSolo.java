package com.example.suelliton.sir.model;

import java.util.ArrayList;

/**
 * Created by suelliton on 22/11/2017.
 */

public class HistoricoUmidadeSolo {


        private ArrayList<String> dia;
        private ArrayList<String> hora;
        private ArrayList<String> minuto;

        @Override
        public String toString() {
            return "Historico{" +
                    "dia=" + dia +
                    ", hora=" + hora +
                    ", minuto=" + minuto +
                    ", segundo="  +
                    '}';
        }

        public HistoricoUmidadeSolo() {
        }

        public HistoricoUmidadeSolo(ArrayList<String> dia, ArrayList<String> hora, ArrayList<String> minuto) {
            this.dia = dia;
            this.hora = hora;
            this.minuto = minuto;

        }

        public ArrayList<String> getDia() {
            return dia;
        }

        public void setDia(ArrayList<String> dia) {
            this.dia = dia;
        }

        public ArrayList<String> getHora() {
            return hora;
        }

        public void setHora(ArrayList<String> hora) {
            this.hora = hora;
        }

        public ArrayList<String> getMinuto() {
            return minuto;
        }

        public void setMinuto(ArrayList<String> minuto) {
            this.minuto = minuto;
        }


}
