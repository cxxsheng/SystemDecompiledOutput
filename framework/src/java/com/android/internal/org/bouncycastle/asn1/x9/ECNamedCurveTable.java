package com.android.internal.org.bouncycastle.asn1.x9;

/* loaded from: classes4.dex */
public class ECNamedCurveTable {
    public static com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters getByName(java.lang.String str) {
        com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters byName = com.android.internal.org.bouncycastle.asn1.x9.X962NamedCurves.getByName(str);
        if (byName == null) {
            byName = com.android.internal.org.bouncycastle.asn1.sec.SECNamedCurves.getByName(str);
        }
        if (byName == null) {
            return com.android.internal.org.bouncycastle.asn1.nist.NISTNamedCurves.getByName(str);
        }
        return byName;
    }

    public static com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier getOID(java.lang.String str) {
        com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier oid = com.android.internal.org.bouncycastle.asn1.x9.X962NamedCurves.getOID(str);
        if (oid == null) {
            oid = com.android.internal.org.bouncycastle.asn1.sec.SECNamedCurves.getOID(str);
        }
        if (oid == null) {
            return com.android.internal.org.bouncycastle.asn1.nist.NISTNamedCurves.getOID(str);
        }
        return oid;
    }

    public static java.lang.String getName(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        java.lang.String name = com.android.internal.org.bouncycastle.asn1.x9.X962NamedCurves.getName(aSN1ObjectIdentifier);
        if (name == null) {
            name = com.android.internal.org.bouncycastle.asn1.sec.SECNamedCurves.getName(aSN1ObjectIdentifier);
        }
        if (name == null) {
            return com.android.internal.org.bouncycastle.asn1.nist.NISTNamedCurves.getName(aSN1ObjectIdentifier);
        }
        return name;
    }

    public static com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters getByOID(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters byOID = com.android.internal.org.bouncycastle.asn1.x9.X962NamedCurves.getByOID(aSN1ObjectIdentifier);
        if (byOID == null) {
            return com.android.internal.org.bouncycastle.asn1.sec.SECNamedCurves.getByOID(aSN1ObjectIdentifier);
        }
        return byOID;
    }

    public static java.util.Enumeration getNames() {
        java.util.Vector vector = new java.util.Vector();
        addEnumeration(vector, com.android.internal.org.bouncycastle.asn1.x9.X962NamedCurves.getNames());
        addEnumeration(vector, com.android.internal.org.bouncycastle.asn1.sec.SECNamedCurves.getNames());
        addEnumeration(vector, com.android.internal.org.bouncycastle.asn1.nist.NISTNamedCurves.getNames());
        return vector.elements();
    }

    private static void addEnumeration(java.util.Vector vector, java.util.Enumeration enumeration) {
        while (enumeration.hasMoreElements()) {
            vector.addElement(enumeration.nextElement());
        }
    }
}
