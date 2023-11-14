package com.example.nanoformula;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nanoformula.modelo.Carrera;
import com.example.nanoformula.modelo.Escuderia;
import com.example.nanoformula.modelo.raceSchedule.Race;
import com.example.nanoformula.modelo.raceSchedule.RaceSchedule;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CarrerasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarrerasFragment extends Fragment {

    private static final String ARG_CARRERAS = "CARRERAS";
    public static final String CARRERA_SELECCIONADA = "carrera_seleccionada";
    public static final String CIRCUITO_SELECCIONADA = "circuito_seleccionada";


    private RaceSchedule raceSchedule;

    private RecyclerView listaCarrerasView;

    public CarrerasFragment() {
        // Required empty public constructor
    }

    public static CarrerasFragment newInstance(RaceSchedule raceSchedule) {
        CarrerasFragment fragment = new CarrerasFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CARRERAS,raceSchedule);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            raceSchedule = (RaceSchedule) getArguments().get(ARG_CARRERAS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_escuderias, container, false);
        listaCarrerasView = (RecyclerView)root.findViewById(R.id.escuderiasRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        listaCarrerasView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(listaCarrerasView.getContext(), layoutManager.getOrientation());
        listaCarrerasView.addItemDecoration(dividerItemDecoration);
        ListaCarrerasAdapter lcAdapter= new ListaCarrerasAdapter(raceSchedule.getMRData().getRaceTable().getRaces(),
                new ListaCarrerasAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Race carrera) {
                        clickonItem(carrera);
                    }
                });
        listaCarrerasView.setAdapter(lcAdapter);

        return root;
    }
    private void clickonItem(Race carrera) {
        Intent intent=new Intent (CarrerasFragment.this.getContext(), CarreraDetails.class);
        intent.putExtra(CARRERA_SELECCIONADA, carrera);
        intent.putExtra(CIRCUITO_SELECCIONADA, carrera.getCircuit());
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
    }
}
