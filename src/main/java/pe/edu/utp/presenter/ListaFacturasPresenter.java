package pe.edu.utp.presenter;

import javax.swing.SwingUtilities;
import pe.edu.utp.entity.CabFactura;
import pe.edu.utp.model.GuiasRemisionModel;
import pe.edu.utp.model.MVPModel;
import pe.edu.utp.view.GuiasRemisionView;
import pe.edu.utp.view.MVPView;

public class ListaFacturasPresenter implements MVPPresenter{
    private MVPView view;
    private MVPModel model;
    private Object[] result;
    private String tipoView;

    public ListaFacturasPresenter(MVPView view, MVPModel model, Object[] params) {
        this.model = model;
        this.view = view;
        this.result = null;
        this.tipoView = (((String) params[0]).length()>=0) ? (String) params[0] : "SELECT";
        view.setPresenter(this);
        view.updateView("Iniciar", new Object[]{"Guia de Remision", tipoView});
        Object[] listObj = model.loadModel("Listar1", new Object[]{"", ""});
        view.updateView("Refrescar", new Object[]{listObj[0]});
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
        if (subject.equalsIgnoreCase("Buscar")) {
            //params: codigo GR, cliente
            Object[] listObj = model.loadModel("Listar1", params);
            view.updateView("Refrescar", new Object[]{listObj[0]});
        }
        if (subject.equalsIgnoreCase("Agregar")) {
            //params: codigo GR, cliente
            SwingUtilities.invokeLater(() -> {
                MVPPresenter p = new GuiasRemisionPresenter(
                        new GuiasRemisionView(null, true), 
                        new GuiasRemisionModel(), 
                        new Object[]{"INSERT"});
                Boolean cambio = (Boolean) p.getResult()[0];   //prueba
                if (cambio){
                    Object[] listObj = model.loadModel("Listar1", params);
                    view.updateView("Refrescar", new Object[]{listObj[0]});
                }
            });
        }
        if (subject.equalsIgnoreCase("Editar")) {
            //params: codigo GR, cliente, codigo GR Editar
            SwingUtilities.invokeLater(() -> {
                MVPPresenter p = new GuiasRemisionPresenter(
                        new GuiasRemisionView(null, true), 
                        new GuiasRemisionModel(), 
                        new Object[]{"UPDATE", params[2]});
                Boolean cambio = (Boolean) p.getResult()[0];   //prueba
                if (cambio){
                    Object[] listObj = model.loadModel("Listar1", params);
                    view.updateView("Refrescar", new Object[]{listObj[0]});
                }
            });
        }
        if (subject.equalsIgnoreCase("Borrar")) {
            //params: codigo GR, cliente, codigo GR Borrar
            if ( (Boolean) view.updateView("DltBox", new Object[]{""})[0] ){
                model.updateModel("DeleteCabDet", new Object[]{params[2]});
                Object[] listObj = model.loadModel("Listar1", params);
                view.updateView("Refrescar", new Object[]{listObj[0]});
            }
        }
        if (subject.equalsIgnoreCase("Seleccionar")) {
            //params: codigo GR Selecionado
            result = new Object[]{params[0]};
            view.closeView();
        }
    }

    @Override
    public Object[] getResult() {
        return result;
    }
    
}