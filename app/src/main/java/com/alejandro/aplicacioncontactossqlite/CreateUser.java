package com.alejandro.aplicacioncontactossqlite;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateUser extends AppCompatActivity {

    private EditText nombreInsertado, direccionInsertada, telefonoInsertado;
    private Button button_agregarContacto, button_cancelarCrearContacto;
    private SQLiteDatabase db;
    private UsuariosSQLiteHelper usuariosSQL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_user);

        usuariosSQL = new UsuariosSQLiteHelper(this, "ContactosDB1", null, 1);

        nombreInsertado = findViewById(R.id.nombreCreateEditText);
        direccionInsertada = findViewById(R.id.direccionCreateEditText);;
        telefonoInsertado = findViewById(R.id.telefonoCreateEditText);;

        button_agregarContacto = findViewById(R.id.agregarButton);
        button_agregarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = nombreInsertado.getText().toString();
                String direccion = direccionInsertada.getText().toString();
                String telefono = telefonoInsertado.getText().toString();

                db = usuariosSQL.getWritableDatabase();

                String sql = "INSERT INTO Contactos (nombre, direccion, telefono) VALUES ('" + nombre + "','" + direccion + "','" + telefono + "') ";
                db.execSQL(sql);

                db.close();

                Toast.makeText(getApplicationContext(), "Agregado " + nombre, Toast.LENGTH_SHORT).show();

                finish();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        button_cancelarCrearContacto = findViewById(R.id.cancelButton);
        button_cancelarCrearContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
