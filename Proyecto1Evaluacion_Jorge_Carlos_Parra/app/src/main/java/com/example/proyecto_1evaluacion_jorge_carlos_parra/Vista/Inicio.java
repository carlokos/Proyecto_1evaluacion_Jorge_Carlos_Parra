package com.example.proyecto_1evaluacion_jorge_carlos_parra.Vista;

import com.example.proyecto_1evaluacion_jorge_carlos_parra.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Controlador.ManejoUsuario;
import Modelo.Usuario;

public class Inicio extends AppCompatActivity {
    EditText user;
    EditText contrasenia;
    Button iniciar,registrarse;
    ManejoUsuario MU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //relacionamos las variables con los componentes
        user = findViewById(R.id.txtUsuario);
        contrasenia = findViewById(R.id.txtPassword);
        iniciar = findViewById(R.id.btnIniciar);
        registrarse = findViewById(R.id.btnRegistrarse);


        //le pasamos el contexto a la base de datos
        MU = new ManejoUsuario(this);

        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Nombre = user.getText().toString();
                String pw = contrasenia.getText().toString();

                Usuario u = MU.iniciarSesion(Nombre,pw);
                if( u != null){
                    Toast("Iniciando sesion...");
                    IniciarSesion(view);
                } else{
                    Toast("Error, el usuario o la contraseña son incorrectos");
                    user.setText("");
                    contrasenia.setText("");
                }
            }
        });

        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Registrar(view);
            }
        });
    }

    public void Toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    //metodo que cambia a la pantalla de registro
    public void Registrar(View view){
        Intent i = new Intent(this, Registro.class);
        startActivity(i);
        finish();
    }

    //metodo para iniciar sesion, leemos la base de datos y comprobamos si existe el usuario con la
    // esa contraseña
    public void IniciarSesion(View view){
        Intent i = new Intent(this, Vista_Principal.class);
        startActivity(i);
        finish();
    }

}
