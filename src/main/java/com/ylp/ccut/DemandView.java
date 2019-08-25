package com.ylp.ccut;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
@ToString
public class DemandView {
    //需求号
    private String iddemand;
    //需求描述
    private String topic;
    //需求状态
    private Integer state;
    //开发者账户
    private String developer;
    // 分配者
    private String assigner;
//    分配日期
    private String date;
//需求状态
    private Integer type;
    //开发者姓名
    private String developerName;

    public String getIddemand() {
        return iddemand;
    }

    public void setIddemand(String iddemand) {
        this.iddemand = iddemand;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getAssigner() {
        return assigner;
    }

    public void setAssigner(String assigner) {
        this.assigner = assigner;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDeveloperName() {
        return developerName;
    }

    public void setDeveloperName(String developerName) {
        this.developerName = developerName;
    }
}
