package com.example.hw_datastorage;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

public class SharedPreferenceView extends FrameLayout {
    String text;
    EditText edit_text;
    Button update_button;

    public SharedPreferenceView(Context context) {
        super(context);
    }

    public SharedPreferenceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = inflate(getContext(), R.layout.shared_preference_layout,this);
        edit_text = (EditText)view.findViewById(R.id.shared_preference_text);
        //update_button = (Button)findViewById(R.id.edit_button);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.SharedPreferenceView);
        text = attributes.getString(R.styleable.SharedPreferenceView_text);
        if (text == null) {
            text = "null";
        }
        edit_text.setText(text);
        attributes.recycle();
    }

    public SharedPreferenceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
