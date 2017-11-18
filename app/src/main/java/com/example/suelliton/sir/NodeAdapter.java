package com.example.suelliton.sir;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.suelliton.sir.model.Node;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by suelliton on 18/11/17.
 */

public class NodeAdapter extends RecyclerView.Adapter {
    Context context;
    List<Node> listaNodes;
    private FirebaseDatabase database ;
    private DatabaseReference nodeReference ;

    public NodeAdapter(Context context, List<Node> listaNodes){
        this.context = context;
        this.listaNodes = listaNodes;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.node_inflate,parent,false);
        NodeViewHolder holder = new NodeViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        database = FirebaseDatabase.getInstance();
        nodeReference = database.getReference("Node");

        final NodeViewHolder nodeHolder = (NodeViewHolder) holder;
        nodeHolder.node_temperatura.setText(listaNodes.get(position).getTemperatura());
        nodeHolder.node_umidade_ar.setText(listaNodes.get(position).getUmidade_ar());
        nodeHolder.node_umidade_solo.setText(listaNodes.get(position).getUmidade_solo());
        nodeHolder.node_nome.setText(listaNodes.get(position).getNome());


        nodeHolder.node_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nodeHolder.node_status.isChecked()){
                    nodeReference.child("Raspberry").child("status").setValue("ligado");
                }else{
                    nodeReference.child("Raspberry").child("status").setValue("desligado");
                }
            }
        });

        nodeHolder.row_clicavel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.tabLayout.getTabAt(1).select();
            }
        });



        /*nodeHolder.node_status.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View view) {
                nodeReference.child("Raspberry").child("status").setValue("ligado");
            }

            @Override
            public void onViewDetachedFromWindow(View view) {
                nodeReference.child("Raspberry").child("status").setValue("desligado");
            }
        });*/


        Log.i("status",listaNodes.get(position).getStatus());
        if(listaNodes.get(position).getStatus().equals("ligado")){
            nodeHolder.row.setBackgroundColor(Color.parseColor("#0df009"));

        }else{
            nodeHolder.row.setBackgroundColor(Color.parseColor("#ce93d8"));

        }


    }

    @Override
    public int getItemCount() {
        return listaNodes.size()-1;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    public class NodeViewHolder extends RecyclerView.ViewHolder{
        final TextView node_temperatura ;
        final TextView node_umidade_ar ;
        final TextView node_umidade_solo ;
        final TextView node_nome;
        final Switch node_status;
        final LinearLayout row;
        final LinearLayout row_clicavel;
        public NodeViewHolder(View view){
            super(view);
            node_temperatura = view.findViewById(R.id.text_temperatura);
            node_umidade_ar = view.findViewById(R.id.text_umidade_ar);
            node_umidade_solo = view.findViewById(R.id.text_umidade_solo);
            node_nome = view.findViewById(R.id.text_nome);
            node_status = view.findViewById(R.id.switch_status);
            row = view.findViewById(R.id.row);
            row_clicavel = view.findViewById(R.id.row_clicavel);
        }

    }


}
