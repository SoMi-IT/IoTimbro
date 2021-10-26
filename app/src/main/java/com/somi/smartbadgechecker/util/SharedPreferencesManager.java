package com.somi.smartbadgechecker.util;


import android.content.Context;
import android.content.SharedPreferences;


public class SharedPreferencesManager {


    final static String sharedPreferencesName = "mindthehead.smartbadgechecker.util";


    public static void writeString(Context context, int idKey, String value) {

        String key = context.getResources().getString(idKey);
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPreferencesName , Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();

    }//writeString


    public static String readString(Context context, int idKey) {

        String key = context.getResources().getString(idKey);
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPreferencesName , Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");

    }//readString


    public static void writeInt(Context context, int idKey, int value) {

        String key = context.getResources().getString(idKey);
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPreferencesName , Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();

    }//writeInt


    public static int readInt(Context context, int idKey) {

        String key = context.getResources().getString(idKey);
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPreferencesName , Context.MODE_PRIVATE);

        return sharedPreferences.getInt(key, 0);

    }//readString


}//SharedPreferencesManager
