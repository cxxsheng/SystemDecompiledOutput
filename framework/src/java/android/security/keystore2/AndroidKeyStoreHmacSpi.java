package android.security.keystore2;

/* loaded from: classes3.dex */
public abstract class AndroidKeyStoreHmacSpi extends javax.crypto.MacSpi implements android.security.keystore.KeyStoreCryptoOperation {
    private static final java.lang.String TAG = "AndroidKeyStoreHmacSpi";
    private final int mKeymasterDigest;
    private final int mMacSizeBits;
    private android.security.KeyStoreOperation mOperation = null;
    private long mOperationChallenge = 0;
    private android.security.keystore2.AndroidKeyStoreSecretKey mKey = null;
    private android.security.keystore2.KeyStoreCryptoOperationChunkedStreamer mChunkedStreamer = null;

    public static class HmacSHA1 extends android.security.keystore2.AndroidKeyStoreHmacSpi {
        public HmacSHA1() {
            super(2);
        }
    }

    public static class HmacSHA224 extends android.security.keystore2.AndroidKeyStoreHmacSpi {
        public HmacSHA224() {
            super(3);
        }
    }

    public static class HmacSHA256 extends android.security.keystore2.AndroidKeyStoreHmacSpi {
        public HmacSHA256() {
            super(4);
        }
    }

    public static class HmacSHA384 extends android.security.keystore2.AndroidKeyStoreHmacSpi {
        public HmacSHA384() {
            super(5);
        }
    }

    public static class HmacSHA512 extends android.security.keystore2.AndroidKeyStoreHmacSpi {
        public HmacSHA512() {
            super(6);
        }
    }

    protected AndroidKeyStoreHmacSpi(int i) {
        this.mKeymasterDigest = i;
        this.mMacSizeBits = android.security.keystore2.KeymasterUtils.getDigestOutputSizeBits(i);
    }

    @Override // javax.crypto.MacSpi
    protected int engineGetMacLength() {
        return (this.mMacSizeBits + 7) / 8;
    }

    @Override // javax.crypto.MacSpi
    protected void engineInit(java.security.Key key, java.security.spec.AlgorithmParameterSpec algorithmParameterSpec) throws java.security.InvalidKeyException, java.security.InvalidAlgorithmParameterException {
        try {
            init(key, algorithmParameterSpec);
            ensureKeystoreOperationInitialized();
        } finally {
            resetAll();
        }
    }

    private void init(java.security.Key key, java.security.spec.AlgorithmParameterSpec algorithmParameterSpec) throws java.security.InvalidKeyException, java.security.InvalidAlgorithmParameterException {
        if (key == null) {
            throw new java.security.InvalidKeyException("key == null");
        }
        if (!(key instanceof android.security.keystore2.AndroidKeyStoreSecretKey)) {
            throw new java.security.InvalidKeyException("Only Android KeyStore secret keys supported. Key: " + key);
        }
        this.mKey = (android.security.keystore2.AndroidKeyStoreSecretKey) key;
        if (algorithmParameterSpec != null) {
            throw new java.security.InvalidAlgorithmParameterException("Unsupported algorithm parameters: " + algorithmParameterSpec);
        }
    }

    private void abortOperation() {
        android.security.keystore2.KeyStoreCryptoOperationUtils.abortOperation(this.mOperation);
        this.mOperation = null;
    }

    private void resetAll() {
        abortOperation();
        this.mOperationChallenge = 0L;
        this.mKey = null;
        this.mChunkedStreamer = null;
    }

    private void resetWhilePreservingInitState() {
        abortOperation();
        this.mOperationChallenge = 0L;
        this.mChunkedStreamer = null;
    }

    @Override // javax.crypto.MacSpi
    protected void engineReset() {
        resetWhilePreservingInitState();
    }

    private void ensureKeystoreOperationInitialized() throws java.security.InvalidKeyException {
        if (this.mChunkedStreamer != null) {
            return;
        }
        if (this.mKey == null) {
            throw new java.lang.IllegalStateException("Not initialized");
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536870913, 2));
        arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(268435458, 128));
        arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536870917, this.mKeymasterDigest));
        arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeInt(805307371, this.mMacSizeBits));
        try {
            this.mOperation = this.mKey.getSecurityLevel().createOperation(this.mKey.getKeyIdDescriptor(), arrayList);
        } catch (android.security.KeyStoreException e) {
            java.security.InvalidKeyException invalidKeyException = android.security.keystore2.KeyStoreCryptoOperationUtils.getInvalidKeyException(this.mKey, e);
            if (invalidKeyException != null) {
                throw invalidKeyException;
            }
        }
        this.mOperationChallenge = android.security.keystore2.KeyStoreCryptoOperationUtils.getOrMakeOperationChallenge(this.mOperation, this.mKey);
        this.mChunkedStreamer = new android.security.keystore2.KeyStoreCryptoOperationChunkedStreamer(new android.security.keystore2.KeyStoreCryptoOperationChunkedStreamer.MainDataStream(this.mOperation));
    }

    @Override // javax.crypto.MacSpi
    protected void engineUpdate(byte b) {
        engineUpdate(new byte[]{b}, 0, 1);
    }

    @Override // javax.crypto.MacSpi
    protected void engineUpdate(byte[] bArr, int i, int i2) {
        try {
            ensureKeystoreOperationInitialized();
            try {
                byte[] update = this.mChunkedStreamer.update(bArr, i, i2);
                if (update != null && update.length != 0) {
                    throw new java.security.ProviderException("Update operation unexpectedly produced output");
                }
            } catch (android.security.KeyStoreException e) {
                throw new java.security.ProviderException("Keystore operation failed", e);
            }
        } catch (java.security.InvalidKeyException e2) {
            throw new java.security.ProviderException("Failed to reinitialize MAC", e2);
        }
    }

    @Override // javax.crypto.MacSpi
    protected byte[] engineDoFinal() {
        try {
            ensureKeystoreOperationInitialized();
            try {
                byte[] doFinal = this.mChunkedStreamer.doFinal(null, 0, 0, null);
                resetWhilePreservingInitState();
                return doFinal;
            } catch (android.security.KeyStoreException e) {
                throw new java.security.ProviderException("Keystore operation failed", e);
            }
        } catch (java.security.InvalidKeyException e2) {
            throw new java.security.ProviderException("Failed to reinitialize MAC", e2);
        }
    }

    public void finalize() throws java.lang.Throwable {
        try {
            abortOperation();
        } finally {
            super.finalize();
        }
    }

    @Override // android.security.keystore.KeyStoreCryptoOperation
    public long getOperationHandle() {
        return this.mOperationChallenge;
    }
}
