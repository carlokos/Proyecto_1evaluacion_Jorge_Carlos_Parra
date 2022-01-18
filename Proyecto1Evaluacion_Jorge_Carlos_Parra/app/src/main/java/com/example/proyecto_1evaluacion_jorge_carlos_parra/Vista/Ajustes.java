package com.example.proyecto_1evaluacion_jorge_carlos_parra.Vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.SwitchPreference;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.proyecto_1evaluacion_jorge_carlos_parra.R;

import Controlador.SettingsFragment;

public class Ajustes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes2);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.vwSustituir, new SettingsFragment())
                .commit();

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


    }

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