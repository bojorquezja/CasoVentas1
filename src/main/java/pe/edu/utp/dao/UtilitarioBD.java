package pe.edu.utp.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UtilitarioBD {
    private static final String CONNJDBC = getJDBCConnection();
    private static final String USER = getJDBCUser();
    private static final char[] PASS = getJDBCPassword();
    
    
    public static <T> List<T> traeListaBD(String sql, Class[] tipoObjeto, Object[] valores, BiConsumer<List<T>, ResultSet> accion){
        List<T> tlista = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(CONNJDBC,USER,PASS.toString());
            PreparedStatement stat = con.prepareStatement(sql);){
            
            for(int x=0 ; x < tipoObjeto.length ; x++){
                if (tipoObjeto[x].equals(String.class)){
                    stat.setString(x+1, (String) valores[x]);
                } else if (tipoObjeto[x].equals(LocalDate.class)){
                    stat.setDate(x+1, toSqlDate((LocalDate) valores[x]));
                } else if (tipoObjeto[x].equals(Integer.class)){
                    stat.setInt(x+1, (Integer) valores[x]);
                } else if (tipoObjeto[x].equals(Float.class)){
                    stat.setFloat(x+1, (Float) valores[x]);
                } else if (tipoObjeto[x].equals(Double.class)){
                    stat.setDouble(x+1, (Double) valores[x]);
                }
            }
            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()){
                    accion.accept(tlista, rs);
                }
            } catch(SQLException e){
                throw new UnsupportedOperationException("Error: " + e);
            }
            
        } catch (SQLException e){
            throw new UnsupportedOperationException("Error: " + e);
        }
        return tlista;
    }
    
    public static boolean grabaTransaccionBD(String[] sql, Class[][] tipoObjeto, Object[][] valores){
        boolean resp = false;
        int totReg=0;
        try (Connection con = DriverManager.getConnection(CONNJDBC,USER,PASS.toString());){
            con.setAutoCommit(false);
            for (int z=0 ; z<sql.length ; z++){
                try (PreparedStatement stat = con.prepareStatement(sql[z]);){
                    for(int x=0 ; x < tipoObjeto[z].length ; x++){
                        if (tipoObjeto[z][x].equals(String.class)){
                            stat.setString(x+1, (String) valores[z][x]);
                        } else if (tipoObjeto[z][x].equals(LocalDate.class)){
                            stat.setDate(x+1, toSqlDate((LocalDate) valores[z][x]));
                        } else if (tipoObjeto[z][x].equals(Integer.class)){
                            stat.setInt(x+1, (Integer) valores[z][x]);
                        } else if (tipoObjeto[z][x].equals(Float.class)){
                            stat.setFloat(x+1, (Float) valores[z][x]);
                        } else if (tipoObjeto[z][x].equals(Double.class)){
                            stat.setDouble(x+1, (Double) valores[z][x]);
                        }
                    }
                    stat.execute();
                    totReg += stat.getUpdateCount();
                } catch (SQLException e){
                    con.rollback();
                    throw new UnsupportedOperationException("Error: " + e);
                }
                
            }
            con.commit();
            if (totReg > 0){
                resp = true;
            }
            con.setAutoCommit(true);
        } catch (SQLException e){
            throw new UnsupportedOperationException("Error: " + e);
        }
        return resp;
    }
    
    public static Date toSqlDate(LocalDate fecha) {
        if (fecha != null){
            return java.sql.Date.valueOf(fecha);
        }else{
            return null;
        }
    }
    public static LocalDate toLocalDate(Date fecha) {
        if (fecha != null){
            return new java.sql.Date(fecha.getTime()).toLocalDate();
        }else{
            return null;
        }
    }
    public static String getJDBCConnection() {
        try (InputStream input = UtilitarioBD.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                resetJDBCConfiguration();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try (InputStream input = UtilitarioBD.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();
            //load a properties file from class path, inside static method
            prop.load(input);
            return "jdbc:mysql://" + prop.getProperty("JDBC.Server") + ":"+
                    prop.getProperty("JDBC.Port") + "/" +
                    prop.getProperty("JDBC.DataBase") +
                    (prop.getProperty("JDBC.Parameters").isEmpty() ? "" : "?" + prop.getProperty("JDBC.Parameters"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public static String getJDBCUser() {
        try (InputStream input = UtilitarioBD.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                resetJDBCConfiguration();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try (InputStream input = UtilitarioBD.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();
            //load a properties file from class path, inside static method
            prop.load(input);
            return prop.getProperty("JDBC.User");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public static char[] getJDBCPassword() {
        try (InputStream input = UtilitarioBD.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                resetJDBCConfiguration();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try (InputStream input = UtilitarioBD.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();
            //load a properties file from class path, inside static method
            prop.load(input);
            return prop.getProperty("JDBC.Password").toCharArray();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public static void setJDBCConfiguration(String server, String port, String dataBase, String parameters, String user, char[] password) {
        try (OutputStream output = Files.newOutputStream(Paths.get(UtilitarioBD.class.getClassLoader().getResource("config.properties").toURI()))) {
            Properties prop = new Properties();
            // set the properties value
            prop.setProperty("JDBC.Server", server);
            prop.setProperty("JDBC.Port", port);
            prop.setProperty("JDBC.DataBase", dataBase);
            prop.setProperty("JDBC.Parameters", parameters);
            prop.setProperty("JDBC.User", user);
            prop.setProperty("JDBC.Password", password.toString());
            // save properties to project root folder
            prop.store(output, null);
        } catch (IOException | URISyntaxException io) {
            io.printStackTrace();
        }
    }
    public static void resetJDBCConfiguration() {
        try (OutputStream output = Files.newOutputStream(Paths.get(UtilitarioBD.class.getClassLoader().getResource("config.properties").toURI()))) {
            Properties prop = new Properties();
            // set the properties value
            prop.setProperty("JDBC.Server", "localhost");
            prop.setProperty("JDBC.Port", "3306");
            prop.setProperty("JDBC.DataBase", "ventas1");
            prop.setProperty("JDBC.Parameters", "useLegacyDatetimeCode=false&serverTimezone=America/Lima");
            prop.setProperty("JDBC.User", "root");
            prop.setProperty("JDBC.Password", "");
            // save properties to project root folder
            prop.store(output, null);
        } catch (IOException | URISyntaxException io) {
            io.printStackTrace();
        }
    }
}
