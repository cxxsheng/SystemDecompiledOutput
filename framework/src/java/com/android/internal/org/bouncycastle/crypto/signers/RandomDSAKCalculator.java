package com.android.internal.org.bouncycastle.crypto.signers;

/* loaded from: classes4.dex */
public class RandomDSAKCalculator implements com.android.internal.org.bouncycastle.crypto.signers.DSAKCalculator {
    private static final java.math.BigInteger ZERO = java.math.BigInteger.valueOf(0);
    private java.math.BigInteger q;
    private java.security.SecureRandom random;

    @Override // com.android.internal.org.bouncycastle.crypto.signers.DSAKCalculator
    public boolean isDeterministic() {
        return false;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.signers.DSAKCalculator
    public void init(java.math.BigInteger bigInteger, java.security.SecureRandom secureRandom) {
        this.q = bigInteger;
        this.random = secureRandom;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.signers.DSAKCalculator
    public void init(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, byte[] bArr) {
        throw new java.lang.IllegalStateException("Operation not supported");
    }

    @Override // com.android.internal.org.bouncycastle.crypto.signers.DSAKCalculator
    public java.math.BigInteger nextK() {
        int bitLength = this.q.bitLength();
        while (true) {
            java.math.BigInteger createRandomBigInteger = com.android.internal.org.bouncycastle.util.BigIntegers.createRandomBigInteger(bitLength, this.random);
            if (!createRandomBigInteger.equals(ZERO) && createRandomBigInteger.compareTo(this.q) < 0) {
                return createRandomBigInteger;
            }
        }
    }
}
