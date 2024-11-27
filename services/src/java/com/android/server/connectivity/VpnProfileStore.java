package com.android.server.connectivity;

/* loaded from: classes.dex */
public class VpnProfileStore {
    @com.android.internal.annotations.VisibleForTesting
    public boolean put(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull byte[] bArr) {
        return android.security.LegacyVpnProfileStore.put(str, bArr);
    }

    @com.android.internal.annotations.VisibleForTesting
    public byte[] get(@android.annotation.NonNull java.lang.String str) {
        return android.security.LegacyVpnProfileStore.get(str);
    }

    @com.android.internal.annotations.VisibleForTesting
    public boolean remove(@android.annotation.NonNull java.lang.String str) {
        return android.security.LegacyVpnProfileStore.remove(str);
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    public java.lang.String[] list(@android.annotation.NonNull java.lang.String str) {
        return android.security.LegacyVpnProfileStore.list(str);
    }
}
