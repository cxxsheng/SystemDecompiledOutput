package com.android.internal.org.bouncycastle.crypto.params;

/* loaded from: classes4.dex */
public class DHValidationParameters {
    private int counter;
    private byte[] seed;

    public DHValidationParameters(byte[] bArr, int i) {
        this.seed = com.android.internal.org.bouncycastle.util.Arrays.clone(bArr);
        this.counter = i;
    }

    public int getCounter() {
        return this.counter;
    }

    public byte[] getSeed() {
        return com.android.internal.org.bouncycastle.util.Arrays.clone(this.seed);
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof com.android.internal.org.bouncycastle.crypto.params.DHValidationParameters)) {
            return false;
        }
        com.android.internal.org.bouncycastle.crypto.params.DHValidationParameters dHValidationParameters = (com.android.internal.org.bouncycastle.crypto.params.DHValidationParameters) obj;
        if (dHValidationParameters.counter != this.counter) {
            return false;
        }
        return com.android.internal.org.bouncycastle.util.Arrays.areEqual(this.seed, dHValidationParameters.seed);
    }

    public int hashCode() {
        return this.counter ^ com.android.internal.org.bouncycastle.util.Arrays.hashCode(this.seed);
    }
}
