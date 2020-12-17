package com.alejandro.aplicacioncontactossqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> listaContactos = new ArrayList<>();
    private ArrayAdapter adapter;
    private FloatingActionButton button_insertFlotante;

    private SQLiteDatabase db;
    private UsuariosSQLiteHelper usuariosSQL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listViewNombres = findViewById(R.id.contactosListView);
        usuariosSQL = new UsuariosSQLiteHelper(this, "ContactosDB1", null, 1);

        db = usuariosSQL.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT id, nombre, direccion, telefono FROM Contactos", null);

        if (c.moveToFirst()) {
            do {
                String id = c.getString(0);
                String nom = c.getString(1);
                String direc = c.getString(2);
                String tel = c.getString(3);
                //listaContactos.add(id + " " + nom + " " + direc + " " + tel);
                listaContactos.add(id + " " + nom);
            } while(c.moveToNext());
            adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1 ,listaContactos);
            listViewNombres.setAdapter(adapter);
        }

        db.close();

        button_insertFlotante = findViewById(R.id.buttonInsertarFlotante);
        button_insertFlotante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateUser.class);
                startActivity(intent);            }
        });

        registerForContextMenu(listViewNombres);

        listViewNombres.setEmptyView(findViewById(R.id.listaContactosVacia));

        listViewNombres.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,ContactoPerfilActivity.class);
                intent.putExtra("nombre", listaContactos.get(i));
                startActivity(intent);
            }
        });
    }

    public void deleteUser(String idContacto){
        db = usuariosSQL.getWritableDatabase();
        String sql = "DELETE FROM Contactos WHERE id=" + idContacto;
        db.execSQL(sql);
        db.close();
        this.recreate();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu_usuario, View view, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_usuario, menu_usuario);
    }

    @Override
    public boolean onContextItemSelected(MenuItem menuItem) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) menuItem.getMenuInfo();
        final String contacto = (listaContactos.get((int) menuInfo.id));
        final String idContacto = contacto.split(" ")[0];
        String nombreContacto = contacto.split(" ")[1];

        switch (menuItem.getItemId()) {
            case R.id.borrar_usuario:
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("¿Está seguro?")
                        .setMessage("Se va a eliminar a " + nombreContacto)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                deleteUser(idContacto);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            case R.id.actualizar_usuario:
                Intent intent = new Intent(MainActivity.this, UpdateUser.class);
                intent.putExtra("contacto", contacto);
                startActivity(intent);
                return true;

            default:
                return super.onContextItemSelected(menuItem);
        }
    }
}