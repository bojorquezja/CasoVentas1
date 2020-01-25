package pe.edu.utp.model;

import java.util.List;
import pe.edu.utp.dao.CabGuiaRemDao;
import pe.edu.utp.dao.DetGuiaRemDao;
import pe.edu.utp.entity.CabGuiaRem;
import pe.edu.utp.entity.DetGuiaRem;

public class GuiasRemisionModel implements MVPModel{

    @Override
    public void updateModel(String subject, Object[] params) {
        if (subject.equalsIgnoreCase("InsertCabDet")) {
            //params: CabGuiaRem con Det
            CabGuiaRemDao dao = new CabGuiaRemDao();
            CabGuiaRem ent = (CabGuiaRem) params[0];
            dao.insert(ent);
            DetGuiaRemDao dao2 = new DetGuiaRemDao();
            ent.getDetGuiaRem().forEach((item) -> {
                dao2.insert(item);
            });
        }
        if (subject.equalsIgnoreCase("UpdateCabDet")) {
            //params: CabGuiaRem con Det
            CabGuiaRemDao dao = new CabGuiaRemDao();
            CabGuiaRem ent = (CabGuiaRem) params[0];
            dao.update(ent);
            DetGuiaRemDao dao2 = new DetGuiaRemDao();
            dao2.delete(ent.getCodGuiaRem());
            ent.getDetGuiaRem().forEach((item) -> {
                dao2.insert(item);
            });
        }
    }

    @Override
    public Object[] loadModel(String subject, Object[] params) {
        if (subject.equalsIgnoreCase("CabDet")) {
            //params: pk CabGuiaRemDao
            String pk = (String) params[0];
            CabGuiaRemDao dao = new CabGuiaRemDao();
            CabGuiaRem ent = dao.getEntity(pk).orElse(null);
            
            if (ent != null){
                DetGuiaRemDao dao2 = new DetGuiaRemDao();
                List<DetGuiaRem> lista1 = dao2.getListOfEntities01(new Object[]{pk});
                ent.setDetGuiaRem(lista1);
                
            }
            
            return new Object[]{ent};
        }
        return null;
    }
    
}
