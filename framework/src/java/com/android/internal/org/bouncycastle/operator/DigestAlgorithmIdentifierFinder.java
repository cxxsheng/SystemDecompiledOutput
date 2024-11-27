package com.android.internal.org.bouncycastle.operator;

/* loaded from: classes4.dex */
public interface DigestAlgorithmIdentifierFinder {
    com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier find(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier);

    com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier find(java.lang.String str);
}
