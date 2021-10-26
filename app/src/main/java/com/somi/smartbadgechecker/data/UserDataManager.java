package com.somi.smartbadgechecker.data;

import android.app.Activity;
import android.util.Log;

import com.somi.smartbadgechecker.R;
import com.somi.smartbadgechecker.util.SharedPreferencesManager;

import java.util.ArrayList;

public class UserDataManager {

    public ArrayList<User> getStoredUsers(Activity context) {

        String storedUsersString = SharedPreferencesManager.readString(context, R.string.users);
        return JsonUserDataManager.getUsersFromString(storedUsersString);

    }//getStoredUsers


    public static User getUserById (Activity context, String id) {

        String storedUsersString = SharedPreferencesManager.readString(context, R.string.users);

        ArrayList<User> users = JsonUserDataManager.getUsersFromString(storedUsersString);

        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getId().equals(id)) return users.get(i);

        }

        User newUser = new User(id, false, false);
        users.add(newUser);

        SharedPreferencesManager.writeString(context, R.string.users, JsonUserDataManager.getStringFromUsers(users));

        return newUser;

    }//getUserById


    public static void editUserById (Activity context, String id) {

        String storedUsersString = SharedPreferencesManager.readString(context, R.string.users);

        ArrayList<User> users = JsonUserDataManager.getUsersFromString(storedUsersString);

        for (int i = 0; i < users.size(); i++) {

            if(users.get(i).getId().equals(id)) users.get(i).setInside(!users.get(i).isInside());

        }

        SharedPreferencesManager.writeString(context, R.string.users, JsonUserDataManager.getStringFromUsers(users));

    }//editUserById


    public static void saveAlertStatus (Activity context) {

        String storedUsersString = SharedPreferencesManager.readString(context, R.string.users);

        ArrayList<User> users = JsonUserDataManager.getUsersFromString(storedUsersString);

        for (int i = 0; i < users.size(); i++) {

            if(users.get(i).isInside()) users.get(i).setAlertOn(true);

        }

        SharedPreferencesManager.writeString(context, R.string.users, JsonUserDataManager.getStringFromUsers(users));

    }//editUserById


}//ScheduleDataManager
