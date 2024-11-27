package com.android.internal.org.bouncycastle.crypto.signers;

/* loaded from: classes4.dex */
public class PlainDSAEncoding implements com.android.internal.org.bouncycastle.crypto.signers.DSAEncoding {
    public static final com.android.internal.org.bouncycastle.crypto.signers.PlainDSAEncoding INSTANCE = new com.android.internal.org.bouncycastle.crypto.signers.PlainDSAEncoding();

    @Override // com.android.internal.org.bouncycastle.crypto.signers.DSAEncoding
    public byte[] encode(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, java.math.BigInteger bigInteger3) {
        int unsignedByteLength = com.android.internal.org.bouncycastle.util.BigIntegers.getUnsignedByteLength(bigInteger);
        byte[] bArr = new byte[unsignedByteLength * 2];
        encodeValue(bigInteger, bigInteger2, bArr, 0, unsignedByteLength);
        encodeValue(bigInteger, bigInteger3, bArr, unsignedByteLength, unsignedByteLength);
        return bArr;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.signers.DSAEncoding
    public java.math.BigInteger[] decode(java.math.BigInteger bigInteger, byte[] bArr) {
        int unsignedByteLength = com.android.internal.org.bouncycastle.util.BigIntegers.getUnsignedByteLength(bigInteger);
        if (bArr.length != unsignedByteLength * 2) {
            throw new java.lang.IllegalArgumentException("Encoding has incorrect length");
        }
        return new java.math.BigInteger[]{decodeValue(bigInteger, bArr, 0, unsignedByteLength), decodeValue(bigInteger, bArr, unsignedByteLength, unsignedByteLength)};
    }

    protected java.math.BigInteger checkValue(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2) {
        if (bigInteger2.signum() < 0 || bigInteger2.compareTo(bigInteger) >= 0) {
            throw new java.lang.IllegalArgumentException("Value out of range");
        }
        return bigInteger2;
    }

    protected java.math.BigInteger decodeValue(java.math.BigInteger bigInteger, byte[] bArr, int i, int i2) {
        return checkValue(bigInteger, new java.math.BigInteger(1, com.android.internal.org.bouncycastle.util.Arrays.copyOfRange(bArr, i, i2 + i)));
    }

    private void encodeValue(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, byte[] bArr, int i, int i2) {
        byte[] byteArray = checkValue(bigInteger, bigInteger2).toByteArray();
        int max = java.lang.Math.max(0, byteArray.length - i2);
        int length = byteArray.length - max;
        int i3 = (i2 - length) + i;
        com.android.internal.org.bouncycastle.util.Arrays.fill(bArr, i, i3, (byte) 0);
        java.lang.System.arraycopy(byteArray, max, bArr, i3, length);
    }
}
