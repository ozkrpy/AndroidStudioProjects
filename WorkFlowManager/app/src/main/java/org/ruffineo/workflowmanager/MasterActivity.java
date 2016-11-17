package org.ruffineo.workflowmanager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MasterActivity extends AppCompatActivity {

    private static final String TAG = "MAIN_LOG";

    private String[] listaRecuperada;
    private String method;
    private ArrayList<Item> listaRecuperadaWS;
    private String user;
    private String pass;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle drawerToggle;
    private ListView lista;
    private MyAdapter adaptador;
    ArrayList<Item> listaSolicitudes = new ArrayList<Item>();
    ListView mDrawerList;
    static final String[] items = new String[]{
            "Actualizar lista de tareas",
            "Ajustes de la aplicacion",
            "Salir"
    };
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

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
        try {
            ListarSolicitudes listaWS = new ListarSolicitudes();
            listaWS.execute();
        } catch (Exception e) {
            escribeLog("Error al invocar la carga del WS: " + e.getMessage());
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.drawer_list);
        final ArrayAdapter adapter = new ArrayAdapter(this,R.layout.drawer_menu_item, items);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                escribeLog("drawer posicion: " + position);
                switch (position) {
                    case 0:
                        escribeLog("Se invoca a una nueva actualizacion de las tareas pendientes");
                        ListarSolicitudes listaRefresh = new ListarSolicitudes();
                        listaRefresh.execute();
                        break;
                    case 1:
                        escribeLog("Se invoca al menu de ajustes");
                        Intent i = new Intent(MasterActivity.this, PreferencesActivity.class);
                        startActivity(i);
                        break;
                    case 2:
                        escribeLog("Salir de la aplicacion");
                        finish();
                        break;
                }
                mDrawerList.setAdapter(adapter);
                drawer.closeDrawer(mDrawerList);

            }
        });
        drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
        lista = (ListView) findViewById(R.id.lista);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                escribeLog("se hizo clic en el item: " + lista.getItemAtPosition(position));
                Item solicitudItem = (Item) lista.getItemAtPosition(position);
                String solicitudNumero = solicitudItem.getTitle().toString();
                Intent intent = new Intent(MasterActivity.this, TareaActivity.class);
                intent.putExtra("tokenU", user);
                intent.putExtra("tokenP", pass);
                intent.putExtra("tokenS", solicitudNumero);
                startActivity(intent);
            }
        });
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        collapsingToolbarLayout.setTitle("Solicitudes");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Se abrira a la aplicacion de MAILS", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("text/email");
                startActivity(Intent.createChooser(email,"Notificacion manual"));

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_master, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent i = null;
        switch (id) {
            case R.id.action_settings:
                i = new Intent(MasterActivity.this, PreferencesActivity.class);
                startActivity(i);
                return true;
            case R.id.action_login:
                i = new Intent(MasterActivity.this, LoginActivity.class);
                startActivity(i);
                metodoFinalizarActividad();
                return true;
            case R.id.action_exit:
                metodoFinalizarActividad();
        }

        return super.onOptionsItemSelected(item);
    }

    private class ListarSolicitudes extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            method = "recuperaSolicitudesPendientes";
            WebService ws = new WebService();
            listaRecuperadaWS = ws.recuperaLista(user, pass, method, MasterActivity.this);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (listaRecuperadaWS != null) {
                adaptador = new MyAdapter(MasterActivity.this, listaRecuperadaWS);
                lista.setAdapter(adaptador);
            } else {
                notificaError("Sin solicitudes pendientes.");
                WebService ws = new WebService();
                listaRecuperadaWS = ws.retornaItemsVacio();
                adaptador = new MyAdapter(MasterActivity.this, listaRecuperadaWS);
                lista.setAdapter(adaptador);
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
        Toast.makeText(MasterActivity.this, mensaje, Toast.LENGTH_LONG).show();
    }

    private void metodoFinalizarActividad() {
        escribeLog("Se invoco la finalizacion de la actividad.");
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            ListarSolicitudes listaRetorno = new ListarSolicitudes();
            listaRetorno.execute();
        } catch (Exception e) {
            escribeLog("Error al invocar la carga del WS: " + e.getMessage());
        }
    }
}
