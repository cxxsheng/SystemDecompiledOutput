package com.android.internal.org.bouncycastle.crypto.util;

/* loaded from: classes4.dex */
public class SSHNamedCurves {
    private static final java.util.Map<java.lang.String, com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier> oidMap = java.util.Collections.unmodifiableMap(new java.util.HashMap<java.lang.String, com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier>() { // from class: com.android.internal.org.bouncycastle.crypto.util.SSHNamedCurves.1
        {
            put("nistp256", com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers.secp256r1);
            put("nistp384", com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers.secp384r1);
            put("nistp521", com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers.secp521r1);
            put("nistk163", com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers.sect163k1);
            put("nistp192", com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers.secp192r1);
            put("nistp224", com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers.secp224r1);
            put("nistk233", com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers.sect233k1);
            put("nistb233", com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers.sect233r1);
            put("nistk283", com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers.sect283k1);
            put("nistk409", com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers.sect409k1);
            put("nistb409", com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers.sect409r1);
            put("nistt571", com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers.sect571k1);
        }
    });
    private static final java.util.Map<java.lang.String, java.lang.String> curveNameToSSHName = java.util.Collections.unmodifiableMap(new java.util.HashMap<java.lang.String, java.lang.String>() { // from class: com.android.internal.org.bouncycastle.crypto.util.SSHNamedCurves.2
        {
            java.lang.String[][] strArr = {new java.lang.String[]{"secp256r1", "nistp256"}, new java.lang.String[]{"secp384r1", "nistp384"}, new java.lang.String[]{"secp521r1", "nistp521"}, new java.lang.String[]{"sect163k1", "nistk163"}, new java.lang.String[]{"secp192r1", "nistp192"}, new java.lang.String[]{"secp224r1", "nistp224"}, new java.lang.String[]{"sect233k1", "nistk233"}, new java.lang.String[]{"sect233r1", "nistb233"}, new java.lang.String[]{"sect283k1", "nistk283"}, new java.lang.String[]{"sect409k1", "nistk409"}, new java.lang.String[]{"sect409r1", "nistb409"}, new java.lang.String[]{"sect571k1", "nistt571"}};
            for (int i = 0; i != 12; i++) {
                java.lang.String[] strArr2 = strArr[i];
                put(strArr2[0], strArr2[1]);
            }
        }
    });
    private static java.util.HashMap<com.android.internal.org.bouncycastle.math.ec.ECCurve, java.lang.String> curveMap = new java.util.HashMap<com.android.internal.org.bouncycastle.math.ec.ECCurve, java.lang.String>() { // from class: com.android.internal.org.bouncycastle.crypto.util.SSHNamedCurves.3
        {
            java.util.Enumeration names = com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves.getNames();
            while (names.hasMoreElements()) {
                java.lang.String str = (java.lang.String) names.nextElement();
                put(com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves.getByName(str).getCurve(), str);
            }
        }
    };
    private static final java.util.Map<com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier, java.lang.String> oidToName = java.util.Collections.unmodifiableMap(new java.util.HashMap<com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier, java.lang.String>() { // from class: com.android.internal.org.bouncycastle.crypto.util.SSHNamedCurves.4
        {
            for (java.lang.String str : com.android.internal.org.bouncycastle.crypto.util.SSHNamedCurves.oidMap.keySet()) {
                put((com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) com.android.internal.org.bouncycastle.crypto.util.SSHNamedCurves.oidMap.get(str), str);
            }
        }
    });

    public static com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier getByName(java.lang.String str) {
        return oidMap.get(str);
    }

    public static com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters getParameters(java.lang.String str) {
        return com.android.internal.org.bouncycastle.asn1.nist.NISTNamedCurves.getByOID(oidMap.get(com.android.internal.org.bouncycastle.util.Strings.toLowerCase(str)));
    }

    public static com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters getParameters(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return com.android.internal.org.bouncycastle.asn1.nist.NISTNamedCurves.getByOID(aSN1ObjectIdentifier);
    }

    public static java.lang.String getName(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return oidToName.get(aSN1ObjectIdentifier);
    }

    public static java.lang.String getNameForParameters(com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters eCDomainParameters) {
        if (eCDomainParameters instanceof com.android.internal.org.bouncycastle.crypto.params.ECNamedDomainParameters) {
            return getName(((com.android.internal.org.bouncycastle.crypto.params.ECNamedDomainParameters) eCDomainParameters).getName());
        }
        return getNameForParameters(eCDomainParameters.getCurve());
    }

    public static java.lang.String getNameForParameters(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve) {
        return curveNameToSSHName.get(curveMap.get(eCCurve));
    }
}
