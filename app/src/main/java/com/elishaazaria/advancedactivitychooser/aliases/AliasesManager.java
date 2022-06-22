package com.elishaazaria.advancedactivitychooser.aliases;

import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import androidx.preference.PreferenceManager;

public class AliasesManager {
    public static void updateAlias(Context context, Aliases alias) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        updateAliasesState(
                sharedPreferences.getBoolean(context.getString(alias.key), alias.defaultEnabled),
                context, alias.extension);
    }

    public static void updateAlias(Context context, Aliases alias, boolean enabled) {
        updateAliasesState(enabled, context, alias.extension);
    }

    public static void updateAllAliases(Context context) {
        for (Aliases alias : Aliases.values()) {
            updateAlias(context, alias);
        }
    }

    private static void updateAliasesState(boolean enable, Context context, String extension) {
        PackageManager pm = context.getApplicationContext().getPackageManager();
        ComponentName compName =
                new ComponentName(context.getPackageName(), context.getPackageName() + extension);
        pm.setComponentEnabledSetting(
                compName,
                enable ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED :
                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }
}
