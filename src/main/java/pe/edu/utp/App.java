package pe.edu.utp;

import javax.swing.SwingUtilities;
import pe.edu.utp.model.PrincipalModel;
import pe.edu.utp.presenter.MVPPresenter;
import pe.edu.utp.presenter.PrincipalPresenter;
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
            MVPPresenter p = new PrincipalPresenter(
                    new PrincipalView(), 
                    new PrincipalModel(), 
                    new Object[]{""});
            //System.out.println(p.getResult()[0]);   //prueba
        });

    }
}
