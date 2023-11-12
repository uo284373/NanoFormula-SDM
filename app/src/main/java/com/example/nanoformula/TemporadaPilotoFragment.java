package com.example.nanoformula;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nanoformula.modelo.driversStandings.Constructor;
import com.example.nanoformula.modelo.driversStandings.DriverStanding;

import java.util.ArrayList;


public class TemporadaPilotoFragment extends Fragment {


    private static final String DRIVER_STANDING = "driver_standing";
    private static final String DRIVER_TEAM = "driver_team";

    private DriverStanding driverStanding;
    private ArrayList<Constructor> constructors;

    TextView posPiloto;
    TextView escuderiaPiloto;
    TextView victoriasPiloto;
    TextView puntosPiloto;

    public TemporadaPilotoFragment() {
        // Required empty public constructor
    }


    public static TemporadaPilotoFragment newInstance(DriverStanding param1, ArrayList<Constructor> param2) {
        TemporadaPilotoFragment fragment = new TemporadaPilotoFragment();
        Bundle args = new Bundle();
        args.putParcelable(DRIVER_STANDING, param1);
        args.putParcelableArrayList(DRIVER_TEAM, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            driverStanding = getArguments().getParcelable(DRIVER_STANDING);
            constructors = getArguments().getParcelableArrayList(DRIVER_TEAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_temporada_piloto, container, false);
        posPiloto = root.findViewById(R.id.txPosicionPiloto);
        escuderiaPiloto = root.findViewById(R.id.txEscuderiaPilotoTemporada);
        victoriasPiloto = root.findViewById(R.id.txVictoriasPilotoTemporada);
        puntosPiloto = root.findViewById(R.id.txPuntosPilotoTemporada);

        posPiloto.setText(driverStanding.getPosition());
        escuderiaPiloto.setText(constructors.get(0).getName());
        victoriasPiloto.setText(driverStanding.getWins());
        puntosPiloto.setText(driverStanding.getPoints());
        return root;
    }
}