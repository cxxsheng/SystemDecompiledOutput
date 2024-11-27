package com.android.internal.org.bouncycastle.asn1.nist;

/* loaded from: classes4.dex */
public class NISTNamedCurves {
    static final java.util.Hashtable objIds = new java.util.Hashtable();
    static final java.util.Hashtable names = new java.util.Hashtable();

    static {
        defineCurve("B-571", com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers.sect571r1);
        defineCurve("B-409", com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers.sect409r1);
        defineCurve("B-283", com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers.sect283r1);
        defineCurve("B-233", com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers.sect233r1);
        defineCurve("B-163", com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers.sect163r2);
        defineCurve("K-571", com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers.sect571k1);
        defineCurve("K-409", com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers.sect409k1);
        defineCurve("K-283", com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers.sect283k1);
        defineCurve("K-233", com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers.sect233k1);
        defineCurve("K-163", com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers.sect163k1);
        defineCurve("P-521", com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers.secp521r1);
        defineCurve("P-384", com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers.secp384r1);
        defineCurve("P-256", com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers.secp256r1);
        defineCurve("P-224", com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers.secp224r1);
        defineCurve("P-192", com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers.secp192r1);
    }

    static void defineCurve(java.lang.String str, com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        objIds.put(str, aSN1ObjectIdentifier);
        names.put(aSN1ObjectIdentifier, str);
    }

    public static com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters getByName(java.lang.String str) {
        com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) objIds.get(com.android.internal.org.bouncycastle.util.Strings.toUpperCase(str));
        if (aSN1ObjectIdentifier != null) {
            return getByOID(aSN1ObjectIdentifier);
        }
        return null;
    }

    public static com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters getByOID(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return com.android.internal.org.bouncycastle.asn1.sec.SECNamedCurves.getByOID(aSN1ObjectIdentifier);
    }

    public static com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier getOID(java.lang.String str) {
        return (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) objIds.get(com.android.internal.org.bouncycastle.util.Strings.toUpperCase(str));
    }

    public static java.lang.String getName(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return (java.lang.String) names.get(aSN1ObjectIdentifier);
    }

    public static java.util.Enumeration getNames() {
        return objIds.keys();
    }
}
