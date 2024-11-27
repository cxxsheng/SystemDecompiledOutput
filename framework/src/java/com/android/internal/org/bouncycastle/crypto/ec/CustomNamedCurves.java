package com.android.internal.org.bouncycastle.crypto.ec;

/* loaded from: classes4.dex */
public class CustomNamedCurves {
    static com.android.internal.org.bouncycastle.asn1.x9.X9ECParametersHolder secp192k1 = new com.android.internal.org.bouncycastle.asn1.x9.X9ECParametersHolder() { // from class: com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves.1
        @Override // com.android.internal.org.bouncycastle.asn1.x9.X9ECParametersHolder
        protected com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters createParameters() {
            com.android.internal.org.bouncycastle.math.ec.ECCurve configureCurveGLV = com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves.configureCurveGLV(new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1Curve(), new com.android.internal.org.bouncycastle.math.ec.endo.GLVTypeBParameters(new java.math.BigInteger("bb85691939b869c1d087f601554b96b80cb4f55b35f433c2", 16), new java.math.BigInteger("3d84f26c12238d7b4f3d516613c1759033b1a5800175d0b1", 16), new com.android.internal.org.bouncycastle.math.ec.endo.ScalarSplitParameters(new java.math.BigInteger[]{new java.math.BigInteger("71169be7330b3038edb025f1", 16), new java.math.BigInteger("-b3fb3400dec5c4adceb8655c", 16)}, new java.math.BigInteger[]{new java.math.BigInteger("12511cfe811d0f4e6bc688b4d", 16), new java.math.BigInteger("71169be7330b3038edb025f1", 16)}, new java.math.BigInteger("71169be7330b3038edb025f1d0f9", 16), new java.math.BigInteger("b3fb3400dec5c4adceb8655d4c94", 16), 208)));
            return new com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters(configureCurveGLV, com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves.configureBasepoint(configureCurveGLV, "04DB4FF10EC057E9AE26B07D0280B7F4341DA5D1B1EAE06C7D9B2F2F6D9C5628A7844163D015BE86344082AA88D95E2F9D"), configureCurveGLV.getOrder(), configureCurveGLV.getCofactor(), null);
        }
    };
    static com.android.internal.org.bouncycastle.asn1.x9.X9ECParametersHolder secp192r1 = new com.android.internal.org.bouncycastle.asn1.x9.X9ECParametersHolder() { // from class: com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves.2
        @Override // com.android.internal.org.bouncycastle.asn1.x9.X9ECParametersHolder
        protected com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters createParameters() {
            byte[] decodeStrict = com.android.internal.org.bouncycastle.util.encoders.Hex.decodeStrict("3045AE6FC8422F64ED579528D38120EAE12196D5");
            com.android.internal.org.bouncycastle.math.ec.ECCurve configureCurve = com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves.configureCurve(new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192R1Curve());
            return new com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters(configureCurve, com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves.configureBasepoint(configureCurve, "04188DA80EB03090F67CBF20EB43A18800F4FF0AFD82FF101207192B95FFC8DA78631011ED6B24CDD573F977A11E794811"), configureCurve.getOrder(), configureCurve.getCofactor(), decodeStrict);
        }
    };
    static com.android.internal.org.bouncycastle.asn1.x9.X9ECParametersHolder secp224k1 = new com.android.internal.org.bouncycastle.asn1.x9.X9ECParametersHolder() { // from class: com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves.3
        @Override // com.android.internal.org.bouncycastle.asn1.x9.X9ECParametersHolder
        protected com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters createParameters() {
            com.android.internal.org.bouncycastle.math.ec.ECCurve configureCurveGLV = com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves.configureCurveGLV(new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Curve(), new com.android.internal.org.bouncycastle.math.ec.endo.GLVTypeBParameters(new java.math.BigInteger("fe0e87005b4e83761908c5131d552a850b3f58b749c37cf5b84d6768", 16), new java.math.BigInteger("60dcd2104c4cbc0be6eeefc2bdd610739ec34e317f9b33046c9e4788", 16), new com.android.internal.org.bouncycastle.math.ec.endo.ScalarSplitParameters(new java.math.BigInteger[]{new java.math.BigInteger("6b8cf07d4ca75c88957d9d670591", 16), new java.math.BigInteger("-b8adf1378a6eb73409fa6c9c637d", 16)}, new java.math.BigInteger[]{new java.math.BigInteger("1243ae1b4d71613bc9f780a03690e", 16), new java.math.BigInteger("6b8cf07d4ca75c88957d9d670591", 16)}, new java.math.BigInteger("6b8cf07d4ca75c88957d9d67059037a4", 16), new java.math.BigInteger("b8adf1378a6eb73409fa6c9c637ba7f5", 16), 240)));
            return new com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters(configureCurveGLV, com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves.configureBasepoint(configureCurveGLV, "04A1455B334DF099DF30FC28A169A467E9E47075A90F7E650EB6B7A45C7E089FED7FBA344282CAFBD6F7E319F7C0B0BD59E2CA4BDB556D61A5"), configureCurveGLV.getOrder(), configureCurveGLV.getCofactor(), null);
        }
    };
    static com.android.internal.org.bouncycastle.asn1.x9.X9ECParametersHolder secp224r1 = new com.android.internal.org.bouncycastle.asn1.x9.X9ECParametersHolder() { // from class: com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves.4
        @Override // com.android.internal.org.bouncycastle.asn1.x9.X9ECParametersHolder
        protected com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters createParameters() {
            byte[] decodeStrict = com.android.internal.org.bouncycastle.util.encoders.Hex.decodeStrict("BD71344799D5C7FCDC45B59FA3B9AB8F6A948BC5");
            com.android.internal.org.bouncycastle.math.ec.ECCurve configureCurve = com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves.configureCurve(new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Curve());
            return new com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters(configureCurve, com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves.configureBasepoint(configureCurve, "04B70E0CBD6BB4BF7F321390B94A03C1D356C21122343280D6115C1D21BD376388B5F723FB4C22DFE6CD4375A05A07476444D5819985007E34"), configureCurve.getOrder(), configureCurve.getCofactor(), decodeStrict);
        }
    };
    static com.android.internal.org.bouncycastle.asn1.x9.X9ECParametersHolder secp256k1 = new com.android.internal.org.bouncycastle.asn1.x9.X9ECParametersHolder() { // from class: com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves.5
        @Override // com.android.internal.org.bouncycastle.asn1.x9.X9ECParametersHolder
        protected com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters createParameters() {
            com.android.internal.org.bouncycastle.math.ec.ECCurve configureCurveGLV = com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves.configureCurveGLV(new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256K1Curve(), new com.android.internal.org.bouncycastle.math.ec.endo.GLVTypeBParameters(new java.math.BigInteger("7ae96a2b657c07106e64479eac3434e99cf0497512f58995c1396c28719501ee", 16), new java.math.BigInteger("5363ad4cc05c30e0a5261c028812645a122e22ea20816678df02967c1b23bd72", 16), new com.android.internal.org.bouncycastle.math.ec.endo.ScalarSplitParameters(new java.math.BigInteger[]{new java.math.BigInteger("3086d221a7d46bcde86c90e49284eb15", 16), new java.math.BigInteger("-e4437ed6010e88286f547fa90abfe4c3", 16)}, new java.math.BigInteger[]{new java.math.BigInteger("114ca50f7a8e2f3f657c1108d9d44cfd8", 16), new java.math.BigInteger("3086d221a7d46bcde86c90e49284eb15", 16)}, new java.math.BigInteger("3086d221a7d46bcde86c90e49284eb153dab", 16), new java.math.BigInteger("e4437ed6010e88286f547fa90abfe4c42212", 16), 272)));
            return new com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters(configureCurveGLV, com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves.configureBasepoint(configureCurveGLV, "0479BE667EF9DCBBAC55A06295CE870B07029BFCDB2DCE28D959F2815B16F81798483ADA7726A3C4655DA4FBFC0E1108A8FD17B448A68554199C47D08FFB10D4B8"), configureCurveGLV.getOrder(), configureCurveGLV.getCofactor(), null);
        }
    };
    static com.android.internal.org.bouncycastle.asn1.x9.X9ECParametersHolder secp256r1 = new com.android.internal.org.bouncycastle.asn1.x9.X9ECParametersHolder() { // from class: com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves.6
        @Override // com.android.internal.org.bouncycastle.asn1.x9.X9ECParametersHolder
        protected com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters createParameters() {
            byte[] decodeStrict = com.android.internal.org.bouncycastle.util.encoders.Hex.decodeStrict("C49D360886E704936A6678E1139D26B7819F7E90");
            com.android.internal.org.bouncycastle.math.ec.ECCurve configureCurve = com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves.configureCurve(new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1Curve());
            return new com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters(configureCurve, com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves.configureBasepoint(configureCurve, "046B17D1F2E12C4247F8BCE6E563A440F277037D812DEB33A0F4A13945D898C2964FE342E2FE1A7F9B8EE7EB4A7C0F9E162BCE33576B315ECECBB6406837BF51F5"), configureCurve.getOrder(), configureCurve.getCofactor(), decodeStrict);
        }
    };
    static com.android.internal.org.bouncycastle.asn1.x9.X9ECParametersHolder secp384r1 = new com.android.internal.org.bouncycastle.asn1.x9.X9ECParametersHolder() { // from class: com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves.7
        @Override // com.android.internal.org.bouncycastle.asn1.x9.X9ECParametersHolder
        protected com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters createParameters() {
            byte[] decodeStrict = com.android.internal.org.bouncycastle.util.encoders.Hex.decodeStrict("A335926AA319A27A1D00896A6773A4827ACDAC73");
            com.android.internal.org.bouncycastle.math.ec.ECCurve configureCurve = com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves.configureCurve(new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Curve());
            return new com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters(configureCurve, com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves.configureBasepoint(configureCurve, "04AA87CA22BE8B05378EB1C71EF320AD746E1D3B628BA79B9859F741E082542A385502F25DBF55296C3A545E3872760AB73617DE4A96262C6F5D9E98BF9292DC29F8F41DBD289A147CE9DA3113B5F0B8C00A60B1CE1D7E819D7A431D7C90EA0E5F"), configureCurve.getOrder(), configureCurve.getCofactor(), decodeStrict);
        }
    };
    static com.android.internal.org.bouncycastle.asn1.x9.X9ECParametersHolder secp521r1 = new com.android.internal.org.bouncycastle.asn1.x9.X9ECParametersHolder() { // from class: com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves.8
        @Override // com.android.internal.org.bouncycastle.asn1.x9.X9ECParametersHolder
        protected com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters createParameters() {
            byte[] decodeStrict = com.android.internal.org.bouncycastle.util.encoders.Hex.decodeStrict("D09E8800291CB85396CC6717393284AAA0DA64BA");
            com.android.internal.org.bouncycastle.math.ec.ECCurve configureCurve = com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves.configureCurve(new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP521R1Curve());
            return new com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters(configureCurve, com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves.configureBasepoint(configureCurve, "0400C6858E06B70404E9CD9E3ECB662395B4429C648139053FB521F828AF606B4D3DBAA14B5E77EFE75928FE1DC127A2FFA8DE3348B3C1856A429BF97E7E31C2E5BD66011839296A789A3BC0045C8A5FB42C7D1BD998F54449579B446817AFBD17273E662C97EE72995EF42640C550B9013FAD0761353C7086A272C24088BE94769FD16650"), configureCurve.getOrder(), configureCurve.getCofactor(), decodeStrict);
        }
    };
    static final java.util.Hashtable nameToCurve = new java.util.Hashtable();
    static final java.util.Hashtable nameToOID = new java.util.Hashtable();
    static final java.util.Hashtable oidToCurve = new java.util.Hashtable();
    static final java.util.Hashtable oidToName = new java.util.Hashtable();
    static final java.util.Vector names = new java.util.Vector();

    /* JADX INFO: Access modifiers changed from: private */
    public static com.android.internal.org.bouncycastle.asn1.x9.X9ECPoint configureBasepoint(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, java.lang.String str) {
        com.android.internal.org.bouncycastle.asn1.x9.X9ECPoint x9ECPoint = new com.android.internal.org.bouncycastle.asn1.x9.X9ECPoint(eCCurve, com.android.internal.org.bouncycastle.util.encoders.Hex.decodeStrict(str));
        com.android.internal.org.bouncycastle.math.ec.WNafUtil.configureBasepoint(x9ECPoint.getPoint());
        return x9ECPoint;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static com.android.internal.org.bouncycastle.math.ec.ECCurve configureCurve(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve) {
        return eCCurve;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static com.android.internal.org.bouncycastle.math.ec.ECCurve configureCurveGLV(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, com.android.internal.org.bouncycastle.math.ec.endo.GLVTypeBParameters gLVTypeBParameters) {
        return eCCurve.configure().setEndomorphism(new com.android.internal.org.bouncycastle.math.ec.endo.GLVTypeBEndomorphism(eCCurve, gLVTypeBParameters)).create();
    }

    static {
        defineCurveWithOID("secp192k1", com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers.secp192k1, secp192k1);
        defineCurveWithOID("secp192r1", com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers.secp192r1, secp192r1);
        defineCurveWithOID("secp224k1", com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers.secp224k1, secp224k1);
        defineCurveWithOID("secp224r1", com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers.secp224r1, secp224r1);
        defineCurveWithOID("secp256k1", com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers.secp256k1, secp256k1);
        defineCurveWithOID("secp256r1", com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers.secp256r1, secp256r1);
        defineCurveWithOID("secp384r1", com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers.secp384r1, secp384r1);
        defineCurveWithOID("secp521r1", com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers.secp521r1, secp521r1);
        defineCurveAlias("P-192", com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers.secp192r1);
        defineCurveAlias("P-224", com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers.secp224r1);
        defineCurveAlias("P-256", com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers.secp256r1);
        defineCurveAlias("P-384", com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers.secp384r1);
        defineCurveAlias("P-521", com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers.secp521r1);
    }

    static void defineCurve(java.lang.String str, com.android.internal.org.bouncycastle.asn1.x9.X9ECParametersHolder x9ECParametersHolder) {
        names.addElement(str);
        nameToCurve.put(com.android.internal.org.bouncycastle.util.Strings.toLowerCase(str), x9ECParametersHolder);
    }

    static void defineCurveWithOID(java.lang.String str, com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.x9.X9ECParametersHolder x9ECParametersHolder) {
        names.addElement(str);
        oidToName.put(aSN1ObjectIdentifier, str);
        oidToCurve.put(aSN1ObjectIdentifier, x9ECParametersHolder);
        java.lang.String lowerCase = com.android.internal.org.bouncycastle.util.Strings.toLowerCase(str);
        nameToOID.put(lowerCase, aSN1ObjectIdentifier);
        nameToCurve.put(lowerCase, x9ECParametersHolder);
    }

    static void defineCurveAlias(java.lang.String str, com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        java.lang.Object obj = oidToCurve.get(aSN1ObjectIdentifier);
        if (obj == null) {
            throw new java.lang.IllegalStateException();
        }
        java.lang.String lowerCase = com.android.internal.org.bouncycastle.util.Strings.toLowerCase(str);
        nameToOID.put(lowerCase, aSN1ObjectIdentifier);
        nameToCurve.put(lowerCase, obj);
    }

    public static com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters getByName(java.lang.String str) {
        com.android.internal.org.bouncycastle.asn1.x9.X9ECParametersHolder x9ECParametersHolder = (com.android.internal.org.bouncycastle.asn1.x9.X9ECParametersHolder) nameToCurve.get(com.android.internal.org.bouncycastle.util.Strings.toLowerCase(str));
        if (x9ECParametersHolder == null) {
            return null;
        }
        return x9ECParametersHolder.getParameters();
    }

    public static com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters getByOID(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        com.android.internal.org.bouncycastle.asn1.x9.X9ECParametersHolder x9ECParametersHolder = (com.android.internal.org.bouncycastle.asn1.x9.X9ECParametersHolder) oidToCurve.get(aSN1ObjectIdentifier);
        if (x9ECParametersHolder == null) {
            return null;
        }
        return x9ECParametersHolder.getParameters();
    }

    public static com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier getOID(java.lang.String str) {
        return (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) nameToOID.get(com.android.internal.org.bouncycastle.util.Strings.toLowerCase(str));
    }

    public static java.lang.String getName(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return (java.lang.String) oidToName.get(aSN1ObjectIdentifier);
    }

    public static java.util.Enumeration getNames() {
        return names.elements();
    }
}
