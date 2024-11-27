package com.android.internal.org.bouncycastle.asn1.cms;

/* loaded from: classes4.dex */
public class ContentInfo extends com.android.internal.org.bouncycastle.asn1.ASN1Object implements com.android.internal.org.bouncycastle.asn1.cms.CMSObjectIdentifiers {
    private com.android.internal.org.bouncycastle.asn1.ASN1Encodable content;
    private com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier contentType;

    public static com.android.internal.org.bouncycastle.asn1.cms.ContentInfo getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.cms.ContentInfo) {
            return (com.android.internal.org.bouncycastle.asn1.cms.ContentInfo) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.cms.ContentInfo(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static com.android.internal.org.bouncycastle.asn1.cms.ContentInfo getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public ContentInfo(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() < 1 || aSN1Sequence.size() > 2) {
            throw new java.lang.IllegalArgumentException("Bad sequence size: " + aSN1Sequence.size());
        }
        this.contentType = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) aSN1Sequence.getObjectAt(0);
        if (aSN1Sequence.size() > 1) {
            com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject = (com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) aSN1Sequence.getObjectAt(1);
            if (!aSN1TaggedObject.isExplicit() || aSN1TaggedObject.getTagNo() != 0) {
                throw new java.lang.IllegalArgumentException("Bad tag for 'content'");
            }
            this.content = aSN1TaggedObject.getObject();
        }
    }

    public ContentInfo(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
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
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.BERTaggedObject(0, this.content));
        }
        return new com.android.internal.org.bouncycastle.asn1.BERSequence(aSN1EncodableVector);
    }
}
