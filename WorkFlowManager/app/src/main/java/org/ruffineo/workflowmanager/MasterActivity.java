package org.ruffineo.workflowmanager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MasterActivity extends AppCompatActivity {

    private static final String TAG = "MAIN_LOG";

    private String[] listaRecuperada;
    private String method;
    private List listaRecuperadaWS;
    private String user;
    private String pass;

    // 1. Toolbar
    private Toolbar toolbar;

    // 2. Drawer
    private DrawerLayout drawer;

    // 3. Drawer Toggle
    private ActionBarDrawerToggle drawerToggle;

    // 4. Creamos un ListView para simplificar
    ListView lista;
    static final String[] Solicitudes = new String[]{
            "Lista Vacia"
    };

    // 5. Collapsing toolbar
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);

        if (savedInstanceState == null) {
            escribeLog("savedInstanceState es nula");
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                escribeLog("extras es nulo");
                user = null;
                pass = null;
            } else {
                escribeLog("extras no es nulo");
                user = extras.getString("tokenU");
                pass = extras.getString("tokenP");
                escribeLog("extras recupero, user: " + user + " pass: " + pass);
            }
        } else {
            escribeLog("savedInstanceState no es nula");
            user = (String) savedInstanceState.getSerializable("tokenU");
            pass = (String) savedInstanceState.getSerializable("tokenP");
            escribeLog("savedInstance recupero, user: " + user + " pass: " + pass);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 1. Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 2. Drawer
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        // 3. Drawer Toggle
        drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        // 4 ListView
        lista = (ListView) findViewById(R.id.lista);
        ListAdapter adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Solicitudes);
        lista.setAdapter(adaptador);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                escribeLog("se hizo clic en el item: " + lista.getItemAtPosition(position));
                String solicitudNumero = lista.getItemAtPosition(position).toString();
                Intent intent = new Intent(MasterActivity.this, TareaActivity.class);
                intent.putExtra("tokenU", user);
                intent.putExtra("tokenP", pass);
                intent.putExtra("tokenS", solicitudNumero);
                startActivity(intent);
            }
        });

        // 5. Collapsing toolbar
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        collapsingToolbarLayout.setTitle("Solicitudes");


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Se abrira a la aplicacion de MAILS", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Invoca al WebService para recuperar la lista
        ListarSolicitudes lista = new ListarSolicitudes();
        lista.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_master, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class ListarSolicitudes extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            method = "recuperaLista";
            WebService ws = new WebService();
            listaRecuperadaWS = ws.recuperaLista("oscar","oscar", method);
            escribeLog("recupero de la clase WS lista: " + listaRecuperadaWS.toString());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (listaRecuperadaWS != null) {
                ListAdapter adaptador = new ArrayAdapter<String>(MasterActivity.this, android.R.layout.simple_list_item_1, listaRecuperadaWS);
                lista.setAdapter(adaptador);
            } else {
                notificaError("No se recuperaron Tareas pendientes");
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    private void escribeLog(String texto) {
        Log.i(TAG, texto);
    }

    private void notificaError(String mensaje) {
        Toast.makeText(MasterActivity.this, mensaje, Toast.LENGTH_SHORT).show();
    }



}
