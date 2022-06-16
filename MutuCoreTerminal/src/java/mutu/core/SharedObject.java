package mutu.core;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import mutu.core.entities.MutuOrders;
import mutu.core.entities.MutuStocks;
import mutu.core.messagehub.MutuMessageListener;
import mutu.models.Orders;

public class SharedObject {
    private MutuStocksBeanRemote stocksDB;
    private MutuOrdersBeanRemote ordersDB;
    
    private final ReentrantLock _lock;
    private final Condition _hasBuy, _hasSell;
    
    public SharedObject(ConnectionFactory _f, Topic _t) throws Exception{    
        this._lock = new ReentrantLock();
        this._hasBuy = this._lock.newCondition();
        this._hasSell = this._lock.newCondition();     
        this.prepareEJB();
        this.prepareMsgListener(_f, _t);
    }
    
    private void prepareEJB() throws NamingException{       
        InitialContext initialContext = new InitialContext();
        stocksDB = (MutuStocksBeanRemote)initialContext.lookup("java:global/MutuCoreEJB/MutuStocksBean!mutu.core.MutuStocksBeanRemote");
        ordersDB = (MutuOrdersBeanRemote)initialContext.lookup("java:global/MutuCoreEJB/MutuOrdersBean!mutu.core.MutuOrdersBeanRemote");
    }    
    
    private void prepareMsgListener(ConnectionFactory _f, Topic _t){    
        JMSContext context = _f.createContext();
        JMSConsumer consumer = context.createConsumer(_t);
        consumer.setMessageListener(new MutuMessageListener(this));
        
    }

    public void notifyOrder(boolean isBuy){
        _lock.lock();
        try{
            if(isBuy)
            {
                _hasBuy.signalAll();
            }else{
                _hasSell.signalAll();
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        } finally{
            _lock.unlock();
        }
    }
    
    public void Buy(Orders _order){
        this._lock.lock();
        
        try{
            MutuOrders _thisOrders = this.placeOrder(_order.getStockId(), _order.getQty(), _order.getPrice(), true);
            
            _hasBuy.signalAll();

            while(!this.isAlreadySettled(_thisOrders.getOid()) && this.getMatchQueueOrder(_thisOrders).length <= 0){
                _hasSell.await();
            }
                      
            if(!this.isAlreadySettled(_thisOrders.getOid())){
                this.confirmOrder(_thisOrders.getOid());
            }
        }catch(InterruptedException e){
            System.out.println(e.getMessage());
        } finally {
            this._lock.unlock();
        }
    }//end of Buy()
    
    public void Sell(Orders _order){
        this._lock.lock();
        
        try{
            MutuOrders _thisOrders = this.placeOrder(_order.getStockId(), _order.getQty(), _order.getPrice(), false);
            
            _hasSell.signalAll();

            while(!this.isAlreadySettled(_thisOrders.getOid()) && this.getMatchQueueOrder(_thisOrders).length <= 0){
                _hasBuy.await();
            }
                     
            if(!this.isAlreadySettled(_thisOrders.getOid())){
                this.confirmOrder(_thisOrders.getOid());
            }
        }catch(InterruptedException e){
            System.out.println(e.getMessage());
        } finally {
            this._lock.unlock();
        }
    }//end of Sell() 

    public MutuStocks[] getAllStocks(){
        return this.stocksDB.getAllStocks();
    }//end of getAllStocks()

    public MutuStocks getStockByStockId(long _stockId){
        return this.stocksDB.getStockByStockId(_stockId);
    }//end of getStockByStockId()

    public MutuOrders[] getAllQueuingOrdersByStockId(long _stockId){
        return this.ordersDB.getAllQueuingOrdersByStockId(_stockId);
    }//end of getAllQueuingTransByStockId()

    public MutuOrders[] getSellSideQueueByStockId(long _stockId){
        return this.ordersDB.getSellSideQueueByStockId(_stockId);
    }//end of getSellSideQueueByStockIdWithPrice()

    public MutuOrders[] getBuySideQueueByStockId(long _stockId){
        return this.ordersDB.getBuySideQueueByStockId(_stockId);
    }//end of getBuySideQueueByStockIdWithPrice()
    
    public MutuOrders[] getMatchQueueOrder(MutuOrders _o){
        return this.ordersDB.getMatchQueueOrder(_o);
    }
    
    public MutuOrders getQueuingOrdersByOid(long _oId){
        return this.ordersDB.getQueuingOrdersByOid(_oId);
    }//end of getQueuingOrdersByOid()        
    
    private boolean isAlreadySettled(long _oId){
        boolean rtn = this.ordersDB.isAlreadySettled(_oId);
        return rtn;
    }//end of isAlreadySettled()    

    private MutuOrders placeOrder(long _stockId, int _qty, double _price, boolean _isBuy){
        return this.ordersDB.placeOrder(_stockId, _qty, _price, _isBuy);
    }//end of placeOrder()

    private boolean confirmOrder(long _oId){
        return this.ordersDB.confirmOrder(_oId);
    }//end of confirmOrder()        
}