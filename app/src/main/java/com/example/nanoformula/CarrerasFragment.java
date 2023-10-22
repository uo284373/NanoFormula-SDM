package com.example.nanoformula;

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

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CarrerasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarrerasFragment extends Fragment {

    private static final String ARG_CARRERAS = "CARRERAS";

    private List<Carrera> carreras;

    private RecyclerView listaCarrerasView;

    public CarrerasFragment() {
        // Required empty public constructor
    }

    public static CarrerasFragment newInstance(List<Carrera> carreras) {
        CarrerasFragment fragment = new CarrerasFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_CARRERAS,new ArrayList<>(carreras));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            carreras = (List<Carrera>) getArguments().get(ARG_CARRERAS);
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
        ListaCarrerasAdapter lcAdapter= new ListaCarrerasAdapter(carreras,
                new ListaCarrerasAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Carrera carrera) {
                        clickonItem(carrera);
                    }
                });
        listaCarrerasView.setAdapter(lcAdapter);

        return root;
    }
    private void clickonItem(Carrera carrera) {
    }
}
