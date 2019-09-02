package com.ylp.ccut;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.ylp.ccut.mapper.DemandMapper;
import com.ylp.ccut.mapper.UserMapper;
import com.ylp.ccut.model.Demand;
import com.ylp.ccut.model.User;
import com.ylp.ccut.util.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class DemandController {
    @Autowired
   private UserMapper userMapper ;
    @Autowired
    private DemandMapper demandMapper;
    @Autowired
    private  DateUtil dateUtil;
    @RequestMapping("/test")
    public String  greeting(@RequestParam(value="name", defaultValue="World") String name)
    {
        generatorDemand();
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
            //转换时间格式
            if(demand.getDate() != null) {
                // 省略时分秒
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String s = sdf.format(demand.getDate());
                demandView.setDate(s);
            } else {
                demandView.setDate("");
            }
            //设置完成时间
            if(demand.getCompletedate() != null) {
                // 省略时分秒
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String s = sdf.format(demand.getCompletedate());
                demandView.setCompletedate(s);
            } else {
                demandView.setCompletedate("");
            }
            // 如果分配者为空，则默认为空。
            if(demand.getAssigner() == null){
                demandView.setAssigner("");
            }
            demandViews[i] = demandView;
        }
        int columnValue = 8;
        String[][] resultDatas = new String[demandViews.length][columnValue];
        for(int i = 0;i < demandViews.length;i++){
            String[] resultView = new String[columnValue];
            resultView[0] = demandViews[i].getIddemand();
            resultView[1] = demandViews[i].getTopic();
            resultView[2] = demandViews[i].getDeveloperName();
            if(demandViews[i].getState() == 0){
                resultView[3] = "待分配";
            }
            if(demandViews[i].getState() == 1){
                resultView[3] = "开发中";
            }
            if(demandViews[i].getState() == 2){
                resultView[3] = "已完成";
            }
            if(demandViews[i].getState() == 3){
                resultView[3] = "已取消";
            }
//            resultView[3] = demandViews[i].getState().toString();
            resultView[4] = demandViews[i].getType().toString();
//            if(demandViews[i].getDate() == null ||
//                    demandViews[i].getDate().equals("")){
//                demandViews[i].setDate("");
//            }
            resultView[5] = demandViews[i].getDate();
            resultView[6] = demandViews[i].getAssigner();
            resultView[7] = demandViews[i].getCompletedate();
            resultDatas[i] = resultView;
        }

        returnMessage.data = resultDatas;
        return returnMessage;
    }
    @RequestMapping("/add")
    public ReturnMessage addDemand(@RequestParam(value="demandno") String demandno,
                                   @RequestParam(value = "topic") String topic,
                                   @RequestParam(value = "state") String state,
                                   @RequestParam(value = "type") String type,
                                   @RequestParam(value = "assigner",required = false) String assignment,
                                   @RequestParam(value = "developer",required = false) String development,
                                   @RequestParam(value = "date",required = false) String date){
        insertDemand( demandno,
                 topic,
                 state,
                 type,
                 assignment,
                 development,
                 date);
        ReturnMessage returnMessage = ReturnMessage.getSuccessInstance();
        return returnMessage;
    }

    /**
     * 模型方法，插入需求
     * @param demandno
     * @param topic
     * @param state
     * @param type
     * @param assignment
     * @param development
     * @param date
     * @return
     */
    private boolean insertDemand(String demandno,
                                 String topic,
                                 String state,
                                 String type,
                                 String assignment,
                                 String development,
                                 String date){
        Demand demand = new Demand();
        demand.setIddemand(demandno);
        demand.setTopic(topic);
        demand.setState(Integer.parseInt(state));
        demand.setType(Integer.parseInt(type));


        if (development != null && development != ""){
            demand.setDeveloper(development);
        }
        if (assignment != null && assignment != ""){
            demand.setAssigner(assignment);
        }
        if (date != null && date != ""){
            demand.setDate(dateUtil.dateFromString(date));
        }
        Integer result = demandMapper.insertSelective(demand);
        if (result > 0 ){
            return true;
        }
        return false;
    }

    /**
     * 测试数据生成函数函数。
     */
    public void generatorDemand(){
        String demandno = "sx-";
        String topic = "";
        String state = "";
        String type = "";
        String assignment = "";
        String development = "";
        String date = "";
        String topics = "银行信用以及其他金融机构以货币形式向客户提供的信用，它是以银行作为中介金融机构所进行的资金融通形式。 [1] \n" +
                "（1）间接性。在间接融资中，资金需求者和资金初始供应者之间不发生直接借贷关系；资金需求者和初始供应者之间由金融中介发挥桥梁作用。资金初始供应者与资金需求者只是与金融中介机构发生融资关系。\n" +
                "（2）相对的集中性。间接融资通过金融中介机构进行。在多数情况下，金融中介并非是对某一个资金供应者与某一个资金需求者之间一对一的对应性中介；而是一方面面对资金供应者群体，另一方面面对资金需求者群体的综合性中介，由此可以看出，在间接融资中，金融机构具有融资中心的地位和作用。\n" +
                "（3）信誉的差异性较小。由于间接融资相对集中于金融机构，世界各国对于金融机构的管理一般都较严格，金融机构自身的经营也多受到相应稳健性经营管理原则的约束，加上一些国家还实行了存款保险制度，因此，相对于直接融资来说，间接融资的信誉程度较高，风险性也相对较小，融资的稳定性较强。\n" +
                "（4）全部具有可逆性。通过金融中介的间接融资均属于借贷性融资，到期均必须返还，并支付利息，具有可逆性。\n" +
                "（5）融资的主动权主要掌握在金融中介手中。在间接融资中，资金主要集中于金融机构，资金贷给谁不贷给谁由金融中介决定。";
        for (int i = 0; i < 1000; i++) {
            Random random = new Random();
            random.setSeed(i);
            int randomInt = random.nextInt(460);
            int randomState = random.nextInt(10);
            int randomType = random.nextInt(20);

            insertDemand(demandno + i,topic + topics.substring(randomInt,randomInt + 6),
                    state+randomState,type+randomType,
                    assignment + topics.substring(randomInt,randomInt + 3),
                    "","");
        }
    }
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public ReturnMessage delete(@RequestParam(value="iddemand") String iddemand){
        ReturnMessage returnMessage = ReturnMessage.getSuccessInstance();
        if (iddemand.equals("")){
            returnMessage.message = "需求号为空，不能删除";
        }
        if(demandMapper.deleteByPrimaryKey(iddemand) > 0){
            //删除成功
            returnMessage.result = true;
        }
        return returnMessage;
    }
    @RequestMapping(value = "/complete",method = RequestMethod.POST)
    public ReturnMessage complete(@RequestParam(value="iddemand") String iddemand){
        Demand demand = new Demand();
        demand.setState(2);
        demand.setIddemand(iddemand);
        demand.setCompletedate(dateUtil.getSystemDate());
        if(demandMapper.updateByPrimaryKeySelective(demand)>0){
            return ReturnMessage.getSuccessInstance();
        } else {
            ReturnMessage returnMessage = ReturnMessage.getFailureInstance();
            returnMessage.message = "需求号错误";
            return returnMessage;
        }
    }
    @RequestMapping(value = "/cancel",method = RequestMethod.POST)
    public ReturnMessage cancelAssignment(@RequestParam(value="iddemand") String iddemand){
        Demand demand = demandMapper.selectByPrimaryKey(iddemand);
        if (demand != null) {
            demand.setState(0);
            demand.setIddemand(iddemand);
            demand.setCompletedate(null);
            //分配者，开发人员，分配时间
            demand.setAssigner(null);
            demand.setDeveloper(null);
            demand.setDate(null);
            if(demandMapper.updateByPrimaryKey(demand)>0){
                return ReturnMessage.getSuccessInstance();
            } else {
                ReturnMessage returnMessage = ReturnMessage.getFailureInstance();
                returnMessage.message = "需求号错误";
                return returnMessage;
            }
        }
        return ReturnMessage.getFailureInstance();
    }



}

