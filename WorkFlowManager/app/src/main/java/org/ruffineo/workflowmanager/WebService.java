package org.ruffineo.workflowmanager;

import java.util.List;

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

    public String[] solicitudesPendientes (String user, String method) {
        String[] solicitudes = new String[] {
                "2386",
                "2340",
                "2305"
        };

        return solicitudes;
    }
}
