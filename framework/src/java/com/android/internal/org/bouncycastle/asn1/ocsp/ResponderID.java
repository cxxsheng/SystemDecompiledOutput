package com.android.internal.org.bouncycastle.asn1.ocsp;

/* loaded from: classes4.dex */
public class ResponderID extends com.android.internal.org.bouncycastle.asn1.ASN1Object implements com.android.internal.org.bouncycastle.asn1.ASN1Choice {
    private com.android.internal.org.bouncycastle.asn1.ASN1Encodable value;

    public ResponderID(com.android.internal.org.bouncycastle.asn1.ASN1OctetString aSN1OctetString) {
        this.value = aSN1OctetString;
    }

    public ResponderID(com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name) {
        this.value = x500Name;
    }

    public static com.android.internal.org.bouncycastle.asn1.ocsp.ResponderID getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ocsp.ResponderID) {
            return (com.android.internal.org.bouncycastle.asn1.ocsp.ResponderID) obj;
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.DEROctetString) {
            return new com.android.internal.org.bouncycastle.asn1.ocsp.ResponderID((com.android.internal.org.bouncycastle.asn1.DEROctetString) obj);
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) {
            com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject = (com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) obj;
            if (aSN1TaggedObject.getTagNo() == 1) {
                return new com.android.internal.org.bouncycastle.asn1.ocsp.ResponderID(com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(aSN1TaggedObject, true));
            }
            return new com.android.internal.org.bouncycastle.asn1.ocsp.ResponderID(com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(aSN1TaggedObject, true));
        }
        return new com.android.internal.org.bouncycastle.asn1.ocsp.ResponderID(com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(obj));
    }

    public static com.android.internal.org.bouncycastle.asn1.ocsp.ResponderID getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(aSN1TaggedObject.getObject());
    }

    public byte[] getKeyHash() {
        if (this.value instanceof com.android.internal.org.bouncycastle.asn1.ASN1OctetString) {
            return ((com.android.internal.org.bouncycastle.asn1.ASN1OctetString) this.value).getOctets();
        }
        return null;
    }

    public com.android.internal.org.bouncycastle.asn1.x500.X500Name getName() {
        if (this.value instanceof com.android.internal.org.bouncycastle.asn1.ASN1OctetString) {
            return null;
        }
        return com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(this.value);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        if (this.value instanceof com.android.internal.org.bouncycastle.asn1.ASN1OctetString) {
            return new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(true, 2, this.value);
        }
        return new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(true, 1, this.value);
    }
}
