package pe.edu.utp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import pe.edu.utp.dao.CabFacturaDao;
import pe.edu.utp.dao.Dao;
import pe.edu.utp.entity.CabFactura;

public class FacturaTest {
    static final String DB_URL = "jdbc:mysql://localhost:3306/ventas1?useLegacyDatetimeCode=false&serverTimezone=America/Lima";
    static final String USUAR = "usuario";
    static final String CONTR = "contrasena";
    
    public static void main(String[] args) {
        System.out.println("Test cabecera factura:");
        Dao<CabFactura> cf = new CabFacturaDao();
        List<CabFactura> lcf = cf.getListOfEntities01(new Object[]{"01", "te 1"});
        System.out.println(lcf);
        //CabFactura c = lcf.get(0);
        //c.setRazSocEmpresa("Emp XYZ");
        //cf.update(c);
        lcf = cf.getListOfEntities01(new Object[]{"01", "te 1"});
        System.out.println(lcf);
        lcf = cf.getListOfEntities01(new Object[]{"01", "te 1"});
        System.out.println(lcf);
        lcf = cf.getListOfEntities01(new Object[]{"01", "te 1"});
        System.out.println(lcf);
    }
    
    public static void selectFactura(){
        //List<CabFactura> personas = new ArrayList<>();
        String nombre = "a";
        String sql = "SELECT codigoFac, fechaEmi, codGuiaRem, rucEmpresa, " +
                    "razSocEmpresa, rucCliente, razSocCliente, direcCliente, " +
                    "cajero, subTotal, igv, total " +
                    "FROM CabFactura " +
                    "WHERE codigoFac like ? AND razSocCliente like ?";
        
        try (Connection con = DriverManager.getConnection(DB_URL,USUAR,CONTR);
            PreparedStatement stat = con.prepareStatement(sql);){
            
            stat.setString(1, "%01%");
            stat.setString(2, "%te 1%");
            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()){
                    Date nac = rs.getDate(2);    //fecha
                    LocalDate fecNac = null;
                    if (nac != null){   //convierte a LocalDate si no es null
                        fecNac = new java.sql.Date(nac.getTime()).toLocalDate();
                    }
                    System.out.print("AA: " + nac.toLocalDate());
                    
                    //CabFactura per = new CabFactura(rs.getString(1),fecNac,rs.getInt(3),rs.getDouble(4));
                    //personas.add(per);
                }
            } catch(SQLException e){System.out.println("Error: " + e);}
            
            //System.out.println("Se obtuvieron " + personas.size() + " registros");
        } catch (SQLException e){
            System.out.println("Error: " + e);
        }
    }
}
