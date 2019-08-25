package com.ylp.ccut.mapper;

import com.ylp.ccut.model.Demand;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface DemandMapper {
    int deleteByPrimaryKey(String iddemand);

    int insert(Demand record);

    int insertSelective(Demand record);

    Demand selectByPrimaryKey(String iddemand);
    List<Demand> selectAll();
    int updateByPrimaryKeySelective(Demand record);

    int updateByPrimaryKey(Demand record);
}