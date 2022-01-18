package com.example.proyecto_1evaluacion_jorge_carlos_parra.Vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.proyecto_1evaluacion_jorge_carlos_parra.R;

import org.json.JSONException;
import org.json.JSONObject;

import Controlador.ConexionAPI;

public class Vista_Detallada extends AppCompatActivity {
    ImageView icono;
    TextView genero, plataforma, des, titulo;

    //no se porque al poner esto en una sola linea no iba bien asi que
    //lo he dividido para asegurarme que los strings esten vacios
    String title = "";
    String img = "";
    String description = "";
    String genre = "";
    String plataform = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_detallada);

        icono = findViewById(R.id.imgIconoDetallado);
        titulo = findViewById(R.id.txtTituloDetallado);
        genero = findViewById(R.id.txtGeneroDetallado);
        plataforma = findViewById(R.id.txtPlataformaDetallada);
        des = findViewById(R.id.txtDescripcionDetallada);

        //no sabemos que tan larga puede ser la descripcion asi que es bueno
        // que pueda hacer scroll para poder leerla bien
        des.setMovementMethod(new ScrollingMovementMethod());
        //vamos a usar solamente el ID del juego para cargar desde la API
        //el ID lo recogemos de la vista anterior y este metodo sirve para recoger datos que se
        // han pasado con el intent
        String id = getIntent().getStringExtra("ID");

        String consulta = "/game?id=" + id;
        new taskConnections().execute("GET", consulta);

        //esta barra se encarga de poder volver a la pantalla anterior
        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void Toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private class taskConnections extends AsyncTask<String, Void, String> {
        @Override
        //nosotros solo vamos a usar el método GET
        protected String doInBackground(String... strings) {
            String result = null;
            switch (strings[0]) {
                case "GET":
                    result = ConexionAPI.getRequest(strings[1]);
                    break;
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                if (s != null) {
                    //nuestra api ya nos devuelve el JSON como array asi que simplemente usamos este
                    //método
                    JSONObject jsonObject = new JSONObject(s);

                    //buscamos los datos que vamos a usar
                    title = jsonObject.getString("title");
                    img = jsonObject.getString("thumbnail");
                    description = jsonObject.getString("short_description");
                    genre = jsonObject.getString("genre");
                    plataform = jsonObject.getString("platform");

                    //rellenamos los datos que hemos cogido de la array siendo asi:
                    //nombre en español = componente de la vista
                    //nombre en ingles = variables con los datos
                    Glide.with(Vista_Detallada.this)
                            .load(img)
                            .placeholder(R.drawable.loading)
                            .error(R.drawable.error)
                            .into(icono);
                    genero.setText("Genre: " + genre);
                    des.setText(description);
                    titulo.setText(title);
                    plataforma.setText("Plataform: " + plataform);
                } else {
                    Toast("Error a conectar con la base de datos");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    //pone el icono para volver hacia atras en la barra del menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}