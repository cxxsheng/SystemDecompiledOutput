package com.android.internal.org.bouncycastle.jce;

/* loaded from: classes4.dex */
public class ECNamedCurveTable {
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0013, code lost:
    
        r0 = com.android.internal.org.bouncycastle.asn1.x9.ECNamedCurveTable.getByName(r8);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveParameterSpec getParameterSpec(java.lang.String str) {
        com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters byName = com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves.getByName(str);
        if (byName == null) {
            try {
                byName = com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves.getByOID(new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier(str));
            } catch (java.lang.IllegalArgumentException e) {
            }
            if (byName == null && byName == null) {
                try {
                    byName = com.android.internal.org.bouncycastle.asn1.x9.ECNamedCurveTable.getByOID(new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier(str));
                } catch (java.lang.IllegalArgumentException e2) {
                }
            }
        }
        if (byName == null) {
            return null;
        }
        return new com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveParameterSpec(str, byName.getCurve(), byName.getG(), byName.getN(), byName.getH(), byName.getSeed());
    }

    public static java.util.Enumeration getNames() {
        return com.android.internal.org.bouncycastle.asn1.x9.ECNamedCurveTable.getNames();
    }
}
