package com.example.hw_datastorage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {
    EditText editText;
    Button done_button;
    Entry back_entry;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_edit);
        Context context = this;

//        int id = getIntent().getIntExtra("id", 0);
//        String text = getIntent().getStringExtra("text");
//        String date = getIntent().getStringExtra("date");
//        back_entry = new Entry(id, text, date);

        back_entry = getIntent().getParcelableExtra("entry");

        editText = (EditText)findViewById(R.id.editText);
        editText.setText(back_entry.getText());
        done_button = (Button)findViewById(R.id.done_button);

        done_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back_entry.setText(editText.getText().toString());
                Intent intent = new Intent();
                intent.putExtra("id", back_entry.getId());
                intent.putExtra("text", back_entry.getText());
                intent.putExtra("date", back_entry.getDate());
                setResult(0, intent);
                finish();
            }
        });

    }
}
