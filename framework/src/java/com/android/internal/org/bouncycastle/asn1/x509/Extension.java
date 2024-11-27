package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class Extension extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private boolean critical;
    private com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier extnId;
    private com.android.internal.org.bouncycastle.asn1.ASN1OctetString value;
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier subjectDirectoryAttributes = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.9").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier subjectKeyIdentifier = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.14").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier keyUsage = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.15").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier privateKeyUsagePeriod = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.16").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier subjectAlternativeName = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.17").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier issuerAlternativeName = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.18").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier basicConstraints = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.19").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier cRLNumber = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.20").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier reasonCode = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.21").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier instructionCode = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.23").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier invalidityDate = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.24").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier deltaCRLIndicator = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.27").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier issuingDistributionPoint = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.28").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier certificateIssuer = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.29").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier nameConstraints = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.30").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier cRLDistributionPoints = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.31").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier certificatePolicies = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.32").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier policyMappings = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.33").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier authorityKeyIdentifier = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.35").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier policyConstraints = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.36").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier extendedKeyUsage = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.37").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier freshestCRL = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.46").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier inhibitAnyPolicy = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.54").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier authorityInfoAccess = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.1").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier subjectInfoAccess = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.11").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier logoType = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.12").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier biometricInfo = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.2").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier qCStatements = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.3").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier auditIdentity = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.4").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier noRevAvail = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.56").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier targetInformation = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.55").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier expiredCertsOnCRL = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.60").intern();

    public Extension(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.ASN1Boolean aSN1Boolean, com.android.internal.org.bouncycastle.asn1.ASN1OctetString aSN1OctetString) {
        this(aSN1ObjectIdentifier, aSN1Boolean.isTrue(), aSN1OctetString);
    }

    public Extension(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean z, byte[] bArr) {
        this(aSN1ObjectIdentifier, z, new com.android.internal.org.bouncycastle.asn1.DEROctetString(bArr));
    }

    public Extension(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean z, com.android.internal.org.bouncycastle.asn1.ASN1OctetString aSN1OctetString) {
        this.extnId = aSN1ObjectIdentifier;
        this.critical = z;
        this.value = aSN1OctetString;
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.Extension create(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean z, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) throws java.io.IOException {
        return new com.android.internal.org.bouncycastle.asn1.x509.Extension(aSN1ObjectIdentifier, z, aSN1Encodable.toASN1Primitive().getEncoded());
    }

    private Extension(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() == 2) {
            this.extnId = com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier.getInstance(aSN1Sequence.getObjectAt(0));
            this.critical = false;
            this.value = com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(1));
        } else {
            if (aSN1Sequence.size() == 3) {
                this.extnId = com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier.getInstance(aSN1Sequence.getObjectAt(0));
                this.critical = com.android.internal.org.bouncycastle.asn1.ASN1Boolean.getInstance(aSN1Sequence.getObjectAt(1)).isTrue();
                this.value = com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(2));
                return;
            }
            throw new java.lang.IllegalArgumentException("Bad sequence size: " + aSN1Sequence.size());
        }
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.Extension getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.Extension) {
            return (com.android.internal.org.bouncycastle.asn1.x509.Extension) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x509.Extension(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier getExtnId() {
        return this.extnId;
    }

    public boolean isCritical() {
        return this.critical;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1OctetString getExtnValue() {
        return this.value;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Encodable getParsedValue() {
        return convertValueToObject(this);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        if (isCritical()) {
            return getExtnValue().hashCode() ^ getExtnId().hashCode();
        }
        return ~(getExtnValue().hashCode() ^ getExtnId().hashCode());
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object
    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof com.android.internal.org.bouncycastle.asn1.x509.Extension)) {
            return false;
        }
        com.android.internal.org.bouncycastle.asn1.x509.Extension extension = (com.android.internal.org.bouncycastle.asn1.x509.Extension) obj;
        return extension.getExtnId().equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) getExtnId()) && extension.getExtnValue().equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) getExtnValue()) && extension.isCritical() == isCritical();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(3);
        aSN1EncodableVector.add(this.extnId);
        if (this.critical) {
            aSN1EncodableVector.add(com.android.internal.org.bouncycastle.asn1.ASN1Boolean.getInstance(true));
        }
        aSN1EncodableVector.add(this.value);
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }

    private static com.android.internal.org.bouncycastle.asn1.ASN1Primitive convertValueToObject(com.android.internal.org.bouncycastle.asn1.x509.Extension extension) throws java.lang.IllegalArgumentException {
        try {
            return com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(extension.getExtnValue().getOctets());
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalArgumentException("can't convert extension: " + e);
        }
    }
}
