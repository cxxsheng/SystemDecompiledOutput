package com.android.internal.org.bouncycastle.jcajce;

/* loaded from: classes4.dex */
public class PKCS12StoreParameter implements java.security.KeyStore.LoadStoreParameter {
    private final boolean forDEREncoding;
    private final java.io.OutputStream out;
    private final java.security.KeyStore.ProtectionParameter protectionParameter;

    public PKCS12StoreParameter(java.io.OutputStream outputStream, char[] cArr) {
        this(outputStream, cArr, false);
    }

    public PKCS12StoreParameter(java.io.OutputStream outputStream, java.security.KeyStore.ProtectionParameter protectionParameter) {
        this(outputStream, protectionParameter, false);
    }

    public PKCS12StoreParameter(java.io.OutputStream outputStream, char[] cArr, boolean z) {
        this(outputStream, new java.security.KeyStore.PasswordProtection(cArr), z);
    }

    public PKCS12StoreParameter(java.io.OutputStream outputStream, java.security.KeyStore.ProtectionParameter protectionParameter, boolean z) {
        this.out = outputStream;
        this.protectionParameter = protectionParameter;
        this.forDEREncoding = z;
    }

    public java.io.OutputStream getOutputStream() {
        return this.out;
    }

    @Override // java.security.KeyStore.LoadStoreParameter
    public java.security.KeyStore.ProtectionParameter getProtectionParameter() {
        return this.protectionParameter;
    }

    public boolean isForDEREncoding() {
        return this.forDEREncoding;
    }
}
