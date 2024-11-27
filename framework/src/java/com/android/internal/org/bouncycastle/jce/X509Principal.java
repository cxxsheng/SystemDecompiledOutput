package com.android.internal.org.bouncycastle.jce;

/* loaded from: classes4.dex */
public class X509Principal extends com.android.internal.org.bouncycastle.asn1.x509.X509Name implements java.security.Principal {
    private static com.android.internal.org.bouncycastle.asn1.ASN1Sequence readSequence(com.android.internal.org.bouncycastle.asn1.ASN1InputStream aSN1InputStream) throws java.io.IOException {
        try {
            return com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1InputStream.readObject());
        } catch (java.lang.IllegalArgumentException e) {
            throw new java.io.IOException("not an ASN.1 Sequence: " + e);
        }
    }

    public X509Principal(byte[] bArr) throws java.io.IOException {
        super(readSequence(new com.android.internal.org.bouncycastle.asn1.ASN1InputStream(bArr)));
    }

    public X509Principal(com.android.internal.org.bouncycastle.asn1.x509.X509Name x509Name) {
        super((com.android.internal.org.bouncycastle.asn1.ASN1Sequence) x509Name.toASN1Primitive());
    }

    public X509Principal(com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name) {
        super((com.android.internal.org.bouncycastle.asn1.ASN1Sequence) x500Name.toASN1Primitive());
    }

    public X509Principal(java.util.Hashtable hashtable) {
        super(hashtable);
    }

    public X509Principal(java.util.Vector vector, java.util.Hashtable hashtable) {
        super(vector, hashtable);
    }

    public X509Principal(java.util.Vector vector, java.util.Vector vector2) {
        super(vector, vector2);
    }

    public X509Principal(java.lang.String str) {
        super(str);
    }

    public X509Principal(boolean z, java.lang.String str) {
        super(z, str);
    }

    public X509Principal(boolean z, java.util.Hashtable hashtable, java.lang.String str) {
        super(z, hashtable, str);
    }

    @Override // java.security.Principal
    public java.lang.String getName() {
        return toString();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.util.Encodable
    public byte[] getEncoded() {
        try {
            return getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
        } catch (java.io.IOException e) {
            throw new java.lang.RuntimeException(e.toString());
        }
    }
}
