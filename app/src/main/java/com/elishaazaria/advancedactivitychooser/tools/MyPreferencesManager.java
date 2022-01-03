package com.elishaazaria.advancedactivitychooser.tools;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.elishaazaria.advancedactivitychooser.R;

public class MyPreferencesManager {
    private int windowAreaPercent;
    private int itemsPerRow;

    private boolean filterIntents;

    private boolean showIntentScheme;
    private boolean showIntentType;
    private boolean showIntentData;
    private boolean showIntentAction;

    private String title;

    private final SharedPreferences sharedPreferences;
    private final Context context;

    public MyPreferencesManager(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;

        reloadPreferences();
    }

    public void reloadPreferences() {
        windowAreaPercent = sharedPreferences.getInt(context.getString(R.string.k_dialog_area_i), 40);
        itemsPerRow = sharedPreferences.getInt(context.getString(R.string.k_dialog_columns_i), 4);

        filterIntents = sharedPreferences.getBoolean(context.getString(R.string.k_filter_enable_b), false);

        showIntentScheme = sharedPreferences.getBoolean(context.getString(R.string.k_dialog_show_scheme_b), false);
        showIntentType = sharedPreferences.getBoolean(context.getString(R.string.k_dialog_show_type_b), true);
        showIntentData = sharedPreferences.getBoolean(context.getString(R.string.k_dialog_show_data_b), false);
        showIntentAction = sharedPreferences.getBoolean(context.getString(R.string.k_dialog_show_action_b), false);

        title = sharedPreferences.getString(context.getString(R.string.k_dialog_title_s), "default");
    }

    public int getWindowAreaPercent() {
        return windowAreaPercent;
    }

    public int getItemsPerRow() {
        return itemsPerRow;
    }

    public boolean isFilterIntents() {
        return filterIntents;
    }

    public boolean isShowIntentScheme() {
        return showIntentScheme;
    }

    public boolean isShowIntentType() {
        return showIntentType;
    }

    public boolean isShowIntentData() {
        return showIntentData;
    }

    public boolean isShowIntentAction() {
        return showIntentAction;
    }

    public String getTitle() {
        return title;
    }
}