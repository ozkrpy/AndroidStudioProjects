package com.example.ozkrp.webservice;

import org.ksoap2.serialization.SoapObject;

/**
 * Created by ozkrp on 20/2/2016.
 */
public class Respuesta {
    int codigo;
    String mensaje;
    String referencia;

    public Respuesta(SoapObject object) {
        new Deserialization().SoapDeserilize(this, object);
    }

    public Respuesta(int codigo, String mensaje, String referencia) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.referencia = referencia;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
}
