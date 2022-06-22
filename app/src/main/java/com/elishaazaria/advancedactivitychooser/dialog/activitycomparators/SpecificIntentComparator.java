package com.elishaazaria.advancedactivitychooser.dialog.activitycomparators;

import android.content.IntentFilter;
import android.util.Log;

import com.elishaazaria.advancedactivitychooser.dialog.ActivityTile;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

public class SpecificIntentComparator implements Comparator<ActivityTile> {

    private final String mimeType;

    public SpecificIntentComparator(String mimeType) {
        this.mimeType = mimeType;
    }

    private final HashMap<ActivityTile, Integer> cache = new HashMap<>();

    @Override
    public int compare(ActivityTile o1, ActivityTile o2) {
        if (mimeType == null) return 0;

        if (o2.info.filter == null) {
            if (o1.info.filter != null)
                return -1;
            return 0;
        }
        if (o1.info.filter == null) return 1;

        int firstMatch;
        int secondMatch;

        if (cache.containsKey(o1))
            firstMatch = cache.get(o1);
        else {
            firstMatch = checkMatchType(o1.info.filter.typesIterator(), mimeType);
            cache.put(o1, firstMatch);
        }

        if (cache.containsKey(o2))
            secondMatch = cache.get(o2);
        else {
            secondMatch = checkMatchType(o2.info.filter.typesIterator(), mimeType);
            cache.put(o2, secondMatch);
        }

        return firstMatch - secondMatch;
    }

    private static final int MATCHES_EXACTLY = 0;
    private static final int MATCHES_CATEGORY = 1;
    private static final int MATCHES_ALL = 2;
    private static final int NO_MATCH = 3;

    private static int checkMatchType(Iterator<String> types, String mimeType) {
        boolean isExact = mimeType.contains("/");
        String cat = isExact ? mimeType.substring(0, mimeType.indexOf('/')) : mimeType;

        int currentMatch = NO_MATCH;

        while (types.hasNext()) {
            String currentType = types.next();

            if (currentType.equals(mimeType)) {
                return MATCHES_EXACTLY;
            }

            if (isExact) {
                if (currentType.equals(cat) && currentMatch > MATCHES_CATEGORY) {
                    currentMatch = MATCHES_CATEGORY;
                }
            }
            if (currentType.equals("*") && currentMatch > MATCHES_ALL) {
                currentMatch = MATCHES_ALL;
            }
        }

        return currentMatch;
    }
}
