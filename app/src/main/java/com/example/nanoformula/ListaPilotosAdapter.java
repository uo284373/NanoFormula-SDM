package com.example.nanoformula;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nanoformula.API.WikipediaApi;
import com.example.nanoformula.modelo.driversImage.DriverImage;
import com.example.nanoformula.modelo.driversStandings.DriverStanding;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ListaPilotosAdapter extends RecyclerView.Adapter<ListaPilotosAdapter.PilotosViewHolder>{

    public interface OnItemClickListener {
        void onItemClick(DriverStanding item);
    }

    private List<DriverStanding> listaPilotos;
    private final OnItemClickListener listener;

    public ListaPilotosAdapter(List<DriverStanding> listaPilotos, OnItemClickListener listener) {
        this.listaPilotos = listaPilotos;
        this.listener = listener;
    }

    /* Indicamos el layout a "inflar" para usar en la vista
     */
    @NonNull
    @Override
    public PilotosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Creamos la vista con el layout para un elemento
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.linea_piloto_recycler_view, parent, false);
        return new PilotosViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull PilotosViewHolder holder, int position) {
        // Extrae de la lista el elemento indicado por posición
        DriverStanding piloto= listaPilotos.get(position);
        // llama al método de nuestro holder para asignar valores a los componentes
        // además, pasamos el listener del evento onClick
        holder.bindUser(piloto, listener);
    }

    @Override
    public int getItemCount() {
        return listaPilotos.size();
    }



    public static class PilotosViewHolder extends RecyclerView.ViewHolder{

        private TextView posicion;
        private TextView nombre;
        private TextView equipo;
        private TextView puntos;
        private ImageView foto;

        public PilotosViewHolder(View itemView) {
            super(itemView);

            posicion= itemView.findViewById(R.id.posicionPiloto);
            nombre= (TextView)itemView.findViewById(R.id.nombrePiloto);
            equipo= (TextView) itemView.findViewById(R.id.nombreEquipo);
            puntos= (TextView) itemView.findViewById(R.id.puntosPiloto);
            foto = (ImageView) itemView.findViewById(R.id.fotoPiloto);
        }

        // asignar valores a los componentes
        public void bindUser(final DriverStanding piloto, final OnItemClickListener listener) {
            posicion.setText(piloto.getPosition());
            nombre.setText(piloto.getDriver().getGivenName()+" "+piloto.getDriver().getFamilyName());
            equipo.setText(piloto.getConstructors().get(0).getName());
            puntos.setText(piloto.getPoints());
            Picasso.get().load(piloto.getDriver().getUrlImage()).into(foto);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Log.i("Hola", "Hola");
                    listener.onItemClick(piloto);
                }
            });
        }


    }


}
