package com.android.server.audio;

/* loaded from: classes.dex */
public class SettingsAdapter {
    public static com.android.server.audio.SettingsAdapter getDefaultAdapter() {
        return new com.android.server.audio.SettingsAdapter();
    }

    public int getGlobalInt(android.content.ContentResolver contentResolver, java.lang.String str, int i) {
        return android.provider.Settings.Global.getInt(contentResolver, str, i);
    }

    public java.lang.String getGlobalString(android.content.ContentResolver contentResolver, java.lang.String str) {
        return android.provider.Settings.Global.getString(contentResolver, str);
    }

    public boolean putGlobalInt(android.content.ContentResolver contentResolver, java.lang.String str, int i) {
        return android.provider.Settings.Global.putInt(contentResolver, str, i);
    }

    public boolean putGlobalString(android.content.ContentResolver contentResolver, java.lang.String str, java.lang.String str2) {
        return android.provider.Settings.Global.putString(contentResolver, str, str2);
    }

    public int getSystemIntForUser(android.content.ContentResolver contentResolver, java.lang.String str, int i, int i2) {
        return android.provider.Settings.System.getIntForUser(contentResolver, str, i, i2);
    }

    public boolean putSystemIntForUser(android.content.ContentResolver contentResolver, java.lang.String str, int i, int i2) {
        return android.provider.Settings.System.putIntForUser(contentResolver, str, i, i2);
    }

    public int getSecureIntForUser(android.content.ContentResolver contentResolver, java.lang.String str, int i, int i2) {
        return android.provider.Settings.Secure.getIntForUser(contentResolver, str, i, i2);
    }

    public java.lang.String getSecureStringForUser(android.content.ContentResolver contentResolver, java.lang.String str, int i) {
        return android.provider.Settings.Secure.getStringForUser(contentResolver, str, i);
    }

    public boolean putSecureIntForUser(android.content.ContentResolver contentResolver, java.lang.String str, int i, int i2) {
        return android.provider.Settings.Secure.putIntForUser(contentResolver, str, i, i2);
    }

    public boolean putSecureStringForUser(android.content.ContentResolver contentResolver, java.lang.String str, java.lang.String str2, int i) {
        return android.provider.Settings.Secure.putStringForUser(contentResolver, str, str2, i);
    }
}
