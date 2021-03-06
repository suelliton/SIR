package com.example.suelliton.sir;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.suelliton.sir.model.HistoricoTemperatura;
import com.example.suelliton.sir.model.HistoricoUmidadeAr;
import com.example.suelliton.sir.model.HistoricoUmidadeSolo;
import com.example.suelliton.sir.model.Node;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import java.util.ArrayList;
import java.util.List;

import static com.example.suelliton.sir.NodeAdapter.nameClicado;


/**
 * Created by suelliton on 18/11/2017.
 */

public class FragmentGrafico extends Fragment {
    View view;
    DataPoint dataPointTemperatura[];
    DataPoint dataPointUmidaddeAr[];
    DataPoint dataPointUmidadeSolo[];

    public static HistoricoTemperatura historicoTemperatura;
    public static HistoricoUmidadeAr historicoUmidadeAr;
    public static HistoricoUmidadeSolo historicoUmidadeSolo;

    ArrayList<Integer> listaTemperatura ;
    ArrayList<Integer> listaUmidadeAr ;
    ArrayList<Integer> listaUmidadeSolo ;

    GraphView graphTemperatura ;
    GraphView graphUmidadeAr ;
    GraphView graphUmidadeSolo ;

    RadioGroup radioGroup ;
    RadioButton radioButtonDia ;
    RadioButton radioButtonHora ;
    RadioButton radioButtonMinuto ;
    Handler handle;
    public static String keyClicado = "";
    private List<Node> listaNodes;
    int time = 500;
    int op = 3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recycler_grafico,container,false);
        listaNodes = new ArrayList<>();//inicializa lista



        atualizaGrafico();
        setFind();
        setListeners();



        return view;
    }
    public void  setFind(){
        graphTemperatura = (GraphView) view.findViewById(R.id.graph_temperatura);
        graphUmidadeAr = (GraphView) view.findViewById(R.id.graph_umidade_ar);
        graphUmidadeSolo = (GraphView) view.findViewById(R.id.graph_umidade_solo);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        radioButtonDia = (RadioButton) view.findViewById(R.id.radio_dia);
        radioButtonHora= (RadioButton) view.findViewById(R.id.radio_hora);
        radioButtonMinuto = (RadioButton) view.findViewById(R.id.radio_minuto);
        radioButtonMinuto.toggle();//seta o inicio para o minuto ficar selecionado
        listaTemperatura = new ArrayList<>();
        listaUmidadeAr = new ArrayList<>();
        listaUmidadeSolo = new ArrayList<>();
    }
    public void setListeners(){
        //cada botao dia hora e minuto chama a setagrafico com os parametros globais e seta a op que é usada lá
        radioButtonDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                op = 1;
                setaGrafico(historicoTemperatura,historicoUmidadeAr,historicoUmidadeSolo);
            }
        });
        radioButtonHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                op = 2;
                setaGrafico(historicoTemperatura,historicoUmidadeAr,historicoUmidadeSolo);
            }
        });
        radioButtonMinuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                op = 3;
                setaGrafico(historicoTemperatura,historicoUmidadeAr,historicoUmidadeSolo);
            }
        });
    }
    public void atualizaGrafico(){
        handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                setaGrafico(historicoTemperatura,historicoUmidadeAr,historicoUmidadeSolo);
                time = 1000;
                atualizaGrafico();
            }
        }, time);
    }

    public  void setaGrafico(HistoricoTemperatura historicoTemperatura, HistoricoUmidadeAr historicoUmidadeAr, HistoricoUmidadeSolo historicoUmidadeSolo){

        //listaTemperatura.removeAll(listaTemperatura);
        //listaUmidadeAr.removeAll(listaUmidadeAr);
        //listaUmidadeSolo.removeAll(listaUmidadeSolo);

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
        for (int s:listaTemperatura) {
            dataPointTemperatura[cont] = new DataPoint(cont,s);
            cont ++;
        }
        cont = 0;
        for (int s:listaUmidadeAr) {
            dataPointUmidaddeAr[cont] = new DataPoint(cont,s);
            cont++;
        }
        cont = 0;
        for (int s:listaUmidadeSolo) {
            dataPointUmidadeSolo[cont] = new DataPoint(cont,s);
            cont++;
        }


        switch (op){//op é setado pelos cliques nos radio buttons
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


        setColorsSeries(seriesTemperatura,Color.argb(255, 255, 60, 60),Color.argb(70, 204, 119, 119));
        setColorsSeries(seriesUmidadeAr,Color.argb(255, 33,150,243),Color.argb(70, 100,181,246));
        setColorsSeries(seriesUmidadeSolo,Color.argb(255, 63,81,181),Color.argb(70, 92,107,192));



        removeSeriesFromGraph(graphTemperatura,graphUmidadeAr,graphUmidadeSolo);

        setTitlesSeries(graphTemperatura,graphUmidadeAr,graphUmidadeSolo,"Temperatura","Umidade do Ar","Umidade do solo");

        addSeriesToGraph(graphTemperatura,graphUmidadeAr,graphUmidadeSolo,seriesTemperatura,seriesUmidadeAr,seriesUmidadeSolo);
    }
    public void addSeriesToGraph(GraphView graphView1,GraphView graphView2, GraphView graphView3,LineGraphSeries<DataPoint> series1,LineGraphSeries<DataPoint> series2,LineGraphSeries<DataPoint> series3){
        graphView1.addSeries(series1);
        graphView2.addSeries(series2);
        graphView3.addSeries(series3);
    }
    public void removeSeriesFromGraph(GraphView graphView1,GraphView graphView2, GraphView graphView3){
        graphView1.removeAllSeries();
        graphView2.removeAllSeries();
        graphView3.removeAllSeries();
    }
    public void setTitlesSeries(GraphView series1, GraphView series2, GraphView series3, String title1, String title2, String title3){
        series1.setTitle("Temperatura");
        series2.setTitle("Umidade do ar");
        series3.setTitle("Umidade do solo");
    }
    public void setColorsSeries(LineGraphSeries<DataPoint> series, int colorLine,int colorBackground){
        series.setDrawBackground(true);
        series.setColor(colorLine);
        series.setBackgroundColor(colorBackground);
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


    public void setaGraficoSimples(List<Node> lista){
        dataPointTemperatura = new DataPoint[lista.size()];
        dataPointUmidaddeAr = new DataPoint[lista.size()];
        dataPointUmidadeSolo = new DataPoint[lista.size()];
        int cont = 0;
        for(Node n:lista){
            dataPointTemperatura[cont] = new DataPoint(cont, n.getTemperatura());
            dataPointUmidaddeAr[cont] = new DataPoint(cont,n.getUmidade_ar());
            dataPointUmidadeSolo[cont] = new DataPoint(cont,n.getUmidade_solo());
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

        seriesTemperatura.setAnimated(true);
        seriesUmidadeAr.setAnimated(true);
        seriesUmidadeSolo.setAnimated(true);



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



    @Override
    public void onPause() {
        super.onPause();
        handle.removeCallbacksAndMessages(null);
    }


    /* essa parte é interessante pegar a parte de realocação de vetores e colocar no python que escreve no firebase
        childEventNode = nodeReference.addChildEventListener(new ChildEventListener() {
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
}