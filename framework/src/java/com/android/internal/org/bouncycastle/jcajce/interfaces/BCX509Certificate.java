package com.android.internal.org.bouncycastle.jcajce.interfaces;

/* loaded from: classes4.dex */
public interface BCX509Certificate {
    com.android.internal.org.bouncycastle.asn1.x500.X500Name getIssuerX500Name();

    com.android.internal.org.bouncycastle.asn1.x500.X500Name getSubjectX500Name();

    com.android.internal.org.bouncycastle.asn1.x509.TBSCertificate getTBSCertificateNative();
}
