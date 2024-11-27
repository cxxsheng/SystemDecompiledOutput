package com.android.internal.org.bouncycastle.jcajce.spec;

/* loaded from: classes4.dex */
public class OpenSSHPrivateKeySpec extends java.security.spec.EncodedKeySpec {
    private final java.lang.String format;

    public OpenSSHPrivateKeySpec(byte[] bArr) {
        super(bArr);
        if (bArr[0] == 48) {
            this.format = "ASN.1";
        } else {
            if (bArr[0] == 111) {
                this.format = "OpenSSH";
                return;
            }
            throw new java.lang.IllegalArgumentException("unknown byte encoding");
        }
    }

    @Override // java.security.spec.EncodedKeySpec
    public java.lang.String getFormat() {
        return this.format;
    }
}
