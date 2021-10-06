package com.elishaazaria.advancedactivitychooser.dialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.elishaazaria.advancedactivitychooser.R;
import com.elishaazaria.advancedactivitychooser.openas.OpenAsWindow;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yevdo.jwildcard.JWildcard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DialogActivity extends AppCompatActivity {

    private int windowAreaPercent;
    private int itemsPerRow;
    private boolean filterIntents;
    private boolean showIntentType;
    private boolean showIntentData;

    private Intent intent;
    private Intent base;

    private List<ActivityTile> activityButtons;
    private CustomGridLayoutManager layoutManager;

    private int width, height;

    private CoordinatorLayout coordinatorLayout;
    private ConstraintLayout constraintLayout;
    private RecyclerView recyclerView;

    private TextView intentType;
    private TextView intentData;

    private Button openAs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        coordinatorLayout = findViewById(R.id.root);

        constraintLayout = findViewById(R.id.constraint_layout);

        recyclerView = findViewById(R.id.recycler_view);

        intentType = findViewById(R.id.intent_type_text);
        intentData = findViewById(R.id.intent_data_text);

        openAs = findViewById(R.id.open_as_button);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        loadPreferences();

        setGravity();

        coordinatorLayout.setOnClickListener(v -> {
            finishAndRemoveTask();
        });

        setUpIntent();

        activityButtons = createActivityButtons(base);

        createButtonsUI(activityButtons);

        setUpBottomSheet();

        if (showIntentType && base.getType() != null) {
            intentType.setText(base.getType());
        }
        if (showIntentData && base.getData() != null) {
            intentData.setText(base.getData().toString());
        }

        openAs.setOnClickListener(v -> openOpenAs(base));
    }

    private void setUpIntent() {
        intent = getIntent();

        base = new Intent(intent);//intent.getParcelableExtra(Intent.EXTRA_INTENT);

        base.setPackage(null);
        base.setComponent(null);
    }

    private void loadPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        windowAreaPercent = sharedPreferences.getInt(getString(R.string.k_dialog_area_i), 40);
        itemsPerRow = sharedPreferences.getInt(getString(R.string.k_dialog_columns_i), 4);
        filterIntents = sharedPreferences.getBoolean(getString(R.string.k_filter_enable_b), false);

        showIntentType = sharedPreferences.getBoolean(getString(R.string.k_dialog_show_type_b), true);
        showIntentData = sharedPreferences.getBoolean(getString(R.string.k_dialog_show_data_b), false);

    }

    private void setGravity() {

        setTitle("");

        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.height = height;

        wlp.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        wlp.flags &= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
    }

    private void openOpenAs(Intent original) {
//        Toast.makeText(this, "Open As!", Toast.LENGTH_SHORT).show();
        OpenAsWindow window = new OpenAsWindow(original);
        window.showPopupWindow(this, openAs, (category, type) -> {
            // TODO:
        });
    }

    private void setUpBottomSheet() {
        BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior = BottomSheetBehavior.from(constraintLayout);
        bottomSheetBehavior.setPeekHeight((int) (height * (windowAreaPercent / 100.0)));

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    layoutManager.setScrollEnabled(true);
                    recyclerView.setNestedScrollingEnabled(true);
                }

                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    layoutManager.setScrollEnabled(false);
//                    recyclerView.setNestedScrollingEnabled(false);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // do stuff during the actual drag event for example
                // animating a background color change based on the offset

                // or for example hidding or showing a fab
//                if (slideOffset > 0.2) {
//                    if (fab.isShown()) {
//                        fab.hide();
//                    }
//                } else if (slideOffset < 0.15) {
//                    if (!fab.isShown()) {
//                        fab.show();
//                    }
//                }
            }
        });
    }

    private List<ActivityTile> createActivityButtons(Intent base) {
        List<ActivityTile> activityButtons = new ArrayList<>();

        PackageManager pm = getPackageManager();
        List<ResolveInfo> resolveInfoList = pm.queryIntentActivities(base, PackageManager.MATCH_ALL | PackageManager.GET_RESOLVED_FILTER);
//        List<ResolveInfo> resolveInfoList = pm.queryIntentActivityOptions(this.getComponentName(), intentList.toArray(intentList.toArray(new Intent[0])), base, PackageManager.MATCH_ALL); //pm.queryIntentActivities(base, PackageManager.MATCH_ALL); // PackageManager.GET_RESOLVED_FILTER |
        if (!resolveInfoList.isEmpty()) {
            for (ResolveInfo info : resolveInfoList) {
//                Log.d("DialogActivity", info.filter.typesIterator().next());

                boolean filter = false;

                if (info.activityInfo.packageName.equals(getPackageName())) {
                    filter = true;
                }

                if (filterIntents)
                    for (String pattern : getFilterPatterns(base.getType())) {
                        if (JWildcard.matches(pattern, info.activityInfo.packageName)) {
                            filter = true;
//                            Log.d("DialogActivity", "Filtering " + info.activityInfo.packageName);
                            break;
                        }
                    }

                if (!filter) {
//                    Log.d("DialogActivity", "Adding " + info.activityInfo.packageName);
                    ActivityTile button = new ActivityTile(info.loadLabel(pm), info.loadIcon(pm), info);
                    button.setOnClickListener(this::onClick);
                    activityButtons.add(button);
                }
            }
        } else {
            Log.d("DialogActivity", "List is empty!");
        }

        activityButtons.sort(new ActivitiesComparator(base.getType()));

        return activityButtons;
    }

    private List<String> getFilterPatterns(String mimeType) {
        String storedHashMapString = PreferenceManager.getDefaultSharedPreferences(this)
                .getString(getString(R.string.k_filter_map_m), "{}");
        java.lang.reflect.Type type = new TypeToken<HashMap<String, List<String>>>() {
        }.getType();
        HashMap<String, List<String>> filterMap = new Gson().fromJson(storedHashMapString, type);

        List<String> patternList = new ArrayList<>();

        if (mimeType != null) {

            if (filterMap.containsKey(mimeType)) {
                patternList.addAll(filterMap.get(mimeType));
            }
            String[] split = mimeType.split("/");
            if (split.length == 2) {
                if (filterMap.containsKey(split[0] + "/*")) {
                    patternList.addAll(filterMap.get(split[0] + "/*"));
                }
                if (filterMap.containsKey("*/*")) {
                    patternList.addAll(filterMap.get("*/*"));
                }
            }
            if (split.length == 1) {
                if (filterMap.containsKey("*")) {
                    patternList.addAll(filterMap.get("*"));
                }
            }
        } else {
            if (filterMap.containsKey("!")) {
                patternList.addAll(filterMap.get("!"));
            }
        }
        return patternList;
    }

    private void createButtonsUI(List<ActivityTile> activityButtons) {
        layoutManager = new CustomGridLayoutManager(this, itemsPerRow);
        layoutManager.setScrollEnabled(false);
//        recyclerView.setNestedScrollingEnabled(false);

//        GridLayoutManager.LayoutParams lp = layoutManager.
//        layoutManager.layoutParams

//        layoutManager.setMeasuredDimension();
        recyclerView.setLayoutManager(layoutManager);

        int size = width / itemsPerRow;

        ActivitiesAdapter adapter = new ActivitiesAdapter(this, size, activityButtons.toArray(new ActivityTile[]{}));
        recyclerView.setAdapter(adapter);

    }

    public void onClick(ActivityTile ab) {
        base = ab.assignIntent(base);
        startActivity(base);
        finishAndRemoveTask();
    }

    public static class CustomGridLayoutManager extends GridLayoutManager {
        private boolean isScrollEnabled = true;

        public CustomGridLayoutManager(Context context, int spanCount) {
            super(context, spanCount);
        }

        public void setScrollEnabled(boolean flag) {
            this.isScrollEnabled = flag;
        }

        @Override
        public boolean canScrollVertically() {
            //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
            return isScrollEnabled && super.canScrollVertically();
        }
    }

}
