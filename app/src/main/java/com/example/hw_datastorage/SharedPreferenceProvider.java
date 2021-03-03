package com.example.hw_datastorage;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SharedPreferenceProvider extends ContentProvider {

    static String AUTHORITY = "com.example.hw_datastorage";
    private static final int MATCH = 1;
    static String PREFERENCE_FILE = "pref";
    static String PREFERENCE_NAME = "third";
    static Uri URI = Uri.parse("content://" + AUTHORITY + "/" + PREFERENCE_FILE);

    static UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, PREFERENCE_FILE, MATCH);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.d("___", "IM IN QUERY");

        int uri_type = sUriMatcher.match(uri);
        MatrixCursor cursor = null;
        switch (uri_type) {
            case MATCH: {
                String[] key = new String[]{uri.getPathSegments().get(0)};
                cursor = new MatrixCursor(new String[]{key[0]});
                SharedPreferences mySharedPreferences = getContext().getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE);
                MatrixCursor.RowBuilder rowBuilder = cursor.newRow();
                rowBuilder.add(mySharedPreferences.getString(PREFERENCE_NAME, "0"));
                break;
            }
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d("___", "IM IN UPDATE");
        int uri_type = sUriMatcher.match(uri);
        switch (uri_type) {
            case MATCH: {
                String new_val = values.getAsString(PREFERENCE_NAME);
                SharedPreferences mySharedPreferences = getContext().getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = mySharedPreferences.edit();
//                editor.putString(PREFERENCE_NAME, new_val);
//                editor.apply();

                mySharedPreferences
                        .edit()
                        .putString(PREFERENCE_NAME, new_val)
                        .apply();

                getContext().getContentResolver().notifyChange(URI,null);
                break;
            }
        }
        return 0;
    }
}
