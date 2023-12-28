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

import com.example.nanoformula.modelo.constructorsStandings.ConstructorStanding;
import com.example.nanoformula.modelo.constructorsStandings.StandingsEscuderias;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EscuderiasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EscuderiasFragment extends Fragment {

    private static final String ARG_ESCUDERIAS = "ESCUDERIAS";
    public static final String ESCUDERIA_SELECCIONADA = "escuderia_seleccionado";
    public static final String TEMPORADA = "temporada";


    private StandingsEscuderias standings;

    private String season;

    private RecyclerView listaEscuderiasView;


    public static EscuderiasFragment newInstance(StandingsEscuderias standings, String temporada) {
        EscuderiasFragment fragment = new EscuderiasFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_ESCUDERIAS,standings);
        args.putString(TEMPORADA,temporada);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            standings = (StandingsEscuderias) getArguments().get(ARG_ESCUDERIAS);
            season = (String) getArguments().get(TEMPORADA);
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
        List<ConstructorStanding> standingList = standings.getMRData().getStandingsTable().getStandingsLists().size() >= 1 ? standings.getMRData().getStandingsTable().getStandingsLists().get(0).getConstructorStandings() : new ArrayList<>();
        ListaEscuderiasAdapter leAdapter= new ListaEscuderiasAdapter(standingList,
                new ListaEscuderiasAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(ConstructorStanding escuderia) {
                        clickonItem(escuderia);
                    }
                });
        listaEscuderiasView.setAdapter(leAdapter);

        return root;
    }

    private void clickonItem(ConstructorStanding escuderia) {
        Intent intent=new Intent (EscuderiasFragment.this.getContext(), EscuderiaDetails.class);
        intent.putExtra(ESCUDERIA_SELECCIONADA, escuderia);
        intent.putExtra(TEMPORADA, season);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
    }
}