package com.android.internal.org.bouncycastle.operator;

/* loaded from: classes4.dex */
public interface ContentVerifierProvider {
    com.android.internal.org.bouncycastle.operator.ContentVerifier get(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException;

    com.android.internal.org.bouncycastle.cert.X509CertificateHolder getAssociatedCertificate();

    boolean hasAssociatedCertificate();
}
