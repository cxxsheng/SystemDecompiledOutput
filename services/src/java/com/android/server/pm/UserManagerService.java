package com.android.server.pm;

/* loaded from: classes2.dex */
public class UserManagerService extends android.os.IUserManager.Stub {
    private static final int ALLOWED_FLAGS_FOR_CREATE_USERS_PERMISSION = 38700;
    private static final java.lang.String ATTR_CONVERTED_FROM_PRE_CREATED = "convertedFromPreCreated";
    private static final java.lang.String ATTR_CREATION_TIME = "created";
    private static final java.lang.String ATTR_FLAGS = "flags";
    private static final java.lang.String ATTR_GUEST_TO_REMOVE = "guestToRemove";
    private static final java.lang.String ATTR_ICON_PATH = "icon";
    private static final java.lang.String ATTR_ID = "id";
    private static final java.lang.String ATTR_KEY = "key";
    private static final java.lang.String ATTR_LAST_ENTERED_FOREGROUND_TIME = "lastEnteredForeground";
    private static final java.lang.String ATTR_LAST_LOGGED_IN_FINGERPRINT = "lastLoggedInFingerprint";
    private static final java.lang.String ATTR_LAST_LOGGED_IN_TIME = "lastLoggedIn";
    private static final java.lang.String ATTR_MULTIPLE = "m";
    private static final java.lang.String ATTR_NEXT_SERIAL_NO = "nextSerialNumber";
    private static final java.lang.String ATTR_PARTIAL = "partial";
    private static final java.lang.String ATTR_PRE_CREATED = "preCreated";
    private static final java.lang.String ATTR_PROFILE_BADGE = "profileBadge";
    private static final java.lang.String ATTR_PROFILE_GROUP_ID = "profileGroupId";
    private static final java.lang.String ATTR_RESTRICTED_PROFILE_PARENT_ID = "restrictedProfileParentId";
    private static final java.lang.String ATTR_SEED_ACCOUNT_NAME = "seedAccountName";
    private static final java.lang.String ATTR_SEED_ACCOUNT_TYPE = "seedAccountType";
    private static final java.lang.String ATTR_SERIAL_NO = "serialNumber";
    private static final java.lang.String ATTR_TYPE = "type";
    private static final java.lang.String ATTR_TYPE_BOOLEAN = "b";
    private static final java.lang.String ATTR_TYPE_BUNDLE = "B";
    private static final java.lang.String ATTR_TYPE_BUNDLE_ARRAY = "BA";
    private static final java.lang.String ATTR_TYPE_INTEGER = "i";
    private static final java.lang.String ATTR_TYPE_STRING = "s";
    private static final java.lang.String ATTR_TYPE_STRING_ARRAY = "sa";
    private static final java.lang.String ATTR_USER_TYPE_VERSION = "userTypeConfigVersion";
    private static final java.lang.String ATTR_USER_VERSION = "version";
    private static final java.lang.String ATTR_VALUE_TYPE = "type";
    private static final long BOOT_USER_SET_TIMEOUT_MS = 300000;
    static final boolean DBG = false;
    public static final boolean DBG_ALLOCATION = false;
    static final boolean DBG_MUMD = false;
    private static final boolean DBG_WITH_STACKTRACE = false;
    private static final long EPOCH_PLUS_30_YEARS = 946080000000L;
    private static final java.lang.String LOG_TAG = "UserManagerService";

    @com.android.internal.annotations.VisibleForTesting
    static final int MAX_RECENTLY_REMOVED_IDS_SIZE = 100;

    @com.android.internal.annotations.VisibleForTesting
    static final int MAX_USER_ID = 21473;

    @com.android.internal.annotations.VisibleForTesting
    static final int MIN_USER_ID = 10;
    private static final long PRIVATE_SPACE_AUTO_LOCK_INACTIVITY_TIMEOUT_MS = 300000;
    private static final boolean RELEASE_DELETED_USER_ID = false;
    private static final java.lang.String RESTRICTIONS_FILE_PREFIX = "res_";
    private static final java.lang.String TAG_ACCOUNT = "account";
    private static final java.lang.String TAG_DEVICE_OWNER_USER_ID = "deviceOwnerUserId";
    private static final java.lang.String TAG_DEVICE_POLICY_GLOBAL_RESTRICTIONS = "device_policy_global_restrictions";
    private static final java.lang.String TAG_DEVICE_POLICY_LOCAL_RESTRICTIONS = "device_policy_local_restrictions";
    private static final java.lang.String TAG_DEVICE_POLICY_RESTRICTIONS = "device_policy_restrictions";
    private static final java.lang.String TAG_ENTRY = "entry";
    private static final java.lang.String TAG_GLOBAL_RESTRICTION_OWNER_ID = "globalRestrictionOwnerUserId";
    private static final java.lang.String TAG_GUEST_RESTRICTIONS = "guestRestrictions";
    private static final java.lang.String TAG_IGNORE_PREPARE_STORAGE_ERRORS = "ignorePrepareStorageErrors";
    private static final java.lang.String TAG_LAST_REQUEST_QUIET_MODE_ENABLED_CALL = "lastRequestQuietModeEnabledCall";
    private static final java.lang.String TAG_NAME = "name";
    private static final java.lang.String TAG_RESTRICTIONS = "restrictions";
    private static final java.lang.String TAG_SEED_ACCOUNT_OPTIONS = "seedAccountOptions";
    private static final java.lang.String TAG_USER = "user";
    private static final java.lang.String TAG_USERS = "users";
    private static final java.lang.String TAG_USER_PROPERTIES = "userProperties";
    private static final java.lang.String TAG_VALUE = "value";
    private static final java.lang.String TRON_DEMO_CREATED = "users_demo_created";
    private static final java.lang.String TRON_GUEST_CREATED = "users_guest_created";
    private static final java.lang.String TRON_USER_CREATED = "users_user_created";
    private static final java.lang.String USER_LIST_FILENAME = "userlist.xml";
    private static final java.lang.String USER_PHOTO_FILENAME = "photo.png";
    private static final java.lang.String USER_PHOTO_FILENAME_TMP = "photo.png.tmp";
    private static final int USER_VERSION = 11;
    static final int WRITE_USER_DELAY = 2000;
    static final int WRITE_USER_LIST_MSG = 2;
    static final int WRITE_USER_MSG = 1;
    private static final java.lang.String XML_SUFFIX = ".xml";
    private static com.android.server.pm.UserManagerService sInstance;
    private final java.lang.String ACTION_DISABLE_QUIET_MODE_AFTER_UNLOCK;
    private android.app.ActivityManagerInternal mAmInternal;
    private com.android.internal.app.IAppOpsService mAppOpsService;
    private final java.lang.Object mAppRestrictionsLock;

    @com.android.internal.annotations.GuardedBy({"mRestrictionsLock"})
    private final com.android.server.pm.RestrictionsSet mAppliedUserRestrictions;

    @com.android.internal.annotations.GuardedBy({"mRestrictionsLock"})
    private final com.android.server.pm.RestrictionsSet mBaseUserRestrictions;

    @com.android.internal.annotations.GuardedBy({"mUsersLock"})
    private int mBootUser;
    private final java.util.concurrent.CountDownLatch mBootUserLatch;

    @com.android.internal.annotations.GuardedBy({"mRestrictionsLock"})
    private final com.android.server.pm.RestrictionsSet mCachedEffectiveUserRestrictions;
    private final android.content.BroadcastReceiver mConfigurationChangeReceiver;
    private final android.content.Context mContext;
    private final android.content.BroadcastReceiver mDeviceInactivityBroadcastReceiver;
    private android.app.admin.DevicePolicyManagerInternal mDevicePolicyManagerInternal;

    @com.android.internal.annotations.GuardedBy({"mRestrictionsLock"})
    private final com.android.server.pm.RestrictionsSet mDevicePolicyUserRestrictions;
    private final android.content.BroadcastReceiver mDisableQuietModeCallback;

    @com.android.internal.annotations.GuardedBy({"mUsersLock"})
    private boolean mForceEphemeralUsers;

    @com.android.internal.annotations.GuardedBy({"mGuestRestrictions"})
    private final android.os.Bundle mGuestRestrictions;
    private final android.os.Handler mHandler;
    private final java.util.concurrent.ThreadPoolExecutor mInternalExecutor;
    private boolean mIsDeviceInactivityBroadcastReceiverRegistered;

    @com.android.internal.annotations.GuardedBy({"mUsersLock"})
    private boolean mIsDeviceManaged;

    @com.android.internal.annotations.GuardedBy({"mUsersLock"})
    private final android.util.SparseBooleanArray mIsUserManaged;
    private android.app.KeyguardManager.KeyguardLockedStateListener mKeyguardLockedStateListener;
    private final android.content.res.Configuration mLastConfiguration;
    private final com.android.server.pm.UserManagerService.LocalService mLocalService;
    private final com.android.internal.widget.LockPatternUtils mLockPatternUtils;

    @com.android.internal.annotations.GuardedBy({"mPackagesLock"})
    private int mNextSerialNumber;
    private final java.util.concurrent.atomic.AtomicReference<java.lang.String> mOwnerName;
    private final android.util.TypedValue mOwnerNameTypedValue;
    private final java.lang.Object mPackagesLock;
    private final com.android.server.pm.PackageManagerService mPm;
    private android.content.pm.PackageManagerInternal mPmInternal;
    private final com.android.server.pm.UserManagerService.SettingsObserver mPrivateSpaceAutoLockSettingsObserver;

    @com.android.internal.annotations.GuardedBy({"mUsersLock"})
    private final java.util.LinkedList<java.lang.Integer> mRecentlyRemovedIds;

    @com.android.internal.annotations.GuardedBy({"mUsersLock"})
    private final android.util.SparseBooleanArray mRemovingUserIds;
    private final java.lang.Object mRestrictionsLock;
    private final com.android.server.pm.UserSystemPackageInstaller mSystemPackageInstaller;
    private boolean mUpdatingSystemUserMode;
    public final java.util.concurrent.atomic.AtomicInteger mUser0Allocations;
    private final com.android.server.pm.UserDataPreparer mUserDataPreparer;

    @com.android.internal.annotations.GuardedBy({"mUsersLock"})
    private int[] mUserIds;

    @com.android.internal.annotations.GuardedBy({"mUsersLock"})
    private int[] mUserIdsIncludingPreCreated;
    private final com.android.server.pm.UserJourneyLogger mUserJourneyLogger;

    @com.android.internal.annotations.GuardedBy({"mUserLifecycleListeners"})
    private final java.util.ArrayList<com.android.server.pm.UserManagerInternal.UserLifecycleListener> mUserLifecycleListeners;
    private final java.io.File mUserListFile;
    private final android.os.IBinder mUserRestrictionToken;

    @com.android.internal.annotations.GuardedBy({"mUserRestrictionsListeners"})
    private final java.util.ArrayList<com.android.server.pm.UserManagerInternal.UserRestrictionsListener> mUserRestrictionsListeners;

    @com.android.internal.annotations.GuardedBy({"mUserStates"})
    private final com.android.server.pm.UserManagerService.WatchedUserStates mUserStates;
    private int mUserTypeVersion;
    private final android.util.ArrayMap<java.lang.String, com.android.server.pm.UserTypeDetails> mUserTypes;
    private int mUserVersion;
    private final com.android.server.pm.UserVisibilityMediator mUserVisibilityMediator;

    @com.android.internal.annotations.GuardedBy({"mUsersLock"})
    private final android.util.SparseArray<com.android.server.pm.UserManagerService.UserData> mUsers;
    private final java.io.File mUsersDir;
    private final java.lang.Object mUsersLock;
    private static final java.lang.String USER_INFO_DIR = "system" + java.io.File.separator + "users";
    private static final java.lang.Object PRIVATE_SPACE_AUTO_LOCK_MESSAGE_TOKEN = new java.lang.Object();

    @com.android.internal.annotations.VisibleForTesting
    static class UserData {
        java.lang.String account;

        @android.annotation.NonNull
        android.content.pm.UserInfo info;
        private boolean mIgnorePrepareStorageErrors;
        long mLastEnteredForegroundTimeMillis;
        private long mLastRequestQuietModeEnabledMillis;
        boolean persistSeedData;
        java.lang.String seedAccountName;
        android.os.PersistableBundle seedAccountOptions;
        java.lang.String seedAccountType;
        long startRealtime;
        long unlockRealtime;
        android.content.pm.UserProperties userProperties;

        UserData() {
        }

        void setLastRequestQuietModeEnabledMillis(long j) {
            this.mLastRequestQuietModeEnabledMillis = j;
        }

        long getLastRequestQuietModeEnabledMillis() {
            return this.mLastRequestQuietModeEnabledMillis;
        }

        boolean getIgnorePrepareStorageErrors() {
            return this.mIgnorePrepareStorageErrors;
        }

        void setIgnorePrepareStorageErrors() {
            if (android.os.Build.VERSION.DEVICE_INITIAL_SDK_INT < 33) {
                this.mIgnorePrepareStorageErrors = true;
            } else {
                android.util.Slog.w(com.android.server.pm.UserManagerService.LOG_TAG, "Not setting mIgnorePrepareStorageErrors to true since this is a new device");
            }
        }

        void clearSeedAccountData() {
            this.seedAccountName = null;
            this.seedAccountType = null;
            this.seedAccountOptions = null;
            this.persistSeedData = false;
        }
    }

    private final class SettingsObserver extends android.database.ContentObserver {
        SettingsObserver(android.os.Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri) {
            if (com.android.server.pm.UserManagerService.isAutoLockForPrivateSpaceEnabled() && android.text.TextUtils.equals(uri.getLastPathSegment(), "private_space_auto_lock")) {
                int intForUser = android.provider.Settings.Secure.getIntForUser(com.android.server.pm.UserManagerService.this.mContext.getContentResolver(), "private_space_auto_lock", 2, com.android.server.pm.UserManagerService.this.getMainUserIdUnchecked());
                android.util.Slog.i(com.android.server.pm.UserManagerService.LOG_TAG, "Auto-lock settings changed to " + intForUser);
                com.android.server.pm.UserManagerService.this.setOrUpdateAutoLockPreferenceForPrivateProfile(intForUser);
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void maybeScheduleMessageToAutoLockPrivateSpace() {
        int privateProfileUserId;
        if (android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "private_space_auto_lock", 2, getMainUserIdUnchecked()) == 1 && (privateProfileUserId = getPrivateProfileUserId()) != -10000) {
            scheduleMessageToAutoLockPrivateSpace(privateProfileUserId, PRIVATE_SPACE_AUTO_LOCK_MESSAGE_TOKEN, com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void scheduleMessageToAutoLockPrivateSpace(final int i, java.lang.Object obj, long j) {
        this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.pm.UserManagerService$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.UserManagerService.this.lambda$scheduleMessageToAutoLockPrivateSpace$0(i);
            }
        }, obj, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleMessageToAutoLockPrivateSpace$0(int i) {
        android.os.PowerManager powerManager = (android.os.PowerManager) this.mContext.getSystemService(android.os.PowerManager.class);
        if (powerManager == null || powerManager.isInteractive()) {
            android.util.Slog.i(LOG_TAG, "Device is interactive, skipping auto-lock");
            return;
        }
        android.util.Slog.i(LOG_TAG, "Auto-locking private space with user-id " + i);
        setQuietModeEnabledAsync(i, true, null, this.mContext.getPackageName());
    }

    @android.annotation.RequiresPermission("android.permission.SUBSCRIBE_TO_KEYGUARD_LOCKED_STATE")
    private void initializeAndRegisterKeyguardLockedStateListener() {
        this.mKeyguardLockedStateListener = new android.app.KeyguardManager.KeyguardLockedStateListener() { // from class: com.android.server.pm.UserManagerService$$ExternalSyntheticLambda2
            @Override // android.app.KeyguardManager.KeyguardLockedStateListener
            public final void onKeyguardLockedStateChanged(boolean z) {
                com.android.server.pm.UserManagerService.this.tryAutoLockingPrivateSpaceOnKeyguardChanged(z);
            }
        };
        try {
            android.app.KeyguardManager keyguardManager = (android.app.KeyguardManager) this.mContext.getSystemService(android.app.KeyguardManager.class);
            android.util.Slog.i(LOG_TAG, "Adding keyguard locked state listener");
            keyguardManager.addKeyguardLockedStateListener(new android.os.HandlerExecutor(this.mHandler), this.mKeyguardLockedStateListener);
        } catch (java.lang.Exception e) {
            android.util.Slog.e(LOG_TAG, "Error adding keyguard locked listener ", e);
        }
    }

    @android.annotation.RequiresPermission("android.permission.SUBSCRIBE_TO_KEYGUARD_LOCKED_STATE")
    @com.android.internal.annotations.VisibleForTesting
    void setOrUpdateAutoLockPreferenceForPrivateProfile(int i) {
        if (getPrivateProfileUserId() == -10000) {
            android.util.Slog.e(LOG_TAG, "Auto-lock preference updated but private space user not found");
            return;
        }
        if (i == 1) {
            if (!this.mIsDeviceInactivityBroadcastReceiverRegistered) {
                android.util.Slog.i(LOG_TAG, "Registering device inactivity broadcast receivers");
                this.mContext.registerReceiver(this.mDeviceInactivityBroadcastReceiver, new android.content.IntentFilter("android.intent.action.SCREEN_OFF"), null, this.mHandler);
                this.mContext.registerReceiver(this.mDeviceInactivityBroadcastReceiver, new android.content.IntentFilter("android.intent.action.SCREEN_ON"), null, this.mHandler);
                this.mIsDeviceInactivityBroadcastReceiverRegistered = true;
            }
        } else if (this.mIsDeviceInactivityBroadcastReceiverRegistered) {
            android.util.Slog.i(LOG_TAG, "Removing device inactivity broadcast receivers");
            this.mHandler.removeCallbacksAndMessages(PRIVATE_SPACE_AUTO_LOCK_MESSAGE_TOKEN);
            this.mContext.unregisterReceiver(this.mDeviceInactivityBroadcastReceiver);
            this.mIsDeviceInactivityBroadcastReceiverRegistered = false;
        }
        if (i == 0) {
            initializeAndRegisterKeyguardLockedStateListener();
            return;
        }
        try {
            android.app.KeyguardManager keyguardManager = (android.app.KeyguardManager) this.mContext.getSystemService(android.app.KeyguardManager.class);
            android.util.Slog.i(LOG_TAG, "Removing keyguard locked state listener");
            keyguardManager.removeKeyguardLockedStateListener(this.mKeyguardLockedStateListener);
        } catch (java.lang.Exception e) {
            android.util.Slog.e(LOG_TAG, "Error adding keyguard locked state listener ", e);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void tryAutoLockingPrivateSpaceOnKeyguardChanged(boolean z) {
        if (isAutoLockForPrivateSpaceEnabled()) {
            boolean z2 = android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "private_space_auto_lock", 2, getMainUserIdUnchecked()) == 0;
            if (z && z2) {
                autoLockPrivateSpace();
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void autoLockPrivateSpace() {
        int privateProfileUserId = getPrivateProfileUserId();
        if (privateProfileUserId != -10000) {
            android.util.Slog.i(LOG_TAG, "Auto-locking private space with user-id " + privateProfileUserId);
            setQuietModeEnabledAsync(privateProfileUserId, true, null, this.mContext.getPackageName());
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void setQuietModeEnabledAsync(final int i, final boolean z, final android.content.IntentSender intentSender, @android.annotation.Nullable final java.lang.String str) {
        if (android.multiuser.Flags.moveQuietModeOperationsToSeparateThread()) {
            android.util.Slog.d(LOG_TAG, "Calling setQuietModeEnabled for user " + i + " on a separate thread");
            this.mInternalExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.pm.UserManagerService$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.pm.UserManagerService.this.lambda$setQuietModeEnabledAsync$1(i, z, intentSender, str);
                }
            });
            return;
        }
        com.android.internal.os.BackgroundThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.pm.UserManagerService$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.UserManagerService.this.lambda$setQuietModeEnabledAsync$2(i, z, intentSender, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    class DisableQuietModeUserUnlockedCallback extends android.os.IProgressListener.Stub {
        private final android.content.IntentSender mTarget;

        public DisableQuietModeUserUnlockedCallback(android.content.IntentSender intentSender) {
            java.util.Objects.requireNonNull(intentSender);
            this.mTarget = intentSender;
        }

        public void onStarted(int i, android.os.Bundle bundle) {
        }

        public void onProgress(int i, int i2, android.os.Bundle bundle) {
        }

        public void onFinished(int i, android.os.Bundle bundle) {
            com.android.server.pm.UserManagerService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.UserManagerService$DisableQuietModeUserUnlockedCallback$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.pm.UserManagerService.DisableQuietModeUserUnlockedCallback.this.lambda$onFinished$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFinished$0() {
            try {
                com.android.server.pm.UserManagerService.this.mContext.startIntentSender(this.mTarget, null, 0, 0, 0, android.app.ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(1).toBundle());
            } catch (android.content.IntentSender.SendIntentException e) {
                android.util.Slog.e(com.android.server.pm.UserManagerService.LOG_TAG, "Failed to start the target in the callback", e);
            }
        }
    }

    private class WatchedUserStates {
        final android.util.SparseIntArray states = new android.util.SparseIntArray();

        public WatchedUserStates() {
            invalidateIsUserUnlockedCache();
        }

        public int get(int i) {
            return this.states.get(i);
        }

        public int get(int i, int i2) {
            return this.states.indexOfKey(i) >= 0 ? this.states.get(i) : i2;
        }

        public void put(int i, int i2) {
            this.states.put(i, i2);
            invalidateIsUserUnlockedCache();
        }

        public void delete(int i) {
            this.states.delete(i);
            invalidateIsUserUnlockedCache();
        }

        public boolean has(int i) {
            return this.states.get(i, com.android.server.am.ProcessList.INVALID_ADJ) != -10000;
        }

        public java.lang.String toString() {
            return this.states.toString();
        }

        private void invalidateIsUserUnlockedCache() {
            android.os.UserManager.invalidateIsUserUnlockedCache();
        }
    }

    public static com.android.server.pm.UserManagerService getInstance() {
        com.android.server.pm.UserManagerService userManagerService;
        synchronized (com.android.server.pm.UserManagerService.class) {
            userManagerService = sInstance;
        }
        return userManagerService;
    }

    public static class LifeCycle extends com.android.server.SystemService {
        private com.android.server.pm.UserManagerService mUms;

        public LifeCycle(android.content.Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            this.mUms = com.android.server.pm.UserManagerService.getInstance();
            publishBinderService(com.android.server.pm.UserManagerService.TAG_USER, this.mUms);
        }

        @Override // com.android.server.SystemService
        public void onBootPhase(int i) {
            if (i == 550) {
                this.mUms.cleanupPartialUsers();
                if (this.mUms.mPm.isDeviceUpgrading()) {
                    this.mUms.cleanupPreCreatedUsers();
                }
                this.mUms.registerStatsCallbacks();
            }
        }

        @Override // com.android.server.SystemService
        public void onUserStarting(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
            synchronized (this.mUms.mUsersLock) {
                try {
                    com.android.server.pm.UserManagerService.UserData userDataLU = this.mUms.getUserDataLU(targetUser.getUserIdentifier());
                    if (userDataLU != null) {
                        userDataLU.startRealtime = android.os.SystemClock.elapsedRealtime();
                        if (targetUser.getUserIdentifier() == 0 && targetUser.isFull()) {
                            this.mUms.setLastEnteredForegroundTimeToNow(userDataLU);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.SystemService
        public void onUserUnlocking(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
            synchronized (this.mUms.mUsersLock) {
                try {
                    com.android.server.pm.UserManagerService.UserData userDataLU = this.mUms.getUserDataLU(targetUser.getUserIdentifier());
                    if (userDataLU != null) {
                        userDataLU.unlockRealtime = android.os.SystemClock.elapsedRealtime();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (targetUser.getUserIdentifier() == 0 && android.os.UserManager.isCommunalProfileEnabled()) {
                this.mUms.startCommunalProfile();
            }
        }

        @Override // com.android.server.SystemService
        public void onUserSwitching(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser, @android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser2) {
            synchronized (this.mUms.mUsersLock) {
                try {
                    com.android.server.pm.UserManagerService.UserData userDataLU = this.mUms.getUserDataLU(targetUser2.getUserIdentifier());
                    if (userDataLU != null) {
                        this.mUms.setLastEnteredForegroundTimeToNow(userDataLU);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.SystemService
        public void onUserStopping(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
            synchronized (this.mUms.mUsersLock) {
                try {
                    com.android.server.pm.UserManagerService.UserData userDataLU = this.mUms.getUserDataLU(targetUser.getUserIdentifier());
                    if (userDataLU != null) {
                        userDataLU.startRealtime = 0L;
                        userDataLU.unlockRealtime = 0L;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    UserManagerService(android.content.Context context) {
        this(context, null, null, new java.lang.Object(), context.getCacheDir(), null);
    }

    UserManagerService(android.content.Context context, com.android.server.pm.PackageManagerService packageManagerService, com.android.server.pm.UserDataPreparer userDataPreparer, java.lang.Object obj) {
        this(context, packageManagerService, userDataPreparer, obj, android.os.Environment.getDataDirectory(), null);
    }

    @com.android.internal.annotations.VisibleForTesting
    UserManagerService(android.content.Context context, com.android.server.pm.PackageManagerService packageManagerService, com.android.server.pm.UserDataPreparer userDataPreparer, java.lang.Object obj, java.io.File file, android.util.SparseArray<com.android.server.pm.UserManagerService.UserData> sparseArray) {
        this.mUsersLock = com.android.server.LockGuard.installNewLock(2);
        this.mRestrictionsLock = new java.lang.Object();
        this.mAppRestrictionsLock = new java.lang.Object();
        this.mUserRestrictionToken = new android.os.Binder();
        this.mBootUserLatch = new java.util.concurrent.CountDownLatch(1);
        this.mBaseUserRestrictions = new com.android.server.pm.RestrictionsSet();
        this.mCachedEffectiveUserRestrictions = new com.android.server.pm.RestrictionsSet();
        this.mAppliedUserRestrictions = new com.android.server.pm.RestrictionsSet();
        this.mDevicePolicyUserRestrictions = new com.android.server.pm.RestrictionsSet();
        this.mGuestRestrictions = new android.os.Bundle();
        this.mRemovingUserIds = new android.util.SparseBooleanArray();
        this.mRecentlyRemovedIds = new java.util.LinkedList<>();
        this.mUserVersion = 0;
        this.mUserTypeVersion = 0;
        this.mIsUserManaged = new android.util.SparseBooleanArray();
        this.mUserRestrictionsListeners = new java.util.ArrayList<>();
        this.mUserLifecycleListeners = new java.util.ArrayList<>();
        this.mUserJourneyLogger = new com.android.server.pm.UserJourneyLogger();
        this.ACTION_DISABLE_QUIET_MODE_AFTER_UNLOCK = "com.android.server.pm.DISABLE_QUIET_MODE_AFTER_UNLOCK";
        this.mDisableQuietModeCallback = new android.content.BroadcastReceiver() { // from class: com.android.server.pm.UserManagerService.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                if (!"com.android.server.pm.DISABLE_QUIET_MODE_AFTER_UNLOCK".equals(intent.getAction())) {
                    return;
                }
                android.content.IntentSender intentSender = (android.content.IntentSender) intent.getParcelableExtra("android.intent.extra.INTENT", android.content.IntentSender.class);
                com.android.server.pm.UserManagerService.this.setQuietModeEnabledAsync(intent.getIntExtra("android.intent.extra.USER_ID", com.android.server.am.ProcessList.INVALID_ADJ), false, intentSender, intent.getStringExtra("android.intent.extra.PACKAGE_NAME"));
            }
        };
        this.mIsDeviceInactivityBroadcastReceiverRegistered = false;
        this.mDeviceInactivityBroadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.pm.UserManagerService.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                if (com.android.server.pm.UserManagerService.isAutoLockForPrivateSpaceEnabled()) {
                    if ("android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                        com.android.server.pm.UserManagerService.this.maybeScheduleMessageToAutoLockPrivateSpace();
                    } else if ("android.intent.action.SCREEN_ON".equals(intent.getAction())) {
                        com.android.server.pm.UserManagerService.this.mHandler.removeCallbacksAndMessages(com.android.server.pm.UserManagerService.PRIVATE_SPACE_AUTO_LOCK_MESSAGE_TOKEN);
                    }
                }
            }
        };
        this.mOwnerName = new java.util.concurrent.atomic.AtomicReference<>();
        this.mOwnerNameTypedValue = new android.util.TypedValue();
        this.mLastConfiguration = new android.content.res.Configuration();
        this.mConfigurationChangeReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.pm.UserManagerService.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                if (!"android.intent.action.CONFIGURATION_CHANGED".equals(intent.getAction())) {
                    return;
                }
                com.android.server.pm.UserManagerService.this.invalidateOwnerNameIfNecessary(context2.getResources(), false);
            }
        };
        this.mUserStates = new com.android.server.pm.UserManagerService.WatchedUserStates();
        this.mBootUser = com.android.server.am.ProcessList.INVALID_ADJ;
        this.mContext = context;
        this.mPm = packageManagerService;
        this.mPackagesLock = obj;
        this.mUsers = sparseArray == null ? new android.util.SparseArray<>() : sparseArray;
        this.mHandler = new com.android.server.pm.UserManagerService.MainHandler();
        this.mInternalExecutor = new java.util.concurrent.ThreadPoolExecutor(0, 1, 24L, java.util.concurrent.TimeUnit.HOURS, new java.util.concurrent.LinkedBlockingQueue());
        this.mUserVisibilityMediator = new com.android.server.pm.UserVisibilityMediator(this.mHandler);
        this.mUserDataPreparer = userDataPreparer;
        this.mUserTypes = com.android.server.pm.UserTypeFactory.getUserTypes();
        invalidateOwnerNameIfNecessary(context.getResources(), true);
        synchronized (this.mPackagesLock) {
            this.mUsersDir = new java.io.File(file, USER_INFO_DIR);
            this.mUsersDir.mkdirs();
            new java.io.File(this.mUsersDir, java.lang.String.valueOf(0)).mkdirs();
            android.os.FileUtils.setPermissions(this.mUsersDir.toString(), 509, -1, -1);
            this.mUserListFile = new java.io.File(this.mUsersDir, USER_LIST_FILENAME);
            initDefaultGuestRestrictions();
            readUserListLP();
            sInstance = this;
        }
        this.mSystemPackageInstaller = new com.android.server.pm.UserSystemPackageInstaller(this, this.mUserTypes);
        this.mLocalService = new com.android.server.pm.UserManagerService.LocalService();
        com.android.server.LocalServices.addService(com.android.server.pm.UserManagerInternal.class, this.mLocalService);
        this.mLockPatternUtils = new com.android.internal.widget.LockPatternUtils(this.mContext);
        this.mUserStates.put(0, 0);
        this.mUser0Allocations = null;
        this.mPrivateSpaceAutoLockSettingsObserver = new com.android.server.pm.UserManagerService.SettingsObserver(this.mHandler);
        emulateSystemUserModeIfNeeded();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isAutoLockForPrivateSpaceEnabled() {
        return android.os.Flags.allowPrivateProfile() && android.multiuser.Flags.supportAutolockForPrivateSpace();
    }

    void systemReady() {
        int mainUserIdUnchecked;
        this.mAppOpsService = com.android.internal.app.IAppOpsService.Stub.asInterface(android.os.ServiceManager.getService("appops"));
        synchronized (this.mRestrictionsLock) {
            applyUserRestrictionsLR(0);
        }
        this.mContext.registerReceiver(this.mDisableQuietModeCallback, new android.content.IntentFilter("com.android.server.pm.DISABLE_QUIET_MODE_AFTER_UNLOCK"), null, this.mHandler);
        this.mContext.registerReceiver(this.mConfigurationChangeReceiver, new android.content.IntentFilter("android.intent.action.CONFIGURATION_CHANGED"), null, this.mHandler);
        if (isAutoLockForPrivateSpaceEnabled() && (mainUserIdUnchecked = getMainUserIdUnchecked()) != -10000) {
            this.mContext.getContentResolver().registerContentObserverAsUser(android.provider.Settings.Secure.getUriFor("private_space_auto_lock"), false, this.mPrivateSpaceAutoLockSettingsObserver, android.os.UserHandle.of(mainUserIdUnchecked));
            setOrUpdateAutoLockPreferenceForPrivateProfile(android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "private_space_auto_lock", 2, mainUserIdUnchecked));
        }
        if (isAutoLockingPrivateSpaceOnRestartsEnabled()) {
            autoLockPrivateSpace();
        }
        markEphemeralUsersForRemoval();
    }

    private boolean isAutoLockingPrivateSpaceOnRestartsEnabled() {
        return android.os.Flags.allowPrivateProfile() && android.multiuser.Flags.enablePrivateSpaceAutolockOnRestarts();
    }

    com.android.server.pm.UserManagerInternal getInternalForInjectorOnly() {
        return this.mLocalService;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startCommunalProfile() {
        boolean z;
        int communalProfileIdUnchecked = getCommunalProfileIdUnchecked();
        if (communalProfileIdUnchecked != -10000) {
            com.android.server.utils.Slogf.d(LOG_TAG, "Starting the Communal Profile");
            try {
                z = android.app.ActivityManager.getService().startProfile(communalProfileIdUnchecked);
            } catch (android.os.RemoteException e) {
                e.rethrowAsRuntimeException();
                z = false;
            }
            if (!z) {
                com.android.server.utils.Slogf.wtf(LOG_TAG, "Failed to start communal profile userId=%d", java.lang.Integer.valueOf(communalProfileIdUnchecked));
                return;
            }
            return;
        }
        com.android.server.utils.Slogf.w(LOG_TAG, "Cannot start Communal Profile because there isn't one");
    }

    private void markEphemeralUsersForRemoval() {
        synchronized (this.mUsersLock) {
            try {
                int size = this.mUsers.size();
                for (int i = 0; i < size; i++) {
                    android.content.pm.UserInfo userInfo = this.mUsers.valueAt(i).info;
                    if (userInfo.isEphemeral() && !userInfo.preCreated && userInfo.id != 0) {
                        addRemovingUserIdLocked(userInfo.id);
                        userInfo.partial = true;
                        userInfo.flags |= 64;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cleanupPartialUsers() {
        int i;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mUsersLock) {
            try {
                int size = this.mUsers.size();
                for (int i2 = 0; i2 < size; i2++) {
                    android.content.pm.UserInfo userInfo = this.mUsers.valueAt(i2).info;
                    if ((userInfo.partial || userInfo.guestToRemove) && userInfo.id != 0) {
                        arrayList.add(userInfo);
                        if (!this.mRemovingUserIds.get(userInfo.id)) {
                            addRemovingUserIdLocked(userInfo.id);
                        }
                        userInfo.partial = true;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        int size2 = arrayList.size();
        for (i = 0; i < size2; i++) {
            android.content.pm.UserInfo userInfo2 = (android.content.pm.UserInfo) arrayList.get(i);
            android.util.Slog.w(LOG_TAG, "Removing partially created user " + userInfo2.id + " (name=" + userInfo2.name + ")");
            removeUserState(userInfo2.id);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cleanupPreCreatedUsers() {
        java.util.ArrayList arrayList;
        int i;
        synchronized (this.mUsersLock) {
            try {
                int size = this.mUsers.size();
                arrayList = new java.util.ArrayList(size);
                for (int i2 = 0; i2 < size; i2++) {
                    android.content.pm.UserInfo userInfo = this.mUsers.valueAt(i2).info;
                    if (userInfo.preCreated) {
                        arrayList.add(userInfo);
                        addRemovingUserIdLocked(userInfo.id);
                        userInfo.flags |= 64;
                        userInfo.partial = true;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        int size2 = arrayList.size();
        for (i = 0; i < size2; i++) {
            android.content.pm.UserInfo userInfo2 = (android.content.pm.UserInfo) arrayList.get(i);
            android.util.Slog.i(LOG_TAG, "Removing pre-created user " + userInfo2.id);
            removeUserState(userInfo2.id);
        }
    }

    public java.lang.String getUserAccount(int i) {
        java.lang.String str;
        checkManageUserAndAcrossUsersFullPermission("get user account");
        synchronized (this.mUsersLock) {
            str = this.mUsers.get(i).account;
        }
        return str;
    }

    public void setUserAccount(int i, java.lang.String str) {
        checkManageUserAndAcrossUsersFullPermission("set user account");
        synchronized (this.mPackagesLock) {
            synchronized (this.mUsersLock) {
                com.android.server.pm.UserManagerService.UserData userData = this.mUsers.get(i);
                if (userData == null) {
                    android.util.Slog.e(LOG_TAG, "User not found for setting user account: u" + i);
                    return;
                }
                if (java.util.Objects.equals(userData.account, str)) {
                    userData = null;
                } else {
                    userData.account = str;
                }
                if (userData != null) {
                    writeUserLP(userData);
                }
            }
        }
    }

    public android.content.pm.UserInfo getPrimaryUser() {
        checkManageUsersPermission("query users");
        synchronized (this.mUsersLock) {
            try {
                int size = this.mUsers.size();
                for (int i = 0; i < size; i++) {
                    android.content.pm.UserInfo userInfo = this.mUsers.valueAt(i).info;
                    if (userInfo.isPrimary() && !this.mRemovingUserIds.get(userInfo.id)) {
                        return userInfo;
                    }
                }
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int getMainUserId() {
        checkQueryOrCreateUsersPermission("get main user id");
        return getMainUserIdUnchecked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getMainUserIdUnchecked() {
        synchronized (this.mUsersLock) {
            try {
                int size = this.mUsers.size();
                for (int i = 0; i < size; i++) {
                    android.content.pm.UserInfo userInfo = this.mUsers.valueAt(i).info;
                    if (userInfo.isMain() && !this.mRemovingUserIds.get(userInfo.id)) {
                        return userInfo.id;
                    }
                }
                return com.android.server.am.ProcessList.INVALID_ADJ;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private int getPrivateProfileUserId() {
        synchronized (this.mUsersLock) {
            try {
                for (int i : getUserIds()) {
                    android.content.pm.UserInfo userInfoLU = getUserInfoLU(i);
                    if (userInfoLU != null && userInfoLU.isPrivateProfile()) {
                        return userInfoLU.id;
                    }
                }
                return com.android.server.am.ProcessList.INVALID_ADJ;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void setBootUser(int i) {
        checkCreateUsersPermission("Set boot user");
        synchronized (this.mUsersLock) {
            com.android.server.utils.Slogf.i(LOG_TAG, "setBootUser %d", java.lang.Integer.valueOf(i));
            this.mBootUser = i;
        }
        this.mBootUserLatch.countDown();
    }

    public int getBootUser() {
        checkCreateUsersPermission("Get boot user");
        try {
            return getBootUserUnchecked();
        } catch (android.os.UserManager.CheckedUserOperationException e) {
            throw e.toServiceSpecificException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getBootUserUnchecked() throws android.os.UserManager.CheckedUserOperationException {
        synchronized (this.mUsersLock) {
            try {
                if (this.mBootUser != -10000) {
                    com.android.server.pm.UserManagerService.UserData userData = this.mUsers.get(this.mBootUser);
                    if (userData != null && userData.info.supportsSwitchToByUser()) {
                        com.android.server.utils.Slogf.i(LOG_TAG, "Using provided boot user: %d", java.lang.Integer.valueOf(this.mBootUser));
                        return this.mBootUser;
                    }
                    com.android.server.utils.Slogf.w(LOG_TAG, "Provided boot user cannot be switched to: %d", java.lang.Integer.valueOf(this.mBootUser));
                }
                if (!isHeadlessSystemUserMode()) {
                    return 0;
                }
                int previousFullUserToEnterForeground = getPreviousFullUserToEnterForeground();
                if (previousFullUserToEnterForeground != -10000) {
                    com.android.server.utils.Slogf.i(LOG_TAG, "Boot user is previous user %d", java.lang.Integer.valueOf(previousFullUserToEnterForeground));
                    return previousFullUserToEnterForeground;
                }
                synchronized (this.mUsersLock) {
                    try {
                        int size = this.mUsers.size();
                        for (int i = 0; i < size; i++) {
                            com.android.server.pm.UserManagerService.UserData valueAt = this.mUsers.valueAt(i);
                            if (valueAt.info.supportsSwitchToByUser()) {
                                int i2 = valueAt.info.id;
                                com.android.server.utils.Slogf.i(LOG_TAG, "Boot user is first switchable user %d", java.lang.Integer.valueOf(i2));
                                return i2;
                            }
                        }
                        throw new android.os.UserManager.CheckedUserOperationException("No switchable users found", 1);
                    } finally {
                    }
                }
            } finally {
            }
        }
    }

    public int getPreviousFullUserToEnterForeground() {
        int i;
        checkQueryOrCreateUsersPermission("get previous user");
        int currentUserId = getCurrentUserId();
        synchronized (this.mUsersLock) {
            try {
                int size = this.mUsers.size();
                i = com.android.server.am.ProcessList.INVALID_ADJ;
                long j = 0;
                for (int i2 = 0; i2 < size; i2++) {
                    com.android.server.pm.UserManagerService.UserData valueAt = this.mUsers.valueAt(i2);
                    int i3 = valueAt.info.id;
                    if (i3 != currentUserId && valueAt.info.isFull() && !valueAt.info.partial && valueAt.info.isEnabled() && !this.mRemovingUserIds.get(i3)) {
                        long j2 = valueAt.mLastEnteredForegroundTimeMillis;
                        if (j2 > j) {
                            i = i3;
                            j = j2;
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return i;
    }

    public int getCommunalProfileId() {
        checkQueryOrCreateUsersPermission("get communal profile user id");
        return getCommunalProfileIdUnchecked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getCommunalProfileIdUnchecked() {
        synchronized (this.mUsersLock) {
            try {
                int size = this.mUsers.size();
                for (int i = 0; i < size; i++) {
                    android.content.pm.UserInfo userInfo = this.mUsers.valueAt(i).info;
                    if (userInfo.isCommunalProfile() && !this.mRemovingUserIds.get(userInfo.id)) {
                        return userInfo.id;
                    }
                }
                return com.android.server.am.ProcessList.INVALID_ADJ;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.NonNull
    public java.util.List<android.content.pm.UserInfo> getUsers(boolean z) {
        return getUsers(true, z, true);
    }

    @android.annotation.NonNull
    public java.util.List<android.content.pm.UserInfo> getUsers(boolean z, boolean z2, boolean z3) {
        checkCreateUsersPermission("query users");
        return getUsersInternal(z, z2, z3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    public java.util.List<android.content.pm.UserInfo> getUsersInternal(boolean z, boolean z2, boolean z3) {
        java.util.ArrayList arrayList;
        synchronized (this.mUsersLock) {
            try {
                arrayList = new java.util.ArrayList(this.mUsers.size());
                int size = this.mUsers.size();
                for (int i = 0; i < size; i++) {
                    android.content.pm.UserInfo userInfo = this.mUsers.valueAt(i).info;
                    if ((!z || !userInfo.partial) && ((!z2 || !this.mRemovingUserIds.get(userInfo.id)) && (!z3 || !userInfo.preCreated))) {
                        arrayList.add(userWithName(userInfo));
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public java.util.List<android.content.pm.UserInfo> getProfiles(int i, boolean z) {
        boolean hasCreateUsersPermission;
        java.util.List<android.content.pm.UserInfo> profilesLU;
        if (i != android.os.UserHandle.getCallingUserId()) {
            checkQueryOrCreateUsersPermission("getting profiles related to user " + i);
            hasCreateUsersPermission = true;
        } else {
            hasCreateUsersPermission = hasCreateUsersPermission();
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mUsersLock) {
                profilesLU = getProfilesLU(i, null, z, hasCreateUsersPermission);
            }
            return profilesLU;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int[] getProfileIds(int i, boolean z) {
        return getProfileIds(i, null, z, false);
    }

    public int[] getProfileIds(int i, @android.annotation.Nullable java.lang.String str, boolean z, boolean z2) {
        int[] array;
        if (i != android.os.UserHandle.getCallingUserId()) {
            checkQueryOrCreateUsersPermission("getting profiles related to user " + i);
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mUsersLock) {
                array = getProfileIdsLU(i, str, z, z2).toArray();
            }
            return array;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUsersLock"})
    private java.util.List<android.content.pm.UserInfo> getProfilesLU(int i, @android.annotation.Nullable java.lang.String str, boolean z, boolean z2) {
        android.content.pm.UserInfo userWithName;
        android.util.IntArray profileIdsLU = getProfileIdsLU(i, str, z, false);
        java.util.ArrayList arrayList = new java.util.ArrayList(profileIdsLU.size());
        for (int i2 = 0; i2 < profileIdsLU.size(); i2++) {
            android.content.pm.UserInfo userInfo = this.mUsers.get(profileIdsLU.get(i2)).info;
            if (!z2) {
                userWithName = new android.content.pm.UserInfo(userInfo);
                userWithName.name = null;
                userWithName.iconPath = null;
            } else {
                userWithName = userWithName(userInfo);
            }
            arrayList.add(userWithName);
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mUsersLock"})
    public android.util.IntArray getProfileIdsLU(int i, @android.annotation.Nullable java.lang.String str, boolean z, boolean z2) {
        android.content.pm.UserInfo userInfoLU = getUserInfoLU(i);
        android.util.IntArray intArray = new android.util.IntArray(this.mUsers.size());
        if (userInfoLU == null) {
            return intArray;
        }
        int size = this.mUsers.size();
        for (int i2 = 0; i2 < size; i2++) {
            android.content.pm.UserInfo userInfo = this.mUsers.valueAt(i2).info;
            if (isProfileOf(userInfoLU, userInfo) && ((!z || userInfo.isEnabled()) && !this.mRemovingUserIds.get(userInfo.id) && !userInfo.partial && ((str == null || str.equals(userInfo.userType)) && (!z2 || !isProfileHidden(userInfo.id))))) {
                intArray.add(userInfo.id);
            }
        }
        return intArray;
    }

    public int[] getProfileIdsExcludingHidden(int i, boolean z) {
        return getProfileIds(i, null, z, true);
    }

    private boolean isProfileHidden(int i) {
        return android.os.Flags.allowPrivateProfile() && android.multiuser.Flags.enableHidingProfiles() && getUserPropertiesCopy(i).getProfileApiVisibility() == 1;
    }

    public int getCredentialOwnerProfile(int i) {
        checkManageUsersPermission("get the credential owner");
        if (!this.mLockPatternUtils.isSeparateProfileChallengeEnabled(i)) {
            synchronized (this.mUsersLock) {
                try {
                    android.content.pm.UserInfo profileParentLU = getProfileParentLU(i);
                    if (profileParentLU != null) {
                        return profileParentLU.id;
                    }
                } finally {
                }
            }
        }
        return i;
    }

    public boolean isSameProfileGroup(int i, int i2) {
        if (i == i2) {
            return true;
        }
        checkQueryUsersPermission("check if in the same profile group");
        return isSameProfileGroupNoChecks(i, i2);
    }

    private boolean isSameProfileGroupNoChecks(int i, int i2) {
        synchronized (this.mUsersLock) {
            try {
                android.content.pm.UserInfo userInfoLU = getUserInfoLU(i);
                if (userInfoLU == null || userInfoLU.profileGroupId == -10000) {
                    return false;
                }
                android.content.pm.UserInfo userInfoLU2 = getUserInfoLU(i2);
                if (userInfoLU2 == null || userInfoLU2.profileGroupId == -10000) {
                    return false;
                }
                return userInfoLU.profileGroupId == userInfoLU2.profileGroupId;
            } finally {
            }
        }
    }

    private boolean isSameUserOrProfileGroupOrTargetIsCommunal(android.content.pm.UserInfo userInfo, android.content.pm.UserInfo userInfo2) {
        if (userInfo.id == userInfo2.id) {
            return true;
        }
        if (android.multiuser.Flags.supportCommunalProfile() && userInfo2.isCommunalProfile()) {
            return true;
        }
        return userInfo.profileGroupId != -10000 && userInfo.profileGroupId == userInfo2.profileGroupId;
    }

    public android.content.pm.UserInfo getProfileParent(int i) {
        android.content.pm.UserInfo profileParentLU;
        if (!hasManageUsersOrPermission("android.permission.INTERACT_ACROSS_USERS")) {
            throw new java.lang.SecurityException("You need MANAGE_USERS or INTERACT_ACROSS_USERS permission to get the profile parent");
        }
        synchronized (this.mUsersLock) {
            profileParentLU = getProfileParentLU(i);
        }
        return profileParentLU;
    }

    public int getProfileParentId(int i) {
        checkManageUsersPermission("get the profile parent");
        return getProfileParentIdUnchecked(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getProfileParentIdUnchecked(int i) {
        synchronized (this.mUsersLock) {
            try {
                android.content.pm.UserInfo profileParentLU = getProfileParentLU(i);
                if (profileParentLU == null) {
                    return i;
                }
                return profileParentLU.id;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUsersLock"})
    private android.content.pm.UserInfo getProfileParentLU(int i) {
        int i2;
        android.content.pm.UserInfo userInfoLU = getUserInfoLU(i);
        if (userInfoLU == null || (i2 = userInfoLU.profileGroupId) == i || i2 == -10000) {
            return null;
        }
        return getUserInfoLU(i2);
    }

    private static boolean isProfileOf(android.content.pm.UserInfo userInfo, android.content.pm.UserInfo userInfo2) {
        return userInfo.id == userInfo2.id || (userInfo.profileGroupId != -10000 && userInfo.profileGroupId == userInfo2.profileGroupId);
    }

    private java.lang.String getAvailabilityIntentAction(boolean z, boolean z2) {
        if (z2) {
            if (z) {
                return "android.intent.action.MANAGED_PROFILE_UNAVAILABLE";
            }
            return "android.intent.action.MANAGED_PROFILE_AVAILABLE";
        }
        if (z) {
            return "android.intent.action.PROFILE_UNAVAILABLE";
        }
        return "android.intent.action.PROFILE_AVAILABLE";
    }

    private void broadcastProfileAvailabilityChanges(android.content.pm.UserInfo userInfo, android.os.UserHandle userHandle, boolean z, boolean z2) {
        android.content.Intent intent = new android.content.Intent();
        intent.setAction(getAvailabilityIntentAction(z, z2));
        intent.putExtra("android.intent.extra.QUIET_MODE", z);
        intent.putExtra("android.intent.extra.USER", userInfo.getUserHandle());
        intent.putExtra("android.intent.extra.user_handle", userInfo.getUserHandle().getIdentifier());
        if (userInfo.isManagedProfile()) {
            getDevicePolicyManagerInternal().broadcastIntentToManifestReceivers(intent, userHandle, true);
        }
        intent.addFlags(com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_WAKEUP_EXACT_ALLOW_WHILE_IDLE);
        this.mContext.sendBroadcastAsUser(intent, userHandle, null, new android.app.BroadcastOptions().setDeferralPolicy(2).setDeliveryGroupPolicy(1).setDeliveryGroupMatchingKey(z2 ? "android.intent.action.MANAGED_PROFILE_AVAILABLE" : "android.intent.action.PROFILE_AVAILABLE", java.lang.String.valueOf(userInfo.getUserHandle().getIdentifier())).toBundle());
    }

    public boolean requestQuietModeEnabled(@android.annotation.NonNull java.lang.String str, boolean z, int i, @android.annotation.Nullable android.content.IntentSender intentSender, int i2) {
        android.content.pm.UserInfo userInfo;
        android.content.pm.UserProperties userPropertiesInternal;
        android.app.KeyguardManager keyguardManager;
        java.util.Objects.requireNonNull(str);
        if (z && intentSender != null) {
            throw new java.lang.IllegalArgumentException("target should only be specified when we are disabling quiet mode.");
        }
        boolean z2 = (i2 & 2) != 0;
        boolean z3 = (i2 & 1) != 0;
        if (z2 && z3) {
            throw new java.lang.IllegalArgumentException("invalid flags: " + i2);
        }
        ensureCanModifyQuietMode(str, android.os.Binder.getCallingUid(), i, intentSender != null, z2);
        if (z3 && str.equals(getPackageManagerInternal().getSystemUiServiceComponent().getPackageName())) {
            throw new java.lang.SecurityException("SystemUI is not allowed to set QUIET_MODE_DISABLE_ONLY_IF_CREDENTIAL_NOT_REQUIRED");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        if (z2) {
            try {
                synchronized (this.mUsersLock) {
                    userInfo = getUserInfo(i);
                }
                if (userInfo == null) {
                    throw new java.lang.IllegalArgumentException("Invalid user. Can't find user details for userId " + i);
                }
                if (!userInfo.isManagedProfile()) {
                    throw new java.lang.IllegalArgumentException("Invalid flags: " + i2 + ". Can't skip credential check for the user");
                }
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        if (z) {
            lambda$setQuietModeEnabledAsync$2(i, true, intentSender, str);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        }
        if (android.os.Flags.allowPrivateProfile() && (userPropertiesInternal = getUserPropertiesInternal(i)) != null && userPropertiesInternal.isAuthAlwaysRequiredToDisableQuietMode()) {
            if (z3) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
            if (!android.multiuser.Flags.showSetScreenLockDialog() || (keyguardManager = (android.app.KeyguardManager) this.mContext.getSystemService(android.app.KeyguardManager.class)) == null || keyguardManager.isDeviceSecure()) {
                showConfirmCredentialToDisableQuietMode(i, intentSender, str);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
            android.content.Intent createBaseIntent = com.android.internal.app.SetScreenLockDialogActivity.createBaseIntent(1);
            createBaseIntent.putExtra("origin_user_id", i);
            this.mContext.startActivity(createBaseIntent);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        }
        boolean isManagedProfileWithUnifiedChallenge = this.mLockPatternUtils.isManagedProfileWithUnifiedChallenge(i);
        if (isManagedProfileWithUnifiedChallenge && (!((android.app.KeyguardManager) this.mContext.getSystemService(android.app.KeyguardManager.class)).isDeviceLocked(this.mLocalService.getProfileParentId(i)) || z3)) {
            this.mLockPatternUtils.tryUnlockWithCachedUnifiedChallenge(i);
        }
        if (!((z2 || !this.mLockPatternUtils.isSecure(i) || (isManagedProfileWithUnifiedChallenge && android.os.storage.StorageManager.isCeStorageUnlocked(i))) ? false : true)) {
            lambda$setQuietModeEnabledAsync$2(i, false, intentSender, str);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        }
        if (z3) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        }
        showConfirmCredentialToDisableQuietMode(i, intentSender, str);
        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        return false;
    }

    private void ensureCanModifyQuietMode(java.lang.String str, int i, int i2, boolean z, boolean z2) {
        verifyCallingPackage(str, i);
        if (hasManageUsersPermission()) {
            return;
        }
        if (z) {
            throw new java.lang.SecurityException("MANAGE_USERS permission is required to start intent after disabling quiet mode.");
        }
        if (z2) {
            throw new java.lang.SecurityException("MANAGE_USERS permission is required to disable quiet mode without credentials.");
        }
        if (!isSameProfileGroupNoChecks(android.os.UserHandle.getUserId(i), i2)) {
            throw new java.lang.SecurityException("MANAGE_USERS permission is required to modify quiet mode for a different profile group.");
        }
        if (hasPermissionGranted("android.permission.MODIFY_QUIET_MODE", i)) {
            return;
        }
        android.content.pm.ShortcutServiceInternal shortcutServiceInternal = (android.content.pm.ShortcutServiceInternal) com.android.server.LocalServices.getService(android.content.pm.ShortcutServiceInternal.class);
        if (shortcutServiceInternal != null && shortcutServiceInternal.isForegroundDefaultLauncher(str, i)) {
        } else {
            throw new java.lang.SecurityException("Can't modify quiet mode, caller is neither foreground default launcher nor has MANAGE_USERS/MODIFY_QUIET_MODE permission");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: setQuietModeEnabled, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void lambda$setQuietModeEnabledAsync$2(int i, boolean z, android.content.IntentSender intentSender, @android.annotation.Nullable java.lang.String str) {
        com.android.server.pm.UserManagerService.DisableQuietModeUserUnlockedCallback disableQuietModeUserUnlockedCallback;
        synchronized (this.mUsersLock) {
            try {
                android.content.pm.UserInfo userInfoLU = getUserInfoLU(i);
                android.content.pm.UserInfo profileParentLU = getProfileParentLU(i);
                if (userInfoLU == null || !userInfoLU.isProfile()) {
                    throw new java.lang.IllegalArgumentException("User " + i + " is not a profile");
                }
                if (userInfoLU.isQuietModeEnabled() == z) {
                    android.util.Slog.i(LOG_TAG, "Quiet mode is already " + z);
                    return;
                }
                userInfoLU.flags ^= 128;
                com.android.server.pm.UserManagerService.UserData userDataLU = getUserDataLU(userInfoLU.id);
                synchronized (this.mPackagesLock) {
                    writeUserLP(userDataLU);
                }
                try {
                    if (z) {
                        stopUserForQuietMode(i);
                        ((android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class)).killForegroundAppsForUser(i);
                    } else {
                        if (intentSender != null) {
                            disableQuietModeUserUnlockedCallback = new com.android.server.pm.UserManagerService.DisableQuietModeUserUnlockedCallback(intentSender);
                        } else {
                            disableQuietModeUserUnlockedCallback = null;
                        }
                        android.app.ActivityManager.getService().startProfileWithListener(i, disableQuietModeUserUnlockedCallback);
                    }
                } catch (android.os.RemoteException e) {
                    e.rethrowAsRuntimeException();
                }
                logQuietModeEnabled(i, z, str);
                if (android.os.Flags.allowPrivateProfile()) {
                    broadcastProfileAvailabilityChanges(userInfoLU, profileParentLU.getUserHandle(), z, false);
                }
                if (userInfoLU.isManagedProfile()) {
                    broadcastProfileAvailabilityChanges(userInfoLU, profileParentLU.getUserHandle(), z, true);
                }
            } finally {
            }
        }
    }

    private void stopUserForQuietMode(int i) throws android.os.RemoteException {
        if (android.os.Flags.allowPrivateProfile() && android.multiuser.Flags.enableBiometricsToUnlockPrivateSpace()) {
            android.app.ActivityManager.getService().stopUserWithDelayedLocking(i, true, (android.app.IStopUserCallback) null);
        } else {
            android.app.ActivityManager.getService().stopUser(i, true, (android.app.IStopUserCallback) null);
        }
    }

    private void logQuietModeEnabled(int i, boolean z, @android.annotation.Nullable java.lang.String str) {
        com.android.server.pm.UserManagerService.UserData userDataLU;
        long j;
        com.android.server.utils.Slogf.i(LOG_TAG, "requestQuietModeEnabled called by package %s, with enableQuietMode %b.", str, java.lang.Boolean.valueOf(z));
        synchronized (this.mUsersLock) {
            userDataLU = getUserDataLU(i);
        }
        if (userDataLU == null) {
            return;
        }
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        if (userDataLU.getLastRequestQuietModeEnabledMillis() != 0) {
            j = currentTimeMillis - userDataLU.getLastRequestQuietModeEnabledMillis();
        } else {
            j = currentTimeMillis - userDataLU.info.creationTime;
        }
        android.app.admin.DevicePolicyEventLogger.createEvent(55).setInt(com.android.server.pm.UserJourneyLogger.getUserTypeForStatsd(userDataLU.info.userType)).setStrings(new java.lang.String[]{str}).setBoolean(z).setTimePeriod(j).write();
        userDataLU.setLastRequestQuietModeEnabledMillis(currentTimeMillis);
    }

    public boolean isQuietModeEnabled(int i) {
        android.content.pm.UserInfo userInfoLU;
        synchronized (this.mPackagesLock) {
            try {
                synchronized (this.mUsersLock) {
                    userInfoLU = getUserInfoLU(i);
                }
                if (userInfoLU == null || !userInfoLU.isProfile()) {
                    return false;
                }
                return userInfoLU.isQuietModeEnabled();
            } finally {
            }
        }
    }

    private void showConfirmCredentialToDisableQuietMode(int i, @android.annotation.Nullable android.content.IntentSender intentSender, @android.annotation.Nullable java.lang.String str) {
        int i2;
        if (android.app.admin.flags.Flags.quietModeCredentialBugFix()) {
            synchronized (this.mUserStates) {
                i2 = this.mUserStates.get(i, -1);
            }
            if (i2 != -1) {
                android.util.Slog.i(LOG_TAG, "showConfirmCredentialToDisableQuietMode() called too early, user " + i + " is still alive.");
                return;
            }
        }
        android.content.Intent createConfirmDeviceCredentialIntent = ((android.app.KeyguardManager) this.mContext.getSystemService("keyguard")).createConfirmDeviceCredentialIntent(null, null, i);
        if (createConfirmDeviceCredentialIntent == null) {
            return;
        }
        android.content.Intent intent = new android.content.Intent("com.android.server.pm.DISABLE_QUIET_MODE_AFTER_UNLOCK");
        if (intentSender != null) {
            intent.putExtra("android.intent.extra.INTENT", intentSender);
        }
        intent.putExtra("android.intent.extra.USER_ID", i);
        intent.setPackage(this.mContext.getPackageName());
        intent.putExtra("android.intent.extra.PACKAGE_NAME", str);
        intent.addFlags(268435456);
        createConfirmDeviceCredentialIntent.putExtra("android.intent.extra.INTENT", android.app.PendingIntent.getBroadcast(this.mContext, 0, intent, 1409286144).getIntentSender());
        createConfirmDeviceCredentialIntent.setFlags(276824064);
        this.mContext.startActivityAsUser(createConfirmDeviceCredentialIntent, android.os.UserHandle.of(getProfileParentIdUnchecked(i)));
    }

    public void setUserEnabled(int i) {
        android.content.pm.UserInfo userInfoLU;
        boolean z;
        checkManageUsersPermission("enable user");
        synchronized (this.mPackagesLock) {
            synchronized (this.mUsersLock) {
                try {
                    userInfoLU = getUserInfoLU(i);
                    if (userInfoLU == null || userInfoLU.isEnabled()) {
                        z = false;
                    } else {
                        userInfoLU.flags ^= 64;
                        writeUserLP(getUserDataLU(userInfoLU.id));
                        z = true;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        if (z && userInfoLU != null && userInfoLU.isProfile()) {
            sendProfileAddedBroadcast(userInfoLU.profileGroupId, userInfoLU.id);
        }
    }

    public void setUserAdmin(int i) {
        android.content.pm.UserInfo userInfoLU;
        checkManageUserAndAcrossUsersFullPermission("set user admin");
        this.mUserJourneyLogger.logUserJourneyBegin(i, 7);
        synchronized (this.mPackagesLock) {
            try {
                synchronized (this.mUsersLock) {
                    userInfoLU = getUserInfoLU(i);
                }
                if (userInfoLU == null) {
                    this.mUserJourneyLogger.logNullUserJourneyError(7, getCurrentUserId(), i, "", -1);
                } else {
                    if (userInfoLU.isAdmin()) {
                        this.mUserJourneyLogger.logUserJourneyFinishWithError(getCurrentUserId(), userInfoLU, 7, 5);
                        return;
                    }
                    userInfoLU.flags ^= 2;
                    writeUserLP(getUserDataLU(userInfoLU.id));
                    this.mUserJourneyLogger.logUserJourneyFinishWithError(getCurrentUserId(), userInfoLU, 7, -1);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void revokeUserAdmin(int i) {
        checkManageUserAndAcrossUsersFullPermission("revoke admin privileges");
        this.mUserJourneyLogger.logUserJourneyBegin(i, 8);
        synchronized (this.mPackagesLock) {
            synchronized (this.mUsersLock) {
                com.android.server.pm.UserManagerService.UserData userDataLU = getUserDataLU(i);
                if (userDataLU == null) {
                    this.mUserJourneyLogger.logNullUserJourneyError(8, getCurrentUserId(), i, "", -1);
                    return;
                }
                if (!userDataLU.info.isAdmin()) {
                    this.mUserJourneyLogger.logUserJourneyFinishWithError(getCurrentUserId(), userDataLU.info, 8, 6);
                    return;
                }
                userDataLU.info.flags ^= 2;
                writeUserLP(userDataLU);
                this.mUserJourneyLogger.logUserJourneyFinishWithError(getCurrentUserId(), userDataLU.info, 8, -1);
            }
        }
    }

    public void evictCredentialEncryptionKey(int i) {
        int i2;
        checkManageUsersPermission("evict CE key");
        android.app.IActivityManager iActivityManager = android.app.ActivityManagerNative.getDefault();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        if (isProfileUnchecked(i)) {
            i2 = 3;
        } else {
            i2 = 2;
        }
        try {
            try {
                iActivityManager.restartUserInBackground(i, i2);
            } catch (android.os.RemoteException e) {
                throw e.rethrowAsRuntimeException();
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isUserOfType(int i, java.lang.String str) {
        checkQueryOrCreateUsersPermission("check user type");
        return str != null && str.equals(getUserTypeNoChecks(i));
    }

    @android.annotation.Nullable
    private java.lang.String getUserTypeNoChecks(int i) {
        java.lang.String str;
        synchronized (this.mUsersLock) {
            try {
                android.content.pm.UserInfo userInfoLU = getUserInfoLU(i);
                str = userInfoLU != null ? userInfoLU.userType : null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.Nullable
    public com.android.server.pm.UserTypeDetails getUserTypeDetailsNoChecks(int i) {
        java.lang.String userTypeNoChecks = getUserTypeNoChecks(i);
        if (userTypeNoChecks != null) {
            return this.mUserTypes.get(userTypeNoChecks);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.Nullable
    public com.android.server.pm.UserTypeDetails getUserTypeDetails(@android.annotation.Nullable android.content.pm.UserInfo userInfo) {
        java.lang.String str = userInfo != null ? userInfo.userType : null;
        if (str != null) {
            return this.mUserTypes.get(str);
        }
        return null;
    }

    public android.content.pm.UserInfo getUserInfo(int i) {
        android.content.pm.UserInfo userWithName;
        checkQueryOrCreateUsersPermission("query user");
        synchronized (this.mUsersLock) {
            userWithName = userWithName(getUserInfoLU(i));
        }
        return userWithName;
    }

    private android.content.pm.UserInfo userWithName(android.content.pm.UserInfo userInfo) {
        java.lang.String str;
        if (userInfo != null && userInfo.name == null) {
            if (userInfo.id == 0) {
                str = getOwnerName();
            } else if (userInfo.isMain()) {
                str = getOwnerName();
            } else if (!userInfo.isGuest()) {
                str = null;
            } else {
                str = getGuestName();
            }
            if (str != null) {
                android.content.pm.UserInfo userInfo2 = new android.content.pm.UserInfo(userInfo);
                userInfo2.name = str;
                return userInfo2;
            }
        }
        return userInfo;
    }

    boolean isUserTypeSubtypeOfFull(java.lang.String str) {
        com.android.server.pm.UserTypeDetails userTypeDetails = this.mUserTypes.get(str);
        return userTypeDetails != null && userTypeDetails.isFull();
    }

    boolean isUserTypeSubtypeOfProfile(java.lang.String str) {
        com.android.server.pm.UserTypeDetails userTypeDetails = this.mUserTypes.get(str);
        return userTypeDetails != null && userTypeDetails.isProfile();
    }

    boolean isUserTypeSubtypeOfSystem(java.lang.String str) {
        com.android.server.pm.UserTypeDetails userTypeDetails = this.mUserTypes.get(str);
        return userTypeDetails != null && userTypeDetails.isSystem();
    }

    @android.annotation.NonNull
    public android.content.pm.UserProperties getUserPropertiesCopy(int i) {
        checkQueryOrInteractPermissionIfCallerInOtherProfileGroup(i, "getUserProperties");
        android.content.pm.UserProperties userPropertiesInternal = getUserPropertiesInternal(i);
        if (userPropertiesInternal != null) {
            return new android.content.pm.UserProperties(userPropertiesInternal, android.os.Binder.getCallingUid() == 1000, hasManageUsersPermission(), hasQueryUsersPermission());
        }
        throw new java.lang.IllegalArgumentException("Cannot access properties for user " + i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.Nullable
    public android.content.pm.UserProperties getUserPropertiesInternal(int i) {
        synchronized (this.mUsersLock) {
            try {
                com.android.server.pm.UserManagerService.UserData userDataLU = getUserDataLU(i);
                if (userDataLU != null) {
                    return userDataLU.userProperties;
                }
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean hasBadge(int i) {
        checkManageOrInteractPermissionIfCallerInOtherProfileGroup(i, "hasBadge");
        com.android.server.pm.UserTypeDetails userTypeDetailsNoChecks = getUserTypeDetailsNoChecks(i);
        return userTypeDetailsNoChecks != null && userTypeDetailsNoChecks.hasBadge();
    }

    public int getUserBadgeLabelResId(int i) {
        checkManageOrInteractPermissionIfCallerInOtherProfileGroup(i, "getUserBadgeLabelResId");
        android.content.pm.UserInfo userInfoNoChecks = getUserInfoNoChecks(i);
        com.android.server.pm.UserTypeDetails userTypeDetails = getUserTypeDetails(userInfoNoChecks);
        if (userInfoNoChecks == null || userTypeDetails == null || !userTypeDetails.hasBadge()) {
            android.util.Slog.e(LOG_TAG, "Requested badge label for non-badged user " + i);
            return 0;
        }
        return userTypeDetails.getBadgeLabel(userInfoNoChecks.profileBadge);
    }

    public int getUserBadgeColorResId(int i) {
        checkManageOrInteractPermissionIfCallerInOtherProfileGroup(i, "getUserBadgeColorResId");
        android.content.pm.UserInfo userInfoNoChecks = getUserInfoNoChecks(i);
        com.android.server.pm.UserTypeDetails userTypeDetails = getUserTypeDetails(userInfoNoChecks);
        if (userInfoNoChecks == null || userTypeDetails == null || !userTypeDetails.hasBadge()) {
            android.util.Slog.e(LOG_TAG, "Requested badge dark color for non-badged user " + i);
            return 0;
        }
        return userTypeDetails.getBadgeColor(userInfoNoChecks.profileBadge);
    }

    public int getUserBadgeDarkColorResId(int i) {
        checkManageOrInteractPermissionIfCallerInOtherProfileGroup(i, "getUserBadgeDarkColorResId");
        android.content.pm.UserInfo userInfoNoChecks = getUserInfoNoChecks(i);
        com.android.server.pm.UserTypeDetails userTypeDetails = getUserTypeDetails(userInfoNoChecks);
        if (userInfoNoChecks == null || userTypeDetails == null || !userTypeDetails.hasBadge()) {
            android.util.Slog.e(LOG_TAG, "Requested badge color for non-badged user " + i);
            return 0;
        }
        return userTypeDetails.getDarkThemeBadgeColor(userInfoNoChecks.profileBadge);
    }

    public int getUserIconBadgeResId(int i) {
        checkManageOrInteractPermissionIfCallerInOtherProfileGroup(i, "getUserIconBadgeResId");
        com.android.server.pm.UserTypeDetails userTypeDetailsNoChecks = getUserTypeDetailsNoChecks(i);
        if (userTypeDetailsNoChecks == null || !userTypeDetailsNoChecks.hasBadge()) {
            android.util.Slog.e(LOG_TAG, "Requested icon badge for non-badged user " + i);
            return 0;
        }
        return userTypeDetailsNoChecks.getIconBadge();
    }

    public int getUserBadgeResId(int i) {
        checkManageOrInteractPermissionIfCallerInOtherProfileGroup(i, "getUserBadgeResId");
        com.android.server.pm.UserTypeDetails userTypeDetailsNoChecks = getUserTypeDetailsNoChecks(i);
        if (userTypeDetailsNoChecks == null || !userTypeDetailsNoChecks.hasBadge()) {
            android.util.Slog.e(LOG_TAG, "Requested badge for non-badged user " + i);
            return 0;
        }
        return userTypeDetailsNoChecks.getBadgePlain();
    }

    public int getUserBadgeNoBackgroundResId(int i) {
        checkManageOrInteractPermissionIfCallerInOtherProfileGroup(i, "getUserBadgeNoBackgroundResId");
        com.android.server.pm.UserTypeDetails userTypeDetailsNoChecks = getUserTypeDetailsNoChecks(i);
        if (userTypeDetailsNoChecks == null || !userTypeDetailsNoChecks.hasBadge()) {
            android.util.Slog.e(LOG_TAG, "Requested badge (no background) for non-badged user " + i);
            return 0;
        }
        return userTypeDetailsNoChecks.getBadgeNoBackground();
    }

    public int getUserStatusBarIconResId(int i) {
        checkManageOrInteractPermissionIfCallerInOtherProfileGroup(i, "getUserStatusBarIconResId");
        com.android.server.pm.UserTypeDetails userTypeDetailsNoChecks = getUserTypeDetailsNoChecks(i);
        if (userTypeDetailsNoChecks == null || !userTypeDetailsNoChecks.hasBadge()) {
            android.util.Slog.w(LOG_TAG, "Requested status bar icon for non-badged user " + i);
            return 0;
        }
        return userTypeDetailsNoChecks.getStatusBarIcon();
    }

    public int getProfileLabelResId(int i) {
        checkQueryOrInteractPermissionIfCallerInOtherProfileGroup(i, "getProfileLabelResId");
        android.content.pm.UserInfo userInfoNoChecks = getUserInfoNoChecks(i);
        com.android.server.pm.UserTypeDetails userTypeDetails = getUserTypeDetails(userInfoNoChecks);
        if (userInfoNoChecks == null || userTypeDetails == null) {
            return 0;
        }
        return userTypeDetails.getLabel(userInfoNoChecks.profileBadge);
    }

    public boolean isProfile(int i) {
        checkQueryOrInteractPermissionIfCallerInOtherProfileGroup(i, "isProfile");
        return isProfileUnchecked(i);
    }

    private boolean isProfileUnchecked(int i) {
        boolean z;
        synchronized (this.mUsersLock) {
            try {
                android.content.pm.UserInfo userInfoLU = getUserInfoLU(i);
                z = userInfoLU != null && userInfoLU.isProfile();
            } finally {
            }
        }
        return z;
    }

    @android.annotation.Nullable
    public java.lang.String getProfileType(int i) {
        checkQueryOrInteractPermissionIfCallerInOtherProfileGroup(i, "getProfileType");
        synchronized (this.mUsersLock) {
            try {
                android.content.pm.UserInfo userInfoLU = getUserInfoLU(i);
                if (userInfoLU != null) {
                    return userInfoLU.isProfile() ? userInfoLU.userType : "";
                }
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean isUserUnlockingOrUnlocked(int i) {
        checkManageOrInteractPermissionIfCallerInOtherProfileGroup(i, "isUserUnlockingOrUnlocked");
        return this.mLocalService.isUserUnlockingOrUnlocked(i);
    }

    public boolean isUserUnlocked(int i) {
        checkManageOrInteractPermissionIfCallerInOtherProfileGroup(i, "isUserUnlocked");
        return this.mLocalService.isUserUnlocked(i);
    }

    public boolean isUserRunning(int i) {
        checkManageOrInteractPermissionIfCallerInOtherProfileGroup(i, "isUserRunning");
        return this.mLocalService.isUserRunning(i);
    }

    public boolean isUserForeground(int i) {
        int callingUserId = android.os.UserHandle.getCallingUserId();
        if (callingUserId == i || hasManageUsersOrPermission("android.permission.INTERACT_ACROSS_USERS")) {
            return i == getCurrentUserId();
        }
        throw new java.lang.SecurityException("Caller from user " + callingUserId + " needs MANAGE_USERS or INTERACT_ACROSS_USERS permission to check if another user (" + i + ") is running in the foreground");
    }

    public boolean isUserVisible(int i) {
        int callingUserId = android.os.UserHandle.getCallingUserId();
        if (callingUserId != i && !hasManageUsersOrPermission("android.permission.INTERACT_ACROSS_USERS")) {
            throw new java.lang.SecurityException("Caller from user " + callingUserId + " needs MANAGE_USERS or INTERACT_ACROSS_USERS permission to check if another user (" + i + ") is visible");
        }
        return this.mUserVisibilityMediator.isUserVisible(i);
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    android.util.Pair<java.lang.Integer, java.lang.Integer> getCurrentAndTargetUserIds() {
        android.app.ActivityManagerInternal activityManagerInternal = getActivityManagerInternal();
        if (activityManagerInternal == null) {
            android.util.Slog.w(LOG_TAG, "getCurrentAndTargetUserId() called too early, ActivityManagerInternal is not set yet");
            return new android.util.Pair<>(java.lang.Integer.valueOf(com.android.server.am.ProcessList.INVALID_ADJ), java.lang.Integer.valueOf(com.android.server.am.ProcessList.INVALID_ADJ));
        }
        return activityManagerInternal.getCurrentAndTargetUserIds();
    }

    @com.android.internal.annotations.VisibleForTesting
    int getCurrentUserId() {
        android.app.ActivityManagerInternal activityManagerInternal = getActivityManagerInternal();
        if (activityManagerInternal == null) {
            android.util.Slog.w(LOG_TAG, "getCurrentUserId() called too early, ActivityManagerInternal is not set yet");
            return com.android.server.am.ProcessList.INVALID_ADJ;
        }
        return activityManagerInternal.getCurrentUserId();
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isCurrentUserOrRunningProfileOfCurrentUser(int i) {
        int currentUserId = getCurrentUserId();
        if (currentUserId == i) {
            return true;
        }
        if (isProfileUnchecked(i) && getProfileParentIdUnchecked(i) == currentUserId) {
            return isUserRunning(i);
        }
        return false;
    }

    boolean isUserVisibleOnDisplay(int i, int i2) {
        return this.mUserVisibilityMediator.isUserVisible(i, i2);
    }

    public int[] getVisibleUsers() {
        if (!hasManageUsersOrPermission("android.permission.INTERACT_ACROSS_USERS")) {
            throw new java.lang.SecurityException("Caller needs MANAGE_USERS or INTERACT_ACROSS_USERS permission to get list of visible users");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mUserVisibilityMediator.getVisibleUsers().toArray();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int getMainDisplayIdAssignedToUser() {
        return this.mUserVisibilityMediator.getMainDisplayAssignedToUser(android.os.UserHandle.getUserId(android.os.Binder.getCallingUid()));
    }

    public boolean isForegroundUserAdmin() {
        synchronized (this.mUsersLock) {
            try {
                int currentUserId = getCurrentUserId();
                boolean z = false;
                if (currentUserId == -10000) {
                    return false;
                }
                android.content.pm.UserInfo userInfoLU = getUserInfoLU(currentUserId);
                if (userInfoLU != null && userInfoLU.isAdmin()) {
                    z = true;
                }
                return z;
            } finally {
            }
        }
    }

    @android.annotation.NonNull
    public java.lang.String getUserName() {
        int callingUid = android.os.Binder.getCallingUid();
        if (!hasQueryOrCreateUsersPermission() && !hasPermissionGranted("android.permission.GET_ACCOUNTS_PRIVILEGED", callingUid)) {
            throw new java.lang.SecurityException("You need MANAGE_USERS, CREATE_USERS, QUERY_USERS, or GET_ACCOUNTS_PRIVILEGED permissions to: get user name");
        }
        int userId = android.os.UserHandle.getUserId(callingUid);
        synchronized (this.mUsersLock) {
            try {
                android.content.pm.UserInfo userWithName = userWithName(getUserInfoLU(userId));
                if (userWithName != null && userWithName.name != null) {
                    return userWithName.name;
                }
                return "";
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public long getUserStartRealtime() {
        int userId = android.os.UserHandle.getUserId(android.os.Binder.getCallingUid());
        synchronized (this.mUsersLock) {
            try {
                com.android.server.pm.UserManagerService.UserData userDataLU = getUserDataLU(userId);
                if (userDataLU == null) {
                    return 0L;
                }
                return userDataLU.startRealtime;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public long getUserUnlockRealtime() {
        synchronized (this.mUsersLock) {
            try {
                com.android.server.pm.UserManagerService.UserData userDataLU = getUserDataLU(android.os.UserHandle.getUserId(android.os.Binder.getCallingUid()));
                if (userDataLU == null) {
                    return 0L;
                }
                return userDataLU.unlockRealtime;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void checkManageOrInteractPermissionIfCallerInOtherProfileGroup(int i, java.lang.String str) {
        int callingUserId = android.os.UserHandle.getCallingUserId();
        if (callingUserId == i || isSameProfileGroupNoChecks(callingUserId, i) || hasManageUsersPermission() || hasPermissionGranted("android.permission.INTERACT_ACROSS_USERS", android.os.Binder.getCallingUid())) {
            return;
        }
        throw new java.lang.SecurityException("You need INTERACT_ACROSS_USERS or MANAGE_USERS permission to: check " + str);
    }

    private void checkQueryOrInteractPermissionIfCallerInOtherProfileGroup(int i, java.lang.String str) {
        int callingUserId = android.os.UserHandle.getCallingUserId();
        if (callingUserId == i || isSameProfileGroupNoChecks(callingUserId, i) || hasQueryUsersPermission() || hasPermissionGranted("android.permission.INTERACT_ACROSS_USERS", android.os.Binder.getCallingUid())) {
            return;
        }
        throw new java.lang.SecurityException("You need INTERACT_ACROSS_USERS, MANAGE_USERS, or QUERY_USERS permission to: check " + str);
    }

    private void checkQueryOrCreateUsersPermissionIfCallerInOtherProfileGroup(int i, java.lang.String str) {
        int callingUserId = android.os.UserHandle.getCallingUserId();
        if (callingUserId == i || isSameProfileGroupNoChecks(callingUserId, i)) {
            return;
        }
        checkQueryOrCreateUsersPermission(str);
    }

    public boolean isDemoUser(int i) {
        if (android.os.UserHandle.getCallingUserId() != i && !hasManageUsersPermission()) {
            throw new java.lang.SecurityException("You need MANAGE_USERS permission to query if u=" + i + " is a demo user");
        }
        boolean z = false;
        if (android.os.SystemProperties.getBoolean("ro.boot.arc_demo_mode", false)) {
            return true;
        }
        synchronized (this.mUsersLock) {
            try {
                android.content.pm.UserInfo userInfoLU = getUserInfoLU(i);
                if (userInfoLU != null && userInfoLU.isDemo()) {
                    z = true;
                }
            } finally {
            }
        }
        return z;
    }

    public boolean isAdminUser(int i) {
        boolean z;
        checkQueryOrCreateUsersPermissionIfCallerInOtherProfileGroup(i, "isAdminUser");
        synchronized (this.mUsersLock) {
            try {
                android.content.pm.UserInfo userInfoLU = getUserInfoLU(i);
                z = userInfoLU != null && userInfoLU.isAdmin();
            } finally {
            }
        }
        return z;
    }

    public boolean isPreCreated(int i) {
        boolean z;
        checkManageOrInteractPermissionIfCallerInOtherProfileGroup(i, "isPreCreated");
        synchronized (this.mUsersLock) {
            try {
                android.content.pm.UserInfo userInfoLU = getUserInfoLU(i);
                z = userInfoLU != null && userInfoLU.preCreated;
            } finally {
            }
        }
        return z;
    }

    /* JADX WARN: Finally extract failed */
    public int getUserSwitchability(int i) {
        int i2;
        checkManageOrInteractPermissionIfCallerInOtherProfileGroup(i, "getUserSwitchability");
        com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog();
        timingsTraceAndSlog.traceBegin("getUserSwitchability-" + i);
        timingsTraceAndSlog.traceBegin("TM.isInCall");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.telecom.TelecomManager telecomManager = (android.telecom.TelecomManager) this.mContext.getSystemService(android.telecom.TelecomManager.class);
            if (com.android.internal.telephony.flags.Flags.enforceTelephonyFeatureMappingForPublicApis()) {
                if (this.mContext.getPackageManager().hasSystemFeature("android.software.telecom") && telecomManager != null && telecomManager.isInCall()) {
                    i2 = 1;
                }
                i2 = 0;
            } else {
                if (telecomManager != null && telecomManager.isInCall()) {
                    i2 = 1;
                }
                i2 = 0;
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("hasUserRestriction-DISALLOW_USER_SWITCH");
            if (this.mLocalService.hasUserRestriction("no_user_switch", i)) {
                i2 |= 2;
            }
            timingsTraceAndSlog.traceEnd();
            if (!isHeadlessSystemUserMode()) {
                timingsTraceAndSlog.traceBegin("getInt-ALLOW_USER_SWITCHING_WHEN_SYSTEM_USER_LOCKED");
                boolean z = android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "allow_user_switching_when_system_user_locked", 0) != 0;
                timingsTraceAndSlog.traceEnd();
                timingsTraceAndSlog.traceBegin("isUserUnlocked-USER_SYSTEM");
                boolean isUserUnlocked = this.mLocalService.isUserUnlocked(0);
                timingsTraceAndSlog.traceEnd();
                if (!z && !isUserUnlocked) {
                    i2 |= 4;
                }
            }
            timingsTraceAndSlog.traceEnd();
            return i2;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isUserSwitcherEnabled(int i) {
        return android.os.UserManager.supportsMultipleUsers() && !hasUserRestriction("no_user_switch", i) && !android.os.UserManager.isDeviceInDemoMode(this.mContext) && (android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "user_switcher_enabled", android.content.res.Resources.getSystem().getBoolean(android.R.bool.config_showNavigationBar) ? 1 : 0) != 0);
    }

    public boolean isUserSwitcherEnabled(boolean z, int i) {
        if (isUserSwitcherEnabled(i)) {
            return z || !hasUserRestriction("no_add_user", i) || areThereMultipleSwitchableUsers();
        }
        return false;
    }

    private boolean areThereMultipleSwitchableUsers() {
        java.util.Iterator<android.content.pm.UserInfo> it = getUsers(true, true, true).iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (it.next().supportsSwitchToByUser()) {
                if (z) {
                    return true;
                }
                z = true;
            }
        }
        return false;
    }

    public boolean isRestricted(int i) {
        boolean isRestricted;
        if (i != android.os.UserHandle.getCallingUserId()) {
            checkQueryOrCreateUsersPermission("query isRestricted for user " + i);
        }
        synchronized (this.mUsersLock) {
            android.content.pm.UserInfo userInfoLU = getUserInfoLU(i);
            isRestricted = userInfoLU == null ? false : userInfoLU.isRestricted();
        }
        return isRestricted;
    }

    public boolean canHaveRestrictedProfile(int i) {
        checkManageUsersPermission("canHaveRestrictedProfile");
        synchronized (this.mUsersLock) {
            try {
                android.content.pm.UserInfo userInfoLU = getUserInfoLU(i);
                boolean z = false;
                if (userInfoLU == null || !userInfoLU.canHaveProfile()) {
                    return false;
                }
                if (!userInfoLU.isAdmin()) {
                    return false;
                }
                if (!this.mIsDeviceManaged && !this.mIsUserManaged.get(i)) {
                    z = true;
                }
                return z;
            } finally {
            }
        }
    }

    public boolean hasRestrictedProfiles(int i) {
        checkManageUsersPermission("hasRestrictedProfiles");
        synchronized (this.mUsersLock) {
            try {
                int size = this.mUsers.size();
                for (int i2 = 0; i2 < size; i2++) {
                    android.content.pm.UserInfo userInfo = this.mUsers.valueAt(i2).info;
                    if (i != userInfo.id && userInfo.restrictedProfileParentId == i) {
                        return true;
                    }
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mUsersLock"})
    public android.content.pm.UserInfo getUserInfoLU(int i) {
        com.android.server.pm.UserManagerService.UserData userData = this.mUsers.get(i);
        if (userData != null && userData.info.partial && !this.mRemovingUserIds.get(i)) {
            android.util.Slog.w(LOG_TAG, "getUserInfo: unknown user #" + i);
            return null;
        }
        if (userData != null) {
            return userData.info;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mUsersLock"})
    public com.android.server.pm.UserManagerService.UserData getUserDataLU(int i) {
        com.android.server.pm.UserManagerService.UserData userData = this.mUsers.get(i);
        if (userData != null && userData.info.partial && !this.mRemovingUserIds.get(i)) {
            return null;
        }
        return userData;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.content.pm.UserInfo getUserInfoNoChecks(int i) {
        android.content.pm.UserInfo userInfo;
        synchronized (this.mUsersLock) {
            try {
                com.android.server.pm.UserManagerService.UserData userData = this.mUsers.get(i);
                userInfo = userData != null ? userData.info : null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return userInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.pm.UserManagerService.UserData getUserDataNoChecks(int i) {
        com.android.server.pm.UserManagerService.UserData userData;
        synchronized (this.mUsersLock) {
            userData = this.mUsers.get(i);
        }
        return userData;
    }

    public boolean exists(int i) {
        return this.mLocalService.exists(i);
    }

    private int getCrossProfileIntentFilterAccessControl(int i) {
        android.content.pm.UserProperties userPropertiesInternal = getUserPropertiesInternal(i);
        if (userPropertiesInternal != null) {
            return userPropertiesInternal.getCrossProfileIntentFilterAccessControl();
        }
        return 0;
    }

    public void enforceCrossProfileIntentFilterAccess(int i, int i2, int i3, boolean z) {
        if (!isCrossProfileIntentFilterAccessible(i, i2, z)) {
            throw new java.lang.SecurityException("CrossProfileIntentFilter cannot be accessed by user " + i3);
        }
    }

    public boolean isCrossProfileIntentFilterAccessible(int i, int i2, boolean z) {
        int crossProfileIntentFilterAccessControl = getCrossProfileIntentFilterAccessControl(i, i2);
        if (10 == crossProfileIntentFilterAccessControl && !com.android.server.pm.PackageManagerServiceUtils.isSystemOrRoot()) {
            return false;
        }
        if (20 == crossProfileIntentFilterAccessControl) {
            return z && com.android.server.pm.PackageManagerServiceUtils.isSystemOrRoot();
        }
        return true;
    }

    public int getCrossProfileIntentFilterAccessControl(int i, int i2) {
        return java.lang.Math.max(getCrossProfileIntentFilterAccessControl(i), getCrossProfileIntentFilterAccessControl(i2));
    }

    public void setUserName(int i, java.lang.String str) {
        checkManageUsersPermission("rename users");
        synchronized (this.mPackagesLock) {
            try {
                com.android.server.pm.UserManagerService.UserData userDataNoChecks = getUserDataNoChecks(i);
                if (userDataNoChecks == null || userDataNoChecks.info.partial) {
                    com.android.server.utils.Slogf.w(LOG_TAG, "setUserName: unknown user #%d", java.lang.Integer.valueOf(i));
                    return;
                }
                if (java.util.Objects.equals(str, userDataNoChecks.info.name)) {
                    com.android.server.utils.Slogf.i(LOG_TAG, "setUserName: ignoring for user #%d as it didn't change (%s)", java.lang.Integer.valueOf(i), getRedacted(str));
                    return;
                }
                if (str == null) {
                    com.android.server.utils.Slogf.i(LOG_TAG, "setUserName: resetting name of user #%d", java.lang.Integer.valueOf(i));
                } else {
                    com.android.server.utils.Slogf.i(LOG_TAG, "setUserName: setting name of user #%d to %s", java.lang.Integer.valueOf(i), getRedacted(str));
                }
                userDataNoChecks.info.name = str;
                writeUserLP(userDataNoChecks);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    sendUserInfoChangedBroadcast(i);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean setUserEphemeral(int i, boolean z) {
        checkCreateUsersPermission("update ephemeral user flag");
        if (z) {
            return android.os.UserManager.isRemoveResultSuccessful(setUserEphemeralUnchecked(i));
        }
        return setUserNonEphemeralUnchecked(i);
    }

    private boolean setUserNonEphemeralUnchecked(int i) {
        synchronized (this.mPackagesLock) {
            synchronized (this.mUsersLock) {
                com.android.server.pm.UserManagerService.UserData userData = this.mUsers.get(i);
                if (userData == null) {
                    android.util.Slog.e(LOG_TAG, android.text.TextUtils.formatSimple("Cannot set user %d non-ephemeral, invalid user id provided.", new java.lang.Object[]{java.lang.Integer.valueOf(i)}));
                    return false;
                }
                if (!userData.info.isEphemeral()) {
                    return true;
                }
                if ((userData.info.flags & 8192) != 0) {
                    android.util.Slog.e(LOG_TAG, android.text.TextUtils.formatSimple("User %d can not be changed to non-ephemeral because it was set ephemeral on create.", new java.lang.Object[]{java.lang.Integer.valueOf(i)}));
                    return false;
                }
                userData.info.flags &= -257;
                writeUserLP(userData);
                return true;
            }
        }
    }

    private int setUserEphemeralUnchecked(int i) {
        synchronized (this.mPackagesLock) {
            synchronized (this.mUsersLock) {
                int userRemovabilityLocked = getUserRemovabilityLocked(i, "set as ephemeral");
                if (userRemovabilityLocked != 3) {
                    return userRemovabilityLocked;
                }
                com.android.server.pm.UserManagerService.UserData userData = this.mUsers.get(i);
                userData.info.flags |= 256;
                writeUserLP(userData);
                android.util.Slog.i(LOG_TAG, android.text.TextUtils.formatSimple("User %d is set ephemeral and will be removed on user switch or reboot.", new java.lang.Object[]{java.lang.Integer.valueOf(i)}));
                return 1;
            }
        }
    }

    public void setUserIcon(int i, android.graphics.Bitmap bitmap) {
        try {
            checkManageUsersPermission("update users");
            enforceUserRestriction("no_set_user_icon", i, "Cannot set user icon");
            this.mLocalService.setUserIcon(i, bitmap);
        } catch (android.os.UserManager.CheckedUserOperationException e) {
            throw e.toServiceSpecificException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendUserInfoChangedBroadcast(int i) {
        android.content.Intent intent = new android.content.Intent("android.intent.action.USER_INFO_CHANGED");
        intent.putExtra("android.intent.extra.user_handle", i);
        intent.addFlags(1073741824);
        this.mContext.sendBroadcastAsUser(intent, android.os.UserHandle.ALL);
    }

    public android.os.ParcelFileDescriptor getUserIcon(int i) {
        if (!hasManageUsersOrPermission("android.permission.GET_ACCOUNTS_PRIVILEGED")) {
            throw new java.lang.SecurityException("You need MANAGE_USERS or GET_ACCOUNTS_PRIVILEGED permissions to: get user icon");
        }
        synchronized (this.mPackagesLock) {
            try {
                android.content.pm.UserInfo userInfoNoChecks = getUserInfoNoChecks(i);
                if (userInfoNoChecks == null || userInfoNoChecks.partial) {
                    android.util.Slog.w(LOG_TAG, "getUserIcon: unknown user #" + i);
                    return null;
                }
                if (!isSameUserOrProfileGroupOrTargetIsCommunal(getUserInfoNoChecks(android.os.UserHandle.getCallingUserId()), userInfoNoChecks)) {
                    checkManageUsersPermission("get the icon of a user who is not related");
                }
                if (userInfoNoChecks.iconPath == null) {
                    return null;
                }
                java.lang.String str = userInfoNoChecks.iconPath;
                try {
                    return android.os.ParcelFileDescriptor.open(new java.io.File(str), 268435456);
                } catch (java.io.FileNotFoundException e) {
                    android.util.Slog.e(LOG_TAG, "Couldn't find icon file", e);
                    return null;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void makeInitialized(int i) {
        boolean z;
        checkManageUsersPermission("makeInitialized");
        synchronized (this.mUsersLock) {
            try {
                com.android.server.pm.UserManagerService.UserData userData = this.mUsers.get(i);
                if (userData == null || userData.info.partial) {
                    android.util.Slog.w(LOG_TAG, "makeInitialized: unknown user #" + i);
                    return;
                }
                if ((userData.info.flags & 16) != 0) {
                    z = false;
                } else {
                    userData.info.flags |= 16;
                    z = true;
                }
                if (z) {
                    scheduleWriteUser(i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void initDefaultGuestRestrictions() {
        synchronized (this.mGuestRestrictions) {
            try {
                if (this.mGuestRestrictions.isEmpty()) {
                    com.android.server.pm.UserTypeDetails userTypeDetails = this.mUserTypes.get("android.os.usertype.full.GUEST");
                    if (userTypeDetails == null) {
                        android.util.Slog.wtf(LOG_TAG, "Can't set default guest restrictions: type doesn't exist.");
                        return;
                    }
                    userTypeDetails.addDefaultRestrictionsTo(this.mGuestRestrictions);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public android.os.Bundle getDefaultGuestRestrictions() {
        android.os.Bundle bundle;
        checkManageUsersPermission("getDefaultGuestRestrictions");
        synchronized (this.mGuestRestrictions) {
            bundle = new android.os.Bundle(this.mGuestRestrictions);
        }
        return bundle;
    }

    public void setDefaultGuestRestrictions(android.os.Bundle bundle) {
        checkManageUsersPermission("setDefaultGuestRestrictions");
        java.util.List<android.content.pm.UserInfo> guestUsers = getGuestUsers();
        synchronized (this.mRestrictionsLock) {
            for (int i = 0; i < guestUsers.size(); i++) {
                try {
                    updateUserRestrictionsInternalLR(bundle, guestUsers.get(i).id);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        synchronized (this.mGuestRestrictions) {
            this.mGuestRestrictions.clear();
            this.mGuestRestrictions.putAll(bundle);
        }
        synchronized (this.mPackagesLock) {
            writeUserListLP();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void setUserRestrictionInner(int i, @android.annotation.NonNull java.lang.String str, boolean z) {
        if (!com.android.server.pm.UserRestrictionsUtils.isValidRestriction(str)) {
            android.util.Slog.e(LOG_TAG, "Setting invalid restriction " + str);
            return;
        }
        synchronized (this.mRestrictionsLock) {
            try {
                android.os.Bundle clone = com.android.server.BundleUtils.clone(this.mDevicePolicyUserRestrictions.getRestrictions(i));
                clone.putBoolean(str, z);
                if (this.mDevicePolicyUserRestrictions.updateRestrictions(i, clone)) {
                    if (i == -1) {
                        applyUserRestrictionsForAllUsersLR();
                    } else {
                        applyUserRestrictionsLR(i);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDevicePolicyUserRestrictionsInner(int i, @android.annotation.NonNull android.os.Bundle bundle, @android.annotation.NonNull com.android.server.pm.RestrictionsSet restrictionsSet, boolean z) {
        synchronized (this.mRestrictionsLock) {
            try {
                android.util.IntArray userIds = this.mDevicePolicyUserRestrictions.getUserIds();
                this.mCachedEffectiveUserRestrictions.removeAllRestrictions();
                this.mDevicePolicyUserRestrictions.removeAllRestrictions();
                this.mDevicePolicyUserRestrictions.updateRestrictions(-1, bundle);
                android.util.IntArray userIds2 = restrictionsSet.getUserIds();
                for (int i2 = 0; i2 < userIds2.size(); i2++) {
                    int i3 = userIds2.get(i2);
                    this.mDevicePolicyUserRestrictions.updateRestrictions(i3, restrictionsSet.getRestrictions(i3));
                    userIds.add(i3);
                }
                applyUserRestrictionsForAllUsersLR();
                for (int i4 = 0; i4 < userIds.size(); i4++) {
                    if (userIds.get(i4) != -1) {
                        applyUserRestrictionsLR(userIds.get(i4));
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mRestrictionsLock"})
    private android.os.Bundle computeEffectiveUserRestrictionsLR(int i) {
        android.os.Bundle restrictionsNonNull = this.mBaseUserRestrictions.getRestrictionsNonNull(i);
        android.os.Bundle restrictionsNonNull2 = this.mDevicePolicyUserRestrictions.getRestrictionsNonNull(-1);
        android.os.Bundle restrictionsNonNull3 = this.mDevicePolicyUserRestrictions.getRestrictionsNonNull(i);
        if (restrictionsNonNull2.isEmpty() && restrictionsNonNull3.isEmpty()) {
            return restrictionsNonNull;
        }
        android.os.Bundle clone = com.android.server.BundleUtils.clone(restrictionsNonNull);
        com.android.server.pm.UserRestrictionsUtils.merge(clone, restrictionsNonNull2);
        com.android.server.pm.UserRestrictionsUtils.merge(clone, restrictionsNonNull3);
        return clone;
    }

    @com.android.internal.annotations.GuardedBy({"mRestrictionsLock"})
    private void invalidateEffectiveUserRestrictionsLR(int i) {
        this.mCachedEffectiveUserRestrictions.remove(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.os.Bundle getEffectiveUserRestrictions(int i) {
        android.os.Bundle restrictions;
        synchronized (this.mRestrictionsLock) {
            try {
                restrictions = this.mCachedEffectiveUserRestrictions.getRestrictions(i);
                if (restrictions == null) {
                    restrictions = computeEffectiveUserRestrictionsLR(i);
                    this.mCachedEffectiveUserRestrictions.updateRestrictions(i, restrictions);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return restrictions;
    }

    public boolean hasUserRestriction(java.lang.String str, int i) {
        if (!userExists(i)) {
            return false;
        }
        checkManageOrInteractPermissionIfCallerInOtherProfileGroup(i, "hasUserRestriction");
        return this.mLocalService.hasUserRestriction(str, i);
    }

    public boolean hasUserRestrictionOnAnyUser(java.lang.String str) {
        if (!com.android.server.pm.UserRestrictionsUtils.isValidRestriction(str)) {
            return false;
        }
        java.util.List<android.content.pm.UserInfo> users = getUsers(true);
        for (int i = 0; i < users.size(); i++) {
            android.os.Bundle effectiveUserRestrictions = getEffectiveUserRestrictions(users.get(i).id);
            if (effectiveUserRestrictions != null && effectiveUserRestrictions.getBoolean(str)) {
                return true;
            }
        }
        return false;
    }

    public boolean isSettingRestrictedForUser(java.lang.String str, int i, java.lang.String str2, int i2) {
        if (android.os.Binder.getCallingUid() != 1000) {
            throw new java.lang.SecurityException("Non-system caller");
        }
        return com.android.server.pm.UserRestrictionsUtils.isSettingRestrictedForUser(this.mContext, str, i, str2, i2);
    }

    public void addUserRestrictionsListener(final android.os.IUserRestrictionsListener iUserRestrictionsListener) {
        if (android.os.Binder.getCallingUid() != 1000) {
            throw new java.lang.SecurityException("Non-system caller");
        }
        this.mLocalService.addUserRestrictionsListener(new com.android.server.pm.UserManagerInternal.UserRestrictionsListener() { // from class: com.android.server.pm.UserManagerService$$ExternalSyntheticLambda10
            @Override // com.android.server.pm.UserManagerInternal.UserRestrictionsListener
            public final void onUserRestrictionsChanged(int i, android.os.Bundle bundle, android.os.Bundle bundle2) {
                com.android.server.pm.UserManagerService.lambda$addUserRestrictionsListener$3(iUserRestrictionsListener, i, bundle, bundle2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$addUserRestrictionsListener$3(android.os.IUserRestrictionsListener iUserRestrictionsListener, int i, android.os.Bundle bundle, android.os.Bundle bundle2) {
        try {
            iUserRestrictionsListener.onUserRestrictionsChanged(i, bundle, bundle2);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e("IUserRestrictionsListener", "Unable to invoke listener: " + e.getMessage());
        }
    }

    public int getUserRestrictionSource(java.lang.String str, int i) {
        java.util.List<android.os.UserManager.EnforcingUser> userRestrictionSources = getUserRestrictionSources(str, i);
        int i2 = 0;
        for (int size = userRestrictionSources.size() - 1; size >= 0; size--) {
            i2 |= userRestrictionSources.get(size).getUserRestrictionSource();
        }
        return i2;
    }

    public java.util.List<android.os.UserManager.EnforcingUser> getUserRestrictionSources(java.lang.String str, int i) {
        checkQueryUsersPermission("call getUserRestrictionSources.");
        if (!hasUserRestriction(str, i)) {
            return java.util.Collections.emptyList();
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (hasBaseUserRestriction(str, i)) {
            arrayList.add(new android.os.UserManager.EnforcingUser(com.android.server.am.ProcessList.INVALID_ADJ, 1));
        }
        android.app.admin.DevicePolicyManagerInternal devicePolicyManagerInternal = getDevicePolicyManagerInternal();
        if (devicePolicyManagerInternal != null) {
            arrayList.addAll(devicePolicyManagerInternal.getUserRestrictionSources(str, i));
        }
        return arrayList;
    }

    public android.os.Bundle getUserRestrictions(int i) {
        checkManageOrInteractPermissionIfCallerInOtherProfileGroup(i, "getUserRestrictions");
        return com.android.server.BundleUtils.clone(getEffectiveUserRestrictions(i));
    }

    public boolean hasBaseUserRestriction(java.lang.String str, int i) {
        checkCreateUsersPermission("hasBaseUserRestriction");
        boolean z = false;
        if (!com.android.server.pm.UserRestrictionsUtils.isValidRestriction(str)) {
            return false;
        }
        synchronized (this.mRestrictionsLock) {
            try {
                android.os.Bundle restrictions = this.mBaseUserRestrictions.getRestrictions(i);
                if (restrictions != null && restrictions.getBoolean(str, false)) {
                    z = true;
                }
            } finally {
            }
        }
        return z;
    }

    public void setUserRestriction(java.lang.String str, boolean z, int i) {
        checkManageUsersPermission("setUserRestriction");
        if (!com.android.server.pm.UserRestrictionsUtils.isValidRestriction(str)) {
            return;
        }
        if (!userExists(i)) {
            com.android.server.utils.Slogf.w(LOG_TAG, "Cannot set user restriction %s. User with id %d does not exist", str, java.lang.Integer.valueOf(i));
            return;
        }
        synchronized (this.mRestrictionsLock) {
            android.os.Bundle clone = com.android.server.BundleUtils.clone(this.mBaseUserRestrictions.getRestrictions(i));
            clone.putBoolean(str, z);
            updateUserRestrictionsInternalLR(clone, i);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mRestrictionsLock"})
    private void updateUserRestrictionsInternalLR(@android.annotation.Nullable android.os.Bundle bundle, final int i) {
        android.os.Bundle nonNull = com.android.server.pm.UserRestrictionsUtils.nonNull(this.mAppliedUserRestrictions.getRestrictions(i));
        if (bundle != null) {
            com.android.internal.util.Preconditions.checkState(this.mBaseUserRestrictions.getRestrictions(i) != bundle);
            com.android.internal.util.Preconditions.checkState(this.mCachedEffectiveUserRestrictions.getRestrictions(i) != bundle);
            if (this.mBaseUserRestrictions.updateRestrictions(i, new android.os.Bundle(bundle))) {
                scheduleWriteUser(i);
            }
        }
        final android.os.Bundle computeEffectiveUserRestrictionsLR = computeEffectiveUserRestrictionsLR(i);
        this.mCachedEffectiveUserRestrictions.updateRestrictions(i, new android.os.Bundle(computeEffectiveUserRestrictionsLR));
        if (this.mAppOpsService != null) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.UserManagerService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.pm.UserManagerService.this.lambda$updateUserRestrictionsInternalLR$4(computeEffectiveUserRestrictionsLR, i);
                }
            });
        }
        propagateUserRestrictionsLR(i, computeEffectiveUserRestrictionsLR, nonNull);
        this.mAppliedUserRestrictions.updateRestrictions(i, new android.os.Bundle(computeEffectiveUserRestrictionsLR));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateUserRestrictionsInternalLR$4(android.os.Bundle bundle, int i) {
        try {
            this.mAppOpsService.setUserRestrictions(bundle, this.mUserRestrictionToken, i);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(LOG_TAG, "Unable to notify AppOpsService of UserRestrictions");
        }
    }

    @com.android.internal.annotations.GuardedBy({"mRestrictionsLock"})
    private void propagateUserRestrictionsLR(final int i, android.os.Bundle bundle, android.os.Bundle bundle2) {
        if (com.android.server.pm.UserRestrictionsUtils.areEqual(bundle, bundle2)) {
            return;
        }
        final android.os.Bundle bundle3 = new android.os.Bundle(bundle);
        final android.os.Bundle bundle4 = new android.os.Bundle(bundle2);
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.UserManagerService.4
            @Override // java.lang.Runnable
            public void run() {
                int size;
                com.android.server.pm.UserManagerInternal.UserRestrictionsListener[] userRestrictionsListenerArr;
                com.android.server.pm.UserRestrictionsUtils.applyUserRestrictions(com.android.server.pm.UserManagerService.this.mContext, i, bundle3, bundle4);
                synchronized (com.android.server.pm.UserManagerService.this.mUserRestrictionsListeners) {
                    size = com.android.server.pm.UserManagerService.this.mUserRestrictionsListeners.size();
                    userRestrictionsListenerArr = new com.android.server.pm.UserManagerInternal.UserRestrictionsListener[size];
                    com.android.server.pm.UserManagerService.this.mUserRestrictionsListeners.toArray(userRestrictionsListenerArr);
                }
                for (int i2 = 0; i2 < size; i2++) {
                    userRestrictionsListenerArr[i2].onUserRestrictionsChanged(i, bundle3, bundle4);
                }
                com.android.server.pm.UserManagerService.this.mContext.sendBroadcastAsUser(new android.content.Intent("android.os.action.USER_RESTRICTIONS_CHANGED").setFlags(1073741824), android.os.UserHandle.of(i), null, android.app.BroadcastOptions.makeBasic().setDeliveryGroupPolicy(1).toBundle());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mRestrictionsLock"})
    public void applyUserRestrictionsLR(int i) {
        updateUserRestrictionsInternalLR(null, i);
        scheduleWriteUser(i);
    }

    @com.android.internal.annotations.GuardedBy({"mRestrictionsLock"})
    private void applyUserRestrictionsForAllUsersLR() {
        this.mCachedEffectiveUserRestrictions.removeAllRestrictions();
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.UserManagerService.5
            @Override // java.lang.Runnable
            public void run() {
                try {
                    int[] runningUserIds = android.app.ActivityManager.getService().getRunningUserIds();
                    synchronized (com.android.server.pm.UserManagerService.this.mRestrictionsLock) {
                        for (int i : runningUserIds) {
                            try {
                                com.android.server.pm.UserManagerService.this.applyUserRestrictionsLR(i);
                            } catch (java.lang.Throwable th) {
                                throw th;
                            }
                        }
                    }
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(com.android.server.pm.UserManagerService.LOG_TAG, "Unable to access ActivityManagerService");
                }
            }
        });
    }

    private boolean isUserLimitReached() {
        int aliveUsersExcludingGuestsCountLU;
        synchronized (this.mUsersLock) {
            aliveUsersExcludingGuestsCountLU = getAliveUsersExcludingGuestsCountLU();
        }
        return aliveUsersExcludingGuestsCountLU >= android.os.UserManager.getMaxSupportedUsers() && !isCreationOverrideEnabled();
    }

    private boolean canAddMoreUsersOfType(@android.annotation.NonNull com.android.server.pm.UserTypeDetails userTypeDetails) {
        if (!isUserTypeEnabled(userTypeDetails)) {
            return false;
        }
        int maxAllowed = userTypeDetails.getMaxAllowed();
        if (maxAllowed == -1) {
            return true;
        }
        return getNumberOfUsersOfType(userTypeDetails.getName()) < maxAllowed || isCreationOverrideEnabled();
    }

    public int getRemainingCreatableUserCount(java.lang.String str) {
        int i;
        boolean z;
        boolean z2;
        checkQueryOrCreateUsersPermission("get the remaining number of users that can be added.");
        com.android.server.pm.UserTypeDetails userTypeDetails = this.mUserTypes.get(str);
        if (userTypeDetails == null || !isUserTypeEnabled(userTypeDetails)) {
            return 0;
        }
        synchronized (this.mUsersLock) {
            try {
                int aliveUsersExcludingGuestsCountLU = getAliveUsersExcludingGuestsCountLU();
                int i2 = Integer.MAX_VALUE;
                if (android.os.UserManager.isUserTypeGuest(str) || android.os.UserManager.isUserTypeDemo(str)) {
                    i = Integer.MAX_VALUE;
                } else {
                    i = android.os.UserManager.getMaxSupportedUsers() - aliveUsersExcludingGuestsCountLU;
                }
                if (userTypeDetails.isManagedProfile()) {
                    if (!this.mContext.getPackageManager().hasSystemFeature("android.software.managed_users")) {
                        return 0;
                    }
                    if (i > 0) {
                        z = false;
                    } else {
                        z = true;
                    }
                    if (aliveUsersExcludingGuestsCountLU != 1) {
                        z2 = false;
                    } else {
                        z2 = true;
                    }
                    if (z2 & z) {
                        i = 1;
                    }
                }
                if (i <= 0) {
                    return 0;
                }
                if (userTypeDetails.getMaxAllowed() != -1) {
                    i2 = userTypeDetails.getMaxAllowed() - getNumberOfUsersOfType(str);
                }
                return java.lang.Math.max(0, java.lang.Math.min(i, i2));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private int getNumberOfUsersOfType(java.lang.String str) {
        int i;
        synchronized (this.mUsersLock) {
            try {
                int size = this.mUsers.size();
                i = 0;
                for (int i2 = 0; i2 < size; i2++) {
                    android.content.pm.UserInfo userInfo = this.mUsers.valueAt(i2).info;
                    if (userInfo.userType.equals(str) && !userInfo.guestToRemove && !this.mRemovingUserIds.get(userInfo.id) && !userInfo.preCreated) {
                        i++;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return i;
    }

    public boolean canAddMoreUsersOfType(java.lang.String str) {
        checkCreateUsersPermission("check if more users can be added.");
        com.android.server.pm.UserTypeDetails userTypeDetails = this.mUserTypes.get(str);
        return userTypeDetails != null && canAddMoreUsersOfType(userTypeDetails);
    }

    public boolean isUserTypeEnabled(java.lang.String str) {
        checkCreateUsersPermission("check if user type is enabled.");
        com.android.server.pm.UserTypeDetails userTypeDetails = this.mUserTypes.get(str);
        return userTypeDetails != null && isUserTypeEnabled(userTypeDetails);
    }

    private boolean isUserTypeEnabled(@android.annotation.NonNull com.android.server.pm.UserTypeDetails userTypeDetails) {
        return userTypeDetails.isEnabled() || isCreationOverrideEnabled();
    }

    private boolean isCreationOverrideEnabled() {
        return android.os.Build.isDebuggable() && android.os.SystemProperties.getBoolean("debug.user.creation_override", false);
    }

    public boolean canAddMoreManagedProfiles(int i, boolean z) {
        return canAddMoreProfilesToUser("android.os.usertype.profile.MANAGED", i, z);
    }

    public boolean canAddMoreProfilesToUser(java.lang.String str, int i, boolean z) {
        return getRemainingCreatableProfileCount(str, i, z) > 0 || isCreationOverrideEnabled();
    }

    public int getRemainingCreatableProfileCount(@android.annotation.NonNull java.lang.String str, int i) {
        return getRemainingCreatableProfileCount(str, i, false);
    }

    private int getRemainingCreatableProfileCount(@android.annotation.NonNull java.lang.String str, int i, boolean z) {
        int i2;
        checkQueryOrCreateUsersPermission("get the remaining number of profiles that can be added to the given user.");
        com.android.server.pm.UserTypeDetails userTypeDetails = this.mUserTypes.get(str);
        if (userTypeDetails == null || !isUserTypeEnabled(userTypeDetails)) {
            return 0;
        }
        boolean isManagedProfile = userTypeDetails.isManagedProfile();
        if (isManagedProfile && !this.mContext.getPackageManager().hasSystemFeature("android.software.managed_users")) {
            return 0;
        }
        synchronized (this.mUsersLock) {
            try {
                android.content.pm.UserInfo userInfoLU = getUserInfoLU(i);
                if (userInfoLU == null || !userInfoLU.canHaveProfile()) {
                    return 0;
                }
                int length = getProfileIds(i, str, false, false).length;
                int i3 = 1;
                if (length <= 0 || !z) {
                    i2 = 0;
                } else {
                    i2 = 1;
                }
                int aliveUsersExcludingGuestsCountLU = getAliveUsersExcludingGuestsCountLU() - i2;
                int maxSupportedUsers = android.os.UserManager.getMaxSupportedUsers() - aliveUsersExcludingGuestsCountLU;
                if (maxSupportedUsers > 0 || !isManagedProfile || aliveUsersExcludingGuestsCountLU != 1) {
                    i3 = maxSupportedUsers;
                }
                int maxUsersOfTypePerParent = getMaxUsersOfTypePerParent(userTypeDetails);
                if (maxUsersOfTypePerParent != -1) {
                    i3 = java.lang.Math.min(i3, maxUsersOfTypePerParent - (length - i2));
                }
                if (i3 <= 0) {
                    return 0;
                }
                if (userTypeDetails.getMaxAllowed() != -1) {
                    i3 = java.lang.Math.min(i3, userTypeDetails.getMaxAllowed() - (getNumberOfUsersOfType(str) - i2));
                }
                return java.lang.Math.max(0, i3);
            } finally {
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUsersLock"})
    private int getAliveUsersExcludingGuestsCountLU() {
        int size = this.mUsers.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            android.content.pm.UserInfo userInfo = this.mUsers.valueAt(i2).info;
            if (!this.mRemovingUserIds.get(userInfo.id) && !userInfo.isGuest() && !userInfo.preCreated) {
                i++;
            }
        }
        return i;
    }

    private static final void checkManageUserAndAcrossUsersFullPermission(java.lang.String str) {
        int callingUid = android.os.Binder.getCallingUid();
        if (callingUid == 1000 || callingUid == 0) {
            return;
        }
        if (hasPermissionGranted("android.permission.MANAGE_USERS", callingUid) && hasPermissionGranted("android.permission.INTERACT_ACROSS_USERS_FULL", callingUid)) {
            return;
        }
        throw new java.lang.SecurityException("You need MANAGE_USERS and INTERACT_ACROSS_USERS_FULL permission to: " + str);
    }

    private static boolean hasPermissionGranted(java.lang.String str, int i) {
        return android.app.ActivityManager.checkComponentPermission(str, i, -1, true) == 0;
    }

    private static final void checkManageUsersPermission(java.lang.String str) {
        if (!hasManageUsersPermission()) {
            throw new java.lang.SecurityException("You need MANAGE_USERS permission to: " + str);
        }
    }

    private static final void checkCreateUsersPermission(java.lang.String str) {
        if (!hasCreateUsersPermission()) {
            throw new java.lang.SecurityException("You either need MANAGE_USERS or CREATE_USERS permission to: " + str);
        }
    }

    private static final void checkQueryUsersPermission(java.lang.String str) {
        if (!hasQueryUsersPermission()) {
            throw new java.lang.SecurityException("You either need MANAGE_USERS or QUERY_USERS permission to: " + str);
        }
    }

    private static final void checkQueryOrCreateUsersPermission(java.lang.String str) {
        if (!hasQueryOrCreateUsersPermission()) {
            throw new java.lang.SecurityException("You either need MANAGE_USERS, CREATE_USERS, or QUERY_USERS permission to: " + str);
        }
    }

    private static final void checkCreateUsersPermission(int i) {
        if (((-38701) & i) == 0) {
            if (!hasCreateUsersPermission()) {
                throw new java.lang.SecurityException("You either need MANAGE_USERS or CREATE_USERS permission to create an user with flags: " + i);
            }
            return;
        }
        if (!hasManageUsersPermission()) {
            throw new java.lang.SecurityException("You need MANAGE_USERS permission to create an user  with flags: " + i);
        }
    }

    private static final boolean hasManageUsersPermission() {
        return hasManageUsersPermission(android.os.Binder.getCallingUid());
    }

    private static boolean hasManageUsersPermission(int i) {
        return android.os.UserHandle.isSameApp(i, 1000) || i == 0 || hasPermissionGranted("android.permission.MANAGE_USERS", i);
    }

    private static final boolean hasManageUsersOrPermission(java.lang.String str) {
        int callingUid = android.os.Binder.getCallingUid();
        return hasManageUsersPermission(callingUid) || hasPermissionGranted(str, callingUid);
    }

    private static final boolean hasCreateUsersPermission() {
        return hasManageUsersOrPermission("android.permission.CREATE_USERS");
    }

    private static final boolean hasQueryUsersPermission() {
        return hasManageUsersOrPermission("android.permission.QUERY_USERS");
    }

    private static final boolean hasQueryOrCreateUsersPermission() {
        return hasCreateUsersPermission() || hasPermissionGranted("android.permission.QUERY_USERS", android.os.Binder.getCallingUid());
    }

    private static void checkSystemOrRoot(java.lang.String str) {
        int callingUid = android.os.Binder.getCallingUid();
        if (!android.os.UserHandle.isSameApp(callingUid, 1000) && callingUid != 0) {
            throw new java.lang.SecurityException("Only system may: " + str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mPackagesLock"})
    public void writeBitmapLP(android.content.pm.UserInfo userInfo, android.graphics.Bitmap bitmap) {
        try {
            java.io.File file = new java.io.File(this.mUsersDir, java.lang.Integer.toString(userInfo.id));
            java.io.File file2 = new java.io.File(file, USER_PHOTO_FILENAME);
            java.io.File file3 = new java.io.File(file, USER_PHOTO_FILENAME_TMP);
            if (!file.exists()) {
                file.mkdir();
                android.os.FileUtils.setPermissions(file.getPath(), 505, -1, -1);
            }
            android.graphics.Bitmap.CompressFormat compressFormat = android.graphics.Bitmap.CompressFormat.PNG;
            java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(file3);
            if (bitmap.compress(compressFormat, 100, fileOutputStream) && file3.renameTo(file2) && android.os.SELinux.restorecon(file2)) {
                userInfo.iconPath = file2.getAbsolutePath();
            }
            try {
                fileOutputStream.close();
            } catch (java.io.IOException e) {
            }
            file3.delete();
        } catch (java.io.FileNotFoundException e2) {
            android.util.Slog.w(LOG_TAG, "Error setting photo for user ", e2);
        }
    }

    @android.annotation.NonNull
    public int[] getUserIds() {
        int[] iArr;
        synchronized (this.mUsersLock) {
            iArr = this.mUserIds;
        }
        return iArr;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean userExists(int i) {
        synchronized (this.mUsersLock) {
            try {
                for (int i2 : this.mUserIds) {
                    if (i2 == i) {
                        return true;
                    }
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.NonNull
    public int[] getUserIdsIncludingPreCreated() {
        int[] iArr;
        synchronized (this.mUsersLock) {
            iArr = this.mUserIdsIncludingPreCreated;
        }
        return iArr;
    }

    public boolean isHeadlessSystemUserMode() {
        boolean z;
        synchronized (this.mUsersLock) {
            z = this.mUsers.get(0).info.isFull() ? false : true;
        }
        return z;
    }

    private boolean isDefaultHeadlessSystemUserMode() {
        if (!android.os.Build.isDebuggable()) {
            return com.android.internal.os.RoSystemProperties.MULTIUSER_HEADLESS_SYSTEM_USER;
        }
        java.lang.String str = android.os.SystemProperties.get("persist.debug.user_mode_emulation");
        if (!android.text.TextUtils.isEmpty(str)) {
            if ("headless".equals(str)) {
                return true;
            }
            if ("full".equals(str)) {
                return false;
            }
            if (!"default".equals(str)) {
                com.android.server.utils.Slogf.e(LOG_TAG, "isDefaultHeadlessSystemUserMode(): ignoring invalid valued of property %s: %s", "persist.debug.user_mode_emulation", str);
            }
        }
        return com.android.internal.os.RoSystemProperties.MULTIUSER_HEADLESS_SYSTEM_USER;
    }

    private void emulateSystemUserModeIfNeeded() {
        java.lang.String str;
        int i;
        android.content.pm.UserInfo earliestCreatedFullUser;
        if (!android.os.Build.isDebuggable() || android.text.TextUtils.isEmpty(android.os.SystemProperties.get("persist.debug.user_mode_emulation"))) {
            return;
        }
        boolean isDefaultHeadlessSystemUserMode = isDefaultHeadlessSystemUserMode();
        synchronized (this.mPackagesLock) {
            synchronized (this.mUsersLock) {
                boolean z = false;
                com.android.server.pm.UserManagerService.UserData userData = this.mUsers.get(0);
                if (userData == null) {
                    com.android.server.utils.Slogf.wtf(LOG_TAG, "emulateSystemUserModeIfNeeded(): no system user data");
                    return;
                }
                int mainUserIdUnchecked = getMainUserIdUnchecked();
                int i2 = userData.info.flags;
                if (isDefaultHeadlessSystemUserMode) {
                    str = "android.os.usertype.system.HEADLESS";
                    i = i2 & (-1025) & (-16385);
                } else {
                    str = "android.os.usertype.full.SYSTEM";
                    i = i2 | 1024 | 16384;
                }
                if (userData.info.userType.equals(str)) {
                    com.android.server.utils.Slogf.d(LOG_TAG, "emulateSystemUserModeIfNeeded(): system user type is already %s, returning", str);
                    return;
                }
                com.android.server.utils.Slogf.i(LOG_TAG, "Persisting emulated system user data: type changed from %s to %s, flags changed from %s to %s", userData.info.userType, str, android.content.pm.UserInfo.flagsToString(i2), android.content.pm.UserInfo.flagsToString(i));
                userData.info.userType = str;
                userData.info.flags = i;
                writeUserLP(userData);
                com.android.server.pm.UserManagerService.UserData userDataNoChecks = getUserDataNoChecks(mainUserIdUnchecked);
                if (isDefaultHeadlessSystemUserMode) {
                    if (userDataNoChecks != null && (userDataNoChecks.info.flags & 2048) == 0) {
                        z = true;
                    }
                    if (!z && isMainUserPermanentAdmin() && (earliestCreatedFullUser = getEarliestCreatedFullUser()) != null) {
                        com.android.server.utils.Slogf.i(LOG_TAG, "Designating user " + earliestCreatedFullUser.id + " to be Main");
                        earliestCreatedFullUser.flags = earliestCreatedFullUser.flags | 16384;
                        writeUserLP(getUserDataNoChecks(earliestCreatedFullUser.id));
                    }
                } else if (userDataNoChecks != null && (userDataNoChecks.info.flags & 2048) == 0) {
                    com.android.server.utils.Slogf.i(LOG_TAG, "Transferring Main to user 0 from " + userDataNoChecks.info.id);
                    android.content.pm.UserInfo userInfo = userDataNoChecks.info;
                    userInfo.flags = userInfo.flags & (-16385);
                    writeUserLP(userDataNoChecks);
                } else {
                    com.android.server.utils.Slogf.i(LOG_TAG, "Designated user 0 to be Main");
                }
                this.mUpdatingSystemUserMode = true;
            }
        }
    }

    private com.android.server.pm.ResilientAtomicFile getUserListFile() {
        return new com.android.server.pm.ResilientAtomicFile(this.mUserListFile, new java.io.File(this.mUserListFile.getParent(), this.mUserListFile.getName() + ".backup"), new java.io.File(this.mUserListFile.getParent(), this.mUserListFile.getName() + ".reservecopy"), 505, "user list", new com.android.server.pm.ResilientAtomicFile.ReadEventLogger() { // from class: com.android.server.pm.UserManagerService$$ExternalSyntheticLambda1
            @Override // com.android.server.pm.ResilientAtomicFile.ReadEventLogger
            public final void logEvent(int i, java.lang.String str) {
                com.android.server.pm.UserManagerService.this.lambda$getUserListFile$5(i, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getUserListFile$5(int i, java.lang.String str) {
        android.util.Slog.e(LOG_TAG, str);
        scheduleWriteUserList();
    }

    /* JADX WARN: Code restructure failed: missing block: B:62:0x00dc, code lost:
    
        if (r3.getName().equals(com.android.server.pm.UserManagerService.TAG_RESTRICTIONS) == false) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00de, code lost:
    
        r4 = r12.mGuestRestrictions;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00e0, code lost:
    
        monitor-enter(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00e1, code lost:
    
        com.android.server.pm.UserRestrictionsUtils.readRestrictions(r3, r12.mGuestRestrictions);
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00e6, code lost:
    
        monitor-exit(r4);
     */
    @com.android.internal.annotations.GuardedBy({"mPackagesLock"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void readUserListLP() {
        java.io.FileInputStream fileInputStream;
        java.lang.Exception e;
        int next;
        com.android.server.pm.ResilientAtomicFile userListFile = getUserListFile();
        try {
            try {
                fileInputStream = userListFile.openRead();
            } catch (java.lang.Throwable th) {
                if (userListFile != null) {
                    try {
                        userListFile.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (java.lang.Exception e2) {
            fileInputStream = null;
            e = e2;
        }
        try {
            if (fileInputStream == null) {
                android.util.Slog.e(LOG_TAG, "userlist.xml not found, fallback to single user");
                fallbackToSingleUserLP();
                userListFile.close();
                return;
            }
            com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(fileInputStream);
            do {
                next = resolvePullParser.next();
                if (next == 2) {
                    break;
                }
            } while (next != 1);
            if (next != 2) {
                android.util.Slog.e(LOG_TAG, "Unable to read user list");
                fallbackToSingleUserLP();
                userListFile.close();
                return;
            }
            this.mNextSerialNumber = -1;
            if (resolvePullParser.getName().equals("users")) {
                this.mNextSerialNumber = resolvePullParser.getAttributeInt((java.lang.String) null, ATTR_NEXT_SERIAL_NO, this.mNextSerialNumber);
                this.mUserVersion = resolvePullParser.getAttributeInt((java.lang.String) null, ATTR_USER_VERSION, this.mUserVersion);
                this.mUserTypeVersion = resolvePullParser.getAttributeInt((java.lang.String) null, ATTR_USER_TYPE_VERSION, this.mUserTypeVersion);
            }
            boolean z = false;
            while (true) {
                int next2 = resolvePullParser.next();
                if (next2 == 1) {
                    break;
                }
                if (next2 == 2) {
                    java.lang.String name = resolvePullParser.getName();
                    if (name.equals(TAG_USER)) {
                        com.android.server.pm.UserManagerService.UserData readUserLP = readUserLP(resolvePullParser.getAttributeInt((java.lang.String) null, ATTR_ID), this.mUserVersion);
                        if (readUserLP != null) {
                            synchronized (this.mUsersLock) {
                                try {
                                    this.mUsers.put(readUserLP.info.id, readUserLP);
                                    if (this.mNextSerialNumber < 0 || this.mNextSerialNumber <= readUserLP.info.id) {
                                        this.mNextSerialNumber = readUserLP.info.id + 1;
                                    }
                                } finally {
                                }
                            }
                        }
                    } else if (name.equals(TAG_GUEST_RESTRICTIONS)) {
                        while (true) {
                            int next3 = resolvePullParser.next();
                            if (next3 == 1 || next3 == 3) {
                                break;
                            } else if (next3 == 2) {
                                break;
                            }
                        }
                        z = true;
                    }
                }
            }
            updateUserIds();
            upgradeIfNecessaryLP();
            updateUsersWithFeatureFlags(z);
            userListFile.close();
            synchronized (this.mUsersLock) {
                try {
                    if (this.mUsers.size() == 0) {
                        android.util.Slog.e(LOG_TAG, "mUsers is empty, fallback to single user");
                        fallbackToSingleUserLP();
                    }
                } finally {
                }
            }
        } catch (java.lang.Exception e3) {
            e = e3;
            userListFile.failRead(fileInputStream, e);
            readUserListLP();
            userListFile.close();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mPackagesLock"})
    private void upgradeIfNecessaryLP() {
        upgradeIfNecessaryLP(this.mUserVersion, this.mUserTypeVersion);
    }

    @com.android.internal.annotations.GuardedBy({"mPackagesLock"})
    private void updateUsersWithFeatureFlags(boolean z) {
        if (z == android.multiuser.Flags.saveGlobalAndGuestRestrictionsOnSystemUserXmlReadOnly()) {
            for (int i : getUserIds()) {
                writeUserLP(getUserDataNoChecks(i));
            }
            writeUserListLP();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mPackagesLock"})
    @com.android.internal.annotations.VisibleForTesting
    void upgradeIfNecessaryLP(int i, int i2) {
        android.content.pm.UserInfo earliestCreatedFullUser;
        android.util.Slog.i(LOG_TAG, "Upgrading users from userVersion " + i + " to 11");
        android.util.ArraySet arraySet = new android.util.ArraySet();
        int i3 = this.mUserVersion;
        int i4 = this.mUserTypeVersion;
        if (i < 1) {
            com.android.server.pm.UserManagerService.UserData userDataNoChecks = getUserDataNoChecks(0);
            if ("Primary".equals(userDataNoChecks.info.name)) {
                userDataNoChecks.info.name = this.mContext.getResources().getString(android.R.string.notification_hidden_text);
                arraySet.add(java.lang.Integer.valueOf(userDataNoChecks.info.id));
            }
            i = 1;
        }
        if (i < 2) {
            com.android.server.pm.UserManagerService.UserData userDataNoChecks2 = getUserDataNoChecks(0);
            if ((userDataNoChecks2.info.flags & 16) == 0) {
                userDataNoChecks2.info.flags |= 16;
                arraySet.add(java.lang.Integer.valueOf(userDataNoChecks2.info.id));
            }
            i = 2;
        }
        if (i < 4) {
            i = 4;
        }
        if (i < 5) {
            initDefaultGuestRestrictions();
            i = 5;
        }
        if (i < 6) {
            synchronized (this.mUsersLock) {
                for (int i5 = 0; i5 < this.mUsers.size(); i5++) {
                    try {
                        com.android.server.pm.UserManagerService.UserData valueAt = this.mUsers.valueAt(i5);
                        if (valueAt.info.isRestricted() && valueAt.info.restrictedProfileParentId == -10000) {
                            valueAt.info.restrictedProfileParentId = 0;
                            arraySet.add(java.lang.Integer.valueOf(valueAt.info.id));
                        }
                    } finally {
                    }
                }
            }
            i = 6;
        }
        if (i < 7) {
            synchronized (this.mRestrictionsLock) {
                try {
                    if (this.mDevicePolicyUserRestrictions.removeRestrictionsForAllUsers("ensure_verify_apps")) {
                        this.mDevicePolicyUserRestrictions.getRestrictionsNonNull(-1).putBoolean("ensure_verify_apps", true);
                    }
                } finally {
                }
            }
            java.util.List<android.content.pm.UserInfo> guestUsers = getGuestUsers();
            for (int i6 = 0; i6 < guestUsers.size(); i6++) {
                android.content.pm.UserInfo userInfo = guestUsers.get(i6);
                if (userInfo != null && !hasUserRestriction("no_config_wifi", userInfo.id)) {
                    setUserRestriction("no_config_wifi", true, userInfo.id);
                }
            }
            i = 7;
        }
        if (i < 8) {
            synchronized (this.mUsersLock) {
                try {
                    com.android.server.pm.UserManagerService.UserData userData = this.mUsers.get(0);
                    userData.info.flags |= 2048;
                    if (!isDefaultHeadlessSystemUserMode()) {
                        userData.info.flags |= 1024;
                    }
                    arraySet.add(java.lang.Integer.valueOf(userData.info.id));
                    for (int i7 = 1; i7 < this.mUsers.size(); i7++) {
                        com.android.server.pm.UserManagerService.UserData valueAt2 = this.mUsers.valueAt(i7);
                        if ((valueAt2.info.flags & 32) == 0) {
                            valueAt2.info.flags |= 1024;
                            arraySet.add(java.lang.Integer.valueOf(valueAt2.info.id));
                        }
                    }
                } finally {
                }
            }
            i = 8;
        }
        if (i < 9) {
            synchronized (this.mUsersLock) {
                for (int i8 = 0; i8 < this.mUsers.size(); i8++) {
                    try {
                        com.android.server.pm.UserManagerService.UserData valueAt3 = this.mUsers.valueAt(i8);
                        int i9 = valueAt3.info.flags;
                        if ((i9 & 2048) != 0) {
                            if ((i9 & 1024) != 0) {
                                valueAt3.info.userType = "android.os.usertype.full.SYSTEM";
                            } else {
                                valueAt3.info.userType = "android.os.usertype.system.HEADLESS";
                            }
                        } else {
                            try {
                                valueAt3.info.userType = android.content.pm.UserInfo.getDefaultUserType(i9);
                            } catch (java.lang.IllegalArgumentException e) {
                                throw new java.lang.IllegalStateException("Cannot upgrade user with flags " + java.lang.Integer.toHexString(i9) + " because it doesn't correspond to a valid user type.", e);
                            }
                        }
                        com.android.server.pm.UserTypeDetails userTypeDetails = this.mUserTypes.get(valueAt3.info.userType);
                        if (userTypeDetails == null) {
                            throw new java.lang.IllegalStateException("Cannot upgrade user with flags " + java.lang.Integer.toHexString(i9) + " because " + valueAt3.info.userType + " isn't defined on this device!");
                        }
                        android.content.pm.UserInfo userInfo2 = valueAt3.info;
                        userInfo2.flags = userTypeDetails.getDefaultUserInfoFlags() | userInfo2.flags;
                        arraySet.add(java.lang.Integer.valueOf(valueAt3.info.id));
                    } finally {
                    }
                }
            }
            i = 9;
        }
        if (i < 10) {
            synchronized (this.mUsersLock) {
                for (int i10 = 0; i10 < this.mUsers.size(); i10++) {
                    try {
                        com.android.server.pm.UserManagerService.UserData valueAt4 = this.mUsers.valueAt(i10);
                        com.android.server.pm.UserTypeDetails userTypeDetails2 = this.mUserTypes.get(valueAt4.info.userType);
                        if (userTypeDetails2 == null) {
                            throw new java.lang.IllegalStateException("Cannot upgrade user because " + valueAt4.info.userType + " isn't defined on this device!");
                        }
                        valueAt4.userProperties = new android.content.pm.UserProperties(userTypeDetails2.getDefaultUserPropertiesReference());
                        arraySet.add(java.lang.Integer.valueOf(valueAt4.info.id));
                    } finally {
                    }
                }
            }
            i = 10;
        }
        if (i < 11) {
            if (isHeadlessSystemUserMode()) {
                if (isMainUserPermanentAdmin() && (earliestCreatedFullUser = getEarliestCreatedFullUser()) != null) {
                    earliestCreatedFullUser.flags |= 16384;
                    arraySet.add(java.lang.Integer.valueOf(earliestCreatedFullUser.id));
                }
            } else {
                synchronized (this.mUsersLock) {
                    com.android.server.pm.UserManagerService.UserData userData2 = this.mUsers.get(0);
                    userData2.info.flags |= 16384;
                    arraySet.add(java.lang.Integer.valueOf(userData2.info.id));
                }
            }
            i = 11;
        }
        int userTypeVersion = com.android.server.pm.UserTypeFactory.getUserTypeVersion();
        if (userTypeVersion > i2) {
            synchronized (this.mUsersLock) {
                upgradeUserTypesLU(com.android.server.pm.UserTypeFactory.getUserTypeUpgrades(), this.mUserTypes, i2, arraySet);
            }
        }
        if (i < 11) {
            android.util.Slog.w(LOG_TAG, "User version " + this.mUserVersion + " didn't upgrade as expected to 11");
            return;
        }
        if (i > 11) {
            android.util.Slog.wtf(LOG_TAG, "Upgraded user version " + this.mUserVersion + " is higher the SDK's one of 11. Someone forgot to update USER_VERSION?");
        }
        this.mUserVersion = i;
        this.mUserTypeVersion = userTypeVersion;
        if (i3 < this.mUserVersion || i4 < this.mUserTypeVersion) {
            java.util.Iterator<java.lang.Integer> it = arraySet.iterator();
            while (it.hasNext()) {
                com.android.server.pm.UserManagerService.UserData userDataNoChecks3 = getUserDataNoChecks(it.next().intValue());
                if (userDataNoChecks3 != null) {
                    writeUserLP(userDataNoChecks3);
                }
            }
            writeUserListLP();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUsersLock"})
    private void upgradeUserTypesLU(@android.annotation.NonNull java.util.List<com.android.server.pm.UserTypeFactory.UserTypeUpgrade> list, @android.annotation.NonNull android.util.ArrayMap<java.lang.String, com.android.server.pm.UserTypeDetails> arrayMap, int i, @android.annotation.NonNull java.util.Set<java.lang.Integer> set) {
        for (com.android.server.pm.UserTypeFactory.UserTypeUpgrade userTypeUpgrade : list) {
            if (i <= userTypeUpgrade.getUpToVersion()) {
                for (int i2 = 0; i2 < this.mUsers.size(); i2++) {
                    com.android.server.pm.UserManagerService.UserData valueAt = this.mUsers.valueAt(i2);
                    if (userTypeUpgrade.getFromType().equals(valueAt.info.userType)) {
                        com.android.server.pm.UserTypeDetails userTypeDetails = arrayMap.get(userTypeUpgrade.getToType());
                        if (userTypeDetails == null) {
                            throw new java.lang.IllegalStateException("Upgrade destination user type not defined: " + userTypeUpgrade.getToType());
                        }
                        upgradeProfileToTypeLU(valueAt.info, userTypeDetails);
                        set.add(java.lang.Integer.valueOf(valueAt.info.id));
                    }
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUsersLock"})
    @com.android.internal.annotations.VisibleForTesting
    void upgradeProfileToTypeLU(@android.annotation.NonNull android.content.pm.UserInfo userInfo, @android.annotation.NonNull com.android.server.pm.UserTypeDetails userTypeDetails) {
        int i;
        android.util.Slog.i(LOG_TAG, "Upgrading user " + userInfo.id + " from " + userInfo.userType + " to " + userTypeDetails.getName());
        if (!userInfo.isProfile()) {
            throw new java.lang.IllegalStateException("Can only upgrade profile types. " + userInfo.userType + " is not a profile type.");
        }
        if (!canAddMoreProfilesToUser(userTypeDetails.getName(), userInfo.profileGroupId, false)) {
            android.util.Slog.w(LOG_TAG, "Exceeded maximum profiles of type " + userTypeDetails.getName() + " for user " + userInfo.id + ". Maximum allowed= " + userTypeDetails.getMaxAllowedPerParent());
        }
        com.android.server.pm.UserTypeDetails userTypeDetails2 = this.mUserTypes.get(userInfo.userType);
        if (userTypeDetails2 != null) {
            i = userTypeDetails2.getDefaultUserInfoFlags();
        } else {
            i = 4096;
        }
        userInfo.userType = userTypeDetails.getName();
        userInfo.flags = (i ^ userInfo.flags) | userTypeDetails.getDefaultUserInfoFlags();
        synchronized (this.mRestrictionsLock) {
            try {
                if (!com.android.server.BundleUtils.isEmpty(userTypeDetails.getDefaultRestrictions())) {
                    android.os.Bundle clone = com.android.server.BundleUtils.clone(this.mBaseUserRestrictions.getRestrictions(userInfo.id));
                    com.android.server.pm.UserRestrictionsUtils.merge(clone, userTypeDetails.getDefaultRestrictions());
                    updateUserRestrictionsInternalLR(clone, userInfo.id);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        userInfo.profileBadge = getFreeProfileBadgeLU(userInfo.profileGroupId, userInfo.userType);
    }

    @android.annotation.Nullable
    private android.content.pm.UserInfo getEarliestCreatedFullUser() {
        java.util.List<android.content.pm.UserInfo> usersInternal = getUsersInternal(true, true, true);
        android.content.pm.UserInfo userInfo = null;
        long j = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
        for (int i = 0; i < usersInternal.size(); i++) {
            android.content.pm.UserInfo userInfo2 = usersInternal.get(i);
            if (userInfo2.isFull() && userInfo2.isAdmin() && userInfo2.creationTime >= 0 && userInfo2.creationTime < j) {
                j = userInfo2.creationTime;
                userInfo = userInfo2;
            }
        }
        return userInfo;
    }

    @com.android.internal.annotations.GuardedBy({"mPackagesLock"})
    private void fallbackToSingleUserLP() {
        java.lang.String str;
        if (isDefaultHeadlessSystemUserMode()) {
            str = "android.os.usertype.system.HEADLESS";
        } else {
            str = "android.os.usertype.full.SYSTEM";
        }
        com.android.server.pm.UserManagerService.UserData putUserInfo = putUserInfo(new android.content.pm.UserInfo(0, (java.lang.String) null, (java.lang.String) null, this.mUserTypes.get(str).getDefaultUserInfoFlags() | 16, str));
        putUserInfo.userProperties = new android.content.pm.UserProperties(this.mUserTypes.get(putUserInfo.info.userType).getDefaultUserPropertiesReference());
        this.mNextSerialNumber = 10;
        this.mUserVersion = 11;
        this.mUserTypeVersion = com.android.server.pm.UserTypeFactory.getUserTypeVersion();
        android.os.Bundle bundle = new android.os.Bundle();
        try {
            for (java.lang.String str2 : this.mContext.getResources().getStringArray(android.R.array.config_defaultAmbientContextServices)) {
                if (com.android.server.pm.UserRestrictionsUtils.isValidRestriction(str2)) {
                    bundle.putBoolean(str2, true);
                }
            }
        } catch (android.content.res.Resources.NotFoundException e) {
            android.util.Slog.e(LOG_TAG, "Couldn't find resource: config_defaultFirstUserRestrictions", e);
        }
        if (!bundle.isEmpty()) {
            synchronized (this.mRestrictionsLock) {
                this.mBaseUserRestrictions.updateRestrictions(0, bundle);
            }
        }
        initDefaultGuestRestrictions();
        writeUserLP(putUserInfo);
        writeUserListLP();
    }

    private java.lang.String getOwnerName() {
        return this.mOwnerName.get();
    }

    private java.lang.String getGuestName() {
        return this.mContext.getString(android.R.string.global_action_screenshot);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invalidateOwnerNameIfNecessary(@android.annotation.NonNull android.content.res.Resources resources, boolean z) {
        int updateFrom = this.mLastConfiguration.updateFrom(resources.getConfiguration());
        if (z || (this.mOwnerNameTypedValue.changingConfigurations & updateFrom) != 0) {
            resources.getValue(android.R.string.notification_hidden_text, this.mOwnerNameTypedValue, true);
            java.lang.CharSequence coerceToString = this.mOwnerNameTypedValue.coerceToString();
            this.mOwnerName.set(coerceToString != null ? coerceToString.toString() : null);
        }
    }

    private void scheduleWriteUserList() {
        if (!this.mHandler.hasMessages(2)) {
            this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(2), 2000L);
        }
    }

    private void scheduleWriteUser(int i) {
        if (!this.mHandler.hasMessages(1, java.lang.Integer.valueOf(i))) {
            this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1, java.lang.Integer.valueOf(i)), 2000L);
        }
    }

    private com.android.server.pm.ResilientAtomicFile getUserFile(final int i) {
        return new com.android.server.pm.ResilientAtomicFile(new java.io.File(this.mUsersDir, i + XML_SUFFIX), new java.io.File(this.mUsersDir, i + XML_SUFFIX + ".backup"), new java.io.File(this.mUsersDir, i + XML_SUFFIX + ".reservecopy"), 505, "user info", new com.android.server.pm.ResilientAtomicFile.ReadEventLogger() { // from class: com.android.server.pm.UserManagerService$$ExternalSyntheticLambda4
            @Override // com.android.server.pm.ResilientAtomicFile.ReadEventLogger
            public final void logEvent(int i2, java.lang.String str) {
                com.android.server.pm.UserManagerService.this.lambda$getUserFile$6(i, i2, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getUserFile$6(int i, int i2, java.lang.String str) {
        android.util.Slog.e(LOG_TAG, str);
        if (getUserDataNoChecks(i) != null) {
            scheduleWriteUser(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:12:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x003a  */
    @com.android.internal.annotations.GuardedBy({"mPackagesLock"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void writeUserLP(com.android.server.pm.UserManagerService.UserData userData) {
        java.io.FileOutputStream fileOutputStream;
        com.android.server.pm.ResilientAtomicFile userFile = getUserFile(userData.info.id);
        try {
            try {
                fileOutputStream = userFile.startWrite();
                try {
                    writeUserLP(userData, fileOutputStream);
                    userFile.finishWrite(fileOutputStream);
                } catch (java.lang.Exception e) {
                    e = e;
                    android.util.Slog.e(LOG_TAG, "Error writing user info " + userData.info.id, e);
                    userFile.failWrite(fileOutputStream);
                    if (userFile == null) {
                    }
                }
            } catch (java.lang.Exception e2) {
                e = e2;
                fileOutputStream = null;
            }
            if (userFile == null) {
                userFile.close();
            }
        } catch (java.lang.Throwable th) {
            if (userFile != null) {
                try {
                    userFile.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mPackagesLock"})
    @com.android.internal.annotations.VisibleForTesting
    void writeUserLP(com.android.server.pm.UserManagerService.UserData userData, java.io.OutputStream outputStream) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(outputStream);
        resolveSerializer.startDocument((java.lang.String) null, true);
        resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        android.content.pm.UserInfo userInfo = userData.info;
        resolveSerializer.startTag((java.lang.String) null, TAG_USER);
        resolveSerializer.attributeInt((java.lang.String) null, ATTR_ID, userInfo.id);
        resolveSerializer.attributeInt((java.lang.String) null, ATTR_SERIAL_NO, userInfo.serialNumber);
        resolveSerializer.attributeInt((java.lang.String) null, ATTR_FLAGS, userInfo.flags);
        resolveSerializer.attribute((java.lang.String) null, com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE, userInfo.userType);
        resolveSerializer.attributeLong((java.lang.String) null, ATTR_CREATION_TIME, userInfo.creationTime);
        resolveSerializer.attributeLong((java.lang.String) null, ATTR_LAST_LOGGED_IN_TIME, userInfo.lastLoggedInTime);
        if (userInfo.lastLoggedInFingerprint != null) {
            resolveSerializer.attribute((java.lang.String) null, ATTR_LAST_LOGGED_IN_FINGERPRINT, userInfo.lastLoggedInFingerprint);
        }
        resolveSerializer.attributeLong((java.lang.String) null, ATTR_LAST_ENTERED_FOREGROUND_TIME, userData.mLastEnteredForegroundTimeMillis);
        if (userInfo.iconPath != null) {
            resolveSerializer.attribute((java.lang.String) null, ATTR_ICON_PATH, userInfo.iconPath);
        }
        if (userInfo.partial) {
            resolveSerializer.attributeBoolean((java.lang.String) null, ATTR_PARTIAL, true);
        }
        if (userInfo.preCreated) {
            resolveSerializer.attributeBoolean((java.lang.String) null, ATTR_PRE_CREATED, true);
        }
        if (userInfo.convertedFromPreCreated) {
            resolveSerializer.attributeBoolean((java.lang.String) null, ATTR_CONVERTED_FROM_PRE_CREATED, true);
        }
        if (userInfo.guestToRemove) {
            resolveSerializer.attributeBoolean((java.lang.String) null, ATTR_GUEST_TO_REMOVE, true);
        }
        if (userInfo.profileGroupId != -10000) {
            resolveSerializer.attributeInt((java.lang.String) null, ATTR_PROFILE_GROUP_ID, userInfo.profileGroupId);
        }
        resolveSerializer.attributeInt((java.lang.String) null, ATTR_PROFILE_BADGE, userInfo.profileBadge);
        if (userInfo.restrictedProfileParentId != -10000) {
            resolveSerializer.attributeInt((java.lang.String) null, ATTR_RESTRICTED_PROFILE_PARENT_ID, userInfo.restrictedProfileParentId);
        }
        if (userData.persistSeedData) {
            if (userData.seedAccountName != null) {
                resolveSerializer.attribute((java.lang.String) null, ATTR_SEED_ACCOUNT_NAME, truncateString(userData.seedAccountName, 500));
            }
            if (userData.seedAccountType != null) {
                resolveSerializer.attribute((java.lang.String) null, ATTR_SEED_ACCOUNT_TYPE, truncateString(userData.seedAccountType, 500));
            }
        }
        if (userInfo.name != null) {
            resolveSerializer.startTag((java.lang.String) null, "name");
            resolveSerializer.text(truncateString(userInfo.name, 100));
            resolveSerializer.endTag((java.lang.String) null, "name");
        }
        synchronized (this.mRestrictionsLock) {
            com.android.server.pm.UserRestrictionsUtils.writeRestrictions(resolveSerializer, this.mBaseUserRestrictions.getRestrictions(userInfo.id), TAG_RESTRICTIONS);
            if (android.multiuser.Flags.saveGlobalAndGuestRestrictionsOnSystemUserXmlReadOnly()) {
                if (userInfo.id == 0) {
                    com.android.server.pm.UserRestrictionsUtils.writeRestrictions(resolveSerializer, this.mDevicePolicyUserRestrictions.getRestrictions(-1), TAG_DEVICE_POLICY_GLOBAL_RESTRICTIONS);
                    resolveSerializer.startTag((java.lang.String) null, TAG_GUEST_RESTRICTIONS);
                    synchronized (this.mGuestRestrictions) {
                        com.android.server.pm.UserRestrictionsUtils.writeRestrictions(resolveSerializer, this.mGuestRestrictions, TAG_RESTRICTIONS);
                    }
                    resolveSerializer.endTag((java.lang.String) null, TAG_GUEST_RESTRICTIONS);
                }
            } else {
                com.android.server.pm.UserRestrictionsUtils.writeRestrictions(resolveSerializer, this.mDevicePolicyUserRestrictions.getRestrictions(-1), TAG_DEVICE_POLICY_GLOBAL_RESTRICTIONS);
            }
            com.android.server.pm.UserRestrictionsUtils.writeRestrictions(resolveSerializer, this.mDevicePolicyUserRestrictions.getRestrictions(userInfo.id), TAG_DEVICE_POLICY_LOCAL_RESTRICTIONS);
        }
        if (userData.account != null) {
            resolveSerializer.startTag((java.lang.String) null, TAG_ACCOUNT);
            resolveSerializer.text(userData.account);
            resolveSerializer.endTag((java.lang.String) null, TAG_ACCOUNT);
        }
        if (userData.persistSeedData && userData.seedAccountOptions != null) {
            resolveSerializer.startTag((java.lang.String) null, TAG_SEED_ACCOUNT_OPTIONS);
            userData.seedAccountOptions.saveToXml(resolveSerializer);
            resolveSerializer.endTag((java.lang.String) null, TAG_SEED_ACCOUNT_OPTIONS);
        }
        if (userData.userProperties != null) {
            resolveSerializer.startTag((java.lang.String) null, TAG_USER_PROPERTIES);
            userData.userProperties.writeToXml(resolveSerializer);
            resolveSerializer.endTag((java.lang.String) null, TAG_USER_PROPERTIES);
        }
        if (userData.getLastRequestQuietModeEnabledMillis() != 0) {
            resolveSerializer.startTag((java.lang.String) null, TAG_LAST_REQUEST_QUIET_MODE_ENABLED_CALL);
            resolveSerializer.text(java.lang.String.valueOf(userData.getLastRequestQuietModeEnabledMillis()));
            resolveSerializer.endTag((java.lang.String) null, TAG_LAST_REQUEST_QUIET_MODE_ENABLED_CALL);
        }
        resolveSerializer.startTag((java.lang.String) null, TAG_IGNORE_PREPARE_STORAGE_ERRORS);
        resolveSerializer.text(java.lang.String.valueOf(userData.getIgnorePrepareStorageErrors()));
        resolveSerializer.endTag((java.lang.String) null, TAG_IGNORE_PREPARE_STORAGE_ERRORS);
        resolveSerializer.endTag((java.lang.String) null, TAG_USER);
        resolveSerializer.endDocument();
    }

    private java.lang.String truncateString(java.lang.String str, int i) {
        if (str == null || str.length() <= i) {
            return str;
        }
        return str.substring(0, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
    @com.android.internal.annotations.GuardedBy({"mPackagesLock"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void writeUserListLP() {
        java.io.FileOutputStream fileOutputStream;
        java.lang.Exception e;
        int size;
        int[] iArr;
        int i;
        com.android.server.pm.ResilientAtomicFile userListFile = getUserListFile();
        try {
            try {
                fileOutputStream = userListFile.startWrite();
            } catch (java.lang.Exception e2) {
                fileOutputStream = null;
                e = e2;
            }
            try {
                com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(fileOutputStream);
                resolveSerializer.startDocument((java.lang.String) null, true);
                resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                resolveSerializer.startTag((java.lang.String) null, "users");
                resolveSerializer.attributeInt((java.lang.String) null, ATTR_NEXT_SERIAL_NO, this.mNextSerialNumber);
                resolveSerializer.attributeInt((java.lang.String) null, ATTR_USER_VERSION, this.mUserVersion);
                resolveSerializer.attributeInt((java.lang.String) null, ATTR_USER_TYPE_VERSION, this.mUserTypeVersion);
                if (!android.multiuser.Flags.saveGlobalAndGuestRestrictionsOnSystemUserXmlReadOnly()) {
                    resolveSerializer.startTag((java.lang.String) null, TAG_GUEST_RESTRICTIONS);
                    synchronized (this.mGuestRestrictions) {
                        com.android.server.pm.UserRestrictionsUtils.writeRestrictions(resolveSerializer, this.mGuestRestrictions, TAG_RESTRICTIONS);
                    }
                    resolveSerializer.endTag((java.lang.String) null, TAG_GUEST_RESTRICTIONS);
                }
                synchronized (this.mUsersLock) {
                    try {
                        size = this.mUsers.size();
                        iArr = new int[size];
                        for (int i2 = 0; i2 < size; i2++) {
                            iArr[i2] = this.mUsers.valueAt(i2).info.id;
                        }
                    } finally {
                    }
                }
                for (i = 0; i < size; i++) {
                    int i3 = iArr[i];
                    resolveSerializer.startTag((java.lang.String) null, TAG_USER);
                    resolveSerializer.attributeInt((java.lang.String) null, ATTR_ID, i3);
                    resolveSerializer.endTag((java.lang.String) null, TAG_USER);
                }
                resolveSerializer.endTag((java.lang.String) null, "users");
                resolveSerializer.endDocument();
                userListFile.finishWrite(fileOutputStream);
            } catch (java.lang.Exception e3) {
                e = e3;
                android.util.Slog.e(LOG_TAG, "Error writing user list", e);
                userListFile.failWrite(fileOutputStream);
                if (userListFile == null) {
                }
            }
            if (userListFile == null) {
                userListFile.close();
            }
        } catch (java.lang.Throwable th) {
            if (userListFile != null) {
                try {
                    userListFile.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mPackagesLock"})
    private com.android.server.pm.UserManagerService.UserData readUserLP(int i, int i2) {
        java.io.FileInputStream fileInputStream;
        java.lang.Exception e;
        com.android.server.pm.ResilientAtomicFile userFile = getUserFile(i);
        try {
            try {
                fileInputStream = userFile.openRead();
            } catch (java.lang.Exception e2) {
                fileInputStream = null;
                e = e2;
            }
            try {
                if (fileInputStream == null) {
                    android.util.Slog.e(LOG_TAG, "User info not found, returning null, user id: " + i);
                    userFile.close();
                    return null;
                }
                com.android.server.pm.UserManagerService.UserData readUserLP = readUserLP(i, fileInputStream, i2);
                userFile.close();
                return readUserLP;
            } catch (java.lang.Exception e3) {
                e = e3;
                android.util.Slog.e(LOG_TAG, "Error reading user info, user id: " + i);
                userFile.failRead(fileInputStream, e);
                com.android.server.pm.UserManagerService.UserData readUserLP2 = readUserLP(i, i2);
                userFile.close();
                return readUserLP2;
            }
        } catch (java.lang.Throwable th) {
            if (userFile != null) {
                try {
                    userFile.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:103:0x01bd, code lost:
    
        if (r1.getName().equals(com.android.server.pm.UserManagerService.TAG_RESTRICTIONS) == false) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x01bf, code lost:
    
        r2 = r49.mGuestRestrictions;
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x01c1, code lost:
    
        monitor-enter(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x01c2, code lost:
    
        com.android.server.pm.UserRestrictionsUtils.readRestrictions(r1, r49.mGuestRestrictions);
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x01c7, code lost:
    
        monitor-exit(r2);
     */
    @com.android.internal.annotations.GuardedBy({"mPackagesLock"})
    @com.android.internal.annotations.VisibleForTesting
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    com.android.server.pm.UserManagerService.UserData readUserLP(int i, java.io.InputStream inputStream, int i2) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        int next;
        int i3;
        int i4;
        int i5;
        android.os.Bundle bundle;
        boolean z;
        android.os.Bundle bundle2;
        android.os.Bundle bundle3;
        java.lang.String str;
        java.lang.String str2;
        int i6;
        android.os.Bundle bundle4;
        boolean z2;
        java.lang.String str3;
        long j;
        long j2;
        boolean z3;
        android.content.pm.UserProperties userProperties;
        android.os.PersistableBundle persistableBundle;
        java.lang.String str4;
        java.lang.String str5;
        boolean z4;
        int i7;
        java.lang.String str6;
        java.lang.String str7;
        boolean z5;
        boolean z6;
        long j3;
        long j4;
        int i8;
        java.lang.String str8;
        int i9;
        com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(inputStream);
        do {
            next = resolvePullParser.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next != 2) {
            android.util.Slog.e(LOG_TAG, "Unable to read user " + i);
            return null;
        }
        if (next != 2 || !resolvePullParser.getName().equals(TAG_USER)) {
            i3 = i;
            i4 = -10000;
            i5 = -10000;
            bundle = null;
            z = false;
            bundle2 = null;
            bundle3 = null;
            str = null;
            str2 = null;
            i6 = 0;
            bundle4 = null;
            z2 = false;
            str3 = null;
            j = 0;
            j2 = 0;
            z3 = false;
            userProperties = null;
            persistableBundle = null;
            str4 = null;
            str5 = null;
            z4 = false;
            i7 = 0;
            str6 = null;
            str7 = null;
            z5 = true;
            z6 = false;
            j3 = 0;
            j4 = 0;
        } else if (resolvePullParser.getAttributeInt((java.lang.String) null, ATTR_ID, -1) == i) {
            int attributeInt = resolvePullParser.getAttributeInt((java.lang.String) null, ATTR_SERIAL_NO, i);
            int attributeInt2 = resolvePullParser.getAttributeInt((java.lang.String) null, ATTR_FLAGS, 0);
            java.lang.String attributeValue = resolvePullParser.getAttributeValue((java.lang.String) null, com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE);
            java.lang.String intern = attributeValue != null ? attributeValue.intern() : null;
            java.lang.String attributeValue2 = resolvePullParser.getAttributeValue((java.lang.String) null, ATTR_ICON_PATH);
            long attributeLong = resolvePullParser.getAttributeLong((java.lang.String) null, ATTR_CREATION_TIME, 0L);
            long attributeLong2 = resolvePullParser.getAttributeLong((java.lang.String) null, ATTR_LAST_LOGGED_IN_TIME, 0L);
            java.lang.String attributeValue3 = resolvePullParser.getAttributeValue((java.lang.String) null, ATTR_LAST_LOGGED_IN_FINGERPRINT);
            long attributeLong3 = resolvePullParser.getAttributeLong((java.lang.String) null, ATTR_LAST_ENTERED_FOREGROUND_TIME, 0L);
            int attributeInt3 = resolvePullParser.getAttributeInt((java.lang.String) null, ATTR_PROFILE_GROUP_ID, com.android.server.am.ProcessList.INVALID_ADJ);
            int attributeInt4 = resolvePullParser.getAttributeInt((java.lang.String) null, ATTR_PROFILE_BADGE, 0);
            int attributeInt5 = resolvePullParser.getAttributeInt((java.lang.String) null, ATTR_RESTRICTED_PROFILE_PARENT_ID, com.android.server.am.ProcessList.INVALID_ADJ);
            boolean attributeBoolean = resolvePullParser.getAttributeBoolean((java.lang.String) null, ATTR_PARTIAL, false);
            boolean attributeBoolean2 = resolvePullParser.getAttributeBoolean((java.lang.String) null, ATTR_PRE_CREATED, false);
            boolean attributeBoolean3 = resolvePullParser.getAttributeBoolean((java.lang.String) null, ATTR_CONVERTED_FROM_PRE_CREATED, false);
            boolean attributeBoolean4 = resolvePullParser.getAttributeBoolean((java.lang.String) null, ATTR_GUEST_TO_REMOVE, false);
            java.lang.String attributeValue4 = resolvePullParser.getAttributeValue((java.lang.String) null, ATTR_SEED_ACCOUNT_NAME);
            java.lang.String attributeValue5 = resolvePullParser.getAttributeValue((java.lang.String) null, ATTR_SEED_ACCOUNT_TYPE);
            boolean z7 = (attributeValue4 == null && attributeValue5 == null) ? false : true;
            int depth = resolvePullParser.getDepth();
            long j5 = 0;
            java.lang.String str9 = null;
            java.lang.String str10 = null;
            android.os.PersistableBundle persistableBundle2 = null;
            android.content.pm.UserProperties userProperties2 = null;
            android.os.Bundle bundle5 = null;
            android.os.Bundle bundle6 = null;
            android.os.Bundle bundle7 = null;
            android.os.Bundle bundle8 = null;
            z5 = true;
            while (true) {
                str8 = attributeValue5;
                int next2 = resolvePullParser.next();
                i9 = attributeInt3;
                if (next2 == 1 || (next2 == 3 && resolvePullParser.getDepth() <= depth)) {
                    break;
                }
                if (next2 == 3 || next2 == 4) {
                    attributeValue5 = str8;
                    attributeInt3 = i9;
                } else {
                    java.lang.String name = resolvePullParser.getName();
                    if ("name".equals(name)) {
                        if (resolvePullParser.next() == 4) {
                            str9 = resolvePullParser.getText();
                        }
                    } else if (TAG_RESTRICTIONS.equals(name)) {
                        bundle5 = com.android.server.pm.UserRestrictionsUtils.readRestrictions(resolvePullParser);
                    } else if (TAG_DEVICE_POLICY_RESTRICTIONS.equals(name)) {
                        bundle6 = com.android.server.pm.UserRestrictionsUtils.readRestrictions(resolvePullParser);
                    } else if (TAG_DEVICE_POLICY_LOCAL_RESTRICTIONS.equals(name)) {
                        if (i2 < 10) {
                            bundle7 = com.android.server.pm.RestrictionsSet.readRestrictions(resolvePullParser, TAG_DEVICE_POLICY_LOCAL_RESTRICTIONS).mergeAll();
                        } else {
                            bundle7 = com.android.server.pm.UserRestrictionsUtils.readRestrictions(resolvePullParser);
                        }
                    } else if (TAG_DEVICE_POLICY_GLOBAL_RESTRICTIONS.equals(name)) {
                        bundle8 = com.android.server.pm.UserRestrictionsUtils.readRestrictions(resolvePullParser);
                    } else if (TAG_GUEST_RESTRICTIONS.equals(name)) {
                        while (true) {
                            int next3 = resolvePullParser.next();
                            if (next3 == 1 || next3 == 3) {
                                break;
                            }
                            if (next3 == 2) {
                                break;
                            }
                        }
                    } else if (TAG_ACCOUNT.equals(name)) {
                        if (resolvePullParser.next() == 4) {
                            str10 = resolvePullParser.getText();
                        }
                    } else if (TAG_SEED_ACCOUNT_OPTIONS.equals(name)) {
                        persistableBundle2 = android.os.PersistableBundle.restoreFromXml(resolvePullParser);
                        z7 = true;
                    } else if (TAG_USER_PROPERTIES.equals(name)) {
                        com.android.server.pm.UserTypeDetails userTypeDetails = this.mUserTypes.get(intern);
                        if (userTypeDetails == null) {
                            android.util.Slog.e(LOG_TAG, "User has properties but no user type!");
                            return null;
                        }
                        userProperties2 = new android.content.pm.UserProperties(resolvePullParser, userTypeDetails.getDefaultUserPropertiesReference());
                    } else if (TAG_LAST_REQUEST_QUIET_MODE_ENABLED_CALL.equals(name)) {
                        if (resolvePullParser.next() == 4) {
                            j5 = java.lang.Long.parseLong(resolvePullParser.getText());
                        }
                    } else if (TAG_IGNORE_PREPARE_STORAGE_ERRORS.equals(name) && resolvePullParser.next() == 4) {
                        z5 = java.lang.Boolean.parseBoolean(resolvePullParser.getText());
                    }
                    attributeValue5 = str8;
                    attributeInt3 = i9;
                }
            }
            z2 = attributeBoolean;
            i5 = attributeInt5;
            i6 = attributeInt2;
            str7 = intern;
            j = attributeLong;
            j4 = attributeLong3;
            j3 = j5;
            z = attributeBoolean3;
            z6 = z7;
            z4 = attributeBoolean4;
            str = str9;
            str6 = str10;
            userProperties = userProperties2;
            bundle = bundle5;
            bundle3 = bundle7;
            bundle2 = bundle8;
            str5 = str8;
            i4 = i9;
            z3 = attributeBoolean2;
            i7 = attributeInt4;
            str2 = attributeValue2;
            j2 = attributeLong2;
            persistableBundle = persistableBundle2;
            str4 = attributeValue4;
            str3 = attributeValue3;
            i3 = attributeInt;
            bundle4 = bundle6;
        } else {
            android.util.Slog.e(LOG_TAG, "User id does not match the file name");
            return null;
        }
        int i10 = i4;
        android.os.Bundle bundle9 = bundle2;
        android.os.Bundle bundle10 = bundle3;
        android.os.Bundle bundle11 = bundle4;
        android.content.pm.UserInfo userInfo = new android.content.pm.UserInfo(i, str, str2, i6, str7);
        userInfo.serialNumber = i3;
        userInfo.creationTime = j;
        userInfo.lastLoggedInTime = j2;
        userInfo.lastLoggedInFingerprint = str3;
        userInfo.partial = z2;
        userInfo.preCreated = z3;
        userInfo.convertedFromPreCreated = z;
        userInfo.guestToRemove = z4;
        userInfo.profileGroupId = i10;
        userInfo.profileBadge = i7;
        userInfo.restrictedProfileParentId = i5;
        com.android.server.pm.UserManagerService.UserData userData = new com.android.server.pm.UserManagerService.UserData();
        userData.info = userInfo;
        userData.account = str6;
        userData.seedAccountName = str4;
        userData.seedAccountType = str5;
        userData.persistSeedData = z6;
        userData.seedAccountOptions = persistableBundle;
        userData.userProperties = userProperties;
        userData.setLastRequestQuietModeEnabledMillis(j3);
        userData.mLastEnteredForegroundTimeMillis = j4;
        if (z5) {
            userData.setIgnorePrepareStorageErrors();
        }
        synchronized (this.mRestrictionsLock) {
            if (bundle == null) {
                i8 = i;
            } else {
                try {
                    i8 = i;
                    this.mBaseUserRestrictions.updateRestrictions(i8, bundle);
                } finally {
                }
            }
            if (bundle10 != null) {
                this.mDevicePolicyUserRestrictions.updateRestrictions(i8, bundle10);
                if (bundle11 != null) {
                    android.util.Slog.wtf(LOG_TAG, "Seeing both legacy and current local restrictions in xml");
                }
            } else if (bundle11 != null) {
                this.mDevicePolicyUserRestrictions.updateRestrictions(i8, bundle11);
            }
            if (bundle9 != null) {
                this.mDevicePolicyUserRestrictions.updateRestrictions(-1, bundle9);
            }
        }
        return userData;
    }

    @com.android.internal.annotations.GuardedBy({"mAppRestrictionsLock"})
    private static boolean cleanAppRestrictionsForPackageLAr(java.lang.String str, int i) {
        java.io.File file = new java.io.File(android.os.Environment.getUserSystemDirectory(i), packageToRestrictionsFileName(str));
        if (file.exists()) {
            file.delete();
            return true;
        }
        return false;
    }

    @android.annotation.NonNull
    public android.content.pm.UserInfo createProfileForUserWithThrow(@android.annotation.Nullable java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i, int i2, @android.annotation.Nullable java.lang.String[] strArr) throws android.os.ServiceSpecificException {
        checkCreateUsersPermission(i);
        try {
            return createUserInternal(str, str2, i, i2, strArr);
        } catch (android.os.UserManager.CheckedUserOperationException e) {
            throw e.toServiceSpecificException();
        }
    }

    @android.annotation.NonNull
    public android.content.pm.UserInfo createProfileForUserEvenWhenDisallowedWithThrow(@android.annotation.Nullable java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i, int i2, @android.annotation.Nullable java.lang.String[] strArr) throws android.os.ServiceSpecificException {
        checkCreateUsersPermission(i);
        try {
            return createUserInternalUnchecked(str, str2, i, i2, false, strArr, null);
        } catch (android.os.UserManager.CheckedUserOperationException e) {
            throw e.toServiceSpecificException();
        }
    }

    @android.annotation.NonNull
    public android.content.pm.UserInfo createUserWithThrow(@android.annotation.Nullable java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i) throws android.os.ServiceSpecificException {
        checkCreateUsersPermission(i);
        try {
            return createUserInternal(str, str2, i, com.android.server.am.ProcessList.INVALID_ADJ, null);
        } catch (android.os.UserManager.CheckedUserOperationException e) {
            throw e.toServiceSpecificException();
        }
    }

    @android.annotation.NonNull
    public android.content.pm.UserInfo preCreateUserWithThrow(@android.annotation.NonNull java.lang.String str) throws android.os.ServiceSpecificException {
        com.android.server.pm.UserTypeDetails userTypeDetails = this.mUserTypes.get(str);
        int defaultUserInfoFlags = userTypeDetails != null ? userTypeDetails.getDefaultUserInfoFlags() : 0;
        checkCreateUsersPermission(defaultUserInfoFlags);
        com.android.internal.util.Preconditions.checkArgument(isUserTypeEligibleForPreCreation(userTypeDetails), "cannot pre-create user of type " + str);
        android.util.Slog.i(LOG_TAG, "Pre-creating user of type " + str);
        try {
            return createUserInternalUnchecked(null, str, defaultUserInfoFlags, com.android.server.am.ProcessList.INVALID_ADJ, true, null, null);
        } catch (android.os.UserManager.CheckedUserOperationException e) {
            throw e.toServiceSpecificException();
        }
    }

    @android.annotation.NonNull
    public android.os.UserHandle createUserWithAttributes(@android.annotation.Nullable java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i, @android.annotation.Nullable android.graphics.Bitmap bitmap, @android.annotation.Nullable java.lang.String str3, @android.annotation.Nullable java.lang.String str4, @android.annotation.Nullable android.os.PersistableBundle persistableBundle) throws android.os.ServiceSpecificException {
        checkCreateUsersPermission(i);
        if (someUserHasAccountNoChecks(str3, str4)) {
            throw new android.os.ServiceSpecificException(7);
        }
        try {
            android.content.pm.UserInfo createUserInternal = createUserInternal(str, str2, i, com.android.server.am.ProcessList.INVALID_ADJ, null);
            if (bitmap != null) {
                this.mLocalService.setUserIcon(createUserInternal.id, bitmap);
            }
            setSeedAccountDataNoChecks(createUserInternal.id, str3, str4, persistableBundle, true);
            return createUserInternal.getUserHandle();
        } catch (android.os.UserManager.CheckedUserOperationException e) {
            throw e.toServiceSpecificException();
        }
    }

    @android.annotation.NonNull
    private android.content.pm.UserInfo createUserInternal(@android.annotation.Nullable java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i, int i2, @android.annotation.Nullable java.lang.String[] strArr) throws android.os.UserManager.CheckedUserOperationException {
        java.lang.String str3;
        if (android.os.UserManager.isUserTypeCloneProfile(str2)) {
            str3 = "no_add_clone_profile";
        } else if (android.os.UserManager.isUserTypeManagedProfile(str2)) {
            str3 = "no_add_managed_profile";
        } else if (!android.os.UserManager.isUserTypePrivateProfile(str2)) {
            str3 = "no_add_user";
        } else {
            str3 = "no_add_private_profile";
        }
        enforceUserRestriction(str3, android.os.UserHandle.getCallingUserId(), "Cannot add user");
        return createUserInternalUnchecked(str, str2, i, i2, false, strArr, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    public android.content.pm.UserInfo createUserInternalUnchecked(@android.annotation.Nullable java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i, int i2, boolean z, @android.annotation.Nullable java.lang.String[] strArr, @android.annotation.Nullable java.lang.Object obj) throws android.os.UserManager.CheckedUserOperationException {
        com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog();
        timingsTraceAndSlog.traceBegin("createUser-" + i);
        this.mUserJourneyLogger.logUserJourneyBegin(-1, 4);
        try {
            android.content.pm.UserInfo createUserInternalUncheckedNoTracing = createUserInternalUncheckedNoTracing(str, str2, i, i2, z, strArr, timingsTraceAndSlog, obj);
            if (createUserInternalUncheckedNoTracing != null) {
                this.mUserJourneyLogger.logUserCreateJourneyFinish(getCurrentUserId(), createUserInternalUncheckedNoTracing);
            } else {
                this.mUserJourneyLogger.logNullUserJourneyError(4, getCurrentUserId(), -1, str2, i);
            }
            timingsTraceAndSlog.traceEnd();
            return createUserInternalUncheckedNoTracing;
        } catch (java.lang.Throwable th) {
            this.mUserJourneyLogger.logNullUserJourneyError(4, getCurrentUserId(), -1, str2, i);
            timingsTraceAndSlog.traceEnd();
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @android.annotation.NonNull
    private android.content.pm.UserInfo createUserInternalUncheckedNoTracing(@android.annotation.Nullable java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i, int i2, boolean z, @android.annotation.Nullable java.lang.String[] strArr, @android.annotation.NonNull com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog, @android.annotation.Nullable java.lang.Object obj) throws android.os.UserManager.CheckedUserOperationException {
        com.android.server.pm.UserManagerService.UserData userDataLU;
        com.android.server.pm.UserManagerService.UserData userData;
        int nextAvailableId;
        android.content.pm.UserInfo userInfo;
        boolean z2;
        com.android.server.pm.UserManagerService.UserData userData2;
        android.content.pm.UserInfo convertPreCreatedUserIfPossible;
        java.lang.String truncateString = truncateString(str, 100);
        com.android.server.pm.UserTypeDetails userTypeDetails = this.mUserTypes.get(str2);
        if (userTypeDetails == null) {
            throwCheckedUserOperationException("Cannot create user of invalid user type: " + str2, 1);
        }
        java.lang.String intern = str2.intern();
        int defaultUserInfoFlags = i | userTypeDetails.getDefaultUserInfoFlags();
        if (!checkUserTypeConsistency(defaultUserInfoFlags)) {
            throwCheckedUserOperationException("Cannot add user. Flags (" + java.lang.Integer.toHexString(defaultUserInfoFlags) + ") and userTypeDetails (" + intern + ") are inconsistent.", 1);
        }
        if ((defaultUserInfoFlags & 2048) != 0) {
            throwCheckedUserOperationException("Cannot add user. Flags (" + java.lang.Integer.toHexString(defaultUserInfoFlags) + ") indicated SYSTEM user, which cannot be created.", 1);
        }
        if (!isUserTypeEnabled(userTypeDetails)) {
            throwCheckedUserOperationException("Cannot add a user of disabled type " + intern + ".", 6);
        }
        synchronized (this.mUsersLock) {
            if (this.mForceEphemeralUsers) {
                defaultUserInfoFlags |= 256;
            }
        }
        if (!z && i2 < 0 && isUserTypeEligibleForPreCreation(userTypeDetails) && (convertPreCreatedUserIfPossible = convertPreCreatedUserIfPossible(intern, defaultUserInfoFlags, truncateString, obj)) != null) {
            return convertPreCreatedUserIfPossible;
        }
        if (((com.android.server.storage.DeviceStorageMonitorInternal) com.android.server.LocalServices.getService(com.android.server.storage.DeviceStorageMonitorInternal.class)).isMemoryLow()) {
            throwCheckedUserOperationException("Cannot add user. Not enough space on disk.", 5);
        }
        boolean z3 = (defaultUserInfoFlags & 16384) != 0;
        boolean isProfile = userTypeDetails.isProfile();
        boolean isUserTypeGuest = android.os.UserManager.isUserTypeGuest(intern);
        boolean isUserTypeRestricted = android.os.UserManager.isUserTypeRestricted(intern);
        boolean isUserTypeDemo = android.os.UserManager.isUserTypeDemo(intern);
        boolean isUserTypeManagedProfile = android.os.UserManager.isUserTypeManagedProfile(intern);
        boolean isUserTypeCommunalProfile = android.os.UserManager.isUserTypeCommunalProfile(intern);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mPackagesLock) {
                if (i2 != -10000) {
                    synchronized (this.mUsersLock) {
                        userDataLU = getUserDataLU(i2);
                    }
                    if (userDataLU == null) {
                        throwCheckedUserOperationException("Cannot find user data for parent user " + i2, 1);
                    }
                    userData = userDataLU;
                } else {
                    userDataLU = null;
                    userData = null;
                }
                if (z3 && getMainUserIdUnchecked() != -10000) {
                    throwCheckedUserOperationException("Cannot add user with FLAG_MAIN as main user already exists.", 6);
                }
                if (!z && !canAddMoreUsersOfType(userTypeDetails)) {
                    throwCheckedUserOperationException("Cannot add more users of type " + intern + ". Maximum number of that type already exists.", 6);
                }
                if (!isUserTypeGuest && !isUserTypeManagedProfile && !isUserTypeDemo && isUserLimitReached()) {
                    throwCheckedUserOperationException("Cannot add user. Maximum user limit is reached.", 6);
                }
                if (isProfile && !isUserTypeCommunalProfile && !canAddMoreProfilesToUser(intern, i2, false)) {
                    throwCheckedUserOperationException("Cannot add more profiles of type " + intern + " for user " + i2, 6);
                }
                if (isUserTypeRestricted && i2 != 0 && !isCreationOverrideEnabled()) {
                    throwCheckedUserOperationException("Cannot add restricted profile - parent user must be system", 1);
                }
                nextAvailableId = getNextAvailableId();
                android.util.Slog.i(LOG_TAG, "Creating user " + nextAvailableId + " of type " + intern);
                android.os.Environment.getUserSystemDirectory(nextAvailableId).mkdirs();
                java.lang.Object obj2 = this.mUsersLock;
                synchronized (obj2) {
                    try {
                        if (userData != null) {
                            try {
                                if (userData.info.isEphemeral()) {
                                    defaultUserInfoFlags |= 256;
                                }
                            } catch (java.lang.Throwable th) {
                                th = th;
                                userDataLU = obj2;
                                throw th;
                            }
                        }
                        if (z) {
                            defaultUserInfoFlags &= -257;
                        }
                        z2 = z3;
                        userInfo = new android.content.pm.UserInfo(nextAvailableId, truncateString, (java.lang.String) null, (defaultUserInfoFlags & 256) != 0 ? defaultUserInfoFlags | 8192 : defaultUserInfoFlags, intern);
                        int i3 = this.mNextSerialNumber;
                        this.mNextSerialNumber = i3 + 1;
                        userInfo.serialNumber = i3;
                        userInfo.creationTime = getCreationTime();
                        userInfo.partial = true;
                        userInfo.preCreated = z;
                        userInfo.lastLoggedInFingerprint = android.content.pm.PackagePartitions.FINGERPRINT;
                        if (userTypeDetails.hasBadge() && i2 != -10000) {
                            userInfo.profileBadge = getFreeProfileBadgeLU(i2, intern);
                        }
                        userData2 = new com.android.server.pm.UserManagerService.UserData();
                        userData2.info = userInfo;
                        userData2.userProperties = new android.content.pm.UserProperties(userTypeDetails.getDefaultUserPropertiesReference());
                        this.mUsers.put(nextAvailableId, userData2);
                    } catch (java.lang.Throwable th2) {
                        th = th2;
                    }
                }
                writeUserLP(userData2);
                writeUserListLP();
                if (userData != null) {
                    if (isProfile) {
                        if (userData.info.profileGroupId == -10000) {
                            userData.info.profileGroupId = userData.info.id;
                            writeUserLP(userData);
                        }
                        userInfo.profileGroupId = userData.info.profileGroupId;
                    } else if (isUserTypeRestricted) {
                        if (userData.info.restrictedProfileParentId == -10000) {
                            userData.info.restrictedProfileParentId = userData.info.id;
                            writeUserLP(userData);
                        }
                        userInfo.restrictedProfileParentId = userData.info.restrictedProfileParentId;
                    }
                }
            }
            timingsTraceAndSlog.traceBegin("createUserStorageKeys");
            ((android.os.storage.StorageManager) this.mContext.getSystemService(android.os.storage.StorageManager.class)).createUserStorageKeys(nextAvailableId, userInfo.isEphemeral());
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("prepareUserData");
            this.mUserDataPreparer.prepareUserData(userInfo, 1);
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("LSS.createNewUser");
            this.mLockPatternUtils.createNewUser(nextAvailableId, userInfo.serialNumber);
            timingsTraceAndSlog.traceEnd();
            java.util.Set<java.lang.String> installablePackagesForUserType = this.mSystemPackageInstaller.getInstallablePackagesForUserType(intern);
            timingsTraceAndSlog.traceBegin("PM.createNewUser");
            this.mPm.createNewUser(nextAvailableId, installablePackagesForUserType, strArr);
            timingsTraceAndSlog.traceEnd();
            android.os.Bundle bundle = new android.os.Bundle();
            if (isUserTypeGuest) {
                synchronized (this.mGuestRestrictions) {
                    bundle.putAll(this.mGuestRestrictions);
                }
            } else {
                userTypeDetails.addDefaultRestrictionsTo(bundle);
                if (z2) {
                    bundle.remove("no_outgoing_calls");
                    bundle.remove("no_sms");
                }
            }
            synchronized (this.mRestrictionsLock) {
                this.mBaseUserRestrictions.updateRestrictions(nextAvailableId, bundle);
            }
            userInfo.partial = false;
            synchronized (this.mPackagesLock) {
                writeUserLP(userData2);
            }
            updateUserIds();
            timingsTraceAndSlog.traceBegin("PM.onNewUserCreated-" + nextAvailableId);
            this.mPm.onNewUserCreated(nextAvailableId, false);
            timingsTraceAndSlog.traceEnd();
            applyDefaultUserSettings(userTypeDetails, nextAvailableId);
            setDefaultCrossProfileIntentFilters(nextAvailableId, userTypeDetails, bundle, i2);
            if (z) {
                android.util.Slog.i(LOG_TAG, "starting pre-created user " + userInfo.toFullString());
                try {
                    android.app.ActivityManager.getService().startUserInBackground(nextAvailableId);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(LOG_TAG, "could not start pre-created user " + nextAvailableId, e);
                }
            } else {
                dispatchUserAdded(userInfo, obj);
            }
            return userInfo;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void applyDefaultUserSettings(com.android.server.pm.UserTypeDetails userTypeDetails, int i) {
        android.os.Bundle defaultSystemSettings = userTypeDetails.getDefaultSystemSettings();
        android.os.Bundle defaultSecureSettings = userTypeDetails.getDefaultSecureSettings();
        if (defaultSystemSettings.isEmpty() && defaultSecureSettings.isEmpty()) {
            return;
        }
        int size = defaultSystemSettings.size();
        java.lang.String[] strArr = (java.lang.String[]) defaultSystemSettings.keySet().toArray(new java.lang.String[size]);
        for (int i2 = 0; i2 < size; i2++) {
            java.lang.String str = strArr[i2];
            if (!android.provider.Settings.System.putStringForUser(this.mContext.getContentResolver(), str, defaultSystemSettings.getString(str), i)) {
                android.util.Slog.e(LOG_TAG, "Failed to insert default system setting: " + str);
            }
        }
        int size2 = defaultSecureSettings.size();
        java.lang.String[] strArr2 = (java.lang.String[]) defaultSecureSettings.keySet().toArray(new java.lang.String[size2]);
        for (int i3 = 0; i3 < size2; i3++) {
            java.lang.String str2 = strArr2[i3];
            if (!android.provider.Settings.Secure.putStringForUser(this.mContext.getContentResolver(), str2, defaultSecureSettings.getString(str2), i)) {
                android.util.Slog.e(LOG_TAG, "Failed to insert default secure setting: " + str2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDefaultCrossProfileIntentFilters(int i, com.android.server.pm.UserTypeDetails userTypeDetails, android.os.Bundle bundle, int i2) {
        if (userTypeDetails == null || !userTypeDetails.isProfile() || userTypeDetails.getDefaultCrossProfileIntentFilters().isEmpty()) {
            return;
        }
        boolean z = bundle.getBoolean("no_sharing_into_profile", false);
        int size = userTypeDetails.getDefaultCrossProfileIntentFilters().size();
        for (int i3 = 0; i3 < size; i3++) {
            com.android.server.pm.DefaultCrossProfileIntentFilter defaultCrossProfileIntentFilter = userTypeDetails.getDefaultCrossProfileIntentFilters().get(i3);
            if (!z || !defaultCrossProfileIntentFilter.letsPersonalDataIntoProfile) {
                if (defaultCrossProfileIntentFilter.direction == 0) {
                    this.mPm.addCrossProfileIntentFilter(this.mPm.snapshotComputer(), defaultCrossProfileIntentFilter.filter, this.mContext.getOpPackageName(), i, i2, defaultCrossProfileIntentFilter.flags);
                } else {
                    this.mPm.addCrossProfileIntentFilter(this.mPm.snapshotComputer(), defaultCrossProfileIntentFilter.filter, this.mContext.getOpPackageName(), i2, i, defaultCrossProfileIntentFilter.flags);
                }
            }
        }
    }

    @android.annotation.Nullable
    private android.content.pm.UserInfo convertPreCreatedUserIfPossible(java.lang.String str, int i, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable final java.lang.Object obj) {
        com.android.server.pm.UserManagerService.UserData preCreatedUserLU;
        synchronized (this.mUsersLock) {
            preCreatedUserLU = getPreCreatedUserLU(str);
        }
        if (preCreatedUserLU == null) {
            return null;
        }
        synchronized (this.mUserStates) {
            try {
                if (this.mUserStates.has(preCreatedUserLU.info.id)) {
                    android.util.Slog.w(LOG_TAG, "Cannot reuse pre-created user " + preCreatedUserLU.info.id + " because it didn't stop yet");
                    return null;
                }
                final android.content.pm.UserInfo userInfo = preCreatedUserLU.info;
                int i2 = userInfo.flags | i;
                if (!checkUserTypeConsistency(i2)) {
                    android.util.Slog.wtf(LOG_TAG, "Cannot reuse pre-created user " + userInfo.id + " of type " + str + " because flags are inconsistent. Flags (" + java.lang.Integer.toHexString(i) + "); preCreatedUserFlags ( " + java.lang.Integer.toHexString(userInfo.flags) + ").");
                    return null;
                }
                android.util.Slog.i(LOG_TAG, "Reusing pre-created user " + userInfo.id + " of type " + str + " and bestowing on it flags " + android.content.pm.UserInfo.flagsToString(i));
                userInfo.name = str2;
                userInfo.flags = i2;
                userInfo.preCreated = false;
                userInfo.convertedFromPreCreated = true;
                userInfo.creationTime = getCreationTime();
                synchronized (this.mPackagesLock) {
                    writeUserLP(preCreatedUserLU);
                    writeUserListLP();
                }
                updateUserIds();
                android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.pm.UserManagerService$$ExternalSyntheticLambda8
                    public final void runOrThrow() {
                        com.android.server.pm.UserManagerService.this.lambda$convertPreCreatedUserIfPossible$7(userInfo, obj);
                    }
                });
                return userInfo;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convertPreCreatedUserIfPossible$7(android.content.pm.UserInfo userInfo, java.lang.Object obj) throws java.lang.Exception {
        this.mPm.onNewUserCreated(userInfo.id, true);
        dispatchUserAdded(userInfo, obj);
        android.service.voice.VoiceInteractionManagerInternal voiceInteractionManagerInternal = (android.service.voice.VoiceInteractionManagerInternal) com.android.server.LocalServices.getService(android.service.voice.VoiceInteractionManagerInternal.class);
        if (voiceInteractionManagerInternal != null) {
            voiceInteractionManagerInternal.onPreCreatedUserConversion(userInfo.id);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static boolean checkUserTypeConsistency(int i) {
        return isAtMostOneFlag(i & 4620) && isAtMostOneFlag(i & 5120) && isAtMostOneFlag(i & 6144);
    }

    private static boolean isAtMostOneFlag(int i) {
        return (i & (i + (-1))) == 0;
    }

    boolean installWhitelistedSystemPackages(boolean z, boolean z2, @android.annotation.Nullable android.util.ArraySet<java.lang.String> arraySet) {
        return this.mSystemPackageInstaller.installWhitelistedSystemPackages(z || this.mUpdatingSystemUserMode, z2, arraySet);
    }

    public java.lang.String[] getPreInstallableSystemPackages(@android.annotation.NonNull java.lang.String str) {
        checkCreateUsersPermission("getPreInstallableSystemPackages");
        java.util.Set<java.lang.String> installablePackagesForUserType = this.mSystemPackageInstaller.getInstallablePackagesForUserType(str);
        if (installablePackagesForUserType == null) {
            return null;
        }
        return (java.lang.String[]) installablePackagesForUserType.toArray(new java.lang.String[installablePackagesForUserType.size()]);
    }

    private long getCreationTime() {
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        if (currentTimeMillis > EPOCH_PLUS_30_YEARS) {
            return currentTimeMillis;
        }
        return 0L;
    }

    private void dispatchUserAdded(@android.annotation.NonNull android.content.pm.UserInfo userInfo, @android.annotation.Nullable java.lang.Object obj) {
        java.lang.String str;
        synchronized (this.mUserLifecycleListeners) {
            for (int i = 0; i < this.mUserLifecycleListeners.size(); i++) {
                try {
                    this.mUserLifecycleListeners.get(i).onUserCreated(userInfo, obj);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        android.content.Intent intent = new android.content.Intent("android.intent.action.USER_ADDED");
        intent.addFlags(16777216);
        intent.addFlags(67108864);
        intent.putExtra("android.intent.extra.user_handle", userInfo.id);
        intent.putExtra("android.intent.extra.USER", android.os.UserHandle.of(userInfo.id));
        this.mContext.sendBroadcastAsUser(intent, android.os.UserHandle.ALL, "android.permission.MANAGE_USERS");
        android.content.Context context = this.mContext;
        if (userInfo.isGuest()) {
            str = TRON_GUEST_CREATED;
        } else {
            str = userInfo.isDemo() ? TRON_DEMO_CREATED : TRON_USER_CREATED;
        }
        com.android.internal.logging.MetricsLogger.count(context, str, 1);
        if (userInfo.isProfile()) {
            sendProfileAddedBroadcast(userInfo.profileGroupId, userInfo.id);
        } else if (android.provider.Settings.Global.getString(this.mContext.getContentResolver(), "user_switcher_enabled") == null) {
            android.provider.Settings.Global.putInt(this.mContext.getContentResolver(), "user_switcher_enabled", 1);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUsersLock"})
    @android.annotation.Nullable
    private com.android.server.pm.UserManagerService.UserData getPreCreatedUserLU(java.lang.String str) {
        int size = this.mUsers.size();
        for (int i = 0; i < size; i++) {
            com.android.server.pm.UserManagerService.UserData valueAt = this.mUsers.valueAt(i);
            if (valueAt.info.preCreated && !valueAt.info.partial && valueAt.info.userType.equals(str)) {
                if (!valueAt.info.isInitialized()) {
                    android.util.Slog.w(LOG_TAG, "found pre-created user of type " + str + ", but it's not initialized yet: " + valueAt.info.toFullString());
                } else {
                    return valueAt;
                }
            }
        }
        return null;
    }

    private static boolean isUserTypeEligibleForPreCreation(com.android.server.pm.UserTypeDetails userTypeDetails) {
        return (userTypeDetails == null || userTypeDetails.isProfile() || userTypeDetails.getName().equals("android.os.usertype.full.RESTRICTED")) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerStatsCallbacks() {
        android.app.StatsManager statsManager = (android.app.StatsManager) this.mContext.getSystemService(android.app.StatsManager.class);
        statsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.USER_INFO, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, new android.app.StatsManager.StatsPullAtomCallback() { // from class: com.android.server.pm.UserManagerService$$ExternalSyntheticLambda7
            public final int onPullAtom(int i, java.util.List list) {
                int onPullAtom;
                onPullAtom = com.android.server.pm.UserManagerService.this.onPullAtom(i, list);
                return onPullAtom;
            }
        });
        statsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.MULTI_USER_INFO, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, new android.app.StatsManager.StatsPullAtomCallback() { // from class: com.android.server.pm.UserManagerService$$ExternalSyntheticLambda7
            public final int onPullAtom(int i, java.util.List list) {
                int onPullAtom;
                onPullAtom = com.android.server.pm.UserManagerService.this.onPullAtom(i, list);
                return onPullAtom;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int onPullAtom(int i, java.util.List<android.util.StatsEvent> list) {
        boolean z;
        int i2 = -1;
        if (i != 10152) {
            if (i == 10160) {
                if (android.os.UserManager.getMaxSupportedUsers() > 1) {
                    list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(com.android.internal.util.FrameworkStatsLog.MULTI_USER_INFO, android.os.UserManager.getMaxSupportedUsers(), isUserSwitcherEnabled(-1), android.os.UserManager.supportsMultipleUsers() && !hasUserRestriction("no_add_user", -1)));
                    return 0;
                }
                return 0;
            }
            com.android.server.utils.Slogf.e(LOG_TAG, "Unexpected atom tag: %d", java.lang.Integer.valueOf(i));
            return 1;
        }
        java.util.List<android.content.pm.UserInfo> usersInternal = getUsersInternal(true, true, true);
        int size = usersInternal.size();
        if (size > 1) {
            int i3 = 0;
            while (i3 < size) {
                android.content.pm.UserInfo userInfo = usersInternal.get(i3);
                int userTypeForStatsd = com.android.server.pm.UserJourneyLogger.getUserTypeForStatsd(userInfo.userType);
                java.lang.String str = userTypeForStatsd == 0 ? userInfo.userType : null;
                synchronized (this.mUserStates) {
                    z = this.mUserStates.get(userInfo.id, i2) == 3;
                }
                list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(com.android.internal.util.FrameworkStatsLog.USER_INFO, userInfo.id, userTypeForStatsd, str, userInfo.flags, userInfo.creationTime, userInfo.lastLoggedInTime, z));
                i3++;
                i2 = -1;
            }
            return 0;
        }
        return 0;
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.pm.UserManagerService.UserData putUserInfo(android.content.pm.UserInfo userInfo) {
        com.android.server.pm.UserManagerService.UserData userData = new com.android.server.pm.UserManagerService.UserData();
        userData.info = userInfo;
        synchronized (this.mUsersLock) {
            this.mUsers.put(userInfo.id, userData);
        }
        updateUserIds();
        return userData;
    }

    @com.android.internal.annotations.VisibleForTesting
    void removeUserInfo(int i) {
        synchronized (this.mUsersLock) {
            this.mUsers.remove(i);
        }
    }

    @android.annotation.NonNull
    public android.content.pm.UserInfo createRestrictedProfileWithThrow(@android.annotation.Nullable java.lang.String str, int i) throws android.os.ServiceSpecificException {
        checkCreateUsersPermission("setupRestrictedProfile");
        android.content.pm.UserInfo createProfileForUserWithThrow = createProfileForUserWithThrow(str, "android.os.usertype.full.RESTRICTED", 0, i, null);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            setUserRestriction("no_modify_accounts", true, createProfileForUserWithThrow.id);
            android.provider.Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "location_mode", 0, createProfileForUserWithThrow.id);
            setUserRestriction("no_share_location", true, createProfileForUserWithThrow.id);
            return createProfileForUserWithThrow;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public java.util.List<android.content.pm.UserInfo> getGuestUsers() {
        checkManageUsersPermission("getGuestUsers");
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mUsersLock) {
            try {
                int size = this.mUsers.size();
                for (int i = 0; i < size; i++) {
                    android.content.pm.UserInfo userInfo = this.mUsers.valueAt(i).info;
                    if (userInfo.isGuest() && !userInfo.guestToRemove && !userInfo.preCreated && !this.mRemovingUserIds.get(userInfo.id)) {
                        arrayList.add(userInfo);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public boolean markGuestForDeletion(int i) {
        checkManageUsersPermission("Only the system can remove users");
        if (getUserRestrictions(android.os.UserHandle.getCallingUserId()).getBoolean("no_remove_user", false)) {
            android.util.Slog.w(LOG_TAG, "Cannot remove user. DISALLOW_REMOVE_USER is enabled.");
            return false;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mPackagesLock) {
                synchronized (this.mUsersLock) {
                    com.android.server.pm.UserManagerService.UserData userData = this.mUsers.get(i);
                    if (i == 0 || userData == null || this.mRemovingUserIds.get(i)) {
                        return false;
                    }
                    if (!userData.info.isGuest()) {
                        return false;
                    }
                    userData.info.guestToRemove = true;
                    userData.info.flags |= 64;
                    writeUserLP(userData);
                    return true;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean removeUser(int i) {
        android.util.Slog.i(LOG_TAG, "removeUser u" + i);
        checkCreateUsersPermission("Only the system can remove users");
        java.lang.String userRemovalRestriction = getUserRemovalRestriction(i);
        if (getUserRestrictions(android.os.UserHandle.getCallingUserId()).getBoolean(userRemovalRestriction, false)) {
            android.util.Slog.w(LOG_TAG, "Cannot remove user. " + userRemovalRestriction + " is enabled.");
            return false;
        }
        return removeUserWithProfilesUnchecked(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean removeUserWithProfilesUnchecked(int i) {
        synchronized (this.mUsersLock) {
            try {
                int userRemovabilityLocked = getUserRemovabilityLocked(i, "removed");
                if (userRemovabilityLocked != 3) {
                    return android.os.UserManager.isRemoveResultSuccessful(userRemovabilityLocked);
                }
                boolean isProfile = this.mUsers.get(i).info.isProfile();
                android.util.IntArray profileIdsLU = isProfile ? null : getProfileIdsLU(i, null, false, false);
                if (!isProfile) {
                    android.util.Pair<java.lang.Integer, java.lang.Integer> currentAndTargetUserIds = getCurrentAndTargetUserIds();
                    if (i == ((java.lang.Integer) currentAndTargetUserIds.first).intValue()) {
                        android.util.Slog.w(LOG_TAG, "Current user cannot be removed.");
                        return false;
                    }
                    if (i == ((java.lang.Integer) currentAndTargetUserIds.second).intValue()) {
                        android.util.Slog.w(LOG_TAG, "Target user of an ongoing user switch cannot be removed.");
                        return false;
                    }
                    for (int size = profileIdsLU.size() - 1; size >= 0; size--) {
                        int i2 = profileIdsLU.get(size);
                        if (i2 != i) {
                            android.util.Slog.i(LOG_TAG, "removing profile:" + i2 + " associated with user:" + i);
                            if (removeUserUnchecked(i2)) {
                                continue;
                            } else {
                                android.util.Slog.i(LOG_TAG, "Unable to immediately remove profile " + i2 + "associated with user " + i + ". User is set as ephemeral and will be removed on user switch or reboot.");
                                synchronized (this.mPackagesLock) {
                                    com.android.server.pm.UserManagerService.UserData userDataNoChecks = getUserDataNoChecks(i);
                                    userDataNoChecks.info.flags |= 256;
                                    writeUserLP(userDataNoChecks);
                                }
                            }
                        }
                    }
                }
                return removeUserUnchecked(i);
            } finally {
            }
        }
    }

    public boolean removeUserEvenWhenDisallowed(int i) {
        checkCreateUsersPermission("Only the system can remove users");
        return removeUserWithProfilesUnchecked(i);
    }

    private java.lang.String getUserRemovalRestriction(int i) {
        android.content.pm.UserInfo userInfoLU;
        synchronized (this.mUsersLock) {
            userInfoLU = getUserInfoLU(i);
        }
        return userInfoLU != null && userInfoLU.isManagedProfile() ? "no_remove_managed_profile" : "no_remove_user";
    }

    private boolean removeUserUnchecked(int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mPackagesLock) {
                synchronized (this.mUsersLock) {
                    int userRemovabilityLocked = getUserRemovabilityLocked(i, "removed");
                    if (userRemovabilityLocked != 3) {
                        boolean isRemoveResultSuccessful = android.os.UserManager.isRemoveResultSuccessful(userRemovabilityLocked);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return isRemoveResultSuccessful;
                    }
                    final com.android.server.pm.UserManagerService.UserData userData = this.mUsers.get(i);
                    android.util.Slog.i(LOG_TAG, "Removing user " + i);
                    addRemovingUserIdLocked(i);
                    userData.info.partial = true;
                    userData.info.flags |= 64;
                    writeUserLP(userData);
                    this.mUserJourneyLogger.logUserJourneyBegin(i, 6);
                    this.mUserJourneyLogger.startSessionForDelayedJourney(i, 9, userData.info.creationTime);
                    try {
                        this.mAppOpsService.removeUser(i);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.w(LOG_TAG, "Unable to notify AppOpsService of removing user.", e);
                    }
                    if (userData.info.profileGroupId != -10000 && userData.info.isProfile()) {
                        sendProfileRemovedBroadcast(userData.info.profileGroupId, userData.info.id, userData.info.userType);
                    }
                    try {
                        boolean z = android.app.ActivityManager.getService().stopUser(i, true, new android.app.IStopUserCallback.Stub() { // from class: com.android.server.pm.UserManagerService.6
                            public void userStopped(int i2) {
                                com.android.server.pm.UserManagerService.this.finishRemoveUser(i2);
                                int currentUserId = com.android.server.pm.UserManagerService.this.getCurrentUserId();
                                com.android.server.pm.UserManagerService.this.mUserJourneyLogger.logUserJourneyFinishWithError(currentUserId, userData.info, 6, -1);
                                com.android.server.pm.UserManagerService.this.mUserJourneyLogger.logDelayedUserJourneyFinishWithError(currentUserId, userData.info, 9, -1);
                            }

                            public void userStopAborted(int i2) {
                                int currentUserId = com.android.server.pm.UserManagerService.this.getCurrentUserId();
                                com.android.server.pm.UserManagerService.this.mUserJourneyLogger.logUserJourneyFinishWithError(currentUserId, userData.info, 6, 3);
                                com.android.server.pm.UserManagerService.this.mUserJourneyLogger.logDelayedUserJourneyFinishWithError(currentUserId, userData.info, 9, 3);
                            }
                        }) == 0;
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return z;
                    } catch (android.os.RemoteException e2) {
                        android.util.Slog.w(LOG_TAG, "Failed to stop user during removal.", e2);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return false;
                    }
                }
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void addRemovingUserId(int i) {
        synchronized (this.mUsersLock) {
            addRemovingUserIdLocked(i);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUsersLock"})
    void addRemovingUserIdLocked(int i) {
        this.mRemovingUserIds.put(i, true);
        this.mRecentlyRemovedIds.add(java.lang.Integer.valueOf(i));
        if (this.mRecentlyRemovedIds.size() > 100) {
            this.mRecentlyRemovedIds.removeFirst();
        }
    }

    public int removeUserWhenPossible(int i, boolean z) {
        android.util.Slog.i(LOG_TAG, "removeUserWhenPossible u" + i);
        checkCreateUsersPermission("Only the system can remove users");
        if (!z) {
            java.lang.String userRemovalRestriction = getUserRemovalRestriction(i);
            if (getUserRestrictions(android.os.UserHandle.getCallingUserId()).getBoolean(userRemovalRestriction, false)) {
                android.util.Slog.w(LOG_TAG, "Cannot remove user. " + userRemovalRestriction + " is enabled.");
                return -2;
            }
        }
        android.util.Slog.i(LOG_TAG, "Attempting to immediately remove user " + i);
        if (removeUserWithProfilesUnchecked(i)) {
            return 0;
        }
        android.util.Slog.i(LOG_TAG, android.text.TextUtils.formatSimple("Unable to immediately remove user %d. Now trying to set it ephemeral.", new java.lang.Object[]{java.lang.Integer.valueOf(i)}));
        return setUserEphemeralUnchecked(i);
    }

    @com.android.internal.annotations.GuardedBy({"mUsersLock"})
    private int getUserRemovabilityLocked(int i, java.lang.String str) {
        java.lang.String formatSimple = android.text.TextUtils.formatSimple("User %d can not be %s, ", new java.lang.Object[]{java.lang.Integer.valueOf(i), str});
        if (i == 0) {
            android.util.Slog.e(LOG_TAG, formatSimple + "system user cannot be removed.");
            return -4;
        }
        com.android.server.pm.UserManagerService.UserData userData = this.mUsers.get(i);
        if (userData == null) {
            android.util.Slog.e(LOG_TAG, formatSimple + "invalid user id provided.");
            return -3;
        }
        if (isNonRemovableMainUser(userData.info)) {
            android.util.Slog.e(LOG_TAG, formatSimple + "main user cannot be removed when it's a permanent admin user.");
            return -5;
        }
        if (this.mRemovingUserIds.get(i)) {
            android.util.Slog.w(LOG_TAG, formatSimple + "it is already scheduled for removal.");
            return 2;
        }
        return 3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishRemoveUser(int i) {
        android.content.pm.UserInfo userInfoLU;
        android.util.Slog.i(LOG_TAG, "finishRemoveUser " + i);
        synchronized (this.mUsersLock) {
            userInfoLU = getUserInfoLU(i);
        }
        if (userInfoLU != null && userInfoLU.preCreated) {
            android.util.Slog.i(LOG_TAG, "Removing a pre-created user with user id: " + i);
            ((com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class)).onUserStopped(i);
            removeUserState(i);
            return;
        }
        synchronized (this.mUserLifecycleListeners) {
            for (int i2 = 0; i2 < this.mUserLifecycleListeners.size(); i2++) {
                try {
                    this.mUserLifecycleListeners.get(i2).onUserRemoved(userInfoLU);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.content.Intent intent = new android.content.Intent("android.intent.action.USER_REMOVED");
            intent.addFlags(16777216);
            intent.putExtra("android.intent.extra.user_handle", i);
            intent.putExtra("android.intent.extra.USER", android.os.UserHandle.of(i));
            getActivityManagerInternal().broadcastIntentWithCallback(intent, new com.android.server.pm.UserManagerService.AnonymousClass7(i), new java.lang.String[]{"android.permission.MANAGE_USERS"}, -1, (int[]) null, (java.util.function.BiFunction) null, (android.os.Bundle) null);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* renamed from: com.android.server.pm.UserManagerService$7, reason: invalid class name */
    class AnonymousClass7 extends android.content.IIntentReceiver.Stub {
        final /* synthetic */ int val$userId;

        AnonymousClass7(int i) {
            this.val$userId = i;
        }

        public void performReceive(android.content.Intent intent, int i, java.lang.String str, android.os.Bundle bundle, boolean z, boolean z2, int i2) {
            final int i3 = this.val$userId;
            new java.lang.Thread(new java.lang.Runnable() { // from class: com.android.server.pm.UserManagerService$7$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.pm.UserManagerService.AnonymousClass7.this.lambda$performReceive$0(i3);
                }
            }).start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$performReceive$0(int i) {
            com.android.server.pm.UserManagerService.this.getActivityManagerInternal().onUserRemoved(i);
            com.android.server.pm.UserManagerService.this.removeUserState(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeUserState(int i) {
        android.util.Slog.i(LOG_TAG, "Removing user state of user " + i);
        this.mLockPatternUtils.removeUser(i);
        try {
            ((android.os.storage.StorageManager) this.mContext.getSystemService(android.os.storage.StorageManager.class)).destroyUserStorageKeys(i);
        } catch (java.lang.IllegalStateException e) {
            android.util.Slog.i(LOG_TAG, "Destroying storage keys for user " + i + " failed, continuing anyway", e);
        }
        this.mPm.cleanUpUser(this, i);
        this.mUserDataPreparer.destroyUserData(i, 3);
        synchronized (this.mUsersLock) {
            this.mUsers.remove(i);
            this.mIsUserManaged.delete(i);
        }
        synchronized (this.mUserStates) {
            this.mUserStates.delete(i);
        }
        synchronized (this.mRestrictionsLock) {
            try {
                this.mBaseUserRestrictions.remove(i);
                this.mAppliedUserRestrictions.remove(i);
                this.mCachedEffectiveUserRestrictions.remove(i);
                if (this.mDevicePolicyUserRestrictions.remove(i)) {
                    applyUserRestrictionsForAllUsersLR();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        synchronized (this.mPackagesLock) {
            writeUserListLP();
        }
        getUserFile(i).delete();
        updateUserIds();
    }

    private void sendProfileAddedBroadcast(int i, int i2) {
        sendProfileBroadcast(new android.content.Intent("android.intent.action.PROFILE_ADDED"), i, i2);
    }

    private void sendProfileRemovedBroadcast(int i, int i2, java.lang.String str) {
        if (java.util.Objects.equals(str, "android.os.usertype.profile.MANAGED")) {
            sendManagedProfileRemovedBroadcast(i, i2);
        }
        sendProfileBroadcast(new android.content.Intent("android.intent.action.PROFILE_REMOVED"), i, i2);
    }

    private void sendProfileBroadcast(android.content.Intent intent, int i, int i2) {
        android.os.UserHandle of = android.os.UserHandle.of(i);
        intent.putExtra("android.intent.extra.USER", android.os.UserHandle.of(i2));
        intent.addFlags(com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_WAKEUP_EXACT_ALLOW_WHILE_IDLE);
        this.mContext.sendBroadcastAsUser(intent, of, null);
    }

    private void sendManagedProfileRemovedBroadcast(int i, int i2) {
        android.content.Intent intent = new android.content.Intent("android.intent.action.MANAGED_PROFILE_REMOVED");
        intent.putExtra("android.intent.extra.USER", android.os.UserHandle.of(i2));
        intent.putExtra("android.intent.extra.user_handle", i2);
        android.os.UserHandle of = android.os.UserHandle.of(i);
        getDevicePolicyManagerInternal().broadcastIntentToManifestReceivers(intent, of, false);
        intent.addFlags(com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_WAKEUP_EXACT_ALLOW_WHILE_IDLE);
        this.mContext.sendBroadcastAsUser(intent, of, null);
    }

    public android.os.Bundle getApplicationRestrictions(java.lang.String str) {
        return getApplicationRestrictionsForUser(str, android.os.UserHandle.getCallingUserId());
    }

    public android.os.Bundle getApplicationRestrictionsForUser(java.lang.String str, int i) {
        android.os.Bundle readApplicationRestrictionsLAr;
        if (android.os.UserHandle.getCallingUserId() != i || !android.os.UserHandle.isSameApp(android.os.Binder.getCallingUid(), getUidForPackage(str))) {
            checkSystemOrRoot("get application restrictions for other user/app " + str);
        }
        synchronized (this.mAppRestrictionsLock) {
            readApplicationRestrictionsLAr = readApplicationRestrictionsLAr(str, i);
        }
        return readApplicationRestrictionsLAr;
    }

    public void setApplicationRestrictions(java.lang.String str, android.os.Bundle bundle, int i) {
        checkSystemOrRoot("set application restrictions");
        java.lang.String validateName = android.content.pm.parsing.FrameworkParsingPackageUtils.validateName(str, false, false);
        if (validateName != null) {
            throw new java.lang.IllegalArgumentException("Invalid package name: " + validateName);
        }
        boolean z = true;
        if (bundle != null) {
            bundle.setDefusable(true);
        }
        synchronized (this.mAppRestrictionsLock) {
            if (bundle != null) {
                try {
                    if (!bundle.isEmpty()) {
                        writeApplicationRestrictionsLAr(str, bundle, i);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            z = cleanAppRestrictionsForPackageLAr(str, i);
        }
        if (!z) {
            return;
        }
        android.content.Intent intent = new android.content.Intent("android.intent.action.APPLICATION_RESTRICTIONS_CHANGED");
        intent.setPackage(str);
        intent.addFlags(1073741824);
        this.mContext.sendBroadcastAsUser(intent, android.os.UserHandle.of(i));
    }

    private int getUidForPackage(java.lang.String str) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            int i = this.mContext.getPackageManager().getApplicationInfo(str, 4194304).uid;
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return i;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return -1;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mAppRestrictionsLock"})
    private static android.os.Bundle readApplicationRestrictionsLAr(java.lang.String str, int i) {
        return readApplicationRestrictionsLAr(new android.util.AtomicFile(new java.io.File(android.os.Environment.getUserSystemDirectory(i), packageToRestrictionsFileName(str))));
    }

    @com.android.internal.annotations.GuardedBy({"mAppRestrictionsLock"})
    @com.android.internal.annotations.VisibleForTesting
    static android.os.Bundle readApplicationRestrictionsLAr(android.util.AtomicFile atomicFile) {
        com.android.modules.utils.TypedXmlPullParser resolvePullParser;
        android.os.Bundle bundle = new android.os.Bundle();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (!atomicFile.getBaseFile().exists()) {
            return bundle;
        }
        java.io.FileInputStream fileInputStream = null;
        try {
            try {
                fileInputStream = atomicFile.openRead();
                resolvePullParser = android.util.Xml.resolvePullParser(fileInputStream);
                com.android.internal.util.XmlUtils.nextElement(resolvePullParser);
            } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                android.util.Slog.w(LOG_TAG, "Error parsing " + atomicFile.getBaseFile(), e);
            }
            if (resolvePullParser.getEventType() == 2) {
                while (resolvePullParser.next() != 1) {
                    readEntry(bundle, arrayList, resolvePullParser);
                }
                return bundle;
            }
            android.util.Slog.e(LOG_TAG, "Unable to read restrictions file " + atomicFile.getBaseFile());
            return bundle;
        } finally {
            libcore.io.IoUtils.closeQuietly((java.lang.AutoCloseable) null);
        }
    }

    private static void readEntry(android.os.Bundle bundle, java.util.ArrayList<java.lang.String> arrayList, com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        if (typedXmlPullParser.getEventType() == 2 && typedXmlPullParser.getName().equals(TAG_ENTRY)) {
            java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_KEY);
            java.lang.String attributeValue2 = typedXmlPullParser.getAttributeValue((java.lang.String) null, com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE);
            int attributeInt = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_MULTIPLE, -1);
            if (attributeInt != -1) {
                arrayList.clear();
                while (attributeInt > 0) {
                    int next = typedXmlPullParser.next();
                    if (next == 1) {
                        break;
                    }
                    if (next == 2 && typedXmlPullParser.getName().equals(TAG_VALUE)) {
                        arrayList.add(typedXmlPullParser.nextText().trim());
                        attributeInt--;
                    }
                }
                java.lang.String[] strArr = new java.lang.String[arrayList.size()];
                arrayList.toArray(strArr);
                bundle.putStringArray(attributeValue, strArr);
                return;
            }
            if (ATTR_TYPE_BUNDLE.equals(attributeValue2)) {
                bundle.putBundle(attributeValue, readBundleEntry(typedXmlPullParser, arrayList));
                return;
            }
            if (ATTR_TYPE_BUNDLE_ARRAY.equals(attributeValue2)) {
                int depth = typedXmlPullParser.getDepth();
                java.util.ArrayList arrayList2 = new java.util.ArrayList();
                while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
                    arrayList2.add(readBundleEntry(typedXmlPullParser, arrayList));
                }
                bundle.putParcelableArray(attributeValue, (android.os.Parcelable[]) arrayList2.toArray(new android.os.Bundle[arrayList2.size()]));
                return;
            }
            java.lang.String trim = typedXmlPullParser.nextText().trim();
            if (ATTR_TYPE_BOOLEAN.equals(attributeValue2)) {
                bundle.putBoolean(attributeValue, java.lang.Boolean.parseBoolean(trim));
            } else if (ATTR_TYPE_INTEGER.equals(attributeValue2)) {
                bundle.putInt(attributeValue, java.lang.Integer.parseInt(trim));
            } else {
                bundle.putString(attributeValue, trim);
            }
        }
    }

    private static android.os.Bundle readBundleEntry(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.util.ArrayList<java.lang.String> arrayList) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        android.os.Bundle bundle = new android.os.Bundle();
        int depth = typedXmlPullParser.getDepth();
        while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
            readEntry(bundle, arrayList, typedXmlPullParser);
        }
        return bundle;
    }

    @com.android.internal.annotations.GuardedBy({"mAppRestrictionsLock"})
    private static void writeApplicationRestrictionsLAr(java.lang.String str, android.os.Bundle bundle, int i) {
        writeApplicationRestrictionsLAr(bundle, new android.util.AtomicFile(new java.io.File(android.os.Environment.getUserSystemDirectory(i), packageToRestrictionsFileName(str))));
    }

    @com.android.internal.annotations.GuardedBy({"mAppRestrictionsLock"})
    @com.android.internal.annotations.VisibleForTesting
    static void writeApplicationRestrictionsLAr(android.os.Bundle bundle, android.util.AtomicFile atomicFile) {
        java.io.FileOutputStream fileOutputStream = null;
        try {
            java.io.FileOutputStream startWrite = atomicFile.startWrite();
            try {
                com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(startWrite);
                resolveSerializer.startDocument((java.lang.String) null, true);
                resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                resolveSerializer.startTag((java.lang.String) null, TAG_RESTRICTIONS);
                writeBundle(bundle, resolveSerializer);
                resolveSerializer.endTag((java.lang.String) null, TAG_RESTRICTIONS);
                resolveSerializer.endDocument();
                atomicFile.finishWrite(startWrite);
            } catch (java.lang.Exception e) {
                e = e;
                fileOutputStream = startWrite;
                atomicFile.failWrite(fileOutputStream);
                android.util.Slog.e(LOG_TAG, "Error writing application restrictions list", e);
            }
        } catch (java.lang.Exception e2) {
            e = e2;
        }
    }

    private static void writeBundle(android.os.Bundle bundle, com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        for (java.lang.String str : bundle.keySet()) {
            java.lang.Object obj = bundle.get(str);
            typedXmlSerializer.startTag((java.lang.String) null, TAG_ENTRY);
            typedXmlSerializer.attribute((java.lang.String) null, ATTR_KEY, str);
            if (obj instanceof java.lang.Boolean) {
                typedXmlSerializer.attribute((java.lang.String) null, com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE, ATTR_TYPE_BOOLEAN);
                typedXmlSerializer.text(obj.toString());
            } else if (obj instanceof java.lang.Integer) {
                typedXmlSerializer.attribute((java.lang.String) null, com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE, ATTR_TYPE_INTEGER);
                typedXmlSerializer.text(obj.toString());
            } else if (obj == null || (obj instanceof java.lang.String)) {
                typedXmlSerializer.attribute((java.lang.String) null, com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE, ATTR_TYPE_STRING);
                typedXmlSerializer.text(obj != null ? (java.lang.String) obj : "");
            } else if (obj instanceof android.os.Bundle) {
                typedXmlSerializer.attribute((java.lang.String) null, com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE, ATTR_TYPE_BUNDLE);
                writeBundle((android.os.Bundle) obj, typedXmlSerializer);
            } else {
                int i = 0;
                if (obj instanceof android.os.Parcelable[]) {
                    typedXmlSerializer.attribute((java.lang.String) null, com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE, ATTR_TYPE_BUNDLE_ARRAY);
                    android.os.Parcelable[] parcelableArr = (android.os.Parcelable[]) obj;
                    int length = parcelableArr.length;
                    while (i < length) {
                        android.os.Parcelable parcelable = parcelableArr[i];
                        if (!(parcelable instanceof android.os.Bundle)) {
                            throw new java.lang.IllegalArgumentException("bundle-array can only hold Bundles");
                        }
                        typedXmlSerializer.startTag((java.lang.String) null, TAG_ENTRY);
                        typedXmlSerializer.attribute((java.lang.String) null, com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE, ATTR_TYPE_BUNDLE);
                        writeBundle((android.os.Bundle) parcelable, typedXmlSerializer);
                        typedXmlSerializer.endTag((java.lang.String) null, TAG_ENTRY);
                        i++;
                    }
                } else {
                    typedXmlSerializer.attribute((java.lang.String) null, com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE, ATTR_TYPE_STRING_ARRAY);
                    java.lang.String[] strArr = (java.lang.String[]) obj;
                    typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_MULTIPLE, strArr.length);
                    int length2 = strArr.length;
                    while (i < length2) {
                        java.lang.String str2 = strArr[i];
                        typedXmlSerializer.startTag((java.lang.String) null, TAG_VALUE);
                        if (str2 == null) {
                            str2 = "";
                        }
                        typedXmlSerializer.text(str2);
                        typedXmlSerializer.endTag((java.lang.String) null, TAG_VALUE);
                        i++;
                    }
                }
            }
            typedXmlSerializer.endTag((java.lang.String) null, TAG_ENTRY);
        }
    }

    public int getUserSerialNumber(int i) {
        int i2;
        synchronized (this.mUsersLock) {
            try {
                android.content.pm.UserInfo userInfoLU = getUserInfoLU(i);
                i2 = userInfoLU != null ? userInfoLU.serialNumber : -1;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return i2;
    }

    public boolean isUserNameSet(int i) {
        boolean z;
        int callingUid = android.os.Binder.getCallingUid();
        int userId = android.os.UserHandle.getUserId(callingUid);
        if (!hasQueryOrCreateUsersPermission() && (userId != i || !hasPermissionGranted("android.permission.GET_ACCOUNTS_PRIVILEGED", callingUid))) {
            throw new java.lang.SecurityException("You need MANAGE_USERS, CREATE_USERS, QUERY_USERS, or GET_ACCOUNTS_PRIVILEGED permissions to: get whether user name is set");
        }
        synchronized (this.mUsersLock) {
            try {
                android.content.pm.UserInfo userInfoLU = getUserInfoLU(i);
                z = (userInfoLU == null || userInfoLU.name == null) ? false : true;
            } finally {
            }
        }
        return z;
    }

    public int getUserHandle(int i) {
        synchronized (this.mUsersLock) {
            try {
                for (int i2 : this.mUserIds) {
                    android.content.pm.UserInfo userInfoLU = getUserInfoLU(i2);
                    if (userInfoLU != null && userInfoLU.serialNumber == i) {
                        return i2;
                    }
                }
                return -1;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public long getUserCreationTime(int i) {
        android.content.pm.UserInfo userInfo;
        int callingUserId = android.os.UserHandle.getCallingUserId();
        synchronized (this.mUsersLock) {
            try {
                if (callingUserId == i) {
                    userInfo = getUserInfoLU(i);
                } else {
                    android.content.pm.UserInfo profileParentLU = getProfileParentLU(i);
                    if (profileParentLU != null && profileParentLU.id == callingUserId) {
                        userInfo = getUserInfoLU(i);
                    } else {
                        userInfo = null;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (userInfo == null) {
            throw new java.lang.SecurityException("userId can only be the calling user or a profile associated with this user");
        }
        return userInfo.creationTime;
    }

    private void updateUserIds() {
        synchronized (this.mUsersLock) {
            try {
                int size = this.mUsers.size();
                int i = 0;
                int i2 = 0;
                for (int i3 = 0; i3 < size; i3++) {
                    android.content.pm.UserInfo userInfo = this.mUsers.valueAt(i3).info;
                    if (!userInfo.partial) {
                        i2++;
                        if (!userInfo.preCreated) {
                            i++;
                        }
                    }
                }
                int[] iArr = new int[i];
                int[] iArr2 = new int[i2];
                int i4 = 0;
                int i5 = 0;
                for (int i6 = 0; i6 < size; i6++) {
                    android.content.pm.UserInfo userInfo2 = this.mUsers.valueAt(i6).info;
                    if (!userInfo2.partial) {
                        int keyAt = this.mUsers.keyAt(i6);
                        int i7 = i4 + 1;
                        iArr2[i4] = keyAt;
                        if (userInfo2.preCreated) {
                            i4 = i7;
                        } else {
                            iArr[i5] = keyAt;
                            i5++;
                            i4 = i7;
                        }
                    }
                }
                this.mUserIds = iArr;
                this.mUserIdsIncludingPreCreated = iArr2;
                android.content.pm.UserPackage.setValidUserIds(this.mUserIds);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void onBeforeStartUser(int i) {
        android.content.pm.UserInfo userInfo = getUserInfo(i);
        if (userInfo == null) {
            return;
        }
        com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog();
        timingsTraceAndSlog.traceBegin("onBeforeStartUser-" + i);
        boolean equals = android.content.pm.PackagePartitions.FINGERPRINT.equals(userInfo.lastLoggedInFingerprint) ^ true;
        timingsTraceAndSlog.traceBegin("prepareUserData");
        this.mUserDataPreparer.prepareUserData(userInfo, 1);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("reconcileAppsData");
        getPackageManagerInternal().reconcileAppsData(i, 1, equals);
        timingsTraceAndSlog.traceEnd();
        if (i != 0) {
            timingsTraceAndSlog.traceBegin("applyUserRestrictions");
            synchronized (this.mRestrictionsLock) {
                applyUserRestrictionsLR(i);
            }
            timingsTraceAndSlog.traceEnd();
        }
        timingsTraceAndSlog.traceEnd();
    }

    public void onBeforeUnlockUser(int i) {
        android.content.pm.UserInfo userInfo = getUserInfo(i);
        if (userInfo == null) {
            return;
        }
        boolean z = !android.content.pm.PackagePartitions.FINGERPRINT.equals(userInfo.lastLoggedInFingerprint);
        com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog();
        timingsTraceAndSlog.traceBegin("prepareUserData-" + i);
        this.mUserDataPreparer.prepareUserData(userInfo, 2);
        timingsTraceAndSlog.traceEnd();
        ((android.os.storage.StorageManagerInternal) com.android.server.LocalServices.getService(android.os.storage.StorageManagerInternal.class)).markCeStoragePrepared(i);
        timingsTraceAndSlog.traceBegin("reconcileAppsData-" + i);
        getPackageManagerInternal().reconcileAppsData(i, 2, z);
        timingsTraceAndSlog.traceEnd();
    }

    void reconcileUsers(java.lang.String str) {
        this.mUserDataPreparer.reconcileUsers(str, getUsers(true, true, false));
    }

    public void onUserLoggedIn(int i) {
        com.android.server.pm.UserManagerService.UserData userDataNoChecks = getUserDataNoChecks(i);
        if (userDataNoChecks == null || userDataNoChecks.info.partial) {
            android.util.Slog.w(LOG_TAG, "userForeground: unknown user #" + i);
            return;
        }
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        if (currentTimeMillis > EPOCH_PLUS_30_YEARS) {
            userDataNoChecks.info.lastLoggedInTime = currentTimeMillis;
        }
        userDataNoChecks.info.lastLoggedInFingerprint = android.content.pm.PackagePartitions.FINGERPRINT;
        scheduleWriteUser(i);
    }

    @com.android.internal.annotations.VisibleForTesting
    int getNextAvailableId() {
        synchronized (this.mUsersLock) {
            try {
                int scanNextAvailableIdLocked = scanNextAvailableIdLocked();
                if (scanNextAvailableIdLocked >= 0) {
                    return scanNextAvailableIdLocked;
                }
                if (this.mRemovingUserIds.size() > 0) {
                    android.util.Slog.i(LOG_TAG, "All available IDs are used. Recycling LRU ids.");
                    this.mRemovingUserIds.clear();
                    java.util.Iterator<java.lang.Integer> it = this.mRecentlyRemovedIds.iterator();
                    while (it.hasNext()) {
                        this.mRemovingUserIds.put(it.next().intValue(), true);
                    }
                    scanNextAvailableIdLocked = scanNextAvailableIdLocked();
                }
                android.os.UserManager.invalidateStaticUserProperties();
                android.os.UserManager.invalidateUserPropertiesCache();
                if (scanNextAvailableIdLocked < 0) {
                    throw new java.lang.IllegalStateException("No user id available!");
                }
                return scanNextAvailableIdLocked;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUsersLock"})
    private int scanNextAvailableIdLocked() {
        for (int i = 10; i < MAX_USER_ID; i++) {
            if (this.mUsers.indexOfKey(i) < 0 && !this.mRemovingUserIds.get(i)) {
                return i;
            }
        }
        return -1;
    }

    private static java.lang.String packageToRestrictionsFileName(java.lang.String str) {
        return RESTRICTIONS_FILE_PREFIX + str + XML_SUFFIX;
    }

    @android.annotation.Nullable
    private static java.lang.String getRedacted(@android.annotation.Nullable java.lang.String str) {
        if (str == null) {
            return null;
        }
        return str.length() + "_chars";
    }

    public void setSeedAccountData(int i, java.lang.String str, java.lang.String str2, android.os.PersistableBundle persistableBundle, boolean z) {
        checkManageUsersPermission("set user seed account data");
        setSeedAccountDataNoChecks(i, str, str2, persistableBundle, z);
    }

    private void setSeedAccountDataNoChecks(int i, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable android.os.PersistableBundle persistableBundle, boolean z) {
        synchronized (this.mPackagesLock) {
            synchronized (this.mUsersLock) {
                com.android.server.pm.UserManagerService.UserData userDataLU = getUserDataLU(i);
                if (userDataLU == null) {
                    android.util.Slog.e(LOG_TAG, "No such user for settings seed data u=" + i);
                    return;
                }
                userDataLU.seedAccountName = truncateString(str, 500);
                userDataLU.seedAccountType = truncateString(str2, 500);
                if (persistableBundle != null && persistableBundle.isBundleContentsWithinLengthLimit(1000)) {
                    userDataLU.seedAccountOptions = persistableBundle;
                }
                userDataLU.persistSeedData = z;
                if (z) {
                    writeUserLP(userDataLU);
                }
            }
        }
    }

    public java.lang.String getSeedAccountName(int i) throws android.os.RemoteException {
        java.lang.String str;
        checkManageUsersPermission("Cannot get seed account information");
        synchronized (this.mUsersLock) {
            com.android.server.pm.UserManagerService.UserData userDataLU = getUserDataLU(i);
            str = userDataLU == null ? null : userDataLU.seedAccountName;
        }
        return str;
    }

    public java.lang.String getSeedAccountType(int i) throws android.os.RemoteException {
        java.lang.String str;
        checkManageUsersPermission("Cannot get seed account information");
        synchronized (this.mUsersLock) {
            com.android.server.pm.UserManagerService.UserData userDataLU = getUserDataLU(i);
            str = userDataLU == null ? null : userDataLU.seedAccountType;
        }
        return str;
    }

    public android.os.PersistableBundle getSeedAccountOptions(int i) throws android.os.RemoteException {
        android.os.PersistableBundle persistableBundle;
        checkManageUsersPermission("Cannot get seed account information");
        synchronized (this.mUsersLock) {
            com.android.server.pm.UserManagerService.UserData userDataLU = getUserDataLU(i);
            persistableBundle = userDataLU == null ? null : userDataLU.seedAccountOptions;
        }
        return persistableBundle;
    }

    public void clearSeedAccountData(int i) throws android.os.RemoteException {
        checkManageUsersPermission("Cannot clear seed account information");
        synchronized (this.mPackagesLock) {
            synchronized (this.mUsersLock) {
                com.android.server.pm.UserManagerService.UserData userDataLU = getUserDataLU(i);
                if (userDataLU == null) {
                    return;
                }
                userDataLU.clearSeedAccountData();
                writeUserLP(userDataLU);
            }
        }
    }

    public boolean someUserHasSeedAccount(java.lang.String str, java.lang.String str2) {
        checkManageUsersPermission("check seed account information");
        return someUserHasSeedAccountNoChecks(str, str2);
    }

    private boolean someUserHasSeedAccountNoChecks(java.lang.String str, java.lang.String str2) {
        synchronized (this.mUsersLock) {
            try {
                int size = this.mUsers.size();
                for (int i = 0; i < size; i++) {
                    com.android.server.pm.UserManagerService.UserData valueAt = this.mUsers.valueAt(i);
                    if (!valueAt.info.isInitialized() && !this.mRemovingUserIds.get(valueAt.info.id) && valueAt.seedAccountName != null && valueAt.seedAccountName.equals(str) && valueAt.seedAccountType != null && valueAt.seedAccountType.equals(str2)) {
                        return true;
                    }
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean someUserHasAccount(java.lang.String str, java.lang.String str2) {
        checkCreateUsersPermission("check seed account information");
        return someUserHasAccountNoChecks(str, str2);
    }

    private boolean someUserHasAccountNoChecks(final java.lang.String str, final java.lang.String str2) {
        if (android.text.TextUtils.isEmpty(str) || android.text.TextUtils.isEmpty(str2)) {
            return false;
        }
        final android.accounts.Account account = new android.accounts.Account(str, str2);
        return ((java.lang.Boolean) android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.pm.UserManagerService$$ExternalSyntheticLambda9
            public final java.lang.Object getOrThrow() {
                java.lang.Boolean lambda$someUserHasAccountNoChecks$8;
                lambda$someUserHasAccountNoChecks$8 = com.android.server.pm.UserManagerService.this.lambda$someUserHasAccountNoChecks$8(account, str, str2);
                return lambda$someUserHasAccountNoChecks$8;
            }
        })).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Boolean lambda$someUserHasAccountNoChecks$8(android.accounts.Account account, java.lang.String str, java.lang.String str2) throws java.lang.Exception {
        return java.lang.Boolean.valueOf(android.accounts.AccountManager.get(this.mContext).someUserHasAccount(account) || someUserHasSeedAccountNoChecks(str, str2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setLastEnteredForegroundTimeToNow(@android.annotation.NonNull com.android.server.pm.UserManagerService.UserData userData) {
        userData.mLastEnteredForegroundTimeMillis = java.lang.System.currentTimeMillis();
        scheduleWriteUser(userData.info.id);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
        new com.android.server.pm.UserManagerServiceShellCommand(this, this.mSystemPackageInstaller, this.mLockPatternUtils, this.mContext).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        java.lang.Object obj;
        java.lang.Object obj2;
        int i;
        long j;
        int i2;
        java.lang.Object obj3;
        boolean z;
        if (!com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, LOG_TAG, printWriter)) {
            return;
        }
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        int i3 = -1;
        if (strArr != null && strArr.length > 0) {
            java.lang.String str = strArr[0];
            switch (str.hashCode()) {
                case -1247813202:
                    if (str.equals("--visibility-mediator")) {
                        z = true;
                        break;
                    }
                    z = -1;
                    break;
                case 1333469547:
                    if (str.equals("--user")) {
                        z = false;
                        break;
                    }
                    z = -1;
                    break;
                default:
                    z = -1;
                    break;
            }
            switch (z) {
                case false:
                    dumpUser(printWriter, android.os.UserHandle.parseUserArg(strArr[1]), sb, currentTimeMillis, elapsedRealtime);
                    return;
                case true:
                    this.mUserVisibilityMediator.dump(printWriter, strArr);
                    return;
            }
        }
        int currentUserId = getCurrentUserId();
        printWriter.print("Current user: ");
        if (currentUserId != -10000) {
            printWriter.println(currentUserId);
        } else {
            printWriter.println("N/A");
        }
        printWriter.println();
        java.lang.Object obj4 = this.mPackagesLock;
        synchronized (obj4) {
            try {
            } catch (java.lang.Throwable th) {
                th = th;
                throw th;
            }
            try {
                java.lang.Object obj5 = this.mUsersLock;
                synchronized (obj5) {
                    try {
                        printWriter.println("Users:");
                        int i4 = 0;
                        while (i4 < this.mUsers.size()) {
                            com.android.server.pm.UserManagerService.UserData valueAt = this.mUsers.valueAt(i4);
                            if (valueAt == null) {
                                obj2 = obj5;
                                i = i4;
                                j = currentTimeMillis;
                                i2 = i3;
                                obj3 = obj4;
                            } else {
                                obj2 = obj5;
                                i = i4;
                                long j2 = currentTimeMillis;
                                j = currentTimeMillis;
                                i2 = i3;
                                obj3 = obj4;
                                try {
                                    dumpUserLocked(printWriter, valueAt, sb, j2, elapsedRealtime);
                                } catch (java.lang.Throwable th2) {
                                    th = th2;
                                    throw th;
                                }
                            }
                            i4 = i + 1;
                            i3 = i2;
                            obj4 = obj3;
                            obj5 = obj2;
                            currentTimeMillis = j;
                        }
                        int i5 = i3;
                        obj = obj4;
                        printWriter.println();
                        printWriter.println("Device properties:");
                        printWriter.println("  Device policy global restrictions:");
                        synchronized (this.mRestrictionsLock) {
                            com.android.server.pm.UserRestrictionsUtils.dumpRestrictions(printWriter, "    ", this.mDevicePolicyUserRestrictions.getRestrictions(i5));
                        }
                        printWriter.println("  Guest restrictions:");
                        synchronized (this.mGuestRestrictions) {
                            com.android.server.pm.UserRestrictionsUtils.dumpRestrictions(printWriter, "    ", this.mGuestRestrictions);
                        }
                        synchronized (this.mUsersLock) {
                            try {
                                printWriter.println();
                                printWriter.println("  Device managed: " + this.mIsDeviceManaged);
                                if (this.mRemovingUserIds.size() > 0) {
                                    printWriter.println();
                                    printWriter.println("  Recently removed userIds: " + this.mRecentlyRemovedIds);
                                }
                            } finally {
                            }
                        }
                        synchronized (this.mUserStates) {
                            try {
                                printWriter.print("  Started users state: [");
                                int size = this.mUserStates.states.size();
                                for (int i6 = 0; i6 < size; i6++) {
                                    int keyAt = this.mUserStates.states.keyAt(i6);
                                    int valueAt2 = this.mUserStates.states.valueAt(i6);
                                    printWriter.print(keyAt);
                                    printWriter.print('=');
                                    printWriter.print(com.android.server.am.UserState.stateToString(valueAt2));
                                    if (i6 != size - 1) {
                                        printWriter.print(", ");
                                    }
                                }
                                printWriter.println(']');
                            } finally {
                            }
                        }
                        synchronized (this.mUsersLock) {
                            printWriter.print("  Cached user IDs: ");
                            printWriter.println(java.util.Arrays.toString(this.mUserIds));
                            printWriter.print("  Cached user IDs (including pre-created): ");
                            printWriter.println(java.util.Arrays.toString(this.mUserIdsIncludingPreCreated));
                        }
                        printWriter.println();
                        this.mUserVisibilityMediator.dump(printWriter, strArr);
                        printWriter.println();
                        printWriter.println();
                        printWriter.print("  Max users: " + android.os.UserManager.getMaxSupportedUsers());
                        printWriter.println(" (limit reached: " + isUserLimitReached() + ")");
                        java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
                        sb2.append("  Supports switchable users: ");
                        sb2.append(android.os.UserManager.supportsMultipleUsers());
                        printWriter.println(sb2.toString());
                        printWriter.println("  All guests ephemeral: " + android.content.res.Resources.getSystem().getBoolean(android.R.bool.config_freeformWindowManagement));
                        printWriter.println("  Force ephemeral users: " + this.mForceEphemeralUsers);
                        boolean isHeadlessSystemUserMode = isHeadlessSystemUserMode();
                        printWriter.println("  Is headless-system mode: " + isHeadlessSystemUserMode);
                        if (isHeadlessSystemUserMode != com.android.internal.os.RoSystemProperties.MULTIUSER_HEADLESS_SYSTEM_USER) {
                            printWriter.println("  (differs from the current default build value)");
                        }
                        if (!android.text.TextUtils.isEmpty(android.os.SystemProperties.get("persist.debug.user_mode_emulation"))) {
                            printWriter.println("  (emulated by 'cmd user set-system-user-mode-emulation')");
                            if (this.mUpdatingSystemUserMode) {
                                printWriter.println("  (and being updated after boot)");
                            }
                        }
                        printWriter.println("  User version: " + this.mUserVersion);
                        printWriter.println("  Owner name: " + getOwnerName());
                        synchronized (this.mUsersLock) {
                            printWriter.println("  Boot user: " + this.mBootUser);
                        }
                        printWriter.println();
                        printWriter.println("Number of listeners for");
                        synchronized (this.mUserRestrictionsListeners) {
                            printWriter.println("  restrictions: " + this.mUserRestrictionsListeners.size());
                        }
                        synchronized (this.mUserLifecycleListeners) {
                            printWriter.println("  user lifecycle events: " + this.mUserLifecycleListeners.size());
                        }
                        printWriter.println();
                        printWriter.println("User types version: " + this.mUserTypeVersion);
                        printWriter.println("User types (" + this.mUserTypes.size() + " types):");
                        for (int i7 = 0; i7 < this.mUserTypes.size(); i7++) {
                            printWriter.println("    " + this.mUserTypes.keyAt(i7) + ": ");
                            this.mUserTypes.valueAt(i7).dump(printWriter, "        ");
                        }
                        android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter);
                        try {
                            indentingPrintWriter.println();
                            this.mSystemPackageInstaller.dump(indentingPrintWriter);
                            indentingPrintWriter.close();
                        } finally {
                        }
                    } catch (java.lang.Throwable th3) {
                        th = th3;
                        obj2 = obj5;
                    }
                }
            } catch (java.lang.Throwable th4) {
                th = th4;
                obj = obj4;
                throw th;
            }
        }
    }

    private void dumpUser(java.io.PrintWriter printWriter, int i, java.lang.StringBuilder sb, long j, long j2) {
        int i2;
        if (i != -2) {
            i2 = i;
        } else {
            i2 = getCurrentUserId();
            printWriter.print("Current user: ");
            if (i2 == -10000) {
                printWriter.println("Cannot determine current user");
                return;
            }
        }
        synchronized (this.mUsersLock) {
            try {
                com.android.server.pm.UserManagerService.UserData userData = this.mUsers.get(i2);
                if (userData == null) {
                    printWriter.println("User " + i2 + " not found");
                    return;
                }
                dumpUserLocked(printWriter, userData, sb, j, j2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUsersLock"})
    private void dumpUserLocked(java.io.PrintWriter printWriter, com.android.server.pm.UserManagerService.UserData userData, java.lang.StringBuilder sb, long j, long j2) {
        int i;
        android.content.pm.UserInfo userInfo = userData.info;
        int i2 = userInfo.id;
        printWriter.print("  ");
        printWriter.print(userInfo);
        printWriter.print(" serialNo=");
        printWriter.print(userInfo.serialNumber);
        printWriter.print(" isPrimary=");
        printWriter.print(userInfo.isPrimary());
        if (userInfo.profileGroupId != userInfo.id && userInfo.profileGroupId != -10000) {
            printWriter.print(" parentId=");
            printWriter.print(userInfo.profileGroupId);
        }
        if (this.mRemovingUserIds.get(i2)) {
            printWriter.print(" <removing> ");
        }
        if (userInfo.partial) {
            printWriter.print(" <partial>");
        }
        if (userInfo.preCreated) {
            printWriter.print(" <pre-created>");
        }
        if (userInfo.convertedFromPreCreated) {
            printWriter.print(" <converted>");
        }
        printWriter.println();
        printWriter.print("    Type: ");
        printWriter.println(userInfo.userType);
        printWriter.print("    Flags: ");
        printWriter.print(userInfo.flags);
        printWriter.print(" (");
        printWriter.print(android.content.pm.UserInfo.flagsToString(userInfo.flags));
        printWriter.println(")");
        printWriter.print("    State: ");
        synchronized (this.mUserStates) {
            i = this.mUserStates.get(i2, -1);
        }
        printWriter.println(com.android.server.am.UserState.stateToString(i));
        printWriter.print("    Created: ");
        dumpTimeAgo(printWriter, sb, j, userInfo.creationTime);
        printWriter.print("    Last logged in: ");
        dumpTimeAgo(printWriter, sb, j, userInfo.lastLoggedInTime);
        printWriter.print("    Last logged in fingerprint: ");
        printWriter.println(userInfo.lastLoggedInFingerprint);
        printWriter.print("    Start time: ");
        dumpTimeAgo(printWriter, sb, j2, userData.startRealtime);
        printWriter.print("    Unlock time: ");
        dumpTimeAgo(printWriter, sb, j2, userData.unlockRealtime);
        printWriter.print("    Last entered foreground: ");
        dumpTimeAgo(printWriter, sb, j, userData.mLastEnteredForegroundTimeMillis);
        printWriter.print("    Has profile owner: ");
        printWriter.println(this.mIsUserManaged.get(i2));
        printWriter.println("    Restrictions:");
        synchronized (this.mRestrictionsLock) {
            com.android.server.pm.UserRestrictionsUtils.dumpRestrictions(printWriter, "      ", this.mBaseUserRestrictions.getRestrictions(userInfo.id));
            printWriter.println("    Device policy restrictions:");
            com.android.server.pm.UserRestrictionsUtils.dumpRestrictions(printWriter, "      ", this.mDevicePolicyUserRestrictions.getRestrictions(userInfo.id));
            printWriter.println("    Effective restrictions:");
            com.android.server.pm.UserRestrictionsUtils.dumpRestrictions(printWriter, "      ", this.mCachedEffectiveUserRestrictions.getRestrictions(userInfo.id));
        }
        if (userData.account != null) {
            printWriter.print("    Account name: " + userData.account);
            printWriter.println();
        }
        if (userData.seedAccountName != null) {
            printWriter.print("    Seed account name: " + userData.seedAccountName);
            printWriter.println();
            if (userData.seedAccountType != null) {
                printWriter.print("         account type: " + userData.seedAccountType);
                printWriter.println();
            }
            if (userData.seedAccountOptions != null) {
                printWriter.print("         account options exist");
                printWriter.println();
            }
        }
        if (userData.userProperties != null) {
            userData.userProperties.println(printWriter, "    ");
        }
        printWriter.println("    Ignore errors preparing storage: " + userData.getIgnorePrepareStorageErrors());
    }

    private static void dumpTimeAgo(java.io.PrintWriter printWriter, java.lang.StringBuilder sb, long j, long j2) {
        if (j2 == 0) {
            printWriter.println("<unknown>");
            return;
        }
        sb.setLength(0);
        android.util.TimeUtils.formatDuration(j - j2, sb);
        sb.append(" ago");
        printWriter.println(sb);
    }

    final class MainHandler extends android.os.Handler {
        MainHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    removeMessages(1, message.obj);
                    synchronized (com.android.server.pm.UserManagerService.this.mPackagesLock) {
                        try {
                            int intValue = ((java.lang.Integer) message.obj).intValue();
                            com.android.server.pm.UserManagerService.UserData userDataNoChecks = com.android.server.pm.UserManagerService.this.getUserDataNoChecks(intValue);
                            if (userDataNoChecks != null) {
                                com.android.server.pm.UserManagerService.this.writeUserLP(userDataNoChecks);
                            } else {
                                android.util.Slog.i(com.android.server.pm.UserManagerService.LOG_TAG, "handle(WRITE_USER_MSG): no data for user " + intValue + ", it was probably removed before handler could handle it");
                            }
                        } finally {
                        }
                    }
                    return;
                case 2:
                    removeMessages(2);
                    synchronized (com.android.server.pm.UserManagerService.this.mPackagesLock) {
                        com.android.server.pm.UserManagerService.this.writeUserListLP();
                    }
                    return;
                default:
                    return;
            }
        }
    }

    boolean isUserInitialized(int i) {
        return this.mLocalService.isUserInitialized(i);
    }

    private class LocalService extends com.android.server.pm.UserManagerInternal {
        private LocalService() {
        }

        @Override // com.android.server.pm.UserManagerInternal
        public void setDevicePolicyUserRestrictions(int i, @android.annotation.NonNull android.os.Bundle bundle, @android.annotation.NonNull com.android.server.pm.RestrictionsSet restrictionsSet, boolean z) {
            com.android.server.pm.UserManagerService.this.setDevicePolicyUserRestrictionsInner(i, bundle, restrictionsSet, z);
        }

        @Override // com.android.server.pm.UserManagerInternal
        public void setUserRestriction(int i, @android.annotation.NonNull java.lang.String str, boolean z) {
            com.android.server.pm.UserManagerService.this.setUserRestrictionInner(i, str, z);
        }

        @Override // com.android.server.pm.UserManagerInternal
        public boolean getUserRestriction(int i, java.lang.String str) {
            return com.android.server.pm.UserManagerService.this.getUserRestrictions(i).getBoolean(str);
        }

        @Override // com.android.server.pm.UserManagerInternal
        public void addUserRestrictionsListener(com.android.server.pm.UserManagerInternal.UserRestrictionsListener userRestrictionsListener) {
            synchronized (com.android.server.pm.UserManagerService.this.mUserRestrictionsListeners) {
                com.android.server.pm.UserManagerService.this.mUserRestrictionsListeners.add(userRestrictionsListener);
            }
        }

        @Override // com.android.server.pm.UserManagerInternal
        public void removeUserRestrictionsListener(com.android.server.pm.UserManagerInternal.UserRestrictionsListener userRestrictionsListener) {
            synchronized (com.android.server.pm.UserManagerService.this.mUserRestrictionsListeners) {
                com.android.server.pm.UserManagerService.this.mUserRestrictionsListeners.remove(userRestrictionsListener);
            }
        }

        @Override // com.android.server.pm.UserManagerInternal
        public void addUserLifecycleListener(com.android.server.pm.UserManagerInternal.UserLifecycleListener userLifecycleListener) {
            synchronized (com.android.server.pm.UserManagerService.this.mUserLifecycleListeners) {
                com.android.server.pm.UserManagerService.this.mUserLifecycleListeners.add(userLifecycleListener);
            }
        }

        @Override // com.android.server.pm.UserManagerInternal
        public void removeUserLifecycleListener(com.android.server.pm.UserManagerInternal.UserLifecycleListener userLifecycleListener) {
            synchronized (com.android.server.pm.UserManagerService.this.mUserLifecycleListeners) {
                com.android.server.pm.UserManagerService.this.mUserLifecycleListeners.remove(userLifecycleListener);
            }
        }

        @Override // com.android.server.pm.UserManagerInternal
        public void setDeviceManaged(boolean z) {
            synchronized (com.android.server.pm.UserManagerService.this.mUsersLock) {
                com.android.server.pm.UserManagerService.this.mIsDeviceManaged = z;
            }
        }

        @Override // com.android.server.pm.UserManagerInternal
        public boolean isDeviceManaged() {
            boolean z;
            synchronized (com.android.server.pm.UserManagerService.this.mUsersLock) {
                z = com.android.server.pm.UserManagerService.this.mIsDeviceManaged;
            }
            return z;
        }

        @Override // com.android.server.pm.UserManagerInternal
        public void setUserManaged(int i, boolean z) {
            synchronized (com.android.server.pm.UserManagerService.this.mUsersLock) {
                com.android.server.pm.UserManagerService.this.mIsUserManaged.put(i, z);
            }
        }

        @Override // com.android.server.pm.UserManagerInternal
        public boolean isUserManaged(int i) {
            boolean z;
            synchronized (com.android.server.pm.UserManagerService.this.mUsersLock) {
                z = com.android.server.pm.UserManagerService.this.mIsUserManaged.get(i);
            }
            return z;
        }

        @Override // com.android.server.pm.UserManagerInternal
        public void setUserIcon(int i, android.graphics.Bitmap bitmap) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.pm.UserManagerService.this.mPackagesLock) {
                    com.android.server.pm.UserManagerService.UserData userDataNoChecks = com.android.server.pm.UserManagerService.this.getUserDataNoChecks(i);
                    if (userDataNoChecks != null && !userDataNoChecks.info.partial) {
                        com.android.server.pm.UserManagerService.this.writeBitmapLP(userDataNoChecks.info, bitmap);
                        com.android.server.pm.UserManagerService.this.writeUserLP(userDataNoChecks);
                        com.android.server.pm.UserManagerService.this.sendUserInfoChangedBroadcast(i);
                    } else {
                        android.util.Slog.w(com.android.server.pm.UserManagerService.LOG_TAG, "setUserIcon: unknown user #" + i);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @Override // com.android.server.pm.UserManagerInternal
        public void setForceEphemeralUsers(boolean z) {
            synchronized (com.android.server.pm.UserManagerService.this.mUsersLock) {
                com.android.server.pm.UserManagerService.this.mForceEphemeralUsers = z;
            }
        }

        @Override // com.android.server.pm.UserManagerInternal
        public void removeAllUsers() {
            if (com.android.server.pm.UserManagerService.this.getCurrentUserId() == 0) {
                com.android.server.pm.UserManagerService.this.removeAllUsersExceptSystemAndPermanentAdminMain();
                return;
            }
            android.content.BroadcastReceiver broadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.pm.UserManagerService.LocalService.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(android.content.Context context, android.content.Intent intent) {
                    if (intent.getIntExtra("android.intent.extra.user_handle", com.android.server.am.ProcessList.INVALID_ADJ) != 0) {
                        return;
                    }
                    com.android.server.pm.UserManagerService.this.mContext.unregisterReceiver(this);
                    com.android.server.pm.UserManagerService.this.removeAllUsersExceptSystemAndPermanentAdminMain();
                }
            };
            android.content.IntentFilter intentFilter = new android.content.IntentFilter();
            intentFilter.addAction("android.intent.action.USER_SWITCHED");
            com.android.server.pm.UserManagerService.this.mContext.registerReceiver(broadcastReceiver, intentFilter, null, com.android.server.pm.UserManagerService.this.mHandler);
            ((android.app.ActivityManager) com.android.server.pm.UserManagerService.this.mContext.getSystemService(com.android.server.am.HostingRecord.HOSTING_TYPE_ACTIVITY)).switchUser(0);
        }

        @Override // com.android.server.pm.UserManagerInternal
        public void onEphemeralUserStop(int i) {
            synchronized (com.android.server.pm.UserManagerService.this.mUsersLock) {
                try {
                    android.content.pm.UserInfo userInfoLU = com.android.server.pm.UserManagerService.this.getUserInfoLU(i);
                    if (userInfoLU != null && userInfoLU.isEphemeral()) {
                        userInfoLU.flags |= 64;
                        if (userInfoLU.isGuest()) {
                            userInfoLU.guestToRemove = true;
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.pm.UserManagerInternal
        @android.annotation.NonNull
        public android.content.pm.UserInfo createUserEvenWhenDisallowed(@android.annotation.Nullable java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i, @android.annotation.Nullable java.lang.String[] strArr, @android.annotation.Nullable java.lang.Object obj) throws android.os.UserManager.CheckedUserOperationException {
            return com.android.server.pm.UserManagerService.this.createUserInternalUnchecked(str, str2, i, com.android.server.am.ProcessList.INVALID_ADJ, false, strArr, obj);
        }

        @Override // com.android.server.pm.UserManagerInternal
        public boolean removeUserEvenWhenDisallowed(int i) {
            return com.android.server.pm.UserManagerService.this.removeUserWithProfilesUnchecked(i);
        }

        @Override // com.android.server.pm.UserManagerInternal
        public boolean isUserRunning(int i) {
            int i2;
            synchronized (com.android.server.pm.UserManagerService.this.mUserStates) {
                i2 = com.android.server.pm.UserManagerService.this.mUserStates.get(i, -1);
            }
            return (i2 == -1 || i2 == 4 || i2 == 5) ? false : true;
        }

        @Override // com.android.server.pm.UserManagerInternal
        public void setUserState(int i, int i2) {
            synchronized (com.android.server.pm.UserManagerService.this.mUserStates) {
                com.android.server.pm.UserManagerService.this.mUserStates.put(i, i2);
            }
        }

        @Override // com.android.server.pm.UserManagerInternal
        public void removeUserState(int i) {
            synchronized (com.android.server.pm.UserManagerService.this.mUserStates) {
                com.android.server.pm.UserManagerService.this.mUserStates.delete(i);
            }
        }

        @Override // com.android.server.pm.UserManagerInternal
        public int[] getUserIds() {
            return com.android.server.pm.UserManagerService.this.getUserIds();
        }

        @Override // com.android.server.pm.UserManagerInternal
        @android.annotation.NonNull
        public java.util.List<android.content.pm.UserInfo> getUsers(boolean z) {
            return getUsers(true, z, true);
        }

        @Override // com.android.server.pm.UserManagerInternal
        @android.annotation.NonNull
        public java.util.List<android.content.pm.UserInfo> getUsers(boolean z, boolean z2, boolean z3) {
            return com.android.server.pm.UserManagerService.this.getUsersInternal(z, z2, z3);
        }

        @Override // com.android.server.pm.UserManagerInternal
        @android.annotation.NonNull
        public int[] getProfileIds(int i, boolean z) {
            int[] array;
            synchronized (com.android.server.pm.UserManagerService.this.mUsersLock) {
                array = com.android.server.pm.UserManagerService.this.getProfileIdsLU(i, null, z, false).toArray();
            }
            return array;
        }

        @Override // com.android.server.pm.UserManagerInternal
        @android.annotation.Nullable
        public android.content.pm.LauncherUserInfo getLauncherUserInfo(int i) {
            android.content.pm.UserInfo userInfoLU;
            synchronized (com.android.server.pm.UserManagerService.this.mUsersLock) {
                userInfoLU = com.android.server.pm.UserManagerService.this.getUserInfoLU(i);
            }
            if (userInfoLU != null) {
                return new android.content.pm.LauncherUserInfo.Builder(com.android.server.pm.UserManagerService.this.getUserTypeDetails(userInfoLU).getName(), userInfoLU.serialNumber).build();
            }
            return null;
        }

        @Override // com.android.server.pm.UserManagerInternal
        public boolean isUserUnlockingOrUnlocked(int i) {
            int i2;
            synchronized (com.android.server.pm.UserManagerService.this.mUserStates) {
                i2 = com.android.server.pm.UserManagerService.this.mUserStates.get(i, -1);
            }
            if (i2 == 4 || i2 == 5) {
                return android.os.storage.StorageManager.isCeStorageUnlocked(i);
            }
            return i2 == 2 || i2 == 3;
        }

        @Override // com.android.server.pm.UserManagerInternal
        public boolean isUserUnlocked(int i) {
            int i2;
            synchronized (com.android.server.pm.UserManagerService.this.mUserStates) {
                i2 = com.android.server.pm.UserManagerService.this.mUserStates.get(i, -1);
            }
            if (i2 == 4 || i2 == 5) {
                return android.os.storage.StorageManager.isCeStorageUnlocked(i);
            }
            return i2 == 3;
        }

        @Override // com.android.server.pm.UserManagerInternal
        public boolean isUserInitialized(int i) {
            android.content.pm.UserInfo userInfo = getUserInfo(i);
            return (userInfo == null || (userInfo.flags & 16) == 0) ? false : true;
        }

        @Override // com.android.server.pm.UserManagerInternal
        public boolean exists(int i) {
            return com.android.server.pm.UserManagerService.this.getUserInfoNoChecks(i) != null;
        }

        @Override // com.android.server.pm.UserManagerInternal
        public boolean isProfileAccessible(int i, int i2, java.lang.String str, boolean z) {
            if (i2 == i) {
                return true;
            }
            synchronized (com.android.server.pm.UserManagerService.this.mUsersLock) {
                try {
                    android.content.pm.UserInfo userInfoLU = com.android.server.pm.UserManagerService.this.getUserInfoLU(i);
                    if (userInfoLU == null || userInfoLU.isProfile()) {
                        if (z) {
                            throw new java.lang.SecurityException(str + " for another profile " + i2 + " from " + i);
                        }
                        android.util.Slog.w(com.android.server.pm.UserManagerService.LOG_TAG, str + " for another profile " + i2 + " from " + i);
                        return false;
                    }
                    android.content.pm.UserInfo userInfoLU2 = com.android.server.pm.UserManagerService.this.getUserInfoLU(i2);
                    if (userInfoLU2 == null || !userInfoLU2.isEnabled()) {
                        if (z) {
                            android.util.Slog.w(com.android.server.pm.UserManagerService.LOG_TAG, str + " for disabled profile " + i2 + " from " + i);
                        }
                        return false;
                    }
                    if (userInfoLU2.profileGroupId != -10000 && userInfoLU2.profileGroupId == userInfoLU.profileGroupId) {
                        return true;
                    }
                    if (!z) {
                        return false;
                    }
                    throw new java.lang.SecurityException(str + " for unrelated profile " + i2);
                } finally {
                }
            }
        }

        @Override // com.android.server.pm.UserManagerInternal
        public int getProfileParentId(int i) {
            return com.android.server.pm.UserManagerService.this.getProfileParentIdUnchecked(i);
        }

        @Override // com.android.server.pm.UserManagerInternal
        public boolean isSettingRestrictedForUser(java.lang.String str, int i, java.lang.String str2, int i2) {
            return com.android.server.pm.UserManagerService.this.isSettingRestrictedForUser(str, i, str2, i2);
        }

        @Override // com.android.server.pm.UserManagerInternal
        public boolean hasUserRestriction(java.lang.String str, int i) {
            android.os.Bundle effectiveUserRestrictions;
            return com.android.server.pm.UserRestrictionsUtils.isValidRestriction(str) && (effectiveUserRestrictions = com.android.server.pm.UserManagerService.this.getEffectiveUserRestrictions(i)) != null && effectiveUserRestrictions.getBoolean(str);
        }

        @Override // com.android.server.pm.UserManagerInternal
        @android.annotation.Nullable
        public android.content.pm.UserInfo getUserInfo(int i) {
            com.android.server.pm.UserManagerService.UserData userData;
            synchronized (com.android.server.pm.UserManagerService.this.mUsersLock) {
                userData = (com.android.server.pm.UserManagerService.UserData) com.android.server.pm.UserManagerService.this.mUsers.get(i);
            }
            if (userData == null) {
                return null;
            }
            return userData.info;
        }

        @Override // com.android.server.pm.UserManagerInternal
        @android.annotation.NonNull
        public android.content.pm.UserInfo[] getUserInfos() {
            android.content.pm.UserInfo[] userInfoArr;
            synchronized (com.android.server.pm.UserManagerService.this.mUsersLock) {
                try {
                    int size = com.android.server.pm.UserManagerService.this.mUsers.size();
                    userInfoArr = new android.content.pm.UserInfo[size];
                    for (int i = 0; i < size; i++) {
                        userInfoArr[i] = ((com.android.server.pm.UserManagerService.UserData) com.android.server.pm.UserManagerService.this.mUsers.valueAt(i)).info;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return userInfoArr;
        }

        @Override // com.android.server.pm.UserManagerInternal
        public void setDefaultCrossProfileIntentFilters(int i, int i2) {
            com.android.server.pm.UserManagerService.this.setDefaultCrossProfileIntentFilters(i2, com.android.server.pm.UserManagerService.this.getUserTypeDetailsNoChecks(i2), com.android.server.pm.UserManagerService.this.getEffectiveUserRestrictions(i2), i);
        }

        @Override // com.android.server.pm.UserManagerInternal
        public boolean shouldIgnorePrepareStorageErrors(int i) {
            boolean z;
            synchronized (com.android.server.pm.UserManagerService.this.mUsersLock) {
                try {
                    com.android.server.pm.UserManagerService.UserData userData = (com.android.server.pm.UserManagerService.UserData) com.android.server.pm.UserManagerService.this.mUsers.get(i);
                    z = userData != null && userData.getIgnorePrepareStorageErrors();
                } finally {
                }
            }
            return z;
        }

        @Override // com.android.server.pm.UserManagerInternal
        @android.annotation.Nullable
        public android.content.pm.UserProperties getUserProperties(int i) {
            android.content.pm.UserProperties userPropertiesInternal = com.android.server.pm.UserManagerService.this.getUserPropertiesInternal(i);
            if (userPropertiesInternal == null) {
                android.util.Slog.w(com.android.server.pm.UserManagerService.LOG_TAG, "A null UserProperties was returned for user " + i);
            }
            return userPropertiesInternal;
        }

        @Override // com.android.server.pm.UserManagerInternal
        @com.android.server.pm.UserManagerInternal.UserAssignmentResult
        public int assignUserToDisplayOnStart(int i, int i2, @com.android.server.pm.UserManagerInternal.UserStartMode int i3, int i4) {
            android.content.pm.UserProperties userProperties = getUserProperties(i);
            return com.android.server.pm.UserManagerService.this.mUserVisibilityMediator.assignUserToDisplayOnStart(i, i2, i3, i4, userProperties != null && userProperties.getAlwaysVisible());
        }

        @Override // com.android.server.pm.UserManagerInternal
        public boolean assignUserToExtraDisplay(int i, int i2) {
            return com.android.server.pm.UserManagerService.this.mUserVisibilityMediator.assignUserToExtraDisplay(i, i2);
        }

        @Override // com.android.server.pm.UserManagerInternal
        public boolean unassignUserFromExtraDisplay(int i, int i2) {
            return com.android.server.pm.UserManagerService.this.mUserVisibilityMediator.unassignUserFromExtraDisplay(i, i2);
        }

        @Override // com.android.server.pm.UserManagerInternal
        public void unassignUserFromDisplayOnStop(int i) {
            com.android.server.pm.UserManagerService.this.mUserVisibilityMediator.unassignUserFromDisplayOnStop(i);
        }

        @Override // com.android.server.pm.UserManagerInternal
        public boolean isUserVisible(int i) {
            return com.android.server.pm.UserManagerService.this.mUserVisibilityMediator.isUserVisible(i);
        }

        @Override // com.android.server.pm.UserManagerInternal
        public boolean isUserVisible(int i, int i2) {
            return com.android.server.pm.UserManagerService.this.mUserVisibilityMediator.isUserVisible(i, i2);
        }

        @Override // com.android.server.pm.UserManagerInternal
        public int getMainDisplayAssignedToUser(int i) {
            return com.android.server.pm.UserManagerService.this.mUserVisibilityMediator.getMainDisplayAssignedToUser(i);
        }

        @Override // com.android.server.pm.UserManagerInternal
        @android.annotation.Nullable
        public int[] getDisplaysAssignedToUser(int i) {
            return com.android.server.pm.UserManagerService.this.mUserVisibilityMediator.getDisplaysAssignedToUser(i);
        }

        @Override // com.android.server.pm.UserManagerInternal
        public int getUserAssignedToDisplay(int i) {
            return com.android.server.pm.UserManagerService.this.mUserVisibilityMediator.getUserAssignedToDisplay(i);
        }

        @Override // com.android.server.pm.UserManagerInternal
        public void addUserVisibilityListener(com.android.server.pm.UserManagerInternal.UserVisibilityListener userVisibilityListener) {
            com.android.server.pm.UserManagerService.this.mUserVisibilityMediator.addListener(userVisibilityListener);
        }

        @Override // com.android.server.pm.UserManagerInternal
        public void removeUserVisibilityListener(com.android.server.pm.UserManagerInternal.UserVisibilityListener userVisibilityListener) {
            com.android.server.pm.UserManagerService.this.mUserVisibilityMediator.removeListener(userVisibilityListener);
        }

        @Override // com.android.server.pm.UserManagerInternal
        public void onSystemUserVisibilityChanged(boolean z) {
            com.android.server.pm.UserManagerService.this.mUserVisibilityMediator.onSystemUserVisibilityChanged(z);
        }

        @Override // com.android.server.pm.UserManagerInternal
        public int[] getUserTypesForStatsd(int[] iArr) {
            if (iArr == null) {
                return null;
            }
            int length = iArr.length;
            int[] iArr2 = new int[length];
            for (int i = 0; i < length; i++) {
                android.content.pm.UserInfo userInfo = getUserInfo(iArr[i]);
                if (userInfo == null) {
                    com.android.server.pm.UserJourneyLogger unused = com.android.server.pm.UserManagerService.this.mUserJourneyLogger;
                    iArr2[i] = com.android.server.pm.UserJourneyLogger.getUserTypeForStatsd("");
                } else {
                    com.android.server.pm.UserJourneyLogger unused2 = com.android.server.pm.UserManagerService.this.mUserJourneyLogger;
                    iArr2[i] = com.android.server.pm.UserJourneyLogger.getUserTypeForStatsd(userInfo.userType);
                }
            }
            return iArr2;
        }

        @Override // com.android.server.pm.UserManagerInternal
        public int getMainUserId() {
            return com.android.server.pm.UserManagerService.this.getMainUserIdUnchecked();
        }

        @Override // com.android.server.pm.UserManagerInternal
        public int getBootUser(boolean z) throws android.os.UserManager.CheckedUserOperationException {
            if (z) {
                com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog();
                timingsTraceAndSlog.traceBegin("wait-boot-user");
                try {
                    if (com.android.server.pm.UserManagerService.this.mBootUserLatch.getCount() != 0) {
                        com.android.server.utils.Slogf.d(com.android.server.pm.UserManagerService.LOG_TAG, "Sleeping for boot user to be set. Max sleep for Time: %d", java.lang.Long.valueOf(com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS));
                    }
                    if (!com.android.server.pm.UserManagerService.this.mBootUserLatch.await(com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS, java.util.concurrent.TimeUnit.MILLISECONDS)) {
                        com.android.server.utils.Slogf.w(com.android.server.pm.UserManagerService.LOG_TAG, "Boot user not set. Timeout: %d", java.lang.Long.valueOf(com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS));
                    }
                } catch (java.lang.InterruptedException e) {
                    java.lang.Thread.currentThread().interrupt();
                    com.android.server.utils.Slogf.w(com.android.server.pm.UserManagerService.LOG_TAG, e, "InterruptedException during wait for boot user.", new java.lang.Object[0]);
                }
                timingsTraceAndSlog.traceEnd();
            }
            return com.android.server.pm.UserManagerService.this.getBootUserUnchecked();
        }

        @Override // com.android.server.pm.UserManagerInternal
        public int getCommunalProfileId() {
            return com.android.server.pm.UserManagerService.this.getCommunalProfileIdUnchecked();
        }
    }

    private void enforceUserRestriction(java.lang.String str, int i, java.lang.String str2) throws android.os.UserManager.CheckedUserOperationException {
        java.lang.String str3;
        if (hasUserRestriction(str, i)) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            if (str2 != null) {
                str3 = str2 + ": ";
            } else {
                str3 = "";
            }
            sb.append(str3);
            sb.append(str);
            sb.append(" is enabled.");
            java.lang.String sb2 = sb.toString();
            android.util.Slog.w(LOG_TAG, sb2);
            throw new android.os.UserManager.CheckedUserOperationException(sb2, 1);
        }
    }

    private void throwCheckedUserOperationException(@android.annotation.NonNull java.lang.String str, int i) throws android.os.UserManager.CheckedUserOperationException {
        android.util.Slog.e(LOG_TAG, str);
        throw new android.os.UserManager.CheckedUserOperationException(str, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeAllUsersExceptSystemAndPermanentAdminMain() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mUsersLock) {
            try {
                int size = this.mUsers.size();
                for (int i = 0; i < size; i++) {
                    android.content.pm.UserInfo userInfo = this.mUsers.valueAt(i).info;
                    if (userInfo.id != 0 && !isNonRemovableMainUser(userInfo)) {
                        arrayList.add(userInfo);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        java.util.Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            removeUser(((android.content.pm.UserInfo) it.next()).id);
        }
    }

    private static void debug(java.lang.String str) {
        android.util.Slog.d(LOG_TAG, str + "");
    }

    @com.android.internal.annotations.VisibleForTesting
    int getMaxUsersOfTypePerParent(java.lang.String str) {
        com.android.server.pm.UserTypeDetails userTypeDetails = this.mUserTypes.get(str);
        if (userTypeDetails == null) {
            return 0;
        }
        return getMaxUsersOfTypePerParent(userTypeDetails);
    }

    private static int getMaxUsersOfTypePerParent(com.android.server.pm.UserTypeDetails userTypeDetails) {
        int maxAllowedPerParent = userTypeDetails.getMaxAllowedPerParent();
        if (!android.os.Build.IS_DEBUGGABLE) {
            return maxAllowedPerParent;
        }
        if (userTypeDetails.isManagedProfile()) {
            return android.os.SystemProperties.getInt("persist.sys.max_profiles", maxAllowedPerParent);
        }
        return maxAllowedPerParent;
    }

    @com.android.internal.annotations.GuardedBy({"mUsersLock"})
    @com.android.internal.annotations.VisibleForTesting
    int getFreeProfileBadgeLU(int i, java.lang.String str) {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        int size = this.mUsers.size();
        for (int i2 = 0; i2 < size; i2++) {
            android.content.pm.UserInfo userInfo = this.mUsers.valueAt(i2).info;
            if (userInfo.userType.equals(str) && userInfo.profileGroupId == i && !this.mRemovingUserIds.get(userInfo.id)) {
                arraySet.add(java.lang.Integer.valueOf(userInfo.profileBadge));
            }
        }
        int maxUsersOfTypePerParent = getMaxUsersOfTypePerParent(str);
        if (maxUsersOfTypePerParent == -1) {
            maxUsersOfTypePerParent = Integer.MAX_VALUE;
        }
        for (int i3 = 0; i3 < maxUsersOfTypePerParent; i3++) {
            if (!arraySet.contains(java.lang.Integer.valueOf(i3))) {
                return i3;
            }
        }
        return 0;
    }

    boolean hasProfile(int i) {
        synchronized (this.mUsersLock) {
            try {
                android.content.pm.UserInfo userInfoLU = getUserInfoLU(i);
                int size = this.mUsers.size();
                for (int i2 = 0; i2 < size; i2++) {
                    android.content.pm.UserInfo userInfo = this.mUsers.valueAt(i2).info;
                    if (i != userInfo.id && isProfileOf(userInfoLU, userInfo)) {
                        return true;
                    }
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void verifyCallingPackage(java.lang.String str, int i) {
        if (this.mPm.snapshotComputer().getPackageUid(str, 0L, android.os.UserHandle.getUserId(i)) != i) {
            throw new java.lang.SecurityException("Specified package " + str + " does not match the calling uid " + i);
        }
    }

    private android.content.pm.PackageManagerInternal getPackageManagerInternal() {
        if (this.mPmInternal == null) {
            this.mPmInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        }
        return this.mPmInternal;
    }

    private android.app.admin.DevicePolicyManagerInternal getDevicePolicyManagerInternal() {
        if (this.mDevicePolicyManagerInternal == null) {
            this.mDevicePolicyManagerInternal = (android.app.admin.DevicePolicyManagerInternal) com.android.server.LocalServices.getService(android.app.admin.DevicePolicyManagerInternal.class);
        }
        return this.mDevicePolicyManagerInternal;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.Nullable
    public android.app.ActivityManagerInternal getActivityManagerInternal() {
        if (this.mAmInternal == null) {
            this.mAmInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
        }
        return this.mAmInternal;
    }

    private boolean isNonRemovableMainUser(android.content.pm.UserInfo userInfo) {
        return userInfo.isMain() && isMainUserPermanentAdmin();
    }

    public boolean isMainUserPermanentAdmin() {
        return android.content.res.Resources.getSystem().getBoolean(android.R.bool.config_intrusiveNotificationLed);
    }

    public boolean canSwitchToHeadlessSystemUser() {
        return android.content.res.Resources.getSystem().getBoolean(android.R.bool.config_canSwitchToHeadlessSystemUser);
    }

    public com.android.server.pm.UserJourneyLogger getUserJourneyLogger() {
        return this.mUserJourneyLogger;
    }
}
