package com.android.internal.org.bouncycastle.crypto.tls;

/* loaded from: classes4.dex */
public class TlsNoCloseNotifyException extends java.io.EOFException {
    public TlsNoCloseNotifyException() {
        super("No close_notify alert received before connection closed");
    }
}
