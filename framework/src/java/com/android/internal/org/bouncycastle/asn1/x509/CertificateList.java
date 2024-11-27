package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class CertificateList extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    int hashCodeValue;
    boolean isHashCodeSet = false;
    com.android.internal.org.bouncycastle.asn1.DERBitString sig;
    com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier sigAlgId;
    com.android.internal.org.bouncycastle.asn1.x509.TBSCertList tbsCertList;

    public static com.android.internal.org.bouncycastle.asn1.x509.CertificateList getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.CertificateList getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.CertificateList) {
            return (com.android.internal.org.bouncycastle.asn1.x509.CertificateList) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x509.CertificateList(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public CertificateList(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() == 3) {
            this.tbsCertList = com.android.internal.org.bouncycastle.asn1.x509.TBSCertList.getInstance(aSN1Sequence.getObjectAt(0));
            this.sigAlgId = com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(1));
            this.sig = com.android.internal.org.bouncycastle.asn1.DERBitString.getInstance(aSN1Sequence.getObjectAt(2));
            return;
        }
        throw new java.lang.IllegalArgumentException("sequence wrong size for CertificateList");
    }

    public com.android.internal.org.bouncycastle.asn1.x509.TBSCertList getTBSCertList() {
        return this.tbsCertList;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.TBSCertList.CRLEntry[] getRevokedCertificates() {
        return this.tbsCertList.getRevokedCertificates();
    }

    public java.util.Enumeration getRevokedCertificateEnumeration() {
        return this.tbsCertList.getRevokedCertificateEnumeration();
    }

    public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getSignatureAlgorithm() {
        return this.sigAlgId;
    }

    public com.android.internal.org.bouncycastle.asn1.DERBitString getSignature() {
        return this.sig;
    }

    public int getVersionNumber() {
        return this.tbsCertList.getVersionNumber();
    }

    public com.android.internal.org.bouncycastle.asn1.x500.X500Name getIssuer() {
        return this.tbsCertList.getIssuer();
    }

    public com.android.internal.org.bouncycastle.asn1.x509.Time getThisUpdate() {
        return this.tbsCertList.getThisUpdate();
    }

    public com.android.internal.org.bouncycastle.asn1.x509.Time getNextUpdate() {
        return this.tbsCertList.getNextUpdate();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(3);
        aSN1EncodableVector.add(this.tbsCertList);
        aSN1EncodableVector.add(this.sigAlgId);
        aSN1EncodableVector.add(this.sig);
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        if (!this.isHashCodeSet) {
            this.hashCodeValue = super.hashCode();
            this.isHashCodeSet = true;
        }
        return this.hashCodeValue;
    }
}
