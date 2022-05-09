package edu.hitsz.dao;

public class User {
    private String userName;
    private int score;
    private String degree;
    private String time;

    public User(String userName,int score,String time,String degree)
    {
        this.userName = userName;
        this.score = score;
        this.degree = degree;
        this.time = time;
    }

    public String getUserName(){
        return this.userName;
    }

    public void setUserName(String userName){
        this.userName=userName;
    }

    public int getScore(){
        return this.score;
    }

    public void setScore(int score){
        this.score = score;
    }

    public String getDegree(){
        return this.degree;
    }

    public void setDegree(String degree){
        this.degree = degree;
    }

    public String getTime(){
        return this.time;
    }

    public void setTime(String time){
        this.time = time;
    }
}
