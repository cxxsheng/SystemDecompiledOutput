package com.android.internal.org.bouncycastle.asn1.cms;

/* loaded from: classes4.dex */
public class CMSAlgorithmProtection extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    public static final int MAC = 2;
    public static final int SIGNATURE = 1;
    private final com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier digestAlgorithm;
    private final com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier macAlgorithm;
    private final com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier signatureAlgorithm;

    public CMSAlgorithmProtection(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, int i, com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier2) {
        if (algorithmIdentifier == null || algorithmIdentifier2 == null) {
            throw new java.lang.NullPointerException("AlgorithmIdentifiers cannot be null");
        }
        this.digestAlgorithm = algorithmIdentifier;
        if (i == 1) {
            this.signatureAlgorithm = algorithmIdentifier2;
            this.macAlgorithm = null;
        } else {
            if (i == 2) {
                this.signatureAlgorithm = null;
                this.macAlgorithm = algorithmIdentifier2;
                return;
            }
            throw new java.lang.IllegalArgumentException("Unknown type: " + i);
        }
    }

    private CMSAlgorithmProtection(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 2) {
            throw new java.lang.IllegalArgumentException("Sequence wrong size: One of signatureAlgorithm or macAlgorithm must be present");
        }
        this.digestAlgorithm = com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(0));
        com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject = com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject.getInstance(aSN1Sequence.getObjectAt(1));
        if (aSN1TaggedObject.getTagNo() == 1) {
            this.signatureAlgorithm = com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(aSN1TaggedObject, false);
            this.macAlgorithm = null;
        } else {
            if (aSN1TaggedObject.getTagNo() == 2) {
                this.signatureAlgorithm = null;
                this.macAlgorithm = com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(aSN1TaggedObject, false);
                return;
            }
            throw new java.lang.IllegalArgumentException("Unknown tag found: " + aSN1TaggedObject.getTagNo());
        }
    }

    public static com.android.internal.org.bouncycastle.asn1.cms.CMSAlgorithmProtection getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.cms.CMSAlgorithmProtection) {
            return (com.android.internal.org.bouncycastle.asn1.cms.CMSAlgorithmProtection) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.cms.CMSAlgorithmProtection(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getDigestAlgorithm() {
        return this.digestAlgorithm;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getMacAlgorithm() {
        return this.macAlgorithm;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getSignatureAlgorithm() {
        return this.signatureAlgorithm;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(3);
        aSN1EncodableVector.add(this.digestAlgorithm);
        if (this.signatureAlgorithm != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, 1, this.signatureAlgorithm));
        }
        if (this.macAlgorithm != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, 2, this.macAlgorithm));
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
