package com.android.internal.org.bouncycastle.asn1.x500;

/* loaded from: classes4.dex */
public interface X500NameStyle {
    boolean areEqual(com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name, com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name2);

    com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier attrNameToOID(java.lang.String str);

    int calculateHashCode(com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name);

    com.android.internal.org.bouncycastle.asn1.x500.RDN[] fromString(java.lang.String str);

    java.lang.String[] oidToAttrNames(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier);

    java.lang.String oidToDisplayName(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier);

    com.android.internal.org.bouncycastle.asn1.ASN1Encodable stringToValue(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, java.lang.String str);

    java.lang.String toString(com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name);
}
