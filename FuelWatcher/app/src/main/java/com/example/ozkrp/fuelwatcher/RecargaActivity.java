package com.example.ozkrp.fuelwatcher;

import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RecargaActivity extends AppCompatActivity implements
        DatePickerFragment.DatePickerListener, TimePickerFragment.TimePickerListener{

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
        double litros = dinero / (double) 5380;
        calculoLitrosCargados = String.valueOf(String.format("%.2f", litros));
        Log.i("ALTA","Monto de la recarga: " + montoRecarga.getText() + " dinero: " + dinero + " litros: " + calculoLitrosCargados);
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
        finish();
    }

    private void cargarListados() {
        ArrayList<String> listaVehiculos = new ArrayList<String>();
        listaVehiculos = dbAdapter.recuperaVehiculos();
        ArrayAdapter<String> vehiculos = new ArrayAdapter<String>(RecargaActivity.this, android.R.layout.simple_list_item_1, listaVehiculos);
        ArrayList<String> listaCombustibles = new ArrayList<String>();
        listaCombustibles = dbAdapter.recuperaCombustibles();
        ArrayAdapter<String> combustibles = new ArrayAdapter<String>(RecargaActivity.this, android.R.layout.simple_list_item_1, listaCombustibles);
        if (vehiculos != null)
            spinnerVehiculos.setAdapter(vehiculos);
        if (combustibles != null)
            spinnerCombustibles.setAdapter(combustibles);
    }

    @Override
    public void onDateSet(int year, int month, int day)
    {
        // Set selected year, month and day in calendar object
        calendar.set(year, month, day);

        // Start Time dialog
        TimePickerFragment timeDialog = new TimePickerFragment();
        timeDialog.show(fm, "fragment_time");

    }

    @Override
    public void onTimeSet(int hourOfDay, int minute)
    {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

        Date date = calendar.getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            cadenaFecha = format1.format(date);
            Log.i("CALENDAR", cadenaFecha);
            fechaRecarga.setText(cadenaFecha);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}
