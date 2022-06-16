package mutu.core.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;

@Entity
@Table(name = "orders")
@Cacheable(false)
public class MutuOrders  implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "OID")
    private Long oid;
    
    @Basic(optional = false)
    @Column(name = "ISBUY")
    private boolean isbuy;
    
    @Basic(optional = false)
    @Column(name = "QTY")
    private int qty;
    
    @Min(value=0L)
    @Basic(optional = false)
    @Column(name = "PRICE")
    private double price;
    
    @Basic(optional = false)
    @Column(name = "ISSETTLE")
    private boolean issettle;
    
    @Basic(optional = false)
    @Column(name = "CREATE_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;
    
    @JoinColumn(name = "SID", referencedColumnName = "SID")
    @ManyToOne(optional = false)
    private MutuStocks sid;

    public MutuOrders() {
    }

    public MutuOrders(Long oid) {
        this.oid = oid;
    }
    
    public MutuOrders(boolean isbuy, int qty, double price, boolean issettle, Date createAt, MutuStocks sid) {
        this.isbuy = isbuy;
        this.qty = qty;
        this.price = price;
        this.issettle = issettle;
        this.createAt = createAt;
        this.sid = sid;
    }    

    public MutuOrders(Long oid, boolean isbuy, int qty, double price, boolean issettle, Date createAt) {
        this.oid = oid;
        this.isbuy = isbuy;
        this.qty = qty;
        this.price = price;
        this.issettle = issettle;
        this.createAt = createAt;
    }

    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
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

    public boolean getIssettle() {
        return issettle;
    }

    public void setIssettle(boolean issettle) {
        this.issettle = issettle;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public MutuStocks getSid() {
        return sid;
    }

    public void setSid(MutuStocks sid) {
        this.sid = sid;
    }
    
    @PrePersist
    @PreUpdate
    public void updateCreateAt(){
        this.createAt = new Date();
    }

    @Override
    public String toString() {
        return "MutuOrders{" + "oid=" + oid + ", isbuy=" + isbuy + ", qty=" + qty + ", price=" + price + ", issettle=" + issettle + ", createAt=" + createAt + ", sid={" + sid.toString() + "}}";
    }
}
