package com.example.nanoformula.vista.comparativa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.nanoformula.API.ErgastApi;
import com.example.nanoformula.API.WikipediaApi;
import com.example.nanoformula.R;
import com.example.nanoformula.modelo.allDrivers.Driver;
import com.example.nanoformula.modelo.allResultsForADriver.AllResultsForADriver;
import com.example.nanoformula.modelo.driverRaceResults.DriverRaceResults;
import com.example.nanoformula.modelo.driversImage.DriverImage;
import com.example.nanoformula.modelo.driversStandings.Constructor;
import com.example.nanoformula.modelo.driversStandings.DriverStanding;
import com.example.nanoformula.modelo.driversStandings.Standings;
import com.example.nanoformula.modelo.driversStandings.StandingsList;
import com.example.nanoformula.util.Loader;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ComparativaPilotos extends AppCompatActivity {

    private Driver piloto1;
    private Driver piloto2;

    private ImageView foto1;
    private TextView nombre1;
    private TextView titulos1;
    private TextView victorias1;
    private TextView podios1;
    private TextView poles1;
    private TextView temporadas1;
    private TextView vueltasRapidas1;
    private TextView puntos1;
    private TextView escuderias1;


    private ImageView foto2;
    private TextView nombre2;
    private TextView titulos2;
    private TextView victorias2;
    private TextView podios2;
    private TextView poles2;
    private TextView temporadas2;
    private TextView vueltasRapidas2;
    private TextView puntos2;
    private TextView escuderias2;


    Loader loaderGif;

    AtomicInteger llamadasCompletadasGeneral = new AtomicInteger(0);
    int totalLlamadasGeneral = 16;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparativa_pilotos);

        loaderGif = new Loader(this);
        loaderGif.show();

        Intent intentEscuderia= getIntent();
        piloto1= intentEscuderia.getParcelableExtra(ComparativaFragment.PILOTO_1);
        Log.i("comparativa",piloto1.toString());

        piloto2= intentEscuderia.getParcelableExtra(ComparativaFragment.PILOTO_2);
        Log.i("comparativa",piloto2.toString());

        Toolbar toolbar = findViewById(R.id.toolbarComparativaPiloto);
        TextView txPiloto1 = findViewById(R.id.txEstadisticasPiloto1);
        TextView txPiloto2 = findViewById(R.id.txEstadisticasPiloto2);

        toolbar.setTitle(piloto1.getFamilyName() + " vs " + piloto2.getFamilyName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txPiloto1.setText(piloto1.toString());
        txPiloto2.setText(piloto2.toString());

        foto1 = findViewById(R.id.ftComparativaPiloto1);
        nombre1 = findViewById(R.id.txNombrePiloto1);
        victorias1 = findViewById(R.id.txVictoriasPiloto1);
        podios1 = findViewById(R.id.txPodiosPiloto1);
        poles1 = findViewById(R.id.txPolesPiloto1);
        temporadas1 = findViewById(R.id.txTemporadasPiloto1);
        vueltasRapidas1 = findViewById(R.id.txVueltaRapidaPiloto1);
        puntos1 = findViewById(R.id.txPuntosPilotoTotal1);
        escuderias1 = findViewById(R.id.txMejorNumEquipos1);
        titulos1 = findViewById(R.id.txTitulosPiloto1);

        foto2 = findViewById(R.id.ftComparativaPiloto2);
        nombre2 = findViewById(R.id.txNombrePiloto2);
        victorias2 = findViewById(R.id.txVictoriasPiloto2);
        podios2 = findViewById(R.id.txPodiosPiloto2);
        poles2 = findViewById(R.id.txPolesPiloto2);
        temporadas2 = findViewById(R.id.txTemporadasPiloto2);
        vueltasRapidas2 = findViewById(R.id.txVueltaRapidaPiloto2);
        puntos2 = findViewById(R.id.txPuntosPilotoTotal2);
        escuderias2 = findViewById(R.id.txMejorNumEquipos2);
        titulos2 = findViewById(R.id.txTitulosPiloto2);

        if(piloto1 != null && piloto2 != null){
            setDriverImage(piloto1);
            mostrarTitulosPiloto(piloto1);
            mostrarNumeroPodios(piloto1);
            mostrarPolesPiloto(piloto1);
            mostrarTemporadasPiloto(piloto1);
            mostrarVueltasRapidasPiloto(piloto1);

            setDriverImage(piloto2);
            mostrarTitulosPiloto(piloto2);
            mostrarNumeroPodios(piloto2);
            mostrarPolesPiloto(piloto2);
            mostrarTemporadasPiloto(piloto2);
            mostrarVueltasRapidasPiloto(piloto2);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    private void setDriverImage(Driver driver){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        int startIndex = driver.getUrl().indexOf("wiki/") + 5; // Sumamos 5 para incluir "wiki/"
        String driverName = driver.getUrl().substring(startIndex);
        try {
            String decodedString = URLDecoder.decode(driverName, "UTF-8");

            WikipediaApi wikipediaApi = retrofit.create(WikipediaApi.class);
            Call<DriverImage> result;
            if(decodedString.equals("Alexander_Albon")){
                result = wikipediaApi.getImageDriver("Alex_Albon");
            }else{
                result = wikipediaApi.getImageDriver(decodedString);
            }

            result.enqueue(new Callback<DriverImage>() {
                @Override
                public void onResponse(Call<DriverImage> call, Response<DriverImage> response) {
                    if(response.isSuccessful()){
                        if(response.body().getQuery().getPages().get(0).getThumbnail()!=null){
                            driver.setUrlImage(response.body().getQuery().getPages().get(0).getThumbnail().getSource());
                        }
                        llamadaCompleta(llamadasCompletadasGeneral,totalLlamadasGeneral);
                    }else{
                        loaderGif.dismiss();
                        Snackbar.make(findViewById(R.id.layoutPrincipal), "Se ha producido un error al recuperar las fotos de los pilotos", Snackbar.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<DriverImage> call, Throwable t) {
                    loaderGif.dismiss();
                    Snackbar.make(findViewById(R.id.layoutPrincipal), "Se ha producido un error al recuperar las fotos de los pilotos", Snackbar.LENGTH_LONG).show();
                }
            });
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }



    }

    private void mostrarNumeroPodios(Driver piloto){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ergast.com/api/f1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ErgastApi ergastApi = retrofit.create(ErgastApi.class);
        Call<DriverRaceResults> result = ergastApi.getDriverRaceResults(piloto.getDriverId(),2);
        result.enqueue(new Callback<DriverRaceResults>() {
            @Override
            public void onResponse(Call<DriverRaceResults> call, Response<DriverRaceResults> response) {
                if(response.isSuccessful()){
                    piloto.setPodios(piloto.getPodios() + Integer.parseInt(response.body().getMRData().getTotal()));
                    llamadaCompleta(llamadasCompletadasGeneral, totalLlamadasGeneral);
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
                    piloto.setPodios(piloto.getPodios() + Integer.parseInt(response.body().getMRData().getTotal()));
                    piloto.setVictorias(Integer.parseInt(response.body().getMRData().getTotal()));
                    llamadaCompleta(llamadasCompletadasGeneral, totalLlamadasGeneral);
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
                    piloto.setPodios(piloto.getPodios() + Integer.parseInt(response.body().getMRData().getTotal()));
                    llamadaCompleta(llamadasCompletadasGeneral, totalLlamadasGeneral);
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

    private void mostrarTitulosPiloto(Driver piloto){
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
                    piloto.setTitulos((Integer.valueOf(response.body().getMRData().getTotal())));
                    llamadaCompleta(llamadasCompletadasGeneral, totalLlamadasGeneral);
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


    private void mostrarPolesPiloto(Driver piloto){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ergast.com/api/f1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ErgastApi ergastApi = retrofit.create(ErgastApi.class);
        Call<AllResultsForADriver> result = ergastApi.getDriverAllRaceResults(piloto.getDriverId());
        result.enqueue(new Callback<AllResultsForADriver>() {
            @Override
            public void onResponse(Call<AllResultsForADriver> call, Response<AllResultsForADriver> response) {
                if(response.isSuccessful()){
                    piloto.setPoles((int) response.body().getMRData().getRaceTable().getRaces().stream().filter(x -> x.getResults().get(0).getGrid().equals("1")).count());
                    llamadaCompleta(llamadasCompletadasGeneral, totalLlamadasGeneral);
                }else{
                    loaderGif.dismiss();
                    Snackbar.make(findViewById(R.id.layoutDetallesPiloto), "Se ha producido un error al recuperar los datos del piloto", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AllResultsForADriver> call, Throwable t) {
                loaderGif.dismiss();
                Snackbar.make(findViewById(R.id.layoutDetallesPiloto), "Se ha producido un error al recuperar los datos del piloto "+t.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void mostrarTemporadasPiloto(Driver piloto){
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
                    piloto.setTemporadas(Integer.valueOf(response.body().getMRData().getTotal()));
                    List<StandingsList> standingsList = response.body().getMRData().getStandingsTable().getStandingsLists();
                    for(StandingsList standingsResponse : response.body().getMRData().getStandingsTable().getStandingsLists()){
                        for(DriverStanding driverStanding : standingsResponse.getDriverStandings()){
                            piloto.setPuntos(piloto.getPuntos() + Double.valueOf(driverStanding.getPoints()));
                            for(Constructor constructor : driverStanding.getConstructors()){
                                if(!piloto.getEscuderias().contains(constructor.getConstructorId())){
                                    piloto.addEscuderia(constructor.getConstructorId());
                                }
                            }
                        }
                    }
                    llamadaCompleta(llamadasCompletadasGeneral, totalLlamadasGeneral);

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

    private void mostrarVueltasRapidasPiloto(Driver piloto){
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
                    piloto.setVueltasRapidas(Integer.valueOf(response.body().getMRData().getTotal()));
                    llamadaCompleta(llamadasCompletadasGeneral, totalLlamadasGeneral);
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




    private void llamadaCompleta(AtomicInteger llamadasCompletadas, int totalLlamadas) {
        if (llamadasCompletadas.incrementAndGet() == totalLlamadas) {
            if(piloto1.getUrlImage() != null)
                Picasso.get().load(piloto1.getUrlImage()).into(foto1);
            else
                foto1.setImageResource(R.drawable.blankprofile);

            if(piloto2.getUrlImage() != null)
                Picasso.get().load(piloto2.getUrlImage()).into(foto2);
            else
                foto2.setImageResource(R.drawable.blankprofile);

            // Todas las llamadas se han completado, ahora puedes actualizar el TextView
            nombre1.setText(piloto1.toString());
            nombre2.setText(piloto2.toString());

            titulos1.setText(String.valueOf(piloto1.getTitulos()));
            titulos2.setText(String.valueOf(piloto2.getTitulos()));

            victorias1.setText(String.valueOf(piloto1.getVictorias()));
            victorias2.setText(String.valueOf(piloto2.getVictorias()));

            podios1.setText(String.valueOf(piloto1.getPodios()));
            podios2.setText(String.valueOf(piloto2.getPodios()));

            poles1.setText(String.valueOf(piloto1.getPoles()));
            poles2.setText(String.valueOf(piloto2.getPoles()));

            temporadas1.setText(String.valueOf(piloto1.getTemporadas()));
            temporadas2.setText(String.valueOf(piloto2.getTemporadas()));

            vueltasRapidas1.setText(String.valueOf(piloto1.getVueltasRapidas()));
            vueltasRapidas2.setText(String.valueOf(piloto2.getVueltasRapidas()));

            puntos1.setText(String.valueOf(piloto1.getPuntos()));
            puntos2.setText(String.valueOf(piloto2.getPuntos()));

            escuderias1.setText(String.valueOf(piloto1.getEscuderias().size()));
            escuderias2.setText(String.valueOf(piloto2.getEscuderias().size()));

            loaderGif.dismiss();
        }
    }

}