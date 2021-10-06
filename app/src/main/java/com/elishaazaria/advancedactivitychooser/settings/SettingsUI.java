package com.elishaazaria.advancedactivitychooser.settings;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;

import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SeekBarPreference;

import com.elishaazaria.advancedactivitychooser.R;

public class SettingsUI extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.ui_preferences, rootKey);

//        EditTextPreference preference = findPreference(getString(R.string.k_dialog_columns_i));
//        preference.setOnBindEditTextListener(editText -> {
//            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
//        });

        SeekBarPreference area = findPreference(getString(R.string.k_dialog_area_i));
        area.setSeekBarIncrement(10);
    }
}