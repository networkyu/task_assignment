package com.ylp.ccut.mapper;

import com.ylp.ccut.model.User;

public interface UserMapper {
    int deleteByPrimaryKey(String iduser);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String iduser);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}