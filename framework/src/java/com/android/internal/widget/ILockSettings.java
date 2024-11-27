package com.android.internal.widget;

/* loaded from: classes5.dex */
public interface ILockSettings extends android.os.IInterface {
    long addWeakEscrowToken(byte[] bArr, int i, com.android.internal.widget.IWeakEscrowTokenActivatedListener iWeakEscrowTokenActivatedListener) throws android.os.RemoteException;

    com.android.internal.widget.VerifyCredentialResponse checkCredential(com.android.internal.widget.LockscreenCredential lockscreenCredential, int i, com.android.internal.widget.ICheckCredentialProgressCallback iCheckCredentialProgressCallback) throws android.os.RemoteException;

    void closeSession(java.lang.String str) throws android.os.RemoteException;

    java.lang.String generateKey(java.lang.String str) throws android.os.RemoteException;

    java.lang.String generateKeyWithMetadata(java.lang.String str, byte[] bArr) throws android.os.RemoteException;

    boolean getBoolean(java.lang.String str, boolean z, int i) throws android.os.RemoteException;

    int getCredentialType(int i) throws android.os.RemoteException;

    byte[] getHashFactor(com.android.internal.widget.LockscreenCredential lockscreenCredential, int i) throws android.os.RemoteException;

    java.lang.String getKey(java.lang.String str) throws android.os.RemoteException;

    android.security.keystore.recovery.KeyChainSnapshot getKeyChainSnapshot() throws android.os.RemoteException;

    byte getLockPatternSize(int i) throws android.os.RemoteException;

    long getLong(java.lang.String str, long j, int i) throws android.os.RemoteException;

    int getPinLength(int i) throws android.os.RemoteException;

    int[] getRecoverySecretTypes() throws android.os.RemoteException;

    java.util.Map getRecoveryStatus() throws android.os.RemoteException;

    boolean getSeparateProfileChallengeEnabled(int i) throws android.os.RemoteException;

    java.lang.String getString(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    int getStrongAuthForUser(int i) throws android.os.RemoteException;

    boolean hasPendingEscrowToken(int i) throws android.os.RemoteException;

    boolean hasSecureLockScreen() throws android.os.RemoteException;

    java.lang.String importKey(java.lang.String str, byte[] bArr) throws android.os.RemoteException;

    java.lang.String importKeyWithMetadata(java.lang.String str, byte[] bArr, byte[] bArr2) throws android.os.RemoteException;

    void initRecoveryServiceWithSigFile(java.lang.String str, byte[] bArr, byte[] bArr2) throws android.os.RemoteException;

    boolean isWeakEscrowTokenActive(long j, int i) throws android.os.RemoteException;

    boolean isWeakEscrowTokenValid(long j, byte[] bArr, int i) throws android.os.RemoteException;

    java.util.Map recoverKeyChainSnapshot(java.lang.String str, byte[] bArr, java.util.List<android.security.keystore.recovery.WrappedApplicationKey> list) throws android.os.RemoteException;

    boolean refreshStoredPinLength(int i) throws android.os.RemoteException;

    void registerStrongAuthTracker(android.app.trust.IStrongAuthTracker iStrongAuthTracker) throws android.os.RemoteException;

    boolean registerWeakEscrowTokenRemovedListener(com.android.internal.widget.IWeakEscrowTokenRemovedListener iWeakEscrowTokenRemovedListener) throws android.os.RemoteException;

    void removeCachedUnifiedChallenge(int i) throws android.os.RemoteException;

    void removeGatekeeperPasswordHandle(long j) throws android.os.RemoteException;

    void removeKey(java.lang.String str) throws android.os.RemoteException;

    boolean removeWeakEscrowToken(long j, int i) throws android.os.RemoteException;

    void reportSuccessfulBiometricUnlock(boolean z, int i) throws android.os.RemoteException;

    void requireStrongAuth(int i, int i2) throws android.os.RemoteException;

    void resetKeyStore(int i) throws android.os.RemoteException;

    void scheduleNonStrongBiometricIdleTimeout(int i) throws android.os.RemoteException;

    void setBoolean(java.lang.String str, boolean z, int i) throws android.os.RemoteException;

    boolean setLockCredential(com.android.internal.widget.LockscreenCredential lockscreenCredential, com.android.internal.widget.LockscreenCredential lockscreenCredential2, int i) throws android.os.RemoteException;

    void setLong(java.lang.String str, long j, int i) throws android.os.RemoteException;

    void setRecoverySecretTypes(int[] iArr) throws android.os.RemoteException;

    void setRecoveryStatus(java.lang.String str, int i) throws android.os.RemoteException;

    void setSeparateProfileChallengeEnabled(int i, boolean z, com.android.internal.widget.LockscreenCredential lockscreenCredential) throws android.os.RemoteException;

    void setServerParams(byte[] bArr) throws android.os.RemoteException;

    void setSnapshotCreatedPendingIntent(android.app.PendingIntent pendingIntent) throws android.os.RemoteException;

    void setString(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    byte[] startRecoverySessionWithCertPath(java.lang.String str, java.lang.String str2, android.security.keystore.recovery.RecoveryCertPath recoveryCertPath, byte[] bArr, byte[] bArr2, java.util.List<android.security.keystore.recovery.KeyChainProtectionParams> list) throws android.os.RemoteException;

    android.app.RemoteLockscreenValidationSession startRemoteLockscreenValidation() throws android.os.RemoteException;

    void systemReady() throws android.os.RemoteException;

    boolean tryUnlockWithCachedUnifiedChallenge(int i) throws android.os.RemoteException;

    void unlockUserKeyIfUnsecured(int i) throws android.os.RemoteException;

    void unregisterStrongAuthTracker(android.app.trust.IStrongAuthTracker iStrongAuthTracker) throws android.os.RemoteException;

    boolean unregisterWeakEscrowTokenRemovedListener(com.android.internal.widget.IWeakEscrowTokenRemovedListener iWeakEscrowTokenRemovedListener) throws android.os.RemoteException;

    void userPresent(int i) throws android.os.RemoteException;

    android.app.RemoteLockscreenValidationResult validateRemoteLockscreen(byte[] bArr) throws android.os.RemoteException;

    com.android.internal.widget.VerifyCredentialResponse verifyCredential(com.android.internal.widget.LockscreenCredential lockscreenCredential, int i, int i2) throws android.os.RemoteException;

    com.android.internal.widget.VerifyCredentialResponse verifyGatekeeperPasswordHandle(long j, long j2, int i) throws android.os.RemoteException;

    com.android.internal.widget.VerifyCredentialResponse verifyTiedProfileChallenge(com.android.internal.widget.LockscreenCredential lockscreenCredential, int i, int i2) throws android.os.RemoteException;

    public static class Default implements com.android.internal.widget.ILockSettings {
        @Override // com.android.internal.widget.ILockSettings
        public void setBoolean(java.lang.String str, boolean z, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setLong(java.lang.String str, long j, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setString(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean getBoolean(java.lang.String str, boolean z, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public long getLong(java.lang.String str, long j, int i) throws android.os.RemoteException {
            return 0L;
        }

        @Override // com.android.internal.widget.ILockSettings
        public java.lang.String getString(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean setLockCredential(com.android.internal.widget.LockscreenCredential lockscreenCredential, com.android.internal.widget.LockscreenCredential lockscreenCredential2, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public void resetKeyStore(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public com.android.internal.widget.VerifyCredentialResponse checkCredential(com.android.internal.widget.LockscreenCredential lockscreenCredential, int i, com.android.internal.widget.ICheckCredentialProgressCallback iCheckCredentialProgressCallback) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public com.android.internal.widget.VerifyCredentialResponse verifyCredential(com.android.internal.widget.LockscreenCredential lockscreenCredential, int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public com.android.internal.widget.VerifyCredentialResponse verifyTiedProfileChallenge(com.android.internal.widget.LockscreenCredential lockscreenCredential, int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public com.android.internal.widget.VerifyCredentialResponse verifyGatekeeperPasswordHandle(long j, long j2, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public void removeGatekeeperPasswordHandle(long j) throws android.os.RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public int getCredentialType(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.widget.ILockSettings
        public int getPinLength(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean refreshStoredPinLength(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public byte[] getHashFactor(com.android.internal.widget.LockscreenCredential lockscreenCredential, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setSeparateProfileChallengeEnabled(int i, boolean z, com.android.internal.widget.LockscreenCredential lockscreenCredential) throws android.os.RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean getSeparateProfileChallengeEnabled(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public void registerStrongAuthTracker(android.app.trust.IStrongAuthTracker iStrongAuthTracker) throws android.os.RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void unregisterStrongAuthTracker(android.app.trust.IStrongAuthTracker iStrongAuthTracker) throws android.os.RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void requireStrongAuth(int i, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void reportSuccessfulBiometricUnlock(boolean z, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void scheduleNonStrongBiometricIdleTimeout(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void systemReady() throws android.os.RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void userPresent(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public int getStrongAuthForUser(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean hasPendingEscrowToken(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public void initRecoveryServiceWithSigFile(java.lang.String str, byte[] bArr, byte[] bArr2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public android.security.keystore.recovery.KeyChainSnapshot getKeyChainSnapshot() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public java.lang.String generateKey(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public java.lang.String generateKeyWithMetadata(java.lang.String str, byte[] bArr) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public java.lang.String importKey(java.lang.String str, byte[] bArr) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public java.lang.String importKeyWithMetadata(java.lang.String str, byte[] bArr, byte[] bArr2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public java.lang.String getKey(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public void removeKey(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setSnapshotCreatedPendingIntent(android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setServerParams(byte[] bArr) throws android.os.RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setRecoveryStatus(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public java.util.Map getRecoveryStatus() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setRecoverySecretTypes(int[] iArr) throws android.os.RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public int[] getRecoverySecretTypes() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public byte[] startRecoverySessionWithCertPath(java.lang.String str, java.lang.String str2, android.security.keystore.recovery.RecoveryCertPath recoveryCertPath, byte[] bArr, byte[] bArr2, java.util.List<android.security.keystore.recovery.KeyChainProtectionParams> list) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public java.util.Map recoverKeyChainSnapshot(java.lang.String str, byte[] bArr, java.util.List<android.security.keystore.recovery.WrappedApplicationKey> list) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public void closeSession(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public android.app.RemoteLockscreenValidationSession startRemoteLockscreenValidation() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public android.app.RemoteLockscreenValidationResult validateRemoteLockscreen(byte[] bArr) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean hasSecureLockScreen() throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean tryUnlockWithCachedUnifiedChallenge(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public void removeCachedUnifiedChallenge(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean registerWeakEscrowTokenRemovedListener(com.android.internal.widget.IWeakEscrowTokenRemovedListener iWeakEscrowTokenRemovedListener) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean unregisterWeakEscrowTokenRemovedListener(com.android.internal.widget.IWeakEscrowTokenRemovedListener iWeakEscrowTokenRemovedListener) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public long addWeakEscrowToken(byte[] bArr, int i, com.android.internal.widget.IWeakEscrowTokenActivatedListener iWeakEscrowTokenActivatedListener) throws android.os.RemoteException {
            return 0L;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean removeWeakEscrowToken(long j, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean isWeakEscrowTokenActive(long j, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean isWeakEscrowTokenValid(long j, byte[] bArr, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public void unlockUserKeyIfUnsecured(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public byte getLockPatternSize(int i) throws android.os.RemoteException {
            return (byte) 0;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.widget.ILockSettings {
        public static final java.lang.String DESCRIPTOR = "com.android.internal.widget.ILockSettings";
        static final int TRANSACTION_addWeakEscrowToken = 53;
        static final int TRANSACTION_checkCredential = 9;
        static final int TRANSACTION_closeSession = 45;
        static final int TRANSACTION_generateKey = 31;
        static final int TRANSACTION_generateKeyWithMetadata = 32;
        static final int TRANSACTION_getBoolean = 4;
        static final int TRANSACTION_getCredentialType = 14;
        static final int TRANSACTION_getHashFactor = 17;
        static final int TRANSACTION_getKey = 35;
        static final int TRANSACTION_getKeyChainSnapshot = 30;
        static final int TRANSACTION_getLockPatternSize = 58;
        static final int TRANSACTION_getLong = 5;
        static final int TRANSACTION_getPinLength = 15;
        static final int TRANSACTION_getRecoverySecretTypes = 42;
        static final int TRANSACTION_getRecoveryStatus = 40;
        static final int TRANSACTION_getSeparateProfileChallengeEnabled = 19;
        static final int TRANSACTION_getString = 6;
        static final int TRANSACTION_getStrongAuthForUser = 27;
        static final int TRANSACTION_hasPendingEscrowToken = 28;
        static final int TRANSACTION_hasSecureLockScreen = 48;
        static final int TRANSACTION_importKey = 33;
        static final int TRANSACTION_importKeyWithMetadata = 34;
        static final int TRANSACTION_initRecoveryServiceWithSigFile = 29;
        static final int TRANSACTION_isWeakEscrowTokenActive = 55;
        static final int TRANSACTION_isWeakEscrowTokenValid = 56;
        static final int TRANSACTION_recoverKeyChainSnapshot = 44;
        static final int TRANSACTION_refreshStoredPinLength = 16;
        static final int TRANSACTION_registerStrongAuthTracker = 20;
        static final int TRANSACTION_registerWeakEscrowTokenRemovedListener = 51;
        static final int TRANSACTION_removeCachedUnifiedChallenge = 50;
        static final int TRANSACTION_removeGatekeeperPasswordHandle = 13;
        static final int TRANSACTION_removeKey = 36;
        static final int TRANSACTION_removeWeakEscrowToken = 54;
        static final int TRANSACTION_reportSuccessfulBiometricUnlock = 23;
        static final int TRANSACTION_requireStrongAuth = 22;
        static final int TRANSACTION_resetKeyStore = 8;
        static final int TRANSACTION_scheduleNonStrongBiometricIdleTimeout = 24;
        static final int TRANSACTION_setBoolean = 1;
        static final int TRANSACTION_setLockCredential = 7;
        static final int TRANSACTION_setLong = 2;
        static final int TRANSACTION_setRecoverySecretTypes = 41;
        static final int TRANSACTION_setRecoveryStatus = 39;
        static final int TRANSACTION_setSeparateProfileChallengeEnabled = 18;
        static final int TRANSACTION_setServerParams = 38;
        static final int TRANSACTION_setSnapshotCreatedPendingIntent = 37;
        static final int TRANSACTION_setString = 3;
        static final int TRANSACTION_startRecoverySessionWithCertPath = 43;
        static final int TRANSACTION_startRemoteLockscreenValidation = 46;
        static final int TRANSACTION_systemReady = 25;
        static final int TRANSACTION_tryUnlockWithCachedUnifiedChallenge = 49;
        static final int TRANSACTION_unlockUserKeyIfUnsecured = 57;
        static final int TRANSACTION_unregisterStrongAuthTracker = 21;
        static final int TRANSACTION_unregisterWeakEscrowTokenRemovedListener = 52;
        static final int TRANSACTION_userPresent = 26;
        static final int TRANSACTION_validateRemoteLockscreen = 47;
        static final int TRANSACTION_verifyCredential = 10;
        static final int TRANSACTION_verifyGatekeeperPasswordHandle = 12;
        static final int TRANSACTION_verifyTiedProfileChallenge = 11;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.internal.widget.ILockSettings asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.widget.ILockSettings)) {
                return (com.android.internal.widget.ILockSettings) queryLocalInterface;
            }
            return new com.android.internal.widget.ILockSettings.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setBoolean";
                case 2:
                    return "setLong";
                case 3:
                    return "setString";
                case 4:
                    return "getBoolean";
                case 5:
                    return "getLong";
                case 6:
                    return "getString";
                case 7:
                    return "setLockCredential";
                case 8:
                    return "resetKeyStore";
                case 9:
                    return "checkCredential";
                case 10:
                    return "verifyCredential";
                case 11:
                    return "verifyTiedProfileChallenge";
                case 12:
                    return "verifyGatekeeperPasswordHandle";
                case 13:
                    return "removeGatekeeperPasswordHandle";
                case 14:
                    return "getCredentialType";
                case 15:
                    return "getPinLength";
                case 16:
                    return "refreshStoredPinLength";
                case 17:
                    return "getHashFactor";
                case 18:
                    return "setSeparateProfileChallengeEnabled";
                case 19:
                    return "getSeparateProfileChallengeEnabled";
                case 20:
                    return "registerStrongAuthTracker";
                case 21:
                    return "unregisterStrongAuthTracker";
                case 22:
                    return "requireStrongAuth";
                case 23:
                    return "reportSuccessfulBiometricUnlock";
                case 24:
                    return "scheduleNonStrongBiometricIdleTimeout";
                case 25:
                    return "systemReady";
                case 26:
                    return "userPresent";
                case 27:
                    return "getStrongAuthForUser";
                case 28:
                    return "hasPendingEscrowToken";
                case 29:
                    return "initRecoveryServiceWithSigFile";
                case 30:
                    return "getKeyChainSnapshot";
                case 31:
                    return "generateKey";
                case 32:
                    return "generateKeyWithMetadata";
                case 33:
                    return "importKey";
                case 34:
                    return "importKeyWithMetadata";
                case 35:
                    return "getKey";
                case 36:
                    return "removeKey";
                case 37:
                    return "setSnapshotCreatedPendingIntent";
                case 38:
                    return "setServerParams";
                case 39:
                    return "setRecoveryStatus";
                case 40:
                    return "getRecoveryStatus";
                case 41:
                    return "setRecoverySecretTypes";
                case 42:
                    return "getRecoverySecretTypes";
                case 43:
                    return "startRecoverySessionWithCertPath";
                case 44:
                    return "recoverKeyChainSnapshot";
                case 45:
                    return "closeSession";
                case 46:
                    return "startRemoteLockscreenValidation";
                case 47:
                    return "validateRemoteLockscreen";
                case 48:
                    return "hasSecureLockScreen";
                case 49:
                    return "tryUnlockWithCachedUnifiedChallenge";
                case 50:
                    return "removeCachedUnifiedChallenge";
                case 51:
                    return "registerWeakEscrowTokenRemovedListener";
                case 52:
                    return "unregisterWeakEscrowTokenRemovedListener";
                case 53:
                    return "addWeakEscrowToken";
                case 54:
                    return "removeWeakEscrowToken";
                case 55:
                    return "isWeakEscrowTokenActive";
                case 56:
                    return "isWeakEscrowTokenValid";
                case 57:
                    return "unlockUserKeyIfUnsecured";
                case 58:
                    return "getLockPatternSize";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setBoolean(readString, readBoolean, readInt);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    long readLong = parcel.readLong();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setLong(readString2, readLong, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    java.lang.String readString3 = parcel.readString();
                    java.lang.String readString4 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setString(readString3, readString4, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    java.lang.String readString5 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean z = getBoolean(readString5, readBoolean2, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(z);
                    return true;
                case 5:
                    java.lang.String readString6 = parcel.readString();
                    long readLong2 = parcel.readLong();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long j = getLong(readString6, readLong2, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeLong(j);
                    return true;
                case 6:
                    java.lang.String readString7 = parcel.readString();
                    java.lang.String readString8 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String string = getString(readString7, readString8, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeString(string);
                    return true;
                case 7:
                    com.android.internal.widget.LockscreenCredential lockscreenCredential = (com.android.internal.widget.LockscreenCredential) parcel.readTypedObject(com.android.internal.widget.LockscreenCredential.CREATOR);
                    com.android.internal.widget.LockscreenCredential lockscreenCredential2 = (com.android.internal.widget.LockscreenCredential) parcel.readTypedObject(com.android.internal.widget.LockscreenCredential.CREATOR);
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean lockCredential = setLockCredential(lockscreenCredential, lockscreenCredential2, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(lockCredential);
                    return true;
                case 8:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resetKeyStore(readInt8);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    com.android.internal.widget.LockscreenCredential lockscreenCredential3 = (com.android.internal.widget.LockscreenCredential) parcel.readTypedObject(com.android.internal.widget.LockscreenCredential.CREATOR);
                    int readInt9 = parcel.readInt();
                    com.android.internal.widget.ICheckCredentialProgressCallback asInterface = com.android.internal.widget.ICheckCredentialProgressCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    com.android.internal.widget.VerifyCredentialResponse checkCredential = checkCredential(lockscreenCredential3, readInt9, asInterface);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(checkCredential, 1);
                    return true;
                case 10:
                    com.android.internal.widget.LockscreenCredential lockscreenCredential4 = (com.android.internal.widget.LockscreenCredential) parcel.readTypedObject(com.android.internal.widget.LockscreenCredential.CREATOR);
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    com.android.internal.widget.VerifyCredentialResponse verifyCredential = verifyCredential(lockscreenCredential4, readInt10, readInt11);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(verifyCredential, 1);
                    return true;
                case 11:
                    com.android.internal.widget.LockscreenCredential lockscreenCredential5 = (com.android.internal.widget.LockscreenCredential) parcel.readTypedObject(com.android.internal.widget.LockscreenCredential.CREATOR);
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    com.android.internal.widget.VerifyCredentialResponse verifyTiedProfileChallenge = verifyTiedProfileChallenge(lockscreenCredential5, readInt12, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(verifyTiedProfileChallenge, 1);
                    return true;
                case 12:
                    long readLong3 = parcel.readLong();
                    long readLong4 = parcel.readLong();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    com.android.internal.widget.VerifyCredentialResponse verifyGatekeeperPasswordHandle = verifyGatekeeperPasswordHandle(readLong3, readLong4, readInt14);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(verifyGatekeeperPasswordHandle, 1);
                    return true;
                case 13:
                    long readLong5 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    removeGatekeeperPasswordHandle(readLong5);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int credentialType = getCredentialType(readInt15);
                    parcel2.writeNoException();
                    parcel2.writeInt(credentialType);
                    return true;
                case 15:
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int pinLength = getPinLength(readInt16);
                    parcel2.writeNoException();
                    parcel2.writeInt(pinLength);
                    return true;
                case 16:
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean refreshStoredPinLength = refreshStoredPinLength(readInt17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(refreshStoredPinLength);
                    return true;
                case 17:
                    com.android.internal.widget.LockscreenCredential lockscreenCredential6 = (com.android.internal.widget.LockscreenCredential) parcel.readTypedObject(com.android.internal.widget.LockscreenCredential.CREATOR);
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] hashFactor = getHashFactor(lockscreenCredential6, readInt18);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(hashFactor);
                    return true;
                case 18:
                    int readInt19 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    com.android.internal.widget.LockscreenCredential lockscreenCredential7 = (com.android.internal.widget.LockscreenCredential) parcel.readTypedObject(com.android.internal.widget.LockscreenCredential.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSeparateProfileChallengeEnabled(readInt19, readBoolean3, lockscreenCredential7);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean separateProfileChallengeEnabled = getSeparateProfileChallengeEnabled(readInt20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(separateProfileChallengeEnabled);
                    return true;
                case 20:
                    android.app.trust.IStrongAuthTracker asInterface2 = android.app.trust.IStrongAuthTracker.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerStrongAuthTracker(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    android.app.trust.IStrongAuthTracker asInterface3 = android.app.trust.IStrongAuthTracker.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterStrongAuthTracker(asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int readInt21 = parcel.readInt();
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requireStrongAuth(readInt21, readInt22);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    boolean readBoolean4 = parcel.readBoolean();
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportSuccessfulBiometricUnlock(readBoolean4, readInt23);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    scheduleNonStrongBiometricIdleTimeout(readInt24);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    systemReady();
                    parcel2.writeNoException();
                    return true;
                case 26:
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    userPresent(readInt25);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int strongAuthForUser = getStrongAuthForUser(readInt26);
                    parcel2.writeNoException();
                    parcel2.writeInt(strongAuthForUser);
                    return true;
                case 28:
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasPendingEscrowToken = hasPendingEscrowToken(readInt27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasPendingEscrowToken);
                    return true;
                case 29:
                    java.lang.String readString9 = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    initRecoveryServiceWithSigFile(readString9, createByteArray, createByteArray2);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    android.security.keystore.recovery.KeyChainSnapshot keyChainSnapshot = getKeyChainSnapshot();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyChainSnapshot, 1);
                    return true;
                case 31:
                    java.lang.String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String generateKey = generateKey(readString10);
                    parcel2.writeNoException();
                    parcel2.writeString(generateKey);
                    return true;
                case 32:
                    java.lang.String readString11 = parcel.readString();
                    byte[] createByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    java.lang.String generateKeyWithMetadata = generateKeyWithMetadata(readString11, createByteArray3);
                    parcel2.writeNoException();
                    parcel2.writeString(generateKeyWithMetadata);
                    return true;
                case 33:
                    java.lang.String readString12 = parcel.readString();
                    byte[] createByteArray4 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    java.lang.String importKey = importKey(readString12, createByteArray4);
                    parcel2.writeNoException();
                    parcel2.writeString(importKey);
                    return true;
                case 34:
                    java.lang.String readString13 = parcel.readString();
                    byte[] createByteArray5 = parcel.createByteArray();
                    byte[] createByteArray6 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    java.lang.String importKeyWithMetadata = importKeyWithMetadata(readString13, createByteArray5, createByteArray6);
                    parcel2.writeNoException();
                    parcel2.writeString(importKeyWithMetadata);
                    return true;
                case 35:
                    java.lang.String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String key = getKey(readString14);
                    parcel2.writeNoException();
                    parcel2.writeString(key);
                    return true;
                case 36:
                    java.lang.String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeKey(readString15);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    android.app.PendingIntent pendingIntent = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSnapshotCreatedPendingIntent(pendingIntent);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    byte[] createByteArray7 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    setServerParams(createByteArray7);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    java.lang.String readString16 = parcel.readString();
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRecoveryStatus(readString16, readInt28);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    java.util.Map recoveryStatus = getRecoveryStatus();
                    parcel2.writeNoException();
                    parcel2.writeMap(recoveryStatus);
                    return true;
                case 41:
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setRecoverySecretTypes(createIntArray);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    int[] recoverySecretTypes = getRecoverySecretTypes();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(recoverySecretTypes);
                    return true;
                case 43:
                    java.lang.String readString17 = parcel.readString();
                    java.lang.String readString18 = parcel.readString();
                    android.security.keystore.recovery.RecoveryCertPath recoveryCertPath = (android.security.keystore.recovery.RecoveryCertPath) parcel.readTypedObject(android.security.keystore.recovery.RecoveryCertPath.CREATOR);
                    byte[] createByteArray8 = parcel.createByteArray();
                    byte[] createByteArray9 = parcel.createByteArray();
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.security.keystore.recovery.KeyChainProtectionParams.CREATOR);
                    parcel.enforceNoDataAvail();
                    byte[] startRecoverySessionWithCertPath = startRecoverySessionWithCertPath(readString17, readString18, recoveryCertPath, createByteArray8, createByteArray9, createTypedArrayList);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(startRecoverySessionWithCertPath);
                    return true;
                case 44:
                    java.lang.String readString19 = parcel.readString();
                    byte[] createByteArray10 = parcel.createByteArray();
                    java.util.ArrayList createTypedArrayList2 = parcel.createTypedArrayList(android.security.keystore.recovery.WrappedApplicationKey.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.util.Map recoverKeyChainSnapshot = recoverKeyChainSnapshot(readString19, createByteArray10, createTypedArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeMap(recoverKeyChainSnapshot);
                    return true;
                case 45:
                    java.lang.String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    closeSession(readString20);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    android.app.RemoteLockscreenValidationSession startRemoteLockscreenValidation = startRemoteLockscreenValidation();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(startRemoteLockscreenValidation, 1);
                    return true;
                case 47:
                    byte[] createByteArray11 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    android.app.RemoteLockscreenValidationResult validateRemoteLockscreen = validateRemoteLockscreen(createByteArray11);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(validateRemoteLockscreen, 1);
                    return true;
                case 48:
                    boolean hasSecureLockScreen = hasSecureLockScreen();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasSecureLockScreen);
                    return true;
                case 49:
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean tryUnlockWithCachedUnifiedChallenge = tryUnlockWithCachedUnifiedChallenge(readInt29);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(tryUnlockWithCachedUnifiedChallenge);
                    return true;
                case 50:
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeCachedUnifiedChallenge(readInt30);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    com.android.internal.widget.IWeakEscrowTokenRemovedListener asInterface4 = com.android.internal.widget.IWeakEscrowTokenRemovedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean registerWeakEscrowTokenRemovedListener = registerWeakEscrowTokenRemovedListener(asInterface4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerWeakEscrowTokenRemovedListener);
                    return true;
                case 52:
                    com.android.internal.widget.IWeakEscrowTokenRemovedListener asInterface5 = com.android.internal.widget.IWeakEscrowTokenRemovedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean unregisterWeakEscrowTokenRemovedListener = unregisterWeakEscrowTokenRemovedListener(asInterface5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unregisterWeakEscrowTokenRemovedListener);
                    return true;
                case 53:
                    byte[] createByteArray12 = parcel.createByteArray();
                    int readInt31 = parcel.readInt();
                    com.android.internal.widget.IWeakEscrowTokenActivatedListener asInterface6 = com.android.internal.widget.IWeakEscrowTokenActivatedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    long addWeakEscrowToken = addWeakEscrowToken(createByteArray12, readInt31, asInterface6);
                    parcel2.writeNoException();
                    parcel2.writeLong(addWeakEscrowToken);
                    return true;
                case 54:
                    long readLong6 = parcel.readLong();
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean removeWeakEscrowToken = removeWeakEscrowToken(readLong6, readInt32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeWeakEscrowToken);
                    return true;
                case 55:
                    long readLong7 = parcel.readLong();
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isWeakEscrowTokenActive = isWeakEscrowTokenActive(readLong7, readInt33);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWeakEscrowTokenActive);
                    return true;
                case 56:
                    long readLong8 = parcel.readLong();
                    byte[] createByteArray13 = parcel.createByteArray();
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isWeakEscrowTokenValid = isWeakEscrowTokenValid(readLong8, createByteArray13, readInt34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWeakEscrowTokenValid);
                    return true;
                case 57:
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unlockUserKeyIfUnsecured(readInt35);
                    parcel2.writeNoException();
                    return true;
                case 58:
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte lockPatternSize = getLockPatternSize(readInt36);
                    parcel2.writeNoException();
                    parcel2.writeByte(lockPatternSize);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.widget.ILockSettings {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setBoolean(java.lang.String str, boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setLong(java.lang.String str, long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setString(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean getBoolean(java.lang.String str, boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public long getLong(java.lang.String str, long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public java.lang.String getString(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean setLockCredential(com.android.internal.widget.LockscreenCredential lockscreenCredential, com.android.internal.widget.LockscreenCredential lockscreenCredential2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(lockscreenCredential, 0);
                    obtain.writeTypedObject(lockscreenCredential2, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void resetKeyStore(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public com.android.internal.widget.VerifyCredentialResponse checkCredential(com.android.internal.widget.LockscreenCredential lockscreenCredential, int i, com.android.internal.widget.ICheckCredentialProgressCallback iCheckCredentialProgressCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(lockscreenCredential, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iCheckCredentialProgressCallback);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (com.android.internal.widget.VerifyCredentialResponse) obtain2.readTypedObject(com.android.internal.widget.VerifyCredentialResponse.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public com.android.internal.widget.VerifyCredentialResponse verifyCredential(com.android.internal.widget.LockscreenCredential lockscreenCredential, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(lockscreenCredential, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return (com.android.internal.widget.VerifyCredentialResponse) obtain2.readTypedObject(com.android.internal.widget.VerifyCredentialResponse.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public com.android.internal.widget.VerifyCredentialResponse verifyTiedProfileChallenge(com.android.internal.widget.LockscreenCredential lockscreenCredential, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(lockscreenCredential, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return (com.android.internal.widget.VerifyCredentialResponse) obtain2.readTypedObject(com.android.internal.widget.VerifyCredentialResponse.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public com.android.internal.widget.VerifyCredentialResponse verifyGatekeeperPasswordHandle(long j, long j2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return (com.android.internal.widget.VerifyCredentialResponse) obtain2.readTypedObject(com.android.internal.widget.VerifyCredentialResponse.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void removeGatekeeperPasswordHandle(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public int getCredentialType(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public int getPinLength(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean refreshStoredPinLength(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public byte[] getHashFactor(com.android.internal.widget.LockscreenCredential lockscreenCredential, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(lockscreenCredential, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setSeparateProfileChallengeEnabled(int i, boolean z, com.android.internal.widget.LockscreenCredential lockscreenCredential) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(lockscreenCredential, 0);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean getSeparateProfileChallengeEnabled(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void registerStrongAuthTracker(android.app.trust.IStrongAuthTracker iStrongAuthTracker) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iStrongAuthTracker);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void unregisterStrongAuthTracker(android.app.trust.IStrongAuthTracker iStrongAuthTracker) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iStrongAuthTracker);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void requireStrongAuth(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void reportSuccessfulBiometricUnlock(boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void scheduleNonStrongBiometricIdleTimeout(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void systemReady() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void userPresent(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public int getStrongAuthForUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean hasPendingEscrowToken(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void initRecoveryServiceWithSigFile(java.lang.String str, byte[] bArr, byte[] bArr2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public android.security.keystore.recovery.KeyChainSnapshot getKeyChainSnapshot() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.security.keystore.recovery.KeyChainSnapshot) obtain2.readTypedObject(android.security.keystore.recovery.KeyChainSnapshot.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public java.lang.String generateKey(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public java.lang.String generateKeyWithMetadata(java.lang.String str, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public java.lang.String importKey(java.lang.String str, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public java.lang.String importKeyWithMetadata(java.lang.String str, byte[] bArr, byte[] bArr2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public java.lang.String getKey(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void removeKey(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setSnapshotCreatedPendingIntent(android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setServerParams(byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setRecoveryStatus(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public java.util.Map getRecoveryStatus() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setRecoverySecretTypes(int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public int[] getRecoverySecretTypes() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public byte[] startRecoverySessionWithCertPath(java.lang.String str, java.lang.String str2, android.security.keystore.recovery.RecoveryCertPath recoveryCertPath, byte[] bArr, byte[] bArr2, java.util.List<android.security.keystore.recovery.KeyChainProtectionParams> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(recoveryCertPath, 0);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public java.util.Map recoverKeyChainSnapshot(java.lang.String str, byte[] bArr, java.util.List<android.security.keystore.recovery.WrappedApplicationKey> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void closeSession(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public android.app.RemoteLockscreenValidationSession startRemoteLockscreenValidation() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.RemoteLockscreenValidationSession) obtain2.readTypedObject(android.app.RemoteLockscreenValidationSession.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public android.app.RemoteLockscreenValidationResult validateRemoteLockscreen(byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.RemoteLockscreenValidationResult) obtain2.readTypedObject(android.app.RemoteLockscreenValidationResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean hasSecureLockScreen() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean tryUnlockWithCachedUnifiedChallenge(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void removeCachedUnifiedChallenge(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean registerWeakEscrowTokenRemovedListener(com.android.internal.widget.IWeakEscrowTokenRemovedListener iWeakEscrowTokenRemovedListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWeakEscrowTokenRemovedListener);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean unregisterWeakEscrowTokenRemovedListener(com.android.internal.widget.IWeakEscrowTokenRemovedListener iWeakEscrowTokenRemovedListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWeakEscrowTokenRemovedListener);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public long addWeakEscrowToken(byte[] bArr, int i, com.android.internal.widget.IWeakEscrowTokenActivatedListener iWeakEscrowTokenActivatedListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iWeakEscrowTokenActivatedListener);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean removeWeakEscrowToken(long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean isWeakEscrowTokenActive(long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean isWeakEscrowTokenValid(long j, byte[] bArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void unlockUserKeyIfUnsecured(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public byte getLockPatternSize(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.widget.ILockSettings.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readByte();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 57;
        }
    }
}
