package com.example.suelliton.sir;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;


import com.example.suelliton.sir.model.Node;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by suelliton on 14/10/2017.
 */

public class FragmentRecycler extends Fragment {
    View view;
    static int POSITION_CLICADO ;
    private FirebaseDatabase database ;
    private DatabaseReference nodeReference ;
    private ChildEventListener childEventNode;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        database = FirebaseDatabase.getInstance();
        nodeReference = database.getReference("Node");

        view = inflater.inflate(R.layout.fragment_recycler_inflate,container,false);

        final RecyclerView recyclerView =  view.findViewById(R.id.recycler);
        final List<Node> listaNodes = new ArrayList<>();

        final NodeAdapter nodeAdapter = new NodeAdapter(view.getContext(),listaNodes);
        recyclerView.setAdapter(nodeAdapter);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layout);


        childEventNode = nodeReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Node node =  dataSnapshot.getValue(Node.class);
                Log.i("key",dataSnapshot.getKey());
                node.setKey(dataSnapshot.getKey());

                listaNodes.add(node);


                nodeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Node modificado = dataSnapshot.getValue(Node.class);
               /* for(int i = 0 ; i< listaNodes.size(); i++){
                  if(listaNodes.get(i).getNome().equals(modificado.getNome())){
                    listaNodes.set(i,modificado);
                    nodeAdapter.notifyDataSetChanged();
                  }
                }*/


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

/*
      recyclerView.addOnItemTouchListener(new MeuRecyclerViewClickListener(recyclerView.getContext(), recyclerView, new MeuRecyclerViewClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                MainActivity.tabLayout.getTabAt(1).select();

            }

            @Override
            public void onItemLongClick(View view, final int position) {


            }
        }));*/







        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


}
