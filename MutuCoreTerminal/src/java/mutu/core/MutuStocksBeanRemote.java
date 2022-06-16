package mutu.core;

import javax.ejb.Remote;
import mutu.core.entities.MutuStocks;

@Remote
public interface MutuStocksBeanRemote {

    MutuStocks[] getAllStocks();

    MutuStocks getStockByStockId(long _stockId);

    boolean updStockCurrentPrice(long _stockId, double _price);
}
