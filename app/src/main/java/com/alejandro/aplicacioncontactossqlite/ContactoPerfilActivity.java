package com.alejandro.aplicacioncontactossqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ContactoPerfilActivity extends AppCompatActivity  {
    private String contacto;
    private TextView nombre, direccion, telefono;
    private SQLiteDatabase db;
    private UsuariosSQLiteHelper usuariosSQL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_user);

        contacto = getIntent().getExtras().getString("nombre");
        usuariosSQL = new UsuariosSQLiteHelper(this, "ContactosDB1", null, 1);

        db = usuariosSQL.getWritableDatabase();
        String idContacto = contacto.split(" ")[0];

        Cursor c = db.rawQuery("SELECT nombre, direccion, telefono FROM Contactos WHERE id = '"+idContacto+"'", null);
        String nombreContacto, direccionContacto, telefonoContacto;
        if(c.moveToFirst()) {
            nombreContacto = c.getString(0);
            direccionContacto = c.getString(1);
            telefonoContacto = c.getString(2);
            Toast.makeText(getApplicationContext(), "Este es el id: " + nombreContacto, Toast.LENGTH_SHORT).show();

            nombre = findViewById(R.id.nombreProfileView);
            direccion = findViewById(R.id.direccionProfileView);
            telefono = findViewById(R.id.telefonoProfileView);

            nombre.setText(nombreContacto);
            direccion.setText(direccionContacto);
            telefono.setText(telefonoContacto);
        }
    }
}
