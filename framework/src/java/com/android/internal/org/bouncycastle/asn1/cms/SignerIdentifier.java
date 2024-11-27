package com.android.internal.org.bouncycastle.asn1.cms;

/* loaded from: classes4.dex */
public class SignerIdentifier extends com.android.internal.org.bouncycastle.asn1.ASN1Object implements com.android.internal.org.bouncycastle.asn1.ASN1Choice {
    private com.android.internal.org.bouncycastle.asn1.ASN1Encodable id;

    public SignerIdentifier(com.android.internal.org.bouncycastle.asn1.cms.IssuerAndSerialNumber issuerAndSerialNumber) {
        this.id = issuerAndSerialNumber;
    }

    public SignerIdentifier(com.android.internal.org.bouncycastle.asn1.ASN1OctetString aSN1OctetString) {
        this.id = new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, 0, aSN1OctetString);
    }

    public SignerIdentifier(com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive) {
        this.id = aSN1Primitive;
    }

    public static com.android.internal.org.bouncycastle.asn1.cms.SignerIdentifier getInstance(java.lang.Object obj) {
        if (obj == null || (obj instanceof com.android.internal.org.bouncycastle.asn1.cms.SignerIdentifier)) {
            return (com.android.internal.org.bouncycastle.asn1.cms.SignerIdentifier) obj;
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.cms.IssuerAndSerialNumber) {
            return new com.android.internal.org.bouncycastle.asn1.cms.SignerIdentifier((com.android.internal.org.bouncycastle.asn1.cms.IssuerAndSerialNumber) obj);
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1OctetString) {
            return new com.android.internal.org.bouncycastle.asn1.cms.SignerIdentifier((com.android.internal.org.bouncycastle.asn1.ASN1OctetString) obj);
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1Primitive) {
            return new com.android.internal.org.bouncycastle.asn1.cms.SignerIdentifier((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) obj);
        }
        throw new java.lang.IllegalArgumentException("Illegal object in SignerIdentifier: " + obj.getClass().getName());
    }

    public boolean isTagged() {
        return this.id instanceof com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Encodable getId() {
        if (this.id instanceof com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) {
            return com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance((com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) this.id, false);
        }
        return this.id;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return this.id.toASN1Primitive();
    }
}
