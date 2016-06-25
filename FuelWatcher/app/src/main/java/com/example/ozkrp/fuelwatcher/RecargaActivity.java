package com.example.ozkrp.fuelwatcher;

import android.content.ClipData;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RecargaActivity extends AppCompatActivity implements
        DatePickerFragment.DatePickerListener, TimePickerFragment.TimePickerListener{

    private static final String TAG = "ALTA";
    /*Vistas en la actividad principal*/
    Button botonConfirmar;
    EditText fechaRecarga;
    EditText odometro;
    EditText montoRecarga;
    TextView litrosCargados;
    Spinner spinnerVehiculos;
    Spinner spinnerCombustibles;

    /*Variables de la ejecucion*/
    private String calculoLitrosCargados = "0";
    private Calendar calendar;
    private FragmentManager fm;
    String cadenaFecha = null;
    Date date = null;
    int codigoVehiculo;
    int codigoCombustible;
    int recuperarPrecioCombustible;

    /*Database connector*/
    private DBAdapter dbAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recarga);

        calendar = Calendar.getInstance();

        /* Asociar las vistas con las variables de sesion */
        spinnerVehiculos = (Spinner) findViewById(R.id.Spinner_vehiculos);
        spinnerCombustibles = (Spinner) findViewById(R.id.Spinner_combustibles);
        fechaRecarga = (EditText) findViewById(R.id.editText_fecha_recarga);
        fechaRecarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm = getSupportFragmentManager();
                DatePickerFragment dateDialog = new DatePickerFragment();
                dateDialog.show(fm, "fragment_date");
            }
        });
        odometro = (EditText) findViewById(R.id.editText_odometro);
        litrosCargados = (TextView) findViewById(R.id.textoLitrosNumero);
        montoRecarga = (EditText) findViewById(R.id.editText_monto_recarga);
        montoRecarga.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (montoRecarga.getText().toString().length() > 0)
                    cambiaLitros();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        botonConfirmar = (Button) findViewById(R.id.botonConfirmarCarga);
        botonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmarOperacion();
            }
        });
        dbAdapter = new DBAdapter(this);
        dbAdapter.openDataBaseConnection();
        cargarListados();
    }

    private void cambiaLitros() {

        int dinero = Integer.parseInt(montoRecarga.getText().toString());
        double litros = dinero / (double) recuperarPrecioCombustible;
        calculoLitrosCargados = String.valueOf(String.format("%.2f", litros));
        litrosCargados.setText(calculoLitrosCargados);
    }

    private void confirmarOperacion() {
        AlertDialog confirma = new AlertDialog.Builder(this).create();
        confirma.setTitle("Confirmar");
        confirma.setMessage("Desea guardar la carga?");
        confirma.setButton(AlertDialog.BUTTON_NEGATIVE, "Atras", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        confirma.setButton(AlertDialog.BUTTON_POSITIVE, "Confirma", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                procesarNuevaCarga();
            }
        });
        confirma.show();
    }

    private void procesarNuevaCarga() {
        int odometroAltaRecarga = Integer.parseInt(odometro.getText().toString().trim());
        int montoAltaRecarga = Integer.parseInt(montoRecarga.getText().toString());
        double litrosAltaRecarga = Double.parseDouble(litrosCargados.getText().toString());

        Log.i(TAG, "Fecha: " + cadenaFecha);
        Log.i(TAG, "Odometro al momento de la carga: " + odometroAltaRecarga);
        Log.i(TAG, "Monto Gs.: " + montoAltaRecarga);
        Log.i(TAG, "Litros cargados: " + litrosAltaRecarga);
        Log.i(TAG, "Combustible: " + codigoCombustible);
        Log.i(TAG, "Vehiculo: " + codigoVehiculo);

        dbAdapter.altaRecarga(cadenaFecha, odometroAltaRecarga, montoAltaRecarga, litrosAltaRecarga, codigoCombustible, codigoVehiculo);
        finish();
    }

    private void cargarListados() {
        ArrayList<ItemSpinner> listaVehiculos = new ArrayList<ItemSpinner>();
        listaVehiculos = dbAdapter.recuperaVehiculos();
        ArrayAdapter<ItemSpinner> vehiculos = new ArrayAdapter<ItemSpinner>(RecargaActivity.this, android.R.layout.simple_spinner_dropdown_item, listaVehiculos);
        ArrayList<ItemSpinner> listaCombustibles = new ArrayList<ItemSpinner>();
        listaCombustibles = dbAdapter.recuperaCombustibles();
        ArrayAdapter<ItemSpinner> combustibles = new ArrayAdapter<ItemSpinner>(RecargaActivity.this, android.R.layout.simple_spinner_dropdown_item, listaCombustibles);
        if (vehiculos != null) {
            spinnerVehiculos.setAdapter(vehiculos);
            spinnerVehiculos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ItemSpinner itemSeleccionado = (ItemSpinner) parent.getSelectedItem();
                    codigoVehiculo = itemSeleccionado.getCodigo();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
        if (combustibles != null) {
            spinnerCombustibles.setAdapter(combustibles);
            spinnerCombustibles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ItemSpinner itemSeleccionado = (ItemSpinner) parent.getSelectedItem();
                    codigoCombustible = itemSeleccionado.getCodigo();
                    recuperarPrecioCombustible = dbAdapter.recuperaPrecioCombustible(codigoCombustible);


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });}
    }

    @Override
    public void onDateSet(int year, int month, int day)
    {
        calendar.set(year, month, day);

        TimePickerFragment timeDialog = new TimePickerFragment();
        timeDialog.show(fm, "fragment_time");

    }

    @Override
    public void onTimeSet(int hourOfDay, int minute)
    {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

        date = calendar.getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            cadenaFecha = format1.format(date);
            fechaRecarga.setText(cadenaFecha);
        } catch (Exception e1) {
            Log.i("ERROR", "Mensaje: " + e1.getMessage());
        }
    }


    @Override
    public void finish() {
        super.finish();
        dbAdapter.closeDataBaseConnection();
    }
}
