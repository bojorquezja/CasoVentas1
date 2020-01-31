package pe.edu.utp.dao;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class UtilConfiguration {
    public static String get(String propiedad){
        inicializa();
        try (InputStream input = UtilDataBase.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();
            //load a properties file from class path, inside static method
            prop.load(input);
            return prop.getProperty(propiedad);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public static String[] getArray(String[] propiedades){
        inicializa();
        String[] res = new String[propiedades.length];
        try (InputStream input = UtilDataBase.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();
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
        try (OutputStream output = Files.newOutputStream(Paths.get(UtilDataBase.class.getClassLoader().getResource("config.properties").toURI()))) {
            Properties prop = new Properties();
            // set the properties value
            prop.setProperty(propiedad, valor);
            prop.store(output, null);
        } catch (IOException | URISyntaxException io) {
            io.printStackTrace();
        }
    }
    public static void setArray(String[] propiedades, String[] valores){
        try (OutputStream output = Files.newOutputStream(Paths.get(UtilDataBase.class.getClassLoader().getResource("config.properties").toURI()))) {
            Properties prop = new Properties();
            // set the properties value
            for(int x=0 ; x<propiedades.length ; x++){
                prop.setProperty(propiedades[x], valores[x]);
            }
            prop.store(output, null);
        } catch (IOException | URISyntaxException io) {
            io.printStackTrace();
        }
    }
    private static void inicializa(){
        try (InputStream input = UtilDataBase.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                reset();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static void reset(){
        String[] propiedad = {"JDBC.Server", "JDBC.Port", "JDBC.DataBase",
                            "JDBC.Parameters", "JDBC.User", "JDBC.Password"
                            };
        String[] valor = {"localhost", "3306", "ventas1", 
                        "useLegacyDatetimeCode=false&serverTimezone=America/Lima", "root", ""
                        };
        setArray(propiedad, valor);
    }
}
