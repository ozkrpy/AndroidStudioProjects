package org.ruffineo.workflowmanager;

/**
 * Created by ruffineo on 09/02/2016.
 */
public class WebService {


    public String validarUsuarioCadena(String user, String pass, String method) {
        if (user.equals("oscar") && (pass.equals("oscar"))) {
            return "OK";
        }
        return "ER";
    }
}
