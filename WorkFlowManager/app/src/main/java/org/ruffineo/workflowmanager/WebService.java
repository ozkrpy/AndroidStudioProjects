package org.ruffineo.workflowmanager;

import android.util.Log;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
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

    public Respuesta consultaUsuarioObjeto(String user, String pass, String method) {
        Respuesta respuesta = new Respuesta(0, "ER", "se inicializo correctamente en la APP");

        String METHOD_NAME = method;
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;

        escribeLog("Method name: " + method);
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        escribeLog("Request : " + request.toString());
        request.addProperty("user", user);
        request.addProperty("pass", pass);
        escribeLog("Properties added: " + request.toString());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        escribeLog("Envelope: " + envelope.toString());
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION, envelope);
            escribeLog("Transport: " + androidHttpTransport.toString());
            //SoapPrimitive resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
            //SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
            SoapObject resultsRequestSOAP = (SoapObject) envelope.getResponse();
            if (resultsRequestSOAP != null) {
                Respuesta respuestaObjeto = new Respuesta(resultsRequestSOAP);
                escribeLog("recupero objeto respuesta - Codigo: " + respuestaObjeto.getCodigo() + " mensaje: " + respuestaObjeto.getMensaje() + " referencia: " + respuestaObjeto.getReferencia());
                respuesta = respuestaObjeto;
            }
            //SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
            escribeLog("Recupero: " + resultsRequestSOAP.toString());

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

    public List recuperaLista(String user, String pass, String method) {
        List respuesta = null;

        String METHOD_NAME = method;
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;

        escribeLog("Method name: " + method);
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        escribeLog("Request : " + request.toString());
        request.addProperty("user", user);
        request.addProperty("pass", pass);
        escribeLog("Properties added: " + request.toString());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        escribeLog("Envelope: " + envelope.toString());
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION, envelope);
            escribeLog("Transport: " + androidHttpTransport.toString());
            escribeLog("envelope response: " + envelope.getResponse().toString());
            try {
                List resultsRequestSOAP = (List) envelope.getResponse();
                //SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
                //SoapObject resultsRequestSOAP = (SoapObject) envelope.getResponse();
                if (resultsRequestSOAP != null) {
                    respuesta = (List) resultsRequestSOAP;
                }
                //SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
                escribeLog("Recupero: " + resultsRequestSOAP.toString());
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

        String METHOD_NAME = method;
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;

        escribeLog("Method name: " + method);
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        escribeLog("Request : " + request.toString());
        request.addProperty("user", user);
        request.addProperty("pass", pass);
        request.addProperty("numeroSolicitud", numeroSolicitud);

        escribeLog("Properties added: " + request.toString());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        escribeLog("Envelope: " + envelope.toString());
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION, envelope);
            escribeLog("Transport: " + androidHttpTransport.toString());
            escribeLog("envelope response: " + envelope.getResponse().toString());
            try {
                //List resultsRequestSOAP = (List) envelope.getResponse();
                //SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
                SoapObject resultsRequestSOAP = (SoapObject) envelope.getResponse();
                if (resultsRequestSOAP != null) {
                    Tarea respuestaObjeto = new Tarea(resultsRequestSOAP);
                    respuesta = respuestaObjeto;
                }
                //SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
                escribeLog("Recupero: " + resultsRequestSOAP.toString());
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

    private void escribeLog(String texto) {
        Log.i(TAG, texto);
    }
}
