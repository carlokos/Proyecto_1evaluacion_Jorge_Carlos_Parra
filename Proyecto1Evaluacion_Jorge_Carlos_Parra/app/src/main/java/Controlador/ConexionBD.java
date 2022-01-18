package Controlador;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import Modelo.Usuario;

public class ConexionBD extends SQLiteOpenHelper{

    public ConexionBD(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    //Metodos que tenemos que sobreescribir a usar la interfaz SQLiteOpenHelper
    @Override
    public void onCreate(SQLiteDatabase BD) {
        //aqui creamos la tabla
        BD.execSQL("CREATE TABLE IF NOT EXISTS USERS(UserName text PRIMARY KEY, pw text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
