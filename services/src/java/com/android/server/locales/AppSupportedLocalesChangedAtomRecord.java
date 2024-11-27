package com.android.server.locales;

/* loaded from: classes2.dex */
public final class AppSupportedLocalesChangedAtomRecord {
    final int mCallingUid;
    int mTargetUid = -1;
    int mNumLocales = -1;
    boolean mOverrideRemoved = false;
    boolean mSameAsResConfig = false;
    boolean mSameAsPrevConfig = false;
    int mStatus = 0;

    AppSupportedLocalesChangedAtomRecord(int i) {
        this.mCallingUid = i;
    }

    void setTargetUid(int i) {
        this.mTargetUid = i;
    }

    void setNumLocales(int i) {
        this.mNumLocales = i;
    }

    void setOverrideRemoved(boolean z) {
        this.mOverrideRemoved = z;
    }

    void setSameAsResConfig(boolean z) {
        this.mSameAsResConfig = z;
    }

    void setSameAsPrevConfig(boolean z) {
        this.mSameAsPrevConfig = z;
    }

    void setStatus(int i) {
        this.mStatus = i;
    }
}
