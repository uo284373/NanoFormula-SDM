package com.example.nanoformula;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.nanoformula.API.ErgastApi;
import com.example.nanoformula.modelo.Carrera;
import com.example.nanoformula.API.Api;
import com.example.nanoformula.modelo.Escuderia;
import com.example.nanoformula.modelo.Piloto;
import com.example.nanoformula.modelo.driversStandings.Standings;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Driver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Standings standings;
    List<Escuderia> escuderias = new ArrayList<>();
    List<Carrera> carreras = new ArrayList<>();
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDriversStandings();
        rellenarListaEscuderias();
        rellenarListaCarreras();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        CarrerasFragment carrerasFragment=CarrerasFragment.newInstance(carreras);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, carrerasFragment).commit();
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Calendario GP");
        setSupportActionBar(toolbar);


    }


//    private void rellenarListaPilotos(){
//        pilotos.add(new Piloto(1,"Fernando Alonso","Aston Martin",333,14,R.drawable.alonso,"Spanish",14,"ALO",42));
//        pilotos.add(new Piloto(2,"Max Verstapen","Red Bull",150,2,R.drawable.verstappen,"",1,"",12));
//        pilotos.add(new Piloto(3,"Carlos Sainz","Ferrari",140,1,R.drawable.carlossainz,"",1,"",12));
//        pilotos.add(new Piloto(4,"Lewis Hamilton","Mercedes",120,0,R.drawable.hamilton,"",1,"",12));
//        pilotos.add(new Piloto(5,"Charles Leclerc","Ferrari",100,0,R.drawable.leclerc,"",1,"",12));
//        pilotos.add(new Piloto(6,"George Rusell","Mercedes",90,0,R.drawable.rusell,"",1,"",12));
//        pilotos.add(new Piloto(7,"Checo Pérez","Red Bull",87,0,R.drawable.checoperez,"",1,"",12));
//        pilotos.add(new Piloto(8,"Lando Norris","McLaren",71,0,R.drawable.landonorris,"",1,"",12));
//        pilotos.add(new Piloto(9,"Lance Stroll","Aston Martin",56,0,R.drawable.lancestroll,"",1,"",12));
//        pilotos.add(new Piloto(10,"Oscar Piastri","McLaren",44,0,R.drawable.piastri,"",1,"",12));
//
//    }

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

    private void rellenarListaCarreras(){
        carreras.add(new Carrera(1,"Bahrain Grand Prix","Bahrain Interntional Circuit",
                "Sakhir - Bahrain", "05-03-23", R.drawable.bahrain));
        carreras.add(new Carrera(2,"Saudi Arabia Grand Prix","Jeddah Corniche Circuit",
                "Jeddah - Saudi Arabia","19-03-23",R.drawable.saudi_arabia));
        carreras.add(new Carrera(3,"Australian Grand Prix","Albert Park Grand Circuit",
                "Melbourne - Australia","02-04-23",R.drawable.australia));
        carreras.add(new Carrera(4,"Azerbaijan Grand Prix","Baku City Circuit",
                "Baku - Azerbaijan","30-04-23",R.drawable.azerbaijan));
        carreras.add(new Carrera(5,"Miami Grand Prix","Miami International Autodrome",
                "Miami - USA","07-05-23",R.drawable.united_states));
        carreras.add(new Carrera(6,"Monaco Grand Prix","Circuit de Monaco",
                "Monte-Carlo - Monaco","28-05-23",R.drawable.monaco));
        carreras.add(new Carrera(7,"Spanish Grand Prix","Circuit de Barcelona-Catalunya",
                "Montmeló - Spain","04-06-23",R.drawable.spain));
        carreras.add(new Carrera(8,"Canadian Grand Prix","Circuit Gilles Villenueve",
                "Montreal - Canada","18-06-23",R.drawable.canada));
        carreras.add(new Carrera(9,"Austrian Grand Prix","Red Bull Ring",
                "Spielberg - Austria","02-07-23",R.drawable.austria));
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


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        /* Cuando se selecciona uno de los botones / ítems*/
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();

            /* Según el caso, crearemos un Fragmento u otro */
            if (itemId == R.id.carrerasFragment)
            {
                CarrerasFragment carrerasFragment=CarrerasFragment.newInstance(carreras);

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
}