import com.ylp.update.ALMDemand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;



public class ALMDemandTest {
    private ALMDemand almDemand = new ALMDemand();
    protected static  Logger logger = LogManager.getLogger();
    @Test
    public void jsonObjectFromMap(){
        HashMap<String,String> map = new HashMap<>();
        map.put("userid","2916");
        String jsonPar = almDemand.getJsonParam(map);
        logger.info(jsonPar);
    }
    @Test
    public void getAllTyp() throws Exception {
        HashMap<String,String> map = new HashMap<>();
        map.put("userid","2916");
        String jsonPar = almDemand.getJsonParam(map);
        String url = "http://alm.ab.com/DevSuite/DetailTab/ProjectFrameService.asmx/GetWorkItemInnerWidgets";
        String responseStr = almDemand.getAllType(url,jsonPar);
        logger.info(responseStr);
    }
}
