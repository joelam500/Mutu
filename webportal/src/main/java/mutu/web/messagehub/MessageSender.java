package mutu.web.messagehub;

public class MessageSender {

    public void Send(boolean isBuy){
        try {
            try { // Call Web Service Operation
                StocksSvc service = new StocksSvc();
                StocksWebAPI port = service.getStocksWebAPIPort();
                port.orderCreated(isBuy);
            } catch (Exception ex) {
                // TODO handle custom exceptions here
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
