package mutu.web.webportal.models;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import mutu.web.repos.models.Orders;
import mutu.web.repos.models.Stocks;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class OrdersViewModel {
    
    public OrdersViewModel(){
        
    }
    
    public OrdersViewModel(Orders _o){
        this.convertToViewModel(_o);
    }    
    
    private Long oid;
    
    @NotEmpty(message = "Stock Code must be provided!")
    @Length(min=1, max=20, message = "Stock code must between 1 to 20 characters")
    private String scode;

    @NotNull(message = "Buy or Sell must be provied!")
    private boolean isbuy;

    @NotNull(message = "Quantity must be provied!")
    private int qty;
    
    @NotNull(message = "Price must be provied!")
    @DecimalMin(value="0.0", message = "Price cannot be lower than ${value}")
    private double price;
    
    public Orders convert(Stocks _stocks){
        Orders _o = new Orders();
        
        _o.setOid(this.oid);
        _o.setIsbuy(this.isbuy);
        _o.setQty(this.qty);
        _o.setPrice(this.price);
        _o.setSid(_stocks);
        _o.setIssettle(false);
        
        return _o;
    }
    
    public void convertToViewModel(Orders _o){
        this.oid = _o.getOid();
        this.scode = _o.getSid().getScode();
        this.isbuy = _o.getIsbuy();
        this.qty = _o.getQty();
        this.price = _o.getPrice();
    }

    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }

    public String getScode() {
        return scode;
    }

    public void setScode(String scode) {
        this.scode = scode;
    }

    public boolean getIsbuy() {
        return isbuy;
    }

    public void setIsbuy(boolean isbuy) {
        this.isbuy = isbuy;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
