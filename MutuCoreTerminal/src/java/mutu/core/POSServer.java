package mutu.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Topic;
import javax.naming.NamingException;

public class POSServer {
    @Resource(lookup="jms/COMPS368TMA04ConnectionFactory")
    private static ConnectionFactory _f;
    
    @Resource(lookup="jms/COMPS368TMA04Topic")
    private static Topic _t;     

    public static void main(String args[]){
        try{
            SharedObject _obj;
            
            try{
                _obj = new SharedObject(_f, _t);
            } catch(NamingException ne) {
                System.out.println("Server cannot be started as EJB cannot be found!");
                ne.printStackTrace();
                return;
            } catch(Exception e) {
                System.out.println("Server cannot be started as EJB cannot be found!");
                return;
            }
            
            ServerSocket ssk = new ServerSocket(12345);
            
            System.out.println("Server started");
            
            while(true){
                Socket _skt = ssk.accept();
                ClientHandler _h = new ClientHandler(_obj, _skt);
                new Thread(_h).start();
                
                System.out.println("A connection received");
            }
            
        } catch (IOException ioe){
            System.out.println(ioe.getMessage());
        }//end of try-catch
    }//end of main()
}