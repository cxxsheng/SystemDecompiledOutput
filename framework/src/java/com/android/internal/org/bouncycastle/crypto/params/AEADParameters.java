package com.android.internal.org.bouncycastle.crypto.params;

/* loaded from: classes4.dex */
public class AEADParameters implements com.android.internal.org.bouncycastle.crypto.CipherParameters {
    private byte[] associatedText;
    private com.android.internal.org.bouncycastle.crypto.params.KeyParameter key;
    private int macSize;
    private byte[] nonce;

    public AEADParameters(com.android.internal.org.bouncycastle.crypto.params.KeyParameter keyParameter, int i, byte[] bArr) {
        this(keyParameter, i, bArr, null);
    }

    public AEADParameters(com.android.internal.org.bouncycastle.crypto.params.KeyParameter keyParameter, int i, byte[] bArr, byte[] bArr2) {
        this.key = keyParameter;
        this.nonce = com.android.internal.org.bouncycastle.util.Arrays.clone(bArr);
        this.macSize = i;
        this.associatedText = com.android.internal.org.bouncycastle.util.Arrays.clone(bArr2);
    }

    public com.android.internal.org.bouncycastle.crypto.params.KeyParameter getKey() {
        return this.key;
    }

    public int getMacSize() {
        return this.macSize;
    }

    public byte[] getAssociatedText() {
        return com.android.internal.org.bouncycastle.util.Arrays.clone(this.associatedText);
    }

    public byte[] getNonce() {
        return com.android.internal.org.bouncycastle.util.Arrays.clone(this.nonce);
    }
}
