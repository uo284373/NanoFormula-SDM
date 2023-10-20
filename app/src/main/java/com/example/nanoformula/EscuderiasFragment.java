package com.example.nanoformula;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nanoformula.modelo.Escuderia;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EscuderiasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EscuderiasFragment extends Fragment {

    private static final String ARG_ESCUDERIAS = "ESCUDERIAS";
    private List<Escuderia> escuderias;

    private RecyclerView listaEscuderiasView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static EscuderiasFragment newInstance(List<Escuderia> escuderias) {
        EscuderiasFragment fragment = new EscuderiasFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_ESCUDERIAS,new ArrayList<>(escuderias));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            escuderias = (List<Escuderia>) getArguments().get(ARG_ESCUDERIAS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_escuderias, container, false);
        listaEscuderiasView = (RecyclerView)root.findViewById(R.id.escuderiasRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        listaEscuderiasView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(listaEscuderiasView.getContext(), layoutManager.getOrientation());
        listaEscuderiasView.addItemDecoration(dividerItemDecoration);
        ListaEscuderiasAdapter leAdapter= new ListaEscuderiasAdapter(escuderias,
                new ListaEscuderiasAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Escuderia escuderia) {
                        clickonItem(escuderia);
                    }
                });
        listaEscuderiasView.setAdapter(leAdapter);

        return root;
    }

    private void clickonItem(Escuderia escuderia) {
    }
}