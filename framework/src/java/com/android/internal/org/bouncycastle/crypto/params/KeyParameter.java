package com.android.internal.org.bouncycastle.crypto.params;

/* loaded from: classes4.dex */
public class KeyParameter implements com.android.internal.org.bouncycastle.crypto.CipherParameters {
    private byte[] key;

    public KeyParameter(byte[] bArr) {
        this(bArr, 0, bArr.length);
    }

    public KeyParameter(byte[] bArr, int i, int i2) {
        this.key = new byte[i2];
        java.lang.System.arraycopy(bArr, i, this.key, 0, i2);
    }

    public byte[] getKey() {
        return this.key;
    }
}
