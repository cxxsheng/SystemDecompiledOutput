package com.android.internal.org.bouncycastle.operator;

/* loaded from: classes4.dex */
public interface ContentSigner {
    com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getAlgorithmIdentifier();

    java.io.OutputStream getOutputStream();

    byte[] getSignature();
}
