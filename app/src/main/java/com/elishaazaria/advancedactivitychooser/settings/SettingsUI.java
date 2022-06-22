package com.elishaazaria.advancedactivitychooser.settings;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;

import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SeekBarPreference;

import com.elishaazaria.advancedactivitychooser.R;
import com.elishaazaria.advancedactivitychooser.dialog.activitycomparators.Comparators;

import java.util.Arrays;

public class SettingsUI extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.ui_preferences, rootKey);

//        EditTextPreference preference = findPreference(getString(R.string.k_dialog_columns_i));
//        preference.setOnBindEditTextListener(editText -> {
//            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
//        });

        SeekBarPreference area = findPreference(getString(R.string.k_dialog_area_i));
        assert area != null;
        area.setSeekBarIncrement(10);

        ListPreference sortComparator = findPreference(getString(R.string.k_sort_comparator_e_comparators));
        assert sortComparator != null;

        Comparators[] values = Comparators.values();
        String[] entries = new String[values.length];
        String[] entryValues = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            entries[i] = getString(values[i].name);
            entryValues[i] = values[i].name();
        }

        sortComparator.setEntries(entries);
        sortComparator.setEntryValues(entryValues);
        sortComparator.setDefaultValue(entryValues[0]);
    }
}