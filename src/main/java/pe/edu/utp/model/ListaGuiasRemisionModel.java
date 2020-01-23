package pe.edu.utp.model;

import java.util.List;
import pe.edu.utp.dao.CabGuiaRemDao;
import pe.edu.utp.dao.DetGuiaRemDao;
import pe.edu.utp.entity.CabGuiaRem;
import pe.edu.utp.entity.DetGuiaRem;

public class ListaGuiasRemisionModel implements MVPModel{

    @Override
    public void updateModel(String subject, Object[] params) {
        if (subject.equalsIgnoreCase("DeleteCabDet")) {
            //params: pk CabGuiaRemDao
            String pk = (String) params[0];
            DetGuiaRemDao dao2 = new DetGuiaRemDao();
            dao2.delete(pk);
            CabGuiaRemDao dao = new CabGuiaRemDao();
            dao.delete(pk);
        }
    }

    @Override
    public Object[] loadModel(String subject, Object[] params) {
        if (subject.equalsIgnoreCase("Listar1")) {
            CabGuiaRemDao dao = new CabGuiaRemDao();
            List<CabGuiaRem> lista1 = dao.getListOfEntities01(params);
            return new Object[]{lista1};
        }
        return null;
    }
    
}
