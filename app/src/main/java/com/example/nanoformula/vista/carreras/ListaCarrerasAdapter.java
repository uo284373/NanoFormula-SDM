package com.example.nanoformula.vista.carreras;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nanoformula.R;
import com.example.nanoformula.modelo.raceSchedule.Race;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ListaCarrerasAdapter extends RecyclerView.Adapter<ListaCarrerasAdapter.CarrerasViewHolder>{

    public interface OnItemClickListener {
        void onItemClick(Race item);
    }

    private List<Race> listaCarreras;
    private final OnItemClickListener listener;

    public ListaCarrerasAdapter( List<Race> listaCarreras, OnItemClickListener listener) {
        this.listaCarreras = listaCarreras;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CarrerasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Creamos la vista con el layout para un elemento
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.linea_carrera_recycler_view, parent, false);
        return new CarrerasViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull CarrerasViewHolder holder, int position) {
        // Extrae de la lista el elemento indicado por posición
        Race carrera= listaCarreras.get(position);
        Log.i("Lista","Visualiza elemento: "+carrera);
        // llama al método de nuestro holder para asignar valores a los componentes
        // además, pasamos el listener del evento onClick
        holder.bindUser(carrera, listener);
    }

    @Override
    public int getItemCount() {
        return listaCarreras.size();
    }



    public static class CarrerasViewHolder extends RecyclerView.ViewHolder{

        private TextView posicion;
        private TextView nombre;
        private TextView localizacion;
        private TextView ciudad;

        private TextView fecha;
        private ImageView bandera;

        public CarrerasViewHolder(View itemView) {
            super(itemView);

            posicion= itemView.findViewById(R.id.posicionCarrera);
            nombre= (TextView)itemView.findViewById(R.id.nombreCarrera);
            localizacion= (TextView) itemView.findViewById(R.id.nombreLocalizacion);
            ciudad= (TextView) itemView.findViewById(R.id.nombreCiudad);
            fecha = (TextView) itemView.findViewById(R.id.fechaCarrera);
            bandera = (ImageView) itemView.findViewById(R.id.fotoBandera);
        }

        // asignar valores a los componentes
        public void bindUser(final Race carrera, final OnItemClickListener listener) {
            posicion.setText(String.valueOf(carrera.getRound()));
            nombre.setText(carrera.getRaceName());
            localizacion.setText(carrera.getCircuit().getCircuitName());
            ciudad.setText(carrera.getCircuit().getLocation().getLocality()+" - "+carrera.getCircuit().getLocation().getCountry());
            fecha.setText(carrera.getDateFormat());
            Picasso.get().load(carrera.getCircuit().getUrl()).into(bandera);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Log.i("Hola", "Hola");
                    listener.onItemClick(carrera);
                }
            });
        }
    }
}

