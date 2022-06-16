package mutu.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.regex.Pattern;
import mutu.core.entities.MutuOrders;
import mutu.core.entities.MutuStocks;
import mutu.models.Orders;

public class ClientHandler implements Runnable{  
    
    private final SharedObject _obj;
    private final Socket _skt;
    private final BufferedReader _in;
    private final PrintWriter _out;
    
    public ClientHandler(SharedObject obj, Socket skt) throws IOException{
        this._obj = obj;
        this._skt = skt;
        this._in = new BufferedReader(new InputStreamReader(_skt.getInputStream()));
        this._out = new PrintWriter(_skt.getOutputStream(), true);
    }//end of constructor
    
    @Override
    public void run(){
        try{
            do{
                _out.println("Choice an action, input an empty line to exit");
                String _userChoice = getUserInput("(P)lace Order / (S)tock enquiry / (O)rders enquiry ", "[P|S|O]");
                
                if(_userChoice.isEmpty()){
                    _out.close();
                    _in.close();
                    _skt.close();
                    break;
                }
                
                switch(_userChoice){
                    case "P":
                        placeOrder();
                        break;
                    case "S":
                        stockEnquiry();
                        break;
                    case "O":
                        ordersEnquiry();
                        break;
                }
            }while(true);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }//end of try-catch
    }
    
    private void placeOrder() throws Exception{
        _out.println("Place an order, input an empty line to exit");
        
        Orders _orders = getUserInputOrder();
        if(_orders == null){
            return;
        }

        if(_orders.isIsBuy()){
            this._obj.Buy(_orders);
        } else {
            this._obj.Sell(_orders);
        }
    }//end of placeOrder();
   
    private void stockEnquiry() throws IOException{
        _out.println("Stock enquiry, input an empty line to exit");
        
        do{
            String _userChoice = getUserInput("(A)ll stocks / (G)et stock by id", "[A|G]");
            if(_userChoice.isEmpty()){
                break;
            } else if(_userChoice.equals("A")) {
                _out.println("StockId, StockCode, StockName, CurrentPrice");
                
                MutuStocks[] _ss = this._obj.getAllStocks();

                for(MutuStocks _s : _ss){
                    _out.println(_s.toString());
                }
            } else if(_userChoice.equals("G")) {
                String[] stocksIdList = Arrays.stream(this._obj.getAllStocks())
                                                .mapToLong(o -> o.getSid())
                                                .mapToObj(String::valueOf)
                                                .toArray(String[]::new);

                String _sStockId = getUserInput("Stock Id : ", stocksIdList);

                long _stockId;
                if(_sStockId.isEmpty()){
                    break;
                } else {
                    _stockId = Long.parseLong(_sStockId);
                }                
                
                _out.println("StockId, StockCode, StockName, CurrentPrice");
                
                MutuStocks _s = this._obj.getStockByStockId(_stockId);

                _out.println(_s.toString());
            }
        }while(true);
    }//end of stockEnquiry()
    
    private void ordersEnquiry() throws IOException{
        _out.println("Queuing Orders enquiry, input an empty line to exit");

        do{
            String _userChoice = getUserInput("(A)ll / (B)uy side / (S)ell side", "[A|B|S]");
            if(_userChoice.isEmpty()){
                break;
            } else if(_userChoice.equals("A")) {
                String[] stocksIdList = Arrays.stream(this._obj.getAllStocks())
                                                .mapToLong(o -> o.getSid())
                                                .mapToObj(String::valueOf)
                                                .toArray(String[]::new);
                String _sStockId = getUserInput("Stock Id : ", stocksIdList);

                long _stockId;
                if(_sStockId.isEmpty()){
                    break;
                } else {
                    _stockId = Long.parseLong(_sStockId);
                }                  
                
                MutuOrders[] _allOrders = this._obj.getAllQueuingOrdersByStockId(_stockId);
                
                _out.println("OrderId, IsBuySide, Qty, Price, IsSettle, CreateAt, Stocks");
                
                for(MutuOrders _mo : _allOrders){
                    _out.println(_mo.toString());
                }
            } else if(_userChoice.equals("B")) {
                String[] stocksIdList = Arrays.stream(this._obj.getAllStocks())
                                                .mapToLong(o -> o.getSid())
                                                .mapToObj(String::valueOf)
                                                .toArray(String[]::new);
                String _sStockId = getUserInput("Stock Id : ", stocksIdList);
                
                long _stockId;
                if(_sStockId.isEmpty()){
                    break;
                } else {
                    _stockId = Long.parseLong(_sStockId);
                }                  
                
                MutuOrders[] _allOrders = this._obj.getBuySideQueueByStockId(_stockId);

                _out.println("OrderId, IsBuySide, Qty, Price, IsSettle, CreateAt, Stocks");
                
                for(MutuOrders _mo : _allOrders){
                    _out.println(_mo.toString());
                }
            } else if(_userChoice.equals("S")) {
                String[] stocksIdList = Arrays.stream(this._obj.getAllStocks())
                                                .mapToLong(o -> o.getSid())
                                                .mapToObj(String::valueOf)
                                                .toArray(String[]::new);
                String _sStockId = getUserInput("Stock Id : ", stocksIdList);

                long _stockId;
                if(_sStockId.isEmpty()){
                    break;
                } else {
                    _stockId = Long.parseLong(_sStockId);
                }  
                
                _out.println("transId,stockid,BuyOrSell,Qty,TransDtm");
                
                MutuOrders[] _allOrders = this._obj.getSellSideQueueByStockId(_stockId);

                _out.println("OrderId, IsBuySide, Qty, Price, IsSettle, CreateAt, Stocks");
                
                for(MutuOrders _mo : _allOrders){
                    _out.println(_mo.toString());
                }
            }
        }while(true);
    }//end of transEnquiry()

    private Orders getUserInputOrder() throws Exception{
        Orders rtn = new Orders();
        
        String _sIsBuy = getUserInput("(B)uy or (S)ell : ", "[B|S]");
        if(_sIsBuy.isEmpty()){
            return null;
        } else {
            rtn.setIsBuy(_sIsBuy.equals("B"));
        }

        String _sQty = getUserInput("Qty (1-99) : ", "^([1-9]|[1-9][0-9])$");
        if(_sQty.isEmpty()){
            return null;
        } else {
            rtn.setQty(Integer.parseInt(_sQty));
        }
        
        String _sPrice = getUserInput("Price (1.00 - 999.99) : ", "^([1-9]|[1-9][0-9].[0-9][0-9])$");
        if(_sPrice.isEmpty()){
            return null;
        } else {
            rtn.setPrice(Double.parseDouble(_sPrice));
        }        
        
        String[] stocksIdList = Arrays.stream(this._obj.getAllStocks())
                                        .mapToLong(o -> o.getSid())
                                        .mapToObj(String::valueOf)
                                        .toArray(String[]::new);
                                        
        String _sStockId = getUserInput("Stock Id : ", stocksIdList);
        
        if(_sStockId.isEmpty()){
            return null;
        } else {
            rtn.setStockId(Long.parseLong(_sStockId));
        }
        
        return rtn;
    }//end of getUserInputOrder()
    
    private String getUserInput(String _msg, String possibleValueRegx) throws IOException{
        String _str = "";

        do{
            _out.println(_msg);
            _str = _in.readLine();
            _str = (_str == null) ? "" : _str;

            if(_str.isEmpty() 
                    || possibleValueRegx.isEmpty() 
                    || Pattern.compile(possibleValueRegx, Pattern.CASE_INSENSITIVE).matcher(_str).matches()
            ){
                break;
            }
            
            _out.println("Possible Value : [" + possibleValueRegx + "]. Please try again.");
        }while(true);

        return _str;
    }//end of getUserInput(String _msg, String possibleValueRegx)    
    
    private String getUserInput(String _msg, String[] possibleValue) throws IOException{
        String _str = "";
        
        do{
            _out.println(_msg);
            _str = _in.readLine();
            _str = (_str == null) ? "" : _str;
            if(_str.isEmpty() || possibleValue.length == 0 || Arrays.asList(possibleValue).contains(_str)){
               break; 
            }
            _out.println("Possible Value : [" + String.join(",", possibleValue) + "]. Please try again.");
        }while(true);

        return _str;
    }//end of getUserInput(String _msg, String[] possibleValue)
}