package mutu.web.webportal.controllers;

import java.util.Optional;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import mutu.web.repos.StocksRepo;
import mutu.web.repos.models.Stocks;
import mutu.web.webportal.common.MutuException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value={"/stocks"})
public class StocksController {
    
    @Resource
    private StocksRepo _repo;
    
    private final String subpath = "/stocks/";   
    
    @RequestMapping(method=RequestMethod.GET, value={"","index"})
    public String index(ModelMap m){
        m.addAttribute("allStocks", _repo.findAll());
        return subpath + "index";
    }

    @RequestMapping(method=RequestMethod.GET, value="details/{sid}")
    public String details(ModelMap m, @PathVariable("sid") Long sid) throws MutuException{
        Optional<Stocks> stockObj = _repo.findById(sid);
        if(!stockObj.isPresent()){
            throw new MutuException("Stocks with id [" + sid + "] not found!", subpath + "index");
        }
        m.addAttribute("stocksObj", stockObj.get());
        return subpath + "details";
    }  

    @ExceptionHandler(value = MutuException.class)
    public ModelAndView customError(HttpServletRequest req, MutuException me){
        ModelAndView mv = new ModelAndView();
        mv.addObject("rtnPath", me.getRtnPath());
        mv.addObject("errMsg", me.getErrMsg());
        mv.setViewName("/common/custError");
        
        return mv;
    }
}
