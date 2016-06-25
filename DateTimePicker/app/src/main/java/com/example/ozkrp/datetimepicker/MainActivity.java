package com.example.ozkrp.datetimepicker;

import android.content.ClipData;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements
        DatePickerFragment.DatePickerListener, TimePickerFragment.TimePickerListener
{
    private Calendar calendar;
    FragmentManager fm;
    EditText fecha;
    String fechaFormateada;
    String horaFormateada;
    int contador;
    Spinner spinnerCombustible;
    private DBAdapter dbAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendar = Calendar.getInstance();

        dbAdapter = new DBAdapter(this);
        dbAdapter.openDataBaseConnection();
        //dbAdapter.altaCombustible("Diesel comun", 4270);
        //dbAdapter.altaCombustible("Diesel podium", 5680);

        fecha = (EditText) findViewById(R.id.editText_fecha);
        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm = getSupportFragmentManager();
                DatePickerFragment dateDialog = new DatePickerFragment();
                dateDialog.show(fm, "fragment_date");
            }
        });

        spinnerCombustible = (Spinner) findViewById(R.id.spinner_combustible);
        cargarListados();
    }

    private void cargarListados() {
        ArrayList<ItemSpinner> listaCombustibles = new ArrayList<ItemSpinner>();
        listaCombustibles = dbAdapter.recuperaCombustibles();
        ArrayAdapter<ItemSpinner> combustibles = new ArrayAdapter<ItemSpinner>(this, android.R.layout.simple_spinner_dropdown_item, listaCombustibles);
        if (combustibles != null) {
            spinnerCombustible.setAdapter(combustibles);
            spinnerCombustible.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.i("SPINN","Entro al onItemSelectedListener");
                    ItemSpinner itemSeleccionado = (ItemSpinner) parent.getSelectedItem();
                    Log.i("SPINN","ID: "+itemSeleccionado.getCodigo()
                            +",  Descripcion : "+itemSeleccionado.getDescripcion());
                    Toast.makeText(MainActivity.this,
                            "Country ID: "+itemSeleccionado.getCodigo()
                                    +",  Country Name : "+itemSeleccionado.getDescripcion(), Toast.LENGTH_LONG).show();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    @Override
    public void onDateSet(int year, int month, int day)
    {
        // Set selected year, month and day in calendar object
        calendar.set(year, month, day);

        month += 1;

        fechaFormateada = day + "/" + month + "/" + year;

        // Start Time dialog
        TimePickerFragment timeDialog = new TimePickerFragment();
        timeDialog.show(fm, "fragment_time");

    }

    @Override
    public void onTimeSet(int hourOfDay, int minute)
    {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

        horaFormateada = hourOfDay + ":" + minute;

        //fecha.setText(calendar.getInstance().toString() + "/");
        fecha.setText(fechaFormateada + " " + horaFormateada);
        // Here, your calendar object is ready.

        //Calendar cal = Calendar.getInstance();
        //cal.add(calendar.DATE, 1);
        Date date = calendar.getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String inActiveDate = null;
        try {
            inActiveDate = format1.format(date);
            System.out.println(inActiveDate );
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}