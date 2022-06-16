package mutu.messagehub;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Topic;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(serviceName="StocksSvc")
public class StocksWebAPI {
    @Resource(lookup="jms/COMPS368TMA04ConnectionFactory")
    private ConnectionFactory _f;
    
    @Resource(lookup="jms/COMPS368TMA04Topic")
    private Topic _t;
    
    @WebMethod(operationName="OrderCreated")
    @WebResult(name="IsSuccess")
    public
        boolean OrderCreated(@WebParam(name="IsBuy") boolean IsBuy
        ){
        try{
            JMSContext context = _f.createContext();
            String _isBuy = Boolean.toString(IsBuy);
            
            JMSProducer producer = context.createProducer();
            producer.send(_t, _isBuy);
            
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
