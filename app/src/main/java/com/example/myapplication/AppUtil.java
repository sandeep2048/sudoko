package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class AppUtil {

    public static final String CHANGE_LANGUAGE = "chnageLanguage";
    static String name = "sudoko";
    static String originalMatrix = "matrix";
    static String gamelMat = "gamelMatrix";
    static String level = "level";

    public static final int getSharedPreferencesInt(String key, Context context) {
        SharedPreferences prefs = context.getSharedPreferences(name, 0);
        return prefs.getInt(key, -1);
    }

    public static final String getSharedPreferencesString(String key, Context context) {
        SharedPreferences prefs = context.getSharedPreferences(name, 0);
        return prefs.getString(key, null);
    }


    public static void putSharedPreferences(String key, int value, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences(name, 0)
                .edit();
        edit.putInt(key, value);
        edit.commit();
    }

    public static void putSharedPreferences(String key, String value, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences(name, 0)
                .edit();
        edit.putString(key, value);
        edit.commit();
    }


    public static void restart(Activity activity) {

        Intent intent = new Intent(activity, activity.getClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);


        activity.finish();
        activity.overridePendingTransition(0, 0);
        activity.startActivity(intent);
    }
}
