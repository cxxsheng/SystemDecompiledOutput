package com.android.internal.org.bouncycastle.crypto.params;

/* loaded from: classes4.dex */
public class DHPublicKeyParameters extends com.android.internal.org.bouncycastle.crypto.params.DHKeyParameters {
    private static final java.math.BigInteger ONE = java.math.BigInteger.valueOf(1);
    private static final java.math.BigInteger TWO = java.math.BigInteger.valueOf(2);
    private java.math.BigInteger y;

    public DHPublicKeyParameters(java.math.BigInteger bigInteger, com.android.internal.org.bouncycastle.crypto.params.DHParameters dHParameters) {
        super(false, dHParameters);
        this.y = validate(bigInteger, dHParameters);
    }

    private java.math.BigInteger validate(java.math.BigInteger bigInteger, com.android.internal.org.bouncycastle.crypto.params.DHParameters dHParameters) {
        if (bigInteger == null) {
            throw new java.lang.NullPointerException("y value cannot be null");
        }
        java.math.BigInteger p = dHParameters.getP();
        if (bigInteger.compareTo(TWO) < 0 || bigInteger.compareTo(p.subtract(TWO)) > 0) {
            throw new java.lang.IllegalArgumentException("invalid DH public key");
        }
        java.math.BigInteger q = dHParameters.getQ();
        if (q == null) {
            return bigInteger;
        }
        if (p.testBit(0) && p.bitLength() - 1 == q.bitLength() && p.shiftRight(1).equals(q)) {
            if (1 == legendre(bigInteger, p)) {
                return bigInteger;
            }
        } else if (ONE.equals(bigInteger.modPow(q, p))) {
            return bigInteger;
        }
        throw new java.lang.IllegalArgumentException("Y value does not appear to be in correct group");
    }

    public java.math.BigInteger getY() {
        return this.y;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.params.DHKeyParameters
    public int hashCode() {
        return this.y.hashCode() ^ super.hashCode();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.params.DHKeyParameters
    public boolean equals(java.lang.Object obj) {
        return (obj instanceof com.android.internal.org.bouncycastle.crypto.params.DHPublicKeyParameters) && ((com.android.internal.org.bouncycastle.crypto.params.DHPublicKeyParameters) obj).getY().equals(this.y) && super.equals(obj);
    }

    private static int legendre(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2) {
        int bitLength = bigInteger2.bitLength();
        int[] fromBigInteger = com.android.internal.org.bouncycastle.math.raw.Nat.fromBigInteger(bitLength, bigInteger);
        int[] fromBigInteger2 = com.android.internal.org.bouncycastle.math.raw.Nat.fromBigInteger(bitLength, bigInteger2);
        int length = fromBigInteger2.length;
        int i = 0;
        while (true) {
            if (fromBigInteger[0] == 0) {
                com.android.internal.org.bouncycastle.math.raw.Nat.shiftDownWord(length, fromBigInteger, 0);
            } else {
                int numberOfTrailingZeros = com.android.internal.org.bouncycastle.util.Integers.numberOfTrailingZeros(fromBigInteger[0]);
                if (numberOfTrailingZeros > 0) {
                    com.android.internal.org.bouncycastle.math.raw.Nat.shiftDownBits(length, fromBigInteger, numberOfTrailingZeros, 0);
                    int i2 = fromBigInteger2[0];
                    i ^= (numberOfTrailingZeros << 1) & (i2 ^ (i2 >>> 1));
                }
                int compare = com.android.internal.org.bouncycastle.math.raw.Nat.compare(length, fromBigInteger, fromBigInteger2);
                if (compare == 0) {
                    break;
                }
                if (compare < 0) {
                    i ^= fromBigInteger[0] & fromBigInteger2[0];
                    int[] iArr = fromBigInteger2;
                    fromBigInteger2 = fromBigInteger;
                    fromBigInteger = iArr;
                }
                while (true) {
                    int i3 = length - 1;
                    if (fromBigInteger[i3] != 0) {
                        break;
                    }
                    length = i3;
                }
                com.android.internal.org.bouncycastle.math.raw.Nat.sub(length, fromBigInteger, fromBigInteger2, fromBigInteger);
            }
        }
        if (com.android.internal.org.bouncycastle.math.raw.Nat.isOne(length, fromBigInteger2)) {
            return 1 - (i & 2);
        }
        return 0;
    }
}
