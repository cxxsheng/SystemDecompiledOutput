package com.android.internal.org.bouncycastle.asn1.pkcs;

/* loaded from: classes4.dex */
public class ContentInfo extends com.android.internal.org.bouncycastle.asn1.ASN1Object implements com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers {
    private com.android.internal.org.bouncycastle.asn1.ASN1Encodable content;
    private com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier contentType;
    private boolean isBer;

    public static com.android.internal.org.bouncycastle.asn1.pkcs.ContentInfo getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.pkcs.ContentInfo) {
            return (com.android.internal.org.bouncycastle.asn1.pkcs.ContentInfo) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.pkcs.ContentInfo(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    private ContentInfo(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.isBer = true;
        java.util.Enumeration objects = aSN1Sequence.getObjects();
        this.contentType = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) objects.nextElement();
        if (objects.hasMoreElements()) {
            this.content = ((com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) objects.nextElement()).getObject();
        }
        this.isBer = aSN1Sequence instanceof com.android.internal.org.bouncycastle.asn1.BERSequence;
    }

    public ContentInfo(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        this.isBer = true;
        this.contentType = aSN1ObjectIdentifier;
        this.content = aSN1Encodable;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier getContentType() {
        return this.contentType;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Encodable getContent() {
        return this.content;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(2);
        aSN1EncodableVector.add(this.contentType);
        if (this.content != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.BERTaggedObject(true, 0, this.content));
        }
        if (this.isBer) {
            return new com.android.internal.org.bouncycastle.asn1.BERSequence(aSN1EncodableVector);
        }
        return new com.android.internal.org.bouncycastle.asn1.DLSequence(aSN1EncodableVector);
    }
}
