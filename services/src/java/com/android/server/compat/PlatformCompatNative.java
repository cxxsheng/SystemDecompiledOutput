package com.android.server.compat;

/* loaded from: classes.dex */
public class PlatformCompatNative extends com.android.internal.compat.IPlatformCompatNative.Stub {
    private final com.android.server.compat.PlatformCompat mPlatformCompat;

    public PlatformCompatNative(com.android.server.compat.PlatformCompat platformCompat) {
        this.mPlatformCompat = platformCompat;
    }

    public void reportChangeByPackageName(long j, java.lang.String str, int i) {
        this.mPlatformCompat.reportChangeByPackageName(j, str, i);
    }

    public void reportChangeByUid(long j, int i) {
        this.mPlatformCompat.reportChangeByUid(j, i);
    }

    public boolean isChangeEnabledByPackageName(long j, java.lang.String str, int i) {
        return this.mPlatformCompat.isChangeEnabledByPackageName(j, str, i);
    }

    public boolean isChangeEnabledByUid(long j, int i) {
        return this.mPlatformCompat.isChangeEnabledByUid(j, i);
    }
}
