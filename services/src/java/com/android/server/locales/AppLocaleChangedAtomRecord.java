package com.android.server.locales;

/* loaded from: classes2.dex */
public final class AppLocaleChangedAtomRecord {
    private static final java.lang.String DEFAULT_PREFIX = "default-";
    final int mCallingUid;
    java.lang.String mNewLocales;
    java.lang.String mPrevLocales;
    int mTargetUid = -1;
    int mStatus = 0;
    int mCaller = 0;

    AppLocaleChangedAtomRecord(int i) {
        this.mNewLocales = DEFAULT_PREFIX;
        this.mPrevLocales = DEFAULT_PREFIX;
        this.mCallingUid = i;
        java.util.Locale locale = java.util.Locale.getDefault();
        if (locale != null) {
            this.mNewLocales = DEFAULT_PREFIX + locale.toLanguageTag();
            this.mPrevLocales = DEFAULT_PREFIX + locale.toLanguageTag();
        }
    }

    void setNewLocales(java.lang.String str) {
        this.mNewLocales = convertEmptyLocales(str);
    }

    void setTargetUid(int i) {
        this.mTargetUid = i;
    }

    void setPrevLocales(java.lang.String str) {
        this.mPrevLocales = convertEmptyLocales(str);
    }

    void setStatus(int i) {
        this.mStatus = i;
    }

    void setCaller(int i) {
        this.mCaller = i;
    }

    private java.lang.String convertEmptyLocales(java.lang.String str) {
        java.util.Locale locale;
        if ("".equals(str) && (locale = java.util.Locale.getDefault()) != null) {
            return DEFAULT_PREFIX + locale.toLanguageTag();
        }
        return str;
    }
}
