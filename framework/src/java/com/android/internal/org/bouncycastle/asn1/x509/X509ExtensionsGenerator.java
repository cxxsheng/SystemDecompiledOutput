package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class X509ExtensionsGenerator {
    private java.util.Hashtable extensions = new java.util.Hashtable();
    private java.util.Vector extOrdering = new java.util.Vector();

    public void reset() {
        this.extensions = new java.util.Hashtable();
        this.extOrdering = new java.util.Vector();
    }

    public void addExtension(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean z, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        try {
            addExtension(aSN1ObjectIdentifier, z, aSN1Encodable.toASN1Primitive().getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER));
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalArgumentException("error encoding value: " + e);
        }
    }

    public void addExtension(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean z, byte[] bArr) {
        if (this.extensions.containsKey(aSN1ObjectIdentifier)) {
            throw new java.lang.IllegalArgumentException("extension " + aSN1ObjectIdentifier + " already added");
        }
        this.extOrdering.addElement(aSN1ObjectIdentifier);
        this.extensions.put(aSN1ObjectIdentifier, new com.android.internal.org.bouncycastle.asn1.x509.X509Extension(z, new com.android.internal.org.bouncycastle.asn1.DEROctetString(bArr)));
    }

    public boolean isEmpty() {
        return this.extOrdering.isEmpty();
    }

    public com.android.internal.org.bouncycastle.asn1.x509.X509Extensions generate() {
        return new com.android.internal.org.bouncycastle.asn1.x509.X509Extensions(this.extOrdering, this.extensions);
    }
}
