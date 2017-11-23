package com.example.suelliton.sir;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;

import com.example.suelliton.sir.model.Node;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.suelliton.sir.FragmentGrafico.historicoTemperatura;
import static com.example.suelliton.sir.FragmentGrafico.historicoUmidadeAr;
import static com.example.suelliton.sir.FragmentGrafico.historicoUmidadeSolo;
import static com.example.suelliton.sir.FragmentGrafico.keyClicado;

public class MainActivity extends AppCompatActivity {
    static TabLayout tabLayout;

    private FirebaseDatabase database ;
    private DatabaseReference nodeReference ;
    private ValueEventListener childValueNode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();
        nodeReference = database.getReference("Node");

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        PagerAdapter pagerAdapter = new FixedTabsPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position){
                    case 0:

                        break;
                    case 1:
                        preencheGraficos();
                        break;
                    case 2:

                        break;
                    default:
                        return;
                }
            }

            @Override
            public void onPageSelected(int position) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });




    }

    public void preencheGraficos(){
        childValueNode = nodeReference.child("Node/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> dia = new ArrayList<>();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()) {

                    Log.i("clicado",snapshot.getKey().toString()+"");
                    if (snapshot.getKey().equals(keyClicado)) {

                        historicoTemperatura = snapshot.getValue(Node.class).getHistoricoTemperatura();
                        historicoUmidadeAr = snapshot.getValue(Node.class).getHistoricoUmidadeAr();
                        historicoUmidadeSolo = snapshot.getValue(Node.class).getHistoricoUmidadeSolo();

                        Log.i("historico", "temperatura:" + historicoTemperatura.toString() + "umidade" + historicoUmidadeAr.toString() + "solo" + historicoUmidadeSolo.toString());
                        //setaGrafico2(historicoTemperatura, historicoUmidadeAr, historicoUmidadeSolo);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        nodeReference.addValueEventListener(childValueNode);
    }

}
