package com.android.server.locksettings;

/* loaded from: classes2.dex */
class RebootEscrowProviderServerBasedImpl implements com.android.server.locksettings.RebootEscrowProviderInterface {
    private static final long DEFAULT_SERVER_BLOB_LIFETIME_IN_MILLIS = 600000;
    private static final long DEFAULT_SERVICE_TIMEOUT_IN_SECONDS = 10;
    private static final java.lang.String TAG = "RebootEscrowProviderServerBased";
    private final com.android.server.locksettings.RebootEscrowProviderServerBasedImpl.Injector mInjector;
    private byte[] mServerBlob;
    private final com.android.server.locksettings.LockSettingsStorage mStorage;

    static class Injector {
        private com.android.server.locksettings.ResumeOnRebootServiceProvider.ResumeOnRebootServiceConnection mServiceConnection;

        Injector(android.content.Context context) {
            this.mServiceConnection = null;
            this.mServiceConnection = new com.android.server.locksettings.ResumeOnRebootServiceProvider(context).getServiceConnection();
            if (this.mServiceConnection == null) {
                android.util.Slog.e(com.android.server.locksettings.RebootEscrowProviderServerBasedImpl.TAG, "Failed to resolve resume on reboot server service.");
            }
        }

        Injector(com.android.server.locksettings.ResumeOnRebootServiceProvider.ResumeOnRebootServiceConnection resumeOnRebootServiceConnection) {
            this.mServiceConnection = null;
            this.mServiceConnection = resumeOnRebootServiceConnection;
        }

        /* JADX INFO: Access modifiers changed from: private */
        @android.annotation.Nullable
        public com.android.server.locksettings.ResumeOnRebootServiceProvider.ResumeOnRebootServiceConnection getServiceConnection() {
            return this.mServiceConnection;
        }

        long getServiceTimeoutInSeconds() {
            return android.provider.DeviceConfig.getLong("ota", "server_based_service_timeout_in_seconds", com.android.server.locksettings.RebootEscrowProviderServerBasedImpl.DEFAULT_SERVICE_TIMEOUT_IN_SECONDS);
        }

        long getServerBlobLifetimeInMillis() {
            return android.provider.DeviceConfig.getLong("ota", "server_based_server_blob_lifetime_in_millis", 600000L);
        }
    }

    RebootEscrowProviderServerBasedImpl(android.content.Context context, com.android.server.locksettings.LockSettingsStorage lockSettingsStorage) {
        this(lockSettingsStorage, new com.android.server.locksettings.RebootEscrowProviderServerBasedImpl.Injector(context));
    }

    @com.android.internal.annotations.VisibleForTesting
    RebootEscrowProviderServerBasedImpl(com.android.server.locksettings.LockSettingsStorage lockSettingsStorage, com.android.server.locksettings.RebootEscrowProviderServerBasedImpl.Injector injector) {
        this.mStorage = lockSettingsStorage;
        this.mInjector = injector;
    }

    @Override // com.android.server.locksettings.RebootEscrowProviderInterface
    public int getType() {
        return 1;
    }

    @Override // com.android.server.locksettings.RebootEscrowProviderInterface
    public boolean hasRebootEscrowSupport() {
        return this.mInjector.getServiceConnection() != null;
    }

    private byte[] unwrapServerBlob(byte[] bArr, javax.crypto.SecretKey secretKey) throws java.util.concurrent.TimeoutException, android.os.RemoteException, java.io.IOException {
        com.android.server.locksettings.ResumeOnRebootServiceProvider.ResumeOnRebootServiceConnection serviceConnection = this.mInjector.getServiceConnection();
        if (serviceConnection == null) {
            android.util.Slog.w(TAG, "Had reboot escrow data for users, but resume on reboot server service is unavailable");
            return null;
        }
        byte[] decrypt = com.android.server.locksettings.AesEncryptionUtil.decrypt(secretKey, bArr);
        if (decrypt == null) {
            android.util.Slog.w(TAG, "Decrypted server blob should not be null");
            return null;
        }
        serviceConnection.bindToService(this.mInjector.getServiceTimeoutInSeconds());
        byte[] unwrap = serviceConnection.unwrap(decrypt, this.mInjector.getServiceTimeoutInSeconds());
        serviceConnection.unbindService();
        return unwrap;
    }

    @Override // com.android.server.locksettings.RebootEscrowProviderInterface
    public com.android.server.locksettings.RebootEscrowKey getAndClearRebootEscrowKey(javax.crypto.SecretKey secretKey) throws java.io.IOException {
        if (this.mServerBlob == null) {
            this.mServerBlob = this.mStorage.readRebootEscrowServerBlob();
        }
        this.mStorage.removeRebootEscrowServerBlob();
        if (this.mServerBlob == null) {
            android.util.Slog.w(TAG, "Failed to read reboot escrow server blob from storage");
            return null;
        }
        if (secretKey == null) {
            android.util.Slog.w(TAG, "Failed to decrypt the escrow key; decryption key from keystore is null.");
            return null;
        }
        android.util.Slog.i(TAG, "Loaded reboot escrow server blob from storage");
        try {
            byte[] unwrapServerBlob = unwrapServerBlob(this.mServerBlob, secretKey);
            if (unwrapServerBlob == null) {
                android.util.Slog.w(TAG, "Decrypted reboot escrow key bytes should not be null");
                return null;
            }
            if (unwrapServerBlob.length != 32) {
                android.util.Slog.e(TAG, "Decrypted reboot escrow key has incorrect size " + unwrapServerBlob.length);
                return null;
            }
            return com.android.server.locksettings.RebootEscrowKey.fromKeyBytes(unwrapServerBlob);
        } catch (android.os.RemoteException | java.util.concurrent.TimeoutException e) {
            android.util.Slog.w(TAG, "Failed to decrypt the server blob ", e);
            return null;
        }
    }

    @Override // com.android.server.locksettings.RebootEscrowProviderInterface
    public void clearRebootEscrowKey() {
        this.mStorage.removeRebootEscrowServerBlob();
    }

    private byte[] wrapEscrowKey(byte[] bArr, javax.crypto.SecretKey secretKey) throws java.util.concurrent.TimeoutException, android.os.RemoteException, java.io.IOException {
        com.android.server.locksettings.ResumeOnRebootServiceProvider.ResumeOnRebootServiceConnection serviceConnection = this.mInjector.getServiceConnection();
        if (serviceConnection == null) {
            android.util.Slog.w(TAG, "Failed to encrypt the reboot escrow key: resume on reboot server service is unavailable");
            return null;
        }
        serviceConnection.bindToService(this.mInjector.getServiceTimeoutInSeconds());
        byte[] wrapBlob = serviceConnection.wrapBlob(bArr, this.mInjector.getServerBlobLifetimeInMillis(), this.mInjector.getServiceTimeoutInSeconds());
        serviceConnection.unbindService();
        if (wrapBlob == null) {
            android.util.Slog.w(TAG, "Server encrypted reboot escrow key cannot be null");
            return null;
        }
        return com.android.server.locksettings.AesEncryptionUtil.encrypt(secretKey, wrapBlob);
    }

    @Override // com.android.server.locksettings.RebootEscrowProviderInterface
    public boolean storeRebootEscrowKey(com.android.server.locksettings.RebootEscrowKey rebootEscrowKey, javax.crypto.SecretKey secretKey) {
        this.mStorage.removeRebootEscrowServerBlob();
        try {
            byte[] wrapEscrowKey = wrapEscrowKey(rebootEscrowKey.getKeyBytes(), secretKey);
            if (wrapEscrowKey == null) {
                android.util.Slog.w(TAG, "Failed to encrypt the reboot escrow key");
                return false;
            }
            this.mStorage.writeRebootEscrowServerBlob(wrapEscrowKey);
            android.util.Slog.i(TAG, "Reboot escrow key encrypted and stored.");
            return true;
        } catch (android.os.RemoteException | java.io.IOException | java.util.concurrent.TimeoutException e) {
            android.util.Slog.w(TAG, "Failed to encrypt the reboot escrow key ", e);
            return false;
        }
    }
}
