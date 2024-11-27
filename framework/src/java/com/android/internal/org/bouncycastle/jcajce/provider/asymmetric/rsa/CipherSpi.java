package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa;

/* loaded from: classes4.dex */
public class CipherSpi extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseCipherSpi {
    private com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseCipherSpi.ErasableOutputStream bOut;
    private com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher cipher;
    private java.security.AlgorithmParameters engineParams;
    private final com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper helper;
    private java.security.spec.AlgorithmParameterSpec paramSpec;
    private boolean privateKeyOnly;
    private boolean publicKeyOnly;

    public CipherSpi(com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher asymmetricBlockCipher) {
        this.helper = new com.android.internal.org.bouncycastle.jcajce.util.DefaultJcaJceHelper();
        this.publicKeyOnly = false;
        this.privateKeyOnly = false;
        this.bOut = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseCipherSpi.ErasableOutputStream();
        this.cipher = asymmetricBlockCipher;
    }

    public CipherSpi(javax.crypto.spec.OAEPParameterSpec oAEPParameterSpec) {
        this.helper = new com.android.internal.org.bouncycastle.jcajce.util.DefaultJcaJceHelper();
        this.publicKeyOnly = false;
        this.privateKeyOnly = false;
        this.bOut = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseCipherSpi.ErasableOutputStream();
        try {
            initFromSpec(oAEPParameterSpec);
        } catch (javax.crypto.NoSuchPaddingException e) {
            throw new java.lang.IllegalArgumentException(e.getMessage());
        }
    }

    public CipherSpi(boolean z, boolean z2, com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher asymmetricBlockCipher) {
        this.helper = new com.android.internal.org.bouncycastle.jcajce.util.DefaultJcaJceHelper();
        this.publicKeyOnly = false;
        this.privateKeyOnly = false;
        this.bOut = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseCipherSpi.ErasableOutputStream();
        this.publicKeyOnly = z;
        this.privateKeyOnly = z2;
        this.cipher = asymmetricBlockCipher;
    }

    private void initFromSpec(javax.crypto.spec.OAEPParameterSpec oAEPParameterSpec) throws javax.crypto.NoSuchPaddingException {
        java.security.spec.MGF1ParameterSpec mGF1ParameterSpec = (java.security.spec.MGF1ParameterSpec) oAEPParameterSpec.getMGFParameters();
        com.android.internal.org.bouncycastle.crypto.Digest digest = com.android.internal.org.bouncycastle.jcajce.provider.util.DigestFactory.getDigest(mGF1ParameterSpec.getDigestAlgorithm());
        if (digest == null) {
            throw new javax.crypto.NoSuchPaddingException("no match on OAEP constructor for digest algorithm: " + mGF1ParameterSpec.getDigestAlgorithm());
        }
        this.cipher = new com.android.internal.org.bouncycastle.crypto.encodings.OAEPEncoding(new com.android.internal.org.bouncycastle.crypto.engines.RSABlindedEngine(), digest, ((javax.crypto.spec.PSource.PSpecified) oAEPParameterSpec.getPSource()).getValue());
        this.paramSpec = oAEPParameterSpec;
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseCipherSpi, javax.crypto.CipherSpi
    protected int engineGetBlockSize() {
        try {
            return this.cipher.getInputBlockSize();
        } catch (java.lang.NullPointerException e) {
            throw new java.lang.IllegalStateException("RSA Cipher not initialised");
        }
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseCipherSpi, javax.crypto.CipherSpi
    protected int engineGetKeySize(java.security.Key key) {
        if (key instanceof java.security.interfaces.RSAPrivateKey) {
            return ((java.security.interfaces.RSAPrivateKey) key).getModulus().bitLength();
        }
        if (key instanceof java.security.interfaces.RSAPublicKey) {
            return ((java.security.interfaces.RSAPublicKey) key).getModulus().bitLength();
        }
        throw new java.lang.IllegalArgumentException("not an RSA key!");
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseCipherSpi, javax.crypto.CipherSpi
    protected int engineGetOutputSize(int i) {
        try {
            return this.cipher.getOutputBlockSize();
        } catch (java.lang.NullPointerException e) {
            throw new java.lang.IllegalStateException("RSA Cipher not initialised");
        }
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseCipherSpi, javax.crypto.CipherSpi
    protected java.security.AlgorithmParameters engineGetParameters() {
        if (this.engineParams == null && this.paramSpec != null) {
            try {
                this.engineParams = this.helper.createAlgorithmParameters("OAEP");
                this.engineParams.init(this.paramSpec);
            } catch (java.lang.Exception e) {
                throw new java.lang.RuntimeException(e.toString());
            }
        }
        return this.engineParams;
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseCipherSpi, javax.crypto.CipherSpi
    protected void engineSetMode(java.lang.String str) throws java.security.NoSuchAlgorithmException {
        java.lang.String upperCase = com.android.internal.org.bouncycastle.util.Strings.toUpperCase(str);
        if (upperCase.equals(android.security.keystore.KeyProperties.DIGEST_NONE) || upperCase.equals(android.security.keystore.KeyProperties.BLOCK_MODE_ECB)) {
            return;
        }
        if (upperCase.equals("1")) {
            this.privateKeyOnly = true;
            this.publicKeyOnly = false;
        } else {
            if (upperCase.equals("2")) {
                this.privateKeyOnly = false;
                this.publicKeyOnly = true;
                return;
            }
            throw new java.security.NoSuchAlgorithmException("can't support mode " + str);
        }
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseCipherSpi, javax.crypto.CipherSpi
    protected void engineSetPadding(java.lang.String str) throws javax.crypto.NoSuchPaddingException {
        java.lang.String upperCase = com.android.internal.org.bouncycastle.util.Strings.toUpperCase(str);
        if (upperCase.equals("NOPADDING")) {
            this.cipher = new com.android.internal.org.bouncycastle.crypto.engines.RSABlindedEngine();
            return;
        }
        if (upperCase.equals("PKCS1PADDING")) {
            this.cipher = new com.android.internal.org.bouncycastle.crypto.encodings.PKCS1Encoding(new com.android.internal.org.bouncycastle.crypto.engines.RSABlindedEngine());
            return;
        }
        if (upperCase.equals("OAEPWITHMD5ANDMGF1PADDING")) {
            initFromSpec(new javax.crypto.spec.OAEPParameterSpec(android.security.keystore.KeyProperties.DIGEST_MD5, "MGF1", new java.security.spec.MGF1ParameterSpec(android.security.keystore.KeyProperties.DIGEST_MD5), javax.crypto.spec.PSource.PSpecified.DEFAULT));
            return;
        }
        if (upperCase.equals("OAEPPADDING")) {
            initFromSpec(javax.crypto.spec.OAEPParameterSpec.DEFAULT);
            return;
        }
        if (upperCase.equals("OAEPWITHSHA1ANDMGF1PADDING") || upperCase.equals("OAEPWITHSHA-1ANDMGF1PADDING")) {
            initFromSpec(javax.crypto.spec.OAEPParameterSpec.DEFAULT);
            return;
        }
        if (upperCase.equals("OAEPWITHSHA224ANDMGF1PADDING") || upperCase.equals("OAEPWITHSHA-224ANDMGF1PADDING")) {
            initFromSpec(new javax.crypto.spec.OAEPParameterSpec(android.security.keystore.KeyProperties.DIGEST_SHA224, "MGF1", new java.security.spec.MGF1ParameterSpec(android.security.keystore.KeyProperties.DIGEST_SHA224), javax.crypto.spec.PSource.PSpecified.DEFAULT));
            return;
        }
        if (upperCase.equals("OAEPWITHSHA256ANDMGF1PADDING") || upperCase.equals("OAEPWITHSHA-256ANDMGF1PADDING")) {
            initFromSpec(new javax.crypto.spec.OAEPParameterSpec("SHA-256", "MGF1", java.security.spec.MGF1ParameterSpec.SHA256, javax.crypto.spec.PSource.PSpecified.DEFAULT));
            return;
        }
        if (upperCase.equals("OAEPWITHSHA384ANDMGF1PADDING") || upperCase.equals("OAEPWITHSHA-384ANDMGF1PADDING")) {
            initFromSpec(new javax.crypto.spec.OAEPParameterSpec(android.security.keystore.KeyProperties.DIGEST_SHA384, "MGF1", java.security.spec.MGF1ParameterSpec.SHA384, javax.crypto.spec.PSource.PSpecified.DEFAULT));
        } else {
            if (upperCase.equals("OAEPWITHSHA512ANDMGF1PADDING") || upperCase.equals("OAEPWITHSHA-512ANDMGF1PADDING")) {
                initFromSpec(new javax.crypto.spec.OAEPParameterSpec(android.security.keystore.KeyProperties.DIGEST_SHA512, "MGF1", java.security.spec.MGF1ParameterSpec.SHA512, javax.crypto.spec.PSource.PSpecified.DEFAULT));
                return;
            }
            throw new javax.crypto.NoSuchPaddingException(str + " unavailable with RSA.");
        }
    }

    @Override // javax.crypto.CipherSpi
    protected void engineInit(int i, java.security.Key key, java.security.spec.AlgorithmParameterSpec algorithmParameterSpec, java.security.SecureRandom secureRandom) throws java.security.InvalidKeyException, java.security.InvalidAlgorithmParameterException {
        com.android.internal.org.bouncycastle.crypto.CipherParameters generatePrivateKeyParameter;
        if (algorithmParameterSpec == null || (algorithmParameterSpec instanceof javax.crypto.spec.OAEPParameterSpec)) {
            if (key instanceof java.security.interfaces.RSAPublicKey) {
                if (this.privateKeyOnly && i == 1) {
                    throw new java.security.InvalidKeyException("mode 1 requires RSAPrivateKey");
                }
                generatePrivateKeyParameter = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.RSAUtil.generatePublicKeyParameter((java.security.interfaces.RSAPublicKey) key);
            } else if (key instanceof java.security.interfaces.RSAPrivateKey) {
                if (this.publicKeyOnly && i == 1) {
                    throw new java.security.InvalidKeyException("mode 2 requires RSAPublicKey");
                }
                generatePrivateKeyParameter = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.RSAUtil.generatePrivateKeyParameter((java.security.interfaces.RSAPrivateKey) key);
            } else {
                throw new java.security.InvalidKeyException("unknown key type passed to RSA");
            }
            if (algorithmParameterSpec != null) {
                javax.crypto.spec.OAEPParameterSpec oAEPParameterSpec = (javax.crypto.spec.OAEPParameterSpec) algorithmParameterSpec;
                this.paramSpec = algorithmParameterSpec;
                if (!oAEPParameterSpec.getMGFAlgorithm().equalsIgnoreCase("MGF1") && !oAEPParameterSpec.getMGFAlgorithm().equals(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_mgf1.getId())) {
                    throw new java.security.InvalidAlgorithmParameterException("unknown mask generation function specified");
                }
                if (!(oAEPParameterSpec.getMGFParameters() instanceof java.security.spec.MGF1ParameterSpec)) {
                    throw new java.security.InvalidAlgorithmParameterException("unkown MGF parameters");
                }
                com.android.internal.org.bouncycastle.crypto.Digest digest = com.android.internal.org.bouncycastle.jcajce.provider.util.DigestFactory.getDigest(oAEPParameterSpec.getDigestAlgorithm());
                if (digest == null) {
                    throw new java.security.InvalidAlgorithmParameterException("no match on digest algorithm: " + oAEPParameterSpec.getDigestAlgorithm());
                }
                java.security.spec.MGF1ParameterSpec mGF1ParameterSpec = (java.security.spec.MGF1ParameterSpec) oAEPParameterSpec.getMGFParameters();
                com.android.internal.org.bouncycastle.crypto.Digest digest2 = com.android.internal.org.bouncycastle.jcajce.provider.util.DigestFactory.getDigest(mGF1ParameterSpec.getDigestAlgorithm());
                if (digest2 == null) {
                    throw new java.security.InvalidAlgorithmParameterException("no match on MGF digest algorithm: " + mGF1ParameterSpec.getDigestAlgorithm());
                }
                this.cipher = new com.android.internal.org.bouncycastle.crypto.encodings.OAEPEncoding(new com.android.internal.org.bouncycastle.crypto.engines.RSABlindedEngine(), digest, digest2, ((javax.crypto.spec.PSource.PSpecified) oAEPParameterSpec.getPSource()).getValue());
            }
            if (!(this.cipher instanceof com.android.internal.org.bouncycastle.crypto.engines.RSABlindedEngine)) {
                if (secureRandom != null) {
                    generatePrivateKeyParameter = new com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom(generatePrivateKeyParameter, secureRandom);
                } else {
                    generatePrivateKeyParameter = new com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom(generatePrivateKeyParameter, com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar.getSecureRandom());
                }
            }
            this.bOut.reset();
            switch (i) {
                case 1:
                case 3:
                    this.cipher.init(true, generatePrivateKeyParameter);
                    return;
                case 2:
                case 4:
                    this.cipher.init(false, generatePrivateKeyParameter);
                    return;
                default:
                    throw new java.security.InvalidParameterException("unknown opmode " + i + " passed to RSA");
            }
        }
        throw new java.security.InvalidAlgorithmParameterException("unknown parameter type: " + algorithmParameterSpec.getClass().getName());
    }

    @Override // javax.crypto.CipherSpi
    protected void engineInit(int i, java.security.Key key, java.security.AlgorithmParameters algorithmParameters, java.security.SecureRandom secureRandom) throws java.security.InvalidKeyException, java.security.InvalidAlgorithmParameterException {
        java.security.spec.AlgorithmParameterSpec parameterSpec;
        if (algorithmParameters == null) {
            parameterSpec = null;
        } else {
            try {
                parameterSpec = algorithmParameters.getParameterSpec(javax.crypto.spec.OAEPParameterSpec.class);
            } catch (java.security.spec.InvalidParameterSpecException e) {
                throw new java.security.InvalidAlgorithmParameterException("cannot recognise parameters: " + e.toString(), e);
            }
        }
        this.engineParams = algorithmParameters;
        engineInit(i, key, parameterSpec, secureRandom);
    }

    @Override // javax.crypto.CipherSpi
    protected void engineInit(int i, java.security.Key key, java.security.SecureRandom secureRandom) throws java.security.InvalidKeyException {
        try {
            engineInit(i, key, (java.security.spec.AlgorithmParameterSpec) null, secureRandom);
        } catch (java.security.InvalidAlgorithmParameterException e) {
            throw new java.security.InvalidKeyException("Eeeek! " + e.toString(), e);
        }
    }

    @Override // javax.crypto.CipherSpi
    protected byte[] engineUpdate(byte[] bArr, int i, int i2) {
        this.bOut.write(bArr, i, i2);
        if (this.cipher instanceof com.android.internal.org.bouncycastle.crypto.engines.RSABlindedEngine) {
            if (this.bOut.size() > this.cipher.getInputBlockSize() + 1) {
                throw new java.lang.ArrayIndexOutOfBoundsException("too much data for RSA block");
            }
            return null;
        }
        if (this.bOut.size() > this.cipher.getInputBlockSize()) {
            throw new java.lang.ArrayIndexOutOfBoundsException("too much data for RSA block");
        }
        return null;
    }

    @Override // javax.crypto.CipherSpi
    protected int engineUpdate(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        this.bOut.write(bArr, i, i2);
        if (this.cipher instanceof com.android.internal.org.bouncycastle.crypto.engines.RSABlindedEngine) {
            if (this.bOut.size() > this.cipher.getInputBlockSize() + 1) {
                throw new java.lang.ArrayIndexOutOfBoundsException("too much data for RSA block");
            }
            return 0;
        }
        if (this.bOut.size() > this.cipher.getInputBlockSize()) {
            throw new java.lang.ArrayIndexOutOfBoundsException("too much data for RSA block");
        }
        return 0;
    }

    @Override // javax.crypto.CipherSpi
    protected byte[] engineDoFinal(byte[] bArr, int i, int i2) throws javax.crypto.IllegalBlockSizeException, javax.crypto.BadPaddingException {
        if (bArr != null) {
            this.bOut.write(bArr, i, i2);
        }
        if (this.cipher instanceof com.android.internal.org.bouncycastle.crypto.engines.RSABlindedEngine) {
            if (this.bOut.size() > this.cipher.getInputBlockSize() + 1) {
                throw new java.lang.ArrayIndexOutOfBoundsException("too much data for RSA block");
            }
        } else if (this.bOut.size() > this.cipher.getInputBlockSize()) {
            throw new java.lang.ArrayIndexOutOfBoundsException("too much data for RSA block");
        }
        return getOutput();
    }

    @Override // javax.crypto.CipherSpi
    protected int engineDoFinal(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws javax.crypto.IllegalBlockSizeException, javax.crypto.BadPaddingException, javax.crypto.ShortBufferException {
        if (engineGetOutputSize(i2) + i3 > bArr2.length) {
            throw new javax.crypto.ShortBufferException("output buffer too short for input.");
        }
        if (bArr != null) {
            this.bOut.write(bArr, i, i2);
        }
        if (this.cipher instanceof com.android.internal.org.bouncycastle.crypto.engines.RSABlindedEngine) {
            if (this.bOut.size() > this.cipher.getInputBlockSize() + 1) {
                throw new java.lang.ArrayIndexOutOfBoundsException("too much data for RSA block");
            }
        } else if (this.bOut.size() > this.cipher.getInputBlockSize()) {
            throw new java.lang.ArrayIndexOutOfBoundsException("too much data for RSA block");
        }
        byte[] output = getOutput();
        for (int i4 = 0; i4 != output.length; i4++) {
            bArr2[i3 + i4] = output[i4];
        }
        return output.length;
    }

    private byte[] getOutput() throws javax.crypto.BadPaddingException {
        try {
            try {
                return this.cipher.processBlock(this.bOut.getBuf(), 0, this.bOut.size());
            } catch (com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException e) {
                throw new com.android.internal.org.bouncycastle.jcajce.provider.util.BadBlockException("unable to decrypt block", e);
            } catch (java.lang.ArrayIndexOutOfBoundsException e2) {
                throw new com.android.internal.org.bouncycastle.jcajce.provider.util.BadBlockException("unable to decrypt block", e2);
            }
        } finally {
            this.bOut.erase();
        }
    }

    public static class NoPadding extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.CipherSpi {
        public NoPadding() {
            super(new com.android.internal.org.bouncycastle.crypto.engines.RSABlindedEngine());
        }
    }
}
