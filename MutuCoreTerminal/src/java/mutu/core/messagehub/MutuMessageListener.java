package mutu.core.messagehub;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import mutu.core.SharedObject;

public class MutuMessageListener implements MessageListener {
    
    @Override
    public void onMessage(Message message){
        try{
            
            String _sIsBuy = message.getBody(String.class);
            
            _obj.notifyOrder(Boolean.parseBoolean(_sIsBuy));
        } catch(JMSException je){
            System.out.println(je.getMessage());
        }
    }

    private final SharedObject _obj;
    
    public MutuMessageListener(SharedObject obj){
        this._obj = obj;
    }    
}
