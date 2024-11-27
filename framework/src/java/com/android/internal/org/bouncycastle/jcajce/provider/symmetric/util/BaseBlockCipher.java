package com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util;

/* loaded from: classes4.dex */
public class BaseBlockCipher extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher implements com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.PBE {
    private static final int BUF_SIZE = 512;
    private static final java.lang.Class gcmSpecClass = com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.ClassUtil.loadClass(com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.class, "javax.crypto.spec.GCMParameterSpec");
    private com.android.internal.org.bouncycastle.crypto.params.AEADParameters aeadParams;
    private java.lang.Class[] availableSpecs;
    private com.android.internal.org.bouncycastle.crypto.BlockCipher baseEngine;
    private com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher cipher;
    private int digest;
    private com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BlockCipherProvider engineProvider;
    private boolean fixedIv;
    private int ivLength;
    private com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV ivParam;
    private int keySizeInBits;
    private java.lang.String modeName;
    private boolean padded;
    private java.lang.String pbeAlgorithm;
    private javax.crypto.spec.PBEParameterSpec pbeSpec;
    private int scheme;

    private interface GenericBlockCipher {
        int doFinal(byte[] bArr, int i) throws java.lang.IllegalStateException, javax.crypto.BadPaddingException;

        java.lang.String getAlgorithmName();

        int getOutputSize(int i);

        com.android.internal.org.bouncycastle.crypto.BlockCipher getUnderlyingCipher();

        int getUpdateOutputSize(int i);

        void init(boolean z, com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters) throws java.lang.IllegalArgumentException;

        int processByte(byte b, byte[] bArr, int i) throws com.android.internal.org.bouncycastle.crypto.DataLengthException;

        int processBytes(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws com.android.internal.org.bouncycastle.crypto.DataLengthException;

        void updateAAD(byte[] bArr, int i, int i2);

        boolean wrapOnNoPadding();
    }

    protected BaseBlockCipher(com.android.internal.org.bouncycastle.crypto.BlockCipher blockCipher) {
        this.availableSpecs = new java.lang.Class[]{gcmSpecClass, javax.crypto.spec.IvParameterSpec.class, javax.crypto.spec.PBEParameterSpec.class};
        this.scheme = -1;
        this.ivLength = 0;
        this.fixedIv = true;
        this.pbeSpec = null;
        this.pbeAlgorithm = null;
        this.modeName = null;
        this.baseEngine = blockCipher;
        this.cipher = new com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.BufferedGenericBlockCipher(blockCipher);
    }

    protected BaseBlockCipher(com.android.internal.org.bouncycastle.crypto.BlockCipher blockCipher, int i, int i2, int i3, int i4) {
        this.availableSpecs = new java.lang.Class[]{gcmSpecClass, javax.crypto.spec.IvParameterSpec.class, javax.crypto.spec.PBEParameterSpec.class};
        this.scheme = -1;
        this.ivLength = 0;
        this.fixedIv = true;
        this.pbeSpec = null;
        this.pbeAlgorithm = null;
        this.modeName = null;
        this.baseEngine = blockCipher;
        this.scheme = i;
        this.digest = i2;
        this.keySizeInBits = i3;
        this.ivLength = i4;
        this.cipher = new com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.BufferedGenericBlockCipher(blockCipher);
    }

    protected BaseBlockCipher(com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BlockCipherProvider blockCipherProvider) {
        this.availableSpecs = new java.lang.Class[]{gcmSpecClass, javax.crypto.spec.IvParameterSpec.class, javax.crypto.spec.PBEParameterSpec.class};
        this.scheme = -1;
        this.ivLength = 0;
        this.fixedIv = true;
        this.pbeSpec = null;
        this.pbeAlgorithm = null;
        this.modeName = null;
        this.baseEngine = blockCipherProvider.get();
        this.engineProvider = blockCipherProvider;
        this.cipher = new com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.BufferedGenericBlockCipher(blockCipherProvider.get());
    }

    protected BaseBlockCipher(com.android.internal.org.bouncycastle.crypto.modes.AEADBlockCipher aEADBlockCipher) {
        this.availableSpecs = new java.lang.Class[]{gcmSpecClass, javax.crypto.spec.IvParameterSpec.class, javax.crypto.spec.PBEParameterSpec.class};
        this.scheme = -1;
        this.ivLength = 0;
        this.fixedIv = true;
        this.pbeSpec = null;
        this.pbeAlgorithm = null;
        this.modeName = null;
        this.baseEngine = aEADBlockCipher.getUnderlyingCipher();
        this.ivLength = this.baseEngine.getBlockSize();
        this.cipher = new com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.AEADGenericBlockCipher(aEADBlockCipher);
    }

    protected BaseBlockCipher(com.android.internal.org.bouncycastle.crypto.modes.AEADCipher aEADCipher, boolean z, int i) {
        this.availableSpecs = new java.lang.Class[]{gcmSpecClass, javax.crypto.spec.IvParameterSpec.class, javax.crypto.spec.PBEParameterSpec.class};
        this.scheme = -1;
        this.ivLength = 0;
        this.fixedIv = true;
        this.pbeSpec = null;
        this.pbeAlgorithm = null;
        this.modeName = null;
        this.baseEngine = null;
        this.fixedIv = z;
        this.ivLength = i;
        this.cipher = new com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.AEADGenericBlockCipher(aEADCipher);
    }

    protected BaseBlockCipher(com.android.internal.org.bouncycastle.crypto.modes.AEADBlockCipher aEADBlockCipher, boolean z, int i) {
        this.availableSpecs = new java.lang.Class[]{gcmSpecClass, javax.crypto.spec.IvParameterSpec.class, javax.crypto.spec.PBEParameterSpec.class};
        this.scheme = -1;
        this.ivLength = 0;
        this.fixedIv = true;
        this.pbeSpec = null;
        this.pbeAlgorithm = null;
        this.modeName = null;
        this.baseEngine = aEADBlockCipher.getUnderlyingCipher();
        this.fixedIv = z;
        this.ivLength = i;
        this.cipher = new com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.AEADGenericBlockCipher(aEADBlockCipher);
    }

    protected BaseBlockCipher(com.android.internal.org.bouncycastle.crypto.BlockCipher blockCipher, int i) {
        this(blockCipher, true, i);
    }

    protected BaseBlockCipher(com.android.internal.org.bouncycastle.crypto.BlockCipher blockCipher, boolean z, int i) {
        this.availableSpecs = new java.lang.Class[]{gcmSpecClass, javax.crypto.spec.IvParameterSpec.class, javax.crypto.spec.PBEParameterSpec.class};
        this.scheme = -1;
        this.ivLength = 0;
        this.fixedIv = true;
        this.pbeSpec = null;
        this.pbeAlgorithm = null;
        this.modeName = null;
        this.baseEngine = blockCipher;
        this.fixedIv = z;
        this.cipher = new com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.BufferedGenericBlockCipher(blockCipher);
        this.ivLength = i / 8;
    }

    protected BaseBlockCipher(com.android.internal.org.bouncycastle.crypto.BufferedBlockCipher bufferedBlockCipher, int i) {
        this(bufferedBlockCipher, true, i);
    }

    protected BaseBlockCipher(com.android.internal.org.bouncycastle.crypto.BufferedBlockCipher bufferedBlockCipher, boolean z, int i) {
        this.availableSpecs = new java.lang.Class[]{gcmSpecClass, javax.crypto.spec.IvParameterSpec.class, javax.crypto.spec.PBEParameterSpec.class};
        this.scheme = -1;
        this.ivLength = 0;
        this.fixedIv = true;
        this.pbeSpec = null;
        this.pbeAlgorithm = null;
        this.modeName = null;
        this.baseEngine = bufferedBlockCipher.getUnderlyingCipher();
        this.cipher = new com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.BufferedGenericBlockCipher(bufferedBlockCipher);
        this.fixedIv = z;
        this.ivLength = i / 8;
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher, javax.crypto.CipherSpi
    protected int engineGetBlockSize() {
        if (this.baseEngine == null) {
            return -1;
        }
        return this.baseEngine.getBlockSize();
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher, javax.crypto.CipherSpi
    protected byte[] engineGetIV() {
        if (this.aeadParams != null) {
            return this.aeadParams.getNonce();
        }
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
        return this.cipher.getOutputSize(i);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher, javax.crypto.CipherSpi
    protected java.security.AlgorithmParameters engineGetParameters() {
        if (this.engineParams == null) {
            if (this.pbeSpec != null) {
                try {
                    this.engineParams = createParametersInstance(this.pbeAlgorithm);
                    this.engineParams.init(this.pbeSpec);
                } catch (java.lang.Exception e) {
                    return null;
                }
            } else if (this.aeadParams != null) {
                if (this.baseEngine == null) {
                    try {
                        this.engineParams = createParametersInstance(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_alg_AEADChaCha20Poly1305.getId());
                        this.engineParams.init(new com.android.internal.org.bouncycastle.asn1.DEROctetString(this.aeadParams.getNonce()).getEncoded());
                    } catch (java.lang.Exception e2) {
                        throw new java.lang.RuntimeException(e2.toString());
                    }
                } else {
                    try {
                        this.engineParams = createParametersInstance(android.security.keystore.KeyProperties.BLOCK_MODE_GCM);
                        this.engineParams.init(new com.android.internal.org.bouncycastle.asn1.cms.GCMParameters(this.aeadParams.getNonce(), this.aeadParams.getMacSize() / 8).getEncoded());
                    } catch (java.lang.Exception e3) {
                        throw new java.lang.RuntimeException(e3.toString());
                    }
                }
            } else if (this.ivParam != null) {
                java.lang.String algorithmName = this.cipher.getUnderlyingCipher().getAlgorithmName();
                if (algorithmName.indexOf(47) >= 0) {
                    algorithmName = algorithmName.substring(0, algorithmName.indexOf(47));
                }
                try {
                    this.engineParams = createParametersInstance(algorithmName);
                    this.engineParams.init(new javax.crypto.spec.IvParameterSpec(this.ivParam.getIV()));
                } catch (java.lang.Exception e4) {
                    throw new java.lang.RuntimeException(e4.toString());
                }
            }
        }
        return this.engineParams;
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher, javax.crypto.CipherSpi
    protected void engineSetMode(java.lang.String str) throws java.security.NoSuchAlgorithmException {
        if (this.baseEngine == null) {
            throw new java.security.NoSuchAlgorithmException("no mode supported for this algorithm");
        }
        this.modeName = com.android.internal.org.bouncycastle.util.Strings.toUpperCase(str);
        if (this.modeName.equals(android.security.keystore.KeyProperties.BLOCK_MODE_ECB)) {
            this.ivLength = 0;
            this.cipher = new com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.BufferedGenericBlockCipher(this.baseEngine);
            return;
        }
        if (this.modeName.equals(android.security.keystore.KeyProperties.BLOCK_MODE_CBC)) {
            this.ivLength = this.baseEngine.getBlockSize();
            this.cipher = new com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.BufferedGenericBlockCipher(new com.android.internal.org.bouncycastle.crypto.modes.CBCBlockCipher(this.baseEngine));
            return;
        }
        if (this.modeName.startsWith("OFB")) {
            this.ivLength = this.baseEngine.getBlockSize();
            if (this.modeName.length() != 3) {
                this.cipher = new com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.BufferedGenericBlockCipher(new com.android.internal.org.bouncycastle.crypto.modes.OFBBlockCipher(this.baseEngine, java.lang.Integer.parseInt(this.modeName.substring(3))));
                return;
            } else {
                this.cipher = new com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.BufferedGenericBlockCipher(new com.android.internal.org.bouncycastle.crypto.modes.OFBBlockCipher(this.baseEngine, this.baseEngine.getBlockSize() * 8));
                return;
            }
        }
        if (this.modeName.startsWith("CFB")) {
            this.ivLength = this.baseEngine.getBlockSize();
            if (this.modeName.length() != 3) {
                this.cipher = new com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.BufferedGenericBlockCipher(new com.android.internal.org.bouncycastle.crypto.modes.CFBBlockCipher(this.baseEngine, java.lang.Integer.parseInt(this.modeName.substring(3))));
                return;
            } else {
                this.cipher = new com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.BufferedGenericBlockCipher(new com.android.internal.org.bouncycastle.crypto.modes.CFBBlockCipher(this.baseEngine, this.baseEngine.getBlockSize() * 8));
                return;
            }
        }
        if (this.modeName.equals(android.security.keystore.KeyProperties.BLOCK_MODE_CTR)) {
            this.ivLength = this.baseEngine.getBlockSize();
            this.fixedIv = false;
            this.cipher = new com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.BufferedGenericBlockCipher(new com.android.internal.org.bouncycastle.crypto.BufferedBlockCipher(new com.android.internal.org.bouncycastle.crypto.modes.SICBlockCipher(this.baseEngine)));
        } else if (this.modeName.equals("CTS")) {
            this.ivLength = this.baseEngine.getBlockSize();
            this.cipher = new com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.BufferedGenericBlockCipher(new com.android.internal.org.bouncycastle.crypto.modes.CTSBlockCipher(new com.android.internal.org.bouncycastle.crypto.modes.CBCBlockCipher(this.baseEngine)));
        } else if (this.modeName.equals("CCM")) {
            this.ivLength = 12;
            this.cipher = new com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.AEADGenericBlockCipher(new com.android.internal.org.bouncycastle.crypto.modes.CCMBlockCipher(this.baseEngine));
        } else {
            if (this.modeName.equals(android.security.keystore.KeyProperties.BLOCK_MODE_GCM)) {
                this.ivLength = this.baseEngine.getBlockSize();
                this.cipher = new com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.AEADGenericBlockCipher(new com.android.internal.org.bouncycastle.crypto.modes.GCMBlockCipher(this.baseEngine));
                return;
            }
            throw new java.security.NoSuchAlgorithmException("can't support mode " + str);
        }
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher, javax.crypto.CipherSpi
    protected void engineSetPadding(java.lang.String str) throws javax.crypto.NoSuchPaddingException {
        if (this.baseEngine == null) {
            throw new javax.crypto.NoSuchPaddingException("no padding supported for this algorithm");
        }
        java.lang.String upperCase = com.android.internal.org.bouncycastle.util.Strings.toUpperCase(str);
        if (upperCase.equals("NOPADDING")) {
            if (this.cipher.wrapOnNoPadding()) {
                this.cipher = new com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.BufferedGenericBlockCipher(new com.android.internal.org.bouncycastle.crypto.BufferedBlockCipher(this.cipher.getUnderlyingCipher()));
                return;
            }
            return;
        }
        if (upperCase.equals("WITHCTS") || upperCase.equals("CTSPADDING") || upperCase.equals("CS3PADDING")) {
            this.cipher = new com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.BufferedGenericBlockCipher(new com.android.internal.org.bouncycastle.crypto.modes.CTSBlockCipher(this.cipher.getUnderlyingCipher()));
            return;
        }
        this.padded = true;
        if (isAEADModeName(this.modeName)) {
            throw new javax.crypto.NoSuchPaddingException("Only NoPadding can be used with AEAD modes.");
        }
        if (upperCase.equals("PKCS5PADDING") || upperCase.equals("PKCS7PADDING")) {
            this.cipher = new com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.BufferedGenericBlockCipher(this.cipher.getUnderlyingCipher());
            return;
        }
        if (upperCase.equals("ZEROBYTEPADDING")) {
            this.cipher = new com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.BufferedGenericBlockCipher(this.cipher.getUnderlyingCipher(), new com.android.internal.org.bouncycastle.crypto.paddings.ZeroBytePadding());
            return;
        }
        if (upperCase.equals("ISO10126PADDING") || upperCase.equals("ISO10126-2PADDING")) {
            this.cipher = new com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.BufferedGenericBlockCipher(this.cipher.getUnderlyingCipher(), new com.android.internal.org.bouncycastle.crypto.paddings.ISO10126d2Padding());
            return;
        }
        if (upperCase.equals("X9.23PADDING") || upperCase.equals("X923PADDING")) {
            this.cipher = new com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.BufferedGenericBlockCipher(this.cipher.getUnderlyingCipher(), new com.android.internal.org.bouncycastle.crypto.paddings.X923Padding());
            return;
        }
        if (upperCase.equals("ISO7816-4PADDING") || upperCase.equals("ISO9797-1PADDING")) {
            this.cipher = new com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.BufferedGenericBlockCipher(this.cipher.getUnderlyingCipher(), new com.android.internal.org.bouncycastle.crypto.paddings.ISO7816d4Padding());
        } else {
            if (upperCase.equals("TBCPADDING")) {
                this.cipher = new com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.BufferedGenericBlockCipher(this.cipher.getUnderlyingCipher(), new com.android.internal.org.bouncycastle.crypto.paddings.TBCPadding());
                return;
            }
            throw new javax.crypto.NoSuchPaddingException("Padding " + str + " unknown.");
        }
    }

    private boolean isBCPBEKeyWithoutIV(java.security.Key key) {
        return (key instanceof com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey) && !(((com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey) key).getParam() instanceof com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v59, types: [com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV] */
    /* JADX WARN: Type inference failed for: r5v12, types: [com.android.internal.org.bouncycastle.crypto.CipherParameters] */
    /* JADX WARN: Type inference failed for: r5v13, types: [com.android.internal.org.bouncycastle.crypto.params.AEADParameters] */
    /* JADX WARN: Type inference failed for: r5v15 */
    /* JADX WARN: Type inference failed for: r5v16 */
    /* JADX WARN: Type inference failed for: r5v19 */
    /* JADX WARN: Type inference failed for: r5v20, types: [com.android.internal.org.bouncycastle.crypto.CipherParameters] */
    /* JADX WARN: Type inference failed for: r5v25, types: [com.android.internal.org.bouncycastle.crypto.params.AEADParameters] */
    /* JADX WARN: Type inference failed for: r5v55 */
    /* JADX WARN: Type inference failed for: r5v56 */
    /* JADX WARN: Type inference failed for: r5v57 */
    /* JADX WARN: Type inference failed for: r5v58 */
    /* JADX WARN: Type inference failed for: r5v59 */
    /* JADX WARN: Type inference failed for: r5v60 */
    /* JADX WARN: Type inference failed for: r5v61 */
    @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher, javax.crypto.CipherSpi
    protected void engineInit(int i, java.security.Key key, java.security.spec.AlgorithmParameterSpec algorithmParameterSpec, java.security.SecureRandom secureRandom) throws java.security.InvalidKeyException, java.security.InvalidAlgorithmParameterException {
        com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters;
        com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV parametersWithIV;
        com.android.internal.org.bouncycastle.crypto.params.KeyParameter keyParameter;
        int i2;
        com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom parametersWithRandom;
        java.security.SecureRandom secureRandom2;
        com.android.internal.org.bouncycastle.crypto.params.KeyParameter keyParameter2;
        com.android.internal.org.bouncycastle.crypto.CipherParameters makePBEParameters;
        this.pbeSpec = null;
        this.pbeAlgorithm = null;
        this.engineParams = null;
        this.aeadParams = null;
        if (!(key instanceof javax.crypto.SecretKey)) {
            throw new java.security.InvalidKeyException("Key for algorithm " + (key != null ? key.getAlgorithm() : null) + " not suitable for symmetric enryption.");
        }
        if (algorithmParameterSpec == null && this.baseEngine != null && this.baseEngine.getAlgorithmName().startsWith("RC5-64")) {
            throw new java.security.InvalidAlgorithmParameterException("RC5 requires an RC5ParametersSpec to be passed in.");
        }
        if ((this.scheme == 2 || (key instanceof com.android.internal.org.bouncycastle.jcajce.PKCS12Key)) && !isBCPBEKeyWithoutIV(key)) {
            try {
                javax.crypto.SecretKey secretKey = (javax.crypto.SecretKey) key;
                if (algorithmParameterSpec instanceof javax.crypto.spec.PBEParameterSpec) {
                    this.pbeSpec = (javax.crypto.spec.PBEParameterSpec) algorithmParameterSpec;
                }
                boolean z = secretKey instanceof javax.crypto.interfaces.PBEKey;
                if (z && this.pbeSpec == null) {
                    javax.crypto.interfaces.PBEKey pBEKey = (javax.crypto.interfaces.PBEKey) secretKey;
                    if (pBEKey.getSalt() != null) {
                        this.pbeSpec = new javax.crypto.spec.PBEParameterSpec(pBEKey.getSalt(), pBEKey.getIterationCount());
                    } else {
                        throw new java.security.InvalidAlgorithmParameterException("PBEKey requires parameters to specify salt");
                    }
                }
                if (this.pbeSpec == null && !z) {
                    throw new java.security.InvalidKeyException("Algorithm requires a PBE key");
                }
                if (!(key instanceof com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey)) {
                    cipherParameters = com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.PBE.Util.makePBEParameters(secretKey.getEncoded(), 2, this.digest, this.keySizeInBits, this.ivLength * 8, this.pbeSpec, this.cipher.getAlgorithmName());
                } else {
                    com.android.internal.org.bouncycastle.crypto.CipherParameters param = ((com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey) key).getParam();
                    boolean z2 = param instanceof com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV;
                    cipherParameters = param;
                    if (!z2) {
                        if (param == null) {
                            throw new java.lang.AssertionError("Unreachable code");
                        }
                        throw new java.security.InvalidKeyException("Algorithm requires a PBE key suitable for PKCS12");
                    }
                }
                boolean z3 = cipherParameters instanceof com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV;
                parametersWithIV = cipherParameters;
                if (z3) {
                    this.ivParam = (com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV) cipherParameters;
                    parametersWithIV = cipherParameters;
                }
            } catch (java.lang.Exception e) {
                throw new java.security.InvalidKeyException("PKCS12 requires a SecretKey/PBEKey");
            }
        } else if (key instanceof com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey) {
            com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey bCPBEKey = (com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey) key;
            if (bCPBEKey.getOID() != null) {
                this.pbeAlgorithm = bCPBEKey.getOID().getId();
            } else {
                this.pbeAlgorithm = bCPBEKey.getAlgorithm();
            }
            if (bCPBEKey.getParam() != null) {
                makePBEParameters = adjustParameters(algorithmParameterSpec, bCPBEKey.getParam());
            } else if (algorithmParameterSpec instanceof javax.crypto.spec.PBEParameterSpec) {
                this.pbeSpec = (javax.crypto.spec.PBEParameterSpec) algorithmParameterSpec;
                if (this.pbeSpec.getSalt().length != 0 && this.pbeSpec.getIterationCount() > 0) {
                    bCPBEKey = new com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey(bCPBEKey.getAlgorithm(), bCPBEKey.getOID(), bCPBEKey.getType(), bCPBEKey.getDigest(), bCPBEKey.getKeySize(), bCPBEKey.getIvSize(), new javax.crypto.spec.PBEKeySpec(bCPBEKey.getPassword(), this.pbeSpec.getSalt(), this.pbeSpec.getIterationCount(), bCPBEKey.getKeySize()), null);
                }
                makePBEParameters = com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.PBE.Util.makePBEParameters(bCPBEKey, algorithmParameterSpec, this.cipher.getUnderlyingCipher().getAlgorithmName());
            } else {
                throw new java.security.InvalidAlgorithmParameterException("PBE requires PBE parameters to be set.");
            }
            boolean z4 = makePBEParameters instanceof com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV;
            parametersWithIV = makePBEParameters;
            if (z4) {
                this.ivParam = (com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV) makePBEParameters;
                parametersWithIV = makePBEParameters;
            }
        } else if (key instanceof javax.crypto.interfaces.PBEKey) {
            javax.crypto.interfaces.PBEKey pBEKey2 = (javax.crypto.interfaces.PBEKey) key;
            this.pbeSpec = (javax.crypto.spec.PBEParameterSpec) algorithmParameterSpec;
            if ((pBEKey2 instanceof com.android.internal.org.bouncycastle.jcajce.PKCS12KeyWithParameters) && this.pbeSpec == null) {
                this.pbeSpec = new javax.crypto.spec.PBEParameterSpec(pBEKey2.getSalt(), pBEKey2.getIterationCount());
            }
            com.android.internal.org.bouncycastle.crypto.CipherParameters makePBEParameters2 = com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.PBE.Util.makePBEParameters(pBEKey2.getEncoded(), this.scheme, this.digest, this.keySizeInBits, this.ivLength * 8, this.pbeSpec, this.cipher.getAlgorithmName());
            boolean z5 = makePBEParameters2 instanceof com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV;
            parametersWithIV = makePBEParameters2;
            if (z5) {
                this.ivParam = (com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV) makePBEParameters2;
                parametersWithIV = makePBEParameters2;
            }
        } else {
            if (this.scheme == 0 || this.scheme == 4 || this.scheme == 1 || this.scheme == 5) {
                throw new java.security.InvalidKeyException("Algorithm requires a PBE key");
            }
            parametersWithIV = new com.android.internal.org.bouncycastle.crypto.params.KeyParameter(key.getEncoded());
        }
        if (algorithmParameterSpec instanceof com.android.internal.org.bouncycastle.jcajce.spec.AEADParameterSpec) {
            if (!isAEADModeName(this.modeName) && !(this.cipher instanceof com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.AEADGenericBlockCipher)) {
                throw new java.security.InvalidAlgorithmParameterException("AEADParameterSpec can only be used with AEAD modes.");
            }
            com.android.internal.org.bouncycastle.jcajce.spec.AEADParameterSpec aEADParameterSpec = (com.android.internal.org.bouncycastle.jcajce.spec.AEADParameterSpec) algorithmParameterSpec;
            if (parametersWithIV instanceof com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV) {
                keyParameter2 = (com.android.internal.org.bouncycastle.crypto.params.KeyParameter) ((com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV) parametersWithIV).getParameters();
            } else {
                keyParameter2 = parametersWithIV;
            }
            parametersWithIV = new com.android.internal.org.bouncycastle.crypto.params.AEADParameters(keyParameter2, aEADParameterSpec.getMacSizeInBits(), aEADParameterSpec.getNonce(), aEADParameterSpec.getAssociatedData());
            this.aeadParams = parametersWithIV;
        } else if (algorithmParameterSpec instanceof javax.crypto.spec.IvParameterSpec) {
            if (this.ivLength != 0) {
                javax.crypto.spec.IvParameterSpec ivParameterSpec = (javax.crypto.spec.IvParameterSpec) algorithmParameterSpec;
                if (ivParameterSpec.getIV().length != this.ivLength && !(this.cipher instanceof com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.AEADGenericBlockCipher) && this.fixedIv) {
                    throw new java.security.InvalidAlgorithmParameterException("IV must be " + this.ivLength + " bytes long.");
                }
                if (parametersWithIV instanceof com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV) {
                    parametersWithIV = new com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV(((com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV) parametersWithIV).getParameters(), ivParameterSpec.getIV());
                } else {
                    parametersWithIV = new com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV(parametersWithIV, ivParameterSpec.getIV());
                }
                this.ivParam = parametersWithIV;
            } else if (this.modeName != null && this.modeName.equals(android.security.keystore.KeyProperties.BLOCK_MODE_ECB)) {
                throw new java.security.InvalidAlgorithmParameterException("ECB mode does not use an IV");
            }
        } else if (gcmSpecClass != null && gcmSpecClass.isInstance(algorithmParameterSpec)) {
            if (!isAEADModeName(this.modeName) && !(this.cipher instanceof com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.AEADGenericBlockCipher)) {
                throw new java.security.InvalidAlgorithmParameterException("GCMParameterSpec can only be used with AEAD modes.");
            }
            if (parametersWithIV instanceof com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV) {
                keyParameter = (com.android.internal.org.bouncycastle.crypto.params.KeyParameter) ((com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV) parametersWithIV).getParameters();
            } else {
                keyParameter = parametersWithIV;
            }
            parametersWithIV = com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.GcmSpecUtil.extractAeadParameters(keyParameter, algorithmParameterSpec);
            this.aeadParams = parametersWithIV;
        } else if (algorithmParameterSpec != null && !(algorithmParameterSpec instanceof javax.crypto.spec.PBEParameterSpec)) {
            throw new java.security.InvalidAlgorithmParameterException("unknown parameter type.");
        }
        if (this.ivLength == 0 || (parametersWithIV instanceof com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV) || (parametersWithIV instanceof com.android.internal.org.bouncycastle.crypto.params.AEADParameters)) {
            i2 = i;
            parametersWithRandom = parametersWithIV;
        } else {
            if (secureRandom != null) {
                secureRandom2 = secureRandom;
            } else {
                secureRandom2 = com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar.getSecureRandom();
            }
            i2 = i;
            if (i2 == 1 || i2 == 3) {
                byte[] bArr = new byte[this.ivLength];
                if (!isBCPBEKeyWithoutIV(key)) {
                    secureRandom2.nextBytes(bArr);
                    ?? parametersWithIV2 = new com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV(parametersWithIV, bArr);
                    this.ivParam = parametersWithIV2;
                    parametersWithRandom = parametersWithIV2;
                } else {
                    throw new java.security.InvalidAlgorithmParameterException("No IV set when using PBE key");
                }
            } else {
                parametersWithRandom = parametersWithIV;
                if (this.cipher.getUnderlyingCipher().getAlgorithmName().indexOf("PGPCFB") < 0) {
                    throw new java.security.InvalidAlgorithmParameterException("No IV set when using PBE key");
                }
            }
        }
        if (secureRandom != null && this.padded) {
            parametersWithRandom = new com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom(parametersWithRandom, secureRandom);
        }
        try {
            switch (i2) {
                case 1:
                case 3:
                    this.cipher.init(true, parametersWithRandom);
                    break;
                case 2:
                case 4:
                    this.cipher.init(false, parametersWithRandom);
                    break;
                default:
                    throw new java.security.InvalidParameterException("unknown opmode " + i2 + " passed");
            }
            if ((this.cipher instanceof com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.AEADGenericBlockCipher) && this.aeadParams == null) {
                this.aeadParams = new com.android.internal.org.bouncycastle.crypto.params.AEADParameters((com.android.internal.org.bouncycastle.crypto.params.KeyParameter) this.ivParam.getParameters(), ((com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.AEADGenericBlockCipher) this.cipher).cipher.getMac().length * 8, this.ivParam.getIV());
            }
        } catch (java.lang.Exception e2) {
            throw new com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher.InvalidKeyOrParametersException(e2.getMessage(), e2);
        }
    }

    private com.android.internal.org.bouncycastle.crypto.CipherParameters adjustParameters(java.security.spec.AlgorithmParameterSpec algorithmParameterSpec, com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters) {
        if (cipherParameters instanceof com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV) {
            com.android.internal.org.bouncycastle.crypto.CipherParameters parameters = ((com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV) cipherParameters).getParameters();
            if (algorithmParameterSpec instanceof javax.crypto.spec.IvParameterSpec) {
                this.ivParam = new com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV(parameters, ((javax.crypto.spec.IvParameterSpec) algorithmParameterSpec).getIV());
                return this.ivParam;
            }
            return cipherParameters;
        }
        if (algorithmParameterSpec instanceof javax.crypto.spec.IvParameterSpec) {
            this.ivParam = new com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV(cipherParameters, ((javax.crypto.spec.IvParameterSpec) algorithmParameterSpec).getIV());
            return this.ivParam;
        }
        return cipherParameters;
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

    @Override // javax.crypto.CipherSpi
    protected void engineUpdateAAD(byte[] bArr, int i, int i2) {
        this.cipher.updateAAD(bArr, i, i2);
    }

    @Override // javax.crypto.CipherSpi
    protected void engineUpdateAAD(java.nio.ByteBuffer byteBuffer) {
        int remaining = byteBuffer.remaining();
        if (remaining >= 1) {
            if (byteBuffer.hasArray()) {
                engineUpdateAAD(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), remaining);
                byteBuffer.position(byteBuffer.limit());
                return;
            }
            if (remaining <= 512) {
                byte[] bArr = new byte[remaining];
                byteBuffer.get(bArr);
                engineUpdateAAD(bArr, 0, remaining);
                com.android.internal.org.bouncycastle.util.Arrays.fill(bArr, (byte) 0);
                return;
            }
            byte[] bArr2 = new byte[512];
            do {
                int min = java.lang.Math.min(512, remaining);
                byteBuffer.get(bArr2, 0, min);
                engineUpdateAAD(bArr2, 0, min);
                remaining -= min;
            } while (remaining > 0);
            com.android.internal.org.bouncycastle.util.Arrays.fill(bArr2, (byte) 0);
        }
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher, javax.crypto.CipherSpi
    protected byte[] engineUpdate(byte[] bArr, int i, int i2) {
        int updateOutputSize = this.cipher.getUpdateOutputSize(i2);
        if (updateOutputSize > 0) {
            byte[] bArr2 = new byte[updateOutputSize];
            int processBytes = this.cipher.processBytes(bArr, i, i2, bArr2, 0);
            if (processBytes == 0) {
                return null;
            }
            if (processBytes != updateOutputSize) {
                byte[] bArr3 = new byte[processBytes];
                java.lang.System.arraycopy(bArr2, 0, bArr3, 0, processBytes);
                return bArr3;
            }
            return bArr2;
        }
        this.cipher.processBytes(bArr, i, i2, null, 0);
        return null;
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher, javax.crypto.CipherSpi
    protected int engineUpdate(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws javax.crypto.ShortBufferException {
        if (this.cipher.getUpdateOutputSize(i2) + i3 > bArr2.length) {
            throw new javax.crypto.ShortBufferException("output buffer too short for input.");
        }
        try {
            return this.cipher.processBytes(bArr, i, i2, bArr2, i3);
        } catch (com.android.internal.org.bouncycastle.crypto.DataLengthException e) {
            throw new java.lang.IllegalStateException(e.toString());
        }
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher, javax.crypto.CipherSpi
    protected byte[] engineDoFinal(byte[] bArr, int i, int i2) throws javax.crypto.IllegalBlockSizeException, javax.crypto.BadPaddingException {
        int i3;
        int engineGetOutputSize = engineGetOutputSize(i2);
        byte[] bArr2 = new byte[engineGetOutputSize];
        if (i2 == 0) {
            i3 = 0;
        } else {
            i3 = this.cipher.processBytes(bArr, i, i2, bArr2, 0);
        }
        try {
            int doFinal = i3 + this.cipher.doFinal(bArr2, i3);
            if (doFinal == engineGetOutputSize) {
                return bArr2;
            }
            if (doFinal > engineGetOutputSize) {
                throw new javax.crypto.IllegalBlockSizeException("internal buffer overflow");
            }
            byte[] bArr3 = new byte[doFinal];
            java.lang.System.arraycopy(bArr2, 0, bArr3, 0, doFinal);
            return bArr3;
        } catch (com.android.internal.org.bouncycastle.crypto.DataLengthException e) {
            throw new javax.crypto.IllegalBlockSizeException(e.getMessage());
        }
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher, javax.crypto.CipherSpi
    protected int engineDoFinal(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws javax.crypto.IllegalBlockSizeException, javax.crypto.BadPaddingException, javax.crypto.ShortBufferException {
        int processBytes;
        if (engineGetOutputSize(i2) + i3 > bArr2.length) {
            throw new javax.crypto.ShortBufferException("output buffer too short for input.");
        }
        if (i2 == 0) {
            processBytes = 0;
        } else {
            try {
                processBytes = this.cipher.processBytes(bArr, i, i2, bArr2, i3);
            } catch (com.android.internal.org.bouncycastle.crypto.OutputLengthException e) {
                throw new javax.crypto.IllegalBlockSizeException(e.getMessage());
            } catch (com.android.internal.org.bouncycastle.crypto.DataLengthException e2) {
                throw new javax.crypto.IllegalBlockSizeException(e2.getMessage());
            }
        }
        return processBytes + this.cipher.doFinal(bArr2, i3 + processBytes);
    }

    private boolean isAEADModeName(java.lang.String str) {
        return "CCM".equals(str) || android.security.keystore.KeyProperties.BLOCK_MODE_GCM.equals(str);
    }

    private static class BufferedGenericBlockCipher implements com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher {
        private com.android.internal.org.bouncycastle.crypto.BufferedBlockCipher cipher;

        BufferedGenericBlockCipher(com.android.internal.org.bouncycastle.crypto.BufferedBlockCipher bufferedBlockCipher) {
            this.cipher = bufferedBlockCipher;
        }

        BufferedGenericBlockCipher(com.android.internal.org.bouncycastle.crypto.BlockCipher blockCipher) {
            this.cipher = new com.android.internal.org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher(blockCipher);
        }

        BufferedGenericBlockCipher(com.android.internal.org.bouncycastle.crypto.BlockCipher blockCipher, com.android.internal.org.bouncycastle.crypto.paddings.BlockCipherPadding blockCipherPadding) {
            this.cipher = new com.android.internal.org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher(blockCipher, blockCipherPadding);
        }

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public void init(boolean z, com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters) throws java.lang.IllegalArgumentException {
            this.cipher.init(z, cipherParameters);
        }

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public boolean wrapOnNoPadding() {
            return !(this.cipher instanceof com.android.internal.org.bouncycastle.crypto.modes.CTSBlockCipher);
        }

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public java.lang.String getAlgorithmName() {
            return this.cipher.getUnderlyingCipher().getAlgorithmName();
        }

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public com.android.internal.org.bouncycastle.crypto.BlockCipher getUnderlyingCipher() {
            return this.cipher.getUnderlyingCipher();
        }

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public int getOutputSize(int i) {
            return this.cipher.getOutputSize(i);
        }

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public int getUpdateOutputSize(int i) {
            return this.cipher.getUpdateOutputSize(i);
        }

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public void updateAAD(byte[] bArr, int i, int i2) {
            throw new java.lang.UnsupportedOperationException("AAD is not supported in the current mode.");
        }

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public int processByte(byte b, byte[] bArr, int i) throws com.android.internal.org.bouncycastle.crypto.DataLengthException {
            return this.cipher.processByte(b, bArr, i);
        }

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public int processBytes(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws com.android.internal.org.bouncycastle.crypto.DataLengthException {
            return this.cipher.processBytes(bArr, i, i2, bArr2, i3);
        }

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public int doFinal(byte[] bArr, int i) throws java.lang.IllegalStateException, javax.crypto.BadPaddingException {
            try {
                return this.cipher.doFinal(bArr, i);
            } catch (com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException e) {
                throw new javax.crypto.BadPaddingException(e.getMessage());
            }
        }
    }

    private static class AEADGenericBlockCipher implements com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher {
        private static final java.lang.reflect.Constructor aeadBadTagConstructor;
        private com.android.internal.org.bouncycastle.crypto.modes.AEADCipher cipher;

        static {
            java.lang.Class loadClass = com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.ClassUtil.loadClass(com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.class, "javax.crypto.AEADBadTagException");
            if (loadClass != null) {
                aeadBadTagConstructor = findExceptionConstructor(loadClass);
            } else {
                aeadBadTagConstructor = null;
            }
        }

        private static java.lang.reflect.Constructor findExceptionConstructor(java.lang.Class cls) {
            try {
                return cls.getConstructor(java.lang.String.class);
            } catch (java.lang.Exception e) {
                return null;
            }
        }

        AEADGenericBlockCipher(com.android.internal.org.bouncycastle.crypto.modes.AEADCipher aEADCipher) {
            this.cipher = aEADCipher;
        }

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public void init(boolean z, com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters) throws java.lang.IllegalArgumentException {
            this.cipher.init(z, cipherParameters);
        }

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public java.lang.String getAlgorithmName() {
            if (this.cipher instanceof com.android.internal.org.bouncycastle.crypto.modes.AEADBlockCipher) {
                return ((com.android.internal.org.bouncycastle.crypto.modes.AEADBlockCipher) this.cipher).getUnderlyingCipher().getAlgorithmName();
            }
            return this.cipher.getAlgorithmName();
        }

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public boolean wrapOnNoPadding() {
            return false;
        }

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public com.android.internal.org.bouncycastle.crypto.BlockCipher getUnderlyingCipher() {
            if (this.cipher instanceof com.android.internal.org.bouncycastle.crypto.modes.AEADBlockCipher) {
                return ((com.android.internal.org.bouncycastle.crypto.modes.AEADBlockCipher) this.cipher).getUnderlyingCipher();
            }
            return null;
        }

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public int getOutputSize(int i) {
            return this.cipher.getOutputSize(i);
        }

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public int getUpdateOutputSize(int i) {
            return this.cipher.getUpdateOutputSize(i);
        }

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public void updateAAD(byte[] bArr, int i, int i2) {
            this.cipher.processAADBytes(bArr, i, i2);
        }

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public int processByte(byte b, byte[] bArr, int i) throws com.android.internal.org.bouncycastle.crypto.DataLengthException {
            return this.cipher.processByte(b, bArr, i);
        }

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public int processBytes(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws com.android.internal.org.bouncycastle.crypto.DataLengthException {
            return this.cipher.processBytes(bArr, i, i2, bArr2, i3);
        }

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher.GenericBlockCipher
        public int doFinal(byte[] bArr, int i) throws java.lang.IllegalStateException, javax.crypto.BadPaddingException {
            javax.crypto.BadPaddingException badPaddingException;
            try {
                return this.cipher.doFinal(bArr, i);
            } catch (com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException e) {
                if (aeadBadTagConstructor != null) {
                    try {
                        badPaddingException = (javax.crypto.BadPaddingException) aeadBadTagConstructor.newInstance(e.getMessage());
                    } catch (java.lang.Exception e2) {
                        badPaddingException = null;
                    }
                    if (badPaddingException != null) {
                        throw badPaddingException;
                    }
                }
                throw new javax.crypto.BadPaddingException(e.getMessage());
            }
        }
    }
}
