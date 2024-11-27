package com.android.internal.org.bouncycastle.jcajce.util;

/* loaded from: classes4.dex */
public class ECKeyUtil {
    public static java.security.interfaces.ECPublicKey createKeyWithCompression(java.security.interfaces.ECPublicKey eCPublicKey) {
        return new com.android.internal.org.bouncycastle.jcajce.util.ECKeyUtil.ECPublicKeyWithCompression(eCPublicKey);
    }

    private static class ECPublicKeyWithCompression implements java.security.interfaces.ECPublicKey {
        private final java.security.interfaces.ECPublicKey ecPublicKey;

        public ECPublicKeyWithCompression(java.security.interfaces.ECPublicKey eCPublicKey) {
            this.ecPublicKey = eCPublicKey;
        }

        @Override // java.security.interfaces.ECPublicKey
        public java.security.spec.ECPoint getW() {
            return this.ecPublicKey.getW();
        }

        @Override // java.security.Key
        public java.lang.String getAlgorithm() {
            return this.ecPublicKey.getAlgorithm();
        }

        @Override // java.security.Key
        public java.lang.String getFormat() {
            return this.ecPublicKey.getFormat();
        }

        @Override // java.security.Key
        public byte[] getEncoded() {
            com.android.internal.org.bouncycastle.math.ec.ECCurve curve;
            com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo = com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo.getInstance(this.ecPublicKey.getEncoded());
            com.android.internal.org.bouncycastle.asn1.x9.X962Parameters x962Parameters = com.android.internal.org.bouncycastle.asn1.x9.X962Parameters.getInstance(subjectPublicKeyInfo.getAlgorithm().getParameters());
            if (x962Parameters.isNamedCurve()) {
                com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) x962Parameters.getParameters();
                com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters byOID = com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves.getByOID(aSN1ObjectIdentifier);
                if (byOID == null) {
                    byOID = com.android.internal.org.bouncycastle.asn1.x9.ECNamedCurveTable.getByOID(aSN1ObjectIdentifier);
                }
                curve = byOID.getCurve();
            } else {
                if (x962Parameters.isImplicitlyCA()) {
                    throw new java.lang.IllegalStateException("unable to identify implictlyCA");
                }
                curve = com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters.getInstance(x962Parameters.getParameters()).getCurve();
            }
            try {
                return new com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo(subjectPublicKeyInfo.getAlgorithm(), com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(new com.android.internal.org.bouncycastle.asn1.x9.X9ECPoint(curve.decodePoint(subjectPublicKeyInfo.getPublicKeyData().getOctets()), true).toASN1Primitive()).getOctets()).getEncoded();
            } catch (java.io.IOException e) {
                throw new java.lang.IllegalStateException("unable to encode EC public key: " + e.getMessage());
            }
        }

        @Override // java.security.interfaces.ECKey
        public java.security.spec.ECParameterSpec getParams() {
            return this.ecPublicKey.getParams();
        }
    }
}
