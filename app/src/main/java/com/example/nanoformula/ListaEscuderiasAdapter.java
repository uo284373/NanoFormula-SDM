package com.example.nanoformula;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nanoformula.modelo.constructorsStandings.ConstructorStanding;

import java.util.List;


public class ListaEscuderiasAdapter extends RecyclerView.Adapter<ListaEscuderiasAdapter.EscuderiasViewHolder>{

    public interface OnItemClickListener {
        void onItemClick(ConstructorStanding item);
    }

    private List<ConstructorStanding> listaEscuderias;
    private final OnItemClickListener listener;

    public ListaEscuderiasAdapter(List<ConstructorStanding> listaEscuderias, OnItemClickListener listener) {
        this.listaEscuderias = listaEscuderias;
        this.listener = listener;
    }

    /* Indicamos el layout a "inflar" para usar en la vista
     */
    @NonNull
    @Override
    public EscuderiasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Creamos la vista con el layout para un elemento
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.linea_escuderia_recycler_view, parent, false);
        return new EscuderiasViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull EscuderiasViewHolder holder, int position) {
        // Extrae de la lista el elemento indicado por posición
        ConstructorStanding escuderia= listaEscuderias.get(position);
        Log.i("Lista","Visualiza elemento: "+escuderia);
        // llama al método de nuestro holder para asignar valores a los componentes
        // además, pasamos el listener del evento onClick
        holder.bindUser(escuderia, listener);
    }

    @Override
    public int getItemCount() {
        return listaEscuderias.size();
    }



    public static class EscuderiasViewHolder extends RecyclerView.ViewHolder{

        private TextView posicion;
        private TextView nombre;
        private TextView pilotos;
        private TextView puntos;
        private ImageView foto;

        public EscuderiasViewHolder(View itemView) {
            super(itemView);

            posicion= itemView.findViewById(R.id.posicionEscuderia);
            nombre= (TextView)itemView.findViewById(R.id.nombreEscuderia);
            pilotos= (TextView) itemView.findViewById(R.id.nombrePilotos);
            puntos= (TextView) itemView.findViewById(R.id.puntosEscuderia);
            foto = (ImageView) itemView.findViewById(R.id.fotoEscuderia);
        }

        // asignar valores a los componentes
        public void bindUser(final ConstructorStanding escuderia, final OnItemClickListener listener) {
            posicion.setText(String.valueOf(escuderia.getPosition()));
            nombre.setText(escuderia.getConstructor().getName());

            String pilots = "";
//            for(String pilot : escuderia.getConstructor().get){
                pilots += "Dato" + " - " + "hardcodeado";
//            }
            pilots = pilots.substring(0, pilots.length() - 2);
            pilotos.setText(pilots);
            puntos.setText(String.valueOf(escuderia.getPoints()));
//            foto.setImageResource(escuderia.getFoto());
            foto.setImageResource(R.drawable.astonmartin);
            //Picasso.get().load(pelicula.getUrlCaratula()).into(imagen);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Log.i("Hola", "Hola");
                    listener.onItemClick(escuderia);
                }
            });
        }
    }
}
