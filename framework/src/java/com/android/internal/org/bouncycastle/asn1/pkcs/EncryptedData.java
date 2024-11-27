package com.android.internal.org.bouncycastle.asn1.pkcs;

/* loaded from: classes4.dex */
public class EncryptedData extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    com.android.internal.org.bouncycastle.asn1.ASN1Sequence data;

    public static com.android.internal.org.bouncycastle.asn1.pkcs.EncryptedData getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.pkcs.EncryptedData) {
            return (com.android.internal.org.bouncycastle.asn1.pkcs.EncryptedData) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.pkcs.EncryptedData(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    private EncryptedData(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        if (((com.android.internal.org.bouncycastle.asn1.ASN1Integer) aSN1Sequence.getObjectAt(0)).intValueExact() != 0) {
            throw new java.lang.IllegalArgumentException("sequence not version 0");
        }
        this.data = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1Sequence.getObjectAt(1));
    }

    public EncryptedData(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(3);
        aSN1EncodableVector.add(aSN1ObjectIdentifier);
        aSN1EncodableVector.add(algorithmIdentifier.toASN1Primitive());
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.BERTaggedObject(false, 0, aSN1Encodable));
        this.data = new com.android.internal.org.bouncycastle.asn1.BERSequence(aSN1EncodableVector);
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier getContentType() {
        return com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier.getInstance(this.data.getObjectAt(0));
    }

    public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getEncryptionAlgorithm() {
        return com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(this.data.getObjectAt(1));
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1OctetString getContent() {
        if (this.data.size() == 3) {
            return com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject.getInstance(this.data.getObjectAt(2)), false);
        }
        return null;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(2);
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.ASN1Integer(0L));
        aSN1EncodableVector.add(this.data);
        return new com.android.internal.org.bouncycastle.asn1.BERSequence(aSN1EncodableVector);
    }
}
