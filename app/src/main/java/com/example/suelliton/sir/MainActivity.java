package com.example.suelliton.sir;

import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.suelliton.sir.model.HistoricoTemperatura;
import com.example.suelliton.sir.model.HistoricoUmidadeAr;
import com.example.suelliton.sir.model.HistoricoUmidadeSolo;
import com.example.suelliton.sir.model.Node;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import static com.example.suelliton.sir.FragmentGrafico.historicoTemperatura;
import static com.example.suelliton.sir.FragmentGrafico.historicoUmidadeAr;
import static com.example.suelliton.sir.FragmentGrafico.historicoUmidadeSolo;
import static com.example.suelliton.sir.FragmentGrafico.keyClicado;
import static com.example.suelliton.sir.NodeAdapter.nameClicado;

public class MainActivity extends AppCompatActivity {
    static TabLayout tabLayout;
    ViewPager viewPager;
    private FirebaseDatabase database ;
    private DatabaseReference nodeReference ;
    private ValueEventListener childValueNode;
    private List<Node> listaNodes;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();//banco
        nodeReference = database.getReference("Node");//referencia
        listaNodes = new ArrayList<>();//inicializa lista

        viewPager = (ViewPager) findViewById(R.id.pager);
        PagerAdapter pagerAdapter = new FixedTabsPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        break;
                    case 1:
                        getDadosFirebase();
                        TextView tx_nameClicado = (TextView) findViewById(R.id.nameClicado);
                        tx_nameClicado.setText(nameClicado);
                        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayoutPai);
                        if(nameClicado.equals("")){
                            linearLayout.setVisibility(View.INVISIBLE);
                        }else{
                            linearLayout.setVisibility(View.VISIBLE);
                        }
                        break;
                    case 2:
                        break;
                    default:
                        return;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        getDadosFirebase();
    }

    public void getDadosFirebase(){
        childValueNode = nodeReference.child("Node/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    Node node = snapshot.getValue(Node.class);//pega o objeto do firebase
                    node.setKey(snapshot.getValue(Node.class).getKey());//seta a chave do objeto localmente
                    listaNodes.add(node);//adiciona na lista que vai para o adapter
                    if(snapshot.getKey().equals(keyClicado)){//verifica se o node é o clicado
                        historicoTemperatura = snapshot.getValue(Node.class).getHistoricoTemperatura();
                        historicoUmidadeAr = snapshot.getValue(Node.class).getHistoricoUmidadeAr();
                        historicoUmidadeSolo = snapshot.getValue(Node.class).getHistoricoUmidadeSolo();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        nodeReference.addValueEventListener(childValueNode);
        if(historicoTemperatura == null){//verifico se o objeto node foi instanciado se nao instancio um vazio
            historicoTemperatura = new HistoricoTemperatura(new ArrayList<Integer>(),new ArrayList<Integer>(),new ArrayList<Integer>());
            historicoUmidadeAr = new HistoricoUmidadeAr(new ArrayList<Integer>(),new ArrayList<Integer>(),new ArrayList<Integer>());
            historicoUmidadeSolo = new HistoricoUmidadeSolo(new ArrayList<Integer>(),new ArrayList<Integer>(),new ArrayList<Integer>());
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
