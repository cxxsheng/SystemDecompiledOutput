package com.android.server.backup;

/* loaded from: classes.dex */
public class UserBackupPreferences {
    private static final java.lang.String PREFERENCES_FILE = "backup_preferences";
    private final android.content.SharedPreferences.Editor mEditor;
    private final android.content.SharedPreferences mPreferences;

    UserBackupPreferences(android.content.Context context, java.io.File file) {
        this.mPreferences = context.getSharedPreferences(new java.io.File(file, PREFERENCES_FILE), 0);
        this.mEditor = this.mPreferences.edit();
    }

    void addExcludedKeys(java.lang.String str, java.util.List<java.lang.String> list) {
        java.util.HashSet hashSet = new java.util.HashSet(this.mPreferences.getStringSet(str, java.util.Collections.emptySet()));
        hashSet.addAll(list);
        this.mEditor.putStringSet(str, hashSet);
        this.mEditor.commit();
    }

    java.util.Set<java.lang.String> getExcludedRestoreKeysForPackage(java.lang.String str) {
        return this.mPreferences.getStringSet(str, java.util.Collections.emptySet());
    }
}
