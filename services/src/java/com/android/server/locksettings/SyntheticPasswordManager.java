package com.android.server.locksettings;

/* loaded from: classes2.dex */
class SyntheticPasswordManager {
    private static final int INVALID_WEAVER_SLOT = -1;
    public static final long NULL_PROTECTOR_ID = 0;
    private static final java.lang.String PASSWORD_DATA_NAME = "pwd";
    private static final java.lang.String PASSWORD_METRICS_NAME = "metrics";
    private static final int PASSWORD_SALT_LENGTH = 16;
    private static final int PASSWORD_SCRYPT_LOG_N = 11;
    private static final int PASSWORD_SCRYPT_LOG_P = 1;
    private static final int PASSWORD_SCRYPT_LOG_R = 3;
    private static final java.lang.String PROTECTOR_KEY_ALIAS_PREFIX = "synthetic_password_";
    private static final byte PROTECTOR_TYPE_LSKF_BASED = 0;
    private static final byte PROTECTOR_TYPE_STRONG_TOKEN_BASED = 1;
    private static final byte PROTECTOR_TYPE_WEAK_TOKEN_BASED = 2;
    private static final int SECDISCARDABLE_LENGTH = 16384;
    private static final java.lang.String SECDISCARDABLE_NAME = "secdis";
    private static final java.lang.String SP_BLOB_NAME = "spblob";
    private static final java.lang.String SP_E0_NAME = "e0";
    private static final java.lang.String SP_HANDLE_NAME = "handle";
    private static final java.lang.String SP_P1_NAME = "p1";
    private static final int STRETCHED_LSKF_LENGTH = 32;
    private static final int SYNTHETIC_PASSWORD_SECURITY_STRENGTH = 32;
    private static final byte SYNTHETIC_PASSWORD_VERSION_V1 = 1;
    private static final byte SYNTHETIC_PASSWORD_VERSION_V2 = 2;
    private static final byte SYNTHETIC_PASSWORD_VERSION_V3 = 3;
    private static final java.lang.String TAG = "SyntheticPasswordManager";
    static final int TOKEN_TYPE_STRONG = 0;
    static final int TOKEN_TYPE_WEAK = 1;
    private static final java.lang.String VENDOR_AUTH_SECRET_NAME = "vendor_auth_secret";
    private static final java.lang.String WEAVER_SLOT_NAME = "weaver";
    private static final byte WEAVER_VERSION = 1;
    private final android.content.Context mContext;
    private com.android.server.locksettings.PasswordSlotManager mPasswordSlotManager;
    private com.android.server.locksettings.LockSettingsStorage mStorage;
    private final android.os.UserManager mUserManager;
    private volatile android.hardware.weaver.IWeaver mWeaver;
    private android.hardware.weaver.WeaverConfig mWeaverConfig;
    private static final byte[] DEFAULT_PASSWORD = "default-password".getBytes();
    private static final byte[] PERSONALIZATION_SECDISCARDABLE = "secdiscardable-transform".getBytes();
    private static final byte[] PERSONALIZATION_KEY_STORE_PASSWORD = "keystore-password".getBytes();
    private static final byte[] PERSONALIZATION_USER_GK_AUTH = "user-gk-authentication".getBytes();
    private static final byte[] PERSONALIZATION_SP_GK_AUTH = "sp-gk-authentication".getBytes();
    private static final byte[] PERSONALIZATION_FBE_KEY = "fbe-key".getBytes();
    private static final byte[] PERSONALIZATION_AUTHSECRET_KEY = "authsecret-hal".getBytes();
    private static final byte[] PERSONALIZATION_AUTHSECRET_ENCRYPTION_KEY = "vendor-authsecret-encryption-key".getBytes();
    private static final byte[] PERSONALIZATION_SP_SPLIT = "sp-split".getBytes();
    private static final byte[] PERSONALIZATION_PASSWORD_HASH = "pw-hash".getBytes();
    private static final byte[] PERSONALIZATION_E0 = "e0-encryption".getBytes();
    private static final byte[] PERSONALIZATION_WEAVER_PASSWORD = "weaver-pwd".getBytes();
    private static final byte[] PERSONALIZATION_WEAVER_KEY = "weaver-key".getBytes();
    private static final byte[] PERSONALIZATION_WEAVER_TOKEN = "weaver-token".getBytes();
    private static final byte[] PERSONALIZATION_PASSWORD_METRICS = "password-metrics".getBytes();
    private static final byte[] PERSONALIZATION_CONTEXT = "android-synthetic-password-personalization-context".getBytes();
    private final android.os.RemoteCallbackList<com.android.internal.widget.IWeakEscrowTokenRemovedListener> mListeners = new android.os.RemoteCallbackList<>();
    private android.util.ArrayMap<java.lang.Integer, android.util.ArrayMap<java.lang.Long, com.android.server.locksettings.SyntheticPasswordManager.TokenData>> tokenMap = new android.util.ArrayMap<>();

    static class AuthenticationResult {

        @android.annotation.Nullable
        public com.android.internal.widget.VerifyCredentialResponse gkResponse;

        @android.annotation.Nullable
        public com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword syntheticPassword;

        AuthenticationResult() {
        }
    }

    private static class TokenData {
        byte[] aggregatedSecret;
        com.android.internal.widget.LockPatternUtils.EscrowTokenStateChangeCallback mCallback;
        int mType;
        byte[] secdiscardableOnDisk;
        byte[] weaverSecret;

        private TokenData() {
        }
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface TokenType {
    }

    private native long nativeSidFromPasswordHandle(byte[] bArr);

    static class SyntheticPassword {

        @android.annotation.Nullable
        private byte[] mEncryptedEscrowSplit0;

        @android.annotation.Nullable
        private byte[] mEscrowSplit1;

        @android.annotation.NonNull
        private byte[] mSyntheticPassword;
        private final byte mVersion;

        SyntheticPassword(byte b) {
            this.mVersion = b;
        }

        private byte[] deriveSubkey(byte[] bArr) {
            if (this.mVersion == 3) {
                return new com.android.server.locksettings.SP800Derive(this.mSyntheticPassword).withContext(bArr, com.android.server.locksettings.SyntheticPasswordManager.PERSONALIZATION_CONTEXT);
            }
            return com.android.server.locksettings.SyntheticPasswordCrypto.personalizedHash(bArr, this.mSyntheticPassword);
        }

        public byte[] deriveKeyStorePassword() {
            return com.android.server.locksettings.SyntheticPasswordManager.bytesToHex(deriveSubkey(com.android.server.locksettings.SyntheticPasswordManager.PERSONALIZATION_KEY_STORE_PASSWORD));
        }

        public byte[] deriveGkPassword() {
            return deriveSubkey(com.android.server.locksettings.SyntheticPasswordManager.PERSONALIZATION_SP_GK_AUTH);
        }

        public byte[] deriveFileBasedEncryptionKey() {
            return deriveSubkey(com.android.server.locksettings.SyntheticPasswordManager.PERSONALIZATION_FBE_KEY);
        }

        public byte[] deriveVendorAuthSecret() {
            return deriveSubkey(com.android.server.locksettings.SyntheticPasswordManager.PERSONALIZATION_AUTHSECRET_KEY);
        }

        public byte[] derivePasswordHashFactor() {
            return deriveSubkey(com.android.server.locksettings.SyntheticPasswordManager.PERSONALIZATION_PASSWORD_HASH);
        }

        public byte[] deriveMetricsKey() {
            return deriveSubkey(com.android.server.locksettings.SyntheticPasswordManager.PERSONALIZATION_PASSWORD_METRICS);
        }

        public byte[] deriveVendorAuthSecretEncryptionKey() {
            return deriveSubkey(com.android.server.locksettings.SyntheticPasswordManager.PERSONALIZATION_AUTHSECRET_ENCRYPTION_KEY);
        }

        public void setEscrowData(@android.annotation.Nullable byte[] bArr, @android.annotation.Nullable byte[] bArr2) {
            this.mEncryptedEscrowSplit0 = bArr;
            this.mEscrowSplit1 = bArr2;
        }

        public void recreateFromEscrow(byte[] bArr) {
            java.util.Objects.requireNonNull(this.mEscrowSplit1);
            java.util.Objects.requireNonNull(this.mEncryptedEscrowSplit0);
            recreate(bArr, this.mEscrowSplit1);
        }

        public void recreateDirectly(byte[] bArr) {
            this.mSyntheticPassword = java.util.Arrays.copyOf(bArr, bArr.length);
        }

        static com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword create() {
            com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword syntheticPassword = new com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword((byte) 3);
            byte[] randomBytes = com.android.server.locksettings.SecureRandomUtils.randomBytes(32);
            byte[] randomBytes2 = com.android.server.locksettings.SecureRandomUtils.randomBytes(32);
            syntheticPassword.recreate(randomBytes, randomBytes2);
            syntheticPassword.setEscrowData(com.android.server.locksettings.SyntheticPasswordCrypto.encrypt(syntheticPassword.mSyntheticPassword, com.android.server.locksettings.SyntheticPasswordManager.PERSONALIZATION_E0, randomBytes), randomBytes2);
            return syntheticPassword;
        }

        private void recreate(byte[] bArr, byte[] bArr2) {
            this.mSyntheticPassword = com.android.server.locksettings.SyntheticPasswordManager.bytesToHex(com.android.server.locksettings.SyntheticPasswordCrypto.personalizedHash(com.android.server.locksettings.SyntheticPasswordManager.PERSONALIZATION_SP_SPLIT, bArr, bArr2));
        }

        public byte[] getEscrowSecret() {
            if (this.mEncryptedEscrowSplit0 == null) {
                return null;
            }
            return com.android.server.locksettings.SyntheticPasswordCrypto.decrypt(this.mSyntheticPassword, com.android.server.locksettings.SyntheticPasswordManager.PERSONALIZATION_E0, this.mEncryptedEscrowSplit0);
        }

        public byte[] getSyntheticPassword() {
            return this.mSyntheticPassword;
        }

        public byte getVersion() {
            return this.mVersion;
        }
    }

    static class PasswordData {
        public int credentialType;
        public byte[] passwordHandle;
        public int pinLength;
        byte[] salt;
        byte scryptLogN;
        byte scryptLogP;
        byte scryptLogR;

        PasswordData() {
        }

        public static com.android.server.locksettings.SyntheticPasswordManager.PasswordData create(int i, int i2) {
            com.android.server.locksettings.SyntheticPasswordManager.PasswordData passwordData = new com.android.server.locksettings.SyntheticPasswordManager.PasswordData();
            passwordData.scryptLogN = (byte) 11;
            passwordData.scryptLogR = (byte) 3;
            passwordData.scryptLogP = (byte) 1;
            passwordData.credentialType = i;
            passwordData.pinLength = i2;
            passwordData.salt = com.android.server.locksettings.SecureRandomUtils.randomBytes(16);
            return passwordData;
        }

        public static boolean isBadFormatFromAndroid14Beta(byte[] bArr) {
            return bArr != null && bArr.length >= 2 && bArr[0] == 0 && bArr[1] == 2;
        }

        public static com.android.server.locksettings.SyntheticPasswordManager.PasswordData fromBytes(byte[] bArr) {
            com.android.server.locksettings.SyntheticPasswordManager.PasswordData passwordData = new com.android.server.locksettings.SyntheticPasswordManager.PasswordData();
            java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(bArr.length);
            allocate.put(bArr, 0, bArr.length);
            allocate.flip();
            passwordData.credentialType = (short) allocate.getInt();
            passwordData.scryptLogN = allocate.get();
            passwordData.scryptLogR = allocate.get();
            passwordData.scryptLogP = allocate.get();
            passwordData.salt = new byte[allocate.getInt()];
            allocate.get(passwordData.salt);
            int i = allocate.getInt();
            if (i > 0) {
                passwordData.passwordHandle = new byte[i];
                allocate.get(passwordData.passwordHandle);
            } else {
                passwordData.passwordHandle = null;
            }
            if (allocate.remaining() >= 4) {
                passwordData.pinLength = allocate.getInt();
            } else {
                passwordData.pinLength = -1;
            }
            return passwordData;
        }

        public byte[] toBytes() {
            java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(this.salt.length + 11 + 4 + (this.passwordHandle != null ? this.passwordHandle.length : 0) + 4);
            if (this.credentialType < -32768 || this.credentialType > 32767) {
                throw new java.lang.IllegalArgumentException("Unknown credential type: " + this.credentialType);
            }
            allocate.putInt(this.credentialType);
            allocate.put(this.scryptLogN);
            allocate.put(this.scryptLogR);
            allocate.put(this.scryptLogP);
            allocate.putInt(this.salt.length);
            allocate.put(this.salt);
            if (this.passwordHandle != null && this.passwordHandle.length > 0) {
                allocate.putInt(this.passwordHandle.length);
                allocate.put(this.passwordHandle);
            } else {
                allocate.putInt(0);
            }
            allocate.putInt(this.pinLength);
            return allocate.array();
        }
    }

    private static class SyntheticPasswordBlob {
        byte[] mContent;
        byte mProtectorType;
        byte mVersion;

        private SyntheticPasswordBlob() {
        }

        public static com.android.server.locksettings.SyntheticPasswordManager.SyntheticPasswordBlob create(byte b, byte b2, byte[] bArr) {
            com.android.server.locksettings.SyntheticPasswordManager.SyntheticPasswordBlob syntheticPasswordBlob = new com.android.server.locksettings.SyntheticPasswordManager.SyntheticPasswordBlob();
            syntheticPasswordBlob.mVersion = b;
            syntheticPasswordBlob.mProtectorType = b2;
            syntheticPasswordBlob.mContent = bArr;
            return syntheticPasswordBlob;
        }

        public static com.android.server.locksettings.SyntheticPasswordManager.SyntheticPasswordBlob fromBytes(byte[] bArr) {
            com.android.server.locksettings.SyntheticPasswordManager.SyntheticPasswordBlob syntheticPasswordBlob = new com.android.server.locksettings.SyntheticPasswordManager.SyntheticPasswordBlob();
            syntheticPasswordBlob.mVersion = bArr[0];
            syntheticPasswordBlob.mProtectorType = bArr[1];
            syntheticPasswordBlob.mContent = java.util.Arrays.copyOfRange(bArr, 2, bArr.length);
            return syntheticPasswordBlob;
        }

        public byte[] toByte() {
            byte[] bArr = new byte[this.mContent.length + 1 + 1];
            bArr[0] = this.mVersion;
            bArr[1] = this.mProtectorType;
            java.lang.System.arraycopy(this.mContent, 0, bArr, 2, this.mContent.length);
            return bArr;
        }
    }

    public SyntheticPasswordManager(android.content.Context context, com.android.server.locksettings.LockSettingsStorage lockSettingsStorage, android.os.UserManager userManager, com.android.server.locksettings.PasswordSlotManager passwordSlotManager) {
        this.mContext = context;
        this.mStorage = lockSettingsStorage;
        this.mUserManager = userManager;
        this.mPasswordSlotManager = passwordSlotManager;
    }

    private boolean isDeviceProvisioned() {
        return android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "device_provisioned", 0) != 0;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected android.hardware.weaver.V1_0.IWeaver getWeaverHidlService() throws android.os.RemoteException {
        try {
            return android.hardware.weaver.V1_0.IWeaver.getService(true);
        } catch (java.util.NoSuchElementException e) {
            return null;
        }
    }

    private class WeaverDiedRecipient implements android.os.IBinder.DeathRecipient {
        private WeaverDiedRecipient() {
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            android.util.Slog.wtf(com.android.server.locksettings.SyntheticPasswordManager.TAG, "Weaver service has died");
            com.android.server.locksettings.SyntheticPasswordManager.this.mWeaver.asBinder().unlinkToDeath(this, 0);
            com.android.server.locksettings.SyntheticPasswordManager.this.mWeaver = null;
        }
    }

    @android.annotation.Nullable
    private android.hardware.weaver.IWeaver getWeaverAidlService() {
        try {
            android.hardware.weaver.IWeaver asInterface = android.hardware.weaver.IWeaver.Stub.asInterface(android.os.ServiceManager.waitForDeclaredService(android.hardware.weaver.IWeaver.DESCRIPTOR + "/default"));
            if (asInterface == null) {
                return null;
            }
            try {
                int interfaceVersion = asInterface.getInterfaceVersion();
                if (interfaceVersion < 2) {
                    android.util.Slog.w(TAG, "Ignoring AIDL weaver service v" + interfaceVersion + " because only v2 and later are supported");
                    return null;
                }
                android.util.Slog.i(TAG, "Found AIDL weaver service v" + interfaceVersion);
                return asInterface;
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Cannot get AIDL weaver service version", e);
                return null;
            }
        } catch (java.lang.SecurityException e2) {
            android.util.Slog.w(TAG, "Does not have permissions to get AIDL weaver service");
            return null;
        }
    }

    @android.annotation.Nullable
    private android.hardware.weaver.IWeaver getWeaverServiceInternal() {
        android.hardware.weaver.IWeaver weaverAidlService = getWeaverAidlService();
        if (weaverAidlService != null) {
            android.util.Slog.i(TAG, "Using AIDL weaver service");
            try {
                weaverAidlService.asBinder().linkToDeath(new com.android.server.locksettings.SyntheticPasswordManager.WeaverDiedRecipient(), 0);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "Unable to register Weaver death recipient", e);
            }
            return weaverAidlService;
        }
        try {
            android.hardware.weaver.V1_0.IWeaver weaverHidlService = getWeaverHidlService();
            if (weaverHidlService != null) {
                android.util.Slog.i(TAG, "Using HIDL weaver service");
                return new com.android.server.locksettings.WeaverHidlAdapter(weaverHidlService);
            }
        } catch (android.os.RemoteException e2) {
            android.util.Slog.w(TAG, "Failed to get HIDL weaver service.", e2);
        }
        android.util.Slog.w(TAG, "Device does not support weaver");
        return null;
    }

    @com.android.internal.annotations.VisibleForTesting
    public boolean isAutoPinConfirmationFeatureAvailable() {
        return com.android.internal.widget.LockPatternUtils.isAutoPinConfirmFeatureAvailable();
    }

    @android.annotation.Nullable
    private synchronized android.hardware.weaver.IWeaver getWeaverService() {
        android.hardware.weaver.IWeaver iWeaver = this.mWeaver;
        if (iWeaver != null) {
            return iWeaver;
        }
        android.hardware.weaver.IWeaver weaverServiceInternal = getWeaverServiceInternal();
        if (weaverServiceInternal == null) {
            return null;
        }
        try {
            android.hardware.weaver.WeaverConfig config = weaverServiceInternal.getConfig();
            if (config == null || config.slots <= 0) {
                android.util.Slog.e(TAG, "Invalid weaver config");
                return null;
            }
            this.mWeaver = weaverServiceInternal;
            this.mWeaverConfig = config;
            this.mPasswordSlotManager.refreshActiveSlots(getUsedWeaverSlots());
            android.util.Slog.i(TAG, "Weaver service initialized");
            return weaverServiceInternal;
        } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
            android.util.Slog.e(TAG, "Failed to get weaver config", e);
            return null;
        }
    }

    private byte[] weaverEnroll(android.hardware.weaver.IWeaver iWeaver, int i, byte[] bArr, @android.annotation.Nullable byte[] bArr2) {
        if (i == -1 || i >= this.mWeaverConfig.slots) {
            throw new java.lang.IllegalArgumentException("Invalid slot for weaver");
        }
        if (bArr == null) {
            bArr = new byte[this.mWeaverConfig.keySize];
        } else if (bArr.length != this.mWeaverConfig.keySize) {
            throw new java.lang.IllegalArgumentException("Invalid key size for weaver");
        }
        if (bArr2 == null) {
            bArr2 = com.android.server.locksettings.SecureRandomUtils.randomBytes(this.mWeaverConfig.valueSize);
        }
        try {
            iWeaver.write(i, bArr, bArr2);
            return bArr2;
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "weaver write binder call failed, slot: " + i, e);
            return null;
        } catch (android.os.ServiceSpecificException e2) {
            android.util.Slog.e(TAG, "weaver write failed, slot: " + i, e2);
            return null;
        }
    }

    private static com.android.internal.widget.VerifyCredentialResponse responseFromTimeout(android.hardware.weaver.WeaverReadResponse weaverReadResponse) {
        int i;
        if (weaverReadResponse.timeout > 2147483647L || weaverReadResponse.timeout < 0) {
            i = Integer.MAX_VALUE;
        } else {
            i = (int) weaverReadResponse.timeout;
        }
        return com.android.internal.widget.VerifyCredentialResponse.fromTimeout(i);
    }

    private com.android.internal.widget.VerifyCredentialResponse weaverVerify(android.hardware.weaver.IWeaver iWeaver, int i, byte[] bArr) {
        if (i == -1 || i >= this.mWeaverConfig.slots) {
            throw new java.lang.IllegalArgumentException("Invalid slot for weaver");
        }
        if (bArr == null) {
            bArr = new byte[this.mWeaverConfig.keySize];
        } else if (bArr.length != this.mWeaverConfig.keySize) {
            throw new java.lang.IllegalArgumentException("Invalid key size for weaver");
        }
        try {
            android.hardware.weaver.WeaverReadResponse read = iWeaver.read(i, bArr);
            switch (read.status) {
                case 0:
                    return new com.android.internal.widget.VerifyCredentialResponse.Builder().setGatekeeperHAT(read.value).build();
                case 1:
                    android.util.Slog.e(TAG, "weaver read failed (FAILED), slot: " + i);
                    return com.android.internal.widget.VerifyCredentialResponse.ERROR;
                case 2:
                    if (read.timeout == 0) {
                        android.util.Slog.e(TAG, "weaver read failed (INCORRECT_KEY), slot: " + i);
                        return com.android.internal.widget.VerifyCredentialResponse.ERROR;
                    }
                    android.util.Slog.e(TAG, "weaver read failed (INCORRECT_KEY/THROTTLE), slot: " + i);
                    return responseFromTimeout(read);
                case 3:
                    android.util.Slog.e(TAG, "weaver read failed (THROTTLE), slot: " + i);
                    return responseFromTimeout(read);
                default:
                    android.util.Slog.e(TAG, "weaver read unknown status " + read.status + ", slot: " + i);
                    return com.android.internal.widget.VerifyCredentialResponse.ERROR;
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "weaver read failed, slot: " + i, e);
            return com.android.internal.widget.VerifyCredentialResponse.ERROR;
        }
    }

    public void removeUser(android.service.gatekeeper.IGateKeeperService iGateKeeperService, int i) {
        java.util.Iterator<java.lang.Long> it = this.mStorage.listSyntheticPasswordProtectorsForUser(SP_BLOB_NAME, i).iterator();
        while (it.hasNext()) {
            long longValue = it.next().longValue();
            destroyWeaverSlot(longValue, i);
            destroyProtectorKey(getProtectorKeyAlias(longValue));
        }
        try {
            iGateKeeperService.clearSecureUserId(fakeUserId(i));
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Failed to clear SID from gatekeeper");
        }
    }

    int getPinLength(long j, int i) {
        byte[] loadState = loadState(PASSWORD_DATA_NAME, j, i);
        if (loadState == null) {
            return -1;
        }
        return com.android.server.locksettings.SyntheticPasswordManager.PasswordData.fromBytes(loadState).pinLength;
    }

    int getCredentialType(long j, int i) {
        byte[] loadState = loadState(PASSWORD_DATA_NAME, j, i);
        if (loadState == null) {
            return -1;
        }
        return com.android.server.locksettings.SyntheticPasswordManager.PasswordData.fromBytes(loadState).credentialType;
    }

    int getSpecialUserCredentialType(int i) {
        com.android.server.locksettings.LockSettingsStorage.PersistentData specialUserPersistentData = getSpecialUserPersistentData(i);
        if ((specialUserPersistentData.type != 1 && specialUserPersistentData.type != 2) || specialUserPersistentData.payload == null) {
            return -1;
        }
        int i2 = com.android.server.locksettings.SyntheticPasswordManager.PasswordData.fromBytes(specialUserPersistentData.payload).credentialType;
        if (i2 != 2) {
            return i2;
        }
        return com.android.internal.widget.LockPatternUtils.pinOrPasswordQualityToCredentialType(specialUserPersistentData.qualityForUi);
    }

    private com.android.server.locksettings.LockSettingsStorage.PersistentData getSpecialUserPersistentData(int i) {
        if (i == -9999) {
            return this.mStorage.readPersistentDataBlock();
        }
        if (i == -9998) {
            return this.mStorage.readRepairModePersistentData();
        }
        throw new java.lang.IllegalArgumentException("Unknown special user id " + i);
    }

    com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword newSyntheticPassword(int i) {
        clearSidForUser(i);
        com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword create = com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword.create();
        saveEscrowData(create, i);
        return create;
    }

    public void newSidForUser(android.service.gatekeeper.IGateKeeperService iGateKeeperService, com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword syntheticPassword, int i) {
        try {
            android.service.gatekeeper.GateKeeperResponse enroll = iGateKeeperService.enroll(i, (byte[]) null, (byte[]) null, syntheticPassword.deriveGkPassword());
            if (enroll.getResponseCode() != 0) {
                throw new java.lang.IllegalStateException("Fail to create new SID for user " + i + " response: " + enroll.getResponseCode());
            }
            saveSyntheticPasswordHandle(enroll.getPayload(), i);
        } catch (android.os.RemoteException e) {
            throw new java.lang.IllegalStateException("Failed to create new SID for user", e);
        }
    }

    public void clearSidForUser(int i) {
        destroyState(SP_HANDLE_NAME, 0L, i);
    }

    public boolean hasSidForUser(int i) {
        return hasState(SP_HANDLE_NAME, 0L, i);
    }

    private byte[] loadSyntheticPasswordHandle(int i) {
        return loadState(SP_HANDLE_NAME, 0L, i);
    }

    private void saveSyntheticPasswordHandle(byte[] bArr, int i) {
        saveState(SP_HANDLE_NAME, bArr, 0L, i);
        syncState(i);
    }

    private boolean loadEscrowData(com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword syntheticPassword, int i) {
        byte[] loadState = loadState(SP_E0_NAME, 0L, i);
        byte[] loadState2 = loadState(SP_P1_NAME, 0L, i);
        syntheticPassword.setEscrowData(loadState, loadState2);
        return (loadState == null || loadState2 == null) ? false : true;
    }

    private void saveEscrowData(com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword syntheticPassword, int i) {
        saveState(SP_E0_NAME, syntheticPassword.mEncryptedEscrowSplit0, 0L, i);
        saveState(SP_P1_NAME, syntheticPassword.mEscrowSplit1, 0L, i);
    }

    public boolean hasEscrowData(int i) {
        return hasState(SP_E0_NAME, 0L, i) && hasState(SP_P1_NAME, 0L, i);
    }

    public boolean hasAnyEscrowData(int i) {
        return hasState(SP_E0_NAME, 0L, i) || hasState(SP_P1_NAME, 0L, i);
    }

    public void destroyEscrowData(int i) {
        destroyState(SP_E0_NAME, 0L, i);
        destroyState(SP_P1_NAME, 0L, i);
    }

    private int loadWeaverSlot(long j, int i) {
        byte[] loadState = loadState(WEAVER_SLOT_NAME, j, i);
        if (loadState == null || loadState.length != 5) {
            return -1;
        }
        java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(5);
        allocate.put(loadState, 0, loadState.length);
        allocate.flip();
        if (allocate.get() != 1) {
            android.util.Slog.e(TAG, "Invalid weaver slot version for protector " + j);
            return -1;
        }
        return allocate.getInt();
    }

    private void saveWeaverSlot(int i, long j, int i2) {
        java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(5);
        allocate.put((byte) 1);
        allocate.putInt(i);
        saveState(WEAVER_SLOT_NAME, allocate.array(), j, i2);
    }

    private void destroyWeaverSlot(long j, int i) {
        int loadWeaverSlot = loadWeaverSlot(j, i);
        destroyState(WEAVER_SLOT_NAME, j, i);
        if (loadWeaverSlot != -1) {
            android.hardware.weaver.IWeaver weaverService = getWeaverService();
            if (weaverService == null) {
                android.util.Slog.e(TAG, "Cannot erase Weaver slot because Weaver is unavailable");
            } else {
                if (!getUsedWeaverSlots().contains(java.lang.Integer.valueOf(loadWeaverSlot))) {
                    com.android.server.utils.Slogf.i(TAG, "Erasing Weaver slot %d", java.lang.Integer.valueOf(loadWeaverSlot));
                    weaverEnroll(weaverService, loadWeaverSlot, null, null);
                    this.mPasswordSlotManager.markSlotDeleted(loadWeaverSlot);
                    return;
                }
                com.android.server.utils.Slogf.i(TAG, "Weaver slot %d was already reused; not erasing it", java.lang.Integer.valueOf(loadWeaverSlot));
            }
        }
    }

    private java.util.Set<java.lang.Integer> getUsedWeaverSlots() {
        java.util.Map<java.lang.Integer, java.util.List<java.lang.Long>> listSyntheticPasswordProtectorsForAllUsers = this.mStorage.listSyntheticPasswordProtectorsForAllUsers(WEAVER_SLOT_NAME);
        java.util.HashSet hashSet = new java.util.HashSet();
        for (java.util.Map.Entry<java.lang.Integer, java.util.List<java.lang.Long>> entry : listSyntheticPasswordProtectorsForAllUsers.entrySet()) {
            java.util.Iterator<java.lang.Long> it = entry.getValue().iterator();
            while (it.hasNext()) {
                hashSet.add(java.lang.Integer.valueOf(loadWeaverSlot(it.next().longValue(), entry.getKey().intValue())));
            }
        }
        return hashSet;
    }

    private int getNextAvailableWeaverSlot() {
        com.android.server.locksettings.LockSettingsStorage.PersistentData readPersistentDataBlock;
        java.util.Set<java.lang.Integer> usedWeaverSlots = getUsedWeaverSlots();
        usedWeaverSlots.addAll(this.mPasswordSlotManager.getUsedSlots());
        if (!isDeviceProvisioned() && (readPersistentDataBlock = this.mStorage.readPersistentDataBlock()) != null && readPersistentDataBlock.type == 2) {
            usedWeaverSlots.add(java.lang.Integer.valueOf(readPersistentDataBlock.userId));
        }
        for (int i = 0; i < this.mWeaverConfig.slots; i++) {
            if (!usedWeaverSlots.contains(java.lang.Integer.valueOf(i))) {
                return i;
            }
        }
        throw new java.lang.IllegalStateException("Run out of weaver slots.");
    }

    public long createLskfBasedProtector(android.service.gatekeeper.IGateKeeperService iGateKeeperService, com.android.internal.widget.LockscreenCredential lockscreenCredential, com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword syntheticPassword, int i) {
        int i2;
        byte[] transformUnderSecdiscardable;
        long generateProtectorId = generateProtectorId();
        if (isAutoPinConfirmationFeatureAvailable()) {
            i2 = derivePinLength(lockscreenCredential.size(), lockscreenCredential.isPin(), i);
        } else {
            i2 = -1;
        }
        com.android.server.locksettings.SyntheticPasswordManager.PasswordData create = lockscreenCredential.isNone() ? null : com.android.server.locksettings.SyntheticPasswordManager.PasswordData.create(lockscreenCredential.getType(), i2);
        byte[] stretchLskf = stretchLskf(lockscreenCredential, create);
        com.android.server.utils.Slogf.i(TAG, "Creating LSKF-based protector %016x for user %d", java.lang.Long.valueOf(generateProtectorId), java.lang.Integer.valueOf(i));
        android.hardware.weaver.IWeaver weaverService = getWeaverService();
        long j = 0;
        if (weaverService != null) {
            int nextAvailableWeaverSlot = getNextAvailableWeaverSlot();
            com.android.server.utils.Slogf.i(TAG, "Enrolling LSKF for user %d into Weaver slot %d", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(nextAvailableWeaverSlot));
            byte[] weaverEnroll = weaverEnroll(weaverService, nextAvailableWeaverSlot, stretchedLskfToWeaverKey(stretchLskf), null);
            if (weaverEnroll == null) {
                throw new java.lang.IllegalStateException("Fail to enroll user password under weaver " + i);
            }
            saveWeaverSlot(nextAvailableWeaverSlot, generateProtectorId, i);
            this.mPasswordSlotManager.markSlotInUse(nextAvailableWeaverSlot);
            synchronizeWeaverFrpPassword(create, 0, i, nextAvailableWeaverSlot);
            transformUnderSecdiscardable = transformUnderWeaverSecret(stretchLskf, weaverEnroll);
        } else {
            if (!lockscreenCredential.isNone()) {
                try {
                    iGateKeeperService.clearSecureUserId(fakeUserId(i));
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(TAG, "Failed to clear SID from gatekeeper");
                }
                com.android.server.utils.Slogf.i(TAG, "Enrolling LSKF for user %d into Gatekeeper", java.lang.Integer.valueOf(i));
                try {
                    android.service.gatekeeper.GateKeeperResponse enroll = iGateKeeperService.enroll(fakeUserId(i), (byte[]) null, (byte[]) null, stretchedLskfToGkPassword(stretchLskf));
                    if (enroll.getResponseCode() != 0) {
                        throw new java.lang.IllegalStateException("Failed to enroll LSKF for new SP protector for user " + i);
                    }
                    create.passwordHandle = enroll.getPayload();
                    j = sidFromPasswordHandle(create.passwordHandle);
                } catch (android.os.RemoteException e2) {
                    throw new java.lang.IllegalStateException("Failed to enroll LSKF for new SP protector for user " + i, e2);
                }
            }
            transformUnderSecdiscardable = transformUnderSecdiscardable(stretchLskf, createSecdiscardable(generateProtectorId, i));
            synchronizeGatekeeperFrpPassword(create, 0, i);
        }
        if (!lockscreenCredential.isNone()) {
            saveState(PASSWORD_DATA_NAME, create.toBytes(), generateProtectorId, i);
            savePasswordMetrics(lockscreenCredential, syntheticPassword, generateProtectorId, i);
        }
        createSyntheticPasswordBlob(generateProtectorId, (byte) 0, syntheticPassword, transformUnderSecdiscardable, j, i);
        syncState(i);
        return generateProtectorId;
    }

    private int derivePinLength(int i, boolean z, int i2) {
        if (!z || !this.mStorage.isAutoPinConfirmSettingEnabled(i2) || i < 6) {
            return -1;
        }
        return i;
    }

    public com.android.internal.widget.VerifyCredentialResponse verifySpecialUserCredential(int i, android.service.gatekeeper.IGateKeeperService iGateKeeperService, com.android.internal.widget.LockscreenCredential lockscreenCredential, com.android.internal.widget.ICheckCredentialProgressCallback iCheckCredentialProgressCallback) {
        com.android.server.locksettings.LockSettingsStorage.PersistentData specialUserPersistentData = getSpecialUserPersistentData(i);
        if (specialUserPersistentData.type == 1) {
            com.android.server.locksettings.SyntheticPasswordManager.PasswordData fromBytes = com.android.server.locksettings.SyntheticPasswordManager.PasswordData.fromBytes(specialUserPersistentData.payload);
            try {
                return com.android.internal.widget.VerifyCredentialResponse.fromGateKeeperResponse(iGateKeeperService.verifyChallenge(fakeUserId(specialUserPersistentData.userId), 0L, fromBytes.passwordHandle, stretchedLskfToGkPassword(stretchLskf(lockscreenCredential, fromBytes))));
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Persistent data credential verifyChallenge failed", e);
                return com.android.internal.widget.VerifyCredentialResponse.ERROR;
            }
        }
        if (specialUserPersistentData.type == 2) {
            android.hardware.weaver.IWeaver weaverService = getWeaverService();
            if (weaverService == null) {
                android.util.Slog.e(TAG, "No weaver service to verify SP-based persistent data credential");
                return com.android.internal.widget.VerifyCredentialResponse.ERROR;
            }
            return weaverVerify(weaverService, specialUserPersistentData.userId, stretchedLskfToWeaverKey(stretchLskf(lockscreenCredential, com.android.server.locksettings.SyntheticPasswordManager.PasswordData.fromBytes(specialUserPersistentData.payload)))).stripPayload();
        }
        android.util.Slog.e(TAG, "persistentData.type must be TYPE_SP_GATEKEEPER or TYPE_SP_WEAVER, but is " + specialUserPersistentData.type);
        return com.android.internal.widget.VerifyCredentialResponse.ERROR;
    }

    public void migrateFrpPasswordLocked(long j, android.content.pm.UserInfo userInfo, int i) {
        if (this.mStorage.getPersistentDataBlockManager() != null && com.android.internal.widget.LockPatternUtils.userOwnsFrpCredential(this.mContext, userInfo) && getCredentialType(j, userInfo.id) != -1) {
            android.util.Slog.i(TAG, "Migrating FRP credential to persistent data block");
            com.android.server.locksettings.SyntheticPasswordManager.PasswordData fromBytes = com.android.server.locksettings.SyntheticPasswordManager.PasswordData.fromBytes(loadState(PASSWORD_DATA_NAME, j, userInfo.id));
            int loadWeaverSlot = loadWeaverSlot(j, userInfo.id);
            if (loadWeaverSlot != -1) {
                synchronizeWeaverFrpPassword(fromBytes, i, userInfo.id, loadWeaverSlot);
            } else {
                synchronizeGatekeeperFrpPassword(fromBytes, i, userInfo.id);
            }
        }
    }

    private static boolean isNoneCredential(com.android.server.locksettings.SyntheticPasswordManager.PasswordData passwordData) {
        return passwordData == null || passwordData.credentialType == -1;
    }

    private boolean shouldSynchronizeFrpCredential(@android.annotation.Nullable com.android.server.locksettings.SyntheticPasswordManager.PasswordData passwordData, int i) {
        if (this.mStorage.getPersistentDataBlockManager() == null) {
            return false;
        }
        if (!com.android.internal.widget.LockPatternUtils.userOwnsFrpCredential(this.mContext, this.mUserManager.getUserInfo(i))) {
            return false;
        }
        if (isNoneCredential(passwordData) && !isDeviceProvisioned()) {
            android.util.Slog.d(TAG, "Not clearing FRP credential yet because device is not yet provisioned");
            return false;
        }
        return true;
    }

    private void synchronizeGatekeeperFrpPassword(@android.annotation.Nullable com.android.server.locksettings.SyntheticPasswordManager.PasswordData passwordData, int i, int i2) {
        if (shouldSynchronizeFrpCredential(passwordData, i2)) {
            com.android.server.utils.Slogf.d(TAG, "Syncing Gatekeeper-based FRP credential tied to user %d", java.lang.Integer.valueOf(i2));
            if (!isNoneCredential(passwordData)) {
                this.mStorage.writePersistentDataBlock(1, i2, i, passwordData.toBytes());
            } else {
                this.mStorage.writePersistentDataBlock(0, i2, 0, null);
            }
        }
    }

    private void synchronizeWeaverFrpPassword(@android.annotation.Nullable com.android.server.locksettings.SyntheticPasswordManager.PasswordData passwordData, int i, int i2, int i3) {
        if (shouldSynchronizeFrpCredential(passwordData, i2)) {
            com.android.server.utils.Slogf.d(TAG, "Syncing Weaver-based FRP credential tied to user %d", java.lang.Integer.valueOf(i2));
            if (!isNoneCredential(passwordData)) {
                this.mStorage.writePersistentDataBlock(2, i3, i, passwordData.toBytes());
            } else {
                this.mStorage.writePersistentDataBlock(0, 0, 0, null);
            }
        }
    }

    public boolean writeRepairModeCredentialLocked(long j, int i) {
        if (!shouldWriteRepairModeCredential(i)) {
            return false;
        }
        byte[] loadState = loadState(PASSWORD_DATA_NAME, j, i);
        if (loadState == null) {
            com.android.server.utils.Slogf.w(TAG, "Password data not found for user %d", java.lang.Integer.valueOf(i));
            return false;
        }
        com.android.server.locksettings.SyntheticPasswordManager.PasswordData fromBytes = com.android.server.locksettings.SyntheticPasswordManager.PasswordData.fromBytes(loadState);
        if (isNoneCredential(fromBytes)) {
            com.android.server.utils.Slogf.w(TAG, "User %d has NONE credential", java.lang.Integer.valueOf(i));
            return false;
        }
        com.android.server.utils.Slogf.d(TAG, "Writing repair mode credential tied to user %d", java.lang.Integer.valueOf(i));
        int loadWeaverSlot = loadWeaverSlot(j, i);
        if (loadWeaverSlot != -1) {
            this.mStorage.writeRepairModePersistentData(2, loadWeaverSlot, fromBytes.toBytes());
        } else {
            this.mStorage.writeRepairModePersistentData(1, i, fromBytes.toBytes());
        }
        return true;
    }

    private boolean shouldWriteRepairModeCredential(int i) {
        if (!com.android.internal.widget.LockPatternUtils.canUserEnterRepairMode(this.mContext, this.mUserManager.getUserInfo(i))) {
            com.android.server.utils.Slogf.w(TAG, "User %d can't enter repair mode", java.lang.Integer.valueOf(i));
            return false;
        }
        if (com.android.internal.widget.LockPatternUtils.isRepairModeActive(this.mContext)) {
            android.util.Slog.w(TAG, "Can't write repair mode credential while repair mode is already active");
            return false;
        }
        if (com.android.internal.widget.LockPatternUtils.isGsiRunning()) {
            android.util.Slog.w(TAG, "Can't write repair mode credential while GSI is running");
            return false;
        }
        return true;
    }

    public long addPendingToken(byte[] bArr, int i, int i2, @android.annotation.Nullable com.android.internal.widget.LockPatternUtils.EscrowTokenStateChangeCallback escrowTokenStateChangeCallback) {
        long generateProtectorId = generateProtectorId();
        if (!this.tokenMap.containsKey(java.lang.Integer.valueOf(i2))) {
            this.tokenMap.put(java.lang.Integer.valueOf(i2), new android.util.ArrayMap<>());
        }
        com.android.server.locksettings.SyntheticPasswordManager.TokenData tokenData = new com.android.server.locksettings.SyntheticPasswordManager.TokenData();
        tokenData.mType = i;
        byte[] randomBytes = com.android.server.locksettings.SecureRandomUtils.randomBytes(16384);
        if (getWeaverService() != null) {
            tokenData.weaverSecret = com.android.server.locksettings.SecureRandomUtils.randomBytes(this.mWeaverConfig.valueSize);
            tokenData.secdiscardableOnDisk = com.android.server.locksettings.SyntheticPasswordCrypto.encrypt(tokenData.weaverSecret, PERSONALIZATION_WEAVER_TOKEN, randomBytes);
        } else {
            tokenData.secdiscardableOnDisk = randomBytes;
            tokenData.weaverSecret = null;
        }
        tokenData.aggregatedSecret = transformUnderSecdiscardable(bArr, randomBytes);
        tokenData.mCallback = escrowTokenStateChangeCallback;
        this.tokenMap.get(java.lang.Integer.valueOf(i2)).put(java.lang.Long.valueOf(generateProtectorId), tokenData);
        return generateProtectorId;
    }

    public java.util.Set<java.lang.Long> getPendingTokensForUser(int i) {
        if (!this.tokenMap.containsKey(java.lang.Integer.valueOf(i))) {
            return java.util.Collections.emptySet();
        }
        return new android.util.ArraySet(this.tokenMap.get(java.lang.Integer.valueOf(i)).keySet());
    }

    public boolean removePendingToken(long j, int i) {
        return this.tokenMap.containsKey(java.lang.Integer.valueOf(i)) && this.tokenMap.get(java.lang.Integer.valueOf(i)).remove(java.lang.Long.valueOf(j)) != null;
    }

    public boolean createTokenBasedProtector(long j, com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword syntheticPassword, int i) {
        com.android.server.locksettings.SyntheticPasswordManager.TokenData tokenData;
        if (!this.tokenMap.containsKey(java.lang.Integer.valueOf(i)) || (tokenData = this.tokenMap.get(java.lang.Integer.valueOf(i)).get(java.lang.Long.valueOf(j))) == null) {
            return false;
        }
        if (!loadEscrowData(syntheticPassword, i)) {
            android.util.Slog.w(TAG, "User is not escrowable");
            return false;
        }
        com.android.server.utils.Slogf.i(TAG, "Creating token-based protector %016x for user %d", java.lang.Long.valueOf(j), java.lang.Integer.valueOf(i));
        android.hardware.weaver.IWeaver weaverService = getWeaverService();
        if (weaverService != null) {
            int nextAvailableWeaverSlot = getNextAvailableWeaverSlot();
            com.android.server.utils.Slogf.i(TAG, "Using Weaver slot %d for new token-based protector", java.lang.Integer.valueOf(nextAvailableWeaverSlot));
            if (weaverEnroll(weaverService, nextAvailableWeaverSlot, null, tokenData.weaverSecret) == null) {
                android.util.Slog.e(TAG, "Failed to enroll weaver secret when activating token");
                return false;
            }
            saveWeaverSlot(nextAvailableWeaverSlot, j, i);
            this.mPasswordSlotManager.markSlotInUse(nextAvailableWeaverSlot);
        }
        saveSecdiscardable(j, tokenData.secdiscardableOnDisk, i);
        createSyntheticPasswordBlob(j, getTokenBasedProtectorType(tokenData.mType), syntheticPassword, tokenData.aggregatedSecret, 0L, i);
        syncState(i);
        this.tokenMap.get(java.lang.Integer.valueOf(i)).remove(java.lang.Long.valueOf(j));
        if (tokenData.mCallback != null) {
            tokenData.mCallback.onEscrowTokenActivated(j, i);
            return true;
        }
        return true;
    }

    private void createSyntheticPasswordBlob(long j, byte b, com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword syntheticPassword, byte[] bArr, long j2, int i) {
        byte[] escrowSecret;
        if (b == 1 || b == 2) {
            escrowSecret = syntheticPassword.getEscrowSecret();
        } else {
            escrowSecret = syntheticPassword.getSyntheticPassword();
        }
        saveState(SP_BLOB_NAME, com.android.server.locksettings.SyntheticPasswordManager.SyntheticPasswordBlob.create(syntheticPassword.mVersion == 3 ? (byte) 3 : (byte) 2, b, createSpBlob(getProtectorKeyAlias(j), escrowSecret, bArr, j2)).toByte(), j, i);
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x015e  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0169  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x010e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public com.android.server.locksettings.SyntheticPasswordManager.AuthenticationResult unlockLskfBasedProtector(android.service.gatekeeper.IGateKeeperService iGateKeeperService, long j, @android.annotation.NonNull com.android.internal.widget.LockscreenCredential lockscreenCredential, int i, com.android.internal.widget.ICheckCredentialProgressCallback iCheckCredentialProgressCallback) {
        com.android.server.locksettings.SyntheticPasswordManager.PasswordData passwordData;
        int i2;
        byte[] loadSecdiscardable;
        long j2;
        byte[] bArr;
        android.service.gatekeeper.GateKeeperResponse gateKeeperResponse;
        com.android.server.locksettings.SyntheticPasswordManager.AuthenticationResult authenticationResult = new com.android.server.locksettings.SyntheticPasswordManager.AuthenticationResult();
        long j3 = 0;
        if (j != 0) {
            byte[] loadState = loadState(PASSWORD_DATA_NAME, j, i);
            if (loadState == null) {
                passwordData = null;
                i2 = -1;
            } else {
                com.android.server.locksettings.SyntheticPasswordManager.PasswordData fromBytes = com.android.server.locksettings.SyntheticPasswordManager.PasswordData.fromBytes(loadState);
                i2 = fromBytes.credentialType;
                passwordData = fromBytes;
            }
            if (!lockscreenCredential.checkAgainstStoredType(i2)) {
                com.android.server.utils.Slogf.e(TAG, "Credential type mismatch: stored type is %s but provided type is %s", com.android.internal.widget.LockPatternUtils.credentialTypeToString(i2), com.android.internal.widget.LockPatternUtils.credentialTypeToString(lockscreenCredential.getType()));
                authenticationResult.gkResponse = com.android.internal.widget.VerifyCredentialResponse.ERROR;
                return authenticationResult;
            }
            byte[] stretchLskf = stretchLskf(lockscreenCredential, passwordData);
            int loadWeaverSlot = loadWeaverSlot(j, i);
            if (loadWeaverSlot != -1) {
                android.hardware.weaver.IWeaver weaverService = getWeaverService();
                if (weaverService == null) {
                    android.util.Slog.e(TAG, "Protector uses Weaver, but Weaver is unavailable");
                    authenticationResult.gkResponse = com.android.internal.widget.VerifyCredentialResponse.ERROR;
                    return authenticationResult;
                }
                authenticationResult.gkResponse = weaverVerify(weaverService, loadWeaverSlot, stretchedLskfToWeaverKey(stretchLskf));
                if (authenticationResult.gkResponse.getResponseCode() != 0) {
                    return authenticationResult;
                }
                byte[] transformUnderWeaverSecret = transformUnderWeaverSecret(stretchLskf, authenticationResult.gkResponse.getGatekeeperHAT());
                j2 = 0;
                bArr = transformUnderWeaverSecret;
            } else {
                if (passwordData == null || passwordData.passwordHandle == null) {
                    if (!lockscreenCredential.isNone()) {
                        android.util.Slog.e(TAG, "Missing Gatekeeper password handle for nonempty LSKF");
                        authenticationResult.gkResponse = com.android.internal.widget.VerifyCredentialResponse.ERROR;
                        return authenticationResult;
                    }
                } else {
                    byte[] stretchedLskfToGkPassword = stretchedLskfToGkPassword(stretchLskf);
                    try {
                        android.service.gatekeeper.GateKeeperResponse verifyChallenge = iGateKeeperService.verifyChallenge(fakeUserId(i), 0L, passwordData.passwordHandle, stretchedLskfToGkPassword);
                        int responseCode = verifyChallenge.getResponseCode();
                        if (responseCode == 0) {
                            authenticationResult.gkResponse = com.android.internal.widget.VerifyCredentialResponse.OK;
                            if (verifyChallenge.getShouldReEnroll()) {
                                try {
                                } catch (android.os.RemoteException e) {
                                    e = e;
                                }
                                try {
                                    gateKeeperResponse = iGateKeeperService.enroll(fakeUserId(i), passwordData.passwordHandle, stretchedLskfToGkPassword, stretchedLskfToGkPassword);
                                } catch (android.os.RemoteException e2) {
                                    e = e2;
                                    android.util.Slog.w(TAG, "Fail to invoke gatekeeper.enroll", e);
                                    gateKeeperResponse = android.service.gatekeeper.GateKeeperResponse.ERROR;
                                    if (gateKeeperResponse.getResponseCode() != 0) {
                                    }
                                    j3 = sidFromPasswordHandle(passwordData.passwordHandle);
                                    loadSecdiscardable = loadSecdiscardable(j, i);
                                    if (loadSecdiscardable != null) {
                                    }
                                }
                                if (gateKeeperResponse.getResponseCode() != 0) {
                                    passwordData.passwordHandle = gateKeeperResponse.getPayload();
                                    passwordData.credentialType = lockscreenCredential.getType();
                                    saveState(PASSWORD_DATA_NAME, passwordData.toBytes(), j, i);
                                    syncState(i);
                                    synchronizeGatekeeperFrpPassword(passwordData, 0, i);
                                } else {
                                    android.util.Slog.w(TAG, "Fail to re-enroll user password for user " + i);
                                }
                            }
                            j3 = sidFromPasswordHandle(passwordData.passwordHandle);
                        } else {
                            if (responseCode == 1) {
                                authenticationResult.gkResponse = com.android.internal.widget.VerifyCredentialResponse.fromTimeout(verifyChallenge.getTimeout());
                                return authenticationResult;
                            }
                            authenticationResult.gkResponse = com.android.internal.widget.VerifyCredentialResponse.ERROR;
                            return authenticationResult;
                        }
                    } catch (android.os.RemoteException e3) {
                        android.util.Slog.e(TAG, "gatekeeper verify failed", e3);
                        authenticationResult.gkResponse = com.android.internal.widget.VerifyCredentialResponse.ERROR;
                        return authenticationResult;
                    }
                }
                loadSecdiscardable = loadSecdiscardable(j, i);
                if (loadSecdiscardable != null) {
                    android.util.Slog.e(TAG, "secdiscardable file not found");
                    authenticationResult.gkResponse = com.android.internal.widget.VerifyCredentialResponse.ERROR;
                    return authenticationResult;
                }
                byte[] transformUnderSecdiscardable = transformUnderSecdiscardable(stretchLskf, loadSecdiscardable);
                j2 = j3;
                bArr = transformUnderSecdiscardable;
            }
            if (iCheckCredentialProgressCallback != null) {
                try {
                    iCheckCredentialProgressCallback.onCredentialVerified();
                } catch (android.os.RemoteException e4) {
                    android.util.Slog.w(TAG, "progressCallback throws exception", e4);
                }
            }
            authenticationResult.syntheticPassword = unwrapSyntheticPasswordBlob(j, (byte) 0, bArr, j2, i);
            authenticationResult.gkResponse = verifyChallenge(iGateKeeperService, authenticationResult.syntheticPassword, 0L, i);
            if (authenticationResult.syntheticPassword != null && !lockscreenCredential.isNone() && !hasPasswordMetrics(j, i)) {
                savePasswordMetrics(lockscreenCredential, authenticationResult.syntheticPassword, j, i);
                syncState(i);
            }
            return authenticationResult;
        }
        com.android.server.utils.Slogf.wtf(TAG, "Synthetic password not found for user %d", java.lang.Integer.valueOf(i));
        authenticationResult.gkResponse = com.android.internal.widget.VerifyCredentialResponse.ERROR;
        return authenticationResult;
    }

    public boolean refreshPinLengthOnDisk(android.app.admin.PasswordMetrics passwordMetrics, long j, int i) {
        byte[] loadState;
        if (!isAutoPinConfirmationFeatureAvailable() || (loadState = loadState(PASSWORD_DATA_NAME, j, i)) == null) {
            return false;
        }
        com.android.server.locksettings.SyntheticPasswordManager.PasswordData fromBytes = com.android.server.locksettings.SyntheticPasswordManager.PasswordData.fromBytes(loadState);
        int derivePinLength = derivePinLength(passwordMetrics.length, passwordMetrics.credType == 3, i);
        if (fromBytes.pinLength != derivePinLength) {
            fromBytes.pinLength = derivePinLength;
            saveState(PASSWORD_DATA_NAME, fromBytes.toBytes(), j, i);
            syncState(i);
        }
        return true;
    }

    @android.annotation.NonNull
    public com.android.server.locksettings.SyntheticPasswordManager.AuthenticationResult unlockTokenBasedProtector(android.service.gatekeeper.IGateKeeperService iGateKeeperService, long j, byte[] bArr, int i) {
        byte[] loadState = loadState(SP_BLOB_NAME, j, i);
        if (loadState == null) {
            com.android.server.locksettings.SyntheticPasswordManager.AuthenticationResult authenticationResult = new com.android.server.locksettings.SyntheticPasswordManager.AuthenticationResult();
            authenticationResult.gkResponse = com.android.internal.widget.VerifyCredentialResponse.ERROR;
            com.android.server.utils.Slogf.w(TAG, "spblob not found for protector %016x, user %d", java.lang.Long.valueOf(j), java.lang.Integer.valueOf(i));
            return authenticationResult;
        }
        return unlockTokenBasedProtectorInternal(iGateKeeperService, j, com.android.server.locksettings.SyntheticPasswordManager.SyntheticPasswordBlob.fromBytes(loadState).mProtectorType, bArr, i);
    }

    @android.annotation.NonNull
    public com.android.server.locksettings.SyntheticPasswordManager.AuthenticationResult unlockStrongTokenBasedProtector(android.service.gatekeeper.IGateKeeperService iGateKeeperService, long j, byte[] bArr, int i) {
        return unlockTokenBasedProtectorInternal(iGateKeeperService, j, (byte) 1, bArr, i);
    }

    @android.annotation.NonNull
    public com.android.server.locksettings.SyntheticPasswordManager.AuthenticationResult unlockWeakTokenBasedProtector(android.service.gatekeeper.IGateKeeperService iGateKeeperService, long j, byte[] bArr, int i) {
        return unlockTokenBasedProtectorInternal(iGateKeeperService, j, (byte) 2, bArr, i);
    }

    @android.annotation.NonNull
    private com.android.server.locksettings.SyntheticPasswordManager.AuthenticationResult unlockTokenBasedProtectorInternal(android.service.gatekeeper.IGateKeeperService iGateKeeperService, long j, byte b, byte[] bArr, int i) {
        com.android.server.locksettings.SyntheticPasswordManager.AuthenticationResult authenticationResult = new com.android.server.locksettings.SyntheticPasswordManager.AuthenticationResult();
        byte[] loadSecdiscardable = loadSecdiscardable(j, i);
        if (loadSecdiscardable == null) {
            android.util.Slog.e(TAG, "secdiscardable file not found");
            authenticationResult.gkResponse = com.android.internal.widget.VerifyCredentialResponse.ERROR;
            return authenticationResult;
        }
        int loadWeaverSlot = loadWeaverSlot(j, i);
        if (loadWeaverSlot != -1) {
            android.hardware.weaver.IWeaver weaverService = getWeaverService();
            if (weaverService == null) {
                android.util.Slog.e(TAG, "Protector uses Weaver, but Weaver is unavailable");
                authenticationResult.gkResponse = com.android.internal.widget.VerifyCredentialResponse.ERROR;
                return authenticationResult;
            }
            com.android.internal.widget.VerifyCredentialResponse weaverVerify = weaverVerify(weaverService, loadWeaverSlot, null);
            if (weaverVerify.getResponseCode() != 0 || weaverVerify.getGatekeeperHAT() == null) {
                android.util.Slog.e(TAG, "Failed to retrieve Weaver secret when unlocking token-based protector");
                authenticationResult.gkResponse = com.android.internal.widget.VerifyCredentialResponse.ERROR;
                return authenticationResult;
            }
            loadSecdiscardable = com.android.server.locksettings.SyntheticPasswordCrypto.decrypt(weaverVerify.getGatekeeperHAT(), PERSONALIZATION_WEAVER_TOKEN, loadSecdiscardable);
        }
        authenticationResult.syntheticPassword = unwrapSyntheticPasswordBlob(j, b, transformUnderSecdiscardable(bArr, loadSecdiscardable), 0L, i);
        if (authenticationResult.syntheticPassword != null) {
            authenticationResult.gkResponse = verifyChallenge(iGateKeeperService, authenticationResult.syntheticPassword, 0L, i);
            if (authenticationResult.gkResponse == null) {
                authenticationResult.gkResponse = com.android.internal.widget.VerifyCredentialResponse.OK;
            }
        } else {
            authenticationResult.gkResponse = com.android.internal.widget.VerifyCredentialResponse.ERROR;
        }
        return authenticationResult;
    }

    private com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword unwrapSyntheticPasswordBlob(long j, byte b, byte[] bArr, long j2, int i) {
        byte[] decryptSpBlob;
        byte[] loadState = loadState(SP_BLOB_NAME, j, i);
        if (loadState == null) {
            return null;
        }
        com.android.server.locksettings.SyntheticPasswordManager.SyntheticPasswordBlob fromBytes = com.android.server.locksettings.SyntheticPasswordManager.SyntheticPasswordBlob.fromBytes(loadState);
        if (fromBytes.mVersion != 3 && fromBytes.mVersion != 2 && fromBytes.mVersion != 1) {
            throw new java.lang.IllegalArgumentException("Unknown blob version: " + ((int) fromBytes.mVersion));
        }
        if (fromBytes.mProtectorType != b) {
            throw new java.lang.IllegalArgumentException("Invalid protector type: " + ((int) fromBytes.mProtectorType));
        }
        if (fromBytes.mVersion == 1) {
            decryptSpBlob = com.android.server.locksettings.SyntheticPasswordCrypto.decryptBlobV1(getProtectorKeyAlias(j), fromBytes.mContent, bArr);
        } else {
            decryptSpBlob = decryptSpBlob(getProtectorKeyAlias(j), fromBytes.mContent, bArr);
        }
        if (decryptSpBlob == null) {
            android.util.Slog.e(TAG, "Fail to decrypt SP for user " + i);
            return null;
        }
        com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword syntheticPassword = new com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword(fromBytes.mVersion);
        if (fromBytes.mProtectorType == 1 || fromBytes.mProtectorType == 2) {
            if (!loadEscrowData(syntheticPassword, i)) {
                android.util.Slog.e(TAG, "User is not escrowable: " + i);
                return null;
            }
            syntheticPassword.recreateFromEscrow(decryptSpBlob);
        } else {
            syntheticPassword.recreateDirectly(decryptSpBlob);
        }
        if (fromBytes.mVersion == 1) {
            android.util.Slog.i(TAG, "Upgrading v1 SP blob for user " + i + ", protectorType = " + ((int) fromBytes.mProtectorType));
            createSyntheticPasswordBlob(j, fromBytes.mProtectorType, syntheticPassword, bArr, j2, i);
            syncState(i);
        }
        return syntheticPassword;
    }

    @android.annotation.Nullable
    public com.android.internal.widget.VerifyCredentialResponse verifyChallenge(android.service.gatekeeper.IGateKeeperService iGateKeeperService, @android.annotation.NonNull com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword syntheticPassword, long j, int i) {
        return verifyChallengeInternal(iGateKeeperService, syntheticPassword.deriveGkPassword(), j, i);
    }

    @android.annotation.Nullable
    protected com.android.internal.widget.VerifyCredentialResponse verifyChallengeInternal(android.service.gatekeeper.IGateKeeperService iGateKeeperService, @android.annotation.NonNull byte[] bArr, long j, int i) {
        android.service.gatekeeper.GateKeeperResponse gateKeeperResponse;
        byte[] loadSyntheticPasswordHandle = loadSyntheticPasswordHandle(i);
        if (loadSyntheticPasswordHandle == null) {
            return null;
        }
        try {
            android.service.gatekeeper.GateKeeperResponse verifyChallenge = iGateKeeperService.verifyChallenge(i, j, loadSyntheticPasswordHandle, bArr);
            int responseCode = verifyChallenge.getResponseCode();
            if (responseCode == 0) {
                com.android.internal.widget.VerifyCredentialResponse build = new com.android.internal.widget.VerifyCredentialResponse.Builder().setGatekeeperHAT(verifyChallenge.getPayload()).build();
                if (verifyChallenge.getShouldReEnroll()) {
                    try {
                        gateKeeperResponse = iGateKeeperService.enroll(i, loadSyntheticPasswordHandle, loadSyntheticPasswordHandle, bArr);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(TAG, "Failed to invoke gatekeeper.enroll", e);
                        gateKeeperResponse = android.service.gatekeeper.GateKeeperResponse.ERROR;
                    }
                    if (gateKeeperResponse.getResponseCode() == 0) {
                        saveSyntheticPasswordHandle(gateKeeperResponse.getPayload(), i);
                        return verifyChallengeInternal(iGateKeeperService, bArr, j, i);
                    }
                    android.util.Slog.w(TAG, "Fail to re-enroll SP handle for user " + i);
                }
                return build;
            }
            if (responseCode == 1) {
                android.util.Slog.e(TAG, "Gatekeeper verification of synthetic password failed with RESPONSE_RETRY");
                return com.android.internal.widget.VerifyCredentialResponse.fromTimeout(verifyChallenge.getTimeout());
            }
            android.util.Slog.e(TAG, "Gatekeeper verification of synthetic password failed with RESPONSE_ERROR");
            return com.android.internal.widget.VerifyCredentialResponse.ERROR;
        } catch (android.os.RemoteException e2) {
            android.util.Slog.e(TAG, "Fail to verify with gatekeeper " + i, e2);
            return com.android.internal.widget.VerifyCredentialResponse.ERROR;
        }
    }

    public boolean protectorExists(long j, int i) {
        return hasState(SP_BLOB_NAME, j, i);
    }

    public void destroyTokenBasedProtector(long j, int i) {
        com.android.server.utils.Slogf.i(TAG, "Destroying token-based protector %016x for user %d", java.lang.Long.valueOf(j), java.lang.Integer.valueOf(i));
        com.android.server.locksettings.SyntheticPasswordManager.SyntheticPasswordBlob fromBytes = com.android.server.locksettings.SyntheticPasswordManager.SyntheticPasswordBlob.fromBytes(loadState(SP_BLOB_NAME, j, i));
        destroyProtectorCommon(j, i);
        if (fromBytes.mProtectorType == 2) {
            notifyWeakEscrowTokenRemovedListeners(j, i);
        }
    }

    public void destroyAllWeakTokenBasedProtectors(int i) {
        java.util.Iterator<java.lang.Long> it = this.mStorage.listSyntheticPasswordProtectorsForUser(SP_BLOB_NAME, i).iterator();
        while (it.hasNext()) {
            long longValue = it.next().longValue();
            if (com.android.server.locksettings.SyntheticPasswordManager.SyntheticPasswordBlob.fromBytes(loadState(SP_BLOB_NAME, longValue, i)).mProtectorType == 2) {
                destroyTokenBasedProtector(longValue, i);
            }
        }
    }

    public void destroyLskfBasedProtector(long j, int i) {
        com.android.server.utils.Slogf.i(TAG, "Destroying LSKF-based protector %016x for user %d", java.lang.Long.valueOf(j), java.lang.Integer.valueOf(i));
        destroyProtectorCommon(j, i);
        destroyState(PASSWORD_DATA_NAME, j, i);
        destroyState(PASSWORD_METRICS_NAME, j, i);
    }

    private void destroyProtectorCommon(long j, int i) {
        destroyState(SP_BLOB_NAME, j, i);
        destroyProtectorKey(getProtectorKeyAlias(j));
        destroyState(SECDISCARDABLE_NAME, j, i);
        if (hasState(WEAVER_SLOT_NAME, j, i)) {
            destroyWeaverSlot(j, i);
        }
    }

    private byte[] transformUnderWeaverSecret(byte[] bArr, byte[] bArr2) {
        return com.android.internal.util.ArrayUtils.concat(new byte[][]{bArr, com.android.server.locksettings.SyntheticPasswordCrypto.personalizedHash(PERSONALIZATION_WEAVER_PASSWORD, bArr2)});
    }

    private byte[] transformUnderSecdiscardable(byte[] bArr, byte[] bArr2) {
        return com.android.internal.util.ArrayUtils.concat(new byte[][]{bArr, com.android.server.locksettings.SyntheticPasswordCrypto.personalizedHash(PERSONALIZATION_SECDISCARDABLE, bArr2)});
    }

    private byte[] createSecdiscardable(long j, int i) {
        byte[] randomBytes = com.android.server.locksettings.SecureRandomUtils.randomBytes(16384);
        saveSecdiscardable(j, randomBytes, i);
        return randomBytes;
    }

    private void saveSecdiscardable(long j, byte[] bArr, int i) {
        saveState(SECDISCARDABLE_NAME, bArr, j, i);
    }

    private byte[] loadSecdiscardable(long j, int i) {
        return loadState(SECDISCARDABLE_NAME, j, i);
    }

    private byte getTokenBasedProtectorType(int i) {
        switch (i) {
            case 1:
                return (byte) 2;
            default:
                return (byte) 1;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean hasPasswordData(long j, int i) {
        return hasState(PASSWORD_DATA_NAME, j, i);
    }

    @android.annotation.Nullable
    public android.app.admin.PasswordMetrics getPasswordMetrics(com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword syntheticPassword, long j, int i) {
        byte[] loadState = loadState(PASSWORD_METRICS_NAME, j, i);
        if (loadState == null) {
            com.android.server.utils.Slogf.e(TAG, "Failed to read password metrics file for user %d", java.lang.Integer.valueOf(i));
            return null;
        }
        byte[] decrypt = com.android.server.locksettings.SyntheticPasswordCrypto.decrypt(syntheticPassword.deriveMetricsKey(), new byte[0], loadState);
        if (decrypt == null) {
            com.android.server.utils.Slogf.e(TAG, "Failed to decrypt password metrics file for user %d", java.lang.Integer.valueOf(i));
            return null;
        }
        return com.android.server.locksettings.VersionedPasswordMetrics.deserialize(decrypt).getMetrics();
    }

    private void savePasswordMetrics(com.android.internal.widget.LockscreenCredential lockscreenCredential, com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword syntheticPassword, long j, int i) {
        saveState(PASSWORD_METRICS_NAME, com.android.server.locksettings.SyntheticPasswordCrypto.encrypt(syntheticPassword.deriveMetricsKey(), new byte[0], new com.android.server.locksettings.VersionedPasswordMetrics(lockscreenCredential).serialize()), j, i);
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean hasPasswordMetrics(long j, int i) {
        return hasState(PASSWORD_METRICS_NAME, j, i);
    }

    private boolean hasState(java.lang.String str, long j, int i) {
        return !com.android.internal.util.ArrayUtils.isEmpty(loadState(str, j, i));
    }

    private byte[] loadState(java.lang.String str, long j, int i) {
        return this.mStorage.readSyntheticPasswordState(i, j, str);
    }

    private void saveState(java.lang.String str, byte[] bArr, long j, int i) {
        this.mStorage.writeSyntheticPasswordState(i, j, str, bArr);
    }

    private void syncState(int i) {
        this.mStorage.syncSyntheticPasswordState(i);
    }

    private void destroyState(java.lang.String str, long j, int i) {
        this.mStorage.deleteSyntheticPasswordState(i, j, str);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected byte[] decryptSpBlob(java.lang.String str, byte[] bArr, byte[] bArr2) {
        return com.android.server.locksettings.SyntheticPasswordCrypto.decryptBlob(str, bArr, bArr2);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected byte[] createSpBlob(java.lang.String str, byte[] bArr, byte[] bArr2, long j) {
        return com.android.server.locksettings.SyntheticPasswordCrypto.createBlob(str, bArr, bArr2, j);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void destroyProtectorKey(java.lang.String str) {
        com.android.server.locksettings.SyntheticPasswordCrypto.destroyProtectorKey(str);
    }

    private static long generateProtectorId() {
        long randomLong;
        do {
            randomLong = com.android.server.locksettings.SecureRandomUtils.randomLong();
        } while (randomLong == 0);
        return randomLong;
    }

    @com.android.internal.annotations.VisibleForTesting
    static int fakeUserId(int i) {
        return i + 100000;
    }

    private java.lang.String getProtectorKeyAlias(long j) {
        return android.text.TextUtils.formatSimple("%s%x", new java.lang.Object[]{PROTECTOR_KEY_ALIAS_PREFIX, java.lang.Long.valueOf(j)});
    }

    @com.android.internal.annotations.VisibleForTesting
    byte[] stretchLskf(com.android.internal.widget.LockscreenCredential lockscreenCredential, @android.annotation.Nullable com.android.server.locksettings.SyntheticPasswordManager.PasswordData passwordData) {
        byte[] credential = lockscreenCredential.isNone() ? DEFAULT_PASSWORD : lockscreenCredential.getCredential();
        if (passwordData == null) {
            com.android.internal.util.Preconditions.checkArgument(lockscreenCredential.isNone());
            return java.util.Arrays.copyOf(credential, 32);
        }
        return scrypt(credential, passwordData.salt, 1 << passwordData.scryptLogN, 1 << passwordData.scryptLogR, 1 << passwordData.scryptLogP, 32);
    }

    private byte[] stretchedLskfToGkPassword(byte[] bArr) {
        return com.android.server.locksettings.SyntheticPasswordCrypto.personalizedHash(PERSONALIZATION_USER_GK_AUTH, bArr);
    }

    private byte[] stretchedLskfToWeaverKey(byte[] bArr) {
        byte[] personalizedHash = com.android.server.locksettings.SyntheticPasswordCrypto.personalizedHash(PERSONALIZATION_WEAVER_KEY, bArr);
        if (personalizedHash.length < this.mWeaverConfig.keySize) {
            throw new java.lang.IllegalArgumentException("weaver key length too small");
        }
        return java.util.Arrays.copyOf(personalizedHash, this.mWeaverConfig.keySize);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected long sidFromPasswordHandle(byte[] bArr) {
        return nativeSidFromPasswordHandle(bArr);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected byte[] scrypt(byte[] bArr, byte[] bArr2, int i, int i2, int i3, int i4) {
        return new android.security.Scrypt().scrypt(bArr, bArr2, i, i2, i3, i4);
    }

    @com.android.internal.annotations.VisibleForTesting
    static byte[] bytesToHex(byte[] bArr) {
        return libcore.util.HexEncoding.encodeToString(bArr).getBytes();
    }

    public boolean migrateKeyNamespace() {
        java.util.Iterator<java.util.List<java.lang.Long>> it = this.mStorage.listSyntheticPasswordProtectorsForAllUsers(SP_BLOB_NAME).values().iterator();
        boolean z = true;
        while (it.hasNext()) {
            java.util.Iterator<java.lang.Long> it2 = it.next().iterator();
            while (it2.hasNext()) {
                z &= com.android.server.locksettings.SyntheticPasswordCrypto.migrateLockSettingsKey(getProtectorKeyAlias(it2.next().longValue()));
            }
        }
        return z;
    }

    public boolean registerWeakEscrowTokenRemovedListener(com.android.internal.widget.IWeakEscrowTokenRemovedListener iWeakEscrowTokenRemovedListener) {
        return this.mListeners.register(iWeakEscrowTokenRemovedListener);
    }

    public boolean unregisterWeakEscrowTokenRemovedListener(com.android.internal.widget.IWeakEscrowTokenRemovedListener iWeakEscrowTokenRemovedListener) {
        return this.mListeners.unregister(iWeakEscrowTokenRemovedListener);
    }

    private void notifyWeakEscrowTokenRemovedListeners(long j, int i) {
        int beginBroadcast = this.mListeners.beginBroadcast();
        while (beginBroadcast > 0) {
            beginBroadcast--;
            try {
                try {
                    this.mListeners.getBroadcastItem(beginBroadcast).onWeakEscrowTokenRemoved(j, i);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(TAG, "Exception while notifying WeakEscrowTokenRemovedListener.", e);
                }
            } finally {
                this.mListeners.finishBroadcast();
            }
        }
    }

    public void writeVendorAuthSecret(@android.annotation.NonNull byte[] bArr, @android.annotation.NonNull com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword syntheticPassword, int i) {
        saveState(VENDOR_AUTH_SECRET_NAME, com.android.server.locksettings.SyntheticPasswordCrypto.encrypt(syntheticPassword.deriveVendorAuthSecretEncryptionKey(), new byte[0], bArr), 0L, i);
        syncState(i);
    }

    @android.annotation.Nullable
    public byte[] readVendorAuthSecret(@android.annotation.NonNull com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword syntheticPassword, int i) {
        byte[] loadState = loadState(VENDOR_AUTH_SECRET_NAME, 0L, i);
        if (loadState == null) {
            return null;
        }
        return com.android.server.locksettings.SyntheticPasswordCrypto.decrypt(syntheticPassword.deriveVendorAuthSecretEncryptionKey(), new byte[0], loadState);
    }
}
