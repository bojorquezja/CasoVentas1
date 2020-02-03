package pe.edu.utp.model;

import java.util.List;
import pe.edu.utp.dao.CabGuiaRemDao;
import pe.edu.utp.dao.DetGuiaRemDao;
import pe.edu.utp.dao.UtilConfiguration;
import pe.edu.utp.entity.CabGuiaRem;
import pe.edu.utp.entity.DetGuiaRem;

public class ConfiguracionModel implements MVPModel{

    @Override
    public void updateModel(String subject, Object[] params) {
        if (subject.equalsIgnoreCase("Todo")) {
            //params: String[]
            String[] propiedades = new String[]{"JDBC.Server", "JDBC.Port", "JDBC.DataBase",
                            "JDBC.Parameters", "JDBC.Connection", "JDBC.User", 
                            "JDBC.Password"};
                
            UtilConfiguration.setArray(propiedades, (String[]) params);
        }
        
    }

    @Override
    public Object[] loadModel(String subject, Object[] params) {
        if (subject.equalsIgnoreCase("Todo")) {
            //params: 
            return UtilConfiguration.getArray(new String[]{"JDBC.Server", "JDBC.Port", "JDBC.DataBase",
                            "JDBC.Parameters", "JDBC.Connection", "JDBC.User", 
                            "JDBC.Password"}
            );
        }
        if (subject.equalsIgnoreCase("Reset")) {
            //params: 
            return UtilConfiguration.getDefaultArray(new String[]{"JDBC.Server", "JDBC.Port", "JDBC.DataBase",
                            "JDBC.Parameters", "JDBC.Connection", "JDBC.User", 
                            "JDBC.Password"}
            );
        }
        return null;
    }
    
}
