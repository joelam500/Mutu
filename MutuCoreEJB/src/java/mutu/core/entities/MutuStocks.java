package mutu.core.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "stocks")
@Cacheable(false)
public class MutuStocks  implements Serializable {

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
    private List<MutuOrders> ordersList;

    public MutuStocks() {
    }

    public MutuStocks(long sid) {
        this.sid = sid;
    }

    public MutuStocks(long sid, String scode, String sname, double scurrentprice) {
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
    public List<MutuOrders> getMutuOrdersList() {
        return ordersList;
    }

    public void setMutuOrdersList(List<MutuOrders> ordersList) {
        this.ordersList = ordersList;
    }

    @Override
    public String toString() {
        return "MutuStocks{" + "sid=" + sid + ", scode=" + scode + ", sname=" + sname + ", scurrentprice=" + scurrentprice + '}';
    }
}