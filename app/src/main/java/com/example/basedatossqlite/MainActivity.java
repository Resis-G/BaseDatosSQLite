package com.example.basedatossqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLData;

public class MainActivity extends AppCompatActivity {

    private EditText edt_codigo,edt_descricion,edt_precio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt_codigo = (EditText) findViewById(R.id.edt_codigo);
        edt_descricion = (EditText) findViewById(R.id.edt_descripcion);
        edt_precio = (EditText) findViewById(R.id.edt_precio);
    }

    //Method Registrar alumno
    public void Registrar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null,1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = edt_codigo.getText().toString();
        String descripcion = edt_descricion.getText().toString();
        String precio = edt_precio.getText().toString();

        if(!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("codigo",codigo);
            registro.put("descripcion",descripcion);
            registro.put("precio",precio);

            BaseDeDatos.insert("articulos",null,registro);
            edt_codigo.setText("");
            edt_descricion.setText("");
            edt_precio.setText("");
        }else{
            Toast.makeText(this, "debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    //metodo de consulta
    public void Buscar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null,1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = edt_codigo.getText().toString();

        if(!codigo.isEmpty()){
            Cursor fila = BaseDeDatos.rawQuery("select descripcion,precio from articulos where codigo ="+codigo,null);

            if(fila.moveToFirst()){
                edt_descricion.setText(fila.getString(0));
                edt_precio.setText(fila.getString(1));
                BaseDeDatos.close();
            }else{
                Toast.makeText(this, "no exite el articulo", Toast.LENGTH_SHORT).show();
                BaseDeDatos.close();

            }
        }else{
            Toast.makeText(this, "debes introducir el codigo del articulo", Toast.LENGTH_SHORT).show();
        }

    }
    //metodo de eliminar Objetos de Base de Datos
    public void Eliminar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null,1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = edt_codigo.getText().toString();

        if(!codigo.isEmpty()) {
            int cantidad = BaseDeDatos.delete("articulos", "codigo="+codigo,null);
            BaseDeDatos.close();

            edt_codigo.setText("");
            edt_descricion.setText("");
            edt_precio.setText("");

            if(cantidad == 1){
                Toast.makeText(this, "Articulo eliminado exitosamente", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "El articulo no existe", Toast.LENGTH_SHORT).show();
            }
                
        }else{
            Toast.makeText(this, "Debes introducir un codigo para eliminar", Toast.LENGTH_SHORT).show();
        }
    }

    //metodo para modificar un articulo o producto
    public void Modificar(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = edt_codigo.getText().toString();
        String descripcion = edt_descricion.getText().toString();
        String precio = edt_precio.getText().toString();

        if(!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("codigo",codigo);
            registro.put("descripcion",descripcion);
            registro.put("precio",precio);

            int cantidad = BaseDeDatos.update("articulos",registro,"codigo="+codigo,null);
            BaseDeDatos.close();

            if(cantidad == 1){
                Toast.makeText(this,"Articulo modificado exitosamente", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"El articulo no ha sido modificado",Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this,"Debes rellenar todos los campos",Toast.LENGTH_SHORT).show();
        }
    }
}