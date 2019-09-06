package com.ylp.ccut;

import com.ylp.ccut.model.User;

public class UserView {
    private String iduser;

    private String name;

    private String password;

    private String email;

    private String tel;

    private String company;

    private String profession;

    public UserView(){
        if(password == null){
            password = "";
        }
        if(email == null){
            email = "";
        }
        if(tel == null){
            tel = "";
        }
        if(company == null){
            company = "";
        }
        if(profession == null){
            profession = "";
        }
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public static void main(String[] args) {
        UserView userView = new UserView();
        System.out.println(userView.iduser);
    }

}
