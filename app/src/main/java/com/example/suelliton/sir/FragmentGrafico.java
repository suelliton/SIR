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
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

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
    DataPoint dataPoint[];
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
                if(listaNodes.size() < 4 ){
                    Node node = dataSnapshot.getValue(Node.class);
                    listaNodes.add(node);
                }else{
                    setaGrafico(listaNodes);
                    listaNodes.removeAll(listaNodes);
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
        dataPoint = new DataPoint[lista.size()];
        int cont = 0;
        for(Node n:lista){
            dataPoint[cont] = new DataPoint(cont, Double.parseDouble(n.getTemperatura()));
            cont ++;
        }
        GraphView graph = (GraphView) view.findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoint);
        graph.addSeries(series);
    }


}
