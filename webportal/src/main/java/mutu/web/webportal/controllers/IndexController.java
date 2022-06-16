package mutu.web.webportal.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/")
public class IndexController {
    
    private final String subpath = "/common/";   
    
    @RequestMapping(method=RequestMethod.GET, value={"/","/index"})
    public String index(ModelMap m){
        return subpath + "index";
    }
    
    @RequestMapping(method=RequestMethod.GET, value="/error")
    public String error(ModelMap m){
        return subpath + "error";
    }
}
