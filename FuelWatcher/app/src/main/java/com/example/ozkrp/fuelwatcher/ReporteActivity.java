package com.example.ozkrp.fuelwatcher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class ReporteActivity extends AppCompatActivity {

    private Spinner spinnerVehiculos;
    private TableLayout tabla;

    private DBAdapter dbAdapter;
    private int codigoVehiculo;

    private TextView tvCombustible;
    private TextView tvFecha;
    private TextView tvOdometro;
    private TextView tvMonto;
    private TextView tvLitros;
    private TextView tvRangoDias;
    private TextView tvKmRecorridos;
    private TextView tvKmPorLitro;
    private TableRow row;
    private TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);;

    private static final String TAG = "REPORT";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte);

        spinnerVehiculos = (Spinner) findViewById(R.id.Spinner_vehiculos);
        tabla = (TableLayout) findViewById(R.id.tabla);
        inicializarTabla();

        dbAdapter = new DBAdapter(this);
        dbAdapter.openDataBaseConnection();
        cargarListados();

    }

    private void cargarListados() {
        ArrayList<ItemSpinner> listaVehiculos = new ArrayList<ItemSpinner>();
        listaVehiculos = dbAdapter.recuperaVehiculos();
        ArrayAdapter<ItemSpinner> vehiculos = new ArrayAdapter<ItemSpinner>(ReporteActivity.this, android.R.layout.simple_spinner_dropdown_item, listaVehiculos);
        if (vehiculos != null) {
            spinnerVehiculos.setAdapter(vehiculos);
            spinnerVehiculos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ItemSpinner itemSeleccionado = (ItemSpinner) parent.getSelectedItem();
                    codigoVehiculo = itemSeleccionado.getCodigo();
                    recuperarRecargas();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

        }

    }
    private void recuperarRecargas() {
        ArrayList<Cargas> listaCargas = new ArrayList<Cargas>();
        listaCargas = dbAdapter.recuperaCargas(codigoVehiculo);
        for(int i=0; i < listaCargas.size(); i++) {
            Cargas individual = listaCargas.get(i);
            Log.i(TAG,"ANTES valor del indice de tabla: " + tabla.getChildCount());
            agregarNuevaFila(individual);
            Log.i(TAG,"DESPUES valor del indice de tabla: " + tabla.getChildCount());
        }
        ArrayAdapter<Cargas> adapter = new ArrayAdapter<Cargas>(ReporteActivity.this, android.R.layout.simple_list_item_1, listaCargas);
    }

    //Initiates the table
    public void inicializarTabla(){
        row = new TableRow(this);
        lp.setMargins(3,3,2,10);
        row.setLayoutParams(lp);

        //This part defines the layout to be used for creating new rows
        tvCombustible = new TextView(this);
        tvFecha = new TextView(this);
        tvOdometro = new TextView(this);
        tvMonto = new TextView(this);
        tvLitros = new TextView(this);
        tvRangoDias = new TextView(this);
        tvKmRecorridos = new TextView(this);
        tvKmPorLitro = new TextView(this);

        //This generates the caption row
        tvCombustible.setText("Fuel");
        tvCombustible.setPadding(3, 3, 3, 3);

        tvFecha.setText("Date");
        tvFecha.setPadding(3, 3, 3, 3);

        tvOdometro.setText("Odometro");
        tvOdometro.setPadding(3, 3, 3, 3);

        tvMonto.setText("Monto");
        tvMonto.setPadding(3, 3, 3, 3);

        tvLitros.setText("Litros");
        tvLitros.setPadding(3, 3, 3, 3);

        tvRangoDias.setText("Dias");
        tvRangoDias.setPadding(3, 3, 3, 3);

        tvKmRecorridos.setText("Kms.");
        tvKmRecorridos.setPadding(3, 3, 3, 3);

        tvKmPorLitro.setText("Km/L");
        tvKmPorLitro.setPadding(3, 3, 3, 3);

        row.addView(tvCombustible);
        row.addView(tvFecha);
        row.addView(tvOdometro);
        row.addView(tvMonto);
        row.addView(tvLitros);
        row.addView(tvRangoDias);
        row.addView(tvKmRecorridos);
        row.addView(tvKmPorLitro);

        tabla.addView(row,0);

    }

    public void agregarNuevaFila (Cargas carga) {

        Log.i (TAG, "OBJETO - " + carga.toString());
        Log.i(TAG, "Fecha: " + carga.getFecha());
        Log.i(TAG, "Odometro: " + carga.getOdometro());
        Log.i(TAG, "Monto: " + carga.getMonto());
        Log.i(TAG, "litros: " + carga.getLitros());
        Log.i(TAG, "rangoDias: " + carga.getRangoDias());
        Log.i(TAG, "kilometrosRecorrido: " + carga.getKmRecorridos());
        Log.i(TAG, "kilometrosPorLitro: " + carga.getKmLitro());
        Log.i(TAG, "Combustible: " + carga.getCodigoCombustible());


        row = new TableRow(this);
        row.setLayoutParams(lp);

        tvCombustible = new TextView(this);
        tvFecha = new TextView(this);
        tvOdometro = new TextView(this);
        tvMonto = new TextView(this);
        tvLitros = new TextView(this);
        tvRangoDias = new TextView(this);
        tvKmRecorridos = new TextView(this);
        tvKmPorLitro = new TextView(this);

        tvCombustible.setText(String.valueOf(carga.getCodigoCombustible()));
        tvCombustible.setPadding(3, 3, 3, 3);

        tvFecha.setText(String.valueOf(carga.getFecha()));
        tvFecha.setPadding(3, 3, 3, 3);

        tvOdometro.setText(String.valueOf(carga.getOdometro()));
        tvOdometro.setPadding(3, 3, 3, 3);

        tvMonto.setText(String.valueOf(carga.getMonto()));
        tvMonto.setPadding(3, 3, 3, 3);

        tvLitros.setText(String.valueOf(carga.getLitros()));
        tvLitros.setPadding(3, 3, 3, 3);

        tvRangoDias.setText(String.valueOf(carga.getRangoDias()));
        tvRangoDias.setPadding(3, 3, 3, 3);

        tvKmRecorridos.setText(String.valueOf(carga.getKmRecorridos()));
        tvKmRecorridos.setPadding(3, 3, 3, 3);

        tvKmPorLitro.setText(String.valueOf(String.format("%.2f", carga.getKmLitro())));
        tvKmPorLitro.setPadding(3, 3, 3, 3);

        row.addView(tvCombustible);
        row.addView(tvFecha);
        row.addView(tvOdometro);
        row.addView(tvMonto);
        row.addView(tvLitros);
        row.addView(tvRangoDias);
        row.addView(tvKmRecorridos);
        row.addView(tvKmPorLitro);

        tabla.addView(row,tabla.getChildCount());
    }

    @Override
    public void finish() {
        super.finish();
        dbAdapter.closeDataBaseConnection();
    }
}
