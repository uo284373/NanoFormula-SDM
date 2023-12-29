package com.example.nanoformula.vista.comparativa;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.nanoformula.API.WikipediaApi;
import com.example.nanoformula.R;
import com.example.nanoformula.modelo.allDrivers.AllDrivers;
import com.example.nanoformula.modelo.allDrivers.Driver;
import com.example.nanoformula.modelo.driversImage.DriverImage;
import com.example.nanoformula.util.Loader;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ComparativaFragment extends Fragment {

    public static final String PILOTO_1 = "piloto-1";
    public static final String PILOTO_2 = "piloto-2";

    private static final String ALL_DRIVERS = "param1";

    private AllDrivers allDrivers;

    private Button buttonComparativa;

    private Spinner piloto1;

    private Spinner piloto2;
    private TextView txNombrePiloto1;
    private TextView txNombrePiloto2;
    private TextView txFechaNacimientoPiloto1;
    private TextView txFechaNacimientoPiloto2;
    private TextView txNacionalidadPiloto1;
    private TextView txNacionalidadPiloto2;
    private Driver driver1;
    private Driver driver2;
    private ImageView foto1;
    private ImageView foto2;

    Loader loaderGif;

    AtomicInteger llamadasCompletadasGeneral = new AtomicInteger(0);
    int totalLlamadasGeneral = 2;



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

        loaderGif = new Loader(root.getContext());
        loaderGif.show();
        txNombrePiloto1 = root.findViewById(R.id.txNombrePiloto);
        txNombrePiloto2 = root.findViewById(R.id.txNombrePiloto2);

        txFechaNacimientoPiloto1 = root.findViewById(R.id.txFechaNacimientoPiloto);
        txFechaNacimientoPiloto2 = root.findViewById(R.id.txFechaNacimientoPiloto2);

        txNacionalidadPiloto1 = root.findViewById(R.id.txNacionalidadPiloto);
        txNacionalidadPiloto2 = root.findViewById(R.id.txNacionalidadPiloto2);

        foto1 = root.findViewById(R.id.ftComparativaPiloto1);
        foto2 = root.findViewById(R.id.ftComparativaPiloto2);

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

                loaderGif.show();
                driver1 = nombreSeleccionado;
                setDriverImage(nombreSeleccionado);
                txNombrePiloto1.setText(nombreSeleccionado.getGivenName() + " "+ nombreSeleccionado.getFamilyName());
                txFechaNacimientoPiloto1.setText(nombreSeleccionado.getDateOfBirth());
                txNacionalidadPiloto1.setText(nombreSeleccionado.getNationality());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Acciones a realizar cuando no se selecciona ningún elemento
            }
        });

        piloto2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Acciones a realizar cuando se selecciona un elemento
                Driver nombreSeleccionado = allDrivers.getMRData().getDriverTable().getDrivers().get(position);
                Toast.makeText(getContext(), "Seleccionaste: " + nombreSeleccionado, Toast.LENGTH_SHORT).show();

                loaderGif.show();
                driver2 = nombreSeleccionado;
                setDriverImage(nombreSeleccionado);
                txNombrePiloto2.setText(nombreSeleccionado.getGivenName() + " "+ nombreSeleccionado.getFamilyName());
                txFechaNacimientoPiloto2.setText(nombreSeleccionado.getDateOfBirth());
                txNacionalidadPiloto2.setText(nombreSeleccionado.getNationality());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Acciones a realizar cuando no se selecciona ningún elemento
            }
        });

        piloto1.setSelection(16);
        piloto2.setSelection(31);

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


    private void setDriverImage(Driver driver){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        int startIndex = driver.getUrl().indexOf("wiki/") + 5; // Sumamos 5 para incluir "wiki/"
        String driverName = driver.getUrl().substring(startIndex);
        try {
            String decodedString = URLDecoder.decode(driverName, "UTF-8");

            WikipediaApi wikipediaApi = retrofit.create(WikipediaApi.class);
            Call<DriverImage> result;
            if(decodedString.equals("Alexander_Albon")){
                result = wikipediaApi.getImageDriver("Alex_Albon");
            }else{
                result = wikipediaApi.getImageDriver(decodedString);
            }

            result.enqueue(new Callback<DriverImage>() {
                @Override
                public void onResponse(Call<DriverImage> call, Response<DriverImage> response) {
                    if(response.isSuccessful()){
                        if(response.body().getQuery().getPages().get(0).getThumbnail()!=null){
                            driver.setUrlImage(response.body().getQuery().getPages().get(0).getThumbnail().getSource());
                        }
                        llamadaCompleta(llamadasCompletadasGeneral,totalLlamadasGeneral);
                    }else{
                        loaderGif.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<DriverImage> call, Throwable t) {
                    loaderGif.dismiss();
                }
            });
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

    }

    private void llamadaCompleta(AtomicInteger llamadasCompletadas, int totalLlamadas) {
        if (llamadasCompletadas.incrementAndGet() == totalLlamadas) {
            if (driver1.getUrlImage() != null)
                Picasso.get().load(driver1.getUrlImage()).into(foto1);
            else
                foto1.setImageResource(R.drawable.blankprofile);

            if (driver2.getUrlImage() != null)
                Picasso.get().load(driver2.getUrlImage()).into(foto2);
            else
                foto2.setImageResource(R.drawable.blankprofile);

            loaderGif.dismiss();
            llamadasCompletadasGeneral.set(0);
            totalLlamadasGeneral = 1;
        }
    }
}