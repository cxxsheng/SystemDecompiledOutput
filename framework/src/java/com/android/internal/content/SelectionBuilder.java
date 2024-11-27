package com.android.internal.content;

/* loaded from: classes4.dex */
public class SelectionBuilder {
    private java.lang.StringBuilder mSelection = new java.lang.StringBuilder();
    private java.util.ArrayList<java.lang.String> mSelectionArgs = new java.util.ArrayList<>();

    public com.android.internal.content.SelectionBuilder reset() {
        this.mSelection.setLength(0);
        this.mSelectionArgs.clear();
        return this;
    }

    public com.android.internal.content.SelectionBuilder append(java.lang.String str, java.lang.Object... objArr) {
        if (android.text.TextUtils.isEmpty(str)) {
            if (objArr != null && objArr.length > 0) {
                throw new java.lang.IllegalArgumentException("Valid selection required when including arguments");
            }
            return this;
        }
        if (this.mSelection.length() > 0) {
            this.mSelection.append(" AND ");
        }
        this.mSelection.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START).append(str).append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        if (objArr != null) {
            for (java.lang.Object obj : objArr) {
                this.mSelectionArgs.add(java.lang.String.valueOf(obj));
            }
        }
        return this;
    }

    public java.lang.String getSelection() {
        return this.mSelection.toString();
    }

    public java.lang.String[] getSelectionArgs() {
        return (java.lang.String[]) this.mSelectionArgs.toArray(new java.lang.String[this.mSelectionArgs.size()]);
    }

    public android.database.Cursor query(android.database.sqlite.SQLiteDatabase sQLiteDatabase, java.lang.String str, java.lang.String[] strArr, java.lang.String str2) {
        return query(sQLiteDatabase, str, strArr, null, null, str2, null);
    }

    public android.database.Cursor query(android.database.sqlite.SQLiteDatabase sQLiteDatabase, java.lang.String str, java.lang.String[] strArr, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5) {
        return sQLiteDatabase.query(str, strArr, getSelection(), getSelectionArgs(), str2, str3, str4, str5);
    }

    public int update(android.database.sqlite.SQLiteDatabase sQLiteDatabase, java.lang.String str, android.content.ContentValues contentValues) {
        return sQLiteDatabase.update(str, contentValues, getSelection(), getSelectionArgs());
    }

    public int delete(android.database.sqlite.SQLiteDatabase sQLiteDatabase, java.lang.String str) {
        return sQLiteDatabase.delete(str, getSelection(), getSelectionArgs());
    }
}
