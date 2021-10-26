package com.somi.smartbadgechecker.data;

public class User {


    public final static String USER_ID = "id";
    public final static String USER_STATUS = "status";
    public final static String USER_ALERT = "alert";

    private String id;
    private boolean isInside;
    private boolean isAlertOn;


    public User(String _id, boolean _isInside, boolean _isAlertOn) {

        id = _id;
        isInside = _isInside;
        isAlertOn = _isAlertOn;

    }//Constructor


    public String getId() { return id; }//getId

    public boolean isInside() { return isInside; }//isInside

    public boolean isAlertOn() { return isAlertOn; }//isAlertOn


    public void setId(String _id) { id = _id; }//setId

    public void setInside(boolean _isInside) { isInside = _isInside; }//setInside

    public void setAlertOn(boolean _isAlertOn) { isAlertOn = _isAlertOn; }//setAlertOn

}//User
