package org.ruffineo.workflowmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class TareaActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "TAREAS_LOG";

    private TextView numeroSolicitud;
    private TextView personaCodigo;
    private TextView personaNombre;
    private TextView fechaIniciadaSolicitud;
    private TextView referenciaSolicitud;
    private TextView tipoSolicitudCodigo;
    private TextView tipoSolicitudDescripcion;
    private TextView numeroTarea;
    private TextView tipoTareaCodigo;
    private TextView tipoTareaDescripcion;
    private Spinner estadoTarea;
    private TextView tareaFechaAsignacion;
    private TextView tareaAsignadorCodigoUsuario;
    private TextView tareaAsignadorNombreUsuario;
    private TextView tareaDescripcion;
    private EditText tareaComentarioRecepcion;
    private EditText tareaComentarioAdicional;
    private Button boton1;
    private Button boton2;
    private Button boton3;
    private String user;
    private String pass;
    private String solicitud;
    private Tarea tarea = null;
    private String method;
    private String recupero = "ER";
    private String nuevoEstado = "PE";
    private String estadoSolicitud = "";
    private Respuesta respuesta = new Respuesta(0, "ER", "Inicializado en la app");
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarea);

        escribeLog("entro al metodo onCreate de tareaActivity");

        if (savedInstanceState == null) {
            escribeLog("savedInstanceState es nula");
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                escribeLog("extras es nulo");
                user = null;
                pass = null;
            } else {
                escribeLog("extras no es nulo");
                user = extras.getString("tokenU");
                pass = extras.getString("tokenP");
                solicitud = extras.getString("tokenS");
                escribeLog("extras recupero, user: " + user + " pass: " + pass + " solicitud: " + solicitud);
            }
        } else {
            escribeLog("savedInstanceState no es nula");
            user = (String) savedInstanceState.getSerializable("tokenU");
            pass = (String) savedInstanceState.getSerializable("tokenP");
            solicitud = (String) savedInstanceState.getSerializable("tokenS");
            escribeLog("savedInstance recupero, user: " + user + " pass: " + pass + " solicitud: " + solicitud);
        }

        /**
         * asignacion de todas las variables de la vista a sus correspondientes
         * manejadores
         */

        numeroSolicitud = (TextView) findViewById(R.id.tarea_numeroSolicitud);
        personaCodigo = (TextView) findViewById(R.id.tarea_codigoPersona);
        personaNombre = (TextView) findViewById(R.id.tarea_nombrePersona);
        fechaIniciadaSolicitud = (TextView) findViewById(R.id.tarea_iniciado);
        referenciaSolicitud = (TextView) findViewById(R.id.tarea_referencia);
        tipoSolicitudCodigo = (TextView) findViewById(R.id.tarea_tipoSolicitudCodigo);
        tipoSolicitudDescripcion = (TextView) findViewById(R.id.tarea_tipoSolicitudDescripcion);
        numeroTarea = (TextView) findViewById(R.id.tarea_numeroTarea);
        tipoTareaCodigo = (TextView) findViewById(R.id.tarea_tipoTareaCodigo);
        tipoTareaDescripcion = (TextView) findViewById(R.id.tarea_tipoTareaDescripcion);
        estadoTarea = (Spinner) findViewById(R.id.spinner_estadoTarea);
        tareaFechaAsignacion = (TextView) findViewById(R.id.tarea_fechaAsignado);
        tareaAsignadorCodigoUsuario = (TextView) findViewById(R.id.tarea_usuarioAsignadorCodigo);
        tareaAsignadorNombreUsuario = (TextView) findViewById(R.id.tarea_usuarioAsignadorNombre);
        tareaDescripcion = (TextView) findViewById(R.id.tarea_descripcion);
        tareaComentarioRecepcion = (EditText) findViewById(R.id.tarea_comentarioRecepcion);
        tareaComentarioRecepcion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (tareaComentarioRecepcion.getText().toString().length() > 0) {
                    boton1.setEnabled(true);
                    boton2.setEnabled(true);
                    boton3.setEnabled(true);
                } else {
                    boton1.setEnabled(false);
                    boton2.setEnabled(false);
                    boton3.setEnabled(false);
                }
            }
        });
        tareaComentarioAdicional = (EditText) findViewById(R.id.tarea_comentarioAdicional);
        boton1 = (Button) findViewById(R.id.botonAprobacionTarea1);
        boton1.setOnClickListener(this);
        boton2 = (Button) findViewById(R.id.botonAprobacionTarea2);
        boton2.setOnClickListener(this);
        boton3 = (Button) findViewById(R.id.botonAprobacionTarea3);
        boton3.setOnClickListener(this);

        try {
        RecuperaDetallesTarea detallesTarea = new RecuperaDetallesTarea();
        detallesTarea.execute();
        } catch (Exception e) {
            escribeLog("excepcion al recuperar tarea: " + e.getMessage());
            finish();
        }

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.botonAprobacionTarea1:
                escribeLog("Se hizo clic en el boton: " + boton1.getText());
                nuevoEstado = "CA";
                estadoSolicitud = "cancelar";
                break;
            case R.id.botonAprobacionTarea2:
                escribeLog("Se hizo clic en el boton: " + boton2.getText());
                nuevoEstado = "RE";
                estadoSolicitud = "rechazar";
                break;
            case R.id.botonAprobacionTarea3:
                escribeLog("Se hizo clic en el boton: " + boton3.getText());
                nuevoEstado = "PE";
                estadoSolicitud = "aprobar";
                break;
        }

        procesarSolicitud();
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

    private void escribeLog(String texto) {
        Log.i(TAG, texto);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Tarea Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://org.ruffineo.workflowmanager/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Tarea Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://org.ruffineo.workflowmanager/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    private class RecuperaDetallesTarea extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            escribeLog("entro a doInBackground");
            //method = "recuperaTareasParametroObjeto";
            method = "recuperaTarea";
            WebService ws = new WebService();
            tarea = ws.recuperaTarea(user, pass, solicitud, method);
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
                cargaDatosTarea();
            } else {
                notificaError("No se encontraron datos de la tarea.");
                finish();
            }
        }
    }

    private void cargaDatosTarea() {
        numeroSolicitud.setText(solicitud.toString());
        personaCodigo.setText(tarea.getPersonaCodigo().toString());
        personaNombre.setText(tarea.getPersonaNombre());
        fechaIniciadaSolicitud.setText(tarea.getSolicitudFechaInicio().toString());
        referenciaSolicitud.setText(tarea.getSolicitudReferencia().toString());
        tipoSolicitudCodigo.setText(tarea.getSolicitudTipoCodigo().toString());
        tipoSolicitudDescripcion.setText(tarea.getSolicitudTipoDescripcion().toString());
        numeroTarea.setText(tarea.getTareaNumero().toString());
        tipoTareaCodigo.setText(tarea.getTareaTipoCodigo().toString());
        tipoTareaDescripcion.setText(tarea.getTareaDescripcion().toString());
        tareaFechaAsignacion.setText(tarea.getTareaFechaAsignacion().toString());
        tareaAsignadorCodigoUsuario.setText(tarea.getTareaAsignadorCodigo().toString());
        tareaAsignadorNombreUsuario.setText(tarea.getTareaAsignadorNombre().toString());
        tareaDescripcion.setText(tarea.getTareaDescripcion().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tarea, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_volver:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void notificaError(String mensaje) {
        Toast.makeText(TareaActivity.this, mensaje, Toast.LENGTH_LONG).show();
    }

    private class ActualizaEstadoSolicitud extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            escribeLog("entro a doInBackground ActualizaEstadoSolicitud");
            //method = "recuperaTareasParametroObjeto";
            method = "actualizaEstadoSolicitud";
            WebService ws = new WebService();
            respuesta = ws.actualizaEstadoSolicitud(user, pass, solicitud, nuevoEstado, method);
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

}

