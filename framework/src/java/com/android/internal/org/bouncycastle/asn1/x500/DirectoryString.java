package com.android.internal.org.bouncycastle.asn1.x500;

/* loaded from: classes4.dex */
public class DirectoryString extends com.android.internal.org.bouncycastle.asn1.ASN1Object implements com.android.internal.org.bouncycastle.asn1.ASN1Choice, com.android.internal.org.bouncycastle.asn1.ASN1String {
    private com.android.internal.org.bouncycastle.asn1.ASN1String string;

    public static com.android.internal.org.bouncycastle.asn1.x500.DirectoryString getInstance(java.lang.Object obj) {
        if (obj == null || (obj instanceof com.android.internal.org.bouncycastle.asn1.x500.DirectoryString)) {
            return (com.android.internal.org.bouncycastle.asn1.x500.DirectoryString) obj;
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.DERT61String) {
            return new com.android.internal.org.bouncycastle.asn1.x500.DirectoryString((com.android.internal.org.bouncycastle.asn1.DERT61String) obj);
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.DERPrintableString) {
            return new com.android.internal.org.bouncycastle.asn1.x500.DirectoryString((com.android.internal.org.bouncycastle.asn1.DERPrintableString) obj);
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.DERUniversalString) {
            return new com.android.internal.org.bouncycastle.asn1.x500.DirectoryString((com.android.internal.org.bouncycastle.asn1.DERUniversalString) obj);
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.DERUTF8String) {
            return new com.android.internal.org.bouncycastle.asn1.x500.DirectoryString((com.android.internal.org.bouncycastle.asn1.DERUTF8String) obj);
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.DERBMPString) {
            return new com.android.internal.org.bouncycastle.asn1.x500.DirectoryString((com.android.internal.org.bouncycastle.asn1.DERBMPString) obj);
        }
        throw new java.lang.IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
    }

    public static com.android.internal.org.bouncycastle.asn1.x500.DirectoryString getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        if (!z) {
            throw new java.lang.IllegalArgumentException("choice item must be explicitly tagged");
        }
        return getInstance(aSN1TaggedObject.getObject());
    }

    private DirectoryString(com.android.internal.org.bouncycastle.asn1.DERT61String dERT61String) {
        this.string = dERT61String;
    }

    private DirectoryString(com.android.internal.org.bouncycastle.asn1.DERPrintableString dERPrintableString) {
        this.string = dERPrintableString;
    }

    private DirectoryString(com.android.internal.org.bouncycastle.asn1.DERUniversalString dERUniversalString) {
        this.string = dERUniversalString;
    }

    private DirectoryString(com.android.internal.org.bouncycastle.asn1.DERUTF8String dERUTF8String) {
        this.string = dERUTF8String;
    }

    private DirectoryString(com.android.internal.org.bouncycastle.asn1.DERBMPString dERBMPString) {
        this.string = dERBMPString;
    }

    public DirectoryString(java.lang.String str) {
        this.string = new com.android.internal.org.bouncycastle.asn1.DERUTF8String(str);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1String
    public java.lang.String getString() {
        return this.string.getString();
    }

    public java.lang.String toString() {
        return this.string.getString();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return ((com.android.internal.org.bouncycastle.asn1.ASN1Encodable) this.string).toASN1Primitive();
    }
}
