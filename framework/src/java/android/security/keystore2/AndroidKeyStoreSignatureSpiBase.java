package android.security.keystore2;

/* loaded from: classes3.dex */
abstract class AndroidKeyStoreSignatureSpiBase extends java.security.SignatureSpi implements android.security.keystore.KeyStoreCryptoOperation {
    private static final java.lang.String TAG = "AndroidKeyStoreSignatureSpiBase";
    private java.lang.Exception mCachedException;
    private android.security.keystore2.KeyStoreCryptoOperationStreamer mMessageStreamer;
    private java.security.Signature mSignature;
    private android.security.KeyStoreOperation mOperation = null;
    private long mOperationChallenge = 0;
    private boolean mSigning = false;
    private android.security.keystore2.AndroidKeyStoreKey mKey = null;

    protected abstract void addAlgorithmSpecificParametersToBegin(java.util.List<android.hardware.security.keymint.KeyParameter> list);

    protected abstract int getAdditionalEntropyAmountForSign();

    protected abstract java.lang.String getAlgorithm();

    AndroidKeyStoreSignatureSpiBase() {
        this.appRandom = null;
        this.mMessageStreamer = null;
        this.mCachedException = null;
        this.mSignature = null;
    }

    @Override // java.security.SignatureSpi
    protected final void engineInitSign(java.security.PrivateKey privateKey) throws java.security.InvalidKeyException {
        engineInitSign(privateKey, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.security.SignatureSpi
    protected final void engineInitSign(java.security.PrivateKey privateKey, java.security.SecureRandom secureRandom) throws java.security.InvalidKeyException {
        resetAll();
        try {
            if (privateKey == 0) {
                throw new java.security.InvalidKeyException("Unsupported key: null");
            }
            if (!(privateKey instanceof android.security.keystore2.AndroidKeyStorePrivateKey)) {
                throw new java.security.InvalidKeyException("Unsupported private key type: " + privateKey);
            }
            this.mSigning = true;
            initKey((android.security.keystore2.AndroidKeyStoreKey) privateKey);
            this.appRandom = secureRandom;
            ensureKeystoreOperationInitialized();
        } catch (java.lang.Throwable th) {
            resetAll();
            throw th;
        }
    }

    @Override // java.security.SignatureSpi
    protected final void engineInitVerify(java.security.PublicKey publicKey) throws java.security.InvalidKeyException {
        resetAll();
        try {
            this.mSignature = java.security.Signature.getInstance(getAlgorithm());
            this.mSignature.initVerify(publicKey);
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new java.security.InvalidKeyException(e);
        }
    }

    protected void initKey(android.security.keystore2.AndroidKeyStoreKey androidKeyStoreKey) throws java.security.InvalidKeyException {
        this.mKey = androidKeyStoreKey;
    }

    private void abortOperation() {
        android.security.keystore2.KeyStoreCryptoOperationUtils.abortOperation(this.mOperation);
        this.mOperation = null;
    }

    protected void resetAll() {
        abortOperation();
        this.mOperationChallenge = 0L;
        this.mSigning = false;
        this.mKey = null;
        this.appRandom = null;
        this.mMessageStreamer = null;
        this.mCachedException = null;
    }

    protected void resetWhilePreservingInitState() {
        abortOperation();
        this.mOperationChallenge = 0L;
        this.mMessageStreamer = null;
        this.mCachedException = null;
    }

    private void ensureKeystoreOperationInitialized() throws java.security.InvalidKeyException {
        if (this.mMessageStreamer != null || this.mCachedException != null) {
            return;
        }
        if (this.mKey == null) {
            throw new java.lang.IllegalStateException("Not initialized");
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        addAlgorithmSpecificParametersToBegin(arrayList);
        arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536870913, this.mSigning ? 2 : 3));
        try {
            this.mOperation = this.mKey.getSecurityLevel().createOperation(this.mKey.getKeyIdDescriptor(), arrayList);
            this.mOperationChallenge = android.security.keystore2.KeyStoreCryptoOperationUtils.getOrMakeOperationChallenge(this.mOperation, this.mKey);
            this.mMessageStreamer = createMainDataStreamer(this.mOperation);
        } catch (android.security.KeyStoreException e) {
            throw android.security.keystore2.KeyStoreCryptoOperationUtils.getInvalidKeyException(this.mKey, e);
        }
    }

    protected android.security.keystore2.KeyStoreCryptoOperationStreamer createMainDataStreamer(android.security.KeyStoreOperation keyStoreOperation) {
        return new android.security.keystore2.KeyStoreCryptoOperationChunkedStreamer(new android.security.keystore2.KeyStoreCryptoOperationChunkedStreamer.MainDataStream(keyStoreOperation));
    }

    @Override // android.security.keystore.KeyStoreCryptoOperation
    public final long getOperationHandle() {
        return this.mOperationChallenge;
    }

    @Override // java.security.SignatureSpi
    protected final void engineUpdate(byte[] bArr, int i, int i2) throws java.security.SignatureException {
        if (this.mSignature != null) {
            this.mSignature.update(bArr, i, i2);
            return;
        }
        if (this.mCachedException != null) {
            throw new java.security.SignatureException(this.mCachedException);
        }
        try {
            ensureKeystoreOperationInitialized();
            if (i2 == 0) {
                return;
            }
            try {
                byte[] update = this.mMessageStreamer.update(bArr, i, i2);
                if (update.length != 0) {
                    throw new java.security.ProviderException("Update operation unexpectedly produced output: " + update.length + " bytes");
                }
            } catch (android.security.KeyStoreException e) {
                throw new java.security.SignatureException(e);
            }
        } catch (java.security.InvalidKeyException e2) {
            throw new java.security.SignatureException(e2);
        }
    }

    @Override // java.security.SignatureSpi
    protected final void engineUpdate(byte b) throws java.security.SignatureException {
        engineUpdate(new byte[]{b}, 0, 1);
    }

    @Override // java.security.SignatureSpi
    protected final void engineUpdate(java.nio.ByteBuffer byteBuffer) {
        byte[] bArr;
        int i;
        int remaining = byteBuffer.remaining();
        if (byteBuffer.hasArray()) {
            bArr = byteBuffer.array();
            i = byteBuffer.arrayOffset() + byteBuffer.position();
            byteBuffer.position(byteBuffer.limit());
        } else {
            bArr = new byte[remaining];
            byteBuffer.get(bArr);
            i = 0;
        }
        try {
            engineUpdate(bArr, i, remaining);
        } catch (java.security.SignatureException e) {
            this.mCachedException = e;
        }
    }

    @Override // java.security.SignatureSpi
    protected final int engineSign(byte[] bArr, int i, int i2) throws java.security.SignatureException {
        return super.engineSign(bArr, i, i2);
    }

    @Override // java.security.SignatureSpi
    protected final byte[] engineSign() throws java.security.SignatureException {
        if (this.mCachedException != null) {
            throw new java.security.SignatureException(this.mCachedException);
        }
        try {
            ensureKeystoreOperationInitialized();
            android.security.keystore2.KeyStoreCryptoOperationUtils.getRandomBytesToMixIntoKeystoreRng(this.appRandom, getAdditionalEntropyAmountForSign());
            byte[] doFinal = this.mMessageStreamer.doFinal(libcore.util.EmptyArray.BYTE, 0, 0, null);
            resetWhilePreservingInitState();
            return doFinal;
        } catch (android.security.KeyStoreException | java.security.InvalidKeyException e) {
            throw new java.security.SignatureException(e);
        }
    }

    @Override // java.security.SignatureSpi
    protected final boolean engineVerify(byte[] bArr) throws java.security.SignatureException {
        if (this.mSignature != null) {
            return this.mSignature.verify(bArr);
        }
        throw new java.lang.IllegalStateException("Not initialised.");
    }

    @Override // java.security.SignatureSpi
    protected final boolean engineVerify(byte[] bArr, int i, int i2) throws java.security.SignatureException {
        return engineVerify(android.security.keystore.ArrayUtils.subarray(bArr, i, i2));
    }

    @Override // java.security.SignatureSpi
    @java.lang.Deprecated
    protected final java.lang.Object engineGetParameter(java.lang.String str) throws java.security.InvalidParameterException {
        throw new java.security.InvalidParameterException();
    }

    @Override // java.security.SignatureSpi
    @java.lang.Deprecated
    protected final void engineSetParameter(java.lang.String str, java.lang.Object obj) throws java.security.InvalidParameterException {
        throw new java.security.InvalidParameterException();
    }

    protected final boolean isSigning() {
        return this.mSigning;
    }
}
