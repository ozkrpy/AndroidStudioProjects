package org.ruffineo.workflowmanager;

import android.util.Log;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

/**
 * Created by ruffineo on 09/02/2016.
 */
public class WebService {

    private static final String TAG = "WorkFlowLOG";

    private static final String NAMESPACE = "http://wspersonascedulaimagen/";
    private static final String IP = "apitest.authorize.net/";
    private static final String URL = "https://" + IP + "soap/v1/Service.asmx?wsdl";
    private static final String SOAP_ACTION = "https://api.authorize.net/soap/v1/AuthenticateTest";
    private static final String METHOD_NAME = "AuthenticateTest";

    /**
    private static final String NAMESPACE = "https://api.authorize.net/soap/v1/";
    private static final String URL ="https://apitest.authorize.net/soap/v1/Service.asmx?wsdl";
    private static final String SOAP_ACTION = "https://api.authorize.net/soap/v1/AuthenticateTest";
    private static final String METHOD_NAME = "AuthenticateTest";
    */

    public String validarUsuarioCadena(String user, String pass, String method) {
        if (user.equals("oscar") && (pass.equals("oscar"))) {
            return "OK";
        }
        return "ER";
    }

    public String[] solicitudesPendientes (String user, String method) {
        String[] solicitudes = new String[] {
                "2386",
                "2340",
                "2305"
        };
        return solicitudes;
    }

    public String consultaUsuario (String user, String pass, String method) {
        String respuesta = "NO";
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("name","44vmMAYrhjfhj66fhJN");
        request.addProperty("transactionKey","9MDQ7fghjghjh53H48k7e7n");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION, envelope);
            //SoapPrimitive  resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
            //SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;

            SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
            escribeLog("Recupero: " + resultsRequestSOAP.toString());

        } catch (IOException e) {
            //e.printStackTrace();
            escribeLog(e.getMessage());
        } catch (XmlPullParserException e) {
            //e.printStackTrace();
            escribeLog(e.getMessage());
        }


        return respuesta;
    }

    private void escribeLog(String texto) {
        Log.i(TAG, texto);
    }
}
