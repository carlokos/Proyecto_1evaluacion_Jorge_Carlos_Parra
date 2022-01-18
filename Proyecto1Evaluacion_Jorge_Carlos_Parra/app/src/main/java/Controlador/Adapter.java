package Controlador;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyecto_1evaluacion_jorge_carlos_parra.R;
import com.example.proyecto_1evaluacion_jorge_carlos_parra.Vista.Vista_Detallada;

import java.util.List;

import Modelo.Juego;

public class Adapter extends RecyclerView.Adapter<Adapter.RecyclerHolder>{
    private List<Juego> Lista;

    public Adapter(List<Juego> listJuegos) {
        this.Lista = listJuegos;
    }

    //métodos que siempre creamos con un adapatedor
    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_element,parent, false);
        RecyclerHolder recyclerHolder = new RecyclerHolder(view);

        return recyclerHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        //aqui mostramos nuestro contenido, le pasamos uno por uno los objetos de la lista
        Juego juego = Lista.get(position);
        holder.asignarElementos(juego);
    }

    @Override
    public int getItemCount() {
        return Lista.size();
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {
        //aqui relacionamos los elementos de nuestro "list-element" con las variables
        ImageView icono;
        TextView titulo, genero, plataforma;
        Button borrar;

        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);

            icono = itemView.findViewById(R.id.iconoJuego);
            titulo = itemView.findViewById(R.id.txtTitulo);
            genero = itemView.findViewById(R.id.txtGenero);
            plataforma = itemView.findViewById(R.id.txtPlataforma);
            borrar = itemView.findViewById(R.id.btnBorrar);
        }

        public void asignarElementos(Juego item){
            //ponemos con Glide un place holder para tener una imagen de carga ya que estara en un
            // hilo segundario
            Glide.with(itemView)
                    .load(item.getImageUrl())
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(icono);
            titulo.setText(item.getName());
            genero.setText(item.getGenero());
            plataforma.setText(item.getPlataforma());
            //para que cada tarjeta pueda eliminarse y borrarse tenemos que poner listeners en el
            // adaptador
            //listener de toda la tarjeta
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //con "putExtra" podemos pasar datos de una vista a otra
                    Intent i = new Intent(itemView.getContext(), Vista_Detallada.class);
                    i.putExtra("ID", item.getId());
                    itemView.getContext().startActivity(i);
                }
            });
            //listener de un boton
            borrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder Alerta = new AlertDialog.Builder(itemView.getContext());
                    Alerta.setMessage("¿Desea borrar este juego?, una vez hecho no podra recuperarse")
                            .setCancelable(false)
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Lista.remove(item);
                                    //esto sirve para que el adaptador se actualice en cuando
                                    // un objeto sea borrado
                                    notifyDataSetChanged();
                                    Toast("Objeto Borrado");
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                   dialogInterface.cancel();
                                }
                            });
                    AlertDialog titulo = Alerta.create();
                    titulo.setTitle("¿Borrar Juego?");
                    titulo.show();
                }
            });
        }

        public void Toast(String msg) {
            Toast.makeText(itemView.getContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }
}
