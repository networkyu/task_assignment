package com.ylp.ccut;

import com.ylp.ccut.mapper.UserMapper;
import com.ylp.ccut.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @RequestMapping(value = "/adduser",method = RequestMethod.POST)
    public ReturnMessage delete(@RequestParam(value="iduser") String iduser,
                                @RequestParam(value="name") String name,
                                @RequestParam(value="password",required = false) String password,
                                @RequestParam(value="email",required = false) String email,
                                @RequestParam(value="company",required = false) String company,
                                @RequestParam(value="tel",required = false) String tel){
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
}
