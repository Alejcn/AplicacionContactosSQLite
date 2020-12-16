package com.alejandro.aplicacioncontactossqlite;

import android.widget.TextView;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ContactoPerfilActivity extends AppCompatActivity  {
    private String contacto;
    private TextView nombre, direccion, telefono;
    private UsuariosSQLiteHelper usuariosSQL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_user);

        contacto = getIntent().getExtras().getString("nombre");

        String nombreContacto = contacto.split(" ")[1];
        String direccionContacto = contacto.split(" ")[2];
        String telefonoContacto = contacto.split(" ")[3];

        nombre = findViewById(R.id.nombreProfileView);
        direccion = findViewById(R.id.direccionProfileView);
        telefono = findViewById(R.id.telefonoProfileView);

        nombre.setText(nombreContacto);
        direccion.setText(direccionContacto);
        telefono.setText(telefonoContacto);
    }
}
