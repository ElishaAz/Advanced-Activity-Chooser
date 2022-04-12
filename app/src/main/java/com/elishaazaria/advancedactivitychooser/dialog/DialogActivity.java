package com.elishaazaria.advancedactivitychooser.dialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
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
import com.elishaazaria.advancedactivitychooser.tools.MyPreferencesManager;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.yevdo.jwildcard.JWildcard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DialogActivity extends AppCompatActivity {
    private static final String TAG = "DialogActivity";
    private ClipboardManager clipboard;

    private Intent intent;
    private Intent base;

    private List<ActivityTile> activityButtons;
    private CustomGridLayoutManager layoutManager;

    private int width, height;

    private CoordinatorLayout coordinatorLayout;
    private ConstraintLayout constraintLayout;
    private RecyclerView recyclerView;

    private TextView intentScheme;
    private TextView intentType;
    private TextView intentData;
    private TextView intentAction;

    private TextView titleText;

    private Button openAs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        setupConstants();

        setGravity();

        coordinatorLayout.setOnClickListener(v -> {
            finishAndRemoveTask();
        });

        setUpIntent();

        activityButtons = createActivityButtons(base);

        createButtonsUI(activityButtons);

        setUpBottomSheet();

        setUpUI();

        openAs.setOnClickListener(v -> openOpenAs(base));
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        finishAndRemoveTask();
    }

    private void setupConstants() {
        coordinatorLayout = findViewById(R.id.root);

        constraintLayout = findViewById(R.id.constraint_layout);

        recyclerView = findViewById(R.id.recycler_view);

        intentScheme = findViewById(R.id.intent_scheme_text);
        intentType = findViewById(R.id.intent_mime_type_text);
        intentData = findViewById(R.id.intent_data_text);
        intentAction = findViewById(R.id.intent_action_text);

        openAs = findViewById(R.id.open_as_button);

        titleText = findViewById(R.id.dialog_title_text);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
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

    private void setUpIntent() {
        intent = getIntent();

        base = new Intent(intent);//intent.getParcelableExtra(Intent.EXTRA_INTENT);

        base.setPackage(null);
        base.setComponent(null);
    }

    private void createButtonsUI(List<ActivityTile> activityButtons) {
        layoutManager = new CustomGridLayoutManager(this, MyPreferencesManager.getItemsPerRow_UI());
        layoutManager.setScrollEnabled(false);

        recyclerView.setLayoutManager(layoutManager);

        int size = width / MyPreferencesManager.getItemsPerRow_UI();

        ActivitiesAdapter adapter = new ActivitiesAdapter(this, size, activityButtons.toArray(new ActivityTile[]{}));
        recyclerView.setAdapter(adapter);

    }

    private void setUpBottomSheet() {
        BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior = BottomSheetBehavior.from(constraintLayout);
        bottomSheetBehavior.setPeekHeight((int) (height * (MyPreferencesManager.getWindowAreaPercent_UI() / 100.0)));

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
            }
        });
    }

    private void setUpUI() {
        String title = MyPreferencesManager.getTitle_UI();

        if (title.trim().isEmpty()) {
            titleText.setVisibility(View.GONE);
        } else if (title.equals("default")) {
            titleText.setText(R.string.ac_title);
        } else {
            titleText.setText(title);
        }

        setUpDataText(intentScheme, MyPreferencesManager.isShowIntentScheme_UI(), base.getScheme(), R.string.dialog_scheme_name);
        setUpDataText(intentType, MyPreferencesManager.isShowIntentType_UI(), base.getType(), R.string.dialog_mime_type_name);
        setUpDataText(intentData, MyPreferencesManager.isShowIntentDataUI(), base.getData() == null ? null : base.getData().toString(), R.string.dialog_data_name);
        setUpDataText(intentAction, MyPreferencesManager.isShowIntentAction_UI(), base.getAction(), R.string.dialog_action_name);
    }

    private void setUpDataText(TextView textView, boolean showPref, String data, int nameRes) {
        if (showPref && data != null) {
            textView.setText(data);
        } else {
            textView.setVisibility(View.GONE);
        }
        textView.setOnLongClickListener(v -> {
            String name = getString(nameRes);
            ClipData clip = android.content.ClipData.newPlainText(name, data);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, name + " copied to clipboard", Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void openOpenAs(Intent original) {
//        Toast.makeText(this, "Open As!", Toast.LENGTH_SHORT).show();
        OpenAsWindow window = new OpenAsWindow(original);
        window.showPopupWindow(this, openAs, (category, type) -> {
            // TODO:
        });
    }

    private List<ActivityTile> createActivityButtons(Intent base) {
        List<ActivityTile> activityButtons = new ArrayList<>();

        PackageManager pm = getPackageManager();
        List<ResolveInfo> resolveInfoList = pm.queryIntentActivities(base, PackageManager.MATCH_ALL | PackageManager.GET_RESOLVED_FILTER);
//        List<ResolveInfo> resolveInfoList = pm.queryIntentActivityOptions(this.getComponentName(), intentList.toArray(intentList.toArray(new Intent[0])), base, PackageManager.MATCH_ALL); //pm.queryIntentActivities(base, PackageManager.MATCH_ALL); // PackageManager.GET_RESOLVED_FILTER |

        boolean filterIntents = MyPreferencesManager.isFilterIntents_Filter();
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
        HashMap<String, List<String>> filterMap = MyPreferencesManager.getFilterMap_Filter();

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
