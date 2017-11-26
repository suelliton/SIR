package com.example.suelliton.sir;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.suelliton.sir.model.HistoricoTemperatura;
import com.example.suelliton.sir.model.HistoricoUmidadeAr;
import com.example.suelliton.sir.model.HistoricoUmidadeSolo;
import com.example.suelliton.sir.model.Node;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.Random;

public class Splash extends AppCompatActivity implements Runnable {
    private FirebaseDatabase database ;
    private DatabaseReference nodeReference ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //uso try catch devido na segunda vez q abrir o app ele buga pois ja tem chamado esse método
        try {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }catch (Exception e){
            e.getCause();
        }
        database = FirebaseDatabase.getInstance();//instancia do banco
        nodeReference = database.getReference("Node");//referencia  do child nó

        //createReferences();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//trava rotação da tela
        Handler handler = new Handler();  //chama o app com delay
        handler.postDelayed(this, 50);
    }

    @Override
    public void run() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
    public void  createReferences(){
        //cria um array pra dia  hora e  dia
        ArrayList<Integer> dia = new ArrayList<>();
        ArrayList<Integer> hora = new ArrayList<>();
        ArrayList<Integer> minuto = new ArrayList<>();
        //gerador de numeros aleatorios
        Random gerador = new Random();
        for(int i=0;i<24;i++){
            dia.add(gerador.nextInt(40));
        }
        for(int i=0;i<60;i++){
            hora.add(gerador.nextInt(40));
        }
        for(int i=0;i<60;i++){
            minuto.add(gerador.nextInt(40));
        }


        //cria objetos historico de cada tipo devariavel
        HistoricoTemperatura historicoTemperatura = new HistoricoTemperatura(dia,hora,minuto);
        HistoricoUmidadeAr historicoUmidadeAr = new HistoricoUmidadeAr(dia,hora,minuto);
        HistoricoUmidadeSolo historicoUmidadeSolo = new HistoricoUmidadeSolo(dia,hora,minuto);
        //cria um objeto de node
        Node node = new Node(1,"Raspberry3",30,30,30,"ligado",historicoTemperatura,historicoUmidadeAr,historicoUmidadeSolo);
        nodeReference.push().setValue(node);//coloca no firebase o node

    }


}
