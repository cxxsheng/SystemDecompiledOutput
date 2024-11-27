package com.android.internal.org.bouncycastle.jcajce.provider.util;

/* loaded from: classes4.dex */
public interface AsymmetricKeyInfoConverter {
    java.security.PrivateKey generatePrivate(com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo privateKeyInfo) throws java.io.IOException;

    java.security.PublicKey generatePublic(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo) throws java.io.IOException;
}
