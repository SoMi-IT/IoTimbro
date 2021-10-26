package com.somi.smartbadgechecker.data;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class JsonUserDataManager {



    public static ArrayList<User> getUsersFromString(String string) {

        ArrayList<User> users = new ArrayList<>();

        try {


            JSONArray jsonArray = new JSONArray(string);

            for(int i = 0; i<jsonArray.length(); i++) {

                User user;
                JSONObject currentJsonObject;
                currentJsonObject = jsonArray.getJSONObject(i);
                user = getUserFromJson(currentJsonObject);

                if(user != null) users.add(user);

            }

        } catch (JSONException e) {
            e.printStackTrace();
            return users;
        }

        return users;

    }//getUsersFromString


    public static User getUserFromJson(JSONObject jsonObject){

        User user;

        try {

            String id = jsonObject.getString(User.USER_ID);
            boolean isInside = jsonObject.getBoolean(User.USER_STATUS);
            boolean isAlertOn = jsonObject.getBoolean(User.USER_ALERT);
            user = new User(id,  isInside, isAlertOn);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return user;

    }//getUserFromJson


    public static String getStringFromUsers(ArrayList<User> users){

        JSONArray jsonArray = new JSONArray();

        for(int i = 0; i<users.size(); i++) {

            if(getJsonObjectFromUser(users.get(i)) != null) jsonArray.put(getJsonObjectFromUser(users.get(i)));

        }

        return jsonArray.toString();

    }//getStringFromUsers


    public static JSONObject getJsonObjectFromUser(User user){

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put(User.USER_ID, user.getId());
            jsonObject.put(User.USER_STATUS, user.isInside());
            jsonObject.put(User.USER_ALERT, user.isAlertOn());

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return jsonObject;

    }//getJsonObjectFromUser

}//JsonDataManager


