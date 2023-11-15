package com.example.nanoformula;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.nanoformula.API.ErgastApi;
import com.example.nanoformula.API.WikipediaApi;
import com.example.nanoformula.API.Api;
import com.example.nanoformula.modelo.Escuderia;
import com.example.nanoformula.modelo.driversImage.DriverImage;
import com.example.nanoformula.modelo.driversStandings.Driver;
import com.example.nanoformula.modelo.driversStandings.DriverStanding;
import com.example.nanoformula.modelo.driversStandings.Standings;
import com.example.nanoformula.modelo.raceImage.RaceImage;
import com.example.nanoformula.modelo.raceSchedule.Race;
import com.example.nanoformula.modelo.raceSchedule.RaceSchedule;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    Standings standings;
    List<Escuderia> escuderias = new ArrayList<>();
    RaceSchedule raceSchedule;
    Toolbar toolbar;
    Loader loaderGif;
    AtomicInteger llamadasCompletadasGeneral = new AtomicInteger(0);
    int totalLlamadasGeneral = 8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loaderGif = new Loader(this);
        loaderGif.show();

        getDriversStandings();
        rellenarListaEscuderias();
        getRaceSchedule();

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
            }
        }
    }


    private void rellenarListaEscuderias(){
        new NetworkTask().execute();


//        escuderias.add(new Escuderia(1,"Aston Martin", "Británica",new HashSet<>(Arrays.asList("Alonso", "Stroll")),389,14, 0, 5, R.drawable.astonmartin));
//        escuderias.add(new Escuderia(2,"Ferrari", "Italiana", new HashSet<>(Arrays.asList("Sainz", "Leclerc")),240,1, 16, 66, R.drawable.ferrari));
//        escuderias.add(new Escuderia(3,"Red Bull", "Austríaca", new HashSet<>(Arrays.asList("Verstappen", "Pérez")),237,2, 6, 19, R.drawable.redbull));
//        escuderias.add(new Escuderia(4,"Mercedes", "Alemana", new HashSet<>(Arrays.asList("Hamilton", "Russel")),210,0, 8, 14, R.drawable.mercedes));
//        escuderias.add(new Escuderia(5,"McLaren", "Británica", new HashSet<>(Arrays.asList("Norris", "Piastri")),115,0, 8, 54, R.drawable.mclaren));
    }

    private class NetworkTask extends AsyncTask<Void, Void, String[]> {
        @Override
        protected String[] doInBackground(Void... params) {
            // Realiza la operación de red aquí (por ejemplo, una solicitud HTTP)
            // Devuelve el resultado como una cadena (o el tipo de datos que necesites)

            String escuderias = Api.makeQuery("https://ergast.com/api/f1/current/constructorstandings.json");
            String pilotos = Api.makeQuery("https://ergast.com/api/f1/current/driverstandings.json");

            return new String[] {escuderias, pilotos};
        }


        @Override
        protected void onPostExecute(String[] result) {
            // Actualiza la interfaz de usuario con el resultado de la operación de red
            // Esto se ejecutará en el hilo UI

            try {
                JSONArray jsonEscuderias = new JSONObject(result[0])
                        .getJSONObject("MRData")
                        .getJSONObject("StandingsTable")
                        .getJSONArray("StandingsLists")
                        .getJSONObject(0)
                        .getJSONArray("ConstructorStandings");

                JSONArray jsonPilotos = new JSONObject(result[1])
                        .getJSONObject("MRData")
                        .getJSONObject("StandingsTable")
                        .getJSONArray("StandingsLists")
                        .getJSONObject(0)
                        .getJSONArray("DriverStandings");


                for(int i = 0; i < jsonEscuderias.length(); i++){
                    JSONObject escuderia = jsonEscuderias.getJSONObject(i);
                    Set<String> pilots = new HashSet<>();

                    for (int j = 0; j < jsonPilotos.length(); j++) {
                        JSONObject json = jsonPilotos.getJSONObject(j);

                        if(json.getJSONArray("Constructors").getJSONObject(0).getString("name").equals(escuderia.getJSONObject("Constructor").getString("name"))){
                            pilots.add(json.getJSONObject("Driver").getString("givenName").substring(0, 1) + ". " + json.getJSONObject("Driver").getString("familyName"));
                        }

                    }
                    escuderias.add(new Escuderia(escuderia.getInt("position"), escuderia.getJSONObject("Constructor").getString("name"),
                            escuderia.getJSONObject("Constructor").getString("nationality"), pilots, escuderia.getInt("points")));
                }


            } catch (JSONException e) {
                throw new RuntimeException(e);
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
                    totalLlamadasGeneral = raceSchedule.getMRData().getRaceTable().getRaces().size();
                    for(Race carrera : raceSchedule.getMRData().getRaceTable().getRaces()){
                        String countryName = carrera.getCircuit().getLocation().getCountry();
                        int startIndex = carrera.getUrl().indexOf("wiki/") + 5; // Sumamos 5 para incluir "wiki/"
                        String raceName = carrera.getUrl().substring(startIndex);
                        try {
                            String decodedString = URLDecoder.decode(countryName, "UTF-8");
                            String decodedString1 = URLDecoder.decode(raceName, "UTF-8");
                            setRaceFlag(decodedString,carrera);
                            setRaceImage(decodedString1,carrera);
                            CarrerasFragment carrerasFragment=CarrerasFragment.newInstance(raceSchedule);
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, carrerasFragment).commit();
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
                    totalLlamadasGeneral = standings.getMRData().getStandingsTable().getStandingsLists().get(0).getDriverStandings().size();
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
                EscuderiasFragment escuderiasFragment = EscuderiasFragment.newInstance(escuderias);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, escuderiasFragment).commit();
                toolbar = findViewById(R.id.toolbar);
                toolbar.setTitle("Clasificacion de Constructores");
                return true;
            }
            throw new IllegalStateException("Unexpected value: " + item.getItemId());
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


