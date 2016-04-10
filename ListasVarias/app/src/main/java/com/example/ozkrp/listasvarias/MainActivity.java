package com.example.ozkrp.listasvarias;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listaDeSolicitudes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. pass context and data to the custom adapter
        MyAdapter adapter = new MyAdapter(this, generateData());

        // 2. Get ListView from activity_main.xml
        final ListView listView = (ListView) findViewById(R.id.listView_listar);

        // 3. setListAdapter
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "position: " + position, Toast.LENGTH_SHORT).show();
                Item solicitudNumero = (Item) listView.getItemAtPosition(position);
                Toast.makeText(MainActivity.this, "solicitud: " + solicitudNumero.getTitle().toString().trim(), Toast.LENGTH_SHORT).show();


            }
        });
    }

    private ArrayList<Item> generateData(){
        ArrayList<Item> items = new ArrayList<Item>();
        items.add(new Item("Item 1","First Item on the list"));
        items.add(new Item("Item 2", "Second Item on the list"));
        items.add(new Item("Item 3", "Third Item on the list"));
        return items;
    }


}
