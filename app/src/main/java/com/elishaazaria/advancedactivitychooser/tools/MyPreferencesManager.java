package com.elishaazaria.advancedactivitychooser.tools;

import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.StringRes;
import androidx.preference.PreferenceManager;

import com.elishaazaria.advancedactivitychooser.MyApplication;
import com.elishaazaria.advancedactivitychooser.R;
import com.elishaazaria.advancedactivitychooser.dialog.activitycomparators.Comparators;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyPreferencesManager {
    private static SharedPreferences sharedPreferences;
    private static MyApplication context;

    public static void init(MyApplication context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        MyPreferencesManager.context = context;
    }

    public static void end() {
        context = null;
        sharedPreferences = null;
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

    /* ******************* Dialog ************************************/

    public static HashMap<ComponentName, Long> getRecentMap_Dialog() {
        String storedHashMapString = sharedPreferences.getString(context.getString(R.string.k_dialog_recent_map_m), "{}");
        java.lang.reflect.Type type = new TypeToken<HashMap<String, Long>>() {
        }.getType();
        HashMap<String, Long> fromSave = new Gson().fromJson(storedHashMapString, type);
        HashMap<ComponentName, Long> ret = new HashMap<>(fromSave.size() + 1);
        fromSave.forEach((k, v) -> ret.put(decodeComponentName(k), v));
        return ret;
    }

    private static ComponentName decodeComponentName(String string) {
        String[] split = string.split(",");
        return new ComponentName(split[0], split[1]);
    }

    public static void setRecentMap_Dialog(Map<ComponentName, Long> recentMap) {
        HashMap<String, Long> toSave = new HashMap<>(recentMap.size());
        recentMap.forEach((k, v) -> toSave.put(encodeComponentName(k), v));
        String recentMapString = new Gson().toJson(toSave);
        sharedPreferences.edit().putString(context.getString(R.string.k_dialog_recent_map_m), recentMapString).apply();
    }

    private static String encodeComponentName(ComponentName componentName) {
        return componentName.getPackageName() + ',' + componentName.getClassName();
    }

    public static void addToRecentMap_Dialog(ComponentName componentName, Long l) {
        String storedHashMapString = sharedPreferences.getString(context.getString(R.string.k_dialog_recent_map_m), "{}");
        java.lang.reflect.Type type = new TypeToken<HashMap<String, Long>>() {
        }.getType();
        HashMap<String, Long> map = new Gson().fromJson(storedHashMapString, type);
        map.put(encodeComponentName(componentName),l);
        String recentMapString = new Gson().toJson(map);
        sharedPreferences.edit().putString(context.getString(R.string.k_dialog_recent_map_m), recentMapString).apply();
    }

    public static Comparators getSelectedComparator_Dialog() {
        String comparator = sharedPreferences.getString(context.getString(R.string.k_sort_comparator_e_comparators), null);
        if (comparator != null) {
            try {
                return Comparators.valueOf(comparator);
            } catch (IllegalArgumentException ignored) {
            }
        }

        return Comparators.values()[0];
    }

    /* ******************************* Debug **************************************/

    public static void deleteFromSharedPreferences_Debug(@StringRes int pref) {
        sharedPreferences.edit().remove(context.getString(pref)).commit();
    }
}