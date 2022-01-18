package com.example.proyecto_1evaluacion_jorge_carlos_parra.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyecto_1evaluacion_jorge_carlos_parra.R;

import Controlador.ManejoUsuario;
import Modelo.Usuario;

public class Registro extends AppCompatActivity {
    EditText User, pw;
    Button confirmar, volver;
    ManejoUsuario MU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //relacionamos las variables con los componentes
        User = findViewById(R.id.txtNewUser);
        pw = findViewById(R.id.txtNewPw);
        confirmar = findViewById(R.id.btnCreateUser);
        volver = findViewById(R.id.btnVolver);

        //le pasamos el contexto a la base de datos
        MU = new ManejoUsuario(this);

        //les ponemos a los botones sus listeners
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Registrar(view);
            }
        });

        volver.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                irInicio(view);
                finish();
            }
        });
    }

    public void Toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    //método comprueba los datos y los registra en la base de datos
    public void Registrar(View view){
        String nombre = User.getText().toString();
        String contrasenia = pw.getText().toString();

        if(!nombre.isEmpty() && !contrasenia.isEmpty()){
            Usuario u = new Usuario(nombre, contrasenia);
            if(!MU.comprobarUsuario(u.getUser())) {
                MU.RegistrarUsu(u);

                Toast("Usuario registrado con exito");

                irInicio(view);
                finish();
            } else{
                Toast("Este usuario ya existe");
                User.setText("");
                pw.setText("");
            }
        } else {
            Toast("Algunos de los campos esta vacio");
        }
    }

    //metodo encargado de volver al inicio
    public void irInicio(View view){
        Intent pantalla = new Intent(this, Inicio.class);
        startActivity(pantalla);
    }
}