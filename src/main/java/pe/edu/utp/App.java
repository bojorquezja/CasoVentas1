package pe.edu.utp;

import javax.swing.SwingUtilities;
import pe.edu.utp.model.ConfiguracionModel;
import pe.edu.utp.model.FacturaModel;
import pe.edu.utp.model.GuiasRemisionModel;
import pe.edu.utp.model.ListaFacturaModel;
import pe.edu.utp.model.ListaGuiasRemisionModel;
import pe.edu.utp.model.PrincipalModel;
import pe.edu.utp.presenter.ConfiguracionPresenter;
import pe.edu.utp.presenter.FacturaPresenter;
import pe.edu.utp.presenter.MVPPresenter;
import pe.edu.utp.presenter.GuiasRemisionPresenter;
import pe.edu.utp.presenter.ListaFacturaPresenter;
import pe.edu.utp.presenter.ListaGuiasRemisionPresenter;
import pe.edu.utp.presenter.PrincipalPresenter;
import pe.edu.utp.view.ConfiguracionView;
import pe.edu.utp.view.FacturaView;
import pe.edu.utp.view.GuiasRemisionView;
import pe.edu.utp.view.ListaFacturaView;
import pe.edu.utp.view.ListaGuiasRemisionView;
import pe.edu.utp.view.PrincipalView;

public class App{
    public static void main( String[] args )    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrincipalView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>


        SwingUtilities.invokeLater(() -> {
            MVPPresenter p = new ConfiguracionPresenter(
                    new ConfiguracionView(null, true), 
                    new ConfiguracionModel(), 
                    new Object[]{""});
            //System.out.println(p.getResult()[0]);   //prueba
        });

    }
}
