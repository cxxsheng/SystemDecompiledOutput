package com.android.internal.widget;

/* loaded from: classes5.dex */
public class LockPatternUtils {
    public static final java.lang.String AUTO_PIN_CONFIRM = "lockscreen.auto_pin_confirm";
    private static final java.lang.String CREDENTIAL_TYPE_API = "getCredentialType";
    public static final int CREDENTIAL_TYPE_NONE = -1;
    public static final int CREDENTIAL_TYPE_PASSWORD = 4;
    public static final int CREDENTIAL_TYPE_PASSWORD_OR_PIN = 2;
    public static final int CREDENTIAL_TYPE_PATTERN = 1;
    public static final int CREDENTIAL_TYPE_PIN = 3;
    public static final java.lang.String CURRENT_LSKF_BASED_PROTECTOR_ID_KEY = "sp-handle";
    public static final java.lang.String DISABLE_LOCKSCREEN_KEY = "lockscreen.disabled";
    private static final java.lang.String ENABLED_TRUST_AGENTS = "lockscreen.enabledtrustagents";
    public static final byte[] ENCRYPTED_REMOTE_CREDENTIALS_HEADER = "encrypted_remote_credentials".getBytes(java.nio.charset.StandardCharsets.UTF_8);
    public static final int FAILED_ATTEMPTS_BEFORE_WIPE_GRACE = 5;
    public static final long FAILED_ATTEMPT_COUNTDOWN_INTERVAL_MS = 1000;
    public static final java.lang.String FLAG_ENABLE_AUTO_PIN_CONFIRMATION = "AutoPinConfirmation__enable_auto_pin_confirmation";
    private static final boolean FRP_CREDENTIAL_ENABLED = true;
    private static final java.lang.String GSI_RUNNING_PROP = "ro.gsid.image_running";
    private static final java.lang.String IS_TRUST_USUALLY_MANAGED = "lockscreen.istrustusuallymanaged";
    private static final java.lang.String KNOWN_TRUST_AGENTS = "lockscreen.knowntrustagents";
    public static final java.lang.String LOCKSCREEN_POWER_BUTTON_INSTANTLY_LOCKS = "lockscreen.power_button_instantly_locks";

    @java.lang.Deprecated
    public static final java.lang.String LOCKSCREEN_WIDGETS_ENABLED = "lockscreen.widgets_enabled";
    public static final java.lang.String LOCK_PASSWORD_SALT_KEY = "lockscreen.password_salt";
    private static final java.lang.String LOCK_PIN_ENHANCED_PRIVACY = "pin_enhanced_privacy";
    private static final java.lang.String LOCK_SCREEN_DEVICE_OWNER_INFO = "lockscreen.device_owner_info";
    private static final java.lang.String LOCK_SCREEN_OWNER_INFO = "lock_screen_owner_info";
    private static final java.lang.String LOCK_SCREEN_OWNER_INFO_ENABLED = "lock_screen_owner_info_enabled";
    public static final int MIN_AUTO_PIN_REQUIREMENT_LENGTH = 6;
    public static final int MIN_LOCK_PASSWORD_SIZE = 4;
    public static final int MIN_LOCK_PATTERN_SIZE = 4;
    public static final int MIN_PATTERN_REGISTER_FAIL = 4;
    public static final java.lang.String PASSWORD_HISTORY_DELIMITER = ",";
    public static final java.lang.String PASSWORD_HISTORY_KEY = "lockscreen.passwordhistory";

    @java.lang.Deprecated
    public static final java.lang.String PASSWORD_TYPE_ALTERNATE_KEY = "lockscreen.password_type_alternate";
    public static final java.lang.String PASSWORD_TYPE_KEY = "lockscreen.password_type";
    public static final byte PATTERN_SIZE_DEFAULT = 3;
    public static final int PIN_LENGTH_UNAVAILABLE = -1;
    private static final java.lang.String TAG = "LockPatternUtils";
    public static final int USER_FRP = -9999;
    public static final int USER_REPAIR_MODE = -9998;
    public static final int VERIFY_FLAG_REQUEST_GK_PW_HANDLE = 1;
    public static final int VERIFY_FLAG_WRITE_REPAIR_MODE_PW = 2;
    private final android.content.ContentResolver mContentResolver;
    private final android.content.Context mContext;
    private final android.app.PropertyInvalidatedCache<java.lang.Integer, java.lang.Integer> mCredentialTypeCache;
    private final android.app.PropertyInvalidatedCache.QueryHandler<java.lang.Integer, java.lang.Integer> mCredentialTypeQuery;
    private android.app.admin.DevicePolicyManager mDevicePolicyManager;
    private final android.os.Handler mHandler;
    private java.lang.Boolean mHasSecureLockScreen;
    private com.android.internal.widget.ILockSettings mLockSettingsService;
    private final android.util.SparseLongArray mLockoutDeadlines;
    private android.os.UserManager mUserManager;
    private java.util.HashMap<android.os.UserHandle, android.os.UserManager> mUserManagerCache;

    public interface CheckCredentialProgressCallback {
        void onEarlyMatched();
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CredentialType {
    }

    public interface EscrowTokenStateChangeCallback {
        void onEscrowTokenActivated(long j, int i);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface VerifyFlag {
    }

    public static java.lang.String credentialTypeToString(int i) {
        switch (i) {
            case -1:
                return android.security.keystore.KeyProperties.DIGEST_NONE;
            case 0:
            case 2:
            default:
                return "UNKNOWN_" + i;
            case 1:
                return "PATTERN";
            case 3:
                return "PIN";
            case 4:
                return "PASSWORD";
        }
    }

    public boolean isTrustUsuallyManaged(int i) {
        if (!(this.mLockSettingsService instanceof com.android.internal.widget.ILockSettings.Stub)) {
            throw new java.lang.IllegalStateException("May only be called by TrustManagerService. Use TrustManager.isTrustUsuallyManaged()");
        }
        try {
            return getLockSettings().getBoolean(IS_TRUST_USUALLY_MANAGED, false, i);
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public void setTrustUsuallyManaged(boolean z, int i) {
        try {
            getLockSettings().setBoolean(IS_TRUST_USUALLY_MANAGED, z, i);
        } catch (android.os.RemoteException e) {
        }
    }

    public void userPresent(int i) {
        try {
            getLockSettings().userPresent(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static final class RequestThrottledException extends java.lang.Exception {
        private int mTimeoutMs;

        public RequestThrottledException(int i) {
            this.mTimeoutMs = i;
        }

        public int getTimeoutMs() {
            return this.mTimeoutMs;
        }
    }

    public android.app.admin.DevicePolicyManager getDevicePolicyManager() {
        if (this.mDevicePolicyManager == null) {
            this.mDevicePolicyManager = (android.app.admin.DevicePolicyManager) this.mContext.getSystemService(android.content.Context.DEVICE_POLICY_SERVICE);
            if (this.mDevicePolicyManager == null) {
                android.util.Log.e(TAG, "Can't get DevicePolicyManagerService: is it running?", new java.lang.IllegalStateException("Stack trace:"));
            }
        }
        return this.mDevicePolicyManager;
    }

    private android.os.UserManager getUserManager() {
        if (this.mUserManager == null) {
            this.mUserManager = android.os.UserManager.get(this.mContext);
        }
        return this.mUserManager;
    }

    private android.os.UserManager getUserManager(int i) {
        android.os.UserHandle of = android.os.UserHandle.of(i);
        if (this.mUserManagerCache.containsKey(of)) {
            return this.mUserManagerCache.get(of);
        }
        try {
            android.os.UserManager userManager = (android.os.UserManager) this.mContext.createPackageContextAsUser("system", 0, of).getSystemService(android.os.UserManager.class);
            this.mUserManagerCache.put(of, userManager);
            return userManager;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            throw new java.lang.RuntimeException("Failed to create context for user " + of, e);
        }
    }

    private android.app.trust.TrustManager getTrustManager() {
        android.app.trust.TrustManager trustManager = (android.app.trust.TrustManager) this.mContext.getSystemService(android.content.Context.TRUST_SERVICE);
        if (trustManager == null) {
            android.util.Log.e(TAG, "Can't get TrustManagerService: is it running?", new java.lang.IllegalStateException("Stack trace:"));
        }
        return trustManager;
    }

    public LockPatternUtils(android.content.Context context) {
        this(context, null);
    }

    public LockPatternUtils(android.content.Context context, com.android.internal.widget.ILockSettings iLockSettings) {
        this.mLockoutDeadlines = new android.util.SparseLongArray();
        this.mUserManagerCache = new java.util.HashMap<>();
        this.mCredentialTypeQuery = new android.app.PropertyInvalidatedCache.QueryHandler<java.lang.Integer, java.lang.Integer>() { // from class: com.android.internal.widget.LockPatternUtils.1
            @Override // android.app.PropertyInvalidatedCache.QueryHandler
            public java.lang.Integer apply(java.lang.Integer num) {
                try {
                    return java.lang.Integer.valueOf(com.android.internal.widget.LockPatternUtils.this.getLockSettings().getCredentialType(num.intValue()));
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(com.android.internal.widget.LockPatternUtils.TAG, "failed to get credential type", e);
                    return -1;
                }
            }

            @Override // android.app.PropertyInvalidatedCache.QueryHandler
            public boolean shouldBypassCache(java.lang.Integer num) {
                return com.android.internal.widget.LockPatternUtils.isSpecialUserId(num.intValue());
            }
        };
        this.mCredentialTypeCache = new android.app.PropertyInvalidatedCache<>(4, "system_server", CREDENTIAL_TYPE_API, CREDENTIAL_TYPE_API, this.mCredentialTypeQuery);
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        android.os.Looper myLooper = android.os.Looper.myLooper();
        this.mHandler = myLooper != null ? new android.os.Handler(myLooper) : null;
        this.mLockSettingsService = iLockSettings;
    }

    public com.android.internal.widget.ILockSettings getLockSettings() {
        if (this.mLockSettingsService == null) {
            this.mLockSettingsService = com.android.internal.widget.ILockSettings.Stub.asInterface(android.os.ServiceManager.getService("lock_settings"));
        }
        return this.mLockSettingsService;
    }

    public int getRequestedMinimumPasswordLength(int i) {
        return getDevicePolicyManager().getPasswordMinimumLength(null, i);
    }

    public int getMaximumPasswordLength(int i) {
        return getDevicePolicyManager().getPasswordMaximumLength(i);
    }

    public android.app.admin.PasswordMetrics getRequestedPasswordMetrics(int i) {
        return getRequestedPasswordMetrics(i, false);
    }

    public android.app.admin.PasswordMetrics getRequestedPasswordMetrics(int i, boolean z) {
        return getDevicePolicyManager().getPasswordMinimumMetrics(i, z);
    }

    private int getRequestedPasswordHistoryLength(int i) {
        return getDevicePolicyManager().getPasswordHistoryLength(null, i);
    }

    public int getRequestedPasswordComplexity(int i) {
        return getRequestedPasswordComplexity(i, false);
    }

    public int getRequestedPasswordComplexity(int i, boolean z) {
        return getDevicePolicyManager().getAggregatedPasswordComplexityForUser(i, z);
    }

    public void reportFailedPasswordAttempt(int i) {
        if (isSpecialUserId(this.mContext, i, true)) {
            return;
        }
        getDevicePolicyManager().reportFailedPasswordAttempt(i);
        getTrustManager().reportUnlockAttempt(false, i);
    }

    public void reportSuccessfulPasswordAttempt(int i) {
        if (isSpecialUserId(this.mContext, i, true)) {
            return;
        }
        getDevicePolicyManager().reportSuccessfulPasswordAttempt(i);
        getTrustManager().reportUnlockAttempt(true, i);
    }

    public void reportPasswordLockout(int i, int i2) {
        if (isSpecialUserId(this.mContext, i2, true)) {
            return;
        }
        getTrustManager().reportUnlockLockout(i, i2);
    }

    public int getCurrentFailedPasswordAttempts(int i) {
        if (isSpecialUserId(this.mContext, i, true)) {
            return 0;
        }
        return getDevicePolicyManager().getCurrentFailedPasswordAttempts(i);
    }

    public int getMaximumFailedPasswordsForWipe(int i) {
        if (isSpecialUserId(this.mContext, i, true)) {
            return 0;
        }
        return getDevicePolicyManager().getMaximumFailedPasswordsForWipe(null, i);
    }

    public com.android.internal.widget.VerifyCredentialResponse verifyCredential(com.android.internal.widget.LockscreenCredential lockscreenCredential, int i, int i2) {
        throwIfCalledOnMainThread();
        try {
            com.android.internal.widget.VerifyCredentialResponse verifyCredential = getLockSettings().verifyCredential(lockscreenCredential, i, i2);
            if (verifyCredential == null) {
                return com.android.internal.widget.VerifyCredentialResponse.ERROR;
            }
            return verifyCredential;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "failed to verify credential", e);
            return com.android.internal.widget.VerifyCredentialResponse.ERROR;
        }
    }

    public com.android.internal.widget.VerifyCredentialResponse verifyGatekeeperPasswordHandle(long j, long j2, int i) {
        try {
            com.android.internal.widget.VerifyCredentialResponse verifyGatekeeperPasswordHandle = getLockSettings().verifyGatekeeperPasswordHandle(j, j2, i);
            if (verifyGatekeeperPasswordHandle == null) {
                return com.android.internal.widget.VerifyCredentialResponse.ERROR;
            }
            return verifyGatekeeperPasswordHandle;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "failed to verify gatekeeper password", e);
            return com.android.internal.widget.VerifyCredentialResponse.ERROR;
        }
    }

    public void removeGatekeeperPasswordHandle(long j) {
        try {
            getLockSettings().removeGatekeeperPasswordHandle(j);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "failed to remove gatekeeper password handle", e);
        }
    }

    public boolean checkCredential(com.android.internal.widget.LockscreenCredential lockscreenCredential, int i, com.android.internal.widget.LockPatternUtils.CheckCredentialProgressCallback checkCredentialProgressCallback) throws com.android.internal.widget.LockPatternUtils.RequestThrottledException {
        throwIfCalledOnMainThread();
        try {
            com.android.internal.widget.VerifyCredentialResponse checkCredential = getLockSettings().checkCredential(lockscreenCredential, i, wrapCallback(checkCredentialProgressCallback));
            if (checkCredential == null) {
                return false;
            }
            if (checkCredential.getResponseCode() == 0) {
                return true;
            }
            if (checkCredential.getResponseCode() != 1) {
                return false;
            }
            throw new com.android.internal.widget.LockPatternUtils.RequestThrottledException(checkCredential.getTimeout());
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "failed to check credential", e);
            return false;
        }
    }

    public com.android.internal.widget.VerifyCredentialResponse verifyTiedProfileChallenge(com.android.internal.widget.LockscreenCredential lockscreenCredential, int i, int i2) {
        throwIfCalledOnMainThread();
        try {
            com.android.internal.widget.VerifyCredentialResponse verifyTiedProfileChallenge = getLockSettings().verifyTiedProfileChallenge(lockscreenCredential, i, i2);
            if (verifyTiedProfileChallenge == null) {
                return com.android.internal.widget.VerifyCredentialResponse.ERROR;
            }
            return verifyTiedProfileChallenge;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "failed to verify tied profile credential", e);
            return com.android.internal.widget.VerifyCredentialResponse.ERROR;
        }
    }

    public byte[] getPasswordHistoryHashFactor(com.android.internal.widget.LockscreenCredential lockscreenCredential, int i) {
        try {
            return getLockSettings().getHashFactor(lockscreenCredential, i);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "failed to get hash factor", e);
            return null;
        }
    }

    public boolean checkPasswordHistory(byte[] bArr, byte[] bArr2, int i) {
        int requestedPasswordHistoryLength;
        if (bArr == null || bArr.length == 0) {
            android.util.Log.e(TAG, "checkPasswordHistory: empty password");
            return false;
        }
        java.lang.String string = getString(PASSWORD_HISTORY_KEY, i);
        if (android.text.TextUtils.isEmpty(string) || (requestedPasswordHistoryLength = getRequestedPasswordHistoryLength(i)) == 0) {
            return false;
        }
        byte[] bytes = getSalt(i).getBytes();
        java.lang.String legacyPasswordToHash = com.android.internal.widget.LockscreenCredential.legacyPasswordToHash(bArr, bytes);
        java.lang.String passwordToHistoryHash = com.android.internal.widget.LockscreenCredential.passwordToHistoryHash(bArr, bytes, bArr2);
        java.lang.String[] split = string.split(",");
        for (int i2 = 0; i2 < java.lang.Math.min(requestedPasswordHistoryLength, split.length); i2++) {
            if (split[i2].equals(legacyPasswordToHash) || split[i2].equals(passwordToHistoryHash)) {
                return true;
            }
        }
        return false;
    }

    public int getPinLength(int i) {
        try {
            return getLockSettings().getPinLength(i);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Could not fetch PIN length " + e);
            return -1;
        }
    }

    public boolean refreshStoredPinLength(int i) {
        try {
            return getLockSettings().refreshStoredPinLength(i);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Could not store PIN length on disk " + e);
            return false;
        }
    }

    public int getActivePasswordQuality(int i) {
        return getKeyguardStoredPasswordQuality(i);
    }

    public void resetKeyStore(int i) {
        try {
            getLockSettings().resetKeyStore(i);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Couldn't reset keystore " + e);
        }
    }

    public void setLockScreenDisabled(boolean z, int i) {
        setBoolean("lockscreen.disabled", z, i);
    }

    public boolean isLockScreenDisabled(int i) {
        if (isSecure(i)) {
            return false;
        }
        boolean z = this.mContext.getResources().getBoolean(com.android.internal.R.bool.config_disableLockscreenByDefault);
        android.content.pm.UserInfo userInfo = getUserManager().getUserInfo(i);
        return getBoolean("lockscreen.disabled", false, i) || z || (android.os.UserManager.isDeviceInDemoMode(this.mContext) && userInfo != null && userInfo.isDemo());
    }

    public void setAutoPinConfirm(boolean z, int i) {
        setBoolean(AUTO_PIN_CONFIRM, z, i);
    }

    public boolean isAutoPinConfirmEnabled(int i) {
        return getBoolean(AUTO_PIN_CONFIRM, false, i);
    }

    public static boolean isAutoPinConfirmFeatureAvailable() {
        return true;
    }

    public static boolean isQualityAlphabeticPassword(int i) {
        return i >= 262144;
    }

    public static boolean isQualityNumericPin(int i) {
        return i == 131072 || i == 196608;
    }

    public static int credentialTypeToPasswordQuality(int i) {
        switch (i) {
            case -1:
                return 0;
            case 0:
            case 2:
            default:
                throw new java.lang.IllegalStateException("Unknown type: " + i);
            case 1:
                return 65536;
            case 3:
                return 131072;
            case 4:
                return 262144;
        }
    }

    public static int pinOrPasswordQualityToCredentialType(int i) {
        if (isQualityAlphabeticPassword(i)) {
            return 4;
        }
        if (isQualityNumericPin(i)) {
            return 3;
        }
        throw new java.lang.IllegalArgumentException("Quality is neither Pin nor password: " + i);
    }

    public boolean setLockCredential(com.android.internal.widget.LockscreenCredential lockscreenCredential, com.android.internal.widget.LockscreenCredential lockscreenCredential2, int i) {
        if (!hasSecureLockScreen() && lockscreenCredential.getType() != -1) {
            throw new java.lang.UnsupportedOperationException("This operation requires the lock screen feature.");
        }
        try {
            if (!getLockSettings().setLockCredential(lockscreenCredential, lockscreenCredential2, i)) {
                return false;
            }
            return true;
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Unable to save lock password", e);
        }
    }

    public void setOwnerInfo(java.lang.String str, int i) {
        setString("lock_screen_owner_info", str, i);
    }

    public void setOwnerInfoEnabled(boolean z, int i) {
        setBoolean("lock_screen_owner_info_enabled", z, i);
    }

    public java.lang.String getOwnerInfo(int i) {
        return getString("lock_screen_owner_info", i);
    }

    public boolean isOwnerInfoEnabled(int i) {
        return getBoolean("lock_screen_owner_info_enabled", false, i);
    }

    public void setDeviceOwnerInfo(java.lang.String str) {
        if (str != null && str.isEmpty()) {
            str = null;
        }
        setString(LOCK_SCREEN_DEVICE_OWNER_INFO, str, 0);
    }

    public java.lang.String getDeviceOwnerInfo() {
        return getString(LOCK_SCREEN_DEVICE_OWNER_INFO, 0);
    }

    public boolean isDeviceOwnerInfoEnabled() {
        return getDeviceOwnerInfo() != null;
    }

    public static boolean isDeviceEncryptionEnabled() {
        return android.os.storage.StorageManager.isEncrypted();
    }

    public static boolean isFileEncryptionEnabled() {
        return android.os.storage.StorageManager.isFileEncrypted();
    }

    @java.lang.Deprecated
    public int getKeyguardStoredPasswordQuality(int i) {
        return credentialTypeToPasswordQuality(getCredentialTypeForUser(i));
    }

    public void setSeparateProfileChallengeEnabled(int i, boolean z, com.android.internal.widget.LockscreenCredential lockscreenCredential) {
        if (!isCredentialSharableWithParent(i)) {
            return;
        }
        try {
            getLockSettings().setSeparateProfileChallengeEnabled(i, z, lockscreenCredential);
            reportEnabledTrustAgentsChanged(i);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Couldn't update work profile challenge enabled");
        }
    }

    public boolean isSeparateProfileChallengeEnabled(int i) {
        return isCredentialSharableWithParent(i) && hasSeparateChallenge(i);
    }

    public boolean isProfileWithUnifiedChallenge(int i) {
        return isCredentialSharableWithParent(i) && !hasSeparateChallenge(i);
    }

    public boolean isManagedProfileWithUnifiedChallenge(int i) {
        return isManagedProfile(i) && !hasSeparateChallenge(i);
    }

    private boolean hasSeparateChallenge(int i) {
        try {
            return getLockSettings().getSeparateProfileChallengeEnabled(i);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Couldn't get separate profile challenge enabled");
            return false;
        }
    }

    private boolean isManagedProfile(int i) {
        android.content.pm.UserInfo userInfo = getUserManager().getUserInfo(i);
        return userInfo != null && userInfo.isManagedProfile();
    }

    private boolean isCredentialSharableWithParent(int i) {
        return getUserManager(i).isCredentialSharableWithParent();
    }

    public static java.util.List<com.android.internal.widget.LockPatternView.Cell> byteArrayToPattern(byte[] bArr, byte b) {
        if (bArr == null) {
            return null;
        }
        java.util.ArrayList newArrayList = com.google.android.collect.Lists.newArrayList();
        com.android.internal.widget.LockPatternView.Cell.updateSize(b);
        for (byte b2 : bArr) {
            byte b3 = (byte) (b2 - 49);
            newArrayList.add(com.android.internal.widget.LockPatternView.Cell.of(b3 / b, b3 % b, b));
        }
        return newArrayList;
    }

    public static byte[] patternToByteArray(java.util.List<com.android.internal.widget.LockPatternView.Cell> list, byte b) {
        if (list == null) {
            return new byte[0];
        }
        int size = list.size();
        byte[] bArr = new byte[size];
        for (int i = 0; i < size; i++) {
            com.android.internal.widget.LockPatternView.Cell cell = list.get(i);
            bArr[i] = (byte) ((cell.getRow() * b) + cell.getColumn() + 49);
        }
        return bArr;
    }

    private java.lang.String getSalt(int i) {
        long j = getLong(LOCK_PASSWORD_SALT_KEY, 0L, i);
        if (j == 0) {
            try {
                j = java.security.SecureRandom.getInstance("SHA1PRNG").nextLong();
                setLong(LOCK_PASSWORD_SALT_KEY, j, i);
                android.util.Log.v(TAG, "Initialized lock password salt for user: " + i);
            } catch (java.security.NoSuchAlgorithmException e) {
                throw new java.lang.IllegalStateException("Couldn't get SecureRandom number", e);
            }
        }
        return java.lang.Long.toHexString(j);
    }

    public static final void invalidateCredentialTypeCache() {
        android.app.PropertyInvalidatedCache.invalidateCache("system_server", CREDENTIAL_TYPE_API);
    }

    public int getCredentialTypeForUser(int i) {
        return this.mCredentialTypeCache.query(java.lang.Integer.valueOf(i)).intValue();
    }

    public byte getLockPatternSize(int i) {
        long j = getLong(android.provider.Settings.Secure.LOCK_PATTERN_SIZE, -1L, i);
        if (j > 0 && j < 128) {
            return (byte) j;
        }
        return (byte) 3;
    }

    public void setLockPatternSize(long j, int i) {
        setLong(android.provider.Settings.Secure.LOCK_PATTERN_SIZE, j, i);
    }

    public void setVisibleDotsEnabled(boolean z, int i) {
        setBoolean(android.provider.Settings.Secure.LOCK_DOTS_VISIBLE, z, i);
    }

    public boolean isVisibleDotsEnabled(int i) {
        return getBoolean(android.provider.Settings.Secure.LOCK_DOTS_VISIBLE, true, i);
    }

    public void setShowErrorPath(boolean z, int i) {
        setBoolean(android.provider.Settings.Secure.LOCK_SHOW_ERROR_PATH, z, i);
    }

    public boolean isShowErrorPath(int i) {
        return getBoolean(android.provider.Settings.Secure.LOCK_SHOW_ERROR_PATH, true, i);
    }

    public boolean isSecure(int i) {
        return getCredentialTypeForUser(i) != -1;
    }

    public boolean isLockPasswordEnabled(int i) {
        int credentialTypeForUser = getCredentialTypeForUser(i);
        return credentialTypeForUser == 4 || credentialTypeForUser == 3;
    }

    public boolean isLockPatternEnabled(int i) {
        return getCredentialTypeForUser(i) == 1;
    }

    public boolean isVisiblePatternEnabled(int i) {
        return getBoolean("lock_pattern_visible_pattern", true, i);
    }

    public void setVisiblePatternEnabled(boolean z, int i) {
        setBoolean("lock_pattern_visible_pattern", z, i);
    }

    public boolean isVisiblePatternEverChosen(int i) {
        return getString("lock_pattern_visible_pattern", i) != null;
    }

    public boolean isPinEnhancedPrivacyEnabled(int i) {
        return getBoolean(LOCK_PIN_ENHANCED_PRIVACY, false, i);
    }

    public void setPinEnhancedPrivacyEnabled(boolean z, int i) {
        setBoolean(LOCK_PIN_ENHANCED_PRIVACY, z, i);
    }

    public boolean isPinEnhancedPrivacyEverChosen(int i) {
        return getString(LOCK_PIN_ENHANCED_PRIVACY, i) != null;
    }

    public long setLockoutAttemptDeadline(int i, int i2) {
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime() + i2;
        if (i == -9999) {
            return elapsedRealtime;
        }
        this.mLockoutDeadlines.put(i, elapsedRealtime);
        return elapsedRealtime;
    }

    public long getLockoutAttemptDeadline(int i) {
        long j = this.mLockoutDeadlines.get(i, 0L);
        if (j < android.os.SystemClock.elapsedRealtime() && j != 0) {
            this.mLockoutDeadlines.put(i, 0L);
            return 0L;
        }
        return j;
    }

    protected boolean getBoolean(java.lang.String str, boolean z, int i) {
        try {
            return getLockSettings().getBoolean(str, z, i);
        } catch (android.os.RemoteException e) {
            return z;
        }
    }

    protected void setBoolean(java.lang.String str, boolean z, int i) {
        try {
            getLockSettings().setBoolean(str, z, i);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Couldn't write boolean " + str + e);
        }
    }

    protected long getLong(java.lang.String str, long j, int i) {
        try {
            return getLockSettings().getLong(str, j, i);
        } catch (android.os.RemoteException e) {
            return j;
        }
    }

    protected void setLong(java.lang.String str, long j, int i) {
        try {
            getLockSettings().setLong(str, j, i);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Couldn't write long " + str + e);
        }
    }

    protected java.lang.String getString(java.lang.String str, int i) {
        try {
            return getLockSettings().getString(str, null, i);
        } catch (android.os.RemoteException e) {
            return null;
        }
    }

    protected void setString(java.lang.String str, java.lang.String str2, int i) {
        try {
            getLockSettings().setString(str, str2, i);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Couldn't write string " + str + e);
        }
    }

    public void setPowerButtonInstantlyLocks(boolean z, int i) {
        setBoolean(LOCKSCREEN_POWER_BUTTON_INSTANTLY_LOCKS, z, i);
    }

    public boolean getPowerButtonInstantlyLocks(int i) {
        return getBoolean(LOCKSCREEN_POWER_BUTTON_INSTANTLY_LOCKS, true, i);
    }

    public boolean isPowerButtonInstantlyLocksEverChosen(int i) {
        return getString(LOCKSCREEN_POWER_BUTTON_INSTANTLY_LOCKS, i) != null;
    }

    public void setEnabledTrustAgents(java.util.Collection<android.content.ComponentName> collection, int i) {
        setString(ENABLED_TRUST_AGENTS, serializeTrustAgents(collection), i);
        getTrustManager().reportEnabledTrustAgentsChanged(i);
    }

    public java.util.List<android.content.ComponentName> getEnabledTrustAgents(int i) {
        return deserializeTrustAgents(getString(ENABLED_TRUST_AGENTS, i));
    }

    public void setKnownTrustAgents(java.util.Collection<android.content.ComponentName> collection, int i) {
        setString(KNOWN_TRUST_AGENTS, serializeTrustAgents(collection), i);
    }

    public java.util.List<android.content.ComponentName> getKnownTrustAgents(int i) {
        return deserializeTrustAgents(getString(KNOWN_TRUST_AGENTS, i));
    }

    private java.lang.String serializeTrustAgents(java.util.Collection<android.content.ComponentName> collection) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (android.content.ComponentName componentName : collection) {
            if (sb.length() > 0) {
                sb.append(',');
            }
            sb.append(componentName.flattenToShortString());
        }
        return sb.toString();
    }

    private java.util.List<android.content.ComponentName> deserializeTrustAgents(java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            return new java.util.ArrayList();
        }
        java.lang.String[] split = str.split(",");
        java.util.ArrayList arrayList = new java.util.ArrayList(split.length);
        for (java.lang.String str2 : split) {
            if (!android.text.TextUtils.isEmpty(str2)) {
                arrayList.add(android.content.ComponentName.unflattenFromString(str2));
            }
        }
        return arrayList;
    }

    public void requireCredentialEntry(int i) {
        requireStrongAuth(4, i);
    }

    public void requireStrongAuth(int i, int i2) {
        try {
            getLockSettings().requireStrongAuth(i, i2);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error while requesting strong auth: " + e);
        }
    }

    private void reportEnabledTrustAgentsChanged(int i) {
        getTrustManager().reportEnabledTrustAgentsChanged(i);
    }

    private void throwIfCalledOnMainThread() {
        if (android.os.Looper.getMainLooper().isCurrentThread()) {
            throw new java.lang.IllegalStateException("should not be called from the main thread.");
        }
    }

    public void registerStrongAuthTracker(com.android.internal.widget.LockPatternUtils.StrongAuthTracker strongAuthTracker) {
        try {
            getLockSettings().registerStrongAuthTracker(strongAuthTracker.getStub());
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Could not register StrongAuthTracker");
        }
    }

    public void unregisterStrongAuthTracker(com.android.internal.widget.LockPatternUtils.StrongAuthTracker strongAuthTracker) {
        try {
            getLockSettings().unregisterStrongAuthTracker(strongAuthTracker.getStub());
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Could not unregister StrongAuthTracker", e);
        }
    }

    public boolean registerWeakEscrowTokenRemovedListener(com.android.internal.widget.IWeakEscrowTokenRemovedListener iWeakEscrowTokenRemovedListener) {
        try {
            return getLockSettings().registerWeakEscrowTokenRemovedListener(iWeakEscrowTokenRemovedListener);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Could not register WeakEscrowTokenRemovedListener.");
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean unregisterWeakEscrowTokenRemovedListener(com.android.internal.widget.IWeakEscrowTokenRemovedListener iWeakEscrowTokenRemovedListener) {
        try {
            return getLockSettings().unregisterWeakEscrowTokenRemovedListener(iWeakEscrowTokenRemovedListener);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Could not register WeakEscrowTokenRemovedListener.");
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportSuccessfulBiometricUnlock(boolean z, int i) {
        try {
            getLockSettings().reportSuccessfulBiometricUnlock(z, i);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Could not report successful biometric unlock", e);
        }
    }

    public void scheduleNonStrongBiometricIdleTimeout(int i) {
        try {
            getLockSettings().scheduleNonStrongBiometricIdleTimeout(i);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Could not schedule non-strong biometric idle timeout", e);
        }
    }

    public int getStrongAuthForUser(int i) {
        try {
            return getLockSettings().getStrongAuthForUser(i);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Could not get StrongAuth", e);
            return com.android.internal.widget.LockPatternUtils.StrongAuthTracker.getDefaultFlags(this.mContext);
        }
    }

    public boolean isCredentialsDisabledForUser(int i) {
        return getDevicePolicyManager().getPasswordQuality(null, i) == 524288;
    }

    public boolean isTrustAllowedForUser(int i) {
        return getStrongAuthForUser(i) == 0;
    }

    public boolean isBiometricAllowedForUser(int i) {
        return (getStrongAuthForUser(i) & (-773)) == 0;
    }

    public boolean isUserInLockdown(int i) {
        return (getStrongAuthForUser(i) & 32) != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class WrappedCallback extends com.android.internal.widget.ICheckCredentialProgressCallback.Stub {
        private com.android.internal.widget.LockPatternUtils.CheckCredentialProgressCallback mCallback;
        private android.os.Handler mHandler;

        WrappedCallback(android.os.Handler handler, com.android.internal.widget.LockPatternUtils.CheckCredentialProgressCallback checkCredentialProgressCallback) {
            this.mHandler = handler;
            this.mCallback = checkCredentialProgressCallback;
        }

        @Override // com.android.internal.widget.ICheckCredentialProgressCallback
        public void onCredentialVerified() throws android.os.RemoteException {
            if (this.mHandler == null) {
                android.util.Log.e(com.android.internal.widget.LockPatternUtils.TAG, "Handler is null during callback");
            }
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.internal.widget.LockPatternUtils$WrappedCallback$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.widget.LockPatternUtils.WrappedCallback.this.lambda$onCredentialVerified$0();
                }
            });
            this.mHandler = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCredentialVerified$0() {
            this.mCallback.onEarlyMatched();
            this.mCallback = null;
        }
    }

    private com.android.internal.widget.ICheckCredentialProgressCallback wrapCallback(com.android.internal.widget.LockPatternUtils.CheckCredentialProgressCallback checkCredentialProgressCallback) {
        if (checkCredentialProgressCallback == null) {
            return null;
        }
        if (this.mHandler == null) {
            throw new java.lang.IllegalStateException("Must construct LockPatternUtils on a looper thread to use progress callbacks.");
        }
        return new com.android.internal.widget.LockPatternUtils.WrappedCallback(this.mHandler, checkCredentialProgressCallback);
    }

    private com.android.internal.widget.LockSettingsInternal getLockSettingsInternal() {
        com.android.internal.widget.LockSettingsInternal lockSettingsInternal = (com.android.internal.widget.LockSettingsInternal) com.android.server.LocalServices.getService(com.android.internal.widget.LockSettingsInternal.class);
        if (lockSettingsInternal == null) {
            throw new java.lang.SecurityException("Only available to system server itself");
        }
        return lockSettingsInternal;
    }

    public long addEscrowToken(byte[] bArr, int i, com.android.internal.widget.LockPatternUtils.EscrowTokenStateChangeCallback escrowTokenStateChangeCallback) {
        return getLockSettingsInternal().addEscrowToken(bArr, i, escrowTokenStateChangeCallback);
    }

    public long addWeakEscrowToken(byte[] bArr, int i, com.android.internal.widget.IWeakEscrowTokenActivatedListener iWeakEscrowTokenActivatedListener) {
        try {
            return getLockSettings().addWeakEscrowToken(bArr, i, iWeakEscrowTokenActivatedListener);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Could not add weak token.");
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean removeEscrowToken(long j, int i) {
        return getLockSettingsInternal().removeEscrowToken(j, i);
    }

    public boolean removeWeakEscrowToken(long j, int i) {
        try {
            return getLockSettings().removeWeakEscrowToken(j, i);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Could not remove the weak token.");
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isEscrowTokenActive(long j, int i) {
        return getLockSettingsInternal().isEscrowTokenActive(j, i);
    }

    public boolean isWeakEscrowTokenActive(long j, int i) {
        try {
            return getLockSettings().isWeakEscrowTokenActive(j, i);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Could not check the weak token.");
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isWeakEscrowTokenValid(long j, byte[] bArr, int i) {
        try {
            return getLockSettings().isWeakEscrowTokenValid(j, bArr, i);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Could not validate the weak token.");
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setLockCredentialWithToken(com.android.internal.widget.LockscreenCredential lockscreenCredential, long j, byte[] bArr, int i) {
        if (!hasSecureLockScreen() && lockscreenCredential.getType() != -1) {
            throw new java.lang.UnsupportedOperationException("This operation requires the lock screen feature.");
        }
        return getLockSettingsInternal().setLockCredentialWithToken(lockscreenCredential, j, bArr, i);
    }

    public boolean unlockUserWithToken(long j, byte[] bArr, int i) {
        return getLockSettingsInternal().unlockUserWithToken(j, bArr, i);
    }

    public static class StrongAuthTracker {
        private static final int ALLOWING_BIOMETRIC = 772;
        public static final int SOME_AUTH_REQUIRED_AFTER_ADAPTIVE_AUTH_REQUEST = 512;
        public static final int SOME_AUTH_REQUIRED_AFTER_TRUSTAGENT_EXPIRED = 256;
        public static final int SOME_AUTH_REQUIRED_AFTER_USER_REQUEST = 4;
        public static final int STRONG_AUTH_NOT_REQUIRED = 0;
        public static final int STRONG_AUTH_REQUIRED_AFTER_BOOT = 1;
        public static final int STRONG_AUTH_REQUIRED_AFTER_DPM_LOCK_NOW = 2;
        public static final int STRONG_AUTH_REQUIRED_AFTER_LOCKOUT = 8;
        public static final int STRONG_AUTH_REQUIRED_AFTER_NON_STRONG_BIOMETRICS_TIMEOUT = 128;
        public static final int STRONG_AUTH_REQUIRED_AFTER_TIMEOUT = 16;
        public static final int STRONG_AUTH_REQUIRED_AFTER_USER_LOCKDOWN = 32;
        public static final int STRONG_AUTH_REQUIRED_FOR_UNATTENDED_UPDATE = 64;
        private final boolean mDefaultIsNonStrongBiometricAllowed;
        private final int mDefaultStrongAuthFlags;
        private final com.android.internal.widget.LockPatternUtils.StrongAuthTracker.H mHandler;
        private final android.util.SparseBooleanArray mIsNonStrongBiometricAllowedForUser;
        private final android.util.SparseIntArray mStrongAuthRequiredForUser;
        private final android.app.trust.IStrongAuthTracker.Stub mStub;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface StrongAuthFlags {
        }

        public StrongAuthTracker(android.content.Context context) {
            this(context, android.os.Looper.myLooper());
        }

        public StrongAuthTracker(android.content.Context context, android.os.Looper looper) {
            this.mStrongAuthRequiredForUser = new android.util.SparseIntArray();
            this.mIsNonStrongBiometricAllowedForUser = new android.util.SparseBooleanArray();
            this.mDefaultIsNonStrongBiometricAllowed = true;
            this.mStub = new android.app.trust.IStrongAuthTracker.Stub() { // from class: com.android.internal.widget.LockPatternUtils.StrongAuthTracker.1
                @Override // android.app.trust.IStrongAuthTracker
                public void onStrongAuthRequiredChanged(int i, int i2) {
                    com.android.internal.widget.LockPatternUtils.StrongAuthTracker.this.mHandler.obtainMessage(1, i, i2).sendToTarget();
                }

                @Override // android.app.trust.IStrongAuthTracker
                public void onIsNonStrongBiometricAllowedChanged(boolean z, int i) {
                    com.android.internal.widget.LockPatternUtils.StrongAuthTracker.this.mHandler.obtainMessage(2, z ? 1 : 0, i).sendToTarget();
                }
            };
            this.mHandler = new com.android.internal.widget.LockPatternUtils.StrongAuthTracker.H(looper);
            this.mDefaultStrongAuthFlags = getDefaultFlags(context);
        }

        public static int getDefaultFlags(android.content.Context context) {
            return context.getResources().getBoolean(com.android.internal.R.bool.config_strongAuthRequiredOnBoot) ? 1 : 0;
        }

        public int getStrongAuthForUser(int i) {
            return this.mStrongAuthRequiredForUser.get(i, this.mDefaultStrongAuthFlags);
        }

        public boolean isTrustAllowedForUser(int i) {
            return getStrongAuthForUser(i) == 0;
        }

        public boolean isBiometricAllowedForUser(boolean z, int i) {
            boolean z2 = (getStrongAuthForUser(i) & (-773)) == 0;
            if (!z) {
                return z2 & isNonStrongBiometricAllowedAfterIdleTimeout(i);
            }
            return z2;
        }

        public boolean isNonStrongBiometricAllowedAfterIdleTimeout(int i) {
            return this.mIsNonStrongBiometricAllowedForUser.get(i, true);
        }

        public void onStrongAuthRequiredChanged(int i) {
        }

        public void onIsNonStrongBiometricAllowedChanged(int i) {
        }

        protected void handleStrongAuthRequiredChanged(int i, int i2) {
            if (i != getStrongAuthForUser(i2)) {
                if (i == this.mDefaultStrongAuthFlags) {
                    this.mStrongAuthRequiredForUser.delete(i2);
                } else {
                    this.mStrongAuthRequiredForUser.put(i2, i);
                }
                onStrongAuthRequiredChanged(i2);
            }
        }

        protected void handleIsNonStrongBiometricAllowedChanged(boolean z, int i) {
            if (z != isNonStrongBiometricAllowedAfterIdleTimeout(i)) {
                if (z) {
                    this.mIsNonStrongBiometricAllowedForUser.delete(i);
                } else {
                    this.mIsNonStrongBiometricAllowedForUser.put(i, z);
                }
                onIsNonStrongBiometricAllowedChanged(i);
            }
        }

        public android.app.trust.IStrongAuthTracker.Stub getStub() {
            return this.mStub;
        }

        private class H extends android.os.Handler {
            static final int MSG_ON_IS_NON_STRONG_BIOMETRIC_ALLOWED_CHANGED = 2;
            static final int MSG_ON_STRONG_AUTH_REQUIRED_CHANGED = 1;

            public H(android.os.Looper looper) {
                super(looper);
            }

            @Override // android.os.Handler
            public void handleMessage(android.os.Message message) {
                switch (message.what) {
                    case 1:
                        com.android.internal.widget.LockPatternUtils.StrongAuthTracker.this.handleStrongAuthRequiredChanged(message.arg1, message.arg2);
                        break;
                    case 2:
                        com.android.internal.widget.LockPatternUtils.StrongAuthTracker.this.handleIsNonStrongBiometricAllowedChanged(message.arg1 == 1, message.arg2);
                        break;
                }
            }
        }
    }

    public boolean hasPendingEscrowToken(int i) {
        try {
            return getLockSettings().hasPendingEscrowToken(i);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public boolean hasSecureLockScreen() {
        if (this.mHasSecureLockScreen == null) {
            try {
                this.mHasSecureLockScreen = java.lang.Boolean.valueOf(getLockSettings().hasSecureLockScreen());
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
        return this.mHasSecureLockScreen.booleanValue();
    }

    public static boolean userOwnsFrpCredential(android.content.Context context, android.content.pm.UserInfo userInfo) {
        return userInfo != null && userInfo.isMain() && userInfo.isAdmin() && frpCredentialEnabled(context);
    }

    public static boolean frpCredentialEnabled(android.content.Context context) {
        return context.getResources().getBoolean(com.android.internal.R.bool.config_enableCredentialFactoryResetProtection);
    }

    public static boolean isRepairModeSupported(android.content.Context context) {
        return context.getResources().getBoolean(com.android.internal.R.bool.config_repairModeSupported);
    }

    public static boolean isRepairModeActive(android.content.Context context) {
        return android.provider.Settings.Global.getInt(context.getContentResolver(), android.provider.Settings.Global.REPAIR_MODE_ACTIVE, 0) > 0;
    }

    public static boolean canUserEnterRepairMode(android.content.Context context, android.content.pm.UserInfo userInfo) {
        return userInfo != null && userInfo.isAdmin() && isRepairModeSupported(context);
    }

    public static boolean isGsiRunning() {
        return android.os.SystemProperties.getInt(GSI_RUNNING_PROP, 0) > 0;
    }

    public static boolean isSpecialUserId(int i) {
        return isSpecialUserId(null, i, false);
    }

    private static boolean isSpecialUserId(android.content.Context context, int i, boolean z) {
        switch (i) {
            case USER_FRP /* -9999 */:
                if (z) {
                    return frpCredentialEnabled(context);
                }
                return true;
            case USER_REPAIR_MODE /* -9998 */:
                if (z) {
                    return isRepairModeSupported(context);
                }
                return true;
            default:
                return false;
        }
    }

    public boolean tryUnlockWithCachedUnifiedChallenge(int i) {
        try {
            return getLockSettings().tryUnlockWithCachedUnifiedChallenge(i);
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public void removeCachedUnifiedChallenge(int i) {
        try {
            getLockSettings().removeCachedUnifiedChallenge(i);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void unlockUserKeyIfUnsecured(int i) {
        try {
            getLockSettings().unlockUserKeyIfUnsecured(i);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void createNewUser(int i, int i2) {
        getLockSettingsInternal().createNewUser(i, i2);
    }

    public void removeUser(int i) {
        getLockSettingsInternal().removeUser(i);
    }

    public android.app.RemoteLockscreenValidationSession startRemoteLockscreenValidation() {
        try {
            return getLockSettings().startRemoteLockscreenValidation();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.app.RemoteLockscreenValidationResult validateRemoteLockscreen(byte[] bArr) {
        try {
            return getLockSettings().validateRemoteLockscreen(bArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
