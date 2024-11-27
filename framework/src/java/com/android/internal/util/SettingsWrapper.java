package com.android.internal.util;

/* loaded from: classes5.dex */
public class SettingsWrapper {
    public java.lang.String getStringForUser(android.content.ContentResolver contentResolver, java.lang.String str, int i) {
        return android.provider.Settings.System.getStringForUser(contentResolver, str, i);
    }

    public java.lang.String putStringForUser(android.content.ContentResolver contentResolver, java.lang.String str, int i) {
        return android.provider.Settings.System.getStringForUser(contentResolver, str, i);
    }
}
