package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util;

/* loaded from: classes4.dex */
public interface DSAEncoder {
    java.math.BigInteger[] decode(byte[] bArr) throws java.io.IOException;

    byte[] encode(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2) throws java.io.IOException;
}
