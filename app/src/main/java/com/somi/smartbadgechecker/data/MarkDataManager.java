package com.somi.smartbadgechecker.data;

import android.app.Activity;
import android.util.Log;

import com.somi.smartbadgechecker.R;
import com.somi.smartbadgechecker.util.SharedPreferencesManager;

import java.util.ArrayList;

public class MarkDataManager {


    public ArrayList<Mark> getStoredMarks(Activity context) {

        String storedMarksString = SharedPreferencesManager.readString(context, R.string.marks);
        return JsonMarkDataManager.getMarksFromString(storedMarksString);

    }//getStoredMarks


    public static void addMark (Activity context, Mark mark) {

        String storedMarksString = SharedPreferencesManager.readString(context, R.string.marks);

        ArrayList<Mark> marks = JsonMarkDataManager.getMarksFromString(storedMarksString);

        marks.add(mark);

        SharedPreferencesManager.writeString(context, R.string.marks, JsonMarkDataManager.getStringFromMarks(marks));

    }//addMark


    public static void editUserById (Activity context, String id) {

        String storedUsersString = SharedPreferencesManager.readString(context, R.string.users);

        ArrayList<User> users = JsonUserDataManager.getUsersFromString(storedUsersString);

        for (int i = 0; i < users.size(); i++) {

            if(users.get(i).getId().equals(id)) users.get(i).setInside(!users.get(i).isInside());

        }

        SharedPreferencesManager.writeString(context, R.string.users, JsonUserDataManager.getStringFromUsers(users));

    }//editUserById


}//ScheduleDataManager
