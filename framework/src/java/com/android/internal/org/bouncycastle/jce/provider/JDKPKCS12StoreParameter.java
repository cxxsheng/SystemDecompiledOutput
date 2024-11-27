package com.android.internal.org.bouncycastle.jce.provider;

/* loaded from: classes4.dex */
public class JDKPKCS12StoreParameter implements java.security.KeyStore.LoadStoreParameter {
    private java.io.OutputStream outputStream;
    private java.security.KeyStore.ProtectionParameter protectionParameter;
    private boolean useDEREncoding;

    public java.io.OutputStream getOutputStream() {
        return this.outputStream;
    }

    @Override // java.security.KeyStore.LoadStoreParameter
    public java.security.KeyStore.ProtectionParameter getProtectionParameter() {
        return this.protectionParameter;
    }

    public boolean isUseDEREncoding() {
        return this.useDEREncoding;
    }

    public void setOutputStream(java.io.OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void setPassword(char[] cArr) {
        this.protectionParameter = new java.security.KeyStore.PasswordProtection(cArr);
    }

    public void setProtectionParameter(java.security.KeyStore.ProtectionParameter protectionParameter) {
        this.protectionParameter = protectionParameter;
    }

    public void setUseDEREncoding(boolean z) {
        this.useDEREncoding = z;
    }
}
