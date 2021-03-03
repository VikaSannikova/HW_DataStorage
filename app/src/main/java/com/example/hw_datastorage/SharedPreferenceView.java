package com.example.hw_datastorage;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.net.Uri;
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
        update_button = (Button)view.findViewById(R.id.shared_preference_update_button);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.SharedPreferenceView);
        if (text == null) {
            text = "null";
        }
        Cursor cursor = getContext().getContentResolver()
                .query(SharedPreferenceProvider.URI,
                        null,
                        null,
                        null,
                        null);
        if (cursor.moveToFirst()) {
            text = cursor.getString(0);
        }
        edit_text.setText(text);

        update_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(SharedPreferenceProvider.PREFERENCE_NAME, edit_text.getText().toString());
                getContext().getContentResolver().update(SharedPreferenceProvider.URI,
                        contentValues,
                        null,
                        null);
            }
        });



        attributes.recycle();
    }

    public SharedPreferenceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
