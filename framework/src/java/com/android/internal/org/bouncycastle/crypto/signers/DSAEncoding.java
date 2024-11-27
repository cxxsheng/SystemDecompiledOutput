package com.android.internal.org.bouncycastle.crypto.signers;

/* loaded from: classes4.dex */
public interface DSAEncoding {
    java.math.BigInteger[] decode(java.math.BigInteger bigInteger, byte[] bArr) throws java.io.IOException;

    byte[] encode(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, java.math.BigInteger bigInteger3) throws java.io.IOException;
}
