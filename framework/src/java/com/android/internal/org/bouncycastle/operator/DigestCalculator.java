package com.android.internal.org.bouncycastle.operator;

/* loaded from: classes4.dex */
public interface DigestCalculator {
    com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getAlgorithmIdentifier();

    byte[] getDigest();

    java.io.OutputStream getOutputStream();
}
