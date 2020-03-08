package com.aki.purchaseforward.pojo;

/**
 * @AUTUOR QXW
 * @CREATE 2020/2/29 13:10
 */

//这是一个用来临时存储用户信息的javabean
//
public class User {
    //create table good(
    //id  int primary key not null auto_increment,
    //username varchar(10) not null,
    //pwd varchar(50) not null,
    //rule int(1) not null,
    //email varchar(30),
    //qq varchar(30)
    private int id;
    private String username;
    private String pwd;
    private int rule;
    private String email;
    private String qq;

    public User(){}

    public User(int id, String username, String pwd, int rule, String email, String qq) {
        this.id = id;
        this.username = username;
        this.pwd = pwd;
        this.rule = rule;
        this.email = email;
        this.qq = qq;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getRule() {
        return rule;
    }

    public void setRule(int rule) {
        this.rule = rule;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }


   /* //重写toString方法,方便我们查询用户信息
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", pwd='" + pwd + '\'' +
                ", rule=" + rule +
                ", email='" + email + '\'' +
                ", qq='" + qq + '\'' +
                '}';
    }*/
}
