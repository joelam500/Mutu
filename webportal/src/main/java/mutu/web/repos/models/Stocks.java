package mutu.web.repos.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "stocks")
public class Stocks implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SID")
    private long sid;
    
    @Basic(optional = false)
    @Column(name = "SCODE")
    private String scode;
    
    @Basic(optional = false)
    @Column(name = "SNAME")
    private String sname;
    
    @Min(value=0L)
    @Basic(optional = false)
    @Column(name = "SCURRENTPRICE")
    private double scurrentprice;
    
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "sid")
    private List<Orders> ordersList;
    
    @Transient
    private double turnover;
    
    @Transient
    private List<Orders> settledOrdersList;

    public Stocks() {
    }

    public Stocks(long sid) {
        this.sid = sid;
    }

    public Stocks(long sid, String scode, String sname, double scurrentprice) {
        this.sid = sid;
        this.scode = scode;
        this.sname = sname;
        this.scurrentprice = scurrentprice;
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public String getScode() {
        return scode;
    }

    public void setScode(String scode) {
        this.scode = scode;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public double getScurrentprice() {
        return scurrentprice;
    }

    public void setScurrentprice(double scurrentprice) {
        this.scurrentprice = scurrentprice;
    }

    @XmlTransient
    public List<Orders> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }

    public double getTurnover() {
        return turnover;
    }

    public void setTurnover(double turnover) {
        this.turnover = turnover;
    }
    
    @PostLoad
    private void CalculateTurnover(){
        double _turn = 0.0;
        if(this.ordersList != null && this.ordersList.size() > 0){
            /* get the sum of either buy or sell transactions as it must be a pair */
            for(Orders o : this.ordersList){
                if(o.getIsbuy() && o.getIssettle()){
                    if(this.settledOrdersList == null){
                        this.settledOrdersList = new ArrayList<>();
                    }
                    this.settledOrdersList.add(o);
                    
                    _turn += o.getPrice() * o.getQty();
                }
                

            }//end of looping all orders
        }//end of checking empty orders list
        this.turnover = _turn;
    }

    public List<Orders> getSettledOrdersList() {
        return settledOrdersList;
    }

}