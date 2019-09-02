package com.ylp.ccut.mapper;

import com.ylp.ccut.model.Demand;

import java.util.List;

public interface DemandMapper {
    int deleteByPrimaryKey(String iddemand);

    int insert(Demand record);

    int insertSelective(Demand record);

    Demand selectByPrimaryKey(String iddemand);

    int updateByPrimaryKeySelective(Demand record);

    int updateByPrimaryKey(Demand record);
    List<Demand> selectAll();
}