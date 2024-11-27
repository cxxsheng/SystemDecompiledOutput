package com.android.internal.org.bouncycastle.jcajce;

/* loaded from: classes4.dex */
public class PKCS12KeyWithParameters extends com.android.internal.org.bouncycastle.jcajce.PKCS12Key implements javax.crypto.interfaces.PBEKey {
    private final int iterationCount;
    private final byte[] salt;

    public PKCS12KeyWithParameters(char[] cArr, byte[] bArr, int i) {
        super(cArr);
        this.salt = com.android.internal.org.bouncycastle.util.Arrays.clone(bArr);
        this.iterationCount = i;
    }

    public PKCS12KeyWithParameters(char[] cArr, boolean z, byte[] bArr, int i) {
        super(cArr, z);
        this.salt = com.android.internal.org.bouncycastle.util.Arrays.clone(bArr);
        this.iterationCount = i;
    }

    @Override // javax.crypto.interfaces.PBEKey
    public byte[] getSalt() {
        return this.salt;
    }

    @Override // javax.crypto.interfaces.PBEKey
    public int getIterationCount() {
        return this.iterationCount;
    }
}
