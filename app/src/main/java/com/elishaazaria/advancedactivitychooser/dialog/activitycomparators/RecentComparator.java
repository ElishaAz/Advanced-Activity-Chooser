package com.elishaazaria.advancedactivitychooser.dialog.activitycomparators;

import android.content.ComponentName;

import com.elishaazaria.advancedactivitychooser.dialog.ActivityTile;
import com.elishaazaria.advancedactivitychooser.tools.MyPreferencesManager;

import java.util.Comparator;
import java.util.Map;
import java.util.Objects;

/**
 * Compare Activity Tiles based on which one was used most recently. Less = Recent.
 */
public class RecentComparator implements Comparator<ActivityTile> {

    private final Map<ComponentName, Long> recentMap = MyPreferencesManager.getRecentMap_Dialog();

    @Override
    public int compare(ActivityTile left, ActivityTile right) {
        if (recentMap.containsKey(left.component)) {
            if (recentMap.containsKey(right.component)) {
                return Long.compare(recentMap.get(right.component), recentMap.get(left.component));
            } else {
                return -1;
            }
        } else if (!recentMap.containsKey(right.component)) {
            return 1;
        }
        return 0;
    }
}
