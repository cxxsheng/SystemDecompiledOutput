package com.android.internal.org.bouncycastle.asn1.x500;

/* loaded from: classes4.dex */
public class X500Name extends com.android.internal.org.bouncycastle.asn1.ASN1Object implements com.android.internal.org.bouncycastle.asn1.ASN1Choice {
    private static com.android.internal.org.bouncycastle.asn1.x500.X500NameStyle defaultStyle = com.android.internal.org.bouncycastle.asn1.x500.style.BCStyle.INSTANCE;
    private int hashCodeValue;
    private boolean isHashCodeCalculated;
    private com.android.internal.org.bouncycastle.asn1.DERSequence rdnSeq;
    private com.android.internal.org.bouncycastle.asn1.x500.RDN[] rdns;
    private com.android.internal.org.bouncycastle.asn1.x500.X500NameStyle style;

    public X500Name(com.android.internal.org.bouncycastle.asn1.x500.X500NameStyle x500NameStyle, com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name) {
        this.style = x500NameStyle;
        this.rdns = x500Name.rdns;
        this.rdnSeq = x500Name.rdnSeq;
    }

    public static com.android.internal.org.bouncycastle.asn1.x500.X500Name getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, true));
    }

    public static com.android.internal.org.bouncycastle.asn1.x500.X500Name getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x500.X500Name) {
            return (com.android.internal.org.bouncycastle.asn1.x500.X500Name) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x500.X500Name(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static com.android.internal.org.bouncycastle.asn1.x500.X500Name getInstance(com.android.internal.org.bouncycastle.asn1.x500.X500NameStyle x500NameStyle, java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x500.X500Name) {
            return new com.android.internal.org.bouncycastle.asn1.x500.X500Name(x500NameStyle, (com.android.internal.org.bouncycastle.asn1.x500.X500Name) obj);
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x500.X500Name(x500NameStyle, com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    private X500Name(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this(defaultStyle, aSN1Sequence);
    }

    private X500Name(com.android.internal.org.bouncycastle.asn1.x500.X500NameStyle x500NameStyle, com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.style = x500NameStyle;
        this.rdns = new com.android.internal.org.bouncycastle.asn1.x500.RDN[aSN1Sequence.size()];
        java.util.Enumeration objects = aSN1Sequence.getObjects();
        boolean z = true;
        int i = 0;
        while (objects.hasMoreElements()) {
            java.lang.Object nextElement = objects.nextElement();
            com.android.internal.org.bouncycastle.asn1.x500.RDN rdn = com.android.internal.org.bouncycastle.asn1.x500.RDN.getInstance(nextElement);
            z &= rdn == nextElement;
            this.rdns[i] = rdn;
            i++;
        }
        if (z) {
            this.rdnSeq = com.android.internal.org.bouncycastle.asn1.DERSequence.convert(aSN1Sequence);
        } else {
            this.rdnSeq = new com.android.internal.org.bouncycastle.asn1.DERSequence(this.rdns);
        }
    }

    public X500Name(com.android.internal.org.bouncycastle.asn1.x500.RDN[] rdnArr) {
        this(defaultStyle, rdnArr);
    }

    public X500Name(com.android.internal.org.bouncycastle.asn1.x500.X500NameStyle x500NameStyle, com.android.internal.org.bouncycastle.asn1.x500.RDN[] rdnArr) {
        this.style = x500NameStyle;
        this.rdns = (com.android.internal.org.bouncycastle.asn1.x500.RDN[]) rdnArr.clone();
        this.rdnSeq = new com.android.internal.org.bouncycastle.asn1.DERSequence(this.rdns);
    }

    public X500Name(java.lang.String str) {
        this(defaultStyle, str);
    }

    public X500Name(com.android.internal.org.bouncycastle.asn1.x500.X500NameStyle x500NameStyle, java.lang.String str) {
        this(x500NameStyle.fromString(str));
        this.style = x500NameStyle;
    }

    public com.android.internal.org.bouncycastle.asn1.x500.RDN[] getRDNs() {
        return (com.android.internal.org.bouncycastle.asn1.x500.RDN[]) this.rdns.clone();
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier[] getAttributeTypes() {
        int length = this.rdns.length;
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            i += this.rdns[i2].size();
        }
        com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier[] aSN1ObjectIdentifierArr = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier[i];
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4++) {
            i3 += this.rdns[i4].collectAttributeTypes(aSN1ObjectIdentifierArr, i3);
        }
        return aSN1ObjectIdentifierArr;
    }

    public com.android.internal.org.bouncycastle.asn1.x500.RDN[] getRDNs(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        int length = this.rdns.length;
        com.android.internal.org.bouncycastle.asn1.x500.RDN[] rdnArr = new com.android.internal.org.bouncycastle.asn1.x500.RDN[length];
        int i = 0;
        for (int i2 = 0; i2 != this.rdns.length; i2++) {
            com.android.internal.org.bouncycastle.asn1.x500.RDN rdn = this.rdns[i2];
            if (rdn.containsAttributeType(aSN1ObjectIdentifier)) {
                rdnArr[i] = rdn;
                i++;
            }
        }
        if (i < length) {
            com.android.internal.org.bouncycastle.asn1.x500.RDN[] rdnArr2 = new com.android.internal.org.bouncycastle.asn1.x500.RDN[i];
            java.lang.System.arraycopy(rdnArr, 0, rdnArr2, 0, i);
            return rdnArr2;
        }
        return rdnArr;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return this.rdnSeq;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        if (this.isHashCodeCalculated) {
            return this.hashCodeValue;
        }
        this.isHashCodeCalculated = true;
        this.hashCodeValue = this.style.calculateHashCode(this);
        return this.hashCodeValue;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object
    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof com.android.internal.org.bouncycastle.asn1.x500.X500Name) && !(obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1Sequence)) {
            return false;
        }
        if (toASN1Primitive().equals(((com.android.internal.org.bouncycastle.asn1.ASN1Encodable) obj).toASN1Primitive())) {
            return true;
        }
        try {
            return this.style.areEqual(this, new com.android.internal.org.bouncycastle.asn1.x500.X500Name(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(((com.android.internal.org.bouncycastle.asn1.ASN1Encodable) obj).toASN1Primitive())));
        } catch (java.lang.Exception e) {
            return false;
        }
    }

    public java.lang.String toString() {
        return this.style.toString(this);
    }

    public static void setDefaultStyle(com.android.internal.org.bouncycastle.asn1.x500.X500NameStyle x500NameStyle) {
        if (x500NameStyle == null) {
            throw new java.lang.NullPointerException("cannot set style to null");
        }
        defaultStyle = x500NameStyle;
    }

    public static com.android.internal.org.bouncycastle.asn1.x500.X500NameStyle getDefaultStyle() {
        return defaultStyle;
    }
}
