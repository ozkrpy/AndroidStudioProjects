package org.ruffineo.workflowmanager;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class WebService {

    private static final String TAG = "WEBSERVICE_LOG";

    private static final String NAMESPACE = "http://operations.ws/";
    //private static final String IP = "190.52.175.153";//cuando se use desde una locacion externa
    private static final String IP = "10.133.21.182";//cuando se use una conexion WIFI local
    private static final String URL = "http://" + IP + ":9999/Mobile/Services?WSDL";

    private DatosUsuario datosUsuario;

    public Respuesta consultaUsuarioParametroObjeto(String user, String pass, String method) {
        escribeLog("URL: " + URL);

        Respuesta respuesta = new Respuesta(0, "ER", "se inicializo correctamente en la APP");

        datosUsuario = new DatosUsuario(user, pass, "20160404");//TODO sending a correct date
        escribeLog("objeto usuario recibido, user: " + datosUsuario.getUser().toString() + " pass: " + datosUsuario.getPass().toString() + " fecha modificacion: " + datosUsuario.getFecha().toString());

        String METHOD_NAME = method;
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;

        escribeLog("Method name: " + method);
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        escribeLog("Request : " + request.toString());

        PropertyInfo pi = new PropertyInfo();
        pi.setName("datosUsuario");
        pi.setValue(datosUsuario);
        pi.setType(datosUsuario.getClass());
        request.addProperty(pi);

        escribeLog("Properties added: " + request.toString());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        escribeLog("Envelope: " + envelope.toString());
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapObject resultsRequestSOAP = (SoapObject) envelope.getResponse();
            if (resultsRequestSOAP != null) {
                escribeLog("Transport: " + androidHttpTransport.toString());
                escribeLog("envelope response: " + envelope.getResponse().toString());
                Respuesta respuestaObjeto = new Respuesta(resultsRequestSOAP);
                escribeLog("recupero objeto respuesta - Codigo: " + respuestaObjeto.getCodigo() + " mensaje: " + respuestaObjeto.getMensaje() + " referencia: " + respuestaObjeto.getReferencia());
                respuesta = respuestaObjeto;
                escribeLog("Recupero: " + resultsRequestSOAP.toString());
            }
        } catch (IOException e) {
            escribeLog("IOException: " + e.getMessage());
            respuesta.setReferencia("No se pudo conectar al servidor.");
        } catch (XmlPullParserException e) {
            escribeLog("XmlPullParserException: " + e.getMessage());
            respuesta.setReferencia("catch: " + e.getMessage());
        }

        return respuesta;
    }

    public Tarea recuperaTarea(String user, String pass, String numeroSolicitud, String method) {
        Tarea respuesta = null;

        datosUsuario = new DatosUsuario(user, pass, "20160404");//TODO

        String METHOD_NAME = method;
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;

        escribeLog("Method name: " + method);
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        escribeLog("Request : " + request.toString());

        PropertyInfo pi = new PropertyInfo();
        pi.setName("datosUsuario");
        pi.setValue(datosUsuario);
        pi.setType(datosUsuario.getClass());
        request.addProperty(pi);

        request.addProperty("numeroSolicitud", numeroSolicitud);

        escribeLog("Properties added: " + request.toString());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        escribeLog("Envelope: " + envelope.toString());
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION, envelope);
            try {
                SoapObject resultsRequestSOAP = (SoapObject) envelope.getResponse();
                if (resultsRequestSOAP != null) {
                    escribeLog("Transport: " + androidHttpTransport.toString());
                    escribeLog("envelope response: " + envelope.getResponse().toString());
                    Tarea respuestaObjeto = new Tarea(resultsRequestSOAP);
                    respuesta = respuestaObjeto;
                    escribeLog("Recupero: " + resultsRequestSOAP.toString());
                }
            } catch (Exception e) {
                escribeLog("Error al castear resultado: " + e.getMessage());
            }
        } catch (IOException e) {
            escribeLog("IOException: " + e.getMessage());
        } catch (XmlPullParserException e) {
            escribeLog("XmlPullParserException: " + e.getMessage());
        }

        return respuesta;
    }

    public ArrayList<Item> recuperaLista(String user, String pass, String method) {
        ArrayList<Item> returnlist = new ArrayList<Item>();

        datosUsuario = new DatosUsuario(user, pass, "20160404");//TODO sending a correct date

        String METHOD_NAME = method;
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;

        escribeLog("Method name: " + method);
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        escribeLog("Request : " + request.toString());

        PropertyInfo pi = new PropertyInfo();
        pi.setName("datosUsuario");
        pi.setValue(datosUsuario);
        pi.setType(datosUsuario.getClass());
        request.addProperty(pi);

        escribeLog("Properties added: " + request.toString());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        escribeLog("Envelope: " + envelope.toString());
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION, envelope);
            escribeLog("Transport executed call");
            escribeLog("Class envelope: " + envelope.getClass().toString());
        if (envelope.getResponse() == null) {
            escribeLog("getResponse fue nulo");
            return null;
        } else {
            if (envelope.getResponse().getClass().equals(java.util.Vector.class)) {
                try {
                    escribeLog("CLASE DE RESPUESTA:" + envelope.getResponse().getClass().toString());
                    java.util.Vector<SoapObject> rs = (java.util.Vector<SoapObject>) envelope.getResponse();
                    if (rs != null) {
                        escribeLog("Vector retorno datos, envelope response: " + rs.toString());
                        for (SoapObject cs : rs) {
                            Item rp = new Item();
                            rp.setDescription(cs.getProperty(0).toString());
                            rp.setTitle(cs.getProperty(1).toString());
                            escribeLog("Titulo = " + rp.getTitle() + " Descripcion = " + rp.getDescription());
                            returnlist.add(rp);
                        }
                        escribeLog("Recupero: " + rs.toString());
                    } else {
                        escribeLog("Vector de respuesta retorno vacio.");
                        return null;
                    }
                } catch (Exception e) {
                    escribeLog("Error al castear resultado: " + e.getMessage());
                    return null;
                }
            } else if (envelope.getResponse().getClass().equals(org.ksoap2.serialization.SoapObject.class)) {
                try {
                    escribeLog("CLASE DE RESPUESTA:" + envelope.getResponse().getClass().toString());
                    SoapObject rs = (SoapObject) envelope.getResponse();
                    if (rs != null) {
                        escribeLog("Objeto Recuperado, envelope response: " + rs.toString());
                        Item rp = new Item();
                        rp.setDescription(rs.getProperty(0).toString());
                        rp.setTitle(rs.getProperty(1).toString());
                        escribeLog("Titulo = " + rp.getTitle() + " Descripcion = " + rp.getDescription());
                        returnlist.add(rp);
                        escribeLog("Recupero: " + rs.toString());
                    } else {
                        escribeLog("Vector de respuesta retorno vacio.");
                        return null;
                    }
                } catch (Exception e) {
                    escribeLog("Error al castear resultado: " + e.getMessage());
                    return null;
                }
            }
        }
        } catch (IOException e) {
            escribeLog("IOException: " + e.getMessage());
            return null;
        } catch (XmlPullParserException e) {
            escribeLog("XmlPullParserException: " + e.getMessage());
            return null;
        }
        return returnlist;
    }

    public ArrayList<Item> retornaItemsVacio () {
        ArrayList<Item> items = new ArrayList<Item>();
        items.add(new Item("Sin Solicitudes","No se recuperaron datos"));
        return items;
    }

    public Respuesta actualizaEstadoSolicitud(String user, String pass, String numeroSolicitud, String nuevoEstado, String method) {
        Respuesta respuesta = null;

        datosUsuario = new DatosUsuario(user, pass, "20160404");//TODO

        String METHOD_NAME = method;
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;

        escribeLog("Method name: " + method);
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        escribeLog("Request : " + request.toString());

        PropertyInfo pi = new PropertyInfo();
        pi.setName("datosUsuario");
        pi.setValue(datosUsuario);
        pi.setType(datosUsuario.getClass());
        request.addProperty(pi);

        request.addProperty("numeroSolicitud", numeroSolicitud);
        request.addProperty("estadoAprobacion", nuevoEstado);

        escribeLog("Properties added: " + request.toString());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        escribeLog("Envelope: " + envelope.toString());
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION, envelope);
            try {
                SoapObject resultsRequestSOAP = (SoapObject) envelope.getResponse();
                if (resultsRequestSOAP != null) {
                    escribeLog("Transport: " + androidHttpTransport.toString());
                    escribeLog("envelope response: " + envelope.getResponse().toString());
                    Respuesta respuestaObjeto = new Respuesta(resultsRequestSOAP);
                    respuesta = respuestaObjeto;
                    escribeLog("Recupero: " + resultsRequestSOAP.toString());
                }
            } catch (Exception e) {
                escribeLog("Error al castear resultado: " + e.getMessage());
            }

        } catch (IOException e) {
            escribeLog("IOException: " + e.getMessage());
            respuesta.setReferencia("No se pudo conectar al servidor.");
        } catch (XmlPullParserException e) {
            escribeLog("XmlPullParserException: " + e.getMessage());
        }

        return respuesta;
    }

    private void escribeLog(String texto) {
        Log.i(TAG, texto);
    }
}
