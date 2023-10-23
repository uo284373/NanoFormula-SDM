package com.example.nanoformula;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.nanoformula.modelo.Piloto;

public class PilotoDetails extends AppCompatActivity {

    private Piloto piloto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piloto_details);

        Intent intentPiloto= getIntent();
        piloto= intentPiloto.getParcelableExtra(PilotosFragment.PILOTO_SELECCIONADO);
    }
}