package com.example.ozkrp.fuelwatcher;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBAdapter {

    Context context;
    private DBHelper dBHelper;
    private SQLiteDatabase db;

    private String DATABASE_NAME = "fuel_watcher";
    private int DATABASE_VERSION = 7;

    public DBAdapter(Context context) {
        this.context = context;
        dBHelper = new DBHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void openDataBaseConnection() {
        db = dBHelper.getWritableDatabase();
    }

    public class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String vehic = "CREATE TABLE vehiculo (id integer primary key autoincrement, " +
                    "marca text, " +
                    "anno integer, " +
                    "modelo text, " +
                    "odometro integer not null);";
            String combus = "CREATE TABLE combustible (codigo integer primary key autoincrement, " +
                    "tipo text, " +
                    "precio integer);";
            String recarga = "CREATE TABLE cargas(fecha datetime," +
                    "odometro_ultimo integer," +
                    "monto_carga integer," +
                    "litros_cargados integer," +
                    "codigo_combustible integer," +
                    "codigo_vehiculo integer," +
                    "FOREIGN KEY(codigo_combustible) REFERENCES combustible(codigo)," +
                    "FOREIGN KEY(codigo_vehiculo) REFERENCES combustible(codigo));";
            db.execSQL(vehic);
            db.execSQL(combus);
            db.execSQL(recarga);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            String query = "DROP TABLE IF EXISTS vehiculo;";
            db.execSQL(query);
            query = "DROP TABLE IF EXISTS combustible;";
            db.execSQL(query);
            query = "DROP TABLE IF EXISTS cargas;";
            db.execSQL(query);
            onCreate(db);
        }
    }

    public void altaVehiculo(String marca, int anno, String modelo, int odometro) {
        Log.i("DB", "Vehiculo: " + marca + " - " + modelo + " - " + anno + " - " + odometro);

        ContentValues cv = new ContentValues();
        cv.put("marca", marca);
        cv.put("anno", anno);
        cv.put("modelo", modelo);
        cv.put("odometro", odometro);
        db.insert("vehiculo", null, cv);
    }

    public void altaCombustible(String tipoCombustible, int precioCombustible) {
        Log.i("DB", "Combustible: " + tipoCombustible + " - " + precioCombustible);

        ContentValues cv = new ContentValues();
        cv.put("tipo", tipoCombustible);
        cv.put("precio", precioCombustible);
        db.insert("combustible", null, cv);
    }

    public ArrayList<String> recuperaVehiculos() {
        ArrayList<String> todosVehiculos = new ArrayList<String>();
        Cursor cursor = db.query("vehiculo", null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                todosVehiculos.add(cursor.getString(0) + "-" + cursor.getString(1) + "-" + cursor.getString(3) + "-" + cursor.getString(2));
            } while (cursor.moveToNext());
        }
        return todosVehiculos;
    }

    public ArrayList<String> recuperaCombustibles() {
        ArrayList<String> todosCombustibles = new ArrayList<String>();
        Cursor cursor = db.query("combustible", null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                todosCombustibles.add(cursor.getString(0) + "-" + cursor.getString(1) + "-" + cursor.getString(2));
            } while (cursor.moveToNext());
        }
        return todosCombustibles;
    }
}