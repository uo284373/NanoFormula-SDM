package com.example.nanoformula;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nanoformula.API.ErgastApi;
import com.example.nanoformula.modelo.constructorRaceResults.ConstructorRaceResults;
import com.example.nanoformula.modelo.constructorsStandings.ConstructorStanding;
import com.example.nanoformula.modelo.constructorsStandings.StandingsEscuderias;
import com.example.nanoformula.modelo.constructorsStandings.StandingsList;
import com.example.nanoformula.modelo.driverRaceResults.DriverRaceResults;
import com.example.nanoformula.modelo.driversStandings.DriverStanding;
import com.example.nanoformula.modelo.driversStandings.Standings;
import com.google.android.material.snackbar.Snackbar;

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

    TextView posEscuderia;
    TextView rondaEscuderia;
    TextView victoriasEscuderiaTemporada;
    TextView puntosEscuderiaTemporada;

    Toolbar toolbar;

    ImageView fotoEscuderia;
    Loader loaderGif;
    AtomicInteger llamadasCompletadasGeneral = new AtomicInteger(0);
    int totalLlamadasGeneral = 3; //8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escuderia_details);

        loaderGif = new Loader(this);
        loaderGif.show();

        Intent intentEscuderia= getIntent();
        standings= intentEscuderia.getParcelableExtra(EscuderiasFragment.ESCUDERIA_SELECCIONADA);
        Log.i("escuderia",standings.getConstructor().getName());
        nombreEscuderia = findViewById(R.id.txNombreEscuderia);
        nacionalidadEscuderia = findViewById(R.id.txNacionalidadEscuderia);
        titulosEscuderia = findViewById(R.id.txTitulos);
        victoriasEscuderia = findViewById(R.id.txVictorias);
        puntosEscuderia = findViewById(R.id.txPuntos);
        temporadasEscuderia = findViewById(R.id.txTemporadas);
        fotoEscuderia = findViewById(R.id.ftEscuderia);
        posEscuderia = findViewById(R.id.txPos);
        rondaEscuderia = findViewById(R.id.txRonda);
        victoriasEscuderiaTemporada = findViewById(R.id.txVictoriasTemp);
        puntosEscuderiaTemporada = findViewById(R.id.txPuntosTemp);
        toolbar = findViewById(R.id.toolbarEscuderia);

        if(standings != null){
            mostrarDatosEscuderia();
            mostrarTitulosConstructor();
            mostrarVictoriasConstructor();
            mostrarTemporadasConstructor();
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

        posEscuderia.setText(standings.getPosition());
        rondaEscuderia.setText(standings.getRound());
        victoriasEscuderiaTemporada.setText(standings.getWins());
        puntosEscuderiaTemporada.setText(standings.getPoints());

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
            loaderGif.dismiss();
        }
    }

}