package pe.edu.utp.presenter;

import pe.edu.utp.entity.CabFactura;
import pe.edu.utp.entity.DetFactura;
import pe.edu.utp.model.MVPModel;
import pe.edu.utp.view.MVPView;

public class FacturasPresenter implements MVPPresenter{
    private MVPView view;
    private MVPModel model;
    private Object[] result;
    private String tipoView;

    public FacturasPresenter(MVPView view, MVPModel model, Object[] params) {
        //tipoview, pk CabGR
        this.model = model;
        this.view = view;
        this.result = new Object[]{(Boolean) true};
        this.tipoView = (((String) params[0]).length()>=0) ? (String) params[0] : "READ";
        view.setPresenter(this);
        CabFactura ent=null;
        if ( this.tipoView.equalsIgnoreCase("READ") || this.tipoView.equalsIgnoreCase("UPDATE") ){
            ent = (CabFactura) model.loadModel("CabDet", new Object[]{params[1]})[0];
        }
        view.updateView("Iniciar", new Object[]{"Guia de Remision", tipoView, ent});
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
            //params: CabFactura con Det
            if (this.tipoView.equalsIgnoreCase("INSERT")){
                CabFactura ent = (CabFactura) params[0];
                model.updateModel("InsertCabDet", new Object[]{ ent });
                result = new Object[]{(Boolean) true};
                view.closeView();
            }
            if (this.tipoView.equalsIgnoreCase("UPDATE")){
                CabFactura ent = (CabFactura) params[0];
                model.updateModel("UpdateCabDet", new Object[]{ ent });
                result = new Object[]{(Boolean) true};
                view.closeView();
            }
            
        }
        
    }

    @Override
    public Object[] getResult() {
        return result;
    }
    
}