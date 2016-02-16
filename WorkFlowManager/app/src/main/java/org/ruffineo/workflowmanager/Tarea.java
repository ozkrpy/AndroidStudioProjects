package org.ruffineo.workflowmanager;

/**
 * Created by ruffineo on 15/02/2016.
 * Objeto que corresponde al detalle de las tareas
 * que seran invocadas de acuerdo a la solicitud
 * Se agrego el constructor con el numero de Solicitud.
 */

public class Tarea {

    private String solicitudNumero;
    private String personaCodigo;
    private String personaNombre;
    private String solicitudFechaInicio;
    private String solicitudReferencia;
    private String solicitudTipoCodigo;
    private String solicitudTipoDescripcion;
    private String tareaNumero;
    private String tareaTipoCodigo;
    private String tareaTipoDescripcion;
    private String tareaEstado;
    private String tareaFechaAsignacion;
    private String tareaAsignadorCodigo;
    private String tareaAsignadorNombre;
    private String tareaDescripcion;
    private String tareaComentarioRecibido;
    private String tareaComentarioAdicional;

    public Tarea() {
    }

    public Tarea(String solicitudNumero) {
        this.solicitudNumero = solicitudNumero;
    }

    public String getSolicitudNumero() {
        return solicitudNumero;
    }

    public void setSolicitudNumero(String solicitudNumero) {
        this.solicitudNumero = solicitudNumero;
    }

    public String getPersonaCodigo() {
        return personaCodigo;
    }

    public void setPersonaCodigo(String personaCodigo) {
        this.personaCodigo = personaCodigo;
    }

    public String getPersonaNombre() {
        return personaNombre;
    }

    public void setPersonaNombre(String personaNombre) {
        this.personaNombre = personaNombre;
    }

    public String getSolicitudFechaInicio() {
        return solicitudFechaInicio;
    }

    public void setSolicitudFechaInicio(String solicitudFechaInicio) {
        this.solicitudFechaInicio = solicitudFechaInicio;
    }

    public String getSolicitudReferencia() {
        return solicitudReferencia;
    }

    public void setSolicitudReferencia(String solicitudReferencia) {
        this.solicitudReferencia = solicitudReferencia;
    }

    public String getSolicitudTipoCodigo() {
        return solicitudTipoCodigo;
    }

    public void setSolicitudTipoCodigo(String solicitudTipoCodigo) {
        this.solicitudTipoCodigo = solicitudTipoCodigo;
    }

    public String getSolicitudTipoDescripcion() {
        return solicitudTipoDescripcion;
    }

    public void setSolicitudTipoDescripcion(String solicitudTipoDescripcion) {
        this.solicitudTipoDescripcion = solicitudTipoDescripcion;
    }

    public String getTareaNumero() {
        return tareaNumero;
    }

    public void setTareaNumero(String tareaNumero) {
        this.tareaNumero = tareaNumero;
    }

    public String getTareaTipoCodigo() {
        return tareaTipoCodigo;
    }

    public void setTareaTipoCodigo(String tareaTipoCodigo) {
        this.tareaTipoCodigo = tareaTipoCodigo;
    }

    public String getTareaTipoDescripcion() {
        return tareaTipoDescripcion;
    }

    public void setTareaTipoDescripcion(String tareaTipoDescripcion) {
        this.tareaTipoDescripcion = tareaTipoDescripcion;
    }

    public String getTareaEstado() {
        return tareaEstado;
    }

    public void setTareaEstado(String tareaEstado) {
        this.tareaEstado = tareaEstado;
    }

    public String getTareaFechaAsignacion() {
        return tareaFechaAsignacion;
    }

    public void setTareaFechaAsignacion(String tareaFechaAsignacion) {
        this.tareaFechaAsignacion = tareaFechaAsignacion;
    }

    public String getTareaAsignadorCodigo() {
        return tareaAsignadorCodigo;
    }

    public void setTareaAsignadorCodigo(String tareaAsignadorCodigo) {
        this.tareaAsignadorCodigo = tareaAsignadorCodigo;
    }

    public String getTareaAsignadorNombre() {
        return tareaAsignadorNombre;
    }

    public void setTareaAsignadorNombre(String tareaAsignadorNombre) {
        this.tareaAsignadorNombre = tareaAsignadorNombre;
    }

    public String getTareaDescripcion() {
        return tareaDescripcion;
    }

    public void setTareaDescripcion(String tareaDescripcion) {
        this.tareaDescripcion = tareaDescripcion;
    }

    public String getTareaComentarioRecibido() {
        return tareaComentarioRecibido;
    }

    public void setTareaComentarioRecibido(String tareaComentarioRecibido) {
        this.tareaComentarioRecibido = tareaComentarioRecibido;
    }

    public String getTareaComentarioAdicional() {
        return tareaComentarioAdicional;
    }

    public void setTareaComentarioAdicional(String tareaComentarioAdicional) {
        this.tareaComentarioAdicional = tareaComentarioAdicional;
    }
}
