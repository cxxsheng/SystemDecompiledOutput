package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class Extensions extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private java.util.Hashtable extensions = new java.util.Hashtable();
    private java.util.Vector ordering = new java.util.Vector();

    public static com.android.internal.org.bouncycastle.asn1.x509.Extension getExtension(com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions, com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        if (extensions == null) {
            return null;
        }
        return extensions.getExtension(aSN1ObjectIdentifier);
    }

    public static com.android.internal.org.bouncycastle.asn1.ASN1Encodable getExtensionParsedValue(com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions, com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        if (extensions == null) {
            return null;
        }
        return extensions.getExtensionParsedValue(aSN1ObjectIdentifier);
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.Extensions getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.Extensions getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.Extensions) {
            return (com.android.internal.org.bouncycastle.asn1.x509.Extensions) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x509.Extensions(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    private Extensions(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        java.util.Enumeration objects = aSN1Sequence.getObjects();
        while (objects.hasMoreElements()) {
            com.android.internal.org.bouncycastle.asn1.x509.Extension extension = com.android.internal.org.bouncycastle.asn1.x509.Extension.getInstance(objects.nextElement());
            if (this.extensions.containsKey(extension.getExtnId())) {
                throw new java.lang.IllegalArgumentException("repeated extension found: " + extension.getExtnId());
            }
            this.extensions.put(extension.getExtnId(), extension);
            this.ordering.addElement(extension.getExtnId());
        }
    }

    public Extensions(com.android.internal.org.bouncycastle.asn1.x509.Extension extension) {
        this.ordering.addElement(extension.getExtnId());
        this.extensions.put(extension.getExtnId(), extension);
    }

    public Extensions(com.android.internal.org.bouncycastle.asn1.x509.Extension[] extensionArr) {
        for (int i = 0; i != extensionArr.length; i++) {
            com.android.internal.org.bouncycastle.asn1.x509.Extension extension = extensionArr[i];
            this.ordering.addElement(extension.getExtnId());
            this.extensions.put(extension.getExtnId(), extension);
        }
    }

    public java.util.Enumeration oids() {
        return this.ordering.elements();
    }

    public com.android.internal.org.bouncycastle.asn1.x509.Extension getExtension(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return (com.android.internal.org.bouncycastle.asn1.x509.Extension) this.extensions.get(aSN1ObjectIdentifier);
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Encodable getExtensionParsedValue(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        com.android.internal.org.bouncycastle.asn1.x509.Extension extension = getExtension(aSN1ObjectIdentifier);
        if (extension != null) {
            return extension.getParsedValue();
        }
        return null;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(this.ordering.size());
        java.util.Enumeration elements = this.ordering.elements();
        while (elements.hasMoreElements()) {
            aSN1EncodableVector.add((com.android.internal.org.bouncycastle.asn1.x509.Extension) this.extensions.get((com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) elements.nextElement()));
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }

    public boolean equivalent(com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions) {
        if (this.extensions.size() != extensions.extensions.size()) {
            return false;
        }
        java.util.Enumeration keys = this.extensions.keys();
        while (keys.hasMoreElements()) {
            java.lang.Object nextElement = keys.nextElement();
            if (!this.extensions.get(nextElement).equals(extensions.extensions.get(nextElement))) {
                return false;
            }
        }
        return true;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier[] getExtensionOIDs() {
        return toOidArray(this.ordering);
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier[] getNonCriticalExtensionOIDs() {
        return getExtensionOIDs(false);
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier[] getCriticalExtensionOIDs() {
        return getExtensionOIDs(true);
    }

    private com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier[] getExtensionOIDs(boolean z) {
        java.util.Vector vector = new java.util.Vector();
        for (int i = 0; i != this.ordering.size(); i++) {
            java.lang.Object elementAt = this.ordering.elementAt(i);
            if (((com.android.internal.org.bouncycastle.asn1.x509.Extension) this.extensions.get(elementAt)).isCritical() == z) {
                vector.addElement(elementAt);
            }
        }
        return toOidArray(vector);
    }

    private com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier[] toOidArray(java.util.Vector vector) {
        int size = vector.size();
        com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier[] aSN1ObjectIdentifierArr = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier[size];
        for (int i = 0; i != size; i++) {
            aSN1ObjectIdentifierArr[i] = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) vector.elementAt(i);
        }
        return aSN1ObjectIdentifierArr;
    }
}
