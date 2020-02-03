package pe.edu.utp.dao;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class UtilConfiguration {
    public static String get(String propiedad){
        String[] res = getArray(new String[]{propiedad});
        return (res == null ? null : res[0]);
    }
    public static String[] getArray(String[] propiedades){
        String[] res = new String[propiedades.length];
        if (Paths.get("config.properties").toFile().exists()) {
            Properties prop = new Properties();
            try (InputStream input = Files.newInputStream(Paths.get("config.properties"))) {
                //load a properties file from class path, inside static method
                prop.load(input);
                for(int x=0 ; x<propiedades.length ; x++){
                    res[x] = prop.getProperty(propiedades[x]);
                }
                return res;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }else{
            Properties prop = initialConfig();
            for(int x=0 ; x<propiedades.length ; x++){
                res[x] = prop.getProperty(propiedades[x]);
            }
            return res;
        }
    }
    
    public static void set(String propiedad, String valor){
        setArray(new String[]{propiedad}, new String[]{valor});
    }
    public static void setArray(String[] propiedades, String[] valores){
        Properties prop = new Properties();
        try (OutputStream output = Files.newOutputStream(Paths.get("config.properties"))) {
            // set the properties value
            for(int x=0 ; x<propiedades.length ; x++){
                prop.setProperty(propiedades[x], valores[x]);
            }
            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
    
    public static String[] getDefaultArray(String[] propiedades){
        String[] res = new String[propiedades.length];
        Properties prop = initialConfig();
        for(int x=0 ; x<propiedades.length ; x++){
            res[x] = prop.getProperty(propiedades[x]);
        }
        return res;
    }
    
    private static Properties initialConfig(){
        Properties defa = new Properties();
        try (InputStream input = UtilDataBase.class.getClassLoader().getResourceAsStream("config.properties")) {
            //load a properties file from class path, inside static method
            defa.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return defa;
    }
}
