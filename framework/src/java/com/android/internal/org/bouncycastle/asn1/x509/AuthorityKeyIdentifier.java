package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class AuthorityKeyIdentifier extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    com.android.internal.org.bouncycastle.asn1.x509.GeneralNames certissuer;
    com.android.internal.org.bouncycastle.asn1.ASN1Integer certserno;
    com.android.internal.org.bouncycastle.asn1.ASN1OctetString keyidentifier;

    public static com.android.internal.org.bouncycastle.asn1.x509.AuthorityKeyIdentifier getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.AuthorityKeyIdentifier getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.AuthorityKeyIdentifier) {
            return (com.android.internal.org.bouncycastle.asn1.x509.AuthorityKeyIdentifier) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x509.AuthorityKeyIdentifier(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.AuthorityKeyIdentifier fromExtensions(com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.x509.Extensions.getExtensionParsedValue(extensions, com.android.internal.org.bouncycastle.asn1.x509.Extension.authorityKeyIdentifier));
    }

    protected AuthorityKeyIdentifier(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.keyidentifier = null;
        this.certissuer = null;
        this.certserno = null;
        java.util.Enumeration objects = aSN1Sequence.getObjects();
        while (objects.hasMoreElements()) {
            com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject = com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject.getInstance(objects.nextElement());
            switch (aSN1TaggedObject.getTagNo()) {
                case 0:
                    this.keyidentifier = com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(aSN1TaggedObject, false);
                    break;
                case 1:
                    this.certissuer = com.android.internal.org.bouncycastle.asn1.x509.GeneralNames.getInstance(aSN1TaggedObject, false);
                    break;
                case 2:
                    this.certserno = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(aSN1TaggedObject, false);
                    break;
                default:
                    throw new java.lang.IllegalArgumentException("illegal tag");
            }
        }
    }

    public AuthorityKeyIdentifier(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo) {
        this.keyidentifier = null;
        this.certissuer = null;
        this.certserno = null;
        com.android.internal.org.bouncycastle.crypto.Digest sha1 = com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getSHA1();
        byte[] bArr = new byte[sha1.getDigestSize()];
        byte[] bytes = subjectPublicKeyInfo.getPublicKeyData().getBytes();
        sha1.update(bytes, 0, bytes.length);
        sha1.doFinal(bArr, 0);
        this.keyidentifier = new com.android.internal.org.bouncycastle.asn1.DEROctetString(bArr);
    }

    public AuthorityKeyIdentifier(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo, com.android.internal.org.bouncycastle.asn1.x509.GeneralNames generalNames, java.math.BigInteger bigInteger) {
        this.keyidentifier = null;
        this.certissuer = null;
        this.certserno = null;
        com.android.internal.org.bouncycastle.crypto.Digest sha1 = com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getSHA1();
        byte[] bArr = new byte[sha1.getDigestSize()];
        byte[] bytes = subjectPublicKeyInfo.getPublicKeyData().getBytes();
        sha1.update(bytes, 0, bytes.length);
        sha1.doFinal(bArr, 0);
        this.keyidentifier = new com.android.internal.org.bouncycastle.asn1.DEROctetString(bArr);
        this.certissuer = generalNames;
        this.certserno = bigInteger != null ? new com.android.internal.org.bouncycastle.asn1.ASN1Integer(bigInteger) : null;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public AuthorityKeyIdentifier(com.android.internal.org.bouncycastle.asn1.x509.GeneralNames generalNames, java.math.BigInteger bigInteger) {
        this((byte[]) null, generalNames, bigInteger);
    }

    public AuthorityKeyIdentifier(byte[] bArr) {
        this(bArr, (com.android.internal.org.bouncycastle.asn1.x509.GeneralNames) null, (java.math.BigInteger) null);
    }

    public AuthorityKeyIdentifier(byte[] bArr, com.android.internal.org.bouncycastle.asn1.x509.GeneralNames generalNames, java.math.BigInteger bigInteger) {
        this.keyidentifier = null;
        this.certissuer = null;
        this.certserno = null;
        this.keyidentifier = bArr != null ? new com.android.internal.org.bouncycastle.asn1.DEROctetString(bArr) : null;
        this.certissuer = generalNames;
        this.certserno = bigInteger != null ? new com.android.internal.org.bouncycastle.asn1.ASN1Integer(bigInteger) : null;
    }

    public byte[] getKeyIdentifier() {
        if (this.keyidentifier != null) {
            return this.keyidentifier.getOctets();
        }
        return null;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.GeneralNames getAuthorityCertIssuer() {
        return this.certissuer;
    }

    public java.math.BigInteger getAuthorityCertSerialNumber() {
        if (this.certserno != null) {
            return this.certserno.getValue();
        }
        return null;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(3);
        if (this.keyidentifier != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, 0, this.keyidentifier));
        }
        if (this.certissuer != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, 1, this.certissuer));
        }
        if (this.certserno != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, 2, this.certserno));
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }

    public java.lang.String toString() {
        return "AuthorityKeyIdentifier: KeyID(" + (this.keyidentifier != null ? com.android.internal.org.bouncycastle.util.encoders.Hex.toHexString(this.keyidentifier.getOctets()) : "null") + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
    }
}
