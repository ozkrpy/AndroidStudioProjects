package com.example.ozkrp.datetimepicker;

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

    private String DATABASE_NAME = "date_picker";
    private int DATABASE_VERSION = 1;



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
            String combus = "CREATE TABLE combustible (codigo integer primary key autoincrement, " +
                    "tipo text, " +
                    "precio integer);";
            db.execSQL(combus);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            String query = "DROP TABLE IF EXISTS vehiculo;";
            db.execSQL(query);
            onCreate(db);
        }
    }


    public void altaCombustible(String tipoCombustible, int precioCombustible) {
        Log.i("DB", "Combustible: " + tipoCombustible + " - " + precioCombustible);

        ContentValues cv = new ContentValues();
        cv.put("tipo", tipoCombustible);
        cv.put("precio", precioCombustible);
        db.insert("combustible", null, cv);
    }

    public ArrayList<ItemSpinner> recuperaCombustibles() {
        int codigo;
        String descripcion;
        ArrayList<ItemSpinner> todosCombustibles = new ArrayList<ItemSpinner>();
        Cursor cursor = db.query("combustible", null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                codigo = Integer.parseInt(cursor.getString(0));
                descripcion = cursor.getString(1) + " - " + cursor.getString(2);
                todosCombustibles.add(new ItemSpinner(codigo, descripcion));
            } while (cursor.moveToNext());
        }
        return todosCombustibles;
    }
}