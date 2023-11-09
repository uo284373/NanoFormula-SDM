package com.example.nanoformula;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nanoformula.modelo.Piloto;
import com.example.nanoformula.modelo.driversStandings.DriverStanding;
import com.example.nanoformula.modelo.driversStandings.Standings;

import java.util.ArrayList;
import java.util.List;


public class PilotosFragment extends Fragment {


    private static final String ARG_PILOTOS = "pilotos";
    public static final String PILOTO_SELECCIONADO = "piloto_seleccionado";
    public static final String EQUIPO_PILOTO_SELECCIONADO = "equipo_piloto_seleccionado";

    private Standings standings;
    private RecyclerView listaPilotosView;



    public static PilotosFragment newInstance(Standings standings) {
        PilotosFragment fragment = new PilotosFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PILOTOS,standings);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            standings = (Standings) getArguments().get(ARG_PILOTOS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_pilotos, container, false);
        listaPilotosView = (RecyclerView)root.findViewById(R.id.pilotosRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        listaPilotosView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(listaPilotosView.getContext(), layoutManager.getOrientation());
        listaPilotosView.addItemDecoration(dividerItemDecoration);
        ListaPilotosAdapter lpAdapter= new ListaPilotosAdapter(standings.getMRData().getStandingsTable().getStandingsLists().get(0).getDriverStandings(),
                new ListaPilotosAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(DriverStanding piloto) {
                        clickonItem(piloto);
                    }
                });
        listaPilotosView.setAdapter(lpAdapter);

        return root;
    }

    private void clickonItem(DriverStanding piloto) {
        Intent intent=new Intent (PilotosFragment.this.getContext(), PilotoDetails.class);
        intent.putExtra(PILOTO_SELECCIONADO, piloto.getDriver());
        intent.putExtra(EQUIPO_PILOTO_SELECCIONADO, piloto);

        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
    }
}