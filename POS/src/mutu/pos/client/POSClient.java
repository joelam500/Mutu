package mutu.pos.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class POSClient {
    public static void main(String[] args) {
        try{
            Socket _skt = new Socket("s1203728.southeastasia.azurecontainer.io", 12345);
            BufferedReader _in = new BufferedReader(new InputStreamReader(_skt.getInputStream()));
            PrintWriter _out = new PrintWriter(_skt.getOutputStream(), true);
            BufferedReader _usr = new BufferedReader(new InputStreamReader(System.in));
            ClientDisplayHandler _d = new ClientDisplayHandler(_in);
            
            new Thread(_d).start();
            
            String _usrInput = "";
            
            while(true){
                _usrInput = _usr.readLine();
                
                if(!_out.checkError()){
                    _out.println((_usrInput == null) ? "" : _usrInput);
                } else {
                    break;
                }
            }
            
            _out.close();
            _in.close();
            _skt.close();
            _usr.close();
        }catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }//end of try-catch
    }//end of main()
    
}

class ClientDisplayHandler implements Runnable {
    
    private final BufferedReader _in;
    
    public ClientDisplayHandler(BufferedReader in){
        this._in = in;
    }
    
    @Override
    public void run(){
        try{
            String _msg = "";
            
            while((_msg = _in.readLine()) != null){
                System.out.println(_msg);
            }
            _in.close();
        }catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }//end of try-catch
    }
    
}