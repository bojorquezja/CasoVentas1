package pe.edu.utp.model;

import pe.edu.utp.dao.UtilConfiguration;

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
