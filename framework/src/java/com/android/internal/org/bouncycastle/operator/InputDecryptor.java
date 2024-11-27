package com.android.internal.org.bouncycastle.operator;

/* loaded from: classes4.dex */
public interface InputDecryptor {
    com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getAlgorithmIdentifier();

    java.io.InputStream getInputStream(java.io.InputStream inputStream);
}
