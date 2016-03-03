package org.ruffineo.workflowmanager;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        escribeLog("entro al metodo onCreate LoginActivity");

        editTextUser = (EditText) findViewById(R.id.editText_user);
        editTextPass = (EditText) findViewById(R.id.editText_pass);

        buttonIngresar = (Button) findViewById(R.id.button_ingresar);
        buttonIngresar.setOnClickListener(LoginActivity.this);

        progressBarEjecucion = (ProgressBar) findViewById(R.id.progressBar_ejecucion);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_ingresar) {

            escribeLog("entro al metodo onClick button");

            user = editTextUser.getText().toString().trim();
            pass = editTextPass.getText().toString().trim();

            if (((user.length() > 0) && (pass.length() > 0)) || (!user.equals("") && (!pass.equals("")))) {
                method = "ValidarLogin";
                ValidarDatos validarDatos = new ValidarDatos();
                validarDatos.execute();
            } else {
                notificaError("Debe Ingresar Usuario y Contraseña");
            }
        }
    }

    private void escribeLog(String texto) {
        Log.i(TAG, texto);
    }

    private void notificaError(String mensaje) {
        Toast.makeText(LoginActivity.this, mensaje, Toast.LENGTH_LONG).show();
    }


    private class ValidarDatos extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            escribeLog("entro al doInBackground ValidarDatos");
            method = "validaUsuarioObjeto";
            WebService ws = new WebService();
            Respuesta respuesta = ws.consultaUsuarioObjeto(user, pass, method);
            escribeLog("recupero objeto respuesta - Codigo: " + respuesta.getCodigo() + " mensaje: " + respuesta.getMensaje() + " referencia: " + respuesta.getReferencia());
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
                Intent i = new Intent(LoginActivity.this, MasterActivity.class);
                i.putExtra("tokenU",user);
                i.putExtra("tokenP",pass);
                startActivity(i);
                finish();
            } else {
                escribeLog("Usuario / contraseña no validos. Resultado: " + validado);
                notificaError("Usuario/Contraseña no validos." + mensaje);
            }
        }
    }
}
