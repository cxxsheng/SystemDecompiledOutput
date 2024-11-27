package com.android.internal.org.bouncycastle.crypto.util;

/* loaded from: classes4.dex */
public class PublicKeyFactory {
    private static java.util.Map converters = new java.util.HashMap();

    static {
        converters.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.rsaEncryption, new com.android.internal.org.bouncycastle.crypto.util.PublicKeyFactory.RSAConverter());
        converters.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_RSASSA_PSS, new com.android.internal.org.bouncycastle.crypto.util.PublicKeyFactory.RSAConverter());
        converters.put(com.android.internal.org.bouncycastle.asn1.x509.X509ObjectIdentifiers.id_ea_rsa, new com.android.internal.org.bouncycastle.crypto.util.PublicKeyFactory.RSAConverter());
        converters.put(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.dhpublicnumber, new com.android.internal.org.bouncycastle.crypto.util.PublicKeyFactory.DHPublicNumberConverter());
        converters.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.dhKeyAgreement, new com.android.internal.org.bouncycastle.crypto.util.PublicKeyFactory.DHAgreementConverter());
        converters.put(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.id_dsa, new com.android.internal.org.bouncycastle.crypto.util.PublicKeyFactory.DSAConverter());
        converters.put(com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.dsaWithSHA1, new com.android.internal.org.bouncycastle.crypto.util.PublicKeyFactory.DSAConverter());
        converters.put(com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers.id_ecPublicKey, new com.android.internal.org.bouncycastle.crypto.util.PublicKeyFactory.ECConverter());
    }

    public static com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter createKey(byte[] bArr) throws java.io.IOException {
        return createKey(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo.getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(bArr)));
    }

    public static com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter createKey(java.io.InputStream inputStream) throws java.io.IOException {
        return createKey(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo.getInstance(new com.android.internal.org.bouncycastle.asn1.ASN1InputStream(inputStream).readObject()));
    }

    public static com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter createKey(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo) throws java.io.IOException {
        return createKey(subjectPublicKeyInfo, null);
    }

    public static com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter createKey(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo, java.lang.Object obj) throws java.io.IOException {
        com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithm = subjectPublicKeyInfo.getAlgorithm();
        com.android.internal.org.bouncycastle.crypto.util.PublicKeyFactory.SubjectPublicKeyInfoConverter subjectPublicKeyInfoConverter = (com.android.internal.org.bouncycastle.crypto.util.PublicKeyFactory.SubjectPublicKeyInfoConverter) converters.get(algorithm.getAlgorithm());
        if (subjectPublicKeyInfoConverter == null) {
            throw new java.io.IOException("algorithm identifier in public key not recognised: " + algorithm.getAlgorithm());
        }
        return subjectPublicKeyInfoConverter.getPublicKeyParameters(subjectPublicKeyInfo, obj);
    }

    private static abstract class SubjectPublicKeyInfoConverter {
        abstract com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter getPublicKeyParameters(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo, java.lang.Object obj) throws java.io.IOException;

        private SubjectPublicKeyInfoConverter() {
        }
    }

    private static class RSAConverter extends com.android.internal.org.bouncycastle.crypto.util.PublicKeyFactory.SubjectPublicKeyInfoConverter {
        private RSAConverter() {
            super();
        }

        @Override // com.android.internal.org.bouncycastle.crypto.util.PublicKeyFactory.SubjectPublicKeyInfoConverter
        com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter getPublicKeyParameters(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo, java.lang.Object obj) throws java.io.IOException {
            com.android.internal.org.bouncycastle.asn1.pkcs.RSAPublicKey rSAPublicKey = com.android.internal.org.bouncycastle.asn1.pkcs.RSAPublicKey.getInstance(subjectPublicKeyInfo.parsePublicKey());
            return new com.android.internal.org.bouncycastle.crypto.params.RSAKeyParameters(false, rSAPublicKey.getModulus(), rSAPublicKey.getPublicExponent());
        }
    }

    private static class DHPublicNumberConverter extends com.android.internal.org.bouncycastle.crypto.util.PublicKeyFactory.SubjectPublicKeyInfoConverter {
        private DHPublicNumberConverter() {
            super();
        }

        @Override // com.android.internal.org.bouncycastle.crypto.util.PublicKeyFactory.SubjectPublicKeyInfoConverter
        com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter getPublicKeyParameters(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo, java.lang.Object obj) throws java.io.IOException {
            java.math.BigInteger bigInteger;
            com.android.internal.org.bouncycastle.crypto.params.DHValidationParameters dHValidationParameters;
            java.math.BigInteger y = com.android.internal.org.bouncycastle.asn1.x9.DHPublicKey.getInstance(subjectPublicKeyInfo.parsePublicKey()).getY();
            com.android.internal.org.bouncycastle.asn1.x9.DomainParameters domainParameters = com.android.internal.org.bouncycastle.asn1.x9.DomainParameters.getInstance(subjectPublicKeyInfo.getAlgorithm().getParameters());
            java.math.BigInteger p = domainParameters.getP();
            java.math.BigInteger g = domainParameters.getG();
            java.math.BigInteger q = domainParameters.getQ();
            if (domainParameters.getJ() == null) {
                bigInteger = null;
            } else {
                bigInteger = domainParameters.getJ();
            }
            com.android.internal.org.bouncycastle.asn1.x9.ValidationParams validationParams = domainParameters.getValidationParams();
            if (validationParams == null) {
                dHValidationParameters = null;
            } else {
                dHValidationParameters = new com.android.internal.org.bouncycastle.crypto.params.DHValidationParameters(validationParams.getSeed(), validationParams.getPgenCounter().intValue());
            }
            return new com.android.internal.org.bouncycastle.crypto.params.DHPublicKeyParameters(y, new com.android.internal.org.bouncycastle.crypto.params.DHParameters(p, g, q, bigInteger, dHValidationParameters));
        }
    }

    private static class DHAgreementConverter extends com.android.internal.org.bouncycastle.crypto.util.PublicKeyFactory.SubjectPublicKeyInfoConverter {
        private DHAgreementConverter() {
            super();
        }

        @Override // com.android.internal.org.bouncycastle.crypto.util.PublicKeyFactory.SubjectPublicKeyInfoConverter
        com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter getPublicKeyParameters(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo, java.lang.Object obj) throws java.io.IOException {
            com.android.internal.org.bouncycastle.asn1.pkcs.DHParameter dHParameter = com.android.internal.org.bouncycastle.asn1.pkcs.DHParameter.getInstance(subjectPublicKeyInfo.getAlgorithm().getParameters());
            com.android.internal.org.bouncycastle.asn1.ASN1Integer aSN1Integer = (com.android.internal.org.bouncycastle.asn1.ASN1Integer) subjectPublicKeyInfo.parsePublicKey();
            java.math.BigInteger l = dHParameter.getL();
            return new com.android.internal.org.bouncycastle.crypto.params.DHPublicKeyParameters(aSN1Integer.getValue(), new com.android.internal.org.bouncycastle.crypto.params.DHParameters(dHParameter.getP(), dHParameter.getG(), null, l == null ? 0 : l.intValue()));
        }
    }

    private static class DSAConverter extends com.android.internal.org.bouncycastle.crypto.util.PublicKeyFactory.SubjectPublicKeyInfoConverter {
        private DSAConverter() {
            super();
        }

        @Override // com.android.internal.org.bouncycastle.crypto.util.PublicKeyFactory.SubjectPublicKeyInfoConverter
        com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter getPublicKeyParameters(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo, java.lang.Object obj) throws java.io.IOException {
            com.android.internal.org.bouncycastle.crypto.params.DSAParameters dSAParameters;
            com.android.internal.org.bouncycastle.asn1.ASN1Integer aSN1Integer = (com.android.internal.org.bouncycastle.asn1.ASN1Integer) subjectPublicKeyInfo.parsePublicKey();
            com.android.internal.org.bouncycastle.asn1.ASN1Encodable parameters = subjectPublicKeyInfo.getAlgorithm().getParameters();
            if (parameters == null) {
                dSAParameters = null;
            } else {
                com.android.internal.org.bouncycastle.asn1.x509.DSAParameter dSAParameter = com.android.internal.org.bouncycastle.asn1.x509.DSAParameter.getInstance(parameters.toASN1Primitive());
                dSAParameters = new com.android.internal.org.bouncycastle.crypto.params.DSAParameters(dSAParameter.getP(), dSAParameter.getQ(), dSAParameter.getG());
            }
            return new com.android.internal.org.bouncycastle.crypto.params.DSAPublicKeyParameters(aSN1Integer.getValue(), dSAParameters);
        }
    }

    private static class ECConverter extends com.android.internal.org.bouncycastle.crypto.util.PublicKeyFactory.SubjectPublicKeyInfoConverter {
        private ECConverter() {
            super();
        }

        @Override // com.android.internal.org.bouncycastle.crypto.util.PublicKeyFactory.SubjectPublicKeyInfoConverter
        com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter getPublicKeyParameters(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo, java.lang.Object obj) {
            com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters eCDomainParameters;
            com.android.internal.org.bouncycastle.asn1.x9.X962Parameters x962Parameters = com.android.internal.org.bouncycastle.asn1.x9.X962Parameters.getInstance(subjectPublicKeyInfo.getAlgorithm().getParameters());
            if (x962Parameters.isNamedCurve()) {
                com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) x962Parameters.getParameters();
                com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters byOID = com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves.getByOID(aSN1ObjectIdentifier);
                if (byOID == null) {
                    byOID = com.android.internal.org.bouncycastle.asn1.x9.ECNamedCurveTable.getByOID(aSN1ObjectIdentifier);
                }
                eCDomainParameters = new com.android.internal.org.bouncycastle.crypto.params.ECNamedDomainParameters(aSN1ObjectIdentifier, byOID);
            } else if (x962Parameters.isImplicitlyCA()) {
                eCDomainParameters = (com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters) obj;
            } else {
                eCDomainParameters = new com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters(com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters.getInstance(x962Parameters.getParameters()));
            }
            byte[] bytes = subjectPublicKeyInfo.getPublicKeyData().getBytes();
            com.android.internal.org.bouncycastle.asn1.ASN1OctetString dEROctetString = new com.android.internal.org.bouncycastle.asn1.DEROctetString(bytes);
            if (bytes[0] == 4 && bytes[1] == bytes.length - 2 && ((bytes[2] == 2 || bytes[2] == 3) && new com.android.internal.org.bouncycastle.asn1.x9.X9IntegerConverter().getByteLength(eCDomainParameters.getCurve()) >= bytes.length - 3)) {
                try {
                    dEROctetString = (com.android.internal.org.bouncycastle.asn1.ASN1OctetString) com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(bytes);
                } catch (java.io.IOException e) {
                    throw new java.lang.IllegalArgumentException("error recovering public key");
                }
            }
            return new com.android.internal.org.bouncycastle.crypto.params.ECPublicKeyParameters(new com.android.internal.org.bouncycastle.asn1.x9.X9ECPoint(eCDomainParameters.getCurve(), dEROctetString).getPoint(), eCDomainParameters);
        }
    }

    private static byte[] getRawKey(com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo, java.lang.Object obj, int i) {
        byte[] octets = subjectPublicKeyInfo.getPublicKeyData().getOctets();
        if (i != octets.length) {
            throw new java.lang.RuntimeException("public key encoding has incorrect length");
        }
        return octets;
    }
}
