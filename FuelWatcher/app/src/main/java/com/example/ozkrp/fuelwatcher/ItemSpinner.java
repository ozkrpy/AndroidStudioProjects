package com.example.ozkrp.fuelwatcher;

public class ItemSpinner {
    int codigo;
    String descripcion;

    public ItemSpinner() {
    }

    public ItemSpinner(int codigo, String descripcion) {

        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String toString() {
        return descripcion;
    }
}
