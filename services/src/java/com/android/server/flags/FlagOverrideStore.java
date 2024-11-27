package com.android.server.flags;

@com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
/* loaded from: classes.dex */
public class FlagOverrideStore {
    private static final java.lang.String KEYNAME_PREFIX = "flag|";
    private static final java.lang.String NAMESPACE_NAME_SEPARATOR = ".";
    private com.android.server.flags.FlagOverrideStore.FlagChangeCallback mCallback;
    private final com.android.server.flags.SettingsProxy mSettingsProxy;

    interface FlagChangeCallback {
        void onFlagChanged(java.lang.String str, java.lang.String str2, java.lang.String str3);
    }

    FlagOverrideStore(com.android.server.flags.SettingsProxy settingsProxy) {
        this.mSettingsProxy = settingsProxy;
    }

    void setChangeCallback(com.android.server.flags.FlagOverrideStore.FlagChangeCallback flagChangeCallback) {
        this.mCallback = flagChangeCallback;
    }

    boolean contains(java.lang.String str, java.lang.String str2) {
        return get(str, str2) != null;
    }

    @com.android.internal.annotations.VisibleForTesting
    public void set(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        this.mSettingsProxy.putString(getPropName(str, str2), str3);
        this.mCallback.onFlagChanged(str, str2, str3);
    }

    @com.android.internal.annotations.VisibleForTesting
    public java.lang.String get(java.lang.String str, java.lang.String str2) {
        return this.mSettingsProxy.getString(getPropName(str, str2));
    }

    @com.android.internal.annotations.VisibleForTesting
    public void erase(java.lang.String str, java.lang.String str2) {
        set(str, str2, null);
    }

    java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.String>> getFlags() {
        return getFlagsForNamespace(null);
    }

    java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.String>> getFlagsForNamespace(java.lang.String str) {
        java.lang.String string;
        android.database.Cursor query = this.mSettingsProxy.getContentResolver().query(android.provider.Settings.Global.CONTENT_URI, new java.lang.String[]{"name", "value"}, null, null, null);
        if (query == null) {
            return java.util.Map.of();
        }
        int length = KEYNAME_PREFIX.length();
        java.util.HashMap hashMap = new java.util.HashMap();
        while (query.moveToNext()) {
            java.lang.String string2 = query.getString(0);
            if (string2.startsWith(KEYNAME_PREFIX) && string2.indexOf(NAMESPACE_NAME_SEPARATOR, length) >= 0 && (string = query.getString(1)) != null && !string.isEmpty()) {
                java.lang.String substring = string2.substring(length, string2.indexOf(NAMESPACE_NAME_SEPARATOR));
                if (str == null || str.equals(substring)) {
                    java.lang.String substring2 = string2.substring(string2.indexOf(NAMESPACE_NAME_SEPARATOR) + 1);
                    hashMap.putIfAbsent(substring, new java.util.HashMap());
                    ((java.util.Map) hashMap.get(substring)).put(substring2, string);
                }
            }
        }
        query.close();
        return hashMap;
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    static java.lang.String getPropName(java.lang.String str, java.lang.String str2) {
        return KEYNAME_PREFIX + str + NAMESPACE_NAME_SEPARATOR + str2;
    }
}
