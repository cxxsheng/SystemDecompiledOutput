package com.android.net.module.util;

/* loaded from: classes.dex */
public class BaseNetdUnsolicitedEventListener extends android.net.INetdUnsolicitedEventListener.Stub {
    @Override // android.net.INetdUnsolicitedEventListener
    public void onInterfaceClassActivityChanged(boolean z, int i, long j, int i2) {
    }

    @Override // android.net.INetdUnsolicitedEventListener
    public void onQuotaLimitReached(@androidx.annotation.NonNull java.lang.String str, @androidx.annotation.NonNull java.lang.String str2) {
    }

    @Override // android.net.INetdUnsolicitedEventListener
    public void onInterfaceDnsServerInfo(@androidx.annotation.NonNull java.lang.String str, long j, @androidx.annotation.NonNull java.lang.String[] strArr) {
    }

    @Override // android.net.INetdUnsolicitedEventListener
    public void onInterfaceAddressUpdated(@androidx.annotation.NonNull java.lang.String str, java.lang.String str2, int i, int i2) {
    }

    @Override // android.net.INetdUnsolicitedEventListener
    public void onInterfaceAddressRemoved(@androidx.annotation.NonNull java.lang.String str, @androidx.annotation.NonNull java.lang.String str2, int i, int i2) {
    }

    @Override // android.net.INetdUnsolicitedEventListener
    public void onInterfaceAdded(@androidx.annotation.NonNull java.lang.String str) {
    }

    @Override // android.net.INetdUnsolicitedEventListener
    public void onInterfaceRemoved(@androidx.annotation.NonNull java.lang.String str) {
    }

    @Override // android.net.INetdUnsolicitedEventListener
    public void onInterfaceChanged(@androidx.annotation.NonNull java.lang.String str, boolean z) {
    }

    @Override // android.net.INetdUnsolicitedEventListener
    public void onInterfaceLinkStateChanged(@androidx.annotation.NonNull java.lang.String str, boolean z) {
    }

    @Override // android.net.INetdUnsolicitedEventListener
    public void onRouteChanged(boolean z, @androidx.annotation.NonNull java.lang.String str, @androidx.annotation.NonNull java.lang.String str2, @androidx.annotation.NonNull java.lang.String str3) {
    }

    @Override // android.net.INetdUnsolicitedEventListener
    public void onStrictCleartextDetected(int i, @androidx.annotation.NonNull java.lang.String str) {
    }

    @Override // android.net.INetdUnsolicitedEventListener
    public int getInterfaceVersion() {
        return 14;
    }

    @Override // android.net.INetdUnsolicitedEventListener
    public java.lang.String getInterfaceHash() {
        return "50bce96bc8d5811ed952950df30ec503f8a561ed";
    }
}
