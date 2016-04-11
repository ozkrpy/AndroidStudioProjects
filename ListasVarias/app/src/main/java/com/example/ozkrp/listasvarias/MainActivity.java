package com.example.ozkrp.listasvarias;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listaDeSolicitudes;
    ArrayList<Item> listaRecuperadaWS;
    ListView listView;
    WebService ws;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. pass context and data to the custom adapter
        //MyAdapter adapter = new MyAdapter(this, generateData());

        // 2. Get ListView from activity_main.xml
        listView = (ListView) findViewById(R.id.listView_listar);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "position: " + position, Toast.LENGTH_SHORT).show();
                Item solicitudNumero = (Item) listView.getItemAtPosition(position);
                Toast.makeText(MainActivity.this, "solicitud: " + solicitudNumero.getTitle().toString().trim(), Toast.LENGTH_SHORT).show();


            }
        });

        try {
            ListarSolicitudes listarSolicitudes = new ListarSolicitudes();
            listarSolicitudes.execute();
        } catch (Exception e) {
            escribeLog(e.getMessage());
        }

    }

    private class ListarSolicitudes extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            String method = "recuperaListaParametroRetorno";
            ws = new WebService();
            listaRecuperadaWS = ws.recuperaListaParametroObjeto("oscar","oscar",method);
            //escribeLog("recupero de la clase WS lista: " + listaRecuperadaWS.toString());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (listaRecuperadaWS != null) {
                adapter = new MyAdapter(MainActivity.this, listaRecuperadaWS);
                listView.setAdapter(adapter);
            } else {
                listaRecuperadaWS = ws.retornaItemsVacio();
                adapter = new MyAdapter(MainActivity.this, listaRecuperadaWS);
                listView.setAdapter(adapter);
                escribeLog("No se recuperaron Tareas pendientes");
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    private void escribeLog(String texto) {
        Log.i("Main_LOG", texto);
    }


}
