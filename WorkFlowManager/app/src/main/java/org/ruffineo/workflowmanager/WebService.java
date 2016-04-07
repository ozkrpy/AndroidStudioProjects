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
import java.util.List;

/**
 * Created by ruffineo on 09/02/2016.
 */
public class WebService {

    private static final String TAG = "WEBSERVICE_LOG";

    private static final String NAMESPACE = "http://servicios.ws/";
    private static final String IP = "190.52.175.153";//cuando se use desde una locacion externa
    //private static final String IP = "192.168.0.105";//cuando se use una conexion WIFI local
    private static final String URL = "http://" + IP + ":9999/WebApps/Servicios?WSDL";

    private DatosUsuario datosUsuario;

    public Respuesta consultaUsuarioParametroObjeto(String user, String pass, String method) {
        Respuesta respuesta = new Respuesta(0, "ER", "se inicializo correctamente en la APP");

        datosUsuario = new DatosUsuario(user, pass, "20160404");//TODO
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
