package com.android.internal.os;

/* loaded from: classes4.dex */
public class FuseUnavailableMountException extends java.lang.Exception {
    public FuseUnavailableMountException(int i) {
        super("AppFuse mount point " + i + " is unavailable");
    }
}
