package org.ruffineo.workflowmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarea);

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
        tareaAsignadorCodigoUsuario= (TextView) findViewById(R.id.tarea_usuarioAsignadorCodigo);
        tareaAsignadorNombreUsuario= (TextView) findViewById(R.id.tarea_usuarioAsignadorNombre);
        tareaDescripcion = (TextView) findViewById(R.id.tarea_descripcion);
        tareaComentarioRecepcion = (EditText) findViewById(R.id.tarea_comentarioRecepcion);
        tareaComentarioAdicional = (EditText) findViewById(R.id.tarea_comentarioAdicional);
        boton1 = (Button) findViewById(R.id.botonAprobacionTarea1);
        boton1.setOnClickListener(this);
        boton2 = (Button) findViewById(R.id.botonAprobacionTarea2);
        boton2.setOnClickListener(this);
        boton3 = (Button) findViewById(R.id.botonAprobacionTarea3);
        boton3.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.botonAprobacionTarea1:
                escribeLog("Se hizo clic en el boton: " + v.getId());
                break;
            case R.id.botonAprobacionTarea2:
                escribeLog("Se hizo clic en el boton: " + v.getId());
                break;
            case R.id.botonAprobacionTarea3:
                escribeLog("Se hizo clic en el boton: " + v.getId());
                break;
        }


    }

    private void escribeLog(String texto) {
        Log.i(TAG, texto);
    }

}

