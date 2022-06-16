package mutu.web.webportal.common;

import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.view.InternalResourceView;

public class BasicView extends InternalResourceView{
    
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response) throws Exception {

        exposeModelAsRequestAttributes(model, request);

        String dispatcherPath = prepareForRendering(request, response);
        response.addHeader("Content-Type", "text/html;charset=UTF-8");
        request.setAttribute("content", dispatcherPath);
        
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/common/base.jsp");
        
        if (requestDispatcher != null) {
            requestDispatcher.include(request, response);
        }//end of checking null
    }//end of renderMergedOutputModel()
}
