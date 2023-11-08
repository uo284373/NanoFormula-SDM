package com.example.nanoformula;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nanoformula.modelo.Piloto;
import com.example.nanoformula.modelo.driversStandings.Driver;
import com.example.nanoformula.modelo.driversStandings.DriverStanding;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class PilotoDetails extends AppCompatActivity {

    private Driver piloto;
    private DriverStanding standings;
    TextView nacionalidadPiloto;
    TextView numeroPiloto;
    TextView codigoPiloto;
    TextView edadPiloto;
    ImageView fotoPiloto;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piloto_details);

        Intent intentPiloto= getIntent();
        piloto= intentPiloto.getParcelableExtra(PilotosFragment.PILOTO_SELECCIONADO);
        standings = intentPiloto.getParcelableExtra(PilotosFragment.EQUIPO_PILOTO_SELECCIONADO);
        nacionalidadPiloto = findViewById(R.id.txNacionalidadPiloto);
        numeroPiloto = findViewById(R.id.txNumero);
        codigoPiloto = findViewById(R.id.txCodigo);
        edadPiloto = findViewById(R.id.txEdad);
        fotoPiloto = findViewById(R.id.ftPiloto);
        toolbar = findViewById(R.id.toolbarPiloto);

        if(piloto != null){
            mostrarDatosPiloto();
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
}