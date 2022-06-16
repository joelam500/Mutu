package mutu.core;

import javax.ejb.Remote;
import mutu.core.entities.MutuOrders;

@Remote
public interface MutuOrdersBeanRemote {

    MutuOrders[] getAllQueuingOrdersByStockId(long _stockId);

    MutuOrders placeOrder(long _stockId, int _qty, double price, boolean _isBuy);

    boolean confirmOrder(long _oId);

    boolean isAlreadySettled(long _oId);

    MutuOrders getQueuingOrdersByOid(long _oId);

    MutuOrders[] getBuySideQueueByStockId(long _stockId);

    MutuOrders[] getSellSideQueueByStockId(long _stockId);
    
    MutuOrders[] getMatchQueueOrder(MutuOrders _o);
}
