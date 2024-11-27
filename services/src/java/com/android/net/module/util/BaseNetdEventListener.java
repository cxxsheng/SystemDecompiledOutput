package com.android.net.module.util;

/* loaded from: classes.dex */
public class BaseNetdEventListener extends android.net.metrics.INetdEventListener.Stub {
    @Override // android.net.metrics.INetdEventListener
    public void onDnsEvent(int i, int i2, int i3, int i4, java.lang.String str, java.lang.String[] strArr, int i5, int i6) {
    }

    @Override // android.net.metrics.INetdEventListener
    public void onPrivateDnsValidationEvent(int i, java.lang.String str, java.lang.String str2, boolean z) {
    }

    @Override // android.net.metrics.INetdEventListener
    public void onConnectEvent(int i, int i2, int i3, java.lang.String str, int i4, int i5) {
    }

    @Override // android.net.metrics.INetdEventListener
    public void onWakeupEvent(java.lang.String str, int i, int i2, int i3, byte[] bArr, java.lang.String str2, java.lang.String str3, int i4, int i5, long j) {
    }

    @Override // android.net.metrics.INetdEventListener
    public void onTcpSocketStatsEvent(int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, int[] iArr5) {
    }

    @Override // android.net.metrics.INetdEventListener
    public void onNat64PrefixEvent(int i, boolean z, java.lang.String str, int i2) {
    }

    @Override // android.net.metrics.INetdEventListener
    public int getInterfaceVersion() {
        return 1;
    }

    @Override // android.net.metrics.INetdEventListener
    public java.lang.String getInterfaceHash() {
        return android.net.metrics.INetdEventListener.HASH;
    }
}
