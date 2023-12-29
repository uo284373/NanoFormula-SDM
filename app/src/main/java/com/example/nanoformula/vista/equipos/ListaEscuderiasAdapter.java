package com.example.nanoformula.vista.equipos;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nanoformula.R;
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


            String drivers = "";
            for(String driver : escuderia.getDriversNames()){
                drivers += driver + " - ";
            }
            drivers = drivers.substring(0, drivers.length() - 2);
            pilotos.setText(drivers);
            puntos.setText(String.valueOf(escuderia.getPoints()));

            switch (escuderia.getConstructor().getConstructorId()){
                case "alfa":
                    escuderia.setDrawableFragment(R.drawable.alfaromeofragment);
                    escuderia.setDrawableDetails(R.drawable.alfaromeodetails);
                    break;
                case "alphatauri":
                    escuderia.setDrawableFragment(R.drawable.alphataurifragment);
                    escuderia.setDrawableDetails(R.drawable.alphatauridetails);
                    break;
                case "alpine":
                    escuderia.setDrawableFragment(R.drawable.alpinefragment);
                    escuderia.setDrawableDetails(R.drawable.alpinedetails);
                    break;
                case "aston_martin":
                    escuderia.setDrawableFragment(R.drawable.astonmartinfragment);
                    escuderia.setDrawableDetails(R.drawable.astonmartindetails);
                    break;
                case "ferrari":
                    escuderia.setDrawableFragment(R.drawable.ferrarifragment);
                    escuderia.setDrawableDetails(R.drawable.ferraridetails);
                    break;
                case "haas":
                    escuderia.setDrawableFragment(R.drawable.haasfragment);
                    escuderia.setDrawableDetails(R.drawable.haasdetails);
                    break;
                case "mclaren":
                    escuderia.setDrawableFragment(R.drawable.mclarenfragment);
                    escuderia.setDrawableDetails(R.drawable.mclarendetails);
                    break;
                case "mercedes":
                    escuderia.setDrawableFragment(R.drawable.mercedesfragment);
                    escuderia.setDrawableDetails(R.drawable.mercedesdetails);
                    break;
                case "red_bull":
                    escuderia.setDrawableFragment(R.drawable.redbullfragment);
                    escuderia.setDrawableDetails(R.drawable.redbulldetails);
                    break;
                case "williams":
                    escuderia.setDrawableFragment(R.drawable.williamsfragment);
                    escuderia.setDrawableDetails(R.drawable.williamsdetails);
                    break;
                default:
                    escuderia.setDrawableFragment(R.drawable.f1logo);
                    escuderia.setDrawableDetails(R.drawable.f1logo);
                    break;
            }

            foto.setImageResource(escuderia.getDrawableFragment());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Log.i("Hola", "Hola");
                    listener.onItemClick(escuderia);
                }
            });
        }
    }
}
