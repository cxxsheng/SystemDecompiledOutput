package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class SubjectKeyIdentifier extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private byte[] keyidentifier;

    public static com.android.internal.org.bouncycastle.asn1.x509.SubjectKeyIdentifier getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.SubjectKeyIdentifier getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.SubjectKeyIdentifier) {
            return (com.android.internal.org.bouncycastle.asn1.x509.SubjectKeyIdentifier) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x509.SubjectKeyIdentifier(com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(obj));
        }
        return null;
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.SubjectKeyIdentifier fromExtensions(com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.x509.Extensions.getExtensionParsedValue(extensions, com.android.internal.org.bouncycastle.asn1.x509.Extension.subjectKeyIdentifier));
    }

    public SubjectKeyIdentifier(byte[] bArr) {
        this.keyidentifier = com.android.internal.org.bouncycastle.util.Arrays.clone(bArr);
    }

    protected SubjectKeyIdentifier(com.android.internal.org.bouncycastle.asn1.ASN1OctetString aSN1OctetString) {
        this(aSN1OctetString.getOctets());
    }

    public byte[] getKeyIdentifier() {
        return com.android.internal.org.bouncycastle.util.Arrays.clone(this.keyidentifier);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return new com.android.internal.org.bouncycastle.asn1.DEROctetString(getKeyIdentifier());
    }
}
