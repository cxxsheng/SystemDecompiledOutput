package com.android.server.inputmethod;

/* loaded from: classes2.dex */
final class ImePlatformCompatUtils {
    private final com.android.internal.compat.IPlatformCompat mPlatformCompat = com.android.internal.compat.IPlatformCompat.Stub.asInterface(android.os.ServiceManager.getService("platform_compat"));

    ImePlatformCompatUtils() {
    }

    public boolean shouldUseSetInteractiveProtocol(int i) {
        return isChangeEnabledByUid(156215187L, i);
    }

    public boolean shouldClearShowForcedFlag(int i) {
        return isChangeEnabledByUid(214016041L, i);
    }

    private boolean isChangeEnabledByUid(long j, int i) {
        try {
            return this.mPlatformCompat.isChangeEnabledByUid(j, i);
        } catch (android.os.RemoteException e) {
            return false;
        }
    }
}
