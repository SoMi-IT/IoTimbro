package com.somi.smartbadgechecker.data;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class JsonMarkDataManager {



    public static ArrayList<Mark> getMarksFromString(String string) {

        ArrayList<Mark> marks = new ArrayList<>();

        try {


            JSONArray jsonArray = new JSONArray(string);

            for(int i = 0; i<jsonArray.length(); i++) {

                Mark mark;
                JSONObject currentJsonObject;
                currentJsonObject = jsonArray.getJSONObject(i);
                mark = getMarkFromJson(currentJsonObject);

                if(mark != null) marks.add(mark);

            }

        } catch (JSONException e) {
            e.printStackTrace();
            return marks;
        }

        return marks;

    }//getMarksFromString


    public static Mark getMarkFromJson(JSONObject jsonObject){

        Mark mark;

        try {

            String time = jsonObject.getString(Mark.MARK_TIME);
            String user = jsonObject.getString(Mark.MARK_USER);
            boolean isGoingIn = jsonObject.getBoolean(Mark.MARK_TYPE);

            mark = new Mark(time, user,  isGoingIn);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return mark;

    }//getMarkFromJson


    public static String getStringFromMarks(ArrayList<Mark> marks){

        JSONArray jsonArray = new JSONArray();

        for(int i = 0; i<marks.size(); i++) {

            if(getJsonObjectFromMark(marks.get(i)) != null) jsonArray.put(getJsonObjectFromMark(marks.get(i)));

        }

        return jsonArray.toString();

    }//getStringFromMarks


    public static JSONObject getJsonObjectFromMark(Mark mark){

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put(Mark.MARK_TIME, mark.getTime());
            jsonObject.put(Mark.MARK_USER, mark.getUser());
            jsonObject.put(Mark.MARK_TYPE, mark.isGoingIn());

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return jsonObject;

    }//getJsonObjectFromMark

}//JsonDataManager


