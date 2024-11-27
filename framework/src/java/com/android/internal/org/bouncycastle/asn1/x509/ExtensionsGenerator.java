package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class ExtensionsGenerator {
    private java.util.Hashtable extensions = new java.util.Hashtable();
    private java.util.Vector extOrdering = new java.util.Vector();

    public void reset() {
        this.extensions = new java.util.Hashtable();
        this.extOrdering = new java.util.Vector();
    }

    public void addExtension(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean z, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) throws java.io.IOException {
        addExtension(aSN1ObjectIdentifier, z, aSN1Encodable.toASN1Primitive().getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER));
    }

    public void addExtension(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean z, byte[] bArr) {
        if (this.extensions.containsKey(aSN1ObjectIdentifier)) {
            throw new java.lang.IllegalArgumentException("extension " + aSN1ObjectIdentifier + " already added");
        }
        this.extOrdering.addElement(aSN1ObjectIdentifier);
        this.extensions.put(aSN1ObjectIdentifier, new com.android.internal.org.bouncycastle.asn1.x509.Extension(aSN1ObjectIdentifier, z, new com.android.internal.org.bouncycastle.asn1.DEROctetString(bArr)));
    }

    public void addExtension(com.android.internal.org.bouncycastle.asn1.x509.Extension extension) {
        if (this.extensions.containsKey(extension.getExtnId())) {
            throw new java.lang.IllegalArgumentException("extension " + extension.getExtnId() + " already added");
        }
        this.extOrdering.addElement(extension.getExtnId());
        this.extensions.put(extension.getExtnId(), extension);
    }

    public void replaceExtension(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean z, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) throws java.io.IOException {
        replaceExtension(aSN1ObjectIdentifier, z, aSN1Encodable.toASN1Primitive().getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER));
    }

    public void replaceExtension(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean z, byte[] bArr) {
        replaceExtension(new com.android.internal.org.bouncycastle.asn1.x509.Extension(aSN1ObjectIdentifier, z, bArr));
    }

    public void replaceExtension(com.android.internal.org.bouncycastle.asn1.x509.Extension extension) {
        if (!this.extensions.containsKey(extension.getExtnId())) {
            throw new java.lang.IllegalArgumentException("extension " + extension.getExtnId() + " not present");
        }
        this.extensions.put(extension.getExtnId(), extension);
    }

    public void removeExtension(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        if (!this.extensions.containsKey(aSN1ObjectIdentifier)) {
            throw new java.lang.IllegalArgumentException("extension " + aSN1ObjectIdentifier + " not present");
        }
        this.extOrdering.removeElement(aSN1ObjectIdentifier);
        this.extensions.remove(aSN1ObjectIdentifier);
    }

    public boolean hasExtension(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return this.extensions.containsKey(aSN1ObjectIdentifier);
    }

    public com.android.internal.org.bouncycastle.asn1.x509.Extension getExtension(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return (com.android.internal.org.bouncycastle.asn1.x509.Extension) this.extensions.get(aSN1ObjectIdentifier);
    }

    public boolean isEmpty() {
        return this.extOrdering.isEmpty();
    }

    public com.android.internal.org.bouncycastle.asn1.x509.Extensions generate() {
        com.android.internal.org.bouncycastle.asn1.x509.Extension[] extensionArr = new com.android.internal.org.bouncycastle.asn1.x509.Extension[this.extOrdering.size()];
        for (int i = 0; i != this.extOrdering.size(); i++) {
            extensionArr[i] = (com.android.internal.org.bouncycastle.asn1.x509.Extension) this.extensions.get(this.extOrdering.elementAt(i));
        }
        return new com.android.internal.org.bouncycastle.asn1.x509.Extensions(extensionArr);
    }
}
