package com.android.internal.org.bouncycastle.operator;

/* loaded from: classes4.dex */
public interface MacCalculator {
    com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getAlgorithmIdentifier();

    com.android.internal.org.bouncycastle.operator.GenericKey getKey();

    byte[] getMac();

    java.io.OutputStream getOutputStream();
}
