package com.ylp.ccut;

import java.util.ArrayList;
import java.util.List;

import com.ylp.ccut.mapper.DemandMapper;
import com.ylp.ccut.mapper.UserMapper;
import com.ylp.ccut.model.Demand;
import com.ylp.ccut.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@CrossOrigin
public class DemandController {
    @Autowired
   private UserMapper userMapper ;
    @Autowired
    private DemandMapper demandMapper;
    @RequestMapping("/test")
    public String  greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return  "Hello,world";
    }
    @RequestMapping("/all")
    public ReturnMessage getDemandView(){
        ReturnMessage returnMessage = ReturnMessage.getSuccessInstance();
        List<Demand> demands = new ArrayList<Demand>();
        demands = demandMapper.selectAll();
        if (demands == null){
            returnMessage.code = "000";
            returnMessage.message = "暂无数据";
            return returnMessage;
        }
        DemandView[] demandViews = new DemandView[demands.size()];
        for (int i = 0;i<demands.size();i++) {
            Demand demand = demands.get(i);
            String userId = demand.getDeveloper();
            User user = userMapper.selectByPrimaryKey(userId);
            String developerName = "";
            if (user != null){
                developerName = user.getName();
            }
            DemandView demandView = new DemandView();
            BeanUtils.copyProperties(demand,demandView);
            demandView.setDeveloperName(developerName);
            demandViews[i] = demandView;
        }
        returnMessage.data = demandViews;
        return returnMessage;
    }
    public ReturnMessage addDemand(){
        ReturnMessage returnMessage = ReturnMessage.getSuccessInstance();
        return returnMessage;
    }
}

