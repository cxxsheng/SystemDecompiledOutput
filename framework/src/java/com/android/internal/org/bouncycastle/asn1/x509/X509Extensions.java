package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class X509Extensions extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private java.util.Hashtable extensions;
    private java.util.Vector ordering;
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier SubjectDirectoryAttributes = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.9");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier SubjectKeyIdentifier = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.14");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier KeyUsage = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.15");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier PrivateKeyUsagePeriod = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.16");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier SubjectAlternativeName = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.17");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier IssuerAlternativeName = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.18");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier BasicConstraints = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.19");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier CRLNumber = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.20");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier ReasonCode = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.21");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier InstructionCode = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.23");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier InvalidityDate = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.24");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier DeltaCRLIndicator = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.27");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier IssuingDistributionPoint = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.28");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier CertificateIssuer = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.29");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier NameConstraints = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.30");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier CRLDistributionPoints = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.31");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier CertificatePolicies = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.32");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier PolicyMappings = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.33");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier AuthorityKeyIdentifier = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.35");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier PolicyConstraints = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.36");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier ExtendedKeyUsage = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.37");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier FreshestCRL = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.46");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier InhibitAnyPolicy = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.54");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier AuthorityInfoAccess = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.1");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier SubjectInfoAccess = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.11");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier LogoType = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.12");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier BiometricInfo = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.2");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier QCStatements = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.3");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier AuditIdentity = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.4");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier NoRevAvail = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.56");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier TargetInformation = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29.55");

    public static com.android.internal.org.bouncycastle.asn1.x509.X509Extensions getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.X509Extensions getInstance(java.lang.Object obj) {
        if (obj == null || (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.X509Extensions)) {
            return (com.android.internal.org.bouncycastle.asn1.x509.X509Extensions) obj;
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1Sequence) {
            return new com.android.internal.org.bouncycastle.asn1.x509.X509Extensions((com.android.internal.org.bouncycastle.asn1.ASN1Sequence) obj);
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.Extensions) {
            return new com.android.internal.org.bouncycastle.asn1.x509.X509Extensions((com.android.internal.org.bouncycastle.asn1.ASN1Sequence) ((com.android.internal.org.bouncycastle.asn1.x509.Extensions) obj).toASN1Primitive());
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) {
            return getInstance(((com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) obj).getObject());
        }
        throw new java.lang.IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
    }

    public X509Extensions(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.extensions = new java.util.Hashtable();
        this.ordering = new java.util.Vector();
        java.util.Enumeration objects = aSN1Sequence.getObjects();
        while (objects.hasMoreElements()) {
            com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence2 = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(objects.nextElement());
            if (aSN1Sequence2.size() == 3) {
                this.extensions.put(aSN1Sequence2.getObjectAt(0), new com.android.internal.org.bouncycastle.asn1.x509.X509Extension(com.android.internal.org.bouncycastle.asn1.ASN1Boolean.getInstance(aSN1Sequence2.getObjectAt(1)), com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(aSN1Sequence2.getObjectAt(2))));
            } else if (aSN1Sequence2.size() == 2) {
                this.extensions.put(aSN1Sequence2.getObjectAt(0), new com.android.internal.org.bouncycastle.asn1.x509.X509Extension(false, com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(aSN1Sequence2.getObjectAt(1))));
            } else {
                throw new java.lang.IllegalArgumentException("Bad sequence size: " + aSN1Sequence2.size());
            }
            this.ordering.addElement(aSN1Sequence2.getObjectAt(0));
        }
    }

    public X509Extensions(java.util.Hashtable hashtable) {
        this((java.util.Vector) null, hashtable);
    }

    public X509Extensions(java.util.Vector vector, java.util.Hashtable hashtable) {
        java.util.Enumeration elements;
        this.extensions = new java.util.Hashtable();
        this.ordering = new java.util.Vector();
        if (vector == null) {
            elements = hashtable.keys();
        } else {
            elements = vector.elements();
        }
        while (elements.hasMoreElements()) {
            this.ordering.addElement(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier.getInstance(elements.nextElement()));
        }
        java.util.Enumeration elements2 = this.ordering.elements();
        while (elements2.hasMoreElements()) {
            com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier = com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier.getInstance(elements2.nextElement());
            this.extensions.put(aSN1ObjectIdentifier, (com.android.internal.org.bouncycastle.asn1.x509.X509Extension) hashtable.get(aSN1ObjectIdentifier));
        }
    }

    public X509Extensions(java.util.Vector vector, java.util.Vector vector2) {
        this.extensions = new java.util.Hashtable();
        this.ordering = new java.util.Vector();
        java.util.Enumeration elements = vector.elements();
        while (elements.hasMoreElements()) {
            this.ordering.addElement(elements.nextElement());
        }
        java.util.Enumeration elements2 = this.ordering.elements();
        int i = 0;
        while (elements2.hasMoreElements()) {
            this.extensions.put((com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) elements2.nextElement(), (com.android.internal.org.bouncycastle.asn1.x509.X509Extension) vector2.elementAt(i));
            i++;
        }
    }

    public java.util.Enumeration oids() {
        return this.ordering.elements();
    }

    public com.android.internal.org.bouncycastle.asn1.x509.X509Extension getExtension(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return (com.android.internal.org.bouncycastle.asn1.x509.X509Extension) this.extensions.get(aSN1ObjectIdentifier);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(this.ordering.size());
        java.util.Enumeration elements = this.ordering.elements();
        while (elements.hasMoreElements()) {
            com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector2 = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(3);
            com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) elements.nextElement();
            com.android.internal.org.bouncycastle.asn1.x509.X509Extension x509Extension = (com.android.internal.org.bouncycastle.asn1.x509.X509Extension) this.extensions.get(aSN1ObjectIdentifier);
            aSN1EncodableVector2.add(aSN1ObjectIdentifier);
            if (x509Extension.isCritical()) {
                aSN1EncodableVector2.add(com.android.internal.org.bouncycastle.asn1.ASN1Boolean.TRUE);
            }
            aSN1EncodableVector2.add(x509Extension.getValue());
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector2));
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }

    public boolean equivalent(com.android.internal.org.bouncycastle.asn1.x509.X509Extensions x509Extensions) {
        if (this.extensions.size() != x509Extensions.extensions.size()) {
            return false;
        }
        java.util.Enumeration keys = this.extensions.keys();
        while (keys.hasMoreElements()) {
            java.lang.Object nextElement = keys.nextElement();
            if (!this.extensions.get(nextElement).equals(x509Extensions.extensions.get(nextElement))) {
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
            if (((com.android.internal.org.bouncycastle.asn1.x509.X509Extension) this.extensions.get(elementAt)).isCritical() == z) {
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
