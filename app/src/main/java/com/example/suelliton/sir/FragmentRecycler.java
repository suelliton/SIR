package com.example.suelliton.sir;

import android.renderscript.Sampler;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by suelliton on 14/10/2017.
 */

public class FragmentRecycler extends Fragment {
    View view;
    private NodeAdapter nodeAdapter;
    private FirebaseDatabase database ;
    private List<Node> listaNodes;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        database = FirebaseDatabase.getInstance();
        view = inflater.inflate(R.layout.fragment_recycler_inflate,container,false);
        recyclerView =  view.findViewById(R.id.recycler);
        listaNodes = new ArrayList<>();

        nodeAdapter = new NodeAdapter(view.getContext(),listaNodes);
        recyclerView.setAdapter(nodeAdapter);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layout);

        database.getReference().child("Node").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaNodes.removeAll(listaNodes);
                for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    Node node = snapshot.getValue(Node.class);
                    node.setKey(snapshot.getKey());
                    Log.i("nodes",node.getKey()+"");
                   listaNodes.add(node);
                }
                nodeAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return view;
    }

}
