package org.ruffineo.workflowmanager;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Date;
import java.util.Hashtable;

/**
 *
 * @author Oscar Ruffinelli
 * @version 1.0
 *
 */
public class DatosUsuario implements KvmSerializable {
    public String user;
    public String pass;
    public String fecha;

    public DatosUsuario() {
    }

    public DatosUsuario(String user, String pass, String fecha) {
        this.user = user;
        this.pass = pass;
        this.fecha = fecha;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }


    @Override
    public Object getProperty(int i) {
        switch(i)
        {
            case 0:
                return user;
            case 1:
                return pass;
            case 2:
                return fecha;
        }

        return null;
    }

    @Override
    public int getPropertyCount() {
        return 3;
    }

    @Override
    public void setProperty(int i, Object o) {
        switch(i)
        {
            case 0:
                user = o.toString();
                break;
            case 1:
                pass = o.toString();
                break;
            case 2:
                fecha = o.toString();
                break;
            default:
                break;
        }
    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        switch(i)
        {
            case 0:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "user";
                break;
            case 1:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "pass";
                break;
            case 2:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "fecha";
                break;
            default:break;
        }
    }
}

