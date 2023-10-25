package com.example.nanoformula;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nanoformula.modelo.Piloto;

public class PilotoDetails extends AppCompatActivity {

    private Piloto piloto;
    TextView nombrePiloto;
    TextView nacionalidadPiloto;
    TextView numeroPiloto;
    TextView codigoPiloto;
    TextView edadPiloto;
    ImageView fotoPiloto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piloto_details);

        Intent intentPiloto= getIntent();
        piloto= intentPiloto.getParcelableExtra(PilotosFragment.PILOTO_SELECCIONADO);
        Log.i("piloto",piloto.toString());
        nombrePiloto = findViewById(R.id.txNombrePiloto);
        nacionalidadPiloto = findViewById(R.id.txNacionalidadPiloto);
        numeroPiloto = findViewById(R.id.txNumero);
        codigoPiloto = findViewById(R.id.txCodigo);
        edadPiloto = findViewById(R.id.txEdad);
        fotoPiloto = findViewById(R.id.ftPiloto);

        if(piloto != null){
            mostrarDatosPiloto();
        }
    }

    private void mostrarDatosPiloto() {
        nombrePiloto.setText(piloto.getName());
        nacionalidadPiloto.setText(piloto.getNacionalidad());
        numeroPiloto.setText(String.valueOf(piloto.getNúmero()));
        codigoPiloto.setText(piloto.getCodigo());
        edadPiloto.setText(String.valueOf(piloto.getEdad()));
        fotoPiloto.setImageResource(piloto.getFoto());
    }
}