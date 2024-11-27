package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class AlgorithmIdentifier extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier algorithm;
    private com.android.internal.org.bouncycastle.asn1.ASN1Encodable parameters;

    public static com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier) {
            return (com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this.algorithm = aSN1ObjectIdentifier;
    }

    public AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        this.algorithm = aSN1ObjectIdentifier;
        this.parameters = aSN1Encodable;
    }

    private AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() < 1 || aSN1Sequence.size() > 2) {
            throw new java.lang.IllegalArgumentException("Bad sequence size: " + aSN1Sequence.size());
        }
        this.algorithm = com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier.getInstance(aSN1Sequence.getObjectAt(0));
        if (aSN1Sequence.size() == 2) {
            this.parameters = aSN1Sequence.getObjectAt(1);
        } else {
            this.parameters = null;
        }
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier getAlgorithm() {
        return this.algorithm;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Encodable getParameters() {
        return this.parameters;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(2);
        aSN1EncodableVector.add(this.algorithm);
        if (this.parameters != null) {
            aSN1EncodableVector.add(this.parameters);
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
