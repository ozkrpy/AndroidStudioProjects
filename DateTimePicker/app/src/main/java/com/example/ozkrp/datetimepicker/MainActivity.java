package com.example.ozkrp.datetimepicker;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendar = Calendar.getInstance();

        fecha = (EditText) findViewById(R.id.editText_fecha);
        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm = getSupportFragmentManager();
                DatePickerFragment dateDialog = new DatePickerFragment();
                dateDialog.show(fm, "fragment_date");
            }
        });


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