package android.security.keystore2;

/* loaded from: classes3.dex */
public class AndroidKeyStoreKeyAgreementSpi extends javax.crypto.KeyAgreementSpi implements android.security.keystore.KeyStoreCryptoOperation {
    private static final java.lang.String TAG = "AndroidKeyStoreKeyAgreementSpi";
    private android.security.keystore2.AndroidKeyStorePrivateKey mKey;
    private final int mKeymintAlgorithm;
    private android.security.KeyStoreOperation mOperation;
    private long mOperationHandle;
    private java.security.PublicKey mOtherPartyKey;

    public static class ECDH extends android.security.keystore2.AndroidKeyStoreKeyAgreementSpi {
        public ECDH() {
            super(3);
        }
    }

    public static class XDH extends android.security.keystore2.AndroidKeyStoreKeyAgreementSpi {
        public XDH() {
            super(3);
        }
    }

    protected AndroidKeyStoreKeyAgreementSpi(int i) {
        resetAll();
        this.mKeymintAlgorithm = i;
    }

    @Override // javax.crypto.KeyAgreementSpi
    protected void engineInit(java.security.Key key, java.security.SecureRandom secureRandom) throws java.security.InvalidKeyException {
        if (key == null) {
            throw new java.security.InvalidKeyException("key == null");
        }
        if (!(key instanceof android.security.keystore2.AndroidKeyStorePrivateKey)) {
            throw new java.security.InvalidKeyException("Only Android KeyStore private keys supported. Key: " + key);
        }
        this.mKey = (android.security.keystore2.AndroidKeyStorePrivateKey) key;
        try {
            ensureKeystoreOperationInitialized();
        } finally {
            resetAll();
        }
    }

    @Override // javax.crypto.KeyAgreementSpi
    protected void engineInit(java.security.Key key, java.security.spec.AlgorithmParameterSpec algorithmParameterSpec, java.security.SecureRandom secureRandom) throws java.security.InvalidKeyException, java.security.InvalidAlgorithmParameterException {
        if (algorithmParameterSpec != null) {
            throw new java.security.InvalidAlgorithmParameterException("Unsupported algorithm parameters: " + algorithmParameterSpec);
        }
        engineInit(key, secureRandom);
    }

    @Override // javax.crypto.KeyAgreementSpi
    protected java.security.Key engineDoPhase(java.security.Key key, boolean z) throws java.security.InvalidKeyException, java.lang.IllegalStateException {
        ensureKeystoreOperationInitialized();
        if (key == null) {
            throw new java.security.InvalidKeyException("key == null");
        }
        if (!(key instanceof java.security.PublicKey)) {
            throw new java.security.InvalidKeyException("Only public keys supported. Key: " + key);
        }
        if ((this.mKey instanceof java.security.interfaces.ECKey) && !(key instanceof java.security.interfaces.ECKey)) {
            throw new java.security.InvalidKeyException("Public and Private key should be of the same type.");
        }
        if ((this.mKey instanceof java.security.interfaces.ECKey) && !((java.security.interfaces.ECKey) key).getParams().getCurve().equals(((java.security.interfaces.ECKey) this.mKey).getParams().getCurve())) {
            throw new java.security.InvalidKeyException("Public and Private key parameters should be same.");
        }
        if (!z) {
            throw new java.lang.IllegalStateException("Only one other party supported. lastPhase must be set to true.");
        }
        if (this.mOtherPartyKey != null) {
            throw new java.lang.IllegalStateException("Only one other party supported. doPhase() must only be called exactly once.");
        }
        this.mOtherPartyKey = (java.security.PublicKey) key;
        return null;
    }

    @Override // javax.crypto.KeyAgreementSpi
    protected byte[] engineGenerateSecret() throws java.lang.IllegalStateException {
        try {
            ensureKeystoreOperationInitialized();
            if (this.mOtherPartyKey == null) {
                throw new java.lang.IllegalStateException("Other party key not provided. Call doPhase() first.");
            }
            byte[] encoded = this.mOtherPartyKey.getEncoded();
            android.os.StrictMode.noteSlowCall("engineGenerateSecret");
            try {
                try {
                    return this.mOperation.finish(encoded, null);
                } catch (android.security.KeyStoreException e) {
                    throw new java.security.ProviderException("Keystore operation failed", e);
                }
            } finally {
                resetWhilePreservingInitState();
            }
        } catch (java.security.InvalidKeyException e2) {
            throw new java.lang.IllegalStateException("Not initialized", e2);
        }
    }

    @Override // javax.crypto.KeyAgreementSpi
    protected javax.crypto.SecretKey engineGenerateSecret(java.lang.String str) throws java.lang.IllegalStateException, java.security.NoSuchAlgorithmException, java.security.InvalidKeyException {
        return new javax.crypto.spec.SecretKeySpec(engineGenerateSecret(), str);
    }

    @Override // javax.crypto.KeyAgreementSpi
    protected int engineGenerateSecret(byte[] bArr, int i) throws java.lang.IllegalStateException, javax.crypto.ShortBufferException {
        byte[] engineGenerateSecret = engineGenerateSecret();
        if (engineGenerateSecret.length > bArr.length - i) {
            throw new javax.crypto.ShortBufferException("Needed: " + engineGenerateSecret.length);
        }
        java.lang.System.arraycopy(engineGenerateSecret, 0, bArr, i, engineGenerateSecret.length);
        return engineGenerateSecret.length;
    }

    @Override // android.security.keystore.KeyStoreCryptoOperation
    public long getOperationHandle() {
        return this.mOperationHandle;
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            resetAll();
        } finally {
            super.finalize();
        }
    }

    private void resetWhilePreservingInitState() {
        android.security.keystore2.KeyStoreCryptoOperationUtils.abortOperation(this.mOperation);
        this.mOperationHandle = 0L;
        this.mOperation = null;
        this.mOtherPartyKey = null;
    }

    private void resetAll() {
        resetWhilePreservingInitState();
        this.mKey = null;
    }

    private void ensureKeystoreOperationInitialized() throws java.security.InvalidKeyException, java.lang.IllegalStateException {
        if (this.mKey == null) {
            throw new java.lang.IllegalStateException("Not initialized");
        }
        if (this.mOperation != null) {
            return;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536870913, 6));
        android.os.StrictMode.noteDiskWrite();
        try {
            this.mOperation = this.mKey.getSecurityLevel().createOperation(this.mKey.getKeyIdDescriptor(), arrayList);
        } catch (android.security.KeyStoreException e) {
            java.security.InvalidKeyException invalidKeyException = android.security.keystore2.KeyStoreCryptoOperationUtils.getInvalidKeyException(this.mKey, e);
            if (invalidKeyException != null) {
                throw invalidKeyException;
            }
        }
        this.mOperationHandle = android.security.keystore2.KeyStoreCryptoOperationUtils.getOrMakeOperationChallenge(this.mOperation, this.mKey);
    }
}
