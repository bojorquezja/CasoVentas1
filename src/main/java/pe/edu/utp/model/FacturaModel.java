package pe.edu.utp.model;

import java.util.List;
import pe.edu.utp.dao.CabFacturaDao;
import pe.edu.utp.dao.DetFacturaDao;
import pe.edu.utp.entity.CabFactura;
import pe.edu.utp.entity.DetFactura;

public class FacturaModel implements MVPModel{

    @Override
    public void updateModel(String subject, Object[] params) {
        if (subject.equalsIgnoreCase("InsertCabDet")) {
            //params: CabFactura con Det
            CabFacturaDao dao = new CabFacturaDao();
            CabFactura ent = (CabFactura) params[0];
            dao.insert(ent);
            DetFacturaDao dao2 = new DetFacturaDao();
            ent.getDetFactura().forEach((item) -> {
                dao2.insert(item);
            });
        }
        if (subject.equalsIgnoreCase("UpdateCabDet")) {
            //params: CabFactura con Det
            CabFacturaDao dao = new CabFacturaDao();
            CabFactura ent = (CabFactura) params[0];
            dao.update(ent);
            DetFacturaDao dao2 = new DetFacturaDao();
            dao2.delete(ent.getCodGuiaRem());
            ent.getDetFactura().forEach((item) -> {
                dao2.insert(item);
            });
        }
    }

    @Override
    public Object[] loadModel(String subject, Object[] params) {
        if (subject.equalsIgnoreCase("CabDet")) {
            //params: pk CabFacturaDao
            String pk = (String) params[0];
            CabFacturaDao dao = new CabFacturaDao();
            CabFactura ent = dao.getEntity(pk).orElse(null);
            
            if (ent != null){
                DetFacturaDao dao2 = new DetFacturaDao();
                List<DetFactura> lista1 = dao2.getListOfEntities01(new Object[]{pk});
                ent.setDetFactura(lista1);
                
            }
            
            return new Object[]{ent};
        }
        return null;
    }
    
}
