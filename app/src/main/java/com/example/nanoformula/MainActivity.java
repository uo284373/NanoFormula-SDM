package com.example.nanoformula;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nanoformula.API.ErgastApi;
import com.example.nanoformula.API.WikipediaApi;
import com.example.nanoformula.modelo.Escuderia;
import com.example.nanoformula.modelo.allDrivers.AllDrivers;
import com.example.nanoformula.modelo.constructorsStandings.Constructor;
import com.example.nanoformula.modelo.constructorsStandings.ConstructorStanding;
import com.example.nanoformula.modelo.constructorsStandings.StandingsEscuderias;
import com.example.nanoformula.modelo.Escuderia;
import com.example.nanoformula.modelo.countryDetails.CountryDetail;
import com.example.nanoformula.modelo.Carrera;
import com.example.nanoformula.modelo.driversForConstructor.DriversByConstructor;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    AllDrivers allDrivers;
    Standings standings;

    StandingsEscuderias constructorStandingsEscuderias;
    RaceSchedule raceSchedule;
    Toolbar toolbar;
    Loader loaderGif;
    AtomicInteger llamadasCompletadasGeneral = new AtomicInteger(0);
    int totalLlamadasGeneral = 0;

    String season = "current";

    String round;

    String window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loaderGif = new Loader(this);
        loaderGif.show();

        getRaceSchedule(season);
        getDriversStandings(season);
        getConstructorsStandings(season);
        getAllDrivers();


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Calendario GP");
        setSupportActionBar(toolbar);

        Button btnTemporada = findViewById(R.id.btnSeleccionarTemporada);

        btnTemporada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(new ContextThemeWrapper(MainActivity.this,R.style.AlertDialogCustom));
                dialogo1.setTitle("Seleccione una temporada");
                ArrayList<String> temps = new ArrayList<>();
                if(((TextView)findViewById(R.id.tituloToolbarPiloto)).getText().equals("Clasificacion de Pilotos")){
                    for(int i = 1950; i < 2024; i++){
                        temps.add(String.valueOf(i));
                    }
                } else{
                    for(int i = 1958; i < 2024; i++){
                        temps.add(String.valueOf(i));
                    }
                }


                CharSequence[] itemsArray = temps.toArray(new CharSequence[temps.size()]);
                dialogo1.setSingleChoiceItems(itemsArray, -1, (dialog, which) -> {
                    CharSequence temp = itemsArray[which];
                    btnTemporada.setText(temp);
                    season = temp.toString();
                    loaderGif.show();
                    getRaceSchedule(temp.toString());
                    getDriversStandings(temp.toString());
                    getConstructorsStandings(temp.toString());
                    dialog.dismiss();
                });
                dialogo1.show();
            }
        });

        Button btnExportarCSV = findViewById(R.id.btnExportarCSV);
        btnExportarCSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportarCSV();
            }
        });
    }

    private void exportarCSV() {
        CarrerasFragment carrerasFragment = (CarrerasFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (carrerasFragment != null) {
            carrerasFragment.exportarCSV();
        }
    }

    private void toggleExportarCSVButtonVisibility(boolean show) {
        Button btnExportarCSV = findViewById(R.id.btnExportarCSV);
        btnExportarCSV.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void toggleTemporadaButtonVisibility(boolean show) {
        Button btnTemporada = findViewById(R.id.btnSeleccionarTemporada);
        btnTemporada.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void llamadaCompletaGif(AtomicInteger llamadasCompletadas, int totalLlamadas) {
        if (!isFinishing() && !isDestroyed()) {
            if (llamadasCompletadas.incrementAndGet() == totalLlamadas) {
                if(window == null || window.equals("carreras")){
                    CarrerasFragment carrerasFragment=CarrerasFragment.newInstance(raceSchedule, season);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, carrerasFragment).commit();
                } else if (window.equals("pilotos")) {
                    PilotosFragment pilotosFragment=PilotosFragment.newInstance(standings, season);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, pilotosFragment).commit();
                } else {
                    EscuderiasFragment escuderiasFragment = EscuderiasFragment.newInstance(constructorStandingsEscuderias, season);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, escuderiasFragment).commit();
                }

                loaderGif.dismiss();
            }
        }
    }

    private void getAllDrivers(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ergast.com/api/f1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ErgastApi ergastApi = retrofit.create(ErgastApi.class);
        Call<AllDrivers> result = ergastApi.getAllDrivers();
        result.enqueue(new Callback<AllDrivers>() {
            @Override
            public void onResponse(Call<AllDrivers> call, Response<AllDrivers> response) {
                if(response.isSuccessful()){
                    allDrivers = response.body();

                }else{
                    Snackbar.make(findViewById(R.id.layoutPrincipal), "Se ha producido un error al recuperar los pilotos", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AllDrivers> call, Throwable t) {
                Snackbar.make(findViewById(R.id.layoutPrincipal), "Se ha producido un error al recuperar los pilotos", Snackbar.LENGTH_LONG).show();
            }
        });
    }









    private void  getRaceSchedule(String season){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ergast.com/api/f1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ErgastApi ergastApi = retrofit.create(ErgastApi.class);
        Call<RaceSchedule> result = ergastApi.getRaceSchedule(season);

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


    private void getDriversStandings(String season){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ergast.com/api/f1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ErgastApi ergastApi = retrofit.create(ErgastApi.class);
        Call<Standings> result = ergastApi.getDriversClasification(season);
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
        Call<DriverImage> result;
        if(driverName.equals("Alexander_Albon")){
             result = wikipediaApi.getImageDriver("Alex_Albon");
        }else{
             result = wikipediaApi.getImageDriver(driverName);
        }

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

    private void getConstructorsStandings(String season) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ergast.com/api/f1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ErgastApi ergastApi = retrofit.create(ErgastApi.class);
        Call<StandingsEscuderias> result = ergastApi.getConstructorsClasification(season);
        result.enqueue(new Callback<StandingsEscuderias>() {
            @Override
            public void onResponse(Call<StandingsEscuderias> call, Response<StandingsEscuderias> response) {
                if(response.isSuccessful()){
                    constructorStandingsEscuderias = response.body();
                    if(constructorStandingsEscuderias.getMRData().getStandingsTable().getStandingsLists().size() > 0) {
                        totalLlamadasGeneral += constructorStandingsEscuderias.getMRData().getStandingsTable().getStandingsLists().get(0).getConstructorStandings().size();

                        round = constructorStandingsEscuderias.getMRData().getStandingsTable().getStandingsLists().get(0).getRound();
                        for (ConstructorStanding escuderia : constructorStandingsEscuderias.getMRData().getStandingsTable().getStandingsLists().get(0).getConstructorStandings()) {
                            setConstructorDrivers(constructorStandingsEscuderias.getMRData().getStandingsTable().getSeason(), escuderia);
                            escuderia.setRound(round);
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

    private void setConstructorDrivers(String season, ConstructorStanding escuderia) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ergast.com/api/f1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ErgastApi ergastApi = retrofit.create(ErgastApi.class);

        Call<DriversByConstructor> result = ergastApi.getDriversBySeasonAndConstructor(season, escuderia.getConstructor().getConstructorId());

        result.enqueue(new Callback<DriversByConstructor>() {
            @Override
            public void onResponse(Call<DriversByConstructor> call, Response<DriversByConstructor> response) {
                if(response.isSuccessful()){
                    DriversByConstructor drivers = response.body();

                    for(com.example.nanoformula.modelo.driversForConstructor.Driver driver : drivers.getMRData().getDriverTable().getDrivers()){
                        escuderia.addDriversName(driver.getFamilyName());

                    }

                    llamadaCompletaGif(llamadasCompletadasGeneral,totalLlamadasGeneral);

                }else{
                    Snackbar.make(findViewById(R.id.layoutPrincipal), "Se ha producido un error al recuperar los pilotos", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DriversByConstructor> call, Throwable t) {

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
        String countryCode = getCountryCode(countryName);
        String url = String.format(Locale.US, "https://www.flagsapi.com/%s/%s/%d.png", countryCode, "flat", 64);
        race.getCircuit().setUrl(url);
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
                CarrerasFragment carrerasFragment=CarrerasFragment.newInstance(raceSchedule, season);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, carrerasFragment).commit();
                TextView tx = findViewById(R.id.tituloToolbarPiloto);
                tx.setText("Calendario GP");
                toggleExportarCSVButtonVisibility(true);
                toggleTemporadaButtonVisibility(true);
                window = "carreras";
                return true;
            }

            else if (itemId == R.id.pilotosFragment)
            {
                PilotosFragment pilotosFragment=PilotosFragment.newInstance(standings, season);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, pilotosFragment).commit();
                TextView tx = findViewById(R.id.tituloToolbarPiloto);
                tx.setText("Clasificacion de Pilotos");

                toggleExportarCSVButtonVisibility(false);
                toggleTemporadaButtonVisibility(true);
                window = "pilotos";
                return true;
            }

            else if (itemId == R.id.escuderiasFragment)
            {
                EscuderiasFragment escuderiasFragment = EscuderiasFragment.newInstance(constructorStandingsEscuderias, season);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, escuderiasFragment).commit();
                TextView tx = findViewById(R.id.tituloToolbarPiloto);
                tx.setText("Clasificacion de Constructores");

                toggleExportarCSVButtonVisibility(false);
                toggleTemporadaButtonVisibility(true);
                window = "escuderias";
                return true;
            }

            else if (itemId == R.id.comparativaFragment)
            {
                ComparativaFragment comparativaFragment = ComparativaFragment.newInstance(allDrivers);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, comparativaFragment).commit();
                TextView tx = findViewById(R.id.tituloToolbarPiloto);
                tx.setText("Comparativa de Pilotos");
                toggleExportarCSVButtonVisibility(false);
                toggleTemporadaButtonVisibility(false);
                return true;
            }else {
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
            }
        };
    };

    public static String getCountryCode(String countryName) {
        Map<String, String> specialCases = createSpecialCasesMap();

        for (Map.Entry<String, String> entry : specialCases.entrySet()) {
            if (countryName.equalsIgnoreCase(entry.getKey())) {
                return entry.getValue();
            }
        }

        Set<String> allCountryCodes = getAllCountryCodes();

        for (String code : allCountryCodes) {
            Locale locale = new Locale("", code);
            String displayName = locale.getDisplayCountry(Locale.ENGLISH);
            String iso3Country = locale.getISO3Country();

            if (countryName.equalsIgnoreCase(displayName)
                    || countryName.equalsIgnoreCase(iso3Country)
                    || countryName.equalsIgnoreCase(locale.getCountry())) {
                return code;
            }
        }

        return "";
    }

    private static Set<String> getAllCountryCodes() {
        Set<String> countryCodes = new HashSet<>();

        String[] isoCountries = Locale.getISOCountries();
        Collections.addAll(countryCodes, isoCountries);

        return countryCodes;
    }

    private static Map<String, String> createSpecialCasesMap() {
        Map<String, String> specialCases = new HashMap<>();
        specialCases.put("UK", "GB");   // Reino Unido
        specialCases.put("UAE", "AE");  // Emiratos Árabes Unidos
        return specialCases;
    }
}


