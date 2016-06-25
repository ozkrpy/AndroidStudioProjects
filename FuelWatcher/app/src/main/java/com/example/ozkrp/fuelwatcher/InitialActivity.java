package com.example.ozkrp.fuelwatcher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InitialActivity extends AppCompatActivity implements View.OnClickListener{

    /*Vistas en la actividad principal*/
    Button botonNuevaRecarga;
    Button botonConsultaRecargas;
    Button botonParametrosVehiculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        botonNuevaRecarga = (Button) findViewById(R.id.botonAgregarInitial);
        botonNuevaRecarga.setOnClickListener(this);
        botonConsultaRecargas = (Button) findViewById(R.id.botonConsultarInitial);
        botonConsultaRecargas.setOnClickListener(this);
        botonParametrosVehiculo = (Button) findViewById(R.id.botonVehiculoInitial);
        botonParametrosVehiculo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.botonAgregarInitial:
                Intent i = new Intent(InitialActivity.this, RecargaActivity.class);
                startActivity(i);
                break;
            case R.id.botonConsultarInitial:
                Intent j = new Intent(InitialActivity.this, ReporteActivity.class);
                startActivity(j);
                break;
            case R.id.botonVehiculoInitial:
                Intent k = new Intent(InitialActivity.this, ParametrosActivity.class);
                startActivity(k);
                break;
        }
    }
}
