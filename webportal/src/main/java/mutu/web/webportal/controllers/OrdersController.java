package mutu.web.webportal.controllers;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import mutu.web.messagehub.MessageSender;
import mutu.web.repos.OrdersRepo;
import mutu.web.repos.StocksRepo;
import mutu.web.repos.models.Orders;
import mutu.web.repos.models.Stocks;
import mutu.web.webportal.common.MutuException;
import mutu.web.webportal.common.OrdersValidator;
import mutu.web.webportal.models.OrdersViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/orders")
public class OrdersController {
    
    @Resource
    private OrdersRepo _repo;
    
    @Resource
    private StocksRepo _stocksRepo;
    
    @Autowired
    private OrdersValidator _validator;
    
    @Autowired
    private MessageSender messageSender;    
    
    private final String subpath = "/orders/";
    
    @RequestMapping(method=RequestMethod.GET, value={"","index"})
    public String index(ModelMap m){
        m.addAttribute("allOrders", _repo.sortedOrders(true));
        return subpath + "index";
    }    
    
    @RequestMapping(method=RequestMethod.GET, value="unsettle")
    public String unsettledOrders(ModelMap m){
        m.addAttribute("allOrders", _repo.sortedOrders(false));
        return subpath + "unsettle";
    }
    
    @RequestMapping(method=RequestMethod.GET, value="create")
    public String create(ModelMap m){
        m.addAttribute("newOrders", new OrdersViewModel());
        return subpath + "create";
    }
    
    @RequestMapping(method=RequestMethod.POST, value="create")
    public String create(ModelMap m, 
                        @ModelAttribute("newOrders") @Valid OrdersViewModel _ordersVM,
                        BindingResult _results) 
            throws ParseException{
        
        _validator.validate(_ordersVM, _results);
        
        if(_results.hasErrors()){
            m.addAttribute("newOrders", _ordersVM);
            return subpath + "create";
        }
        
        Stocks _stocks = _stocksRepo.getStocksByCode(_ordersVM.getScode());
        Orders newOrders = _repo.save(_ordersVM.convert(_stocks));
        
        matchOrders(newOrders);
        
        return "redirect:" + subpath + "unsettle";
    } 
    
    @RequestMapping(method=RequestMethod.GET, value="details/{oId}")
    public String details(ModelMap m, @PathVariable("oId") Long _oId) throws MutuException{
        Optional<Orders> _orders = _repo.findById(_oId);
        if(!_orders.isPresent()){
            throw new MutuException("Orders with id [" + _oId + "] not found!", subpath + "index");
        }
        m.addAttribute("orders", _orders.get());
        return subpath + "details";
    }
    
    @RequestMapping(method=RequestMethod.GET, value="edit/{oId}")
    public String edit(ModelMap m, @PathVariable("oId") Long _oId) throws MutuException{
        Optional<Orders> _orders = _repo.findById(_oId);
        if(!_orders.isPresent()){
            throw new MutuException("Orders with id [" + _oId + "] not found!", subpath + "index");
        }
        if(_orders.get().getIssettle()){
            throw new MutuException("Orders with id [" + _oId + "] is settled! Cannot be edited", subpath + "index");
        }        
        m.addAttribute("editOrders", new OrdersViewModel(_orders.get()));
        return subpath + "edit";
    }
    
    @RequestMapping(method=RequestMethod.POST, value="edit/{oId}")
    public String edit(ModelMap m, 
                        @PathVariable("oId") Long _oId,
                        @ModelAttribute("editOrders") @Valid OrdersViewModel _ordersVM,
                        BindingResult _results) 
            throws ParseException{
        
        _validator.validate(_ordersVM, _results);
        
        if(_results.hasErrors()){
            m.addAttribute("editOrders", _ordersVM);
            return subpath + "edit";
        }
        
        Stocks _stocks = _stocksRepo.getStocksByCode(_ordersVM.getScode());
        Orders updatedOrders = _repo.save(_ordersVM.convert(_stocks));
        
        matchOrders(updatedOrders);
        
        return "redirect:" + subpath + "unsettle";
    }    

    @RequestMapping(value="delete/{oId}", method=RequestMethod.GET)
    public String delete(@PathVariable("oId") Long _oId) throws MutuException{
        Optional<Orders> _orders = _repo.findById(_oId);
        if(!_orders.isPresent()){
            throw new MutuException("Orders with id [" + _oId + "] not found!", subpath + "index");
        }

        if(_orders.get().getIssettle()){
            throw new MutuException("Orders with id [" + _oId + "] is settled! Cannot be deleted", subpath + "index");
        }
        _repo.deleteById(_oId);
        return "redirect:" + subpath + "unsettle";
    }   
    
    @ExceptionHandler(value = MutuException.class)
    public ModelAndView customError(HttpServletRequest req, MutuException me){
        ModelAndView mv = new ModelAndView();
        mv.addObject("rtnPath", me.getRtnPath());
        mv.addObject("errMsg", me.getErrMsg());
        mv.setViewName("/common/custError");
        return mv;
    }
    
    private void matchOrders(Orders _thisOrder){
        boolean isBuy = _thisOrder.getIsbuy();
        String scode = _thisOrder.getSid().getScode();
        int qty = _thisOrder.getQty();
        double price = _thisOrder.getPrice();
        
        List<Orders> _orders = _repo.findMatchOrders(isBuy, scode, qty, price);
        if(_orders != null && _orders.size() > 0){
            //update this order
            _thisOrder.setIssettle(true);
            _thisOrder.setCreateAt(new Date());
            _repo.save(_thisOrder);
            //update paired order
            Orders _pairedOrders = _orders.get(0);
            _pairedOrders.setIssettle(true);
            _pairedOrders.setCreateAt(new Date());
            _repo.save(_pairedOrders);
            //update the stock price
            Stocks _s = _thisOrder.getSid();
            _s.setScurrentprice(price);
            _stocksRepo.save(_s);
        }
        
        messageSender.Send(isBuy);
    }
}