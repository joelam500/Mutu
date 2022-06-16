package mutu.web.webportal.common;

public class MutuException extends Exception {
    
    public MutuException(String _errMsg, String _rtnPath){
        this.errMsg = _errMsg;
        this.rtnPath = _rtnPath;
    }
    
    private String errMsg;
    
    private String rtnPath;

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getRtnPath() {
        return rtnPath;
    }

    public void setRtnPath(String rtnPath) {
        this.rtnPath = rtnPath;
    }
}
