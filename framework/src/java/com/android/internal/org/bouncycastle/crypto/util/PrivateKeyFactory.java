package com.android.internal.org.bouncycastle.crypto.util;

/* loaded from: classes4.dex */
public class PrivateKeyFactory {
    public static com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter createKey(byte[] bArr) throws java.io.IOException {
        return createKey(com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo.getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(bArr)));
    }

    public static com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter createKey(java.io.InputStream inputStream) throws java.io.IOException {
        return createKey(com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo.getInstance(new com.android.internal.org.bouncycastle.asn1.ASN1InputStream(inputStream).readObject()));
    }

    public static com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter createKey(com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo privateKeyInfo) throws java.io.IOException {
        com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters eCDomainParameters;
        com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier privateKeyAlgorithm = privateKeyInfo.getPrivateKeyAlgorithm();
        com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier algorithm = privateKeyAlgorithm.getAlgorithm();
        if (algorithm.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.rsaEncryption) || algorithm.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_RSASSA_PSS) || algorithm.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.x509.X509ObjectIdentifiers.id_ea_rsa)) {
            com.android.internal.org.bouncycastle.asn1.pkcs.RSAPrivateKey rSAPrivateKey = com.android.internal.org.bouncycastle.asn1.pkcs.RSAPrivateKey.getInstance(privateKeyInfo.parsePrivateKey());
            return new com.android.internal.org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters(rSAPrivateKey.getModulus(), rSAPrivateKey.getPublicExponent(), rSAPrivateKey.getPrivateExponent(), rSAPrivateKey.getPrime1(), rSAPrivateKey.getPrime2(), rSAPrivateKey.getExponent1(), rSAPrivateKey.getExponent2(), rSAPrivateKey.getCoefficient());
        }
        com.android.internal.org.bouncycastle.crypto.params.DSAParameters dSAParameters = null;
        if (algorithm.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.dhKeyAgreement)) {
            com.android.internal.org.bouncycastle.asn1.pkcs.DHParameter dHParameter = com.android.internal.org.bouncycastle.asn1.pkcs.DHParameter.getInstance(privateKeyAlgorithm.getParameters());
            com.android.internal.org.bouncycastle.asn1.ASN1Integer aSN1Integer = (com.android.internal.org.bouncycastle.asn1.ASN1Integer) privateKeyInfo.parsePrivateKey();
            java.math.BigInteger l = dHParameter.getL();
            return new com.android.internal.org.bouncycastle.crypto.params.DHPrivateKeyParameters(aSN1Integer.getValue(), new com.android.internal.org.bouncycastle.crypto.params.DHParameters(dHParameter.getP(), dHParameter.getG(), null, l == null ? 0 : l.intValue()));
        }
        if (algorithm.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.id_dsa)) {
            com.android.internal.org.bouncycastle.asn1.ASN1Integer aSN1Integer2 = (com.android.internal.org.bouncycastle.asn1.ASN1Integer) privateKeyInfo.parsePrivateKey();
            com.android.internal.org.bouncycastle.asn1.ASN1Encodable parameters = privateKeyAlgorithm.getParameters();
            if (parameters != null) {
                com.android.internal.org.bouncycastle.asn1.x509.DSAParameter dSAParameter = com.android.internal.org.bouncycastle.asn1.x509.DSAParameter.getInstance(parameters.toASN1Primitive());
                dSAParameters = new com.android.internal.org.bouncycastle.crypto.params.DSAParameters(dSAParameter.getP(), dSAParameter.getQ(), dSAParameter.getG());
            }
            return new com.android.internal.org.bouncycastle.crypto.params.DSAPrivateKeyParameters(aSN1Integer2.getValue(), dSAParameters);
        }
        if (algorithm.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.id_ecPublicKey)) {
            com.android.internal.org.bouncycastle.asn1.x9.X962Parameters x962Parameters = com.android.internal.org.bouncycastle.asn1.x9.X962Parameters.getInstance(privateKeyAlgorithm.getParameters());
            if (x962Parameters.isNamedCurve()) {
                com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) x962Parameters.getParameters();
                com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters byOID = com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves.getByOID(aSN1ObjectIdentifier);
                if (byOID == null) {
                    byOID = com.android.internal.org.bouncycastle.asn1.x9.ECNamedCurveTable.getByOID(aSN1ObjectIdentifier);
                }
                eCDomainParameters = new com.android.internal.org.bouncycastle.crypto.params.ECNamedDomainParameters(aSN1ObjectIdentifier, byOID);
            } else {
                com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters x9ECParameters = com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters.getInstance(x962Parameters.getParameters());
                eCDomainParameters = new com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters(x9ECParameters.getCurve(), x9ECParameters.getG(), x9ECParameters.getN(), x9ECParameters.getH(), x9ECParameters.getSeed());
            }
            return new com.android.internal.org.bouncycastle.crypto.params.ECPrivateKeyParameters(com.android.internal.org.bouncycastle.asn1.sec.ECPrivateKey.getInstance(privateKeyInfo.parsePrivateKey()).getKey(), eCDomainParameters);
        }
        throw new java.lang.RuntimeException("algorithm identifier in private key not recognised");
    }

    private static byte[] getRawKey(com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo privateKeyInfo, int i) throws java.io.IOException {
        byte[] octets = com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(privateKeyInfo.parsePrivateKey()).getOctets();
        if (i != octets.length) {
            throw new java.lang.RuntimeException("private key encoding has incorrect length");
        }
        return octets;
    }
}
