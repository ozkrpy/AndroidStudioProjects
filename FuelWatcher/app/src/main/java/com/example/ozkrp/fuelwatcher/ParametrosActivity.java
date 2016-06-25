package com.example.ozkrp.fuelwatcher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.botonAltaVehiculo:
                altaVehiculo();
                vaciarVistasVehiculo();
                break;
            case R.id.botonAltaCombustible:
                altaCombustible();
                vaciarVistasCombustible();
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
