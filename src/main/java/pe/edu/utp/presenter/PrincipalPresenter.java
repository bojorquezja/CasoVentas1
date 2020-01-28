package pe.edu.utp.presenter;

import javax.swing.SwingUtilities;
import pe.edu.utp.model.ListaFacturaModel;
import pe.edu.utp.model.ListaGuiasRemisionModel;
import pe.edu.utp.model.MVPModel;
import pe.edu.utp.view.ListaFacturaView;
import pe.edu.utp.view.ListaGuiasRemisionView;
import pe.edu.utp.view.MVPView;

public class PrincipalPresenter implements MVPPresenter{
    private MVPView view;
    private MVPModel model;
    private Object[] result;
    private String tipoView;

    public PrincipalPresenter(MVPView view, MVPModel model, Object[] params) {
        //tipoview
        this.model = model;
        this.view = view;
        this.result = new Object[]{(Boolean) true};
        this.tipoView = (((String) params[0]).length()>=0) ? (String) params[0] : "READ";
        view.setPresenter(this);
        view.updateView("Iniciar", new Object[]{"Guia de Remision"});
        view.showView();
    }
    
    @Override
    public void setView(MVPView view) {
        this.view = view;
    }

    @Override
    public void setModel(MVPModel model) {
        this.model = model;
    }

    @Override
    public void notifyPresenter(String subject, Object[] params) {
        if (subject.equalsIgnoreCase("Cancelar")) {
            view.closeView();
        }
        
        if (subject.equalsIgnoreCase("Menu")) {
            //params: Opcion
            if (((String) params[0]).equalsIgnoreCase("MantGuiaRemision")){
                SwingUtilities.invokeLater(() -> {
                    MVPPresenter p = new ListaGuiasRemisionPresenter(
                            new ListaGuiasRemisionView(null, true), 
                            new ListaGuiasRemisionModel(), 
                            new Object[]{"MAINTENANCE"});
                });
            }
            if (((String) params[0]).equalsIgnoreCase("MantFactura")){
                SwingUtilities.invokeLater(() -> {
                    MVPPresenter p = new ListaFacturaPresenter(
                            new ListaFacturaView(null, true), 
                            new ListaFacturaModel(), 
                            new Object[]{"MAINTENANCE"});
                });
            }
            
        }
        
    }

    @Override
    public Object[] getResult() {
        return result;
    }
    
}
