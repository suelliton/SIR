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
    private DatabaseReference historicoReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
        nodeReference = database.getReference("Node");
       // historicoReference = database.getReference("Historico");

        //createReferences();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Handler handler = new Handler();
        handler.postDelayed(this, 50);
    }

    @Override
    public void run() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
    public void  createReferences(){


        ArrayList<Integer> dia = new ArrayList<>();
        ArrayList<Integer> hora = new ArrayList<>();
        ArrayList<Integer> minuto = new ArrayList<>();
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



        HistoricoTemperatura historicoTemperatura = new HistoricoTemperatura(dia,hora,minuto);
        HistoricoUmidadeAr historicoUmidadeAr = new HistoricoUmidadeAr(dia,hora,minuto);
        HistoricoUmidadeSolo historicoUmidadeSolo = new HistoricoUmidadeSolo(dia,hora,minuto);
        Node node = new Node(1,"Raspberry3",30,30,30,"ligado",historicoTemperatura,historicoUmidadeAr,historicoUmidadeSolo);
        nodeReference.push().setValue(node);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
