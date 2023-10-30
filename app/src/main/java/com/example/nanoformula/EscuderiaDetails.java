package com.example.nanoformula;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nanoformula.modelo.Escuderia;

public class EscuderiaDetails extends AppCompatActivity {

    private Escuderia escuderia;
    TextView nombreEscuderia;
    TextView nacionalidadEscuderia;
    TextView titulosEscuderia;
    TextView victoriasEscuderia;
    TextView puntosEscuderia;
    TextView temporadasEscuderia;
    ImageView fotoEscuderia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escuderia_details);

        Intent intentEscuderia= getIntent();
        escuderia= intentEscuderia.getParcelableExtra(EscuderiasFragment.ESCUDERIA_SELECCIONADA);
        Log.i("escuderia",escuderia.toString());
        nombreEscuderia = findViewById(R.id.txNombreEscuderia);
        nacionalidadEscuderia = findViewById(R.id.txNacionalidadEscuderia);
        titulosEscuderia = findViewById(R.id.txTitulos);
        victoriasEscuderia = findViewById(R.id.txVictorias);
        puntosEscuderia = findViewById(R.id.txPuntos);
        temporadasEscuderia = findViewById(R.id.txTemporadas);
        fotoEscuderia = findViewById(R.id.ftEscuderia);

        if(escuderia != null){
            mostrarDatosEscuderia();
        }
    }

    private void mostrarDatosEscuderia() {
        nombreEscuderia.setText(escuderia.getName());
        nacionalidadEscuderia.setText(escuderia.getNacionalidad());
        titulosEscuderia.setText(String.valueOf(escuderia.getTitles()));
        victoriasEscuderia.setText(String.valueOf(escuderia.getWins()));
        puntosEscuderia.setText(String.valueOf(escuderia.getPoints()));
        temporadasEscuderia.setText(String.valueOf((escuderia.getSeasons())));
        fotoEscuderia.setImageResource(escuderia.getFoto());
    }

}