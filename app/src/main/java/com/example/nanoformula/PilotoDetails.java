package com.example.nanoformula;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nanoformula.API.ErgastApi;
import com.example.nanoformula.modelo.Piloto;
import com.example.nanoformula.modelo.driverQualifyingResults.DriverQualifyingResults;
import com.example.nanoformula.modelo.driverRaceResults.DriverRaceResults;
import com.example.nanoformula.modelo.driverRaceResults.Race;
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
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PilotoDetails extends AppCompatActivity {

    private Driver piloto;
    private DriverStanding standings;
    private ArrayList<Constructor> constructors;
    private int podios;
    private int puntos;
    private ArrayList<String> puntosTemp = new ArrayList<>();
    private List<String> equipos = new ArrayList<>();
    private List<StandingsList> standingsList = new ArrayList<>();
    private ArrayList<String> racesStartPosition = new ArrayList<>();
    private ArrayList<String> racesFinalPosition = new ArrayList<>();
    private ArrayList<String> numTempPiloto = new ArrayList<>();
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
    Button btnSelectTemp;
    Loader loaderGif;
    AtomicInteger llamadasCompletadasGeneral = new AtomicInteger(0);
    AtomicInteger llamadasCompletadasTemporada = new AtomicInteger(0);
    int totalLlamadasGeneral = 9;
    int llamadasTemporada = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piloto_details);

        loaderGif = new Loader(this);
        loaderGif.show();

        Intent intentPiloto= getIntent();
        piloto= intentPiloto.getParcelableExtra(PilotosFragment.PILOTO_SELECCIONADO);
        standings = intentPiloto.getParcelableExtra(PilotosFragment.STANDINGS_PILOTO_SELECCIONADO);
        constructors = intentPiloto.getParcelableArrayListExtra(PilotosFragment.EQUIPO_PILOTO_SELECCIONADO);
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
        btnSelectTemp = findViewById(R.id.btnSeleccionarTemporada);

        if(piloto != null){
            mostrarDatosPiloto();
            mostrarNumeroVictorias();
            mostrarTitulosPiloto();
            mostrarNumeroPodios();
            mostrarPolesPiloto();
            mostrarTemporadasPiloto();
            mostrarVueltasRapidasPiloto();
            mostrarTemporadaStandingsCurrent();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    private void mostrarDatosPiloto() {
        ((TextView)findViewById(R.id.tituloToolbarPiloto)).setText(piloto.getGivenName()+" "+piloto.getFamilyName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nacionalidadPiloto.setText(piloto.getNationality());
        numeroPiloto.setText(piloto.getPermanentNumber());
        codigoPiloto.setText(piloto.getCode());

        edadPiloto.setText(String.valueOf(calcularEdad(piloto.getDateOfBirth())));

        Picasso.get().load(piloto.getUrlImage()).into(fotoPiloto);
    }

    public static int calcularEdad(String fechaNacimiento) {
        // Obtener la fecha actual
        Date fechaActual = new Date();

        // Convertir la fecha de nacimiento a un objeto Date
        Date fechaNac = parseFecha(fechaNacimiento);

        // Calcular la diferencia en milisegundos
        long diferenciaMillis = fechaActual.getTime() - fechaNac.getTime();

        // Calcular la diferencia en años
        int edad = (int) (diferenciaMillis / 1000 / 60 / 60 / 24 / 365.25);

        return edad;
    }

    private static Date parseFecha(String fecha) {
        // Método para convertir una cadena de fecha a un objeto Date
        String[] partes = fecha.split("-");
        int año = Integer.parseInt(partes[0]);
        int mes = Integer.parseInt(partes[1]) - 1; // Los meses en Date van de 0 a 11
        int dia = Integer.parseInt(partes[2]);

        return new Date(año - 1900, mes, dia);
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

    private void llamadaCompletaTemporada(AtomicInteger llamadasCompletadas, int totalLlamadas) {
        if (llamadasCompletadas.incrementAndGet() == totalLlamadas) {
            TemporadaPilotoFragment temporadaPilotoFragment=TemporadaPilotoFragment.newInstance(standings,constructors,puntosTemp,racesStartPosition,racesFinalPosition,btnSelectTemp.getText().toString());
            getSupportFragmentManager().beginTransaction().replace(R.id.layoutTemporadaPiloto, temporadaPilotoFragment).commit();
            loaderGif.dismiss();
        }
    }
    private void llamadaCompletaGif(AtomicInteger llamadasCompletadas, int totalLlamadas) {
        if (llamadasCompletadas.incrementAndGet() == totalLlamadas) {
            TemporadaPilotoFragment temporadaPilotoFragment=TemporadaPilotoFragment.newInstance(standings,constructors,puntosTemp,racesStartPosition,racesFinalPosition,btnSelectTemp.getText().toString());
            getSupportFragmentManager().beginTransaction().replace(R.id.layoutTemporadaPiloto, temporadaPilotoFragment).commit();
            loaderGif.dismiss();

            btnSelectTemp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(new ContextThemeWrapper(PilotoDetails.this,R.style.AlertDialogCustom));
                    dialogo1.setTitle("Seleccione una temporada");
                    CharSequence[] itemsArray = numTempPiloto.toArray(new CharSequence[numTempPiloto.size()]);
                    dialogo1.setSingleChoiceItems(itemsArray, -1, (dialog, which) -> {
                        CharSequence temp = itemsArray[which];
                        btnSelectTemp.setText(temp);
                        puntosTemp.clear();
                        racesFinalPosition.clear();
                        racesStartPosition.clear();
                        llamadasTemporada = 2;
                        llamadasCompletadasTemporada = new AtomicInteger(0);
                        loaderGif.show();
                        mostrarTemporadaStandings(temp.toString());
                        mostrarEstadisticasGeneralesTemporadaPiloto(temp.toString());
                        dialog.dismiss();
                    });
                    dialogo1.show();
                }
            });
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
                .client(new OkHttpClient.Builder()
                        .connectTimeout(30, TimeUnit.SECONDS) // Ajusta este tiempo según tus necesidades
                        .readTimeout(30, TimeUnit.SECONDS)
                        .build())
                .build();

        ErgastApi ergastApi = retrofit.create(ErgastApi.class);
        Call<Standings> result = ergastApi.getDriverStandings(piloto.getDriverId());
        result.enqueue(new Callback<Standings>() {
            @Override
            public void onResponse(Call<Standings> call, Response<Standings> response) {
                if(response.isSuccessful()){
                    temporadasPiloto.setText(response.body().getMRData().getTotal());
                    standingsList = response.body().getMRData().getStandingsTable().getStandingsLists();
                    for(StandingsList standingsResponse : response.body().getMRData().getStandingsTable().getStandingsLists()){
                        numTempPiloto.add(standingsResponse.getSeason());
                        for(DriverStanding driverStanding : standingsResponse.getDriverStandings()){
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



    private void mostrarTemporadaStandingsCurrent(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ergast.com/api/f1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ErgastApi ergastApi = retrofit.create(ErgastApi.class);
        Call<DriverRaceResults> result = ergastApi.getDriverRaceResultsForTemp("current",piloto.getDriverId());
        result.enqueue(new Callback<DriverRaceResults>() {
            @Override
            public void onResponse(Call<DriverRaceResults> call, Response<DriverRaceResults> response) {
                if(response.isSuccessful()){
                    int points = 0;
                    for(Race race : response.body().getMRData().getRaceTable().getRaces()){
                        points += Integer.parseInt(race.getResults().get(0).getPoints());
                        puntosTemp.add(race.getRound()+";"+String.valueOf(points));
                    }
                    getRaceStartPosition(response.body().getMRData().getRaceTable().getRaces());
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

    private void mostrarTemporadaStandings(String temporada){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ergast.com/api/f1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ErgastApi ergastApi = retrofit.create(ErgastApi.class);
        Call<DriverRaceResults> result = ergastApi.getDriverRaceResultsForTemp(temporada,piloto.getDriverId());
        result.enqueue(new Callback<DriverRaceResults>() {
            @Override
            public void onResponse(Call<DriverRaceResults> call, Response<DriverRaceResults> response) {
                if(response.isSuccessful()){
                    int points = 0;
                    for(Race race : response.body().getMRData().getRaceTable().getRaces()){
                        points += Integer.parseInt(race.getResults().get(0).getPoints());
                        puntosTemp.add(race.getRound()+";"+String.valueOf(points));
                    }
                    getRaceStartPosition(response.body().getMRData().getRaceTable().getRaces());
                    llamadaCompletaTemporada(llamadasCompletadasTemporada,llamadasTemporada);
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

    private void mostrarEstadisticasGeneralesTemporadaPiloto(String temp){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ergast.com/api/f1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ErgastApi ergastApi = retrofit.create(ErgastApi.class);
        Call<Standings> result = ergastApi.getDriverStandingsForTemp(temp,piloto.getDriverId());
        result.enqueue(new Callback<Standings>() {
            @Override
            public void onResponse(Call<Standings> call, Response<Standings> response) {
                if(response.isSuccessful()){
                    standings = response.body().getMRData().getStandingsTable().getStandingsLists().get(0).getDriverStandings().get(0);
                    constructors = new ArrayList<>(response.body().getMRData().getStandingsTable().getStandingsLists().get(0).getDriverStandings().get(0).getConstructors());
                    llamadaCompletaTemporada(llamadasCompletadasTemporada,llamadasTemporada);

                }else{
                    Snackbar.make(findViewById(R.id.layoutDetallesPiloto), "Se ha producido un error al recuperar los datos del piloto", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Standings> call, Throwable t) {
                Snackbar.make(findViewById(R.id.layoutDetallesPiloto), "Se ha producido un error al recuperar los datos del piloto "+t.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }



    private void getRaceStartPosition(List<Race> races){
        for(Race race : races){
            if(!race.getResults().get(0).getGrid().equals("0")){
                racesStartPosition.add(race.getRound()+";"+race.getResults().get(0).getGrid());
            }else{
                racesStartPosition.add(race.getRound()+";"+"20");
            }
            racesFinalPosition.add(race.getRound()+";"+race.getResults().get(0).getPosition());
        }
    }
}