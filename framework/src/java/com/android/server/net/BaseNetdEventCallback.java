package com.android.server.net;

/* loaded from: classes5.dex */
public class BaseNetdEventCallback extends android.net.INetdEventCallback.Stub {
    @Override // android.net.INetdEventCallback
    public void onDnsEvent(int i, int i2, int i3, java.lang.String str, java.lang.String[] strArr, int i4, long j, int i5) {
    }

    @Override // android.net.INetdEventCallback
    public void onNat64PrefixEvent(int i, boolean z, java.lang.String str, int i2) {
    }

    @Override // android.net.INetdEventCallback
    public void onPrivateDnsValidationEvent(int i, java.lang.String str, java.lang.String str2, boolean z) {
    }

    @Override // android.net.INetdEventCallback
    public void onConnectEvent(java.lang.String str, int i, long j, int i2) {
    }
}
