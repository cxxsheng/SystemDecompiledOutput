package com.android.internal.org.bouncycastle.math.field;

/* loaded from: classes4.dex */
public abstract class FiniteFields {
    static final com.android.internal.org.bouncycastle.math.field.FiniteField GF_2 = new com.android.internal.org.bouncycastle.math.field.PrimeField(java.math.BigInteger.valueOf(2));
    static final com.android.internal.org.bouncycastle.math.field.FiniteField GF_3 = new com.android.internal.org.bouncycastle.math.field.PrimeField(java.math.BigInteger.valueOf(3));

    public static com.android.internal.org.bouncycastle.math.field.PolynomialExtensionField getBinaryExtensionField(int[] iArr) {
        if (iArr[0] != 0) {
            throw new java.lang.IllegalArgumentException("Irreducible polynomials in GF(2) must have constant term");
        }
        for (int i = 1; i < iArr.length; i++) {
            if (iArr[i] <= iArr[i - 1]) {
                throw new java.lang.IllegalArgumentException("Polynomial exponents must be monotonically increasing");
            }
        }
        return new com.android.internal.org.bouncycastle.math.field.GenericPolynomialExtensionField(GF_2, new com.android.internal.org.bouncycastle.math.field.GF2Polynomial(iArr));
    }

    public static com.android.internal.org.bouncycastle.math.field.FiniteField getPrimeField(java.math.BigInteger bigInteger) {
        int bitLength = bigInteger.bitLength();
        if (bigInteger.signum() <= 0 || bitLength < 2) {
            throw new java.lang.IllegalArgumentException("'characteristic' must be >= 2");
        }
        if (bitLength < 3) {
            switch (bigInteger.intValue()) {
                case 2:
                    return GF_2;
                case 3:
                    return GF_3;
            }
        }
        return new com.android.internal.org.bouncycastle.math.field.PrimeField(bigInteger);
    }
}
