package com.android.internal.org.bouncycastle.jcajce.provider.symmetric;

/* loaded from: classes4.dex */
public class PBES2AlgorithmParameters {
    private PBES2AlgorithmParameters() {
    }

    private static abstract class BasePBEWithHmacAlgorithmParameters extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameters {
        private final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier cipherAlgorithm;
        private final java.lang.String cipherAlgorithmShortName;
        private final com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier kdf;
        private final java.lang.String kdfShortName;
        private final int keySize;
        private com.android.internal.org.bouncycastle.asn1.pkcs.PBES2Parameters params;

        private BasePBEWithHmacAlgorithmParameters(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, java.lang.String str, int i, com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier2, java.lang.String str2) {
            this.kdf = new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE);
            this.kdfShortName = str;
            this.keySize = i;
            this.cipherAlgorithm = aSN1ObjectIdentifier2;
            this.cipherAlgorithmShortName = str2;
        }

        @Override // java.security.AlgorithmParametersSpi
        protected byte[] engineGetEncoded() {
            try {
                return new com.android.internal.org.bouncycastle.asn1.DERSequence(new com.android.internal.org.bouncycastle.asn1.ASN1Encodable[]{com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_PBES2, this.params}).getEncoded();
            } catch (java.io.IOException e) {
                throw new java.lang.RuntimeException("Unable to read PBES2 parameters: " + e.toString());
            }
        }

        @Override // java.security.AlgorithmParametersSpi
        protected byte[] engineGetEncoded(java.lang.String str) {
            if (isASN1FormatString(str)) {
                return engineGetEncoded();
            }
            return null;
        }

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameters
        protected java.security.spec.AlgorithmParameterSpec localEngineGetParameterSpec(java.lang.Class cls) throws java.security.spec.InvalidParameterSpecException {
            if (cls == javax.crypto.spec.PBEParameterSpec.class) {
                com.android.internal.org.bouncycastle.asn1.pkcs.PBKDF2Params pBKDF2Params = (com.android.internal.org.bouncycastle.asn1.pkcs.PBKDF2Params) this.params.getKeyDerivationFunc().getParameters();
                return com.android.internal.org.bouncycastle.jcajce.provider.symmetric.PBES2AlgorithmParameters.createPBEParameterSpec(pBKDF2Params.getSalt(), pBKDF2Params.getIterationCount().intValue(), ((com.android.internal.org.bouncycastle.asn1.ASN1OctetString) this.params.getEncryptionScheme().getParameters()).getOctets());
            }
            throw new java.security.spec.InvalidParameterSpecException("unknown parameter spec passed to PBES2 parameters object.");
        }

        @Override // java.security.AlgorithmParametersSpi
        protected void engineInit(java.security.spec.AlgorithmParameterSpec algorithmParameterSpec) throws java.security.spec.InvalidParameterSpecException {
            if (!(algorithmParameterSpec instanceof javax.crypto.spec.PBEParameterSpec)) {
                throw new java.security.spec.InvalidParameterSpecException("PBEParameterSpec required to initialise PBES2 algorithm parameters");
            }
            javax.crypto.spec.PBEParameterSpec pBEParameterSpec = (javax.crypto.spec.PBEParameterSpec) algorithmParameterSpec;
            java.security.spec.AlgorithmParameterSpec parameterSpecFromPBEParameterSpec = com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.PBE.Util.getParameterSpecFromPBEParameterSpec(pBEParameterSpec);
            if (parameterSpecFromPBEParameterSpec instanceof javax.crypto.spec.IvParameterSpec) {
                this.params = new com.android.internal.org.bouncycastle.asn1.pkcs.PBES2Parameters(new com.android.internal.org.bouncycastle.asn1.pkcs.KeyDerivationFunc(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_PBKDF2, new com.android.internal.org.bouncycastle.asn1.pkcs.PBKDF2Params(pBEParameterSpec.getSalt(), pBEParameterSpec.getIterationCount(), this.keySize, this.kdf)), new com.android.internal.org.bouncycastle.asn1.pkcs.EncryptionScheme(this.cipherAlgorithm, new com.android.internal.org.bouncycastle.asn1.DEROctetString(((javax.crypto.spec.IvParameterSpec) parameterSpecFromPBEParameterSpec).getIV())));
                return;
            }
            throw new java.lang.IllegalArgumentException("Expecting an IV as a parameter");
        }

        @Override // java.security.AlgorithmParametersSpi
        protected void engineInit(byte[] bArr) throws java.io.IOException {
            java.util.Enumeration objects = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(bArr)).getObjects();
            if (!((com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) objects.nextElement()).getId().equals(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_PBES2.getId())) {
                throw new java.lang.IllegalArgumentException("Invalid PBES2 parameters");
            }
            this.params = com.android.internal.org.bouncycastle.asn1.pkcs.PBES2Parameters.getInstance(objects.nextElement());
        }

        @Override // java.security.AlgorithmParametersSpi
        protected void engineInit(byte[] bArr, java.lang.String str) throws java.io.IOException {
            if (isASN1FormatString(str)) {
                engineInit(bArr);
                return;
            }
            throw new java.io.IOException("Unknown parameters format in PBES2 parameters object");
        }

        @Override // java.security.AlgorithmParametersSpi
        protected java.lang.String engineToString() {
            return "PBES2 " + this.kdfShortName + " " + this.cipherAlgorithmShortName + " Parameters";
        }
    }

    public static class PBEWithHmacSHA1AES128AlgorithmParameters extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.PBES2AlgorithmParameters.BasePBEWithHmacAlgorithmParameters {
        public PBEWithHmacSHA1AES128AlgorithmParameters() {
            super(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_hmacWithSHA1, android.security.keystore.KeyProperties.KEY_ALGORITHM_HMAC_SHA1, 16, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes128_CBC, "AES128");
        }
    }

    public static class PBEWithHmacSHA224AES128AlgorithmParameters extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.PBES2AlgorithmParameters.BasePBEWithHmacAlgorithmParameters {
        public PBEWithHmacSHA224AES128AlgorithmParameters() {
            super(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_hmacWithSHA224, android.security.keystore.KeyProperties.KEY_ALGORITHM_HMAC_SHA224, 16, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes128_CBC, "AES128");
        }
    }

    public static class PBEWithHmacSHA256AES128AlgorithmParameters extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.PBES2AlgorithmParameters.BasePBEWithHmacAlgorithmParameters {
        public PBEWithHmacSHA256AES128AlgorithmParameters() {
            super(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_hmacWithSHA256, android.security.keystore.KeyProperties.KEY_ALGORITHM_HMAC_SHA256, 16, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes128_CBC, "AES128");
        }
    }

    public static class PBEWithHmacSHA384AES128AlgorithmParameters extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.PBES2AlgorithmParameters.BasePBEWithHmacAlgorithmParameters {
        public PBEWithHmacSHA384AES128AlgorithmParameters() {
            super(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_hmacWithSHA384, android.security.keystore.KeyProperties.KEY_ALGORITHM_HMAC_SHA384, 16, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes128_CBC, "AES128");
        }
    }

    public static class PBEWithHmacSHA512AES128AlgorithmParameters extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.PBES2AlgorithmParameters.BasePBEWithHmacAlgorithmParameters {
        public PBEWithHmacSHA512AES128AlgorithmParameters() {
            super(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_hmacWithSHA512, android.security.keystore.KeyProperties.KEY_ALGORITHM_HMAC_SHA512, 16, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes128_CBC, "AES128");
        }
    }

    public static class PBEWithHmacSHA1AES256AlgorithmParameters extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.PBES2AlgorithmParameters.BasePBEWithHmacAlgorithmParameters {
        public PBEWithHmacSHA1AES256AlgorithmParameters() {
            super(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_hmacWithSHA1, android.security.keystore.KeyProperties.KEY_ALGORITHM_HMAC_SHA1, 32, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes256_CBC, "AES256");
        }
    }

    public static class PBEWithHmacSHA224AES256AlgorithmParameters extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.PBES2AlgorithmParameters.BasePBEWithHmacAlgorithmParameters {
        public PBEWithHmacSHA224AES256AlgorithmParameters() {
            super(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_hmacWithSHA224, android.security.keystore.KeyProperties.KEY_ALGORITHM_HMAC_SHA224, 32, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes256_CBC, "AES256");
        }
    }

    public static class PBEWithHmacSHA256AES256AlgorithmParameters extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.PBES2AlgorithmParameters.BasePBEWithHmacAlgorithmParameters {
        public PBEWithHmacSHA256AES256AlgorithmParameters() {
            super(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_hmacWithSHA256, android.security.keystore.KeyProperties.KEY_ALGORITHM_HMAC_SHA256, 32, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes256_CBC, "AES256");
        }
    }

    public static class PBEWithHmacSHA384AES256AlgorithmParameters extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.PBES2AlgorithmParameters.BasePBEWithHmacAlgorithmParameters {
        public PBEWithHmacSHA384AES256AlgorithmParameters() {
            super(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_hmacWithSHA384, android.security.keystore.KeyProperties.KEY_ALGORITHM_HMAC_SHA384, 32, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes256_CBC, "AES256");
        }
    }

    public static class PBEWithHmacSHA512AES256AlgorithmParameters extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.PBES2AlgorithmParameters.BasePBEWithHmacAlgorithmParameters {
        public PBEWithHmacSHA512AES256AlgorithmParameters() {
            super(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_hmacWithSHA512, android.security.keystore.KeyProperties.KEY_ALGORITHM_HMAC_SHA512, 32, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_aes256_CBC, "AES256");
        }
    }

    public static class Mappings extends com.android.internal.org.bouncycastle.jcajce.provider.util.AlgorithmProvider {
        private static final java.lang.String PREFIX = com.android.internal.org.bouncycastle.jcajce.provider.symmetric.PBES2AlgorithmParameters.class.getName();

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.util.AlgorithmProvider
        public void configure(com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider configurableProvider) {
            int[] iArr = {128, 256};
            int[] iArr2 = {1, 224, 256, 384, 512};
            for (int i = 0; i < 2; i++) {
                int i2 = iArr[i];
                for (int i3 = 0; i3 < 5; i3++) {
                    int i4 = iArr2[i3];
                    configurableProvider.addAlgorithm("AlgorithmParameters.PBEWithHmacSHA" + i4 + "AndAES_" + i2, PREFIX + "$PBEWithHmacSHA" + i4 + android.security.keystore.KeyProperties.KEY_ALGORITHM_AES + i2 + "AlgorithmParameters");
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static javax.crypto.spec.PBEParameterSpec createPBEParameterSpec(byte[] bArr, int i, byte[] bArr2) {
        try {
            return (javax.crypto.spec.PBEParameterSpec) com.android.internal.org.bouncycastle.jcajce.provider.symmetric.PBES2AlgorithmParameters.class.getClassLoader().loadClass("javax.crypto.spec.PBEParameterSpec").getConstructor(byte[].class, java.lang.Integer.TYPE, java.security.spec.AlgorithmParameterSpec.class).newInstance(bArr, java.lang.Integer.valueOf(i), new javax.crypto.spec.IvParameterSpec(bArr2));
        } catch (java.lang.Exception e) {
            throw new java.lang.IllegalStateException("Requested creation PBES2 parameters in an SDK that doesn't support them", e);
        }
    }
}
