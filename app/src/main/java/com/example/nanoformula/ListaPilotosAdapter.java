package com.example.nanoformula;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nanoformula.modelo.Piloto;

import java.util.List;


public class ListaPilotosAdapter extends RecyclerView.Adapter<ListaPilotosAdapter.PilotosViewHolder>{

    public interface OnItemClickListener {
        void onItemClick(Piloto item);
    }

    private List<Piloto> listaPilotos;
    private final OnItemClickListener listener;

    public ListaPilotosAdapter(List<Piloto> listaPilotos, OnItemClickListener listener) {
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
        Piloto piloto= listaPilotos.get(position);
        Log.i("Lista","Visualiza elemento: "+piloto);
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

        public PilotosViewHolder(View itemView) {
            super(itemView);

            posicion= (TextView)itemView.findViewById(R.id.posicionPiloto);
            nombre= (TextView)itemView.findViewById(R.id.nombrePiloto);
            equipo= (TextView) itemView.findViewById(R.id.nombreEquipo);
            puntos= (TextView) itemView.findViewById(R.id.puntosPiloto);
        }

        // asignar valores a los componentes
        public void bindUser(final Piloto piloto, final OnItemClickListener listener) {
            posicion.setText(piloto.getPosition());
            nombre.setText(piloto.getName());
            equipo.setText(piloto.getTeam());
            puntos.setText(piloto.getPoints());
            //Picasso.get().load(pelicula.getUrlCaratula()).into(imagen);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Log.i("Hola", "Hola");
                    listener.onItemClick(piloto);
                }
            });
        }
    }
}
