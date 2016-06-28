package com.example.ozkrp.tabledraw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TableLayout tl;
    TableRow tr;
    TextView tvFuel;
    TextView tvDate;
    TextView tvOdometer;
    TextView tvAmmount;
    TextView tvLiters;
    TextView tvDays;
    TextView tvKmRunned;
    TextView tvKmPerLiter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tl = (TableLayout) findViewById(R.id.tabla_cuerpo);

        agregarNuevaFila();
        agregarNuevaFila();

    }

    private void agregarNuevaFila() {
        tvFuel = new TextView(this);
        tvDate = new TextView(this);
        tvOdometer = new TextView(this);
        tvAmmount = new TextView(this);
        tvLiters = new TextView(this);
        tvDays = new TextView(this);
        tvKmRunned = new TextView(this);
        tvKmPerLiter = new TextView(this);

        tvFuel.setBackgroundResource(R.drawable.cell_shape);
        tvDate.setBackgroundResource(R.drawable.cell_shape);
        tvOdometer.setBackgroundResource(R.drawable.cell_shape);
        tvAmmount.setBackgroundResource(R.drawable.cell_shape);
        tvLiters.setBackgroundResource(R.drawable.cell_shape);
        tvDays.setBackgroundResource(R.drawable.cell_shape);
        tvKmRunned.setBackgroundResource(R.drawable.cell_shape);
        tvKmPerLiter.setBackgroundResource(R.drawable.cell_shape);

        tr = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);;
        tr.setLayoutParams(lp);

        tvFuel.setText("1");
        tvDate.setText("1");
        tvOdometer.setText("1");
        tvAmmount.setText("1");
        tvLiters.setText("1");
        tvDays.setText("1");
        tvKmRunned.setText("1");
        tvKmPerLiter.setText("1");

        tr.addView(tvFuel);
        tr.addView(tvDate);
        tr.addView(tvOdometer);
        tr.addView(tvAmmount);
        tr.addView(tvLiters);
        tr.addView(tvDays);
        tr.addView(tvKmRunned);
        tr.addView(tvKmPerLiter);

        tl.addView(tr, tl.getChildCount());
    }
}
