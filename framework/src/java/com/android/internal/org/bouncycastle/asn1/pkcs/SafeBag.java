package com.android.internal.org.bouncycastle.asn1.pkcs;

/* loaded from: classes4.dex */
public class SafeBag extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private com.android.internal.org.bouncycastle.asn1.ASN1Set bagAttributes;
    private com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier bagId;
    private com.android.internal.org.bouncycastle.asn1.ASN1Encodable bagValue;

    public SafeBag(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        this.bagId = aSN1ObjectIdentifier;
        this.bagValue = aSN1Encodable;
        this.bagAttributes = null;
    }

    public SafeBag(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable, com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set) {
        this.bagId = aSN1ObjectIdentifier;
        this.bagValue = aSN1Encodable;
        this.bagAttributes = aSN1Set;
    }

    public static com.android.internal.org.bouncycastle.asn1.pkcs.SafeBag getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.pkcs.SafeBag) {
            return (com.android.internal.org.bouncycastle.asn1.pkcs.SafeBag) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.pkcs.SafeBag(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    private SafeBag(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.bagId = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) aSN1Sequence.getObjectAt(0);
        this.bagValue = ((com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) aSN1Sequence.getObjectAt(1)).getObject();
        if (aSN1Sequence.size() == 3) {
            this.bagAttributes = (com.android.internal.org.bouncycastle.asn1.ASN1Set) aSN1Sequence.getObjectAt(2);
        }
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier getBagId() {
        return this.bagId;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Encodable getBagValue() {
        return this.bagValue;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Set getBagAttributes() {
        return this.bagAttributes;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(3);
        aSN1EncodableVector.add(this.bagId);
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DLTaggedObject(true, 0, this.bagValue));
        if (this.bagAttributes != null) {
            aSN1EncodableVector.add(this.bagAttributes);
        }
        return new com.android.internal.org.bouncycastle.asn1.DLSequence(aSN1EncodableVector);
    }
}
