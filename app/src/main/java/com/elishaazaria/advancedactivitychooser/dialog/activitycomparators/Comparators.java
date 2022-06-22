package com.elishaazaria.advancedactivitychooser.dialog.activitycomparators;

import androidx.annotation.StringRes;

import com.elishaazaria.advancedactivitychooser.R;

public enum Comparators {
    NONE(R.string.settings_sort_comparator_none_name),
    RECENT(R.string.settings_sort_comparator_recents_name),
    SPECIFIC_INTENT(R.string.settings_sort_comparator_specific_name);

    @StringRes
    public final int name;

    Comparators(int name) {
        this.name = name;
    }
}
