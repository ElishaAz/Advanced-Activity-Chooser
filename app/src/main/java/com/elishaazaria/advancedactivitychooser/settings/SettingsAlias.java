package com.elishaazaria.advancedactivitychooser.settings;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.elishaazaria.advancedactivitychooser.R;
import com.elishaazaria.advancedactivitychooser.aliases.Aliases;
import com.elishaazaria.advancedactivitychooser.aliases.AliasesManager;
import com.elishaazaria.advancedactivitychooser.tools.MyPreferencesManager;

public class SettingsAlias extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.alias_preferences, rootKey);

        Context context = getContext();
        if (context == null) return;

        PreferenceCategory category = findPreference(getString(R.string.k_alias_category_n));
        if (category == null) return;

        for (Aliases alias : Aliases.values()) {
            SwitchPreference switchPreference = new SwitchPreference(context);

            switchPreference.setKey(context.getString(alias.key));
            switchPreference.setTitle(alias.label);
            switchPreference.setSummary(formatAliasSummery(context, alias.summery, MyPreferencesManager.isShowAdvanced_Alias()));
            switchPreference.setDefaultValue(alias.defaultEnabled);

            switchPreference.setSelectable(false);

            switchPreference.setOnPreferenceChangeListener((preference, newValue) -> {
                AliasesManager.updateAlias(context, alias, (Boolean) newValue);
                return true;
            });

            category.addPreference(switchPreference);
        }

        findPreference(getString(R.string.k_alias_show_advanced_b)).setOnPreferenceChangeListener((preference, newValue) -> {
            boolean val = (Boolean) newValue;
            for (Aliases alias : Aliases.values()) {
                findPreference(getString(alias.key)).setSummary(formatAliasSummery(context, alias.summery, val));
            }

            return true;
        });

        findPreference(getString(R.string.k_alias_enable_editing_b)).setOnPreferenceChangeListener((preference, newValue) -> {
            boolean val = (Boolean) newValue;
            for (Aliases alias : Aliases.values()) {
                findPreference(getString(alias.key)).setSelectable(val);
            }
            return true;
        });
    }


    private static String formatAliasSummery(Context context, @StringRes int summery, boolean showAdvanced) {
        String summeryS = context.getString(summery);
        int index = summeryS.indexOf(';');
        if (index == -1) return summeryS;

        if (showAdvanced) {
            return summeryS.substring(0, index) + summeryS.substring(index + 1);
        } else {
            return summeryS.substring(0, index);
        }
    }
}