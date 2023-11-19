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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nanoformula.modelo.Carrera;
import com.example.nanoformula.modelo.Escuderia;
import com.example.nanoformula.modelo.raceSchedule.Race;
import com.example.nanoformula.modelo.raceSchedule.RaceSchedule;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    ShapeableImageView fotoCircuito;
    ImageView bandera;
    TextView nombreCarrera;
    TextView nombreCircuito;
    TextView localidad;
    TextView fecha;
    TextView hora;


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
        View root= inflater.inflate(R.layout.fragment_carreras, container, false);
        fotoCircuito = root.findViewById(R.id.ftPilotoCircuito);
        bandera = root.findViewById(R.id.fotoBanderaCarrera);
        nombreCarrera = root.findViewById(R.id.txNombreCarrera);
        nombreCircuito = root.findViewById(R.id.txNombreCircuito);
        localidad= root.findViewById(R.id.txLocalidad);
        fecha = root.findViewById(R.id.txFecha);
        hora = root.findViewById(R.id.txHora);
        listaCarrerasView = (RecyclerView)root.findViewById(R.id.carrerasRecyclerView);
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
        List<Race> raceList = raceSchedule.getMRData().getRaceTable().getRaces();
        Race upcomingRace = findUpcomingRace(raceList);
        if (upcomingRace == null && !raceList.isEmpty()) {
            upcomingRace = raceList.get(raceList.size() - 1);
        }
        rellenarProximaCarrera(upcomingRace);
        return root;
    }

    private void rellenarProximaCarrera(Race race){
        Picasso.get().load(race.getUrl()).into(fotoCircuito);
        Picasso.get().load(race.getCircuit().getUrl()).into(bandera);
        nombreCarrera.setText(race.getRaceName());
        nombreCircuito.setText(race.getCircuit().getCircuitName());
        localidad.setText(race.getCircuit().getLocation().getLocality());
        fecha.setText(race.getDate());
        hora.setText(race.getTime().substring(0,5));
    }

    private Race findUpcomingRace(List<Race> raceList) {
        Race upcomingRace = null;
        long currentTimeMillis = System.currentTimeMillis();

        for (Race race : raceList) {
            try {
                // Parse the date of the race
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date raceDate = sdf.parse(race.getDate());

                // Compare with the current date
                if (raceDate != null && raceDate.getTime() >= currentTimeMillis) {
                    if (upcomingRace == null || raceDate.getTime() < sdf.parse(upcomingRace.getDate()).getTime()) {
                        upcomingRace = race;
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return upcomingRace;
    }
    private void clickonItem(Race carrera) {
        Intent intent=new Intent (CarrerasFragment.this.getContext(), CarreraDetails.class);
        intent.putExtra(CARRERA_SELECCIONADA, carrera);
        intent.putExtra(CIRCUITO_SELECCIONADA, carrera.getCircuit());
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
    }
}
