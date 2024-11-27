package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class X509Extension {
    boolean critical;
    com.android.internal.org.bouncycastle.asn1.ASN1OctetString value;
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier subjectDirectoryAttributes = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.9");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier subjectKeyIdentifier = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.14");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier keyUsage = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.15");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier privateKeyUsagePeriod = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.16");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier subjectAlternativeName = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.17");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier issuerAlternativeName = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.18");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier basicConstraints = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.19");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier cRLNumber = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.20");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier reasonCode = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.21");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier instructionCode = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.23");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier invalidityDate = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.24");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier deltaCRLIndicator = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.27");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier issuingDistributionPoint = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.28");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier certificateIssuer = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.29");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier nameConstraints = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.30");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier cRLDistributionPoints = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.31");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier certificatePolicies = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.32");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier policyMappings = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.33");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier authorityKeyIdentifier = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.35");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier policyConstraints = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.36");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier extendedKeyUsage = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.37");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier freshestCRL = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.46");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier inhibitAnyPolicy = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.54");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier authorityInfoAccess = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.1");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier subjectInfoAccess = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.11");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier logoType = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.12");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier biometricInfo = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.2");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier qCStatements = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.3");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier auditIdentity = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.4");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier noRevAvail = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.56");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier targetInformation = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.55");

    public X509Extension(com.android.internal.org.bouncycastle.asn1.ASN1Boolean aSN1Boolean, com.android.internal.org.bouncycastle.asn1.ASN1OctetString aSN1OctetString) {
        this.critical = aSN1Boolean.isTrue();
        this.value = aSN1OctetString;
    }

    public X509Extension(boolean z, com.android.internal.org.bouncycastle.asn1.ASN1OctetString aSN1OctetString) {
        this.critical = z;
        this.value = aSN1OctetString;
    }

    public boolean isCritical() {
        return this.critical;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1OctetString getValue() {
        return this.value;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Encodable getParsedValue() {
        return convertValueToObject(this);
    }

    public int hashCode() {
        if (isCritical()) {
            return getValue().hashCode();
        }
        return ~getValue().hashCode();
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof com.android.internal.org.bouncycastle.asn1.x509.X509Extension)) {
            return false;
        }
        com.android.internal.org.bouncycastle.asn1.x509.X509Extension x509Extension = (com.android.internal.org.bouncycastle.asn1.x509.X509Extension) obj;
        return x509Extension.getValue().equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) getValue()) && x509Extension.isCritical() == isCritical();
    }

    public static com.android.internal.org.bouncycastle.asn1.ASN1Primitive convertValueToObject(com.android.internal.org.bouncycastle.asn1.x509.X509Extension x509Extension) throws java.lang.IllegalArgumentException {
        try {
            return com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(x509Extension.getValue().getOctets());
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalArgumentException("can't convert extension: " + e);
        }
    }
}
