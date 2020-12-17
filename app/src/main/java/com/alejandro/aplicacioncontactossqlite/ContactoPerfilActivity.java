package com.alejandro.aplicacioncontactossqlite;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ContactoPerfilActivity extends AppCompatActivity  {
    private String contacto, direccionMaps, telefonoLlamar, telefonoMensaje;
    private TextView nombre, direccion, telefono;
    private SQLiteDatabase db;
    private UsuariosSQLiteHelper usuariosSQL;
    private Button button_maps, button_call, button_sms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_user);

        contacto = getIntent().getExtras().getString("nombre");
        usuariosSQL = new UsuariosSQLiteHelper(this, "ContactosDB1", null, 1);

        db = usuariosSQL.getWritableDatabase();
        final String idContacto = contacto.split(" ")[0];

        final Cursor c = db.rawQuery("SELECT nombre, direccion, telefono FROM Contactos WHERE id = '"+idContacto+"'", null);
        String nombreContacto, direccionContacto, telefonoContacto;
        if(c.moveToFirst()) {
            nombreContacto = c.getString(0);
            direccionContacto = c.getString(1);
            telefonoContacto = c.getString(2);

            nombre = findViewById(R.id.nombreProfileView);
            direccion = findViewById(R.id.direccionProfileView);
            telefono = findViewById(R.id.telefonoProfileView);

            nombre.setText(nombreContacto);
            direccion.setText(direccionContacto);
            telefono.setText(telefonoContacto);
        }

        button_maps = findViewById(R.id.buttonMaps);
        button_maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor c = db.rawQuery("SELECT direccion FROM Contactos WHERE id = '"+idContacto+"'", null);
                if(c.moveToFirst())
                    direccionMaps = c.getString(0);
                Uri uri = Uri.parse("http://www.google.com/maps/place/" + direccionMaps.replaceAll(" ","+"));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        button_call = findViewById(R.id.buttonCall);
        button_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor c = db.rawQuery("SELECT telefono FROM Contactos WHERE id = '"+idContacto+"'", null);
                if(c.moveToFirst())
                    telefonoLlamar = c.getString(0);

                if(!TextUtils.isEmpty(telefonoLlamar)) {
                    String dial = "tel:" + telefonoLlamar;
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                }else {
                    Toast.makeText(ContactoPerfilActivity.this, "Este contacto no tiene numero de telefono",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        button_sms = findViewById(R.id.buttonSms);
        button_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor c = db.rawQuery("SELECT telefono FROM Contactos WHERE id = '"+idContacto+"'", null);
                if(c.moveToFirst())
                    telefonoMensaje = c.getString(0);

                if(!TextUtils.isEmpty(telefonoMensaje)) {
                    Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + telefonoMensaje));
                    startActivity(smsIntent);
                }else {
                    Toast.makeText(ContactoPerfilActivity.this, "Este contacto no tiene numero de telefono",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
