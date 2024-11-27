package android.security.keystore2;

/* loaded from: classes3.dex */
abstract class AndroidKeyStoreAuthenticatedAESCipherSpi extends android.security.keystore2.AndroidKeyStoreCipherSpiBase {
    private static final int BLOCK_SIZE_BYTES = 16;
    private byte[] mIv;
    private boolean mIvHasBeenUsed;
    private final int mKeymasterBlockMode;
    private final int mKeymasterPadding;

    static abstract class GCM extends android.security.keystore2.AndroidKeyStoreAuthenticatedAESCipherSpi {
        private static final int DEFAULT_TAG_LENGTH_BITS = 128;
        private static final int IV_LENGTH_BYTES = 12;
        private static final int MAX_SUPPORTED_TAG_LENGTH_BITS = 128;
        static final int MIN_SUPPORTED_TAG_LENGTH_BITS = 96;
        private int mTagLengthBits;

        GCM(int i) {
            super(32, i);
            this.mTagLengthBits = 128;
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final java.lang.String getTransform() {
            return "AES/GCM/NoPadding";
        }

        @Override // android.security.keystore2.AndroidKeyStoreAuthenticatedAESCipherSpi, android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final void resetAll() {
            this.mTagLengthBits = 128;
            super.resetAll();
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final void resetWhilePreservingInitState() {
            super.resetWhilePreservingInitState();
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final void initAlgorithmSpecificParameters() throws java.security.InvalidKeyException {
            if (!isEncrypting()) {
                throw new java.security.InvalidKeyException("IV required when decrypting. Use IvParameterSpec or AlgorithmParameters to provide it.");
            }
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final void initAlgorithmSpecificParameters(java.security.spec.AlgorithmParameterSpec algorithmParameterSpec) throws java.security.InvalidAlgorithmParameterException {
            if (algorithmParameterSpec == null) {
                if (!isEncrypting()) {
                    throw new java.security.InvalidAlgorithmParameterException("GCMParameterSpec must be provided when decrypting");
                }
                return;
            }
            if (!(algorithmParameterSpec instanceof javax.crypto.spec.GCMParameterSpec)) {
                throw new java.security.InvalidAlgorithmParameterException("Only GCMParameterSpec supported");
            }
            javax.crypto.spec.GCMParameterSpec gCMParameterSpec = (javax.crypto.spec.GCMParameterSpec) algorithmParameterSpec;
            byte[] iv = gCMParameterSpec.getIV();
            if (iv == null) {
                throw new java.security.InvalidAlgorithmParameterException("Null IV in GCMParameterSpec");
            }
            if (iv.length != 12) {
                throw new java.security.InvalidAlgorithmParameterException("Unsupported IV length: " + iv.length + " bytes. Only 12 bytes long IV supported");
            }
            int tLen = gCMParameterSpec.getTLen();
            if (tLen < 96 || tLen > 128 || tLen % 8 != 0) {
                throw new java.security.InvalidAlgorithmParameterException("Unsupported tag length: " + tLen + " bits. Supported lengths: 96, 104, 112, 120, 128");
            }
            setIv(iv);
            this.mTagLengthBits = tLen;
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final void initAlgorithmSpecificParameters(java.security.AlgorithmParameters algorithmParameters) throws java.security.InvalidAlgorithmParameterException {
            if (algorithmParameters == null) {
                if (!isEncrypting()) {
                    throw new java.security.InvalidAlgorithmParameterException("IV required when decrypting. Use GCMParameterSpec or GCM AlgorithmParameters to provide it.");
                }
            } else {
                if (!android.security.keystore.KeyProperties.BLOCK_MODE_GCM.equalsIgnoreCase(algorithmParameters.getAlgorithm())) {
                    throw new java.security.InvalidAlgorithmParameterException("Unsupported AlgorithmParameters algorithm: " + algorithmParameters.getAlgorithm() + ". Supported: GCM");
                }
                try {
                    initAlgorithmSpecificParameters((javax.crypto.spec.GCMParameterSpec) algorithmParameters.getParameterSpec(javax.crypto.spec.GCMParameterSpec.class));
                } catch (java.security.spec.InvalidParameterSpecException e) {
                    if (!isEncrypting()) {
                        throw new java.security.InvalidAlgorithmParameterException("IV and tag length required when decrypting, but not found in parameters: " + algorithmParameters, e);
                    }
                    setIv(null);
                }
            }
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase, javax.crypto.CipherSpi
        protected final java.security.AlgorithmParameters engineGetParameters() {
            byte[] iv = getIv();
            if (iv != null && iv.length > 0) {
                try {
                    java.security.AlgorithmParameters algorithmParameters = java.security.AlgorithmParameters.getInstance(android.security.keystore.KeyProperties.BLOCK_MODE_GCM);
                    algorithmParameters.init(new javax.crypto.spec.GCMParameterSpec(this.mTagLengthBits, iv));
                    return algorithmParameters;
                } catch (java.security.NoSuchAlgorithmException e) {
                    throw new java.security.ProviderException("Failed to obtain GCM AlgorithmParameters", e);
                } catch (java.security.spec.InvalidParameterSpecException e2) {
                    throw new java.security.ProviderException("Failed to initialize GCM AlgorithmParameters", e2);
                }
            }
            return null;
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected android.security.keystore2.KeyStoreCryptoOperationStreamer createMainDataStreamer(android.security.KeyStoreOperation keyStoreOperation) {
            android.security.keystore2.KeyStoreCryptoOperationChunkedStreamer keyStoreCryptoOperationChunkedStreamer = new android.security.keystore2.KeyStoreCryptoOperationChunkedStreamer(new android.security.keystore2.KeyStoreCryptoOperationChunkedStreamer.MainDataStream(keyStoreOperation), 0);
            if (isEncrypting()) {
                return keyStoreCryptoOperationChunkedStreamer;
            }
            return new android.security.keystore2.AndroidKeyStoreAuthenticatedAESCipherSpi.BufferAllOutputUntilDoFinalStreamer(keyStoreCryptoOperationChunkedStreamer);
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final android.security.keystore2.KeyStoreCryptoOperationStreamer createAdditionalAuthenticationDataStreamer(android.security.KeyStoreOperation keyStoreOperation) {
            return new android.security.keystore2.KeyStoreCryptoOperationChunkedStreamer(new android.security.keystore2.AndroidKeyStoreAuthenticatedAESCipherSpi.AdditionalAuthenticationDataStream(keyStoreOperation), 0);
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final int getAdditionalEntropyAmountForBegin() {
            if (getIv() == null && isEncrypting()) {
                return 12;
            }
            return 0;
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final int getAdditionalEntropyAmountForFinish() {
            return 0;
        }

        @Override // android.security.keystore2.AndroidKeyStoreAuthenticatedAESCipherSpi, android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final void addAlgorithmSpecificParametersToBegin(java.util.List<android.hardware.security.keymint.KeyParameter> list) {
            super.addAlgorithmSpecificParametersToBegin(list);
            list.add(android.security.keystore2.KeyStore2ParameterUtils.makeInt(805307371, this.mTagLengthBits));
        }

        protected final int getTagLengthBits() {
            return this.mTagLengthBits;
        }

        public static final class NoPadding extends android.security.keystore2.AndroidKeyStoreAuthenticatedAESCipherSpi.GCM {
            @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
            public /* bridge */ /* synthetic */ void finalize() throws java.lang.Throwable {
                super.finalize();
            }

            public NoPadding() {
                super(1);
            }

            @Override // javax.crypto.CipherSpi
            protected final int engineGetOutputSize(int i) {
                long consumedInputSizeBytes;
                int tagLengthBits = (getTagLengthBits() + 7) / 8;
                if (isEncrypting()) {
                    consumedInputSizeBytes = (getConsumedInputSizeBytes() - getProducedOutputSizeBytes()) + i + tagLengthBits;
                } else {
                    consumedInputSizeBytes = ((getConsumedInputSizeBytes() - getProducedOutputSizeBytes()) + i) - tagLengthBits;
                }
                if (consumedInputSizeBytes < 0) {
                    return 0;
                }
                if (consumedInputSizeBytes > 2147483647L) {
                    return Integer.MAX_VALUE;
                }
                return (int) consumedInputSizeBytes;
            }
        }
    }

    AndroidKeyStoreAuthenticatedAESCipherSpi(int i, int i2) {
        this.mKeymasterBlockMode = i;
        this.mKeymasterPadding = i2;
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected void resetAll() {
        this.mIv = null;
        this.mIvHasBeenUsed = false;
        super.resetAll();
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
    protected void addAlgorithmSpecificParametersToBegin(java.util.List<android.hardware.security.keymint.KeyParameter> list) {
        if (isEncrypting() && this.mIvHasBeenUsed) {
            throw new java.lang.IllegalStateException("IV has already been used. Reusing IV in encryption mode violates security best practices.");
        }
        list.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(268435458, 32));
        list.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536870916, this.mKeymasterBlockMode));
        list.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536870918, this.mKeymasterPadding));
        if (this.mIv != null) {
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
        if (this.mIv == null) {
            this.mIv = bArr;
        } else if (bArr != null && !java.util.Arrays.equals(bArr, this.mIv)) {
            throw new java.security.ProviderException("IV in use differs from provided IV");
        }
    }

    @Override // javax.crypto.CipherSpi
    protected final int engineGetBlockSize() {
        return 16;
    }

    @Override // javax.crypto.CipherSpi
    protected final byte[] engineGetIV() {
        return android.security.keystore.ArrayUtils.cloneIfNotEmpty(this.mIv);
    }

    protected void setIv(byte[] bArr) {
        this.mIv = bArr;
    }

    protected byte[] getIv() {
        return this.mIv;
    }

    private static class BufferAllOutputUntilDoFinalStreamer implements android.security.keystore2.KeyStoreCryptoOperationStreamer {
        private java.io.ByteArrayOutputStream mBufferedOutput;
        private final android.security.keystore2.KeyStoreCryptoOperationStreamer mDelegate;
        private long mProducedOutputSizeBytes;

        private BufferAllOutputUntilDoFinalStreamer(android.security.keystore2.KeyStoreCryptoOperationStreamer keyStoreCryptoOperationStreamer) {
            this.mBufferedOutput = new java.io.ByteArrayOutputStream();
            this.mDelegate = keyStoreCryptoOperationStreamer;
        }

        @Override // android.security.keystore2.KeyStoreCryptoOperationStreamer
        public byte[] update(byte[] bArr, int i, int i2) throws android.security.KeyStoreException {
            byte[] update = this.mDelegate.update(bArr, i, i2);
            if (update != null) {
                try {
                    this.mBufferedOutput.write(update);
                } catch (java.io.IOException e) {
                    throw new java.security.ProviderException("Failed to buffer output", e);
                }
            }
            return libcore.util.EmptyArray.BYTE;
        }

        @Override // android.security.keystore2.KeyStoreCryptoOperationStreamer
        public byte[] doFinal(byte[] bArr, int i, int i2, byte[] bArr2) throws android.security.KeyStoreException {
            byte[] doFinal = this.mDelegate.doFinal(bArr, i, i2, bArr2);
            if (doFinal != null) {
                try {
                    this.mBufferedOutput.write(doFinal);
                } catch (java.io.IOException e) {
                    throw new java.security.ProviderException("Failed to buffer output", e);
                }
            }
            byte[] byteArray = this.mBufferedOutput.toByteArray();
            this.mBufferedOutput.reset();
            this.mProducedOutputSizeBytes += byteArray.length;
            return byteArray;
        }

        @Override // android.security.keystore2.KeyStoreCryptoOperationStreamer
        public long getConsumedInputSizeBytes() {
            return this.mDelegate.getConsumedInputSizeBytes();
        }

        @Override // android.security.keystore2.KeyStoreCryptoOperationStreamer
        public long getProducedOutputSizeBytes() {
            return this.mProducedOutputSizeBytes;
        }
    }

    private static class AdditionalAuthenticationDataStream implements android.security.keystore2.KeyStoreCryptoOperationChunkedStreamer.Stream {
        private final android.security.KeyStoreOperation mOperation;

        private AdditionalAuthenticationDataStream(android.security.KeyStoreOperation keyStoreOperation) {
            this.mOperation = keyStoreOperation;
        }

        @Override // android.security.keystore2.KeyStoreCryptoOperationChunkedStreamer.Stream
        public byte[] update(byte[] bArr) throws android.security.KeyStoreException {
            this.mOperation.updateAad(bArr);
            return null;
        }

        @Override // android.security.keystore2.KeyStoreCryptoOperationChunkedStreamer.Stream
        public byte[] finish(byte[] bArr, byte[] bArr2) {
            return null;
        }
    }
}
