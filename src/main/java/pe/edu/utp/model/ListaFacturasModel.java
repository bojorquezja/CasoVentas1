package pe.edu.utp.model;

import java.util.List;
import pe.edu.utp.dao.CabFacturaDao;
import pe.edu.utp.dao.DetFacturaDao;
import pe.edu.utp.entity.CabFactura;

public class ListaFacturasModel implements MVPModel{

    @Override
    public void updateModel(String subject, Object[] params) {
        if (subject.equalsIgnoreCase("DeleteCabDet")) {
            //params: pk CabFacturaDao
            String pk = (String) params[0];
            DetFacturaDao dao2 = new DetFacturaDao();
            dao2.delete(pk);
            CabFacturaDao dao = new CabFacturaDao();
            dao.delete(pk);
        }
    }

    @Override
    public Object[] loadModel(String subject, Object[] params) {
        if (subject.equalsIgnoreCase("Listar1")) {
            CabFacturaDao dao = new CabFacturaDao();
            List<CabFactura> lista1 = dao.getListOfEntities01(params);
            return new Object[]{lista1};
        }
        return null;
    }
    
}
