package com.android.internal.org.bouncycastle.jce.provider;

/* loaded from: classes4.dex */
class PrincipalUtils {
    PrincipalUtils() {
    }

    static com.android.internal.org.bouncycastle.asn1.x500.X500Name getCA(java.security.cert.TrustAnchor trustAnchor) {
        return getX500Name(notNull(trustAnchor).getCA());
    }

    static com.android.internal.org.bouncycastle.asn1.x500.X500Name getEncodedIssuerPrincipal(java.lang.Object obj) {
        if (obj instanceof java.security.cert.X509Certificate) {
            return getIssuerPrincipal((java.security.cert.X509Certificate) obj);
        }
        return getX500Name((javax.security.auth.x500.X500Principal) ((com.android.internal.org.bouncycastle.x509.X509AttributeCertificate) obj).getIssuer().getPrincipals()[0]);
    }

    static com.android.internal.org.bouncycastle.asn1.x500.X500Name getIssuerPrincipal(java.security.cert.X509Certificate x509Certificate) {
        return getX500Name(notNull(x509Certificate).getIssuerX500Principal());
    }

    static com.android.internal.org.bouncycastle.asn1.x500.X500Name getIssuerPrincipal(java.security.cert.X509CRL x509crl) {
        return getX500Name(notNull(x509crl).getIssuerX500Principal());
    }

    static com.android.internal.org.bouncycastle.asn1.x500.X500Name getSubjectPrincipal(java.security.cert.X509Certificate x509Certificate) {
        return getX500Name(notNull(x509Certificate).getSubjectX500Principal());
    }

    static com.android.internal.org.bouncycastle.asn1.x500.X500Name getX500Name(javax.security.auth.x500.X500Principal x500Principal) {
        return notNull(com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(getEncoded(x500Principal)));
    }

    static com.android.internal.org.bouncycastle.asn1.x500.X500Name getX500Name(com.android.internal.org.bouncycastle.asn1.x500.X500NameStyle x500NameStyle, javax.security.auth.x500.X500Principal x500Principal) {
        return notNull(com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(x500NameStyle, getEncoded(x500Principal)));
    }

    private static byte[] getEncoded(javax.security.auth.x500.X500Principal x500Principal) {
        return notNull(notNull(x500Principal).getEncoded());
    }

    private static byte[] notNull(byte[] bArr) {
        if (bArr == null) {
            throw new java.lang.IllegalStateException();
        }
        return bArr;
    }

    private static java.security.cert.TrustAnchor notNull(java.security.cert.TrustAnchor trustAnchor) {
        if (trustAnchor == null) {
            throw new java.lang.IllegalStateException();
        }
        return trustAnchor;
    }

    private static java.security.cert.X509Certificate notNull(java.security.cert.X509Certificate x509Certificate) {
        if (x509Certificate == null) {
            throw new java.lang.IllegalStateException();
        }
        return x509Certificate;
    }

    private static java.security.cert.X509CRL notNull(java.security.cert.X509CRL x509crl) {
        if (x509crl == null) {
            throw new java.lang.IllegalStateException();
        }
        return x509crl;
    }

    private static com.android.internal.org.bouncycastle.asn1.x500.X500Name notNull(com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name) {
        if (x500Name == null) {
            throw new java.lang.IllegalStateException();
        }
        return x500Name;
    }

    private static javax.security.auth.x500.X500Principal notNull(javax.security.auth.x500.X500Principal x500Principal) {
        if (x500Principal == null) {
            throw new java.lang.IllegalStateException();
        }
        return x500Principal;
    }
}
