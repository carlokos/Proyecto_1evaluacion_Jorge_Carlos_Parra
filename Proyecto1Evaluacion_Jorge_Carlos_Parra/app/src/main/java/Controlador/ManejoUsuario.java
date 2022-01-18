package Controlador;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import Modelo.Usuario;

public class ManejoUsuario {
    //creamos un context para pasarlo y que vaya bien en ambas vistas (la de registro y la de inicio)
    Context context;
    ConexionBD sql;
    SQLiteDatabase BD;

    //usamos nuestra conexion y abrimos una base de datos que se pueda editar en el constructor
    public ManejoUsuario(Context context){
        this.context = context;

        //al usar el constructor nos conectamos a la base de datos
        sql = new ConexionBD(context, "Users", null, 1);
        BD = sql.getWritableDatabase();
    }

    //Metodo para insertar en la tabla un usuario nuevo
    public void RegistrarUsu(Usuario u){
        ContentValues registro = new ContentValues();

        registro.put("UserName", u.getUser());
        registro.put("pw", u.getPw());

        BD.insert("Users", null, registro);
        BD.close();
    }

    //metodo que devuelve un usuario de la base de datos, si no existe devuelve null
    public Usuario iniciarSesion(String nombre, String pw){
        Usuario u = null;
        Cursor cursor = BD.rawQuery("SELECT * FROM USERS", null);
        if(cursor != null && cursor.moveToFirst()){
            do{
                if (cursor.getString(0).equals(nombre) && cursor.getString(1).equals(pw)){
                    u = new Usuario(nombre, pw);
                }
            } while(cursor.moveToNext());
        }
        return u;
    }

    //comprueba si existe un nombre de usuario (clave primaria de la BD) mediante un boolean
    public boolean comprobarUsuario(String nombre){
        boolean existe = false;
        Cursor cursor = BD.rawQuery("SELECT * FROM USERS", null);
        if(cursor != null && cursor.moveToFirst()){
            do{
                if(cursor.getString(0).equals(nombre)){
                    existe = true;
                }
            } while(cursor.moveToNext());
        }
        return existe;
    }
}
