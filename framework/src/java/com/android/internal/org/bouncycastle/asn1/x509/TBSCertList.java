package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class TBSCertList extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    com.android.internal.org.bouncycastle.asn1.x509.Extensions crlExtensions;
    com.android.internal.org.bouncycastle.asn1.x500.X500Name issuer;
    com.android.internal.org.bouncycastle.asn1.x509.Time nextUpdate;
    com.android.internal.org.bouncycastle.asn1.ASN1Sequence revokedCertificates;
    com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier signature;
    com.android.internal.org.bouncycastle.asn1.x509.Time thisUpdate;
    com.android.internal.org.bouncycastle.asn1.ASN1Integer version;

    public static class CRLEntry extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
        com.android.internal.org.bouncycastle.asn1.x509.Extensions crlEntryExtensions;
        com.android.internal.org.bouncycastle.asn1.ASN1Sequence seq;

        private CRLEntry(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
            if (aSN1Sequence.size() < 2 || aSN1Sequence.size() > 3) {
                throw new java.lang.IllegalArgumentException("Bad sequence size: " + aSN1Sequence.size());
            }
            this.seq = aSN1Sequence;
        }

        public static com.android.internal.org.bouncycastle.asn1.x509.TBSCertList.CRLEntry getInstance(java.lang.Object obj) {
            if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.TBSCertList.CRLEntry) {
                return (com.android.internal.org.bouncycastle.asn1.x509.TBSCertList.CRLEntry) obj;
            }
            if (obj != null) {
                return new com.android.internal.org.bouncycastle.asn1.x509.TBSCertList.CRLEntry(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
            }
            return null;
        }

        public com.android.internal.org.bouncycastle.asn1.ASN1Integer getUserCertificate() {
            return com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(this.seq.getObjectAt(0));
        }

        public com.android.internal.org.bouncycastle.asn1.x509.Time getRevocationDate() {
            return com.android.internal.org.bouncycastle.asn1.x509.Time.getInstance(this.seq.getObjectAt(1));
        }

        public com.android.internal.org.bouncycastle.asn1.x509.Extensions getExtensions() {
            if (this.crlEntryExtensions == null && this.seq.size() == 3) {
                this.crlEntryExtensions = com.android.internal.org.bouncycastle.asn1.x509.Extensions.getInstance(this.seq.getObjectAt(2));
            }
            return this.crlEntryExtensions;
        }

        @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
        public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
            return this.seq;
        }

        public boolean hasExtensions() {
            return this.seq.size() == 3;
        }
    }

    private class RevokedCertificatesEnumeration implements java.util.Enumeration {
        private final java.util.Enumeration en;

        RevokedCertificatesEnumeration(java.util.Enumeration enumeration) {
            this.en = enumeration;
        }

        @Override // java.util.Enumeration
        public boolean hasMoreElements() {
            return this.en.hasMoreElements();
        }

        @Override // java.util.Enumeration
        public java.lang.Object nextElement() {
            return com.android.internal.org.bouncycastle.asn1.x509.TBSCertList.CRLEntry.getInstance(this.en.nextElement());
        }
    }

    private class EmptyEnumeration implements java.util.Enumeration {
        private EmptyEnumeration() {
        }

        @Override // java.util.Enumeration
        public boolean hasMoreElements() {
            return false;
        }

        @Override // java.util.Enumeration
        public java.lang.Object nextElement() {
            throw new java.util.NoSuchElementException("Empty Enumeration");
        }
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.TBSCertList getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.TBSCertList getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.TBSCertList) {
            return (com.android.internal.org.bouncycastle.asn1.x509.TBSCertList) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x509.TBSCertList(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public TBSCertList(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() < 3 || aSN1Sequence.size() > 7) {
            throw new java.lang.IllegalArgumentException("Bad sequence size: " + aSN1Sequence.size());
        }
        int i = 0;
        if (aSN1Sequence.getObjectAt(0) instanceof com.android.internal.org.bouncycastle.asn1.ASN1Integer) {
            this.version = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0));
            i = 1;
        } else {
            this.version = null;
        }
        int i2 = i + 1;
        this.signature = com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(i));
        int i3 = i2 + 1;
        this.issuer = com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(aSN1Sequence.getObjectAt(i2));
        int i4 = i3 + 1;
        this.thisUpdate = com.android.internal.org.bouncycastle.asn1.x509.Time.getInstance(aSN1Sequence.getObjectAt(i3));
        if (i4 < aSN1Sequence.size() && ((aSN1Sequence.getObjectAt(i4) instanceof com.android.internal.org.bouncycastle.asn1.ASN1UTCTime) || (aSN1Sequence.getObjectAt(i4) instanceof com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime) || (aSN1Sequence.getObjectAt(i4) instanceof com.android.internal.org.bouncycastle.asn1.x509.Time))) {
            this.nextUpdate = com.android.internal.org.bouncycastle.asn1.x509.Time.getInstance(aSN1Sequence.getObjectAt(i4));
            i4++;
        }
        if (i4 < aSN1Sequence.size() && !(aSN1Sequence.getObjectAt(i4) instanceof com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject)) {
            this.revokedCertificates = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1Sequence.getObjectAt(i4));
            i4++;
        }
        if (i4 < aSN1Sequence.size() && (aSN1Sequence.getObjectAt(i4) instanceof com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject)) {
            this.crlExtensions = com.android.internal.org.bouncycastle.asn1.x509.Extensions.getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance((com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) aSN1Sequence.getObjectAt(i4), true));
        }
    }

    public int getVersionNumber() {
        if (this.version == null) {
            return 1;
        }
        return this.version.intValueExact() + 1;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Integer getVersion() {
        return this.version;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getSignature() {
        return this.signature;
    }

    public com.android.internal.org.bouncycastle.asn1.x500.X500Name getIssuer() {
        return this.issuer;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.Time getThisUpdate() {
        return this.thisUpdate;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.Time getNextUpdate() {
        return this.nextUpdate;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.TBSCertList.CRLEntry[] getRevokedCertificates() {
        if (this.revokedCertificates == null) {
            return new com.android.internal.org.bouncycastle.asn1.x509.TBSCertList.CRLEntry[0];
        }
        int size = this.revokedCertificates.size();
        com.android.internal.org.bouncycastle.asn1.x509.TBSCertList.CRLEntry[] cRLEntryArr = new com.android.internal.org.bouncycastle.asn1.x509.TBSCertList.CRLEntry[size];
        for (int i = 0; i < size; i++) {
            cRLEntryArr[i] = com.android.internal.org.bouncycastle.asn1.x509.TBSCertList.CRLEntry.getInstance(this.revokedCertificates.getObjectAt(i));
        }
        return cRLEntryArr;
    }

    public java.util.Enumeration getRevokedCertificateEnumeration() {
        if (this.revokedCertificates == null) {
            return new com.android.internal.org.bouncycastle.asn1.x509.TBSCertList.EmptyEnumeration();
        }
        return new com.android.internal.org.bouncycastle.asn1.x509.TBSCertList.RevokedCertificatesEnumeration(this.revokedCertificates.getObjects());
    }

    public com.android.internal.org.bouncycastle.asn1.x509.Extensions getExtensions() {
        return this.crlExtensions;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(7);
        if (this.version != null) {
            aSN1EncodableVector.add(this.version);
        }
        aSN1EncodableVector.add(this.signature);
        aSN1EncodableVector.add(this.issuer);
        aSN1EncodableVector.add(this.thisUpdate);
        if (this.nextUpdate != null) {
            aSN1EncodableVector.add(this.nextUpdate);
        }
        if (this.revokedCertificates != null) {
            aSN1EncodableVector.add(this.revokedCertificates);
        }
        if (this.crlExtensions != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(0, this.crlExtensions));
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
