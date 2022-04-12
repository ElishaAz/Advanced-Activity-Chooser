package com.elishaazaria.advancedactivitychooser.tools;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.elishaazaria.advancedactivitychooser.MyApplication;
import com.elishaazaria.advancedactivitychooser.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;

public class MyPreferencesManager {
    private static SharedPreferences sharedPreferences;
    private static MyApplication context;

    public static void init(MyApplication context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        MyPreferencesManager.context = context;
    }

    public static void end() {

    }

    /* *********************** UI ******************************/

    public static int getWindowAreaPercent_UI() {
        return sharedPreferences.getInt(context.getString(R.string.k_dialog_area_i), 40);
    }

    public static int getItemsPerRow_UI() {
        return sharedPreferences.getInt(context.getString(R.string.k_dialog_columns_i), 4);
    }

    public static boolean isShowIntentScheme_UI() {
        return sharedPreferences.getBoolean(context.getString(R.string.k_dialog_show_scheme_b), false);
    }

    public static boolean isShowIntentType_UI() {
        return sharedPreferences.getBoolean(context.getString(R.string.k_dialog_show_type_b), true);
    }

    public static boolean isShowIntentDataUI() {
        return sharedPreferences.getBoolean(context.getString(R.string.k_dialog_show_data_b), false);
    }

    public static boolean isShowIntentAction_UI() {
        return sharedPreferences.getBoolean(context.getString(R.string.k_dialog_show_action_b), false);
    }

    public static String getTitle_UI() {
        return sharedPreferences.getString(context.getString(R.string.k_dialog_title_s), "default");
    }

    /* *********************** filters ************************************/

    public static boolean isFilterIntents_Filter() {
//        return sharedPreferences.getBoolean(context.getString(R.string.k_filter_enable_b), false);
        return false;
    }

    public static HashMap<String, List<String>> getFilterMap_Filter() {
        String storedHashMapString = sharedPreferences.getString(context.getString(R.string.k_filter_map_m), "{}");
        java.lang.reflect.Type type = new TypeToken<HashMap<String, List<String>>>() {
        }.getType();
        return new Gson().fromJson(storedHashMapString, type);
    }

    /* ******************** Aliases ***********************************/

    public static boolean isShowAdvanced_Alias() {
        return sharedPreferences.getBoolean(context.getString(R.string.k_alias_show_advanced_b), false);
    }

}