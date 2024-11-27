package android.security.identity;

/* loaded from: classes3.dex */
class CredstorePresentationSession extends android.security.identity.PresentationSession {
    private static final java.lang.String TAG = "CredstorePresentationSession";
    private android.security.identity.ISession mBinder;
    private int mCipherSuite;
    private android.content.Context mContext;
    private int mFeatureVersion;
    private android.security.identity.CredstoreIdentityCredentialStore mStore;
    private java.util.Map<java.lang.String, android.security.identity.CredstoreIdentityCredential> mCredentialCache = new java.util.LinkedHashMap();
    private java.security.KeyPair mEphemeralKeyPair = null;
    private byte[] mSessionTranscript = null;
    private boolean mOperationHandleSet = false;
    private long mOperationHandle = 0;

    CredstorePresentationSession(android.content.Context context, int i, android.security.identity.CredstoreIdentityCredentialStore credstoreIdentityCredentialStore, android.security.identity.ISession iSession, int i2) {
        this.mFeatureVersion = 0;
        this.mContext = context;
        this.mCipherSuite = i;
        this.mStore = credstoreIdentityCredentialStore;
        this.mBinder = iSession;
        this.mFeatureVersion = i2;
    }

    private void ensureEphemeralKeyPair() {
        if (this.mEphemeralKeyPair != null) {
            return;
        }
        try {
            byte[] ephemeralKeyPair = this.mBinder.getEphemeralKeyPair();
            char[] cArr = new char[0];
            java.security.KeyStore keyStore = java.security.KeyStore.getInstance(android.security.KeyChain.EXTRA_PKCS12);
            keyStore.load(new java.io.ByteArrayInputStream(ephemeralKeyPair), cArr);
            this.mEphemeralKeyPair = new java.security.KeyPair(keyStore.getCertificate("ephemeralKey").getPublicKey(), (java.security.PrivateKey) keyStore.getKey("ephemeralKey", cArr));
        } catch (android.os.RemoteException | java.io.IOException | java.security.KeyStoreException | java.security.NoSuchAlgorithmException | java.security.UnrecoverableKeyException | java.security.cert.CertificateException e) {
            throw new java.lang.RuntimeException("Unexpected exception ", e);
        } catch (android.os.ServiceSpecificException e2) {
            throw new java.lang.RuntimeException("Unexpected ServiceSpecificException with code " + e2.errorCode, e2);
        }
    }

    @Override // android.security.identity.PresentationSession
    public java.security.KeyPair getEphemeralKeyPair() {
        ensureEphemeralKeyPair();
        return this.mEphemeralKeyPair;
    }

    @Override // android.security.identity.PresentationSession
    public void setReaderEphemeralPublicKey(java.security.PublicKey publicKey) throws java.security.InvalidKeyException {
        try {
            this.mBinder.setReaderEphemeralPublicKey(android.security.identity.Util.publicKeyEncodeUncompressedForm(publicKey));
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Unexpected RemoteException ", e);
        } catch (android.os.ServiceSpecificException e2) {
            throw new java.lang.RuntimeException("Unexpected ServiceSpecificException with code " + e2.errorCode, e2);
        }
    }

    @Override // android.security.identity.PresentationSession
    public void setSessionTranscript(byte[] bArr) {
        try {
            this.mBinder.setSessionTranscript(bArr);
            this.mSessionTranscript = bArr;
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Unexpected RemoteException ", e);
        } catch (android.os.ServiceSpecificException e2) {
            throw new java.lang.RuntimeException("Unexpected ServiceSpecificException with code " + e2.errorCode, e2);
        }
    }

    @Override // android.security.identity.PresentationSession
    public android.security.identity.CredentialDataResult getCredentialData(java.lang.String str, android.security.identity.CredentialDataRequest credentialDataRequest) throws android.security.identity.NoAuthenticationKeyAvailableException, android.security.identity.InvalidReaderSignatureException, android.security.identity.InvalidRequestMessageException, android.security.identity.EphemeralPublicKeyNotFoundException {
        try {
            android.security.identity.CredstoreIdentityCredential credstoreIdentityCredential = this.mCredentialCache.get(str);
            if (credstoreIdentityCredential == null) {
                credstoreIdentityCredential = new android.security.identity.CredstoreIdentityCredential(this.mContext, str, this.mCipherSuite, this.mBinder.getCredentialForPresentation(str), this, this.mFeatureVersion);
                this.mCredentialCache.put(str, credstoreIdentityCredential);
                credstoreIdentityCredential.setAllowUsingExhaustedKeys(credentialDataRequest.isAllowUsingExhaustedKeys());
                credstoreIdentityCredential.setAllowUsingExpiredKeys(credentialDataRequest.isAllowUsingExpiredKeys());
                credstoreIdentityCredential.setIncrementKeyUsageCount(credentialDataRequest.isIncrementUseCount());
            }
            return new android.security.identity.CredstoreCredentialDataResult(credstoreIdentityCredential.getEntries(credentialDataRequest.getRequestMessage(), credentialDataRequest.getDeviceSignedEntriesToRequest(), this.mSessionTranscript, credentialDataRequest.getReaderSignature()), credstoreIdentityCredential.getEntries(credentialDataRequest.getRequestMessage(), credentialDataRequest.getIssuerSignedEntriesToRequest(), this.mSessionTranscript, credentialDataRequest.getReaderSignature()));
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Unexpected RemoteException ", e);
        } catch (android.os.ServiceSpecificException e2) {
            if (e2.errorCode == 3) {
                return null;
            }
            throw new java.lang.RuntimeException("Unexpected ServiceSpecificException with code " + e2.errorCode, e2);
        } catch (android.security.identity.SessionTranscriptMismatchException e3) {
            throw new java.lang.RuntimeException("Unexpected ", e3);
        }
    }

    @Override // android.security.identity.PresentationSession
    public long getCredstoreOperationHandle() {
        if (!this.mOperationHandleSet) {
            try {
                this.mOperationHandle = this.mBinder.getAuthChallenge();
                this.mOperationHandleSet = true;
            } catch (android.os.RemoteException e) {
                throw new java.lang.RuntimeException("Unexpected RemoteException ", e);
            } catch (android.os.ServiceSpecificException e2) {
                int i = e2.errorCode;
                throw new java.lang.RuntimeException("Unexpected ServiceSpecificException with code " + e2.errorCode, e2);
            }
        }
        return this.mOperationHandle;
    }
}
