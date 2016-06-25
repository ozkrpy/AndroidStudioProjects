package com.example.ozkrp.fuelwatcher;

/**
 * Created by ozkrp on 17/6/2016.
 */
public class Cargas {
    String fecha;
    int odometro;
    int monto;
    double litros;
    int codigoCombustible;
    int codigoVehiculo;
    int rangoDias;
    int kmRecorridos;
    double kmLitro;



    public Cargas() {
    }

    public Cargas(String fecha, int odometro, int monto, double litros, int codigoCombustible, int codigoVehiculo, int rangoDias, int kmRecorridos, double kmLitro) {
        this.fecha = fecha;
        this.odometro = odometro;
        this.monto = monto;
        this.litros = litros;
        this.codigoCombustible = codigoCombustible;
        this.codigoVehiculo = codigoVehiculo;
        this.rangoDias = rangoDias;
        this.kmRecorridos = kmRecorridos;
        this.kmLitro = kmLitro;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getOdometro() {
        return odometro;
    }

    public void setOdometro(int odometro) {
        this.odometro = odometro;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public double getLitros() {
        return litros;
    }

    public void setLitros(double litros) {
        this.litros = litros;
    }

    public int getCodigoCombustible() {
        return codigoCombustible;
    }

    public void setCodigoCombustible(int codigoCombustible) {
        this.codigoCombustible = codigoCombustible;
    }

    public int getCodigoVehiculo() {
        return codigoVehiculo;
    }

    public void setCodigoVehiculo(int codigoVehiculo) {
        this.codigoVehiculo = codigoVehiculo;
    }

    public int getRangoDias() {
        return rangoDias;
    }

    public void setRangoDias(int rangoDias) {
        this.rangoDias = rangoDias;
    }

    public int getKmRecorridos() {
        return kmRecorridos;
    }

    public void setKmRecorridos(int kmRecorridos) {
        this.kmRecorridos = kmRecorridos;
    }

    public double getKmLitro() {
        return kmLitro;
    }

    public void setKmLitro(double kmLitro) {
        this.kmLitro = kmLitro;
    }

    @Override
    public String toString() {
        return "Date: " + this.fecha + ", Monto: " + this.monto + ", Litros: " + this.litros;
    }
}
