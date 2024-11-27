package com.android.internal.org.bouncycastle.jcajce.provider.config;

/* loaded from: classes4.dex */
public class PKCS12StoreParameter extends com.android.internal.org.bouncycastle.jcajce.PKCS12StoreParameter {
    public PKCS12StoreParameter(java.io.OutputStream outputStream, char[] cArr) {
        super(outputStream, cArr, false);
    }

    public PKCS12StoreParameter(java.io.OutputStream outputStream, java.security.KeyStore.ProtectionParameter protectionParameter) {
        super(outputStream, protectionParameter, false);
    }

    public PKCS12StoreParameter(java.io.OutputStream outputStream, char[] cArr, boolean z) {
        super(outputStream, new java.security.KeyStore.PasswordProtection(cArr), z);
    }

    public PKCS12StoreParameter(java.io.OutputStream outputStream, java.security.KeyStore.ProtectionParameter protectionParameter, boolean z) {
        super(outputStream, protectionParameter, z);
    }
}
