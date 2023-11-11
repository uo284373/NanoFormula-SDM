package com.example.nanoformula;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.nanoformula.API.ErgastApi;
import com.example.nanoformula.API.RESTCountriesApi;
import com.example.nanoformula.API.WikipediaApi;
import com.example.nanoformula.modelo.constructorsStandings.Constructor;
import com.example.nanoformula.modelo.constructorsStandings.ConstructorStanding;
import com.example.nanoformula.modelo.constructorsStandings.StandingsEscuderias;
import com.example.nanoformula.modelo.countryDetails.CountryDetail;
import com.example.nanoformula.modelo.driversImage.DriverImage;
import com.example.nanoformula.modelo.driversStandings.Driver;
import com.example.nanoformula.modelo.driversStandings.DriverStanding;
import com.example.nanoformula.modelo.driversStandings.Standings;
import com.example.nanoformula.modelo.raceImage.RaceImage;
import com.example.nanoformula.modelo.raceSchedule.Race;
import com.example.nanoformula.modelo.raceSchedule.RaceSchedule;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Standings standings;

    StandingsEscuderias constructorStandingsEscuderias;
    RaceSchedule raceSchedule;
    Toolbar toolbar;
    Loader loaderGif;
    AtomicInteger llamadasCompletadasGeneral = new AtomicInteger(0);
    int totalLlamadasGeneral = 0;

    String round;

    private boolean hasEndedDrivers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loaderGif = new Loader(this);
        loaderGif.show();

        getRaceSchedule();
        getDriversStandings();

        try {
            getConstructorsStandings();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Calendario GP");
        setSupportActionBar(toolbar);


    }

    private void llamadaCompletaGif(AtomicInteger llamadasCompletadas, int totalLlamadas) {
        if (!isFinishing() && !isDestroyed()) {
            if (llamadasCompletadas.incrementAndGet() == totalLlamadas) {
                loaderGif.dismiss();
                CarrerasFragment carrerasFragment=CarrerasFragment.newInstance(raceSchedule);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, carrerasFragment).commit();
            }
        }
    }




    private void  getRaceSchedule(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ergast.com/api/f1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ErgastApi ergastApi = retrofit.create(ErgastApi.class);
        Call<RaceSchedule> result = ergastApi.getRaceSchedule();

        result.enqueue(new Callback<RaceSchedule>() {
            @Override
            public void onResponse(Call<RaceSchedule> call, Response<RaceSchedule> response) {
                if(response.isSuccessful()){
                    raceSchedule = response.body();
                    totalLlamadasGeneral += raceSchedule.getMRData().getRaceTable().getRaces().size();
                    for(Race carrera : raceSchedule.getMRData().getRaceTable().getRaces()){
                        String countryName = carrera.getCircuit().getLocation().getCountry();
                        int startIndex = carrera.getUrl().indexOf("wiki/") + 5; // Sumamos 5 para incluir "wiki/"
                        String raceName = carrera.getUrl().substring(startIndex);
                        try {
                            String decodedString = URLDecoder.decode(countryName, "UTF-8");
                            String decodedString1 = URLDecoder.decode(raceName, "UTF-8");
                            setRaceFlag(decodedString,carrera);
                            setRaceImage(decodedString1,carrera);

                        }catch (UnsupportedEncodingException e){
                            e.printStackTrace();
                        }
                    }
                }else{
                    Snackbar.make(findViewById(R.id.layoutPrincipal), "Se ha producido un error al recuperar las carreras", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RaceSchedule> call, Throwable t) {
                Snackbar.make(findViewById(R.id.layoutPrincipal), "Se ha producido un error al recuperar las carreras", Snackbar.LENGTH_LONG).show();
            }
        });
    }


    private void getDriversStandings(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ergast.com/api/f1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ErgastApi ergastApi = retrofit.create(ErgastApi.class);
        Call<Standings> result = ergastApi.getDriversClasification();
        result.enqueue(new Callback<Standings>() {
            @Override
            public void onResponse(Call<Standings> call, Response<Standings> response) {
                if(response.isSuccessful()){
                    standings = response.body();
                    totalLlamadasGeneral += standings.getMRData().getStandingsTable().getStandingsLists().get(0).getDriverStandings().size();
                    for(DriverStanding piloto : standings.getMRData().getStandingsTable().getStandingsLists().get(0).getDriverStandings()){
                        int startIndex = piloto.getDriver().getUrl().indexOf("wiki/") + 5; // Sumamos 5 para incluir "wiki/"
                        String driverName = piloto.getDriver().getUrl().substring(startIndex);
                        try {
                            String decodedString = URLDecoder.decode(driverName, "UTF-8");
                            setDriverImage(decodedString,piloto.getDriver());
                            hasEndedDrivers = true;
                        }catch (UnsupportedEncodingException e){
                            e.printStackTrace();
                        }
                    }
                }else{
                    Snackbar.make(findViewById(R.id.layoutPrincipal), "Se ha producido un error al recuperar los pilotos", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Standings> call, Throwable t) {
                Snackbar.make(findViewById(R.id.layoutPrincipal), "Se ha producido un error al recuperar los pilotos", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void setDriverImage(String driverName, Driver driver){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WikipediaApi wikipediaApi = retrofit.create(WikipediaApi.class);
        Call<DriverImage> result = wikipediaApi.getImageDriver(driverName);

        result.enqueue(new Callback<DriverImage>() {
            @Override
            public void onResponse(Call<DriverImage> call, Response<DriverImage> response) {
                if(response.isSuccessful()){
                    if(response.body().getQuery().getPages().get(0).getThumbnail()!=null){
                        driver.setUrlImage(response.body().getQuery().getPages().get(0).getThumbnail().getSource());
                    }
                    llamadaCompletaGif(llamadasCompletadasGeneral,totalLlamadasGeneral);
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
    }

    private void getConstructorsStandings() throws InterruptedException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ergast.com/api/f1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ErgastApi ergastApi = retrofit.create(ErgastApi.class);
        Call<StandingsEscuderias> result = ergastApi.getConstructorsClasification();
        result.enqueue(new Callback<StandingsEscuderias>() {
            @Override
            public void onResponse(Call<StandingsEscuderias> call, Response<StandingsEscuderias> response) {
                if(response.isSuccessful()){
                    constructorStandingsEscuderias = response.body();
                    round = constructorStandingsEscuderias.getMRData().getStandingsTable().getStandingsLists().get(0).getRound();
                    for(ConstructorStanding escuderia : constructorStandingsEscuderias.getMRData().getStandingsTable().getStandingsLists().get(0).getConstructorStandings()){
//                        for(DriverStanding standing : standings.getMRData().getStandingsTable().getStandingsLists().get(0).getDriverStandings()){
//                            for(com.example.nanoformula.modelo.driversStandings.Constructor constructor : standing.getConstructors()){
//                                if(constructor.getConstructorId().equals(escuderia.getConstructor().getConstructorId())){
//                                    escuderia.addDriversName(standing.getDriver().getGivenName() + ". " + standing.getDriver().getFamilyName());
//                                }
//                            }
//                        }
                        escuderia.setRound(round);
                        int startIndex = escuderia.getConstructor().getUrl().indexOf("wiki/") + 5; // Sumamos 5 para incluir "wiki/"
                        String constructorName = escuderia.getConstructor().getUrl().substring(startIndex);
                        try {
                            String decodedString = URLDecoder.decode(constructorName, "UTF-8");
                            //setConstructorImage(decodedString,escuderia.getConstructor());
                        }catch (UnsupportedEncodingException e){
                            e.printStackTrace();
                        }
                    }
                }else{
                    Snackbar.make(findViewById(R.id.layoutPrincipal), "Se ha producido un error al recuperar los pilotos", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<StandingsEscuderias> call, Throwable t) {
                Snackbar.make(findViewById(R.id.layoutPrincipal), "Se ha producido un error al recuperar los pilotos", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void setConstructorImage(String driverName, Constructor constructor){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WikipediaApi wikipediaApi = retrofit.create(WikipediaApi.class);
        Call<DriverImage> result = wikipediaApi.getImageDriver(driverName);

        result.enqueue(new Callback<DriverImage>() {
            @Override
            public void onResponse(Call<DriverImage> call, Response<DriverImage> response) {
                if(response.isSuccessful()){
                    if(response.body().getQuery().getPages().get(0).getThumbnail()!=null){
                        constructor.setUrlImage(response.body().getQuery().getPages().get(0).getThumbnail().getSource());
                    }
                }else{
                    Snackbar.make(findViewById(R.id.layoutPrincipal), "Se ha producido un error al recuperar las fotos de los pilotos", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DriverImage> call, Throwable t) {
                Snackbar.make(findViewById(R.id.layoutPrincipal), "Se ha producido un error al recuperar las fotos de los pilotos", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void setRaceImage(String raceName, Race race){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WikipediaApi wikipediaApi = retrofit.create(WikipediaApi.class);
        Call<RaceImage> result = wikipediaApi.getImageRace(raceName);

        result.enqueue(new Callback<RaceImage>() {
            @Override
            public void onResponse(Call<RaceImage> call, Response<RaceImage> response) {
                if(response.isSuccessful()){
                    if(response.body().getQuery().getPages().get(0).getThumbnail()!=null){
                        race.setUrl(response.body().getQuery().getPages().get(0).getThumbnail().getSource());
                    }
                    llamadaCompletaGif(llamadasCompletadasGeneral,totalLlamadasGeneral);
                }else{
                    loaderGif.dismiss();
                    Snackbar.make(findViewById(R.id.layoutPrincipal), "Se ha producido un error al recuperar las fotos de la carrera", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RaceImage> call, Throwable t) {
                loaderGif.dismiss();
                Snackbar.make(findViewById(R.id.layoutPrincipal), "Se ha producido un error al recuperar las fotos de la carrera", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void setRaceFlag(String countryName, Race race){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://restcountries.com/v3.1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RESTCountriesApi restCountriesApi = retrofit.create(RESTCountriesApi.class);
        Call<List<CountryDetail>> result = restCountriesApi.getCountryCode(countryName);

        result.enqueue(new Callback<List<CountryDetail>>() {
            @Override
            public void onResponse(Call<List<CountryDetail>> call, Response<List<CountryDetail>> response) {
                if(response.isSuccessful()){
                    if(response.body().get(0).getFlags().getPng()!=null){
                        race.getCircuit().setUrl(response.body().get(0).getFlags().getPng());
                    }
                }else{
                    loaderGif.dismiss();
                    Snackbar.make(findViewById(R.id.layoutPrincipal), "Se ha producido un error al recuperar las fotos de la bandera", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<CountryDetail>> call, Throwable t) {
                loaderGif.dismiss();
                Snackbar.make(findViewById(R.id.layoutPrincipal), "Se ha producido un error al recuperar las fotos de la bandera: "+t.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        /* Cuando se selecciona uno de los botones / ítems*/
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();

            /* Según el caso, crearemos un Fragmento u otro */
            if (itemId == R.id.carrerasFragment)
            {
                CarrerasFragment carrerasFragment=CarrerasFragment.newInstance(raceSchedule);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, carrerasFragment).commit();
                toolbar = findViewById(R.id.toolbar);
                toolbar.setTitle("Calendario GP");
                return true;
            }

            if (itemId == R.id.pilotosFragment)
            {
                PilotosFragment pilotosFragment=PilotosFragment.newInstance(standings);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, pilotosFragment).commit();
                toolbar = findViewById(R.id.toolbar);
                toolbar.setTitle("Clasificacion de Pilotos");
                return true;
            }

            if (itemId == R.id.escuderiasFragment)
            {
                EscuderiasFragment escuderiasFragment = EscuderiasFragment.newInstance(constructorStandingsEscuderias);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, escuderiasFragment).commit();
                toolbar = findViewById(R.id.toolbar);
                toolbar.setTitle("Clasificacion de Constructores");
                return true;
            }
            throw new IllegalStateException("Unexpected value: " + item.getItemId());
        };
    };
}