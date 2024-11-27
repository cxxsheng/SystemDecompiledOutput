package com.android.internal.org.bouncycastle.asn1.x500.style;

/* loaded from: classes4.dex */
public abstract class AbstractX500NameStyle implements com.android.internal.org.bouncycastle.asn1.x500.X500NameStyle {
    public static java.util.Hashtable copyHashTable(java.util.Hashtable hashtable) {
        java.util.Hashtable hashtable2 = new java.util.Hashtable();
        java.util.Enumeration keys = hashtable.keys();
        while (keys.hasMoreElements()) {
            java.lang.Object nextElement = keys.nextElement();
            hashtable2.put(nextElement, hashtable.get(nextElement));
        }
        return hashtable2;
    }

    private int calcHashCode(com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        return com.android.internal.org.bouncycastle.asn1.x500.style.IETFUtils.canonicalString(aSN1Encodable).hashCode();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.x500.X500NameStyle
    public int calculateHashCode(com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name) {
        com.android.internal.org.bouncycastle.asn1.x500.RDN[] rDNs = x500Name.getRDNs();
        int i = 0;
        for (int i2 = 0; i2 != rDNs.length; i2++) {
            if (rDNs[i2].isMultiValued()) {
                com.android.internal.org.bouncycastle.asn1.x500.AttributeTypeAndValue[] typesAndValues = rDNs[i2].getTypesAndValues();
                for (int i3 = 0; i3 != typesAndValues.length; i3++) {
                    i = (i ^ typesAndValues[i3].getType().hashCode()) ^ calcHashCode(typesAndValues[i3].getValue());
                }
            } else {
                i = (i ^ rDNs[i2].getFirst().getType().hashCode()) ^ calcHashCode(rDNs[i2].getFirst().getValue());
            }
        }
        return i;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.x500.X500NameStyle
    public com.android.internal.org.bouncycastle.asn1.ASN1Encodable stringToValue(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, java.lang.String str) {
        if (str.length() != 0 && str.charAt(0) == '#') {
            try {
                return com.android.internal.org.bouncycastle.asn1.x500.style.IETFUtils.valueFromHexString(str, 1);
            } catch (java.io.IOException e) {
                throw new com.android.internal.org.bouncycastle.asn1.ASN1ParsingException("can't recode value for oid " + aSN1ObjectIdentifier.getId());
            }
        }
        if (str.length() != 0 && str.charAt(0) == '\\') {
            str = str.substring(1);
        }
        return encodeStringValue(aSN1ObjectIdentifier, str);
    }

    protected com.android.internal.org.bouncycastle.asn1.ASN1Encodable encodeStringValue(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, java.lang.String str) {
        return new com.android.internal.org.bouncycastle.asn1.DERUTF8String(str);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.x500.X500NameStyle
    public boolean areEqual(com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name, com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name2) {
        com.android.internal.org.bouncycastle.asn1.x500.RDN[] rDNs = x500Name.getRDNs();
        com.android.internal.org.bouncycastle.asn1.x500.RDN[] rDNs2 = x500Name2.getRDNs();
        if (rDNs.length != rDNs2.length) {
            return false;
        }
        boolean z = (rDNs[0].getFirst() != null && rDNs2[0].getFirst() != null) ? !rDNs[0].getFirst().getType().equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) rDNs2[0].getFirst().getType()) : false;
        for (int i = 0; i != rDNs.length; i++) {
            if (!foundMatch(z, rDNs[i], rDNs2)) {
                return false;
            }
        }
        return true;
    }

    private boolean foundMatch(boolean z, com.android.internal.org.bouncycastle.asn1.x500.RDN rdn, com.android.internal.org.bouncycastle.asn1.x500.RDN[] rdnArr) {
        if (z) {
            for (int length = rdnArr.length - 1; length >= 0; length--) {
                if (rdnArr[length] != null && rdnAreEqual(rdn, rdnArr[length])) {
                    rdnArr[length] = null;
                    return true;
                }
            }
        } else {
            for (int i = 0; i != rdnArr.length; i++) {
                if (rdnArr[i] != null && rdnAreEqual(rdn, rdnArr[i])) {
                    rdnArr[i] = null;
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean rdnAreEqual(com.android.internal.org.bouncycastle.asn1.x500.RDN rdn, com.android.internal.org.bouncycastle.asn1.x500.RDN rdn2) {
        return com.android.internal.org.bouncycastle.asn1.x500.style.IETFUtils.rDNAreEqual(rdn, rdn2);
    }
}
