package android.security.keystore2;

/* loaded from: classes3.dex */
abstract class AndroidKeyStoreUnauthenticatedAESCipherSpi extends android.security.keystore2.AndroidKeyStoreCipherSpiBase {
    private static final int BLOCK_SIZE_BYTES = 16;
    private byte[] mIv;
    private boolean mIvHasBeenUsed;
    private final boolean mIvRequired;
    private final int mKeymasterBlockMode;
    private final int mKeymasterPadding;

    static abstract class ECB extends android.security.keystore2.AndroidKeyStoreUnauthenticatedAESCipherSpi {
        protected ECB(int i) {
            super(1, i, false);
        }

        public static class NoPadding extends android.security.keystore2.AndroidKeyStoreUnauthenticatedAESCipherSpi.ECB {
            @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
            public /* bridge */ /* synthetic */ void finalize() throws java.lang.Throwable {
                super.finalize();
            }

            public NoPadding() {
                super(1);
            }

            @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
            protected final java.lang.String getTransform() {
                return "AES/ECB/NoPadding";
            }
        }

        public static class PKCS7Padding extends android.security.keystore2.AndroidKeyStoreUnauthenticatedAESCipherSpi.ECB {
            @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
            public /* bridge */ /* synthetic */ void finalize() throws java.lang.Throwable {
                super.finalize();
            }

            public PKCS7Padding() {
                super(64);
            }

            @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
            protected final java.lang.String getTransform() {
                return "AES/ECB/PKCS7Padding";
            }
        }
    }

    static abstract class CBC extends android.security.keystore2.AndroidKeyStoreUnauthenticatedAESCipherSpi {
        protected CBC(int i) {
            super(2, i, true);
        }

        public static class NoPadding extends android.security.keystore2.AndroidKeyStoreUnauthenticatedAESCipherSpi.CBC {
            @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
            public /* bridge */ /* synthetic */ void finalize() throws java.lang.Throwable {
                super.finalize();
            }

            public NoPadding() {
                super(1);
            }

            @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
            protected final java.lang.String getTransform() {
                return "AES/CBC/NoPadding";
            }
        }

        public static class PKCS7Padding extends android.security.keystore2.AndroidKeyStoreUnauthenticatedAESCipherSpi.CBC {
            @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
            public /* bridge */ /* synthetic */ void finalize() throws java.lang.Throwable {
                super.finalize();
            }

            public PKCS7Padding() {
                super(64);
            }

            @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
            protected final java.lang.String getTransform() {
                return "AES/CBC/PKCS7Padding";
            }
        }
    }

    static abstract class CTR extends android.security.keystore2.AndroidKeyStoreUnauthenticatedAESCipherSpi {
        protected CTR(int i) {
            super(3, i, true);
        }

        public static class NoPadding extends android.security.keystore2.AndroidKeyStoreUnauthenticatedAESCipherSpi.CTR {
            @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
            public /* bridge */ /* synthetic */ void finalize() throws java.lang.Throwable {
                super.finalize();
            }

            public NoPadding() {
                super(1);
            }

            @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
            protected final java.lang.String getTransform() {
                return "AES/CTR/NoPadding";
            }
        }
    }

    AndroidKeyStoreUnauthenticatedAESCipherSpi(int i, int i2, boolean z) {
        this.mKeymasterBlockMode = i;
        this.mKeymasterPadding = i2;
        this.mIvRequired = z;
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected final void resetAll() {
        this.mIv = null;
        this.mIvHasBeenUsed = false;
        super.resetAll();
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected final void resetWhilePreservingInitState() {
        super.resetWhilePreservingInitState();
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected final void initKey(int i, java.security.Key key) throws java.security.InvalidKeyException {
        if (!(key instanceof android.security.keystore2.AndroidKeyStoreSecretKey)) {
            throw new java.security.InvalidKeyException("Unsupported key: " + (key != null ? key.getClass().getName() : "null"));
        }
        if (!android.security.keystore.KeyProperties.KEY_ALGORITHM_AES.equalsIgnoreCase(key.getAlgorithm())) {
            throw new java.security.InvalidKeyException("Unsupported key algorithm: " + key.getAlgorithm() + ". Only " + android.security.keystore.KeyProperties.KEY_ALGORITHM_AES + " supported");
        }
        setKey((android.security.keystore2.AndroidKeyStoreSecretKey) key);
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected final void initAlgorithmSpecificParameters() throws java.security.InvalidKeyException {
        if (this.mIvRequired && !isEncrypting()) {
            throw new java.security.InvalidKeyException("IV required when decrypting. Use IvParameterSpec or AlgorithmParameters to provide it.");
        }
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected final void initAlgorithmSpecificParameters(java.security.spec.AlgorithmParameterSpec algorithmParameterSpec) throws java.security.InvalidAlgorithmParameterException {
        if (!this.mIvRequired) {
            if (algorithmParameterSpec != null) {
                throw new java.security.InvalidAlgorithmParameterException("Unsupported parameters: " + algorithmParameterSpec);
            }
        } else if (algorithmParameterSpec == null) {
            if (!isEncrypting()) {
                throw new java.security.InvalidAlgorithmParameterException("IvParameterSpec must be provided when decrypting");
            }
        } else {
            if (!(algorithmParameterSpec instanceof javax.crypto.spec.IvParameterSpec)) {
                throw new java.security.InvalidAlgorithmParameterException("Only IvParameterSpec supported");
            }
            this.mIv = ((javax.crypto.spec.IvParameterSpec) algorithmParameterSpec).getIV();
            if (this.mIv == null) {
                throw new java.security.InvalidAlgorithmParameterException("Null IV in IvParameterSpec");
            }
        }
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected final void initAlgorithmSpecificParameters(java.security.AlgorithmParameters algorithmParameters) throws java.security.InvalidAlgorithmParameterException {
        if (!this.mIvRequired) {
            if (algorithmParameters != null) {
                throw new java.security.InvalidAlgorithmParameterException("Unsupported parameters: " + algorithmParameters);
            }
            return;
        }
        if (algorithmParameters == null) {
            if (!isEncrypting()) {
                throw new java.security.InvalidAlgorithmParameterException("IV required when decrypting. Use IvParameterSpec or AlgorithmParameters to provide it.");
            }
        } else {
            if (!android.security.keystore.KeyProperties.KEY_ALGORITHM_AES.equalsIgnoreCase(algorithmParameters.getAlgorithm())) {
                throw new java.security.InvalidAlgorithmParameterException("Unsupported AlgorithmParameters algorithm: " + algorithmParameters.getAlgorithm() + ". Supported: AES");
            }
            try {
                this.mIv = ((javax.crypto.spec.IvParameterSpec) algorithmParameters.getParameterSpec(javax.crypto.spec.IvParameterSpec.class)).getIV();
                if (this.mIv == null) {
                    throw new java.security.InvalidAlgorithmParameterException("Null IV in AlgorithmParameters");
                }
            } catch (java.security.spec.InvalidParameterSpecException e) {
                if (!isEncrypting()) {
                    throw new java.security.InvalidAlgorithmParameterException("IV required when decrypting, but not found in parameters: " + algorithmParameters, e);
                }
                this.mIv = null;
            }
        }
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected final int getAdditionalEntropyAmountForBegin() {
        if (this.mIvRequired && this.mIv == null && isEncrypting()) {
            return 16;
        }
        return 0;
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected final int getAdditionalEntropyAmountForFinish() {
        return 0;
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected final void addAlgorithmSpecificParametersToBegin(java.util.List<android.hardware.security.keymint.KeyParameter> list) {
        if (isEncrypting() && this.mIvRequired && this.mIvHasBeenUsed) {
            throw new java.lang.IllegalStateException("IV has already been used. Reusing IV in encryption mode violates security best practices.");
        }
        list.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(268435458, 32));
        list.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536870916, this.mKeymasterBlockMode));
        list.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536870918, this.mKeymasterPadding));
        if (this.mIvRequired && this.mIv != null) {
            list.add(android.security.keystore2.KeyStore2ParameterUtils.makeBytes(-1879047191, this.mIv));
        }
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected final void loadAlgorithmSpecificParametersFromBeginResult(android.hardware.security.keymint.KeyParameter[] keyParameterArr) {
        byte[] bArr;
        this.mIvHasBeenUsed = true;
        if (keyParameterArr != null) {
            for (android.hardware.security.keymint.KeyParameter keyParameter : keyParameterArr) {
                if (keyParameter.tag == -1879047191) {
                    bArr = keyParameter.value.getBlob();
                    break;
                }
            }
        }
        bArr = null;
        if (this.mIvRequired) {
            if (this.mIv == null) {
                this.mIv = bArr;
                return;
            } else {
                if (bArr != null && !java.util.Arrays.equals(bArr, this.mIv)) {
                    throw new java.security.ProviderException("IV in use differs from provided IV");
                }
                return;
            }
        }
        if (bArr != null) {
            throw new java.security.ProviderException("IV in use despite IV not being used by this transformation");
        }
    }

    @Override // javax.crypto.CipherSpi
    protected final int engineGetBlockSize() {
        return 16;
    }

    @Override // javax.crypto.CipherSpi
    protected final int engineGetOutputSize(int i) {
        return i + 48;
    }

    @Override // javax.crypto.CipherSpi
    protected final byte[] engineGetIV() {
        return android.security.keystore.ArrayUtils.cloneIfNotEmpty(this.mIv);
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase, javax.crypto.CipherSpi
    protected final java.security.AlgorithmParameters engineGetParameters() {
        if (!this.mIvRequired || this.mIv == null || this.mIv.length <= 0) {
            return null;
        }
        try {
            java.security.AlgorithmParameters algorithmParameters = java.security.AlgorithmParameters.getInstance(android.security.keystore.KeyProperties.KEY_ALGORITHM_AES);
            algorithmParameters.init(new javax.crypto.spec.IvParameterSpec(this.mIv));
            return algorithmParameters;
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new java.security.ProviderException("Failed to obtain AES AlgorithmParameters", e);
        } catch (java.security.spec.InvalidParameterSpecException e2) {
            throw new java.security.ProviderException("Failed to initialize AES AlgorithmParameters with an IV", e2);
        }
    }
}