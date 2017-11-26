package com.example.suelliton.sir;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.List;

import static com.example.suelliton.sir.FragmentGrafico.keyClicado;


/**
 * Created by suelliton on 18/11/17.
 */

public class NodeAdapter extends RecyclerView.Adapter {
    Context context;
    private List<Node> listaNodes;
    private FirebaseDatabase database ;
    private DatabaseReference nodeReference ;

    public NodeAdapter(Context context, List<Node> lista){
        this.context = context;
        this.listaNodes = lista;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //infla layout node_inflate
        View view = LayoutInflater.from(context).inflate(R.layout.node_inflate,parent,false);
        NodeViewHolder holder = new NodeViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        database = FirebaseDatabase.getInstance();//pega uma instancia do banco
        nodeReference = database.getReference("Node");//pega referencia node
        final NodeViewHolder nodeHolder = (NodeViewHolder) holder;//faz cast do holder para nodeViewHolder
        //seta os valores nas views pegando dos elementos nodes da lista que recebeu no construtor
        nodeHolder.node_temperatura.setText(listaNodes.get(position).getTemperatura()+"°");
        nodeHolder.node_umidade_ar.setText(listaNodes.get(position).getUmidade_ar()+"%");
        nodeHolder.node_umidade_solo.setText(listaNodes.get(position).getUmidade_solo()+"%");
        nodeHolder.node_nome.setText(listaNodes.get(position).getNome());
        //listener de status para mudar a cor do dispositivo
        nodeReference.child(listaNodes.get(position).getKey()).child("status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String status = (String) dataSnapshot.getValue();
                    if(status != null) {
                        if (status.equals("ligado")) {
                            nodeHolder.node_status.setChecked(true);
                            //nodeHolder.row.setBackgroundColor(Color.parseColor("#0df009"));
                            nodeHolder.row.setBackgroundColor(Color.argb(50,0,255,0));
                        } else {
                            nodeHolder.node_status.setChecked(false);
                            nodeHolder.row.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        }
                    }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        //verifica o click no botao de status e trata e seta no firebase se esta ativado ou não
        nodeHolder.node_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nodeHolder.node_status.isChecked()){
                    nodeReference.child(listaNodes.get(position).getKey()).child("status").setValue("ligado");
                }else{
                    nodeReference.child(listaNodes.get(position).getKey()).child("status").setValue("desligado");
                }
            }
        });
        //verifica o click na row_clicavel e seta a keyClicado  e chama o fragment de graficos
        final String clicado = listaNodes.get(position).getKey();
        nodeHolder.row_clicavel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyClicado = clicado;
                Log.i("clicado",keyClicado);
                MainActivity.tabLayout.getTabAt(1).select();
            }
        });
        //replica a função acima para chamar o grafico só que clicando no nome do dispositivo
        nodeHolder.node_nome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyClicado = clicado;
                Log.i("clicado",keyClicado);
                MainActivity.tabLayout.getTabAt(1).select();
            }
        });
    }
    @Override
    public int getItemCount() {
        return listaNodes.size();
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
