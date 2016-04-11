package org.ruffineo.workflowmanager;

import android.util.Log;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruffineo on 09/02/2016.
 */

public class WebService {

    private static final String TAG = "WEBSERVICE_LOG";

    private static final String NAMESPACE = "http://servicios.ws/";
    private static final String IP = "190.52.175.153";//cuando se use desde una locacion externa
    //private static final String IP = "192.168.1.3";//cuando se use una conexion WIFI local
    private static final String URL = "http://" + IP + ":9999/WebApps/Servicios?WSDL";

    private DatosUsuario datosUsuario;

    public Respuesta consultaUsuarioParametroObjeto(String user, String pass, String method) {
        Respuesta respuesta = new Respuesta(0, "ER", "se inicializo correctamente en la APP");

        datosUsuario = new DatosUsuario(user, pass, "20160404");//TODO sending a correct date
        escribeLog("objeto usuario recibido, user: " + datosUsuario.getUser().toString() + " pass: " + datosUsuario.getPass().toString() + " fecha modificacion: " + datosUsuario.getFecha().toString());

        String METHOD_NAME = method;
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;

        escribeLog("Method name: " + method);
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        escribeLog("Request : " + request.toString());

        PropertyInfo pi = new PropertyInfo();
        pi.setName("datosUser");
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
            //SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;

        } catch (IOException e) {
            //e.printStackTrace();
            escribeLog("IOException: " + e.getMessage());
            respuesta.setReferencia("catch: " + e.getMessage());
        } catch (XmlPullParserException e) {
            //e.printStackTrace();
            escribeLog("XmlPullParserException: " + e.getMessage());
            respuesta.setReferencia("catch: " + e.getMessage());
        }

        return respuesta;
    }

    public List recuperaListaParametroObjeto(String user, String pass, String method) {
        List respuesta = null;

        datosUsuario = new DatosUsuario(user, pass, "20160404");//TODO

        String METHOD_NAME = method;
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;

        escribeLog("Method name: " + method);
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        escribeLog("Request : " + request.toString());

        PropertyInfo pi = new PropertyInfo();
        pi.setName("datosUser");
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
            try {
                List resultsRequestSOAP = (List) envelope.getResponse();
                //SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
                //SoapObject resultsRequestSOAP = (SoapObject) envelope.getResponse();
                if (resultsRequestSOAP != null) {
                    escribeLog("Transport: " + androidHttpTransport.toString());
                    escribeLog("envelope response: " + envelope.getResponse().toString());
                    respuesta = (List) resultsRequestSOAP;
                    escribeLog("Recupero: " + resultsRequestSOAP.toString());
                }
                //SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
            } catch (Exception e) {
                escribeLog("Error al castear resultado: " + e.getMessage());
            }

        } catch (IOException e) {
            //e.printStackTrace();
            escribeLog("IOException: " + e.getMessage());
        } catch (XmlPullParserException e) {
            //e.printStackTrace();
            escribeLog("XmlPullParserException: " + e.getMessage());
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
        pi.setName("datosUser");
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
                //List resultsRequestSOAP = (List) envelope.getResponse();
                //SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
                SoapObject resultsRequestSOAP = (SoapObject) envelope.getResponse();
                if (resultsRequestSOAP != null) {
                    escribeLog("Transport: " + androidHttpTransport.toString());
                    escribeLog("envelope response: " + envelope.getResponse().toString());
                    Tarea respuestaObjeto = new Tarea(resultsRequestSOAP);
                    respuesta = respuestaObjeto;
                    escribeLog("Recupero: " + resultsRequestSOAP.toString());
                }
                //SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
            } catch (Exception e) {
                escribeLog("Error al castear resultado: " + e.getMessage());
            }

        } catch (IOException e) {
            //e.printStackTrace();
            escribeLog("IOException: " + e.getMessage());
        } catch (XmlPullParserException e) {
            //e.printStackTrace();
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
        pi.setName("datosUser");
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

            try {
                java.util.Vector<SoapObject> rs = (java.util.Vector<SoapObject>) envelope.getResponse();
                if (rs != null) {
                    escribeLog("Vector retorno datos, envelope response: " + rs.toString());

                    for (SoapObject cs : rs)
                    {
                        Item rp = new Item();

                        rp.setDescription(cs.getProperty(0).toString());
                        rp.setTitle(cs.getProperty(1).toString());

                        escribeLog("Titulo = "+rp.getTitle() +" Descripcion = " + rp.getDescription());

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

    private void escribeLog(String texto) {
        Log.i(TAG, texto);
    }
}
