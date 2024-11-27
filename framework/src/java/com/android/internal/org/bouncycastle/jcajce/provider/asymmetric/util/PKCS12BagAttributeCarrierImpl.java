package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util;

/* loaded from: classes4.dex */
public class PKCS12BagAttributeCarrierImpl implements com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier {
    private java.util.Hashtable pkcs12Attributes;
    private java.util.Vector pkcs12Ordering;

    PKCS12BagAttributeCarrierImpl(java.util.Hashtable hashtable, java.util.Vector vector) {
        this.pkcs12Attributes = hashtable;
        this.pkcs12Ordering = vector;
    }

    public PKCS12BagAttributeCarrierImpl() {
        this(new java.util.Hashtable(), new java.util.Vector());
    }

    @Override // com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier
    public void setBagAttribute(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        if (this.pkcs12Attributes.containsKey(aSN1ObjectIdentifier)) {
            this.pkcs12Attributes.put(aSN1ObjectIdentifier, aSN1Encodable);
        } else {
            this.pkcs12Attributes.put(aSN1ObjectIdentifier, aSN1Encodable);
            this.pkcs12Ordering.addElement(aSN1ObjectIdentifier);
        }
    }

    @Override // com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier
    public com.android.internal.org.bouncycastle.asn1.ASN1Encodable getBagAttribute(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return (com.android.internal.org.bouncycastle.asn1.ASN1Encodable) this.pkcs12Attributes.get(aSN1ObjectIdentifier);
    }

    @Override // com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier
    public java.util.Enumeration getBagAttributeKeys() {
        return this.pkcs12Ordering.elements();
    }

    int size() {
        return this.pkcs12Ordering.size();
    }

    java.util.Hashtable getAttributes() {
        return this.pkcs12Attributes;
    }

    java.util.Vector getOrdering() {
        return this.pkcs12Ordering;
    }

    public void writeObject(java.io.ObjectOutputStream objectOutputStream) throws java.io.IOException {
        if (this.pkcs12Ordering.size() == 0) {
            objectOutputStream.writeObject(new java.util.Hashtable());
            objectOutputStream.writeObject(new java.util.Vector());
            return;
        }
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        com.android.internal.org.bouncycastle.asn1.ASN1OutputStream create = com.android.internal.org.bouncycastle.asn1.ASN1OutputStream.create(byteArrayOutputStream);
        java.util.Enumeration bagAttributeKeys = getBagAttributeKeys();
        while (bagAttributeKeys.hasMoreElements()) {
            com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier = com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier.getInstance(bagAttributeKeys.nextElement());
            create.writeObject((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) aSN1ObjectIdentifier);
            create.writeObject((com.android.internal.org.bouncycastle.asn1.ASN1Encodable) this.pkcs12Attributes.get(aSN1ObjectIdentifier));
        }
        objectOutputStream.writeObject(byteArrayOutputStream.toByteArray());
    }

    public void readObject(java.io.ObjectInputStream objectInputStream) throws java.io.IOException, java.lang.ClassNotFoundException {
        java.lang.Object readObject = objectInputStream.readObject();
        if (readObject instanceof java.util.Hashtable) {
            this.pkcs12Attributes = (java.util.Hashtable) readObject;
            this.pkcs12Ordering = (java.util.Vector) objectInputStream.readObject();
        } else {
            com.android.internal.org.bouncycastle.asn1.ASN1InputStream aSN1InputStream = new com.android.internal.org.bouncycastle.asn1.ASN1InputStream((byte[]) readObject);
            while (true) {
                com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) aSN1InputStream.readObject();
                if (aSN1ObjectIdentifier != null) {
                    setBagAttribute(aSN1ObjectIdentifier, aSN1InputStream.readObject());
                } else {
                    return;
                }
            }
        }
    }
}
