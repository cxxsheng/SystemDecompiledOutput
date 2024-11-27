package com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util;

/* loaded from: classes4.dex */
public interface PBE {
    public static final int MD5 = 0;
    public static final int OPENSSL = 3;
    public static final int PKCS12 = 2;
    public static final int PKCS5S1 = 0;
    public static final int PKCS5S1_UTF8 = 4;
    public static final int PKCS5S2 = 1;
    public static final int PKCS5S2_UTF8 = 5;
    public static final int SHA1 = 1;
    public static final int SHA224 = 7;
    public static final int SHA256 = 4;
    public static final int SHA384 = 8;
    public static final int SHA512 = 9;

    public static class Util {
        private static com.android.internal.org.bouncycastle.crypto.PBEParametersGenerator makePBEGenerator(int i, int i2) {
            if (i == 0 || i == 4) {
                switch (i2) {
                    case 0:
                        return new com.android.internal.org.bouncycastle.crypto.generators.PKCS5S1ParametersGenerator(com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getMD5());
                    case 1:
                        return new com.android.internal.org.bouncycastle.crypto.generators.PKCS5S1ParametersGenerator(com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getSHA1());
                    default:
                        throw new java.lang.IllegalStateException("PKCS5 scheme 1 only supports MD2, MD5 and SHA1.");
                }
            }
            if (i == 1 || i == 5) {
                switch (i2) {
                    case 0:
                        return new com.android.internal.org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator(com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getMD5());
                    case 1:
                        return new com.android.internal.org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator(com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getSHA1());
                    case 2:
                    case 3:
                    case 5:
                    case 6:
                    default:
                        throw new java.lang.IllegalStateException("unknown digest scheme for PBE PKCS5S2 encryption.");
                    case 4:
                        return new com.android.internal.org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator(com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getSHA256());
                    case 7:
                        return new com.android.internal.org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator(com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getSHA224());
                    case 8:
                        return new com.android.internal.org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator(com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getSHA384());
                    case 9:
                        return new com.android.internal.org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator(com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getSHA512());
                }
            }
            if (i == 2) {
                switch (i2) {
                    case 0:
                        return new com.android.internal.org.bouncycastle.crypto.generators.PKCS12ParametersGenerator(com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getMD5());
                    case 1:
                        return new com.android.internal.org.bouncycastle.crypto.generators.PKCS12ParametersGenerator(com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getSHA1());
                    case 2:
                    case 3:
                    case 5:
                    case 6:
                    default:
                        throw new java.lang.IllegalStateException("unknown digest scheme for PBE encryption.");
                    case 4:
                        return new com.android.internal.org.bouncycastle.crypto.generators.PKCS12ParametersGenerator(com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getSHA256());
                    case 7:
                        return new com.android.internal.org.bouncycastle.crypto.generators.PKCS12ParametersGenerator(com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getSHA224());
                    case 8:
                        return new com.android.internal.org.bouncycastle.crypto.generators.PKCS12ParametersGenerator(com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getSHA384());
                    case 9:
                        return new com.android.internal.org.bouncycastle.crypto.generators.PKCS12ParametersGenerator(com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getSHA512());
                }
            }
            return new com.android.internal.org.bouncycastle.crypto.generators.OpenSSLPBEParametersGenerator();
        }

        public static com.android.internal.org.bouncycastle.crypto.CipherParameters makePBEParameters(byte[] bArr, int i, int i2, int i3, int i4, java.security.spec.AlgorithmParameterSpec algorithmParameterSpec, java.lang.String str) throws java.security.InvalidAlgorithmParameterException {
            com.android.internal.org.bouncycastle.crypto.CipherParameters generateDerivedParameters;
            if (algorithmParameterSpec == null || !(algorithmParameterSpec instanceof javax.crypto.spec.PBEParameterSpec)) {
                throw new java.security.InvalidAlgorithmParameterException("Need a PBEParameter spec with a PBE key.");
            }
            javax.crypto.spec.PBEParameterSpec pBEParameterSpec = (javax.crypto.spec.PBEParameterSpec) algorithmParameterSpec;
            com.android.internal.org.bouncycastle.crypto.PBEParametersGenerator makePBEGenerator = makePBEGenerator(i, i2);
            makePBEGenerator.init(bArr, pBEParameterSpec.getSalt(), pBEParameterSpec.getIterationCount());
            if (i4 != 0) {
                generateDerivedParameters = makePBEGenerator.generateDerivedParameters(i3, i4);
                java.security.spec.AlgorithmParameterSpec parameterSpecFromPBEParameterSpec = getParameterSpecFromPBEParameterSpec(pBEParameterSpec);
                if ((i == 1 || i == 5) && (parameterSpecFromPBEParameterSpec instanceof javax.crypto.spec.IvParameterSpec)) {
                    generateDerivedParameters = new com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV((com.android.internal.org.bouncycastle.crypto.params.KeyParameter) ((com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV) generateDerivedParameters).getParameters(), ((javax.crypto.spec.IvParameterSpec) parameterSpecFromPBEParameterSpec).getIV());
                }
            } else {
                generateDerivedParameters = makePBEGenerator.generateDerivedParameters(i3);
            }
            if (str.startsWith("DES")) {
                if (generateDerivedParameters instanceof com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV) {
                    com.android.internal.org.bouncycastle.crypto.params.DESParameters.setOddParity(((com.android.internal.org.bouncycastle.crypto.params.KeyParameter) ((com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV) generateDerivedParameters).getParameters()).getKey());
                } else {
                    com.android.internal.org.bouncycastle.crypto.params.DESParameters.setOddParity(((com.android.internal.org.bouncycastle.crypto.params.KeyParameter) generateDerivedParameters).getKey());
                }
            }
            return generateDerivedParameters;
        }

        public static com.android.internal.org.bouncycastle.crypto.CipherParameters makePBEParameters(com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey bCPBEKey, java.security.spec.AlgorithmParameterSpec algorithmParameterSpec, java.lang.String str) {
            com.android.internal.org.bouncycastle.crypto.CipherParameters generateDerivedParameters;
            if (algorithmParameterSpec == null || !(algorithmParameterSpec instanceof javax.crypto.spec.PBEParameterSpec)) {
                throw new java.lang.IllegalArgumentException("Need a PBEParameter spec with a PBE key.");
            }
            javax.crypto.spec.PBEParameterSpec pBEParameterSpec = (javax.crypto.spec.PBEParameterSpec) algorithmParameterSpec;
            com.android.internal.org.bouncycastle.crypto.PBEParametersGenerator makePBEGenerator = makePBEGenerator(bCPBEKey.getType(), bCPBEKey.getDigest());
            byte[] encoded = bCPBEKey.getEncoded();
            if (bCPBEKey.shouldTryWrongPKCS12()) {
                encoded = new byte[2];
            }
            makePBEGenerator.init(encoded, pBEParameterSpec.getSalt(), pBEParameterSpec.getIterationCount());
            if (bCPBEKey.getIvSize() != 0) {
                generateDerivedParameters = makePBEGenerator.generateDerivedParameters(bCPBEKey.getKeySize(), bCPBEKey.getIvSize());
                java.security.spec.AlgorithmParameterSpec parameterSpecFromPBEParameterSpec = getParameterSpecFromPBEParameterSpec(pBEParameterSpec);
                if ((bCPBEKey.getType() == 1 || bCPBEKey.getType() == 5) && (parameterSpecFromPBEParameterSpec instanceof javax.crypto.spec.IvParameterSpec)) {
                    generateDerivedParameters = new com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV((com.android.internal.org.bouncycastle.crypto.params.KeyParameter) ((com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV) generateDerivedParameters).getParameters(), ((javax.crypto.spec.IvParameterSpec) parameterSpecFromPBEParameterSpec).getIV());
                }
            } else {
                generateDerivedParameters = makePBEGenerator.generateDerivedParameters(bCPBEKey.getKeySize());
            }
            if (str.startsWith("DES")) {
                if (generateDerivedParameters instanceof com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV) {
                    com.android.internal.org.bouncycastle.crypto.params.DESParameters.setOddParity(((com.android.internal.org.bouncycastle.crypto.params.KeyParameter) ((com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV) generateDerivedParameters).getParameters()).getKey());
                } else {
                    com.android.internal.org.bouncycastle.crypto.params.DESParameters.setOddParity(((com.android.internal.org.bouncycastle.crypto.params.KeyParameter) generateDerivedParameters).getKey());
                }
            }
            return generateDerivedParameters;
        }

        public static com.android.internal.org.bouncycastle.crypto.CipherParameters makePBEMacParameters(com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey bCPBEKey, java.security.spec.AlgorithmParameterSpec algorithmParameterSpec) {
            if (algorithmParameterSpec == null || !(algorithmParameterSpec instanceof javax.crypto.spec.PBEParameterSpec)) {
                throw new java.lang.IllegalArgumentException("Need a PBEParameter spec with a PBE key.");
            }
            javax.crypto.spec.PBEParameterSpec pBEParameterSpec = (javax.crypto.spec.PBEParameterSpec) algorithmParameterSpec;
            com.android.internal.org.bouncycastle.crypto.PBEParametersGenerator makePBEGenerator = makePBEGenerator(bCPBEKey.getType(), bCPBEKey.getDigest());
            makePBEGenerator.init(bCPBEKey.getEncoded(), pBEParameterSpec.getSalt(), pBEParameterSpec.getIterationCount());
            return makePBEGenerator.generateDerivedMacParameters(bCPBEKey.getKeySize());
        }

        public static com.android.internal.org.bouncycastle.crypto.CipherParameters makePBEMacParameters(javax.crypto.spec.PBEKeySpec pBEKeySpec, int i, int i2, int i3) {
            com.android.internal.org.bouncycastle.crypto.PBEParametersGenerator makePBEGenerator = makePBEGenerator(i, i2);
            byte[] convertPassword = convertPassword(i, pBEKeySpec);
            makePBEGenerator.init(convertPassword, pBEKeySpec.getSalt(), pBEKeySpec.getIterationCount());
            com.android.internal.org.bouncycastle.crypto.CipherParameters generateDerivedMacParameters = makePBEGenerator.generateDerivedMacParameters(i3);
            for (int i4 = 0; i4 != convertPassword.length; i4++) {
                convertPassword[i4] = 0;
            }
            return generateDerivedMacParameters;
        }

        public static com.android.internal.org.bouncycastle.crypto.CipherParameters makePBEParameters(javax.crypto.spec.PBEKeySpec pBEKeySpec, int i, int i2, int i3, int i4) {
            com.android.internal.org.bouncycastle.crypto.CipherParameters generateDerivedParameters;
            com.android.internal.org.bouncycastle.crypto.PBEParametersGenerator makePBEGenerator = makePBEGenerator(i, i2);
            byte[] convertPassword = convertPassword(i, pBEKeySpec);
            makePBEGenerator.init(convertPassword, pBEKeySpec.getSalt(), pBEKeySpec.getIterationCount());
            if (i4 != 0) {
                generateDerivedParameters = makePBEGenerator.generateDerivedParameters(i3, i4);
            } else {
                generateDerivedParameters = makePBEGenerator.generateDerivedParameters(i3);
            }
            for (int i5 = 0; i5 != convertPassword.length; i5++) {
                convertPassword[i5] = 0;
            }
            return generateDerivedParameters;
        }

        public static com.android.internal.org.bouncycastle.crypto.CipherParameters makePBEMacParameters(javax.crypto.SecretKey secretKey, int i, int i2, int i3, javax.crypto.spec.PBEParameterSpec pBEParameterSpec) {
            com.android.internal.org.bouncycastle.crypto.PBEParametersGenerator makePBEGenerator = makePBEGenerator(i, i2);
            byte[] encoded = secretKey.getEncoded();
            makePBEGenerator.init(secretKey.getEncoded(), pBEParameterSpec.getSalt(), pBEParameterSpec.getIterationCount());
            com.android.internal.org.bouncycastle.crypto.CipherParameters generateDerivedMacParameters = makePBEGenerator.generateDerivedMacParameters(i3);
            for (int i4 = 0; i4 != encoded.length; i4++) {
                encoded[i4] = 0;
            }
            return generateDerivedMacParameters;
        }

        public static java.security.spec.AlgorithmParameterSpec getParameterSpecFromPBEParameterSpec(javax.crypto.spec.PBEParameterSpec pBEParameterSpec) {
            try {
                return (java.security.spec.AlgorithmParameterSpec) com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.PBE.class.getClassLoader().loadClass("javax.crypto.spec.PBEParameterSpec").getMethod("getParameterSpec", new java.lang.Class[0]).invoke(pBEParameterSpec, new java.lang.Object[0]);
            } catch (java.lang.Exception e) {
                return null;
            }
        }

        private static byte[] convertPassword(int i, javax.crypto.spec.PBEKeySpec pBEKeySpec) {
            if (i == 2) {
                return com.android.internal.org.bouncycastle.crypto.PBEParametersGenerator.PKCS12PasswordToBytes(pBEKeySpec.getPassword());
            }
            if (i == 5 || i == 4) {
                return com.android.internal.org.bouncycastle.crypto.PBEParametersGenerator.PKCS5PasswordToUTF8Bytes(pBEKeySpec.getPassword());
            }
            return com.android.internal.org.bouncycastle.crypto.PBEParametersGenerator.PKCS5PasswordToBytes(pBEKeySpec.getPassword());
        }
    }
}
