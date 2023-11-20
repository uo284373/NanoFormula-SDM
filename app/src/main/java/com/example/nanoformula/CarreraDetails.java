package com.example.nanoformula;

import android.content.Intent;
import android.os.Bundle;

import com.example.nanoformula.API.ErgastApi;
import com.example.nanoformula.API.WikipediaApi;
import com.example.nanoformula.API.YouTubeSearchTask;
import com.example.nanoformula.modelo.driversImage.DriverImage;
import com.example.nanoformula.modelo.raceResults.Driver;
import com.example.nanoformula.modelo.raceResults.FastestLap;
import com.example.nanoformula.modelo.raceResults.RaceResults;
import com.example.nanoformula.modelo.raceResults.Result;
import com.example.nanoformula.modelo.raceSchedule.Circuit;
import com.example.nanoformula.modelo.raceSchedule.Race;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CarreraDetails extends AppCompatActivity {

    private Circuit circuit;
    private Race race;

    private RaceResults raceResults;


    Toolbar toolbar;
    TableLayout table;

    ShapeableImageView fotoCircuito;
    ImageView bandera;
    TextView nombreCarrera;
    TextView nombreCircuito;
    TextView localidad;
    TextView fecha;
    TextView hora;

    ShapeableImageView fotoPilotoGanador;
    TextView nombrePilotoGanador;
    TextView pilotoPole;
    TextView pilotoVueltaRapida;
    TextView vueltas;
    TextView tiempo;
    Loader loaderGif;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrera_details);

        loaderGif = new Loader(this);
        loaderGif.show();

        Intent intentCarrera= getIntent();
        race = intentCarrera.getParcelableExtra(CarrerasFragment.CARRERA_SELECCIONADA);
        circuit=intentCarrera.getParcelableExtra(CarrerasFragment.CIRCUITO_SELECCIONADA);;
        toolbar= findViewById(R.id.toolbarCarrera);
        fotoCircuito = findViewById(R.id.ftPilotoCircuito);
        bandera = findViewById(R.id.fotoBanderaCarrera);
        nombreCarrera = findViewById(R.id.txNombreCarrera);
        nombreCircuito = findViewById(R.id.txNombreCircuito);
        localidad= findViewById(R.id.txLocalidad);
        fecha = findViewById(R.id.txFecha);
        hora = findViewById(R.id.txHora);
        fotoPilotoGanador = findViewById(R.id.ftPilotoGanador);
        nombrePilotoGanador = findViewById(R.id.txNombreGanador);
        pilotoPole = findViewById(R.id.txPilotoPole);
        pilotoVueltaRapida = findViewById(R.id.txVueltaRapidaPiloto);
        vueltas = findViewById(R.id.txVueltasCarrera);
        tiempo = findViewById(R.id.txTiempoPiloto);
        table = findViewById(R.id.tableResultados);

        if(race!=null){
            mostrarDatosCarrera();
            Date fechaActual = new Date();
            Date fechaCarrera = convertirFechaStringADate(race.getDate());
            long timeDifference = fechaActual.getTime() - fechaCarrera.getTime();
            long hoursDifference = timeDifference / (60 * 60 * 1000);
            if (hoursDifference >= 24) {
                //new YouTubeSearchTask().execute("F1 Highlights " + race.getSeason() + " " + race.getRaceName());
                cargarDatos();
            }else {
                mostrarCarreraNoDisponible();
            }
        }
    }

    private void mostrarCarreraNoDisponible() {
        table.setVisibility(View.GONE);
        findViewById(R.id.txResultados).setVisibility(View.GONE);
        findViewById(R.id.layoutDatosGandor).setVisibility(View.GONE);
        findViewById(R.id.layoutGanador).setVisibility(View.GONE);
        findViewById(R.id.txTemporada).setVisibility(View.GONE);

        LinearLayout layoutDetallesCarrera = findViewById(R.id.layoutPrincipalDetallesCarrera);
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.banderaacuadros);
        int imageViewSize = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                126, getResources().getDisplayMetrics());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(imageViewSize, imageViewSize);
        imageView.setLayoutParams(params);

        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        TextView textView = new TextView(this);
        textView.setText(R.string.carrera_no_disponible);
        textView.setGravity(Gravity.CENTER);
        layoutDetallesCarrera.addView(imageView,4);
        layoutDetallesCarrera.addView(textView,5);
        loaderGif.dismiss();
    }

    private void rellenarTablaResultados() {
        for (Result result : raceResults.getMRData().getRaceTable().getRaces().get(0).getResults()) {            TableRow tableRow = new TableRow(this);

            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));

            TextView txPosCarrera = new TextView(this);
            TextView txNombrePiloto = new TextView(this);
            TextView txNombreEscuderiaCarrera = new TextView(this);
            TextView txPuntosCarrera = new TextView(this);
            TextView txTiempoPilotoCarrera = new TextView(this);

            txPosCarrera.setText(result.getPositionText());
            txNombrePiloto.setText(result.getDriver().getFamilyName());
            txNombreEscuderiaCarrera.setText(result.getConstructor().getName());
            txPuntosCarrera.setText(result.getPoints());
            String time = result.getTime() != null ? result.getTime().getTime() : result.getStatus();
            txTiempoPilotoCarrera.setText(time);

            // Establecer layout_weight en 1 para los TextView dinámicos
            txPosCarrera.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT, 1));
            txNombrePiloto.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT, 1));
            txNombreEscuderiaCarrera.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT, 1));
            txPuntosCarrera.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT, 1));
            txTiempoPilotoCarrera.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT, 1));

            tableRow.addView(txPosCarrera);
            tableRow.addView(txNombrePiloto);
            tableRow.addView(txNombreEscuderiaCarrera);
            tableRow.addView(txPuntosCarrera);
            tableRow.addView(txTiempoPilotoCarrera);

            table.addView(tableRow);
        }
    }


    private void cargarDatos() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ergast.com/api/f1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ErgastApi ergastApi = retrofit.create(ErgastApi.class);
        Call<RaceResults> result = ergastApi.getRaceResults(race.getRound());
        result.enqueue(new Callback<RaceResults>() {
            @Override
            public void onResponse(Call<RaceResults> call, Response<RaceResults> response) {
                if(response.isSuccessful()){
                    raceResults = response.body();
                    Driver piloto = raceResults.getMRData().getRaceTable().getRaces().get(0).getResults().get(0).getDriver();
                    int startIndex = piloto.getUrl().indexOf("wiki/") + 5; // Sumamos 5 para incluir "wiki/"
                    String driverName = piloto.getUrl().substring(startIndex);
                    try {
                        String decodedString = URLDecoder.decode(driverName, "UTF-8");
                        setDriverImage(decodedString,piloto);
                        rellenarTablaResultados();
                    }catch (UnsupportedEncodingException e){
                        e.printStackTrace();
                    }
                }else{
                    loaderGif.dismiss();
                    Snackbar.make(findViewById(R.id.layoutDetallesPiloto), "Se ha producido un error al recuperar los datos del piloto", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RaceResults> call, Throwable t) {
                Snackbar.make(findViewById(R.id.layoutDetallesPiloto), "Se ha producido un error al recuperar los datos del piloto "+t.getMessage(), Snackbar.LENGTH_LONG).show();
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
                        driver.setUrl(response.body().getQuery().getPages().get(0).getThumbnail().getSource());
                        mostrarDatosGanador();
                        loaderGif.dismiss();
                    }
                }else{
                    loaderGif.dismiss();
                    Snackbar.make(findViewById(R.id.layoutPrincipal), "Se ha producido un error al recuperar las fotos de los pilotos", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DriverImage> call, Throwable t) {
                Snackbar.make(findViewById(R.id.layoutPrincipal), "Se ha producido un error al recuperar las fotos de los pilotos", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    private void mostrarDatosCarrera() {
        toolbar.setTitle(race.getRaceName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Picasso.get().load(race.getUrl()).into(fotoCircuito);
        Picasso.get().load(circuit.getUrl()).into(bandera);
        nombreCarrera.setText(race.getRaceName());
        nombreCircuito.setText(circuit.getCircuitName());
        localidad.setText(circuit.getLocation().getLocality());
        fecha.setText(race.getDateFormat());
        hora.setText(race.getTime().substring(0,5));
    }

    private void mostrarDatosGanador() {
        Picasso.get().load(raceResults.getMRData().getRaceTable().getRaces().get(0).getResults().get(0).getDriver().getUrl()).into(fotoPilotoGanador);

        nombrePilotoGanador.setText(raceResults.getMRData().getRaceTable().getRaces().get(0).getResults().get(0).getDriver().getFamilyName());
        pilotoPole.setText(raceResults.getMRData().getRaceTable().getRaces().get(0).getResults().get(0).getDriver().getFamilyName());
        pilotoVueltaRapida.setText(pilotoVueltaRapida());
        vueltas.setText(raceResults.getMRData().getRaceTable().getRaces().get(0).getResults().get(0).getLaps());
        tiempo.setText(raceResults.getMRData().getRaceTable().getRaces().get(0).getResults().get(0).getTime().getTime());
    }

    private String pilotoVueltaRapida() {
        List<Result> resultList = raceResults.getMRData().getRaceTable().getRaces().get(0).getResults();
        Result resultWithRank1 = null;

        for (Result result : resultList) {
            FastestLap fastestLap = result.getFastestLap();

            if (fastestLap != null && "1".equals(fastestLap.getRank())) {
                resultWithRank1 = result;
                break;
            }
        }
        return resultWithRank1.getDriver().getFamilyName();
    }

    private static Date convertirFechaStringADate(String fechaString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(fechaString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}