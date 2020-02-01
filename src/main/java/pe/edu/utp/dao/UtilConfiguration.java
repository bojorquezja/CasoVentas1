package pe.edu.utp.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class UtilConfiguration {
    public static String get(String propiedad){
        String[] res = getArray(new String[]{propiedad});
        return (res == null ? null : res[0]);
    }
    public static String[] getArray(String[] propiedades){
        if (Files.exists(Paths.get("config.properties"))) {
            reset();
        }
        String[] res = new String[propiedades.length];
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
    public static void reset(){
        Properties prop=new Properties();
        try (InputStream input = UtilDataBase.class.getClassLoader().getResourceAsStream("config.properties")) {
            //load a properties file from class path, inside static method
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        Properties prop2 = new Properties(prop);
        try (OutputStream output = Files.newOutputStream(Paths.get("config.properties"))) {
            // set the properties value
            prop2.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
