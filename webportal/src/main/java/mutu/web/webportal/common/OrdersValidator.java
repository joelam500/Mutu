package mutu.web.webportal.common;

import mutu.web.repos.StocksRepo;
import mutu.web.repos.models.Stocks;
import mutu.web.webportal.models.OrdersViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class OrdersValidator implements Validator {

    @Autowired
    private StocksRepo repo;
    
    @Override
    public boolean supports(Class<?> type) {
        return OrdersViewModel.class.equals(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        OrdersViewModel orderVm = (OrdersViewModel)o;
        
        String scode = orderVm.getScode();
        Stocks _s = repo.getStocksByCode(scode);
        if(_s == null){
            errors.rejectValue("scode", "", "Stock Code [" + scode + "] not found!");
        }
    }
    
}
