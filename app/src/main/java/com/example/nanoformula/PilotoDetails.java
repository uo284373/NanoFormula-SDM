package com.example.nanoformula;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nanoformula.API.ErgastApi;
import com.example.nanoformula.modelo.Piloto;
import com.example.nanoformula.modelo.driverQualifyingResults.DriverQualifyingResults;
import com.example.nanoformula.modelo.driverRaceResults.DriverRaceResults;
import com.example.nanoformula.modelo.driversStandings.Constructor;
import com.example.nanoformula.modelo.driversStandings.Driver;
import com.example.nanoformula.modelo.driversStandings.DriverStanding;
import com.example.nanoformula.modelo.driversStandings.Standings;
import com.example.nanoformula.modelo.driversStandings.StandingsList;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PilotoDetails extends AppCompatActivity {

    private Driver piloto;
    private DriverStanding standings;
    private int podios;
    private int puntos;
    private List<String> equipos = new ArrayList<>();
    private List<StandingsList> standingsList = new ArrayList<>();
    TextView nacionalidadPiloto;
    TextView numeroPiloto;
    TextView codigoPiloto;
    TextView edadPiloto;
    ImageView fotoPiloto;
    Toolbar toolbar;
    TextView victoriasPiloto;
    TextView titulosPiloto;
    TextView podiosPiloto;
    TextView polesPiloto;
    TextView temporadasPiloto;
    TextView vueltasRapidasPiloto;
    TextView puntosPiloto;
    TextView numEquipos;
    Loader loaderGif;
    AtomicInteger llamadasCompletadasGeneral = new AtomicInteger(0);
    int totalLlamadasGeneral = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piloto_details);

        loaderGif = new Loader(this);
        loaderGif.show();

        Intent intentPiloto= getIntent();
        piloto= intentPiloto.getParcelableExtra(PilotosFragment.PILOTO_SELECCIONADO);
        standings = intentPiloto.getParcelableExtra(PilotosFragment.EQUIPO_PILOTO_SELECCIONADO);
        nacionalidadPiloto = findViewById(R.id.txNacionalidadPiloto);
        numeroPiloto = findViewById(R.id.txNumero);
        codigoPiloto = findViewById(R.id.txCodigo);
        edadPiloto = findViewById(R.id.txEdad);
        fotoPiloto = findViewById(R.id.ftPiloto);
        toolbar = findViewById(R.id.toolbarPiloto);
        victoriasPiloto = findViewById(R.id.txVictoriasPiloto);
        titulosPiloto = findViewById(R.id.txTitulosPiloto);
        podiosPiloto = findViewById(R.id.txPodiosPiloto);
        polesPiloto = findViewById(R.id.txPolesPiloto);
        temporadasPiloto = findViewById(R.id.txTemporadasPiloto);
        vueltasRapidasPiloto = findViewById(R.id.txVueltaRapidaPiloto);
        puntosPiloto = findViewById(R.id.txPuntosPilotoTotal);
        numEquipos = findViewById(R.id.txMejorNumEquipos);

        if(piloto != null){
            mostrarDatosPiloto();
            mostrarNumeroVictorias();
            mostrarTitulosPiloto();
            mostrarNumeroPodios();
            mostrarPolesPiloto();
            mostrarTemporadasPiloto();
            mostrarVueltasRapidasPiloto();
            mostrarTemporadaActualStandings();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    private void mostrarDatosPiloto() {
        toolbar.setTitle(piloto.getGivenName()+" "+piloto.getFamilyName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nacionalidadPiloto.setText(piloto.getNationality());
        numeroPiloto.setText(piloto.getPermanentNumber());
        codigoPiloto.setText(piloto.getCode());

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaNac = LocalDate.parse(piloto.getDateOfBirth(), fmt);
        LocalDate ahora = LocalDate.now();

        Period periodo = Period.between(fechaNac, ahora);
        edadPiloto.setText(String.valueOf(periodo.getYears()));
        Picasso.get().load(piloto.getUrlImage()).into(fotoPiloto);
    }

    private void mostrarNumeroVictorias(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ergast.com/api/f1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ErgastApi ergastApi = retrofit.create(ErgastApi.class);
        Call<DriverRaceResults> result = ergastApi.getDriverRaceResults(piloto.getDriverId(),1);
        result.enqueue(new Callback<DriverRaceResults>() {
            @Override
            public void onResponse(Call<DriverRaceResults> call, Response<DriverRaceResults> response) {
                if(response.isSuccessful()){
                    victoriasPiloto.setText(response.body().getMRData().getTotal());
                    llamadaCompletaGif(llamadasCompletadasGeneral,totalLlamadasGeneral);
                }else{
                    loaderGif.dismiss();
                    Snackbar.make(findViewById(R.id.layoutDetallesPiloto), "Se ha producido un error al recuperar los datos del piloto", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DriverRaceResults> call, Throwable t) {
                loaderGif.dismiss();
                Snackbar.make(findViewById(R.id.layoutDetallesPiloto), "Se ha producido un error al recuperar los datos del piloto "+t.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void mostrarTitulosPiloto(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ergast.com/api/f1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ErgastApi ergastApi = retrofit.create(ErgastApi.class);
        Call<Standings> result = ergastApi.getDriverChampionStandings(piloto.getDriverId());
        result.enqueue(new Callback<Standings>() {
            @Override
            public void onResponse(Call<Standings> call, Response<Standings> response) {
                if(response.isSuccessful()){
                    titulosPiloto.setText(response.body().getMRData().getTotal());
                    llamadaCompletaGif(llamadasCompletadasGeneral,totalLlamadasGeneral);
                }else{
                    loaderGif.dismiss();
                    Snackbar.make(findViewById(R.id.layoutDetallesPiloto), "Se ha producido un error al recuperar los datos del piloto", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Standings> call, Throwable t) {
                loaderGif.dismiss();
                Snackbar.make(findViewById(R.id.layoutDetallesPiloto), "Se ha producido un error al recuperar los datos del piloto "+t.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void llamadaCompleta(AtomicInteger llamadasCompletadas, int totalLlamadas) {
        if (llamadasCompletadas.incrementAndGet() == totalLlamadas) {
            // Todas las llamadas se han completado, ahora puedes actualizar el TextView
            podiosPiloto.setText(String.valueOf(podios));
        }
    }
    private void llamadaCompletaGif(AtomicInteger llamadasCompletadas, int totalLlamadas) {
        if (llamadasCompletadas.incrementAndGet() == totalLlamadas) {
            loaderGif.dismiss();
        }
    }

    private void mostrarNumeroPodios(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ergast.com/api/f1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        int totalLlamadas = 3;  // Número total de llamadas a la API
        AtomicInteger llamadasCompletadas = new AtomicInteger(0);

        ErgastApi ergastApi = retrofit.create(ErgastApi.class);
        Call<DriverRaceResults> result = ergastApi.getDriverRaceResults(piloto.getDriverId(),2);
        result.enqueue(new Callback<DriverRaceResults>() {
            @Override
            public void onResponse(Call<DriverRaceResults> call, Response<DriverRaceResults> response) {
                if(response.isSuccessful()){
                    podios += Integer.parseInt(response.body().getMRData().getTotal());
                    llamadaCompleta(llamadasCompletadas, totalLlamadas);
                    llamadaCompletaGif(llamadasCompletadasGeneral,totalLlamadasGeneral);
                }else{
                    loaderGif.dismiss();
                    Snackbar.make(findViewById(R.id.layoutDetallesPiloto), "Se ha producido un error al recuperar los datos del piloto", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DriverRaceResults> call, Throwable t) {
                loaderGif.dismiss();
                Snackbar.make(findViewById(R.id.layoutDetallesPiloto), "Se ha producido un error al recuperar los datos del piloto "+t.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
        result = ergastApi.getDriverRaceResults(piloto.getDriverId(),1);
        result.enqueue(new Callback<DriverRaceResults>() {
            @Override
            public void onResponse(Call<DriverRaceResults> call, Response<DriverRaceResults> response) {
                if(response.isSuccessful()){
                    podios += Integer.parseInt(response.body().getMRData().getTotal());
                    llamadaCompleta(llamadasCompletadas, totalLlamadas);
                    llamadaCompletaGif(llamadasCompletadasGeneral,totalLlamadasGeneral);
                }else{
                    loaderGif.dismiss();
                    Snackbar.make(findViewById(R.id.layoutDetallesPiloto), "Se ha producido un error al recuperar los datos del piloto", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DriverRaceResults> call, Throwable t) {
                loaderGif.dismiss();
                Snackbar.make(findViewById(R.id.layoutDetallesPiloto), "Se ha producido un error al recuperar los datos del piloto "+t.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });

        result = ergastApi.getDriverRaceResults(piloto.getDriverId(),3);
        result.enqueue(new Callback<DriverRaceResults>() {
            @Override
            public void onResponse(Call<DriverRaceResults> call, Response<DriverRaceResults> response) {
                if(response.isSuccessful()){
                    podios += Integer.parseInt(response.body().getMRData().getTotal());
                    llamadaCompleta(llamadasCompletadas, totalLlamadas);
                    llamadaCompletaGif(llamadasCompletadasGeneral,totalLlamadasGeneral);
                }else{
                    loaderGif.dismiss();
                    Snackbar.make(findViewById(R.id.layoutDetallesPiloto), "Se ha producido un error al recuperar los datos del piloto", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DriverRaceResults> call, Throwable t) {
                loaderGif.dismiss();
                Snackbar.make(findViewById(R.id.layoutDetallesPiloto), "Se ha producido un error al recuperar los datos del piloto "+t.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void mostrarPolesPiloto(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ergast.com/api/f1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ErgastApi ergastApi = retrofit.create(ErgastApi.class);
        Call<DriverQualifyingResults> result = ergastApi.getDriverQualifyingResults(piloto.getDriverId());
        result.enqueue(new Callback<DriverQualifyingResults>() {
            @Override
            public void onResponse(Call<DriverQualifyingResults> call, Response<DriverQualifyingResults> response) {
                if(response.isSuccessful()){
                    polesPiloto.setText(response.body().getMRData().getTotal());
                    llamadaCompletaGif(llamadasCompletadasGeneral,totalLlamadasGeneral);
                }else{
                    loaderGif.dismiss();
                    Snackbar.make(findViewById(R.id.layoutDetallesPiloto), "Se ha producido un error al recuperar los datos del piloto", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DriverQualifyingResults> call, Throwable t) {
                loaderGif.dismiss();
                Snackbar.make(findViewById(R.id.layoutDetallesPiloto), "Se ha producido un error al recuperar los datos del piloto "+t.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void mostrarTemporadasPiloto(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ergast.com/api/f1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ErgastApi ergastApi = retrofit.create(ErgastApi.class);
        Call<Standings> result = ergastApi.getDriverStandings(piloto.getDriverId());
        result.enqueue(new Callback<Standings>() {
            @Override
            public void onResponse(Call<Standings> call, Response<Standings> response) {
                if(response.isSuccessful()){
                    temporadasPiloto.setText(response.body().getMRData().getTotal());
                    standingsList = response.body().getMRData().getStandingsTable().getStandingsLists();
                    for(StandingsList standings : response.body().getMRData().getStandingsTable().getStandingsLists()){
                        for(DriverStanding driverStanding : standings.getDriverStandings()){
                            puntos += Double.parseDouble(driverStanding.getPoints());
                            for(Constructor constructor : driverStanding.getConstructors()){
                                if(!equipos.contains(constructor.getConstructorId())){
                                    equipos.add(constructor.getConstructorId());
                                }
                            }
                        }
                    }
                    puntos += Integer.parseInt(standings.getPoints());
                    puntosPiloto.setText(String.valueOf(puntos));
                    numEquipos.setText(String.valueOf(equipos.size()));

                    llamadaCompletaGif(llamadasCompletadasGeneral,totalLlamadasGeneral);
                }else{
                    loaderGif.dismiss();
                    Snackbar.make(findViewById(R.id.layoutDetallesPiloto), "Se ha producido un error al recuperar los datos del piloto", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Standings> call, Throwable t) {
                loaderGif.dismiss();
                Snackbar.make(findViewById(R.id.layoutDetallesPiloto), "Se ha producido un error al recuperar los datos del piloto "+t.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void mostrarVueltasRapidasPiloto(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ergast.com/api/f1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ErgastApi ergastApi = retrofit.create(ErgastApi.class);
        Call<DriverRaceResults> result = ergastApi.getDriverFastestLap(piloto.getDriverId());
        result.enqueue(new Callback<DriverRaceResults>() {
            @Override
            public void onResponse(Call<DriverRaceResults> call, Response<DriverRaceResults> response) {
                if(response.isSuccessful()){
                    vueltasRapidasPiloto.setText(response.body().getMRData().getTotal());
                    llamadaCompletaGif(llamadasCompletadasGeneral,totalLlamadasGeneral);
                }else{
                    loaderGif.dismiss();
                    Snackbar.make(findViewById(R.id.layoutDetallesPiloto), "Se ha producido un error al recuperar los datos del piloto", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DriverRaceResults> call, Throwable t) {
                loaderGif.dismiss();
                Snackbar.make(findViewById(R.id.layoutDetallesPiloto), "Se ha producido un error al recuperar los datos del piloto "+t.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void mostrarTemporadaStandings(int year){
        for(StandingsList standings : standingsList){
            if(standings.getSeason().equals(String.valueOf(year))){
                TemporadaPilotoFragment temporadaPilotoFragment=TemporadaPilotoFragment.newInstance(standings.getDriverStandings().get(0));
                getSupportFragmentManager().beginTransaction().replace(R.id.layoutTemporadaPiloto, temporadaPilotoFragment).commit();
                return;
            }
        }
    }

    private void mostrarTemporadaActualStandings(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ergast.com/api/f1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ErgastApi ergastApi = retrofit.create(ErgastApi.class);
        Call<Standings> result = ergastApi.getDriverStandingsCurrent(piloto.getDriverId());
        result.enqueue(new Callback<Standings>() {
            @Override
            public void onResponse(Call<Standings> call, Response<Standings> response) {
                if(response.isSuccessful()){
                    TemporadaPilotoFragment temporadaPilotoFragment=TemporadaPilotoFragment.newInstance(response.body().getMRData().getStandingsTable().getStandingsLists().get(0).getDriverStandings().get(0));
                    getSupportFragmentManager().beginTransaction().replace(R.id.layoutTemporadaPiloto, temporadaPilotoFragment).commit();
                    llamadaCompletaGif(llamadasCompletadasGeneral,totalLlamadasGeneral);
                }else{
                    loaderGif.dismiss();
                    Snackbar.make(findViewById(R.id.layoutDetallesPiloto), "Se ha producido un error al recuperar los datos del piloto", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Standings> call, Throwable t) {
                loaderGif.dismiss();
                Snackbar.make(findViewById(R.id.layoutDetallesPiloto), "Se ha producido un error al recuperar los datos del piloto "+t.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }
}