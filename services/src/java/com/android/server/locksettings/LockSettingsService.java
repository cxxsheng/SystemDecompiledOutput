package com.android.server.locksettings;

/* loaded from: classes2.dex */
public class LockSettingsService extends com.android.internal.widget.ILockSettings.Stub {
    private static final java.lang.String BIOMETRIC_PERMISSION = "android.permission.MANAGE_BIOMETRIC";
    private static final int GK_PW_HANDLE_STORE_DURATION_MS = 600000;
    private static final int HEADLESS_VENDOR_AUTH_SECRET_LENGTH = 32;
    private static final java.lang.String LSKF_LAST_CHANGED_TIME_KEY = "sp-handle-ts";
    private static final java.lang.String MIGRATED_FRP2 = "migrated_frp2";
    private static final java.lang.String MIGRATED_KEYSTORE_NS = "migrated_keystore_namespace";
    private static final java.lang.String MIGRATED_SP_CE_ONLY = "migrated_all_users_to_sp_and_bound_ce";
    private static final java.lang.String MIGRATED_SP_FULL = "migrated_all_users_to_sp_and_bound_keys";
    private static final java.lang.String PERMISSION = "android.permission.ACCESS_KEYGUARD_SECURE_STORAGE";
    private static final java.lang.String PREV_LSKF_BASED_PROTECTOR_ID_KEY = "prev-sp-handle";
    private static final int PROFILE_KEY_IV_SIZE = 12;
    private static final java.lang.String PROFILE_KEY_NAME_DECRYPT = "profile_key_name_decrypt_";
    private static final java.lang.String PROFILE_KEY_NAME_ENCRYPT = "profile_key_name_encrypt_";
    private static final java.lang.String SEPARATE_PROFILE_CHALLENGE_KEY = "lockscreen.profilechallenge";
    private static final java.lang.String TAG = "LockSettingsService";
    private static final java.lang.String USER_SERIAL_NUMBER_KEY = "serial-number";
    private final android.app.IActivityManager mActivityManager;

    @com.android.internal.annotations.GuardedBy({"mHeadlessAuthSecretLock"})
    @com.android.internal.annotations.VisibleForTesting
    protected byte[] mAuthSecret;
    protected android.hardware.authsecret.IAuthSecret mAuthSecretService;
    private final com.android.server.locksettings.BiometricDeferredQueue mBiometricDeferredQueue;
    private final android.content.BroadcastReceiver mBroadcastReceiver;
    private final android.content.Context mContext;
    private final com.android.server.locksettings.LockSettingsService.DeviceProvisionedObserver mDeviceProvisionedObserver;

    @com.android.internal.annotations.GuardedBy({"mUserCreationAndRemovalLock"})
    private android.util.SparseIntArray mEarlyCreatedUsers;

    @com.android.internal.annotations.GuardedBy({"mUserCreationAndRemovalLock"})
    private android.util.SparseIntArray mEarlyRemovedUsers;
    protected android.service.gatekeeper.IGateKeeperService mGateKeeperService;
    private final android.util.LongSparseArray<byte[]> mGatekeeperPasswords;

    @com.android.internal.annotations.VisibleForTesting
    protected final android.os.Handler mHandler;

    @com.android.internal.annotations.VisibleForTesting
    protected boolean mHasSecureLockScreen;

    @com.android.internal.annotations.VisibleForTesting
    protected final java.lang.Object mHeadlessAuthSecretLock;
    private final com.android.server.locksettings.LockSettingsService.Injector mInjector;
    private final java.security.KeyStore mKeyStore;
    private final java.util.concurrent.CopyOnWriteArrayList<com.android.internal.widget.LockSettingsStateListener> mLockSettingsStateListeners;
    private final android.app.NotificationManager mNotificationManager;
    private final com.android.server.locksettings.RebootEscrowManager mRebootEscrowManager;
    private final com.android.server.locksettings.recoverablekeystore.RecoverableKeyStoreManager mRecoverableKeyStoreManager;
    private final java.lang.Object mSeparateChallengeLock;
    private final com.android.server.locksettings.SyntheticPasswordManager mSpManager;

    @com.android.internal.annotations.VisibleForTesting
    protected final com.android.server.locksettings.LockSettingsStorage mStorage;
    private final android.os.storage.IStorageManager mStorageManager;
    private final com.android.server.locksettings.LockSettingsStrongAuth mStrongAuth;
    private final com.android.server.locksettings.LockSettingsService.SynchronizedStrongAuthTracker mStrongAuthTracker;

    @com.android.internal.annotations.GuardedBy({"mUserCreationAndRemovalLock"})
    private boolean mThirdPartyAppsStarted;
    private final com.android.server.locksettings.UnifiedProfilePasswordCache mUnifiedProfilePasswordCache;
    private final java.lang.Object mUserCreationAndRemovalLock;
    protected final android.os.UserManager mUserManager;
    private java.util.HashMap<android.os.UserHandle, android.os.UserManager> mUserManagerCache;

    @com.android.internal.annotations.GuardedBy({"this"})
    private final android.util.SparseArray<android.app.admin.PasswordMetrics> mUserPasswordMetrics;
    private static final boolean FIX_UNLOCKED_DEVICE_REQUIRED_KEYS = android.security.Flags.fixUnlockedDeviceRequiredKeysV2();
    private static final int[] SYSTEM_CREDENTIAL_UIDS = {1016, 0, 1000};

    public static final class Lifecycle extends com.android.server.SystemService {
        private com.android.server.locksettings.LockSettingsService mLockSettingsService;

        public Lifecycle(android.content.Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            android.security.keystore2.AndroidKeyStoreProvider.install();
            this.mLockSettingsService = new com.android.server.locksettings.LockSettingsService(getContext());
            publishBinderService("lock_settings", this.mLockSettingsService);
        }

        @Override // com.android.server.SystemService
        public void onBootPhase(int i) {
            super.onBootPhase(i);
            if (i == 550) {
                this.mLockSettingsService.migrateOldDataAfterSystemReady();
                this.mLockSettingsService.deleteRepairModePersistentDataIfNeeded();
            } else if (i == 1000) {
                this.mLockSettingsService.loadEscrowData();
            }
        }

        @Override // com.android.server.SystemService
        public void onUserStarting(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
            this.mLockSettingsService.onUserStarting(targetUser.getUserIdentifier());
        }

        @Override // com.android.server.SystemService
        public void onUserUnlocking(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
            this.mLockSettingsService.onUserUnlocking(targetUser.getUserIdentifier());
        }

        @Override // com.android.server.SystemService
        public void onUserStopped(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
            this.mLockSettingsService.onUserStopped(targetUser.getUserIdentifier());
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected static class SynchronizedStrongAuthTracker extends com.android.internal.widget.LockPatternUtils.StrongAuthTracker {
        public SynchronizedStrongAuthTracker(android.content.Context context) {
            super(context);
        }

        protected void handleStrongAuthRequiredChanged(int i, int i2) {
            synchronized (this) {
                super.handleStrongAuthRequiredChanged(i, i2);
            }
        }

        public int getStrongAuthForUser(int i) {
            int strongAuthForUser;
            synchronized (this) {
                strongAuthForUser = super.getStrongAuthForUser(i);
            }
            return strongAuthForUser;
        }

        void register(com.android.server.locksettings.LockSettingsStrongAuth lockSettingsStrongAuth) {
            lockSettingsStrongAuth.registerStrongAuthTracker(getStub());
        }
    }

    private com.android.internal.widget.LockscreenCredential generateRandomProfilePassword() {
        byte[] randomBytes = com.android.server.locksettings.SecureRandomUtils.randomBytes(40);
        char[] encode = libcore.util.HexEncoding.encode(randomBytes);
        byte[] bArr = new byte[encode.length];
        for (int i = 0; i < encode.length; i++) {
            bArr[i] = (byte) encode[i];
        }
        com.android.internal.widget.LockscreenCredential createUnifiedProfilePassword = com.android.internal.widget.LockscreenCredential.createUnifiedProfilePassword(bArr);
        java.util.Arrays.fill(encode, (char) 0);
        java.util.Arrays.fill(bArr, (byte) 0);
        java.util.Arrays.fill(randomBytes, (byte) 0);
        return createUnifiedProfilePassword;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void tieProfileLockIfNecessary(int i, com.android.internal.widget.LockscreenCredential lockscreenCredential) {
        android.content.pm.UserInfo profileParent;
        if (!isCredentialSharableWithParent(i) || getSeparateProfileChallengeEnabledInternal(i) || this.mStorage.hasChildProfileLock(i) || (profileParent = this.mUserManager.getProfileParent(i)) == null) {
            return;
        }
        if (!isUserSecure(profileParent.id) && !lockscreenCredential.isNone()) {
            com.android.server.utils.Slogf.i(TAG, "Clearing password for profile user %d to match parent", java.lang.Integer.valueOf(i));
            setLockCredentialInternal(com.android.internal.widget.LockscreenCredential.createNone(), lockscreenCredential, i, true);
            return;
        }
        try {
            long secureUserId = getGateKeeperService().getSecureUserId(profileParent.id);
            if (secureUserId == 0) {
                return;
            }
            com.android.internal.widget.LockscreenCredential generateRandomProfilePassword = generateRandomProfilePassword();
            try {
                setLockCredentialInternal(generateRandomProfilePassword, lockscreenCredential, i, true);
                tieProfileLockToParent(i, profileParent.id, generateRandomProfilePassword);
                this.mUnifiedProfilePasswordCache.storePassword(i, generateRandomProfilePassword, secureUserId);
                if (generateRandomProfilePassword != null) {
                    generateRandomProfilePassword.close();
                }
            } catch (java.lang.Throwable th) {
                if (generateRandomProfilePassword != null) {
                    try {
                        generateRandomProfilePassword.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to talk to GateKeeper service", e);
        }
    }

    static class Injector {
        protected android.content.Context mContext;
        private android.os.Handler mHandler;
        private com.android.server.ServiceThread mHandlerThread;

        public Injector(android.content.Context context) {
            this.mContext = context;
        }

        public android.content.Context getContext() {
            return this.mContext;
        }

        public com.android.server.ServiceThread getServiceThread() {
            if (this.mHandlerThread == null) {
                this.mHandlerThread = new com.android.server.ServiceThread(com.android.server.locksettings.LockSettingsService.TAG, 10, true);
                this.mHandlerThread.start();
            }
            return this.mHandlerThread;
        }

        public android.os.Handler getHandler(com.android.server.ServiceThread serviceThread) {
            if (this.mHandler == null) {
                this.mHandler = new android.os.Handler(serviceThread.getLooper());
            }
            return this.mHandler;
        }

        public com.android.server.locksettings.LockSettingsStorage getStorage() {
            final com.android.server.locksettings.LockSettingsStorage lockSettingsStorage = new com.android.server.locksettings.LockSettingsStorage(this.mContext);
            lockSettingsStorage.setDatabaseOnCreateCallback(new com.android.server.locksettings.LockSettingsStorage.Callback() { // from class: com.android.server.locksettings.LockSettingsService.Injector.1
                @Override // com.android.server.locksettings.LockSettingsStorage.Callback
                public void initialize(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
                    if (android.os.SystemProperties.getBoolean("ro.lockscreen.disable.default", false)) {
                        lockSettingsStorage.writeKeyValue(sQLiteDatabase, "lockscreen.disabled", "1", 0);
                    }
                }
            });
            return lockSettingsStorage;
        }

        public com.android.server.locksettings.LockSettingsStrongAuth getStrongAuth() {
            return new com.android.server.locksettings.LockSettingsStrongAuth(this.mContext);
        }

        public com.android.server.locksettings.LockSettingsService.SynchronizedStrongAuthTracker getStrongAuthTracker() {
            return new com.android.server.locksettings.LockSettingsService.SynchronizedStrongAuthTracker(this.mContext);
        }

        public android.app.IActivityManager getActivityManager() {
            return android.app.ActivityManager.getService();
        }

        public android.app.NotificationManager getNotificationManager() {
            return (android.app.NotificationManager) this.mContext.getSystemService("notification");
        }

        public android.os.UserManager getUserManager() {
            return (android.os.UserManager) this.mContext.getSystemService("user");
        }

        public com.android.server.pm.UserManagerInternal getUserManagerInternal() {
            return (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
        }

        public android.app.admin.DevicePolicyManager getDevicePolicyManager() {
            return (android.app.admin.DevicePolicyManager) this.mContext.getSystemService("device_policy");
        }

        public android.app.admin.DeviceStateCache getDeviceStateCache() {
            return android.app.admin.DeviceStateCache.getInstance();
        }

        public com.android.server.locksettings.recoverablekeystore.RecoverableKeyStoreManager getRecoverableKeyStoreManager() {
            return com.android.server.locksettings.recoverablekeystore.RecoverableKeyStoreManager.getInstance(this.mContext);
        }

        public android.os.storage.IStorageManager getStorageManager() {
            android.os.IBinder service = android.os.ServiceManager.getService("mount");
            if (service != null) {
                return android.os.storage.IStorageManager.Stub.asInterface(service);
            }
            return null;
        }

        public com.android.server.locksettings.SyntheticPasswordManager getSyntheticPasswordManager(com.android.server.locksettings.LockSettingsStorage lockSettingsStorage) {
            return new com.android.server.locksettings.SyntheticPasswordManager(getContext(), lockSettingsStorage, getUserManager(), new com.android.server.locksettings.PasswordSlotManager());
        }

        public com.android.server.locksettings.RebootEscrowManager getRebootEscrowManager(com.android.server.locksettings.RebootEscrowManager.Callbacks callbacks, com.android.server.locksettings.LockSettingsStorage lockSettingsStorage) {
            return new com.android.server.locksettings.RebootEscrowManager(this.mContext, callbacks, lockSettingsStorage, getHandler(getServiceThread()));
        }

        public int binderGetCallingUid() {
            return android.os.Binder.getCallingUid();
        }

        public boolean isGsiRunning() {
            return com.android.internal.widget.LockPatternUtils.isGsiRunning();
        }

        public android.hardware.fingerprint.FingerprintManager getFingerprintManager() {
            if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.fingerprint")) {
                return (android.hardware.fingerprint.FingerprintManager) this.mContext.getSystemService("fingerprint");
            }
            return null;
        }

        public android.hardware.face.FaceManager getFaceManager() {
            if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.biometrics.face")) {
                return (android.hardware.face.FaceManager) this.mContext.getSystemService("face");
            }
            return null;
        }

        public android.hardware.biometrics.BiometricManager getBiometricManager() {
            return (android.hardware.biometrics.BiometricManager) this.mContext.getSystemService("biometric");
        }

        public java.security.KeyStore getKeyStore() {
            try {
                java.security.KeyStore keyStore = java.security.KeyStore.getInstance(com.android.server.locksettings.SyntheticPasswordCrypto.androidKeystoreProviderName());
                keyStore.load(new android.security.keystore2.AndroidKeyStoreLoadStoreParameter(com.android.server.locksettings.SyntheticPasswordCrypto.keyNamespace()));
                return keyStore;
            } catch (java.lang.Exception e) {
                throw new java.lang.IllegalStateException("Cannot load keystore", e);
            }
        }

        @android.annotation.NonNull
        public com.android.server.locksettings.UnifiedProfilePasswordCache getUnifiedProfilePasswordCache(java.security.KeyStore keyStore) {
            return new com.android.server.locksettings.UnifiedProfilePasswordCache(keyStore);
        }

        public boolean isHeadlessSystemUserMode() {
            return android.os.UserManager.isHeadlessSystemUserMode();
        }

        public boolean isMainUserPermanentAdmin() {
            return android.content.res.Resources.getSystem().getBoolean(android.R.bool.config_intrusiveNotificationLed);
        }
    }

    public LockSettingsService(android.content.Context context) {
        this(new com.android.server.locksettings.LockSettingsService.Injector(context));
    }

    @com.android.internal.annotations.VisibleForTesting
    protected LockSettingsService(com.android.server.locksettings.LockSettingsService.Injector injector) {
        this.mSeparateChallengeLock = new java.lang.Object();
        this.mDeviceProvisionedObserver = new com.android.server.locksettings.LockSettingsService.DeviceProvisionedObserver();
        this.mUserCreationAndRemovalLock = new java.lang.Object();
        this.mEarlyCreatedUsers = new android.util.SparseIntArray();
        this.mEarlyRemovedUsers = new android.util.SparseIntArray();
        this.mUserPasswordMetrics = new android.util.SparseArray<>();
        this.mHeadlessAuthSecretLock = new java.lang.Object();
        this.mUserManagerCache = new java.util.HashMap<>();
        this.mLockSettingsStateListeners = new java.util.concurrent.CopyOnWriteArrayList<>();
        this.mBroadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.locksettings.LockSettingsService.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                if ("android.intent.action.USER_ADDED".equals(intent.getAction())) {
                    if (!com.android.server.locksettings.LockSettingsService.FIX_UNLOCKED_DEVICE_REQUIRED_KEYS) {
                        android.security.AndroidKeyStoreMaintenance.onUserAdded(intent.getIntExtra("android.intent.extra.user_handle", 0));
                    }
                } else if ("android.intent.action.USER_STARTING".equals(intent.getAction())) {
                    com.android.server.locksettings.LockSettingsService.this.mStorage.prefetchUser(intent.getIntExtra("android.intent.extra.user_handle", 0));
                } else if ("android.intent.action.LOCALE_CHANGED".equals(intent.getAction())) {
                    com.android.server.locksettings.LockSettingsService.this.updateActivatedEncryptionNotifications("locale changed");
                }
            }
        };
        this.mInjector = injector;
        this.mContext = injector.getContext();
        this.mKeyStore = injector.getKeyStore();
        this.mRecoverableKeyStoreManager = injector.getRecoverableKeyStoreManager();
        this.mHandler = injector.getHandler(injector.getServiceThread());
        this.mStrongAuth = injector.getStrongAuth();
        this.mActivityManager = injector.getActivityManager();
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.USER_ADDED");
        intentFilter.addAction("android.intent.action.USER_STARTING");
        intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
        injector.getContext().registerReceiverAsUser(this.mBroadcastReceiver, android.os.UserHandle.ALL, intentFilter, null, null);
        this.mStorage = injector.getStorage();
        this.mNotificationManager = injector.getNotificationManager();
        this.mUserManager = injector.getUserManager();
        this.mStorageManager = injector.getStorageManager();
        this.mStrongAuthTracker = injector.getStrongAuthTracker();
        this.mStrongAuthTracker.register(this.mStrongAuth);
        this.mGatekeeperPasswords = new android.util.LongSparseArray<>();
        this.mSpManager = injector.getSyntheticPasswordManager(this.mStorage);
        this.mUnifiedProfilePasswordCache = injector.getUnifiedProfilePasswordCache(this.mKeyStore);
        this.mBiometricDeferredQueue = new com.android.server.locksettings.BiometricDeferredQueue(this.mSpManager, this.mHandler);
        this.mRebootEscrowManager = injector.getRebootEscrowManager(new com.android.server.locksettings.LockSettingsService.RebootEscrowCallbacks(), this.mStorage);
        com.android.server.LocalServices.addService(com.android.internal.widget.LockSettingsInternal.class, new com.android.server.locksettings.LockSettingsService.LocalService());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateActivatedEncryptionNotifications(java.lang.String str) {
        for (android.content.pm.UserInfo userInfo : this.mUserManager.getUsers()) {
            int i = 0;
            android.service.notification.StatusBarNotification[] activeNotifications = ((android.app.NotificationManager) this.mContext.createContextAsUser(android.os.UserHandle.of(userInfo.id), 0).getSystemService("notification")).getActiveNotifications();
            int length = activeNotifications.length;
            while (true) {
                if (i >= length) {
                    break;
                }
                if (activeNotifications[i].getId() != 9) {
                    i++;
                } else {
                    maybeShowEncryptionNotificationForUser(userInfo.id, str);
                    break;
                }
            }
        }
    }

    private void maybeShowEncryptionNotificationForUser(int i, java.lang.String str) {
        android.content.pm.UserInfo profileParent;
        android.content.pm.UserInfo userInfo = this.mUserManager.getUserInfo(i);
        if (!userInfo.isManagedProfile() || isCeStorageUnlocked(i)) {
            return;
        }
        android.os.UserHandle userHandle = userInfo.getUserHandle();
        if (isUserSecure(i) && !this.mUserManager.isUserUnlockingOrUnlocked(userHandle) && (profileParent = this.mUserManager.getProfileParent(i)) != null && this.mUserManager.isUserUnlockingOrUnlocked(profileParent.getUserHandle()) && !this.mUserManager.isQuietModeEnabled(userHandle)) {
            showEncryptionNotificationForProfile(userHandle, str);
        }
    }

    private void showEncryptionNotificationForProfile(android.os.UserHandle userHandle, java.lang.String str) {
        java.lang.String encryptionNotificationTitle = getEncryptionNotificationTitle();
        java.lang.String encryptionNotificationMessage = getEncryptionNotificationMessage();
        java.lang.String encryptionNotificationDetail = getEncryptionNotificationDetail();
        android.content.Intent createConfirmDeviceCredentialIntent = ((android.app.KeyguardManager) this.mContext.getSystemService("keyguard")).createConfirmDeviceCredentialIntent(null, null, userHandle.getIdentifier());
        if (createConfirmDeviceCredentialIntent != null && android.os.storage.StorageManager.isFileEncrypted()) {
            createConfirmDeviceCredentialIntent.setFlags(276824064);
            android.app.PendingIntent activity = android.app.PendingIntent.getActivity(this.mContext, 0, createConfirmDeviceCredentialIntent, android.hardware.audio.common.V2_0.AudioFormat.E_AC3);
            com.android.server.utils.Slogf.d(TAG, "Showing encryption notification for user %d; reason: %s", java.lang.Integer.valueOf(userHandle.getIdentifier()), str);
            showEncryptionNotification(userHandle, encryptionNotificationTitle, encryptionNotificationMessage, encryptionNotificationDetail, activity);
        }
    }

    private java.lang.String getEncryptionNotificationTitle() {
        return this.mInjector.getDevicePolicyManager().getResources().getString("Core.PROFILE_ENCRYPTED_TITLE", new java.util.function.Supplier() { // from class: com.android.server.locksettings.LockSettingsService$$ExternalSyntheticLambda6
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.String lambda$getEncryptionNotificationTitle$0;
                lambda$getEncryptionNotificationTitle$0 = com.android.server.locksettings.LockSettingsService.this.lambda$getEncryptionNotificationTitle$0();
                return lambda$getEncryptionNotificationTitle$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.String lambda$getEncryptionNotificationTitle$0() {
        return this.mContext.getString(android.R.string.policylab_encryptedStorage);
    }

    private java.lang.String getEncryptionNotificationDetail() {
        return this.mInjector.getDevicePolicyManager().getResources().getString("Core.PROFILE_ENCRYPTED_DETAIL", new java.util.function.Supplier() { // from class: com.android.server.locksettings.LockSettingsService$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.String lambda$getEncryptionNotificationDetail$1;
                lambda$getEncryptionNotificationDetail$1 = com.android.server.locksettings.LockSettingsService.this.lambda$getEncryptionNotificationDetail$1();
                return lambda$getEncryptionNotificationDetail$1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.String lambda$getEncryptionNotificationDetail$1() {
        return this.mContext.getString(android.R.string.policylab_disableCamera);
    }

    private java.lang.String getEncryptionNotificationMessage() {
        return this.mInjector.getDevicePolicyManager().getResources().getString("Core.PROFILE_ENCRYPTED_MESSAGE", new java.util.function.Supplier() { // from class: com.android.server.locksettings.LockSettingsService$$ExternalSyntheticLambda7
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.String lambda$getEncryptionNotificationMessage$2;
                lambda$getEncryptionNotificationMessage$2 = com.android.server.locksettings.LockSettingsService.this.lambda$getEncryptionNotificationMessage$2();
                return lambda$getEncryptionNotificationMessage$2;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.String lambda$getEncryptionNotificationMessage$2() {
        return this.mContext.getString(android.R.string.policylab_disableKeyguardFeatures);
    }

    private void showEncryptionNotification(android.os.UserHandle userHandle, java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, java.lang.CharSequence charSequence3, android.app.PendingIntent pendingIntent) {
        this.mNotificationManager.notifyAsUser(null, 9, new android.app.Notification.Builder(this.mContext, com.android.internal.notification.SystemNotificationChannels.DEVICE_ADMIN).setSmallIcon(android.R.drawable.ic_thermostat_notification).setWhen(0L).setOngoing(true).setTicker(charSequence).setColor(this.mContext.getColor(android.R.color.system_notification_accent_color)).setContentTitle(charSequence).setContentText(charSequence2).setSubText(charSequence3).setVisibility(1).setContentIntent(pendingIntent).build(), userHandle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideEncryptionNotification(android.os.UserHandle userHandle) {
        com.android.server.utils.Slogf.d(TAG, "Hiding encryption notification for user %d", java.lang.Integer.valueOf(userHandle.getIdentifier()));
        this.mNotificationManager.cancelAsUser(null, 9, userHandle);
    }

    @android.annotation.RequiresPermission(anyOf = {"android.permission.MANAGE_USERS", "android.permission.QUERY_USERS", "android.permission.INTERACT_ACROSS_USERS"}, conditional = true)
    @com.android.internal.annotations.VisibleForTesting
    void onUserStopped(int i) {
        android.content.pm.UserProperties userProperties;
        hideEncryptionNotification(new android.os.UserHandle(i));
        if (android.os.Flags.allowPrivateProfile() && android.multiuser.Flags.enableBiometricsToUnlockPrivateSpace() && (userProperties = this.mUserManager.getUserProperties(android.os.UserHandle.of(i))) != null && userProperties.getAllowStoppingUserWithDelayedLocking()) {
            return;
        }
        requireStrongAuth(com.android.internal.widget.LockPatternUtils.StrongAuthTracker.getDefaultFlags(this.mContext), i);
        synchronized (this) {
            this.mUserPasswordMetrics.remove(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUserStarting(int i) {
        maybeShowEncryptionNotificationForUser(i, "user started");
    }

    private void removeStateForReusedUserIdIfNecessary(int i, int i2) {
        int i3;
        if (i != 0 && (i3 = this.mStorage.getInt(USER_SERIAL_NUMBER_KEY, -1, i)) != i2) {
            if (i3 != -1) {
                com.android.server.utils.Slogf.i(TAG, "Removing stale state for reused userId %d (serial %d => %d)", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i3), java.lang.Integer.valueOf(i2));
                removeUserState(i);
            }
            this.mStorage.setInt(USER_SERIAL_NUMBER_KEY, i2, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUserUnlocking(final int i) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.locksettings.LockSettingsService.1
            @Override // java.lang.Runnable
            public void run() {
                com.android.server.locksettings.LockSettingsService.this.hideEncryptionNotification(new android.os.UserHandle(i));
                if (com.android.server.locksettings.LockSettingsService.this.isCredentialSharableWithParent(i)) {
                    com.android.server.locksettings.LockSettingsService.this.tieProfileLockIfNecessary(i, com.android.internal.widget.LockscreenCredential.createNone());
                }
            }
        });
    }

    public void systemReady() {
        checkWritePermission();
        this.mHasSecureLockScreen = this.mContext.getPackageManager().hasSystemFeature("android.software.secure_lock_screen");
        migrateOldData();
        getAuthSecretHal();
        this.mDeviceProvisionedObserver.onSystemReady();
        com.android.internal.widget.LockPatternUtils.invalidateCredentialTypeCache();
        this.mStorage.prefetchUser(0);
        this.mBiometricDeferredQueue.systemReady(this.mInjector.getFingerprintManager(), this.mInjector.getFaceManager(), this.mInjector.getBiometricManager());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadEscrowData() {
        this.mRebootEscrowManager.loadRebootEscrowDataIfAvailable(this.mHandler);
    }

    private void getAuthSecretHal() {
        this.mAuthSecretService = android.hardware.authsecret.IAuthSecret.Stub.asInterface(android.os.ServiceManager.waitForDeclaredService(android.hardware.authsecret.IAuthSecret.DESCRIPTOR + "/default"));
        if (this.mAuthSecretService != null) {
            android.util.Slog.i(TAG, "Device implements AIDL AuthSecret HAL");
            return;
        }
        try {
            this.mAuthSecretService = new com.android.server.locksettings.AuthSecretHidlAdapter(android.hardware.authsecret.V1_0.IAuthSecret.getService(true));
            android.util.Slog.i(TAG, "Device implements HIDL AuthSecret HAL");
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Failed to get AuthSecret HAL(hidl)", e);
        } catch (java.util.NoSuchElementException e2) {
            android.util.Slog.i(TAG, "Device doesn't implement AuthSecret HAL");
        }
    }

    private void migrateOldData() {
        boolean migrateKeyNamespace;
        if (getString(MIGRATED_KEYSTORE_NS, null, 0) == null) {
            synchronized (this.mSpManager) {
                migrateKeyNamespace = this.mSpManager.migrateKeyNamespace() & true;
            }
            if (migrateProfileLockKeys() & migrateKeyNamespace) {
                setString(MIGRATED_KEYSTORE_NS, "true", 0);
                android.util.Slog.i(TAG, "Migrated keys to LSS namespace");
            } else {
                android.util.Slog.w(TAG, "Failed to migrate keys to LSS namespace");
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void migrateOldDataAfterSystemReady() {
        if (com.android.internal.widget.LockPatternUtils.frpCredentialEnabled(this.mContext) && !getBoolean(MIGRATED_FRP2, false, 0)) {
            migrateFrpCredential();
            setBoolean(MIGRATED_FRP2, true, 0);
        }
    }

    private void migrateFrpCredential() {
        com.android.server.locksettings.LockSettingsStorage.PersistentData readPersistentDataBlock = this.mStorage.readPersistentDataBlock();
        if (readPersistentDataBlock != com.android.server.locksettings.LockSettingsStorage.PersistentData.NONE && !readPersistentDataBlock.isBadFormatFromAndroid14Beta()) {
            return;
        }
        for (android.content.pm.UserInfo userInfo : this.mUserManager.getUsers()) {
            if (com.android.internal.widget.LockPatternUtils.userOwnsFrpCredential(this.mContext, userInfo) && isUserSecure(userInfo.id)) {
                synchronized (this.mSpManager) {
                    this.mSpManager.migrateFrpPasswordLocked(getCurrentLskfBasedProtectorId(userInfo.id), userInfo, redactActualQualityToMostLenientEquivalentQuality((int) getLong("lockscreen.password_type", 0L, userInfo.id)));
                }
                return;
            }
        }
    }

    private boolean migrateProfileLockKeys() {
        java.util.List users = this.mUserManager.getUsers();
        int size = users.size();
        boolean z = true;
        for (int i = 0; i < size; i++) {
            android.content.pm.UserInfo userInfo = (android.content.pm.UserInfo) users.get(i);
            if (isCredentialSharableWithParent(userInfo.id) && !getSeparateProfileChallengeEnabledInternal(userInfo.id)) {
                z = z & com.android.server.locksettings.SyntheticPasswordCrypto.migrateLockSettingsKey(PROFILE_KEY_NAME_ENCRYPT + userInfo.id) & com.android.server.locksettings.SyntheticPasswordCrypto.migrateLockSettingsKey(PROFILE_KEY_NAME_DECRYPT + userInfo.id);
            }
        }
        return z;
    }

    @com.android.internal.annotations.VisibleForTesting
    void deleteRepairModePersistentDataIfNeeded() {
        if (!com.android.internal.widget.LockPatternUtils.isRepairModeSupported(this.mContext) || com.android.internal.widget.LockPatternUtils.isRepairModeActive(this.mContext) || this.mInjector.isGsiRunning()) {
            return;
        }
        this.mStorage.deleteRepairModePersistentData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onThirdPartyAppsStarted() {
        synchronized (this.mUserCreationAndRemovalLock) {
            for (int i = 0; i < this.mEarlyRemovedUsers.size(); i++) {
                try {
                    int keyAt = this.mEarlyRemovedUsers.keyAt(i);
                    com.android.server.utils.Slogf.i(TAG, "Removing locksettings state for removed user %d now that boot is complete", java.lang.Integer.valueOf(keyAt));
                    removeUserState(keyAt);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            this.mEarlyRemovedUsers = null;
            for (int i2 = 0; i2 < this.mEarlyCreatedUsers.size(); i2++) {
                int keyAt2 = this.mEarlyCreatedUsers.keyAt(i2);
                removeStateForReusedUserIdIfNecessary(keyAt2, this.mEarlyCreatedUsers.valueAt(i2));
                com.android.server.utils.Slogf.i(TAG, "Creating locksettings state for user %d now that boot is complete", java.lang.Integer.valueOf(keyAt2));
                initializeSyntheticPassword(keyAt2);
            }
            this.mEarlyCreatedUsers = null;
            if (FIX_UNLOCKED_DEVICE_REQUIRED_KEYS) {
                if (!getBoolean(MIGRATED_SP_FULL, false, 0)) {
                    for (android.content.pm.UserInfo userInfo : this.mUserManager.getAliveUsers()) {
                        removeStateForReusedUserIdIfNecessary(userInfo.id, userInfo.serialNumber);
                        synchronized (this.mSpManager) {
                            migrateUserToSpWithBoundKeysLocked(userInfo.id);
                        }
                    }
                    setBoolean(MIGRATED_SP_FULL, true, 0);
                }
                this.mThirdPartyAppsStarted = true;
            } else {
                if (getString(MIGRATED_SP_CE_ONLY, null, 0) == null) {
                    for (android.content.pm.UserInfo userInfo2 : this.mUserManager.getAliveUsers()) {
                        removeStateForReusedUserIdIfNecessary(userInfo2.id, userInfo2.serialNumber);
                        synchronized (this.mSpManager) {
                            migrateUserToSpWithBoundCeKeyLocked(userInfo2.id);
                        }
                    }
                    setString(MIGRATED_SP_CE_ONLY, "true", 0);
                }
                if (getBoolean(MIGRATED_SP_FULL, false, 0)) {
                    setBoolean(MIGRATED_SP_FULL, false, 0);
                }
                this.mThirdPartyAppsStarted = true;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mSpManager"})
    private void migrateUserToSpWithBoundCeKeyLocked(int i) {
        if (isUserSecure(i)) {
            com.android.server.utils.Slogf.d(TAG, "User %d is secured; no migration needed", java.lang.Integer.valueOf(i));
            return;
        }
        long currentLskfBasedProtectorId = getCurrentLskfBasedProtectorId(i);
        if (currentLskfBasedProtectorId == 0) {
            com.android.server.utils.Slogf.i(TAG, "Migrating unsecured user %d to SP-based credential", java.lang.Integer.valueOf(i));
            initializeSyntheticPassword(i);
            return;
        }
        com.android.server.utils.Slogf.i(TAG, "Existing unsecured user %d has a synthetic password; re-encrypting CE key with it", java.lang.Integer.valueOf(i));
        com.android.server.locksettings.SyntheticPasswordManager.AuthenticationResult unlockLskfBasedProtector = this.mSpManager.unlockLskfBasedProtector(getGateKeeperService(), currentLskfBasedProtectorId, com.android.internal.widget.LockscreenCredential.createNone(), i, null);
        if (unlockLskfBasedProtector.syntheticPassword == null) {
            com.android.server.utils.Slogf.wtf(TAG, "Failed to unwrap synthetic password for unsecured user %d", java.lang.Integer.valueOf(i));
        } else {
            setCeStorageProtection(i, unlockLskfBasedProtector.syntheticPassword);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mSpManager"})
    private void migrateUserToSpWithBoundKeysLocked(int i) {
        if (isUserSecure(i)) {
            com.android.server.utils.Slogf.d(TAG, "User %d is secured; no migration needed", java.lang.Integer.valueOf(i));
            return;
        }
        long currentLskfBasedProtectorId = getCurrentLskfBasedProtectorId(i);
        if (currentLskfBasedProtectorId == 0) {
            com.android.server.utils.Slogf.i(TAG, "Migrating unsecured user %d to SP-based credential", java.lang.Integer.valueOf(i));
            initializeSyntheticPassword(i);
            return;
        }
        com.android.server.utils.Slogf.i(TAG, "Existing unsecured user %d has a synthetic password", java.lang.Integer.valueOf(i));
        com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword syntheticPassword = this.mSpManager.unlockLskfBasedProtector(getGateKeeperService(), currentLskfBasedProtectorId, com.android.internal.widget.LockscreenCredential.createNone(), i, null).syntheticPassword;
        if (syntheticPassword == null) {
            com.android.server.utils.Slogf.wtf(TAG, "Failed to unwrap synthetic password for unsecured user %d", java.lang.Integer.valueOf(i));
            return;
        }
        if (getString(MIGRATED_SP_CE_ONLY, null, 0) == null) {
            com.android.server.utils.Slogf.i(TAG, "Encrypting CE key of user %d with synthetic password", java.lang.Integer.valueOf(i));
            setCeStorageProtection(i, syntheticPassword);
        }
        com.android.server.utils.Slogf.i(TAG, "Initializing Keystore super keys for user %d", java.lang.Integer.valueOf(i));
        initKeystoreSuperKeys(i, syntheticPassword, true);
    }

    private int redactActualQualityToMostLenientEquivalentQuality(int i) {
        switch (i) {
            case 131072:
            case 196608:
                return 131072;
            case 262144:
            case 327680:
            case 393216:
                return 262144;
            default:
                return i;
        }
    }

    private void enforceFrpResolved() {
        int mainUserId = this.mInjector.getUserManagerInternal().getMainUserId();
        if (mainUserId < 0) {
            android.util.Slog.d(TAG, "No Main user on device; skipping enforceFrpResolved");
            return;
        }
        android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
        boolean z = false;
        boolean z2 = android.provider.Settings.Secure.getIntForUser(contentResolver, "user_setup_complete", 0, mainUserId) == 0;
        if (android.security.Flags.frpEnforcement()) {
            z = this.mStorage.isFactoryResetProtectionActive();
        } else if (android.provider.Settings.Global.getInt(contentResolver, "secure_frp_mode", 0) == 1) {
            z = true;
        }
        if (z2 && z) {
            throw new java.lang.SecurityException("Cannot change credential in SUW while factory reset protection is not resolved yet");
        }
    }

    private final void checkWritePermission() {
        this.mContext.enforceCallingOrSelfPermission(PERMISSION, "LockSettingsWrite");
    }

    private final void checkPasswordReadPermission() {
        this.mContext.enforceCallingOrSelfPermission(PERMISSION, "LockSettingsRead");
    }

    private final void checkPasswordHavePermission() {
        this.mContext.enforceCallingOrSelfPermission(PERMISSION, "LockSettingsHave");
    }

    private final void checkDatabaseReadPermission(java.lang.String str, int i) {
        if (!hasPermission(PERMISSION)) {
            throw new java.lang.SecurityException("uid=" + com.android.internal.widget.ILockSettings.Stub.getCallingUid() + " needs permission " + PERMISSION + " to read " + str + " for user " + i);
        }
    }

    private final void checkBiometricPermission() {
        this.mContext.enforceCallingOrSelfPermission(BIOMETRIC_PERMISSION, "LockSettingsBiometric");
    }

    private boolean hasPermission(java.lang.String str) {
        return this.mContext.checkCallingOrSelfPermission(str) == 0;
    }

    private void checkManageWeakEscrowTokenMethodUsage() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_WEAK_ESCROW_TOKEN", "Requires MANAGE_WEAK_ESCROW_TOKEN permission.");
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive")) {
            throw new java.lang.IllegalArgumentException("Weak escrow token are only for automotive devices.");
        }
    }

    public boolean hasSecureLockScreen() {
        return this.mHasSecureLockScreen;
    }

    public boolean getSeparateProfileChallengeEnabled(int i) {
        checkDatabaseReadPermission(SEPARATE_PROFILE_CHALLENGE_KEY, i);
        return getSeparateProfileChallengeEnabledInternal(i);
    }

    private boolean getSeparateProfileChallengeEnabledInternal(int i) {
        boolean z;
        synchronized (this.mSeparateChallengeLock) {
            z = this.mStorage.getBoolean(SEPARATE_PROFILE_CHALLENGE_KEY, false, i);
        }
        return z;
    }

    public void setSeparateProfileChallengeEnabled(int i, boolean z, com.android.internal.widget.LockscreenCredential lockscreenCredential) {
        checkWritePermission();
        if (!this.mHasSecureLockScreen && lockscreenCredential != null && lockscreenCredential.getType() != -1) {
            throw new java.lang.UnsupportedOperationException("This operation requires secure lock screen feature.");
        }
        synchronized (this.mSeparateChallengeLock) {
            if (lockscreenCredential == null) {
                lockscreenCredential = com.android.internal.widget.LockscreenCredential.createNone();
            }
            setSeparateProfileChallengeEnabledLocked(i, z, lockscreenCredential);
        }
        notifySeparateProfileChallengeChanged(i);
    }

    @com.android.internal.annotations.GuardedBy({"mSeparateChallengeLock"})
    private void setSeparateProfileChallengeEnabledLocked(int i, boolean z, com.android.internal.widget.LockscreenCredential lockscreenCredential) {
        boolean z2 = getBoolean(SEPARATE_PROFILE_CHALLENGE_KEY, false, i);
        setBoolean(SEPARATE_PROFILE_CHALLENGE_KEY, z, i);
        try {
            if (z) {
                this.mStorage.removeChildProfileLock(i);
                removeKeystoreProfileKey(i);
            } else {
                tieProfileLockIfNecessary(i, lockscreenCredential);
            }
        } catch (java.lang.IllegalStateException e) {
            setBoolean(SEPARATE_PROFILE_CHALLENGE_KEY, z2, i);
            throw e;
        }
    }

    private void notifySeparateProfileChallengeChanged(final int i) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.locksettings.LockSettingsService$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.locksettings.LockSettingsService.lambda$notifySeparateProfileChallengeChanged$3(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$notifySeparateProfileChallengeChanged$3(int i) {
        android.app.admin.DevicePolicyManagerInternal devicePolicyManagerInternal = (android.app.admin.DevicePolicyManagerInternal) com.android.server.LocalServices.getService(android.app.admin.DevicePolicyManagerInternal.class);
        if (devicePolicyManagerInternal != null) {
            devicePolicyManagerInternal.reportSeparateProfileChallengeChanged(i);
        }
    }

    public void setBoolean(java.lang.String str, boolean z, int i) {
        checkWritePermission();
        java.util.Objects.requireNonNull(str);
        this.mStorage.setBoolean(str, z, i);
    }

    public void setLong(java.lang.String str, long j, int i) {
        checkWritePermission();
        java.util.Objects.requireNonNull(str);
        this.mStorage.setLong(str, j, i);
    }

    public void setString(java.lang.String str, java.lang.String str2, int i) {
        checkWritePermission();
        java.util.Objects.requireNonNull(str);
        this.mStorage.setString(str, str2, i);
    }

    public boolean getBoolean(java.lang.String str, boolean z, int i) {
        checkDatabaseReadPermission(str, i);
        return this.mStorage.getBoolean(str, z, i);
    }

    public long getLong(java.lang.String str, long j, int i) {
        checkDatabaseReadPermission(str, i);
        return this.mStorage.getLong(str, j, i);
    }

    public java.lang.String getString(java.lang.String str, java.lang.String str2, int i) {
        checkDatabaseReadPermission(str, i);
        return this.mStorage.getString(str, str2, i);
    }

    private int getKeyguardStoredQuality(int i) {
        return (int) this.mStorage.getLong("lockscreen.password_type", 0L, i);
    }

    public int getPinLength(int i) {
        checkPasswordHavePermission();
        android.app.admin.PasswordMetrics userPasswordMetrics = getUserPasswordMetrics(i);
        if (userPasswordMetrics != null && userPasswordMetrics.credType == 3) {
            return userPasswordMetrics.length;
        }
        synchronized (this.mSpManager) {
            try {
                long currentLskfBasedProtectorId = getCurrentLskfBasedProtectorId(i);
                if (currentLskfBasedProtectorId == 0) {
                    return -1;
                }
                return this.mSpManager.getPinLength(currentLskfBasedProtectorId, i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean refreshStoredPinLength(int i) {
        checkPasswordHavePermission();
        synchronized (this.mSpManager) {
            try {
                android.app.admin.PasswordMetrics userPasswordMetrics = getUserPasswordMetrics(i);
                if (userPasswordMetrics != null) {
                    return this.mSpManager.refreshPinLengthOnDisk(userPasswordMetrics, getCurrentLskfBasedProtectorId(i), i);
                }
                android.util.Log.w(TAG, "PasswordMetrics is not available");
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int getCredentialType(int i) {
        checkPasswordHavePermission();
        return getCredentialTypeInternal(i);
    }

    private int getCredentialTypeInternal(int i) {
        if (com.android.internal.widget.LockPatternUtils.isSpecialUserId(i)) {
            return this.mSpManager.getSpecialUserCredentialType(i);
        }
        synchronized (this.mSpManager) {
            try {
                long currentLskfBasedProtectorId = getCurrentLskfBasedProtectorId(i);
                if (currentLskfBasedProtectorId == 0) {
                    return -1;
                }
                int credentialType = this.mSpManager.getCredentialType(currentLskfBasedProtectorId, i);
                if (credentialType != 2) {
                    return credentialType;
                }
                return com.android.internal.widget.LockPatternUtils.pinOrPasswordQualityToCredentialType(getKeyguardStoredQuality(i));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isUserSecure(int i) {
        return getCredentialTypeInternal(i) != -1;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setKeystorePassword(byte[] bArr, int i) {
        android.security.AndroidKeyStoreMaintenance.onUserPasswordChanged(i, bArr);
    }

    @com.android.internal.annotations.VisibleForTesting
    void initKeystoreSuperKeys(int i, com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword syntheticPassword, boolean z) {
        byte[] deriveKeyStorePassword = syntheticPassword.deriveKeyStorePassword();
        try {
            if (android.security.AndroidKeyStoreMaintenance.initUserSuperKeys(i, deriveKeyStorePassword, z) != 0) {
                throw new java.lang.IllegalStateException("Failed to initialize Keystore super keys for user " + i);
            }
        } finally {
            java.util.Arrays.fill(deriveKeyStorePassword, (byte) 0);
        }
    }

    private void unlockKeystore(int i, com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword syntheticPassword) {
        android.security.Authorization.onDeviceUnlocked(i, syntheticPassword.deriveKeyStorePassword());
    }

    @com.android.internal.annotations.VisibleForTesting
    protected com.android.internal.widget.LockscreenCredential getDecryptedPasswordForTiedProfile(int i) throws java.security.KeyStoreException, java.security.UnrecoverableKeyException, java.security.NoSuchAlgorithmException, javax.crypto.NoSuchPaddingException, java.security.InvalidKeyException, java.security.InvalidAlgorithmParameterException, javax.crypto.IllegalBlockSizeException, javax.crypto.BadPaddingException, java.security.cert.CertificateException, java.io.IOException {
        com.android.server.utils.Slogf.d(TAG, "Decrypting password for tied profile %d", java.lang.Integer.valueOf(i));
        byte[] readChildProfileLock = this.mStorage.readChildProfileLock(i);
        if (readChildProfileLock == null) {
            throw new java.io.FileNotFoundException("Child profile lock file not found");
        }
        byte[] copyOfRange = java.util.Arrays.copyOfRange(readChildProfileLock, 0, 12);
        byte[] copyOfRange2 = java.util.Arrays.copyOfRange(readChildProfileLock, 12, readChildProfileLock.length);
        javax.crypto.SecretKey secretKey = (javax.crypto.SecretKey) this.mKeyStore.getKey(PROFILE_KEY_NAME_DECRYPT + i, null);
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(2, secretKey, new javax.crypto.spec.GCMParameterSpec(128, copyOfRange));
        byte[] doFinal = cipher.doFinal(copyOfRange2);
        com.android.internal.widget.LockscreenCredential createUnifiedProfilePassword = com.android.internal.widget.LockscreenCredential.createUnifiedProfilePassword(doFinal);
        java.util.Arrays.fill(doFinal, (byte) 0);
        try {
            this.mUnifiedProfilePasswordCache.storePassword(i, createUnifiedProfilePassword, getGateKeeperService().getSecureUserId(this.mUserManager.getProfileParent(i).id));
        } catch (android.os.RemoteException e) {
            com.android.server.utils.Slogf.w(TAG, "Failed to talk to GateKeeper service", e);
        }
        return createUnifiedProfilePassword;
    }

    private void unlockChildProfile(int i) {
        try {
            doVerifyCredential(getDecryptedPasswordForTiedProfile(i), i, null, 0);
        } catch (java.io.IOException | java.security.InvalidAlgorithmParameterException | java.security.InvalidKeyException | java.security.KeyStoreException | java.security.NoSuchAlgorithmException | java.security.UnrecoverableKeyException | java.security.cert.CertificateException | javax.crypto.BadPaddingException | javax.crypto.IllegalBlockSizeException | javax.crypto.NoSuchPaddingException e) {
            if (e instanceof java.io.FileNotFoundException) {
                android.util.Slog.i(TAG, "Child profile key not found");
            } else {
                android.util.Slog.e(TAG, "Failed to decrypt child profile key", e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: unlockUser, reason: merged with bridge method [inline-methods] */
    public void lambda$setLockCredentialWithToken$7(int i) {
        boolean isUserUnlockingOrUnlocked = this.mUserManager.isUserUnlockingOrUnlocked(i);
        final java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
        try {
            this.mActivityManager.unlockUser2(i, new android.os.IProgressListener.Stub() { // from class: com.android.server.locksettings.LockSettingsService.3
                public void onStarted(int i2, android.os.Bundle bundle) throws android.os.RemoteException {
                    android.util.Slog.d(com.android.server.locksettings.LockSettingsService.TAG, "unlockUser started");
                }

                public void onProgress(int i2, int i3, android.os.Bundle bundle) throws android.os.RemoteException {
                    android.util.Slog.d(com.android.server.locksettings.LockSettingsService.TAG, "unlockUser progress " + i3);
                }

                public void onFinished(int i2, android.os.Bundle bundle) throws android.os.RemoteException {
                    android.util.Slog.d(com.android.server.locksettings.LockSettingsService.TAG, "unlockUser finished");
                    countDownLatch.countDown();
                }
            });
            try {
                countDownLatch.await(15L, java.util.concurrent.TimeUnit.SECONDS);
            } catch (java.lang.InterruptedException e) {
                java.lang.Thread.currentThread().interrupt();
            }
            if (isCredentialSharableWithParent(i)) {
                if (!hasUnifiedChallenge(i)) {
                    this.mBiometricDeferredQueue.processPendingLockoutResets();
                    return;
                }
                return;
            }
            for (android.content.pm.UserInfo userInfo : this.mUserManager.getProfiles(i)) {
                if (userInfo.id != i && isCredentialSharableWithParent(userInfo.id)) {
                    if (hasUnifiedChallenge(userInfo.id)) {
                        if (this.mUserManager.isUserRunning(userInfo.id)) {
                            unlockChildProfile(userInfo.id);
                        } else {
                            try {
                                getDecryptedPasswordForTiedProfile(userInfo.id);
                            } catch (java.io.IOException | java.security.GeneralSecurityException e2) {
                                android.util.Slog.d(TAG, "Cache unified profile password failed", e2);
                            }
                        }
                    }
                    if (isUserUnlockingOrUnlocked) {
                        continue;
                    } else {
                        long clearCallingIdentity = com.android.internal.widget.ILockSettings.Stub.clearCallingIdentity();
                        try {
                            maybeShowEncryptionNotificationForUser(userInfo.id, "parent unlocked");
                        } finally {
                            com.android.internal.widget.ILockSettings.Stub.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                }
            }
            this.mBiometricDeferredQueue.processPendingLockoutResets();
        } catch (android.os.RemoteException e3) {
            throw e3.rethrowAsRuntimeException();
        }
    }

    private boolean hasUnifiedChallenge(int i) {
        return !getSeparateProfileChallengeEnabledInternal(i) && this.mStorage.hasChildProfileLock(i);
    }

    private java.util.Map<java.lang.Integer, com.android.internal.widget.LockscreenCredential> getDecryptedPasswordsForAllTiedProfiles(int i) {
        if (isCredentialSharableWithParent(i)) {
            return null;
        }
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        java.util.List profiles = this.mUserManager.getProfiles(i);
        int size = profiles.size();
        for (int i2 = 0; i2 < size; i2++) {
            android.content.pm.UserInfo userInfo = (android.content.pm.UserInfo) profiles.get(i2);
            if (isCredentialSharableWithParent(userInfo.id)) {
                int i3 = userInfo.id;
                if (!getSeparateProfileChallengeEnabledInternal(i3)) {
                    try {
                        arrayMap.put(java.lang.Integer.valueOf(i3), getDecryptedPasswordForTiedProfile(i3));
                    } catch (java.io.IOException | java.security.InvalidAlgorithmParameterException | java.security.InvalidKeyException | java.security.KeyStoreException | java.security.NoSuchAlgorithmException | java.security.UnrecoverableKeyException | java.security.cert.CertificateException | javax.crypto.BadPaddingException | javax.crypto.IllegalBlockSizeException | javax.crypto.NoSuchPaddingException e) {
                        android.util.Slog.e(TAG, "getDecryptedPasswordsForAllTiedProfiles failed for user " + i3, e);
                    }
                }
            }
        }
        return arrayMap;
    }

    private void synchronizeUnifiedChallengeForProfiles(int i, java.util.Map<java.lang.Integer, com.android.internal.widget.LockscreenCredential> map) {
        if (isCredentialSharableWithParent(i)) {
            return;
        }
        boolean isUserSecure = isUserSecure(i);
        java.util.List profiles = this.mUserManager.getProfiles(i);
        int size = profiles.size();
        for (int i2 = 0; i2 < size; i2++) {
            int i3 = ((android.content.pm.UserInfo) profiles.get(i2)).id;
            if (isCredentialSharableWithParent(i3) && !getSeparateProfileChallengeEnabledInternal(i3)) {
                if (isUserSecure) {
                    tieProfileLockIfNecessary(i3, com.android.internal.widget.LockscreenCredential.createNone());
                } else if (map != null && map.containsKey(java.lang.Integer.valueOf(i3))) {
                    setLockCredentialInternal(com.android.internal.widget.LockscreenCredential.createNone(), map.get(java.lang.Integer.valueOf(i3)), i3, true);
                    this.mStorage.removeChildProfileLock(i3);
                    removeKeystoreProfileKey(i3);
                } else {
                    android.util.Slog.wtf(TAG, "Attempt to clear tied challenge, but no password supplied.");
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isProfileWithUnifiedLock(int i) {
        return isCredentialSharableWithParent(i) && !getSeparateProfileChallengeEnabledInternal(i);
    }

    public byte getLockPatternSize(int i) {
        return this.mStorage.getLockPatternSize(i);
    }

    private void sendCredentialsOnUnlockIfRequired(com.android.internal.widget.LockscreenCredential lockscreenCredential, int i) {
        if (com.android.internal.widget.LockPatternUtils.isSpecialUserId(i) || lockscreenCredential.isNone() || isProfileWithUnifiedLock(i)) {
            return;
        }
        java.util.Iterator<java.lang.Integer> it = getProfilesWithSameLockScreen(i).iterator();
        while (it.hasNext()) {
            this.mRecoverableKeyStoreManager.lockScreenSecretAvailable(lockscreenCredential.getType(), lockscreenCredential.getCredential(), it.next().intValue());
        }
    }

    private void sendCredentialsOnChangeIfRequired(com.android.internal.widget.LockscreenCredential lockscreenCredential, int i, boolean z) {
        if (z) {
            return;
        }
        byte[] credential = lockscreenCredential.isNone() ? null : lockscreenCredential.getCredential();
        java.util.Iterator<java.lang.Integer> it = getProfilesWithSameLockScreen(i).iterator();
        while (it.hasNext()) {
            this.mRecoverableKeyStoreManager.lockScreenSecretChanged(lockscreenCredential.getType(), credential, it.next().intValue());
        }
    }

    private java.util.Set<java.lang.Integer> getProfilesWithSameLockScreen(int i) {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        for (android.content.pm.UserInfo userInfo : this.mUserManager.getProfiles(i)) {
            if (userInfo.id == i || (userInfo.profileGroupId == i && isProfileWithUnifiedLock(userInfo.id))) {
                arraySet.add(java.lang.Integer.valueOf(userInfo.id));
            }
        }
        return arraySet;
    }

    public boolean setLockCredential(com.android.internal.widget.LockscreenCredential lockscreenCredential, com.android.internal.widget.LockscreenCredential lockscreenCredential2, int i) {
        if (!this.mHasSecureLockScreen && lockscreenCredential != null && lockscreenCredential.getType() != -1) {
            throw new java.lang.UnsupportedOperationException("This operation requires secure lock screen feature");
        }
        if (!hasPermission(PERMISSION) && !hasPermission("android.permission.SET_AND_VERIFY_LOCKSCREEN_CREDENTIALS") && (!hasPermission("android.permission.SET_INITIAL_LOCK") || !lockscreenCredential2.isNone())) {
            throw new java.lang.SecurityException("setLockCredential requires SET_AND_VERIFY_LOCKSCREEN_CREDENTIALS or android.permission.ACCESS_KEYGUARD_SECURE_STORAGE");
        }
        lockscreenCredential.validateBasicRequirements();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            enforceFrpResolved();
            if (!lockscreenCredential2.isNone() && isProfileWithUnifiedLock(i)) {
                verifyCredential(lockscreenCredential2, this.mUserManager.getProfileParent(i).id, 0);
                lockscreenCredential2.zeroize();
                lockscreenCredential2 = com.android.internal.widget.LockscreenCredential.createNone();
            }
            synchronized (this.mSeparateChallengeLock) {
                if (!setLockCredentialInternal(lockscreenCredential, lockscreenCredential2, i, false)) {
                    scheduleGc();
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return false;
                }
                setSeparateProfileChallengeEnabledLocked(i, true, null);
                notifyPasswordChanged(lockscreenCredential, i);
                if (isCredentialSharableWithParent(i)) {
                    setDeviceUnlockedForUser(i);
                }
                notifySeparateProfileChallengeChanged(i);
                onPostPasswordChanged(lockscreenCredential, i);
                scheduleGc();
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0073 A[Catch: all -> 0x001b, TryCatch #2 {, blocks: (B:4:0x0009, B:6:0x000f, B:9:0x0015, B:11:0x0030, B:15:0x004a, B:18:0x0052, B:20:0x0058, B:21:0x005f, B:24:0x0061, B:25:0x0069, B:26:0x006a, B:27:0x0071, B:29:0x0073, B:30:0x007c, B:36:0x001e, B:34:0x0027), top: B:3:0x0009, inners: #3 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean setLockCredentialInternal(com.android.internal.widget.LockscreenCredential lockscreenCredential, com.android.internal.widget.LockscreenCredential lockscreenCredential2, int i, boolean z) {
        com.android.internal.widget.LockscreenCredential lockscreenCredential3;
        com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword syntheticPassword;
        java.util.Objects.requireNonNull(lockscreenCredential);
        java.util.Objects.requireNonNull(lockscreenCredential2);
        synchronized (this.mSpManager) {
            if (lockscreenCredential2.isNone() && isProfileWithUnifiedLock(i)) {
                try {
                    lockscreenCredential3 = getDecryptedPasswordForTiedProfile(i);
                } catch (java.io.FileNotFoundException e) {
                    android.util.Slog.i(TAG, "Child profile key not found");
                } catch (java.io.IOException | java.security.InvalidAlgorithmParameterException | java.security.InvalidKeyException | java.security.KeyStoreException | java.security.NoSuchAlgorithmException | java.security.UnrecoverableKeyException | java.security.cert.CertificateException | javax.crypto.BadPaddingException | javax.crypto.IllegalBlockSizeException | javax.crypto.NoSuchPaddingException e2) {
                    android.util.Slog.e(TAG, "Failed to decrypt child profile key", e2);
                }
                com.android.server.locksettings.SyntheticPasswordManager.AuthenticationResult unlockLskfBasedProtector = this.mSpManager.unlockLskfBasedProtector(getGateKeeperService(), getCurrentLskfBasedProtectorId(i), lockscreenCredential3, i, null);
                com.android.internal.widget.VerifyCredentialResponse verifyCredentialResponse = unlockLskfBasedProtector.gkResponse;
                syntheticPassword = unlockLskfBasedProtector.syntheticPassword;
                if (syntheticPassword != null) {
                    if (verifyCredentialResponse != null && verifyCredentialResponse.getResponseCode() != -1) {
                        if (verifyCredentialResponse.getResponseCode() == 1) {
                            android.util.Slog.w(TAG, "Failed to enroll: rate limit exceeded.");
                            return false;
                        }
                        throw new java.lang.IllegalStateException("password change failed");
                    }
                    android.util.Slog.w(TAG, "Failed to enroll: incorrect credential.");
                    return false;
                }
                onSyntheticPasswordUnlocked(i, syntheticPassword);
                setLockCredentialWithSpLocked(lockscreenCredential, syntheticPassword, i);
                sendCredentialsOnChangeIfRequired(lockscreenCredential, i, z);
                return true;
            }
            lockscreenCredential3 = lockscreenCredential2;
            com.android.server.locksettings.SyntheticPasswordManager.AuthenticationResult unlockLskfBasedProtector2 = this.mSpManager.unlockLskfBasedProtector(getGateKeeperService(), getCurrentLskfBasedProtectorId(i), lockscreenCredential3, i, null);
            com.android.internal.widget.VerifyCredentialResponse verifyCredentialResponse2 = unlockLskfBasedProtector2.gkResponse;
            syntheticPassword = unlockLskfBasedProtector2.syntheticPassword;
            if (syntheticPassword != null) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPostPasswordChanged(com.android.internal.widget.LockscreenCredential lockscreenCredential, int i) {
        updatePasswordHistory(lockscreenCredential, i);
        ((android.app.trust.TrustManager) this.mContext.getSystemService(android.app.trust.TrustManager.class)).reportEnabledTrustAgentsChanged(i);
        sendMainUserCredentialChangedNotificationIfNeeded(i);
    }

    private void updatePasswordHistory(com.android.internal.widget.LockscreenCredential lockscreenCredential, int i) {
        if (lockscreenCredential.isNone() || lockscreenCredential.isPattern()) {
            return;
        }
        java.lang.String string = getString("lockscreen.passwordhistory", null, i);
        java.lang.String str = "";
        if (string == null) {
            string = "";
        }
        int requestedPasswordHistoryLength = getRequestedPasswordHistoryLength(i);
        if (requestedPasswordHistoryLength != 0) {
            com.android.server.utils.Slogf.d(TAG, "Adding new password to password history for user %d", java.lang.Integer.valueOf(i));
            str = lockscreenCredential.passwordToHistoryHash(getSalt(i).getBytes(), getHashFactor(lockscreenCredential, i));
            if (str == null) {
                android.util.Slog.e(TAG, "Failed to compute password hash; password history won't be updated");
                return;
            }
            if (!android.text.TextUtils.isEmpty(string)) {
                java.lang.String[] split = string.split(",");
                java.util.StringJoiner stringJoiner = new java.util.StringJoiner(",");
                stringJoiner.add(str);
                for (int i2 = 0; i2 < requestedPasswordHistoryLength - 1 && i2 < split.length; i2++) {
                    stringJoiner.add(split[i2]);
                }
                str = stringJoiner.toString();
            }
        }
        setString("lockscreen.passwordhistory", str, i);
    }

    private java.lang.String getSalt(int i) {
        long j = getLong("lockscreen.password_salt", 0L, i);
        if (j == 0) {
            j = com.android.server.locksettings.SecureRandomUtils.randomLong();
            setLong("lockscreen.password_salt", j, i);
        }
        return java.lang.Long.toHexString(j);
    }

    private int getRequestedPasswordHistoryLength(int i) {
        return this.mInjector.getDevicePolicyManager().getPasswordHistoryLength(null, i);
    }

    private android.os.UserManager getUserManagerFromCache(int i) {
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

    @com.android.internal.annotations.VisibleForTesting
    protected boolean isCredentialSharableWithParent(int i) {
        return getUserManagerFromCache(i).isCredentialSharableWithParent();
    }

    public boolean registerWeakEscrowTokenRemovedListener(@android.annotation.NonNull com.android.internal.widget.IWeakEscrowTokenRemovedListener iWeakEscrowTokenRemovedListener) {
        checkManageWeakEscrowTokenMethodUsage();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mSpManager.registerWeakEscrowTokenRemovedListener(iWeakEscrowTokenRemovedListener);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean unregisterWeakEscrowTokenRemovedListener(@android.annotation.NonNull com.android.internal.widget.IWeakEscrowTokenRemovedListener iWeakEscrowTokenRemovedListener) {
        checkManageWeakEscrowTokenMethodUsage();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mSpManager.unregisterWeakEscrowTokenRemovedListener(iWeakEscrowTokenRemovedListener);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public long addWeakEscrowToken(byte[] bArr, int i, @android.annotation.NonNull final com.android.internal.widget.IWeakEscrowTokenActivatedListener iWeakEscrowTokenActivatedListener) {
        checkManageWeakEscrowTokenMethodUsage();
        java.util.Objects.requireNonNull(iWeakEscrowTokenActivatedListener, "Listener can not be null.");
        com.android.internal.widget.LockPatternUtils.EscrowTokenStateChangeCallback escrowTokenStateChangeCallback = new com.android.internal.widget.LockPatternUtils.EscrowTokenStateChangeCallback() { // from class: com.android.server.locksettings.LockSettingsService$$ExternalSyntheticLambda1
            public final void onEscrowTokenActivated(long j, int i2) {
                com.android.server.locksettings.LockSettingsService.lambda$addWeakEscrowToken$4(iWeakEscrowTokenActivatedListener, j, i2);
            }
        };
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return addEscrowToken(bArr, 1, i, escrowTokenStateChangeCallback);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$addWeakEscrowToken$4(com.android.internal.widget.IWeakEscrowTokenActivatedListener iWeakEscrowTokenActivatedListener, long j, int i) {
        try {
            iWeakEscrowTokenActivatedListener.onWeakEscrowTokenActivated(j, i);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Exception while notifying weak escrow token has been activated", e);
        }
    }

    public boolean removeWeakEscrowToken(long j, int i) {
        checkManageWeakEscrowTokenMethodUsage();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return removeEscrowToken(j, i);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isWeakEscrowTokenActive(long j, int i) {
        checkManageWeakEscrowTokenMethodUsage();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return isEscrowTokenActive(j, i);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isWeakEscrowTokenValid(long j, byte[] bArr, int i) {
        checkManageWeakEscrowTokenMethodUsage();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mSpManager) {
                if (!this.mSpManager.hasEscrowData(i)) {
                    android.util.Slog.w(TAG, "Escrow token is disabled on the current user");
                    return false;
                }
                if (this.mSpManager.unlockWeakTokenBasedProtector(getGateKeeperService(), j, bArr, i).syntheticPassword == null) {
                    android.util.Slog.w(TAG, "Invalid escrow token supplied");
                    return false;
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void tieProfileLockToParent(int i, int i2, com.android.internal.widget.LockscreenCredential lockscreenCredential) {
        com.android.server.utils.Slogf.i(TAG, "Tying lock for profile user %d to parent user %d", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
        try {
            long secureUserId = getGateKeeperService().getSecureUserId(i2);
            try {
                javax.crypto.KeyGenerator keyGenerator = javax.crypto.KeyGenerator.getInstance("AES");
                keyGenerator.init(new java.security.SecureRandom());
                javax.crypto.SecretKey generateKey = keyGenerator.generateKey();
                try {
                    this.mKeyStore.setEntry(PROFILE_KEY_NAME_ENCRYPT + i, new java.security.KeyStore.SecretKeyEntry(generateKey), new android.security.keystore.KeyProtection.Builder(1).setBlockModes("GCM").setEncryptionPaddings("NoPadding").build());
                    this.mKeyStore.setEntry(PROFILE_KEY_NAME_DECRYPT + i, new java.security.KeyStore.SecretKeyEntry(generateKey), new android.security.keystore.KeyProtection.Builder(2).setBlockModes("GCM").setEncryptionPaddings("NoPadding").setUserAuthenticationRequired(true).setBoundToSpecificSecureUserId(secureUserId).setUserAuthenticationValidityDurationSeconds(30).build());
                    javax.crypto.SecretKey secretKey = (javax.crypto.SecretKey) this.mKeyStore.getKey(PROFILE_KEY_NAME_ENCRYPT + i, null);
                    javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES/GCM/NoPadding");
                    cipher.init(1, secretKey);
                    byte[] doFinal = cipher.doFinal(lockscreenCredential.getCredential());
                    byte[] iv = cipher.getIV();
                    if (iv.length != 12) {
                        throw new java.lang.IllegalArgumentException("Invalid iv length: " + iv.length);
                    }
                    this.mStorage.writeChildProfileLock(i, com.android.internal.util.ArrayUtils.concat(new byte[][]{iv, doFinal}));
                } finally {
                    this.mKeyStore.deleteEntry(PROFILE_KEY_NAME_ENCRYPT + i);
                }
            } catch (java.security.InvalidKeyException | java.security.KeyStoreException | java.security.NoSuchAlgorithmException | java.security.UnrecoverableKeyException | javax.crypto.BadPaddingException | javax.crypto.IllegalBlockSizeException | javax.crypto.NoSuchPaddingException e) {
                throw new java.lang.IllegalStateException("Failed to encrypt key", e);
            }
        } catch (android.os.RemoteException e2) {
            throw new java.lang.IllegalStateException("Failed to talk to GateKeeper service", e2);
        }
    }

    private void setCeStorageProtection(int i, com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword syntheticPassword) {
        byte[] deriveFileBasedEncryptionKey = syntheticPassword.deriveFileBasedEncryptionKey();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mStorageManager.setCeStorageProtection(i, deriveFileBasedEncryptionKey);
            } catch (android.os.RemoteException e) {
                throw new java.lang.IllegalStateException("Failed to protect CE key for user " + i, e);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private boolean isCeStorageUnlocked(int i) {
        try {
            return this.mStorageManager.isCeStorageUnlocked(i);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Error checking whether CE storage is unlocked", e);
            return false;
        }
    }

    private void unlockCeStorage(int i, com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword syntheticPassword) {
        if (isCeStorageUnlocked(i)) {
            com.android.server.utils.Slogf.d(TAG, "CE storage for user %d is already unlocked", java.lang.Integer.valueOf(i));
            return;
        }
        java.lang.String str = isUserSecure(i) ? "secured" : "unsecured";
        byte[] deriveFileBasedEncryptionKey = syntheticPassword.deriveFileBasedEncryptionKey();
        try {
            try {
                this.mStorageManager.unlockCeStorage(i, deriveFileBasedEncryptionKey);
                com.android.server.utils.Slogf.i(TAG, "Unlocked CE storage for %s user %d", str, java.lang.Integer.valueOf(i));
            } catch (android.os.RemoteException e) {
                com.android.server.utils.Slogf.wtf(TAG, e, "Failed to unlock CE storage for %s user %d", str, java.lang.Integer.valueOf(i));
            }
        } finally {
            java.util.Arrays.fill(deriveFileBasedEncryptionKey, (byte) 0);
        }
    }

    public void unlockUserKeyIfUnsecured(int i) {
        checkPasswordReadPermission();
        synchronized (this.mSpManager) {
            try {
                if (isCeStorageUnlocked(i)) {
                    com.android.server.utils.Slogf.d(TAG, "CE storage for user %d is already unlocked", java.lang.Integer.valueOf(i));
                    return;
                }
                if (isUserSecure(i)) {
                    com.android.server.utils.Slogf.d(TAG, "Not unlocking CE storage for user %d yet because user is secured", java.lang.Integer.valueOf(i));
                    return;
                }
                com.android.server.utils.Slogf.i(TAG, "Unwrapping synthetic password for unsecured user %d", java.lang.Integer.valueOf(i));
                com.android.server.locksettings.SyntheticPasswordManager.AuthenticationResult unlockLskfBasedProtector = this.mSpManager.unlockLskfBasedProtector(getGateKeeperService(), getCurrentLskfBasedProtectorId(i), com.android.internal.widget.LockscreenCredential.createNone(), i, null);
                if (unlockLskfBasedProtector.syntheticPassword == null) {
                    com.android.server.utils.Slogf.wtf(TAG, "Failed to unwrap synthetic password for unsecured user %d", java.lang.Integer.valueOf(i));
                    return;
                }
                onSyntheticPasswordUnlocked(i, unlockLskfBasedProtector.syntheticPassword);
                if (FIX_UNLOCKED_DEVICE_REQUIRED_KEYS) {
                    unlockKeystore(i, unlockLskfBasedProtector.syntheticPassword);
                }
                unlockCeStorage(i, unlockLskfBasedProtector.syntheticPassword);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void resetKeyStore(int i) {
        checkWritePermission();
        com.android.server.utils.Slogf.d(TAG, "Resetting keystore for user %d", java.lang.Integer.valueOf(i));
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        for (android.content.pm.UserInfo userInfo : this.mUserManager.getProfiles(i)) {
            if (isCredentialSharableWithParent(userInfo.id) && !getSeparateProfileChallengeEnabledInternal(userInfo.id) && this.mStorage.hasChildProfileLock(userInfo.id)) {
                try {
                    arrayList2.add(getDecryptedPasswordForTiedProfile(userInfo.id));
                    arrayList.add(java.lang.Integer.valueOf(userInfo.id));
                } catch (java.io.IOException | java.security.InvalidAlgorithmParameterException | java.security.InvalidKeyException | java.security.KeyStoreException | java.security.NoSuchAlgorithmException | java.security.UnrecoverableKeyException | java.security.cert.CertificateException | javax.crypto.BadPaddingException | javax.crypto.IllegalBlockSizeException | javax.crypto.NoSuchPaddingException e) {
                    android.util.Slog.e(TAG, "Failed to decrypt child profile key", e);
                }
            }
        }
        int i2 = 0;
        try {
            for (int i3 : this.mUserManager.getProfileIdsWithDisabled(i)) {
                int length = SYSTEM_CREDENTIAL_UIDS.length;
                for (int i4 = 0; i4 < length; i4++) {
                    android.security.AndroidKeyStoreMaintenance.clearNamespace(0, android.os.UserHandle.getUid(i3, r8[i4]));
                }
            }
            if (this.mUserManager.getUserInfo(i).isPrimary()) {
                android.security.AndroidKeyStoreMaintenance.clearNamespace(2, 102L);
            }
            while (i2 < arrayList.size()) {
                int intValue = ((java.lang.Integer) arrayList.get(i2)).intValue();
                com.android.internal.widget.LockscreenCredential lockscreenCredential = (com.android.internal.widget.LockscreenCredential) arrayList2.get(i2);
                if (intValue != -1 && lockscreenCredential != null) {
                    tieProfileLockToParent(intValue, i, lockscreenCredential);
                }
                if (lockscreenCredential != null) {
                    lockscreenCredential.zeroize();
                }
                i2++;
            }
        } catch (java.lang.Throwable th) {
            while (i2 < arrayList.size()) {
                int intValue2 = ((java.lang.Integer) arrayList.get(i2)).intValue();
                com.android.internal.widget.LockscreenCredential lockscreenCredential2 = (com.android.internal.widget.LockscreenCredential) arrayList2.get(i2);
                if (intValue2 != -1 && lockscreenCredential2 != null) {
                    tieProfileLockToParent(intValue2, i, lockscreenCredential2);
                }
                if (lockscreenCredential2 != null) {
                    lockscreenCredential2.zeroize();
                }
                i2++;
            }
            throw th;
        }
    }

    public com.android.internal.widget.VerifyCredentialResponse checkCredential(com.android.internal.widget.LockscreenCredential lockscreenCredential, int i, com.android.internal.widget.ICheckCredentialProgressCallback iCheckCredentialProgressCallback) {
        checkPasswordReadPermission();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return doVerifyCredential(lockscreenCredential, i, iCheckCredentialProgressCallback, 0);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            scheduleGc();
        }
    }

    @android.annotation.Nullable
    public com.android.internal.widget.VerifyCredentialResponse verifyCredential(com.android.internal.widget.LockscreenCredential lockscreenCredential, int i, int i2) {
        if (!hasPermission(PERMISSION) && !hasPermission("android.permission.SET_AND_VERIFY_LOCKSCREEN_CREDENTIALS")) {
            throw new java.lang.SecurityException("verifyCredential requires SET_AND_VERIFY_LOCKSCREEN_CREDENTIALS or android.permission.ACCESS_KEYGUARD_SECURE_STORAGE");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return doVerifyCredential(lockscreenCredential, i, null, i2);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            scheduleGc();
        }
    }

    public com.android.internal.widget.VerifyCredentialResponse verifyGatekeeperPasswordHandle(long j, long j2, int i) {
        byte[] bArr;
        com.android.internal.widget.VerifyCredentialResponse verifyChallengeInternal;
        checkPasswordReadPermission();
        synchronized (this.mGatekeeperPasswords) {
            bArr = this.mGatekeeperPasswords.get(j);
        }
        synchronized (this.mSpManager) {
            try {
                if (bArr == null) {
                    android.util.Slog.d(TAG, "No gatekeeper password for handle");
                    verifyChallengeInternal = com.android.internal.widget.VerifyCredentialResponse.ERROR;
                } else {
                    verifyChallengeInternal = this.mSpManager.verifyChallengeInternal(getGateKeeperService(), bArr, j2, i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return verifyChallengeInternal;
    }

    public void removeGatekeeperPasswordHandle(long j) {
        checkPasswordReadPermission();
        synchronized (this.mGatekeeperPasswords) {
            this.mGatekeeperPasswords.remove(j);
        }
    }

    private com.android.internal.widget.VerifyCredentialResponse doVerifyCredential(com.android.internal.widget.LockscreenCredential lockscreenCredential, int i, com.android.internal.widget.ICheckCredentialProgressCallback iCheckCredentialProgressCallback, int i2) {
        if (lockscreenCredential == null || lockscreenCredential.isNone()) {
            throw new java.lang.IllegalArgumentException("Credential can't be null or empty");
        }
        if (i == -9999 && android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "device_provisioned", 0) != 0) {
            android.util.Slog.e(TAG, "FRP credential can only be verified prior to provisioning.");
            return com.android.internal.widget.VerifyCredentialResponse.ERROR;
        }
        if (i == -9998 && !com.android.internal.widget.LockPatternUtils.isRepairModeActive(this.mContext)) {
            android.util.Slog.e(TAG, "Repair mode is not active on the device.");
            return com.android.internal.widget.VerifyCredentialResponse.ERROR;
        }
        com.android.server.utils.Slogf.i(TAG, "Verifying lockscreen credential for user %d", java.lang.Integer.valueOf(i));
        synchronized (this.mSpManager) {
            try {
                if (com.android.internal.widget.LockPatternUtils.isSpecialUserId(i)) {
                    com.android.internal.widget.VerifyCredentialResponse verifySpecialUserCredential = this.mSpManager.verifySpecialUserCredential(i, getGateKeeperService(), lockscreenCredential, iCheckCredentialProgressCallback);
                    if (android.security.Flags.frpEnforcement() && verifySpecialUserCredential.isMatched() && i == -9999) {
                        this.mStorage.deactivateFactoryResetProtectionWithoutSecret();
                    }
                    return verifySpecialUserCredential;
                }
                long currentLskfBasedProtectorId = getCurrentLskfBasedProtectorId(i);
                com.android.server.locksettings.SyntheticPasswordManager.AuthenticationResult unlockLskfBasedProtector = this.mSpManager.unlockLskfBasedProtector(getGateKeeperService(), currentLskfBasedProtectorId, lockscreenCredential, i, iCheckCredentialProgressCallback);
                com.android.internal.widget.VerifyCredentialResponse verifyCredentialResponse = unlockLskfBasedProtector.gkResponse;
                if (verifyCredentialResponse.getResponseCode() == 0) {
                    if ((i2 & 2) != 0 && !this.mSpManager.writeRepairModeCredentialLocked(currentLskfBasedProtectorId, i)) {
                        android.util.Slog.e(TAG, "Failed to write repair mode credential");
                        return com.android.internal.widget.VerifyCredentialResponse.ERROR;
                    }
                    this.mBiometricDeferredQueue.addPendingLockoutResetForUser(i, unlockLskfBasedProtector.syntheticPassword.deriveGkPassword());
                }
                if (verifyCredentialResponse.getResponseCode() == 0) {
                    com.android.server.utils.Slogf.i(TAG, "Successfully verified lockscreen credential for user %d", java.lang.Integer.valueOf(i));
                    onCredentialVerified(unlockLskfBasedProtector.syntheticPassword, android.app.admin.PasswordMetrics.computeForCredential(lockscreenCredential), i);
                    if ((i2 & 1) != 0) {
                        verifyCredentialResponse = new com.android.internal.widget.VerifyCredentialResponse.Builder().setGatekeeperPasswordHandle(storeGatekeeperPasswordTemporarily(unlockLskfBasedProtector.syntheticPassword.deriveGkPassword())).build();
                    }
                    sendCredentialsOnUnlockIfRequired(lockscreenCredential, i);
                } else if (verifyCredentialResponse.getResponseCode() == 1 && verifyCredentialResponse.getTimeout() > 0) {
                    requireStrongAuth(8, i);
                }
                if (android.security.Flags.reportPrimaryAuthAttempts()) {
                    notifyLockSettingsStateListeners(verifyCredentialResponse.getResponseCode() == 0, i);
                }
                return verifyCredentialResponse;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void notifyLockSettingsStateListeners(boolean z, int i) {
        java.util.Iterator<com.android.internal.widget.LockSettingsStateListener> it = this.mLockSettingsStateListeners.iterator();
        while (it.hasNext()) {
            com.android.internal.widget.LockSettingsStateListener next = it.next();
            if (z) {
                next.onAuthenticationSucceeded(i);
            } else {
                next.onAuthenticationFailed(i);
            }
        }
    }

    public com.android.internal.widget.VerifyCredentialResponse verifyTiedProfileChallenge(com.android.internal.widget.LockscreenCredential lockscreenCredential, int i, int i2) {
        checkPasswordReadPermission();
        com.android.server.utils.Slogf.i(TAG, "Verifying tied profile challenge for user %d", java.lang.Integer.valueOf(i));
        if (!isProfileWithUnifiedLock(i)) {
            throw new java.lang.IllegalArgumentException("User id must be managed/clone profile with unified lock");
        }
        com.android.internal.widget.VerifyCredentialResponse doVerifyCredential = doVerifyCredential(lockscreenCredential, this.mUserManager.getProfileParent(i).id, null, i2);
        if (doVerifyCredential.getResponseCode() == 0) {
            try {
                try {
                    return doVerifyCredential(getDecryptedPasswordForTiedProfile(i), i, null, i2);
                } catch (java.io.IOException | java.security.InvalidAlgorithmParameterException | java.security.InvalidKeyException | java.security.KeyStoreException | java.security.NoSuchAlgorithmException | java.security.UnrecoverableKeyException | java.security.cert.CertificateException | javax.crypto.BadPaddingException | javax.crypto.IllegalBlockSizeException | javax.crypto.NoSuchPaddingException e) {
                    android.util.Slog.e(TAG, "Failed to decrypt child profile key", e);
                    throw new java.lang.IllegalStateException("Unable to get tied profile token");
                }
            } finally {
                scheduleGc();
            }
        }
        return doVerifyCredential;
    }

    private void setUserPasswordMetrics(com.android.internal.widget.LockscreenCredential lockscreenCredential, int i) {
        synchronized (this) {
            this.mUserPasswordMetrics.put(i, android.app.admin.PasswordMetrics.computeForCredential(lockscreenCredential));
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    android.app.admin.PasswordMetrics getUserPasswordMetrics(int i) {
        android.app.admin.PasswordMetrics passwordMetrics;
        if (!isUserSecure(i)) {
            return new android.app.admin.PasswordMetrics(-1);
        }
        synchronized (this) {
            passwordMetrics = this.mUserPasswordMetrics.get(i);
        }
        return passwordMetrics;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.Nullable
    public android.app.admin.PasswordMetrics loadPasswordMetrics(com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword syntheticPassword, int i) {
        synchronized (this.mSpManager) {
            try {
                if (!isUserSecure(i)) {
                    return null;
                }
                return this.mSpManager.getPasswordMetrics(syntheticPassword, getCurrentLskfBasedProtectorId(i), i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void notifyPasswordChanged(final com.android.internal.widget.LockscreenCredential lockscreenCredential, final int i) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.locksettings.LockSettingsService$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.locksettings.LockSettingsService.this.lambda$notifyPasswordChanged$5(lockscreenCredential, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyPasswordChanged$5(com.android.internal.widget.LockscreenCredential lockscreenCredential, int i) {
        this.mInjector.getDevicePolicyManager().reportPasswordChanged(android.app.admin.PasswordMetrics.computeForCredential(lockscreenCredential), i);
        ((com.android.server.wm.WindowManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.WindowManagerInternal.class)).reportPasswordChanged(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createNewUser(int i, int i2) {
        if (FIX_UNLOCKED_DEVICE_REQUIRED_KEYS) {
            android.security.AndroidKeyStoreMaintenance.onUserAdded(i);
        }
        synchronized (this.mUserCreationAndRemovalLock) {
            try {
                if (!this.mThirdPartyAppsStarted) {
                    com.android.server.utils.Slogf.i(TAG, "Delaying locksettings state creation for user %d until third-party apps are started", java.lang.Integer.valueOf(i));
                    this.mEarlyCreatedUsers.put(i, i2);
                    this.mEarlyRemovedUsers.delete(i);
                } else {
                    removeStateForReusedUserIdIfNecessary(i, i2);
                    initializeSyntheticPassword(i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeUser(int i) {
        synchronized (this.mUserCreationAndRemovalLock) {
            try {
                if (!this.mThirdPartyAppsStarted) {
                    com.android.server.utils.Slogf.i(TAG, "Delaying locksettings state removal for user %d until third-party apps are started", java.lang.Integer.valueOf(i));
                    if (this.mEarlyCreatedUsers.indexOfKey(i) >= 0) {
                        this.mEarlyCreatedUsers.delete(i);
                    } else {
                        this.mEarlyRemovedUsers.put(i, -1);
                    }
                    return;
                }
                com.android.server.utils.Slogf.i(TAG, "Removing state for user %d", java.lang.Integer.valueOf(i));
                removeUserState(i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void removeUserState(int i) {
        removeBiometricsForUser(i);
        this.mSpManager.removeUser(getGateKeeperService(), i);
        this.mStrongAuth.removeUser(i);
        android.security.AndroidKeyStoreMaintenance.onUserRemoved(i);
        this.mUnifiedProfilePasswordCache.removePassword(i);
        gateKeeperClearSecureUserId(i);
        removeKeystoreProfileKey(i);
        this.mStorage.removeUser(i);
    }

    private void removeKeystoreProfileKey(int i) {
        java.lang.String str = PROFILE_KEY_NAME_ENCRYPT + i;
        java.lang.String str2 = PROFILE_KEY_NAME_DECRYPT + i;
        try {
            if (this.mKeyStore.containsAlias(str) || this.mKeyStore.containsAlias(str2)) {
                com.android.server.utils.Slogf.i(TAG, "Removing keystore profile key for user %d", java.lang.Integer.valueOf(i));
                this.mKeyStore.deleteEntry(str);
                this.mKeyStore.deleteEntry(str2);
            }
        } catch (java.security.KeyStoreException e) {
            com.android.server.utils.Slogf.e(TAG, e, "Error removing keystore profile key for user %d", java.lang.Integer.valueOf(i));
        }
    }

    public void registerStrongAuthTracker(android.app.trust.IStrongAuthTracker iStrongAuthTracker) {
        checkPasswordReadPermission();
        this.mStrongAuth.registerStrongAuthTracker(iStrongAuthTracker);
    }

    public void unregisterStrongAuthTracker(android.app.trust.IStrongAuthTracker iStrongAuthTracker) {
        checkPasswordReadPermission();
        this.mStrongAuth.unregisterStrongAuthTracker(iStrongAuthTracker);
    }

    public void requireStrongAuth(int i, int i2) {
        checkWritePermission();
        this.mStrongAuth.requireStrongAuth(i, i2);
    }

    public void reportSuccessfulBiometricUnlock(boolean z, int i) {
        checkBiometricPermission();
        this.mStrongAuth.reportSuccessfulBiometricUnlock(z, i);
    }

    public void scheduleNonStrongBiometricIdleTimeout(int i) {
        checkBiometricPermission();
        this.mStrongAuth.scheduleNonStrongBiometricIdleTimeout(i);
    }

    public void userPresent(int i) {
        checkWritePermission();
        this.mStrongAuth.reportUnlock(i);
    }

    public int getStrongAuthForUser(int i) {
        checkPasswordReadPermission();
        return this.mStrongAuthTracker.getStrongAuthForUser(i);
    }

    private boolean isCallerShell() {
        int callingUid = android.os.Binder.getCallingUid();
        return callingUid == 2000 || callingUid == 0;
    }

    private void enforceShell() {
        if (!isCallerShell()) {
            throw new java.lang.SecurityException("Caller must be shell");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
        enforceShell();
        int callingPid = android.os.Binder.getCallingPid();
        int callingUid = android.os.Binder.getCallingUid();
        com.android.server.utils.Slogf.i(TAG, "Executing shell command '%s'; callingPid=%d, callingUid=%d", com.android.internal.util.ArrayUtils.isEmpty(strArr) ? "" : strArr[0], java.lang.Integer.valueOf(callingPid), java.lang.Integer.valueOf(callingUid));
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            new com.android.server.locksettings.LockSettingsShellCommand(new com.android.internal.widget.LockPatternUtils(this.mContext), this.mContext, callingPid, callingUid).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void initRecoveryServiceWithSigFile(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull byte[] bArr, @android.annotation.NonNull byte[] bArr2) throws android.os.RemoteException {
        this.mRecoverableKeyStoreManager.initRecoveryServiceWithSigFile(str, bArr, bArr2);
    }

    @android.annotation.NonNull
    public android.security.keystore.recovery.KeyChainSnapshot getKeyChainSnapshot() throws android.os.RemoteException {
        return this.mRecoverableKeyStoreManager.getKeyChainSnapshot();
    }

    public void setSnapshotCreatedPendingIntent(@android.annotation.Nullable android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
        this.mRecoverableKeyStoreManager.setSnapshotCreatedPendingIntent(pendingIntent);
    }

    public void setServerParams(byte[] bArr) throws android.os.RemoteException {
        this.mRecoverableKeyStoreManager.setServerParams(bArr);
    }

    public void setRecoveryStatus(java.lang.String str, int i) throws android.os.RemoteException {
        this.mRecoverableKeyStoreManager.setRecoveryStatus(str, i);
    }

    @android.annotation.NonNull
    public java.util.Map getRecoveryStatus() throws android.os.RemoteException {
        return this.mRecoverableKeyStoreManager.getRecoveryStatus();
    }

    public void setRecoverySecretTypes(@android.annotation.NonNull int[] iArr) throws android.os.RemoteException {
        this.mRecoverableKeyStoreManager.setRecoverySecretTypes(iArr);
    }

    @android.annotation.NonNull
    public int[] getRecoverySecretTypes() throws android.os.RemoteException {
        return this.mRecoverableKeyStoreManager.getRecoverySecretTypes();
    }

    @android.annotation.NonNull
    public byte[] startRecoverySessionWithCertPath(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull android.security.keystore.recovery.RecoveryCertPath recoveryCertPath, @android.annotation.NonNull byte[] bArr, @android.annotation.NonNull byte[] bArr2, @android.annotation.NonNull java.util.List<android.security.keystore.recovery.KeyChainProtectionParams> list) throws android.os.RemoteException {
        return this.mRecoverableKeyStoreManager.startRecoverySessionWithCertPath(str, str2, recoveryCertPath, bArr, bArr2, list);
    }

    public java.util.Map<java.lang.String, java.lang.String> recoverKeyChainSnapshot(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull byte[] bArr, @android.annotation.NonNull java.util.List<android.security.keystore.recovery.WrappedApplicationKey> list) throws android.os.RemoteException {
        return this.mRecoverableKeyStoreManager.recoverKeyChainSnapshot(str, bArr, list);
    }

    public void closeSession(@android.annotation.NonNull java.lang.String str) throws android.os.RemoteException {
        this.mRecoverableKeyStoreManager.closeSession(str);
    }

    public void removeKey(@android.annotation.NonNull java.lang.String str) throws android.os.RemoteException {
        this.mRecoverableKeyStoreManager.removeKey(str);
    }

    @android.annotation.Nullable
    public java.lang.String generateKey(@android.annotation.NonNull java.lang.String str) throws android.os.RemoteException {
        return this.mRecoverableKeyStoreManager.generateKey(str);
    }

    @android.annotation.Nullable
    public java.lang.String generateKeyWithMetadata(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable byte[] bArr) throws android.os.RemoteException {
        return this.mRecoverableKeyStoreManager.generateKeyWithMetadata(str, bArr);
    }

    @android.annotation.Nullable
    public java.lang.String importKey(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull byte[] bArr) throws android.os.RemoteException {
        return this.mRecoverableKeyStoreManager.importKey(str, bArr);
    }

    @android.annotation.Nullable
    public java.lang.String importKeyWithMetadata(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull byte[] bArr, @android.annotation.Nullable byte[] bArr2) throws android.os.RemoteException {
        return this.mRecoverableKeyStoreManager.importKeyWithMetadata(str, bArr, bArr2);
    }

    @android.annotation.Nullable
    public java.lang.String getKey(@android.annotation.NonNull java.lang.String str) throws android.os.RemoteException {
        return this.mRecoverableKeyStoreManager.getKey(str);
    }

    @android.annotation.NonNull
    public android.app.RemoteLockscreenValidationSession startRemoteLockscreenValidation() {
        return this.mRecoverableKeyStoreManager.startRemoteLockscreenValidation(this);
    }

    @android.annotation.NonNull
    public android.app.RemoteLockscreenValidationResult validateRemoteLockscreen(@android.annotation.NonNull byte[] bArr) {
        return this.mRecoverableKeyStoreManager.validateRemoteLockscreen(bArr, this);
    }

    private class GateKeeperDiedRecipient implements android.os.IBinder.DeathRecipient {
        private GateKeeperDiedRecipient() {
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            com.android.server.locksettings.LockSettingsService.this.mGateKeeperService.asBinder().unlinkToDeath(this, 0);
            com.android.server.locksettings.LockSettingsService.this.mGateKeeperService = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized android.service.gatekeeper.IGateKeeperService getGateKeeperService() {
        if (this.mGateKeeperService != null) {
            return this.mGateKeeperService;
        }
        android.os.IBinder waitForService = android.os.ServiceManager.waitForService("android.service.gatekeeper.IGateKeeperService");
        if (waitForService != null) {
            try {
                waitForService.linkToDeath(new com.android.server.locksettings.LockSettingsService.GateKeeperDiedRecipient(), 0);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, " Unable to register death recipient", e);
            }
            this.mGateKeeperService = android.service.gatekeeper.IGateKeeperService.Stub.asInterface(waitForService);
            return this.mGateKeeperService;
        }
        android.util.Slog.e(TAG, "Unable to acquire GateKeeperService");
        return null;
    }

    private void gateKeeperClearSecureUserId(int i) {
        try {
            getGateKeeperService().clearSecureUserId(i);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Failed to clear SID", e);
        }
    }

    private void onSyntheticPasswordCreated(int i, com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword syntheticPassword) {
        onSyntheticPasswordKnown(i, syntheticPassword, true);
    }

    private void onSyntheticPasswordUnlocked(int i, com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword syntheticPassword) {
        onSyntheticPasswordKnown(i, syntheticPassword, false);
    }

    private void onSyntheticPasswordKnown(int i, com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword syntheticPassword, boolean z) {
        if (this.mInjector.isGsiRunning()) {
            android.util.Slog.w(TAG, "Running in GSI; skipping calls to AuthSecret and RebootEscrow");
        } else {
            this.mRebootEscrowManager.callToRebootEscrowIfNeeded(i, syntheticPassword.getVersion(), syntheticPassword.getSyntheticPassword());
            callToAuthSecretIfNeeded(i, syntheticPassword, z);
        }
    }

    private void callToAuthSecretIfNeeded(int i, com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword syntheticPassword, boolean z) {
        android.content.pm.UserInfo userInfo;
        byte[] readVendorAuthSecret;
        byte[] bArr;
        byte[] bArr2;
        if (this.mAuthSecretService == null || (userInfo = this.mInjector.getUserManagerInternal().getUserInfo(i)) == null) {
            return;
        }
        if (!this.mInjector.isHeadlessSystemUserMode()) {
            if (i != 0) {
                return;
            } else {
                readVendorAuthSecret = syntheticPassword.deriveVendorAuthSecret();
            }
        } else {
            if (!this.mInjector.isMainUserPermanentAdmin() || !userInfo.isFull()) {
                return;
            }
            if (z) {
                if (userInfo.isMain()) {
                    android.util.Slog.i(TAG, "Generating new vendor auth secret and storing for user: " + i);
                    bArr2 = com.android.server.locksettings.SecureRandomUtils.randomBytes(32);
                    synchronized (this.mHeadlessAuthSecretLock) {
                        this.mAuthSecret = bArr2;
                    }
                } else {
                    synchronized (this.mHeadlessAuthSecretLock) {
                        bArr = this.mAuthSecret;
                    }
                    if (bArr != null) {
                        bArr2 = bArr;
                    } else {
                        android.util.Slog.e(TAG, "Creating non-main user " + i + " but vendor auth secret is not in memory");
                        return;
                    }
                }
                this.mSpManager.writeVendorAuthSecret(bArr2, syntheticPassword, i);
                readVendorAuthSecret = bArr2;
            } else {
                readVendorAuthSecret = this.mSpManager.readVendorAuthSecret(syntheticPassword, i);
                if (readVendorAuthSecret == null) {
                    android.util.Slog.e(TAG, "Unable to read vendor auth secret for user: " + i);
                    return;
                }
                synchronized (this.mHeadlessAuthSecretLock) {
                    this.mAuthSecret = readVendorAuthSecret;
                }
            }
        }
        android.util.Slog.i(TAG, "Sending vendor auth secret to IAuthSecret HAL as user: " + i);
        try {
            this.mAuthSecretService.setPrimaryUserCredential(readVendorAuthSecret);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Failed to send vendor auth secret to IAuthSecret HAL", e);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword initializeSyntheticPassword(int i) {
        com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword newSyntheticPassword;
        synchronized (this.mSpManager) {
            try {
                com.android.server.utils.Slogf.i(TAG, "Initializing synthetic password for user %d", java.lang.Integer.valueOf(i));
                com.android.internal.util.Preconditions.checkState(getCurrentLskfBasedProtectorId(i) == 0, "Cannot reinitialize SP");
                newSyntheticPassword = this.mSpManager.newSyntheticPassword(i);
                setCurrentLskfBasedProtectorId(this.mSpManager.createLskfBasedProtector(getGateKeeperService(), com.android.internal.widget.LockscreenCredential.createNone(), newSyntheticPassword, i), i);
                setCeStorageProtection(i, newSyntheticPassword);
                if (FIX_UNLOCKED_DEVICE_REQUIRED_KEYS) {
                    initKeystoreSuperKeys(i, newSyntheticPassword, false);
                }
                onSyntheticPasswordCreated(i, newSyntheticPassword);
                com.android.server.utils.Slogf.i(TAG, "Successfully initialized synthetic password for user %d", java.lang.Integer.valueOf(i));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return newSyntheticPassword;
    }

    @com.android.internal.annotations.VisibleForTesting
    long getCurrentLskfBasedProtectorId(int i) {
        return getLong("sp-handle", 0L, i);
    }

    private void setCurrentLskfBasedProtectorId(long j, int i) {
        long currentLskfBasedProtectorId = getCurrentLskfBasedProtectorId(i);
        setLong("sp-handle", j, i);
        setLong(PREV_LSKF_BASED_PROTECTOR_ID_KEY, currentLskfBasedProtectorId, i);
        setLong(LSKF_LAST_CHANGED_TIME_KEY, java.lang.System.currentTimeMillis(), i);
    }

    private long storeGatekeeperPasswordTemporarily(byte[] bArr) {
        final long j;
        synchronized (this.mGatekeeperPasswords) {
            j = 0;
            while (true) {
                if (j != 0) {
                    if (this.mGatekeeperPasswords.get(j) == null) {
                        this.mGatekeeperPasswords.put(j, bArr);
                    }
                }
                j = com.android.server.locksettings.SecureRandomUtils.randomLong();
            }
        }
        this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.locksettings.LockSettingsService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.locksettings.LockSettingsService.this.lambda$storeGatekeeperPasswordTemporarily$6(j);
            }
        }, 600000L);
        return j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$storeGatekeeperPasswordTemporarily$6(long j) {
        synchronized (this.mGatekeeperPasswords) {
            try {
                if (this.mGatekeeperPasswords.get(j) != null) {
                    com.android.server.utils.Slogf.d(TAG, "Cached Gatekeeper password with handle %016x has expired", java.lang.Long.valueOf(j));
                    this.mGatekeeperPasswords.remove(j);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onCredentialVerified(com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword syntheticPassword, @android.annotation.Nullable android.app.admin.PasswordMetrics passwordMetrics, int i) {
        if (passwordMetrics != null) {
            synchronized (this) {
                this.mUserPasswordMetrics.put(i, passwordMetrics);
            }
        }
        unlockKeystore(i, syntheticPassword);
        unlockCeStorage(i, syntheticPassword);
        lambda$setLockCredentialWithToken$7(i);
        activateEscrowTokens(syntheticPassword, i);
        if (isCredentialSharableWithParent(i)) {
            if (getSeparateProfileChallengeEnabledInternal(i)) {
                setDeviceUnlockedForUser(i);
            } else {
                this.mStrongAuth.reportUnlock(i);
            }
        }
        this.mStrongAuth.reportSuccessfulStrongAuthUnlock(i);
        onSyntheticPasswordUnlocked(i, syntheticPassword);
    }

    private void setDeviceUnlockedForUser(int i) {
        ((android.app.trust.TrustManager) this.mContext.getSystemService(android.app.trust.TrustManager.class)).setDeviceLockedForUser(i, false);
    }

    @com.android.internal.annotations.GuardedBy({"mSpManager"})
    private long setLockCredentialWithSpLocked(com.android.internal.widget.LockscreenCredential lockscreenCredential, com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword syntheticPassword, int i) {
        java.lang.String str;
        java.util.Map<java.lang.Integer, com.android.internal.widget.LockscreenCredential> decryptedPasswordsForAllTiedProfiles;
        java.util.Map<java.lang.Integer, com.android.internal.widget.LockscreenCredential> map;
        com.android.server.utils.Slogf.i(TAG, "Changing lockscreen credential of user %d; newCredentialType=%s\n", java.lang.Integer.valueOf(i), com.android.internal.widget.LockPatternUtils.credentialTypeToString(lockscreenCredential.getType()));
        int credentialTypeInternal = getCredentialTypeInternal(i);
        long currentLskfBasedProtectorId = getCurrentLskfBasedProtectorId(i);
        long createLskfBasedProtector = this.mSpManager.createLskfBasedProtector(getGateKeeperService(), lockscreenCredential, syntheticPassword, i);
        if (!lockscreenCredential.isNone()) {
            if (this.mSpManager.hasSidForUser(i)) {
                str = TAG;
                map = null;
            } else {
                this.mSpManager.newSidForUser(getGateKeeperService(), syntheticPassword, i);
                com.android.server.locksettings.SyntheticPasswordManager syntheticPasswordManager = this.mSpManager;
                android.service.gatekeeper.IGateKeeperService gateKeeperService = getGateKeeperService();
                str = TAG;
                map = null;
                syntheticPasswordManager.verifyChallenge(gateKeeperService, syntheticPassword, 0L, i);
                if (!FIX_UNLOCKED_DEVICE_REQUIRED_KEYS) {
                    setKeystorePassword(syntheticPassword.deriveKeyStorePassword(), i);
                }
            }
            decryptedPasswordsForAllTiedProfiles = map;
        } else {
            str = TAG;
            decryptedPasswordsForAllTiedProfiles = getDecryptedPasswordsForAllTiedProfiles(i);
            this.mSpManager.clearSidForUser(i);
            gateKeeperClearSecureUserId(i);
            unlockCeStorage(i, syntheticPassword);
            unlockKeystore(i, syntheticPassword);
            if (FIX_UNLOCKED_DEVICE_REQUIRED_KEYS) {
                android.security.AndroidKeyStoreMaintenance.onUserLskfRemoved(i);
            } else {
                setKeystorePassword(null, i);
            }
            removeBiometricsForUser(i);
        }
        setCurrentLskfBasedProtectorId(createLskfBasedProtector, i);
        com.android.internal.widget.LockPatternUtils.invalidateCredentialTypeCache();
        synchronizeUnifiedChallengeForProfiles(i, decryptedPasswordsForAllTiedProfiles);
        setUserPasswordMetrics(lockscreenCredential, i);
        this.mUnifiedProfilePasswordCache.removePassword(i);
        if (credentialTypeInternal != -1) {
            this.mSpManager.destroyAllWeakTokenBasedProtectors(i);
        }
        if (decryptedPasswordsForAllTiedProfiles != null) {
            java.util.Iterator<java.util.Map.Entry<java.lang.Integer, com.android.internal.widget.LockscreenCredential>> it = decryptedPasswordsForAllTiedProfiles.entrySet().iterator();
            while (it.hasNext()) {
                it.next().getValue().zeroize();
            }
        }
        this.mSpManager.destroyLskfBasedProtector(currentLskfBasedProtectorId, i);
        com.android.server.utils.Slogf.i(str, "Successfully changed lockscreen credential of user %d", java.lang.Integer.valueOf(i));
        return createLskfBasedProtector;
    }

    private void sendMainUserCredentialChangedNotificationIfNeeded(int i) {
        if (!android.security.Flags.frpEnforcement() || i != this.mInjector.getUserManagerInternal().getMainUserId()) {
            return;
        }
        sendBroadcast(new android.content.Intent("android.intent.action.MAIN_USER_LOCKSCREEN_KNOWLEDGE_FACTOR_CHANGED"), android.os.UserHandle.of(i), "android.permission.CONFIGURE_FACTORY_RESET_PROTECTION");
    }

    @com.android.internal.annotations.VisibleForTesting
    void sendBroadcast(android.content.Intent intent, android.os.UserHandle userHandle, java.lang.String str) {
        this.mContext.sendBroadcastAsUser(intent, userHandle, str, null);
    }

    private void removeBiometricsForUser(int i) {
        removeAllFingerprintForUser(i);
        removeAllFaceForUser(i);
    }

    private void removeAllFingerprintForUser(int i) {
        android.hardware.fingerprint.FingerprintManager fingerprintManager = this.mInjector.getFingerprintManager();
        if (fingerprintManager != null && fingerprintManager.isHardwareDetected() && fingerprintManager.hasEnrolledFingerprints(i)) {
            java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
            fingerprintManager.removeAll(i, fingerprintManagerRemovalCallback(countDownLatch));
            try {
                countDownLatch.await(com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY, java.util.concurrent.TimeUnit.MILLISECONDS);
            } catch (java.lang.InterruptedException e) {
                android.util.Slog.e(TAG, "Latch interrupted when removing fingerprint", e);
            }
        }
    }

    private void removeAllFaceForUser(int i) {
        android.hardware.face.FaceManager faceManager = this.mInjector.getFaceManager();
        if (faceManager != null && faceManager.isHardwareDetected() && faceManager.hasEnrolledTemplates(i)) {
            java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
            faceManager.removeAll(i, faceManagerRemovalCallback(countDownLatch));
            try {
                countDownLatch.await(com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY, java.util.concurrent.TimeUnit.MILLISECONDS);
            } catch (java.lang.InterruptedException e) {
                android.util.Slog.e(TAG, "Latch interrupted when removing face", e);
            }
        }
    }

    private android.hardware.fingerprint.FingerprintManager.RemovalCallback fingerprintManagerRemovalCallback(final java.util.concurrent.CountDownLatch countDownLatch) {
        return new android.hardware.fingerprint.FingerprintManager.RemovalCallback() { // from class: com.android.server.locksettings.LockSettingsService.4
            public void onRemovalError(@android.annotation.Nullable android.hardware.fingerprint.Fingerprint fingerprint, int i, java.lang.CharSequence charSequence) {
                android.util.Slog.e(com.android.server.locksettings.LockSettingsService.TAG, "Unable to remove fingerprint, error: " + ((java.lang.Object) charSequence));
                countDownLatch.countDown();
            }

            public void onRemovalSucceeded(android.hardware.fingerprint.Fingerprint fingerprint, int i) {
                if (i == 0) {
                    countDownLatch.countDown();
                }
            }
        };
    }

    private android.hardware.face.FaceManager.RemovalCallback faceManagerRemovalCallback(final java.util.concurrent.CountDownLatch countDownLatch) {
        return new android.hardware.face.FaceManager.RemovalCallback() { // from class: com.android.server.locksettings.LockSettingsService.5
            public void onRemovalError(@android.annotation.Nullable android.hardware.face.Face face, int i, java.lang.CharSequence charSequence) {
                android.util.Slog.e(com.android.server.locksettings.LockSettingsService.TAG, "Unable to remove face, error: " + ((java.lang.Object) charSequence));
                countDownLatch.countDown();
            }

            public void onRemovalSucceeded(android.hardware.face.Face face, int i) {
                if (i == 0) {
                    countDownLatch.countDown();
                }
            }
        };
    }

    public byte[] getHashFactor(com.android.internal.widget.LockscreenCredential lockscreenCredential, int i) {
        checkPasswordReadPermission();
        try {
            com.android.server.utils.Slogf.d(TAG, "Getting password history hash factor for user %d", java.lang.Integer.valueOf(i));
            com.android.internal.widget.LockscreenCredential decryptedPasswordForTiedProfile = isProfileWithUnifiedLock(i) ? getDecryptedPasswordForTiedProfile(i) : lockscreenCredential;
            synchronized (this.mSpManager) {
                com.android.server.locksettings.SyntheticPasswordManager.AuthenticationResult unlockLskfBasedProtector = this.mSpManager.unlockLskfBasedProtector(getGateKeeperService(), getCurrentLskfBasedProtectorId(i), decryptedPasswordForTiedProfile, i, null);
                if (unlockLskfBasedProtector.syntheticPassword != null) {
                    return unlockLskfBasedProtector.syntheticPassword.derivePasswordHashFactor();
                }
                android.util.Slog.w(TAG, "Current credential is incorrect");
                return null;
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Failed to get unified profile password", e);
            return null;
        } finally {
            scheduleGc();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long addEscrowToken(@android.annotation.NonNull byte[] bArr, int i, int i2, @android.annotation.NonNull com.android.internal.widget.LockPatternUtils.EscrowTokenStateChangeCallback escrowTokenStateChangeCallback) {
        com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword syntheticPassword;
        long addPendingToken;
        com.android.server.utils.Slogf.i(TAG, "Adding escrow token for user %d", java.lang.Integer.valueOf(i2));
        synchronized (this.mSpManager) {
            try {
                if (isUserSecure(i2)) {
                    syntheticPassword = null;
                } else {
                    syntheticPassword = this.mSpManager.unlockLskfBasedProtector(getGateKeeperService(), getCurrentLskfBasedProtectorId(i2), com.android.internal.widget.LockscreenCredential.createNone(), i2, null).syntheticPassword;
                }
                disableEscrowTokenOnNonManagedDevicesIfNeeded(i2);
                if (!this.mSpManager.hasEscrowData(i2)) {
                    throw new java.lang.SecurityException("Escrow token is disabled on the current user");
                }
                addPendingToken = this.mSpManager.addPendingToken(bArr, i, i2, escrowTokenStateChangeCallback);
                if (syntheticPassword != null) {
                    com.android.server.utils.Slogf.i(TAG, "Immediately activating escrow token %016x", java.lang.Long.valueOf(addPendingToken));
                    this.mSpManager.createTokenBasedProtector(addPendingToken, syntheticPassword, i2);
                } else {
                    com.android.server.utils.Slogf.i(TAG, "Escrow token %016x will be activated when user is unlocked", java.lang.Long.valueOf(addPendingToken));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return addPendingToken;
    }

    private void activateEscrowTokens(com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword syntheticPassword, int i) {
        synchronized (this.mSpManager) {
            try {
                disableEscrowTokenOnNonManagedDevicesIfNeeded(i);
                java.util.Iterator<java.lang.Long> it = this.mSpManager.getPendingTokensForUser(i).iterator();
                while (it.hasNext()) {
                    long longValue = it.next().longValue();
                    com.android.server.utils.Slogf.i(TAG, "Activating escrow token %016x for user %d", java.lang.Long.valueOf(longValue), java.lang.Integer.valueOf(i));
                    this.mSpManager.createTokenBasedProtector(longValue, syntheticPassword, i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isEscrowTokenActive(long j, int i) {
        boolean protectorExists;
        synchronized (this.mSpManager) {
            protectorExists = this.mSpManager.protectorExists(j, i);
        }
        return protectorExists;
    }

    public boolean hasPendingEscrowToken(int i) {
        boolean z;
        checkPasswordReadPermission();
        synchronized (this.mSpManager) {
            z = !this.mSpManager.getPendingTokensForUser(i).isEmpty();
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean removeEscrowToken(long j, int i) {
        synchronized (this.mSpManager) {
            try {
                if (j == getCurrentLskfBasedProtectorId(i)) {
                    android.util.Slog.w(TAG, "Escrow token handle equals LSKF-based protector ID");
                    return false;
                }
                if (this.mSpManager.removePendingToken(j, i)) {
                    return true;
                }
                if (!this.mSpManager.protectorExists(j, i)) {
                    return false;
                }
                this.mSpManager.destroyTokenBasedProtector(j, i);
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean setLockCredentialWithToken(com.android.internal.widget.LockscreenCredential lockscreenCredential, long j, byte[] bArr, final int i) {
        lockscreenCredential.validateBasicRequirements();
        synchronized (this.mSpManager) {
            try {
                if (!this.mSpManager.hasEscrowData(i)) {
                    throw new java.lang.SecurityException("Escrow token is disabled on the current user");
                }
                if (!isEscrowTokenActive(j, i)) {
                    android.util.Slog.e(TAG, "Unknown or unactivated token: " + java.lang.Long.toHexString(j));
                    return false;
                }
                boolean lockCredentialWithTokenInternalLocked = setLockCredentialWithTokenInternalLocked(lockscreenCredential, j, bArr, i);
                if (lockCredentialWithTokenInternalLocked) {
                    synchronized (this.mSeparateChallengeLock) {
                        setSeparateProfileChallengeEnabledLocked(i, true, null);
                    }
                    if (lockscreenCredential.isNone()) {
                        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.locksettings.LockSettingsService$$ExternalSyntheticLambda8
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.locksettings.LockSettingsService.this.lambda$setLockCredentialWithToken$7(i);
                            }
                        });
                    }
                    notifyPasswordChanged(lockscreenCredential, i);
                    notifySeparateProfileChallengeChanged(i);
                }
                return lockCredentialWithTokenInternalLocked;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mSpManager"})
    private boolean setLockCredentialWithTokenInternalLocked(com.android.internal.widget.LockscreenCredential lockscreenCredential, long j, byte[] bArr, int i) {
        com.android.server.utils.Slogf.i(TAG, "Resetting lockscreen credential of user %d using escrow token %016x", java.lang.Integer.valueOf(i), java.lang.Long.valueOf(j));
        com.android.server.locksettings.SyntheticPasswordManager.AuthenticationResult unlockTokenBasedProtector = this.mSpManager.unlockTokenBasedProtector(getGateKeeperService(), j, bArr, i);
        if (unlockTokenBasedProtector.syntheticPassword == null) {
            android.util.Slog.w(TAG, "Invalid escrow token supplied");
            return false;
        }
        if (unlockTokenBasedProtector.gkResponse.getResponseCode() != 0) {
            android.util.Slog.e(TAG, "Obsolete token: synthetic password decrypted but it fails GK verification.");
            return false;
        }
        onSyntheticPasswordUnlocked(i, unlockTokenBasedProtector.syntheticPassword);
        setLockCredentialWithSpLocked(lockscreenCredential, unlockTokenBasedProtector.syntheticPassword, i);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean unlockUserWithToken(long j, byte[] bArr, int i) {
        synchronized (this.mSpManager) {
            try {
                com.android.server.utils.Slogf.i(TAG, "Unlocking user %d using escrow token %016x", java.lang.Integer.valueOf(i), java.lang.Long.valueOf(j));
                if (!this.mSpManager.hasEscrowData(i)) {
                    com.android.server.utils.Slogf.w(TAG, "Escrow token support is disabled on user %d", java.lang.Integer.valueOf(i));
                    return false;
                }
                com.android.server.locksettings.SyntheticPasswordManager.AuthenticationResult unlockTokenBasedProtector = this.mSpManager.unlockTokenBasedProtector(getGateKeeperService(), j, bArr, i);
                if (unlockTokenBasedProtector.syntheticPassword == null) {
                    android.util.Slog.w(TAG, "Invalid escrow token supplied");
                    return false;
                }
                com.android.server.utils.Slogf.i(TAG, "Unlocked synthetic password for user %d using escrow token", java.lang.Integer.valueOf(i));
                onCredentialVerified(unlockTokenBasedProtector.syntheticPassword, loadPasswordMetrics(unlockTokenBasedProtector.syntheticPassword, i), i);
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean tryUnlockWithCachedUnifiedChallenge(int i) {
        checkPasswordReadPermission();
        com.android.internal.widget.LockscreenCredential retrievePassword = this.mUnifiedProfilePasswordCache.retrievePassword(i);
        if (retrievePassword != null) {
            try {
                boolean z = doVerifyCredential(retrievePassword, i, null, 0).getResponseCode() == 0;
                retrievePassword.close();
                return z;
            } catch (java.lang.Throwable th) {
                try {
                    retrievePassword.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        if (retrievePassword != null) {
            retrievePassword.close();
        }
        return false;
    }

    public void removeCachedUnifiedChallenge(int i) {
        checkWritePermission();
        this.mUnifiedProfilePasswordCache.removePassword(i);
    }

    static java.lang.String timestampToString(long j) {
        return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(j));
    }

    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter)) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                dumpInternal(printWriter);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    private void dumpInternal(java.io.PrintWriter printWriter) {
        com.android.internal.util.IndentingPrintWriter indentingPrintWriter = new com.android.internal.util.IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.println("Current lock settings service state:");
        indentingPrintWriter.println();
        indentingPrintWriter.println("User State:");
        indentingPrintWriter.increaseIndent();
        java.util.List users = this.mUserManager.getUsers();
        for (int i = 0; i < users.size(); i++) {
            int i2 = ((android.content.pm.UserInfo) users.get(i)).id;
            indentingPrintWriter.println("User " + i2);
            indentingPrintWriter.increaseIndent();
            synchronized (this.mSpManager) {
                indentingPrintWriter.println(android.text.TextUtils.formatSimple("LSKF-based SP protector ID: %016x", new java.lang.Object[]{java.lang.Long.valueOf(getCurrentLskfBasedProtectorId(i2))}));
                indentingPrintWriter.println(android.text.TextUtils.formatSimple("LSKF last changed: %s (previous protector: %016x)", new java.lang.Object[]{timestampToString(getLong(LSKF_LAST_CHANGED_TIME_KEY, 0L, i2)), java.lang.Long.valueOf(getLong(PREV_LSKF_BASED_PROTECTOR_ID_KEY, 0L, i2))}));
            }
            try {
                indentingPrintWriter.println(android.text.TextUtils.formatSimple("SID: %016x", new java.lang.Object[]{java.lang.Long.valueOf(getGateKeeperService().getSecureUserId(i2))}));
            } catch (android.os.RemoteException e) {
            }
            indentingPrintWriter.println("Quality: " + getKeyguardStoredQuality(i2));
            indentingPrintWriter.println("CredentialType: " + com.android.internal.widget.LockPatternUtils.credentialTypeToString(getCredentialTypeInternal(i2)));
            indentingPrintWriter.println("SeparateChallenge: " + getSeparateProfileChallengeEnabledInternal(i2));
            indentingPrintWriter.println(android.text.TextUtils.formatSimple("Metrics: %s", new java.lang.Object[]{getUserPasswordMetrics(i2) != null ? "known" : "unknown"}));
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.println();
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Keys in namespace:");
        indentingPrintWriter.increaseIndent();
        dumpKeystoreKeys(indentingPrintWriter);
        indentingPrintWriter.println();
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Storage:");
        indentingPrintWriter.increaseIndent();
        this.mStorage.dump(indentingPrintWriter);
        indentingPrintWriter.println();
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("StrongAuth:");
        indentingPrintWriter.increaseIndent();
        this.mStrongAuth.dump(indentingPrintWriter);
        indentingPrintWriter.println();
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("RebootEscrow:");
        indentingPrintWriter.increaseIndent();
        this.mRebootEscrowManager.dump(indentingPrintWriter);
        indentingPrintWriter.println();
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("PasswordHandleCount: " + this.mGatekeeperPasswords.size());
        synchronized (this.mUserCreationAndRemovalLock) {
            indentingPrintWriter.println("ThirdPartyAppsStarted: " + this.mThirdPartyAppsStarted);
        }
    }

    private void dumpKeystoreKeys(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        try {
            java.util.Enumeration<java.lang.String> aliases = this.mKeyStore.aliases();
            while (aliases.hasMoreElements()) {
                indentingPrintWriter.println(aliases.nextElement());
            }
        } catch (java.security.KeyStoreException e) {
            indentingPrintWriter.println("Unable to get keys: " + e.toString());
            android.util.Slog.d(TAG, "Dump error", e);
        }
    }

    private void disableEscrowTokenOnNonManagedDevicesIfNeeded(int i) {
        if (this.mSpManager.hasAnyEscrowData(i)) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (!android.provider.DeviceConfig.getBoolean("device_policy_manager", "deprecate_usermanagerinternal_devicepolicy", true)) {
                    com.android.server.pm.UserManagerInternal userManagerInternal = this.mInjector.getUserManagerInternal();
                    if (userManagerInternal.isUserManaged(i)) {
                        android.util.Slog.i(TAG, "Managed profile can have escrow token");
                        return;
                    } else if (userManagerInternal.isDeviceManaged()) {
                        android.util.Slog.i(TAG, "Corp-owned device can have escrow token");
                        return;
                    }
                } else if (this.mInjector.getDeviceStateCache().isUserOrganizationManaged(i)) {
                    android.util.Slog.i(TAG, "Organization managed users can have escrow token");
                    return;
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                if (!this.mInjector.getDeviceStateCache().isDeviceProvisioned()) {
                    android.util.Slog.i(TAG, "Postpone disabling escrow tokens until device is provisioned");
                } else {
                    if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive")) {
                        return;
                    }
                    com.android.server.utils.Slogf.i(TAG, "Permanently disabling support for escrow tokens on user %d", java.lang.Integer.valueOf(i));
                    this.mSpManager.destroyEscrowData(i);
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    private void scheduleGc() {
        this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.locksettings.LockSettingsService$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.locksettings.LockSettingsService.lambda$scheduleGc$8();
            }
        }, 2000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$scheduleGc$8() {
        java.lang.System.gc();
        java.lang.System.runFinalization();
        java.lang.System.gc();
    }

    private class DeviceProvisionedObserver extends android.database.ContentObserver {
        private final android.net.Uri mDeviceProvisionedUri;
        private boolean mRegistered;

        public DeviceProvisionedObserver() {
            super(null);
            this.mDeviceProvisionedUri = android.provider.Settings.Global.getUriFor("device_provisioned");
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri, int i) {
            if (this.mDeviceProvisionedUri.equals(uri)) {
                updateRegistration();
                if (isProvisioned()) {
                    android.util.Slog.i(com.android.server.locksettings.LockSettingsService.TAG, "Reporting device setup complete to IGateKeeperService");
                    reportDeviceSetupComplete();
                    clearFrpCredentialIfOwnerNotSecure();
                }
            }
        }

        public void onSystemReady() {
            if (com.android.internal.widget.LockPatternUtils.frpCredentialEnabled(com.android.server.locksettings.LockSettingsService.this.mContext)) {
                updateRegistration();
            } else if (!isProvisioned()) {
                android.util.Slog.i(com.android.server.locksettings.LockSettingsService.TAG, "FRP credential disabled, reporting device setup complete to Gatekeeper immediately");
                reportDeviceSetupComplete();
            }
        }

        private void reportDeviceSetupComplete() {
            try {
                com.android.server.locksettings.LockSettingsService.this.getGateKeeperService().reportDeviceSetupComplete();
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.locksettings.LockSettingsService.TAG, "Failure reporting to IGateKeeperService", e);
            }
        }

        private void clearFrpCredentialIfOwnerNotSecure() {
            for (android.content.pm.UserInfo userInfo : com.android.server.locksettings.LockSettingsService.this.mUserManager.getUsers()) {
                if (com.android.internal.widget.LockPatternUtils.userOwnsFrpCredential(com.android.server.locksettings.LockSettingsService.this.mContext, userInfo)) {
                    if (!com.android.server.locksettings.LockSettingsService.this.isUserSecure(userInfo.id)) {
                        com.android.server.utils.Slogf.d(com.android.server.locksettings.LockSettingsService.TAG, "Clearing FRP credential tied to user %d", java.lang.Integer.valueOf(userInfo.id));
                        com.android.server.locksettings.LockSettingsService.this.mStorage.writePersistentDataBlock(0, userInfo.id, 0, null);
                        return;
                    }
                    return;
                }
            }
        }

        private void updateRegistration() {
            boolean z = !isProvisioned();
            if (z == this.mRegistered) {
                return;
            }
            if (z) {
                com.android.server.locksettings.LockSettingsService.this.mContext.getContentResolver().registerContentObserver(this.mDeviceProvisionedUri, false, this);
            } else {
                com.android.server.locksettings.LockSettingsService.this.mContext.getContentResolver().unregisterContentObserver(this);
            }
            this.mRegistered = z;
        }

        private boolean isProvisioned() {
            return android.provider.Settings.Global.getInt(com.android.server.locksettings.LockSettingsService.this.mContext.getContentResolver(), "device_provisioned", 0) != 0;
        }
    }

    private final class LocalService extends com.android.internal.widget.LockSettingsInternal {
        private LocalService() {
        }

        public void onThirdPartyAppsStarted() {
            com.android.server.locksettings.LockSettingsService.this.onThirdPartyAppsStarted();
        }

        public void createNewUser(int i, int i2) {
            com.android.server.locksettings.LockSettingsService.this.createNewUser(i, i2);
        }

        public void removeUser(int i) {
            com.android.server.locksettings.LockSettingsService.this.removeUser(i);
        }

        public long addEscrowToken(byte[] bArr, int i, com.android.internal.widget.LockPatternUtils.EscrowTokenStateChangeCallback escrowTokenStateChangeCallback) {
            return com.android.server.locksettings.LockSettingsService.this.addEscrowToken(bArr, 0, i, escrowTokenStateChangeCallback);
        }

        public boolean removeEscrowToken(long j, int i) {
            return com.android.server.locksettings.LockSettingsService.this.removeEscrowToken(j, i);
        }

        public boolean isEscrowTokenActive(long j, int i) {
            return com.android.server.locksettings.LockSettingsService.this.isEscrowTokenActive(j, i);
        }

        public boolean setLockCredentialWithToken(com.android.internal.widget.LockscreenCredential lockscreenCredential, long j, byte[] bArr, int i) {
            if (!com.android.server.locksettings.LockSettingsService.this.mHasSecureLockScreen && lockscreenCredential != null && lockscreenCredential.getType() != -1) {
                throw new java.lang.UnsupportedOperationException("This operation requires secure lock screen feature.");
            }
            if (!com.android.server.locksettings.LockSettingsService.this.setLockCredentialWithToken(lockscreenCredential, j, bArr, i)) {
                return false;
            }
            com.android.server.locksettings.LockSettingsService.this.onPostPasswordChanged(lockscreenCredential, i);
            return true;
        }

        public boolean unlockUserWithToken(long j, byte[] bArr, int i) {
            return com.android.server.locksettings.LockSettingsService.this.unlockUserWithToken(j, bArr, i);
        }

        public android.app.admin.PasswordMetrics getUserPasswordMetrics(int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (com.android.server.locksettings.LockSettingsService.this.isProfileWithUnifiedLock(i)) {
                    android.util.Slog.w(com.android.server.locksettings.LockSettingsService.TAG, "Querying password metrics for unified challenge profile: " + i);
                }
                android.app.admin.PasswordMetrics userPasswordMetrics = com.android.server.locksettings.LockSettingsService.this.getUserPasswordMetrics(i);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return userPasswordMetrics;
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public boolean prepareRebootEscrow() {
            if (!com.android.server.locksettings.LockSettingsService.this.mRebootEscrowManager.prepareRebootEscrow()) {
                return false;
            }
            com.android.server.locksettings.LockSettingsService.this.mStrongAuth.requireStrongAuth(64, -1);
            return true;
        }

        public void setRebootEscrowListener(com.android.internal.widget.RebootEscrowListener rebootEscrowListener) {
            com.android.server.locksettings.LockSettingsService.this.mRebootEscrowManager.setRebootEscrowListener(rebootEscrowListener);
        }

        public boolean clearRebootEscrow() {
            if (!com.android.server.locksettings.LockSettingsService.this.mRebootEscrowManager.clearRebootEscrow()) {
                return false;
            }
            com.android.server.locksettings.LockSettingsService.this.mStrongAuth.noLongerRequireStrongAuth(64, -1);
            return true;
        }

        public int armRebootEscrow() {
            return com.android.server.locksettings.LockSettingsService.this.mRebootEscrowManager.armRebootEscrowIfNeeded();
        }

        public void refreshStrongAuthTimeout(int i) {
            com.android.server.locksettings.LockSettingsService.this.mStrongAuth.refreshStrongAuthTimeout(i);
        }

        public void registerLockSettingsStateListener(@android.annotation.NonNull com.android.internal.widget.LockSettingsStateListener lockSettingsStateListener) {
            java.util.Objects.requireNonNull(lockSettingsStateListener, "listener cannot be null");
            com.android.server.locksettings.LockSettingsService.this.mLockSettingsStateListeners.add(lockSettingsStateListener);
        }

        public void unregisterLockSettingsStateListener(@android.annotation.NonNull com.android.internal.widget.LockSettingsStateListener lockSettingsStateListener) {
            com.android.server.locksettings.LockSettingsService.this.mLockSettingsStateListeners.remove(lockSettingsStateListener);
        }
    }

    private class RebootEscrowCallbacks implements com.android.server.locksettings.RebootEscrowManager.Callbacks {
        private RebootEscrowCallbacks() {
        }

        @Override // com.android.server.locksettings.RebootEscrowManager.Callbacks
        public boolean isUserSecure(int i) {
            return com.android.server.locksettings.LockSettingsService.this.isUserSecure(i);
        }

        @Override // com.android.server.locksettings.RebootEscrowManager.Callbacks
        public void onRebootEscrowRestored(byte b, byte[] bArr, int i) {
            com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword syntheticPassword = new com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword(b);
            syntheticPassword.recreateDirectly(bArr);
            synchronized (com.android.server.locksettings.LockSettingsService.this.mSpManager) {
                com.android.server.locksettings.LockSettingsService.this.mSpManager.verifyChallenge(com.android.server.locksettings.LockSettingsService.this.getGateKeeperService(), syntheticPassword, 0L, i);
            }
            com.android.server.utils.Slogf.i(com.android.server.locksettings.LockSettingsService.TAG, "Restored synthetic password for user %d using reboot escrow", java.lang.Integer.valueOf(i));
            com.android.server.locksettings.LockSettingsService.this.onCredentialVerified(syntheticPassword, com.android.server.locksettings.LockSettingsService.this.loadPasswordMetrics(syntheticPassword, i), i);
        }
    }
}
