package com.example.ozkrp.fuelwatcher;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ParametrosActivity extends AppCompatActivity implements View.OnClickListener{

    EditText marca;
    EditText modelo;
    EditText tipoCombustible;
    EditText anno;
    EditText precioCombustible;
    EditText odometro;
    Button botonAltaVehiculo;
    Button botonAltaCombustible;

    private DBAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametros);
        marca = (EditText) findViewById(R.id.editText_Parametros_Marca);
        modelo = (EditText) findViewById(R.id.editText_Parametros_Modelo);
        anno = (EditText) findViewById(R.id.editText_Parametros_Anno);
        odometro = (EditText) findViewById(R.id.editText_Parametros_Odometro);
        botonAltaVehiculo = (Button) findViewById(R.id.botonAltaVehiculo);
        botonAltaVehiculo.setOnClickListener(this);

        tipoCombustible = (EditText) findViewById(R.id.editText_Parametros_TipoCombusible);
        precioCombustible = (EditText) findViewById(R.id.editText_Parametros_PrecioCombustible);
        botonAltaCombustible = (Button) findViewById(R.id.botonAltaCombustible);
        botonAltaCombustible.setOnClickListener(this);

        dbAdapter = new DBAdapter(this);
        dbAdapter.openDataBaseConnection();
    }

    private void altaVehiculo () {
        String marcaVehiculo = marca.getText().toString();
        int annoVehiculo = Integer.parseInt(anno.getText().toString());
        String modeloVehiculo = modelo.getText().toString();
        int odometroVehiculo = Integer.parseInt(odometro.getText().toString());
        Log.i ("PAR", "Vehiculo: " + marcaVehiculo + " - " + modeloVehiculo + " - " + annoVehiculo + " - " + odometroVehiculo);
        dbAdapter.altaVehiculo(marcaVehiculo,annoVehiculo,modeloVehiculo,odometroVehiculo);
    }

    private void altaCombustible () {
        String tipo = tipoCombustible.getText().toString();
        int precio = Integer.parseInt(precioCombustible.getText().toString());
        Log.i ("PAR", "Combustible: " + tipo+ " - " + precio);
        dbAdapter.altaCombustible(tipo, precio);
    }

    private boolean validarCamposVehiculo() {
        if ((marca.getText().toString()!=null || modelo.getText().toString()!=null || anno.getText().toString()!=null || odometro.getText().toString()!=null)
             && (marca.getText().toString().length()>0 || modelo.getText().toString().length()>0  || anno.getText().toString().length()>0  || odometro.getText().toString().length()>0))
            return true;
        return false;
    }

    private boolean validarCamposCombustible() {
        if ((tipoCombustible.getText().toString()!=null || precioCombustible.getText().toString()!=null)
             && (tipoCombustible.getText().toString().length()>0 || precioCombustible.getText().toString().length()>0))
            return true;
        return false;
    }


    @Override
    public void onClick(View v) {
            switch (v.getId()) {
                case R.id.botonAltaVehiculo:
                    if (validarCamposVehiculo()) {
                        altaVehiculo();
                        vaciarVistasVehiculo();
                    } else {
                        Toast.makeText(ParametrosActivity.this, "Campos vacios al intentar guardar Vehiculo.", Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.botonAltaCombustible:
                    if (validarCamposCombustible()) {
                        altaCombustible();
                        vaciarVistasCombustible();
                    } else {
                        Toast.makeText(ParametrosActivity.this, "Campos vacios al intentar guardar Combustible.", Toast.LENGTH_LONG).show();
                    }
                    break;
            }

    }

    private void vaciarVistasCombustible() {
        tipoCombustible.setText("");
        precioCombustible.setText("");
    }

    private void vaciarVistasVehiculo() {
        marca.setText("");
        modelo.setText("");
        anno.setText("");
        odometro.setText("");
    }

    @Override
    public void finish() {
        super.finish();
        dbAdapter.closeDataBaseConnection();
    }
}
