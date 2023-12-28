package com.example.nanoformula;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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

    private static final int CREATE_FILE_REQUEST_CODE = 123;

    private static final String ARG_CARRERAS = "CARRERAS";
    public static final String TEMPORADA_CARRERA = "TEMPORADA";
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
    private String season;


    public CarrerasFragment() {
        // Required empty public constructor
    }

    public static CarrerasFragment newInstance(RaceSchedule raceSchedule, String season) {
        CarrerasFragment fragment = new CarrerasFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CARRERAS,raceSchedule);
        args.putString(TEMPORADA_CARRERA, season);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            raceSchedule = (RaceSchedule) getArguments().get(ARG_CARRERAS);
            season = (String) getArguments().get(TEMPORADA_CARRERA);
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
        fecha.setText(race.getDateFormat());
        hora.setText(race.getTime() != null ? race.getTime().substring(0,5) : "--:--");
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
        intent.putExtra(TEMPORADA_CARRERA, season);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
    }
    public void exportarCSV() {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/csv");
        intent.putExtra(Intent.EXTRA_TITLE, "carreras_google_calendar.csv");

        startActivityForResult(intent, CREATE_FILE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);

        if (requestCode == CREATE_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                Uri uri = resultData.getData();
                writeToFile(uri);
            }
        }
    }

    private void writeToFile(Uri uri) {
        try {
            OutputStream outputStream = getContext().getContentResolver().openOutputStream(uri);
            if (outputStream != null) {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
                writer.write("Subject,Start Date,Start Time,End Date,End Time,Description,Location\n");

                List<Race> carreras = raceSchedule.getMRData().getRaceTable().getRaces();

                for (Race carrera : carreras) {
                    writer.write(String.format("%s,%s,%s,%s,%s,%s,%s\n",
                            carrera.getRaceName(),
                            carrera.getDate(),
                            carrera.getTime().substring(0, 5),
                            carrera.getDate(),
                            carrera.getTime().substring(0, 5),
                            carrera.getCircuit().getCircuitName(),
                            carrera.getCircuit().getLocation().getLocality()));
                }

                writer.close();
                Log.d("ExportarCSV", "Archivo CSV generado con éxito: " + uri.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("ExportarCSV", "Error al generar el archivo CSV");
        }
    }


}
