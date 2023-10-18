package com.example.nanoformula;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nanoformula.modelo.Piloto;

import java.util.ArrayList;
import java.util.List;


public class PilotosFragment extends Fragment {


    private static final String ARG_PILOTOS = "pilotos";

    private List<Piloto> pilotos;
    private RecyclerView listaPilotosView;



    public static PilotosFragment newInstance(List<Piloto> pilotos) {
        PilotosFragment fragment = new PilotosFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PILOTOS,new ArrayList<>(pilotos));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pilotos = (List<Piloto>) getArguments().get(ARG_PILOTOS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_pilotos, container, false);
        listaPilotosView = (RecyclerView)root.findViewById(R.id.pilotosRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        listaPilotosView.setLayoutManager(layoutManager);

        ListaPilotosAdapter lpAdapter= new ListaPilotosAdapter(pilotos,
                new ListaPilotosAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Piloto piloto) {
                        clickonItem(piloto);
                    }
                });
        listaPilotosView.setAdapter(lpAdapter);
        return root;
    }

    private void clickonItem(Piloto piloto) {
    }
}