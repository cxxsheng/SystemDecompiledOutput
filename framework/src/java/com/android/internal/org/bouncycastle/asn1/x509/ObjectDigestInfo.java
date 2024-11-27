package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class ObjectDigestInfo extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    public static final int otherObjectDigest = 2;
    public static final int publicKey = 0;
    public static final int publicKeyCert = 1;
    com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier digestAlgorithm;
    com.android.internal.org.bouncycastle.asn1.ASN1Enumerated digestedObjectType;
    com.android.internal.org.bouncycastle.asn1.DERBitString objectDigest;
    com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier otherObjectTypeID;

    public static com.android.internal.org.bouncycastle.asn1.x509.ObjectDigestInfo getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.ObjectDigestInfo) {
            return (com.android.internal.org.bouncycastle.asn1.x509.ObjectDigestInfo) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x509.ObjectDigestInfo(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.ObjectDigestInfo getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public ObjectDigestInfo(int i, com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, byte[] bArr) {
        this.digestedObjectType = new com.android.internal.org.bouncycastle.asn1.ASN1Enumerated(i);
        if (i == 2) {
            this.otherObjectTypeID = aSN1ObjectIdentifier;
        }
        this.digestAlgorithm = algorithmIdentifier;
        this.objectDigest = new com.android.internal.org.bouncycastle.asn1.DERBitString(bArr);
    }

    private ObjectDigestInfo(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() > 4 || aSN1Sequence.size() < 3) {
            throw new java.lang.IllegalArgumentException("Bad sequence size: " + aSN1Sequence.size());
        }
        int i = 0;
        this.digestedObjectType = com.android.internal.org.bouncycastle.asn1.ASN1Enumerated.getInstance(aSN1Sequence.getObjectAt(0));
        if (aSN1Sequence.size() == 4) {
            i = 1;
            this.otherObjectTypeID = com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier.getInstance(aSN1Sequence.getObjectAt(1));
        }
        this.digestAlgorithm = com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(i + 1));
        this.objectDigest = com.android.internal.org.bouncycastle.asn1.DERBitString.getInstance(aSN1Sequence.getObjectAt(i + 2));
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Enumerated getDigestedObjectType() {
        return this.digestedObjectType;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier getOtherObjectTypeID() {
        return this.otherObjectTypeID;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getDigestAlgorithm() {
        return this.digestAlgorithm;
    }

    public com.android.internal.org.bouncycastle.asn1.DERBitString getObjectDigest() {
        return this.objectDigest;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(4);
        aSN1EncodableVector.add(this.digestedObjectType);
        if (this.otherObjectTypeID != null) {
            aSN1EncodableVector.add(this.otherObjectTypeID);
        }
        aSN1EncodableVector.add(this.digestAlgorithm);
        aSN1EncodableVector.add(this.objectDigest);
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
