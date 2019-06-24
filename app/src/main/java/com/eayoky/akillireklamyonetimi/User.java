package com.eayoky.akillireklamyonetimi;
public class User {

    private String UserName;
    private String UserPass;
    public User(){

    }

    public User(String userName, String userPass){

        this.setUserName (userName);
        this.setUserPass (userPass);
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserPass() {
        return UserPass;
    }

    public void setUserPass(String userPass) {
        UserPass = userPass;
    }

}