package com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util;

/* loaded from: classes4.dex */
public class BaseStreamCipher extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher implements com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.PBE {
    private java.lang.Class[] availableSpecs;
    private com.android.internal.org.bouncycastle.crypto.StreamCipher cipher;
    private int digest;
    private int ivLength;
    private com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV ivParam;
    private int keySizeInBits;
    private java.lang.String pbeAlgorithm;
    private javax.crypto.spec.PBEParameterSpec pbeSpec;

    protected BaseStreamCipher(com.android.internal.org.bouncycastle.crypto.StreamCipher streamCipher, int i) {
        this(streamCipher, i, -1, -1);
    }

    protected BaseStreamCipher(com.android.internal.org.bouncycastle.crypto.StreamCipher streamCipher, int i, int i2) {
        this(streamCipher, i, i2, -1);
    }

    protected BaseStreamCipher(com.android.internal.org.bouncycastle.crypto.StreamCipher streamCipher, int i, int i2, int i3) {
        this.availableSpecs = new java.lang.Class[]{javax.crypto.spec.IvParameterSpec.class, javax.crypto.spec.PBEParameterSpec.class};
        this.ivLength = 0;
        this.pbeSpec = null;
        this.pbeAlgorithm = null;
        this.cipher = streamCipher;
        this.ivLength = i;
        this.keySizeInBits = i2;
        this.digest = i3;
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher, javax.crypto.CipherSpi
    protected int engineGetBlockSize() {
        return 0;
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher, javax.crypto.CipherSpi
    protected byte[] engineGetIV() {
        if (this.ivParam != null) {
            return this.ivParam.getIV();
        }
        return null;
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher, javax.crypto.CipherSpi
    protected int engineGetKeySize(java.security.Key key) {
        return key.getEncoded().length * 8;
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher, javax.crypto.CipherSpi
    protected int engineGetOutputSize(int i) {
        return i;
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher, javax.crypto.CipherSpi
    protected java.security.AlgorithmParameters engineGetParameters() {
        if (this.engineParams == null) {
            if (this.pbeSpec != null) {
                try {
                    java.security.AlgorithmParameters createParametersInstance = createParametersInstance(this.pbeAlgorithm);
                    createParametersInstance.init(this.pbeSpec);
                    return createParametersInstance;
                } catch (java.lang.Exception e) {
                    return null;
                }
            }
            if (this.ivParam != null) {
                java.lang.String algorithmName = this.cipher.getAlgorithmName();
                if (algorithmName.indexOf(47) >= 0) {
                    algorithmName = algorithmName.substring(0, algorithmName.indexOf(47));
                }
                if (algorithmName.startsWith("ChaCha7539")) {
                    algorithmName = "ChaCha7539";
                } else if (algorithmName.startsWith("Grain")) {
                    algorithmName = "Grainv1";
                } else if (algorithmName.startsWith("HC")) {
                    int indexOf = algorithmName.indexOf(45);
                    algorithmName = algorithmName.substring(0, indexOf) + algorithmName.substring(indexOf + 1);
                }
                try {
                    this.engineParams = createParametersInstance(algorithmName);
                    this.engineParams.init(new javax.crypto.spec.IvParameterSpec(this.ivParam.getIV()));
                } catch (java.lang.Exception e2) {
                    throw new java.lang.RuntimeException(e2.toString());
                }
            }
        }
        return this.engineParams;
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher, javax.crypto.CipherSpi
    protected void engineSetMode(java.lang.String str) throws java.security.NoSuchAlgorithmException {
        if (!str.equalsIgnoreCase(android.security.keystore.KeyProperties.BLOCK_MODE_ECB) && !str.equals(android.security.keystore.KeyProperties.DIGEST_NONE)) {
            throw new java.security.NoSuchAlgorithmException("can't support mode " + str);
        }
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher, javax.crypto.CipherSpi
    protected void engineSetPadding(java.lang.String str) throws javax.crypto.NoSuchPaddingException {
        if (!str.equalsIgnoreCase(android.security.keystore.KeyProperties.ENCRYPTION_PADDING_NONE)) {
            throw new javax.crypto.NoSuchPaddingException("Padding " + str + " unknown.");
        }
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher, javax.crypto.CipherSpi
    protected void engineInit(int i, java.security.Key key, java.security.spec.AlgorithmParameterSpec algorithmParameterSpec, java.security.SecureRandom secureRandom) throws java.security.InvalidKeyException, java.security.InvalidAlgorithmParameterException {
        com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters;
        com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters2;
        this.pbeSpec = null;
        this.pbeAlgorithm = null;
        this.engineParams = null;
        if (!(key instanceof javax.crypto.SecretKey)) {
            throw new java.security.InvalidKeyException("Key for algorithm " + key.getAlgorithm() + " not suitable for symmetric enryption.");
        }
        if (key instanceof com.android.internal.org.bouncycastle.jcajce.PKCS12Key) {
            com.android.internal.org.bouncycastle.jcajce.PKCS12Key pKCS12Key = (com.android.internal.org.bouncycastle.jcajce.PKCS12Key) key;
            this.pbeSpec = (javax.crypto.spec.PBEParameterSpec) algorithmParameterSpec;
            if ((pKCS12Key instanceof com.android.internal.org.bouncycastle.jcajce.PKCS12KeyWithParameters) && this.pbeSpec == null) {
                com.android.internal.org.bouncycastle.jcajce.PKCS12KeyWithParameters pKCS12KeyWithParameters = (com.android.internal.org.bouncycastle.jcajce.PKCS12KeyWithParameters) pKCS12Key;
                this.pbeSpec = new javax.crypto.spec.PBEParameterSpec(pKCS12KeyWithParameters.getSalt(), pKCS12KeyWithParameters.getIterationCount());
            }
            cipherParameters = com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.PBE.Util.makePBEParameters(pKCS12Key.getEncoded(), 2, this.digest, this.keySizeInBits, this.ivLength * 8, this.pbeSpec, this.cipher.getAlgorithmName());
        } else if (key instanceof com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey) {
            com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey bCPBEKey = (com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey) key;
            if (bCPBEKey.getOID() != null) {
                this.pbeAlgorithm = bCPBEKey.getOID().getId();
            } else {
                this.pbeAlgorithm = bCPBEKey.getAlgorithm();
            }
            if (bCPBEKey.getParam() != null) {
                cipherParameters2 = bCPBEKey.getParam();
                this.pbeSpec = new javax.crypto.spec.PBEParameterSpec(bCPBEKey.getSalt(), bCPBEKey.getIterationCount());
            } else if (algorithmParameterSpec instanceof javax.crypto.spec.PBEParameterSpec) {
                com.android.internal.org.bouncycastle.crypto.CipherParameters makePBEParameters = com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.PBE.Util.makePBEParameters(bCPBEKey, algorithmParameterSpec, this.cipher.getAlgorithmName());
                this.pbeSpec = (javax.crypto.spec.PBEParameterSpec) algorithmParameterSpec;
                cipherParameters2 = makePBEParameters;
            } else {
                throw new java.security.InvalidAlgorithmParameterException("PBE requires PBE parameters to be set.");
            }
            if (bCPBEKey.getIvSize() != 0) {
                this.ivParam = (com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV) cipherParameters2;
            }
            cipherParameters = cipherParameters2;
        } else if (algorithmParameterSpec == null) {
            if (this.digest > 0) {
                throw new java.security.InvalidKeyException("Algorithm requires a PBE key");
            }
            cipherParameters = new com.android.internal.org.bouncycastle.crypto.params.KeyParameter(key.getEncoded());
        } else if (algorithmParameterSpec instanceof javax.crypto.spec.IvParameterSpec) {
            com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV parametersWithIV = new com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV(new com.android.internal.org.bouncycastle.crypto.params.KeyParameter(key.getEncoded()), ((javax.crypto.spec.IvParameterSpec) algorithmParameterSpec).getIV());
            this.ivParam = parametersWithIV;
            cipherParameters = parametersWithIV;
        } else {
            throw new java.security.InvalidAlgorithmParameterException("unknown parameter type.");
        }
        if (this.ivLength != 0 && !(cipherParameters instanceof com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV)) {
            if (secureRandom == null) {
                secureRandom = com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar.getSecureRandom();
            }
            if (i == 1 || i == 3) {
                byte[] bArr = new byte[this.ivLength];
                secureRandom.nextBytes(bArr);
                com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV parametersWithIV2 = new com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV(cipherParameters, bArr);
                this.ivParam = parametersWithIV2;
                cipherParameters = parametersWithIV2;
            } else {
                throw new java.security.InvalidAlgorithmParameterException("no IV set when one expected");
            }
        }
        try {
            switch (i) {
                case 1:
                case 3:
                    this.cipher.init(true, cipherParameters);
                    return;
                case 2:
                case 4:
                    this.cipher.init(false, cipherParameters);
                    return;
                default:
                    throw new java.security.InvalidParameterException("unknown opmode " + i + " passed");
            }
        } catch (java.lang.Exception e) {
            throw new java.security.InvalidKeyException(e.getMessage());
        }
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher, javax.crypto.CipherSpi
    protected void engineInit(int i, java.security.Key key, java.security.AlgorithmParameters algorithmParameters, java.security.SecureRandom secureRandom) throws java.security.InvalidKeyException, java.security.InvalidAlgorithmParameterException {
        java.security.spec.AlgorithmParameterSpec algorithmParameterSpec;
        if (algorithmParameters == null) {
            algorithmParameterSpec = null;
        } else {
            algorithmParameterSpec = com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.SpecUtil.extractSpec(algorithmParameters, this.availableSpecs);
            if (algorithmParameterSpec == null) {
                throw new java.security.InvalidAlgorithmParameterException("can't handle parameter " + algorithmParameters.toString());
            }
        }
        engineInit(i, key, algorithmParameterSpec, secureRandom);
        this.engineParams = algorithmParameters;
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher, javax.crypto.CipherSpi
    protected void engineInit(int i, java.security.Key key, java.security.SecureRandom secureRandom) throws java.security.InvalidKeyException {
        try {
            engineInit(i, key, (java.security.spec.AlgorithmParameterSpec) null, secureRandom);
        } catch (java.security.InvalidAlgorithmParameterException e) {
            throw new java.security.InvalidKeyException(e.getMessage());
        }
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher, javax.crypto.CipherSpi
    protected byte[] engineUpdate(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[i2];
        this.cipher.processBytes(bArr, i, i2, bArr2, 0);
        return bArr2;
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher, javax.crypto.CipherSpi
    protected int engineUpdate(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws javax.crypto.ShortBufferException {
        if (i3 + i2 > bArr2.length) {
            throw new javax.crypto.ShortBufferException("output buffer too short for input.");
        }
        try {
            this.cipher.processBytes(bArr, i, i2, bArr2, i3);
            return i2;
        } catch (com.android.internal.org.bouncycastle.crypto.DataLengthException e) {
            throw new java.lang.IllegalStateException(e.getMessage());
        }
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher, javax.crypto.CipherSpi
    protected byte[] engineDoFinal(byte[] bArr, int i, int i2) {
        if (i2 != 0) {
            byte[] engineUpdate = engineUpdate(bArr, i, i2);
            this.cipher.reset();
            return engineUpdate;
        }
        this.cipher.reset();
        return new byte[0];
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher, javax.crypto.CipherSpi
    protected int engineDoFinal(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws javax.crypto.ShortBufferException {
        if (i3 + i2 > bArr2.length) {
            throw new javax.crypto.ShortBufferException("output buffer too short for input.");
        }
        if (i2 != 0) {
            this.cipher.processBytes(bArr, i, i2, bArr2, i3);
        }
        this.cipher.reset();
        return i2;
    }
}
