package android.content;

/* loaded from: classes.dex */
public interface SharedPreferences {

    public interface Editor {
        void apply();

        android.content.SharedPreferences.Editor clear();

        boolean commit();

        android.content.SharedPreferences.Editor putBoolean(java.lang.String str, boolean z);

        android.content.SharedPreferences.Editor putFloat(java.lang.String str, float f);

        android.content.SharedPreferences.Editor putInt(java.lang.String str, int i);

        android.content.SharedPreferences.Editor putLong(java.lang.String str, long j);

        android.content.SharedPreferences.Editor putString(java.lang.String str, java.lang.String str2);

        android.content.SharedPreferences.Editor putStringSet(java.lang.String str, java.util.Set<java.lang.String> set);

        android.content.SharedPreferences.Editor remove(java.lang.String str);
    }

    public interface OnSharedPreferenceChangeListener {
        void onSharedPreferenceChanged(android.content.SharedPreferences sharedPreferences, java.lang.String str);
    }

    boolean contains(java.lang.String str);

    android.content.SharedPreferences.Editor edit();

    java.util.Map<java.lang.String, ?> getAll();

    boolean getBoolean(java.lang.String str, boolean z);

    float getFloat(java.lang.String str, float f);

    int getInt(java.lang.String str, int i);

    long getLong(java.lang.String str, long j);

    java.lang.String getString(java.lang.String str, java.lang.String str2);

    java.util.Set<java.lang.String> getStringSet(java.lang.String str, java.util.Set<java.lang.String> set);

    void registerOnSharedPreferenceChangeListener(android.content.SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener);

    void unregisterOnSharedPreferenceChangeListener(android.content.SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener);
}
