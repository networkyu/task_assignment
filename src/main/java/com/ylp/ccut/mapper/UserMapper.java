package com.ylp.ccut.mapper;

import com.ylp.ccut.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {
    int deleteByPrimaryKey(String iduser);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String iduser);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}