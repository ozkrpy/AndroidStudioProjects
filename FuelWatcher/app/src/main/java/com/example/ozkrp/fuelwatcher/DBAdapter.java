package com.example.ozkrp.fuelwatcher;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DBAdapter {

    private static final String TAG = "DATABASE";
    Context context;
    private DBHelper dBHelper;
    private SQLiteDatabase db;

    private String DATABASE_NAME = "fuel_watcher";
    private int DATABASE_VERSION = 15;

    public DBAdapter(Context context) {
        this.context = context;
        dBHelper = new DBHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void openDataBaseConnection() {
        Log.i(TAG, "DB open");
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
                    "odometro integer," +
                    "monto_carga integer," +
                    "litros_cargados real," +
                    "rango_dias integer," +
                    "kilometros_recorridos integer," +
                    "kilometros_litro real," +
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

    public ArrayList<ItemSpinner> recuperaVehiculos() {
        int codigo;
        String descripcion;
        ArrayList<ItemSpinner> todosVehiculos = new ArrayList<ItemSpinner>();
        Cursor cursor = db.query("vehiculo", null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                codigo = Integer.parseInt(cursor.getString(0));
                descripcion = cursor.getString(1) + "-" + cursor.getString(3) + "-" + cursor.getString(2);
                todosVehiculos.add(new ItemSpinner(codigo, descripcion));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return todosVehiculos;
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
            cursor.close();
        }
        return todosCombustibles;
    }

    public int recuperaPrecioCombustible (int codigoCombustible) {
        int precioRecuperado = 0;
        String[] columna = {"precio"};
        Cursor queryDatabase = db.query("combustible", columna, "codigo = " + codigoCombustible, null, null, null, null);
        if (queryDatabase != null && queryDatabase.moveToFirst()) {
            precioRecuperado = Integer.parseInt(queryDatabase.getString(0));
            Log.i("PRECIO", "recupero: " + precioRecuperado);
        }
        queryDatabase.close();
        return precioRecuperado;
    }

    public void altaRecarga (String fecha, int odometro, int monto, double litros, int codigoCombustible, int codigoVehiculo) {
        if (esPrimeraCarga(codigoVehiculo, codigoCombustible) > 0) {
            actualizaCargaAnterior(codigoVehiculo, codigoCombustible, odometro, fecha, litros);
        }
        ContentValues cv = new ContentValues();
        cv.put("fecha", fecha);
        cv.put("odometro", odometro);
        cv.put("monto_carga", monto);
        cv.put("litros_cargados", litros);
        cv.put("codigo_combustible", codigoCombustible);
        cv.put("codigo_vehiculo", codigoVehiculo);

        try {
            db.insert("cargas", null, cv);
            actualizaOdometroVehiculo(codigoVehiculo, odometro);
        } catch (Exception e) {
            Log.i("ERROR", "Mensaje: " + e.getMessage());
        }
    }

    private void actualizaCargaAnterior(int codigoVehiculo, int codigoCombustible, int odometroNuevo, String fecha, double litros) {
        int odometroAntes = recuperaOdometroViejo(codigoVehiculo);
        int kilometrosRecorridos = odometroNuevo - odometroAntes;

        String ultimaFecha = recuperaUltimaFecha(codigoCombustible, codigoVehiculo);
        long dias = calculaRangoDias(fecha, ultimaFecha);

        double kilometrosLitro = kilometrosRecorridos / litros;
        Log.i(TAG, "Km Recorridos: " + kilometrosRecorridos + " rango dias: " + dias + " Kilometros x Litro: " + kilometrosLitro);

        //db.update(tabla, contentValues, where, whereArguments);
        ContentValues cv = new ContentValues();
        cv.put("rango_dias", dias);
        cv.put("kilometros_recorridos", kilometrosRecorridos);
        cv.put("kilometros_litro", kilometrosLitro);

        String where = "codigo_vehiculo = " + codigoVehiculo + " AND codigo_combustible = " + codigoCombustible + " AND fecha = '" + ultimaFecha + "'";

        int contador = db.update("cargas", cv, where, null);
        Log.i(TAG, "Se actualizaron " + contador + " filas en la tabla cargas");

    }

    private void actualizaOdometroVehiculo(int codigoVehiculo, int odometro) {

        ContentValues cv = new ContentValues();
        cv.put("odometro", odometro);

        String where = "id = " + codigoVehiculo;

        int contador = db.update("vehiculo", cv, where, null);
        Log.i(TAG, "Se actualizaron " + contador + " filas en la tabla vehiculos");

    }

    private long calculaRangoDias(String nuevaFecha, String ultimaFecha) {
        long rangoDias = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date fechaUltima = simpleDateFormat.parse(ultimaFecha);
            Date fechaNueva = simpleDateFormat.parse(nuevaFecha);
            Log.i(TAG, "ultima: " + fechaUltima.getTime() + " nueva: " + fechaNueva.getTime());
            rangoDias = (fechaNueva.getTime() - fechaUltima.getTime()) / (24 * 60 * 60 * 1000);
            Log.i(TAG, "recupero rango de dias: " + rangoDias);
        } catch (ParseException e) {
            Log.i(TAG, "ERROR: " + e.getMessage());
        }
        return rangoDias;
    }

    private String recuperaUltimaFecha(int codigoCombustible, int codigoVehiculo) {
        String fechaUltimaCarga = null;
        String[] columna = {"MAX(fecha)"};
        String where = "codigo_vehiculo = " + codigoVehiculo + " AND codigo_combustible = " + codigoCombustible;
        long dias = 0;
        Cursor queryDatabase = db.query("cargas", columna, where, null, null, null, null);
        if (queryDatabase != null && queryDatabase.moveToFirst()) {
            fechaUltimaCarga = String.valueOf(queryDatabase.getString(0));
            Log.i(TAG, "recupero fecha de la ultima carga: " + fechaUltimaCarga);
        }
        queryDatabase.close();
        Log.i(TAG, "ULTIMA FECHA Seteo valor: " + fechaUltimaCarga);

        return fechaUltimaCarga;
    }

    public ArrayList<Cargas> recuperaCargas(int codigoVehiculo) {
        Log.i("DB", "codigo vehiculo: " + codigoVehiculo);
        ArrayList<Cargas> todosCombustibles = new ArrayList<Cargas>();
        Cursor cursor = db.query("cargas", null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Cargas carga = new Cargas();
                carga.setFecha(cursor.getString(0));
                carga.setOdometro(Integer.parseInt(cursor.getString(1)));
                carga.setMonto(Integer.parseInt(cursor.getString(2)));
                carga.setLitros(Double.parseDouble(cursor.getString(3)));
                if (cursor.getString(4) != null) {
                    carga.setRangoDias(Integer.parseInt(cursor.getString(4)));
                    carga.setKmRecorridos(Integer.parseInt(cursor.getString(5)));
                    carga.setKmLitro(Double.parseDouble(cursor.getString(6)));
                }
                carga.setCodigoCombustible(Integer.parseInt(cursor.getString(7)));
                carga.setCodigoVehiculo(Integer.parseInt(cursor.getString(8)));
                todosCombustibles.add(carga);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return todosCombustibles;
    }

    public int recuperaOdometroViejo (int codigoVehiculo) {
        int odometroRecuperado = 0;
        String[] columna = {"odometro"};
        Cursor queryDatabase = db.query("vehiculo", columna, "id = " + codigoVehiculo, null, null, null, null);
        if (queryDatabase != null && queryDatabase.moveToFirst()) {
            odometroRecuperado = Integer.parseInt(queryDatabase.getString(0));
            Log.i("PRECIO", "recupero: " + odometroRecuperado);
        }
        queryDatabase.close();
        return odometroRecuperado;
    }

    public int esPrimeraCarga (int codigoVehiculo, int codigoCombustible) {
        int cantidadFilas = 0;
        String[] columna = {"count(0)"};
        String where = "codigo_vehiculo = " + codigoVehiculo + " AND codigo_combustible = " + codigoCombustible;
        Cursor queryDatabase = db.query("cargas", columna, where, null, null, null, null);
        if (queryDatabase != null && queryDatabase.moveToFirst()) {
            cantidadFilas = Integer.parseInt(queryDatabase.getString(0));
            Log.i("PRECIO", "recupero cantidad de filas: " + cantidadFilas);
        }
        queryDatabase.close();
        return cantidadFilas;
    }

    public void closeDataBaseConnection() {
        Log.i(TAG, "DB close");
        dBHelper.getWritableDatabase().close();
    }

}