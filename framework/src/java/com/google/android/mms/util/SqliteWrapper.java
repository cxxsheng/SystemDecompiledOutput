package com.google.android.mms.util;

/* loaded from: classes5.dex */
public final class SqliteWrapper {
    private static final java.lang.String TAG = "SqliteWrapper";

    private SqliteWrapper() {
    }

    public static android.database.Cursor query(android.content.Context context, android.content.ContentResolver contentResolver, android.net.Uri uri, java.lang.String[] strArr, java.lang.String str, java.lang.String[] strArr2, java.lang.String str2) {
        try {
            return contentResolver.query(uri, strArr, str, strArr2, str2);
        } catch (android.database.sqlite.SQLiteException e) {
            android.util.Log.e(TAG, "Catch a SQLiteException when query: ", e);
            return null;
        }
    }

    public static int update(android.content.Context context, android.content.ContentResolver contentResolver, android.net.Uri uri, android.content.ContentValues contentValues, java.lang.String str, java.lang.String[] strArr) {
        try {
            return contentResolver.update(uri, contentValues, str, strArr);
        } catch (android.database.sqlite.SQLiteException e) {
            android.util.Log.e(TAG, "Catch a SQLiteException when update: ", e);
            return -1;
        }
    }

    public static int delete(android.content.Context context, android.content.ContentResolver contentResolver, android.net.Uri uri, java.lang.String str, java.lang.String[] strArr) {
        try {
            return contentResolver.delete(uri, str, strArr);
        } catch (android.database.sqlite.SQLiteException e) {
            android.util.Log.e(TAG, "Catch a SQLiteException when delete: ", e);
            return -1;
        }
    }

    public static android.net.Uri insert(android.content.Context context, android.content.ContentResolver contentResolver, android.net.Uri uri, android.content.ContentValues contentValues) {
        try {
            return contentResolver.insert(uri, contentValues);
        } catch (android.database.sqlite.SQLiteException e) {
            android.util.Log.e(TAG, "Catch a SQLiteException when insert: ", e);
            return null;
        }
    }
}
