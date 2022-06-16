package mutu.web.repos;

import java.util.List;
import mutu.web.repos.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrdersRepo extends JpaRepository<Orders, Long> {
    
    @Query("select o from Orders o where o.issettle = ?1 and ((?1 = false) or (?1 = true and o.issettle = ?1 and o.isbuy = true)) order by o.oid desc")
    List<Orders> sortedOrders(boolean isSettle);    
    
    /***
     * Find all unsettled order that match exactly with stock code, qty and price
     * @param isBuy 1 : buy side, 0 : sell side
     * @param scode
     * @param qty
     * @param price
     * @return 
     */
    @Query("select o from Orders o ,Stocks s where o.sid = s and o.issettle = false and o.isbuy <> ?1 and s.scode = ?2 and o.qty = ?3 and o.price = ?4 order by o.createAt asc")
    List<Orders> findMatchOrders(boolean isBuy, String scode, int qty, double price);

}
