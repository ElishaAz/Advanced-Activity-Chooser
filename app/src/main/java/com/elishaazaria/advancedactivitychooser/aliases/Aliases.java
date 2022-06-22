package com.elishaazaria.advancedactivitychooser.aliases;

import androidx.annotation.StringRes;

import com.elishaazaria.advancedactivitychooser.R;

public enum Aliases {
    FILES(".dialog.AliasFiles", R.string.k_alias_files_b,
            R.string.settings_alias_files, R.string.settings_alias_files_summery, true),
    WEB_BROWSER(".dialog.AliasWebBrowser", R.string.k_alias_web_browser_b,
            R.string.settings_alias_web_browser, R.string.settings_alias_web_browser_summery, true),
    WEB_LINKS(".dialog.AliasWebLinks", R.string.k_alias_web_links_b,
            R.string.settings_alias_web_links, R.string.settings_alias_web_links_summery, false),
    SCHEME_INTENTS(".dialog.AliasSchemeIntents", R.string.k_alias_scheme_intents_b,
            R.string.settings_alias_scheme_intents, R.string.settings_alias_scheme_intents_summery, true),
    OPEN_CAMERA(".dialog.AliasOpenCamera", R.string.k_alias_open_camera_b,
            R.string.settings_alias_open_camera, R.string.settings_alias_open_camera_summery, false);


    public final String extension;
    @StringRes
    public final int key;
    @StringRes
    public final int label;
    public final int summery;
    public final boolean defaultEnabled;

    private Aliases(String extension, @StringRes int key, @StringRes int title, @StringRes int summery, boolean defaultEnabled) {

        this.extension = extension;
        this.key = key;
        this.label = title;
        this.summery = summery;
        this.defaultEnabled = defaultEnabled;
    }
}
