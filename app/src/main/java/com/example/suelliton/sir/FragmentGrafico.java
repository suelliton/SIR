package com.example.suelliton.sir;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.suelliton.sir.model.Node;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
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
    private ChildEventListener childEventNode;
    DataPoint dataPointTemperatura[];
    DataPoint dataPointUmidaddeAr[];
    DataPoint dataPointUmidadeSolo[];
    private List<Node> listaNodes = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        database = FirebaseDatabase.getInstance();
        nodeReference = database.getReference("Node");
        view = inflater.inflate(R.layout.fragment_recycler_grafico,container,false);


        childEventNode = nodeReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Node node = dataSnapshot.getValue(Node.class);
                if(listaNodes.size() < 24 ){
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
                }

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




        return view;
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


    }


}