package com.example.ozkrp.fuelwatcher;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ReporteActivity extends AppCompatActivity {

    private Spinner spinnerVehiculos;
    private TextView promedio;
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
    //private TextView tvKmPorLitro;
    private TableRow row;
    private TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);;

    private static final String TAG = "REPORT";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte);

        spinnerVehiculos = (Spinner) findViewById(R.id.Spinner_vehiculos);
        tabla = (TableLayout) findViewById(R.id.tabla);

        promedio = (TextView) findViewById(R.id.texto_promedioConsumo);

        dbAdapter = new DBAdapter(this);
        dbAdapter.openDataBaseConnection();
        cargarListados();

    }

    private void calcularConsumoPromedio() {
        double litrosPor100 = dbAdapter.retornaConsumoPromedio(codigoVehiculo, 1);
        String cadenaPromedio = String.valueOf(String.format("%.2f", litrosPor100));
        promedio.setText("Promedio consumo: " + cadenaPromedio);
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
                    poblarTabla();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }

            });

        }

    }

    private void poblarTabla() {
        vaciaTabla();
        inicializarTabla();
        recuperarRecargas();
    }

    private void vaciaTabla() {
        tabla.removeAllViews();
    }

    private void recuperarRecargas() {
        ArrayList<Cargas> listaCargas = new ArrayList<Cargas>();
        listaCargas = dbAdapter.recuperaCargas(codigoVehiculo);
        for(int i=0; i < listaCargas.size(); i++) {
            Cargas individual = listaCargas.get(i);
            agregarNuevaFila(individual);
        }
        if (tabla.getChildCount()>1) {
            calcularConsumoPromedio();
            Log.i(TAG,"DESPUES valor del indice de tabla: " + tabla.getChildCount());
        }
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
        //tvKmPorLitro = new TextView(this);

        //This generates the caption row
        tvCombustible.setText(" " + "Fuel" + " ");
        tvCombustible.setPadding(3, 3, 3, 3);
        tvCombustible.setBackgroundResource(R.drawable.cell_shape);

        tvFecha.setText(" " + "Date" + " ");
        tvFecha.setPadding(3, 3, 3, 3);
        tvFecha.setBackgroundResource(R.drawable.cell_shape);

        tvOdometro.setText(" " + "Odometro" + " ");
        tvOdometro.setPadding(3, 3, 3, 3);
        tvOdometro.setBackgroundResource(R.drawable.cell_shape);

        tvMonto.setText(" " + "Monto" + " ");
        tvMonto.setPadding(3, 3, 3, 3);
        tvMonto.setBackgroundResource(R.drawable.cell_shape);

        tvLitros.setText(" " + "Litros" + " ");
        tvLitros.setPadding(3, 3, 3, 3);
        tvLitros.setBackgroundResource(R.drawable.cell_shape);

        tvRangoDias.setText(" " + "Dias" + " ");
        tvRangoDias.setPadding(3, 3, 3, 3);
        tvRangoDias.setBackgroundResource(R.drawable.cell_shape);

        tvKmRecorridos.setText(" " + "Kms." + " ");
        tvKmRecorridos.setPadding(3, 3, 3, 3);
        tvKmRecorridos.setBackgroundResource(R.drawable.cell_shape);

        //tvKmPorLitro.setText(" " + "Km/L" + " ");
        //tvKmPorLitro.setPadding(3, 3, 3, 3);
        //tvKmPorLitro.setBackgroundResource(R.drawable.cell_shape);

        row.addView(tvCombustible);
        row.addView(tvFecha);
        row.addView(tvOdometro);
        row.addView(tvMonto);
        row.addView(tvLitros);
        row.addView(tvRangoDias);
        row.addView(tvKmRecorridos);
        //row.addView(tvKmPorLitro);

        tabla.addView(row,0);

    }

    public void agregarNuevaFila (Cargas carga) {

        Log.i(TAG, "Fecha: " + carga.getFecha() + " Odometro: " + carga.getOdometro() + " Monto: " + carga.getMonto()
                + " litros: " + carga.getLitros() + " rangoDias: " + carga.getRangoDias()
                + " kilometrosRecorrido: " + carga.getKmRecorridos() + " kilometrosPorLitro: " + carga.getKmLitro()
                + " Combustible: " + carga.getCodigoCombustible());

        row = new TableRow(this);
        row.setLayoutParams(lp);
        row.setId(tabla.getChildCount());

        tvCombustible = new TextView(this);
        tvFecha = new TextView(this);
        tvOdometro = new TextView(this);
        tvMonto = new TextView(this);
        tvLitros = new TextView(this);
        tvRangoDias = new TextView(this);
        tvKmRecorridos = new TextView(this);
        //tvKmPorLitro = new TextView(this);

        tvCombustible.setText(String.valueOf(carga.getCodigoCombustible()) + " ");
        tvCombustible.setPadding(3, 3, 3, 3);
        tvCombustible.setBackgroundResource(R.drawable.cell_shape);

        tvFecha.setText(String.valueOf(carga.getFecha()) + " ");
        tvFecha.setPadding(3, 3, 3, 3);
        tvFecha.setBackgroundResource(R.drawable.cell_shape);

        tvOdometro.setText(" " + String.valueOf(carga.getOdometro()) + " ");
        tvOdometro.setPadding(3, 3, 3, 3);
        tvOdometro.setBackgroundResource(R.drawable.cell_shape);

        int amount = carga.getMonto();
        DecimalFormat formatter = new DecimalFormat("#,###");
        tvMonto.setText(" " + String.valueOf(formatter.format(amount)) + " ");
        tvMonto.setPadding(3, 3, 3, 3);
        tvMonto.setBackgroundResource(R.drawable.cell_shape);

        tvLitros.setText(" " + String.valueOf(carga.getLitros()) + " ");
        tvLitros.setPadding(3, 3, 3, 3);
        tvLitros.setBackgroundResource(R.drawable.cell_shape);

        tvRangoDias.setText(" " + String.valueOf(carga.getRangoDias()) + " ");
        tvRangoDias.setPadding(3, 3, 3, 3);
        tvRangoDias.setBackgroundResource(R.drawable.cell_shape);

        tvKmRecorridos.setText(" " + String.valueOf(carga.getKmRecorridos()) + " ");
        tvKmRecorridos.setPadding(3, 3, 3, 3);
        tvKmRecorridos.setBackgroundResource(R.drawable.cell_shape);

        //tvKmPorLitro.setText(" " + String.valueOf(String.format("%.2f", carga.getKmLitro())) + " ");
        //tvKmPorLitro.setPadding(3, 3, 3, 3);
        //tvKmPorLitro.setBackgroundResource(R.drawable.cell_shape);

        row.addView(tvCombustible);
        row.addView(tvFecha);
        row.addView(tvOdometro);
        row.addView(tvMonto);
        row.addView(tvLitros);
        row.addView(tvRangoDias);
        row.addView(tvKmRecorridos);
        //row.addView(tvKmPorLitro);

        row.setBackgroundColor(getResources().getColor(R.color.blanco));

        tabla.addView(row,tabla.getChildCount());

        Log.i(TAG, "Id de la fila " + row.getId());
        row.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.i(TAG, "Fila: " + v.getId());
                dialogoBorradoFila(v);
                return false;
            }
        });
    }

    private void dialogoBorradoFila(final View numeroFila) {
            AlertDialog confirma = new AlertDialog.Builder(this).create();
            confirma.setTitle("Confirmar");
            confirma.setMessage("Desea borrar la carga?");
            confirma.setButton(AlertDialog.BUTTON_NEGATIVE, "Atras", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            confirma.setButton(AlertDialog.BUTTON_POSITIVE, "Confirma", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //tabla.removeViewAt(numeroFila);
                    Log.i(TAG, "Borrar fila: " + numeroFila);
                    borrarFila(numeroFila);
                }
            });
            confirma.show();
    }

    private void borrarFila(View numeroFila) {
        TableRow vista = (TableRow) tabla.getChildAt(numeroFila.getId());
        TextView textViewCombus = (TextView) vista.getChildAt(0);
        String combustible = textViewCombus.getText().toString().trim();
        int combustibleCodigo = Integer.parseInt(combustible);
        TextView textViewFecha = (TextView) vista.getChildAt(1);
        String fechaCargaBorrar = textViewFecha.getText().toString().trim();
        Log.i(TAG, "BORRAR Vehiculo: " + codigoVehiculo +
                " Combustible String: " + combustible +
                " Combustible Int: " + combustibleCodigo +
                " Fecha: " + fechaCargaBorrar);
        if (dbAdapter.borrarRegistro(codigoVehiculo, combustibleCodigo, fechaCargaBorrar)) {
            tabla.removeViewInLayout(numeroFila);
            poblarTabla();
        } else {
            Toast.makeText(ReporteActivity.this, "No se pudo borrar el registro.-", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void finish() {
        super.finish();
        dbAdapter.closeDataBaseConnection();
    }
}
