package com.ylp.ccut.mapper;

import com.ylp.ccut.model.User;

import java.util.ArrayList;
import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(String iduser);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String iduser);
    ArrayList<User> selectAll();

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}