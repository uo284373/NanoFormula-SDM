package com.example.nanoformula;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nanoformula.modelo.allDrivers.AllDrivers;
import com.example.nanoformula.modelo.allDrivers.Driver;

import java.util.List;


public class ComparativaFragment extends Fragment {

    public static final String PILOTO_1 = "piloto-1";
    public static final String PILOTO_2 = "piloto-2";

    private static final String ALL_DRIVERS = "param1";

    private AllDrivers allDrivers;

    private Button buttonComparativa;

    private Spinner piloto1;

    private Spinner piloto2;

    public ComparativaFragment() {
        // Required empty public constructor
    }

    public static ComparativaFragment newInstance(AllDrivers allDrivers) {
        ComparativaFragment fragment = new ComparativaFragment();
        Bundle args = new Bundle();
        args.putParcelable(ALL_DRIVERS, allDrivers);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            allDrivers = (AllDrivers) getArguments().get(ALL_DRIVERS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_comparativa, container, false);
        buttonComparativa = root.findViewById(R.id.buttonComparativa);


        // Referencia al Spinner en tu diseño XML
        piloto1 = root.findViewById(R.id.spinnerPiloto1);
        piloto2 = root.findViewById(R.id.spinnerPiloto2);

        // Crea un adaptador para el Spinner
        ArrayAdapter<Driver> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, allDrivers.getMRData().getDriverTable().getDrivers());

        // Especifica el diseño para mostrar la lista de opciones
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Establece el adaptador en el Spinner
        piloto1.setAdapter(adapter);
        piloto2.setAdapter(adapter);

        // Agrega un listener para manejar la selección del Spinner
        piloto1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Acciones a realizar cuando se selecciona un elemento
                Driver nombreSeleccionado = allDrivers.getMRData().getDriverTable().getDrivers().get(position);
                Toast.makeText(getContext(), "Seleccionaste: " + nombreSeleccionado, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Acciones a realizar cuando no se selecciona ningún elemento
            }
        });

        piloto1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Acciones a realizar cuando se selecciona un elemento
                Driver nombreSeleccionado = allDrivers.getMRData().getDriverTable().getDrivers().get(position);
                Toast.makeText(getContext(), "Seleccionaste: " + nombreSeleccionado, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Acciones a realizar cuando no se selecciona ningún elemento
            }
        });


        buttonComparativa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ComparativaFragment.this.getContext(),ComparativaPilotos.class);
                intent.putExtra(PILOTO_1, (Driver) piloto1.getSelectedItem());
                intent.putExtra(PILOTO_2, (Driver) piloto2.getSelectedItem());
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
            }
        });
        return root;
    }
}