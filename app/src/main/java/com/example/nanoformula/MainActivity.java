package com.example.nanoformula;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.nanoformula.modelo.Piloto;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Piloto> pilotos = new ArrayList<Piloto>();
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rellenarListaPilotos();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        CarrerasFragment carrerasFragment=CarrerasFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, carrerasFragment).commit();
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Calendario GP");
        setSupportActionBar(toolbar);


    }


    private void rellenarListaPilotos(){
        pilotos.add(new Piloto(1,"Fernando Alonso","Aston Martin",333,14,R.drawable.fernandoalonso));
        pilotos.add(new Piloto(2,"Max Verstapen","Red Bull",300,2,R.drawable.maxverstappen));
        pilotos.add(new Piloto(1,"Fernando Alonso","Aston Martin",333,14,R.drawable.fernandoalonso));
        pilotos.add(new Piloto(2,"Max Verstapen","Red Bull",300,2,R.drawable.maxverstappen));
        pilotos.add(new Piloto(1,"Fernando Alonso","Aston Martin",333,14,R.drawable.fernandoalonso));
        pilotos.add(new Piloto(2,"Max Verstapen","Red Bull",300,2,R.drawable.maxverstappen));
        pilotos.add(new Piloto(1,"Fernando Alonso","Aston Martin",333,14,R.drawable.fernandoalonso));
        pilotos.add(new Piloto(2,"Max Verstapen","Red Bull",300,2,R.drawable.maxverstappen));
        pilotos.add(new Piloto(1,"Fernando Alonso","Aston Martin",333,14,R.drawable.fernandoalonso));
        pilotos.add(new Piloto(2,"Max Verstapen","Red Bull",300,2,R.drawable.maxverstappen));
        pilotos.add(new Piloto(1,"Fernando Alonso","Aston Martin",333,14,R.drawable.fernandoalonso));
        pilotos.add(new Piloto(2,"Max Verstapen","Red Bull",300,2,R.drawable.maxverstappen));
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
                CarrerasFragment carrerasFragment=CarrerasFragment.newInstance();

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, carrerasFragment).commit();
                toolbar = findViewById(R.id.toolbar);
                toolbar.setTitle("Calendario GP");
                return true;
            }

            if (itemId == R.id.pilotosFragment)
            {
                PilotosFragment pilotosFragment=PilotosFragment.newInstance(pilotos);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, pilotosFragment).commit();
                toolbar = findViewById(R.id.toolbar);
                toolbar.setTitle("Clasificacion de Pilotos");
                return true;
            }

            if (itemId == R.id.constructoresFragment)
            {
                ConstructoresFragment constructoresFragment=ConstructoresFragment.newInstance();

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, constructoresFragment).commit();
                toolbar = findViewById(R.id.toolbar);
                toolbar.setTitle("Clasificacion de Constructores");
                return true;
            }
            throw new IllegalStateException("Unexpected value: " + item.getItemId());
        };
    };
}