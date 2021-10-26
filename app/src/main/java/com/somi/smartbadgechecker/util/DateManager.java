package com.somi.smartbadgechecker.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class DateManager {


    /*public static String getFormattedStringFromDate(int year, int month, int day) {


        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        return df.format(calendar.getTime());

    }//getFormattedStringFromDate


    public static String getDayNameFromDate(int year, int month, int day) {


        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());

    }//getFormattedStringFromDate*/

    public static String getCurrentItalianDate(){

        return new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

    }//getCurrentItalianDate

    public static String getItalianConvertedTime(String strCurrentDate){

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = null;

        try {

            Date newDate  = format.parse(strCurrentDate);
            format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            date = format.format(newDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;

    }//getItalianConvertedTime

    public static String getCurrentTime(){

        return new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

    }//getCurrentTime

    public static int getCurrentHour(){

        return Integer.parseInt(new SimpleDateFormat("HH", Locale.getDefault()).format(new Date()));

    }//getCurrentTime

    public static String getCurrentMoment(){

        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

    }//getCurrentMoment


    public static String getCurrentItalianMoment(){

        return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(new Date());

    }//getCurrentMoment

}//DateManager
