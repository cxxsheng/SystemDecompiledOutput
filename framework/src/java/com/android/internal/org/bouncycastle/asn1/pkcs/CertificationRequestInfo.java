package com.android.internal.org.bouncycastle.asn1.pkcs;

/* loaded from: classes4.dex */
public class CertificationRequestInfo extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    com.android.internal.org.bouncycastle.asn1.ASN1Set attributes;
    com.android.internal.org.bouncycastle.asn1.x500.X500Name subject;
    com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPKInfo;
    com.android.internal.org.bouncycastle.asn1.ASN1Integer version;

    public static com.android.internal.org.bouncycastle.asn1.pkcs.CertificationRequestInfo getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.pkcs.CertificationRequestInfo) {
            return (com.android.internal.org.bouncycastle.asn1.pkcs.CertificationRequestInfo) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.pkcs.CertificationRequestInfo(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public CertificationRequestInfo(com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name, com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo, com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set) {
        this.version = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(0L);
        this.attributes = null;
        if (x500Name == null || subjectPublicKeyInfo == null) {
            throw new java.lang.IllegalArgumentException("Not all mandatory fields set in CertificationRequestInfo generator.");
        }
        validateAttributes(aSN1Set);
        this.subject = x500Name;
        this.subjectPKInfo = subjectPublicKeyInfo;
        this.attributes = aSN1Set;
    }

    public CertificationRequestInfo(com.android.internal.org.bouncycastle.asn1.x509.X509Name x509Name, com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo, com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set) {
        this(com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(x509Name.toASN1Primitive()), subjectPublicKeyInfo, aSN1Set);
    }

    public CertificationRequestInfo(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.version = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(0L);
        this.attributes = null;
        this.version = (com.android.internal.org.bouncycastle.asn1.ASN1Integer) aSN1Sequence.getObjectAt(0);
        this.subject = com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(aSN1Sequence.getObjectAt(1));
        this.subjectPKInfo = com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo.getInstance(aSN1Sequence.getObjectAt(2));
        if (aSN1Sequence.size() > 3) {
            this.attributes = com.android.internal.org.bouncycastle.asn1.ASN1Set.getInstance((com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) aSN1Sequence.getObjectAt(3), false);
        }
        validateAttributes(this.attributes);
        if (this.subject == null || this.version == null || this.subjectPKInfo == null) {
            throw new java.lang.IllegalArgumentException("Not all mandatory fields set in CertificationRequestInfo generator.");
        }
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Integer getVersion() {
        return this.version;
    }

    public com.android.internal.org.bouncycastle.asn1.x500.X500Name getSubject() {
        return this.subject;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo getSubjectPublicKeyInfo() {
        return this.subjectPKInfo;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Set getAttributes() {
        return this.attributes;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(4);
        aSN1EncodableVector.add(this.version);
        aSN1EncodableVector.add(this.subject);
        aSN1EncodableVector.add(this.subjectPKInfo);
        if (this.attributes != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, 0, this.attributes));
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }

    private static void validateAttributes(com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set) {
        if (aSN1Set == null) {
            return;
        }
        java.util.Enumeration objects = aSN1Set.getObjects();
        while (objects.hasMoreElements()) {
            com.android.internal.org.bouncycastle.asn1.pkcs.Attribute attribute = com.android.internal.org.bouncycastle.asn1.pkcs.Attribute.getInstance(objects.nextElement());
            if (attribute.getAttrType().equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.pkcs_9_at_challengePassword) && attribute.getAttrValues().size() != 1) {
                throw new java.lang.IllegalArgumentException("challengePassword attribute must have one value");
            }
        }
    }
}
