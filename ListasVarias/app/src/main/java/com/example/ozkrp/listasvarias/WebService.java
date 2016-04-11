package com.example.ozkrp.listasvarias;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by ruffineo on 09/02/2016.
 */

public class WebService {

    private static final String NAMESPACE = "http://servicios.ws/";
    //private static final String IP = "190.52.175.153";//cuando se use desde una locacion externa
    private static final String IP = "192.168.1.3";//cuando se use una conexion WIFI local
    private static final String URL = "http://" + IP + ":9999/WebApps/Servicios?WSDL";

    private DatosUsuario datosUsuario;

    public ArrayList<Item> recuperaListaParametroObjeto(String user, String pass, String method) {
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
                    escribeLog("envelope response: " + rs.toString());

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
                    returnlist.add(new Item("Vacio","Sin Solicitudes pendientes"));

                }

            } catch (Exception e) {
                escribeLog("Error al castear resultado: " + e.getMessage());
            }

        } catch (IOException e) {
            escribeLog("IOException: " + e.getMessage());
        } catch (XmlPullParserException e) {
            escribeLog("XmlPullParserException: " + e.getMessage());
        }

        return returnlist;
    }

    public ArrayList<Item> retornaItems () {
        ArrayList<Item> items = new ArrayList<Item>();
        items.add(new Item("Item 1","First Item on the list"));
        items.add(new Item("Item 2", "Second Item on the list"));
        items.add(new Item("Item 3", "Third Item on the list"));
        return items;
    }

    public ArrayList<Item> retornaItemsVacio () {
        ArrayList<Item> items = new ArrayList<Item>();
        items.add(new Item("Sin Solicitudes","No se recuperaron datos"));
        return items;
    }

    private void escribeLog(String texto) {
        Log.i("webservice_LOG", texto);
    }

}
