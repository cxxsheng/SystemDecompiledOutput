package com.android.internal.org.bouncycastle.jcajce.spec;

/* loaded from: classes4.dex */
public class OpenSSHPublicKeySpec extends java.security.spec.EncodedKeySpec {
    private static final java.lang.String[] allowedTypes = {"ssh-rsa", "ssh-ed25519", "ssh-dss"};
    private final java.lang.String type;

    public OpenSSHPublicKeySpec(byte[] bArr) {
        super(bArr);
        int i = (((bArr[0] & 255) << 24) | ((bArr[1] & 255) << 16) | ((bArr[2] & 255) << 8) | (bArr[3] & 255)) + 4;
        if (i >= bArr.length) {
            throw new java.lang.IllegalArgumentException("invalid public key blob: type field longer than blob");
        }
        this.type = com.android.internal.org.bouncycastle.util.Strings.fromByteArray(com.android.internal.org.bouncycastle.util.Arrays.copyOfRange(bArr, 4, i));
        if (this.type.startsWith("ecdsa")) {
            return;
        }
        for (int i2 = 0; i2 < allowedTypes.length; i2++) {
            if (allowedTypes[i2].equals(this.type)) {
                return;
            }
        }
        throw new java.lang.IllegalArgumentException("unrecognised public key type " + this.type);
    }

    @Override // java.security.spec.EncodedKeySpec
    public java.lang.String getFormat() {
        return "OpenSSH";
    }

    public java.lang.String getType() {
        return this.type;
    }
}
