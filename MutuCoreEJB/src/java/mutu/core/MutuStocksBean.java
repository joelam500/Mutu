package mutu.core;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import mutu.core.entities.MutuStocks;

@Stateless
@LocalBean
public class MutuStocksBean implements MutuStocksBeanRemote {

    @PersistenceContext(unitName = "MutuCoreEJBPU")
    private EntityManager em;
    
    @Override
    public MutuStocks[] getAllStocks() {
        Query q = em.createQuery("select s from MutuStocks s");
        List<MutuStocks> allStocks = q.getResultList();
        return allStocks.toArray(new MutuStocks[allStocks.size()]);
    }
    
    @Override
    public MutuStocks getStockByStockId(long _stockId) {
        return em.find(MutuStocks.class, _stockId);
    }
    
    @Override
    public boolean updStockCurrentPrice(long _stockId, double _price){
        boolean rtn = false;
        MutuStocks _stock = em.find(MutuStocks.class, _stockId);
        if(_stock != null){
            _stock.setScurrentprice(_price);
            rtn = true;
        }
        return rtn;
    }
}
