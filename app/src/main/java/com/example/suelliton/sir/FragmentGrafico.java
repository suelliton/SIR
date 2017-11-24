package com.example.suelliton.sir;

import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.suelliton.sir.model.HistoricoTemperatura;
import com.example.suelliton.sir.model.HistoricoUmidadeAr;
import com.example.suelliton.sir.model.HistoricoUmidadeSolo;
import com.example.suelliton.sir.model.Node;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;


/**
 * Created by suelliton on 18/11/2017.
 */

public class FragmentGrafico extends Fragment {
    View view;
    private FirebaseDatabase database ;
    private DatabaseReference nodeReference ;
    private ValueEventListener childValueNode;
    DataPoint dataPointTemperatura[];
    DataPoint dataPointUmidaddeAr[];
    DataPoint dataPointUmidadeSolo[];

    public static HistoricoTemperatura historicoTemperatura;
    public static HistoricoUmidadeAr historicoUmidadeAr;
    public static HistoricoUmidadeSolo historicoUmidadeSolo;


    RadioGroup radioGroup ;
    RadioButton radioButtonDia ;
    RadioButton radioButtonHora ;
    RadioButton radioButtonMinuto ;

    public static String keyClicado= "-Kza1ZqNHZY6Mk7nQ4jW";

    int time = 5000;
    int op = 3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        database = FirebaseDatabase.getInstance();
        nodeReference = database.getReference("Node");
        view = inflater.inflate(R.layout.fragment_recycler_grafico,container,false);
        radioButtonMinuto = (RadioButton) view.findViewById(R.id.radio_minuto);
        radioButtonMinuto.toggle();

        /*childEventNode = nodeReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Node node = dataSnapshot.getValue(Node.class);
               *//* if(listaNodes.size() < 24 ){ metodo em tempo real
                    listaNodes.add(node);
                    setaGrafico(listaNodes);
                }else{

                    setaGrafico(listaNodes);
                    for(int i =0;i<23;i++){
                        listaNodes.set(i,listaNodes.get(i+1));
                    }
                    listaNodes.set(23,node);
                    setaGrafico(listaNodes);
                    // listaNodes.removeAll(listaNodes);
                }*//*

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        nodeReference.addChildEventListener(childEventNode);
*/
    atualizaGrafico();



        radioButtonDia = (RadioButton) view.findViewById(R.id.radio_dia);
        radioButtonHora= (RadioButton) view.findViewById(R.id.radio_hora);
        radioButtonMinuto = (RadioButton) view.findViewById(R.id.radio_minuto);
        radioButtonDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                op = 1;
                setaGrafico2(historicoTemperatura,historicoUmidadeAr,historicoUmidadeSolo);
            }
        });
        radioButtonHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                op = 2;
                setaGrafico2(historicoTemperatura,historicoUmidadeAr,historicoUmidadeSolo);
            }
        });
        radioButtonMinuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                op = 3;
                setaGrafico2(historicoTemperatura,historicoUmidadeAr,historicoUmidadeSolo);
            }
        });








        return view;
    }

    public void atualizaGrafico(){
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {

                setaGrafico2(historicoTemperatura,historicoUmidadeAr,historicoUmidadeSolo);
                time = 500;
                atualizaGrafico();
            }
        }, time);

    }

    @Override
    public void onStart() {
        super.onStart();




    }

    @Override
    public void onResume() {
        super.onResume();









    }

    public  void setaGrafico2(HistoricoTemperatura historicoTemperatura, HistoricoUmidadeAr historicoUmidadeAr, HistoricoUmidadeSolo historicoUmidadeSolo){

       radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
       radioButtonDia = (RadioButton) view.findViewById(R.id.radio_dia);
       radioButtonHora = (RadioButton) view.findViewById(R.id.radio_hora);
       radioButtonMinuto = (RadioButton) view.findViewById(R.id.radio_minuto);

        ArrayList<String> listaTemperatura = new ArrayList<>();
        ArrayList<String> listaUmidadeAr = new ArrayList<>();
        ArrayList<String> listaUmidadeSolo = new ArrayList<>();

       switch (radioGroup.getCheckedRadioButtonId()){
           case R.id.radio_dia:
                    listaTemperatura =  historicoTemperatura.getDia();
                    listaUmidadeAr = historicoUmidadeAr.getDia();
                    listaUmidadeSolo = historicoUmidadeSolo.getDia();
               break;
           case R.id.radio_hora:

               listaTemperatura =  historicoTemperatura.getHora();
               listaUmidadeAr = historicoUmidadeAr.getHora();
               listaUmidadeSolo = historicoUmidadeSolo.getHora();
               break;
           case R.id.radio_minuto:

               listaTemperatura =  historicoTemperatura.getMinuto();
               listaUmidadeAr = historicoUmidadeAr.getMinuto();
               listaUmidadeSolo = historicoUmidadeSolo.getMinuto();
               break;
           default:

       }


        dataPointTemperatura = new DataPoint[listaTemperatura.size()];
        dataPointUmidaddeAr = new DataPoint[listaUmidadeAr.size()];
        dataPointUmidadeSolo = new DataPoint[listaUmidadeSolo.size()];




        int cont = 0;
        for (String s:listaTemperatura) {
            dataPointTemperatura[cont] = new DataPoint(cont,Double.parseDouble(s));
            cont++;
        }
        cont = 0;
        for (String s:listaUmidadeAr) {
            dataPointUmidaddeAr[cont] = new DataPoint(cont,Double.parseDouble(s));
            cont++;
        }
        cont = 0;
        for (String s:listaUmidadeSolo) {
            dataPointUmidadeSolo[cont] = new DataPoint(cont,Double.parseDouble(s));
            cont++;
        }




        GraphView graphTemperatura = (GraphView) view.findViewById(R.id.graph_temperatura);
        GraphView graphUmidadeAr = (GraphView) view.findViewById(R.id.graph_umidade_ar);
        GraphView graphUmidadeSolo = (GraphView) view.findViewById(R.id.graph_umidade_solo);


        switch (op){
            case 1:

                setTamanhoGrafico(graphTemperatura,0,24,0,50);
                setTamanhoGrafico(graphUmidadeAr,0,24,0,100);
                setTamanhoGrafico(graphUmidadeSolo,0,24,0,100);
                break;
            case 2:
                setTamanhoGrafico(graphTemperatura,0,60,0,50);
                setTamanhoGrafico(graphUmidadeAr,0,60,0,100);
                setTamanhoGrafico(graphUmidadeSolo,0,60,0,100);
                break;
            case 3:
                setTamanhoGrafico(graphTemperatura,0,60,0,50);
                setTamanhoGrafico(graphUmidadeAr,0,60,0,100);
                setTamanhoGrafico(graphUmidadeSolo,0,60,0,100);
                break;
            default:

        }



        LineGraphSeries<DataPoint> seriesTemperatura = new LineGraphSeries<>(dataPointTemperatura);
        LineGraphSeries<DataPoint> seriesUmidadeAr = new LineGraphSeries<>(dataPointUmidaddeAr);
        LineGraphSeries<DataPoint> seriesUmidadeSolo = new LineGraphSeries<>(dataPointUmidadeSolo);

        graphTemperatura.removeAllSeries();
        graphUmidadeAr.removeAllSeries();
        graphUmidadeSolo.removeAllSeries();


        graphTemperatura.setTitle("Temperatura");
        graphUmidadeAr.setTitle("Umidade do ar");
        graphUmidadeSolo.setTitle("Umidade do solo");

        graphTemperatura.addSeries(seriesTemperatura);
        graphUmidadeAr.addSeries(seriesUmidadeAr);
        graphUmidadeSolo.addSeries(seriesUmidadeSolo);
    }


    public void setaGrafico(List<Node> lista){
        dataPointTemperatura = new DataPoint[lista.size()];
        dataPointUmidaddeAr = new DataPoint[lista.size()];
        dataPointUmidadeSolo = new DataPoint[lista.size()];
        int cont = 0;
        for(Node n:lista){
            dataPointTemperatura[cont] = new DataPoint(cont, Double.parseDouble(n.getTemperatura()));
            dataPointUmidaddeAr[cont] = new DataPoint(cont,Double.parseDouble(n.getUmidade_ar()));
            dataPointUmidadeSolo[cont] = new DataPoint(cont,Double.parseDouble(n.getUmidade_solo()));
            cont ++;
        }
        GraphView graphTemperatura = (GraphView) view.findViewById(R.id.graph_temperatura);
        GraphView graphUmidadeAr = (GraphView) view.findViewById(R.id.graph_umidade_ar);
        GraphView graphUmidadeSolo = (GraphView) view.findViewById(R.id.graph_umidade_solo);

        setTamanhoGrafico(graphTemperatura,0,24,0,50);
        setTamanhoGrafico(graphUmidadeAr,0,24,0,100);
        setTamanhoGrafico(graphUmidadeSolo,0,24,0,100);


        LineGraphSeries<DataPoint> seriesTemperatura = new LineGraphSeries<>(dataPointTemperatura);
        LineGraphSeries<DataPoint> seriesUmidadeAr = new LineGraphSeries<>(dataPointUmidaddeAr);
        LineGraphSeries<DataPoint> seriesUmidadeSolo = new LineGraphSeries<>(dataPointUmidadeSolo);

      /*  seriesTemperatura.setAnimated(true);
        seriesUmidadeAr.setAnimated(true);
        seriesUmidadeSolo.setAnimated(true);*/

        graphTemperatura.removeAllSeries();
        graphUmidadeAr.removeAllSeries();
        graphUmidadeSolo.removeAllSeries();

        graphTemperatura.setTitle("Temperatura");
        graphUmidadeAr.setTitle("Umidade do ar");
        graphUmidadeSolo.setTitle("Umidade do solo");

        graphTemperatura.addSeries(seriesTemperatura);
        graphUmidadeAr.addSeries(seriesUmidadeAr);
        graphUmidadeSolo.addSeries(seriesUmidadeSolo);
    }

    public void setTamanhoGrafico(GraphView graphView,int Xmin,int Xmax,int Ymin,int Ymax){
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(Ymin);
        graphView.getViewport().setMaxY(Ymax);

        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(Xmin);
        graphView.getViewport().setMaxX(Xmax);

        graphView.getGridLabelRenderer().setLabelFormatter(new StaticLabelsFormatter(graphView));
        graphView.getGridLabelRenderer().setNumHorizontalLabels(12); // only 4 because of the space


    }

    @Override
    public void onPause() {
        super.onPause();
       // nodeReference.removeEventListener(childValueNode);
    }
}