package com.example.suelliton.sir;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.suelliton.sir.model.Node;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by suelliton on 26/11/2017.
 */

public class FragmentGastos extends Fragment {
    View view;

    private FirebaseDatabase database ;
    private List<Node> listaNodes;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recycler_gasto,container,false);
        database = FirebaseDatabase.getInstance();
        listaNodes = new ArrayList<>();



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        database.getReference().child("Node").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaNodes.removeAll(listaNodes);
                for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    Node node = snapshot.getValue(Node.class);
                    node.setKey(snapshot.getKey());
                    listaNodes.add(node);
                }
                calculaGasto();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public void calculaGasto(){
        int gastoAgua = 0;
        double gastoKwh = 0 ;
        for (Node node: listaNodes) {
            gastoAgua = gastoAgua + node.getVazao()* (node.getTempoAtivo()/60000)/60;
            gastoKwh = gastoKwh + (node.getConsumoWatt()/1000) * (node.getTempoAtivo()/60000)/60;
        }
        TextView textAgua = (TextView) view.findViewById(R.id.text_agua);
        textAgua.setText(gastoAgua+" Litros");
        TextView textKwh = (TextView) view.findViewById(R.id.text_kwh);
        DecimalFormat precision = new DecimalFormat("0.00");
        textKwh.setText(precision.format(gastoKwh)+"  Kwh");
        TextView textFinanceiro = (TextView) view.findViewById(R.id.text_financeiro);
        textFinanceiro.setText(precision.format(gastoKwh*0.38) + " Reais");
    }
}

