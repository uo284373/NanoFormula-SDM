package com.example.nanoformula;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private CarrerasFragment carrerasFragment = new CarrerasFragment();
    private PilotosFragment pilotosFragment = new PilotosFragment();
    private ConstructoresFragment constructoresFragment = new ConstructoresFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.carrerasFragment){
                    loadFragment(carrerasFragment);
                    return true;
                }else if(item.getItemId()==R.id.pilotosFragment){
                    loadFragment(pilotosFragment);
                    return true;
                }else if(item.getItemId()==R.id.constructoresFragment){
                    loadFragment(constructoresFragment);
                    return true;
                }
                return false;
            }
        });
        loadFragment(carrerasFragment);
    }


    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.containerView,fragment);
        transaction.commit();
    }
}