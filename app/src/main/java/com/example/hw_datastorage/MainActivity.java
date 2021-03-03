package com.example.hw_datastorage;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    RecyclerView fileRV;
    ArrayList<Entry> entries;
    String AUTHORITY = "com.swtecnn.contentproviderlesson.DbContentProvider";
    String DIARY_ENTRY_TABLE = "DiaryEntry";
    Uri uri = Uri.parse("content://" + AUTHORITY + "/" + DIARY_ENTRY_TABLE);
    FileAdapter fileAdapter;
    Cursor cursor;

    EditText editText;

    SharedPreferenceView sharedPreferenceView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        Context context = this;
        sharedPreferenceView = (SharedPreferenceView)findViewById(R.id.shared_preference_view);


        cursor = this.getContentResolver().query(uri, null, null, null, null);
        ContentObserver contentObserver = new ContentObserver(new Handler(getMainLooper())) {
            @Override
            public boolean deliverSelfNotifications() {
                Log.d("___", "DATA CHANGED NOTIF");
                return super.deliverSelfNotifications();
            }

            @Override
            public void onChange(boolean selfChange) {
                Log.d("___", "DATA CHANGED");
                super.onChange(selfChange);
            }

            @Override
            public void onChange(boolean selfChange, @Nullable Uri uri) {
                Log.d("___", "DATA CHANGED BY URI");
                super.onChange(selfChange, uri);
                entries.clear();
                getDataFromContentProvider();
                fileRV.setAdapter(fileAdapter);
            }
        };
        getContentResolver().registerContentObserver(uri,true, contentObserver);

        entries = new ArrayList<>();
        entries.clear();

        fileRV = (RecyclerView)findViewById(R.id.fileRV);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        fileRV.setLayoutManager(linearLayoutManager);

        fileAdapter = new FileAdapter(this,this);
        getDataFromContentProvider();
        fileRV.setAdapter(fileAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            int id = data.getIntExtra("id",0);
            String text = data.getStringExtra("text");
            String date = data.getStringExtra("date");
            Entry entry = new Entry(id, text, date);
            ContentValues contentValues = new ContentValues();
            contentValues.put("id", entry.getId());
            contentValues.put("entry_text", entry.getText());
            contentValues.put("entry_date", entry.getDate());
            Uri update_uri = Uri.parse("content://" + AUTHORITY + "/" + DIARY_ENTRY_TABLE + "/"+ entry.getId());
            this.getContentResolver().update(update_uri, contentValues, null);
        }
    }

    private void getDataFromContentProvider() {
        Cursor cursor = this.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String text = cursor.getString(cursor.getColumnIndex("entry_text"));
                String date = cursor.getString(cursor.getColumnIndex("entry_date"));
                entries.add(new Entry(id, text, date));
            }
            fileAdapter.setItems(entries);
        }
    }
}
