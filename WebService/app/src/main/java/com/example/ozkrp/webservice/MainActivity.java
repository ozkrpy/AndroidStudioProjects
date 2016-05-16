package com.example.ozkrp.webservice;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompatExtras;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "LOGIN_LOG";

    private EditText editTextUser;
    private EditText editTextPass;
    private Button buttonIngresar;
    private ProgressBar progressBarEjecucion;

    private String user;
    private String pass;
    private String method;
    private String validado = "NO";
    String mensaje;

    private String recupero = "ER";
    private String nuevoEstado = "PE";
    private String estadoSolicitud = "";
    private Respuesta respuesta = new Respuesta(0, "ER", "Inicializado en la app");
    private Tarea tarea = null;

    private String[] listaRecuperada;
    private ArrayList<Item> listaRecuperadaWS;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle drawerToggle;
    private ListView lista;
    //private MyAdapter adaptador;
    ArrayList<Item> listaSolicitudes = new ArrayList<Item>();
    ListView mDrawerList;
    static final String[] items = new String[]{
            "Actualizar lista de tareas",
            "Ajustes de la aplicacion",
            "Salir"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        escribeLog("entro al metodo onCreate LoginActivity");

        editTextUser = (EditText) findViewById(R.id.editText_user);
        editTextPass = (EditText) findViewById(R.id.editText_pass);

        buttonIngresar = (Button) findViewById(R.id.button_ingresar);
        buttonIngresar.setOnClickListener(MainActivity.this);

        progressBarEjecucion = (ProgressBar) findViewById(R.id.progressBar_ejecucion);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_ingresar) {

            escribeLog("entro al metodo onClick button");

            user = editTextUser.getText().toString().trim();
            pass = editTextPass.getText().toString().trim();

            if (((user.length() > 0) && (pass.length() > 0)) || (!user.equals("") && (!pass.equals("")))) {
                procesarSolicitud();

            } else {
                notificaError("Debe Ingresar Usuario y Contraseña");
            }
        }
    }

    private void procesarSolicitud() {
        escribeLog("Entro a procesar solicitud");
        AlertDialog confirma = new AlertDialog.Builder(this).create();
        confirma.setTitle("Confirmar");
        confirma.setMessage("Desea " + estadoSolicitud + " la solicitud?");
        confirma.setButton(AlertDialog.BUTTON_NEGATIVE, "Atras", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                escribeLog("Se selecciono atras");
                dialog.cancel();
            }
        });
        confirma.setButton(AlertDialog.BUTTON_POSITIVE, "Confirma", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                escribeLog("Se procesara la solicitud");
                ActualizaEstadoSolicitud actualizaEstadoSolicitud = new ActualizaEstadoSolicitud();
                actualizaEstadoSolicitud.execute();
            }
        });
        confirma.show();
    }

    private class ActualizaEstadoSolicitud extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            escribeLog("entro a doInBackground ActualizaEstadoSolicitud");
            //method = "recuperaTareasParametroObjeto";
            method = "actualizaEstadoSolicitud";
            WebService ws = new WebService();
            respuesta = ws.actualizaEstadoSolicitud(user, pass, "2386", "OK", method);
            if (respuesta.getCodigo() == 0) {
                recupero = "ER";
            } else if (respuesta.getCodigo() == 1){
                recupero = "OK";
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            escribeLog("entro a onPreExecute");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            escribeLog("entro a onPostExecute, recupero datos: " + recupero.toString());
            if (recupero.equals("OK")) {
                notificaError(respuesta.getReferencia());
                finish();
            } else {
                notificaError("No se actualizo correctamente.");
            }
        }
    }

    private void escribeLog(String texto) {
        Log.i(TAG, texto);
    }

    private void notificaError(String mensaje) {
        Toast.makeText(MainActivity.this, mensaje, Toast.LENGTH_LONG).show();
    }


    private class ValidarDatos extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            escribeLog("entro al doInBackground ValidarDatos");
            //method = "validarUsuarioParametroObjeto";
            method = "validarUsuario";
            WebService ws = new WebService();
            Respuesta respuesta = ws.consultaUsuario(user, pass, method);
            escribeLog("recupero objeto respuesta - Codigo: " + respuesta.getCodigo() +" mensaje: " + respuesta.getMensaje() + " referencia: " + respuesta.getReferencia());
            if (respuesta.getCodigo() == 1) {
                validado = "OK";
            } else {
                validado = "ER";
                mensaje = respuesta.getReferencia();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            escribeLog("entro al onPreExecute ValidarDatos");
            progressBarEjecucion.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            escribeLog("entro al onPostExecute ValidarDatos");
            progressBarEjecucion.setVisibility(View.INVISIBLE);
            if (validado.equals("OK")) {
                escribeLog("Usuario / contraseña validos. Resultado: " + validado);

            } else {
                escribeLog("Usuario / contraseña no validos. Resultado: " + validado + " " + mensaje);
                notificaError("Usuario/Contraseña no validados. " + mensaje);
            }
        }
    }

    private class RecuperaDetallesTarea extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            escribeLog("entro a doInBackground");
            //method = "recuperaTareasParametroObjeto";
            method = "recuperaTarea";
            WebService ws = new WebService();
            tarea = ws.recuperaTarea(user, pass, "2386", method);
            if (tarea == null) {
                recupero = "ER";
            } else {
                recupero = "OK";
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            escribeLog("entro a onPreExecute");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            escribeLog("entro a onPostExecute, recupero datos: " + recupero.toString());
            if (recupero.equals("OK")) {
                //cargaDatosTarea();
                notificaError("se recupero la tarea: " + tarea.getPersonaNombre().toString());
            } else {
                notificaError("No se encontraron datos de la tarea.");
                finish();
            }
        }
    }


    private class ListarSolicitudes extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            method = "recuperaSolicitudesPendientes";
            WebService ws = new WebService();
            listaRecuperadaWS = ws.recuperaLista(user, pass, method);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (listaRecuperadaWS != null) {
                //adaptador = new MyAdapter(MasterActivity.this, listaRecuperadaWS);
                //lista.setAdapter(adaptador);
                escribeLog(listaRecuperadaWS.toString());
            } else {
                notificaError("Sin solicitudes pendientes.");
                WebService ws = new WebService();
                listaRecuperadaWS = ws.retornaItemsVacio();
                //adaptador = new MyAdapter(MasterActivity.this, listaRecuperadaWS);
                //lista.setAdapter(adaptador);
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

}
