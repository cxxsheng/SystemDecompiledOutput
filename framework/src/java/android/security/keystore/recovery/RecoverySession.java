package android.security.keystore.recovery;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class RecoverySession implements java.lang.AutoCloseable {
    private static final int SESSION_ID_LENGTH_BYTES = 16;
    private static final java.lang.String TAG = "RecoverySession";
    private final android.security.keystore.recovery.RecoveryController mRecoveryController;
    private final java.lang.String mSessionId;

    private RecoverySession(android.security.keystore.recovery.RecoveryController recoveryController, java.lang.String str) {
        this.mRecoveryController = recoveryController;
        this.mSessionId = str;
    }

    static android.security.keystore.recovery.RecoverySession newInstance(android.security.keystore.recovery.RecoveryController recoveryController) {
        return new android.security.keystore.recovery.RecoverySession(recoveryController, newSessionId());
    }

    private static java.lang.String newSessionId() {
        byte[] bArr = new byte[16];
        new java.security.SecureRandom().nextBytes(bArr);
        return libcore.util.HexEncoding.encodeToString(bArr, false);
    }

    public byte[] start(java.lang.String str, java.security.cert.CertPath certPath, byte[] bArr, byte[] bArr2, java.util.List<android.security.keystore.recovery.KeyChainProtectionParams> list) throws java.security.cert.CertificateException, android.security.keystore.recovery.InternalRecoveryServiceException {
        try {
            return this.mRecoveryController.getBinder().startRecoverySessionWithCertPath(this.mSessionId, str, android.security.keystore.recovery.RecoveryCertPath.createRecoveryCertPath(certPath), bArr, bArr2, list);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (android.os.ServiceSpecificException e2) {
            if (e2.errorCode == 25 || e2.errorCode == 28) {
                throw new java.security.cert.CertificateException("Invalid certificate for recovery session", e2);
            }
            throw this.mRecoveryController.wrapUnexpectedServiceSpecificException(e2);
        }
    }

    public java.util.Map<java.lang.String, java.security.Key> recoverKeyChainSnapshot(byte[] bArr, java.util.List<android.security.keystore.recovery.WrappedApplicationKey> list) throws android.security.keystore.recovery.SessionExpiredException, android.security.keystore.recovery.DecryptionFailedException, android.security.keystore.recovery.InternalRecoveryServiceException {
        try {
            return getKeysFromGrants(this.mRecoveryController.getBinder().recoverKeyChainSnapshot(this.mSessionId, bArr, list));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (android.os.ServiceSpecificException e2) {
            if (e2.errorCode == 26) {
                throw new android.security.keystore.recovery.DecryptionFailedException(e2.getMessage());
            }
            if (e2.errorCode == 24) {
                throw new android.security.keystore.recovery.SessionExpiredException(e2.getMessage());
            }
            throw this.mRecoveryController.wrapUnexpectedServiceSpecificException(e2);
        }
    }

    private java.util.Map<java.lang.String, java.security.Key> getKeysFromGrants(java.util.Map<java.lang.String, java.lang.String> map) throws android.security.keystore.recovery.InternalRecoveryServiceException {
        android.util.ArrayMap arrayMap = new android.util.ArrayMap(map.size());
        for (java.lang.String str : map.keySet()) {
            java.lang.String str2 = map.get(str);
            try {
                arrayMap.put(str, this.mRecoveryController.getKeyFromGrant(str2));
            } catch (android.security.keystore.KeyPermanentlyInvalidatedException | java.security.UnrecoverableKeyException e) {
                throw new android.security.keystore.recovery.InternalRecoveryServiceException(java.lang.String.format(java.util.Locale.US, "Failed to get key '%s' from grant '%s'", str, str2), e);
            }
        }
        return arrayMap;
    }

    java.lang.String getSessionId() {
        return this.mSessionId;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        try {
            this.mRecoveryController.getBinder().closeSession(this.mSessionId);
        } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
            android.util.Log.e(TAG, "Unexpected error trying to close session", e);
        }
    }
}
