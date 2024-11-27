package android.security.keystore2;

/* loaded from: classes3.dex */
abstract class AndroidKeyStoreCipherSpiBase extends javax.crypto.CipherSpi implements android.security.keystore.KeyStoreCryptoOperation {
    public static final java.lang.String DEFAULT_MGF1_DIGEST = "SHA-1";
    private static final java.lang.String TAG = "AndroidKeyStoreCipherSpiBase";
    private int mKeymasterPurposeOverride;
    private android.security.KeyStoreOperation mOperation = null;
    private boolean mEncrypting = false;
    private android.security.keystore2.AndroidKeyStoreKey mKey = null;
    private java.security.SecureRandom mRng = null;
    private long mOperationChallenge = 0;
    private android.security.keystore2.KeyStoreCryptoOperationStreamer mMainDataStreamer = null;
    private android.security.keystore2.KeyStoreCryptoOperationStreamer mAdditionalAuthenticationDataStreamer = null;
    private boolean mAdditionalAuthenticationDataStreamerClosed = false;
    private java.lang.Exception mCachedException = null;
    private javax.crypto.Cipher mCipher = null;

    protected abstract void addAlgorithmSpecificParametersToBegin(java.util.List<android.hardware.security.keymint.KeyParameter> list);

    @Override // javax.crypto.CipherSpi
    protected abstract java.security.AlgorithmParameters engineGetParameters();

    protected abstract int getAdditionalEntropyAmountForBegin();

    protected abstract int getAdditionalEntropyAmountForFinish();

    protected abstract java.lang.String getTransform();

    protected abstract void initAlgorithmSpecificParameters() throws java.security.InvalidKeyException;

    protected abstract void initAlgorithmSpecificParameters(java.security.AlgorithmParameters algorithmParameters) throws java.security.InvalidAlgorithmParameterException;

    protected abstract void initAlgorithmSpecificParameters(java.security.spec.AlgorithmParameterSpec algorithmParameterSpec) throws java.security.InvalidAlgorithmParameterException;

    protected abstract void initKey(int i, java.security.Key key) throws java.security.InvalidKeyException;

    protected abstract void loadAlgorithmSpecificParametersFromBeginResult(android.hardware.security.keymint.KeyParameter[] keyParameterArr);

    AndroidKeyStoreCipherSpiBase() {
        this.mKeymasterPurposeOverride = -1;
        this.mKeymasterPurposeOverride = -1;
    }

    private android.system.keystore2.Authorization[] getKeyCharacteristics(java.security.Key key) {
        if (!(key instanceof android.security.keystore2.AndroidKeyStoreKey)) {
            return new android.system.keystore2.Authorization[0];
        }
        return ((android.security.keystore2.AndroidKeyStoreKey) key).getAuthorizations();
    }

    @Override // javax.crypto.CipherSpi
    protected final void engineInit(int i, java.security.Key key, java.security.SecureRandom secureRandom) throws java.security.InvalidKeyException {
        resetAll();
        if (!(key instanceof android.security.keystore2.AndroidKeyStorePrivateKey) && ((key instanceof java.security.PrivateKey) || (key instanceof java.security.PublicKey))) {
            try {
                android.os.StrictMode.noteSlowCall("engineInit");
                this.mCipher = javax.crypto.Cipher.getInstance(getTransform());
                java.lang.String transform = getTransform();
                if ("RSA/ECB/OAEPWithSHA-224AndMGF1Padding".equals(transform)) {
                    this.mCipher.init(i, key, new javax.crypto.spec.OAEPParameterSpec(android.security.keystore.KeyProperties.DIGEST_SHA224, "MGF1", new java.security.spec.MGF1ParameterSpec("SHA-1"), javax.crypto.spec.PSource.PSpecified.DEFAULT), secureRandom);
                    return;
                }
                if ("RSA/ECB/OAEPWithSHA-256AndMGF1Padding".equals(transform)) {
                    this.mCipher.init(i, key, new javax.crypto.spec.OAEPParameterSpec("SHA-256", "MGF1", new java.security.spec.MGF1ParameterSpec("SHA-1"), javax.crypto.spec.PSource.PSpecified.DEFAULT), secureRandom);
                    return;
                } else if ("RSA/ECB/OAEPWithSHA-384AndMGF1Padding".equals(transform)) {
                    this.mCipher.init(i, key, new javax.crypto.spec.OAEPParameterSpec(android.security.keystore.KeyProperties.DIGEST_SHA384, "MGF1", new java.security.spec.MGF1ParameterSpec("SHA-1"), javax.crypto.spec.PSource.PSpecified.DEFAULT), secureRandom);
                    return;
                } else if ("RSA/ECB/OAEPWithSHA-512AndMGF1Padding".equals(transform)) {
                    this.mCipher.init(i, key, new javax.crypto.spec.OAEPParameterSpec(android.security.keystore.KeyProperties.DIGEST_SHA512, "MGF1", new java.security.spec.MGF1ParameterSpec("SHA-1"), javax.crypto.spec.PSource.PSpecified.DEFAULT), secureRandom);
                    return;
                } else {
                    this.mCipher.init(i, key, secureRandom);
                    return;
                }
            } catch (java.security.InvalidAlgorithmParameterException | java.security.NoSuchAlgorithmException | javax.crypto.NoSuchPaddingException e) {
                throw new java.security.InvalidKeyException(e);
            }
        }
        try {
            init(i, key, secureRandom);
            initAlgorithmSpecificParameters();
            try {
                ensureKeystoreOperationInitialized(getKeyCharacteristics(key));
            } catch (java.security.InvalidAlgorithmParameterException e2) {
                throw new java.security.InvalidKeyException(e2);
            }
        } catch (java.lang.Throwable th) {
            resetAll();
            throw th;
        }
    }

    @Override // javax.crypto.CipherSpi
    protected final void engineInit(int i, java.security.Key key, java.security.AlgorithmParameters algorithmParameters, java.security.SecureRandom secureRandom) throws java.security.InvalidKeyException, java.security.InvalidAlgorithmParameterException {
        resetAll();
        if (!(key instanceof android.security.keystore2.AndroidKeyStorePrivateKey) && ((key instanceof java.security.PrivateKey) || (key instanceof java.security.PublicKey))) {
            try {
                android.os.StrictMode.noteSlowCall("engineInit");
                this.mCipher = javax.crypto.Cipher.getInstance(getTransform());
                this.mCipher.init(i, key, algorithmParameters, secureRandom);
                return;
            } catch (java.security.NoSuchAlgorithmException | javax.crypto.NoSuchPaddingException e) {
                throw new java.security.InvalidKeyException(e);
            }
        }
        try {
            init(i, key, secureRandom);
            initAlgorithmSpecificParameters(algorithmParameters);
            ensureKeystoreOperationInitialized(getKeyCharacteristics(key));
        } catch (java.lang.Throwable th) {
            resetAll();
            throw th;
        }
    }

    @Override // javax.crypto.CipherSpi
    protected final void engineInit(int i, java.security.Key key, java.security.spec.AlgorithmParameterSpec algorithmParameterSpec, java.security.SecureRandom secureRandom) throws java.security.InvalidKeyException, java.security.InvalidAlgorithmParameterException {
        resetAll();
        if (!(key instanceof android.security.keystore2.AndroidKeyStorePrivateKey) && ((key instanceof java.security.PrivateKey) || (key instanceof java.security.PublicKey))) {
            try {
                android.os.StrictMode.noteSlowCall("engineInit");
                this.mCipher = javax.crypto.Cipher.getInstance(getTransform());
                this.mCipher.init(i, key, algorithmParameterSpec, secureRandom);
                return;
            } catch (java.security.NoSuchAlgorithmException | javax.crypto.NoSuchPaddingException e) {
                throw new java.security.InvalidKeyException(e);
            }
        }
        try {
            init(i, key, secureRandom);
            initAlgorithmSpecificParameters(algorithmParameterSpec);
            ensureKeystoreOperationInitialized(getKeyCharacteristics(key));
        } catch (java.lang.Throwable th) {
            resetAll();
            throw th;
        }
    }

    private void init(int i, java.security.Key key, java.security.SecureRandom secureRandom) throws java.security.InvalidKeyException {
        switch (i) {
            case 1:
            case 3:
                this.mEncrypting = true;
                break;
            case 2:
            case 4:
                this.mEncrypting = false;
                break;
            default:
                throw new java.security.InvalidParameterException("Unsupported opmode: " + i);
        }
        initKey(i, key);
        if (this.mKey == null) {
            throw new java.security.ProviderException("initKey did not initialize the key");
        }
        this.mRng = secureRandom;
    }

    private void abortOperation() {
        android.security.keystore2.KeyStoreCryptoOperationUtils.abortOperation(this.mOperation);
        this.mOperation = null;
    }

    protected void resetAll() {
        abortOperation();
        this.mEncrypting = false;
        this.mKeymasterPurposeOverride = -1;
        this.mKey = null;
        this.mRng = null;
        this.mOperationChallenge = 0L;
        this.mMainDataStreamer = null;
        this.mAdditionalAuthenticationDataStreamer = null;
        this.mAdditionalAuthenticationDataStreamerClosed = false;
        this.mCachedException = null;
        this.mCipher = null;
    }

    protected void resetWhilePreservingInitState() {
        abortOperation();
        this.mOperationChallenge = 0L;
        this.mMainDataStreamer = null;
        this.mAdditionalAuthenticationDataStreamer = null;
        this.mAdditionalAuthenticationDataStreamerClosed = false;
        this.mCachedException = null;
    }

    private void ensureKeystoreOperationInitialized(android.system.keystore2.Authorization[] authorizationArr) throws java.security.InvalidKeyException, java.security.InvalidAlgorithmParameterException {
        int i;
        if (this.mMainDataStreamer != null || this.mCachedException != null) {
            return;
        }
        if (this.mKey == null) {
            throw new java.lang.IllegalStateException("Not initialized");
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        addAlgorithmSpecificParametersToBegin(arrayList, authorizationArr);
        if (this.mKeymasterPurposeOverride != -1) {
            i = this.mKeymasterPurposeOverride;
        } else {
            i = this.mEncrypting ? 0 : 1;
        }
        arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536870913, i));
        try {
            android.os.StrictMode.noteDiskRead();
            this.mOperation = this.mKey.getSecurityLevel().createOperation(this.mKey.getKeyIdDescriptor(), arrayList);
        } catch (android.security.KeyStoreException e) {
            java.security.GeneralSecurityException exceptionForCipherInit = android.security.keystore2.KeyStoreCryptoOperationUtils.getExceptionForCipherInit(this.mKey, e);
            if (exceptionForCipherInit != null) {
                if (exceptionForCipherInit instanceof java.security.InvalidKeyException) {
                    throw ((java.security.InvalidKeyException) exceptionForCipherInit);
                }
                if (exceptionForCipherInit instanceof java.security.InvalidAlgorithmParameterException) {
                    throw ((java.security.InvalidAlgorithmParameterException) exceptionForCipherInit);
                }
                throw new java.security.ProviderException("Unexpected exception type", exceptionForCipherInit);
            }
        }
        this.mOperationChallenge = android.security.keystore2.KeyStoreCryptoOperationUtils.getOrMakeOperationChallenge(this.mOperation, this.mKey);
        loadAlgorithmSpecificParametersFromBeginResult(this.mOperation.getParameters());
        this.mMainDataStreamer = createMainDataStreamer(this.mOperation);
        this.mAdditionalAuthenticationDataStreamer = createAdditionalAuthenticationDataStreamer(this.mOperation);
        this.mAdditionalAuthenticationDataStreamerClosed = false;
    }

    protected android.security.keystore2.KeyStoreCryptoOperationStreamer createMainDataStreamer(android.security.KeyStoreOperation keyStoreOperation) {
        return new android.security.keystore2.KeyStoreCryptoOperationChunkedStreamer(new android.security.keystore2.KeyStoreCryptoOperationChunkedStreamer.MainDataStream(keyStoreOperation), 0);
    }

    protected android.security.keystore2.KeyStoreCryptoOperationStreamer createAdditionalAuthenticationDataStreamer(android.security.KeyStoreOperation keyStoreOperation) {
        return null;
    }

    @Override // javax.crypto.CipherSpi
    protected final byte[] engineUpdate(byte[] bArr, int i, int i2) {
        if (this.mCipher != null) {
            return this.mCipher.update(bArr, i, i2);
        }
        if (this.mCachedException != null) {
            return null;
        }
        try {
            ensureKeystoreOperationInitialized(getKeyCharacteristics(this.mKey));
            if (i2 == 0) {
                return null;
            }
            try {
                flushAAD();
                byte[] update = this.mMainDataStreamer.update(bArr, i, i2);
                if (update.length == 0) {
                    return null;
                }
                return update;
            } catch (android.security.KeyStoreException e) {
                this.mCachedException = e;
                return null;
            }
        } catch (java.security.InvalidAlgorithmParameterException | java.security.InvalidKeyException e2) {
            this.mCachedException = e2;
            return null;
        }
    }

    private void flushAAD() throws android.security.KeyStoreException {
        if (this.mAdditionalAuthenticationDataStreamer != null && !this.mAdditionalAuthenticationDataStreamerClosed) {
            try {
                byte[] doFinal = this.mAdditionalAuthenticationDataStreamer.doFinal(libcore.util.EmptyArray.BYTE, 0, 0, null);
                if (doFinal != null && doFinal.length > 0) {
                    throw new java.security.ProviderException("AAD update unexpectedly returned data: " + doFinal.length + " bytes");
                }
            } finally {
                this.mAdditionalAuthenticationDataStreamerClosed = true;
            }
        }
    }

    @Override // javax.crypto.CipherSpi
    protected final int engineUpdate(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws javax.crypto.ShortBufferException {
        if (this.mCipher != null) {
            return this.mCipher.update(bArr, i, i2, bArr2);
        }
        byte[] engineUpdate = engineUpdate(bArr, i, i2);
        if (engineUpdate == null) {
            return 0;
        }
        int length = bArr2.length - i3;
        if (engineUpdate.length <= length) {
            java.lang.System.arraycopy(engineUpdate, 0, bArr2, i3, engineUpdate.length);
            return engineUpdate.length;
        }
        throw new javax.crypto.ShortBufferException("Output buffer too short. Produced: " + engineUpdate.length + ", available: " + length);
    }

    @Override // javax.crypto.CipherSpi
    protected final int engineUpdate(java.nio.ByteBuffer byteBuffer, java.nio.ByteBuffer byteBuffer2) throws javax.crypto.ShortBufferException {
        byte[] engineUpdate;
        if (this.mCipher != null) {
            return this.mCipher.update(byteBuffer, byteBuffer2);
        }
        if (byteBuffer == null) {
            throw new java.lang.NullPointerException("input == null");
        }
        if (byteBuffer2 == null) {
            throw new java.lang.NullPointerException("output == null");
        }
        int remaining = byteBuffer.remaining();
        if (byteBuffer.hasArray()) {
            engineUpdate = engineUpdate(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), remaining);
            byteBuffer.position(byteBuffer.position() + remaining);
        } else {
            byte[] bArr = new byte[remaining];
            byteBuffer.get(bArr);
            engineUpdate = engineUpdate(bArr, 0, remaining);
        }
        int length = engineUpdate != null ? engineUpdate.length : 0;
        if (length > 0) {
            int remaining2 = byteBuffer2.remaining();
            try {
                byteBuffer2.put(engineUpdate);
            } catch (java.nio.BufferOverflowException e) {
                throw new javax.crypto.ShortBufferException("Output buffer too small. Produced: " + length + ", available: " + remaining2);
            }
        }
        return length;
    }

    @Override // javax.crypto.CipherSpi
    protected final void engineUpdateAAD(byte[] bArr, int i, int i2) {
        if (this.mCipher != null) {
            android.os.StrictMode.noteSlowCall("engineUpdateAAD");
            this.mCipher.updateAAD(bArr, i, i2);
            return;
        }
        if (this.mCachedException != null) {
            return;
        }
        try {
            ensureKeystoreOperationInitialized(getKeyCharacteristics(this.mKey));
            if (this.mAdditionalAuthenticationDataStreamerClosed) {
                throw new java.lang.IllegalStateException("AAD can only be provided before Cipher.update is invoked");
            }
            if (this.mAdditionalAuthenticationDataStreamer == null) {
                throw new java.lang.IllegalStateException("This cipher does not support AAD");
            }
            try {
                byte[] update = this.mAdditionalAuthenticationDataStreamer.update(bArr, i, i2);
                if (update != null && update.length > 0) {
                    throw new java.security.ProviderException("AAD update unexpectedly produced output: " + update.length + " bytes");
                }
            } catch (android.security.KeyStoreException e) {
                this.mCachedException = e;
            }
        } catch (java.security.InvalidAlgorithmParameterException | java.security.InvalidKeyException e2) {
            this.mCachedException = e2;
        }
    }

    @Override // javax.crypto.CipherSpi
    protected final void engineUpdateAAD(java.nio.ByteBuffer byteBuffer) {
        int remaining;
        byte[] bArr;
        int i;
        if (this.mCipher != null) {
            android.os.StrictMode.noteSlowCall("engineUpdateAAD");
            this.mCipher.updateAAD(byteBuffer);
            return;
        }
        if (byteBuffer == null) {
            throw new java.lang.IllegalArgumentException("src == null");
        }
        if (!byteBuffer.hasRemaining()) {
            return;
        }
        if (byteBuffer.hasArray()) {
            bArr = byteBuffer.array();
            i = byteBuffer.arrayOffset() + byteBuffer.position();
            remaining = byteBuffer.remaining();
            byteBuffer.position(byteBuffer.limit());
        } else {
            remaining = byteBuffer.remaining();
            bArr = new byte[remaining];
            byteBuffer.get(bArr);
            i = 0;
        }
        engineUpdateAAD(bArr, i, remaining);
    }

    @Override // javax.crypto.CipherSpi
    protected final byte[] engineDoFinal(byte[] bArr, int i, int i2) throws javax.crypto.IllegalBlockSizeException, javax.crypto.BadPaddingException {
        if (this.mCipher != null) {
            if (bArr == null && i2 == 0) {
                return this.mCipher.doFinal();
            }
            return this.mCipher.doFinal(bArr, i, i2);
        }
        if (this.mCachedException != null) {
            throw ((javax.crypto.IllegalBlockSizeException) new javax.crypto.IllegalBlockSizeException().initCause(this.mCachedException));
        }
        try {
            ensureKeystoreOperationInitialized(getKeyCharacteristics(this.mKey));
            try {
                flushAAD();
                byte[] doFinal = this.mMainDataStreamer.doFinal(bArr, i, i2, null);
                resetWhilePreservingInitState();
                return doFinal;
            } catch (android.security.KeyStoreException e) {
                switch (e.getErrorCode()) {
                    case -38:
                        throw ((javax.crypto.BadPaddingException) new javax.crypto.BadPaddingException().initCause(e));
                    case -30:
                        throw ((javax.crypto.AEADBadTagException) new javax.crypto.AEADBadTagException().initCause(e));
                    default:
                        throw ((javax.crypto.IllegalBlockSizeException) new javax.crypto.IllegalBlockSizeException().initCause(e));
                }
            }
        } catch (java.security.InvalidAlgorithmParameterException | java.security.InvalidKeyException e2) {
            throw ((javax.crypto.IllegalBlockSizeException) new javax.crypto.IllegalBlockSizeException().initCause(e2));
        }
    }

    @Override // javax.crypto.CipherSpi
    protected final int engineDoFinal(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws javax.crypto.ShortBufferException, javax.crypto.IllegalBlockSizeException, javax.crypto.BadPaddingException {
        if (this.mCipher != null) {
            return this.mCipher.doFinal(bArr, i, i2, bArr2);
        }
        byte[] engineDoFinal = engineDoFinal(bArr, i, i2);
        if (engineDoFinal == null) {
            return 0;
        }
        int length = bArr2.length - i3;
        if (engineDoFinal.length <= length) {
            java.lang.System.arraycopy(engineDoFinal, 0, bArr2, i3, engineDoFinal.length);
            return engineDoFinal.length;
        }
        throw new javax.crypto.ShortBufferException("Output buffer too short. Produced: " + engineDoFinal.length + ", available: " + length);
    }

    @Override // javax.crypto.CipherSpi
    protected final int engineDoFinal(java.nio.ByteBuffer byteBuffer, java.nio.ByteBuffer byteBuffer2) throws javax.crypto.ShortBufferException, javax.crypto.IllegalBlockSizeException, javax.crypto.BadPaddingException {
        byte[] engineDoFinal;
        if (this.mCipher != null) {
            return this.mCipher.doFinal(byteBuffer, byteBuffer2);
        }
        if (byteBuffer == null) {
            throw new java.lang.NullPointerException("input == null");
        }
        if (byteBuffer2 == null) {
            throw new java.lang.NullPointerException("output == null");
        }
        int remaining = byteBuffer.remaining();
        if (byteBuffer.hasArray()) {
            engineDoFinal = engineDoFinal(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), remaining);
            byteBuffer.position(byteBuffer.position() + remaining);
        } else {
            byte[] bArr = new byte[remaining];
            byteBuffer.get(bArr);
            engineDoFinal = engineDoFinal(bArr, 0, remaining);
        }
        int length = engineDoFinal != null ? engineDoFinal.length : 0;
        if (length > 0) {
            int remaining2 = byteBuffer2.remaining();
            try {
                byteBuffer2.put(engineDoFinal);
            } catch (java.nio.BufferOverflowException e) {
                throw new javax.crypto.ShortBufferException("Output buffer too small. Produced: " + length + ", available: " + remaining2);
            }
        }
        return length;
    }

    @Override // javax.crypto.CipherSpi
    protected final byte[] engineWrap(java.security.Key key) throws javax.crypto.IllegalBlockSizeException, java.security.InvalidKeyException {
        byte[] encoded;
        if (this.mCipher != null) {
            return this.mCipher.wrap(key);
        }
        if (this.mKey == null) {
            throw new java.lang.IllegalStateException("Not initilized");
        }
        if (!isEncrypting()) {
            throw new java.lang.IllegalStateException("Cipher must be initialized in Cipher.WRAP_MODE to wrap keys");
        }
        if (key == null) {
            throw new java.lang.NullPointerException("key == null");
        }
        android.os.StrictMode.noteSlowCall("engineWrap");
        if (key instanceof javax.crypto.SecretKey) {
            encoded = "RAW".equalsIgnoreCase(key.getFormat()) ? key.getEncoded() : null;
            if (encoded == null) {
                try {
                    encoded = ((javax.crypto.spec.SecretKeySpec) javax.crypto.SecretKeyFactory.getInstance(key.getAlgorithm()).getKeySpec((javax.crypto.SecretKey) key, javax.crypto.spec.SecretKeySpec.class)).getEncoded();
                } catch (java.security.NoSuchAlgorithmException | java.security.spec.InvalidKeySpecException e) {
                    throw new java.security.InvalidKeyException("Failed to wrap key because it does not export its key material", e);
                }
            }
        } else if (key instanceof java.security.PrivateKey) {
            encoded = "PKCS8".equalsIgnoreCase(key.getFormat()) ? key.getEncoded() : null;
            if (encoded == null) {
                try {
                    encoded = ((java.security.spec.PKCS8EncodedKeySpec) java.security.KeyFactory.getInstance(key.getAlgorithm()).getKeySpec(key, java.security.spec.PKCS8EncodedKeySpec.class)).getEncoded();
                } catch (java.security.NoSuchAlgorithmException | java.security.spec.InvalidKeySpecException e2) {
                    throw new java.security.InvalidKeyException("Failed to wrap key because it does not export its key material", e2);
                }
            }
        } else if (key instanceof java.security.PublicKey) {
            encoded = "X.509".equalsIgnoreCase(key.getFormat()) ? key.getEncoded() : null;
            if (encoded == null) {
                try {
                    encoded = ((java.security.spec.X509EncodedKeySpec) java.security.KeyFactory.getInstance(key.getAlgorithm()).getKeySpec(key, java.security.spec.X509EncodedKeySpec.class)).getEncoded();
                } catch (java.security.NoSuchAlgorithmException | java.security.spec.InvalidKeySpecException e3) {
                    throw new java.security.InvalidKeyException("Failed to wrap key because it does not export its key material", e3);
                }
            }
        } else {
            throw new java.security.InvalidKeyException("Unsupported key type: " + key.getClass().getName());
        }
        if (encoded == null) {
            throw new java.security.InvalidKeyException("Failed to wrap key because it does not export its key material");
        }
        try {
            return engineDoFinal(encoded, 0, encoded.length);
        } catch (javax.crypto.BadPaddingException e4) {
            throw ((javax.crypto.IllegalBlockSizeException) new javax.crypto.IllegalBlockSizeException().initCause(e4));
        }
    }

    @Override // javax.crypto.CipherSpi
    protected final java.security.Key engineUnwrap(byte[] bArr, java.lang.String str, int i) throws java.security.InvalidKeyException, java.security.NoSuchAlgorithmException {
        if (this.mCipher != null) {
            return this.mCipher.unwrap(bArr, str, i);
        }
        if (this.mKey == null) {
            throw new java.lang.IllegalStateException("Not initilized");
        }
        if (isEncrypting()) {
            throw new java.lang.IllegalStateException("Cipher must be initialized in Cipher.WRAP_MODE to wrap keys");
        }
        if (bArr == null) {
            throw new java.lang.NullPointerException("wrappedKey == null");
        }
        try {
            byte[] engineDoFinal = engineDoFinal(bArr, 0, bArr.length);
            android.os.StrictMode.noteSlowCall("engineUnwrap");
            switch (i) {
                case 1:
                    try {
                        return java.security.KeyFactory.getInstance(str).generatePublic(new java.security.spec.X509EncodedKeySpec(engineDoFinal));
                    } catch (java.security.spec.InvalidKeySpecException e) {
                        throw new java.security.InvalidKeyException("Failed to create public key from its X.509 encoded form", e);
                    }
                case 2:
                    try {
                        return java.security.KeyFactory.getInstance(str).generatePrivate(new java.security.spec.PKCS8EncodedKeySpec(engineDoFinal));
                    } catch (java.security.spec.InvalidKeySpecException e2) {
                        throw new java.security.InvalidKeyException("Failed to create private key from its PKCS#8 encoded form", e2);
                    }
                case 3:
                    return new javax.crypto.spec.SecretKeySpec(engineDoFinal, str);
                default:
                    throw new java.security.InvalidParameterException("Unsupported wrappedKeyType: " + i);
            }
        } catch (javax.crypto.BadPaddingException | javax.crypto.IllegalBlockSizeException e3) {
            throw new java.security.InvalidKeyException("Failed to unwrap key", e3);
        }
    }

    @Override // javax.crypto.CipherSpi
    protected final void engineSetMode(java.lang.String str) throws java.security.NoSuchAlgorithmException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // javax.crypto.CipherSpi
    protected final void engineSetPadding(java.lang.String str) throws javax.crypto.NoSuchPaddingException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // javax.crypto.CipherSpi
    protected final int engineGetKeySize(java.security.Key key) throws java.security.InvalidKeyException {
        throw new java.lang.UnsupportedOperationException();
    }

    public void finalize() throws java.lang.Throwable {
        try {
            abortOperation();
        } finally {
            super.finalize();
        }
    }

    @Override // android.security.keystore.KeyStoreCryptoOperation
    public final long getOperationHandle() {
        return this.mOperationChallenge;
    }

    protected final void setKey(android.security.keystore2.AndroidKeyStoreKey androidKeyStoreKey) {
        this.mKey = androidKeyStoreKey;
    }

    protected final void setKeymasterPurposeOverride(int i) {
        this.mKeymasterPurposeOverride = i;
    }

    protected final int getKeymasterPurposeOverride() {
        return this.mKeymasterPurposeOverride;
    }

    protected final boolean isEncrypting() {
        return this.mEncrypting;
    }

    protected final long getConsumedInputSizeBytes() {
        if (this.mMainDataStreamer == null) {
            throw new java.lang.IllegalStateException("Not initialized");
        }
        return this.mMainDataStreamer.getConsumedInputSizeBytes();
    }

    protected final long getProducedOutputSizeBytes() {
        if (this.mMainDataStreamer == null) {
            throw new java.lang.IllegalStateException("Not initialized");
        }
        return this.mMainDataStreamer.getProducedOutputSizeBytes();
    }

    static java.lang.String opmodeToString(int i) {
        switch (i) {
            case 1:
                return "ENCRYPT_MODE";
            case 2:
                return "DECRYPT_MODE";
            case 3:
                return "WRAP_MODE";
            case 4:
                return "UNWRAP_MODE";
            default:
                return java.lang.String.valueOf(i);
        }
    }

    protected void addAlgorithmSpecificParametersToBegin(java.util.List<android.hardware.security.keymint.KeyParameter> list, android.system.keystore2.Authorization[] authorizationArr) {
        addAlgorithmSpecificParametersToBegin(list);
    }
}
