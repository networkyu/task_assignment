package com.ylp.ccut;

import com.ylp.ccut.mapper.UserMapper;
import com.ylp.ccut.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ReturnMessage add(@RequestParam(value="iduser") String iduser,
                                @RequestParam(value="name") String name,
                                @RequestParam(value="password",required = false) String password,
                                @RequestParam(value="email",required = false) String email,
                                @RequestParam(value="company",required = false) String company,
                                @RequestParam(value="tel",required = false) String tel,
                                @RequestParam(value="profession",required = false) String profession){
        ReturnMessage returnMessage = ReturnMessage.getSuccessInstance();
        User user = new User();
        user.setIduser(iduser);
        user.setName(name);
        if (password != null && !password.equals("")){
            user.setPassword(Safe.md5(password));
        }
        if (email != null && !email.equals("")){
            user.setEmail(email);
        }
        if (company != null && !company.equals("")){
            user.setCompany(company);
        }
        if (tel != null && !tel.equals("")){
            user.setTel(tel);
        }
        if (profession != null && !profession.equals("")){
            user.setProfession(profession);
        }
        User selectUser = userMapper.selectByPrimaryKey(user.getIduser());
        if(selectUser != null){
            returnMessage.result = false;
            returnMessage.message = "人员已存在";
            return returnMessage;
        }
        if (userMapper.insertSelective(user) <= 0){
            returnMessage.result = false;
            returnMessage.message = "人员已存在";
        }
        return returnMessage;
    }
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public ReturnMessage user(){
        ArrayList<User> users = new ArrayList<>();
        users = userMapper.selectAll();
        ReturnMessage returnMessage = ReturnMessage.getSuccessInstance();
        returnMessage.data = getReturnArrayByList(users,1,"User");
        return  returnMessage;
    }

    /**
     * 根据版本返回相应的数组
     * @param arrayList
     * @param version
     * @return
     */
    public String[][] getReturnArrayByList(ArrayList<User> arrayList,Integer version,String type){
        if(arrayList == null){
            return null;
        }
        Integer rowCont = arrayList.size();

        String[][] rows = new String[rowCont][];
        if(version == 1){
            if(type.equals("User")){
                for(int i = 0;i<rowCont;i++){
                    User user = (User) arrayList.get(i);
                    UserView userView = new UserView();

                    BeanUtils.copyProperties(user,userView);
                    Integer columnCont = 6;
                    String[] columns = new String[columnCont];
                    columns[0] = userView.getIduser();
                    columns[1] = userView.getName();
                    columns[2] = userView.getEmail();
                    columns[3] = userView.getTel();
                    columns[4] = userView.getCompany();
                    columns[5] = userView.getProfession();
                    rows[i] = columns;
                }
            }
        }
        return rows;
    }
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public ReturnMessage delete(@RequestParam(value="iduser") String iduser){
        if(userMapper.deleteByPrimaryKey(iduser) > 0){
            return ReturnMessage.getSuccessInstance();
        } else {
            return ReturnMessage.getFailureInstance();
        }
    }
}
