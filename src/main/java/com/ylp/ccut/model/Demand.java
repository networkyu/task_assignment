package com.ylp.ccut.model;

import java.util.Date;

public class Demand {
    private String iddemand;

    private String topic;

    private Integer state;

    private String developer;

    private String assigner;

    private Date date;

    private Integer type;

    public String getIddemand() {
        return iddemand;
    }

    public void setIddemand(String iddemand) {
        this.iddemand = iddemand == null ? null : iddemand.trim();
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic == null ? null : topic.trim();
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
        this.developer = developer == null ? null : developer.trim();
    }

    public String getAssigner() {
        return assigner;
    }

    public void setAssigner(String assigner) {
        this.assigner = assigner == null ? null : assigner.trim();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}