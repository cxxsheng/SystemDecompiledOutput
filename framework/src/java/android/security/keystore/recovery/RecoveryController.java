package android.security.keystore.recovery;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class RecoveryController {
    private static final java.lang.String APPLICATION_KEY_GRANT_PREFIX = "recoverable_key:";
    public static final int ERROR_BAD_CERTIFICATE_FORMAT = 25;
    public static final int ERROR_DECRYPTION_FAILED = 26;
    public static final int ERROR_DOWNGRADE_CERTIFICATE = 29;
    public static final int ERROR_INSECURE_USER = 23;
    public static final int ERROR_INVALID_CERTIFICATE = 28;
    public static final int ERROR_INVALID_KEY_FORMAT = 27;
    public static final int ERROR_KEY_NOT_FOUND = 30;
    public static final int ERROR_NO_SNAPSHOT_PENDING = 21;
    public static final int ERROR_SERVICE_INTERNAL_ERROR = 22;
    public static final int ERROR_SESSION_EXPIRED = 24;
    public static final int RECOVERY_STATUS_PERMANENT_FAILURE = 3;
    public static final int RECOVERY_STATUS_SYNCED = 0;
    public static final int RECOVERY_STATUS_SYNC_IN_PROGRESS = 1;
    private static final java.lang.String TAG = "RecoveryController";
    private final com.android.internal.widget.ILockSettings mBinder;

    private RecoveryController(com.android.internal.widget.ILockSettings iLockSettings) {
        this.mBinder = iLockSettings;
    }

    com.android.internal.widget.ILockSettings getBinder() {
        return this.mBinder;
    }

    public static android.security.keystore.recovery.RecoveryController getInstance(android.content.Context context) {
        return new android.security.keystore.recovery.RecoveryController(com.android.internal.widget.ILockSettings.Stub.asInterface(android.os.ServiceManager.getService("lock_settings")));
    }

    public static boolean isRecoverableKeyStoreEnabled(android.content.Context context) {
        android.app.KeyguardManager keyguardManager = (android.app.KeyguardManager) context.getSystemService(android.app.KeyguardManager.class);
        return keyguardManager != null && keyguardManager.isDeviceSecure();
    }

    public void initRecoveryService(java.lang.String str, byte[] bArr, byte[] bArr2) throws java.security.cert.CertificateException, android.security.keystore.recovery.InternalRecoveryServiceException {
        try {
            this.mBinder.initRecoveryServiceWithSigFile(str, bArr, bArr2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (android.os.ServiceSpecificException e2) {
            if (e2.errorCode == 25 || e2.errorCode == 28) {
                throw new java.security.cert.CertificateException("Invalid certificate for recovery service", e2);
            }
            if (e2.errorCode == 29) {
                throw new java.security.cert.CertificateException("Downgrading certificate serial version isn't supported.", e2);
            }
            throw wrapUnexpectedServiceSpecificException(e2);
        }
    }

    public android.security.keystore.recovery.KeyChainSnapshot getKeyChainSnapshot() throws android.security.keystore.recovery.InternalRecoveryServiceException {
        try {
            return this.mBinder.getKeyChainSnapshot();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (android.os.ServiceSpecificException e2) {
            if (e2.errorCode == 21) {
                return null;
            }
            throw wrapUnexpectedServiceSpecificException(e2);
        }
    }

    public void setSnapshotCreatedPendingIntent(android.app.PendingIntent pendingIntent) throws android.security.keystore.recovery.InternalRecoveryServiceException {
        try {
            this.mBinder.setSnapshotCreatedPendingIntent(pendingIntent);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (android.os.ServiceSpecificException e2) {
            throw wrapUnexpectedServiceSpecificException(e2);
        }
    }

    public void setServerParams(byte[] bArr) throws android.security.keystore.recovery.InternalRecoveryServiceException {
        try {
            this.mBinder.setServerParams(bArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (android.os.ServiceSpecificException e2) {
            throw wrapUnexpectedServiceSpecificException(e2);
        }
    }

    public java.util.List<java.lang.String> getAliases() throws android.security.keystore.recovery.InternalRecoveryServiceException {
        try {
            return new java.util.ArrayList(this.mBinder.getRecoveryStatus().keySet());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (android.os.ServiceSpecificException e2) {
            throw wrapUnexpectedServiceSpecificException(e2);
        }
    }

    public void setRecoveryStatus(java.lang.String str, int i) throws android.security.keystore.recovery.InternalRecoveryServiceException {
        try {
            this.mBinder.setRecoveryStatus(str, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (android.os.ServiceSpecificException e2) {
            throw wrapUnexpectedServiceSpecificException(e2);
        }
    }

    public int getRecoveryStatus(java.lang.String str) throws android.security.keystore.recovery.InternalRecoveryServiceException {
        try {
            java.lang.Integer num = (java.lang.Integer) this.mBinder.getRecoveryStatus().get(str);
            if (num == null) {
                return 3;
            }
            return num.intValue();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (android.os.ServiceSpecificException e2) {
            throw wrapUnexpectedServiceSpecificException(e2);
        }
    }

    public void setRecoverySecretTypes(int[] iArr) throws android.security.keystore.recovery.InternalRecoveryServiceException {
        try {
            this.mBinder.setRecoverySecretTypes(iArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (android.os.ServiceSpecificException e2) {
            throw wrapUnexpectedServiceSpecificException(e2);
        }
    }

    public int[] getRecoverySecretTypes() throws android.security.keystore.recovery.InternalRecoveryServiceException {
        try {
            return this.mBinder.getRecoverySecretTypes();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (android.os.ServiceSpecificException e2) {
            throw wrapUnexpectedServiceSpecificException(e2);
        }
    }

    @java.lang.Deprecated
    public java.security.Key generateKey(java.lang.String str) throws android.security.keystore.recovery.InternalRecoveryServiceException, android.security.keystore.recovery.LockScreenRequiredException {
        try {
            java.lang.String generateKey = this.mBinder.generateKey(str);
            if (generateKey == null) {
                throw new android.security.keystore.recovery.InternalRecoveryServiceException("null grant alias");
            }
            return getKeyFromGrant(generateKey);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (android.os.ServiceSpecificException e2) {
            if (e2.errorCode == 23) {
                throw new android.security.keystore.recovery.LockScreenRequiredException(e2.getMessage());
            }
            throw wrapUnexpectedServiceSpecificException(e2);
        } catch (android.security.keystore.KeyPermanentlyInvalidatedException | java.security.UnrecoverableKeyException e3) {
            throw new android.security.keystore.recovery.InternalRecoveryServiceException("Failed to get key from keystore", e3);
        }
    }

    public java.security.Key generateKey(java.lang.String str, byte[] bArr) throws android.security.keystore.recovery.InternalRecoveryServiceException, android.security.keystore.recovery.LockScreenRequiredException {
        try {
            java.lang.String generateKeyWithMetadata = this.mBinder.generateKeyWithMetadata(str, bArr);
            if (generateKeyWithMetadata == null) {
                throw new android.security.keystore.recovery.InternalRecoveryServiceException("null grant alias");
            }
            return getKeyFromGrant(generateKeyWithMetadata);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (android.os.ServiceSpecificException e2) {
            if (e2.errorCode == 23) {
                throw new android.security.keystore.recovery.LockScreenRequiredException(e2.getMessage());
            }
            throw wrapUnexpectedServiceSpecificException(e2);
        } catch (android.security.keystore.KeyPermanentlyInvalidatedException | java.security.UnrecoverableKeyException e3) {
            throw new android.security.keystore.recovery.InternalRecoveryServiceException("Failed to get key from keystore", e3);
        }
    }

    @java.lang.Deprecated
    public java.security.Key importKey(java.lang.String str, byte[] bArr) throws android.security.keystore.recovery.InternalRecoveryServiceException, android.security.keystore.recovery.LockScreenRequiredException {
        try {
            java.lang.String importKey = this.mBinder.importKey(str, bArr);
            if (importKey == null) {
                throw new android.security.keystore.recovery.InternalRecoveryServiceException("Null grant alias");
            }
            return getKeyFromGrant(importKey);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (android.os.ServiceSpecificException e2) {
            if (e2.errorCode == 23) {
                throw new android.security.keystore.recovery.LockScreenRequiredException(e2.getMessage());
            }
            throw wrapUnexpectedServiceSpecificException(e2);
        } catch (android.security.keystore.KeyPermanentlyInvalidatedException | java.security.UnrecoverableKeyException e3) {
            throw new android.security.keystore.recovery.InternalRecoveryServiceException("Failed to get key from keystore", e3);
        }
    }

    public java.security.Key importKey(java.lang.String str, byte[] bArr, byte[] bArr2) throws android.security.keystore.recovery.InternalRecoveryServiceException, android.security.keystore.recovery.LockScreenRequiredException {
        try {
            java.lang.String importKeyWithMetadata = this.mBinder.importKeyWithMetadata(str, bArr, bArr2);
            if (importKeyWithMetadata == null) {
                throw new android.security.keystore.recovery.InternalRecoveryServiceException("Null grant alias");
            }
            return getKeyFromGrant(importKeyWithMetadata);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (android.os.ServiceSpecificException e2) {
            if (e2.errorCode == 23) {
                throw new android.security.keystore.recovery.LockScreenRequiredException(e2.getMessage());
            }
            throw wrapUnexpectedServiceSpecificException(e2);
        } catch (android.security.keystore.KeyPermanentlyInvalidatedException | java.security.UnrecoverableKeyException e3) {
            throw new android.security.keystore.recovery.InternalRecoveryServiceException("Failed to get key from keystore", e3);
        }
    }

    public java.security.Key getKey(java.lang.String str) throws android.security.keystore.recovery.InternalRecoveryServiceException, java.security.UnrecoverableKeyException {
        try {
            java.lang.String key = this.mBinder.getKey(str);
            if (key != null && !"".equals(key)) {
                return getKeyFromGrant(key);
            }
            return null;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (android.os.ServiceSpecificException e2) {
            if (e2.errorCode == 30) {
                throw new java.security.UnrecoverableKeyException(e2.getMessage());
            }
            throw wrapUnexpectedServiceSpecificException(e2);
        } catch (android.security.keystore.KeyPermanentlyInvalidatedException | java.security.UnrecoverableKeyException e3) {
            throw new java.security.UnrecoverableKeyException(e3.getMessage());
        }
    }

    java.security.Key getKeyFromGrant(java.lang.String str) throws java.security.UnrecoverableKeyException, android.security.keystore.KeyPermanentlyInvalidatedException {
        return android.security.keystore2.AndroidKeyStoreProvider.loadAndroidKeyStoreSecretKeyFromKeystore(android.security.KeyStore2.getInstance(), getGrantDescriptor(str));
    }

    private static android.system.keystore2.KeyDescriptor getGrantDescriptor(java.lang.String str) {
        android.system.keystore2.KeyDescriptor keyDescriptor = new android.system.keystore2.KeyDescriptor();
        keyDescriptor.domain = 1;
        keyDescriptor.blob = null;
        keyDescriptor.alias = null;
        try {
            keyDescriptor.nspace = java.lang.Long.parseUnsignedLong(str.substring(APPLICATION_KEY_GRANT_PREFIX.length()), 16);
            return keyDescriptor;
        } catch (java.lang.NumberFormatException e) {
            return null;
        }
    }

    public void removeKey(java.lang.String str) throws android.security.keystore.recovery.InternalRecoveryServiceException {
        try {
            this.mBinder.removeKey(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (android.os.ServiceSpecificException e2) {
            throw wrapUnexpectedServiceSpecificException(e2);
        }
    }

    public android.security.keystore.recovery.RecoverySession createRecoverySession() {
        return android.security.keystore.recovery.RecoverySession.newInstance(this);
    }

    public java.util.Map<java.lang.String, java.security.cert.X509Certificate> getRootCertificates() {
        return android.security.keystore.recovery.TrustedRootCertificates.getRootCertificates();
    }

    android.security.keystore.recovery.InternalRecoveryServiceException wrapUnexpectedServiceSpecificException(android.os.ServiceSpecificException serviceSpecificException) {
        if (serviceSpecificException.errorCode == 22) {
            return new android.security.keystore.recovery.InternalRecoveryServiceException(serviceSpecificException.getMessage(), serviceSpecificException);
        }
        return new android.security.keystore.recovery.InternalRecoveryServiceException("Unexpected error code for method: " + serviceSpecificException.errorCode, serviceSpecificException);
    }
}
