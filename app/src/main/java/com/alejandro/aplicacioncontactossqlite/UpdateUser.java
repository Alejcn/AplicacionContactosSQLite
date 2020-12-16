package com.alejandro.aplicacioncontactossqlite;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateUser extends AppCompatActivity {

    private String contacto;
    private EditText nombreInsertado, direccionInsertada, telefonoInsertado;
    private Button button_modificarContacto, button_cancelarCrearContacto;
    private SQLiteDatabase db;
    private UsuariosSQLiteHelper usuariosSQL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_user);

        contacto = getIntent().getExtras().getString("contacto");
        usuariosSQL = new UsuariosSQLiteHelper(this, "ContactosDB1", null, 1);

        String nombreContacto = contacto.split(" ")[1];
        String direccionContacto = contacto.split(" ")[2];
        String telefonoContacto = contacto.split(" ")[3];

        final TextView textoNombre = findViewById(R.id.nombreUpdateEditText);
        textoNombre.setText(nombreContacto);
        final TextView textoDireccionc = findViewById(R.id.direccionUpdateEditText);
        textoDireccionc.setText(direccionContacto);
        final TextView textoTelefono = findViewById(R.id.telefonoUpdateEditText);
        textoTelefono.setText(telefonoContacto);

        nombreInsertado = findViewById(R.id.nombreUpdateEditText);
        direccionInsertada = findViewById(R.id.direccionUpdateEditText);
        telefonoInsertado = findViewById(R.id.telefonoUpdateEditText);

        button_modificarContacto = findViewById(R.id.modificarButton);
        button_modificarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idContacto = contacto.split(" ")[0];
                String nombre = nombreInsertado.getText().toString();
                String direccion = direccionInsertada.getText().toString();
                String telefono = telefonoInsertado.getText().toString();

                db = usuariosSQL.getWritableDatabase();
                String sql = "UPDATE Contactos SET nombre='" + nombre + "', direccion= '" + direccion + "', telefono='" + telefono + "' WHERE id=" + idContacto;
                db.execSQL(sql);
                db.close();
                finish();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        button_cancelarCrearContacto = findViewById(R.id.cancelButtonUpdate);
        button_cancelarCrearContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
