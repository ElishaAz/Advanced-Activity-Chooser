package com.elishaazaria.advancedactivitychooser.openas;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;

import com.elishaazaria.advancedactivitychooser.R;
import com.elishaazaria.advancedactivitychooser.tools.MimeTypeTools;

public class OpenAsWindow {
    private final Intent originalIntent;
    private WhenDone whenDone;
    private PopupWindow window;

    public OpenAsWindow(Intent originalIntent) {
        this.originalIntent = originalIntent;
    }

    //PopupWindow display method

    public void showPopupWindow(final Context context, final View view, WhenDone whenDone) {
        this.whenDone = whenDone;

        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.window_open_as, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        //Create a window with our parameters
        window = new PopupWindow(popupView, width, height, focusable);

        window.setBackgroundDrawable(new ColorDrawable(0x88000000));

        //Set the location of the window on the screen
        window.showAtLocation(view, Gravity.CENTER, 0, 0);

        popupView.setOnTouchListener((v, event) -> {
//            popupView.performClick();
            //Close the window when clicked
            window.dismiss();
            return true;
        });

        setupUI(context, popupView);
    }

    private void setupUI(Context context, View root) {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_dropdown_item_1line, MimeTypeTools.mimeTypes.keySet().toArray(new String[]{}));
        AutoCompleteTextView categoryText = root.findViewById(R.id.categoryText);
        categoryText.setAdapter(adapter);

        AutoCompleteTextView typeText = root.findViewById(R.id.typeText);

        categoryText.setThreshold(1);
        typeText.setThreshold(1);

        categoryText.setOnClickListener(v -> {
            categoryText.showDropDown();
        });
        typeText.setOnClickListener(v -> {
            typeText.showDropDown();
        });

        categoryText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();

                String[] options = new String[0];

                if (MimeTypeTools.mimeTypes.containsKey(text)) {
                    options = MimeTypeTools.mimeTypes.get(text);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                        android.R.layout.simple_dropdown_item_1line, options);
                typeText.setAdapter(adapter);
            }
        });

        Button launch = root.findViewById(R.id.launch);
        launch.setOnClickListener(v -> {
            whenDone.whenDone(categoryText.getText().toString(), typeText.getText().toString());
            window.dismiss();
        });
    }

    public interface WhenDone {
        void whenDone(String category, String type);
    }
}
