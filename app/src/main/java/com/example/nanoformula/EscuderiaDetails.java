package com.example.nanoformula;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nanoformula.API.ErgastApi;
import com.example.nanoformula.modelo.constructorRaceResults.ConstructorRaceResults;
import com.example.nanoformula.modelo.constructorsStandings.ConstructorStanding;
import com.example.nanoformula.modelo.constructorsStandings.StandingsEscuderias;
import com.example.nanoformula.modelo.constructorsStandings.StandingsList;
import com.example.nanoformula.modelo.driverRaceResults.DriverRaceResults;
import com.example.nanoformula.modelo.driversStandings.Standings;
import com.example.nanoformula.modelo.raceResultsByConstructor.Race;
import com.example.nanoformula.modelo.raceResultsByConstructor.RaceResultsByConstructor;
import com.example.nanoformula.modelo.raceResultsByConstructor.Result;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EscuderiaDetails extends AppCompatActivity {

    private ConstructorStanding standings;
    TextView nombreEscuderia;
    TextView nacionalidadEscuderia;
    TextView titulosEscuderia;
    TextView victoriasEscuderia;
    TextView puntosEscuderia;
    TextView temporadasEscuderia;
    Button btnSelectTemp;
    private ArrayList<String> numTemporadasEscuderia = new ArrayList<>();
    Toolbar toolbar;

    ImageView fotoEscuderia;
    Loader loaderGif;
    AtomicInteger llamadasCompletadasGeneral = new AtomicInteger(0);
    AtomicInteger llamadasCompletadasTemporada = new AtomicInteger(0);

    int totalLlamadasGeneral = 4; //8;
    int llamadasTemporada = 2;

    private ArrayList<String> puntosTemp = new ArrayList<>();
    private ArrayList<String> pilotosTemp = new ArrayList<>();

    private String season = "2023";

    private boolean first = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escuderia_details);

        loaderGif = new Loader(this);
        loaderGif.show();

        Intent intentEscuderia= getIntent();
        standings= intentEscuderia.getParcelableExtra(EscuderiasFragment.ESCUDERIA_SELECCIONADA);
        Log.i("escuderia",standings.getConstructor().getName());
        nacionalidadEscuderia = findViewById(R.id.txNacionalidadEscuderia);
        titulosEscuderia = findViewById(R.id.txTitulos);
        victoriasEscuderia = findViewById(R.id.txVictorias);
        puntosEscuderia = findViewById(R.id.txPuntos);
        temporadasEscuderia = findViewById(R.id.txTemporadas);
        fotoEscuderia = findViewById(R.id.ftEscuderia);
        toolbar = findViewById(R.id.toolbarEscuderia);
        btnSelectTemp = findViewById(R.id.btnSeleccionarTemporada);

        if(standings != null){
            mostrarDatosEscuderia();
            mostrarTitulosConstructor();
            mostrarVictoriasConstructor();
            mostrarTemporadasConstructor();
            mostrarChart("current");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    private void mostrarDatosEscuderia() {
        toolbar.setTitle(standings.getConstructor().getName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nacionalidadEscuderia.setText(standings.getConstructor().getNationality());

        fotoEscuderia.setImageResource(standings.getDrawableDetails());
//        fotoEscuderia.setScaleType();

    }

    private void mostrarTitulosConstructor(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ergast.com/api/f1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ErgastApi ergastApi = retrofit.create(ErgastApi.class);
        Call<StandingsEscuderias> result = ergastApi.getConstructorsChampionStandings(standings.getConstructor().getConstructorId());
        result.enqueue(new Callback<StandingsEscuderias>() {
            @Override
            public void onResponse(Call<StandingsEscuderias> call, Response<StandingsEscuderias> response) {
                if(response.isSuccessful()){
                    titulosEscuderia.setText(response.body().getMRData().getTotal());
                    llamadaCompletaGif(llamadasCompletadasGeneral,totalLlamadasGeneral);
                }else{
                    Snackbar.make(findViewById(R.id.layoutPrincipal), "Se ha producido un error al recuperar los datos del piloto", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<StandingsEscuderias> call, Throwable t) {
                Snackbar.make(findViewById(R.id.layoutPrincipal), "Se ha producido un error al recuperar los datos del piloto", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void mostrarVictoriasConstructor(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ergast.com/api/f1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ErgastApi ergastApi = retrofit.create(ErgastApi.class);
        Call<ConstructorRaceResults> result = ergastApi.getConstructorsRaceResults(standings.getConstructor().getConstructorId(),1);
        result.enqueue(new Callback<ConstructorRaceResults>() {
            @Override
            public void onResponse(Call<ConstructorRaceResults> call, Response<ConstructorRaceResults> response) {
                if(response.isSuccessful()){
                    victoriasEscuderia.setText(response.body().getMRData().getTotal());
                    llamadaCompletaGif(llamadasCompletadasGeneral,totalLlamadasGeneral);
                }else{
                    Snackbar.make(findViewById(R.id.layoutPrincipal), "Se ha producido un error al recuperar los datos del piloto", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ConstructorRaceResults> call, Throwable t) {
                Snackbar.make(findViewById(R.id.layoutPrincipal), "Se ha producido un error al recuperar los datos del piloto", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void mostrarTemporadasConstructor(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ergast.com/api/f1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ErgastApi ergastApi = retrofit.create(ErgastApi.class);
        Call<StandingsEscuderias> result = ergastApi.getConstructorStandings(standings.getConstructor().getConstructorId());
        result.enqueue(new Callback<StandingsEscuderias>() {
            @Override
            public void onResponse(Call<StandingsEscuderias> call, Response<StandingsEscuderias> response) {
                if(response.isSuccessful()){
                    double puntos = 0;
                    int seasons = 0;
                    temporadasEscuderia.setText(response.body().getMRData().getTotal());
                    for(StandingsList standings : response.body().getMRData().getStandingsTable().getStandingsLists()){
                        for(ConstructorStanding constructorStanding : standings.getConstructorStandings()){
                            puntos += Double.parseDouble(constructorStanding.getPoints());
                            seasons++;
                            numTemporadasEscuderia.add(standings.getSeason());
                        }
                    }
                    Log.i("Seasons", String.valueOf(seasons));
                    puntos += Integer.parseInt(standings.getPoints());
                    puntosEscuderia.setText(String.valueOf(puntos));
                    llamadaCompletaGif(llamadasCompletadasGeneral,totalLlamadasGeneral);
                }else{
                    Snackbar.make(findViewById(R.id.layoutPrincipal), "Se ha producido un error al recuperar los datos del piloto", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<StandingsEscuderias> call, Throwable t) {
                Snackbar.make(findViewById(R.id.layoutPrincipal), "Se ha producido un error al recuperar los datos del piloto", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void llamadaCompletaGif(AtomicInteger llamadasCompletadas, int totalLlamadas) {
        if (llamadasCompletadas.incrementAndGet() == totalLlamadas) {
            TemporadaEscuderiaFragment temporadaEscuderiaFragment=TemporadaEscuderiaFragment.newInstance(season, puntosTemp, pilotosTemp, standings.getPosition(), standings.getRound(), standings.getWins(), standings.getPoints());
            getSupportFragmentManager().beginTransaction().replace(R.id.layoutTemporadaEscuderia, temporadaEscuderiaFragment).commit();
            loaderGif.dismiss();

            btnSelectTemp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(EscuderiaDetails.this);
                    dialogo1.setTitle("Seleccione una temporada");
                    CharSequence[] itemsArray = numTemporadasEscuderia.toArray(new CharSequence[numTemporadasEscuderia.size()]);
                    dialogo1.setSingleChoiceItems(itemsArray, -1, (dialog, which) -> {
                        CharSequence temp = itemsArray[which];
                        btnSelectTemp.setText(temp);
                        puntosTemp.clear();
                        pilotosTemp.clear();
                        llamadasTemporada = 2;
                        llamadasCompletadasTemporada = new AtomicInteger(0);
                        loaderGif.show();
                        season = temp.toString();
                        mostrarChart(temp.toString());
                        mostrarEstadisticasGeneralesTemporadaEscuderias(temp.toString());
                        dialog.dismiss();
                    });
                    dialogo1.show();
                }
            });
        }
    }

    private void llamadaCompletaTemporada(AtomicInteger llamadasCompletadas, int totalLlamadas) {
        if (llamadasCompletadas.incrementAndGet() == totalLlamadas) {
            TemporadaEscuderiaFragment temporadaEscuderiaFragment=TemporadaEscuderiaFragment.newInstance(season, puntosTemp, pilotosTemp, standings.getPosition(), standings.getRound(), standings.getWins(), standings.getPoints());
            getSupportFragmentManager().beginTransaction().replace(R.id.layoutTemporadaEscuderia, temporadaEscuderiaFragment).commit();
            loaderGif.dismiss();
        }
    }

    private void mostrarEstadisticasGeneralesTemporadaEscuderias(String temp){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ergast.com/api/f1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ErgastApi ergastApi = retrofit.create(ErgastApi.class);
        Call<StandingsEscuderias> result = ergastApi.getConstructorsStandingsForTemp(temp,standings.getConstructor().getConstructorId());
        result.enqueue(new Callback<StandingsEscuderias>() {
            @Override
            public void onResponse(Call<StandingsEscuderias> call, Response<StandingsEscuderias> response) {
                if(response.isSuccessful()){
                    standings = response.body().getMRData().getStandingsTable().getStandingsLists().get(0).getConstructorStandings().get(0);
                    standings.setRound(response.body().getMRData().getStandingsTable().getStandingsLists().get(0).getRound());
                    llamadaCompletaTemporada(llamadasCompletadasTemporada,llamadasTemporada);

                }else{
                    Snackbar.make(findViewById(R.id.layoutDetallesPiloto), "Se ha producido un error al recuperar los datos del piloto", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<StandingsEscuderias> call, Throwable t) {
                Snackbar.make(findViewById(R.id.layoutDetallesPiloto), "Se ha producido un error al recuperar los datos del piloto "+t.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }






















    private void mostrarChart(String temp){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ergast.com/api/f1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ErgastApi ergastApi = retrofit.create(ErgastApi.class);
        Call<RaceResultsByConstructor> result = ergastApi.getConstructorsRaceResultsForTemp(temp,standings.getConstructor().getConstructorId());
        result.enqueue(new Callback<RaceResultsByConstructor>() {
            @Override
            public void onResponse(Call<RaceResultsByConstructor> call, Response<RaceResultsByConstructor> response) {
                if(response.isSuccessful()){
                    int points = 0;
                    HashMap<String, String> drivers = new HashMap<>();
                    for(Race race : response.body().getMRData().getRaceTable().getRaces()){
                        for(Result result : race.getResults()){
                            points += Integer.parseInt(result.getPoints());

                            String races = drivers.get(result.getDriver().getFamilyName());

                            drivers.put(result.getDriver().getFamilyName(), races == null ? race.getRound() + "," + result.getPoints() : races + ";" + race.getRound() + "," + result.getPoints());
                        }
                        puntosTemp.add(race.getRound()+";"+String.valueOf(points));
                    }
                    for(String driver : drivers.keySet()){
                        pilotosTemp.add(driver + "-" + drivers.get(driver));
                    }

                    if(first) {
                        first = false;
                        llamadaCompletaGif(llamadasCompletadasGeneral, totalLlamadasGeneral);
                    } else{
                        llamadaCompletaTemporada(llamadasCompletadasTemporada,llamadasTemporada);
                    }

                }else{
                    loaderGif.dismiss();
                    Snackbar.make(findViewById(R.id.layoutDetallesPiloto), "Se ha producido un error al recuperar los datos del piloto", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RaceResultsByConstructor> call, Throwable t) {
                loaderGif.dismiss();
                Snackbar.make(findViewById(R.id.layoutDetallesPiloto), "Se ha producido un error al recuperar los datos del piloto "+t.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });


    }

}