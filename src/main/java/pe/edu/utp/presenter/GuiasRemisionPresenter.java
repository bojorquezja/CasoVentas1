package pe.edu.utp.presenter;

import java.time.LocalDate;
import javax.swing.SwingUtilities;
import pe.edu.utp.entity.CabGuiaRem;
import pe.edu.utp.model.GuiasRemisionModel;
import pe.edu.utp.model.MVPModel;
import pe.edu.utp.view.GuiasRemisionView;
import pe.edu.utp.view.MVPView;

public class GuiasRemisionPresenter implements MVPPresenter{
    private MVPView view;
    private MVPModel model;
    private Object[] result;
    private String tipoView;

    public GuiasRemisionPresenter(MVPView view, MVPModel model, Object[] params) {
        this.model = model;
        this.view = view;
        this.result = new Object[]{(Boolean) true};
        this.tipoView = (((String) params[0]).length()>=0) ? (String) params[0] : "READ";
        view.setPresenter(this);
        view.updateView("Iniciar", new Object[]{"Guia de Remision", tipoView});
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
        
        if (subject.equalsIgnoreCase("Aceptar")) {
            //params: CabGuiaRem con Det
            if (this.tipoView.equalsIgnoreCase("INSERT")){
                CabGuiaRem ent = (CabGuiaRem) params[0];
                model.updateModel("InsertCabDet", new Object[]{ent});
                result = new Object[]{(Boolean) true};
            }
            if (this.tipoView.equalsIgnoreCase("UPDATE")){
                CabGuiaRem ent = (CabGuiaRem) params[0];
                model.updateModel("UpdateCabDet", new Object[]{ent});
                result = new Object[]{(Boolean) true};
            }
            
        }
        
    }

    @Override
    public Object[] getResult() {
        return result;
    }
    
}
