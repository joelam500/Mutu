package mutu.web.repos;

import java.util.Optional;
import mutu.web.repos.models.Stocks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StocksRepo extends JpaRepository<Stocks, Long> {

    @Query("SELECT s FROM Stocks s WHERE s.scode = ?1")
    Stocks getStocksByCode(String scode);
    
}
