package com.android.internal.org.bouncycastle.jcajce;

/* loaded from: classes4.dex */
public class PKCS12Key implements com.android.internal.org.bouncycastle.jcajce.PBKDFKey {
    private final char[] password;
    private final boolean useWrongZeroLengthConversion;

    public PKCS12Key(char[] cArr) {
        this(cArr, false);
    }

    public PKCS12Key(char[] cArr, boolean z) {
        cArr = cArr == null ? new char[0] : cArr;
        this.password = new char[cArr.length];
        this.useWrongZeroLengthConversion = z;
        java.lang.System.arraycopy(cArr, 0, this.password, 0, cArr.length);
    }

    public char[] getPassword() {
        return this.password;
    }

    @Override // java.security.Key
    public java.lang.String getAlgorithm() {
        return android.security.KeyChain.EXTRA_PKCS12;
    }

    @Override // java.security.Key
    public java.lang.String getFormat() {
        return android.security.KeyChain.EXTRA_PKCS12;
    }

    @Override // java.security.Key
    public byte[] getEncoded() {
        if (this.useWrongZeroLengthConversion && this.password.length == 0) {
            return new byte[2];
        }
        return com.android.internal.org.bouncycastle.crypto.PBEParametersGenerator.PKCS12PasswordToBytes(this.password);
    }
}
