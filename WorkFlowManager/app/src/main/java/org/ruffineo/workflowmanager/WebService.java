package org.ruffineo.workflowmanager;

import android.util.Log;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.util.ArrayList;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class WebService {

    private DatosUsuario datosUsuario;
    private String tagParametroDatosUsuario = "datosUsuario";
    private String tagParametroNumeroSolicitud = "numeroSolicitud";
    private String tagParametroEstadoAprobacion = "estadoAprobacion";
    private static final String TAG = "WEBSERVICE_LOG";
    private static final String NAMESPACE = "http://operations.ws/";
    private static String IP = "190.52.175.153";//cuando se use desde una locacion externa
    //private static String IP = "10.133.23.1";//cuando se use desde una locacion externa
    private static final String URL = "http://" + IP + ":9999/Mobile/Services?WSDL";

    public Respuesta consultaUsuario(String user, String pass, String method) {
        escribeLog("entro al metodo consultaUsuario");
        Respuesta respuesta = new Respuesta(0, "ER", "se inicializo correctamente en la APP");
        datosUsuario = new DatosUsuario(user, pass, "20160404");//TODO sending a correct date
        SoapSerializationEnvelope envelope = crearSOAPenvelope(method, datosUsuario, null, null);
        escribeLog("recupero el envelope");
        try {
            if (envelope != null) {
                escribeLog("envelope no es nulo");
                SoapObject resultsRequestSOAP = (SoapObject) envelope.getResponse();
                if (resultsRequestSOAP != null) {
                    escribeLog("recupero getResponse");
                    Respuesta respuestaObjeto = new Respuesta(resultsRequestSOAP);
                    respuesta = respuestaObjeto;
                } else {
                    respuesta.setReferencia("Retorno respuesta vacia.");
                }
            } else {
                respuesta.setReferencia("Respuesta vacia al invocar el WS.");
            }
        } catch (IOException e) {
            respuesta.setReferencia("No se pudo conectar al servidor.");
        } catch (Exception e) {
            respuesta.setReferencia("Excepcion: " + e.getMessage());
        }
        return respuesta;
    }

    public Tarea recuperaTarea(String user, String pass, String numeroSolicitud, String method) {
        escribeLog("entro al metodo recuperaTarea");
        Tarea respuesta = null;
        datosUsuario = new DatosUsuario(user, pass, "20160404");//TODO
        SoapSerializationEnvelope envelope = crearSOAPenvelope(method, datosUsuario, numeroSolicitud, null);
        escribeLog("recupero el envelope");
        try {
            if (envelope != null) {
                escribeLog("envelope no es nulo");
                SoapObject resultsRequestSOAP = (SoapObject) envelope.getResponse();
                if (resultsRequestSOAP != null) {
                    escribeLog("recupero getResponse");
                    Tarea respuestaObjeto = new Tarea(resultsRequestSOAP);
                    respuesta = respuestaObjeto;
                } else {
                    escribeLog("Retorno respuesta vacia.");
                }
            } else {
                escribeLog("Respuesta vacia al invocar el WS.");
            }
        } catch (IOException e) {
            escribeLog("No se pudo conectar al servidor.");
        } catch (Exception e) {
            escribeLog("Excepcion: " + e.getMessage());
        }
        return respuesta;
    }

    public ArrayList<Item> recuperaLista(String user, String pass, String method) {
        escribeLog("entro al metodo recuperaLista");
        ArrayList<Item> returnlist = new ArrayList<Item>();
        datosUsuario = new DatosUsuario(user, pass, "20160404");//TODO sending a correct date
        SoapSerializationEnvelope envelope = crearSOAPenvelope(method, datosUsuario, null, null);
        escribeLog("recupero el envelope");
        try {
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
            escribeLog("No se pudo conectar al servidor.");
        } catch (Exception e) {
            escribeLog("Excepcion: " + e.getMessage());
        }
        return returnlist;
    }

    public Respuesta actualizaEstadoSolicitud(String user, String pass, String numeroSolicitud, String nuevoEstado, String method) {
        escribeLog("entro al metodo actualizaEstadoSolicitud");
        Respuesta respuesta = null;
        datosUsuario = new DatosUsuario(user, pass, "20160404");//TODO
        SoapSerializationEnvelope envelope = crearSOAPenvelope(method, datosUsuario, numeroSolicitud, nuevoEstado);
        escribeLog("recupero el envelope");
        try {
            if (envelope != null) {
                escribeLog("envelope no es nulo");
                SoapObject resultsRequestSOAP = (SoapObject) envelope.getResponse();
                if (resultsRequestSOAP != null) {
                    escribeLog("recupero getResponse");
                    Respuesta respuestaObjeto = new Respuesta(resultsRequestSOAP);
                    respuesta = respuestaObjeto;
                    escribeLog("Recupero: " + resultsRequestSOAP.toString());
                } else {
                    respuesta.setReferencia("Retorno respuesta vacia.");
                }
            } else {
                respuesta.setReferencia("Respuesta vacia al invocar el WS.");
            }
        } catch (IOException e) {
            respuesta.setReferencia("No se pudo conectar al servidor.");
        } catch (Exception e) {
            respuesta.setReferencia("Excepcion: " + e.getMessage());
        }
        return respuesta;
    }

    private SoapSerializationEnvelope crearSOAPenvelope (String method, DatosUsuario datosUsuario, String numeroSolicitud, String nuevoEstado) {
        escribeLog("entro a crear soap envelope");
        String SOAP_ACTION = NAMESPACE + method;
        SoapObject request = new SoapObject(NAMESPACE, method);
        escribeLog("creo correctamente el request");
        PropertyInfo propiedadUsuario = agregarPropiedadUsuario(tagParametroDatosUsuario, datosUsuario);
        request.addProperty(propiedadUsuario);
        if (numeroSolicitud != null) {
            request.addProperty(tagParametroNumeroSolicitud, numeroSolicitud);
        }
        if (nuevoEstado != null) {
            request.addProperty(tagParametroEstadoAprobacion, nuevoEstado);
        }
        escribeLog("termino de agregar propiedades");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        escribeLog("genero el http transport");
        try {
            androidHttpTransport.call(SOAP_ACTION, envelope);
            escribeLog("creo la llamada al envelope");
            if (envelope != null) {
                escribeLog("envelope no esta vacio");
                return envelope;
            }
        } catch (IOException e) {
            escribeLog("error de IO");
            return null;
        } catch (XmlPullParserException e) {
            escribeLog("error de XML Pull parser");
            return null;
        }
        return null;
    }

    private PropertyInfo agregarPropiedadUsuario (String nombrePropiedad, DatosUsuario datosUsuario) {
        escribeLog("entro a agregar propiedad objeto DatosUsuario");
        PropertyInfo pi = new PropertyInfo();
        pi.setName(nombrePropiedad);
        pi.setValue(datosUsuario);
        pi.setType(datosUsuario.getClass());
        escribeLog("agrego propiedad objeto datos usuario correctamente");
        return pi;
    }

    public ArrayList<Item> retornaItemsVacio () {
        escribeLog("entro al metodo retornaItemsVacio");
        ArrayList<Item> items = new ArrayList<Item>();
        items.add(new Item("Sin Solicitudes","No se recuperaron datos"));
        return items;
    }

    private void escribeLog(String texto) {
        Log.i(TAG, texto);
    }
}