package com.somi.smartbadgechecker.data;

public class Mark {


    public final static String MARK_TIME = "time";
    public final static String MARK_USER = "user";
    public final static String MARK_TYPE = "type";


    private String time;
    private String user;
    private boolean isGoingIn;


    public Mark(String _time, String _user, boolean _isGoingIn) {

        time = _time;
        user = _user;
        isGoingIn = _isGoingIn;

    }//Constructor


    public String getTime() { return time; }//getTime

    public String getUser() { return user; }//getUser

    public boolean isGoingIn() { return isGoingIn; }//isGoingIn


    public void setTime(String _time) { time = _time; }//setTime

    public void setUser(String _user) { user = _user; }//setUser

    public void setGoingIn(boolean _isGoingIn) { isGoingIn = _isGoingIn; }//setGoingIn



}//Mark
