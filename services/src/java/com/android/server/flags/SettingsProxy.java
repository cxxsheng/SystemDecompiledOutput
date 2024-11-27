package com.android.server.flags;

/* loaded from: classes.dex */
public interface SettingsProxy {
    android.content.ContentResolver getContentResolver();

    java.lang.String getStringForUser(java.lang.String str, int i);

    android.net.Uri getUriFor(java.lang.String str);

    boolean putString(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.lang.String str3, boolean z);

    boolean putString(java.lang.String str, java.lang.String str2, boolean z);

    boolean putStringForUser(java.lang.String str, java.lang.String str2, int i);

    boolean putStringForUser(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.lang.String str3, boolean z, int i, boolean z2);

    default int getUserId() {
        return getContentResolver().getUserId();
    }

    default java.lang.String getString(java.lang.String str) {
        return getStringForUser(str, getUserId());
    }

    default boolean putString(java.lang.String str, java.lang.String str2) {
        return putStringForUser(str, str2, getUserId());
    }

    default int getIntForUser(java.lang.String str, int i, int i2) {
        java.lang.String stringForUser = getStringForUser(str, i2);
        if (stringForUser == null) {
            return i;
        }
        try {
            return java.lang.Integer.parseInt(stringForUser);
        } catch (java.lang.NumberFormatException e) {
            return i;
        }
    }

    default int getInt(java.lang.String str) throws android.provider.Settings.SettingNotFoundException {
        return getIntForUser(str, getUserId());
    }

    default int getIntForUser(java.lang.String str, int i) throws android.provider.Settings.SettingNotFoundException {
        try {
            return java.lang.Integer.parseInt(getStringForUser(str, i));
        } catch (java.lang.NumberFormatException e) {
            throw new android.provider.Settings.SettingNotFoundException(str);
        }
    }

    default boolean putInt(java.lang.String str, int i) {
        return putIntForUser(str, i, getUserId());
    }

    default boolean putIntForUser(java.lang.String str, int i, int i2) {
        return putStringForUser(str, java.lang.Integer.toString(i), i2);
    }

    default boolean getBool(java.lang.String str, boolean z) {
        return getBoolForUser(str, z, getUserId());
    }

    default boolean getBoolForUser(java.lang.String str, boolean z, int i) {
        return getIntForUser(str, z ? 1 : 0, i) != 0;
    }

    default boolean getBool(java.lang.String str) throws android.provider.Settings.SettingNotFoundException {
        return getBoolForUser(str, getUserId());
    }

    default boolean getBoolForUser(java.lang.String str, int i) throws android.provider.Settings.SettingNotFoundException {
        return getIntForUser(str, i) != 0;
    }

    default boolean putBool(java.lang.String str, boolean z) {
        return putBoolForUser(str, z, getUserId());
    }

    default boolean putBoolForUser(java.lang.String str, boolean z, int i) {
        return putIntForUser(str, z ? 1 : 0, i);
    }

    default long getLong(java.lang.String str, long j) {
        return getLongForUser(str, j, getUserId());
    }

    default long getLongForUser(java.lang.String str, long j, int i) {
        java.lang.String stringForUser = getStringForUser(str, i);
        if (stringForUser != null) {
            try {
                return java.lang.Long.parseLong(stringForUser);
            } catch (java.lang.NumberFormatException e) {
                return j;
            }
        }
        return j;
    }

    default long getLong(java.lang.String str) throws android.provider.Settings.SettingNotFoundException {
        return getLongForUser(str, getUserId());
    }

    default long getLongForUser(java.lang.String str, int i) throws android.provider.Settings.SettingNotFoundException {
        try {
            return java.lang.Long.parseLong(getStringForUser(str, i));
        } catch (java.lang.NumberFormatException e) {
            throw new android.provider.Settings.SettingNotFoundException(str);
        }
    }

    default boolean putLong(java.lang.String str, long j) {
        return putLongForUser(str, j, getUserId());
    }

    default boolean putLongForUser(java.lang.String str, long j, int i) {
        return putStringForUser(str, java.lang.Long.toString(j), i);
    }

    default float getFloat(java.lang.String str, float f) {
        return getFloatForUser(str, f, getUserId());
    }

    default float getFloatForUser(java.lang.String str, float f, int i) {
        java.lang.String stringForUser = getStringForUser(str, i);
        if (stringForUser == null) {
            return f;
        }
        try {
            return java.lang.Float.parseFloat(stringForUser);
        } catch (java.lang.NumberFormatException e) {
            return f;
        }
    }

    default float getFloat(java.lang.String str) throws android.provider.Settings.SettingNotFoundException {
        return getFloatForUser(str, getUserId());
    }

    default float getFloatForUser(java.lang.String str, int i) throws android.provider.Settings.SettingNotFoundException {
        java.lang.String stringForUser = getStringForUser(str, i);
        if (stringForUser == null) {
            throw new android.provider.Settings.SettingNotFoundException(str);
        }
        try {
            return java.lang.Float.parseFloat(stringForUser);
        } catch (java.lang.NumberFormatException e) {
            throw new android.provider.Settings.SettingNotFoundException(str);
        }
    }

    default boolean putFloat(java.lang.String str, float f) {
        return putFloatForUser(str, f, getUserId());
    }

    default boolean putFloatForUser(java.lang.String str, float f, int i) {
        return putStringForUser(str, java.lang.Float.toString(f), i);
    }

    default void registerContentObserver(java.lang.String str, android.database.ContentObserver contentObserver) {
        registerContentObserver(getUriFor(str), contentObserver);
    }

    default void registerContentObserver(android.net.Uri uri, android.database.ContentObserver contentObserver) {
        registerContentObserverForUser(uri, contentObserver, getUserId());
    }

    default void registerContentObserver(java.lang.String str, boolean z, android.database.ContentObserver contentObserver) {
        registerContentObserver(getUriFor(str), z, contentObserver);
    }

    default void registerContentObserver(android.net.Uri uri, boolean z, android.database.ContentObserver contentObserver) {
        registerContentObserverForUser(uri, z, contentObserver, getUserId());
    }

    default void registerContentObserverForUser(java.lang.String str, android.database.ContentObserver contentObserver, int i) {
        registerContentObserverForUser(getUriFor(str), contentObserver, i);
    }

    default void registerContentObserverForUser(android.net.Uri uri, android.database.ContentObserver contentObserver, int i) {
        registerContentObserverForUser(uri, false, contentObserver, i);
    }

    default void registerContentObserverForUser(java.lang.String str, boolean z, android.database.ContentObserver contentObserver, int i) {
        registerContentObserverForUser(getUriFor(str), z, contentObserver, i);
    }

    default void registerContentObserverForUser(android.net.Uri uri, boolean z, android.database.ContentObserver contentObserver, int i) {
        getContentResolver().registerContentObserver(uri, z, contentObserver, i);
    }

    default void unregisterContentObserver(android.database.ContentObserver contentObserver) {
        getContentResolver().unregisterContentObserver(contentObserver);
    }
}
