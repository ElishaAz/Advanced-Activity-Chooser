package com.elishaazaria.advancedactivitychooser.dialog;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;

import java.util.Objects;

public class ActivityTile {
    public final CharSequence label;
    public final Drawable icon;
    public final ResolveInfo info;
    public final ComponentName component;

    OnClickListener onClick;
    OnLongClickListener onLongClick;

    public ActivityTile(CharSequence label, Drawable icon, ResolveInfo info) {
        this.label = label;
        this.icon = icon;
        this.info = info;
        ActivityInfo activity = info.activityInfo;
        component = new ComponentName(activity.applicationInfo.packageName, activity.name);
    }

    public Intent assignIntent(Intent source) {
        source.setComponent(component);
        return source;
    }

    public void setOnClickListener(OnClickListener l) {
        this.onClick = l;
    }

    public void setOnLongClickListener(OnLongClickListener l) {
        this.onLongClick = l;
    }

    public interface OnClickListener {
        void onClick(ActivityTile ab);
    }

    public interface OnLongClickListener {
        boolean onLongClick(ActivityTile ab);
    }

    @Override
    public String toString() {
        return "ActivityTile{" +
                label +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivityTile that = (ActivityTile) o;
        return Objects.equals(label, that.label) && Objects.equals(icon, that.icon) && info.equals(that.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, icon, info);
    }
}
