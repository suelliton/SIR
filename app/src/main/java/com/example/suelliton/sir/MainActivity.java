package com.example.suelliton.sir;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TableLayout;

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

public class MainActivity extends AppCompatActivity {
    static TabLayout tabLayout;
    ViewPager viewPager;
    private FirebaseDatabase database ;
    private DatabaseReference nodeReference ;
    private ValueEventListener childValueNode;
    private List<Node> listaNodes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();
        nodeReference = database.getReference("Node");
        listaNodes = new ArrayList<>();
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
                    Node node = snapshot.getValue(Node.class);
                    node.setKey(snapshot.getValue(Node.class).getKey());
                    listaNodes.add(node);
                    if(snapshot.getKey().equals(keyClicado)){
                        historicoTemperatura = snapshot.getValue(Node.class).getHistoricoTemperatura();
                        historicoUmidadeAr = snapshot.getValue(Node.class).getHistoricoUmidadeAr();
                        historicoUmidadeSolo = snapshot.getValue(Node.class).getHistoricoUmidadeSolo();
                        //setaGrafico2(historicoTemperatura,historicoUmidadeAr,historicoUmidadeSolo);
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        nodeReference.addValueEventListener(childValueNode);



        if(historicoTemperatura == null){
            historicoTemperatura = new HistoricoTemperatura(new ArrayList<Integer>(),new ArrayList<Integer>(),new ArrayList<Integer>());
            historicoUmidadeAr = new HistoricoUmidadeAr(new ArrayList<Integer>(),new ArrayList<Integer>(),new ArrayList<Integer>());
            historicoUmidadeSolo = new HistoricoUmidadeSolo(new ArrayList<Integer>(),new ArrayList<Integer>(),new ArrayList<Integer>());

        }
    }



}
