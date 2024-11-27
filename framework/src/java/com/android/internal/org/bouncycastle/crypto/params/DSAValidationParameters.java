package com.android.internal.org.bouncycastle.crypto.params;

/* loaded from: classes4.dex */
public class DSAValidationParameters {
    private int counter;
    private byte[] seed;
    private int usageIndex;

    public DSAValidationParameters(byte[] bArr, int i) {
        this(bArr, i, -1);
    }

    public DSAValidationParameters(byte[] bArr, int i, int i2) {
        this.seed = com.android.internal.org.bouncycastle.util.Arrays.clone(bArr);
        this.counter = i;
        this.usageIndex = i2;
    }

    public int getCounter() {
        return this.counter;
    }

    public byte[] getSeed() {
        return com.android.internal.org.bouncycastle.util.Arrays.clone(this.seed);
    }

    public int getUsageIndex() {
        return this.usageIndex;
    }

    public int hashCode() {
        return this.counter ^ com.android.internal.org.bouncycastle.util.Arrays.hashCode(this.seed);
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof com.android.internal.org.bouncycastle.crypto.params.DSAValidationParameters)) {
            return false;
        }
        com.android.internal.org.bouncycastle.crypto.params.DSAValidationParameters dSAValidationParameters = (com.android.internal.org.bouncycastle.crypto.params.DSAValidationParameters) obj;
        if (dSAValidationParameters.counter != this.counter) {
            return false;
        }
        return com.android.internal.org.bouncycastle.util.Arrays.areEqual(this.seed, dSAValidationParameters.seed);
    }
}
