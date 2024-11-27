package com.android.internal.org.bouncycastle.jcajce.spec;

/* loaded from: classes4.dex */
public class AEADParameterSpec extends javax.crypto.spec.IvParameterSpec {
    private final byte[] associatedData;
    private final int macSizeInBits;

    public AEADParameterSpec(byte[] bArr, int i) {
        this(bArr, i, null);
    }

    public AEADParameterSpec(byte[] bArr, int i, byte[] bArr2) {
        super(bArr);
        this.macSizeInBits = i;
        this.associatedData = com.android.internal.org.bouncycastle.util.Arrays.clone(bArr2);
    }

    public int getMacSizeInBits() {
        return this.macSizeInBits;
    }

    public byte[] getAssociatedData() {
        return com.android.internal.org.bouncycastle.util.Arrays.clone(this.associatedData);
    }

    public byte[] getNonce() {
        return getIV();
    }
}
