package com.example.proyecto_1evaluacion_jorge_carlos_parra.Vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.proyecto_1evaluacion_jorge_carlos_parra.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import Controlador.Adapter;
import Controlador.ConexionAPI;
import Modelo.Juego;

public class Vista_Principal extends AppCompatActivity {
    RecyclerView recyclerView;
    Adapter Adapter;
    ArrayList<Juego> list = new ArrayList<Juego>();
    String consulta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_principal);

        loadPreferences();

        recyclerView = findViewById(R.id.RVjuegos);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        Adapter = new Adapter(list);
        recyclerView.setAdapter(Adapter);

        //al ejecutar muchos elementos a la vez hay que usar hilos segundarios, le pasamos el resto
        // de la url
        new taskConnections().execute("GET", consulta);
    }

    public void Salir(){
        //construimos un AlertDialog para confirmar si quiere cerrar sesion
        AlertDialog.Builder Alerta = new AlertDialog.Builder(this);
        Alerta.setMessage("¿Desea cerrar sesion?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent pantalla = new Intent(Vista_Principal.this, Inicio.class);
                        startActivity(pantalla);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog titulo = Alerta.create();
        titulo.setTitle("¿Cerrar sesión?");
        titulo.show();
    }

    //Codigo del menu:
    //"inflamos el menu con nuestro archivo xml
    public boolean onCreateOptionsMenu(android.view.Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //metodo que gestiona lo que hace cada item del menu
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.itemAjustes){
            Intent pantalla = new Intent(Vista_Principal.this, Ajustes.class);
            startActivity(pantalla);
        } else if(id == R.id.itemSalir){
            Salir();
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadPreferences(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Vista_Principal.this);

        //guardamos el estado del checkbox en esta variable y segun su estado alteramos la consulta
        boolean caso = sharedPreferences.getBoolean("Mostrar", false);

        if(!caso) {
            consulta = "/games";
        } else{
            consulta= "/games?sort-by=alphabetical";
        }

    }

    //este metodo nos ayuda a que la lista siempre este actualiza segun los apuntes
    @Override
    public void onResume(){
        super.onResume();
        loadPreferences();
        new taskConnections().execute("GET", consulta);
    }

    public void Toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    //al tener que esperar una respuesta del servidor tenemos que usar hilos segundarios para que
    // la app no se ralentice
    //El primer string sera el metodo que usemos y el segundo string sera la parte de la
    // url que añadamos
   private class taskConnections extends AsyncTask<String,Void,String> {

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
                if(s != null){
                    //primero borramos toda la lista
                    list.clear();

                    //nuestra api ya nos devuelve el JSON como array asi que simplemente usamos este
                    // método
                    JSONArray jsonArray = new JSONArray(s);

                    //creamos la variables para cargar los datos y rellenamos la arrayList
                    String name = "";
                    String id = "";
                    String img = "";
                    String des = "";
                    String genero = "";
                    String plataforma = "";
                    for(int i=0; i<jsonArray.length(); i++){
                        name = jsonArray.getJSONObject(i).getString("title");
                        id = jsonArray.getJSONObject(i).getString("id");
                        img = jsonArray.getJSONObject(i).getString("thumbnail");
                        des = jsonArray.getJSONObject(i).getString("short_description");
                        genero = jsonArray.getJSONObject(i).getString("genre");
                        plataforma = jsonArray.getJSONObject(i).getString("platform");
                        Juego juego = new Juego(id,name,img,des,genero,plataforma);
                        list.add(juego);
                    }

                    //llamamos al adaptador lo ultimo para tenerlo actualizado
                    Adapter.notifyDataSetChanged();

                }else{
                    Toast("Error a conectar con la base de datos");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}