package com.android.server.notification;

/* loaded from: classes2.dex */
public class NotificationManagerService extends com.android.server.SystemService {
    private static final java.lang.String ATTR_VERSION = "version";
    private static final long CHANGE_BACKGROUND_CUSTOM_TOAST_BLOCK = 128611929;
    private static final int DB_VERSION = 1;
    static final float DEFAULT_MAX_NOTIFICATION_ENQUEUE_RATE = 5.0f;
    private static final long DELAY_FOR_ASSISTANT_TIME = 200;
    static final long ENFORCE_NO_CLEAR_FLAG_ON_MEDIA_NOTIFICATION = 264179692;
    private static final int EVENTLOG_ENQUEUE_STATUS_IGNORED = 2;
    private static final int EVENTLOG_ENQUEUE_STATUS_NEW = 0;
    private static final int EVENTLOG_ENQUEUE_STATUS_UPDATE = 1;
    private static final java.lang.String EXTRA_KEY = "key";
    static final int FINISH_TOKEN_TIMEOUT = 11000;
    static final int INVALID_UID = -1;
    private static final java.lang.String LOCKSCREEN_ALLOW_SECURE_NOTIFICATIONS_TAG = "allow-secure-notifications-on-lockscreen";
    private static final java.lang.String LOCKSCREEN_ALLOW_SECURE_NOTIFICATIONS_VALUE = "value";
    static final int LONG_DELAY = 3500;
    static final long MANAGE_GLOBAL_ZEN_VIA_IMPLICIT_RULES = 308670109;
    static final int MATCHES_CALL_FILTER_CONTACTS_TIMEOUT_MS = 3000;
    static final float MATCHES_CALL_FILTER_TIMEOUT_AFFINITY = 1.0f;
    static final int MAX_PACKAGE_NOTIFICATIONS = 50;
    static final int MAX_PACKAGE_TOASTS = 5;
    static final int MESSAGE_DURATION_REACHED = 2;
    static final int MESSAGE_FINISH_TOKEN_TIMEOUT = 7;
    static final int MESSAGE_LISTENER_HINTS_CHANGED = 5;
    static final int MESSAGE_LISTENER_NOTIFICATION_FILTER_CHANGED = 6;
    static final int MESSAGE_ON_PACKAGE_CHANGED = 8;
    private static final int MESSAGE_RANKING_SORT = 1001;
    private static final int MESSAGE_RECONSIDER_RANKING = 1000;
    static final int MESSAGE_SEND_RANKING_UPDATE = 4;
    private static final long MIN_PACKAGE_OVERRATE_LOG_INTERVAL = 5000;
    private static final long NOTIFICATION_CANCELLATION_REASONS = 175319604;
    private static final int NOTIFICATION_INSTANCE_ID_MAX = 8192;
    private static final long NOTIFICATION_LOG_ASSISTANT_CANCEL = 195579280;
    private static final int NOTIFICATION_RAPID_CLEAR_THRESHOLD_MS = 5000;
    private static final long NOTIFICATION_TRAMPOLINE_BLOCK = 167676448;
    private static final long NOTIFICATION_TRAMPOLINE_BLOCK_FOR_EXEMPT_ROLES = 227752274;
    private static final long RATE_LIMIT_TOASTS = 174840628;
    public static final int REPORT_REMOTE_VIEWS = 1;
    private static final int REQUEST_CODE_TIMEOUT = 1;
    static final java.lang.String REVIEW_NOTIF_ACTION_CANCELED = "REVIEW_NOTIF_ACTION_CANCELED";
    static final java.lang.String REVIEW_NOTIF_ACTION_DISMISS = "REVIEW_NOTIF_ACTION_DISMISS";
    static final java.lang.String REVIEW_NOTIF_ACTION_REMIND = "REVIEW_NOTIF_ACTION_REMIND";
    static final int REVIEW_NOTIF_STATE_DISMISSED = 2;
    static final int REVIEW_NOTIF_STATE_RESHOWN = 3;
    static final int REVIEW_NOTIF_STATE_SHOULD_SHOW = 0;
    static final int REVIEW_NOTIF_STATE_UNKNOWN = -1;
    static final int REVIEW_NOTIF_STATE_USER_INTERACTED = 1;
    static final java.lang.String ROOT_PKG = "root";
    private static final java.lang.String SCHEME_TIMEOUT = "timeout";
    static final int SHORT_DELAY = 2000;
    static final long SNOOZE_UNTIL_UNSPECIFIED = -1;
    private static final java.lang.String TAG_NOTIFICATION_POLICY = "notification-policy";
    static final java.lang.String TOAST_QUOTA_TAG = "toast_quota_tag";
    private android.view.accessibility.AccessibilityManager mAccessibilityManager;
    private android.app.ActivityManager mActivityManager;
    private android.app.AlarmManager mAlarmManager;
    private com.android.internal.util.function.TriPredicate<java.lang.String, java.lang.Integer, java.lang.String> mAllowedManagedServicePackages;
    private android.app.IActivityManager mAm;
    private android.app.ActivityManagerInternal mAmi;
    private android.app.AppOpsManager mAppOps;
    private android.app.usage.UsageStatsManagerInternal mAppUsageStats;
    private com.android.server.notification.NotificationManagerService.Archive mArchive;

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.notification.NotificationManagerService.NotificationAssistants mAssistants;
    private com.android.server.wm.ActivityTaskManagerInternal mAtm;
    protected com.android.server.notification.NotificationAttentionHelper mAttentionHelper;
    com.android.server.lights.LogicalLight mAttentionLight;
    android.media.AudioManager mAudioManager;
    android.media.AudioManagerInternal mAudioManagerInternal;
    private int mAutoGroupAtCount;

    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    final android.util.ArrayMap<java.lang.Integer, android.util.ArrayMap<java.lang.String, java.lang.String>> mAutobundledSummaries;
    final android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.Integer, android.os.RemoteCallbackList<android.app.ICallNotificationEventCallback>>> mCallNotificationEventCallbacks;
    private android.os.Binder mCallNotificationToken;
    private int mCallState;

    @com.android.internal.annotations.VisibleForTesting
    android.companion.ICompanionDeviceManager mCompanionManager;
    private com.android.server.notification.ConditionProviders mConditionProviders;
    private java.lang.String mDefaultSearchSelectorPkg;
    private android.provider.DeviceConfig.OnPropertiesChangedListener mDeviceConfigChangedListener;
    private android.os.DeviceIdleManager mDeviceIdleManager;
    private boolean mDisableNotificationEffects;
    private android.app.admin.DevicePolicyManagerInternal mDpm;
    private java.util.List<android.content.ComponentName> mEffectsSuppressors;

    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    final java.util.ArrayList<com.android.server.notification.NotificationRecord> mEnqueuedNotifications;
    private com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.FlagResolver mFlagResolver;
    final android.os.IBinder mForegroundToken;
    private com.android.server.notification.GroupHelper mGroupHelper;

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.notification.NotificationManagerService.WorkerHandler mHandler;
    boolean mHasLight;
    private com.android.server.notification.NotificationHistoryManager mHistoryManager;
    private android.media.AudioAttributes mInCallNotificationAudioAttributes;
    private android.net.Uri mInCallNotificationUri;
    private float mInCallNotificationVolume;
    protected boolean mInCallStateOffHook;

    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    final android.util.ArrayMap<java.lang.String, com.android.server.notification.InlineReplyUriRecord> mInlineReplyRecordsByKey;
    private final android.content.BroadcastReceiver mIntentReceiver;
    private final com.android.server.notification.NotificationManagerInternal mInternalService;
    private int mInterruptionFilter;
    private boolean mIsAutomotive;

    @com.android.internal.annotations.GuardedBy({"mToastQueue"})
    private boolean mIsCurrentToastShown;
    private boolean mIsTelevision;
    private android.app.KeyguardManager mKeyguardManager;
    private long mLastOverRateLogTime;
    java.util.ArrayList<java.lang.String> mLights;
    private int mListenerHints;
    private com.android.server.notification.NotificationManagerService.NotificationListeners mListeners;
    private final android.util.SparseArray<android.util.ArraySet<android.content.ComponentName>> mListenersDisablingEffects;
    protected final android.content.BroadcastReceiver mLocaleChangeReceiver;
    private boolean mLockScreenAllowSecureNotifications;
    private float mMaxPackageEnqueueRate;
    private com.android.internal.logging.MetricsLogger mMetricsLogger;
    private java.util.Set<java.lang.String> mMsgPkgsAllowedAsConvos;
    private com.android.server.notification.NotificationChannelLogger mNotificationChannelLogger;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.notification.NotificationDelegate mNotificationDelegate;
    private boolean mNotificationEffectsEnabledForAutomotive;
    private com.android.internal.logging.InstanceIdSequence mNotificationInstanceIdSequence;
    private com.android.server.lights.LogicalLight mNotificationLight;

    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    final java.util.ArrayList<com.android.server.notification.NotificationRecord> mNotificationList;
    final java.lang.Object mNotificationLock;
    com.android.server.notification.NotificationManagerPrivate mNotificationManagerPrivate;
    boolean mNotificationPulseEnabled;
    private com.android.server.notification.NotificationRecordLogger mNotificationRecordLogger;
    private final android.content.BroadcastReceiver mNotificationTimeoutReceiver;

    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    final android.util.ArrayMap<java.lang.String, com.android.server.notification.NotificationRecord> mNotificationsByKey;
    private final android.content.BroadcastReceiver mPackageIntentReceiver;

    @com.android.internal.annotations.VisibleForTesting
    android.content.pm.IPackageManager mPackageManager;
    private android.content.pm.PackageManager mPackageManagerClient;
    android.content.pm.PackageManagerInternal mPackageManagerInternal;
    private com.android.server.notification.PermissionHelper mPermissionHelper;
    private android.permission.PermissionManager mPermissionManager;
    private com.android.server.policy.PermissionPolicyInternal mPermissionPolicyInternal;
    private com.android.internal.compat.IPlatformCompat mPlatformCompat;
    private android.util.AtomicFile mPolicyFile;
    private com.android.server.notification.NotificationManagerService.PostNotificationTrackerFactory mPostNotificationTrackerFactory;
    private android.os.PowerManager mPowerManager;

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.notification.PreferencesHelper mPreferencesHelper;
    private com.android.server.notification.NotificationManagerService.StatsPullAtomCallbackImpl mPullAtomCallback;
    protected com.android.server.notification.RankingHandler mRankingHandler;

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.notification.RankingHelper mRankingHelper;
    private final android.os.HandlerThread mRankingThread;
    private final android.content.BroadcastReceiver mRestoreReceiver;
    private com.android.server.notification.ReviewNotificationPermissionsReceiver mReviewNotificationPermissionsReceiver;
    private volatile com.android.server.notification.NotificationManagerService.RoleObserver mRoleObserver;
    private final com.android.server.notification.NotificationManagerService.SavePolicyFileRunnable mSavePolicyFile;
    boolean mScreenOn;

    @com.android.internal.annotations.VisibleForTesting
    final android.os.IBinder mService;
    private com.android.server.notification.NotificationManagerService.SettingsObserver mSettingsObserver;
    private com.android.server.notification.ShortcutHelper mShortcutHelper;
    private com.android.server.notification.ShortcutHelper.ShortcutListener mShortcutListener;

    @com.android.internal.annotations.VisibleForTesting
    protected boolean mShowReviewPermissionsNotification;
    protected com.android.server.notification.SnoozeHelper mSnoozeHelper;
    private java.lang.String mSoundNotificationKey;
    private android.app.StatsManager mStatsManager;

    @android.annotation.Nullable
    com.android.server.statusbar.StatusBarManagerInternal mStatusBar;
    private int mStripRemoteViewsSizeBytes;
    private com.android.server.notification.NotificationManagerService.StrongAuthTracker mStrongAuthTracker;
    final android.util.ArrayMap<java.lang.String, com.android.server.notification.NotificationRecord> mSummaryByGroupKey;
    boolean mSystemExemptFromDismissal;
    boolean mSystemReady;
    private android.telecom.TelecomManager mTelecomManager;
    final java.util.ArrayList<com.android.server.notification.toast.ToastRecord> mToastQueue;
    private com.android.server.utils.quota.MultiRateLimiter mToastRateLimiter;

    @com.android.internal.annotations.GuardedBy({"mToastQueue"})
    private final java.util.Set<java.lang.Integer> mToastRateLimitingDisabledUids;
    private android.app.IUriGrantsManager mUgm;
    private com.android.server.uri.UriGrantsManagerInternal mUgmInternal;
    private android.os.UserManager mUm;
    private com.android.server.pm.UserManagerInternal mUmInternal;
    private com.android.server.notification.NotificationUsageStats mUsageStats;
    private android.app.usage.UsageStatsManagerInternal mUsageStatsManagerInternal;
    private boolean mUseAttentionLight;
    private final com.android.server.notification.ManagedServices.UserProfiles mUserProfiles;
    private java.lang.String mVibrateNotificationKey;
    private com.android.server.notification.VibratorHelper mVibratorHelper;
    private int mWarnRemoteViewsSizeBytes;
    private com.android.server.wm.WindowManagerInternal mWindowManagerInternal;
    protected com.android.server.notification.ZenModeHelper mZenModeHelper;
    public static final java.lang.String TAG = "NotificationService";
    public static final boolean DBG = android.util.Log.isLoggable(TAG, 3);
    public static final boolean ENABLE_CHILD_NOTIFICATIONS = android.os.SystemProperties.getBoolean("debug.child_notifs", true);
    static final boolean DEBUG_INTERRUPTIVENESS = android.os.SystemProperties.getBoolean("debug.notification.interruptiveness", false);
    static final java.time.Duration BITMAP_DURATION = java.time.Duration.ofHours(24);
    static final java.lang.String[] ALLOWED_ADJUSTMENTS = {"key_people", "key_snooze_criteria", "key_user_sentiment", "key_contextual_actions", "key_text_replies", "key_importance", "key_importance_proposal", "key_sensitive_content", "key_ranking_score", "key_not_conversation"};
    static final java.lang.String[] NON_BLOCKABLE_DEFAULT_ROLES = {"android.app.role.DIALER", "android.app.role.EMERGENCY"};
    private static final com.android.server.utils.quota.MultiRateLimiter.RateLimit[] TOAST_RATE_LIMITS = {com.android.server.utils.quota.MultiRateLimiter.RateLimit.create(3, java.time.Duration.ofSeconds(20)), com.android.server.utils.quota.MultiRateLimiter.RateLimit.create(5, java.time.Duration.ofSeconds(42)), com.android.server.utils.quota.MultiRateLimiter.RateLimit.create(6, java.time.Duration.ofSeconds(68))};
    private static final java.lang.String ACTION_NOTIFICATION_TIMEOUT = com.android.server.notification.NotificationManagerService.class.getSimpleName() + ".TIMEOUT";
    private static final java.time.Duration POST_WAKE_LOCK_TIMEOUT = java.time.Duration.ofSeconds(30);
    private static final int MY_UID = android.os.Process.myUid();
    private static final int MY_PID = android.os.Process.myPid();
    static final android.os.IBinder ALLOWLIST_TOKEN = new android.os.Binder();

    /* JADX INFO: Access modifiers changed from: private */
    interface FlagChecker {
        boolean apply(int i);
    }

    static class Archive {
        final int mBufferSize;
        final java.lang.Object mBufferLock = new java.lang.Object();

        @com.android.internal.annotations.GuardedBy({"mBufferLock"})
        final java.util.LinkedList<android.util.Pair<android.service.notification.StatusBarNotification, java.lang.Integer>> mBuffer = new java.util.LinkedList<>();
        final android.util.SparseArray<java.lang.Boolean> mEnabled = new android.util.SparseArray<>();

        public Archive(int i) {
            this.mBufferSize = i;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            int size = this.mBuffer.size();
            sb.append("Archive (");
            sb.append(size);
            sb.append(" notification");
            sb.append(size == 1 ? ")" : "s)");
            return sb.toString();
        }

        public void record(android.service.notification.StatusBarNotification statusBarNotification, int i) {
            if (!this.mEnabled.get(statusBarNotification.getNormalizedUserId(), false).booleanValue()) {
                return;
            }
            synchronized (this.mBufferLock) {
                try {
                    if (this.mBuffer.size() == this.mBufferSize) {
                        this.mBuffer.removeFirst();
                    }
                    this.mBuffer.addLast(new android.util.Pair<>(statusBarNotification.cloneLight(), java.lang.Integer.valueOf(i)));
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public java.util.Iterator<android.util.Pair<android.service.notification.StatusBarNotification, java.lang.Integer>> descendingIterator() {
            return this.mBuffer.descendingIterator();
        }

        public android.service.notification.StatusBarNotification[] getArray(final android.os.UserManager userManager, int i, boolean z) {
            android.service.notification.StatusBarNotification[] statusBarNotificationArr;
            final java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.add(-1);
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.notification.NotificationManagerService$Archive$$ExternalSyntheticLambda0
                public final void runOrThrow() {
                    com.android.server.notification.NotificationManagerService.Archive.lambda$getArray$0(userManager, arrayList);
                }
            });
            synchronized (this.mBufferLock) {
                if (i == 0) {
                    try {
                        i = this.mBufferSize;
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                java.util.ArrayList arrayList2 = new java.util.ArrayList();
                java.util.Iterator<android.util.Pair<android.service.notification.StatusBarNotification, java.lang.Integer>> descendingIterator = descendingIterator();
                int i2 = 0;
                while (descendingIterator.hasNext() && i2 < i) {
                    android.util.Pair<android.service.notification.StatusBarNotification, java.lang.Integer> next = descendingIterator.next();
                    if ((((java.lang.Integer) next.second).intValue() != 18 || z) && arrayList.contains(java.lang.Integer.valueOf(((android.service.notification.StatusBarNotification) next.first).getUserId()))) {
                        i2++;
                        arrayList2.add((android.service.notification.StatusBarNotification) next.first);
                    }
                }
                statusBarNotificationArr = (android.service.notification.StatusBarNotification[]) arrayList2.toArray(new android.service.notification.StatusBarNotification[arrayList2.size()]);
            }
            return statusBarNotificationArr;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$getArray$0(android.os.UserManager userManager, java.util.ArrayList arrayList) throws java.lang.Exception {
            for (int i : userManager.getProfileIds(android.app.ActivityManager.getCurrentUser(), false)) {
                arrayList.add(java.lang.Integer.valueOf(i));
            }
        }

        public void updateHistoryEnabled(int i, boolean z) {
            this.mEnabled.put(i, java.lang.Boolean.valueOf(z));
            if (!z) {
                synchronized (this.mBufferLock) {
                    try {
                        for (int size = this.mBuffer.size() - 1; size >= 0; size--) {
                            if (i == ((android.service.notification.StatusBarNotification) this.mBuffer.get(size).first).getNormalizedUserId()) {
                                this.mBuffer.remove(size);
                            }
                        }
                    } finally {
                    }
                }
            }
        }

        public void removeChannelNotifications(java.lang.String str, int i, java.lang.String str2) {
            synchronized (this.mBufferLock) {
                try {
                    java.util.Iterator<android.util.Pair<android.service.notification.StatusBarNotification, java.lang.Integer>> descendingIterator = descendingIterator();
                    while (descendingIterator.hasNext()) {
                        android.util.Pair<android.service.notification.StatusBarNotification, java.lang.Integer> next = descendingIterator.next();
                        if (next.first != null && i == ((android.service.notification.StatusBarNotification) next.first).getNormalizedUserId() && str != null && str.equals(((android.service.notification.StatusBarNotification) next.first).getPackageName()) && ((android.service.notification.StatusBarNotification) next.first).getNotification() != null && java.util.Objects.equals(str2, ((android.service.notification.StatusBarNotification) next.first).getNotification().getChannelId())) {
                            descendingIterator.remove();
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void removePackageNotifications(java.lang.String str, int i) {
            synchronized (this.mBufferLock) {
                try {
                    java.util.Iterator<android.util.Pair<android.service.notification.StatusBarNotification, java.lang.Integer>> descendingIterator = descendingIterator();
                    while (descendingIterator.hasNext()) {
                        android.util.Pair<android.service.notification.StatusBarNotification, java.lang.Integer> next = descendingIterator.next();
                        if (next.first != null && i == ((android.service.notification.StatusBarNotification) next.first).getNormalizedUserId() && str != null && str.equals(((android.service.notification.StatusBarNotification) next.first).getPackageName()) && ((android.service.notification.StatusBarNotification) next.first).getNotification() != null) {
                            descendingIterator.remove();
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        void dumpImpl(java.io.PrintWriter printWriter, @android.annotation.NonNull com.android.server.notification.NotificationManagerService.DumpFilter dumpFilter) {
            synchronized (this.mBufferLock) {
                try {
                    java.util.Iterator<android.util.Pair<android.service.notification.StatusBarNotification, java.lang.Integer>> descendingIterator = descendingIterator();
                    int i = 0;
                    while (true) {
                        if (!descendingIterator.hasNext()) {
                            break;
                        }
                        android.service.notification.StatusBarNotification statusBarNotification = (android.service.notification.StatusBarNotification) descendingIterator.next().first;
                        if (dumpFilter == null || dumpFilter.matches(statusBarNotification)) {
                            printWriter.println("    " + statusBarNotification);
                            i++;
                            if (i >= 5) {
                                if (descendingIterator.hasNext()) {
                                    printWriter.println("    ...");
                                }
                            }
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    void loadDefaultApprovedServices(int i) {
        this.mListeners.loadDefaultsFromConfig();
        this.mConditionProviders.loadDefaultsFromConfig();
        this.mAssistants.loadDefaultsFromConfig();
    }

    protected void allowDefaultApprovedServices(int i) {
        android.util.ArraySet<android.content.ComponentName> defaultComponents = this.mListeners.getDefaultComponents();
        for (int i2 = 0; i2 < defaultComponents.size(); i2++) {
            allowNotificationListener(i, defaultComponents.valueAt(i2));
        }
        allowDndPackages(i);
        setDefaultAssistantForUser(i);
    }

    @com.android.internal.annotations.VisibleForTesting
    void allowDndPackages(int i) {
        android.util.ArraySet<java.lang.String> defaultPackages = this.mConditionProviders.getDefaultPackages();
        for (int i2 = 0; i2 < defaultPackages.size(); i2++) {
            allowDndPackage(i, defaultPackages.valueAt(i2));
        }
        if (!isDNDMigrationDone(i)) {
            setDNDMigrationDone(i);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isDNDMigrationDone(int i) {
        return android.provider.Settings.Secure.getIntForUser(getContext().getContentResolver(), "dnd_settings_migrated", 0, i) == 1;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setDNDMigrationDone(int i) {
        android.provider.Settings.Secure.putIntForUser(getContext().getContentResolver(), "dnd_settings_migrated", 1, i);
    }

    protected void migrateDefaultNAS() {
        for (android.content.pm.UserInfo userInfo : this.mUm.getUsers()) {
            int identifier = userInfo.getUserHandle().getIdentifier();
            if (!isNASMigrationDone(identifier) && !userInfo.isManagedProfile() && !userInfo.isCloneProfile()) {
                if (this.mAssistants.getAllowedComponents(identifier).size() == 0) {
                    android.util.Slog.d(TAG, "NAS Migration: user set to none, disable new NAS setting");
                    setNASMigrationDone(identifier);
                    this.mAssistants.clearDefaults();
                } else {
                    android.util.Slog.d(TAG, "Reset NAS setting and migrate to new default");
                    resetAssistantUserSet(identifier);
                    this.mAssistants.resetDefaultAssistantsIfNecessary();
                }
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void setNASMigrationDone(int i) {
        for (int i2 : this.mUm.getProfileIds(i, false)) {
            android.provider.Settings.Secure.putIntForUser(getContext().getContentResolver(), "nas_settings_updated", 1, i2);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isNASMigrationDone(int i) {
        return android.provider.Settings.Secure.getIntForUser(getContext().getContentResolver(), "nas_settings_updated", 0, i) == 1;
    }

    protected void setDefaultAssistantForUser(int i) {
        java.lang.String property = android.provider.DeviceConfig.getProperty("systemui", "nas_default_service");
        if (property != null) {
            android.util.ArraySet<android.content.ComponentName> queryPackageForServices = this.mAssistants.queryPackageForServices(property, 786432, i);
            for (int i2 = 0; i2 < queryPackageForServices.size(); i2++) {
                if (allowAssistant(i, queryPackageForServices.valueAt(i2))) {
                    return;
                }
            }
        }
        android.util.ArraySet<android.content.ComponentName> defaultComponents = this.mAssistants.getDefaultComponents();
        for (int i3 = 0; i3 < defaultComponents.size() && !allowAssistant(i, defaultComponents.valueAt(i3)); i3++) {
        }
    }

    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    protected void updateAutobundledSummaryLocked(int i, java.lang.String str, com.android.server.notification.GroupHelper.NotificationAttributes notificationAttributes, boolean z) {
        java.lang.String str2;
        com.android.server.notification.NotificationRecord notificationRecord;
        android.util.ArrayMap<java.lang.String, java.lang.String> arrayMap = this.mAutobundledSummaries.get(java.lang.Integer.valueOf(i));
        if (arrayMap == null || (str2 = arrayMap.get(str)) == null || (notificationRecord = this.mNotificationsByKey.get(str2)) == null) {
            return;
        }
        int i2 = notificationRecord.getSbn().getNotification().flags;
        boolean z2 = (notificationAttributes.icon.sameAs(notificationRecord.getSbn().getNotification().getSmallIcon()) && notificationAttributes.iconColor == notificationRecord.getSbn().getNotification().color && notificationAttributes.visibility == notificationRecord.getSbn().getNotification().visibility) ? false : true;
        if (i2 != notificationAttributes.flags || z2) {
            android.app.Notification notification = notificationRecord.getSbn().getNotification();
            if (notificationAttributes.flags != -1) {
                i2 = notificationAttributes.flags;
            }
            notification.flags = i2;
            notificationRecord.getSbn().getNotification().setSmallIcon(notificationAttributes.icon);
            notificationRecord.getSbn().getNotification().color = notificationAttributes.iconColor;
            notificationRecord.getSbn().getNotification().visibility = notificationAttributes.visibility;
            this.mHandler.post(new com.android.server.notification.NotificationManagerService.EnqueueNotificationRunnable(i, notificationRecord, z, this.mPostNotificationTrackerFactory.newTracker(null)));
        }
    }

    private void allowDndPackage(int i, java.lang.String str) {
        try {
            getBinderService().setNotificationPolicyAccessGrantedForUser(str, i, true);
        } catch (android.os.RemoteException e) {
            e.printStackTrace();
        }
    }

    private void allowNotificationListener(int i, android.content.ComponentName componentName) {
        try {
            getBinderService().setNotificationListenerAccessGrantedForUser(componentName, i, true, true);
        } catch (android.os.RemoteException e) {
            e.printStackTrace();
        }
    }

    private boolean allowAssistant(int i, android.content.ComponentName componentName) {
        android.util.ArraySet<android.content.ComponentName> queryPackageForServices = this.mAssistants.queryPackageForServices(null, 786432, i);
        if (componentName == null || !queryPackageForServices.contains(componentName)) {
            return false;
        }
        setNotificationAssistantAccessGrantedForUserInternal(componentName, i, true, false);
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x00df A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0038 A[ADDED_TO_REGION, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void readPolicyXml(java.io.InputStream inputStream, boolean z, int i) throws org.xmlpull.v1.XmlPullParserException, java.lang.NumberFormatException, java.io.IOException {
        com.android.modules.utils.TypedXmlPullParser resolvePullParser;
        if (z) {
            resolvePullParser = android.util.Xml.newFastPullParser();
            resolvePullParser.setInput(inputStream, java.nio.charset.StandardCharsets.UTF_8.name());
        } else {
            resolvePullParser = android.util.Xml.resolvePullParser(inputStream);
        }
        com.android.internal.util.XmlUtils.beginDocument(resolvePullParser, TAG_NOTIFICATION_POLICY);
        android.content.pm.UserInfo userInfo = this.mUmInternal.getUserInfo(i);
        boolean z2 = false;
        boolean z3 = z && (userInfo.isManagedProfile() || userInfo.isCloneProfile());
        int depth = resolvePullParser.getDepth();
        while (com.android.internal.util.XmlUtils.nextElementWithin(resolvePullParser, depth)) {
            if ("zen".equals(resolvePullParser.getName())) {
                this.mZenModeHelper.readXml(resolvePullParser, z, i);
            } else if ("ranking".equals(resolvePullParser.getName())) {
                this.mPreferencesHelper.readXml(resolvePullParser, z, i);
            }
            if (this.mListeners.getConfig().xmlTag.equals(resolvePullParser.getName())) {
                if (!z3) {
                    this.mListeners.readXml(resolvePullParser, this.mAllowedManagedServicePackages, z, i);
                    z2 = true;
                    if (!LOCKSCREEN_ALLOW_SECURE_NOTIFICATIONS_TAG.equals(resolvePullParser.getName()) && (!z || i == 0)) {
                        this.mLockScreenAllowSecureNotifications = resolvePullParser.getAttributeBoolean((java.lang.String) null, LOCKSCREEN_ALLOW_SECURE_NOTIFICATIONS_VALUE, true);
                    }
                }
            } else if (this.mAssistants.getConfig().xmlTag.equals(resolvePullParser.getName())) {
                if (!z3) {
                    this.mAssistants.readXml(resolvePullParser, this.mAllowedManagedServicePackages, z, i);
                    z2 = true;
                    if (!LOCKSCREEN_ALLOW_SECURE_NOTIFICATIONS_TAG.equals(resolvePullParser.getName())) {
                        this.mLockScreenAllowSecureNotifications = resolvePullParser.getAttributeBoolean((java.lang.String) null, LOCKSCREEN_ALLOW_SECURE_NOTIFICATIONS_VALUE, true);
                    }
                }
            } else {
                if (this.mConditionProviders.getConfig().xmlTag.equals(resolvePullParser.getName())) {
                    if (!z3) {
                        this.mConditionProviders.readXml(resolvePullParser, this.mAllowedManagedServicePackages, z, i);
                        z2 = true;
                    }
                } else if ("snoozed-notifications".equals(resolvePullParser.getName())) {
                    this.mSnoozeHelper.readXml(resolvePullParser, java.lang.System.currentTimeMillis());
                }
                if (!LOCKSCREEN_ALLOW_SECURE_NOTIFICATIONS_TAG.equals(resolvePullParser.getName())) {
                }
            }
        }
        if (!z2) {
            this.mListeners.migrateToXml();
            this.mAssistants.migrateToXml();
            this.mConditionProviders.migrateToXml();
            handleSavePolicyFile();
        }
        this.mAssistants.resetDefaultAssistantsIfNecessary();
        this.mPreferencesHelper.syncChannelsBypassingDnd();
    }

    @com.android.internal.annotations.VisibleForTesting
    void resetDefaultDndIfNecessary() {
        java.util.Iterator it = this.mUm.getAliveUsers().iterator();
        boolean z = false;
        while (it.hasNext()) {
            int identifier = ((android.content.pm.UserInfo) it.next()).getUserHandle().getIdentifier();
            if (!isDNDMigrationDone(identifier)) {
                z |= this.mConditionProviders.removeDefaultFromConfig(identifier);
                this.mConditionProviders.resetDefaultFromConfig();
                allowDndPackages(identifier);
            }
        }
        if (z) {
            handleSavePolicyFile();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void loadPolicyFile() {
        if (DBG) {
            android.util.Slog.d(TAG, "loadPolicyFile");
        }
        synchronized (this.mPolicyFile) {
            java.io.FileInputStream fileInputStream = null;
            try {
                try {
                    try {
                        fileInputStream = this.mPolicyFile.openRead();
                        readPolicyXml(fileInputStream, false, -1);
                        if (this.mPackageManagerClient.hasSystemFeature("android.hardware.type.watch")) {
                            resetDefaultDndIfNecessary();
                        }
                    } catch (java.io.IOException e) {
                        android.util.Log.wtf(TAG, "Unable to read notification policy", e);
                    } catch (java.lang.NumberFormatException e2) {
                        android.util.Log.wtf(TAG, "Unable to parse notification policy", e2);
                    }
                } catch (java.io.FileNotFoundException e3) {
                    loadDefaultApprovedServices(0);
                    allowDefaultApprovedServices(0);
                } catch (org.xmlpull.v1.XmlPullParserException e4) {
                    android.util.Log.wtf(TAG, "Unable to parse notification policy", e4);
                }
            } finally {
                libcore.io.IoUtils.closeQuietly(fileInputStream);
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void handleSavePolicyFile() {
        if (!com.android.server.IoThread.getHandler().hasCallbacks(this.mSavePolicyFile)) {
            com.android.server.IoThread.getHandler().postDelayed(this.mSavePolicyFile, 250L);
        }
    }

    private final class SavePolicyFileRunnable implements java.lang.Runnable {
        private SavePolicyFileRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (com.android.server.notification.NotificationManagerService.DBG) {
                android.util.Slog.d(com.android.server.notification.NotificationManagerService.TAG, "handleSavePolicyFile");
            }
            synchronized (com.android.server.notification.NotificationManagerService.this.mPolicyFile) {
                try {
                    java.io.FileOutputStream startWrite = com.android.server.notification.NotificationManagerService.this.mPolicyFile.startWrite();
                    try {
                        com.android.server.notification.NotificationManagerService.this.writePolicyXml(startWrite, false, -1);
                        com.android.server.notification.NotificationManagerService.this.mPolicyFile.finishWrite(startWrite);
                    } catch (java.io.IOException e) {
                        android.util.Slog.w(com.android.server.notification.NotificationManagerService.TAG, "Failed to save policy file, restoring backup", e);
                        com.android.server.notification.NotificationManagerService.this.mPolicyFile.failWrite(startWrite);
                    }
                } catch (java.io.IOException e2) {
                    android.util.Slog.w(com.android.server.notification.NotificationManagerService.TAG, "Failed to save policy file", e2);
                    return;
                }
            }
            android.app.backup.BackupManager.dataChanged(com.android.server.notification.NotificationManagerService.this.getContext().getPackageName());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void writePolicyXml(java.io.OutputStream outputStream, boolean z, int i) throws java.io.IOException {
        com.android.modules.utils.TypedXmlSerializer resolveSerializer;
        if (z) {
            resolveSerializer = android.util.Xml.newFastSerializer();
            resolveSerializer.setOutput(outputStream, java.nio.charset.StandardCharsets.UTF_8.name());
        } else {
            resolveSerializer = android.util.Xml.resolveSerializer(outputStream);
        }
        resolveSerializer.startDocument((java.lang.String) null, true);
        resolveSerializer.startTag((java.lang.String) null, TAG_NOTIFICATION_POLICY);
        resolveSerializer.attributeInt((java.lang.String) null, ATTR_VERSION, 1);
        this.mZenModeHelper.writeXml(resolveSerializer, z, null, i);
        this.mPreferencesHelper.writeXml(resolveSerializer, z, i);
        this.mListeners.writeXml(resolveSerializer, z, i);
        this.mAssistants.writeXml(resolveSerializer, z, i);
        this.mSnoozeHelper.writeXml(resolveSerializer);
        this.mConditionProviders.writeXml(resolveSerializer, z, i);
        if (!z || i == 0) {
            writeSecureNotificationsPolicy(resolveSerializer);
        }
        resolveSerializer.endTag((java.lang.String) null, TAG_NOTIFICATION_POLICY);
        resolveSerializer.endDocument();
    }

    /* renamed from: com.android.server.notification.NotificationManagerService$1, reason: invalid class name */
    class AnonymousClass1 implements com.android.server.notification.NotificationDelegate {
        AnonymousClass1() {
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void prepareForPossibleShutdown() {
            com.android.server.notification.NotificationManagerService.this.mHistoryManager.triggerWriteToDisk();
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void onSetDisabled(int i) {
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                com.android.server.notification.Flags.refactorAttentionHelper();
                com.android.server.notification.NotificationManagerService.this.mAttentionHelper.updateDisableNotificationEffectsLocked(i);
            }
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void onClearAll(int i, int i2, int i3) {
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                com.android.server.notification.NotificationManagerService.this.cancelAllLocked(i, i2, i3, 3, null, true, 34);
            }
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void onNotificationClick(int i, int i2, java.lang.String str, com.android.internal.statusbar.NotificationVisibility notificationVisibility) {
            com.android.server.notification.NotificationManagerService.this.exitIdle();
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                try {
                    com.android.server.notification.NotificationRecord notificationRecord = com.android.server.notification.NotificationManagerService.this.mNotificationsByKey.get(str);
                    if (notificationRecord == null) {
                        android.util.Slog.w(com.android.server.notification.NotificationManagerService.TAG, "No notification with key: " + str);
                        return;
                    }
                    long currentTimeMillis = java.lang.System.currentTimeMillis();
                    com.android.internal.logging.MetricsLogger.action(notificationRecord.getItemLogMaker().setType(4).addTaggedData(798, java.lang.Integer.valueOf(notificationVisibility.rank)).addTaggedData(1395, java.lang.Integer.valueOf(notificationVisibility.count)));
                    com.android.server.notification.NotificationManagerService.this.mNotificationRecordLogger.log(com.android.server.notification.NotificationRecordLogger.NotificationEvent.NOTIFICATION_CLICKED, notificationRecord);
                    com.android.server.EventLogTags.writeNotificationClicked(str, notificationRecord.getLifespanMs(currentTimeMillis), notificationRecord.getFreshnessMs(currentTimeMillis), notificationRecord.getExposureMs(currentTimeMillis), notificationVisibility.rank, notificationVisibility.count);
                    android.service.notification.StatusBarNotification sbn = notificationRecord.getSbn();
                    com.android.server.notification.NotificationManagerService.this.cancelNotification(i, i2, sbn.getPackageName(), sbn.getTag(), sbn.getId(), 16, 36928, false, notificationRecord.getUserId(), 1, notificationVisibility.rank, notificationVisibility.count, null);
                    notificationVisibility.recycle();
                    com.android.server.notification.NotificationManagerService.this.reportUserInteraction(notificationRecord);
                    com.android.server.notification.NotificationManagerService.this.mAssistants.notifyAssistantNotificationClicked(notificationRecord);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void onNotificationActionClick(int i, int i2, java.lang.String str, int i3, android.app.Notification.Action action, com.android.internal.statusbar.NotificationVisibility notificationVisibility, boolean z) {
            com.android.server.notification.NotificationManagerService.this.exitIdle();
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                try {
                    com.android.server.notification.NotificationRecord notificationRecord = com.android.server.notification.NotificationManagerService.this.mNotificationsByKey.get(str);
                    if (notificationRecord == null) {
                        android.util.Slog.w(com.android.server.notification.NotificationManagerService.TAG, "No notification with key: " + str);
                        return;
                    }
                    long currentTimeMillis = java.lang.System.currentTimeMillis();
                    com.android.internal.logging.MetricsLogger.action(notificationRecord.getLogMaker(currentTimeMillis).setCategory(129).setType(4).setSubtype(i3).addTaggedData(798, java.lang.Integer.valueOf(notificationVisibility.rank)).addTaggedData(1395, java.lang.Integer.valueOf(notificationVisibility.count)).addTaggedData(1601, java.lang.Integer.valueOf(action.isContextual() ? 1 : 0)).addTaggedData(1600, java.lang.Integer.valueOf(z ? 1 : 0)).addTaggedData(1629, java.lang.Integer.valueOf(notificationVisibility.location.toMetricsEventEnum())));
                    com.android.server.notification.NotificationManagerService.this.mNotificationRecordLogger.log(com.android.server.notification.NotificationRecordLogger.NotificationEvent.fromAction(i3, z, action.isContextual()), notificationRecord);
                    com.android.server.EventLogTags.writeNotificationActionClicked(str, action.actionIntent.getTarget().toString(), action.actionIntent.getIntent().toString(), i3, notificationRecord.getLifespanMs(currentTimeMillis), notificationRecord.getFreshnessMs(currentTimeMillis), notificationRecord.getExposureMs(currentTimeMillis), notificationVisibility.rank, notificationVisibility.count);
                    notificationVisibility.recycle();
                    com.android.server.notification.NotificationManagerService.this.reportUserInteraction(notificationRecord);
                    com.android.server.notification.NotificationManagerService.this.mAssistants.notifyAssistantActionClicked(notificationRecord, action, z);
                    if (android.app.Flags.lifetimeExtensionRefactor()) {
                        notificationRecord.getSbn().getNotification().flags &= -65537;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void onNotificationClear(int i, int i2, java.lang.String str, int i3, java.lang.String str2, int i4, int i5, com.android.internal.statusbar.NotificationVisibility notificationVisibility) {
            int i6;
            java.lang.String str3;
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                try {
                    com.android.server.notification.NotificationRecord notificationRecord = com.android.server.notification.NotificationManagerService.this.mNotificationsByKey.get(str2);
                    if (notificationRecord == null) {
                        i6 = 0;
                        str3 = null;
                    } else {
                        notificationRecord.recordDismissalSurface(i4);
                        notificationRecord.recordDismissalSentiment(i5);
                        java.lang.String tag = notificationRecord.getSbn().getTag();
                        i6 = notificationRecord.getSbn().getId();
                        str3 = tag;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            com.android.server.notification.NotificationManagerService.this.cancelNotification(i, i2, str, str3, i6, 0, 8192, true, i3, 2, notificationVisibility.rank, notificationVisibility.count, null);
            notificationVisibility.recycle();
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void onPanelRevealed(boolean z, int i) {
            com.android.internal.logging.MetricsLogger.visible(com.android.server.notification.NotificationManagerService.this.getContext(), 127);
            com.android.internal.logging.MetricsLogger.histogram(com.android.server.notification.NotificationManagerService.this.getContext(), "note_load", i);
            com.android.server.notification.NotificationManagerService.this.mNotificationRecordLogger.log(com.android.server.notification.NotificationRecordLogger.NotificationPanelEvent.NOTIFICATION_PANEL_OPEN);
            com.android.server.EventLogTags.writeNotificationPanelRevealed(i);
            if (z) {
                clearEffects();
            }
            com.android.server.notification.NotificationManagerService.this.mAssistants.onPanelRevealed(i);
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void onPanelHidden() {
            com.android.internal.logging.MetricsLogger.hidden(com.android.server.notification.NotificationManagerService.this.getContext(), 127);
            com.android.server.notification.NotificationManagerService.this.mNotificationRecordLogger.log(com.android.server.notification.NotificationRecordLogger.NotificationPanelEvent.NOTIFICATION_PANEL_CLOSE);
            com.android.server.EventLogTags.writeNotificationPanelHidden();
            com.android.server.notification.NotificationManagerService.this.mAssistants.onPanelHidden();
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void clearEffects() {
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                try {
                    if (com.android.server.notification.NotificationManagerService.DBG) {
                        android.util.Slog.d(com.android.server.notification.NotificationManagerService.TAG, "clearEffects");
                    }
                    com.android.server.notification.Flags.refactorAttentionHelper();
                    com.android.server.notification.NotificationManagerService.this.mAttentionHelper.clearAttentionEffects();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void onNotificationError(int i, int i2, final java.lang.String str, final java.lang.String str2, final int i3, final int i4, final int i5, final java.lang.String str3, int i6) {
            boolean z;
            boolean z2;
            final int i7;
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                try {
                    com.android.server.notification.NotificationRecord findNotificationLocked = com.android.server.notification.NotificationManagerService.this.findNotificationLocked(str, str2, i3, i6);
                    z = (findNotificationLocked == null || (findNotificationLocked.getNotification().flags & 64) == 0) ? false : true;
                    z2 = (findNotificationLocked == null || (findNotificationLocked.getNotification().flags & 32768) == 0) ? false : true;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            com.android.server.notification.NotificationManagerService.this.cancelNotification(i, i2, str, str2, i3, 0, 0, false, i6, 4, null);
            if (z || z2) {
                if (z) {
                    i7 = 3;
                } else {
                    i7 = 6;
                }
                android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.notification.NotificationManagerService$1$$ExternalSyntheticLambda0
                    public final void runOrThrow() {
                        com.android.server.notification.NotificationManagerService.AnonymousClass1.this.lambda$onNotificationError$0(i4, i5, str, str2, i3, str3, i7);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onNotificationError$0(int i, int i2, java.lang.String str, java.lang.String str2, int i3, java.lang.String str3, int i4) throws java.lang.Exception {
            com.android.server.notification.NotificationManagerService.this.mAm.crashApplicationWithType(i, i2, str, -1, "Bad notification(tag=" + str2 + ", id=" + i3 + ") posted from package " + str + ", crashing app(uid=" + i + ", pid=" + i2 + "): " + str3, true, i4);
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void onNotificationVisibilityChanged(com.android.internal.statusbar.NotificationVisibility[] notificationVisibilityArr, com.android.internal.statusbar.NotificationVisibility[] notificationVisibilityArr2) {
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                try {
                    for (com.android.internal.statusbar.NotificationVisibility notificationVisibility : notificationVisibilityArr) {
                        com.android.server.notification.NotificationRecord notificationRecord = com.android.server.notification.NotificationManagerService.this.mNotificationsByKey.get(notificationVisibility.key);
                        if (notificationRecord != null) {
                            if (!notificationRecord.isSeen()) {
                                if (com.android.server.notification.NotificationManagerService.DBG) {
                                    android.util.Slog.d(com.android.server.notification.NotificationManagerService.TAG, "Marking notification as visible " + notificationVisibility.key);
                                }
                                com.android.server.notification.NotificationManagerService.this.reportSeen(notificationRecord);
                            }
                            boolean z = true;
                            notificationRecord.setVisibility(true, notificationVisibility.rank, notificationVisibility.count, com.android.server.notification.NotificationManagerService.this.mNotificationRecordLogger);
                            com.android.server.notification.NotificationManagerService.this.mAssistants.notifyAssistantVisibilityChangedLocked(notificationRecord, true);
                            if (notificationVisibility.location != com.android.internal.statusbar.NotificationVisibility.NotificationLocation.LOCATION_FIRST_HEADS_UP) {
                                z = false;
                            }
                            if (z || notificationRecord.hasBeenVisiblyExpanded()) {
                                com.android.server.notification.NotificationManagerService.this.logSmartSuggestionsVisible(notificationRecord, notificationVisibility.location.toMetricsEventEnum());
                            }
                            com.android.server.notification.NotificationManagerService.this.maybeRecordInterruptionLocked(notificationRecord);
                            notificationVisibility.recycle();
                        }
                    }
                    for (com.android.internal.statusbar.NotificationVisibility notificationVisibility2 : notificationVisibilityArr2) {
                        com.android.server.notification.NotificationRecord notificationRecord2 = com.android.server.notification.NotificationManagerService.this.mNotificationsByKey.get(notificationVisibility2.key);
                        if (notificationRecord2 != null) {
                            notificationRecord2.setVisibility(false, notificationVisibility2.rank, notificationVisibility2.count, com.android.server.notification.NotificationManagerService.this.mNotificationRecordLogger);
                            com.android.server.notification.NotificationManagerService.this.mAssistants.notifyAssistantVisibilityChangedLocked(notificationRecord2, false);
                            notificationVisibility2.recycle();
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void onNotificationExpansionChanged(java.lang.String str, boolean z, boolean z2, int i) {
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                try {
                    com.android.server.notification.NotificationRecord notificationRecord = com.android.server.notification.NotificationManagerService.this.mNotificationsByKey.get(str);
                    if (notificationRecord != null) {
                        notificationRecord.stats.onExpansionChanged(z, z2);
                        if (notificationRecord.hasBeenVisiblyExpanded()) {
                            com.android.server.notification.NotificationManagerService.this.logSmartSuggestionsVisible(notificationRecord, i);
                        }
                        if (z) {
                            com.android.internal.logging.MetricsLogger.action(notificationRecord.getItemLogMaker().setType(z2 ? 3 : 14));
                            com.android.server.notification.NotificationManagerService.this.mNotificationRecordLogger.log(com.android.server.notification.NotificationRecordLogger.NotificationEvent.fromExpanded(z2, z), notificationRecord);
                        }
                        if (z2 && z) {
                            notificationRecord.recordExpanded();
                            com.android.server.notification.NotificationManagerService.this.reportUserInteraction(notificationRecord);
                        }
                        com.android.server.notification.NotificationManagerService.this.mAssistants.notifyAssistantExpansionChangedLocked(notificationRecord.getSbn(), notificationRecord.getNotificationType(), z, z2);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void onNotificationDirectReplied(java.lang.String str) {
            java.lang.String str2;
            int i;
            com.android.server.notification.NotificationManagerService.this.exitIdle();
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                try {
                    com.android.server.notification.NotificationRecord notificationRecord = com.android.server.notification.NotificationManagerService.this.mNotificationsByKey.get(str);
                    if (notificationRecord == null) {
                        str2 = null;
                    } else {
                        str2 = notificationRecord.getSbn().getPackageName();
                    }
                } finally {
                }
            }
            if (android.app.Flags.lifetimeExtensionRefactor() && str2 != null) {
                i = com.android.server.notification.NotificationManagerService.this.getPackageImportanceWithIdentity(str2);
            } else {
                i = 0;
            }
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                try {
                    com.android.server.notification.NotificationRecord notificationRecord2 = com.android.server.notification.NotificationManagerService.this.mNotificationsByKey.get(str);
                    if (notificationRecord2 != null) {
                        if (android.app.Flags.lifetimeExtensionRefactor()) {
                            com.android.server.notification.NotificationManagerService.this.maybeNotifySystemUiListenerLifetimeExtendedLocked(notificationRecord2, notificationRecord2.getSbn().getPackageName(), i);
                        }
                        notificationRecord2.recordDirectReplied();
                        com.android.server.notification.NotificationManagerService.this.mMetricsLogger.write(notificationRecord2.getLogMaker().setCategory(1590).setType(4));
                        com.android.server.notification.NotificationManagerService.this.mNotificationRecordLogger.log(com.android.server.notification.NotificationRecordLogger.NotificationEvent.NOTIFICATION_DIRECT_REPLIED, notificationRecord2);
                        com.android.server.notification.NotificationManagerService.this.reportUserInteraction(notificationRecord2);
                        com.android.server.notification.NotificationManagerService.this.mAssistants.notifyAssistantNotificationDirectReplyLocked(notificationRecord2);
                    }
                } finally {
                }
            }
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void onNotificationSmartSuggestionsAdded(java.lang.String str, int i, int i2, boolean z, boolean z2) {
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                try {
                    com.android.server.notification.NotificationRecord notificationRecord = com.android.server.notification.NotificationManagerService.this.mNotificationsByKey.get(str);
                    if (notificationRecord != null) {
                        notificationRecord.setNumSmartRepliesAdded(i);
                        notificationRecord.setNumSmartActionsAdded(i2);
                        notificationRecord.setSuggestionsGeneratedByAssistant(z);
                        notificationRecord.setEditChoicesBeforeSending(z2);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void onNotificationSmartReplySent(java.lang.String str, int i, java.lang.CharSequence charSequence, int i2, boolean z) {
            java.lang.String str2;
            int i3;
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                try {
                    com.android.server.notification.NotificationRecord notificationRecord = com.android.server.notification.NotificationManagerService.this.mNotificationsByKey.get(str);
                    if (notificationRecord == null) {
                        str2 = null;
                    } else {
                        str2 = notificationRecord.getSbn().getPackageName();
                    }
                } finally {
                }
            }
            if (android.app.Flags.lifetimeExtensionRefactor() && str2 != null) {
                i3 = com.android.server.notification.NotificationManagerService.this.getPackageImportanceWithIdentity(str2);
            } else {
                i3 = 0;
            }
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                try {
                    com.android.server.notification.NotificationRecord notificationRecord2 = com.android.server.notification.NotificationManagerService.this.mNotificationsByKey.get(str);
                    if (notificationRecord2 != null) {
                        if (android.app.Flags.lifetimeExtensionRefactor()) {
                            com.android.server.notification.NotificationManagerService.this.maybeNotifySystemUiListenerLifetimeExtendedLocked(notificationRecord2, notificationRecord2.getSbn().getPackageName(), i3);
                        }
                        notificationRecord2.recordSmartReplied();
                        com.android.server.notification.NotificationManagerService.this.mMetricsLogger.write(notificationRecord2.getLogMaker().setCategory(1383).setSubtype(i).addTaggedData(1600, java.lang.Integer.valueOf(notificationRecord2.getSuggestionsGeneratedByAssistant() ? 1 : 0)).addTaggedData(1629, java.lang.Integer.valueOf(i2)).addTaggedData(1647, java.lang.Integer.valueOf(notificationRecord2.getEditChoicesBeforeSending() ? 1 : 0)).addTaggedData(1648, java.lang.Integer.valueOf(z ? 1 : 0)));
                        com.android.server.notification.NotificationManagerService.this.mNotificationRecordLogger.log(com.android.server.notification.NotificationRecordLogger.NotificationEvent.NOTIFICATION_SMART_REPLIED, notificationRecord2);
                        com.android.server.notification.NotificationManagerService.this.reportUserInteraction(notificationRecord2);
                        com.android.server.notification.NotificationManagerService.this.mAssistants.notifyAssistantSuggestedReplySent(notificationRecord2.getSbn(), notificationRecord2.getNotificationType(), charSequence, notificationRecord2.getSuggestionsGeneratedByAssistant());
                    }
                } finally {
                }
            }
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void onNotificationSettingsViewed(java.lang.String str) {
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                try {
                    com.android.server.notification.NotificationRecord notificationRecord = com.android.server.notification.NotificationManagerService.this.mNotificationsByKey.get(str);
                    if (notificationRecord != null) {
                        notificationRecord.recordViewedSettings();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void onNotificationBubbleChanged(java.lang.String str, boolean z, int i) {
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                try {
                    com.android.server.notification.NotificationRecord notificationRecord = com.android.server.notification.NotificationManagerService.this.mNotificationsByKey.get(str);
                    if (notificationRecord != null) {
                        if (!z) {
                            notificationRecord.getNotification().flags &= -4097;
                            notificationRecord.setFlagBubbleRemoved(true);
                        } else {
                            notificationRecord.getNotification().flags |= 8;
                            notificationRecord.setFlagBubbleRemoved(false);
                            if (notificationRecord.getNotification().getBubbleMetadata() != null) {
                                notificationRecord.getNotification().getBubbleMetadata().setFlags(i);
                            }
                            com.android.server.notification.NotificationManagerService.this.mHandler.post(com.android.server.notification.NotificationManagerService.this.new EnqueueNotificationRunnable(notificationRecord.getUser().getIdentifier(), notificationRecord, true, com.android.server.notification.NotificationManagerService.this.mPostNotificationTrackerFactory.newTracker(null)));
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void onBubbleMetadataFlagChanged(java.lang.String str, int i) {
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                try {
                    com.android.server.notification.NotificationRecord notificationRecord = com.android.server.notification.NotificationManagerService.this.mNotificationsByKey.get(str);
                    if (notificationRecord != null) {
                        android.app.Notification.BubbleMetadata bubbleMetadata = notificationRecord.getNotification().getBubbleMetadata();
                        if (bubbleMetadata == null) {
                            return;
                        }
                        if (i != bubbleMetadata.getFlags()) {
                            if (((bubbleMetadata.getFlags() ^ i) & 2) != 0) {
                                com.android.server.notification.Flags.refactorAttentionHelper();
                                com.android.server.notification.NotificationManagerService.this.mAttentionHelper.clearEffectsLocked(str);
                            }
                            bubbleMetadata.setFlags(i);
                            notificationRecord.getNotification().flags |= 8;
                            com.android.server.notification.NotificationManagerService.this.mHandler.post(com.android.server.notification.NotificationManagerService.this.new EnqueueNotificationRunnable(notificationRecord.getUser().getIdentifier(), notificationRecord, true, com.android.server.notification.NotificationManagerService.this.mPostNotificationTrackerFactory.newTracker(null)));
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void grantInlineReplyUriPermission(java.lang.String str, android.net.Uri uri, android.os.UserHandle userHandle, java.lang.String str2, int i) {
            int packageUid;
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                try {
                    com.android.server.notification.InlineReplyUriRecord inlineReplyUriRecord = com.android.server.notification.NotificationManagerService.this.mInlineReplyRecordsByKey.get(str);
                    if (inlineReplyUriRecord == null) {
                        inlineReplyUriRecord = new com.android.server.notification.InlineReplyUriRecord(com.android.server.notification.NotificationManagerService.this.mUgmInternal.newUriPermissionOwner("INLINE_REPLY:" + str), userHandle, str2, str);
                        com.android.server.notification.NotificationManagerService.this.mInlineReplyRecordsByKey.put(str, inlineReplyUriRecord);
                    }
                    android.os.IBinder permissionOwner = inlineReplyUriRecord.getPermissionOwner();
                    int userId = inlineReplyUriRecord.getUserId();
                    if (android.os.UserHandle.getUserId(i) != userId) {
                        try {
                            java.lang.String[] packagesForUid = com.android.server.notification.NotificationManagerService.this.mPackageManager.getPackagesForUid(i);
                            if (packagesForUid == null) {
                                android.util.Log.e(com.android.server.notification.NotificationManagerService.TAG, "Cannot grant uri permission to unknown UID: " + i);
                            }
                            packageUid = com.android.server.notification.NotificationManagerService.this.mPackageManager.getPackageUid(packagesForUid[0], 0L, userId);
                        } catch (android.os.RemoteException e) {
                            android.util.Log.e(com.android.server.notification.NotificationManagerService.TAG, "Cannot talk to package manager", e);
                        }
                        inlineReplyUriRecord.addUri(uri);
                        com.android.server.notification.NotificationManagerService.this.grantUriPermission(permissionOwner, uri, packageUid, inlineReplyUriRecord.getPackageName(), userId);
                    }
                    packageUid = i;
                    inlineReplyUriRecord.addUri(uri);
                    com.android.server.notification.NotificationManagerService.this.grantUriPermission(permissionOwner, uri, packageUid, inlineReplyUriRecord.getPackageName(), userId);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void clearInlineReplyUriPermissions(java.lang.String str, int i) {
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                try {
                    com.android.server.notification.InlineReplyUriRecord inlineReplyUriRecord = com.android.server.notification.NotificationManagerService.this.mInlineReplyRecordsByKey.get(str);
                    if (inlineReplyUriRecord != null) {
                        com.android.server.notification.NotificationManagerService.this.destroyPermissionOwner(inlineReplyUriRecord.getPermissionOwner(), inlineReplyUriRecord.getUserId(), "INLINE_REPLY: " + inlineReplyUriRecord.getKey());
                        com.android.server.notification.NotificationManagerService.this.mInlineReplyRecordsByKey.remove(str);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.notification.NotificationDelegate
        public void onNotificationFeedbackReceived(java.lang.String str, android.os.Bundle bundle) {
            com.android.server.notification.NotificationManagerService.this.exitIdle();
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                try {
                    com.android.server.notification.NotificationRecord notificationRecord = com.android.server.notification.NotificationManagerService.this.mNotificationsByKey.get(str);
                    if (notificationRecord == null) {
                        if (com.android.server.notification.NotificationManagerService.DBG) {
                            android.util.Slog.w(com.android.server.notification.NotificationManagerService.TAG, "No notification with key: " + str);
                        }
                        return;
                    }
                    com.android.server.notification.NotificationManagerService.this.mAssistants.notifyAssistantFeedbackReceived(notificationRecord, bundle);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void logSmartSuggestionsVisible(com.android.server.notification.NotificationRecord notificationRecord, int i) {
        if ((notificationRecord.getNumSmartRepliesAdded() > 0 || notificationRecord.getNumSmartActionsAdded() > 0) && !notificationRecord.hasSeenSmartReplies()) {
            notificationRecord.setSeenSmartReplies(true);
            this.mMetricsLogger.write(notificationRecord.getLogMaker().setCategory(1382).addTaggedData(1384, java.lang.Integer.valueOf(notificationRecord.getNumSmartRepliesAdded())).addTaggedData(android.hardware.audio.common.V2_0.AudioChannelMask.OUT_7POINT1, java.lang.Integer.valueOf(notificationRecord.getNumSmartActionsAdded())).addTaggedData(1600, java.lang.Integer.valueOf(notificationRecord.getSuggestionsGeneratedByAssistant() ? 1 : 0)).addTaggedData(1629, java.lang.Integer.valueOf(i)).addTaggedData(1647, java.lang.Integer.valueOf(notificationRecord.getEditChoicesBeforeSending() ? 1 : 0)));
            this.mNotificationRecordLogger.log(com.android.server.notification.NotificationRecordLogger.NotificationEvent.NOTIFICATION_SMART_REPLY_VISIBLE, notificationRecord);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    void clearSoundLocked() {
        this.mSoundNotificationKey = null;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.media.IRingtonePlayer ringtonePlayer = this.mAudioManager.getRingtonePlayer();
            if (ringtonePlayer != null) {
                ringtonePlayer.stopAsync();
            }
        } catch (android.os.RemoteException e) {
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    void clearVibrateLocked() {
        this.mVibrateNotificationKey = null;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mVibratorHelper.cancelVibration();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    private void clearLightsLocked() {
        this.mLights.clear();
        updateLightsLocked();
    }

    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    private void clearEffectsLocked(java.lang.String str) {
        if (str.equals(this.mSoundNotificationKey)) {
            clearSoundLocked();
        }
        if (str.equals(this.mVibrateNotificationKey)) {
            clearVibrateLocked();
        }
        if (this.mLights.remove(str)) {
            updateLightsLocked();
        }
    }

    private final class SettingsObserver extends android.database.ContentObserver {
        private final android.net.Uri LOCK_SCREEN_ALLOW_PRIVATE_NOTIFICATIONS;
        private final android.net.Uri LOCK_SCREEN_SHOW_NOTIFICATIONS;
        private final android.net.Uri NOTIFICATION_BADGING_URI;
        private final android.net.Uri NOTIFICATION_BUBBLES_URI;
        private final android.net.Uri NOTIFICATION_HISTORY_ENABLED;
        private final android.net.Uri NOTIFICATION_LIGHT_PULSE_URI;
        private final android.net.Uri NOTIFICATION_RATE_LIMIT_URI;
        private final android.net.Uri NOTIFICATION_SHOW_MEDIA_ON_QUICK_SETTINGS_URI;
        private final android.net.Uri SHOW_NOTIFICATION_SNOOZE;

        SettingsObserver(android.os.Handler handler) {
            super(handler);
            this.NOTIFICATION_BADGING_URI = android.provider.Settings.Secure.getUriFor("notification_badging");
            this.NOTIFICATION_BUBBLES_URI = android.provider.Settings.Secure.getUriFor("notification_bubbles");
            this.NOTIFICATION_LIGHT_PULSE_URI = android.provider.Settings.System.getUriFor("notification_light_pulse");
            this.NOTIFICATION_RATE_LIMIT_URI = android.provider.Settings.Global.getUriFor("max_notification_enqueue_rate");
            this.NOTIFICATION_HISTORY_ENABLED = android.provider.Settings.Secure.getUriFor("notification_history_enabled");
            this.NOTIFICATION_SHOW_MEDIA_ON_QUICK_SETTINGS_URI = android.provider.Settings.Global.getUriFor("qs_media_controls");
            this.LOCK_SCREEN_ALLOW_PRIVATE_NOTIFICATIONS = android.provider.Settings.Secure.getUriFor("lock_screen_allow_private_notifications");
            this.LOCK_SCREEN_SHOW_NOTIFICATIONS = android.provider.Settings.Secure.getUriFor("lock_screen_show_notifications");
            this.SHOW_NOTIFICATION_SNOOZE = android.provider.Settings.Secure.getUriFor("show_notification_snooze");
        }

        void observe() {
            android.content.ContentResolver contentResolver = com.android.server.notification.NotificationManagerService.this.getContext().getContentResolver();
            contentResolver.registerContentObserver(this.NOTIFICATION_BADGING_URI, false, this, -1);
            com.android.server.notification.Flags.refactorAttentionHelper();
            contentResolver.registerContentObserver(this.NOTIFICATION_RATE_LIMIT_URI, false, this, -1);
            contentResolver.registerContentObserver(this.NOTIFICATION_BUBBLES_URI, false, this, -1);
            contentResolver.registerContentObserver(this.NOTIFICATION_HISTORY_ENABLED, false, this, -1);
            contentResolver.registerContentObserver(this.NOTIFICATION_SHOW_MEDIA_ON_QUICK_SETTINGS_URI, false, this, -1);
            contentResolver.registerContentObserver(this.LOCK_SCREEN_ALLOW_PRIVATE_NOTIFICATIONS, false, this, -1);
            contentResolver.registerContentObserver(this.LOCK_SCREEN_SHOW_NOTIFICATIONS, false, this, -1);
            contentResolver.registerContentObserver(this.SHOW_NOTIFICATION_SNOOZE, false, this, -1);
            update(null);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri, int i) {
            update(uri);
        }

        public void update(android.net.Uri uri) {
            android.content.ContentResolver contentResolver = com.android.server.notification.NotificationManagerService.this.getContext().getContentResolver();
            com.android.server.notification.Flags.refactorAttentionHelper();
            if (uri == null || this.NOTIFICATION_RATE_LIMIT_URI.equals(uri)) {
                com.android.server.notification.NotificationManagerService.this.mMaxPackageEnqueueRate = android.provider.Settings.Global.getFloat(contentResolver, "max_notification_enqueue_rate", com.android.server.notification.NotificationManagerService.this.mMaxPackageEnqueueRate);
            }
            if (uri == null || this.NOTIFICATION_BADGING_URI.equals(uri)) {
                com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.updateBadgingEnabled();
            }
            if (uri == null || this.NOTIFICATION_BUBBLES_URI.equals(uri)) {
                com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.updateBubblesEnabled();
            }
            if (uri == null || this.NOTIFICATION_HISTORY_ENABLED.equals(uri)) {
                java.util.Iterator it = com.android.server.notification.NotificationManagerService.this.mUm.getUsers().iterator();
                while (it.hasNext()) {
                    update(uri, ((android.content.pm.UserInfo) it.next()).id);
                }
            }
            if (uri == null || this.NOTIFICATION_SHOW_MEDIA_ON_QUICK_SETTINGS_URI.equals(uri)) {
                com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.updateMediaNotificationFilteringEnabled();
            }
            if (uri == null || this.LOCK_SCREEN_ALLOW_PRIVATE_NOTIFICATIONS.equals(uri)) {
                com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.updateLockScreenPrivateNotifications();
            }
            if (uri == null || this.LOCK_SCREEN_SHOW_NOTIFICATIONS.equals(uri)) {
                com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.updateLockScreenShowNotifications();
            }
            if (this.SHOW_NOTIFICATION_SNOOZE.equals(uri)) {
                if (!(android.provider.Settings.Secure.getIntForUser(contentResolver, "show_notification_snooze", 0, -2) != 0)) {
                    com.android.server.notification.NotificationManagerService.this.unsnoozeAll();
                }
            }
        }

        public void update(android.net.Uri uri, int i) {
            android.content.ContentResolver contentResolver = com.android.server.notification.NotificationManagerService.this.getContext().getContentResolver();
            if (uri == null || this.NOTIFICATION_HISTORY_ENABLED.equals(uri)) {
                com.android.server.notification.NotificationManagerService.this.mArchive.updateHistoryEnabled(i, android.provider.Settings.Secure.getIntForUser(contentResolver, "notification_history_enabled", 0, i) == 1);
            }
        }
    }

    protected class StrongAuthTracker extends com.android.internal.widget.LockPatternUtils.StrongAuthTracker {
        android.util.SparseBooleanArray mUserInLockDownMode;

        StrongAuthTracker(android.content.Context context) {
            super(context);
            this.mUserInLockDownMode = new android.util.SparseBooleanArray();
        }

        private boolean containsFlag(int i, int i2) {
            return (i & i2) != 0;
        }

        public boolean isInLockDownMode(int i) {
            return this.mUserInLockDownMode.get(i, false);
        }

        public synchronized void onStrongAuthRequiredChanged(int i) {
            try {
                boolean containsFlag = containsFlag(getStrongAuthForUser(i), 32);
                if (containsFlag == isInLockDownMode(i)) {
                    return;
                }
                if (containsFlag) {
                    com.android.server.notification.NotificationManagerService.this.cancelNotificationsWhenEnterLockDownMode(i);
                }
                this.mUserInLockDownMode.put(i, containsFlag);
                if (!containsFlag) {
                    com.android.server.notification.NotificationManagerService.this.postNotificationsWhenExitLockDownMode(i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public NotificationManagerService(android.content.Context context) {
        this(context, new com.android.server.notification.NotificationRecordLoggerImpl(), new com.android.internal.logging.InstanceIdSequence(8192));
    }

    @com.android.internal.annotations.VisibleForTesting
    public NotificationManagerService(android.content.Context context, com.android.server.notification.NotificationRecordLogger notificationRecordLogger, com.android.internal.logging.InstanceIdSequence instanceIdSequence) {
        super(context);
        this.mForegroundToken = new android.os.Binder();
        this.mRankingThread = new android.os.HandlerThread("ranker", 10);
        this.mHasLight = true;
        this.mListenersDisablingEffects = new android.util.SparseArray<>();
        this.mEffectsSuppressors = new java.util.ArrayList();
        this.mInterruptionFilter = 0;
        this.mScreenOn = true;
        this.mInCallStateOffHook = false;
        this.mCallNotificationToken = null;
        this.mNotificationLock = new java.lang.Object();
        this.mNotificationList = new java.util.ArrayList<>();
        this.mNotificationsByKey = new android.util.ArrayMap<>();
        this.mInlineReplyRecordsByKey = new android.util.ArrayMap<>();
        this.mEnqueuedNotifications = new java.util.ArrayList<>();
        this.mAutobundledSummaries = new android.util.ArrayMap<>();
        this.mToastQueue = new java.util.ArrayList<>();
        this.mToastRateLimitingDisabledUids = new android.util.ArraySet();
        this.mSummaryByGroupKey = new android.util.ArrayMap<>();
        this.mIsCurrentToastShown = false;
        this.mLights = new java.util.ArrayList<>();
        this.mUserProfiles = new com.android.server.notification.ManagedServices.UserProfiles();
        this.mLockScreenAllowSecureNotifications = true;
        this.mSystemExemptFromDismissal = false;
        this.mCallNotificationEventCallbacks = new android.util.ArrayMap<>();
        this.mMaxPackageEnqueueRate = DEFAULT_MAX_NOTIFICATION_ENQUEUE_RATE;
        this.mSavePolicyFile = new com.android.server.notification.NotificationManagerService.SavePolicyFileRunnable();
        this.mMsgPkgsAllowedAsConvos = new java.util.HashSet();
        this.mNotificationDelegate = new com.android.server.notification.NotificationManagerService.AnonymousClass1();
        this.mNotificationManagerPrivate = new com.android.server.notification.NotificationManagerPrivate() { // from class: com.android.server.notification.NotificationManagerService.2
            @Override // com.android.server.notification.NotificationManagerPrivate
            public com.android.server.notification.NotificationRecord getNotificationByKey(java.lang.String str) {
                com.android.server.notification.NotificationRecord notificationRecord;
                synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                    notificationRecord = com.android.server.notification.NotificationManagerService.this.mNotificationsByKey.get(str);
                }
                return notificationRecord;
            }

            @Override // com.android.server.notification.NotificationManagerPrivate
            public long getNotificationSoundTimeout(java.lang.String str, int i) {
                return com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.getNotificationSoundTimeout(str, i);
            }
        };
        this.mLocaleChangeReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.notification.NotificationManagerService.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                if ("android.intent.action.LOCALE_CHANGED".equals(intent.getAction())) {
                    com.android.internal.notification.SystemNotificationChannels.createAll(context2);
                    com.android.server.notification.NotificationManagerService.this.mZenModeHelper.updateDefaultZenRules(android.os.Binder.getCallingUid());
                    com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.onLocaleChanged(context2, android.app.ActivityManager.getCurrentUser());
                }
            }
        };
        this.mRestoreReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.notification.NotificationManagerService.4
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                if ("android.os.action.SETTING_RESTORED".equals(intent.getAction())) {
                    try {
                        java.lang.String stringExtra = intent.getStringExtra("setting_name");
                        java.lang.String stringExtra2 = intent.getStringExtra("new_value");
                        int intExtra = intent.getIntExtra("restored_from_sdk_int", 0);
                        com.android.server.notification.NotificationManagerService.this.mListeners.onSettingRestored(stringExtra, stringExtra2, intExtra, getSendingUserId());
                        com.android.server.notification.NotificationManagerService.this.mConditionProviders.onSettingRestored(stringExtra, stringExtra2, intExtra, getSendingUserId());
                    } catch (java.lang.Exception e) {
                        android.util.Slog.wtf(com.android.server.notification.NotificationManagerService.TAG, "Cannot restore managed services from settings", e);
                    }
                }
            }
        };
        this.mNotificationTimeoutReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.notification.NotificationManagerService.5
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                com.android.server.notification.NotificationRecord findNotificationByKeyLocked;
                java.lang.String action = intent.getAction();
                if (action != null && com.android.server.notification.NotificationManagerService.ACTION_NOTIFICATION_TIMEOUT.equals(action)) {
                    synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                        findNotificationByKeyLocked = com.android.server.notification.NotificationManagerService.this.findNotificationByKeyLocked(intent.getStringExtra(com.android.server.notification.NotificationManagerService.EXTRA_KEY));
                    }
                    if (findNotificationByKeyLocked != null) {
                        if (android.app.Flags.lifetimeExtensionRefactor()) {
                            com.android.server.notification.NotificationManagerService.this.cancelNotification(findNotificationByKeyLocked.getSbn().getUid(), findNotificationByKeyLocked.getSbn().getInitialPid(), findNotificationByKeyLocked.getSbn().getPackageName(), findNotificationByKeyLocked.getSbn().getTag(), findNotificationByKeyLocked.getSbn().getId(), 0, 98368, true, findNotificationByKeyLocked.getUserId(), 19, null);
                            int packageImportanceWithIdentity = com.android.server.notification.NotificationManagerService.this.getPackageImportanceWithIdentity(findNotificationByKeyLocked.getSbn().getPackageName());
                            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                                com.android.server.notification.NotificationManagerService.this.maybeNotifySystemUiListenerLifetimeExtendedLocked(findNotificationByKeyLocked, findNotificationByKeyLocked.getSbn().getPackageName(), packageImportanceWithIdentity);
                            }
                            return;
                        }
                        com.android.server.notification.NotificationManagerService.this.cancelNotification(findNotificationByKeyLocked.getSbn().getUid(), findNotificationByKeyLocked.getSbn().getInitialPid(), findNotificationByKeyLocked.getSbn().getPackageName(), findNotificationByKeyLocked.getSbn().getTag(), findNotificationByKeyLocked.getSbn().getId(), 0, 32832, true, findNotificationByKeyLocked.getUserId(), 19, null);
                    }
                }
            }
        };
        this.mPackageIntentReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.notification.NotificationManagerService.6
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                boolean z;
                boolean z2;
                java.lang.String schemeSpecificPart;
                boolean z3;
                int[] iArr;
                java.lang.String[] strArr;
                boolean z4;
                boolean z5;
                boolean z6;
                java.lang.String action = intent.getAction();
                if (action == null) {
                    return;
                }
                if (action.equals("android.intent.action.PACKAGE_ADDED")) {
                    z = false;
                    z2 = false;
                } else {
                    z = action.equals("android.intent.action.PACKAGE_REMOVED");
                    if (!z && !action.equals("android.intent.action.PACKAGE_RESTARTED")) {
                        z2 = action.equals("android.intent.action.PACKAGE_CHANGED");
                        if (!z2 && !action.equals("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE") && !action.equals("android.intent.action.PACKAGES_SUSPENDED") && !action.equals("android.intent.action.PACKAGES_UNSUSPENDED") && !action.equals("android.intent.action.DISTRACTING_PACKAGES_CHANGED")) {
                            return;
                        }
                    } else {
                        z2 = false;
                    }
                }
                int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                boolean z7 = true;
                boolean z8 = z && !intent.getBooleanExtra("android.intent.extra.REPLACING", false);
                if (com.android.server.notification.NotificationManagerService.DBG) {
                    android.util.Slog.i(com.android.server.notification.NotificationManagerService.TAG, "action=" + action + " removing=" + z8);
                }
                if (action.equals("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE")) {
                    strArr = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                    iArr = intent.getIntArrayExtra("android.intent.extra.changed_uid_list");
                    z4 = false;
                    z5 = false;
                } else if (action.equals("android.intent.action.PACKAGES_SUSPENDED")) {
                    strArr = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                    iArr = intent.getIntArrayExtra("android.intent.extra.changed_uid_list");
                    z5 = false;
                    z4 = true;
                    z7 = false;
                } else if (action.equals("android.intent.action.PACKAGES_UNSUSPENDED")) {
                    strArr = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                    iArr = intent.getIntArrayExtra("android.intent.extra.changed_uid_list");
                    z4 = false;
                    z5 = true;
                    z7 = false;
                } else if (action.equals("android.intent.action.DISTRACTING_PACKAGES_CHANGED")) {
                    if ((intent.getIntExtra("android.intent.extra.distraction_restrictions", 0) & 2) != 0) {
                        strArr = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                        iArr = intent.getIntArrayExtra("android.intent.extra.changed_uid_list");
                        z6 = false;
                    } else {
                        strArr = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                        iArr = intent.getIntArrayExtra("android.intent.extra.changed_uid_list");
                        z6 = true;
                        z7 = false;
                    }
                    z5 = z6;
                    z4 = z7;
                    z7 = false;
                } else {
                    android.net.Uri data = intent.getData();
                    if (data == null || (schemeSpecificPart = data.getSchemeSpecificPart()) == null) {
                        return;
                    }
                    if (z2) {
                        try {
                            int applicationEnabledSetting = com.android.server.notification.NotificationManagerService.this.mPackageManager.getApplicationEnabledSetting(schemeSpecificPart, intExtra != -1 ? intExtra : 0);
                            if (applicationEnabledSetting != 1 && applicationEnabledSetting != 0) {
                                z3 = true;
                            } else {
                                z3 = false;
                            }
                        } catch (android.os.RemoteException e) {
                        } catch (java.lang.IllegalArgumentException e2) {
                            if (com.android.server.notification.NotificationManagerService.DBG) {
                                android.util.Slog.i(com.android.server.notification.NotificationManagerService.TAG, "Exception trying to look up app enabled setting", e2);
                            }
                        }
                        z7 = z3;
                        iArr = new int[]{intent.getIntExtra("android.intent.extra.UID", -1)};
                        strArr = new java.lang.String[]{schemeSpecificPart};
                        z4 = false;
                        z5 = false;
                    }
                    z3 = true;
                    z7 = z3;
                    iArr = new int[]{intent.getIntExtra("android.intent.extra.UID", -1)};
                    strArr = new java.lang.String[]{schemeSpecificPart};
                    z4 = false;
                    z5 = false;
                }
                if (strArr != null && strArr.length > 0) {
                    if (z7) {
                        for (java.lang.String str : strArr) {
                            com.android.server.notification.NotificationManagerService.this.cancelAllNotificationsInt(com.android.server.notification.NotificationManagerService.MY_UID, com.android.server.notification.NotificationManagerService.MY_PID, str, null, 0, 0, intExtra, 5);
                        }
                    } else if (z4 && iArr != null && iArr.length > 0) {
                        com.android.server.notification.NotificationManagerService.this.hideNotificationsForPackages(strArr, iArr);
                    } else if (z5 && iArr != null && iArr.length > 0) {
                        com.android.server.notification.NotificationManagerService.this.unhideNotificationsForPackages(strArr, iArr);
                    }
                }
                com.android.server.notification.NotificationManagerService.this.mHandler.scheduleOnPackageChanged(z8, intExtra, strArr, iArr);
            }
        };
        this.mIntentReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.notification.NotificationManagerService.7
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                java.lang.String action = intent.getAction();
                com.android.server.notification.Flags.refactorAttentionHelper();
                if (action.equals("android.intent.action.USER_STOPPED")) {
                    int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                    if (intExtra >= 0) {
                        com.android.server.notification.NotificationManagerService.this.cancelAllNotificationsInt(com.android.server.notification.NotificationManagerService.MY_UID, com.android.server.notification.NotificationManagerService.MY_PID, null, null, 0, 0, intExtra, 6);
                        return;
                    }
                    return;
                }
                if (isProfileUnavailable(action)) {
                    int intExtra2 = intent.getIntExtra("android.intent.extra.user_handle", -1);
                    if (intExtra2 >= 0) {
                        com.android.server.notification.NotificationManagerService.this.cancelAllNotificationsInt(com.android.server.notification.NotificationManagerService.MY_UID, com.android.server.notification.NotificationManagerService.MY_PID, null, null, 0, 0, intExtra2, 15);
                        com.android.server.notification.NotificationManagerService.this.mSnoozeHelper.clearData(intExtra2);
                        return;
                    }
                    return;
                }
                if (action.equals("android.intent.action.USER_SWITCHED")) {
                    int intExtra3 = intent.getIntExtra("android.intent.extra.user_handle", com.android.server.am.ProcessList.INVALID_ADJ);
                    com.android.server.notification.NotificationManagerService.this.mUserProfiles.updateCache(context2);
                    if (!com.android.server.notification.NotificationManagerService.this.mUserProfiles.isProfileUser(intExtra3)) {
                        com.android.server.notification.NotificationManagerService.this.mSettingsObserver.update(null);
                        com.android.server.notification.NotificationManagerService.this.mConditionProviders.onUserSwitched(intExtra3);
                        com.android.server.notification.NotificationManagerService.this.mListeners.onUserSwitched(intExtra3);
                        com.android.server.notification.NotificationManagerService.this.mZenModeHelper.onUserSwitched(intExtra3);
                        com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.syncChannelsBypassingDnd();
                    }
                    com.android.server.notification.NotificationManagerService.this.mAssistants.onUserSwitched(intExtra3);
                    return;
                }
                if (action.equals("android.intent.action.USER_ADDED")) {
                    int intExtra4 = intent.getIntExtra("android.intent.extra.user_handle", com.android.server.am.ProcessList.INVALID_ADJ);
                    if (intExtra4 != -10000) {
                        com.android.server.notification.NotificationManagerService.this.mUserProfiles.updateCache(context2);
                        if (!com.android.server.notification.NotificationManagerService.this.mUserProfiles.isProfileUser(intExtra4)) {
                            com.android.server.notification.NotificationManagerService.this.allowDefaultApprovedServices(intExtra4);
                        }
                        com.android.server.notification.NotificationManagerService.this.mHistoryManager.onUserAdded(intExtra4);
                        com.android.server.notification.NotificationManagerService.this.mSettingsObserver.update(null, intExtra4);
                        return;
                    }
                    return;
                }
                if (action.equals("android.intent.action.USER_REMOVED")) {
                    int intExtra5 = intent.getIntExtra("android.intent.extra.user_handle", com.android.server.am.ProcessList.INVALID_ADJ);
                    com.android.server.notification.NotificationManagerService.this.mUserProfiles.updateCache(context2);
                    com.android.server.notification.NotificationManagerService.this.mZenModeHelper.onUserRemoved(intExtra5);
                    com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.onUserRemoved(intExtra5);
                    com.android.server.notification.NotificationManagerService.this.mListeners.onUserRemoved(intExtra5);
                    com.android.server.notification.NotificationManagerService.this.mConditionProviders.onUserRemoved(intExtra5);
                    com.android.server.notification.NotificationManagerService.this.mAssistants.onUserRemoved(intExtra5);
                    com.android.server.notification.NotificationManagerService.this.mHistoryManager.onUserRemoved(intExtra5);
                    com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.syncChannelsBypassingDnd();
                    com.android.server.notification.NotificationManagerService.this.handleSavePolicyFile();
                    return;
                }
                if (action.equals("android.intent.action.USER_UNLOCKED")) {
                    int intExtra6 = intent.getIntExtra("android.intent.extra.user_handle", com.android.server.am.ProcessList.INVALID_ADJ);
                    com.android.server.notification.NotificationManagerService.this.mUserProfiles.updateCache(context2);
                    com.android.server.notification.NotificationManagerService.this.mAssistants.onUserUnlocked(intExtra6);
                    if (!com.android.server.notification.NotificationManagerService.this.mUserProfiles.isProfileUser(intExtra6)) {
                        com.android.server.notification.NotificationManagerService.this.mConditionProviders.onUserUnlocked(intExtra6);
                        com.android.server.notification.NotificationManagerService.this.mListeners.onUserUnlocked(intExtra6);
                        if (!android.app.Flags.modesApi()) {
                            com.android.server.notification.NotificationManagerService.this.mZenModeHelper.onUserUnlocked(intExtra6);
                        }
                    }
                }
            }

            private boolean isProfileUnavailable(java.lang.String str) {
                if (android.os.Flags.allowPrivateProfile()) {
                    return str.equals("android.intent.action.PROFILE_UNAVAILABLE");
                }
                return str.equals("android.intent.action.MANAGED_PROFILE_UNAVAILABLE");
            }
        };
        this.mService = new com.android.server.notification.NotificationManagerService.AnonymousClass13();
        this.mInternalService = new com.android.server.notification.NotificationManagerService.AnonymousClass14();
        this.mShortcutListener = new com.android.server.notification.ShortcutHelper.ShortcutListener() { // from class: com.android.server.notification.NotificationManagerService.15
            @Override // com.android.server.notification.ShortcutHelper.ShortcutListener
            public void onShortcutRemoved(java.lang.String str) {
                java.lang.String packageName;
                synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                    try {
                        com.android.server.notification.NotificationRecord notificationRecord = com.android.server.notification.NotificationManagerService.this.mNotificationsByKey.get(str);
                        packageName = notificationRecord != null ? notificationRecord.getSbn().getPackageName() : null;
                    } finally {
                    }
                }
                boolean z = packageName != null && com.android.server.notification.NotificationManagerService.this.getPackageImportanceWithIdentity(packageName) == 100;
                synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                    try {
                        com.android.server.notification.NotificationRecord notificationRecord2 = com.android.server.notification.NotificationManagerService.this.mNotificationsByKey.get(str);
                        if (notificationRecord2 != null) {
                            notificationRecord2.setShortcutInfo(null);
                            notificationRecord2.getNotification().flags |= 8;
                            com.android.server.notification.NotificationManagerService.this.mHandler.post(com.android.server.notification.NotificationManagerService.this.new EnqueueNotificationRunnable(notificationRecord2.getUser().getIdentifier(), notificationRecord2, z, com.android.server.notification.NotificationManagerService.this.mPostNotificationTrackerFactory.newTracker(null)));
                        }
                    } finally {
                    }
                }
            }
        };
        this.mNotificationRecordLogger = notificationRecordLogger;
        this.mNotificationInstanceIdSequence = instanceIdSequence;
        android.app.Notification.processAllowlistToken = ALLOWLIST_TOKEN;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setAudioManager(android.media.AudioManager audioManager) {
        this.mAudioManager = audioManager;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setStrongAuthTracker(com.android.server.notification.NotificationManagerService.StrongAuthTracker strongAuthTracker) {
        this.mStrongAuthTracker = strongAuthTracker;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setKeyguardManager(android.app.KeyguardManager keyguardManager) {
        this.mKeyguardManager = keyguardManager;
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.notification.ShortcutHelper getShortcutHelper() {
        return this.mShortcutHelper;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setShortcutHelper(com.android.server.notification.ShortcutHelper shortcutHelper) {
        this.mShortcutHelper = shortcutHelper;
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.notification.VibratorHelper getVibratorHelper() {
        return this.mVibratorHelper;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setVibratorHelper(com.android.server.notification.VibratorHelper vibratorHelper) {
        this.mVibratorHelper = vibratorHelper;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setHints(int i) {
        this.mListenerHints = i;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setLights(com.android.server.lights.LogicalLight logicalLight) {
        this.mNotificationLight = logicalLight;
        this.mAttentionLight = logicalLight;
        this.mNotificationPulseEnabled = true;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setScreenOn(boolean z) {
        this.mScreenOn = z;
    }

    @com.android.internal.annotations.VisibleForTesting
    int getNotificationRecordCount() {
        int size;
        synchronized (this.mNotificationLock) {
            try {
                size = this.mNotificationList.size() + this.mNotificationsByKey.size() + this.mSummaryByGroupKey.size() + this.mEnqueuedNotifications.size();
                java.util.Iterator<com.android.server.notification.NotificationRecord> it = this.mNotificationList.iterator();
                while (it.hasNext()) {
                    com.android.server.notification.NotificationRecord next = it.next();
                    if (this.mNotificationsByKey.containsKey(next.getKey())) {
                        size--;
                    }
                    if (next.getSbn().isGroup() && next.getNotification().isGroupSummary()) {
                        size--;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return size;
    }

    @com.android.internal.annotations.VisibleForTesting
    void clearNotifications() {
        synchronized (this.mNotificationList) {
            this.mEnqueuedNotifications.clear();
            this.mNotificationList.clear();
            this.mNotificationsByKey.clear();
            this.mSummaryByGroupKey.clear();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void addNotification(com.android.server.notification.NotificationRecord notificationRecord) {
        this.mNotificationList.add(notificationRecord);
        this.mNotificationsByKey.put(notificationRecord.getSbn().getKey(), notificationRecord);
        if (notificationRecord.getSbn().isGroup()) {
            this.mSummaryByGroupKey.put(notificationRecord.getGroupKey(), notificationRecord);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void addEnqueuedNotification(com.android.server.notification.NotificationRecord notificationRecord) {
        this.mEnqueuedNotifications.add(notificationRecord);
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.notification.NotificationRecord getNotificationRecord(java.lang.String str) {
        return this.mNotificationsByKey.get(str);
    }

    @com.android.internal.annotations.VisibleForTesting
    void setSystemReady(boolean z) {
        this.mSystemReady = z;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setHandler(com.android.server.notification.NotificationManagerService.WorkerHandler workerHandler) {
        this.mHandler = workerHandler;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setRankingHelper(com.android.server.notification.RankingHelper rankingHelper) {
        this.mRankingHelper = rankingHelper;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setPreferencesHelper(com.android.server.notification.PreferencesHelper preferencesHelper) {
        this.mPreferencesHelper = preferencesHelper;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setZenHelper(com.android.server.notification.ZenModeHelper zenModeHelper) {
        this.mZenModeHelper = zenModeHelper;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setIsAutomotive(boolean z) {
        this.mIsAutomotive = z;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setNotificationEffectsEnabledForAutomotive(boolean z) {
        this.mNotificationEffectsEnabledForAutomotive = z;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setIsTelevision(boolean z) {
        this.mIsTelevision = z;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setUsageStats(com.android.server.notification.NotificationUsageStats notificationUsageStats) {
        this.mUsageStats = notificationUsageStats;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setAccessibilityManager(android.view.accessibility.AccessibilityManager accessibilityManager) {
        this.mAccessibilityManager = accessibilityManager;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setTelecomManager(android.telecom.TelecomManager telecomManager) {
        this.mTelecomManager = telecomManager;
    }

    @com.android.internal.annotations.VisibleForTesting
    void init(com.android.server.notification.NotificationManagerService.WorkerHandler workerHandler, com.android.server.notification.RankingHandler rankingHandler, android.content.pm.IPackageManager iPackageManager, android.content.pm.PackageManager packageManager, com.android.server.lights.LightsManager lightsManager, com.android.server.notification.NotificationManagerService.NotificationListeners notificationListeners, com.android.server.notification.NotificationManagerService.NotificationAssistants notificationAssistants, com.android.server.notification.ConditionProviders conditionProviders, android.companion.ICompanionDeviceManager iCompanionDeviceManager, com.android.server.notification.SnoozeHelper snoozeHelper, com.android.server.notification.NotificationUsageStats notificationUsageStats, android.util.AtomicFile atomicFile, android.app.ActivityManager activityManager, com.android.server.notification.GroupHelper groupHelper, android.app.IActivityManager iActivityManager, com.android.server.wm.ActivityTaskManagerInternal activityTaskManagerInternal, android.app.usage.UsageStatsManagerInternal usageStatsManagerInternal, android.app.admin.DevicePolicyManagerInternal devicePolicyManagerInternal, android.app.IUriGrantsManager iUriGrantsManager, com.android.server.uri.UriGrantsManagerInternal uriGrantsManagerInternal, android.app.AppOpsManager appOpsManager, android.os.UserManager userManager, com.android.server.notification.NotificationHistoryManager notificationHistoryManager, android.app.StatsManager statsManager, android.telephony.TelephonyManager telephonyManager, android.app.ActivityManagerInternal activityManagerInternal, com.android.server.utils.quota.MultiRateLimiter multiRateLimiter, com.android.server.notification.PermissionHelper permissionHelper, android.app.usage.UsageStatsManagerInternal usageStatsManagerInternal2, android.telecom.TelecomManager telecomManager, com.android.server.notification.NotificationChannelLogger notificationChannelLogger, com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.FlagResolver flagResolver, android.permission.PermissionManager permissionManager, android.os.PowerManager powerManager, com.android.server.notification.NotificationManagerService.PostNotificationTrackerFactory postNotificationTrackerFactory) {
        java.lang.String[] strArr;
        this.mHandler = workerHandler;
        android.content.res.Resources resources = getContext().getResources();
        this.mMaxPackageEnqueueRate = android.provider.Settings.Global.getFloat(getContext().getContentResolver(), "max_notification_enqueue_rate", DEFAULT_MAX_NOTIFICATION_ENQUEUE_RATE);
        this.mAccessibilityManager = (android.view.accessibility.AccessibilityManager) getContext().getSystemService("accessibility");
        this.mAm = iActivityManager;
        this.mAtm = activityTaskManagerInternal;
        this.mAtm.setBackgroundActivityStartCallback(new com.android.server.notification.NotificationManagerService.NotificationTrampolineCallback());
        this.mUgm = iUriGrantsManager;
        this.mUgmInternal = uriGrantsManagerInternal;
        this.mPackageManager = iPackageManager;
        this.mPackageManagerClient = packageManager;
        this.mPackageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        this.mPermissionManager = permissionManager;
        this.mPermissionPolicyInternal = (com.android.server.policy.PermissionPolicyInternal) com.android.server.LocalServices.getService(com.android.server.policy.PermissionPolicyInternal.class);
        this.mUmInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
        this.mUsageStatsManagerInternal = usageStatsManagerInternal2;
        this.mAppOps = appOpsManager;
        this.mAppUsageStats = usageStatsManagerInternal;
        this.mAlarmManager = (android.app.AlarmManager) getContext().getSystemService(com.android.server.am.HostingRecord.TRIGGER_TYPE_ALARM);
        this.mCompanionManager = iCompanionDeviceManager;
        this.mActivityManager = activityManager;
        this.mAmi = activityManagerInternal;
        this.mDeviceIdleManager = (android.os.DeviceIdleManager) getContext().getSystemService(android.os.DeviceIdleManager.class);
        this.mDpm = devicePolicyManagerInternal;
        this.mUm = userManager;
        this.mTelecomManager = telecomManager;
        this.mPowerManager = powerManager;
        this.mPostNotificationTrackerFactory = postNotificationTrackerFactory;
        this.mPlatformCompat = com.android.internal.compat.IPlatformCompat.Stub.asInterface(android.os.ServiceManager.getService("platform_compat"));
        this.mStrongAuthTracker = new com.android.server.notification.NotificationManagerService.StrongAuthTracker(getContext());
        try {
            strArr = resources.getStringArray(android.R.array.config_notificationFallbackVibePattern);
        } catch (android.content.res.Resources.NotFoundException e) {
            strArr = new java.lang.String[0];
        }
        this.mUsageStats = notificationUsageStats;
        this.mMetricsLogger = new com.android.internal.logging.MetricsLogger();
        this.mRankingHandler = rankingHandler;
        this.mConditionProviders = conditionProviders;
        this.mZenModeHelper = new com.android.server.notification.ZenModeHelper(getContext(), this.mHandler.getLooper(), java.time.Clock.systemUTC(), this.mConditionProviders, flagResolver, new com.android.server.notification.ZenModeEventLogger(this.mPackageManagerClient));
        this.mZenModeHelper.addCallback(new com.android.server.notification.NotificationManagerService.AnonymousClass8());
        this.mPermissionHelper = permissionHelper;
        this.mNotificationChannelLogger = notificationChannelLogger;
        this.mUserProfiles.updateCache(getContext());
        this.mPreferencesHelper = new com.android.server.notification.PreferencesHelper(getContext(), this.mPackageManagerClient, this.mRankingHandler, this.mZenModeHelper, this.mPermissionHelper, this.mPermissionManager, this.mNotificationChannelLogger, this.mAppOps, this.mUserProfiles, this.mShowReviewPermissionsNotification);
        this.mRankingHelper = new com.android.server.notification.RankingHelper(getContext(), this.mRankingHandler, this.mPreferencesHelper, this.mZenModeHelper, this.mUsageStats, strArr);
        this.mSnoozeHelper = snoozeHelper;
        this.mGroupHelper = groupHelper;
        this.mVibratorHelper = new com.android.server.notification.VibratorHelper(getContext());
        this.mHistoryManager = notificationHistoryManager;
        this.mListeners = notificationListeners;
        this.mAssistants = notificationAssistants;
        this.mAllowedManagedServicePackages = new com.android.internal.util.function.TriPredicate() { // from class: com.android.server.notification.NotificationManagerService$$ExternalSyntheticLambda1
            public final boolean test(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                return com.android.server.notification.NotificationManagerService.this.canUseManagedServices((java.lang.String) obj, (java.lang.Integer) obj2, (java.lang.String) obj3);
            }
        };
        this.mPolicyFile = atomicFile;
        loadPolicyFile();
        this.mStatusBar = (com.android.server.statusbar.StatusBarManagerInternal) getLocalService(com.android.server.statusbar.StatusBarManagerInternal.class);
        if (this.mStatusBar != null) {
            this.mStatusBar.setNotificationDelegate(this.mNotificationDelegate);
        }
        this.mNotificationLight = lightsManager.getLight(4);
        this.mAttentionLight = lightsManager.getLight(5);
        this.mInCallNotificationUri = android.net.Uri.parse("file://" + resources.getString(android.R.string.config_globalAppSearchDataQuerierPackage));
        this.mInCallNotificationAudioAttributes = new android.media.AudioAttributes.Builder().setContentType(4).setUsage(2).build();
        this.mInCallNotificationVolume = resources.getFloat(android.R.dimen.config_displayWhiteBalanceColorTemperatureFilterIntercept);
        this.mUseAttentionLight = resources.getBoolean(android.R.bool.config_usbChargingMessage);
        this.mHasLight = resources.getBoolean(android.R.bool.config_hotswapCapable);
        boolean z = true;
        if (android.provider.Settings.Global.getInt(getContext().getContentResolver(), "device_provisioned", 0) == 0) {
            this.mDisableNotificationEffects = true;
        }
        this.mZenModeHelper.initZenMode();
        this.mInterruptionFilter = this.mZenModeHelper.getZenModeListenerInterruptionFilter();
        if (this.mPackageManagerClient.hasSystemFeature("android.hardware.telephony")) {
            telephonyManager.listen(new android.telephony.PhoneStateListener() { // from class: com.android.server.notification.NotificationManagerService.9
                @Override // android.telephony.PhoneStateListener
                public void onCallStateChanged(int i, java.lang.String str) {
                    if (com.android.server.notification.NotificationManagerService.this.mCallState == i) {
                        return;
                    }
                    if (com.android.server.notification.NotificationManagerService.DBG) {
                        android.util.Slog.d(com.android.server.notification.NotificationManagerService.TAG, "Call state changed: " + com.android.server.notification.NotificationManagerService.callStateToString(i));
                    }
                    com.android.server.notification.NotificationManagerService.this.mCallState = i;
                }
            }, 32);
        }
        this.mSettingsObserver = new com.android.server.notification.NotificationManagerService.SettingsObserver(this.mHandler);
        this.mArchive = new com.android.server.notification.NotificationManagerService.Archive(resources.getInteger(android.R.integer.config_nightDisplayColorTemperatureDefault));
        if (!this.mPackageManagerClient.hasSystemFeature("android.software.leanback") && !this.mPackageManagerClient.hasSystemFeature("android.hardware.type.television")) {
            z = false;
        }
        this.mIsTelevision = z;
        this.mIsAutomotive = this.mPackageManagerClient.hasSystemFeature("android.hardware.type.automotive", 0);
        this.mNotificationEffectsEnabledForAutomotive = resources.getBoolean(android.R.bool.config_enableScreenshotChord);
        this.mZenModeHelper.setPriorityOnlyDndExemptPackages(getContext().getResources().getStringArray(android.R.array.config_packagesExemptFromSuspension));
        this.mWarnRemoteViewsSizeBytes = getContext().getResources().getInteger(android.R.integer.config_nightDisplayColorTemperatureMin);
        this.mStripRemoteViewsSizeBytes = getContext().getResources().getInteger(android.R.integer.config_nightDisplayColorTemperatureMax);
        this.mMsgPkgsAllowedAsConvos = java.util.Set.of((java.lang.Object[]) getStringArrayResource(android.R.array.config_nonPreemptibleInputMethods));
        this.mDefaultSearchSelectorPkg = getContext().getString(getContext().getResources().getIdentifier("config_defaultSearchSelectorPackageName", "string", com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME));
        this.mFlagResolver = flagResolver;
        this.mStatsManager = statsManager;
        this.mToastRateLimiter = multiRateLimiter;
        com.android.server.notification.Flags.refactorAttentionHelper();
        this.mAttentionHelper = new com.android.server.notification.NotificationAttentionHelper(getContext(), lightsManager, this.mAccessibilityManager, this.mPackageManagerClient, userManager, notificationUsageStats, this.mNotificationManagerPrivate, this.mZenModeHelper, flagResolver);
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        com.android.server.notification.Flags.refactorAttentionHelper();
        intentFilter.addAction("android.intent.action.USER_STOPPED");
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        intentFilter.addAction("android.intent.action.USER_ADDED");
        intentFilter.addAction("android.intent.action.USER_REMOVED");
        intentFilter.addAction("android.intent.action.USER_UNLOCKED");
        intentFilter.addAction("android.intent.action.MANAGED_PROFILE_UNAVAILABLE");
        if (android.os.Flags.allowPrivateProfile()) {
            intentFilter.addAction("android.intent.action.PROFILE_UNAVAILABLE");
        }
        getContext().registerReceiverAsUser(this.mIntentReceiver, android.os.UserHandle.ALL, intentFilter, null, null);
        android.content.IntentFilter intentFilter2 = new android.content.IntentFilter();
        intentFilter2.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter2.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter2.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter2.addAction("android.intent.action.PACKAGE_RESTARTED");
        intentFilter2.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
        getContext().registerReceiverAsUser(this.mPackageIntentReceiver, android.os.UserHandle.ALL, intentFilter2, null, null);
        android.content.IntentFilter intentFilter3 = new android.content.IntentFilter();
        intentFilter3.addAction("android.intent.action.PACKAGES_SUSPENDED");
        intentFilter3.addAction("android.intent.action.PACKAGES_UNSUSPENDED");
        intentFilter3.addAction("android.intent.action.DISTRACTING_PACKAGES_CHANGED");
        getContext().registerReceiverAsUser(this.mPackageIntentReceiver, android.os.UserHandle.ALL, intentFilter3, null, null);
        getContext().registerReceiverAsUser(this.mPackageIntentReceiver, android.os.UserHandle.ALL, new android.content.IntentFilter("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE"), null, null);
        android.content.IntentFilter intentFilter4 = new android.content.IntentFilter(ACTION_NOTIFICATION_TIMEOUT);
        intentFilter4.addDataScheme(SCHEME_TIMEOUT);
        getContext().registerReceiver(this.mNotificationTimeoutReceiver, intentFilter4, 2);
        getContext().registerReceiver(this.mRestoreReceiver, new android.content.IntentFilter("android.os.action.SETTING_RESTORED"));
        getContext().registerReceiver(this.mLocaleChangeReceiver, new android.content.IntentFilter("android.intent.action.LOCALE_CHANGED"));
        this.mReviewNotificationPermissionsReceiver = new com.android.server.notification.ReviewNotificationPermissionsReceiver();
        getContext().registerReceiver(this.mReviewNotificationPermissionsReceiver, com.android.server.notification.ReviewNotificationPermissionsReceiver.getFilter(), 4);
        this.mAppOps.startWatchingMode(11, (java.lang.String) null, (android.app.AppOpsManager.OnOpChangedListener) new com.android.server.notification.NotificationManagerService.AnonymousClass10());
    }

    /* renamed from: com.android.server.notification.NotificationManagerService$8, reason: invalid class name */
    class AnonymousClass8 extends com.android.server.notification.ZenModeHelper.Callback {
        AnonymousClass8() {
        }

        @Override // com.android.server.notification.ZenModeHelper.Callback
        public void onConfigChanged() {
            com.android.server.notification.NotificationManagerService.this.handleSavePolicyFile();
        }

        @Override // com.android.server.notification.ZenModeHelper.Callback
        void onZenModeChanged() {
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.notification.NotificationManagerService$8$$ExternalSyntheticLambda2
                public final void runOrThrow() {
                    com.android.server.notification.NotificationManagerService.AnonymousClass8.this.lambda$onZenModeChanged$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onZenModeChanged$0() throws java.lang.Exception {
            com.android.server.notification.NotificationManagerService.this.sendRegisteredOnlyBroadcast("android.app.action.INTERRUPTION_FILTER_CHANGED");
            com.android.server.notification.NotificationManagerService.this.getContext().sendBroadcastAsUser(new android.content.Intent("android.app.action.INTERRUPTION_FILTER_CHANGED_INTERNAL").addFlags(67108864), android.os.UserHandle.ALL, "android.permission.MANAGE_NOTIFICATIONS");
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                com.android.server.notification.NotificationManagerService.this.updateInterruptionFilterLocked();
            }
            com.android.server.notification.NotificationManagerService.this.mRankingHandler.requestSort();
        }

        @Override // com.android.server.notification.ZenModeHelper.Callback
        void onPolicyChanged(final android.app.NotificationManager.Policy policy) {
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.notification.NotificationManagerService$8$$ExternalSyntheticLambda3
                public final void runOrThrow() {
                    com.android.server.notification.NotificationManagerService.AnonymousClass8.this.lambda$onPolicyChanged$1(policy);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPolicyChanged$1(android.app.NotificationManager.Policy policy) throws java.lang.Exception {
            android.content.Intent intent = new android.content.Intent("android.app.action.NOTIFICATION_POLICY_CHANGED");
            if (android.app.Flags.modesApi()) {
                intent.putExtra("android.app.extra.NOTIFICATION_POLICY", policy);
            }
            com.android.server.notification.NotificationManagerService.this.sendRegisteredOnlyBroadcast(intent);
            com.android.server.notification.NotificationManagerService.this.mRankingHandler.requestSort();
        }

        @Override // com.android.server.notification.ZenModeHelper.Callback
        void onConsolidatedPolicyChanged(final android.app.NotificationManager.Policy policy) {
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.notification.NotificationManagerService$8$$ExternalSyntheticLambda0
                public final void runOrThrow() {
                    com.android.server.notification.NotificationManagerService.AnonymousClass8.this.lambda$onConsolidatedPolicyChanged$2(policy);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onConsolidatedPolicyChanged$2(android.app.NotificationManager.Policy policy) throws java.lang.Exception {
            if (android.app.Flags.modesApi()) {
                android.content.Intent intent = new android.content.Intent("android.app.action.CONSOLIDATED_NOTIFICATION_POLICY_CHANGED");
                intent.putExtra("android.app.extra.NOTIFICATION_POLICY", policy);
                com.android.server.notification.NotificationManagerService.this.sendRegisteredOnlyBroadcast(intent);
            }
            com.android.server.notification.NotificationManagerService.this.mRankingHandler.requestSort();
        }

        @Override // com.android.server.notification.ZenModeHelper.Callback
        void onAutomaticRuleStatusChanged(final int i, final java.lang.String str, final java.lang.String str2, final int i2) {
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.notification.NotificationManagerService$8$$ExternalSyntheticLambda1
                public final void runOrThrow() {
                    com.android.server.notification.NotificationManagerService.AnonymousClass8.this.lambda$onAutomaticRuleStatusChanged$3(str, str2, i2, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAutomaticRuleStatusChanged$3(java.lang.String str, java.lang.String str2, int i, int i2) throws java.lang.Exception {
            android.content.Intent intent = new android.content.Intent("android.app.action.AUTOMATIC_ZEN_RULE_STATUS_CHANGED");
            intent.setPackage(str);
            intent.putExtra("android.app.extra.AUTOMATIC_ZEN_RULE_ID", str2);
            intent.putExtra("android.app.extra.AUTOMATIC_ZEN_RULE_STATUS", i);
            com.android.server.notification.NotificationManagerService.this.getContext().sendBroadcastAsUser(intent, android.os.UserHandle.of(i2));
        }
    }

    /* renamed from: com.android.server.notification.NotificationManagerService$10, reason: invalid class name */
    class AnonymousClass10 extends android.app.AppOpsManager.OnOpChangedInternalListener {
        AnonymousClass10() {
        }

        public void onOpChanged(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull final java.lang.String str2, final int i) {
            com.android.server.notification.NotificationManagerService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.notification.NotificationManagerService$10$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.notification.NotificationManagerService.AnonymousClass10.this.lambda$onOpChanged$0(str2, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onOpChanged$0(java.lang.String str, int i) {
            com.android.server.notification.NotificationManagerService.this.handleNotificationPermissionChange(str, i);
        }
    }

    public void onDestroy() {
        getContext().unregisterReceiver(this.mIntentReceiver);
        getContext().unregisterReceiver(this.mPackageIntentReceiver);
        getContext().unregisterReceiver(this.mNotificationTimeoutReceiver);
        getContext().unregisterReceiver(this.mRestoreReceiver);
        getContext().unregisterReceiver(this.mLocaleChangeReceiver);
        if (this.mDeviceConfigChangedListener != null) {
            android.provider.DeviceConfig.removeOnPropertiesChangedListener(this.mDeviceConfigChangedListener);
        }
    }

    protected java.lang.String[] getStringArrayResource(int i) {
        return getContext().getResources().getStringArray(i);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        com.android.server.notification.SnoozeHelper snoozeHelper = new com.android.server.notification.SnoozeHelper(getContext(), new com.android.server.notification.SnoozeHelper.Callback() { // from class: com.android.server.notification.NotificationManagerService$$ExternalSyntheticLambda12
            @Override // com.android.server.notification.SnoozeHelper.Callback
            public final void repost(int i, com.android.server.notification.NotificationRecord notificationRecord, boolean z) {
                com.android.server.notification.NotificationManagerService.this.lambda$onStart$0(i, notificationRecord, z);
            }
        }, this.mUserProfiles);
        java.io.File file = new java.io.File(android.os.Environment.getDataDirectory(), "system");
        this.mRankingThread.start();
        com.android.server.notification.NotificationManagerService.WorkerHandler workerHandler = new com.android.server.notification.NotificationManagerService.WorkerHandler(android.os.Looper.myLooper());
        this.mShowReviewPermissionsNotification = getContext().getResources().getBoolean(android.R.bool.config_networkSamplingWakesDevice);
        com.android.server.notification.NotificationManagerService.RankingHandlerWorker rankingHandlerWorker = new com.android.server.notification.NotificationManagerService.RankingHandlerWorker(this.mRankingThread.getLooper());
        android.content.pm.IPackageManager packageManager = android.app.AppGlobals.getPackageManager();
        android.content.pm.PackageManager packageManager2 = getContext().getPackageManager();
        com.android.server.lights.LightsManager lightsManager = (com.android.server.lights.LightsManager) getLocalService(com.android.server.lights.LightsManager.class);
        com.android.server.notification.NotificationManagerService.NotificationListeners notificationListeners = new com.android.server.notification.NotificationManagerService.NotificationListeners(this, getContext(), this.mNotificationLock, this.mUserProfiles, android.app.AppGlobals.getPackageManager());
        com.android.server.notification.NotificationManagerService.NotificationAssistants notificationAssistants = new com.android.server.notification.NotificationManagerService.NotificationAssistants(getContext(), this.mNotificationLock, this.mUserProfiles, android.app.AppGlobals.getPackageManager());
        com.android.server.notification.ConditionProviders conditionProviders = new com.android.server.notification.ConditionProviders(getContext(), this.mUserProfiles, android.app.AppGlobals.getPackageManager());
        com.android.server.notification.NotificationUsageStats notificationUsageStats = new com.android.server.notification.NotificationUsageStats(getContext());
        android.util.AtomicFile atomicFile = new android.util.AtomicFile(new java.io.File(file, "notification_policy.xml"), TAG_NOTIFICATION_POLICY);
        android.app.ActivityManager activityManager = (android.app.ActivityManager) getContext().getSystemService(com.android.server.am.HostingRecord.HOSTING_TYPE_ACTIVITY);
        com.android.server.notification.GroupHelper groupHelper = getGroupHelper();
        android.app.IActivityManager service = android.app.ActivityManager.getService();
        com.android.server.wm.ActivityTaskManagerInternal activityTaskManagerInternal = (com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class);
        android.app.usage.UsageStatsManagerInternal usageStatsManagerInternal = (android.app.usage.UsageStatsManagerInternal) com.android.server.LocalServices.getService(android.app.usage.UsageStatsManagerInternal.class);
        android.app.admin.DevicePolicyManagerInternal devicePolicyManagerInternal = (android.app.admin.DevicePolicyManagerInternal) com.android.server.LocalServices.getService(android.app.admin.DevicePolicyManagerInternal.class);
        android.app.IUriGrantsManager service2 = android.app.UriGrantsManager.getService();
        com.android.server.uri.UriGrantsManagerInternal uriGrantsManagerInternal = (com.android.server.uri.UriGrantsManagerInternal) com.android.server.LocalServices.getService(com.android.server.uri.UriGrantsManagerInternal.class);
        android.app.AppOpsManager appOpsManager = (android.app.AppOpsManager) getContext().getSystemService(android.app.AppOpsManager.class);
        android.os.UserManager userManager = (android.os.UserManager) getContext().getSystemService(android.os.UserManager.class);
        com.android.server.notification.NotificationHistoryManager notificationHistoryManager = new com.android.server.notification.NotificationHistoryManager(getContext(), workerHandler);
        android.app.StatsManager statsManager = (android.app.StatsManager) getContext().getSystemService("stats");
        this.mStatsManager = statsManager;
        init(workerHandler, rankingHandlerWorker, packageManager, packageManager2, lightsManager, notificationListeners, notificationAssistants, conditionProviders, null, snoozeHelper, notificationUsageStats, atomicFile, activityManager, groupHelper, service, activityTaskManagerInternal, usageStatsManagerInternal, devicePolicyManagerInternal, service2, uriGrantsManagerInternal, appOpsManager, userManager, notificationHistoryManager, statsManager, (android.telephony.TelephonyManager) getContext().getSystemService(android.telephony.TelephonyManager.class), (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class), createToastRateLimiter(), new com.android.server.notification.PermissionHelper(getContext(), android.app.AppGlobals.getPackageManager(), android.app.AppGlobals.getPermissionManager()), (android.app.usage.UsageStatsManagerInternal) com.android.server.LocalServices.getService(android.app.usage.UsageStatsManagerInternal.class), (android.telecom.TelecomManager) getContext().getSystemService(android.telecom.TelecomManager.class), new com.android.server.notification.NotificationChannelLoggerImpl(), com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.getResolver(), (android.permission.PermissionManager) getContext().getSystemService(android.permission.PermissionManager.class), (android.os.PowerManager) getContext().getSystemService(android.os.PowerManager.class), new com.android.server.notification.NotificationManagerService.PostNotificationTrackerFactory() { // from class: com.android.server.notification.NotificationManagerService.11
        });
        publishBinderService("notification", this.mService, false, 5);
        publishBinderService("notification", this.mService);
        publishLocalService(com.android.server.notification.NotificationManagerInternal.class, this.mInternalService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStart$0(int i, com.android.server.notification.NotificationRecord notificationRecord, boolean z) {
        try {
            if (DBG) {
                android.util.Slog.d(TAG, "Reposting " + notificationRecord.getKey() + " " + z);
            }
            enqueueNotificationInternal(notificationRecord.getSbn().getPackageName(), notificationRecord.getSbn().getOpPkg(), notificationRecord.getSbn().getUid(), notificationRecord.getSbn().getInitialPid(), notificationRecord.getSbn().getTag(), notificationRecord.getSbn().getId(), notificationRecord.getSbn().getNotification(), i, z, false);
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Cannot un-snooze notification", e);
        }
    }

    void registerDeviceConfigChange() {
        this.mDeviceConfigChangedListener = new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.notification.NotificationManagerService$$ExternalSyntheticLambda6
            public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                com.android.server.notification.NotificationManagerService.this.lambda$registerDeviceConfigChange$1(properties);
            }
        };
        this.mSystemExemptFromDismissal = android.provider.DeviceConfig.getBoolean("device_policy_manager", "application_exemptions", true);
        android.provider.DeviceConfig.addOnPropertiesChangedListener("systemui", new android.os.HandlerExecutor(this.mHandler), this.mDeviceConfigChangedListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerDeviceConfigChange$1(android.provider.DeviceConfig.Properties properties) {
        if (!"systemui".equals(properties.getNamespace())) {
            return;
        }
        java.util.Iterator it = properties.getKeyset().iterator();
        while (it.hasNext()) {
            if ("nas_default_service".equals((java.lang.String) it.next())) {
                this.mAssistants.resetDefaultAssistantsIfNecessary();
            }
        }
    }

    private void registerNotificationPreferencesPullers() {
        this.mPullAtomCallback = new com.android.server.notification.NotificationManagerService.StatsPullAtomCallbackImpl();
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.PACKAGE_NOTIFICATION_PREFERENCES, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mPullAtomCallback);
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.PACKAGE_NOTIFICATION_CHANNEL_PREFERENCES, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mPullAtomCallback);
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.PACKAGE_NOTIFICATION_CHANNEL_GROUP_PREFERENCES, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mPullAtomCallback);
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.DND_MODE_RULE, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mPullAtomCallback);
    }

    private class StatsPullAtomCallbackImpl implements android.app.StatsManager.StatsPullAtomCallback {
        private StatsPullAtomCallbackImpl() {
        }

        public int onPullAtom(int i, java.util.List<android.util.StatsEvent> list) {
            switch (i) {
                case com.android.internal.util.FrameworkStatsLog.PACKAGE_NOTIFICATION_PREFERENCES /* 10071 */:
                case com.android.internal.util.FrameworkStatsLog.PACKAGE_NOTIFICATION_CHANNEL_PREFERENCES /* 10072 */:
                case com.android.internal.util.FrameworkStatsLog.PACKAGE_NOTIFICATION_CHANNEL_GROUP_PREFERENCES /* 10073 */:
                case com.android.internal.util.FrameworkStatsLog.DND_MODE_RULE /* 10084 */:
                    return com.android.server.notification.NotificationManagerService.this.pullNotificationStates(i, list);
                default:
                    throw new java.lang.UnsupportedOperationException("Unknown tagId=" + i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int pullNotificationStates(int i, java.util.List<android.util.StatsEvent> list) {
        switch (i) {
            case com.android.internal.util.FrameworkStatsLog.PACKAGE_NOTIFICATION_PREFERENCES /* 10071 */:
                this.mPreferencesHelper.pullPackagePreferencesStats(list, getAllUsersNotificationPermissions());
                break;
            case com.android.internal.util.FrameworkStatsLog.PACKAGE_NOTIFICATION_CHANNEL_PREFERENCES /* 10072 */:
                this.mPreferencesHelper.pullPackageChannelPreferencesStats(list);
                break;
            case com.android.internal.util.FrameworkStatsLog.PACKAGE_NOTIFICATION_CHANNEL_GROUP_PREFERENCES /* 10073 */:
                this.mPreferencesHelper.pullPackageChannelGroupPreferencesStats(list);
                break;
            case com.android.internal.util.FrameworkStatsLog.DND_MODE_RULE /* 10084 */:
                this.mZenModeHelper.pullRules(list);
                break;
        }
        return 0;
    }

    private com.android.server.notification.GroupHelper getGroupHelper() {
        this.mAutoGroupAtCount = getContext().getResources().getInteger(android.R.integer.config_autoGroupAtCount);
        return new com.android.server.notification.GroupHelper(getContext(), getContext().getPackageManager(), this.mAutoGroupAtCount, new com.android.server.notification.GroupHelper.Callback() { // from class: com.android.server.notification.NotificationManagerService.12
            @Override // com.android.server.notification.GroupHelper.Callback
            public void addAutoGroup(java.lang.String str) {
                synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                    com.android.server.notification.NotificationManagerService.this.addAutogroupKeyLocked(str);
                }
            }

            @Override // com.android.server.notification.GroupHelper.Callback
            public void removeAutoGroup(java.lang.String str) {
                synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                    com.android.server.notification.NotificationManagerService.this.removeAutogroupKeyLocked(str);
                }
            }

            @Override // com.android.server.notification.GroupHelper.Callback
            public void addAutoGroupSummary(int i, java.lang.String str, java.lang.String str2, com.android.server.notification.GroupHelper.NotificationAttributes notificationAttributes) {
                com.android.server.notification.NotificationRecord createAutoGroupSummary = com.android.server.notification.NotificationManagerService.this.createAutoGroupSummary(i, str, str2, notificationAttributes.flags, notificationAttributes.icon, notificationAttributes.iconColor, notificationAttributes.visibility);
                if (createAutoGroupSummary != null) {
                    com.android.server.notification.NotificationManagerService.this.mHandler.post(com.android.server.notification.NotificationManagerService.this.new EnqueueNotificationRunnable(i, createAutoGroupSummary, com.android.server.notification.NotificationManagerService.this.mActivityManager.getPackageImportance(str) == 100, com.android.server.notification.NotificationManagerService.this.mPostNotificationTrackerFactory.newTracker(null)));
                }
            }

            @Override // com.android.server.notification.GroupHelper.Callback
            public void removeAutoGroupSummary(int i, java.lang.String str) {
                synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                    com.android.server.notification.NotificationManagerService.this.clearAutogroupSummaryLocked(i, str);
                }
            }

            @Override // com.android.server.notification.GroupHelper.Callback
            public void updateAutogroupSummary(int i, java.lang.String str, com.android.server.notification.GroupHelper.NotificationAttributes notificationAttributes) {
                boolean z = str != null && com.android.server.notification.NotificationManagerService.this.mActivityManager.getPackageImportance(str) == 100;
                synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                    com.android.server.notification.NotificationManagerService.this.updateAutobundledSummaryLocked(i, str, notificationAttributes, z);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendRegisteredOnlyBroadcast(java.lang.String str) {
        sendRegisteredOnlyBroadcast(new android.content.Intent(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendRegisteredOnlyBroadcast(android.content.Intent intent) {
        int[] profileIds = this.mUmInternal.getProfileIds(this.mAmi.getCurrentUserId(), true);
        android.content.Intent addFlags = new android.content.Intent(intent).addFlags(1073741824);
        for (int i : profileIds) {
            getContext().sendBroadcastAsUser(addFlags, android.os.UserHandle.of(i), null);
        }
        for (int i2 : profileIds) {
            java.util.Iterator<java.lang.String> it = this.mConditionProviders.getAllowedPackages(i2).iterator();
            while (it.hasNext()) {
                getContext().sendBroadcastAsUser(new android.content.Intent(intent).setPackage(it.next()).setFlags(67108864), android.os.UserHandle.of(i2));
            }
        }
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        onBootPhase(i, android.os.Looper.getMainLooper());
    }

    @com.android.internal.annotations.VisibleForTesting
    void onBootPhase(int i, android.os.Looper looper) {
        if (i == 500) {
            this.mSystemReady = true;
            this.mAudioManager = (android.media.AudioManager) getContext().getSystemService("audio");
            this.mAudioManagerInternal = (android.media.AudioManagerInternal) getLocalService(android.media.AudioManagerInternal.class);
            this.mWindowManagerInternal = (com.android.server.wm.WindowManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.WindowManagerInternal.class);
            this.mKeyguardManager = (android.app.KeyguardManager) getContext().getSystemService(android.app.KeyguardManager.class);
            this.mZenModeHelper.onSystemReady();
            com.android.server.notification.NotificationManagerService.RoleObserver roleObserver = new com.android.server.notification.NotificationManagerService.RoleObserver(getContext(), (android.app.role.RoleManager) getContext().getSystemService(android.app.role.RoleManager.class), this.mPackageManager, looper);
            roleObserver.init();
            this.mRoleObserver = roleObserver;
            this.mShortcutHelper = new com.android.server.notification.ShortcutHelper((android.content.pm.LauncherApps) getContext().getSystemService("launcherapps"), this.mShortcutListener, (android.content.pm.ShortcutServiceInternal) getLocalService(android.content.pm.ShortcutServiceInternal.class), (android.os.UserManager) getContext().getSystemService("user"));
            com.android.server.notification.BubbleExtractor bubbleExtractor = (com.android.server.notification.BubbleExtractor) this.mRankingHelper.findExtractor(com.android.server.notification.BubbleExtractor.class);
            if (bubbleExtractor != null) {
                bubbleExtractor.setShortcutHelper(this.mShortcutHelper);
            }
            registerNotificationPreferencesPullers();
            new com.android.internal.widget.LockPatternUtils(getContext()).registerStrongAuthTracker(this.mStrongAuthTracker);
            com.android.server.notification.Flags.refactorAttentionHelper();
            this.mAttentionHelper.onSystemReady();
            return;
        }
        if (i == 600) {
            this.mSettingsObserver.observe();
            this.mListeners.onBootPhaseAppsCanStart();
            this.mAssistants.onBootPhaseAppsCanStart();
            this.mConditionProviders.onBootPhaseAppsCanStart();
            this.mHistoryManager.onBootPhaseAppsCanStart();
            registerDeviceConfigChange();
            migrateDefaultNAS();
            maybeShowInitialReviewPermissionsNotification();
            if (android.app.Flags.modesApi()) {
                this.mZenModeHelper.setDeviceEffectsApplier(new com.android.server.notification.DefaultDeviceEffectsApplier(getContext()));
                return;
            }
            return;
        }
        if (i == 550) {
            this.mSnoozeHelper.scheduleRepostsForPersistedNotifications(java.lang.System.currentTimeMillis());
            return;
        }
        if (i == 520) {
            this.mPreferencesHelper.updateFixedImportance(this.mUm.getUsers());
            this.mPreferencesHelper.migrateNotificationPermissions(this.mUm.getUsers());
        } else if (i == 1000) {
            if (this.mFlagResolver.isEnabled(com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.NotificationFlags.DEBUG_SHORT_BITMAP_DURATION)) {
                new java.lang.Thread(new java.lang.Runnable() { // from class: com.android.server.notification.NotificationManagerService$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.notification.NotificationManagerService.this.lambda$onBootPhase$2();
                    }
                }).start();
            } else {
                com.android.server.notification.Flags.expireBitmaps();
                com.android.server.notification.NotificationBitmapJobService.scheduleJob(getContext());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBootPhase$2() {
        while (true) {
            try {
                java.lang.Thread.sleep(MIN_PACKAGE_OVERRATE_LOG_INTERVAL);
            } catch (java.lang.InterruptedException e) {
            }
            this.mInternalService.removeBitmaps();
        }
    }

    @Override // com.android.server.SystemService
    public void onUserUnlocked(@android.annotation.NonNull final com.android.server.SystemService.TargetUser targetUser) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.notification.NotificationManagerService$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.notification.NotificationManagerService.this.lambda$onUserUnlocked$3(targetUser);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUserUnlocked$3(com.android.server.SystemService.TargetUser targetUser) {
        android.os.Trace.traceBegin(524288L, "notifHistoryUnlockUser");
        try {
            this.mHistoryManager.onUserUnlocked(targetUser.getUserIdentifier());
        } finally {
            android.os.Trace.traceEnd(524288L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAppBlockStateChangedBroadcast(final java.lang.String str, final int i, final boolean z) {
        this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.notification.NotificationManagerService$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.notification.NotificationManagerService.this.lambda$sendAppBlockStateChangedBroadcast$4(z, str, i);
            }
        }, 500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendAppBlockStateChangedBroadcast$4(boolean z, java.lang.String str, int i) {
        try {
            getContext().sendBroadcastAsUser(new android.content.Intent("android.app.action.APP_BLOCK_STATE_CHANGED").putExtra("android.app.extra.BLOCKED_STATE", z).addFlags(268435456).setPackage(str), android.os.UserHandle.of(android.os.UserHandle.getUserId(i)), null);
        } catch (java.lang.SecurityException e) {
            android.util.Slog.w(TAG, "Can't notify app about app block change", e);
        }
    }

    @Override // com.android.server.SystemService
    public void onUserStopping(@android.annotation.NonNull final com.android.server.SystemService.TargetUser targetUser) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.notification.NotificationManagerService$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.notification.NotificationManagerService.this.lambda$onUserStopping$5(targetUser);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUserStopping$5(com.android.server.SystemService.TargetUser targetUser) {
        android.os.Trace.traceBegin(524288L, "notifHistoryStopUser");
        try {
            this.mHistoryManager.onUserStopped(targetUser.getUserIdentifier());
        } finally {
            android.os.Trace.traceEnd(524288L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    public void updateListenerHintsLocked() {
        int calculateHints = calculateHints();
        if (calculateHints == this.mListenerHints) {
            return;
        }
        com.android.server.notification.ZenLog.traceListenerHintsChanged(this.mListenerHints, calculateHints, this.mEffectsSuppressors.size());
        this.mListenerHints = calculateHints;
        scheduleListenerHintsChanged(calculateHints);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    public void updateEffectsSuppressorLocked() {
        long calculateSuppressedEffects = calculateSuppressedEffects();
        if (calculateSuppressedEffects == this.mZenModeHelper.getSuppressedEffects()) {
            return;
        }
        java.util.ArrayList<android.content.ComponentName> suppressors = getSuppressors();
        com.android.server.notification.ZenLog.traceEffectsSuppressorChanged(this.mEffectsSuppressors, suppressors, calculateSuppressedEffects);
        this.mEffectsSuppressors = suppressors;
        this.mZenModeHelper.setSuppressedEffects(calculateSuppressedEffects);
        sendRegisteredOnlyBroadcast("android.os.action.ACTION_EFFECTS_SUPPRESSOR_CHANGED");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void exitIdle() {
        if (this.mDeviceIdleManager != null) {
            this.mDeviceIdleManager.endIdle("notification interaction");
        }
    }

    void updateNotificationChannelInt(java.lang.String str, int i, android.app.NotificationChannel notificationChannel, boolean z) {
        if (notificationChannel.getImportance() == 0) {
            cancelAllNotificationsInt(MY_UID, MY_PID, str, notificationChannel.getId(), 0, 0, android.os.UserHandle.getUserId(i), 17);
            if (isUidSystemOrPhone(i)) {
                android.util.IntArray currentProfileIds = this.mUserProfiles.getCurrentProfileIds();
                int size = currentProfileIds.size();
                for (int i2 = 0; i2 < size; i2++) {
                    cancelAllNotificationsInt(MY_UID, MY_PID, str, notificationChannel.getId(), 0, 0, currentProfileIds.get(i2), 17);
                }
            }
        }
        android.app.NotificationChannel notificationChannel2 = this.mPreferencesHelper.getNotificationChannel(str, i, notificationChannel.getId(), true);
        this.mPreferencesHelper.updateNotificationChannel(str, i, notificationChannel, true, android.os.Binder.getCallingUid(), isCallerSystemOrSystemUi());
        if (this.mPreferencesHelper.onlyHasDefaultChannel(str, i)) {
            this.mPermissionHelper.setNotificationPermission(str, android.os.UserHandle.getUserId(i), notificationChannel.getImportance() != 0, true);
        }
        maybeNotifyChannelOwner(str, i, notificationChannel2, notificationChannel);
        if (!z) {
            this.mListeners.notifyNotificationChannelChanged(str, android.os.UserHandle.getUserHandleForUid(i), this.mPreferencesHelper.getNotificationChannel(str, i, notificationChannel.getId(), false), 2);
        }
        handleSavePolicyFile();
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0039  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void maybeNotifyChannelOwner(java.lang.String str, int i, android.app.NotificationChannel notificationChannel, android.app.NotificationChannel notificationChannel2) {
        try {
            if (notificationChannel.getImportance() == 0) {
                if (notificationChannel2.getImportance() == 0) {
                }
                getContext().sendBroadcastAsUser(new android.content.Intent("android.app.action.NOTIFICATION_CHANNEL_BLOCK_STATE_CHANGED").putExtra("android.app.extra.NOTIFICATION_CHANNEL_ID", notificationChannel2.getId()).putExtra("android.app.extra.BLOCKED_STATE", notificationChannel2.getImportance() != 0).addFlags(268435456).setPackage(str), android.os.UserHandle.of(android.os.UserHandle.getUserId(i)), null);
            }
            if (notificationChannel.getImportance() == 0 || notificationChannel2.getImportance() != 0) {
                return;
            }
            getContext().sendBroadcastAsUser(new android.content.Intent("android.app.action.NOTIFICATION_CHANNEL_BLOCK_STATE_CHANGED").putExtra("android.app.extra.NOTIFICATION_CHANNEL_ID", notificationChannel2.getId()).putExtra("android.app.extra.BLOCKED_STATE", notificationChannel2.getImportance() != 0).addFlags(268435456).setPackage(str), android.os.UserHandle.of(android.os.UserHandle.getUserId(i)), null);
        } catch (java.lang.SecurityException e) {
            android.util.Slog.w(TAG, "Can't notify app about channel change", e);
        }
    }

    void createNotificationChannelGroup(java.lang.String str, int i, android.app.NotificationChannelGroup notificationChannelGroup, boolean z, boolean z2) {
        java.util.Objects.requireNonNull(notificationChannelGroup);
        java.util.Objects.requireNonNull(str);
        android.app.NotificationChannelGroup notificationChannelGroup2 = this.mPreferencesHelper.getNotificationChannelGroup(notificationChannelGroup.getId(), str, i);
        this.mPreferencesHelper.createNotificationChannelGroup(str, i, notificationChannelGroup, z, android.os.Binder.getCallingUid(), isCallerSystemOrSystemUi());
        if (!z) {
            maybeNotifyChannelGroupOwner(str, i, notificationChannelGroup2, notificationChannelGroup);
        }
        if (!z2) {
            this.mListeners.notifyNotificationChannelGroupChanged(str, android.os.UserHandle.of(android.os.UserHandle.getCallingUserId()), notificationChannelGroup, 1);
        }
    }

    private void maybeNotifyChannelGroupOwner(java.lang.String str, int i, android.app.NotificationChannelGroup notificationChannelGroup, android.app.NotificationChannelGroup notificationChannelGroup2) {
        try {
            if (notificationChannelGroup.isBlocked() != notificationChannelGroup2.isBlocked()) {
                getContext().sendBroadcastAsUser(new android.content.Intent("android.app.action.NOTIFICATION_CHANNEL_GROUP_BLOCK_STATE_CHANGED").putExtra("android.app.extra.NOTIFICATION_CHANNEL_GROUP_ID", notificationChannelGroup2.getId()).putExtra("android.app.extra.BLOCKED_STATE", notificationChannelGroup2.isBlocked()).addFlags(268435456).setPackage(str), android.os.UserHandle.of(android.os.UserHandle.getUserId(i)), null);
            }
        } catch (java.lang.SecurityException e) {
            android.util.Slog.w(TAG, "Can't notify app about group change", e);
        }
    }

    private java.util.ArrayList<android.content.ComponentName> getSuppressors() {
        java.util.ArrayList<android.content.ComponentName> arrayList = new java.util.ArrayList<>();
        for (int size = this.mListenersDisablingEffects.size() - 1; size >= 0; size--) {
            java.util.Iterator<android.content.ComponentName> it = this.mListenersDisablingEffects.valueAt(size).iterator();
            while (it.hasNext()) {
                arrayList.add(it.next());
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean removeDisabledHints(com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo) {
        return removeDisabledHints(managedServiceInfo, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean removeDisabledHints(com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo, int i) {
        boolean z = false;
        for (int size = this.mListenersDisablingEffects.size() - 1; size >= 0; size--) {
            int keyAt = this.mListenersDisablingEffects.keyAt(size);
            android.util.ArraySet<android.content.ComponentName> valueAt = this.mListenersDisablingEffects.valueAt(size);
            if (i == 0 || (keyAt & i) == keyAt) {
                z |= valueAt.remove(managedServiceInfo.component);
            }
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addDisabledHints(com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo, int i) {
        if ((i & 1) != 0) {
            addDisabledHint(managedServiceInfo, 1);
        }
        if ((i & 2) != 0) {
            addDisabledHint(managedServiceInfo, 2);
        }
        if ((i & 4) != 0) {
            addDisabledHint(managedServiceInfo, 4);
        }
    }

    private void addDisabledHint(com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo, int i) {
        if (this.mListenersDisablingEffects.indexOfKey(i) < 0) {
            this.mListenersDisablingEffects.put(i, new android.util.ArraySet<>());
        }
        this.mListenersDisablingEffects.get(i).add(managedServiceInfo.component);
    }

    private int calculateHints() {
        int i = 0;
        for (int size = this.mListenersDisablingEffects.size() - 1; size >= 0; size--) {
            int keyAt = this.mListenersDisablingEffects.keyAt(size);
            if (!this.mListenersDisablingEffects.valueAt(size).isEmpty()) {
                i |= keyAt;
            }
        }
        return i;
    }

    private long calculateSuppressedEffects() {
        long j;
        int calculateHints = calculateHints();
        if ((calculateHints & 1) == 0) {
            j = 0;
        } else {
            j = 3;
        }
        if ((calculateHints & 2) != 0) {
            j |= 1;
        }
        if ((calculateHints & 4) != 0) {
            return j | 2;
        }
        return j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    public void updateInterruptionFilterLocked() {
        int zenModeListenerInterruptionFilter = this.mZenModeHelper.getZenModeListenerInterruptionFilter();
        if (zenModeListenerInterruptionFilter == this.mInterruptionFilter) {
            return;
        }
        this.mInterruptionFilter = zenModeListenerInterruptionFilter;
        scheduleInterruptionFilterChanged(zenModeListenerInterruptionFilter);
    }

    int correctCategory(int i, int i2, int i3) {
        int i4 = i & i2;
        if (i4 != 0 && (i3 & i2) == 0) {
            return i & (~i2);
        }
        if (i4 == 0 && (i3 & i2) != 0) {
            return i | i2;
        }
        return i;
    }

    @com.android.internal.annotations.VisibleForTesting
    android.app.INotificationManager getBinderService() {
        return android.app.INotificationManager.Stub.asInterface(this.mService);
    }

    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    protected void reportSeen(com.android.server.notification.NotificationRecord notificationRecord) {
        if (!notificationRecord.isProxied()) {
            this.mAppUsageStats.reportEvent(notificationRecord.getSbn().getPackageName(), getRealUserId(notificationRecord.getSbn().getUserId()), 10);
        }
    }

    protected int calculateSuppressedVisualEffects(android.app.NotificationManager.Policy policy, android.app.NotificationManager.Policy policy2, int i) {
        if (policy.suppressedVisualEffects == -1) {
            return policy.suppressedVisualEffects;
        }
        int[] iArr = {4, 8, 16, 32, 64, 128, 256};
        int i2 = policy.suppressedVisualEffects;
        if (i < 28) {
            while (r4 < 7) {
                i2 = (i2 & (~iArr[r4])) | (policy2.suppressedVisualEffects & iArr[r4]);
                r4++;
            }
            if ((i2 & 1) != 0) {
                i2 = i2 | 8 | 4;
            }
            if ((i2 & 2) != 0) {
                return i2 | 16;
            }
            return i2;
        }
        if (((i2 + (-2)) - 1 > 0 ? 1 : 0) != 0) {
            int i3 = i2 & (-4);
            if ((i3 & 16) != 0) {
                i3 |= 2;
            }
            if ((i3 & 8) != 0 && (i3 & 4) != 0 && (i3 & 128) != 0) {
                return i3 | 1;
            }
            return i3;
        }
        if ((i2 & 1) != 0) {
            i2 = i2 | 8 | 4 | 128;
        }
        if ((i2 & 2) != 0) {
            return i2 | 16;
        }
        return i2;
    }

    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    protected void maybeRecordInterruptionLocked(com.android.server.notification.NotificationRecord notificationRecord) {
        if (notificationRecord.isInterruptive() && !notificationRecord.hasRecordedInterruption()) {
            this.mAppUsageStats.reportInterruptiveNotification(notificationRecord.getSbn().getPackageName(), notificationRecord.getChannel().getId(), getRealUserId(notificationRecord.getSbn().getUserId()));
            android.os.Trace.traceBegin(524288L, "notifHistoryAddItem");
            try {
                if (notificationRecord.getNotification().getSmallIcon() != null) {
                    this.mHistoryManager.addNotification(new android.app.NotificationHistory.HistoricalNotification.Builder().setPackage(notificationRecord.getSbn().getPackageName()).setUid(notificationRecord.getSbn().getUid()).setUserId(notificationRecord.getSbn().getNormalizedUserId()).setChannelId(notificationRecord.getChannel().getId()).setChannelName(notificationRecord.getChannel().getName().toString()).setPostedTimeMs(java.lang.System.currentTimeMillis()).setTitle(getHistoryTitle(notificationRecord.getNotification())).setText(getHistoryText(notificationRecord.getNotification())).setIcon(notificationRecord.getNotification().getSmallIcon()).build());
                }
                android.os.Trace.traceEnd(524288L);
                notificationRecord.setRecordedInterruption(true);
            } catch (java.lang.Throwable th) {
                android.os.Trace.traceEnd(524288L);
                throw th;
            }
        }
    }

    protected void reportForegroundServiceUpdate(final boolean z, final android.app.Notification notification, final int i, final java.lang.String str, final int i2) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.notification.NotificationManagerService$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.notification.NotificationManagerService.this.lambda$reportForegroundServiceUpdate$6(z, notification, i, str, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportForegroundServiceUpdate$6(boolean z, android.app.Notification notification, int i, java.lang.String str, int i2) {
        this.mAmi.onForegroundServiceNotificationUpdate(z, notification, i, str, i2);
    }

    protected void maybeReportForegroundServiceUpdate(com.android.server.notification.NotificationRecord notificationRecord, boolean z) {
        if (notificationRecord.isForegroundService()) {
            android.service.notification.StatusBarNotification sbn = notificationRecord.getSbn();
            reportForegroundServiceUpdate(z, sbn.getNotification(), sbn.getId(), sbn.getPackageName(), sbn.getUser().getIdentifier());
        }
    }

    private java.lang.String getHistoryTitle(android.app.Notification notification) {
        java.lang.CharSequence charSequence;
        if (notification.extras == null) {
            charSequence = null;
        } else {
            charSequence = notification.extras.getCharSequence("android.title");
            if (charSequence == null) {
                charSequence = notification.extras.getCharSequence("android.title.big");
            }
        }
        return charSequence == null ? getContext().getResources().getString(android.R.string.notification_channel_mobile_data_status) : java.lang.String.valueOf(charSequence);
    }

    private java.lang.String getHistoryText(android.app.Notification notification) {
        java.lang.CharSequence charSequence;
        java.util.List<android.app.Notification.MessagingStyle.Message> messages;
        if (notification.extras == null) {
            charSequence = null;
        } else {
            charSequence = notification.extras.getCharSequence("android.text");
            android.app.Notification.Builder recoverBuilder = android.app.Notification.Builder.recoverBuilder(getContext(), notification);
            if (recoverBuilder.getStyle() instanceof android.app.Notification.BigTextStyle) {
                charSequence = ((android.app.Notification.BigTextStyle) recoverBuilder.getStyle()).getBigText();
            } else if ((recoverBuilder.getStyle() instanceof android.app.Notification.MessagingStyle) && (messages = ((android.app.Notification.MessagingStyle) recoverBuilder.getStyle()).getMessages()) != null && messages.size() > 0) {
                charSequence = messages.get(messages.size() - 1).getText();
            }
            if (android.text.TextUtils.isEmpty(charSequence)) {
                charSequence = notification.extras.getCharSequence("android.text");
            }
        }
        if (charSequence == null) {
            return null;
        }
        return java.lang.String.valueOf(charSequence);
    }

    protected void maybeRegisterMessageSent(com.android.server.notification.NotificationRecord notificationRecord) {
        if (notificationRecord.isConversation()) {
            if (notificationRecord.getShortcutInfo() != null) {
                if (this.mPreferencesHelper.setValidMessageSent(notificationRecord.getSbn().getPackageName(), notificationRecord.getUid())) {
                    handleSavePolicyFile();
                    return;
                } else {
                    if (notificationRecord.getNotification().getBubbleMetadata() != null && this.mPreferencesHelper.setValidBubbleSent(notificationRecord.getSbn().getPackageName(), notificationRecord.getUid())) {
                        handleSavePolicyFile();
                        return;
                    }
                    return;
                }
            }
            if (this.mPreferencesHelper.setInvalidMessageSent(notificationRecord.getSbn().getPackageName(), notificationRecord.getUid())) {
                handleSavePolicyFile();
            }
        }
    }

    protected void reportUserInteraction(com.android.server.notification.NotificationRecord notificationRecord) {
        this.mAppUsageStats.reportEvent(notificationRecord.getSbn().getPackageName(), getRealUserId(notificationRecord.getSbn().getUserId()), 7);
        com.android.server.notification.Flags.politeNotifications();
    }

    private int getRealUserId(int i) {
        if (i == -1) {
            return 0;
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.notification.toast.ToastRecord getToastRecord(int i, int i2, java.lang.String str, boolean z, android.os.IBinder iBinder, @android.annotation.Nullable java.lang.CharSequence charSequence, @android.annotation.Nullable android.app.ITransientNotification iTransientNotification, int i3, android.os.Binder binder, int i4, @android.annotation.Nullable android.app.ITransientNotificationCallback iTransientNotificationCallback) {
        if (iTransientNotification == null) {
            return new com.android.server.notification.toast.TextToastRecord(this, this.mStatusBar, i, i2, str, z, iBinder, charSequence, i3, binder, i4, iTransientNotificationCallback);
        }
        return new com.android.server.notification.toast.CustomToastRecord(this, i, i2, str, z, iBinder, iTransientNotification, i3, binder, i4);
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.notification.NotificationManagerInternal getInternalService() {
        return this.mInternalService;
    }

    private com.android.server.utils.quota.MultiRateLimiter createToastRateLimiter() {
        return new com.android.server.utils.quota.MultiRateLimiter.Builder(getContext()).addRateLimits(TOAST_RATE_LIMITS).build();
    }

    protected int checkComponentPermission(java.lang.String str, int i, int i2, boolean z) {
        return android.app.ActivityManager.checkComponentPermission(str, i, i2, z);
    }

    /* renamed from: com.android.server.notification.NotificationManagerService$13, reason: invalid class name */
    class AnonymousClass13 extends android.app.INotificationManager.Stub {
        AnonymousClass13() {
        }

        public void enqueueTextToast(java.lang.String str, android.os.IBinder iBinder, java.lang.CharSequence charSequence, int i, boolean z, int i2, @android.annotation.Nullable android.app.ITransientNotificationCallback iTransientNotificationCallback) {
            enqueueToast(str, iBinder, charSequence, null, i, z, i2, iTransientNotificationCallback);
        }

        public void enqueueToast(java.lang.String str, android.os.IBinder iBinder, android.app.ITransientNotification iTransientNotification, int i, boolean z, int i2) {
            enqueueToast(str, iBinder, null, iTransientNotification, i, z, i2, null);
        }

        private void enqueueToast(java.lang.String str, android.os.IBinder iBinder, @android.annotation.Nullable java.lang.CharSequence charSequence, @android.annotation.Nullable android.app.ITransientNotification iTransientNotification, int i, boolean z, int i2, @android.annotation.Nullable android.app.ITransientNotificationCallback iTransientNotificationCallback) {
            int i3;
            java.util.ArrayList<com.android.server.notification.toast.ToastRecord> arrayList;
            int userId;
            int mainDisplayAssignedToUser;
            if (com.android.server.notification.NotificationManagerService.DBG) {
                android.util.Slog.i(com.android.server.notification.NotificationManagerService.TAG, "enqueueToast pkg=" + str + " token=" + iBinder + " duration=" + i + " isUiContext=" + z + " displayId=" + i2);
            }
            if (str == null || ((charSequence == null && iTransientNotification == null) || (!(charSequence == null || iTransientNotification == null) || iBinder == null))) {
                android.util.Slog.e(com.android.server.notification.NotificationManagerService.TAG, "Not enqueuing toast. pkg=" + str + " text=" + ((java.lang.Object) charSequence) + " callback= token=" + iBinder);
                return;
            }
            int callingUid = android.os.Binder.getCallingUid();
            if (z || i2 != 0 || !com.android.server.notification.NotificationManagerService.this.mUm.isVisibleBackgroundUsersSupported() || i2 == (mainDisplayAssignedToUser = com.android.server.notification.NotificationManagerService.this.mUmInternal.getMainDisplayAssignedToUser((userId = android.os.UserHandle.getUserId(callingUid))))) {
                i3 = i2;
            } else {
                if (com.android.server.notification.NotificationManagerService.DBG) {
                    com.android.server.utils.Slogf.d(com.android.server.notification.NotificationManagerService.TAG, "Changing display id from %d to %d on user %d", java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(mainDisplayAssignedToUser), java.lang.Integer.valueOf(userId));
                }
                i3 = mainDisplayAssignedToUser;
            }
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSameApp(str);
            boolean z2 = com.android.server.notification.NotificationManagerService.this.isCallerSystemOrSystemUi() || com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(str);
            if (!checkCanEnqueueToast(str, callingUid, i3, iTransientNotification != null, z2)) {
                return;
            }
            java.util.ArrayList<com.android.server.notification.toast.ToastRecord> arrayList2 = com.android.server.notification.NotificationManagerService.this.mToastQueue;
            synchronized (arrayList2) {
                try {
                    try {
                        int callingPid = android.os.Binder.getCallingPid();
                        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        try {
                            int indexOfToastLocked = com.android.server.notification.NotificationManagerService.this.indexOfToastLocked(str, iBinder);
                            if (indexOfToastLocked >= 0) {
                                com.android.server.notification.NotificationManagerService.this.mToastQueue.get(indexOfToastLocked).update(i);
                                arrayList = arrayList2;
                            } else {
                                int size = com.android.server.notification.NotificationManagerService.this.mToastQueue.size();
                                int i4 = 0;
                                for (int i5 = 0; i5 < size; i5++) {
                                    if (com.android.server.notification.NotificationManagerService.this.mToastQueue.get(i5).pkg.equals(str) && (i4 = i4 + 1) >= 5) {
                                        android.util.Slog.e(com.android.server.notification.NotificationManagerService.TAG, "Package has already queued " + i4 + " toasts. Not showing more. Package=" + str);
                                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                        return;
                                    }
                                }
                                android.os.Binder binder = new android.os.Binder();
                                com.android.server.notification.NotificationManagerService.this.mWindowManagerInternal.addWindowToken(binder, 2005, i3, null);
                                arrayList = arrayList2;
                                try {
                                    com.android.server.notification.toast.ToastRecord toastRecord = com.android.server.notification.NotificationManagerService.this.getToastRecord(callingUid, callingPid, str, z2, iBinder, charSequence, iTransientNotification, i, binder, i3, iTransientNotificationCallback);
                                    int size2 = com.android.server.notification.NotificationManagerService.this.mToastQueue.size();
                                    if (z2) {
                                        size2 = getInsertIndexForSystemToastLocked();
                                    }
                                    if (size2 < com.android.server.notification.NotificationManagerService.this.mToastQueue.size()) {
                                        com.android.server.notification.NotificationManagerService.this.mToastQueue.add(size2, toastRecord);
                                        indexOfToastLocked = size2;
                                    } else {
                                        com.android.server.notification.NotificationManagerService.this.mToastQueue.add(toastRecord);
                                        indexOfToastLocked = com.android.server.notification.NotificationManagerService.this.mToastQueue.size() - 1;
                                    }
                                    com.android.server.notification.NotificationManagerService.this.keepProcessAliveForToastIfNeededLocked(callingPid);
                                } catch (java.lang.Throwable th) {
                                    th = th;
                                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                    throw th;
                                }
                            }
                            if (indexOfToastLocked == 0) {
                                com.android.server.notification.NotificationManagerService.this.showNextToastLocked(false);
                            }
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        } catch (java.lang.Throwable th2) {
                            th = th2;
                        }
                    } catch (java.lang.Throwable th3) {
                        th = th3;
                        throw th;
                    }
                } catch (java.lang.Throwable th4) {
                    th = th4;
                    throw th;
                }
            }
        }

        @com.android.internal.annotations.GuardedBy({"mToastQueue"})
        private int getInsertIndexForSystemToastLocked() {
            java.util.Iterator<com.android.server.notification.toast.ToastRecord> it = com.android.server.notification.NotificationManagerService.this.mToastQueue.iterator();
            int i = 0;
            while (it.hasNext()) {
                com.android.server.notification.toast.ToastRecord next = it.next();
                if (i == 0 && com.android.server.notification.NotificationManagerService.this.mIsCurrentToastShown) {
                    i++;
                } else {
                    if (!next.isSystemToast) {
                        return i;
                    }
                    i++;
                }
            }
            return i;
        }

        private boolean checkCanEnqueueToast(java.lang.String str, int i, int i2, boolean z, boolean z2) {
            boolean isPackagePaused = isPackagePaused(str);
            boolean z3 = !areNotificationsEnabledForPackage(str, i);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                boolean z4 = com.android.server.notification.NotificationManagerService.this.mActivityManager.getUidImportance(i) == 100;
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                if (!z2 && ((z3 && !z4) || isPackagePaused)) {
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append("Suppressing toast from package ");
                    sb.append(str);
                    sb.append(isPackagePaused ? " due to package suspended." : " by user request.");
                    android.util.Slog.e(com.android.server.notification.NotificationManagerService.TAG, sb.toString());
                    return false;
                }
                if (com.android.server.notification.NotificationManagerService.this.blockToast(i, z2, z, com.android.server.notification.NotificationManagerService.this.isPackageInForegroundForToast(i))) {
                    android.util.Slog.w(com.android.server.notification.NotificationManagerService.TAG, "Blocking custom toast from package " + str + " due to package not in the foreground at time the toast was posted");
                    return false;
                }
                int userId = android.os.UserHandle.getUserId(i);
                if (z2 || com.android.server.notification.NotificationManagerService.this.mUmInternal.isUserVisible(userId, i2)) {
                    return true;
                }
                android.util.Slog.e(com.android.server.notification.NotificationManagerService.TAG, "Suppressing toast from package " + str + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + i + " as user " + userId + " is not visible on display " + i2);
                return false;
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public void cancelToast(java.lang.String str, android.os.IBinder iBinder) {
            android.util.Slog.i(com.android.server.notification.NotificationManagerService.TAG, "cancelToast pkg=" + str + " token=" + iBinder);
            if (str == null || iBinder == null) {
                android.util.Slog.e(com.android.server.notification.NotificationManagerService.TAG, "Not cancelling notification. pkg=" + str + " token=" + iBinder);
                return;
            }
            synchronized (com.android.server.notification.NotificationManagerService.this.mToastQueue) {
                try {
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        int indexOfToastLocked = com.android.server.notification.NotificationManagerService.this.indexOfToastLocked(str, iBinder);
                        if (indexOfToastLocked >= 0) {
                            com.android.server.notification.NotificationManagerService.this.cancelToastLocked(indexOfToastLocked);
                        } else {
                            android.util.Slog.w(com.android.server.notification.NotificationManagerService.TAG, "Toast already cancelled. pkg=" + str + " token=" + iBinder);
                        }
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    } catch (java.lang.Throwable th) {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                } catch (java.lang.Throwable th2) {
                    throw th2;
                }
            }
        }

        @android.annotation.EnforcePermission("android.permission.MANAGE_TOAST_RATE_LIMITING")
        public void setToastRateLimitingEnabled(boolean z) {
            super.setToastRateLimitingEnabled_enforcePermission();
            synchronized (com.android.server.notification.NotificationManagerService.this.mToastQueue) {
                int callingUid = android.os.Binder.getCallingUid();
                int userId = android.os.UserHandle.getUserId(callingUid);
                if (z) {
                    com.android.server.notification.NotificationManagerService.this.mToastRateLimitingDisabledUids.remove(java.lang.Integer.valueOf(callingUid));
                    try {
                        java.lang.String[] packagesForUid = com.android.server.notification.NotificationManagerService.this.mPackageManager.getPackagesForUid(callingUid);
                        if (packagesForUid == null) {
                            android.util.Slog.e(com.android.server.notification.NotificationManagerService.TAG, "setToastRateLimitingEnabled method haven't found any packages for the  given uid: " + callingUid + ", toast rate limiter not reset for that uid.");
                            return;
                        }
                        for (java.lang.String str : packagesForUid) {
                            com.android.server.notification.NotificationManagerService.this.mToastRateLimiter.clear(userId, str);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(com.android.server.notification.NotificationManagerService.TAG, "Failed to reset toast rate limiter for given uid", e);
                    }
                } else {
                    com.android.server.notification.NotificationManagerService.this.mToastRateLimitingDisabledUids.add(java.lang.Integer.valueOf(callingUid));
                }
            }
        }

        public void finishToken(java.lang.String str, android.os.IBinder iBinder) {
            synchronized (com.android.server.notification.NotificationManagerService.this.mToastQueue) {
                try {
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        int indexOfToastLocked = com.android.server.notification.NotificationManagerService.this.indexOfToastLocked(str, iBinder);
                        if (indexOfToastLocked >= 0) {
                            com.android.server.notification.toast.ToastRecord toastRecord = com.android.server.notification.NotificationManagerService.this.mToastQueue.get(indexOfToastLocked);
                            com.android.server.notification.NotificationManagerService.this.finishWindowTokenLocked(toastRecord.windowToken, toastRecord.displayId);
                        } else {
                            android.util.Slog.w(com.android.server.notification.NotificationManagerService.TAG, "Toast already killed. pkg=" + str + " token=" + iBinder);
                        }
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    } catch (java.lang.Throwable th) {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                } catch (java.lang.Throwable th2) {
                    throw th2;
                }
            }
        }

        public void enqueueNotificationWithTag(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, android.app.Notification notification, int i2) throws android.os.RemoteException {
            com.android.server.notification.NotificationManagerService.this.enqueueNotificationInternal(str, str2, android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid(), str3, i, notification, i2, false);
        }

        public void cancelNotificationWithTag(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, int i2) {
            int i3;
            int i4 = com.android.server.notification.NotificationManagerService.this.isCallingUidSystem() ? 0 : 33856;
            if (!android.app.Flags.lifetimeExtensionRefactor()) {
                i3 = i4;
            } else {
                int i5 = i4 | 65536;
                int packageImportanceWithIdentity = com.android.server.notification.NotificationManagerService.this.getPackageImportanceWithIdentity(str);
                synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                    com.android.server.notification.NotificationManagerService.this.maybeNotifySystemUiListenerLifetimeExtendedLocked(com.android.server.notification.NotificationManagerService.this.findNotificationLocked(str, str3, i, i2), str, packageImportanceWithIdentity);
                }
                i3 = i5;
            }
            com.android.server.notification.NotificationManagerService.this.cancelNotificationInternal(str, str2, android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid(), str3, i, i2, i3);
        }

        public void cancelAllNotifications(java.lang.String str, int i) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystemOrSameApp(str);
            int handleIncomingUser = android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, true, false, "cancelAllNotifications", str);
            if (android.app.Flags.lifetimeExtensionRefactor()) {
                com.android.server.notification.NotificationManagerService.this.cancelAllNotificationsInt(android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid(), str, null, 0, 98368, handleIncomingUser, 9);
                int packageImportanceWithIdentity = com.android.server.notification.NotificationManagerService.this.getPackageImportanceWithIdentity(str);
                synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                    com.android.server.notification.NotificationManagerService.this.notifySystemUiListenerLifetimeExtendedListLocked(com.android.server.notification.NotificationManagerService.this.mNotificationList, packageImportanceWithIdentity);
                    com.android.server.notification.NotificationManagerService.this.notifySystemUiListenerLifetimeExtendedListLocked(com.android.server.notification.NotificationManagerService.this.mEnqueuedNotifications, packageImportanceWithIdentity);
                }
                return;
            }
            com.android.server.notification.NotificationManagerService.this.cancelAllNotificationsInt(android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid(), str, null, 0, 32832, handleIncomingUser, 9);
        }

        public void silenceNotificationSound() {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystem();
            com.android.server.notification.NotificationManagerService.this.mNotificationDelegate.clearEffects();
        }

        public void setNotificationsEnabledForPackage(java.lang.String str, int i, boolean z) {
            enforceSystemOrSystemUI("setNotificationsEnabledForPackage");
            if (com.android.server.notification.NotificationManagerService.this.mPermissionHelper.hasPermission(i) == z) {
                return;
            }
            com.android.server.notification.NotificationManagerService.this.mPermissionHelper.setNotificationPermission(str, android.os.UserHandle.getUserId(i), z, true);
            com.android.server.notification.NotificationManagerService.this.sendAppBlockStateChangedBroadcast(str, i, !z);
            com.android.server.notification.NotificationManagerService.this.mMetricsLogger.write(new android.metrics.LogMaker(147).setType(4).setPackageName(str).setSubtype(z ? 1 : 0));
            com.android.server.notification.NotificationManagerService.this.mNotificationChannelLogger.logAppNotificationsAllowed(i, str, z);
        }

        public void setNotificationSoundTimeout(java.lang.String str, int i, long j) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystem();
            com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.setNotificationSoundTimeout(str, i, j);
            com.android.server.notification.NotificationManagerService.this.handleSavePolicyFile();
        }

        public long getNotificationSoundTimeout(java.lang.String str, int i) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystem();
            return com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.getNotificationSoundTimeout(str, i);
        }

        public void setNotificationsEnabledWithImportanceLockForPackage(java.lang.String str, int i, boolean z) {
            setNotificationsEnabledForPackage(str, i, z);
        }

        public boolean areNotificationsEnabled(java.lang.String str) {
            return areNotificationsEnabledForPackage(str, android.os.Binder.getCallingUid());
        }

        public boolean areNotificationsEnabledForPackage(java.lang.String str, int i) {
            enforceSystemOrSystemUIOrSamePackage(str, "Caller not system or systemui or same package");
            if (android.os.UserHandle.getCallingUserId() != android.os.UserHandle.getUserId(i)) {
                com.android.server.notification.NotificationManagerService.this.getContext().enforceCallingPermission("android.permission.INTERACT_ACROSS_USERS", "canNotifyAsPackage for uid " + i);
            }
            return com.android.server.notification.NotificationManagerService.this.areNotificationsEnabledForPackageInt(str, i);
        }

        public boolean areBubblesAllowed(java.lang.String str) {
            return getBubblePreferenceForPackage(str, android.os.Binder.getCallingUid()) == 1;
        }

        public boolean areBubblesEnabled(android.os.UserHandle userHandle) {
            if (android.os.UserHandle.getCallingUserId() != userHandle.getIdentifier()) {
                com.android.server.notification.NotificationManagerService.this.getContext().enforceCallingPermission("android.permission.INTERACT_ACROSS_USERS", "areBubblesEnabled for user " + userHandle.getIdentifier());
            }
            return com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.bubblesEnabled(userHandle);
        }

        public int getBubblePreferenceForPackage(java.lang.String str, int i) {
            enforceSystemOrSystemUIOrSamePackage(str, "Caller not system or systemui or same package");
            if (android.os.UserHandle.getCallingUserId() != android.os.UserHandle.getUserId(i)) {
                com.android.server.notification.NotificationManagerService.this.getContext().enforceCallingPermission("android.permission.INTERACT_ACROSS_USERS", "getBubblePreferenceForPackage for uid " + i);
            }
            return com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.getBubblePreference(str, i);
        }

        public void setBubblesAllowed(java.lang.String str, int i, int i2) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystemOrSystemUiOrShell("Caller not system or sysui or shell");
            com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.setBubblesAllowed(str, i, i2);
            com.android.server.notification.NotificationManagerService.this.handleSavePolicyFile();
        }

        public boolean shouldHideSilentStatusIcons(java.lang.String str) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSameApp(str);
            if (com.android.server.notification.NotificationManagerService.this.isCallerSystemOrPhone() || com.android.server.notification.NotificationManagerService.this.mListeners.isListenerPackage(str)) {
                return com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.shouldHideSilentStatusIcons();
            }
            throw new java.lang.SecurityException("Only available for notification listeners");
        }

        public void setHideSilentStatusIcons(boolean z) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystem();
            com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.setHideSilentStatusIcons(z);
            com.android.server.notification.NotificationManagerService.this.handleSavePolicyFile();
            com.android.server.notification.NotificationManagerService.this.mListeners.onStatusBarIconsBehaviorChanged(z);
        }

        public void deleteNotificationHistoryItem(java.lang.String str, int i, long j) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystem();
            com.android.server.notification.NotificationManagerService.this.mHistoryManager.deleteNotificationHistoryItem(str, i, j);
        }

        public android.service.notification.NotificationListenerFilter getListenerFilter(android.content.ComponentName componentName, int i) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystem();
            return com.android.server.notification.NotificationManagerService.this.mListeners.getNotificationListenerFilter(android.util.Pair.create(componentName, java.lang.Integer.valueOf(i)));
        }

        public void setListenerFilter(android.content.ComponentName componentName, int i, android.service.notification.NotificationListenerFilter notificationListenerFilter) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystem();
            com.android.server.notification.NotificationManagerService.this.mListeners.setNotificationListenerFilter(android.util.Pair.create(componentName, java.lang.Integer.valueOf(i)), notificationListenerFilter);
            com.android.server.notification.NotificationManagerService.this.handleSavePolicyFile();
        }

        public int getPackageImportance(java.lang.String str) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystemOrSameApp(str);
            if (com.android.server.notification.NotificationManagerService.this.mPermissionHelper.hasPermission(android.os.Binder.getCallingUid())) {
                return 3;
            }
            return 0;
        }

        public boolean isImportanceLocked(java.lang.String str, int i) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystem();
            return com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.isImportanceLocked(str, i);
        }

        public boolean canShowBadge(java.lang.String str, int i) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystem();
            return com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.canShowBadge(str, i);
        }

        public void setShowBadge(java.lang.String str, int i, boolean z) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystem();
            com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.setShowBadge(str, i, z);
            com.android.server.notification.NotificationManagerService.this.handleSavePolicyFile();
        }

        public boolean hasSentValidMsg(java.lang.String str, int i) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystem();
            return com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.hasSentValidMsg(str, i);
        }

        public boolean isInInvalidMsgState(java.lang.String str, int i) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystem();
            return com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.isInInvalidMsgState(str, i);
        }

        public boolean hasUserDemotedInvalidMsgApp(java.lang.String str, int i) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystem();
            return com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.hasUserDemotedInvalidMsgApp(str, i);
        }

        public void setInvalidMsgAppDemoted(java.lang.String str, int i, boolean z) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystem();
            com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.setInvalidMsgAppDemoted(str, i, z);
            com.android.server.notification.NotificationManagerService.this.handleSavePolicyFile();
        }

        public boolean hasSentValidBubble(java.lang.String str, int i) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystem();
            return com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.hasSentValidBubble(str, i);
        }

        public void setNotificationDelegate(java.lang.String str, java.lang.String str2) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSameApp(str);
            int callingUid = android.os.Binder.getCallingUid();
            android.os.UserHandle userHandleForUid = android.os.UserHandle.getUserHandleForUid(callingUid);
            if (str2 == null) {
                com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.revokeNotificationDelegate(str, android.os.Binder.getCallingUid());
                com.android.server.notification.NotificationManagerService.this.handleSavePolicyFile();
                return;
            }
            try {
                android.content.pm.ApplicationInfo applicationInfo = com.android.server.notification.NotificationManagerService.this.mPackageManager.getApplicationInfo(str2, 786432L, userHandleForUid.getIdentifier());
                if (applicationInfo != null) {
                    com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.setNotificationDelegate(str, callingUid, str2, applicationInfo.uid);
                    com.android.server.notification.NotificationManagerService.this.handleSavePolicyFile();
                }
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }

        public java.lang.String getNotificationDelegate(java.lang.String str) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystemOrSameApp(str);
            return com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.getNotificationDelegate(str, android.os.Binder.getCallingUid());
        }

        public boolean canNotifyAsPackage(java.lang.String str, java.lang.String str2, int i) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSameApp(str);
            int callingUid = android.os.Binder.getCallingUid();
            if (android.os.UserHandle.getUserHandleForUid(callingUid).getIdentifier() != i) {
                com.android.server.notification.NotificationManagerService.this.getContext().enforceCallingPermission("android.permission.INTERACT_ACROSS_USERS", "canNotifyAsPackage for user " + i);
            }
            if (str.equals(str2)) {
                return true;
            }
            try {
                android.content.pm.ApplicationInfo applicationInfo = com.android.server.notification.NotificationManagerService.this.mPackageManager.getApplicationInfo(str2, 786432L, i);
                if (applicationInfo != null) {
                    return com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.isDelegateAllowed(str2, applicationInfo.uid, str, callingUid);
                }
                return false;
            } catch (android.os.RemoteException e) {
                return false;
            }
        }

        public boolean canUseFullScreenIntent(@android.annotation.NonNull android.content.AttributionSource attributionSource) {
            java.lang.String packageName = attributionSource.getPackageName();
            int uid = attributionSource.getUid();
            int userId = android.os.UserHandle.getUserId(uid);
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSameApp(packageName, uid, userId);
            try {
                return com.android.server.notification.NotificationManagerService.this.checkUseFullScreenIntentPermission(attributionSource, com.android.server.notification.NotificationManagerService.this.mPackageManagerClient.getApplicationInfoAsUser(packageName, 268435456, userId), false);
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                android.util.Slog.e(com.android.server.notification.NotificationManagerService.TAG, "Failed to getApplicationInfo() in canUseFullScreenIntent()", e);
                return false;
            }
        }

        public void updateNotificationChannelGroupForPackage(java.lang.String str, int i, android.app.NotificationChannelGroup notificationChannelGroup) throws android.os.RemoteException {
            enforceSystemOrSystemUI("Caller not system or systemui");
            com.android.server.notification.NotificationManagerService.this.createNotificationChannelGroup(str, i, notificationChannelGroup, false, false);
            com.android.server.notification.NotificationManagerService.this.handleSavePolicyFile();
        }

        public void createNotificationChannelGroups(java.lang.String str, android.content.pm.ParceledListSlice parceledListSlice) throws android.os.RemoteException {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystemOrSameApp(str);
            java.util.List list = parceledListSlice.getList();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                com.android.server.notification.NotificationManagerService.this.createNotificationChannelGroup(str, android.os.Binder.getCallingUid(), (android.app.NotificationChannelGroup) list.get(i), true, false);
            }
            com.android.server.notification.NotificationManagerService.this.handleSavePolicyFile();
        }

        private void createNotificationChannelsImpl(java.lang.String str, int i, android.content.pm.ParceledListSlice parceledListSlice) {
            createNotificationChannelsImpl(str, i, parceledListSlice, -1);
        }

        private void createNotificationChannelsImpl(java.lang.String str, int i, android.content.pm.ParceledListSlice parceledListSlice, int i2) {
            boolean z;
            boolean z2;
            java.util.List list = parceledListSlice.getList();
            int size = list.size();
            android.content.pm.ParceledListSlice<android.app.NotificationChannel> notificationChannels = com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.getNotificationChannels(str, i, true);
            if (notificationChannels != null && !notificationChannels.getList().isEmpty()) {
                z = true;
            } else {
                z = false;
            }
            boolean z3 = false;
            int i3 = 0;
            boolean z4 = false;
            while (i3 < size) {
                android.app.NotificationChannel notificationChannel = (android.app.NotificationChannel) list.get(i3);
                java.util.Objects.requireNonNull(notificationChannel, "channel in list is null");
                int i4 = i3;
                z3 = com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.createNotificationChannel(str, i, notificationChannel, true, com.android.server.notification.NotificationManagerService.this.mConditionProviders.isPackageOrComponentAllowed(str, android.os.UserHandle.getUserId(i)), android.os.Binder.getCallingUid(), com.android.server.notification.NotificationManagerService.this.isCallerSystemOrSystemUi());
                if (z3) {
                    com.android.server.notification.NotificationManagerService.this.mListeners.notifyNotificationChannelChanged(str, android.os.UserHandle.getUserHandleForUid(i), com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.getNotificationChannel(str, i, notificationChannel.getId(), false), 1);
                    if (z || z4) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    if (!z2) {
                        android.content.pm.ParceledListSlice<android.app.NotificationChannel> notificationChannels2 = com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.getNotificationChannels(str, i, true);
                        if (notificationChannels2 != null && !notificationChannels2.getList().isEmpty()) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                    }
                    if (!z && z2 && !z4 && i2 != -1) {
                        if (com.android.server.notification.NotificationManagerService.this.mPermissionPolicyInternal == null) {
                            com.android.server.notification.NotificationManagerService.this.mPermissionPolicyInternal = (com.android.server.policy.PermissionPolicyInternal) com.android.server.LocalServices.getService(com.android.server.policy.PermissionPolicyInternal.class);
                        }
                        com.android.server.notification.NotificationManagerService.this.mHandler.post(new com.android.server.notification.NotificationManagerService.ShowNotificationPermissionPromptRunnable(str, android.os.UserHandle.getUserId(i), i2, com.android.server.notification.NotificationManagerService.this.mPermissionPolicyInternal));
                        z4 = true;
                    }
                }
                i3 = i4 + 1;
            }
            if (z3) {
                com.android.server.notification.NotificationManagerService.this.handleSavePolicyFile();
            }
        }

        public void createNotificationChannels(java.lang.String str, android.content.pm.ParceledListSlice parceledListSlice) {
            int i;
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystemOrSameApp(str);
            try {
                i = com.android.server.notification.NotificationManagerService.this.mAtm.getTaskToShowPermissionDialogOn(str, com.android.server.notification.NotificationManagerService.this.mPackageManager.getPackageUid(str, 0L, android.os.UserHandle.getUserId(android.os.Binder.getCallingUid())));
            } catch (android.os.RemoteException e) {
                i = -1;
            }
            createNotificationChannelsImpl(str, android.os.Binder.getCallingUid(), parceledListSlice, i);
        }

        public void createNotificationChannelsForPackage(java.lang.String str, int i, android.content.pm.ParceledListSlice parceledListSlice) {
            enforceSystemOrSystemUI("only system can call this");
            createNotificationChannelsImpl(str, i, parceledListSlice);
        }

        public void createConversationNotificationChannelForPackage(java.lang.String str, int i, android.app.NotificationChannel notificationChannel, java.lang.String str2) {
            enforceSystemOrSystemUI("only system can call this");
            com.android.internal.util.Preconditions.checkNotNull(notificationChannel);
            com.android.internal.util.Preconditions.checkNotNull(str2);
            java.lang.String id = notificationChannel.getId();
            notificationChannel.setId(java.lang.String.format("%1$s : %2$s", id, str2));
            notificationChannel.setConversationId(id, str2);
            createNotificationChannelsImpl(str, i, new android.content.pm.ParceledListSlice(java.util.Arrays.asList(notificationChannel)));
            com.android.server.notification.NotificationManagerService.this.mRankingHandler.requestSort();
            com.android.server.notification.NotificationManagerService.this.handleSavePolicyFile();
        }

        public android.app.NotificationChannel getNotificationChannel(java.lang.String str, int i, java.lang.String str2, java.lang.String str3) {
            return getConversationNotificationChannel(str, i, str2, str3, true, null);
        }

        public android.app.NotificationChannel getConversationNotificationChannel(java.lang.String str, int i, java.lang.String str2, java.lang.String str3, boolean z, java.lang.String str4) {
            int i2;
            if (canNotifyAsPackage(str, str2, i) || com.android.server.notification.NotificationManagerService.this.isCallerSystemOrSystemUiOrShell()) {
                try {
                    i2 = com.android.server.notification.NotificationManagerService.this.mPackageManagerClient.getPackageUidAsUser(str2, i);
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    i2 = -1;
                }
                return com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.getConversationNotificationChannel(str2, i2, str3, str4, z, false);
            }
            throw new java.lang.SecurityException("Pkg " + str + " cannot read channels for " + str2 + " in " + i);
        }

        public android.app.NotificationChannel getNotificationChannelForPackage(java.lang.String str, int i, java.lang.String str2, java.lang.String str3, boolean z) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystem();
            return com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.getConversationNotificationChannel(str, i, str2, str3, true, z);
        }

        private void enforceDeletingChannelHasNoFgService(java.lang.String str, int i, java.lang.String str2) {
            if (com.android.server.notification.NotificationManagerService.this.mAmi.hasForegroundServiceNotification(str, i, str2)) {
                android.util.Slog.w(com.android.server.notification.NotificationManagerService.TAG, "Package u" + i + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + str + " may not delete notification channel '" + str2 + "' with fg service");
                throw new java.lang.SecurityException("Not allowed to delete channel " + str2 + " with a foreground service");
            }
        }

        private void enforceDeletingChannelHasNoUserInitiatedJob(java.lang.String str, int i, java.lang.String str2) {
            com.android.server.job.JobSchedulerInternal jobSchedulerInternal = (com.android.server.job.JobSchedulerInternal) com.android.server.LocalServices.getService(com.android.server.job.JobSchedulerInternal.class);
            if (jobSchedulerInternal != null && jobSchedulerInternal.isNotificationChannelAssociatedWithAnyUserInitiatedJobs(str2, i, str)) {
                android.util.Slog.w(com.android.server.notification.NotificationManagerService.TAG, "Package u" + i + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + str + " may not delete notification channel '" + str2 + "' with user-initiated job");
                throw new java.lang.SecurityException("Not allowed to delete channel " + str2 + " with a user-initiated job");
            }
        }

        public void deleteNotificationChannel(java.lang.String str, java.lang.String str2) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystemOrSameApp(str);
            int callingUid = android.os.Binder.getCallingUid();
            boolean isCallerSystemOrSystemUi = com.android.server.notification.NotificationManagerService.this.isCallerSystemOrSystemUi();
            int userId = android.os.UserHandle.getUserId(callingUid);
            if ("miscellaneous".equals(str2)) {
                throw new java.lang.IllegalArgumentException("Cannot delete default channel");
            }
            enforceDeletingChannelHasNoFgService(str, userId, str2);
            enforceDeletingChannelHasNoUserInitiatedJob(str, userId, str2);
            com.android.server.notification.NotificationManagerService.this.cancelAllNotificationsInt(com.android.server.notification.NotificationManagerService.MY_UID, com.android.server.notification.NotificationManagerService.MY_PID, str, str2, 0, 0, userId, 20);
            if (com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.deleteNotificationChannel(str, callingUid, str2, callingUid, isCallerSystemOrSystemUi)) {
                com.android.server.notification.NotificationManagerService.this.mArchive.removeChannelNotifications(str, userId, str2);
                com.android.server.notification.NotificationManagerService.this.mHistoryManager.deleteNotificationChannel(str, callingUid, str2);
                com.android.server.notification.NotificationManagerService.this.mListeners.notifyNotificationChannelChanged(str, android.os.UserHandle.getUserHandleForUid(callingUid), com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.getNotificationChannel(str, callingUid, str2, true), 3);
                com.android.server.notification.NotificationManagerService.this.handleSavePolicyFile();
            }
        }

        public android.app.NotificationChannelGroup getNotificationChannelGroup(java.lang.String str, java.lang.String str2) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystemOrSameApp(str);
            return com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.getNotificationChannelGroupWithChannels(str, android.os.Binder.getCallingUid(), str2, false);
        }

        public android.content.pm.ParceledListSlice<android.app.NotificationChannelGroup> getNotificationChannelGroups(java.lang.String str) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystemOrSameApp(str);
            return com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.getNotificationChannelGroups(str, android.os.Binder.getCallingUid(), false, false, true, true, null);
        }

        public void deleteNotificationChannelGroup(java.lang.String str, java.lang.String str2) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystemOrSameApp(str);
            int callingUid = android.os.Binder.getCallingUid();
            boolean isCallerSystemOrSystemUi = com.android.server.notification.NotificationManagerService.this.isCallerSystemOrSystemUi();
            android.app.NotificationChannelGroup notificationChannelGroupWithChannels = com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.getNotificationChannelGroupWithChannels(str, callingUid, str2, false);
            if (notificationChannelGroupWithChannels != null) {
                int userId = android.os.UserHandle.getUserId(callingUid);
                java.util.List<android.app.NotificationChannel> channels = notificationChannelGroupWithChannels.getChannels();
                for (int i = 0; i < channels.size(); i++) {
                    java.lang.String id = channels.get(i).getId();
                    enforceDeletingChannelHasNoFgService(str, userId, id);
                    enforceDeletingChannelHasNoUserInitiatedJob(str, userId, id);
                }
                java.util.List<android.app.NotificationChannel> deleteNotificationChannelGroup = com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.deleteNotificationChannelGroup(str, callingUid, str2, callingUid, isCallerSystemOrSystemUi);
                int i2 = 0;
                while (i2 < deleteNotificationChannelGroup.size()) {
                    android.app.NotificationChannel notificationChannel = deleteNotificationChannelGroup.get(i2);
                    com.android.server.notification.NotificationManagerService.this.cancelAllNotificationsInt(com.android.server.notification.NotificationManagerService.MY_UID, com.android.server.notification.NotificationManagerService.MY_PID, str, notificationChannel.getId(), 0, 0, userId, 20);
                    com.android.server.notification.NotificationManagerService.this.mListeners.notifyNotificationChannelChanged(str, android.os.UserHandle.getUserHandleForUid(callingUid), notificationChannel, 3);
                    i2++;
                    userId = userId;
                }
                com.android.server.notification.NotificationManagerService.this.mListeners.notifyNotificationChannelGroupChanged(str, android.os.UserHandle.getUserHandleForUid(callingUid), notificationChannelGroupWithChannels, 3);
                com.android.server.notification.NotificationManagerService.this.handleSavePolicyFile();
            }
        }

        public void updateNotificationChannelForPackage(java.lang.String str, int i, android.app.NotificationChannel notificationChannel) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystemOrSystemUiOrShell("Caller not system or sysui or shell");
            java.util.Objects.requireNonNull(notificationChannel);
            com.android.server.notification.NotificationManagerService.this.updateNotificationChannelInt(str, i, notificationChannel, false);
        }

        public void unlockNotificationChannel(java.lang.String str, int i, java.lang.String str2) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystemOrSystemUiOrShell("Caller not system or sysui or shell");
            com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.unlockNotificationChannelImportance(str, i, str2);
            com.android.server.notification.NotificationManagerService.this.handleSavePolicyFile();
        }

        public void unlockAllNotificationChannels() {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystem();
            com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.unlockAllNotificationChannels();
            com.android.server.notification.NotificationManagerService.this.handleSavePolicyFile();
        }

        public android.content.pm.ParceledListSlice<android.app.NotificationChannel> getNotificationChannelsForPackage(java.lang.String str, int i, boolean z) {
            enforceSystemOrSystemUI("getNotificationChannelsForPackage");
            return com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.getNotificationChannels(str, i, z);
        }

        public int getNumNotificationChannelsForPackage(java.lang.String str, int i, boolean z) {
            enforceSystemOrSystemUI("getNumNotificationChannelsForPackage");
            return com.android.server.notification.NotificationManagerService.this.getNumNotificationChannelsForPackage(str, i, z);
        }

        public boolean onlyHasDefaultChannel(java.lang.String str, int i) {
            enforceSystemOrSystemUI("onlyHasDefaultChannel");
            return com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.onlyHasDefaultChannel(str, i);
        }

        public int getDeletedChannelCount(java.lang.String str, int i) {
            enforceSystemOrSystemUI("getDeletedChannelCount");
            return com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.getDeletedChannelCount(str, i);
        }

        public int getBlockedChannelCount(java.lang.String str, int i) {
            enforceSystemOrSystemUI("getBlockedChannelCount");
            return com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.getBlockedChannelCount(str, i);
        }

        public android.content.pm.ParceledListSlice<android.service.notification.ConversationChannelWrapper> getConversations(boolean z) {
            enforceSystemOrSystemUI("getConversations");
            java.util.ArrayList<android.service.notification.ConversationChannelWrapper> conversations = com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.getConversations(com.android.server.notification.NotificationManagerService.this.mUserProfiles.getCurrentProfileIds(), z);
            java.util.Iterator<android.service.notification.ConversationChannelWrapper> it = conversations.iterator();
            while (it.hasNext()) {
                android.service.notification.ConversationChannelWrapper next = it.next();
                if (com.android.server.notification.NotificationManagerService.this.mShortcutHelper == null) {
                    next.setShortcutInfo((android.content.pm.ShortcutInfo) null);
                } else {
                    next.setShortcutInfo(com.android.server.notification.NotificationManagerService.this.mShortcutHelper.getValidShortcutInfo(next.getNotificationChannel().getConversationId(), next.getPkg(), android.os.UserHandle.of(android.os.UserHandle.getUserId(next.getUid()))));
                }
            }
            return new android.content.pm.ParceledListSlice<>(conversations);
        }

        public android.content.pm.ParceledListSlice<android.app.NotificationChannelGroup> getNotificationChannelGroupsForPackage(java.lang.String str, int i, boolean z) {
            enforceSystemOrSystemUI("getNotificationChannelGroupsForPackage");
            return com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.getNotificationChannelGroups(str, i, z, true, false, true, null);
        }

        public android.content.pm.ParceledListSlice<android.app.NotificationChannelGroup> getRecentBlockedNotificationChannelGroupsForPackage(java.lang.String str, int i) {
            java.lang.String str2;
            enforceSystemOrSystemUI("getRecentBlockedNotificationChannelGroupsForPackage");
            java.util.HashSet hashSet = new java.util.HashSet();
            long currentTimeMillis = java.lang.System.currentTimeMillis();
            android.app.usage.UsageEvents queryEventsForUser = com.android.server.notification.NotificationManagerService.this.mUsageStatsManagerInternal.queryEventsForUser(android.os.UserHandle.getUserId(i), currentTimeMillis - 1209600000, currentTimeMillis, 0);
            if (queryEventsForUser != null) {
                android.app.usage.UsageEvents.Event event = new android.app.usage.UsageEvents.Event();
                while (queryEventsForUser.hasNextEvent()) {
                    queryEventsForUser.getNextEvent(event);
                    if (event.getEventType() == 12 && str.equals(event.mPackage) && (str2 = event.mNotificationChannelId) != null) {
                        hashSet.add(str2);
                    }
                }
            }
            return com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.getNotificationChannelGroups(str, i, false, true, false, true, hashSet);
        }

        public android.content.pm.ParceledListSlice<android.service.notification.ConversationChannelWrapper> getConversationsForPackage(java.lang.String str, int i) {
            enforceSystemOrSystemUI("getConversationsForPackage");
            java.util.ArrayList<android.service.notification.ConversationChannelWrapper> conversations = com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.getConversations(str, i);
            java.util.Iterator<android.service.notification.ConversationChannelWrapper> it = conversations.iterator();
            while (it.hasNext()) {
                android.service.notification.ConversationChannelWrapper next = it.next();
                if (com.android.server.notification.NotificationManagerService.this.mShortcutHelper == null) {
                    next.setShortcutInfo((android.content.pm.ShortcutInfo) null);
                } else {
                    next.setShortcutInfo(com.android.server.notification.NotificationManagerService.this.mShortcutHelper.getValidShortcutInfo(next.getNotificationChannel().getConversationId(), str, android.os.UserHandle.of(android.os.UserHandle.getUserId(i))));
                }
            }
            return new android.content.pm.ParceledListSlice<>(conversations);
        }

        public android.app.NotificationChannelGroup getPopulatedNotificationChannelGroupForPackage(java.lang.String str, int i, java.lang.String str2, boolean z) {
            enforceSystemOrSystemUI("getPopulatedNotificationChannelGroupForPackage");
            return com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.getNotificationChannelGroupWithChannels(str, i, str2, z);
        }

        public android.app.NotificationChannelGroup getNotificationChannelGroupForPackage(java.lang.String str, java.lang.String str2, int i) {
            enforceSystemOrSystemUI("getNotificationChannelGroupForPackage");
            return com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.getNotificationChannelGroup(str, str2, i);
        }

        public android.content.pm.ParceledListSlice<android.app.NotificationChannel> getNotificationChannels(java.lang.String str, java.lang.String str2, int i) {
            int i2;
            if (canNotifyAsPackage(str, str2, i) || com.android.server.notification.NotificationManagerService.this.isCallingUidSystem()) {
                try {
                    i2 = com.android.server.notification.NotificationManagerService.this.mPackageManagerClient.getPackageUidAsUser(str2, i);
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    i2 = -1;
                }
                return com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.getNotificationChannels(str2, i2, false);
            }
            throw new java.lang.SecurityException("Pkg " + str + " cannot read channels for " + str2 + " in " + i);
        }

        public android.content.pm.ParceledListSlice<android.app.NotificationChannel> getNotificationChannelsBypassingDnd(java.lang.String str, int i) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystem();
            if (!areNotificationsEnabledForPackage(str, i)) {
                return android.content.pm.ParceledListSlice.emptyList();
            }
            return com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.getNotificationChannelsBypassingDnd(str, i);
        }

        public boolean areChannelsBypassingDnd() {
            if (android.app.Flags.modesApi()) {
                return com.android.server.notification.NotificationManagerService.this.mZenModeHelper.getConsolidatedNotificationPolicy().allowPriorityChannels() && com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.areChannelsBypassingDnd();
            }
            return com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.areChannelsBypassingDnd();
        }

        public void clearData(java.lang.String str, int i, boolean z) throws android.os.RemoteException {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystem();
            int userId = android.os.UserHandle.getUserId(i);
            com.android.server.notification.NotificationManagerService.this.cancelAllNotificationsInt(com.android.server.notification.NotificationManagerService.MY_UID, com.android.server.notification.NotificationManagerService.MY_PID, str, null, 0, 0, android.os.UserHandle.getUserId(android.os.Binder.getCallingUid()), 21);
            boolean resetPackage = com.android.server.notification.NotificationManagerService.this.mConditionProviders.resetPackage(str, userId) | false;
            android.util.ArrayMap<java.lang.Boolean, java.util.ArrayList<android.content.ComponentName>> resetComponents = com.android.server.notification.NotificationManagerService.this.mListeners.resetComponents(str, userId);
            boolean z2 = resetPackage | (resetComponents.get(true).size() > 0 || resetComponents.get(false).size() > 0);
            for (int i2 = 0; i2 < resetComponents.get(true).size(); i2++) {
                com.android.server.notification.NotificationManagerService.this.mConditionProviders.setPackageOrComponentEnabled(resetComponents.get(true).get(i2).getPackageName(), userId, false, true);
            }
            android.util.ArrayMap<java.lang.Boolean, java.util.ArrayList<android.content.ComponentName>> resetComponents2 = com.android.server.notification.NotificationManagerService.this.mAssistants.resetComponents(str, userId);
            boolean z3 = z2 | (resetComponents2.get(true).size() > 0 || resetComponents2.get(false).size() > 0);
            for (int i3 = 1; i3 < resetComponents2.get(true).size(); i3++) {
                com.android.server.notification.NotificationManagerService.this.mAssistants.setPackageOrComponentEnabled(resetComponents2.get(true).get(i3).flattenToString(), userId, true, false);
            }
            if (resetComponents2.get(true).size() > 0) {
                com.android.server.notification.NotificationManagerService.this.mConditionProviders.setPackageOrComponentEnabled(resetComponents2.get(true).get(0).getPackageName(), userId, false, true);
            }
            com.android.server.notification.NotificationManagerService.this.mSnoozeHelper.clearData(android.os.UserHandle.getUserId(i), str);
            if (!z) {
                com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.clearData(str, i);
            }
            if (z3) {
                com.android.server.notification.NotificationManagerService.this.getContext().sendBroadcastAsUser(new android.content.Intent("android.app.action.NOTIFICATION_POLICY_ACCESS_GRANTED_CHANGED").setPackage(str).addFlags(67108864), android.os.UserHandle.of(userId), null);
            }
            com.android.server.notification.NotificationManagerService.this.handleSavePolicyFile();
        }

        public java.util.List<java.lang.String> getAllowedAssistantAdjustments(java.lang.String str) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystemOrSameApp(str);
            if (!com.android.server.notification.NotificationManagerService.this.isCallerSystemOrPhone() && !com.android.server.notification.NotificationManagerService.this.mAssistants.isPackageAllowed(str, android.os.UserHandle.getCallingUserId())) {
                throw new java.lang.SecurityException("Not currently an assistant");
            }
            return com.android.server.notification.NotificationManagerService.this.mAssistants.getAllowedAssistantAdjustments();
        }

        @java.lang.Deprecated
        public android.service.notification.StatusBarNotification[] getActiveNotifications(java.lang.String str) {
            return getActiveNotificationsWithAttribution(str, null);
        }

        @android.annotation.EnforcePermission("android.permission.ACCESS_NOTIFICATIONS")
        public android.service.notification.StatusBarNotification[] getActiveNotificationsWithAttribution(java.lang.String str, java.lang.String str2) {
            getActiveNotificationsWithAttribution_enforcePermission();
            java.util.ArrayList arrayList = new java.util.ArrayList();
            int callingUid = android.os.Binder.getCallingUid();
            final java.util.ArrayList arrayList2 = new java.util.ArrayList();
            arrayList2.add(-1);
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.notification.NotificationManagerService$13$$ExternalSyntheticLambda4
                public final void runOrThrow() {
                    com.android.server.notification.NotificationManagerService.AnonymousClass13.this.lambda$getActiveNotificationsWithAttribution$0(arrayList2);
                }
            });
            if (com.android.server.notification.NotificationManagerService.this.mAppOps.noteOpNoThrow(25, callingUid, str, str2, (java.lang.String) null) == 0) {
                synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                    try {
                        int size = com.android.server.notification.NotificationManagerService.this.mNotificationList.size();
                        for (int i = 0; i < size; i++) {
                            android.service.notification.StatusBarNotification sbn = com.android.server.notification.NotificationManagerService.this.mNotificationList.get(i).getSbn();
                            if (arrayList2.contains(java.lang.Integer.valueOf(sbn.getUserId()))) {
                                arrayList.add(sbn);
                            }
                        }
                    } finally {
                    }
                }
            }
            return (android.service.notification.StatusBarNotification[]) arrayList.toArray(new android.service.notification.StatusBarNotification[arrayList.size()]);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getActiveNotificationsWithAttribution$0(java.util.ArrayList arrayList) throws java.lang.Exception {
            for (int i : com.android.server.notification.NotificationManagerService.this.mUm.getProfileIds(android.app.ActivityManager.getCurrentUser(), false)) {
                arrayList.add(java.lang.Integer.valueOf(i));
            }
        }

        public android.content.pm.ParceledListSlice<android.service.notification.StatusBarNotification> getAppActiveNotifications(java.lang.String str, int i) {
            android.content.pm.ParceledListSlice<android.service.notification.StatusBarNotification> parceledListSlice;
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystemOrSameApp(str);
            int handleIncomingUser = android.app.ActivityManager.handleIncomingUser(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, true, false, "getAppActiveNotifications", str);
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                try {
                    android.util.ArrayMap arrayMap = new android.util.ArrayMap(com.android.server.notification.NotificationManagerService.this.mNotificationList.size() + com.android.server.notification.NotificationManagerService.this.mEnqueuedNotifications.size());
                    int size = com.android.server.notification.NotificationManagerService.this.mNotificationList.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        android.service.notification.StatusBarNotification sanitizeSbn = sanitizeSbn(str, handleIncomingUser, com.android.server.notification.NotificationManagerService.this.mNotificationList.get(i2).getSbn());
                        if (sanitizeSbn != null) {
                            arrayMap.put(sanitizeSbn.getKey(), sanitizeSbn);
                        }
                    }
                    java.util.Iterator<com.android.server.notification.NotificationRecord> it = com.android.server.notification.NotificationManagerService.this.mSnoozeHelper.getSnoozed(handleIncomingUser, str).iterator();
                    while (it.hasNext()) {
                        android.service.notification.StatusBarNotification sanitizeSbn2 = sanitizeSbn(str, handleIncomingUser, it.next().getSbn());
                        if (sanitizeSbn2 != null) {
                            arrayMap.put(sanitizeSbn2.getKey(), sanitizeSbn2);
                        }
                    }
                    int size2 = com.android.server.notification.NotificationManagerService.this.mEnqueuedNotifications.size();
                    for (int i3 = 0; i3 < size2; i3++) {
                        android.service.notification.StatusBarNotification sanitizeSbn3 = sanitizeSbn(str, handleIncomingUser, com.android.server.notification.NotificationManagerService.this.mEnqueuedNotifications.get(i3).getSbn());
                        if (sanitizeSbn3 != null) {
                            arrayMap.put(sanitizeSbn3.getKey(), sanitizeSbn3);
                        }
                    }
                    java.util.ArrayList arrayList = new java.util.ArrayList(arrayMap.size());
                    arrayList.addAll(arrayMap.values());
                    parceledListSlice = new android.content.pm.ParceledListSlice<>(arrayList);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return parceledListSlice;
        }

        private android.service.notification.StatusBarNotification sanitizeSbn(java.lang.String str, int i, android.service.notification.StatusBarNotification statusBarNotification) {
            if (statusBarNotification.getUserId() != i || (!statusBarNotification.getPackageName().equals(str) && !statusBarNotification.getOpPkg().equals(str))) {
                return null;
            }
            android.app.Notification clone = statusBarNotification.getNotification().clone();
            clone.overrideAllowlistToken(null);
            return new android.service.notification.StatusBarNotification(statusBarNotification.getPackageName(), statusBarNotification.getOpPkg(), statusBarNotification.getId(), statusBarNotification.getTag(), statusBarNotification.getUid(), statusBarNotification.getInitialPid(), clone, statusBarNotification.getUser(), statusBarNotification.getOverrideGroupKey(), statusBarNotification.getPostTime());
        }

        @android.annotation.RequiresPermission("android.permission.ACCESS_NOTIFICATIONS")
        @java.lang.Deprecated
        public android.service.notification.StatusBarNotification[] getHistoricalNotifications(java.lang.String str, int i, boolean z) {
            return getHistoricalNotificationsWithAttribution(str, null, i, z);
        }

        @android.annotation.EnforcePermission("android.permission.ACCESS_NOTIFICATIONS")
        @android.annotation.RequiresPermission("android.permission.ACCESS_NOTIFICATIONS")
        public android.service.notification.StatusBarNotification[] getHistoricalNotificationsWithAttribution(java.lang.String str, java.lang.String str2, int i, boolean z) {
            android.service.notification.StatusBarNotification[] array;
            getHistoricalNotificationsWithAttribution_enforcePermission();
            if (com.android.server.notification.NotificationManagerService.this.mAppOps.noteOpNoThrow(25, android.os.Binder.getCallingUid(), str, str2, (java.lang.String) null) != 0) {
                return null;
            }
            synchronized (com.android.server.notification.NotificationManagerService.this.mArchive) {
                array = com.android.server.notification.NotificationManagerService.this.mArchive.getArray(com.android.server.notification.NotificationManagerService.this.mUm, i, z);
            }
            return array;
        }

        @android.annotation.EnforcePermission("android.permission.ACCESS_NOTIFICATIONS")
        @android.annotation.RequiresPermission("android.permission.ACCESS_NOTIFICATIONS")
        public android.app.NotificationHistory getNotificationHistory(java.lang.String str, java.lang.String str2) {
            getNotificationHistory_enforcePermission();
            if (com.android.server.notification.NotificationManagerService.this.mAppOps.noteOpNoThrow(25, android.os.Binder.getCallingUid(), str, str2, (java.lang.String) null) == 0) {
                android.util.IntArray currentProfileIds = com.android.server.notification.NotificationManagerService.this.mUserProfiles.getCurrentProfileIds();
                android.os.Trace.traceBegin(524288L, "notifHistoryReadHistory");
                try {
                    return com.android.server.notification.NotificationManagerService.this.mHistoryManager.readNotificationHistory(currentProfileIds.toArray());
                } finally {
                    android.os.Trace.traceEnd(524288L);
                }
            }
            return new android.app.NotificationHistory();
        }

        @android.annotation.EnforcePermission(allOf = {"android.permission.INTERACT_ACROSS_USERS", "android.permission.ACCESS_NOTIFICATIONS"})
        public void registerCallNotificationEventListener(java.lang.String str, android.os.UserHandle userHandle, android.app.ICallNotificationEventCallback iCallNotificationEventCallback) {
            registerCallNotificationEventListener_enforcePermission();
            int identifier = userHandle.getIdentifier() != -2 ? userHandle.getIdentifier() : com.android.server.notification.NotificationManagerService.this.mAmi.getCurrentUserId();
            synchronized (com.android.server.notification.NotificationManagerService.this.mCallNotificationEventCallbacks) {
                android.util.ArrayMap<java.lang.Integer, android.os.RemoteCallbackList<android.app.ICallNotificationEventCallback>> orDefault = com.android.server.notification.NotificationManagerService.this.mCallNotificationEventCallbacks.getOrDefault(str, new android.util.ArrayMap<>());
                android.os.RemoteCallbackList<android.app.ICallNotificationEventCallback> orDefault2 = orDefault.getOrDefault(java.lang.Integer.valueOf(identifier), new android.os.RemoteCallbackList<>());
                if (orDefault2.register(iCallNotificationEventCallback)) {
                    orDefault.put(java.lang.Integer.valueOf(identifier), orDefault2);
                    com.android.server.notification.NotificationManagerService.this.mCallNotificationEventCallbacks.put(str, orDefault);
                    synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                        java.util.Iterator<com.android.server.notification.NotificationRecord> it = com.android.server.notification.NotificationManagerService.this.mNotificationList.iterator();
                        while (it.hasNext()) {
                            com.android.server.notification.NotificationRecord next = it.next();
                            if (next.getNotification().isStyle(android.app.Notification.CallStyle.class) && com.android.server.notification.NotificationManagerService.notificationMatchesUserId(next, identifier, false) && next.getSbn().getPackageName().equals(str)) {
                                try {
                                    iCallNotificationEventCallback.onCallNotificationPosted(str, next.getUser());
                                } catch (android.os.RemoteException e) {
                                    throw new java.lang.RuntimeException(e);
                                }
                            }
                        }
                    }
                    return;
                }
                android.util.Log.e(com.android.server.notification.NotificationManagerService.TAG, "registerCallNotificationEventListener failed to register listener: " + str + " " + userHandle + " " + iCallNotificationEventCallback);
            }
        }

        @android.annotation.EnforcePermission(allOf = {"android.permission.INTERACT_ACROSS_USERS", "android.permission.ACCESS_NOTIFICATIONS"})
        public void unregisterCallNotificationEventListener(java.lang.String str, android.os.UserHandle userHandle, android.app.ICallNotificationEventCallback iCallNotificationEventCallback) {
            unregisterCallNotificationEventListener_enforcePermission();
            synchronized (com.android.server.notification.NotificationManagerService.this.mCallNotificationEventCallbacks) {
                try {
                    int identifier = userHandle.getIdentifier() != -2 ? userHandle.getIdentifier() : com.android.server.notification.NotificationManagerService.this.mAmi.getCurrentUserId();
                    android.util.ArrayMap<java.lang.Integer, android.os.RemoteCallbackList<android.app.ICallNotificationEventCallback>> arrayMap = com.android.server.notification.NotificationManagerService.this.mCallNotificationEventCallbacks.get(str);
                    if (arrayMap == null) {
                        return;
                    }
                    android.os.RemoteCallbackList<android.app.ICallNotificationEventCallback> remoteCallbackList = arrayMap.get(java.lang.Integer.valueOf(identifier));
                    if (remoteCallbackList == null) {
                        return;
                    }
                    if (!remoteCallbackList.unregister(iCallNotificationEventCallback)) {
                        android.util.Log.e(com.android.server.notification.NotificationManagerService.TAG, "unregisterCallNotificationEventListener listener not found for: " + str + " " + userHandle + " " + iCallNotificationEventCallback);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void registerListener(android.service.notification.INotificationListener iNotificationListener, android.content.ComponentName componentName, int i) {
            enforceSystemOrSystemUI("INotificationManager.registerListener");
            com.android.server.notification.NotificationManagerService.this.mListeners.registerSystemService(iNotificationListener, componentName, i, android.os.Binder.getCallingUid());
        }

        public void unregisterListener(android.service.notification.INotificationListener iNotificationListener, int i) {
            com.android.server.notification.NotificationManagerService.this.mListeners.unregisterService((android.os.IInterface) iNotificationListener, i);
        }

        public void cancelNotificationsFromListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String[] strArr) {
            com.android.server.notification.ManagedServices.ManagedServiceInfo checkServiceTokenLocked;
            java.lang.String packageName;
            java.lang.Object obj;
            java.lang.String str;
            boolean z;
            int userId;
            int i;
            boolean z2;
            int i2;
            int i3;
            java.lang.Object obj2;
            int i4;
            java.lang.String str2;
            com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo;
            java.lang.String[] strArr2 = strArr;
            int callingUid = android.os.Binder.getCallingUid();
            int callingPid = android.os.Binder.getCallingPid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                    checkServiceTokenLocked = com.android.server.notification.NotificationManagerService.this.mListeners.checkServiceTokenLocked(iNotificationListener);
                    packageName = checkServiceTokenLocked.component.getPackageName();
                }
                boolean z3 = false;
                int packageImportanceWithIdentity = android.app.Flags.lifetimeExtensionRefactor() ? com.android.server.notification.NotificationManagerService.this.getPackageImportanceWithIdentity(packageName) : 0;
                java.lang.Object obj3 = com.android.server.notification.NotificationManagerService.this.mNotificationLock;
                try {
                    synchronized (obj3) {
                        try {
                            int i5 = com.android.server.notification.NotificationManagerService.this.mAssistants.isServiceTokenValidLocked(iNotificationListener) ? 22 : 10;
                            if (strArr2 != null) {
                                int length = strArr2.length;
                                z = false;
                                int i6 = 0;
                                while (i6 < length) {
                                    com.android.server.notification.NotificationRecord notificationRecord = com.android.server.notification.NotificationManagerService.this.mNotificationsByKey.get(strArr2[i6]);
                                    if (notificationRecord != null && ((userId = notificationRecord.getSbn().getUserId()) == checkServiceTokenLocked.userid || userId == -1 || com.android.server.notification.NotificationManagerService.this.mUserProfiles.isCurrentProfile(userId))) {
                                        if (z) {
                                            i = userId;
                                        } else {
                                            i = userId;
                                            if (!com.android.server.notification.NotificationManagerService.this.isNotificationRecent(notificationRecord.getUpdateTimeMs())) {
                                                z2 = false;
                                                int i7 = i;
                                                i2 = i6;
                                                i3 = length;
                                                obj2 = obj3;
                                                i4 = packageImportanceWithIdentity;
                                                str2 = packageName;
                                                managedServiceInfo = checkServiceTokenLocked;
                                                cancelNotificationFromListenerLocked(checkServiceTokenLocked, callingUid, callingPid, notificationRecord.getSbn().getPackageName(), notificationRecord.getSbn().getTag(), notificationRecord.getSbn().getId(), i7, i5, i4);
                                                z = z2;
                                                i6 = i2 + 1;
                                                packageImportanceWithIdentity = i4;
                                                checkServiceTokenLocked = managedServiceInfo;
                                                length = i3;
                                                obj3 = obj2;
                                                packageName = str2;
                                                strArr2 = strArr;
                                            }
                                        }
                                        z2 = true;
                                        int i72 = i;
                                        i2 = i6;
                                        i3 = length;
                                        obj2 = obj3;
                                        i4 = packageImportanceWithIdentity;
                                        str2 = packageName;
                                        managedServiceInfo = checkServiceTokenLocked;
                                        cancelNotificationFromListenerLocked(checkServiceTokenLocked, callingUid, callingPid, notificationRecord.getSbn().getPackageName(), notificationRecord.getSbn().getTag(), notificationRecord.getSbn().getId(), i72, i5, i4);
                                        z = z2;
                                        i6 = i2 + 1;
                                        packageImportanceWithIdentity = i4;
                                        checkServiceTokenLocked = managedServiceInfo;
                                        length = i3;
                                        obj3 = obj2;
                                        packageName = str2;
                                        strArr2 = strArr;
                                    }
                                    i2 = i6;
                                    i3 = length;
                                    obj2 = obj3;
                                    i4 = packageImportanceWithIdentity;
                                    str2 = packageName;
                                    managedServiceInfo = checkServiceTokenLocked;
                                    i6 = i2 + 1;
                                    packageImportanceWithIdentity = i4;
                                    checkServiceTokenLocked = managedServiceInfo;
                                    length = i3;
                                    obj3 = obj2;
                                    packageName = str2;
                                    strArr2 = strArr;
                                }
                                obj = obj3;
                                str = packageName;
                            } else {
                                obj = obj3;
                                int i8 = packageImportanceWithIdentity;
                                str = packageName;
                                java.util.Iterator<com.android.server.notification.NotificationRecord> it = com.android.server.notification.NotificationManagerService.this.mNotificationList.iterator();
                                while (true) {
                                    if (!it.hasNext()) {
                                        break;
                                    } else if (com.android.server.notification.NotificationManagerService.this.isNotificationRecent(it.next().getUpdateTimeMs())) {
                                        z3 = true;
                                        break;
                                    }
                                }
                                if (android.app.Flags.lifetimeExtensionRefactor()) {
                                    com.android.server.notification.NotificationManagerService.this.cancelAllLocked(callingUid, callingPid, checkServiceTokenLocked.userid, 11, checkServiceTokenLocked, checkServiceTokenLocked.supportsProfiles(), 65570);
                                    com.android.server.notification.NotificationManagerService.this.notifySystemUiListenerLifetimeExtendedListLocked(com.android.server.notification.NotificationManagerService.this.mNotificationList, i8);
                                    com.android.server.notification.NotificationManagerService.this.notifySystemUiListenerLifetimeExtendedListLocked(com.android.server.notification.NotificationManagerService.this.mEnqueuedNotifications, i8);
                                } else {
                                    com.android.server.notification.NotificationManagerService.this.cancelAllLocked(callingUid, callingPid, checkServiceTokenLocked.userid, 11, checkServiceTokenLocked, checkServiceTokenLocked.supportsProfiles(), 34);
                                }
                                z = z3;
                            }
                            if (z) {
                                com.android.server.notification.NotificationManagerService.this.mAppOps.noteOpNoThrow(142, callingUid, str, (java.lang.String) null, (java.lang.String) null);
                            }
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        } catch (java.lang.Throwable th) {
                            th = th;
                            java.lang.Object obj4 = obj3;
                            throw th;
                        }
                    }
                } catch (java.lang.Throwable th2) {
                    th = th2;
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void requestBindListener(android.content.ComponentName componentName) {
            com.android.server.notification.ManagedServices managedServices;
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystemOrSameApp(componentName.getPackageName());
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (com.android.server.notification.NotificationManagerService.this.mAssistants.isComponentEnabledForCurrentProfiles(componentName)) {
                    managedServices = com.android.server.notification.NotificationManagerService.this.mAssistants;
                } else {
                    managedServices = com.android.server.notification.NotificationManagerService.this.mListeners;
                }
                managedServices.setComponentState(componentName, android.os.UserHandle.getUserId(callingUid), true);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public void requestUnbindListener(android.service.notification.INotificationListener iNotificationListener) {
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                    com.android.server.notification.ManagedServices.ManagedServiceInfo checkServiceTokenLocked = com.android.server.notification.NotificationManagerService.this.mListeners.checkServiceTokenLocked(iNotificationListener);
                    checkServiceTokenLocked.getOwner().setComponentState(checkServiceTokenLocked.component, android.os.UserHandle.getUserId(callingUid), false);
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void requestUnbindListenerComponent(android.content.ComponentName componentName) {
            com.android.server.notification.ManagedServices managedServices;
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSameApp(componentName.getPackageName());
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                    try {
                        if (com.android.server.notification.NotificationManagerService.this.mAssistants.isComponentEnabledForCurrentProfiles(componentName)) {
                            managedServices = com.android.server.notification.NotificationManagerService.this.mAssistants;
                        } else {
                            managedServices = com.android.server.notification.NotificationManagerService.this.mListeners;
                        }
                        if (managedServices.isPackageOrComponentAllowed(componentName.flattenToString(), android.os.UserHandle.getUserId(callingUid))) {
                            managedServices.setComponentState(componentName, android.os.UserHandle.getUserId(callingUid), false);
                        }
                    } finally {
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setNotificationsShownFromListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String[] strArr) {
            int userId;
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                    com.android.server.notification.ManagedServices.ManagedServiceInfo checkServiceTokenLocked = com.android.server.notification.NotificationManagerService.this.mListeners.checkServiceTokenLocked(iNotificationListener);
                    if (strArr == null) {
                        return;
                    }
                    java.util.ArrayList<com.android.server.notification.NotificationRecord> arrayList = new java.util.ArrayList<>();
                    int length = strArr.length;
                    for (int i = 0; i < length; i++) {
                        com.android.server.notification.NotificationRecord notificationRecord = com.android.server.notification.NotificationManagerService.this.mNotificationsByKey.get(strArr[i]);
                        if (notificationRecord != null && ((userId = notificationRecord.getSbn().getUserId()) == checkServiceTokenLocked.userid || userId == -1 || com.android.server.notification.NotificationManagerService.this.mUserProfiles.isCurrentProfile(userId))) {
                            arrayList.add(notificationRecord);
                            if (!notificationRecord.isSeen()) {
                                if (com.android.server.notification.NotificationManagerService.DBG) {
                                    android.util.Slog.d(com.android.server.notification.NotificationManagerService.TAG, "Marking notification as seen " + strArr[i]);
                                }
                                com.android.server.notification.NotificationManagerService.this.reportSeen(notificationRecord);
                                notificationRecord.setSeen();
                                com.android.server.notification.NotificationManagerService.this.maybeRecordInterruptionLocked(notificationRecord);
                            }
                        }
                    }
                    if (!arrayList.isEmpty()) {
                        com.android.server.notification.NotificationManagerService.this.mAssistants.onNotificationsSeenLocked(arrayList);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
        private void cancelNotificationFromListenerLocked(com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo, int i, int i2, java.lang.String str, java.lang.String str2, int i3, int i4, int i5, int i6) {
            int i7;
            if (android.app.Flags.lifetimeExtensionRefactor()) {
                com.android.server.notification.NotificationManagerService.this.maybeNotifySystemUiListenerLifetimeExtendedLocked(com.android.server.notification.NotificationManagerService.this.findNotificationLocked(str, str2, i3, i4), str, i6);
                i7 = 65538;
            } else {
                i7 = 2;
            }
            com.android.server.notification.NotificationManagerService.this.cancelNotification(i, i2, str, str2, i3, 0, i7, true, i4, i5, managedServiceInfo);
        }

        public void snoozeNotificationUntilContextFromListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String str, java.lang.String str2) {
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.notification.NotificationManagerService.this.snoozeNotificationInt(callingUid, iNotificationListener, str, -1L, str2);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void snoozeNotificationUntilFromListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String str, long j) {
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.notification.NotificationManagerService.this.snoozeNotificationInt(callingUid, iNotificationListener, str, j, null);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void unsnoozeNotificationFromAssistant(android.service.notification.INotificationListener iNotificationListener, java.lang.String str) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                    com.android.server.notification.NotificationManagerService.this.unsnoozeNotificationInt(str, com.android.server.notification.NotificationManagerService.this.mAssistants.checkServiceTokenLocked(iNotificationListener), false);
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void unsnoozeNotificationFromSystemListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String str) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                    com.android.server.notification.ManagedServices.ManagedServiceInfo checkServiceTokenLocked = com.android.server.notification.NotificationManagerService.this.mListeners.checkServiceTokenLocked(iNotificationListener);
                    if (!checkServiceTokenLocked.isSystem) {
                        throw new java.lang.SecurityException("Not allowed to unsnooze before deadline");
                    }
                    com.android.server.notification.NotificationManagerService.this.unsnoozeNotificationInt(str, checkServiceTokenLocked, true);
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void migrateNotificationFilter(android.service.notification.INotificationListener iNotificationListener, int i, java.util.List<java.lang.String> list) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                    try {
                        com.android.server.notification.ManagedServices.ManagedServiceInfo checkServiceTokenLocked = com.android.server.notification.NotificationManagerService.this.mListeners.checkServiceTokenLocked(iNotificationListener);
                        android.util.Pair<android.content.ComponentName, java.lang.Integer> create = android.util.Pair.create(checkServiceTokenLocked.component, java.lang.Integer.valueOf(checkServiceTokenLocked.userid));
                        android.service.notification.NotificationListenerFilter notificationListenerFilter = com.android.server.notification.NotificationManagerService.this.mListeners.getNotificationListenerFilter(create);
                        if (notificationListenerFilter == null) {
                            notificationListenerFilter = new android.service.notification.NotificationListenerFilter();
                        }
                        if (notificationListenerFilter.getDisallowedPackages().isEmpty() && list != null) {
                            for (java.lang.String str : list) {
                                for (int i2 : com.android.server.notification.NotificationManagerService.this.mUm.getProfileIds(checkServiceTokenLocked.userid, false)) {
                                    try {
                                        int uidForPackageAndUser = getUidForPackageAndUser(str, android.os.UserHandle.of(i2));
                                        if (uidForPackageAndUser != -1) {
                                            notificationListenerFilter.addPackage(new android.content.pm.VersionedPackage(str, uidForPackageAndUser));
                                        }
                                    } catch (java.lang.Exception e) {
                                    }
                                }
                            }
                        }
                        if (notificationListenerFilter.areAllTypesAllowed()) {
                            notificationListenerFilter.setTypes(i);
                        }
                        com.android.server.notification.NotificationManagerService.this.mListeners.setNotificationListenerFilter(create, notificationListenerFilter);
                    } finally {
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void cancelNotificationFromListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String str, java.lang.String str2, int i) {
            int i2;
            int callingUid = android.os.Binder.getCallingUid();
            int callingPid = android.os.Binder.getCallingPid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                int packageImportanceWithIdentity = android.app.Flags.lifetimeExtensionRefactor() ? com.android.server.notification.NotificationManagerService.this.getPackageImportanceWithIdentity(str) : 0;
                synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                    try {
                        com.android.server.notification.ManagedServices.ManagedServiceInfo checkServiceTokenLocked = com.android.server.notification.NotificationManagerService.this.mListeners.checkServiceTokenLocked(iNotificationListener);
                        if (!com.android.server.notification.NotificationManagerService.this.mAssistants.isServiceTokenValidLocked(iNotificationListener)) {
                            i2 = 10;
                        } else {
                            i2 = 22;
                        }
                        if (checkServiceTokenLocked.supportsProfiles()) {
                            android.util.Slog.e(com.android.server.notification.NotificationManagerService.TAG, "Ignoring deprecated cancelNotification(pkg, tag, id) from " + checkServiceTokenLocked.component + " use cancelNotification(key) instead.");
                        } else {
                            cancelNotificationFromListenerLocked(checkServiceTokenLocked, callingUid, callingPid, str, str2, i, checkServiceTokenLocked.userid, i2, packageImportanceWithIdentity);
                        }
                    } finally {
                    }
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public android.content.pm.ParceledListSlice<android.service.notification.StatusBarNotification> getActiveNotificationsFromListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String[] strArr, int i) {
            android.content.pm.ParceledListSlice<android.service.notification.StatusBarNotification> parceledListSlice;
            com.android.server.notification.NotificationRecord notificationRecord;
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                try {
                    com.android.server.notification.ManagedServices.ManagedServiceInfo checkServiceTokenLocked = com.android.server.notification.NotificationManagerService.this.mListeners.checkServiceTokenLocked(iNotificationListener);
                    boolean z = strArr != null;
                    int length = z ? strArr.length : com.android.server.notification.NotificationManagerService.this.mNotificationList.size();
                    java.util.ArrayList<android.service.notification.StatusBarNotification> arrayList = new java.util.ArrayList<>(length);
                    for (int i2 = 0; i2 < length; i2++) {
                        if (z) {
                            notificationRecord = com.android.server.notification.NotificationManagerService.this.mNotificationsByKey.get(strArr[i2]);
                        } else {
                            notificationRecord = com.android.server.notification.NotificationManagerService.this.mNotificationList.get(i2);
                        }
                        addToListIfNeeded(notificationRecord, checkServiceTokenLocked, arrayList, i);
                    }
                    parceledListSlice = new android.content.pm.ParceledListSlice<>(arrayList);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return parceledListSlice;
        }

        public android.content.pm.ParceledListSlice<android.service.notification.StatusBarNotification> getSnoozedNotificationsFromListener(android.service.notification.INotificationListener iNotificationListener, int i) {
            android.content.pm.ParceledListSlice<android.service.notification.StatusBarNotification> parceledListSlice;
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                try {
                    com.android.server.notification.ManagedServices.ManagedServiceInfo checkServiceTokenLocked = com.android.server.notification.NotificationManagerService.this.mListeners.checkServiceTokenLocked(iNotificationListener);
                    java.util.List<com.android.server.notification.NotificationRecord> snoozed = com.android.server.notification.NotificationManagerService.this.mSnoozeHelper.getSnoozed();
                    int size = snoozed.size();
                    java.util.ArrayList<android.service.notification.StatusBarNotification> arrayList = new java.util.ArrayList<>(size);
                    for (int i2 = 0; i2 < size; i2++) {
                        addToListIfNeeded(snoozed.get(i2), checkServiceTokenLocked, arrayList, i);
                    }
                    parceledListSlice = new android.content.pm.ParceledListSlice<>(arrayList);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return parceledListSlice;
        }

        private void addToListIfNeeded(com.android.server.notification.NotificationRecord notificationRecord, com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo, java.util.ArrayList<android.service.notification.StatusBarNotification> arrayList, int i) {
            if (notificationRecord == null) {
                return;
            }
            android.service.notification.StatusBarNotification sbn = notificationRecord.getSbn();
            if (com.android.server.notification.NotificationManagerService.this.isVisibleToListener(sbn, notificationRecord.getNotificationType(), managedServiceInfo)) {
                if (com.android.server.notification.NotificationManagerService.this.mListeners.hasSensitiveContent(notificationRecord) && !com.android.server.notification.NotificationManagerService.this.mListeners.isUidTrusted(managedServiceInfo.uid)) {
                    arrayList.add(com.android.server.notification.NotificationManagerService.this.mListeners.redactStatusBarNotification(sbn));
                    return;
                }
                if (i != 0) {
                    sbn = sbn.cloneLight();
                }
                arrayList.add(sbn);
            }
        }

        public void clearRequestedListenerHints(android.service.notification.INotificationListener iNotificationListener) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                    com.android.server.notification.NotificationManagerService.this.removeDisabledHints(com.android.server.notification.NotificationManagerService.this.mListeners.checkServiceTokenLocked(iNotificationListener));
                    com.android.server.notification.NotificationManagerService.this.updateListenerHintsLocked();
                    com.android.server.notification.NotificationManagerService.this.updateEffectsSuppressorLocked();
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void requestHintsFromListener(android.service.notification.INotificationListener iNotificationListener, int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                    try {
                        com.android.server.notification.ManagedServices.ManagedServiceInfo checkServiceTokenLocked = com.android.server.notification.NotificationManagerService.this.mListeners.checkServiceTokenLocked(iNotificationListener);
                        if ((i & 7) != 0) {
                            com.android.server.notification.NotificationManagerService.this.addDisabledHints(checkServiceTokenLocked, i);
                        } else {
                            com.android.server.notification.NotificationManagerService.this.removeDisabledHints(checkServiceTokenLocked, i);
                        }
                        com.android.server.notification.NotificationManagerService.this.updateListenerHintsLocked();
                        com.android.server.notification.NotificationManagerService.this.updateEffectsSuppressorLocked();
                    } finally {
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int getHintsFromListener(android.service.notification.INotificationListener iNotificationListener) {
            int i;
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                i = com.android.server.notification.NotificationManagerService.this.mListenerHints;
            }
            return i;
        }

        public int getHintsFromListenerNoToken() {
            int i;
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                i = com.android.server.notification.NotificationManagerService.this.mListenerHints;
            }
            return i;
        }

        public void requestInterruptionFilterFromListener(android.service.notification.INotificationListener iNotificationListener, int i) throws android.os.RemoteException {
            final com.android.server.notification.ManagedServices.ManagedServiceInfo checkServiceTokenLocked;
            if (android.app.Flags.modesApi()) {
                final int callingUid = android.os.Binder.getCallingUid();
                synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                    checkServiceTokenLocked = com.android.server.notification.NotificationManagerService.this.mListeners.checkServiceTokenLocked(iNotificationListener);
                }
                final int zenModeFromInterruptionFilter = android.app.NotificationManager.zenModeFromInterruptionFilter(i, -1);
                if (zenModeFromInterruptionFilter == -1) {
                    return;
                }
                if (!canManageGlobalZenPolicy(checkServiceTokenLocked.component.getPackageName(), callingUid)) {
                    com.android.server.notification.NotificationManagerService.this.mZenModeHelper.applyGlobalZenModeAsImplicitZenRule(checkServiceTokenLocked.component.getPackageName(), callingUid, zenModeFromInterruptionFilter);
                    return;
                } else {
                    final int computeZenOrigin = computeZenOrigin(false);
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.notification.NotificationManagerService$13$$ExternalSyntheticLambda3
                        public final void runOrThrow() {
                            com.android.server.notification.NotificationManagerService.AnonymousClass13.this.lambda$requestInterruptionFilterFromListener$1(zenModeFromInterruptionFilter, computeZenOrigin, checkServiceTokenLocked, callingUid);
                        }
                    });
                    return;
                }
            }
            int callingUid2 = android.os.Binder.getCallingUid();
            boolean isCallerSystemOrSystemUi = com.android.server.notification.NotificationManagerService.this.isCallerSystemOrSystemUi();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                    com.android.server.notification.NotificationManagerService.this.mZenModeHelper.requestFromListener(com.android.server.notification.NotificationManagerService.this.mListeners.checkServiceTokenLocked(iNotificationListener).component, i, callingUid2, isCallerSystemOrSystemUi);
                    com.android.server.notification.NotificationManagerService.this.updateInterruptionFilterLocked();
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestInterruptionFilterFromListener$1(int i, int i2, com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo, int i3) throws java.lang.Exception {
            com.android.server.notification.NotificationManagerService.this.mZenModeHelper.setManualZenMode(i, null, i2, "listener:" + managedServiceInfo.component.flattenToShortString(), managedServiceInfo.component.getPackageName(), i3);
        }

        public int getInterruptionFilterFromListener(android.service.notification.INotificationListener iNotificationListener) throws android.os.RemoteException {
            int i;
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                i = com.android.server.notification.NotificationManagerService.this.mInterruptionFilter;
            }
            return i;
        }

        public void setOnNotificationPostedTrimFromListener(android.service.notification.INotificationListener iNotificationListener, int i) throws android.os.RemoteException {
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                try {
                    com.android.server.notification.ManagedServices.ManagedServiceInfo checkServiceTokenLocked = com.android.server.notification.NotificationManagerService.this.mListeners.checkServiceTokenLocked(iNotificationListener);
                    if (checkServiceTokenLocked == null) {
                        return;
                    }
                    com.android.server.notification.NotificationManagerService.this.mListeners.setOnNotificationPostedTrimLocked(checkServiceTokenLocked, i);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public int getZenMode() {
            return com.android.server.notification.NotificationManagerService.this.mZenModeHelper.getZenMode();
        }

        public android.service.notification.ZenModeConfig getZenModeConfig() {
            enforceSystemOrSystemUI("INotificationManager.getZenModeConfig");
            return com.android.server.notification.NotificationManagerService.this.mZenModeHelper.getConfig();
        }

        public void setZenMode(int i, android.net.Uri uri, java.lang.String str, boolean z) {
            enforceSystemOrSystemUI("INotificationManager.setZenMode");
            enforceUserOriginOnlyFromSystem(z, "setZenMode");
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.notification.NotificationManagerService.this.mZenModeHelper.setManualZenMode(i, uri, computeZenOrigin(z), str, null, callingUid);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public java.util.List<android.service.notification.ZenModeConfig.ZenRule> getZenRules() throws android.os.RemoteException {
            enforcePolicyAccess(android.os.Binder.getCallingUid(), "getZenRules");
            return com.android.server.notification.NotificationManagerService.this.mZenModeHelper.getZenRules();
        }

        public java.util.Map<java.lang.String, android.app.AutomaticZenRule> getAutomaticZenRules() {
            if (!android.app.Flags.modesApi()) {
                throw new java.lang.IllegalStateException("getAutomaticZenRules called with flag off!");
            }
            enforcePolicyAccess(android.os.Binder.getCallingUid(), "getAutomaticZenRules");
            return com.android.server.notification.NotificationManagerService.this.mZenModeHelper.getAutomaticZenRules();
        }

        public android.app.AutomaticZenRule getAutomaticZenRule(java.lang.String str) throws android.os.RemoteException {
            java.util.Objects.requireNonNull(str, "Id is null");
            enforcePolicyAccess(android.os.Binder.getCallingUid(), "getAutomaticZenRule");
            return com.android.server.notification.NotificationManagerService.this.mZenModeHelper.getAutomaticZenRule(str);
        }

        public java.lang.String addAutomaticZenRule(android.app.AutomaticZenRule automaticZenRule, java.lang.String str, boolean z) {
            java.lang.String str2;
            validateAutomaticZenRule(null, automaticZenRule);
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSameApp(str);
            if (automaticZenRule.getZenPolicy() != null && automaticZenRule.getInterruptionFilter() != 2) {
                throw new java.lang.IllegalArgumentException("ZenPolicy is only applicable to INTERRUPTION_FILTER_PRIORITY filters");
            }
            enforcePolicyAccess(android.os.Binder.getCallingUid(), "addAutomaticZenRule");
            enforceUserOriginOnlyFromSystem(z, "addAutomaticZenRule");
            if (com.android.server.notification.NotificationManagerService.this.isCallingAppIdSystem() && automaticZenRule.getOwner() != null) {
                str2 = automaticZenRule.getOwner().getPackageName();
            } else {
                str2 = str;
            }
            return com.android.server.notification.NotificationManagerService.this.mZenModeHelper.addAutomaticZenRule(str2, automaticZenRule, computeZenOrigin(z), "addAutomaticZenRule", android.os.Binder.getCallingUid());
        }

        public boolean updateAutomaticZenRule(java.lang.String str, android.app.AutomaticZenRule automaticZenRule, boolean z) throws android.os.RemoteException {
            validateAutomaticZenRule(str, automaticZenRule);
            enforcePolicyAccess(android.os.Binder.getCallingUid(), "updateAutomaticZenRule");
            enforceUserOriginOnlyFromSystem(z, "updateAutomaticZenRule");
            return com.android.server.notification.NotificationManagerService.this.mZenModeHelper.updateAutomaticZenRule(str, automaticZenRule, computeZenOrigin(z), "updateAutomaticZenRule", android.os.Binder.getCallingUid());
        }

        private void validateAutomaticZenRule(@android.annotation.Nullable java.lang.String str, android.app.AutomaticZenRule automaticZenRule) {
            java.util.Objects.requireNonNull(automaticZenRule, "automaticZenRule is null");
            java.util.Objects.requireNonNull(automaticZenRule.getName(), "Name is null");
            automaticZenRule.validate();
            boolean z = false;
            if (android.app.Flags.modesApi()) {
                if (!(str != null && com.android.server.notification.ZenModeHelper.isImplicitRuleId(str) && com.android.server.notification.NotificationManagerService.this.isCallerSystemOrSystemUi()) && automaticZenRule.getOwner() == null && automaticZenRule.getConfigurationActivity() == null) {
                    throw new java.lang.NullPointerException("Rule must have a ConditionProviderService and/or configuration activity");
                }
            } else if (automaticZenRule.getOwner() == null && automaticZenRule.getConfigurationActivity() == null) {
                throw new java.lang.NullPointerException("Rule must have a ConditionProviderService and/or configuration activity");
            }
            java.util.Objects.requireNonNull(automaticZenRule.getConditionId(), "ConditionId is null");
            if (!android.app.Flags.modesApi() || com.android.server.notification.NotificationManagerService.this.isCallerSystemOrSystemUi()) {
                return;
            }
            final int callingUid = android.os.Binder.getCallingUid();
            int userId = android.os.UserHandle.getUserId(callingUid);
            if (automaticZenRule.getType() == 7) {
                if (!((java.lang.Boolean) android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.notification.NotificationManagerService$13$$ExternalSyntheticLambda1
                    public final java.lang.Object getOrThrow() {
                        java.lang.Boolean lambda$validateAutomaticZenRule$2;
                        lambda$validateAutomaticZenRule$2 = com.android.server.notification.NotificationManagerService.AnonymousClass13.this.lambda$validateAutomaticZenRule$2(callingUid);
                        return lambda$validateAutomaticZenRule$2;
                    }
                })).booleanValue()) {
                    throw new java.lang.IllegalArgumentException("Only Device Owners can use AutomaticZenRules with TYPE_MANAGED");
                }
            } else if (automaticZenRule.getType() == 3) {
                java.lang.String string = com.android.server.notification.NotificationManagerService.this.getContext().getResources().getString(android.R.string.config_systemWellbeing);
                if (!android.text.TextUtils.isEmpty(string) && com.android.server.notification.NotificationManagerService.this.mPackageManagerInternal.isSameApp(string, callingUid, userId)) {
                    z = true;
                }
                if (!z) {
                    throw new java.lang.IllegalArgumentException("Only the 'Wellbeing' package can use AutomaticZenRules with TYPE_BEDTIME");
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.Boolean lambda$validateAutomaticZenRule$2(int i) throws java.lang.Exception {
            return java.lang.Boolean.valueOf(com.android.server.notification.NotificationManagerService.this.mDpm.isActiveDeviceOwner(i));
        }

        public boolean removeAutomaticZenRule(java.lang.String str, boolean z) throws android.os.RemoteException {
            java.util.Objects.requireNonNull(str, "Id is null");
            enforcePolicyAccess(android.os.Binder.getCallingUid(), "removeAutomaticZenRule");
            enforceUserOriginOnlyFromSystem(z, "removeAutomaticZenRule");
            return com.android.server.notification.NotificationManagerService.this.mZenModeHelper.removeAutomaticZenRule(str, computeZenOrigin(z), "removeAutomaticZenRule", android.os.Binder.getCallingUid());
        }

        public boolean removeAutomaticZenRules(java.lang.String str, boolean z) throws android.os.RemoteException {
            java.util.Objects.requireNonNull(str, "Package name is null");
            enforceSystemOrSystemUI("removeAutomaticZenRules");
            enforceUserOriginOnlyFromSystem(z, "removeAutomaticZenRules");
            return com.android.server.notification.NotificationManagerService.this.mZenModeHelper.removeAutomaticZenRules(str, computeZenOrigin(z), str + "|removeAutomaticZenRules", android.os.Binder.getCallingUid());
        }

        public int getRuleInstanceCount(android.content.ComponentName componentName) throws android.os.RemoteException {
            java.util.Objects.requireNonNull(componentName, "Owner is null");
            enforceSystemOrSystemUI("getRuleInstanceCount");
            return com.android.server.notification.NotificationManagerService.this.mZenModeHelper.getCurrentInstanceCount(componentName);
        }

        public int getAutomaticZenRuleState(@android.annotation.NonNull java.lang.String str) {
            java.util.Objects.requireNonNull(str, "id is null");
            enforcePolicyAccess(android.os.Binder.getCallingUid(), "getAutomaticZenRuleState");
            return com.android.server.notification.NotificationManagerService.this.mZenModeHelper.getAutomaticZenRuleState(str);
        }

        public void setAutomaticZenRuleState(java.lang.String str, android.service.notification.Condition condition) {
            java.util.Objects.requireNonNull(str, "id is null");
            java.util.Objects.requireNonNull(condition, "Condition is null");
            condition.validate();
            enforcePolicyAccess(android.os.Binder.getCallingUid(), "setAutomaticZenRuleState");
            com.android.server.notification.NotificationManagerService.this.mZenModeHelper.setAutomaticZenRuleState(str, condition, computeZenOrigin(condition.source == 1), android.os.Binder.getCallingUid());
        }

        private int computeZenOrigin(boolean z) {
            if (android.app.Flags.modesApi() && z) {
                return 3;
            }
            if (com.android.server.notification.NotificationManagerService.this.isCallerSystemOrSystemUi()) {
                return 5;
            }
            return 4;
        }

        private void enforceUserOriginOnlyFromSystem(boolean z, java.lang.String str) {
            if (android.app.Flags.modesApi() && z && !com.android.server.notification.NotificationManagerService.this.isCallerSystemOrSystemUiOrShell()) {
                throw new java.lang.SecurityException(android.text.TextUtils.formatSimple("Calling %s with fromUser == true is only allowed for system", new java.lang.Object[]{str}));
            }
        }

        public void setInterruptionFilter(java.lang.String str, int i, boolean z) {
            enforcePolicyAccess(str, "setInterruptionFilter");
            int zenModeFromInterruptionFilter = android.app.NotificationManager.zenModeFromInterruptionFilter(i, -1);
            if (zenModeFromInterruptionFilter == -1) {
                throw new java.lang.IllegalArgumentException("Invalid filter: " + i);
            }
            int callingUid = android.os.Binder.getCallingUid();
            enforceUserOriginOnlyFromSystem(z, "setInterruptionFilter");
            if (android.app.Flags.modesApi() && !canManageGlobalZenPolicy(str, callingUid)) {
                com.android.server.notification.NotificationManagerService.this.mZenModeHelper.applyGlobalZenModeAsImplicitZenRule(str, callingUid, zenModeFromInterruptionFilter);
                return;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.notification.NotificationManagerService.this.mZenModeHelper.setManualZenMode(zenModeFromInterruptionFilter, null, computeZenOrigin(z), "setInterruptionFilter", str, callingUid);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void notifyConditions(final java.lang.String str, android.service.notification.IConditionProvider iConditionProvider, final android.service.notification.Condition[] conditionArr) {
            final com.android.server.notification.ManagedServices.ManagedServiceInfo checkServiceToken = com.android.server.notification.NotificationManagerService.this.mConditionProviders.checkServiceToken(iConditionProvider);
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystemOrSameApp(str);
            com.android.server.notification.NotificationManagerService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.notification.NotificationManagerService.13.1
                @Override // java.lang.Runnable
                public void run() {
                    com.android.server.notification.NotificationManagerService.this.mConditionProviders.notifyConditions(str, checkServiceToken, conditionArr);
                }
            });
        }

        public void requestUnbindProvider(android.service.notification.IConditionProvider iConditionProvider) {
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.notification.ManagedServices.ManagedServiceInfo checkServiceToken = com.android.server.notification.NotificationManagerService.this.mConditionProviders.checkServiceToken(iConditionProvider);
                checkServiceToken.getOwner().setComponentState(checkServiceToken.component, android.os.UserHandle.getUserId(callingUid), false);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void requestBindProvider(android.content.ComponentName componentName) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystemOrSameApp(componentName.getPackageName());
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.notification.NotificationManagerService.this.mConditionProviders.setComponentState(componentName, android.os.UserHandle.getUserId(callingUid), true);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        private void enforceSystemOrSystemUI(java.lang.String str) {
            if (com.android.server.notification.NotificationManagerService.this.isCallerSystemOrPhone()) {
                return;
            }
            com.android.server.notification.NotificationManagerService.this.getContext().enforceCallingPermission("android.permission.STATUS_BAR_SERVICE", str);
        }

        private void enforceSystemOrSystemUIOrSamePackage(java.lang.String str, java.lang.String str2) {
            try {
                com.android.server.notification.NotificationManagerService.this.checkCallerIsSystemOrSameApp(str);
            } catch (java.lang.SecurityException e) {
                com.android.server.notification.NotificationManagerService.this.getContext().enforceCallingPermission("android.permission.STATUS_BAR_SERVICE", str2);
            }
        }

        private void enforcePolicyAccess(int i, java.lang.String str) {
            if (com.android.server.notification.NotificationManagerService.this.getContext().checkCallingPermission("android.permission.MANAGE_NOTIFICATIONS") == 0) {
                return;
            }
            boolean z = false;
            for (java.lang.String str2 : com.android.server.notification.NotificationManagerService.this.mPackageManagerClient.getPackagesForUid(i)) {
                if (com.android.server.notification.NotificationManagerService.this.mConditionProviders.isPackageOrComponentAllowed(str2, android.os.UserHandle.getUserId(i))) {
                    z = true;
                }
            }
            if (!z) {
                android.util.Slog.w(com.android.server.notification.NotificationManagerService.TAG, "Notification policy access denied calling " + str);
                throw new java.lang.SecurityException("Notification policy access denied");
            }
        }

        private boolean canManageGlobalZenPolicy(java.lang.String str, final int i) {
            return !((java.lang.Boolean) android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.notification.NotificationManagerService$13$$ExternalSyntheticLambda2
                public final java.lang.Object getOrThrow() {
                    java.lang.Boolean lambda$canManageGlobalZenPolicy$3;
                    lambda$canManageGlobalZenPolicy$3 = com.android.server.notification.NotificationManagerService.AnonymousClass13.lambda$canManageGlobalZenPolicy$3(i);
                    return lambda$canManageGlobalZenPolicy$3;
                }
            })).booleanValue() || com.android.server.notification.NotificationManagerService.this.isCallerSystemOrSystemUi() || com.android.server.notification.NotificationManagerService.this.hasCompanionDevice(str, android.os.UserHandle.getUserId(i), java.util.Set.of("android.app.role.COMPANION_DEVICE_WATCH", "android.app.role.SYSTEM_AUTOMOTIVE_PROJECTION"));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ java.lang.Boolean lambda$canManageGlobalZenPolicy$3(int i) throws java.lang.Exception {
            return java.lang.Boolean.valueOf(android.app.compat.CompatChanges.isChangeEnabled(com.android.server.notification.NotificationManagerService.MANAGE_GLOBAL_ZEN_VIA_IMPLICIT_RULES, i));
        }

        private void enforcePolicyAccess(java.lang.String str, java.lang.String str2) {
            if (com.android.server.notification.NotificationManagerService.this.getContext().checkCallingPermission("android.permission.MANAGE_NOTIFICATIONS") == 0) {
                return;
            }
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSameApp(str);
            if (!checkPolicyAccess(str)) {
                android.util.Slog.w(com.android.server.notification.NotificationManagerService.TAG, "Notification policy access denied calling " + str2);
                throw new java.lang.SecurityException("Notification policy access denied");
            }
        }

        private boolean checkPackagePolicyAccess(java.lang.String str) {
            return com.android.server.notification.NotificationManagerService.this.mConditionProviders.isPackageOrComponentAllowed(str, android.app.INotificationManager.Stub.getCallingUserHandle().getIdentifier());
        }

        private boolean checkPolicyAccess(java.lang.String str) {
            try {
                int packageUidAsUser = com.android.server.notification.NotificationManagerService.this.getContext().getPackageManager().getPackageUidAsUser(str, android.os.UserHandle.getCallingUserId());
                if (com.android.server.notification.NotificationManagerService.this.checkComponentPermission("android.permission.MANAGE_NOTIFICATIONS", packageUidAsUser, -1, true) == 0) {
                    return true;
                }
                if (!checkPackagePolicyAccess(str) && !com.android.server.notification.NotificationManagerService.this.mListeners.isComponentEnabledForPackage(str)) {
                    if (com.android.server.notification.NotificationManagerService.this.mDpm == null) {
                        return false;
                    }
                    if (!com.android.server.notification.NotificationManagerService.this.mDpm.isActiveProfileOwner(packageUidAsUser) && !com.android.server.notification.NotificationManagerService.this.mDpm.isActiveDeviceOwner(packageUidAsUser)) {
                        return false;
                    }
                }
                return true;
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                return false;
            }
        }

        protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            if (com.android.internal.util.DumpUtils.checkDumpAndUsageStatsPermission(com.android.server.notification.NotificationManagerService.this.getContext(), com.android.server.notification.NotificationManagerService.TAG, printWriter)) {
                com.android.server.notification.NotificationManagerService.DumpFilter parseFromArguments = com.android.server.notification.NotificationManagerService.DumpFilter.parseFromArguments(strArr);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    android.util.ArrayMap<android.util.Pair<java.lang.Integer, java.lang.String>, android.util.Pair<java.lang.Boolean, java.lang.Boolean>> allUsersNotificationPermissions = com.android.server.notification.NotificationManagerService.this.getAllUsersNotificationPermissions();
                    if (parseFromArguments.stats) {
                        com.android.server.notification.NotificationManagerService.this.dumpJson(printWriter, parseFromArguments, allUsersNotificationPermissions);
                    } else if (parseFromArguments.rvStats) {
                        com.android.server.notification.NotificationManagerService.this.dumpRemoteViewStats(printWriter, parseFromArguments);
                    } else if (parseFromArguments.proto) {
                        com.android.server.notification.NotificationManagerService.this.dumpProto(fileDescriptor, parseFromArguments, allUsersNotificationPermissions);
                    } else if (parseFromArguments.criticalPriority) {
                        com.android.server.notification.NotificationManagerService.this.dumpNotificationRecords(printWriter, parseFromArguments);
                    } else {
                        com.android.server.notification.NotificationManagerService.this.dumpImpl(printWriter, parseFromArguments, allUsersNotificationPermissions);
                    }
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
        }

        public android.content.ComponentName getEffectsSuppressor() {
            if (com.android.server.notification.NotificationManagerService.this.mEffectsSuppressors.isEmpty()) {
                return null;
            }
            return (android.content.ComponentName) com.android.server.notification.NotificationManagerService.this.mEffectsSuppressors.get(0);
        }

        /* JADX WARN: Code restructure failed: missing block: B:15:0x0041, code lost:
        
            if (r5 == 0) goto L20;
         */
        /* JADX WARN: Code restructure failed: missing block: B:16:0x0043, code lost:
        
            r9.this$0.getContext().enforceCallingPermission("android.permission.READ_CONTACTS", "matchesCallFilter requires listener permission, contacts read access, or system level access");
         */
        /* JADX WARN: Code restructure failed: missing block: B:24:0x0062, code lost:
        
            if (r2 == 0) goto L20;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public boolean matchesCallFilter(android.os.Bundle bundle) {
            boolean z;
            int i = 0;
            try {
                enforceSystemOrSystemUI("INotificationManager.matchesCallFilter");
                z = true;
            } catch (java.lang.SecurityException e) {
                z = false;
            }
            try {
                java.lang.String[] packagesForUid = com.android.server.notification.NotificationManagerService.this.mPackageManager.getPackagesForUid(android.os.Binder.getCallingUid());
                int i2 = 0;
                while (i < packagesForUid.length) {
                    try {
                        i2 |= com.android.server.notification.NotificationManagerService.this.mListeners.hasAllowedListener(packagesForUid[i], android.os.Binder.getCallingUserHandle().getIdentifier()) ? 1 : 0;
                        i++;
                    } catch (android.os.RemoteException e2) {
                        i = i2;
                        if (!z) {
                        }
                        return com.android.server.notification.NotificationManagerService.this.mZenModeHelper.matchesCallFilter(android.os.Binder.getCallingUserHandle(), bundle, (com.android.server.notification.ValidateNotificationPeople) com.android.server.notification.NotificationManagerService.this.mRankingHelper.findExtractor(com.android.server.notification.ValidateNotificationPeople.class), 3000, 1.0f, android.os.Binder.getCallingUid());
                    } catch (java.lang.Throwable th) {
                        th = th;
                        i = i2;
                        if (!z && i == 0) {
                            com.android.server.notification.NotificationManagerService.this.getContext().enforceCallingPermission("android.permission.READ_CONTACTS", "matchesCallFilter requires listener permission, contacts read access, or system level access");
                        }
                        throw th;
                    }
                }
                if (!z) {
                }
            } catch (android.os.RemoteException e3) {
            } catch (java.lang.Throwable th2) {
                th = th2;
            }
            return com.android.server.notification.NotificationManagerService.this.mZenModeHelper.matchesCallFilter(android.os.Binder.getCallingUserHandle(), bundle, (com.android.server.notification.ValidateNotificationPeople) com.android.server.notification.NotificationManagerService.this.mRankingHelper.findExtractor(com.android.server.notification.ValidateNotificationPeople.class), 3000, 1.0f, android.os.Binder.getCallingUid());
        }

        public void cleanUpCallersAfter(long j) {
            enforceSystemOrSystemUI("INotificationManager.cleanUpCallersAfter");
            com.android.server.notification.NotificationManagerService.this.mZenModeHelper.cleanUpCallersAfter(j);
        }

        public boolean isSystemConditionProviderEnabled(java.lang.String str) {
            enforceSystemOrSystemUI("INotificationManager.isSystemConditionProviderEnabled");
            return com.android.server.notification.NotificationManagerService.this.mConditionProviders.isSystemProviderEnabled(str);
        }

        public byte[] getBackupPayload(int i) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystem();
            if (com.android.server.notification.NotificationManagerService.DBG) {
                android.util.Slog.d(com.android.server.notification.NotificationManagerService.TAG, "getBackupPayload u=" + i);
            }
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            try {
                com.android.server.notification.NotificationManagerService.this.writePolicyXml(byteArrayOutputStream, true, i);
                return byteArrayOutputStream.toByteArray();
            } catch (java.io.IOException e) {
                android.util.Slog.w(com.android.server.notification.NotificationManagerService.TAG, "getBackupPayload: error writing payload for user " + i, e);
                return null;
            }
        }

        public void applyRestore(byte[] bArr, int i) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystem();
            if (com.android.server.notification.NotificationManagerService.DBG) {
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("applyRestore u=");
                sb.append(i);
                sb.append(" payload=");
                sb.append(bArr != null ? new java.lang.String(bArr, java.nio.charset.StandardCharsets.UTF_8) : null);
                android.util.Slog.d(com.android.server.notification.NotificationManagerService.TAG, sb.toString());
            }
            if (bArr == null) {
                android.util.Slog.w(com.android.server.notification.NotificationManagerService.TAG, "applyRestore: no payload to restore for user " + i);
                return;
            }
            try {
                com.android.server.notification.NotificationManagerService.this.readPolicyXml(new java.io.ByteArrayInputStream(bArr), true, i);
                com.android.server.notification.NotificationManagerService.this.handleSavePolicyFile();
            } catch (java.io.IOException | java.lang.NumberFormatException | org.xmlpull.v1.XmlPullParserException e) {
                android.util.Slog.w(com.android.server.notification.NotificationManagerService.TAG, "applyRestore: error reading payload", e);
            }
        }

        public boolean isNotificationPolicyAccessGranted(java.lang.String str) {
            return checkPolicyAccess(str);
        }

        public boolean isNotificationPolicyAccessGrantedForPackage(java.lang.String str) {
            enforceSystemOrSystemUIOrSamePackage(str, "request policy access status for another package");
            return checkPolicyAccess(str);
        }

        public void setNotificationPolicyAccessGranted(java.lang.String str, boolean z) throws android.os.RemoteException {
            setNotificationPolicyAccessGrantedForUser(str, android.app.INotificationManager.Stub.getCallingUserHandle().getIdentifier(), z);
        }

        public void setNotificationPolicyAccessGrantedForUser(java.lang.String str, int i, boolean z) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystemOrShell();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (com.android.server.notification.NotificationManagerService.this.mAllowedManagedServicePackages.test(str, java.lang.Integer.valueOf(i), com.android.server.notification.NotificationManagerService.this.mConditionProviders.getRequiredPermission())) {
                    com.android.server.notification.NotificationManagerService.this.mConditionProviders.setPackageOrComponentEnabled(str, i, true, z);
                    com.android.server.notification.NotificationManagerService.this.getContext().sendBroadcastAsUser(new android.content.Intent("android.app.action.NOTIFICATION_POLICY_ACCESS_GRANTED_CHANGED").setPackage(str).addFlags(67108864), android.os.UserHandle.of(i), null);
                    com.android.server.notification.NotificationManagerService.this.handleSavePolicyFile();
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public android.app.NotificationManager.Policy getNotificationPolicy(java.lang.String str) {
            int callingUid = android.os.Binder.getCallingUid();
            if (android.app.Flags.modesApi() && !canManageGlobalZenPolicy(str, callingUid)) {
                return com.android.server.notification.NotificationManagerService.this.mZenModeHelper.getNotificationPolicyFromImplicitZenRule(str);
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.notification.NotificationManagerService.this.mZenModeHelper.getNotificationPolicy();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public android.app.NotificationManager.Policy getConsolidatedNotificationPolicy() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.notification.NotificationManagerService.this.mZenModeHelper.getConsolidatedNotificationPolicy();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setNotificationPolicy(java.lang.String str, android.app.NotificationManager.Policy policy, boolean z) {
            android.app.NotificationManager.Policy policy2 = policy;
            enforcePolicyAccess(str, "setNotificationPolicy");
            enforceUserOriginOnlyFromSystem(z, "setNotificationPolicy");
            int callingUid = android.os.Binder.getCallingUid();
            int computeZenOrigin = computeZenOrigin(z);
            boolean z2 = android.app.Flags.modesApi() && !canManageGlobalZenPolicy(str, callingUid);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                try {
                    android.content.pm.ApplicationInfo applicationInfo = com.android.server.notification.NotificationManagerService.this.mPackageManager.getApplicationInfo(str, 0L, android.os.UserHandle.getUserId(callingUid));
                    android.app.NotificationManager.Policy notificationPolicy = com.android.server.notification.NotificationManagerService.this.mZenModeHelper.getNotificationPolicy();
                    if (applicationInfo.targetSdkVersion < 28) {
                        policy2 = new android.app.NotificationManager.Policy((policy2.priorityCategories & (-33) & (-65) & (-129)) | (notificationPolicy.priorityCategories & 32) | (notificationPolicy.priorityCategories & 64) | (notificationPolicy.priorityCategories & 128), policy2.priorityCallSenders, policy2.priorityMessageSenders, policy2.suppressedVisualEffects);
                    }
                    if (applicationInfo.targetSdkVersion < 30) {
                        policy2 = new android.app.NotificationManager.Policy(com.android.server.notification.NotificationManagerService.this.correctCategory(policy2.priorityCategories, 256, notificationPolicy.priorityCategories), policy2.priorityCallSenders, policy2.priorityMessageSenders, policy2.suppressedVisualEffects, notificationPolicy.priorityConversationSenders);
                    }
                    android.app.NotificationManager.Policy policy3 = new android.app.NotificationManager.Policy(policy2.priorityCategories, policy2.priorityCallSenders, policy2.priorityMessageSenders, com.android.server.notification.NotificationManagerService.this.calculateSuppressedVisualEffects(policy2, notificationPolicy, applicationInfo.targetSdkVersion), policy2.priorityConversationSenders);
                    if (z2) {
                        com.android.server.notification.NotificationManagerService.this.mZenModeHelper.applyGlobalPolicyAsImplicitZenRule(str, callingUid, policy3);
                    } else {
                        com.android.server.notification.ZenLog.traceSetNotificationPolicy(str, applicationInfo.targetSdkVersion, policy3);
                        com.android.server.notification.NotificationManagerService.this.mZenModeHelper.setNotificationPolicy(policy3, computeZenOrigin, callingUid);
                    }
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.notification.NotificationManagerService.TAG, "Failed to set notification policy", e);
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public android.service.notification.ZenPolicy getDefaultZenPolicy() {
            enforceSystemOrSystemUI("INotificationManager.getDefaultZenPolicy");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.notification.NotificationManagerService.this.mZenModeHelper.getDefaultZenPolicy();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public java.util.List<java.lang.String> getEnabledNotificationListenerPackages() {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystem();
            return com.android.server.notification.NotificationManagerService.this.mListeners.getAllowedPackages(android.app.INotificationManager.Stub.getCallingUserHandle().getIdentifier());
        }

        public java.util.List<android.content.ComponentName> getEnabledNotificationListeners(int i) {
            com.android.server.notification.NotificationManagerService.this.checkNotificationListenerAccess();
            return com.android.server.notification.NotificationManagerService.this.mListeners.getAllowedComponents(i);
        }

        public android.content.ComponentName getAllowedNotificationAssistantForUser(int i) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystemOrSystemUiOrShell();
            java.util.List<android.content.ComponentName> allowedComponents = com.android.server.notification.NotificationManagerService.this.mAssistants.getAllowedComponents(i);
            if (allowedComponents.size() > 1) {
                throw new java.lang.IllegalStateException("At most one NotificationAssistant: " + allowedComponents.size());
            }
            return (android.content.ComponentName) com.android.internal.util.CollectionUtils.firstOrNull(allowedComponents);
        }

        public android.content.ComponentName getAllowedNotificationAssistant() {
            return getAllowedNotificationAssistantForUser(android.app.INotificationManager.Stub.getCallingUserHandle().getIdentifier());
        }

        public android.content.ComponentName getDefaultNotificationAssistant() {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystem();
            return com.android.server.notification.NotificationManagerService.this.mAssistants.getDefaultFromConfig();
        }

        public void setNASMigrationDoneAndResetDefault(int i, boolean z) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystem();
            com.android.server.notification.NotificationManagerService.this.setNASMigrationDone(i);
            if (z) {
                com.android.server.notification.NotificationManagerService.this.mAssistants.resetDefaultFromConfig();
            } else {
                com.android.server.notification.NotificationManagerService.this.mAssistants.clearDefaults();
            }
        }

        public boolean hasEnabledNotificationListener(java.lang.String str, int i) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystem();
            return com.android.server.notification.NotificationManagerService.this.mListeners.isPackageAllowed(str, i);
        }

        public boolean isNotificationListenerAccessGranted(android.content.ComponentName componentName) {
            java.util.Objects.requireNonNull(componentName);
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystemOrSameApp(componentName.getPackageName());
            return com.android.server.notification.NotificationManagerService.this.mListeners.isPackageOrComponentAllowed(componentName.flattenToString(), android.app.INotificationManager.Stub.getCallingUserHandle().getIdentifier());
        }

        public boolean isNotificationListenerAccessGrantedForUser(android.content.ComponentName componentName, int i) {
            java.util.Objects.requireNonNull(componentName);
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystem();
            return com.android.server.notification.NotificationManagerService.this.mListeners.isPackageOrComponentAllowed(componentName.flattenToString(), i);
        }

        public boolean isNotificationAssistantAccessGranted(android.content.ComponentName componentName) {
            java.util.Objects.requireNonNull(componentName);
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystemOrSameApp(componentName.getPackageName());
            return com.android.server.notification.NotificationManagerService.this.mAssistants.isPackageOrComponentAllowed(componentName.flattenToString(), android.app.INotificationManager.Stub.getCallingUserHandle().getIdentifier());
        }

        public void setNotificationListenerAccessGranted(android.content.ComponentName componentName, boolean z, boolean z2) throws android.os.RemoteException {
            setNotificationListenerAccessGrantedForUser(componentName, android.app.INotificationManager.Stub.getCallingUserHandle().getIdentifier(), z, z2);
        }

        public void setNotificationAssistantAccessGranted(android.content.ComponentName componentName, boolean z) {
            setNotificationAssistantAccessGrantedForUser(componentName, android.app.INotificationManager.Stub.getCallingUserHandle().getIdentifier(), z);
        }

        public void setNotificationListenerAccessGrantedForUser(android.content.ComponentName componentName, int i, boolean z, boolean z2) {
            java.util.Objects.requireNonNull(componentName);
            if (android.os.UserHandle.getCallingUserId() != i) {
                com.android.server.notification.NotificationManagerService.this.getContext().enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS", "setNotificationListenerAccessGrantedForUser for user " + i);
            }
            com.android.server.notification.NotificationManagerService.this.checkNotificationListenerAccess();
            if (z && componentName.flattenToString().length() > android.app.NotificationManager.MAX_SERVICE_COMPONENT_NAME_LENGTH) {
                throw new java.lang.IllegalArgumentException("Component name too long: " + componentName.flattenToString());
            }
            if (!z2 && isNotificationListenerAccessUserSet(componentName, i)) {
                return;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (com.android.server.notification.NotificationManagerService.this.mAllowedManagedServicePackages.test(componentName.getPackageName(), java.lang.Integer.valueOf(i), com.android.server.notification.NotificationManagerService.this.mListeners.getRequiredPermission())) {
                    com.android.server.notification.NotificationManagerService.this.mConditionProviders.setPackageOrComponentEnabled(componentName.flattenToString(), i, false, z, z2);
                    com.android.server.notification.NotificationManagerService.this.mListeners.setPackageOrComponentEnabled(componentName.flattenToString(), i, true, z, z2);
                    com.android.server.notification.NotificationManagerService.this.getContext().sendBroadcastAsUser(new android.content.Intent("android.app.action.NOTIFICATION_POLICY_ACCESS_GRANTED_CHANGED").setPackage(componentName.getPackageName()).addFlags(1073741824), android.os.UserHandle.of(i), null);
                    com.android.server.notification.NotificationManagerService.this.handleSavePolicyFile();
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        private boolean isNotificationListenerAccessUserSet(android.content.ComponentName componentName, int i) {
            return com.android.server.notification.NotificationManagerService.this.mListeners.isPackageOrComponentUserSet(componentName.flattenToString(), i);
        }

        public void setNotificationAssistantAccessGrantedForUser(android.content.ComponentName componentName, int i, boolean z) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystemOrSystemUiOrShell();
            java.util.Iterator it = com.android.server.notification.NotificationManagerService.this.mUm.getEnabledProfiles(i).iterator();
            while (it.hasNext()) {
                com.android.server.notification.NotificationManagerService.this.mAssistants.setUserSet(((android.content.pm.UserInfo) it.next()).id, true);
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.notification.NotificationManagerService.this.setNotificationAssistantAccessGrantedForUserInternal(componentName, i, z, true);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void applyEnqueuedAdjustmentFromAssistant(android.service.notification.INotificationListener iNotificationListener, android.service.notification.Adjustment adjustment) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                    try {
                        com.android.server.notification.NotificationManagerService.this.mAssistants.checkServiceTokenLocked(iNotificationListener);
                        int size = com.android.server.notification.NotificationManagerService.this.mEnqueuedNotifications.size();
                        boolean z = false;
                        for (int i = 0; i < size; i++) {
                            com.android.server.notification.NotificationRecord notificationRecord = com.android.server.notification.NotificationManagerService.this.mEnqueuedNotifications.get(i);
                            if (java.util.Objects.equals(adjustment.getKey(), notificationRecord.getKey()) && java.util.Objects.equals(java.lang.Integer.valueOf(adjustment.getUser()), java.lang.Integer.valueOf(notificationRecord.getUserId())) && com.android.server.notification.NotificationManagerService.this.mAssistants.isSameUser(iNotificationListener, notificationRecord.getUserId())) {
                                com.android.server.notification.NotificationManagerService.this.applyAdjustment(notificationRecord, adjustment);
                                notificationRecord.applyAdjustments();
                                notificationRecord.calculateImportance();
                                z = true;
                            }
                        }
                        if (!z) {
                            applyAdjustmentFromAssistant(iNotificationListener, adjustment);
                        }
                    } finally {
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void applyAdjustmentFromAssistant(android.service.notification.INotificationListener iNotificationListener, android.service.notification.Adjustment adjustment) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.add(adjustment);
            applyAdjustmentsFromAssistant(iNotificationListener, arrayList);
        }

        public void applyAdjustmentsFromAssistant(android.service.notification.INotificationListener iNotificationListener, java.util.List<android.service.notification.Adjustment> list) {
            boolean z;
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                    try {
                        com.android.server.notification.NotificationManagerService.this.mAssistants.checkServiceTokenLocked(iNotificationListener);
                        z = false;
                        for (android.service.notification.Adjustment adjustment : list) {
                            com.android.server.notification.NotificationRecord notificationRecord = com.android.server.notification.NotificationManagerService.this.mNotificationsByKey.get(adjustment.getKey());
                            if (notificationRecord != null && com.android.server.notification.NotificationManagerService.this.mAssistants.isSameUser(iNotificationListener, notificationRecord.getUserId())) {
                                com.android.server.notification.NotificationManagerService.this.applyAdjustment(notificationRecord, adjustment);
                                if (adjustment.getSignals().containsKey("key_importance") && adjustment.getSignals().getInt("key_importance") == 0) {
                                    cancelNotificationsFromListener(iNotificationListener, new java.lang.String[]{notificationRecord.getKey()});
                                } else {
                                    z = true;
                                    notificationRecord.setPendingLogUpdate(true);
                                }
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                if (z) {
                    com.android.server.notification.NotificationManagerService.this.mRankingHandler.requestSort();
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void updateNotificationChannelGroupFromPrivilegedListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String str, android.os.UserHandle userHandle, android.app.NotificationChannelGroup notificationChannelGroup) throws android.os.RemoteException {
            java.util.Objects.requireNonNull(userHandle);
            verifyPrivilegedListener(iNotificationListener, userHandle, false);
            com.android.server.notification.NotificationManagerService.this.createNotificationChannelGroup(str, getUidForPackageAndUser(str, userHandle), notificationChannelGroup, false, true);
            com.android.server.notification.NotificationManagerService.this.handleSavePolicyFile();
        }

        public void updateNotificationChannelFromPrivilegedListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String str, android.os.UserHandle userHandle, android.app.NotificationChannel notificationChannel) throws android.os.RemoteException {
            java.util.Objects.requireNonNull(notificationChannel);
            java.util.Objects.requireNonNull(str);
            java.util.Objects.requireNonNull(userHandle);
            verifyPrivilegedListener(iNotificationListener, userHandle, false);
            verifyPrivilegedListenerUriPermission(android.os.Binder.getCallingUid(), notificationChannel, com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.getNotificationChannel(str, getUidForPackageAndUser(str, userHandle), notificationChannel.getId(), true));
            com.android.server.notification.NotificationManagerService.this.updateNotificationChannelInt(str, getUidForPackageAndUser(str, userHandle), notificationChannel, true);
        }

        public android.content.pm.ParceledListSlice<android.app.NotificationChannel> getNotificationChannelsFromPrivilegedListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String str, android.os.UserHandle userHandle) throws android.os.RemoteException {
            java.util.Objects.requireNonNull(str);
            java.util.Objects.requireNonNull(userHandle);
            verifyPrivilegedListener(iNotificationListener, userHandle, true);
            return com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.getNotificationChannels(str, getUidForPackageAndUser(str, userHandle), false);
        }

        public android.content.pm.ParceledListSlice<android.app.NotificationChannelGroup> getNotificationChannelGroupsFromPrivilegedListener(android.service.notification.INotificationListener iNotificationListener, java.lang.String str, android.os.UserHandle userHandle) throws android.os.RemoteException {
            java.util.Objects.requireNonNull(str);
            java.util.Objects.requireNonNull(userHandle);
            verifyPrivilegedListener(iNotificationListener, userHandle, true);
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.addAll(com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.getNotificationChannelGroups(str, getUidForPackageAndUser(str, userHandle)));
            return new android.content.pm.ParceledListSlice<>(arrayList);
        }

        public boolean isInCall(java.lang.String str, int i) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystemOrSystemUiOrShell();
            return com.android.server.notification.NotificationManagerService.this.isCallNotification(str, i);
        }

        public void setPrivateNotificationsAllowed(boolean z) {
            if (com.android.server.notification.NotificationManagerService.this.getContext().checkCallingPermission("android.permission.CONTROL_KEYGUARD_SECURE_NOTIFICATIONS") != 0) {
                throw new java.lang.SecurityException("Requires CONTROL_KEYGUARD_SECURE_NOTIFICATIONS permission");
            }
            if (z != com.android.server.notification.NotificationManagerService.this.mLockScreenAllowSecureNotifications) {
                com.android.server.notification.NotificationManagerService.this.mLockScreenAllowSecureNotifications = z;
                if (android.app.Flags.keyguardPrivateNotifications()) {
                    com.android.server.notification.NotificationManagerService.this.getContext().sendBroadcast(new android.content.Intent("android.app.action.KEYGUARD_PRIVATE_NOTIFICATIONS_CHANGED").putExtra("android.app.extra.KM_PRIVATE_NOTIFS_ALLOWED", com.android.server.notification.NotificationManagerService.this.mLockScreenAllowSecureNotifications), "android.permission.STATUS_BAR_SERVICE");
                }
                com.android.server.notification.NotificationManagerService.this.handleSavePolicyFile();
            }
        }

        public boolean getPrivateNotificationsAllowed() {
            if (com.android.server.notification.NotificationManagerService.this.getContext().checkCallingPermission("android.permission.CONTROL_KEYGUARD_SECURE_NOTIFICATIONS") != 0) {
                throw new java.lang.SecurityException("Requires CONTROL_KEYGUARD_SECURE_NOTIFICATIONS permission");
            }
            return com.android.server.notification.NotificationManagerService.this.mLockScreenAllowSecureNotifications;
        }

        public boolean isPackagePaused(java.lang.String str) {
            java.util.Objects.requireNonNull(str);
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSameApp(str);
            return com.android.server.notification.NotificationManagerService.this.isPackagePausedOrSuspended(str, android.os.Binder.getCallingUid());
        }

        public boolean isPermissionFixed(java.lang.String str, int i) {
            enforceSystemOrSystemUI("isPermissionFixed");
            return com.android.server.notification.NotificationManagerService.this.mPermissionHelper.isPermissionFixed(str, i);
        }

        private void verifyPrivilegedListener(android.service.notification.INotificationListener iNotificationListener, android.os.UserHandle userHandle, boolean z) {
            com.android.server.notification.ManagedServices.ManagedServiceInfo checkServiceTokenLocked;
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                checkServiceTokenLocked = com.android.server.notification.NotificationManagerService.this.mListeners.checkServiceTokenLocked(iNotificationListener);
            }
            if (!com.android.server.notification.NotificationManagerService.this.hasCompanionDevice(checkServiceTokenLocked)) {
                synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                    if (z) {
                        try {
                            if (com.android.server.notification.NotificationManagerService.this.mAssistants.isServiceTokenValidLocked(checkServiceTokenLocked.service)) {
                            }
                        } finally {
                        }
                    }
                    throw new java.lang.SecurityException(checkServiceTokenLocked + " does not have access");
                }
            }
            if (!checkServiceTokenLocked.enabledAndUserMatches(userHandle.getIdentifier())) {
                throw new java.lang.SecurityException(checkServiceTokenLocked + " does not have access");
            }
        }

        private void verifyPrivilegedListenerUriPermission(final int i, @android.annotation.NonNull android.app.NotificationChannel notificationChannel, @android.annotation.Nullable android.app.NotificationChannel notificationChannel2) {
            final android.net.Uri sound = notificationChannel.getSound();
            android.net.Uri sound2 = notificationChannel2 != null ? notificationChannel2.getSound() : null;
            if (sound != null && !java.util.Objects.equals(sound2, sound)) {
                android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.notification.NotificationManagerService$13$$ExternalSyntheticLambda0
                    public final void runOrThrow() {
                        com.android.server.notification.NotificationManagerService.AnonymousClass13.this.lambda$verifyPrivilegedListenerUriPermission$4(i, sound);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$verifyPrivilegedListenerUriPermission$4(int i, android.net.Uri uri) throws java.lang.Exception {
            com.android.server.notification.NotificationManagerService.this.mUgmInternal.checkGrantUriPermission(i, null, android.content.ContentProvider.getUriWithoutUserId(uri), 1, android.content.ContentProvider.getUserIdFromUri(uri, android.os.UserHandle.getUserId(i)));
        }

        private int getUidForPackageAndUser(java.lang.String str, android.os.UserHandle userHandle) throws android.os.RemoteException {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.notification.NotificationManagerService.this.mPackageManager.getPackageUid(str, 0L, userHandle.getIdentifier());
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
            new com.android.server.notification.NotificationShellCmd(com.android.server.notification.NotificationManagerService.this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        public long pullStats(long j, int i, boolean z, java.util.List<android.os.ParcelFileDescriptor> list) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystemOrShell();
            long convert = java.util.concurrent.TimeUnit.MILLISECONDS.convert(j, java.util.concurrent.TimeUnit.NANOSECONDS);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                switch (i) {
                    case 1:
                        android.util.Slog.e(com.android.server.notification.NotificationManagerService.TAG, "pullStats REPORT_REMOTE_VIEWS from: " + convert + "  with " + z);
                        com.android.server.notification.PulledStats remoteViewStats = com.android.server.notification.NotificationManagerService.this.mUsageStats.remoteViewStats(convert, z);
                        if (remoteViewStats != null) {
                            list.add(remoteViewStats.toParcelFileDescriptor(i));
                            android.util.Slog.e(com.android.server.notification.NotificationManagerService.TAG, "exiting pullStats with: " + list.size());
                            return java.util.concurrent.TimeUnit.NANOSECONDS.convert(remoteViewStats.endTimeMs(), java.util.concurrent.TimeUnit.MILLISECONDS);
                        }
                        android.util.Slog.e(com.android.server.notification.NotificationManagerService.TAG, "null stats for: " + i);
                    default:
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        android.util.Slog.e(com.android.server.notification.NotificationManagerService.TAG, "exiting pullStats: bad request");
                        return 0L;
                }
            } catch (java.io.IOException e) {
                android.util.Slog.e(com.android.server.notification.NotificationManagerService.TAG, "exiting pullStats: on error", e);
                return 0L;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleNotificationPermissionChange(java.lang.String str, int i) {
        if (!this.mUmInternal.isUserInitialized(i)) {
            return;
        }
        int packageUid = this.mPackageManagerInternal.getPackageUid(str, 0L, i);
        if (packageUid == -1) {
            android.util.Log.e(TAG, java.lang.String.format("No uid found for %s, %s!", str, java.lang.Integer.valueOf(i)));
        } else if (!this.mPermissionHelper.hasPermission(packageUid)) {
            cancelAllNotificationsInt(MY_UID, MY_PID, str, null, 0, 0, i, 7);
        }
    }

    protected void checkNotificationListenerAccess() {
        if (!isCallerSystemOrPhone()) {
            getContext().enforceCallingPermission("android.permission.MANAGE_NOTIFICATION_LISTENERS", "Caller must hold android.permission.MANAGE_NOTIFICATION_LISTENERS");
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void setNotificationAssistantAccessGrantedForUserInternal(android.content.ComponentName componentName, int i, boolean z, boolean z2) {
        java.util.List enabledProfiles = this.mUm.getEnabledProfiles(i);
        if (enabledProfiles != null) {
            java.util.Iterator it = enabledProfiles.iterator();
            while (it.hasNext()) {
                int i2 = ((android.content.pm.UserInfo) it.next()).id;
                if (componentName == null) {
                    android.content.ComponentName componentName2 = (android.content.ComponentName) com.android.internal.util.CollectionUtils.firstOrNull(this.mAssistants.getAllowedComponents(i2));
                    if (componentName2 != null) {
                        setNotificationAssistantAccessGrantedForUserInternal(componentName2, i2, false, z2);
                    }
                } else if (!z || this.mAllowedManagedServicePackages.test(componentName.getPackageName(), java.lang.Integer.valueOf(i2), this.mAssistants.getRequiredPermission())) {
                    this.mConditionProviders.setPackageOrComponentEnabled(componentName.flattenToString(), i2, false, z);
                    this.mAssistants.setPackageOrComponentEnabled(componentName.flattenToString(), i2, true, z, z2);
                    getContext().sendBroadcastAsUser(new android.content.Intent("android.app.action.NOTIFICATION_POLICY_ACCESS_GRANTED_CHANGED").setPackage(componentName.getPackageName()).addFlags(1073741824), android.os.UserHandle.of(i2), null);
                    handleSavePolicyFile();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyAdjustment(com.android.server.notification.NotificationRecord notificationRecord, android.service.notification.Adjustment adjustment) {
        if (notificationRecord != null && adjustment.getSignals() != null) {
            android.os.Bundle signals = adjustment.getSignals();
            android.os.Bundle.setDefusable(signals, true);
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (java.lang.String str : signals.keySet()) {
                if (!this.mAssistants.isAdjustmentAllowed(str)) {
                    arrayList.add(str);
                }
            }
            java.util.Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                signals.remove((java.lang.String) it.next());
            }
            notificationRecord.addAdjustment(adjustment);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    void addAutogroupKeyLocked(java.lang.String str) {
        com.android.server.notification.NotificationRecord notificationRecord = this.mNotificationsByKey.get(str);
        if (notificationRecord != null && notificationRecord.getSbn().getOverrideGroupKey() == null) {
            addAutoGroupAdjustment(notificationRecord, "ranker_group");
            com.android.server.EventLogTags.writeNotificationAutogrouped(str);
            this.mRankingHandler.requestSort();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    void removeAutogroupKeyLocked(java.lang.String str) {
        com.android.server.notification.NotificationRecord notificationRecord = this.mNotificationsByKey.get(str);
        if (notificationRecord == null) {
            android.util.Slog.w(TAG, "Failed to remove autogroup " + str);
            return;
        }
        if (notificationRecord.getSbn().getOverrideGroupKey() != null) {
            addAutoGroupAdjustment(notificationRecord, null);
            com.android.server.EventLogTags.writeNotificationUnautogrouped(str);
            this.mRankingHandler.requestSort();
        }
    }

    private void addAutoGroupAdjustment(com.android.server.notification.NotificationRecord notificationRecord, java.lang.String str) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putString("key_group_key", str);
        notificationRecord.addAdjustment(new android.service.notification.Adjustment(notificationRecord.getSbn().getPackageName(), notificationRecord.getKey(), bundle, "", notificationRecord.getSbn().getUserId()));
    }

    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    @com.android.internal.annotations.VisibleForTesting
    void clearAutogroupSummaryLocked(int i, java.lang.String str) {
        com.android.server.notification.NotificationRecord findNotificationByKeyLocked;
        android.util.ArrayMap<java.lang.String, java.lang.String> arrayMap = this.mAutobundledSummaries.get(java.lang.Integer.valueOf(i));
        if (arrayMap != null && arrayMap.containsKey(str) && (findNotificationByKeyLocked = findNotificationByKeyLocked(arrayMap.remove(str))) != null) {
            android.service.notification.StatusBarNotification sbn = findNotificationByKeyLocked.getSbn();
            cancelNotification(MY_UID, MY_PID, str, sbn.getTag(), sbn.getId(), 0, 0, false, i, 16, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    public boolean hasAutoGroupSummaryLocked(android.service.notification.StatusBarNotification statusBarNotification) {
        android.util.ArrayMap<java.lang.String, java.lang.String> arrayMap = this.mAutobundledSummaries.get(java.lang.Integer.valueOf(statusBarNotification.getUserId()));
        return arrayMap != null && arrayMap.containsKey(statusBarNotification.getPackageName());
    }

    com.android.server.notification.NotificationRecord createAutoGroupSummary(int i, java.lang.String str, java.lang.String str2, int i2, android.graphics.drawable.Icon icon, int i3, int i4) {
        android.util.ArrayMap<java.lang.String, java.lang.String> arrayMap;
        com.android.server.notification.NotificationRecord notificationRecord;
        android.app.Notification notification;
        android.util.ArrayMap<java.lang.String, java.lang.String> arrayMap2;
        boolean isPermissionFixed = this.mPermissionHelper.isPermissionFixed(str, i);
        synchronized (this.mNotificationLock) {
            try {
                com.android.server.notification.NotificationRecord notificationRecord2 = this.mNotificationsByKey.get(str2);
                if (notificationRecord2 == null) {
                    return null;
                }
                android.service.notification.StatusBarNotification sbn = notificationRecord2.getSbn();
                int identifier = sbn.getUser().getIdentifier();
                int uid = sbn.getUid();
                android.util.ArrayMap<java.lang.String, java.lang.String> arrayMap3 = this.mAutobundledSummaries.get(java.lang.Integer.valueOf(identifier));
                if (arrayMap3 != null) {
                    arrayMap = arrayMap3;
                } else {
                    arrayMap = new android.util.ArrayMap<>();
                }
                this.mAutobundledSummaries.put(java.lang.Integer.valueOf(identifier), arrayMap);
                if (arrayMap.containsKey(str)) {
                    notificationRecord = null;
                } else {
                    android.content.pm.ApplicationInfo applicationInfo = (android.content.pm.ApplicationInfo) sbn.getNotification().extras.getParcelable("android.appInfo", android.content.pm.ApplicationInfo.class);
                    android.os.Bundle bundle = new android.os.Bundle();
                    bundle.putParcelable("android.appInfo", applicationInfo);
                    android.app.Notification build = new android.app.Notification.Builder(getContext(), notificationRecord2.getChannel().getId()).setSmallIcon(icon).setGroupSummary(true).setGroupAlertBehavior(2).setGroup("ranker_group").setFlag(i2, true).setColor(i3).setVisibility(i4).build();
                    build.extras.putAll(bundle);
                    android.content.Intent launchIntentForPackage = getContext().getPackageManager().getLaunchIntentForPackage(str);
                    if (launchIntentForPackage == null) {
                        notification = build;
                        arrayMap2 = arrayMap;
                    } else {
                        notification = build;
                        arrayMap2 = arrayMap;
                        notification.contentIntent = this.mAmi.getPendingIntentActivityAsApp(0, launchIntentForPackage, 67108864, (android.os.Bundle) null, str, applicationInfo.uid);
                    }
                    android.service.notification.StatusBarNotification statusBarNotification = new android.service.notification.StatusBarNotification(sbn.getPackageName(), sbn.getOpPkg(), Integer.MAX_VALUE, "ranker_group", sbn.getUid(), sbn.getInitialPid(), notification, sbn.getUser(), "ranker_group", java.lang.System.currentTimeMillis());
                    notificationRecord = new com.android.server.notification.NotificationRecord(getContext(), statusBarNotification, notificationRecord2.getChannel());
                    notificationRecord.setImportanceFixed(isPermissionFixed);
                    notificationRecord.setIsAppImportanceLocked(notificationRecord2.getIsAppImportanceLocked());
                    arrayMap2.put(str, statusBarNotification.getKey());
                }
                if (notificationRecord == null || !checkDisqualifyingFeatures(identifier, uid, notificationRecord.getSbn().getId(), notificationRecord.getSbn().getTag(), notificationRecord, true, false)) {
                    return null;
                }
                return notificationRecord;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private java.lang.String disableNotificationEffects(com.android.server.notification.NotificationRecord notificationRecord) {
        if (this.mDisableNotificationEffects) {
            return "booleanState";
        }
        if ((this.mListenerHints & 1) != 0) {
            return "listenerHints";
        }
        if (notificationRecord != null && notificationRecord.getAudioAttributes() != null) {
            if ((this.mListenerHints & 2) != 0 && notificationRecord.getAudioAttributes().getUsage() != 6) {
                return "listenerNoti";
            }
            if ((this.mListenerHints & 4) != 0 && notificationRecord.getAudioAttributes().getUsage() == 6) {
                return "listenerCall";
            }
        }
        if (this.mCallState != 0 && !this.mZenModeHelper.isCall(notificationRecord)) {
            return "callState";
        }
        return null;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected android.util.ArrayMap<android.util.Pair<java.lang.Integer, java.lang.String>, android.util.Pair<java.lang.Boolean, java.lang.Boolean>> getAllUsersNotificationPermissions() {
        android.util.ArrayMap<android.util.Pair<java.lang.Integer, java.lang.String>, android.util.Pair<java.lang.Boolean, java.lang.Boolean>> arrayMap = new android.util.ArrayMap<>();
        java.util.Iterator it = this.mUm.getUsers().iterator();
        while (it.hasNext()) {
            android.util.ArrayMap<android.util.Pair<java.lang.Integer, java.lang.String>, android.util.Pair<java.lang.Boolean, java.lang.Boolean>> notificationPermissionValues = this.mPermissionHelper.getNotificationPermissionValues(((android.content.pm.UserInfo) it.next()).getUserHandle().getIdentifier());
            for (android.util.Pair<java.lang.Integer, java.lang.String> pair : notificationPermissionValues.keySet()) {
                arrayMap.put(pair, notificationPermissionValues.get(pair));
            }
        }
        return arrayMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dumpJson(java.io.PrintWriter printWriter, @android.annotation.NonNull com.android.server.notification.NotificationManagerService.DumpFilter dumpFilter, android.util.ArrayMap<android.util.Pair<java.lang.Integer, java.lang.String>, android.util.Pair<java.lang.Boolean, java.lang.Boolean>> arrayMap) {
        org.json.JSONObject jSONObject = new org.json.JSONObject();
        try {
            jSONObject.put(com.android.server.am.HostingRecord.HOSTING_TYPE_SERVICE, "Notification Manager");
            jSONObject.put("bans", this.mPreferencesHelper.dumpBansJson(dumpFilter, arrayMap));
            jSONObject.put("ranking", this.mPreferencesHelper.dumpJson(dumpFilter, arrayMap));
            jSONObject.put("stats", this.mUsageStats.dumpJson(dumpFilter));
            jSONObject.put("channels", this.mPreferencesHelper.dumpChannelsJson(dumpFilter));
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
        printWriter.println(jSONObject);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dumpRemoteViewStats(java.io.PrintWriter printWriter, @android.annotation.NonNull com.android.server.notification.NotificationManagerService.DumpFilter dumpFilter) {
        com.android.server.notification.PulledStats remoteViewStats = this.mUsageStats.remoteViewStats(dumpFilter.since, true);
        if (remoteViewStats == null) {
            printWriter.println("no remote view stats reported.");
        } else {
            remoteViewStats.dump(1, printWriter, dumpFilter);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dumpProto(java.io.FileDescriptor fileDescriptor, @android.annotation.NonNull com.android.server.notification.NotificationManagerService.DumpFilter dumpFilter, android.util.ArrayMap<android.util.Pair<java.lang.Integer, java.lang.String>, android.util.Pair<java.lang.Boolean, java.lang.Boolean>> arrayMap) {
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(fileDescriptor);
        synchronized (this.mNotificationLock) {
            try {
                int size = this.mNotificationList.size();
                for (int i = 0; i < size; i++) {
                    com.android.server.notification.NotificationRecord notificationRecord = this.mNotificationList.get(i);
                    if (!dumpFilter.filtered || dumpFilter.matches(notificationRecord.getSbn())) {
                        notificationRecord.dump(protoOutputStream, 2246267895809L, dumpFilter.redact, 1);
                    }
                }
                int size2 = this.mEnqueuedNotifications.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    com.android.server.notification.NotificationRecord notificationRecord2 = this.mEnqueuedNotifications.get(i2);
                    if (!dumpFilter.filtered || dumpFilter.matches(notificationRecord2.getSbn())) {
                        notificationRecord2.dump(protoOutputStream, 2246267895809L, dumpFilter.redact, 0);
                    }
                }
                java.util.List<com.android.server.notification.NotificationRecord> snoozed = this.mSnoozeHelper.getSnoozed();
                int size3 = snoozed.size();
                for (int i3 = 0; i3 < size3; i3++) {
                    com.android.server.notification.NotificationRecord notificationRecord3 = snoozed.get(i3);
                    if (!dumpFilter.filtered || dumpFilter.matches(notificationRecord3.getSbn())) {
                        notificationRecord3.dump(protoOutputStream, 2246267895809L, dumpFilter.redact, 2);
                    }
                }
                long start = protoOutputStream.start(1146756268034L);
                this.mZenModeHelper.dump(protoOutputStream);
                java.util.Iterator<android.content.ComponentName> it = this.mEffectsSuppressors.iterator();
                while (it.hasNext()) {
                    it.next().dumpDebug(protoOutputStream, 2246267895812L);
                }
                protoOutputStream.end(start);
                long start2 = protoOutputStream.start(1146756268035L);
                this.mListeners.dump(protoOutputStream, dumpFilter);
                protoOutputStream.end(start2);
                protoOutputStream.write(1120986464260L, this.mListenerHints);
                for (int i4 = 0; i4 < this.mListenersDisablingEffects.size(); i4++) {
                    long start3 = protoOutputStream.start(2246267895813L);
                    protoOutputStream.write(1120986464257L, this.mListenersDisablingEffects.keyAt(i4));
                    android.util.ArraySet<android.content.ComponentName> valueAt = this.mListenersDisablingEffects.valueAt(i4);
                    for (int i5 = 0; i5 < valueAt.size(); i5++) {
                        valueAt.valueAt(i5).dumpDebug(protoOutputStream, 2246267895811L);
                    }
                    protoOutputStream.end(start3);
                }
                long start4 = protoOutputStream.start(1146756268038L);
                this.mAssistants.dump(protoOutputStream, dumpFilter);
                protoOutputStream.end(start4);
                long start5 = protoOutputStream.start(1146756268039L);
                this.mConditionProviders.dump(protoOutputStream, dumpFilter);
                protoOutputStream.end(start5);
                long start6 = protoOutputStream.start(1146756268040L);
                this.mRankingHelper.dump(protoOutputStream, dumpFilter);
                this.mPreferencesHelper.dump(protoOutputStream, dumpFilter, arrayMap);
                protoOutputStream.end(start6);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        protoOutputStream.flush();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dumpNotificationRecords(java.io.PrintWriter printWriter, @android.annotation.NonNull com.android.server.notification.NotificationManagerService.DumpFilter dumpFilter) {
        synchronized (this.mNotificationLock) {
            try {
                int size = this.mNotificationList.size();
                if (size > 0) {
                    printWriter.println("  Notification List:");
                    for (int i = 0; i < size; i++) {
                        com.android.server.notification.NotificationRecord notificationRecord = this.mNotificationList.get(i);
                        if (!dumpFilter.filtered || dumpFilter.matches(notificationRecord.getSbn())) {
                            notificationRecord.dump(printWriter, "    ", getContext(), dumpFilter.redact);
                        }
                    }
                    printWriter.println("  ");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void dumpImpl(java.io.PrintWriter printWriter, @android.annotation.NonNull com.android.server.notification.NotificationManagerService.DumpFilter dumpFilter, android.util.ArrayMap<android.util.Pair<java.lang.Integer, java.lang.String>, android.util.Pair<java.lang.Boolean, java.lang.Boolean>> arrayMap) {
        printWriter.print("Current Notification Manager state");
        if (dumpFilter.filtered) {
            printWriter.print(" (filtered to ");
            printWriter.print(dumpFilter);
            printWriter.print(")");
        }
        printWriter.println(':');
        boolean z = dumpFilter.filtered && dumpFilter.zen;
        if (!z) {
            synchronized (this.mToastQueue) {
                try {
                    int size = this.mToastQueue.size();
                    if (size > 0) {
                        printWriter.println("  Toast Queue:");
                        for (int i = 0; i < size; i++) {
                            this.mToastQueue.get(i).dump(printWriter, "    ", dumpFilter);
                        }
                        printWriter.println("  ");
                    }
                } finally {
                }
            }
        }
        synchronized (this.mNotificationLock) {
            if (!z) {
                try {
                    if (!dumpFilter.normalPriority) {
                        dumpNotificationRecords(printWriter, dumpFilter);
                    }
                    if (!dumpFilter.filtered) {
                        int size2 = this.mLights.size();
                        if (size2 > 0) {
                            printWriter.println("  Lights List:");
                            for (int i2 = 0; i2 < size2; i2++) {
                                if (i2 == size2 - 1) {
                                    printWriter.print("  > ");
                                } else {
                                    printWriter.print("    ");
                                }
                                printWriter.println(this.mLights.get(i2));
                            }
                            printWriter.println("  ");
                        }
                        printWriter.println("  mUseAttentionLight=" + this.mUseAttentionLight);
                        printWriter.println("  mHasLight=" + this.mHasLight);
                        printWriter.println("  mNotificationPulseEnabled=" + this.mNotificationPulseEnabled);
                        printWriter.println("  mSoundNotificationKey=" + this.mSoundNotificationKey);
                        printWriter.println("  mVibrateNotificationKey=" + this.mVibrateNotificationKey);
                        printWriter.println("  mDisableNotificationEffects=" + this.mDisableNotificationEffects);
                        printWriter.println("  mCallState=" + callStateToString(this.mCallState));
                        printWriter.println("  mSystemReady=" + this.mSystemReady);
                        printWriter.println("  mMaxPackageEnqueueRate=" + this.mMaxPackageEnqueueRate);
                        printWriter.println("  hideSilentStatusBar=" + this.mPreferencesHelper.shouldHideSilentStatusIcons());
                        com.android.server.notification.Flags.refactorAttentionHelper();
                        this.mAttentionHelper.dump(printWriter, "    ", dumpFilter);
                    }
                    printWriter.println("  mArchive=" + this.mArchive.toString());
                    this.mArchive.dumpImpl(printWriter, dumpFilter);
                    if (!z) {
                        int size3 = this.mEnqueuedNotifications.size();
                        if (size3 > 0) {
                            printWriter.println("  Enqueued Notification List:");
                            for (int i3 = 0; i3 < size3; i3++) {
                                com.android.server.notification.NotificationRecord notificationRecord = this.mEnqueuedNotifications.get(i3);
                                if (!dumpFilter.filtered || dumpFilter.matches(notificationRecord.getSbn())) {
                                    notificationRecord.dump(printWriter, "    ", getContext(), dumpFilter.redact);
                                }
                            }
                            printWriter.println("  ");
                        }
                        this.mSnoozeHelper.dump(printWriter, dumpFilter);
                    }
                } finally {
                }
            }
            if (!z) {
                printWriter.println("\n  Ranking Config:");
                this.mRankingHelper.dump(printWriter, "    ", dumpFilter);
                printWriter.println("\n Notification Preferences:");
                this.mPreferencesHelper.dump(printWriter, "    ", dumpFilter, arrayMap);
                printWriter.println("\n  Notification listeners:");
                this.mListeners.dump(printWriter, dumpFilter);
                printWriter.print("    mListenerHints: ");
                printWriter.println(this.mListenerHints);
                printWriter.print("    mListenersDisablingEffects: (");
                int size4 = this.mListenersDisablingEffects.size();
                for (int i4 = 0; i4 < size4; i4++) {
                    int keyAt = this.mListenersDisablingEffects.keyAt(i4);
                    if (i4 > 0) {
                        printWriter.print(';');
                    }
                    printWriter.print("hint[" + keyAt + "]:");
                    android.util.ArraySet<android.content.ComponentName> valueAt = this.mListenersDisablingEffects.valueAt(i4);
                    int size5 = valueAt.size();
                    for (int i5 = 0; i5 < size5; i5++) {
                        if (i5 > 0) {
                            printWriter.print(',');
                        }
                        android.content.ComponentName valueAt2 = valueAt.valueAt(i5);
                        if (valueAt2 != null) {
                            printWriter.print(valueAt2);
                        }
                    }
                }
                printWriter.println(')');
                printWriter.println("\n  Notification assistant services:");
                this.mAssistants.dump(printWriter, dumpFilter);
            }
            if (!dumpFilter.filtered || z) {
                printWriter.println("\n  Zen Mode:");
                printWriter.print("    mInterruptionFilter=");
                printWriter.println(this.mInterruptionFilter);
                this.mZenModeHelper.dump(printWriter, "    ");
                printWriter.println("\n  Zen Log:");
                com.android.server.notification.ZenLog.dump(printWriter, "    ");
            }
            printWriter.println("\n  Condition providers:");
            this.mConditionProviders.dump(printWriter, dumpFilter);
            printWriter.println("\n  Group summaries:");
            for (java.util.Map.Entry<java.lang.String, com.android.server.notification.NotificationRecord> entry : this.mSummaryByGroupKey.entrySet()) {
                com.android.server.notification.NotificationRecord value = entry.getValue();
                printWriter.println("    " + entry.getKey() + " -> " + value.getKey());
                if (this.mNotificationsByKey.get(value.getKey()) != value) {
                    printWriter.println("!!!!!!LEAK: Record not found in mNotificationsByKey.");
                    value.dump(printWriter, "      ", getContext(), dumpFilter.redact);
                }
            }
            if (!z) {
                printWriter.println("\n  Usage Stats:");
                this.mUsageStats.dump(printWriter, "    ", dumpFilter);
            }
        }
    }

    /* renamed from: com.android.server.notification.NotificationManagerService$14, reason: invalid class name */
    class AnonymousClass14 implements com.android.server.notification.NotificationManagerInternal {
        AnonymousClass14() {
        }

        @Override // com.android.server.notification.NotificationManagerInternal
        public android.app.NotificationChannel getNotificationChannel(java.lang.String str, int i, java.lang.String str2) {
            return com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.getNotificationChannel(str, i, str2, false);
        }

        @Override // com.android.server.notification.NotificationManagerInternal
        public android.app.NotificationChannelGroup getNotificationChannelGroup(java.lang.String str, int i, java.lang.String str2) {
            return com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.getGroupForChannel(str, i, str2);
        }

        @Override // com.android.server.notification.NotificationManagerInternal
        public void enqueueNotification(java.lang.String str, java.lang.String str2, int i, int i2, java.lang.String str3, int i3, android.app.Notification notification, int i4) {
            com.android.server.notification.NotificationManagerService.this.enqueueNotificationInternal(str, str2, i, i2, str3, i3, notification, i4, false);
        }

        @Override // com.android.server.notification.NotificationManagerInternal
        public void enqueueNotification(java.lang.String str, java.lang.String str2, int i, int i2, java.lang.String str3, int i3, android.app.Notification notification, int i4, boolean z) {
            com.android.server.notification.NotificationManagerService.this.enqueueNotificationInternal(str, str2, i, i2, str3, i3, notification, i4, z);
        }

        @Override // com.android.server.notification.NotificationManagerInternal
        public void cancelNotification(java.lang.String str, java.lang.String str2, int i, int i2, java.lang.String str3, int i3, int i4) {
            com.android.server.notification.NotificationManagerService.this.cancelNotificationInternal(str, str2, i, i2, str3, i3, i4, com.android.server.notification.NotificationManagerService.this.isCallingUidSystem() ? 0 : 33856);
        }

        @Override // com.android.server.notification.NotificationManagerInternal
        public boolean isNotificationShown(java.lang.String str, java.lang.String str2, int i, int i2) {
            return com.android.server.notification.NotificationManagerService.this.isNotificationShownInternal(str, str2, i, i2);
        }

        @Override // com.android.server.notification.NotificationManagerInternal
        public void removeForegroundServiceFlagFromNotification(final java.lang.String str, final int i, final int i2) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystem();
            com.android.server.notification.NotificationManagerService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.notification.NotificationManagerService$14$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.notification.NotificationManagerService.AnonymousClass14.this.lambda$removeForegroundServiceFlagFromNotification$0(str, i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$removeForegroundServiceFlagFromNotification$0(java.lang.String str, int i, int i2) {
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                removeFlagFromNotificationLocked(str, i, i2, 64);
            }
        }

        @Override // com.android.server.notification.NotificationManagerInternal
        public void removeUserInitiatedJobFlagFromNotification(final java.lang.String str, final int i, final int i2) {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystem();
            com.android.server.notification.NotificationManagerService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.notification.NotificationManagerService$14$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.notification.NotificationManagerService.AnonymousClass14.this.lambda$removeUserInitiatedJobFlagFromNotification$1(str, i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$removeUserInitiatedJobFlagFromNotification$1(java.lang.String str, int i, int i2) {
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                removeFlagFromNotificationLocked(str, i, i2, 32768);
            }
        }

        @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
        private void removeFlagFromNotificationLocked(java.lang.String str, int i, int i2, int i3) {
            boolean z;
            if (com.android.server.notification.NotificationManagerService.this.getNotificationCount(str, i2) <= 50) {
                z = false;
            } else {
                com.android.server.notification.NotificationManagerService.this.mUsageStats.registerOverCountQuota(str);
                z = true;
            }
            if (z) {
                com.android.server.notification.NotificationRecord findNotificationLocked = com.android.server.notification.NotificationManagerService.this.findNotificationLocked(str, null, i, i2);
                if (findNotificationLocked != null) {
                    if (com.android.server.notification.NotificationManagerService.DBG) {
                        java.lang.String str2 = i3 == 64 ? "FGS" : "UIJ";
                        android.util.Slog.d(com.android.server.notification.NotificationManagerService.TAG, "Remove " + str2 + " flag not allow. Cancel " + str2 + " notification");
                    }
                    com.android.server.notification.NotificationManagerService.this.removeFromNotificationListsLocked(findNotificationLocked);
                    com.android.server.notification.NotificationManagerService.this.cancelNotificationLocked(findNotificationLocked, false, 8, true, null, android.os.SystemClock.elapsedRealtime());
                    return;
                }
                return;
            }
            java.util.List findNotificationsByListLocked = com.android.server.notification.NotificationManagerService.findNotificationsByListLocked(com.android.server.notification.NotificationManagerService.this.mEnqueuedNotifications, str, null, i, i2);
            for (int i4 = 0; i4 < findNotificationsByListLocked.size(); i4++) {
                com.android.server.notification.NotificationRecord notificationRecord = (com.android.server.notification.NotificationRecord) findNotificationsByListLocked.get(i4);
                if (notificationRecord != null) {
                    notificationRecord.getSbn().getNotification().flags = notificationRecord.mOriginalFlags & (~i3);
                }
            }
            com.android.server.notification.NotificationRecord findNotificationByListLocked = com.android.server.notification.NotificationManagerService.findNotificationByListLocked(com.android.server.notification.NotificationManagerService.this.mNotificationList, str, null, i, i2);
            if (findNotificationByListLocked != null) {
                findNotificationByListLocked.getSbn().getNotification().flags = findNotificationByListLocked.mOriginalFlags & (~i3);
                com.android.server.notification.NotificationManagerService.this.mRankingHelper.sort(com.android.server.notification.NotificationManagerService.this.mNotificationList);
                com.android.server.notification.NotificationManagerService.this.mListeners.notifyPostedLocked(findNotificationByListLocked, findNotificationByListLocked);
            }
        }

        @Override // com.android.server.notification.NotificationManagerInternal
        public void onConversationRemoved(java.lang.String str, int i, java.util.Set<java.lang.String> set) {
            com.android.server.notification.NotificationManagerService.this.onConversationRemovedInternal(str, i, set);
        }

        @Override // com.android.server.notification.NotificationManagerInternal
        public int getNumNotificationChannelsForPackage(java.lang.String str, int i, boolean z) {
            return com.android.server.notification.NotificationManagerService.this.getNumNotificationChannelsForPackage(str, i, z);
        }

        @Override // com.android.server.notification.NotificationManagerInternal
        public boolean areNotificationsEnabledForPackage(java.lang.String str, int i) {
            return com.android.server.notification.NotificationManagerService.this.areNotificationsEnabledForPackageInt(str, i);
        }

        @Override // com.android.server.notification.NotificationManagerInternal
        public void sendReviewPermissionsNotification() {
            if (!com.android.server.notification.NotificationManagerService.this.mShowReviewPermissionsNotification) {
                return;
            }
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystem();
            ((android.app.NotificationManager) com.android.server.notification.NotificationManagerService.this.getContext().getSystemService(android.app.NotificationManager.class)).notify(com.android.server.notification.NotificationManagerService.TAG, 71, com.android.server.notification.NotificationManagerService.this.createReviewPermissionsNotification());
            android.provider.Settings.Global.putInt(com.android.server.notification.NotificationManagerService.this.getContext().getContentResolver(), "review_permissions_notification_state", 3);
        }

        @Override // com.android.server.notification.NotificationManagerInternal
        public void cleanupHistoryFiles() {
            com.android.server.notification.NotificationManagerService.this.checkCallerIsSystem();
            com.android.server.notification.NotificationManagerService.this.mHistoryManager.cleanupHistoryFiles();
        }

        @Override // com.android.server.notification.NotificationManagerInternal
        public void removeBitmaps() {
            long millis;
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                try {
                    java.util.Iterator<com.android.server.notification.NotificationRecord> it = com.android.server.notification.NotificationManagerService.this.mNotificationList.iterator();
                    while (it.hasNext()) {
                        com.android.server.notification.NotificationRecord next = it.next();
                        long postTime = next.getSbn().getPostTime();
                        long currentTimeMillis = java.lang.System.currentTimeMillis();
                        if (com.android.server.notification.NotificationManagerService.this.mFlagResolver.isEnabled(com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.NotificationFlags.DEBUG_SHORT_BITMAP_DURATION)) {
                            millis = java.time.Duration.ofSeconds(5L).toMillis();
                        } else {
                            millis = com.android.server.notification.NotificationManagerService.BITMAP_DURATION.toMillis();
                        }
                        if (com.android.server.notification.NotificationManagerService.isBitmapExpired(postTime, currentTimeMillis, millis)) {
                            com.android.server.notification.NotificationManagerService.this.removeBitmapAndRepost(next);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.notification.NotificationManagerInternal
        public void setDeviceEffectsApplier(android.service.notification.DeviceEffectsApplier deviceEffectsApplier) {
            if (!android.app.Flags.modesApi()) {
                return;
            }
            if (com.android.server.notification.NotificationManagerService.this.mZenModeHelper == null) {
                throw new java.lang.IllegalStateException("ZenModeHelper is not yet ready!");
            }
            com.android.server.notification.NotificationManagerService.this.mZenModeHelper.setDeviceEffectsApplier(deviceEffectsApplier);
        }
    }

    private static boolean isBigPictureWithBitmapOrIcon(android.app.Notification notification) {
        if (!notification.isStyle(android.app.Notification.BigPictureStyle.class)) {
            return false;
        }
        if (notification.extras.containsKey("android.picture") && notification.extras.getParcelable("android.picture") != null) {
            return true;
        }
        return notification.extras.containsKey("android.pictureIcon") && notification.extras.getParcelable("android.pictureIcon") != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isBitmapExpired(long j, long j2, long j3) {
        return j2 - j > j3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeBitmapAndRepost(com.android.server.notification.NotificationRecord notificationRecord) {
        if (!isBigPictureWithBitmapOrIcon(notificationRecord.getNotification())) {
            return;
        }
        notificationRecord.getNotification().extras.putParcelable("android.picture", null);
        notificationRecord.getNotification().extras.putParcelable("android.pictureIcon", null);
        notificationRecord.getNotification().flags |= 8;
        enqueueNotificationInternal(notificationRecord.getSbn().getPackageName(), notificationRecord.getSbn().getOpPkg(), notificationRecord.getSbn().getUid(), notificationRecord.getSbn().getInitialPid(), notificationRecord.getSbn().getTag(), notificationRecord.getSbn().getId(), notificationRecord.getNotification(), notificationRecord.getSbn().getUserId(), true, false);
    }

    int getNumNotificationChannelsForPackage(java.lang.String str, int i, boolean z) {
        return this.mPreferencesHelper.getNotificationChannels(str, i, z).getList().size();
    }

    void cancelNotificationInternal(java.lang.String str, java.lang.String str2, int i, int i2, java.lang.String str3, int i3, int i4, int i5) {
        int handleIncomingUser = android.app.ActivityManager.handleIncomingUser(i2, i, i4, true, false, "cancelNotificationWithTag", str);
        int resolveNotificationUid = resolveNotificationUid(str2, str, i, handleIncomingUser);
        if (resolveNotificationUid == -1) {
            android.util.Slog.w(TAG, str2 + ":" + i + " trying to cancel notification for nonexistent pkg " + str + " in user " + handleIncomingUser);
            return;
        }
        if (!java.util.Objects.equals(str, str2)) {
            synchronized (this.mNotificationLock) {
                try {
                    com.android.server.notification.NotificationRecord findNotificationLocked = findNotificationLocked(str, str3, i3, handleIncomingUser);
                    if (findNotificationLocked != null && !java.util.Objects.equals(str2, findNotificationLocked.getSbn().getOpPkg())) {
                        throw new java.lang.SecurityException(str2 + " does not have permission to cancel a notification they did not post " + str3 + " " + i3);
                    }
                } finally {
                }
            }
        }
        cancelNotification(resolveNotificationUid, i2, str, str3, i3, 0, i5, false, handleIncomingUser, 8, null);
    }

    boolean isNotificationShownInternal(java.lang.String str, java.lang.String str2, int i, int i2) {
        boolean z;
        synchronized (this.mNotificationLock) {
            z = findNotificationLocked(str, str2, i, i2) != null;
        }
        return z;
    }

    void enqueueNotificationInternal(java.lang.String str, java.lang.String str2, int i, int i2, java.lang.String str3, int i3, android.app.Notification notification, int i4, boolean z) {
        enqueueNotificationInternal(str, str2, i, i2, str3, i3, notification, i4, false, z);
    }

    void enqueueNotificationInternal(java.lang.String str, java.lang.String str2, int i, int i2, java.lang.String str3, int i3, android.app.Notification notification, int i4, boolean z, boolean z2) {
        com.android.server.notification.NotificationManagerService.PostNotificationTracker acquireWakeLockForPost = acquireWakeLockForPost(str, i);
        try {
            if (!enqueueNotificationInternal(str, str2, i, i2, str3, i3, notification, i4, z, acquireWakeLockForPost, z2)) {
            }
        } finally {
            acquireWakeLockForPost.cancel();
        }
    }

    private com.android.server.notification.NotificationManagerService.PostNotificationTracker acquireWakeLockForPost(final java.lang.String str, final int i) {
        return (com.android.server.notification.NotificationManagerService.PostNotificationTracker) android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.notification.NotificationManagerService$$ExternalSyntheticLambda11
            public final java.lang.Object getOrThrow() {
                com.android.server.notification.NotificationManagerService.PostNotificationTracker lambda$acquireWakeLockForPost$7;
                lambda$acquireWakeLockForPost$7 = com.android.server.notification.NotificationManagerService.this.lambda$acquireWakeLockForPost$7(str, i);
                return lambda$acquireWakeLockForPost$7;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.server.notification.NotificationManagerService.PostNotificationTracker lambda$acquireWakeLockForPost$7(java.lang.String str, int i) throws java.lang.Exception {
        android.os.PowerManager.WakeLock newWakeLock = this.mPowerManager.newWakeLock(1, "NotificationManagerService:post:" + str);
        newWakeLock.setWorkSource(new android.os.WorkSource(i, str));
        newWakeLock.acquire(POST_WAKE_LOCK_TIMEOUT.toMillis());
        return this.mPostNotificationTrackerFactory.newTracker(newWakeLock);
    }

    private boolean enqueueNotificationInternal(java.lang.String str, java.lang.String str2, int i, int i2, java.lang.String str3, int i3, android.app.Notification notification, int i4, boolean z, com.android.server.notification.NotificationManagerService.PostNotificationTracker postNotificationTracker, boolean z2) {
        java.lang.String str4;
        com.android.server.notification.NotificationRecord notificationRecord;
        android.os.UserHandle userHandle;
        android.content.pm.ShortcutInfo shortcutInfo;
        int size;
        if (DBG) {
            android.util.Slog.v(TAG, "enqueueNotificationInternal: pkg=" + str + " id=" + i3 + " notification=" + notification);
        }
        if (str == null || notification == null) {
            throw new java.lang.IllegalArgumentException("null not allowed: pkg=" + str + " id=" + i3 + " notification=" + notification);
        }
        int handleIncomingUser = android.app.ActivityManager.handleIncomingUser(i2, i, i4, true, false, "enqueueNotification", str);
        android.os.UserHandle of = android.os.UserHandle.of(handleIncomingUser);
        int resolveNotificationUid = resolveNotificationUid(str2, str, i, handleIncomingUser);
        if (resolveNotificationUid == -1) {
            throw new java.lang.SecurityException("Caller " + str2 + ":" + i + " trying to post for invalid pkg " + str + " in user " + i4);
        }
        checkRestrictedCategories(notification);
        android.app.ActivityManagerInternal.ServiceNotificationPolicy applyForegroundServiceNotification = this.mAmi.applyForegroundServiceNotification(notification, str3, i3, str, handleIncomingUser);
        try {
            fixNotification(notification, str, str3, i3, handleIncomingUser, resolveNotificationUid, applyForegroundServiceNotification, ((com.android.server.job.JobSchedulerInternal) com.android.server.LocalServices.getService(com.android.server.job.JobSchedulerInternal.class)) != null ? !r1.isNotificationAssociatedWithAnyUserInitiatedJobs(i3, handleIncomingUser, str) : true);
            if (applyForegroundServiceNotification == android.app.ActivityManagerInternal.ServiceNotificationPolicy.UPDATE_ONLY && !isNotificationShownInternal(str, str3, i3, handleIncomingUser)) {
                reportForegroundServiceUpdate(false, notification, i3, str, handleIncomingUser);
                return false;
            }
            this.mUsageStats.registerEnqueuedByApp(str);
            android.service.notification.StatusBarNotification statusBarNotification = new android.service.notification.StatusBarNotification(str, str2, i3, str3, resolveNotificationUid, i2, notification, of, (java.lang.String) null, java.lang.System.currentTimeMillis());
            java.lang.String channelId = notification.getChannelId();
            if (this.mIsTelevision && new android.app.Notification.TvExtender(notification).getChannelId() != null) {
                str4 = new android.app.Notification.TvExtender(notification).getChannelId();
            } else {
                str4 = channelId;
            }
            android.app.NotificationChannel notificationChannelRestoreDeleted = getNotificationChannelRestoreDeleted(str, i, resolveNotificationUid, str4, statusBarNotification.getShortcutId());
            if (notificationChannelRestoreDeleted == null) {
                android.util.Slog.e(TAG, "No Channel found for pkg=" + str + ", channelId=" + str4 + ", id=" + i3 + ", tag=" + str3 + ", opPkg=" + str2 + ", callingUid=" + i + ", userId=" + handleIncomingUser + ", incomingUserId=" + i4 + ", notificationUid=" + resolveNotificationUid + ", notification=" + notification);
                if (!(!this.mPermissionHelper.hasPermission(resolveNotificationUid))) {
                    doChannelWarningToast(resolveNotificationUid, "Developer warning for package \"" + str + "\"\nFailed to post notification on channel \"" + str4 + "\"\nSee log for more details");
                    return false;
                }
                return false;
            }
            com.android.server.notification.NotificationRecord notificationRecord2 = new com.android.server.notification.NotificationRecord(getContext(), statusBarNotification, notificationChannelRestoreDeleted);
            notificationRecord2.setIsAppImportanceLocked(this.mPermissionHelper.isPermissionUserSet(str, handleIncomingUser));
            notificationRecord2.setPostSilently(z);
            notificationRecord2.setFlagBubbleRemoved(false);
            notificationRecord2.setPkgAllowedAsConvo(this.mMsgPkgsAllowedAsConvos.contains(str));
            notificationRecord2.setImportanceFixed(this.mPermissionHelper.isPermissionFixed(str, handleIncomingUser));
            if (!notification.isFgsOrUij()) {
                notificationRecord = notificationRecord2;
            } else if (((notificationChannelRestoreDeleted.getUserLockedFields() & 4) == 0 || !notificationChannelRestoreDeleted.isUserVisibleTaskShown()) && (notificationRecord2.getImportance() == 1 || notificationRecord2.getImportance() == 0)) {
                notificationChannelRestoreDeleted.setImportance(2);
                notificationRecord2.setSystemImportance(2);
                if (!notificationChannelRestoreDeleted.isUserVisibleTaskShown()) {
                    notificationChannelRestoreDeleted.unlockFields(4);
                    notificationChannelRestoreDeleted.setUserVisibleTaskShown(true);
                }
                notificationRecord = notificationRecord2;
                this.mPreferencesHelper.updateNotificationChannel(str, resolveNotificationUid, notificationChannelRestoreDeleted, false, i, isCallerSystemOrSystemUi());
                notificationRecord.updateNotificationChannel(notificationChannelRestoreDeleted);
            } else if (notificationChannelRestoreDeleted.isUserVisibleTaskShown() || android.text.TextUtils.isEmpty(str4)) {
                notificationRecord = notificationRecord2;
            } else if ("miscellaneous".equals(str4)) {
                notificationRecord = notificationRecord2;
            } else {
                notificationChannelRestoreDeleted.setUserVisibleTaskShown(true);
                notificationRecord2.updateNotificationChannel(notificationChannelRestoreDeleted);
                notificationRecord = notificationRecord2;
            }
            if (this.mShortcutHelper != null) {
                userHandle = of;
                shortcutInfo = this.mShortcutHelper.getValidShortcutInfo(notification.getShortcutId(), str, userHandle);
            } else {
                userHandle = of;
                shortcutInfo = null;
            }
            if (notification.getShortcutId() != null && shortcutInfo == null) {
                android.util.Slog.w(TAG, "notification " + notificationRecord.getKey() + " added an invalid shortcut");
            }
            notificationRecord.setShortcutInfo(shortcutInfo);
            notificationRecord.setHasSentValidMsg(this.mPreferencesHelper.hasSentValidMsg(str, resolveNotificationUid));
            notificationRecord.userDemotedAppFromConvoSpace(this.mPreferencesHelper.hasUserDemotedInvalidMsgApp(str, resolveNotificationUid));
            android.content.pm.ShortcutInfo shortcutInfo2 = shortcutInfo;
            android.os.UserHandle userHandle2 = userHandle;
            if (!checkDisqualifyingFeatures(handleIncomingUser, resolveNotificationUid, i3, str3, notificationRecord, notificationRecord.getSbn().getOverrideGroupKey() != null, z2)) {
                return false;
            }
            this.mUsageStats.registerEnqueuedByAppAndAccepted(str);
            if (shortcutInfo2 != null) {
                this.mShortcutHelper.cacheShortcut(shortcutInfo2, userHandle2);
            }
            if (notification.allPendingIntents != null && (size = notification.allPendingIntents.size()) > 0) {
                long notificationAllowlistDuration = ((com.android.server.DeviceIdleInternal) com.android.server.LocalServices.getService(com.android.server.DeviceIdleInternal.class)).getNotificationAllowlistDuration();
                for (int i5 = 0; i5 < size; i5++) {
                    android.app.PendingIntent pendingIntent = (android.app.PendingIntent) notification.allPendingIntents.valueAt(i5);
                    if (pendingIntent != null) {
                        this.mAmi.setPendingIntentAllowlistDuration(pendingIntent.getTarget(), ALLOWLIST_TOKEN, notificationAllowlistDuration, 0, 310, "NotificationManagerService");
                        this.mAmi.setPendingIntentAllowBgActivityStarts(pendingIntent.getTarget(), ALLOWLIST_TOKEN, 7);
                    }
                }
            }
            this.mHandler.post(new com.android.server.notification.NotificationManagerService.EnqueueNotificationRunnable(handleIncomingUser, notificationRecord, getPackageImportanceWithIdentity(str) == 100, postNotificationTracker));
            return true;
        } catch (java.lang.Exception e) {
            if (notification.isForegroundService()) {
                throw new java.lang.SecurityException("Invalid FGS notification", e);
            }
            android.util.Slog.e(TAG, "Cannot fix notification", e);
            return false;
        }
    }

    @android.annotation.Nullable
    private android.app.NotificationChannel getNotificationChannelRestoreDeleted(java.lang.String str, int i, int i2, java.lang.String str2, java.lang.String str3) {
        android.app.NotificationChannel conversationNotificationChannel = this.mPreferencesHelper.getConversationNotificationChannel(str, i2, str2, str3, true, !android.text.TextUtils.isEmpty(str3));
        if (conversationNotificationChannel != null && conversationNotificationChannel.isDeleted()) {
            if (java.util.Objects.equals(str3, conversationNotificationChannel.getConversationId())) {
                if (this.mPreferencesHelper.createNotificationChannel(str, i2, conversationNotificationChannel, true, this.mConditionProviders.isPackageOrComponentAllowed(str, android.os.UserHandle.getUserId(i2)), i, true)) {
                    handleSavePolicyFile();
                    return conversationNotificationChannel;
                }
                return conversationNotificationChannel;
            }
            return null;
        }
        return conversationNotificationChannel;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onConversationRemovedInternal(java.lang.String str, int i, java.util.Set<java.lang.String> set) {
        checkCallerIsSystem();
        com.android.internal.util.Preconditions.checkStringNotEmpty(str);
        this.mHistoryManager.deleteConversations(str, i, set);
        java.util.Iterator<java.lang.String> it = this.mPreferencesHelper.deleteConversations(str, i, set, 1000, true).iterator();
        while (it.hasNext()) {
            cancelAllNotificationsInt(MY_UID, MY_PID, str, it.next(), 0, 0, android.os.UserHandle.getUserId(i), 20);
        }
        handleSavePolicyFile();
    }

    private void makeStickyHun(android.app.Notification notification, java.lang.String str, int i) {
        if (this.mPermissionHelper.hasRequestedPermission("android.permission.USE_FULL_SCREEN_INTENT", str, i)) {
            notification.flags |= 16384;
        }
        if (notification.contentIntent == null) {
            notification.contentIntent = notification.fullScreenIntent;
        }
        notification.fullScreenIntent = null;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void fixNotification(android.app.Notification notification, java.lang.String str, java.lang.String str2, int i, int i2, int i3, android.app.ActivityManagerInternal.ServiceNotificationPolicy serviceNotificationPolicy, boolean z) throws android.content.pm.PackageManager.NameNotFoundException, android.os.RemoteException {
        android.content.pm.ApplicationInfo applicationInfoAsUser = this.mPackageManagerClient.getApplicationInfoAsUser(str, 268435456, i2 == -1 ? 0 : i2);
        android.app.Notification.addFieldsFromContext(applicationInfoAsUser, notification);
        if (notification.isForegroundService() && serviceNotificationPolicy == android.app.ActivityManagerInternal.ServiceNotificationPolicy.NOT_FOREGROUND_SERVICE) {
            notification.flags &= -65;
        }
        if (notification.isUserInitiatedJob() && z) {
            notification.flags &= -32769;
        }
        if (notification.isFgsOrUij()) {
            notification.flags &= -17;
        }
        if ((notification.flags & 2) > 0 && canBeNonDismissible(applicationInfoAsUser, notification)) {
            notification.flags |= 8192;
        } else {
            notification.flags &= -8193;
        }
        if (getContext().checkPermission("android.permission.USE_COLORIZED_NOTIFICATIONS", -1, i3) == 0) {
            notification.flags |= 2048;
        } else {
            notification.flags &= -2049;
        }
        if (notification.extras.getBoolean("android.allowDuringSetup", false) && getContext().checkPermission("android.permission.NOTIFICATION_DURING_SETUP", -1, i3) != 0) {
            notification.extras.remove("android.allowDuringSetup");
            if (DBG) {
                android.util.Slog.w(TAG, "warning: pkg " + str + " attempting to show during setup without holding perm android.permission.NOTIFICATION_DURING_SETUP");
            }
        }
        notification.flags &= -16385;
        if (android.app.Flags.lifetimeExtensionRefactor()) {
            notification.flags &= -65537;
        }
        boolean z2 = true;
        if (notification.fullScreenIntent != null && !checkUseFullScreenIntentPermission(new android.content.AttributionSource.Builder(i3).setPackageName(str).build(), applicationInfoAsUser, true)) {
            makeStickyHun(notification, str, i2);
        }
        if (notification.actions != null) {
            int length = notification.actions.length;
            int i4 = 0;
            while (true) {
                if (i4 >= length) {
                    z2 = false;
                    break;
                } else if (notification.actions[i4] == null) {
                    break;
                } else {
                    i4++;
                }
            }
            if (z2) {
                java.util.ArrayList arrayList = new java.util.ArrayList();
                for (int i5 = 0; i5 < length; i5++) {
                    if (notification.actions[i5] != null) {
                        arrayList.add(notification.actions[i5]);
                    }
                }
                if (arrayList.size() != 0) {
                    notification.actions = (android.app.Notification.Action[]) arrayList.toArray(new android.app.Notification.Action[0]);
                } else {
                    notification.actions = null;
                }
            }
        }
        if (notification.isStyle(android.app.Notification.CallStyle.class)) {
            java.util.ArrayList actionsListWithSystemActions = ((android.app.Notification.CallStyle) android.app.Notification.Builder.recoverBuilder(getContext(), notification).getStyle()).getActionsListWithSystemActions();
            notification.actions = new android.app.Notification.Action[actionsListWithSystemActions.size()];
            actionsListWithSystemActions.toArray(notification.actions);
        }
        if (notification.isStyle(android.app.Notification.MediaStyle.class) || notification.isStyle(android.app.Notification.DecoratedMediaCustomViewStyle.class)) {
            if (getContext().checkPermission("android.permission.MEDIA_CONTENT_CONTROL", -1, i3) != 0) {
                notification.extras.remove("android.mediaRemoteDevice");
                notification.extras.remove("android.mediaRemoteIcon");
                notification.extras.remove("android.mediaRemoteIntent");
                if (DBG) {
                    android.util.Slog.w(TAG, "Package " + str + ": Use of setRemotePlayback requires the MEDIA_CONTENT_CONTROL permission");
                }
            }
            if (android.app.compat.CompatChanges.isChangeEnabled(ENFORCE_NO_CLEAR_FLAG_ON_MEDIA_NOTIFICATION, i3)) {
                notification.flags |= 32;
            }
        }
        if (notification.extras.containsKey("android.substName") && getContext().checkPermission("android.permission.SUBSTITUTE_NOTIFICATION_APP_NAME", -1, i3) != 0) {
            notification.extras.remove("android.substName");
            if (DBG) {
                android.util.Slog.w(TAG, "warning: pkg " + str + " attempting to substitute app name without holding perm android.permission.SUBSTITUTE_NOTIFICATION_APP_NAME");
            }
        }
        notification.overrideAllowlistToken(ALLOWLIST_TOKEN);
        checkRemoteViews(str, str2, i, notification);
    }

    private boolean canBeNonDismissible(android.content.pm.ApplicationInfo applicationInfo, android.app.Notification notification) {
        return notification.isMediaNotification() || isEnterpriseExempted(applicationInfo) || notification.isStyle(android.app.Notification.CallStyle.class) || isDefaultSearchSelectorPackage(applicationInfo.packageName);
    }

    private boolean isDefaultSearchSelectorPackage(java.lang.String str) {
        return java.util.Objects.equals(this.mDefaultSearchSelectorPkg, str);
    }

    private boolean isEnterpriseExempted(android.content.pm.ApplicationInfo applicationInfo) {
        if (this.mDpm == null || !(this.mDpm.isActiveProfileOwner(applicationInfo.uid) || this.mDpm.isActiveDeviceOwner(applicationInfo.uid))) {
            return this.mSystemExemptFromDismissal && this.mAppOps.checkOpNoThrow(125, applicationInfo.uid, applicationInfo.packageName) == 0;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkUseFullScreenIntentPermission(@android.annotation.NonNull android.content.AttributionSource attributionSource, @android.annotation.NonNull android.content.pm.ApplicationInfo applicationInfo, boolean z) {
        if (applicationInfo.targetSdkVersion < 29) {
            return true;
        }
        return (z ? this.mPermissionManager.checkPermissionForDataDelivery("android.permission.USE_FULL_SCREEN_INTENT", attributionSource, (java.lang.String) null) : this.mPermissionManager.checkPermissionForPreflight("android.permission.USE_FULL_SCREEN_INTENT", attributionSource)) == 0;
    }

    private void checkRemoteViews(java.lang.String str, java.lang.String str2, int i, android.app.Notification notification) {
        if (removeRemoteView(str, str2, i, notification.contentView)) {
            notification.contentView = null;
        }
        if (removeRemoteView(str, str2, i, notification.bigContentView)) {
            notification.bigContentView = null;
        }
        if (removeRemoteView(str, str2, i, notification.headsUpContentView)) {
            notification.headsUpContentView = null;
        }
        if (notification.publicVersion != null) {
            if (removeRemoteView(str, str2, i, notification.publicVersion.contentView)) {
                notification.publicVersion.contentView = null;
            }
            if (removeRemoteView(str, str2, i, notification.publicVersion.bigContentView)) {
                notification.publicVersion.bigContentView = null;
            }
            if (removeRemoteView(str, str2, i, notification.publicVersion.headsUpContentView)) {
                notification.publicVersion.headsUpContentView = null;
            }
        }
    }

    private boolean removeRemoteView(java.lang.String str, java.lang.String str2, int i, android.widget.RemoteViews remoteViews) {
        if (remoteViews == null) {
            return false;
        }
        int estimateMemoryUsage = remoteViews.estimateMemoryUsage();
        if (estimateMemoryUsage > this.mWarnRemoteViewsSizeBytes && estimateMemoryUsage < this.mStripRemoteViewsSizeBytes) {
            android.util.Slog.w(TAG, "RemoteViews too large on pkg: " + str + " tag: " + str2 + " id: " + i + " this might be stripped in a future release");
        }
        if (estimateMemoryUsage < this.mStripRemoteViewsSizeBytes) {
            return false;
        }
        this.mUsageStats.registerImageRemoved(str);
        android.util.Slog.w(TAG, "Removed too large RemoteViews (" + estimateMemoryUsage + " bytes) on pkg: " + str + " tag: " + str2 + " id: " + i);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateNotificationBubbleFlags(com.android.server.notification.NotificationRecord notificationRecord, boolean z) {
        android.app.Notification.BubbleMetadata bubbleMetadata = notificationRecord.getNotification().getBubbleMetadata();
        if (bubbleMetadata == null) {
            return;
        }
        if (!z) {
            bubbleMetadata.setFlags(bubbleMetadata.getFlags() & (-2));
        }
        if (!bubbleMetadata.isBubbleSuppressable()) {
            bubbleMetadata.setFlags(bubbleMetadata.getFlags() & (-9));
        }
    }

    protected void doChannelWarningToast(int i, final java.lang.CharSequence charSequence) {
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.notification.NotificationManagerService$$ExternalSyntheticLambda13
            public final void runOrThrow() {
                com.android.server.notification.NotificationManagerService.this.lambda$doChannelWarningToast$8(charSequence);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$doChannelWarningToast$8(java.lang.CharSequence charSequence) throws java.lang.Exception {
        if (android.provider.Settings.Global.getInt(getContext().getContentResolver(), "show_notification_channel_warnings", 0) != 0) {
            android.widget.Toast.makeText(getContext(), this.mHandler.getLooper(), charSequence, 0).show();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    int resolveNotificationUid(java.lang.String str, java.lang.String str2, int i, int i2) {
        int i3 = -1;
        if (i2 == -1) {
            i2 = 0;
        }
        if (isCallerSameApp(str2, i, i2) && (android.text.TextUtils.equals(str, str2) || isCallerSameApp(str, i, i2))) {
            return i;
        }
        try {
            i3 = this.mPackageManagerClient.getPackageUidAsUser(str2, i2);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
        }
        if (isCallerAndroid(str, i) || this.mPreferencesHelper.isDelegateAllowed(str2, i3, str, i)) {
            return i3;
        }
        throw new java.lang.SecurityException("Caller " + str + ":" + i + " cannot post for pkg " + str2 + " in user " + i2);
    }

    public boolean hasFlag(int i, int i2) {
        return (i & i2) != 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x00a6 A[Catch: all -> 0x0065, TryCatch #0 {all -> 0x0065, blocks: (B:12:0x0032, B:14:0x0042, B:17:0x0049, B:18:0x0064, B:20:0x0068, B:22:0x0078, B:27:0x008e, B:30:0x009a, B:32:0x00a6, B:34:0x00b9, B:35:0x00e9, B:37:0x00eb), top: B:11:0x0032 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    boolean checkDisqualifyingFeatures(int i, int i2, int i3, java.lang.String str, com.android.server.notification.NotificationRecord notificationRecord, boolean z, boolean z2) {
        boolean isRecordBlockedLocked;
        boolean z3;
        int notificationCount;
        float appEnqueueRate;
        android.app.Notification notification = notificationRecord.getNotification();
        java.lang.String packageName = notificationRecord.getSbn().getPackageName();
        boolean z4 = isUidSystemOrPhone(i2) || com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(packageName);
        boolean isListenerPackage = this.mListeners.isListenerPackage(packageName);
        if (!z4 && !isListenerPackage) {
            int callingUid = android.os.Binder.getCallingUid();
            synchronized (this.mNotificationLock) {
                try {
                    if (this.mNotificationsByKey.get(notificationRecord.getSbn().getKey()) == null && isCallerInstantApp(callingUid, i)) {
                        throw new java.lang.SecurityException("Instant app " + packageName + " cannot create notifications");
                    }
                    if (this.mNotificationsByKey.get(notificationRecord.getSbn().getKey()) == null && findNotificationByListLocked(this.mEnqueuedNotifications, notificationRecord.getSbn().getKey()) == null) {
                        z3 = false;
                        if (z3 && !notificationRecord.getNotification().hasCompletedProgress() && !z) {
                            appEnqueueRate = this.mUsageStats.getAppEnqueueRate(packageName);
                            if (appEnqueueRate > this.mMaxPackageEnqueueRate) {
                                this.mUsageStats.registerOverRateQuota(packageName);
                                long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                                if (elapsedRealtime - this.mLastOverRateLogTime > MIN_PACKAGE_OVERRATE_LOG_INTERVAL) {
                                    android.util.Slog.e(TAG, "Package enqueue rate is " + appEnqueueRate + ". Shedding " + notificationRecord.getSbn().getKey() + ". package=" + packageName);
                                    this.mLastOverRateLogTime = elapsedRealtime;
                                }
                                return false;
                            }
                        }
                        if (!notification.isFgsOrUij() && (notificationCount = getNotificationCount(packageName, i, i3, str)) >= 50) {
                            this.mUsageStats.registerOverCountQuota(packageName);
                            android.util.Slog.e(TAG, "Package has already posted or enqueued " + notificationCount + " notifications.  Not showing more.  package=" + packageName);
                            return false;
                        }
                    }
                    z3 = true;
                    if (z3) {
                        appEnqueueRate = this.mUsageStats.getAppEnqueueRate(packageName);
                        if (appEnqueueRate > this.mMaxPackageEnqueueRate) {
                        }
                    }
                    if (!notification.isFgsOrUij()) {
                        this.mUsageStats.registerOverCountQuota(packageName);
                        android.util.Slog.e(TAG, "Package has already posted or enqueued " + notificationCount + " notifications.  Not showing more.  package=" + packageName);
                        return false;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        if (notification.getBubbleMetadata() != null && notification.getBubbleMetadata().getIntent() != null && hasFlag(this.mAmi.getPendingIntentFlags(notification.getBubbleMetadata().getIntent().getTarget()), 67108864)) {
            throw new java.lang.IllegalArgumentException(notificationRecord.getKey() + " Not posted. PendingIntents attached to bubbles must be mutable");
        }
        if (notification.actions != null) {
            for (android.app.Notification.Action action : notification.actions) {
                if ((action.getRemoteInputs() != null || action.getDataOnlyRemoteInputs() != null) && hasFlag(this.mAmi.getPendingIntentFlags(action.actionIntent.getTarget()), 67108864)) {
                    throw new java.lang.IllegalArgumentException(notificationRecord.getKey() + " Not posted. PendingIntents attached to actions with remote inputs must be mutable");
                }
            }
        }
        if (notificationRecord.getSystemGeneratedSmartActions() != null) {
            java.util.Iterator<android.app.Notification.Action> it = notificationRecord.getSystemGeneratedSmartActions().iterator();
            while (it.hasNext()) {
                android.app.Notification.Action next = it.next();
                if (next.getRemoteInputs() != null || next.getDataOnlyRemoteInputs() != null) {
                    if (hasFlag(this.mAmi.getPendingIntentFlags(next.actionIntent.getTarget()), 67108864)) {
                        throw new java.lang.IllegalArgumentException(notificationRecord.getKey() + " Not posted. PendingIntents attached to contextual actions with remote inputs must be mutable");
                    }
                }
            }
        }
        if (notification.isStyle(android.app.Notification.CallStyle.class)) {
            boolean z5 = notification.fullScreenIntent != null;
            boolean z6 = (notification.flags & 16384) != 0;
            if (!notification.isFgsOrUij() && !z5 && !z6 && !z2) {
                throw new java.lang.IllegalArgumentException(notificationRecord.getKey() + " Not posted. CallStyle notifications must be for a foreground service or user initated job or use a fullScreenIntent.");
            }
        }
        if (this.mSnoozeHelper.isSnoozed(i, packageName, notificationRecord.getKey())) {
            com.android.internal.logging.MetricsLogger.action(notificationRecord.getLogMaker().setType(6).setCategory(831));
            this.mNotificationRecordLogger.log(com.android.server.notification.NotificationRecordLogger.NotificationEvent.NOTIFICATION_NOT_POSTED_SNOOZED, notificationRecord);
            if (DBG) {
                android.util.Slog.d(TAG, "Ignored enqueue for snoozed notification " + notificationRecord.getKey());
            }
            this.mSnoozeHelper.update(i, notificationRecord);
            handleSavePolicyFile();
            return false;
        }
        boolean z7 = !areNotificationsEnabledForPackageInt(packageName, i2);
        synchronized (this.mNotificationLock) {
            isRecordBlockedLocked = z7 | isRecordBlockedLocked(notificationRecord);
        }
        if (!isRecordBlockedLocked || notification.isMediaNotification() || isCallNotification(packageName, i2, notification)) {
            return true;
        }
        if (DBG) {
            android.util.Slog.e(TAG, "Suppressing notification from package " + notificationRecord.getSbn().getPackageName() + " by user request.");
        }
        this.mUsageStats.registerBlocked(notificationRecord);
        return false;
    }

    private boolean isCallNotification(java.lang.String str, int i, android.app.Notification notification) {
        if (notification.isStyle(android.app.Notification.CallStyle.class)) {
            return isCallNotification(str, i);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0022, code lost:
    
        if (r4.mTelecomManager.isInSelfManagedCall(r5, true) != false) goto L16;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean isCallNotification(java.lang.String str, int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            boolean z = false;
            if (!this.mPackageManagerClient.hasSystemFeature("android.software.telecom") || this.mTelecomManager == null) {
                return false;
            }
            try {
                if (!this.mTelecomManager.isInManagedCall()) {
                }
                z = true;
                return z;
            } catch (java.lang.IllegalStateException e) {
                return false;
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean areNotificationsEnabledForPackageInt(java.lang.String str, int i) {
        return this.mPermissionHelper.hasPermission(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getNotificationCount(java.lang.String str, int i) {
        int i2;
        synchronized (this.mNotificationLock) {
            try {
                int size = this.mNotificationList.size();
                i2 = 0;
                for (int i3 = 0; i3 < size; i3++) {
                    com.android.server.notification.NotificationRecord notificationRecord = this.mNotificationList.get(i3);
                    if (notificationRecord.getSbn().getPackageName().equals(str) && notificationRecord.getSbn().getUserId() == i) {
                        i2++;
                    }
                }
                int size2 = this.mEnqueuedNotifications.size();
                for (int i4 = 0; i4 < size2; i4++) {
                    com.android.server.notification.NotificationRecord notificationRecord2 = this.mEnqueuedNotifications.get(i4);
                    if (notificationRecord2.getSbn().getPackageName().equals(str) && notificationRecord2.getSbn().getUserId() == i) {
                        i2++;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return i2;
    }

    protected int getNotificationCount(java.lang.String str, int i, int i2, java.lang.String str2) {
        int i3;
        synchronized (this.mNotificationLock) {
            try {
                int size = this.mNotificationList.size();
                i3 = 0;
                for (int i4 = 0; i4 < size; i4++) {
                    com.android.server.notification.NotificationRecord notificationRecord = this.mNotificationList.get(i4);
                    if (notificationRecord.getSbn().getPackageName().equals(str) && notificationRecord.getSbn().getUserId() == i && (notificationRecord.getSbn().getId() != i2 || !android.text.TextUtils.equals(notificationRecord.getSbn().getTag(), str2))) {
                        i3++;
                    }
                }
                int size2 = this.mEnqueuedNotifications.size();
                for (int i5 = 0; i5 < size2; i5++) {
                    com.android.server.notification.NotificationRecord notificationRecord2 = this.mEnqueuedNotifications.get(i5);
                    if (notificationRecord2.getSbn().getPackageName().equals(str) && notificationRecord2.getSbn().getUserId() == i) {
                        i3++;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return i3;
    }

    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    boolean isRecordBlockedLocked(com.android.server.notification.NotificationRecord notificationRecord) {
        return this.mPreferencesHelper.isGroupBlocked(notificationRecord.getSbn().getPackageName(), notificationRecord.getSbn().getUid(), notificationRecord.getChannel().getGroup()) || notificationRecord.getImportance() == 0;
    }

    protected class SnoozeNotificationRunnable implements java.lang.Runnable {
        private final long mDuration;
        private final java.lang.String mKey;
        private final java.lang.String mSnoozeCriterionId;

        SnoozeNotificationRunnable(java.lang.String str, long j, java.lang.String str2) {
            this.mKey = str;
            this.mDuration = j;
            this.mSnoozeCriterionId = str2;
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                try {
                    com.android.server.notification.NotificationRecord findInCurrentAndSnoozedNotificationByKeyLocked = com.android.server.notification.NotificationManagerService.this.findInCurrentAndSnoozedNotificationByKeyLocked(this.mKey);
                    if (findInCurrentAndSnoozedNotificationByKeyLocked != null) {
                        snoozeLocked(findInCurrentAndSnoozedNotificationByKeyLocked);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
        void snoozeLocked(com.android.server.notification.NotificationRecord notificationRecord) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            if (notificationRecord.getSbn().isGroup()) {
                java.util.List<com.android.server.notification.NotificationRecord> findCurrentAndSnoozedGroupNotificationsLocked = com.android.server.notification.NotificationManagerService.this.findCurrentAndSnoozedGroupNotificationsLocked(notificationRecord.getSbn().getPackageName(), notificationRecord.getSbn().getGroupKey(), notificationRecord.getSbn().getUserId());
                if (notificationRecord.getNotification().isGroupSummary()) {
                    for (int i = 0; i < findCurrentAndSnoozedGroupNotificationsLocked.size(); i++) {
                        if (!this.mKey.equals(findCurrentAndSnoozedGroupNotificationsLocked.get(i).getKey())) {
                            arrayList.add(findCurrentAndSnoozedGroupNotificationsLocked.get(i));
                        }
                    }
                } else if (com.android.server.notification.NotificationManagerService.this.mSummaryByGroupKey.containsKey(notificationRecord.getSbn().getGroupKey()) && findCurrentAndSnoozedGroupNotificationsLocked.size() == 2) {
                    for (int i2 = 0; i2 < findCurrentAndSnoozedGroupNotificationsLocked.size(); i2++) {
                        if (!this.mKey.equals(findCurrentAndSnoozedGroupNotificationsLocked.get(i2).getKey())) {
                            arrayList.add(findCurrentAndSnoozedGroupNotificationsLocked.get(i2));
                        }
                    }
                }
            }
            arrayList.add(notificationRecord);
            if (!com.android.server.notification.NotificationManagerService.this.mSnoozeHelper.canSnooze(arrayList.size())) {
                android.util.Log.w(com.android.server.notification.NotificationManagerService.TAG, "Cannot snooze " + notificationRecord.getKey() + ": too many snoozed notifications");
                return;
            }
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                snoozeNotificationLocked((com.android.server.notification.NotificationRecord) arrayList.get(i3));
            }
        }

        @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
        void snoozeNotificationLocked(com.android.server.notification.NotificationRecord notificationRecord) {
            com.android.internal.logging.MetricsLogger.action(notificationRecord.getLogMaker().setCategory(831).setType(2).addTaggedData(1139, java.lang.Long.valueOf(this.mDuration)).addTaggedData(832, java.lang.Integer.valueOf(this.mSnoozeCriterionId == null ? 0 : 1)));
            com.android.server.notification.NotificationManagerService.this.mNotificationRecordLogger.log(com.android.server.notification.NotificationRecordLogger.NotificationEvent.NOTIFICATION_SNOOZED, notificationRecord);
            com.android.server.notification.NotificationManagerService.this.reportUserInteraction(notificationRecord);
            com.android.server.notification.NotificationManagerService.this.cancelNotificationLocked(notificationRecord, false, 18, com.android.server.notification.NotificationManagerService.this.removeFromNotificationListsLocked(notificationRecord), null, android.os.SystemClock.elapsedRealtime());
            com.android.server.notification.Flags.refactorAttentionHelper();
            com.android.server.notification.NotificationManagerService.this.mAttentionHelper.updateLightsLocked();
            if (isSnoozable(notificationRecord)) {
                if (this.mSnoozeCriterionId != null) {
                    com.android.server.notification.NotificationManagerService.this.mAssistants.notifyAssistantSnoozedLocked(notificationRecord, this.mSnoozeCriterionId);
                    com.android.server.notification.NotificationManagerService.this.mSnoozeHelper.snooze(notificationRecord, this.mSnoozeCriterionId);
                } else {
                    com.android.server.notification.NotificationManagerService.this.mSnoozeHelper.snooze(notificationRecord, this.mDuration);
                }
                notificationRecord.recordSnoozed();
                com.android.server.notification.NotificationManagerService.this.handleSavePolicyFile();
            }
        }

        private boolean isSnoozable(com.android.server.notification.NotificationRecord notificationRecord) {
            return (notificationRecord.getNotification().isGroupSummary() && "ranker_group".equals(notificationRecord.getNotification().getGroup())) ? false : true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unsnoozeAll() {
        synchronized (this.mNotificationLock) {
            this.mSnoozeHelper.repostAll(this.mUserProfiles.getCurrentProfileIds());
            handleSavePolicyFile();
        }
    }

    protected class CancelNotificationRunnable implements java.lang.Runnable {
        private final int mCallingPid;
        private final int mCallingUid;
        private final long mCancellationElapsedTimeMs;
        private final int mCount;
        private final int mId;
        private final com.android.server.notification.ManagedServices.ManagedServiceInfo mListener;
        private final int mMustHaveFlags;
        private final int mMustNotHaveFlags;
        private final java.lang.String mPkg;
        private final int mRank;
        private final int mReason;
        private final boolean mSendDelete;
        private final java.lang.String mTag;
        private final int mUserId;

        CancelNotificationRunnable(int i, int i2, java.lang.String str, java.lang.String str2, int i3, int i4, int i5, boolean z, int i6, int i7, int i8, int i9, com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo, long j) {
            this.mCallingUid = i;
            this.mCallingPid = i2;
            this.mPkg = str;
            this.mTag = str2;
            this.mId = i3;
            this.mMustHaveFlags = i4;
            this.mMustNotHaveFlags = i5;
            this.mSendDelete = z;
            this.mUserId = i6;
            this.mReason = i7;
            this.mRank = i8;
            this.mCount = i9;
            this.mListener = managedServiceInfo;
            this.mCancellationElapsedTimeMs = j;
        }

        @Override // java.lang.Runnable
        public void run() {
            int i;
            java.lang.String shortString = this.mListener == null ? null : this.mListener.component.toShortString();
            if (com.android.server.notification.NotificationManagerService.DBG) {
                com.android.server.EventLogTags.writeNotificationCancel(this.mCallingUid, this.mCallingPid, this.mPkg, this.mId, this.mTag, this.mUserId, this.mMustHaveFlags, this.mMustNotHaveFlags, this.mReason, shortString);
            }
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                try {
                    com.android.server.notification.NotificationRecord findNotificationLocked = com.android.server.notification.NotificationManagerService.this.findNotificationLocked(this.mPkg, this.mTag, this.mId, this.mUserId);
                    if (findNotificationLocked != null) {
                        if (this.mReason == 1) {
                            com.android.server.notification.NotificationManagerService.this.mUsageStats.registerClickedByUser(findNotificationLocked);
                        }
                        if ((this.mReason == 10 && findNotificationLocked.getNotification().isBubbleNotification()) || (this.mReason == 1 && findNotificationLocked.canBubble() && findNotificationLocked.isFlagBubbleRemoved())) {
                            if (findNotificationLocked.getNotification().getBubbleMetadata() == null) {
                                i = 0;
                            } else {
                                i = findNotificationLocked.getNotification().getBubbleMetadata().getFlags();
                            }
                            com.android.server.notification.NotificationManagerService.this.mNotificationDelegate.onBubbleMetadataFlagChanged(findNotificationLocked.getKey(), i | 2);
                            return;
                        }
                        if ((findNotificationLocked.getNotification().flags & this.mMustHaveFlags) != this.mMustHaveFlags) {
                            return;
                        }
                        if ((findNotificationLocked.getNotification().flags & this.mMustNotHaveFlags) != 0) {
                            return;
                        }
                        com.android.server.notification.NotificationManagerService.FlagChecker flagChecker = new com.android.server.notification.NotificationManagerService.FlagChecker() { // from class: com.android.server.notification.NotificationManagerService$CancelNotificationRunnable$$ExternalSyntheticLambda0
                            @Override // com.android.server.notification.NotificationManagerService.FlagChecker
                            public final boolean apply(int i2) {
                                boolean lambda$run$0;
                                lambda$run$0 = com.android.server.notification.NotificationManagerService.CancelNotificationRunnable.this.lambda$run$0(i2);
                                return lambda$run$0;
                            }
                        };
                        com.android.server.notification.NotificationManagerService.this.cancelNotificationLocked(findNotificationLocked, this.mSendDelete, this.mReason, this.mRank, this.mCount, com.android.server.notification.NotificationManagerService.this.removeFromNotificationListsLocked(findNotificationLocked), shortString, this.mCancellationElapsedTimeMs);
                        com.android.server.notification.NotificationManagerService.this.cancelGroupChildrenLocked(findNotificationLocked, this.mCallingUid, this.mCallingPid, shortString, this.mSendDelete, flagChecker, this.mReason, this.mCancellationElapsedTimeMs);
                        com.android.server.notification.Flags.refactorAttentionHelper();
                        com.android.server.notification.NotificationManagerService.this.mAttentionHelper.updateLightsLocked();
                        if (com.android.server.notification.NotificationManagerService.this.mShortcutHelper != null) {
                            com.android.server.notification.NotificationManagerService.this.mShortcutHelper.maybeListenForShortcutChangesForBubbles(findNotificationLocked, true, com.android.server.notification.NotificationManagerService.this.mHandler);
                        }
                    } else if (this.mReason != 18 && com.android.server.notification.NotificationManagerService.this.mSnoozeHelper.cancel(this.mUserId, this.mPkg, this.mTag, this.mId)) {
                        com.android.server.notification.NotificationManagerService.this.handleSavePolicyFile();
                    }
                } finally {
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$run$0(int i) {
            if (this.mReason == 2 || this.mReason == 1 || this.mReason == 3) {
                if ((i & 4096) != 0) {
                    return false;
                }
            } else if (this.mReason == 8 && ((i & 64) != 0 || (32768 & i) != 0)) {
                return false;
            }
            return (i & this.mMustNotHaveFlags) == 0;
        }
    }

    protected static class ShowNotificationPermissionPromptRunnable implements java.lang.Runnable {
        private final java.lang.String mPkgName;
        private final com.android.server.policy.PermissionPolicyInternal mPpi;
        private final int mTaskId;
        private final int mUserId;

        ShowNotificationPermissionPromptRunnable(java.lang.String str, int i, int i2, com.android.server.policy.PermissionPolicyInternal permissionPolicyInternal) {
            this.mPkgName = str;
            this.mUserId = i;
            this.mTaskId = i2;
            this.mPpi = permissionPolicyInternal;
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof com.android.server.notification.NotificationManagerService.ShowNotificationPermissionPromptRunnable)) {
                return false;
            }
            com.android.server.notification.NotificationManagerService.ShowNotificationPermissionPromptRunnable showNotificationPermissionPromptRunnable = (com.android.server.notification.NotificationManagerService.ShowNotificationPermissionPromptRunnable) obj;
            return java.util.Objects.equals(this.mPkgName, showNotificationPermissionPromptRunnable.mPkgName) && this.mUserId == showNotificationPermissionPromptRunnable.mUserId && this.mTaskId == showNotificationPermissionPromptRunnable.mTaskId;
        }

        public int hashCode() {
            return java.util.Objects.hash(this.mPkgName, java.lang.Integer.valueOf(this.mUserId), java.lang.Integer.valueOf(this.mTaskId));
        }

        @Override // java.lang.Runnable
        public void run() {
            this.mPpi.showNotificationPromptIfNeeded(this.mPkgName, this.mUserId, this.mTaskId);
        }
    }

    protected class EnqueueNotificationRunnable implements java.lang.Runnable {
        private final boolean isAppForeground;
        private final com.android.server.notification.NotificationManagerService.PostNotificationTracker mTracker;
        private final com.android.server.notification.NotificationRecord r;
        private final int userId;

        EnqueueNotificationRunnable(int i, com.android.server.notification.NotificationRecord notificationRecord, boolean z, com.android.server.notification.NotificationManagerService.PostNotificationTracker postNotificationTracker) {
            this.userId = i;
            this.r = notificationRecord;
            this.isAppForeground = z;
            this.mTracker = (com.android.server.notification.NotificationManagerService.PostNotificationTracker) com.android.internal.util.Preconditions.checkNotNull(postNotificationTracker);
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                if (!enqueueNotification()) {
                }
            } finally {
                this.mTracker.cancel();
            }
        }

        private boolean enqueueNotification() {
            int i;
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                try {
                    long longValue = com.android.server.notification.NotificationManagerService.this.mSnoozeHelper.getSnoozeTimeForUnpostedNotification(this.r.getUser().getIdentifier(), this.r.getSbn().getPackageName(), this.r.getSbn().getKey()).longValue();
                    long currentTimeMillis = java.lang.System.currentTimeMillis();
                    if (longValue > currentTimeMillis) {
                        com.android.server.notification.NotificationManagerService.this.new SnoozeNotificationRunnable(this.r.getSbn().getKey(), longValue - currentTimeMillis, null).snoozeLocked(this.r);
                        return false;
                    }
                    java.lang.String snoozeContextForUnpostedNotification = com.android.server.notification.NotificationManagerService.this.mSnoozeHelper.getSnoozeContextForUnpostedNotification(this.r.getUser().getIdentifier(), this.r.getSbn().getPackageName(), this.r.getSbn().getKey());
                    if (snoozeContextForUnpostedNotification != null) {
                        com.android.server.notification.NotificationManagerService.this.new SnoozeNotificationRunnable(this.r.getSbn().getKey(), 0L, snoozeContextForUnpostedNotification).snoozeLocked(this.r);
                        return false;
                    }
                    com.android.server.notification.NotificationManagerService.this.mEnqueuedNotifications.add(this.r);
                    com.android.server.notification.NotificationManagerService.this.scheduleTimeoutLocked(this.r);
                    android.service.notification.StatusBarNotification sbn = this.r.getSbn();
                    if (com.android.server.notification.NotificationManagerService.DBG) {
                        android.util.Slog.d(com.android.server.notification.NotificationManagerService.TAG, "EnqueueNotificationRunnable.run for: " + sbn.getKey());
                    }
                    com.android.server.notification.NotificationRecord notificationRecord = com.android.server.notification.NotificationManagerService.this.mNotificationsByKey.get(sbn.getKey());
                    if (notificationRecord != null) {
                        this.r.copyRankingInformation(notificationRecord);
                    }
                    int uid = sbn.getUid();
                    int initialPid = sbn.getInitialPid();
                    android.app.Notification notification = sbn.getNotification();
                    java.lang.String packageName = sbn.getPackageName();
                    int id = sbn.getId();
                    java.lang.String tag = sbn.getTag();
                    com.android.server.notification.NotificationManagerService.this.updateNotificationBubbleFlags(this.r, this.isAppForeground);
                    com.android.server.notification.NotificationManagerService.this.handleGroupedNotificationLocked(this.r, notificationRecord, uid, initialPid);
                    if (sbn.isGroup() && notification.isGroupChild()) {
                        com.android.server.notification.NotificationManagerService.this.mSnoozeHelper.repostGroupSummary(packageName, this.r.getUserId(), sbn.getGroupKey());
                    }
                    if (!packageName.equals("com.android.providers.downloads") || android.util.Log.isLoggable("DownloadManager", 2)) {
                        if (notificationRecord == null) {
                            i = 0;
                        } else {
                            i = 1;
                        }
                        com.android.server.EventLogTags.writeNotificationEnqueue(uid, initialPid, packageName, id, tag, this.userId, notification.toString(), i);
                    }
                    if (com.android.server.notification.NotificationManagerService.this.mAssistants.isEnabled()) {
                        com.android.server.notification.NotificationManagerService.this.mAssistants.onNotificationEnqueuedLocked(this.r);
                        com.android.server.notification.NotificationManagerService.this.mHandler.postDelayed(com.android.server.notification.NotificationManagerService.this.new PostNotificationRunnable(this.r.getKey(), this.r.getSbn().getPackageName(), this.r.getUid(), this.mTracker), com.android.server.notification.NotificationManagerService.DELAY_FOR_ASSISTANT_TIME);
                    } else {
                        com.android.server.notification.NotificationManagerService.this.mHandler.post(com.android.server.notification.NotificationManagerService.this.new PostNotificationRunnable(this.r.getKey(), this.r.getSbn().getPackageName(), this.r.getUid(), this.mTracker));
                    }
                    return true;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    boolean isPackagePausedOrSuspended(java.lang.String str, int i) {
        return isPackageSuspendedForUser(str, i) | ((((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).getDistractingPackageRestrictions(str, android.os.Binder.getCallingUserHandle().getIdentifier()) & 2) != 0);
    }

    protected class PostNotificationRunnable implements java.lang.Runnable {
        private final java.lang.String key;
        private final com.android.server.notification.NotificationManagerService.PostNotificationTracker mTracker;
        private final java.lang.String pkg;
        private final int uid;

        PostNotificationRunnable(java.lang.String str, java.lang.String str2, int i, com.android.server.notification.NotificationManagerService.PostNotificationTracker postNotificationTracker) {
            this.key = str;
            this.pkg = str2;
            this.uid = i;
            this.mTracker = (com.android.server.notification.NotificationManagerService.PostNotificationTracker) com.android.internal.util.Preconditions.checkNotNull(postNotificationTracker);
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                try {
                    if (postNotification()) {
                    }
                } catch (java.lang.Exception e) {
                    android.util.Slog.e(com.android.server.notification.NotificationManagerService.TAG, "Error posting", e);
                }
            } finally {
                this.mTracker.cancel();
            }
        }

        private boolean postNotification() {
            int i;
            boolean z;
            boolean z2 = !com.android.server.notification.NotificationManagerService.this.areNotificationsEnabledForPackageInt(this.pkg, this.uid);
            boolean isCallNotification = com.android.server.notification.NotificationManagerService.this.isCallNotification(this.pkg, this.uid);
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                int i2 = 0;
                try {
                    try {
                        com.android.server.notification.NotificationRecord findNotificationByListLocked = com.android.server.notification.NotificationManagerService.findNotificationByListLocked(com.android.server.notification.NotificationManagerService.this.mEnqueuedNotifications, this.key);
                        if (findNotificationByListLocked == null) {
                            android.util.Slog.i(com.android.server.notification.NotificationManagerService.TAG, "Cannot find enqueued record for key: " + this.key);
                            int size = com.android.server.notification.NotificationManagerService.this.mEnqueuedNotifications.size();
                            int i3 = 0;
                            while (true) {
                                if (i3 >= size) {
                                    break;
                                }
                                if (java.util.Objects.equals(this.key, com.android.server.notification.NotificationManagerService.this.mEnqueuedNotifications.get(i3).getKey())) {
                                    com.android.server.notification.NotificationManagerService.this.mEnqueuedNotifications.remove(i3);
                                    break;
                                }
                                i3++;
                            }
                            return false;
                        }
                        final android.service.notification.StatusBarNotification sbn = findNotificationByListLocked.getSbn();
                        android.app.Notification notification = sbn.getNotification();
                        boolean z3 = isCallNotification && notification.isStyle(android.app.Notification.CallStyle.class);
                        if (!notification.isMediaNotification() && !z3 && (z2 || com.android.server.notification.NotificationManagerService.this.isRecordBlockedLocked(findNotificationByListLocked))) {
                            com.android.server.notification.NotificationManagerService.this.mUsageStats.registerBlocked(findNotificationByListLocked);
                            if (com.android.server.notification.NotificationManagerService.DBG) {
                                android.util.Slog.e(com.android.server.notification.NotificationManagerService.TAG, "Suppressing notification from package " + this.pkg);
                            }
                            int size2 = com.android.server.notification.NotificationManagerService.this.mEnqueuedNotifications.size();
                            int i4 = 0;
                            while (true) {
                                if (i4 >= size2) {
                                    break;
                                }
                                if (java.util.Objects.equals(this.key, com.android.server.notification.NotificationManagerService.this.mEnqueuedNotifications.get(i4).getKey())) {
                                    com.android.server.notification.NotificationManagerService.this.mEnqueuedNotifications.remove(i4);
                                    break;
                                }
                                i4++;
                            }
                            return false;
                        }
                        boolean isPackagePausedOrSuspended = com.android.server.notification.NotificationManagerService.this.isPackagePausedOrSuspended(findNotificationByListLocked.getSbn().getPackageName(), findNotificationByListLocked.getUid());
                        findNotificationByListLocked.setHidden(isPackagePausedOrSuspended);
                        if (isPackagePausedOrSuspended) {
                            com.android.server.notification.NotificationManagerService.this.mUsageStats.registerSuspendedByAdmin(findNotificationByListLocked);
                        }
                        com.android.server.notification.NotificationRecord notificationRecord = com.android.server.notification.NotificationManagerService.this.mNotificationsByKey.get(this.key);
                        if (notificationRecord == null || notificationRecord.getSbn().getInstanceId() == null) {
                            sbn.setInstanceId(com.android.server.notification.NotificationManagerService.this.mNotificationInstanceIdSequence.newInstanceId());
                        } else {
                            sbn.setInstanceId(notificationRecord.getSbn().getInstanceId());
                        }
                        int indexOfNotificationLocked = com.android.server.notification.NotificationManagerService.this.indexOfNotificationLocked(sbn.getKey());
                        if (indexOfNotificationLocked < 0) {
                            com.android.server.notification.NotificationManagerService.this.mNotificationList.add(findNotificationByListLocked);
                            com.android.server.notification.NotificationManagerService.this.mUsageStats.registerPostedByApp(findNotificationByListLocked);
                            com.android.server.notification.NotificationManagerService.this.mUsageStatsManagerInternal.reportNotificationPosted(findNotificationByListLocked.getSbn().getOpPkg(), findNotificationByListLocked.getSbn().getUser(), this.mTracker.getStartTime());
                            boolean isVisuallyInterruptive = com.android.server.notification.NotificationManagerService.this.isVisuallyInterruptive(null, findNotificationByListLocked);
                            findNotificationByListLocked.setInterruptive(isVisuallyInterruptive);
                            findNotificationByListLocked.setTextChanged(isVisuallyInterruptive);
                        } else {
                            notificationRecord = com.android.server.notification.NotificationManagerService.this.mNotificationList.get(indexOfNotificationLocked);
                            com.android.server.notification.NotificationManagerService.this.mNotificationList.set(indexOfNotificationLocked, findNotificationByListLocked);
                            com.android.server.notification.NotificationManagerService.this.mUsageStats.registerUpdatedByApp(findNotificationByListLocked, notificationRecord);
                            com.android.server.notification.NotificationManagerService.this.mUsageStatsManagerInternal.reportNotificationUpdated(findNotificationByListLocked.getSbn().getOpPkg(), findNotificationByListLocked.getSbn().getUser(), this.mTracker.getStartTime());
                            notification.flags |= notificationRecord.getNotification().flags & 64;
                            findNotificationByListLocked.isUpdate = true;
                            findNotificationByListLocked.setTextChanged(com.android.server.notification.NotificationManagerService.this.isVisuallyInterruptive(notificationRecord, findNotificationByListLocked));
                        }
                        com.android.server.notification.NotificationManagerService.this.mNotificationsByKey.put(sbn.getKey(), findNotificationByListLocked);
                        if ((notification.flags & 64) != 0) {
                            notification.flags |= 32;
                        }
                        com.android.server.notification.NotificationManagerService.this.mRankingHelper.extractSignals(findNotificationByListLocked);
                        com.android.server.notification.NotificationManagerService.this.mRankingHelper.sort(com.android.server.notification.NotificationManagerService.this.mNotificationList);
                        int indexOf = com.android.server.notification.NotificationManagerService.this.mRankingHelper.indexOf(com.android.server.notification.NotificationManagerService.this.mNotificationList, findNotificationByListLocked);
                        if (findNotificationByListLocked.isHidden()) {
                            i = 0;
                        } else {
                            com.android.server.notification.Flags.refactorAttentionHelper();
                            i = com.android.server.notification.NotificationManagerService.this.mAttentionHelper.buzzBeepBlinkLocked(findNotificationByListLocked, new com.android.server.notification.NotificationAttentionHelper.Signals(com.android.server.notification.NotificationManagerService.this.mUserProfiles.isCurrentProfile(findNotificationByListLocked.getUserId()), com.android.server.notification.NotificationManagerService.this.mListenerHints));
                        }
                        if (notification.getSmallIcon() != null) {
                            com.android.server.notification.NotificationManagerService.this.notifyListenersPostedAndLogLocked(findNotificationByListLocked, notificationRecord, this.mTracker, com.android.server.notification.NotificationManagerService.this.mNotificationRecordLogger.prepareToLogNotificationPosted(findNotificationByListLocked, notificationRecord, indexOf, i, com.android.server.notification.NotificationManagerService.this.getGroupInstanceId(findNotificationByListLocked.getSbn().getGroupKey())));
                            android.service.notification.StatusBarNotification sbn2 = notificationRecord != null ? notificationRecord.getSbn() : null;
                            if ((sbn2 == null || !java.util.Objects.equals(sbn2.getGroup(), sbn.getGroup()) || sbn2.getNotification().flags != sbn.getNotification().flags) && !com.android.server.notification.NotificationManagerService.this.isCritical(findNotificationByListLocked)) {
                                com.android.server.notification.NotificationManagerService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.notification.NotificationManagerService$PostNotificationRunnable$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        com.android.server.notification.NotificationManagerService.PostNotificationRunnable.this.lambda$postNotification$0(sbn);
                                    }
                                });
                            }
                            z = true;
                        } else {
                            android.util.Slog.e(com.android.server.notification.NotificationManagerService.TAG, "Not posting notification without small icon: " + notification);
                            if (notificationRecord != null && !notificationRecord.isCanceled) {
                                com.android.server.notification.NotificationManagerService.this.mListeners.notifyRemovedLocked(findNotificationByListLocked, 4, findNotificationByListLocked.getStats());
                                com.android.server.notification.NotificationManagerService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.notification.NotificationManagerService.PostNotificationRunnable.1
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        com.android.server.notification.NotificationManagerService.this.mGroupHelper.onNotificationRemoved(sbn);
                                    }
                                });
                            }
                            if (com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.callstyleCallbackApi()) {
                                com.android.server.notification.NotificationManagerService.this.notifyCallNotificationEventListenerOnRemoved(findNotificationByListLocked);
                            }
                            android.util.Slog.e(com.android.server.notification.NotificationManagerService.TAG, "WARNING: In a future release this will crash the app: " + sbn.getPackageName());
                            z = false;
                        }
                        if (com.android.server.notification.NotificationManagerService.this.mShortcutHelper != null) {
                            com.android.server.notification.NotificationManagerService.this.mShortcutHelper.maybeListenForShortcutChangesForBubbles(findNotificationByListLocked, false, com.android.server.notification.NotificationManagerService.this.mHandler);
                        }
                        com.android.server.notification.NotificationManagerService.this.maybeRecordInterruptionLocked(findNotificationByListLocked);
                        com.android.server.notification.NotificationManagerService.this.maybeRegisterMessageSent(findNotificationByListLocked);
                        com.android.server.notification.NotificationManagerService.this.maybeReportForegroundServiceUpdate(findNotificationByListLocked, true);
                        int size3 = com.android.server.notification.NotificationManagerService.this.mEnqueuedNotifications.size();
                        while (true) {
                            if (i2 >= size3) {
                                break;
                            }
                            if (java.util.Objects.equals(this.key, com.android.server.notification.NotificationManagerService.this.mEnqueuedNotifications.get(i2).getKey())) {
                                com.android.server.notification.NotificationManagerService.this.mEnqueuedNotifications.remove(i2);
                                break;
                            }
                            i2++;
                        }
                        return z;
                    } catch (java.lang.Throwable th) {
                        int size4 = com.android.server.notification.NotificationManagerService.this.mEnqueuedNotifications.size();
                        while (true) {
                            if (i2 >= size4) {
                                break;
                            }
                            if (java.util.Objects.equals(this.key, com.android.server.notification.NotificationManagerService.this.mEnqueuedNotifications.get(i2).getKey())) {
                                com.android.server.notification.NotificationManagerService.this.mEnqueuedNotifications.remove(i2);
                                break;
                            }
                            i2++;
                        }
                        throw th;
                    }
                } catch (java.lang.Throwable th2) {
                    throw th2;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$postNotification$0(android.service.notification.StatusBarNotification statusBarNotification) {
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                com.android.server.notification.NotificationManagerService.this.mGroupHelper.onNotificationPosted(statusBarNotification, com.android.server.notification.NotificationManagerService.this.hasAutoGroupSummaryLocked(statusBarNotification));
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    com.android.internal.logging.InstanceId getGroupInstanceId(java.lang.String str) {
        com.android.server.notification.NotificationRecord notificationRecord;
        if (str == null || (notificationRecord = this.mSummaryByGroupKey.get(str)) == null) {
            return null;
        }
        return notificationRecord.getSbn().getInstanceId();
    }

    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    @com.android.internal.annotations.VisibleForTesting
    protected boolean isVisuallyInterruptive(@android.annotation.Nullable com.android.server.notification.NotificationRecord notificationRecord, @android.annotation.NonNull com.android.server.notification.NotificationRecord notificationRecord2) {
        android.app.Notification.Builder recoverBuilder;
        android.app.Notification.Builder recoverBuilder2;
        if (notificationRecord2.getSbn().isGroup() && notificationRecord2.getSbn().getNotification().isGroupSummary()) {
            if (DEBUG_INTERRUPTIVENESS) {
                android.util.Slog.v(TAG, "INTERRUPTIVENESS: " + notificationRecord2.getKey() + " is not interruptive: summary");
            }
            return false;
        }
        if (notificationRecord == null) {
            if (DEBUG_INTERRUPTIVENESS) {
                android.util.Slog.v(TAG, "INTERRUPTIVENESS: " + notificationRecord2.getKey() + " is interruptive: new notification");
            }
            return true;
        }
        android.app.Notification notification = notificationRecord.getSbn().getNotification();
        android.app.Notification notification2 = notificationRecord2.getSbn().getNotification();
        if (notification.extras == null || notification2.extras == null) {
            if (DEBUG_INTERRUPTIVENESS) {
                android.util.Slog.v(TAG, "INTERRUPTIVENESS: " + notificationRecord2.getKey() + " is not interruptive: no extras");
            }
            return false;
        }
        if ((notificationRecord2.getSbn().getNotification().flags & 64) != 0) {
            if (DEBUG_INTERRUPTIVENESS) {
                android.util.Slog.v(TAG, "INTERRUPTIVENESS: " + notificationRecord2.getKey() + " is not interruptive: foreground service");
            }
            return false;
        }
        java.lang.String valueOf = java.lang.String.valueOf(notification.extras.get("android.title"));
        java.lang.String valueOf2 = java.lang.String.valueOf(notification2.extras.get("android.title"));
        if (!java.util.Objects.equals(valueOf, valueOf2)) {
            if (DEBUG_INTERRUPTIVENESS) {
                android.util.Slog.v(TAG, "INTERRUPTIVENESS: " + notificationRecord2.getKey() + " is interruptive: changed title");
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("INTERRUPTIVENESS: ");
                sb.append(java.lang.String.format("   old title: %s (%s@0x%08x)", valueOf, valueOf.getClass(), java.lang.Integer.valueOf(valueOf.hashCode())));
                android.util.Slog.v(TAG, sb.toString());
                android.util.Slog.v(TAG, "INTERRUPTIVENESS: " + java.lang.String.format("   new title: %s (%s@0x%08x)", valueOf2, valueOf2.getClass(), java.lang.Integer.valueOf(valueOf2.hashCode())));
            }
            return true;
        }
        java.lang.String valueOf3 = java.lang.String.valueOf(notification.extras.get("android.text"));
        java.lang.String valueOf4 = java.lang.String.valueOf(notification2.extras.get("android.text"));
        if (!java.util.Objects.equals(valueOf3, valueOf4)) {
            if (DEBUG_INTERRUPTIVENESS) {
                android.util.Slog.v(TAG, "INTERRUPTIVENESS: " + notificationRecord2.getKey() + " is interruptive: changed text");
                java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
                sb2.append("INTERRUPTIVENESS: ");
                sb2.append(java.lang.String.format("   old text: %s (%s@0x%08x)", valueOf3, valueOf3.getClass(), java.lang.Integer.valueOf(valueOf3.hashCode())));
                android.util.Slog.v(TAG, sb2.toString());
                android.util.Slog.v(TAG, "INTERRUPTIVENESS: " + java.lang.String.format("   new text: %s (%s@0x%08x)", valueOf4, valueOf4.getClass(), java.lang.Integer.valueOf(valueOf4.hashCode())));
            }
            return true;
        }
        if (notification.hasCompletedProgress() != notification2.hasCompletedProgress()) {
            if (DEBUG_INTERRUPTIVENESS) {
                android.util.Slog.v(TAG, "INTERRUPTIVENESS: " + notificationRecord2.getKey() + " is interruptive: completed progress");
            }
            return true;
        }
        if (android.app.Notification.areIconsDifferent(notification, notification2)) {
            if (DEBUG_INTERRUPTIVENESS) {
                android.util.Slog.v(TAG, "INTERRUPTIVENESS: " + notificationRecord2.getKey() + " is interruptive: icons differ");
            }
            return true;
        }
        if (notificationRecord2.canBubble()) {
            if (DEBUG_INTERRUPTIVENESS) {
                android.util.Slog.v(TAG, "INTERRUPTIVENESS: " + notificationRecord2.getKey() + " is not interruptive: bubble");
            }
            return false;
        }
        if (android.app.Notification.areActionsVisiblyDifferent(notification, notification2)) {
            if (DEBUG_INTERRUPTIVENESS) {
                android.util.Slog.v(TAG, "INTERRUPTIVENESS: " + notificationRecord2.getKey() + " is interruptive: changed actions");
            }
            return true;
        }
        try {
            recoverBuilder = android.app.Notification.Builder.recoverBuilder(getContext(), notification);
            recoverBuilder2 = android.app.Notification.Builder.recoverBuilder(getContext(), notification2);
        } catch (java.lang.Exception e) {
            android.util.Slog.w(TAG, "error recovering builder", e);
        }
        if (android.app.Notification.areStyledNotificationsVisiblyDifferent(recoverBuilder, recoverBuilder2)) {
            if (DEBUG_INTERRUPTIVENESS) {
                android.util.Slog.v(TAG, "INTERRUPTIVENESS: " + notificationRecord2.getKey() + " is interruptive: styles differ");
            }
            return true;
        }
        if (android.app.Notification.areRemoteViewsChanged(recoverBuilder, recoverBuilder2)) {
            if (DEBUG_INTERRUPTIVENESS) {
                android.util.Slog.v(TAG, "INTERRUPTIVENESS: " + notificationRecord2.getKey() + " is interruptive: remoteviews differ");
            }
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isCritical(com.android.server.notification.NotificationRecord notificationRecord) {
        return notificationRecord.getCriticality() < 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    public void handleGroupedNotificationLocked(com.android.server.notification.NotificationRecord notificationRecord, com.android.server.notification.NotificationRecord notificationRecord2, int i, int i2) {
        com.android.server.notification.NotificationRecord remove;
        android.service.notification.StatusBarNotification sbn = notificationRecord.getSbn();
        android.app.Notification notification = sbn.getNotification();
        if (notification.isGroupSummary() && !sbn.isAppGroup()) {
            notification.flags &= -513;
        }
        java.lang.String groupKey = sbn.getGroupKey();
        boolean isGroupSummary = notification.isGroupSummary();
        android.app.Notification notification2 = notificationRecord2 != null ? notificationRecord2.getSbn().getNotification() : null;
        java.lang.String groupKey2 = notificationRecord2 != null ? notificationRecord2.getSbn().getGroupKey() : null;
        boolean z = notificationRecord2 != null && notification2.isGroupSummary();
        if (z && (remove = this.mSummaryByGroupKey.remove(groupKey2)) != notificationRecord2) {
            android.util.Slog.w(TAG, "Removed summary didn't match old notification: old=" + notificationRecord2.getKey() + ", removed=" + (remove != null ? remove.getKey() : "<null>"));
        }
        if (isGroupSummary) {
            this.mSummaryByGroupKey.put(groupKey, notificationRecord);
        }
        com.android.server.notification.NotificationManagerService.FlagChecker flagChecker = new com.android.server.notification.NotificationManagerService.FlagChecker() { // from class: com.android.server.notification.NotificationManagerService$$ExternalSyntheticLambda5
            @Override // com.android.server.notification.NotificationManagerService.FlagChecker
            public final boolean apply(int i3) {
                boolean lambda$handleGroupedNotificationLocked$9;
                lambda$handleGroupedNotificationLocked$9 = com.android.server.notification.NotificationManagerService.lambda$handleGroupedNotificationLocked$9(i3);
                return lambda$handleGroupedNotificationLocked$9;
            }
        };
        if (z) {
            if (!isGroupSummary || !groupKey2.equals(groupKey)) {
                cancelGroupChildrenLocked(notificationRecord2, i, i2, null, false, flagChecker, 8, android.os.SystemClock.elapsedRealtime());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$handleGroupedNotificationLocked$9(int i) {
        if ((i & 64) != 0 || (i & 32768) != 0) {
            return false;
        }
        return true;
    }

    private android.app.PendingIntent getNotificationTimeoutPendingIntent(com.android.server.notification.NotificationRecord notificationRecord, int i) {
        return android.app.PendingIntent.getBroadcast(getContext(), 1, new android.content.Intent(ACTION_NOTIFICATION_TIMEOUT).setPackage(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME).setData(new android.net.Uri.Builder().scheme(SCHEME_TIMEOUT).appendPath(notificationRecord.getKey()).build()).addFlags(268435456).putExtra(EXTRA_KEY, notificationRecord.getKey()), i | 67108864);
    }

    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    @com.android.internal.annotations.VisibleForTesting
    void scheduleTimeoutLocked(com.android.server.notification.NotificationRecord notificationRecord) {
        if (notificationRecord.getNotification().getTimeoutAfter() > 0) {
            this.mAlarmManager.setExactAndAllowWhileIdle(2, android.os.SystemClock.elapsedRealtime() + notificationRecord.getNotification().getTimeoutAfter(), getNotificationTimeoutPendingIntent(notificationRecord, 134217728));
        }
    }

    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    @com.android.internal.annotations.VisibleForTesting
    void cancelScheduledTimeoutLocked(com.android.server.notification.NotificationRecord notificationRecord) {
        android.app.PendingIntent notificationTimeoutPendingIntent = getNotificationTimeoutPendingIntent(notificationRecord, 268435456);
        if (notificationTimeoutPendingIntent != null) {
            this.mAlarmManager.cancel(notificationTimeoutPendingIntent);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v0 */
    /* JADX WARN: Type inference failed for: r12v1, types: [int] */
    /* JADX WARN: Type inference failed for: r12v14 */
    /* JADX WARN: Type inference failed for: r12v6 */
    /* JADX WARN: Type inference failed for: r12v7 */
    /* JADX WARN: Type inference failed for: r9v23 */
    /* JADX WARN: Type inference failed for: r9v5 */
    /* JADX WARN: Type inference failed for: r9v6, types: [int] */
    /* JADX WARN: Type inference failed for: r9v7 */
    /* JADX WARN: Type inference failed for: r9v8 */
    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    @com.android.internal.annotations.VisibleForTesting
    int buzzBeepBlinkLocked(com.android.server.notification.NotificationRecord notificationRecord) {
        boolean z;
        boolean z2;
        boolean z3;
        ?? r9;
        ?? r12;
        boolean z4;
        int i;
        boolean z5;
        boolean z6;
        boolean z7;
        boolean z8;
        if (this.mIsAutomotive && !this.mNotificationEffectsEnabledForAutomotive) {
            return 0;
        }
        java.lang.String key = notificationRecord.getKey();
        if (this.mIsAutomotive) {
            z = notificationRecord.getImportance() > 3;
        } else {
            z = notificationRecord.getImportance() >= 3;
        }
        boolean z9 = key != null && key.equals(this.mSoundNotificationKey);
        boolean z10 = key != null && key.equals(this.mVibrateNotificationKey);
        boolean z11 = notificationRecord.isIntercepted() && (notificationRecord.getSuppressedVisualEffects() & 32) != 0;
        if (!notificationRecord.isUpdate && notificationRecord.getImportance() > 1 && !z11 && isNotificationForCurrentUser(notificationRecord)) {
            sendAccessibilityEvent(notificationRecord);
            z2 = true;
        } else {
            z2 = false;
        }
        if (z && isNotificationForCurrentUser(notificationRecord) && this.mSystemReady && this.mAudioManager != null) {
            android.net.Uri sound = notificationRecord.getSound();
            z4 = (sound == null || android.net.Uri.EMPTY.equals(sound)) ? false : true;
            android.os.VibrationEffect vibration = notificationRecord.getVibration();
            if (vibration == null && z4 && this.mAudioManager.getRingerModeInternal() == 1 && this.mAudioManager.getStreamVolume(android.media.AudioAttributes.toLegacyStreamType(notificationRecord.getAudioAttributes())) == 0) {
                vibration = this.mVibratorHelper.createFallbackVibration((notificationRecord.getFlags() & 4) != 0);
            }
            z3 = vibration != null;
            if ((z4 || z3) && !shouldMuteNotificationLocked(notificationRecord)) {
                if (!z2) {
                    sendAccessibilityEvent(notificationRecord);
                }
                if (DBG) {
                    android.util.Slog.v(TAG, "Interrupting!");
                }
                boolean isInsistentUpdate = isInsistentUpdate(notificationRecord);
                if (!z4) {
                    z7 = false;
                } else if (isInsistentUpdate) {
                    z7 = true;
                } else {
                    if (isInCall()) {
                        playInCallNotification();
                        z7 = true;
                    } else {
                        z7 = playSound(notificationRecord, sound);
                    }
                    if (z7) {
                        this.mSoundNotificationKey = key;
                    }
                }
                boolean z12 = this.mAudioManager.getRingerModeInternal() == 0;
                if (!isInCall() && z3 && !z12) {
                    if (isInsistentUpdate) {
                        z8 = true;
                    } else {
                        boolean playVibration = playVibration(notificationRecord, vibration, z4);
                        z8 = playVibration;
                        if (playVibration) {
                            this.mVibrateNotificationKey = key;
                            z8 = playVibration;
                        }
                    }
                } else {
                    z8 = false;
                }
                this.mAccessibilityManager.startFlashNotificationEvent(getContext(), 3, notificationRecord.getSbn().getPackageName());
                r9 = z8;
                r12 = z7;
            } else if ((notificationRecord.getFlags() & 4) == 0) {
                r9 = 0;
                r12 = 0;
            } else {
                r9 = 0;
                r12 = 0;
                z4 = false;
            }
        } else {
            z3 = false;
            r9 = 0;
            r12 = 0;
            z4 = false;
        }
        if (z9 && !z4) {
            clearSoundLocked();
        }
        if (z10 && !z3) {
            clearVibrateLocked();
        }
        boolean remove = this.mLights.remove(key);
        if (canShowLightsLocked(notificationRecord, z)) {
            this.mLights.add(key);
            updateLightsLocked();
            if (this.mUseAttentionLight && this.mAttentionLight != null) {
                this.mAttentionLight.pulse();
            }
            i = 1;
        } else {
            if (remove) {
                updateLightsLocked();
            }
            i = 0;
        }
        int i2 = (r12 != 0 ? 2 : 0) | r9 | (i == 0 ? 0 : 4);
        if (i2 <= 0) {
            z5 = false;
            z6 = true;
        } else {
            if (notificationRecord.getSbn().isGroup() && notificationRecord.getSbn().getNotification().isGroupSummary()) {
                if (DEBUG_INTERRUPTIVENESS) {
                    android.util.Slog.v(TAG, "INTERRUPTIVENESS: " + notificationRecord.getKey() + " is not interruptive: summary");
                }
            } else if (notificationRecord.canBubble()) {
                if (DEBUG_INTERRUPTIVENESS) {
                    android.util.Slog.v(TAG, "INTERRUPTIVENESS: " + notificationRecord.getKey() + " is not interruptive: bubble");
                }
            } else {
                notificationRecord.setInterruptive(true);
                if (DEBUG_INTERRUPTIVENESS) {
                    android.util.Slog.v(TAG, "INTERRUPTIVENESS: " + notificationRecord.getKey() + " is interruptive: alerted");
                }
            }
            z6 = true;
            com.android.internal.logging.MetricsLogger.action(notificationRecord.getLogMaker().setCategory(com.android.internal.util.FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__SET_PREFERENTIAL_NETWORK_SERVICE_ENABLED).setType(1).setSubtype(i2));
            z5 = false;
            com.android.server.EventLogTags.writeNotificationAlert(key, r9, r12, i, 0);
        }
        if (r9 != 0 || r12 != 0) {
            z5 = z6;
        }
        notificationRecord.setAudiblyAlerted(z5);
        return i2;
    }

    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    boolean canShowLightsLocked(com.android.server.notification.NotificationRecord notificationRecord, boolean z) {
        if (!this.mHasLight || !this.mNotificationPulseEnabled || notificationRecord.getLight() == null || !z || (notificationRecord.getSuppressedVisualEffects() & 8) != 0) {
            return false;
        }
        android.app.Notification notification = notificationRecord.getNotification();
        if (!notificationRecord.isUpdate || (notification.flags & 8) == 0) {
            return ((notificationRecord.getSbn().isGroup() && notificationRecord.getNotification().suppressAlertingDueToGrouping()) || isInCall() || !isNotificationForCurrentUser(notificationRecord)) ? false : true;
        }
        return false;
    }

    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    boolean isInsistentUpdate(com.android.server.notification.NotificationRecord notificationRecord) {
        return (java.util.Objects.equals(notificationRecord.getKey(), this.mSoundNotificationKey) || java.util.Objects.equals(notificationRecord.getKey(), this.mVibrateNotificationKey)) && isCurrentlyInsistent();
    }

    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    boolean isCurrentlyInsistent() {
        return isLoopingRingtoneNotification(this.mNotificationsByKey.get(this.mSoundNotificationKey)) || isLoopingRingtoneNotification(this.mNotificationsByKey.get(this.mVibrateNotificationKey));
    }

    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    boolean shouldMuteNotificationLocked(com.android.server.notification.NotificationRecord notificationRecord) {
        android.app.Notification notification = notificationRecord.getNotification();
        if ((notificationRecord.isUpdate && (notification.flags & 8) != 0) || notificationRecord.shouldPostSilently()) {
            return true;
        }
        java.lang.String disableNotificationEffects = disableNotificationEffects(notificationRecord);
        if (disableNotificationEffects != null) {
            com.android.server.notification.ZenLog.traceDisableEffects(notificationRecord, disableNotificationEffects);
            return true;
        }
        if (notificationRecord.isIntercepted()) {
            return true;
        }
        if (notificationRecord.getSbn().isGroup() && notification.suppressAlertingDueToGrouping()) {
            return true;
        }
        if (this.mUsageStats.isAlertRateLimited(notificationRecord.getSbn().getPackageName())) {
            android.util.Slog.e(TAG, "Muting recently noisy " + notificationRecord.getKey());
            return true;
        }
        if (!isCurrentlyInsistent() || isInsistentUpdate(notificationRecord)) {
            return notificationRecord.isUpdate && !notificationRecord.isInterruptive() && (notificationRecord.canBubble() && (notificationRecord.isFlagBubbleRemoved() || notificationRecord.getNotification().isBubbleNotification())) && notificationRecord.getNotification().getBubbleMetadata() != null && notificationRecord.getNotification().getBubbleMetadata().isNotificationSuppressed();
        }
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    private boolean isLoopingRingtoneNotification(com.android.server.notification.NotificationRecord notificationRecord) {
        if (notificationRecord != null && notificationRecord.getAudioAttributes().getUsage() == 6 && (notificationRecord.getNotification().flags & 4) != 0) {
            return true;
        }
        return false;
    }

    private boolean playSound(com.android.server.notification.NotificationRecord notificationRecord, android.net.Uri uri) {
        if (!(android.media.audio.Flags.focusExclusiveWithRecording() ? this.mAudioManager.shouldNotificationSoundPlay(notificationRecord.getAudioAttributes()) : (this.mAudioManager.isAudioFocusExclusive() || this.mAudioManager.getStreamVolume(android.media.AudioAttributes.toLegacyStreamType(notificationRecord.getAudioAttributes())) == 0) ? false : true)) {
            if (DBG) {
                android.util.Slog.v(TAG, "Not playing sound " + uri + " due to focus/volume");
            }
            return false;
        }
        boolean z = (notificationRecord.getNotification().flags & 4) != 0;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.media.IRingtonePlayer ringtonePlayer = this.mAudioManager.getRingtonePlayer();
            if (ringtonePlayer != null) {
                if (DBG) {
                    android.util.Slog.v(TAG, "Playing sound " + uri + " with attributes " + notificationRecord.getAudioAttributes());
                }
                ringtonePlayer.playAsync(uri, notificationRecord.getSbn().getUser(), z, notificationRecord.getAudioAttributes(), 1.0f);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            }
        } catch (android.os.RemoteException e) {
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        return false;
    }

    private boolean playVibration(final com.android.server.notification.NotificationRecord notificationRecord, final android.os.VibrationEffect vibrationEffect, boolean z) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (z) {
                new java.lang.Thread(new java.lang.Runnable() { // from class: com.android.server.notification.NotificationManagerService$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.notification.NotificationManagerService.this.lambda$playVibration$10(notificationRecord, vibrationEffect);
                    }
                }).start();
            } else {
                vibrate(notificationRecord, vibrationEffect, false);
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$playVibration$10(com.android.server.notification.NotificationRecord notificationRecord, android.os.VibrationEffect vibrationEffect) {
        int focusRampTimeMs = this.mAudioManager.getFocusRampTimeMs(3, notificationRecord.getAudioAttributes());
        if (DBG) {
            android.util.Slog.v(TAG, "Delaying vibration for notification " + notificationRecord.getKey() + " by " + focusRampTimeMs + "ms");
        }
        try {
            java.lang.Thread.sleep(focusRampTimeMs);
        } catch (java.lang.InterruptedException e) {
        }
        synchronized (this.mNotificationLock) {
            try {
                if (this.mNotificationsByKey.get(notificationRecord.getKey()) != null) {
                    if (notificationRecord.getKey().equals(this.mVibrateNotificationKey)) {
                        vibrate(notificationRecord, vibrationEffect, true);
                    } else if (DBG) {
                        android.util.Slog.v(TAG, "No vibration for notification " + notificationRecord.getKey() + ": a new notification is vibrating, or effects were cleared while waiting");
                    }
                } else {
                    android.util.Slog.w(TAG, "No vibration for canceled notification " + notificationRecord.getKey());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void vibrate(com.android.server.notification.NotificationRecord notificationRecord, android.os.VibrationEffect vibrationEffect, boolean z) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Notification (");
        sb.append(notificationRecord.getSbn().getOpPkg());
        sb.append(" ");
        sb.append(notificationRecord.getSbn().getUid());
        sb.append(") ");
        sb.append(z ? "(Delayed)" : "");
        this.mVibratorHelper.vibrate(vibrationEffect, notificationRecord.getAudioAttributes(), sb.toString());
    }

    private boolean isNotificationForCurrentUser(com.android.server.notification.NotificationRecord notificationRecord) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            int currentUser = android.app.ActivityManager.getCurrentUser();
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return notificationRecord.getUserId() == -1 || notificationRecord.getUserId() == currentUser || this.mUserProfiles.isCurrentProfile(notificationRecord.getUserId());
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    protected void playInCallNotification() {
        android.content.ContentResolver contentResolver = getContext().getContentResolver();
        if (this.mAudioManager.getRingerModeInternal() == 2 && android.provider.Settings.Secure.getIntForUser(contentResolver, "in_call_notification_enabled", 1, contentResolver.getUserId()) != 0) {
            new java.lang.Thread() { // from class: com.android.server.notification.NotificationManagerService.16
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        android.media.IRingtonePlayer ringtonePlayer = com.android.server.notification.NotificationManagerService.this.mAudioManager.getRingtonePlayer();
                        if (ringtonePlayer != null) {
                            if (com.android.server.notification.NotificationManagerService.this.mCallNotificationToken != null) {
                                ringtonePlayer.stop(com.android.server.notification.NotificationManagerService.this.mCallNotificationToken);
                            }
                            com.android.server.notification.NotificationManagerService.this.mCallNotificationToken = new android.os.Binder();
                            ringtonePlayer.play(com.android.server.notification.NotificationManagerService.this.mCallNotificationToken, com.android.server.notification.NotificationManagerService.this.mInCallNotificationUri, com.android.server.notification.NotificationManagerService.this.mInCallNotificationAudioAttributes, com.android.server.notification.NotificationManagerService.this.mInCallNotificationVolume, false);
                        }
                    } catch (android.os.RemoteException e) {
                    } catch (java.lang.Throwable th) {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }.start();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mToastQueue"})
    void showNextToastLocked(boolean z) {
        if (this.mIsCurrentToastShown) {
            return;
        }
        com.android.server.notification.toast.ToastRecord toastRecord = this.mToastQueue.get(0);
        while (toastRecord != null) {
            int userId = android.os.UserHandle.getUserId(toastRecord.uid);
            boolean z2 = !this.mToastRateLimitingDisabledUids.contains(java.lang.Integer.valueOf(toastRecord.uid));
            boolean z3 = this.mToastRateLimiter.isWithinQuota(userId, toastRecord.pkg, TOAST_QUOTA_TAG) || isExemptFromRateLimiting(toastRecord.pkg, userId);
            boolean isPackageInForegroundForToast = isPackageInForegroundForToast(toastRecord.uid);
            if (tryShowToast(toastRecord, z2, z3, isPackageInForegroundForToast)) {
                scheduleDurationReachedLocked(toastRecord, z);
                this.mIsCurrentToastShown = true;
                if (z2 && !isPackageInForegroundForToast) {
                    this.mToastRateLimiter.noteEvent(userId, toastRecord.pkg, TOAST_QUOTA_TAG);
                    return;
                }
                return;
            }
            int indexOf = this.mToastQueue.indexOf(toastRecord);
            if (indexOf >= 0) {
                com.android.server.notification.toast.ToastRecord remove = this.mToastQueue.remove(indexOf);
                this.mWindowManagerInternal.removeWindowToken(remove.windowToken, true, remove.displayId);
            }
            toastRecord = this.mToastQueue.size() > 0 ? this.mToastQueue.get(0) : null;
        }
    }

    private boolean tryShowToast(com.android.server.notification.toast.ToastRecord toastRecord, boolean z, boolean z2, boolean z3) {
        if (z && !z2 && !z3) {
            reportCompatRateLimitingToastsChange(toastRecord.uid);
            android.util.Slog.w(TAG, "Package " + toastRecord.pkg + " is above allowed toast quota, the following toast was blocked and discarded: " + toastRecord);
            return false;
        }
        if (blockToast(toastRecord.uid, toastRecord.isSystemToast, toastRecord.isAppRendered(), z3)) {
            android.util.Slog.w(TAG, "Blocking custom toast from package " + toastRecord.pkg + " due to package not in the foreground at the time of showing the toast");
            return false;
        }
        return toastRecord.show();
    }

    private boolean isExemptFromRateLimiting(java.lang.String str, int i) {
        try {
            return this.mPackageManager.checkPermission("android.permission.UNLIMITED_TOASTS", str, i) == 0;
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to connect with package manager");
            return false;
        }
    }

    private void reportCompatRateLimitingToastsChange(int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mPlatformCompat.reportChangeByUid(RATE_LIMIT_TOASTS, i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Unexpected exception while reporting toast was blocked due to rate limiting", e);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mToastQueue"})
    void cancelToastLocked(int i) {
        com.android.server.notification.toast.ToastRecord toastRecord = this.mToastQueue.get(i);
        toastRecord.hide();
        if (i == 0) {
            this.mIsCurrentToastShown = false;
        }
        com.android.server.notification.toast.ToastRecord remove = this.mToastQueue.remove(i);
        scheduleKillTokenTimeout(remove);
        keepProcessAliveForToastIfNeededLocked(toastRecord.pid);
        if (this.mToastQueue.size() > 0) {
            showNextToastLocked(remove instanceof com.android.server.notification.toast.TextToastRecord);
        }
    }

    void finishWindowTokenLocked(android.os.IBinder iBinder, int i) {
        this.mHandler.removeCallbacksAndMessages(iBinder);
        this.mWindowManagerInternal.removeWindowToken(iBinder, true, i);
    }

    @com.android.internal.annotations.GuardedBy({"mToastQueue"})
    private void scheduleDurationReachedLocked(com.android.server.notification.toast.ToastRecord toastRecord, boolean z) {
        this.mHandler.removeCallbacksAndMessages(toastRecord);
        android.os.Message obtain = android.os.Message.obtain(this.mHandler, 2, toastRecord);
        int recommendedTimeoutMillis = this.mAccessibilityManager.getRecommendedTimeoutMillis(toastRecord.getDuration() == 1 ? LONG_DELAY : 2000, 2);
        if (z) {
            recommendedTimeoutMillis += 250;
        }
        if (toastRecord instanceof com.android.server.notification.toast.TextToastRecord) {
            recommendedTimeoutMillis += com.android.internal.util.FrameworkStatsLog.DEVICE_ROTATED;
        }
        this.mHandler.sendMessageDelayed(obtain, recommendedTimeoutMillis);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDurationReached(com.android.server.notification.toast.ToastRecord toastRecord) {
        if (DBG) {
            android.util.Slog.d(TAG, "Timeout pkg=" + toastRecord.pkg + " token=" + toastRecord.token);
        }
        synchronized (this.mToastQueue) {
            try {
                int indexOfToastLocked = indexOfToastLocked(toastRecord.pkg, toastRecord.token);
                if (indexOfToastLocked >= 0) {
                    cancelToastLocked(indexOfToastLocked);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mToastQueue"})
    private void scheduleKillTokenTimeout(com.android.server.notification.toast.ToastRecord toastRecord) {
        this.mHandler.removeCallbacksAndMessages(toastRecord);
        this.mHandler.sendMessageDelayed(android.os.Message.obtain(this.mHandler, 7, toastRecord), 11000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleKillTokenTimeout(com.android.server.notification.toast.ToastRecord toastRecord) {
        if (DBG) {
            android.util.Slog.d(TAG, "Kill Token Timeout token=" + toastRecord.windowToken);
        }
        synchronized (this.mToastQueue) {
            finishWindowTokenLocked(toastRecord.windowToken, toastRecord.displayId);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mToastQueue"})
    int indexOfToastLocked(java.lang.String str, android.os.IBinder iBinder) {
        java.util.ArrayList<com.android.server.notification.toast.ToastRecord> arrayList = this.mToastQueue;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            com.android.server.notification.toast.ToastRecord toastRecord = arrayList.get(i);
            if (toastRecord.pkg.equals(str) && toastRecord.token == iBinder) {
                return i;
            }
        }
        return -1;
    }

    public void keepProcessAliveForToastIfNeeded(int i) {
        synchronized (this.mToastQueue) {
            keepProcessAliveForToastIfNeededLocked(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mToastQueue"})
    public void keepProcessAliveForToastIfNeededLocked(int i) {
        java.util.ArrayList<com.android.server.notification.toast.ToastRecord> arrayList = this.mToastQueue;
        int size = arrayList.size();
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            com.android.server.notification.toast.ToastRecord toastRecord = arrayList.get(i3);
            if (toastRecord.pid == i && toastRecord.keepProcessAlive()) {
                i2++;
            }
        }
        try {
            this.mAm.setProcessImportant(this.mForegroundToken, i, i2 > 0, "toast");
        } catch (android.os.RemoteException e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isPackageInForegroundForToast(int i) {
        return this.mAtm.hasResumedActivity(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean blockToast(int i, boolean z, boolean z2, boolean z3) {
        return z2 && !z && !z3 && android.app.compat.CompatChanges.isChangeEnabled(CHANGE_BACKGROUND_CUSTOM_TOAST_BLOCK, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRankingReconsideration(android.os.Message message) {
        if (message.obj instanceof com.android.server.notification.RankingReconsideration) {
            com.android.server.notification.RankingReconsideration rankingReconsideration = (com.android.server.notification.RankingReconsideration) message.obj;
            rankingReconsideration.run();
            synchronized (this.mNotificationLock) {
                try {
                    com.android.server.notification.NotificationRecord notificationRecord = this.mNotificationsByKey.get(rankingReconsideration.getKey());
                    if (notificationRecord == null) {
                        return;
                    }
                    int findNotificationRecordIndexLocked = findNotificationRecordIndexLocked(notificationRecord);
                    boolean isIntercepted = notificationRecord.isIntercepted();
                    int packageVisibilityOverride = notificationRecord.getPackageVisibilityOverride();
                    boolean isInterruptive = notificationRecord.isInterruptive();
                    rankingReconsideration.applyChangesLocked(notificationRecord);
                    applyZenModeLocked(notificationRecord);
                    this.mRankingHelper.sort(this.mNotificationList);
                    boolean z = (findNotificationRecordIndexLocked != findNotificationRecordIndexLocked(notificationRecord)) || (isIntercepted != notificationRecord.isIntercepted()) || (packageVisibilityOverride != notificationRecord.getPackageVisibilityOverride()) || (notificationRecord.canBubble() && isInterruptive != notificationRecord.isInterruptive());
                    if (isIntercepted && !notificationRecord.isIntercepted() && notificationRecord.isNewEnoughForAlerting(java.lang.System.currentTimeMillis())) {
                        com.android.server.notification.Flags.refactorAttentionHelper();
                        this.mAttentionHelper.buzzBeepBlinkLocked(notificationRecord, new com.android.server.notification.NotificationAttentionHelper.Signals(this.mUserProfiles.isCurrentProfile(notificationRecord.getUserId()), this.mListenerHints));
                        com.android.server.notification.ZenLog.traceAlertOnUpdatedIntercept(notificationRecord);
                    }
                    if (z) {
                        this.mHandler.scheduleSendRankingUpdate();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    void handleRankingSort() {
        if (this.mRankingHelper == null) {
            return;
        }
        synchronized (this.mNotificationLock) {
            try {
                int size = this.mNotificationList.size();
                android.util.ArrayMap arrayMap = new android.util.ArrayMap(size);
                for (int i = 0; i < size; i++) {
                    com.android.server.notification.NotificationRecord notificationRecord = this.mNotificationList.get(i);
                    arrayMap.put(notificationRecord.getKey(), new com.android.server.notification.NotificationRecordExtractorData(i, notificationRecord.getPackageVisibilityOverride(), notificationRecord.canShowBadge(), notificationRecord.canBubble(), notificationRecord.getNotification().isBubbleNotification(), notificationRecord.getChannel(), notificationRecord.getGroupKey(), notificationRecord.getPeopleOverride(), notificationRecord.getSnoozeCriteria(), java.lang.Integer.valueOf(notificationRecord.getUserSentiment()), java.lang.Integer.valueOf(notificationRecord.getSuppressedVisualEffects()), notificationRecord.getSystemGeneratedSmartActions(), notificationRecord.getSmartReplies(), notificationRecord.getImportance(), notificationRecord.getRankingScore(), notificationRecord.isConversation(), notificationRecord.getProposedImportance(), notificationRecord.hasSensitiveContent()));
                    this.mRankingHelper.extractSignals(notificationRecord);
                }
                this.mRankingHelper.sort(this.mNotificationList);
                for (int i2 = 0; i2 < size; i2++) {
                    com.android.server.notification.NotificationRecord notificationRecord2 = this.mNotificationList.get(i2);
                    if (arrayMap.containsKey(notificationRecord2.getKey())) {
                        if (((com.android.server.notification.NotificationRecordExtractorData) arrayMap.get(notificationRecord2.getKey())).hasDiffForRankingLocked(notificationRecord2, i2)) {
                            this.mHandler.scheduleSendRankingUpdate();
                        }
                        if (notificationRecord2.hasPendingLogUpdate()) {
                            if (((com.android.server.notification.NotificationRecordExtractorData) arrayMap.get(notificationRecord2.getKey())).hasDiffForLoggingLocked(notificationRecord2, i2)) {
                                this.mNotificationRecordLogger.logNotificationAdjusted(notificationRecord2, i2, 0, getGroupInstanceId(notificationRecord2.getSbn().getGroupKey()));
                            }
                            notificationRecord2.setPendingLogUpdate(false);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    private void recordCallerLocked(com.android.server.notification.NotificationRecord notificationRecord) {
        if (this.mZenModeHelper.isCall(notificationRecord)) {
            this.mZenModeHelper.recordCaller(notificationRecord);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    private void applyZenModeLocked(com.android.server.notification.NotificationRecord notificationRecord) {
        notificationRecord.setIntercepted(this.mZenModeHelper.shouldIntercept(notificationRecord));
        if (notificationRecord.isIntercepted()) {
            notificationRecord.setSuppressedVisualEffects(this.mZenModeHelper.getConsolidatedNotificationPolicy().suppressedVisualEffects);
        } else {
            notificationRecord.setSuppressedVisualEffects(0);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    private int findNotificationRecordIndexLocked(com.android.server.notification.NotificationRecord notificationRecord) {
        return this.mRankingHelper.indexOf(this.mNotificationList, notificationRecord);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSendRankingUpdate() {
        synchronized (this.mNotificationLock) {
            this.mListeners.notifyRankingUpdateLocked(null);
        }
    }

    private void scheduleListenerHintsChanged(int i) {
        com.android.server.notification.Flags.notificationReduceMessagequeueUsage();
        this.mHandler.obtainMessage(5, i, 0).sendToTarget();
    }

    private void scheduleInterruptionFilterChanged(int i) {
        com.android.server.notification.Flags.notificationReduceMessagequeueUsage();
        this.mHandler.obtainMessage(6, i, 0).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleListenerHintsChanged(int i) {
        synchronized (this.mNotificationLock) {
            this.mListeners.notifyListenerHintsChangedLocked(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleListenerInterruptionFilterChanged(int i) {
        synchronized (this.mNotificationLock) {
            this.mListeners.notifyInterruptionFilterChanged(i);
        }
    }

    void handleOnPackageChanged(boolean z, int i, java.lang.String[] strArr, int[] iArr) {
        this.mListeners.onPackagesChanged(z, strArr, iArr);
        this.mAssistants.onPackagesChanged(z, strArr, iArr);
        this.mConditionProviders.onPackagesChanged(z, strArr, iArr);
        boolean onPackagesChanged = this.mPreferencesHelper.onPackagesChanged(z, i, strArr, iArr) | z;
        if (z) {
            int min = java.lang.Math.min(strArr.length, iArr.length);
            for (int i2 = 0; i2 < min; i2++) {
                java.lang.String str = strArr[i2];
                int userId = android.os.UserHandle.getUserId(iArr[i2]);
                this.mArchive.removePackageNotifications(str, userId);
                this.mHistoryManager.onPackageRemoved(userId, str);
            }
        }
        if (onPackagesChanged) {
            handleSavePolicyFile();
        }
    }

    protected class WorkerHandler extends android.os.Handler {
        public WorkerHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 2:
                    com.android.server.notification.NotificationManagerService.this.handleDurationReached((com.android.server.notification.toast.ToastRecord) message.obj);
                    break;
                case 4:
                    com.android.server.notification.NotificationManagerService.this.handleSendRankingUpdate();
                    break;
                case 5:
                    com.android.server.notification.NotificationManagerService.this.handleListenerHintsChanged(message.arg1);
                    break;
                case 6:
                    com.android.server.notification.NotificationManagerService.this.handleListenerInterruptionFilterChanged(message.arg1);
                    break;
                case 7:
                    com.android.server.notification.NotificationManagerService.this.handleKillTokenTimeout((com.android.server.notification.toast.ToastRecord) message.obj);
                    break;
                case 8:
                    com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    com.android.server.notification.NotificationManagerService.this.handleOnPackageChanged(((java.lang.Boolean) someArgs.arg1).booleanValue(), someArgs.argi1, (java.lang.String[]) someArgs.arg2, (int[]) someArgs.arg3);
                    someArgs.recycle();
                    break;
            }
        }

        protected void scheduleSendRankingUpdate() {
            com.android.server.notification.Flags.notificationReduceMessagequeueUsage();
            sendMessage(android.os.Message.obtain(this, 4));
        }

        protected void scheduleCancelNotification(com.android.server.notification.NotificationManagerService.CancelNotificationRunnable cancelNotificationRunnable) {
            com.android.server.notification.Flags.notificationReduceMessagequeueUsage();
            sendMessage(android.os.Message.obtain(this, cancelNotificationRunnable));
        }

        protected void scheduleOnPackageChanged(boolean z, int i, java.lang.String[] strArr, int[] iArr) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = java.lang.Boolean.valueOf(z);
            obtain.argi1 = i;
            obtain.arg2 = strArr;
            obtain.arg3 = iArr;
            sendMessage(android.os.Message.obtain(this, 8, obtain));
        }
    }

    private final class RankingHandlerWorker extends android.os.Handler implements com.android.server.notification.RankingHandler {
        public RankingHandlerWorker(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1000:
                    com.android.server.notification.NotificationManagerService.this.handleRankingReconsideration(message);
                    break;
                case 1001:
                    com.android.server.notification.NotificationManagerService.this.handleRankingSort();
                    break;
            }
        }

        @Override // com.android.server.notification.RankingHandler
        public void requestSort() {
            com.android.server.notification.Flags.notificationReduceMessagequeueUsage();
            android.os.Message obtain = android.os.Message.obtain();
            obtain.what = 1001;
            sendMessage(obtain);
        }

        @Override // com.android.server.notification.RankingHandler
        public void requestReconsideration(com.android.server.notification.RankingReconsideration rankingReconsideration) {
            sendMessageDelayed(android.os.Message.obtain(this, 1000, rankingReconsideration), rankingReconsideration.getDelay(java.util.concurrent.TimeUnit.MILLISECONDS));
        }
    }

    static int clamp(int i, int i2, int i3) {
        return i < i2 ? i2 : i > i3 ? i3 : i;
    }

    void sendAccessibilityEvent(com.android.server.notification.NotificationRecord notificationRecord) {
        if (!this.mAccessibilityManager.isEnabled()) {
            return;
        }
        android.app.Notification notification = notificationRecord.getNotification();
        java.lang.String packageName = notificationRecord.getSbn().getPackageName();
        android.view.accessibility.AccessibilityEvent obtain = android.view.accessibility.AccessibilityEvent.obtain(64);
        obtain.setPackageName(packageName);
        obtain.setClassName(android.app.Notification.class.getName());
        int packageVisibilityOverride = notificationRecord.getPackageVisibilityOverride();
        if (packageVisibilityOverride == -1000) {
            packageVisibilityOverride = notification.visibility;
        }
        int identifier = notificationRecord.getUser().getIdentifier();
        if ((identifier >= 0 && this.mKeyguardManager.isDeviceLocked(identifier)) && packageVisibilityOverride != 1) {
            obtain.setParcelableData(notification.publicVersion);
        } else {
            obtain.setParcelableData(notification);
        }
        java.lang.CharSequence charSequence = notification.tickerText;
        if (!android.text.TextUtils.isEmpty(charSequence)) {
            obtain.getText().add(charSequence);
        }
        this.mAccessibilityManager.sendAccessibilityEvent(obtain);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    public boolean removeFromNotificationListsLocked(com.android.server.notification.NotificationRecord notificationRecord) {
        boolean z;
        com.android.server.notification.NotificationRecord findNotificationByListLocked = findNotificationByListLocked(this.mNotificationList, notificationRecord.getKey());
        if (findNotificationByListLocked == null) {
            z = false;
        } else {
            this.mNotificationList.remove(findNotificationByListLocked);
            this.mNotificationsByKey.remove(findNotificationByListLocked.getSbn().getKey());
            z = true;
        }
        while (true) {
            com.android.server.notification.NotificationRecord findNotificationByListLocked2 = findNotificationByListLocked(this.mEnqueuedNotifications, notificationRecord.getKey());
            if (findNotificationByListLocked2 != null) {
                this.mEnqueuedNotifications.remove(findNotificationByListLocked2);
            } else {
                return z;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    public void cancelNotificationLocked(com.android.server.notification.NotificationRecord notificationRecord, boolean z, int i, boolean z2, java.lang.String str, long j) {
        cancelNotificationLocked(notificationRecord, z, i, -1, -1, z2, str, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    public void cancelNotificationLocked(final com.android.server.notification.NotificationRecord notificationRecord, boolean z, int i, int i2, int i3, boolean z2, java.lang.String str, long j) {
        android.app.PendingIntent pendingIntent;
        java.lang.String key = notificationRecord.getKey();
        cancelScheduledTimeoutLocked(notificationRecord);
        recordCallerLocked(notificationRecord);
        if (notificationRecord.getStats().getDismissalSurface() == -1) {
            notificationRecord.recordDismissalSurface(0);
        }
        if (z && (pendingIntent = notificationRecord.getNotification().deleteIntent) != null) {
            try {
                ((android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class)).clearPendingIntentAllowBgActivityStarts(pendingIntent.getTarget(), ALLOWLIST_TOKEN);
                pendingIntent.send();
            } catch (android.app.PendingIntent.CanceledException e) {
                android.util.Slog.w(TAG, "canceled PendingIntent for " + notificationRecord.getSbn().getPackageName(), e);
            }
        }
        if (z2) {
            if (notificationRecord.getNotification().getSmallIcon() != null) {
                if (i != 18) {
                    notificationRecord.isCanceled = true;
                }
                this.mListeners.notifyRemovedLocked(notificationRecord, i, notificationRecord.getStats());
                this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.notification.NotificationManagerService.17
                    @Override // java.lang.Runnable
                    public void run() {
                        com.android.server.notification.NotificationManagerService.this.mGroupHelper.onNotificationRemoved(notificationRecord.getSbn());
                    }
                });
                if (com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.callstyleCallbackApi()) {
                    notifyCallNotificationEventListenerOnRemoved(notificationRecord);
                }
            }
            com.android.server.notification.Flags.refactorAttentionHelper();
            this.mAttentionHelper.clearEffectsLocked(key);
        }
        switch (i) {
            case 2:
            case 3:
            case 10:
            case 11:
                this.mUsageStats.registerDismissedByUser(notificationRecord);
                break;
            case 8:
            case 9:
                this.mUsageStats.registerRemovedByApp(notificationRecord);
                this.mUsageStatsManagerInternal.reportNotificationRemoved(notificationRecord.getSbn().getOpPkg(), notificationRecord.getUser(), j);
                break;
        }
        java.lang.String groupKey = notificationRecord.getGroupKey();
        com.android.server.notification.NotificationRecord notificationRecord2 = this.mSummaryByGroupKey.get(groupKey);
        if (notificationRecord2 != null && notificationRecord2.getKey().equals(key)) {
            this.mSummaryByGroupKey.remove(groupKey);
        }
        android.util.ArrayMap<java.lang.String, java.lang.String> arrayMap = this.mAutobundledSummaries.get(java.lang.Integer.valueOf(notificationRecord.getSbn().getUserId()));
        if (arrayMap != null && notificationRecord.getSbn().getKey().equals(arrayMap.get(notificationRecord.getSbn().getPackageName()))) {
            arrayMap.remove(notificationRecord.getSbn().getPackageName());
        }
        if (i != 20) {
            this.mArchive.record(notificationRecord.getSbn(), i);
        }
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        android.metrics.LogMaker subtype = notificationRecord.getItemLogMaker().setType(5).setSubtype(i);
        if (i2 != -1 && i3 != -1) {
            subtype.addTaggedData(798, java.lang.Integer.valueOf(i2)).addTaggedData(1395, java.lang.Integer.valueOf(i3));
        }
        com.android.internal.logging.MetricsLogger.action(subtype);
        com.android.server.EventLogTags.writeNotificationCanceled(key, i, notificationRecord.getLifespanMs(currentTimeMillis), notificationRecord.getFreshnessMs(currentTimeMillis), notificationRecord.getExposureMs(currentTimeMillis), i2, i3, str);
        if (z2) {
            this.mNotificationRecordLogger.logNotificationCancelled(notificationRecord, i, notificationRecord.getStats().getDismissalSurface());
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void updateUriPermissions(@android.annotation.Nullable com.android.server.notification.NotificationRecord notificationRecord, @android.annotation.Nullable com.android.server.notification.NotificationRecord notificationRecord2, java.lang.String str, int i) {
        updateUriPermissions(notificationRecord, notificationRecord2, str, i, false);
    }

    @com.android.internal.annotations.VisibleForTesting
    void updateUriPermissions(@android.annotation.Nullable com.android.server.notification.NotificationRecord notificationRecord, @android.annotation.Nullable com.android.server.notification.NotificationRecord notificationRecord2, java.lang.String str, int i, boolean z) {
        android.os.IBinder iBinder;
        android.os.IBinder iBinder2;
        java.lang.String key = notificationRecord != null ? notificationRecord.getKey() : notificationRecord2.getKey();
        if (DBG) {
            android.util.Slog.d(TAG, key + ": updating permissions");
        }
        android.util.ArraySet<android.net.Uri> grantableUris = notificationRecord != null ? notificationRecord.getGrantableUris() : null;
        android.util.ArraySet<android.net.Uri> grantableUris2 = notificationRecord2 != null ? notificationRecord2.getGrantableUris() : null;
        if (grantableUris == null && grantableUris2 == null) {
            return;
        }
        if (notificationRecord != null) {
            iBinder = notificationRecord.permissionOwner;
        } else {
            iBinder = null;
        }
        if (notificationRecord2 != null && iBinder == null) {
            iBinder = notificationRecord2.permissionOwner;
        }
        if (grantableUris != null && iBinder == null) {
            if (DBG) {
                android.util.Slog.d(TAG, key + ": creating owner");
            }
            iBinder = this.mUgmInternal.newUriPermissionOwner("NOTIF:" + key);
        }
        if (grantableUris == null && iBinder != null && !z) {
            destroyPermissionOwner(iBinder, android.os.UserHandle.getUserId(notificationRecord2.getUid()), key);
            iBinder2 = null;
        } else {
            iBinder2 = iBinder;
        }
        if (grantableUris != null && iBinder2 != null) {
            for (int i2 = 0; i2 < grantableUris.size(); i2++) {
                android.net.Uri valueAt = grantableUris.valueAt(i2);
                if (grantableUris2 == null || !grantableUris2.contains(valueAt)) {
                    if (DBG) {
                        android.util.Slog.d(TAG, key + ": granting " + valueAt);
                    }
                    grantUriPermission(iBinder2, valueAt, notificationRecord.getUid(), str, i);
                }
            }
        }
        if (grantableUris2 != null && iBinder2 != null) {
            for (int i3 = 0; i3 < grantableUris2.size(); i3++) {
                android.net.Uri valueAt2 = grantableUris2.valueAt(i3);
                if (grantableUris == null || !grantableUris.contains(valueAt2)) {
                    if (DBG) {
                        android.util.Slog.d(TAG, key + ": revoking " + valueAt2);
                    }
                    if (z) {
                        revokeUriPermission(iBinder2, valueAt2, android.os.UserHandle.getUserId(notificationRecord2.getUid()), str, i);
                    } else {
                        revokeUriPermission(iBinder2, valueAt2, android.os.UserHandle.getUserId(notificationRecord2.getUid()), null, -1);
                    }
                }
            }
        }
        if (notificationRecord != null) {
            notificationRecord.permissionOwner = iBinder2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void grantUriPermission(android.os.IBinder iBinder, android.net.Uri uri, int i, java.lang.String str, int i2) {
        if (uri == null || !com.android.server.wm.ActivityTaskManagerInternal.ASSIST_KEY_CONTENT.equals(uri.getScheme())) {
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mUgm.grantUriPermissionFromOwner(iBinder, i, str, android.content.ContentProvider.getUriWithoutUserId(uri), 1, android.content.ContentProvider.getUserIdFromUri(uri, android.os.UserHandle.getUserId(i)), i2);
            } catch (android.os.RemoteException e) {
            } catch (java.lang.SecurityException e2) {
                android.util.Slog.e(TAG, "Cannot grant uri access; " + i + " does not own " + uri);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void revokeUriPermission(android.os.IBinder iBinder, android.net.Uri uri, int i, java.lang.String str, int i2) {
        if (uri == null || !com.android.server.wm.ActivityTaskManagerInternal.ASSIST_KEY_CONTENT.equals(uri.getScheme())) {
            return;
        }
        int userIdFromUri = android.content.ContentProvider.getUserIdFromUri(uri, i);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mUgmInternal.revokeUriPermissionFromOwner(iBinder, android.content.ContentProvider.getUriWithoutUserId(uri), 1, userIdFromUri, str, i2);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void destroyPermissionOwner(android.os.IBinder iBinder, int i, java.lang.String str) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (DBG) {
                android.util.Slog.d(TAG, str + ": destroying owner");
            }
            this.mUgmInternal.revokeUriPermissionFromOwner(iBinder, null, -1, i);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    void cancelNotification(int i, int i2, java.lang.String str, java.lang.String str2, int i3, int i4, int i5, boolean z, int i6, int i7, com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo) {
        cancelNotification(i, i2, str, str2, i3, i4, i5, z, i6, i7, -1, -1, managedServiceInfo);
    }

    void cancelNotification(int i, int i2, java.lang.String str, java.lang.String str2, int i3, int i4, int i5, boolean z, int i6, int i7, int i8, int i9, com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo) {
        this.mHandler.scheduleCancelNotification(new com.android.server.notification.NotificationManagerService.CancelNotificationRunnable(i, i2, str, str2, i3, i4, i5, z, i6, i7, i8, i9, managedServiceInfo, android.os.SystemClock.elapsedRealtime()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean notificationMatchesUserId(com.android.server.notification.NotificationRecord notificationRecord, int i, boolean z) {
        return z ? notificationRecord.getUserId() == i : i == -1 || notificationRecord.getUserId() == -1 || notificationRecord.getUserId() == i;
    }

    private boolean notificationMatchesCurrentProfiles(com.android.server.notification.NotificationRecord notificationRecord, int i) {
        return notificationMatchesUserId(notificationRecord, i, false) || this.mUserProfiles.isCurrentProfile(notificationRecord.getUserId());
    }

    /* renamed from: com.android.server.notification.NotificationManagerService$18, reason: invalid class name */
    class AnonymousClass18 implements java.lang.Runnable {
        final /* synthetic */ int val$callingPid;
        final /* synthetic */ int val$callingUid;
        final /* synthetic */ long val$cancellationElapsedTimeMs;
        final /* synthetic */ java.lang.String val$channelId;
        final /* synthetic */ int val$mustHaveFlags;
        final /* synthetic */ int val$mustNotHaveFlags;
        final /* synthetic */ java.lang.String val$pkg;
        final /* synthetic */ int val$reason;
        final /* synthetic */ int val$userId;

        AnonymousClass18(int i, int i2, java.lang.String str, int i3, int i4, int i5, int i6, java.lang.String str2, long j) {
            this.val$callingUid = i;
            this.val$callingPid = i2;
            this.val$pkg = str;
            this.val$userId = i3;
            this.val$mustHaveFlags = i4;
            this.val$mustNotHaveFlags = i5;
            this.val$reason = i6;
            this.val$channelId = str2;
            this.val$cancellationElapsedTimeMs = j;
        }

        @Override // java.lang.Runnable
        public void run() {
            com.android.server.EventLogTags.writeNotificationCancelAll(this.val$callingUid, this.val$callingPid, this.val$pkg, this.val$userId, this.val$mustHaveFlags, this.val$mustNotHaveFlags, this.val$reason, null);
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                final int i = this.val$mustHaveFlags;
                final int i2 = this.val$mustNotHaveFlags;
                com.android.server.notification.NotificationManagerService.FlagChecker flagChecker = new com.android.server.notification.NotificationManagerService.FlagChecker() { // from class: com.android.server.notification.NotificationManagerService$18$$ExternalSyntheticLambda0
                    @Override // com.android.server.notification.NotificationManagerService.FlagChecker
                    public final boolean apply(int i3) {
                        boolean lambda$run$0;
                        lambda$run$0 = com.android.server.notification.NotificationManagerService.AnonymousClass18.lambda$run$0(i, i2, i3);
                        return lambda$run$0;
                    }
                };
                com.android.server.notification.NotificationManagerService.this.cancelAllNotificationsByListLocked(com.android.server.notification.NotificationManagerService.this.mNotificationList, this.val$pkg, true, this.val$channelId, flagChecker, false, this.val$userId, false, this.val$reason, null, true, this.val$cancellationElapsedTimeMs);
                com.android.server.notification.NotificationManagerService.this.cancelAllNotificationsByListLocked(com.android.server.notification.NotificationManagerService.this.mEnqueuedNotifications, this.val$pkg, true, this.val$channelId, flagChecker, false, this.val$userId, false, this.val$reason, null, false, this.val$cancellationElapsedTimeMs);
                com.android.server.notification.NotificationManagerService.this.mSnoozeHelper.cancel(this.val$userId, this.val$pkg);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$run$0(int i, int i2, int i3) {
            return (i3 & i) == i && (i3 & i2) == 0;
        }
    }

    void cancelAllNotificationsInt(int i, int i2, java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i3, int i4, int i5, int i6) {
        this.mHandler.post(new com.android.server.notification.NotificationManagerService.AnonymousClass18(i, i2, str, i5, i3, i4, i6, str2, android.os.SystemClock.elapsedRealtime()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    public void cancelAllNotificationsByListLocked(java.util.ArrayList<com.android.server.notification.NotificationRecord> arrayList, @android.annotation.Nullable java.lang.String str, boolean z, @android.annotation.Nullable java.lang.String str2, com.android.server.notification.NotificationManagerService.FlagChecker flagChecker, boolean z2, int i, boolean z3, int i2, java.lang.String str3, boolean z4, long j) {
        java.util.HashSet hashSet = null;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            com.android.server.notification.NotificationRecord notificationRecord = arrayList.get(size);
            if (z2) {
                if (!notificationMatchesCurrentProfiles(notificationRecord, i)) {
                }
                if ((z || str != null || notificationRecord.getUserId() != -1) && flagChecker.apply(notificationRecord.getFlags()) && ((str == null || notificationRecord.getSbn().getPackageName().equals(str)) && (str2 == null || str2.equals(notificationRecord.getChannel().getId())))) {
                    if (!notificationRecord.getSbn().isGroup() && notificationRecord.getNotification().isGroupChild()) {
                        if (hashSet == null) {
                            hashSet = new java.util.HashSet();
                        }
                        hashSet.add(notificationRecord.getKey());
                    } else {
                        arrayList.remove(size);
                        this.mNotificationsByKey.remove(notificationRecord.getKey());
                        notificationRecord.recordDismissalSentiment(1);
                        cancelNotificationLocked(notificationRecord, z3, i2, z4, str3, j);
                    }
                }
            } else {
                if (!notificationMatchesUserId(notificationRecord, i, false)) {
                }
                if (z) {
                }
                if (!notificationRecord.getSbn().isGroup()) {
                }
                arrayList.remove(size);
                this.mNotificationsByKey.remove(notificationRecord.getKey());
                notificationRecord.recordDismissalSentiment(1);
                cancelNotificationLocked(notificationRecord, z3, i2, z4, str3, j);
            }
        }
        if (hashSet != null) {
            for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
                com.android.server.notification.NotificationRecord notificationRecord2 = arrayList.get(size2);
                if (hashSet.contains(notificationRecord2.getKey())) {
                    arrayList.remove(size2);
                    this.mNotificationsByKey.remove(notificationRecord2.getKey());
                    notificationRecord2.recordDismissalSentiment(1);
                    cancelNotificationLocked(notificationRecord2, z3, i2, z4, str3, j);
                }
            }
            com.android.server.notification.Flags.refactorAttentionHelper();
            this.mAttentionHelper.updateLightsLocked();
        }
    }

    void snoozeNotificationInt(int i, android.service.notification.INotificationListener iNotificationListener, java.lang.String str, long j, java.lang.String str2) {
        synchronized (this.mNotificationLock) {
            try {
                com.android.server.notification.ManagedServices.ManagedServiceInfo checkServiceTokenLocked = this.mListeners.checkServiceTokenLocked(iNotificationListener);
                if (checkServiceTokenLocked == null) {
                    return;
                }
                java.lang.String packageName = checkServiceTokenLocked.component.getPackageName();
                java.lang.String shortString = checkServiceTokenLocked.component.toShortString();
                if ((j > 0 || str2 != null) && str != null) {
                    com.android.server.notification.NotificationRecord findInCurrentAndSnoozedNotificationByKeyLocked = findInCurrentAndSnoozedNotificationByKeyLocked(str);
                    if (findInCurrentAndSnoozedNotificationByKeyLocked == null) {
                        return;
                    }
                    if (checkServiceTokenLocked.enabledAndUserMatches(findInCurrentAndSnoozedNotificationByKeyLocked.getSbn().getNormalizedUserId())) {
                        long updateTimeMs = findInCurrentAndSnoozedNotificationByKeyLocked.getUpdateTimeMs();
                        if (DBG) {
                            android.util.Slog.d(TAG, java.lang.String.format("snooze event(%s, %d, %s, %s)", str, java.lang.Long.valueOf(j), str2, shortString));
                        }
                        this.mHandler.post(new com.android.server.notification.NotificationManagerService.SnoozeNotificationRunnable(str, j, str2));
                        if (isNotificationRecent(updateTimeMs)) {
                            this.mAppOps.noteOpNoThrow(142, i, packageName, (java.lang.String) null, (java.lang.String) null);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void unsnoozeNotificationInt(java.lang.String str, com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo, boolean z) {
        java.lang.String shortString = managedServiceInfo == null ? null : managedServiceInfo.component.toShortString();
        if (DBG) {
            android.util.Slog.d(TAG, java.lang.String.format("unsnooze event(%s, %s)", str, shortString));
        }
        this.mSnoozeHelper.repost(str, z);
        handleSavePolicyFile();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isNotificationRecent(long j) {
        return android.view.contentprotection.flags.Flags.rapidClearNotificationsByListenerAppOpEnabled() && java.lang.System.currentTimeMillis() - j < MIN_PACKAGE_OVERRATE_LOG_INTERVAL;
    }

    /* renamed from: com.android.server.notification.NotificationManagerService$19, reason: invalid class name */
    class AnonymousClass19 implements java.lang.Runnable {
        final /* synthetic */ int val$callingPid;
        final /* synthetic */ int val$callingUid;
        final /* synthetic */ long val$cancellationElapsedTimeMs;
        final /* synthetic */ boolean val$includeCurrentProfiles;
        final /* synthetic */ com.android.server.notification.ManagedServices.ManagedServiceInfo val$listener;
        final /* synthetic */ int val$mustNotHaveFlags;
        final /* synthetic */ int val$reason;
        final /* synthetic */ int val$userId;

        AnonymousClass19(com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo, int i, int i2, int i3, int i4, int i5, boolean z, long j) {
            this.val$listener = managedServiceInfo;
            this.val$callingUid = i;
            this.val$callingPid = i2;
            this.val$userId = i3;
            this.val$reason = i4;
            this.val$mustNotHaveFlags = i5;
            this.val$includeCurrentProfiles = z;
            this.val$cancellationElapsedTimeMs = j;
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                java.lang.String shortString = this.val$listener == null ? null : this.val$listener.component.toShortString();
                com.android.server.EventLogTags.writeNotificationCancelAll(this.val$callingUid, this.val$callingPid, null, this.val$userId, 0, 0, this.val$reason, shortString);
                final int i = this.val$mustNotHaveFlags;
                final int i2 = this.val$reason;
                com.android.server.notification.NotificationManagerService.FlagChecker flagChecker = new com.android.server.notification.NotificationManagerService.FlagChecker() { // from class: com.android.server.notification.NotificationManagerService$19$$ExternalSyntheticLambda0
                    @Override // com.android.server.notification.NotificationManagerService.FlagChecker
                    public final boolean apply(int i3) {
                        boolean lambda$run$0;
                        lambda$run$0 = com.android.server.notification.NotificationManagerService.AnonymousClass19.lambda$run$0(i, i2, i3);
                        return lambda$run$0;
                    }
                };
                com.android.server.notification.NotificationManagerService.this.cancelAllNotificationsByListLocked(com.android.server.notification.NotificationManagerService.this.mNotificationList, null, false, null, flagChecker, this.val$includeCurrentProfiles, this.val$userId, true, this.val$reason, shortString, true, this.val$cancellationElapsedTimeMs);
                com.android.server.notification.NotificationManagerService.this.cancelAllNotificationsByListLocked(com.android.server.notification.NotificationManagerService.this.mEnqueuedNotifications, null, false, null, flagChecker, this.val$includeCurrentProfiles, this.val$userId, true, this.val$reason, shortString, false, this.val$cancellationElapsedTimeMs);
                com.android.server.notification.NotificationManagerService.this.mSnoozeHelper.cancel(this.val$userId, this.val$includeCurrentProfiles);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$run$0(int i, int i2, int i3) {
            if (11 == i2 || 3 == i2) {
                i |= 4096;
            }
            if ((i & i3) != 0) {
                return false;
            }
            return true;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    void cancelAllLocked(int i, int i2, int i3, int i4, com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo, boolean z, int i5) {
        this.mHandler.post(new com.android.server.notification.NotificationManagerService.AnonymousClass19(managedServiceInfo, i, i2, i3, i4, i5, z, android.os.SystemClock.elapsedRealtime()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    public void cancelGroupChildrenLocked(com.android.server.notification.NotificationRecord notificationRecord, int i, int i2, java.lang.String str, boolean z, com.android.server.notification.NotificationManagerService.FlagChecker flagChecker, int i3, long j) {
        if (!notificationRecord.getNotification().isGroupSummary()) {
            return;
        }
        if (notificationRecord.getSbn().getPackageName() == null) {
            if (DBG) {
                android.util.Slog.e(TAG, "No package for group summary: " + notificationRecord.getKey());
                return;
            }
            return;
        }
        cancelGroupChildrenByListLocked(this.mNotificationList, notificationRecord, i, i2, str, z, true, flagChecker, i3, j);
        cancelGroupChildrenByListLocked(this.mEnqueuedNotifications, notificationRecord, i, i2, str, z, false, flagChecker, i3, j);
    }

    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    private void cancelGroupChildrenByListLocked(java.util.ArrayList<com.android.server.notification.NotificationRecord> arrayList, com.android.server.notification.NotificationRecord notificationRecord, int i, int i2, java.lang.String str, boolean z, boolean z2, com.android.server.notification.NotificationManagerService.FlagChecker flagChecker, int i3, long j) {
        java.lang.String packageName = notificationRecord.getSbn().getPackageName();
        int userId = notificationRecord.getUserId();
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            com.android.server.notification.NotificationRecord notificationRecord2 = arrayList.get(size);
            android.service.notification.StatusBarNotification sbn = notificationRecord2.getSbn();
            if (sbn.isGroup() && !sbn.getNotification().isGroupSummary()) {
                if (notificationRecord2.getGroupKey().equals(notificationRecord.getGroupKey())) {
                    if (flagChecker != null) {
                        if (!flagChecker.apply(notificationRecord2.getFlags())) {
                        }
                    }
                    if (notificationRecord2.getChannel().isImportantConversation() && i3 == 2) {
                    }
                    com.android.server.EventLogTags.writeNotificationCancel(i, i2, packageName, sbn.getId(), sbn.getTag(), userId, 0, 0, 12, str);
                    arrayList.remove(size);
                    this.mNotificationsByKey.remove(notificationRecord2.getKey());
                    cancelNotificationLocked(notificationRecord2, z, 12, z2, str, j);
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    void updateLightsLocked() {
        if (this.mNotificationLight == null) {
            return;
        }
        com.android.server.notification.NotificationRecord notificationRecord = null;
        while (notificationRecord == null && !this.mLights.isEmpty()) {
            java.lang.String str = this.mLights.get(this.mLights.size() - 1);
            com.android.server.notification.NotificationRecord notificationRecord2 = this.mNotificationsByKey.get(str);
            if (notificationRecord2 == null) {
                android.util.Slog.wtfStack(TAG, "LED Notification does not exist: " + str);
                this.mLights.remove(str);
            }
            notificationRecord = notificationRecord2;
        }
        if (notificationRecord == null || isInCall() || this.mScreenOn) {
            this.mNotificationLight.turnOff();
            return;
        }
        com.android.server.notification.NotificationRecord.Light light = notificationRecord.getLight();
        if (light != null && this.mNotificationPulseEnabled) {
            this.mNotificationLight.setFlashing(light.color, 1, light.onMs, light.offMs);
        }
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    java.util.List<com.android.server.notification.NotificationRecord> findCurrentAndSnoozedGroupNotificationsLocked(java.lang.String str, java.lang.String str2, int i) {
        java.util.ArrayList<com.android.server.notification.NotificationRecord> notifications = this.mSnoozeHelper.getNotifications(str, str2, java.lang.Integer.valueOf(i));
        notifications.addAll(findGroupNotificationsLocked(str, str2, i));
        return notifications;
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    java.util.List<com.android.server.notification.NotificationRecord> findGroupNotificationsLocked(java.lang.String str, java.lang.String str2, int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.addAll(findGroupNotificationByListLocked(this.mNotificationList, str, str2, i));
        arrayList.addAll(findGroupNotificationByListLocked(this.mEnqueuedNotifications, str, str2, i));
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    public com.android.server.notification.NotificationRecord findInCurrentAndSnoozedNotificationByKeyLocked(java.lang.String str) {
        com.android.server.notification.NotificationRecord findNotificationByKeyLocked = findNotificationByKeyLocked(str);
        if (findNotificationByKeyLocked == null) {
            return this.mSnoozeHelper.getNotification(str);
        }
        return findNotificationByKeyLocked;
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    private java.util.List<com.android.server.notification.NotificationRecord> findGroupNotificationByListLocked(java.util.ArrayList<com.android.server.notification.NotificationRecord> arrayList, java.lang.String str, java.lang.String str2, int i) {
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.server.notification.NotificationRecord notificationRecord = arrayList.get(i2);
            if (notificationMatchesUserId(notificationRecord, i, false) && notificationRecord.getGroupKey().equals(str2) && notificationRecord.getSbn().getPackageName().equals(str)) {
                arrayList2.add(notificationRecord);
            }
        }
        return arrayList2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    public com.android.server.notification.NotificationRecord findNotificationByKeyLocked(java.lang.String str) {
        com.android.server.notification.NotificationRecord findNotificationByListLocked = findNotificationByListLocked(this.mNotificationList, str);
        if (findNotificationByListLocked != null) {
            return findNotificationByListLocked;
        }
        com.android.server.notification.NotificationRecord findNotificationByListLocked2 = findNotificationByListLocked(this.mEnqueuedNotifications, str);
        if (findNotificationByListLocked2 != null) {
            return findNotificationByListLocked2;
        }
        return null;
    }

    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    com.android.server.notification.NotificationRecord findNotificationLocked(java.lang.String str, java.lang.String str2, int i, int i2) {
        com.android.server.notification.NotificationRecord findNotificationByListLocked = findNotificationByListLocked(this.mNotificationList, str, str2, i, i2);
        if (findNotificationByListLocked != null) {
            return findNotificationByListLocked;
        }
        com.android.server.notification.NotificationRecord findNotificationByListLocked2 = findNotificationByListLocked(this.mEnqueuedNotifications, str, str2, i, i2);
        if (findNotificationByListLocked2 != null) {
            return findNotificationByListLocked2;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.Nullable
    public static com.android.server.notification.NotificationRecord findNotificationByListLocked(java.util.ArrayList<com.android.server.notification.NotificationRecord> arrayList, java.lang.String str, java.lang.String str2, int i, int i2) {
        int size = arrayList.size();
        for (int i3 = 0; i3 < size; i3++) {
            com.android.server.notification.NotificationRecord notificationRecord = arrayList.get(i3);
            if (notificationMatchesUserId(notificationRecord, i2, (notificationRecord.getFlags() & com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_EMBED_UNDEFINED) != 0) && notificationRecord.getSbn().getId() == i && android.text.TextUtils.equals(notificationRecord.getSbn().getTag(), str2) && notificationRecord.getSbn().getPackageName().equals(str)) {
                return notificationRecord;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.util.List<com.android.server.notification.NotificationRecord> findNotificationsByListLocked(java.util.ArrayList<com.android.server.notification.NotificationRecord> arrayList, java.lang.String str, java.lang.String str2, int i, int i2) {
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        int size = arrayList.size();
        for (int i3 = 0; i3 < size; i3++) {
            com.android.server.notification.NotificationRecord notificationRecord = arrayList.get(i3);
            if (notificationMatchesUserId(notificationRecord, i2, false) && notificationRecord.getSbn().getId() == i && android.text.TextUtils.equals(notificationRecord.getSbn().getTag(), str2) && notificationRecord.getSbn().getPackageName().equals(str)) {
                arrayList2.add(notificationRecord);
            }
        }
        return arrayList2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.Nullable
    public static com.android.server.notification.NotificationRecord findNotificationByListLocked(java.util.ArrayList<com.android.server.notification.NotificationRecord> arrayList, java.lang.String str) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (str.equals(arrayList.get(i).getKey())) {
                return arrayList.get(i);
            }
        }
        return null;
    }

    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    int indexOfNotificationLocked(java.lang.String str) {
        int size = this.mNotificationList.size();
        for (int i = 0; i < size; i++) {
            if (str.equals(this.mNotificationList.get(i).getKey())) {
                return i;
            }
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideNotificationsForPackages(@android.annotation.NonNull java.lang.String[] strArr, @android.annotation.NonNull int[] iArr) {
        synchronized (this.mNotificationLock) {
            try {
                java.util.Set set = (java.util.Set) java.util.Arrays.stream(iArr).boxed().collect(java.util.stream.Collectors.toSet());
                java.util.List asList = java.util.Arrays.asList(strArr);
                java.util.ArrayList arrayList = new java.util.ArrayList();
                int size = this.mNotificationList.size();
                for (int i = 0; i < size; i++) {
                    com.android.server.notification.NotificationRecord notificationRecord = this.mNotificationList.get(i);
                    if (asList.contains(notificationRecord.getSbn().getPackageName()) && set.contains(java.lang.Integer.valueOf(notificationRecord.getUid()))) {
                        notificationRecord.setHidden(true);
                        arrayList.add(notificationRecord);
                    }
                }
                this.mListeners.notifyHiddenLocked(arrayList);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unhideNotificationsForPackages(@android.annotation.NonNull java.lang.String[] strArr, @android.annotation.NonNull int[] iArr) {
        synchronized (this.mNotificationLock) {
            try {
                java.util.Set set = (java.util.Set) java.util.Arrays.stream(iArr).boxed().collect(java.util.stream.Collectors.toSet());
                java.util.List asList = java.util.Arrays.asList(strArr);
                java.util.ArrayList arrayList = new java.util.ArrayList();
                int size = this.mNotificationList.size();
                for (int i = 0; i < size; i++) {
                    com.android.server.notification.NotificationRecord notificationRecord = this.mNotificationList.get(i);
                    if (asList.contains(notificationRecord.getSbn().getPackageName()) && set.contains(java.lang.Integer.valueOf(notificationRecord.getUid()))) {
                        notificationRecord.setHidden(false);
                        arrayList.add(notificationRecord);
                    }
                }
                this.mListeners.notifyUnhiddenLocked(arrayList);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelNotificationsWhenEnterLockDownMode(int i) {
        synchronized (this.mNotificationLock) {
            try {
                int size = this.mNotificationList.size();
                for (int i2 = 0; i2 < size; i2++) {
                    com.android.server.notification.NotificationRecord notificationRecord = this.mNotificationList.get(i2);
                    if (notificationRecord.getUser().getIdentifier() == i) {
                        this.mListeners.notifyRemovedLocked(notificationRecord, 23, notificationRecord.getStats());
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postNotificationsWhenExitLockDownMode(int i) {
        synchronized (this.mNotificationLock) {
            try {
                int size = this.mNotificationList.size();
                long j = 0;
                for (int i2 = 0; i2 < size; i2++) {
                    final com.android.server.notification.NotificationRecord notificationRecord = this.mNotificationList.get(i2);
                    if (notificationRecord.getUser().getIdentifier() == i) {
                        this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.notification.NotificationManagerService$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.notification.NotificationManagerService.this.lambda$postNotificationsWhenExitLockDownMode$11(notificationRecord);
                            }
                        }, j);
                        j += 20;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$postNotificationsWhenExitLockDownMode$11(com.android.server.notification.NotificationRecord notificationRecord) {
        synchronized (this.mNotificationLock) {
            this.mListeners.notifyPostedLocked(notificationRecord, notificationRecord);
        }
    }

    private void updateNotificationPulse() {
        synchronized (this.mNotificationLock) {
            updateLightsLocked();
        }
    }

    protected boolean isCallingUidSystem() {
        return android.os.Binder.getCallingUid() == 1000;
    }

    protected boolean isCallingAppIdSystem() {
        return android.os.UserHandle.getAppId(android.os.Binder.getCallingUid()) == 1000;
    }

    protected boolean isUidSystemOrPhone(int i) {
        int appId = android.os.UserHandle.getAppId(i);
        return appId == 1000 || appId == 1001 || i == 0;
    }

    protected boolean isCallerSystemOrPhone() {
        return isUidSystemOrPhone(android.os.Binder.getCallingUid());
    }

    @com.android.internal.annotations.VisibleForTesting
    protected boolean isCallerSystemOrSystemUi() {
        return isCallerSystemOrPhone() || getContext().checkCallingPermission("android.permission.STATUS_BAR_SERVICE") == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isCallerSystemOrSystemUiOrShell() {
        int callingUid = android.os.Binder.getCallingUid();
        if (callingUid == 2000 || callingUid == 0) {
            return true;
        }
        return isCallerSystemOrSystemUi();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkCallerIsSystemOrShell() {
        int callingUid = android.os.Binder.getCallingUid();
        if (callingUid == 2000 || callingUid == 0) {
            return;
        }
        checkCallerIsSystem();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkCallerIsSystem() {
        if (isCallerSystemOrPhone()) {
            return;
        }
        throw new java.lang.SecurityException("Disallowed call for uid " + android.os.Binder.getCallingUid());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkCallerIsSystemOrSystemUiOrShell() {
        checkCallerIsSystemOrSystemUiOrShell(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkCallerIsSystemOrSystemUiOrShell(java.lang.String str) {
        int callingUid = android.os.Binder.getCallingUid();
        if (callingUid == 2000 || callingUid == 0 || isCallerSystemOrPhone()) {
            return;
        }
        getContext().enforceCallingPermission("android.permission.STATUS_BAR_SERVICE", str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkCallerIsSystemOrSameApp(java.lang.String str) {
        if (isCallerSystemOrPhone()) {
            return;
        }
        checkCallerIsSameApp(str);
    }

    private boolean isCallerAndroid(java.lang.String str, int i) {
        return isUidSystemOrPhone(i) && str != null && com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(str);
    }

    private void checkRestrictedCategories(android.app.Notification notification) {
        try {
            if (!this.mPackageManager.hasSystemFeature("android.hardware.type.automotive", 0)) {
                return;
            }
        } catch (android.os.RemoteException e) {
            if (DBG) {
                android.util.Slog.e(TAG, "Unable to confirm if it's safe to skip category restrictions check thus the check will be done anyway");
            }
        }
        if ("car_emergency".equals(notification.category) || "car_warning".equals(notification.category) || "car_information".equals(notification.category)) {
            getContext().enforceCallingPermission("android.permission.SEND_CATEGORY_CAR_NOTIFICATIONS", java.lang.String.format("Notification category %s restricted", notification.category));
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isCallerInstantApp(int i, int i2) {
        if (isUidSystemOrPhone(i)) {
            return false;
        }
        if (i2 == -1) {
            i2 = 0;
        }
        try {
            java.lang.String[] packagesForUid = this.mPackageManager.getPackagesForUid(i);
            if (packagesForUid == null) {
                throw new java.lang.SecurityException("Unknown uid " + i);
            }
            java.lang.String str = packagesForUid[0];
            this.mAppOps.checkPackage(i, str);
            android.content.pm.ApplicationInfo applicationInfo = this.mPackageManager.getApplicationInfo(str, 0L, i2);
            if (applicationInfo == null) {
                throw new java.lang.SecurityException("Unknown package " + str);
            }
            return applicationInfo.isInstantApp();
        } catch (android.os.RemoteException e) {
            throw new java.lang.SecurityException("Unknown uid " + i, e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkCallerIsSameApp(java.lang.String str) {
        checkCallerIsSameApp(str, android.os.Binder.getCallingUid(), android.os.UserHandle.getCallingUserId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkCallerIsSameApp(java.lang.String str, int i, int i2) {
        if ((i != 0 || !ROOT_PKG.equals(str)) && !this.mPackageManagerInternal.isSameApp(str, i, i2)) {
            throw new java.lang.SecurityException("Package " + str + " is not owned by uid " + i);
        }
    }

    private boolean isCallerSameApp(java.lang.String str, int i, int i2) {
        try {
            checkCallerIsSameApp(str, i, i2);
            return true;
        } catch (java.lang.SecurityException e) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String callStateToString(int i) {
        switch (i) {
            case 0:
                return "CALL_STATE_IDLE";
            case 1:
                return "CALL_STATE_RINGING";
            case 2:
                return "CALL_STATE_OFFHOOK";
            default:
                return "CALL_STATE_UNKNOWN_" + i;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    android.service.notification.NotificationRankingUpdate makeRankingUpdateLocked(com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo) {
        java.util.ArrayList<android.app.Notification.Action> arrayList;
        java.util.ArrayList<java.lang.CharSequence> arrayList2;
        boolean z;
        int i;
        int size = this.mNotificationList.size();
        java.util.ArrayList arrayList3 = new java.util.ArrayList();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.server.notification.NotificationRecord notificationRecord = this.mNotificationList.get(i2);
            if (!isInLockDownMode(notificationRecord.getUser().getIdentifier()) && isVisibleToListener(notificationRecord.getSbn(), notificationRecord.getNotificationType(), managedServiceInfo)) {
                java.lang.String key = notificationRecord.getSbn().getKey();
                android.service.notification.NotificationListenerService.Ranking ranking = new android.service.notification.NotificationListenerService.Ranking();
                java.util.ArrayList<android.app.Notification.Action> systemGeneratedSmartActions = notificationRecord.getSystemGeneratedSmartActions();
                java.util.ArrayList<java.lang.CharSequence> smartReplies = notificationRecord.getSmartReplies();
                if (com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.redactSensitiveNotificationsFromUntrustedListeners() && managedServiceInfo != null && !this.mListeners.isUidTrusted(managedServiceInfo.uid) && this.mListeners.hasSensitiveContent(notificationRecord)) {
                    arrayList = null;
                    arrayList2 = null;
                } else {
                    arrayList = systemGeneratedSmartActions;
                    arrayList2 = smartReplies;
                }
                int size2 = arrayList3.size();
                boolean z2 = !notificationRecord.isIntercepted();
                int packageVisibilityOverride = notificationRecord.getPackageVisibilityOverride();
                int suppressedVisualEffects = notificationRecord.getSuppressedVisualEffects();
                int importance = notificationRecord.getImportance();
                java.lang.CharSequence importanceExplanation = notificationRecord.getImportanceExplanation();
                java.lang.String overrideGroupKey = notificationRecord.getSbn().getOverrideGroupKey();
                android.app.NotificationChannel channel = notificationRecord.getChannel();
                java.util.ArrayList<java.lang.String> peopleOverride = notificationRecord.getPeopleOverride();
                java.util.ArrayList<android.service.notification.SnoozeCriterion> snoozeCriteria = notificationRecord.getSnoozeCriteria();
                boolean canShowBadge = notificationRecord.canShowBadge();
                int userSentiment = notificationRecord.getUserSentiment();
                boolean isHidden = notificationRecord.isHidden();
                long lastAudiblyAlertedMs = notificationRecord.getLastAudiblyAlertedMs();
                if (notificationRecord.getSound() == null && notificationRecord.getVibration() == null) {
                    z = false;
                } else {
                    z = true;
                }
                boolean canBubble = notificationRecord.canBubble();
                boolean isTextChanged = notificationRecord.isTextChanged();
                boolean isConversation = notificationRecord.isConversation();
                android.content.pm.ShortcutInfo shortcutInfo = notificationRecord.getShortcutInfo();
                if (notificationRecord.getRankingScore() != com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                    i = notificationRecord.getRankingScore() > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE ? 1 : -1;
                } else {
                    i = 0;
                }
                ranking.populate(key, size2, z2, packageVisibilityOverride, suppressedVisualEffects, importance, importanceExplanation, overrideGroupKey, channel, peopleOverride, snoozeCriteria, canShowBadge, userSentiment, isHidden, lastAudiblyAlertedMs, z, arrayList, arrayList2, canBubble, isTextChanged, isConversation, shortcutInfo, i, notificationRecord.getNotification().isBubbleNotification(), notificationRecord.getProposedImportance(), notificationRecord.hasSensitiveContent());
                arrayList3.add(ranking);
            }
        }
        return new android.service.notification.NotificationRankingUpdate((android.service.notification.NotificationListenerService.Ranking[]) arrayList3.toArray(new android.service.notification.NotificationListenerService.Ranking[0]));
    }

    boolean isInLockDownMode(int i) {
        return this.mStrongAuthTracker.isInLockDownMode(i);
    }

    boolean hasCompanionDevice(com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo) {
        return hasCompanionDevice(managedServiceInfo.component.getPackageName(), managedServiceInfo.userid, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasCompanionDevice(java.lang.String str, int i, @android.annotation.Nullable java.util.Set<java.lang.String> set) {
        if (this.mCompanionManager == null) {
            this.mCompanionManager = getCompanionManager();
        }
        if (this.mCompanionManager == null) {
            return false;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                for (android.companion.AssociationInfo associationInfo : this.mCompanionManager.getAssociations(str, i)) {
                    if (set == null || set.contains(associationInfo.getDeviceProfile())) {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return true;
                    }
                }
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Cannot reach companion device service", e);
            } catch (java.lang.SecurityException e2) {
            } catch (java.lang.Exception e3) {
                android.util.Slog.e(TAG, "Cannot verify caller pkg=" + str + ", userId=" + i, e3);
            }
            return false;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    protected android.companion.ICompanionDeviceManager getCompanionManager() {
        return android.companion.ICompanionDeviceManager.Stub.asInterface(android.os.ServiceManager.getService("companiondevice"));
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isVisibleToListener(android.service.notification.StatusBarNotification statusBarNotification, int i, com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo) {
        if (!managedServiceInfo.enabledAndUserMatches(statusBarNotification.getUserId()) || !isInteractionVisibleToListener(managedServiceInfo, statusBarNotification.getUserId())) {
            return false;
        }
        android.service.notification.NotificationListenerFilter notificationListenerFilter = this.mListeners.getNotificationListenerFilter(managedServiceInfo.mKey);
        if (notificationListenerFilter != null) {
            return notificationListenerFilter.isTypeAllowed(i) && notificationListenerFilter.isPackageAllowed(new android.content.pm.VersionedPackage(statusBarNotification.getPackageName(), statusBarNotification.getUid()));
        }
        return true;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isInteractionVisibleToListener(com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo, int i) {
        return !isServiceTokenValid(managedServiceInfo.getService()) || managedServiceInfo.isSameUser(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isServiceTokenValid(android.os.IInterface iInterface) {
        boolean isServiceTokenValidLocked;
        synchronized (this.mNotificationLock) {
            isServiceTokenValidLocked = this.mAssistants.isServiceTokenValidLocked(iInterface);
        }
        return isServiceTokenValidLocked;
    }

    private boolean isPackageSuspendedForUser(java.lang.String str, int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                return this.mPackageManager.isPackageSuspendedForUser(str, android.os.UserHandle.getUserId(i));
            } catch (android.os.RemoteException e) {
                throw new java.lang.SecurityException("Could not talk to package manager service");
            } catch (java.lang.IllegalArgumentException e2) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean canUseManagedServices(java.lang.String str, java.lang.Integer num, java.lang.String str2) {
        if (str2 == null) {
            return true;
        }
        try {
            if (this.mPackageManager.checkPermission(str2, str, num.intValue()) == 0) {
                return true;
            }
            return false;
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "can't talk to pm", e);
            return true;
        }
    }

    private class TrimCache {
        android.service.notification.StatusBarNotification heavy;
        android.service.notification.StatusBarNotification sbnClone;
        android.service.notification.StatusBarNotification sbnCloneLight;

        TrimCache(android.service.notification.StatusBarNotification statusBarNotification) {
            this.heavy = statusBarNotification;
        }

        android.service.notification.StatusBarNotification ForListener(com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo) {
            if (com.android.server.notification.NotificationManagerService.this.mListeners.getOnNotificationPostedTrim(managedServiceInfo) == 1) {
                if (this.sbnCloneLight == null) {
                    this.sbnCloneLight = this.heavy.cloneLight();
                }
                return this.sbnCloneLight;
            }
            if (this.sbnClone == null) {
                this.sbnClone = this.heavy.clone();
            }
            return this.sbnClone;
        }
    }

    private boolean isInCall() {
        int mode;
        return this.mInCallStateOffHook || (mode = this.mAudioManager.getMode()) == 2 || mode == 3;
    }

    public class NotificationAssistants extends com.android.server.notification.ManagedServices {
        private static final java.lang.String ATT_TYPES = "types";
        static final java.lang.String TAG_ENABLED_NOTIFICATION_ASSISTANTS = "enabled_assistants";

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private java.util.Set<java.lang.String> mAllowedAdjustments;
        protected android.content.ComponentName mDefaultFromConfig;
        private final java.lang.Object mLock;

        @Override // com.android.server.notification.ManagedServices
        protected void loadDefaultsFromConfig() {
            loadDefaultsFromConfig(true);
        }

        protected void loadDefaultsFromConfig(boolean z) {
            android.util.ArraySet arraySet = new android.util.ArraySet();
            arraySet.addAll(java.util.Arrays.asList(this.mContext.getResources().getString(android.R.string.config_dataUsageSummaryComponent).split(":")));
            for (int i = 0; i < arraySet.size(); i++) {
                android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString((java.lang.String) arraySet.valueAt(i));
                java.lang.String str = (java.lang.String) arraySet.valueAt(i);
                if (unflattenFromString != null) {
                    str = unflattenFromString.getPackageName();
                }
                if (!android.text.TextUtils.isEmpty(str) && queryPackageForServices(str, 786432, 0).contains(unflattenFromString)) {
                    if (z) {
                        addDefaultComponentOrPackage(unflattenFromString.flattenToString());
                    } else {
                        this.mDefaultFromConfig = unflattenFromString;
                    }
                }
            }
        }

        android.content.ComponentName getDefaultFromConfig() {
            if (this.mDefaultFromConfig == null) {
                loadDefaultsFromConfig(false);
            }
            return this.mDefaultFromConfig;
        }

        @Override // com.android.server.notification.ManagedServices
        protected void upgradeUserSet() {
            java.util.Iterator<java.lang.Integer> it = this.mApproved.keySet().iterator();
            while (it.hasNext()) {
                int intValue = it.next().intValue();
                android.util.ArraySet<java.lang.String> arraySet = this.mUserSetServices.get(java.lang.Integer.valueOf(intValue));
                this.mIsUserChanged.put(java.lang.Integer.valueOf(intValue), java.lang.Boolean.valueOf(arraySet != null && arraySet.size() > 0));
            }
        }

        @Override // com.android.server.notification.ManagedServices
        protected void addApprovedList(java.lang.String str, int i, boolean z, java.lang.String str2) {
            if (!android.text.TextUtils.isEmpty(str)) {
                java.lang.String[] split = str.split(":");
                if (split.length > 1) {
                    android.util.Slog.d(this.TAG, "More than one approved assistants");
                    str = split[0];
                }
            }
            super.addApprovedList(str, i, z, str2);
        }

        public NotificationAssistants(android.content.Context context, java.lang.Object obj, com.android.server.notification.ManagedServices.UserProfiles userProfiles, android.content.pm.IPackageManager iPackageManager) {
            super(context, obj, userProfiles, iPackageManager);
            this.mLock = new java.lang.Object();
            this.mAllowedAdjustments = new android.util.ArraySet();
            this.mDefaultFromConfig = null;
            for (int i = 0; i < com.android.server.notification.NotificationManagerService.ALLOWED_ADJUSTMENTS.length; i++) {
                this.mAllowedAdjustments.add(com.android.server.notification.NotificationManagerService.ALLOWED_ADJUSTMENTS[i]);
            }
        }

        @Override // com.android.server.notification.ManagedServices
        protected com.android.server.notification.ManagedServices.Config getConfig() {
            com.android.server.notification.ManagedServices.Config config = new com.android.server.notification.ManagedServices.Config();
            config.caption = "notification assistant";
            config.serviceInterface = "android.service.notification.NotificationAssistantService";
            config.xmlTag = TAG_ENABLED_NOTIFICATION_ASSISTANTS;
            config.secureSettingName = "enabled_notification_assistant";
            config.bindPermission = "android.permission.BIND_NOTIFICATION_ASSISTANT_SERVICE";
            config.settingsAction = "android.settings.MANAGE_DEFAULT_APPS_SETTINGS";
            config.clientLabel = android.R.string.notification_channel_physical_keyboard;
            return config;
        }

        @Override // com.android.server.notification.ManagedServices
        protected android.os.IInterface asInterface(android.os.IBinder iBinder) {
            return android.service.notification.INotificationListener.Stub.asInterface(iBinder);
        }

        @Override // com.android.server.notification.ManagedServices
        protected boolean checkType(android.os.IInterface iInterface) {
            return iInterface instanceof android.service.notification.INotificationListener;
        }

        @Override // com.android.server.notification.ManagedServices
        protected void onServiceAdded(com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo) {
            com.android.server.notification.NotificationManagerService.this.mListeners.registerGuestService(managedServiceInfo);
        }

        @Override // com.android.server.notification.ManagedServices
        protected void ensureFilters(android.content.pm.ServiceInfo serviceInfo, int i) {
        }

        @Override // com.android.server.notification.ManagedServices
        @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
        protected void onServiceRemovedLocked(com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo) {
            com.android.server.notification.NotificationManagerService.this.mListeners.unregisterService(managedServiceInfo.service, managedServiceInfo.userid);
        }

        @Override // com.android.server.notification.ManagedServices
        public void onUserUnlocked(int i) {
            if (this.DEBUG) {
                android.util.Slog.d(this.TAG, "onUserUnlocked u=" + i);
            }
            rebindServices(true, i);
        }

        @Override // com.android.server.notification.ManagedServices
        protected boolean allowRebindForParentUser() {
            return false;
        }

        @Override // com.android.server.notification.ManagedServices
        protected java.lang.String getRequiredPermission() {
            return "android.permission.REQUEST_NOTIFICATION_ASSISTANT_SERVICE";
        }

        protected java.util.List<java.lang.String> getAllowedAssistantAdjustments() {
            java.util.ArrayList arrayList;
            synchronized (this.mLock) {
                arrayList = new java.util.ArrayList();
                arrayList.addAll(this.mAllowedAdjustments);
            }
            return arrayList;
        }

        protected boolean isAdjustmentAllowed(java.lang.String str) {
            boolean contains;
            synchronized (this.mLock) {
                contains = this.mAllowedAdjustments.contains(str);
            }
            return contains;
        }

        protected void onNotificationsSeenLocked(java.util.ArrayList<com.android.server.notification.NotificationRecord> arrayList) {
            for (final com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo : getServices()) {
                final java.util.ArrayList arrayList2 = new java.util.ArrayList(arrayList.size());
                java.util.Iterator<com.android.server.notification.NotificationRecord> it = arrayList.iterator();
                while (it.hasNext()) {
                    com.android.server.notification.NotificationRecord next = it.next();
                    if (com.android.server.notification.NotificationManagerService.this.isVisibleToListener(next.getSbn(), next.getNotificationType(), managedServiceInfo) && managedServiceInfo.isSameUser(next.getUserId())) {
                        arrayList2.add(next.getKey());
                    }
                }
                if (!arrayList2.isEmpty()) {
                    com.android.server.notification.NotificationManagerService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.notification.NotificationManagerService$NotificationAssistants$$ExternalSyntheticLambda9
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.notification.NotificationManagerService.NotificationAssistants.this.lambda$onNotificationsSeenLocked$0(managedServiceInfo, arrayList2);
                        }
                    });
                }
            }
        }

        protected void onPanelRevealed(final int i) {
            for (final com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo : getServices()) {
                com.android.server.notification.NotificationManagerService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.notification.NotificationManagerService$NotificationAssistants$$ExternalSyntheticLambda10
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.notification.NotificationManagerService.NotificationAssistants.this.lambda$onPanelRevealed$1(managedServiceInfo, i);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPanelRevealed$1(com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo, int i) {
            try {
                managedServiceInfo.service.onPanelRevealed(i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(this.TAG, "unable to notify assistant (panel revealed): " + managedServiceInfo, e);
            }
        }

        protected void onPanelHidden() {
            for (final com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo : getServices()) {
                com.android.server.notification.NotificationManagerService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.notification.NotificationManagerService$NotificationAssistants$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.notification.NotificationManagerService.NotificationAssistants.this.lambda$onPanelHidden$2(managedServiceInfo);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPanelHidden$2(com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo) {
            try {
                managedServiceInfo.service.onPanelHidden();
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(this.TAG, "unable to notify assistant (panel hidden): " + managedServiceInfo, e);
            }
        }

        boolean hasUserSet(int i) {
            java.lang.Boolean bool = this.mIsUserChanged.get(java.lang.Integer.valueOf(i));
            return bool != null && bool.booleanValue();
        }

        void setUserSet(int i, boolean z) {
            this.mIsUserChanged.put(java.lang.Integer.valueOf(i), java.lang.Boolean.valueOf(z));
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: notifySeen, reason: merged with bridge method [inline-methods] */
        public void lambda$onNotificationsSeenLocked$0(com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo, java.util.ArrayList<java.lang.String> arrayList) {
            try {
                managedServiceInfo.service.onNotificationsSeen(arrayList);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(this.TAG, "unable to notify assistant (seen): " + managedServiceInfo, e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
        public void onNotificationEnqueuedLocked(com.android.server.notification.NotificationRecord notificationRecord) {
            boolean isVerboseLogEnabled = isVerboseLogEnabled();
            if (isVerboseLogEnabled) {
                android.util.Slog.v(this.TAG, "onNotificationEnqueuedLocked() called with: r = [" + notificationRecord + "]");
            }
            android.service.notification.StatusBarNotification sbn = notificationRecord.getSbn();
            for (com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo : getServices()) {
                if (com.android.server.notification.NotificationManagerService.this.isVisibleToListener(sbn, notificationRecord.getNotificationType(), managedServiceInfo) && managedServiceInfo.isSameUser(notificationRecord.getUserId())) {
                    com.android.server.notification.NotificationManagerService.TrimCache trimCache = com.android.server.notification.NotificationManagerService.this.new TrimCache(sbn);
                    android.service.notification.INotificationListener iNotificationListener = managedServiceInfo.service;
                    com.android.server.notification.NotificationManagerService.StatusBarNotificationHolder statusBarNotificationHolder = new com.android.server.notification.NotificationManagerService.StatusBarNotificationHolder(trimCache.ForListener(managedServiceInfo));
                    if (isVerboseLogEnabled) {
                        try {
                            android.util.Slog.v(this.TAG, "calling onNotificationEnqueuedWithChannel " + statusBarNotificationHolder);
                        } catch (android.os.RemoteException e) {
                            android.util.Slog.e(this.TAG, "unable to notify assistant (enqueued): " + iNotificationListener, e);
                        }
                    }
                    iNotificationListener.onNotificationEnqueuedWithChannel(statusBarNotificationHolder, notificationRecord.getChannel(), com.android.server.notification.NotificationManagerService.this.makeRankingUpdateLocked(managedServiceInfo));
                }
            }
        }

        @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
        void notifyAssistantVisibilityChangedLocked(com.android.server.notification.NotificationRecord notificationRecord, final boolean z) {
            final java.lang.String key = notificationRecord.getSbn().getKey();
            if (com.android.server.notification.NotificationManagerService.DBG) {
                android.util.Slog.d(this.TAG, "notifyAssistantVisibilityChangedLocked: " + key);
            }
            notifyAssistantLocked(notificationRecord.getSbn(), notificationRecord.getNotificationType(), true, new java.util.function.BiConsumer() { // from class: com.android.server.notification.NotificationManagerService$NotificationAssistants$$ExternalSyntheticLambda5
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    com.android.server.notification.NotificationManagerService.NotificationAssistants.this.lambda$notifyAssistantVisibilityChangedLocked$3(key, z, (android.service.notification.INotificationListener) obj, (com.android.server.notification.NotificationManagerService.StatusBarNotificationHolder) obj2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyAssistantVisibilityChangedLocked$3(java.lang.String str, boolean z, android.service.notification.INotificationListener iNotificationListener, com.android.server.notification.NotificationManagerService.StatusBarNotificationHolder statusBarNotificationHolder) {
            try {
                iNotificationListener.onNotificationVisibilityChanged(str, z);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(this.TAG, "unable to notify assistant (visible): " + iNotificationListener, e);
            }
        }

        @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
        void notifyAssistantExpansionChangedLocked(android.service.notification.StatusBarNotification statusBarNotification, int i, final boolean z, final boolean z2) {
            final java.lang.String key = statusBarNotification.getKey();
            notifyAssistantLocked(statusBarNotification, i, true, new java.util.function.BiConsumer() { // from class: com.android.server.notification.NotificationManagerService$NotificationAssistants$$ExternalSyntheticLambda3
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    com.android.server.notification.NotificationManagerService.NotificationAssistants.this.lambda$notifyAssistantExpansionChangedLocked$4(key, z, z2, (android.service.notification.INotificationListener) obj, (com.android.server.notification.NotificationManagerService.StatusBarNotificationHolder) obj2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyAssistantExpansionChangedLocked$4(java.lang.String str, boolean z, boolean z2, android.service.notification.INotificationListener iNotificationListener, com.android.server.notification.NotificationManagerService.StatusBarNotificationHolder statusBarNotificationHolder) {
            try {
                iNotificationListener.onNotificationExpansionChanged(str, z, z2);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(this.TAG, "unable to notify assistant (expanded): " + iNotificationListener, e);
            }
        }

        @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
        void notifyAssistantNotificationDirectReplyLocked(com.android.server.notification.NotificationRecord notificationRecord) {
            final java.lang.String key = notificationRecord.getKey();
            notifyAssistantLocked(notificationRecord.getSbn(), notificationRecord.getNotificationType(), true, new java.util.function.BiConsumer() { // from class: com.android.server.notification.NotificationManagerService$NotificationAssistants$$ExternalSyntheticLambda7
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    com.android.server.notification.NotificationManagerService.NotificationAssistants.this.lambda$notifyAssistantNotificationDirectReplyLocked$5(key, (android.service.notification.INotificationListener) obj, (com.android.server.notification.NotificationManagerService.StatusBarNotificationHolder) obj2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyAssistantNotificationDirectReplyLocked$5(java.lang.String str, android.service.notification.INotificationListener iNotificationListener, com.android.server.notification.NotificationManagerService.StatusBarNotificationHolder statusBarNotificationHolder) {
            try {
                iNotificationListener.onNotificationDirectReply(str);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(this.TAG, "unable to notify assistant (expanded): " + iNotificationListener, e);
            }
        }

        @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
        void notifyAssistantSuggestedReplySent(android.service.notification.StatusBarNotification statusBarNotification, int i, final java.lang.CharSequence charSequence, final boolean z) {
            final java.lang.String key = statusBarNotification.getKey();
            notifyAssistantLocked(statusBarNotification, i, true, new java.util.function.BiConsumer() { // from class: com.android.server.notification.NotificationManagerService$NotificationAssistants$$ExternalSyntheticLambda2
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    com.android.server.notification.NotificationManagerService.NotificationAssistants.this.lambda$notifyAssistantSuggestedReplySent$6(key, charSequence, z, (android.service.notification.INotificationListener) obj, (com.android.server.notification.NotificationManagerService.StatusBarNotificationHolder) obj2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyAssistantSuggestedReplySent$6(java.lang.String str, java.lang.CharSequence charSequence, boolean z, android.service.notification.INotificationListener iNotificationListener, com.android.server.notification.NotificationManagerService.StatusBarNotificationHolder statusBarNotificationHolder) {
            int i;
            if (z) {
                i = 1;
            } else {
                i = 0;
            }
            try {
                iNotificationListener.onSuggestedReplySent(str, charSequence, i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(this.TAG, "unable to notify assistant (snoozed): " + iNotificationListener, e);
            }
        }

        @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
        void notifyAssistantActionClicked(com.android.server.notification.NotificationRecord notificationRecord, final android.app.Notification.Action action, final boolean z) {
            final java.lang.String key = notificationRecord.getSbn().getKey();
            notifyAssistantLocked(notificationRecord.getSbn(), notificationRecord.getNotificationType(), true, new java.util.function.BiConsumer() { // from class: com.android.server.notification.NotificationManagerService$NotificationAssistants$$ExternalSyntheticLambda6
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    com.android.server.notification.NotificationManagerService.NotificationAssistants.this.lambda$notifyAssistantActionClicked$7(key, action, z, (android.service.notification.INotificationListener) obj, (com.android.server.notification.NotificationManagerService.StatusBarNotificationHolder) obj2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyAssistantActionClicked$7(java.lang.String str, android.app.Notification.Action action, boolean z, android.service.notification.INotificationListener iNotificationListener, com.android.server.notification.NotificationManagerService.StatusBarNotificationHolder statusBarNotificationHolder) {
            int i;
            if (z) {
                i = 1;
            } else {
                i = 0;
            }
            try {
                iNotificationListener.onActionClicked(str, action, i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(this.TAG, "unable to notify assistant (snoozed): " + iNotificationListener, e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
        public void notifyAssistantSnoozedLocked(com.android.server.notification.NotificationRecord notificationRecord, final java.lang.String str) {
            notifyAssistantLocked(notificationRecord.getSbn(), notificationRecord.getNotificationType(), true, new java.util.function.BiConsumer() { // from class: com.android.server.notification.NotificationManagerService$NotificationAssistants$$ExternalSyntheticLambda8
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    com.android.server.notification.NotificationManagerService.NotificationAssistants.this.lambda$notifyAssistantSnoozedLocked$8(str, (android.service.notification.INotificationListener) obj, (com.android.server.notification.NotificationManagerService.StatusBarNotificationHolder) obj2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyAssistantSnoozedLocked$8(java.lang.String str, android.service.notification.INotificationListener iNotificationListener, com.android.server.notification.NotificationManagerService.StatusBarNotificationHolder statusBarNotificationHolder) {
            try {
                iNotificationListener.onNotificationSnoozedUntilContext(statusBarNotificationHolder, str);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(this.TAG, "unable to notify assistant (snoozed): " + iNotificationListener, e);
            }
        }

        @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
        void notifyAssistantNotificationClicked(com.android.server.notification.NotificationRecord notificationRecord) {
            final java.lang.String key = notificationRecord.getSbn().getKey();
            notifyAssistantLocked(notificationRecord.getSbn(), notificationRecord.getNotificationType(), true, new java.util.function.BiConsumer() { // from class: com.android.server.notification.NotificationManagerService$NotificationAssistants$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    com.android.server.notification.NotificationManagerService.NotificationAssistants.this.lambda$notifyAssistantNotificationClicked$9(key, (android.service.notification.INotificationListener) obj, (com.android.server.notification.NotificationManagerService.StatusBarNotificationHolder) obj2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyAssistantNotificationClicked$9(java.lang.String str, android.service.notification.INotificationListener iNotificationListener, com.android.server.notification.NotificationManagerService.StatusBarNotificationHolder statusBarNotificationHolder) {
            try {
                iNotificationListener.onNotificationClicked(str);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(this.TAG, "unable to notify assistant (clicked): " + iNotificationListener, e);
            }
        }

        @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
        void notifyAssistantFeedbackReceived(com.android.server.notification.NotificationRecord notificationRecord, android.os.Bundle bundle) {
            android.service.notification.StatusBarNotification sbn = notificationRecord.getSbn();
            for (com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo : getServices()) {
                if (com.android.server.notification.NotificationManagerService.this.isVisibleToListener(sbn, notificationRecord.getNotificationType(), managedServiceInfo) && managedServiceInfo.isSameUser(notificationRecord.getUserId())) {
                    android.service.notification.INotificationListener iNotificationListener = managedServiceInfo.service;
                    try {
                        iNotificationListener.onNotificationFeedbackReceived(sbn.getKey(), com.android.server.notification.NotificationManagerService.this.makeRankingUpdateLocked(managedServiceInfo), bundle);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(this.TAG, "unable to notify assistant (feedback): " + iNotificationListener, e);
                    }
                }
            }
        }

        @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
        private void notifyAssistantLocked(android.service.notification.StatusBarNotification statusBarNotification, int i, boolean z, final java.util.function.BiConsumer<android.service.notification.INotificationListener, com.android.server.notification.NotificationManagerService.StatusBarNotificationHolder> biConsumer) {
            com.android.server.notification.NotificationManagerService.TrimCache trimCache = com.android.server.notification.NotificationManagerService.this.new TrimCache(statusBarNotification);
            boolean isVerboseLogEnabled = isVerboseLogEnabled();
            if (isVerboseLogEnabled) {
                android.util.Slog.v(this.TAG, "notifyAssistantLocked() called with: sbn = [" + statusBarNotification + "], sameUserOnly = [" + z + "], callback = [" + biConsumer + "]");
            }
            for (com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo : getServices()) {
                boolean z2 = com.android.server.notification.NotificationManagerService.this.isVisibleToListener(statusBarNotification, i, managedServiceInfo) && (!z || managedServiceInfo.isSameUser(statusBarNotification.getUserId()));
                if (isVerboseLogEnabled) {
                    android.util.Slog.v(this.TAG, "notifyAssistantLocked info=" + managedServiceInfo + " snbVisible=" + z2);
                }
                if (z2) {
                    final android.service.notification.INotificationListener iNotificationListener = managedServiceInfo.service;
                    final com.android.server.notification.NotificationManagerService.StatusBarNotificationHolder statusBarNotificationHolder = new com.android.server.notification.NotificationManagerService.StatusBarNotificationHolder(trimCache.ForListener(managedServiceInfo));
                    com.android.server.notification.NotificationManagerService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.notification.NotificationManagerService$NotificationAssistants$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            biConsumer.accept(iNotificationListener, statusBarNotificationHolder);
                        }
                    });
                }
            }
        }

        public boolean isEnabled() {
            return !getServices().isEmpty();
        }

        protected void resetDefaultAssistantsIfNecessary() {
            java.util.Iterator it = this.mUm.getAliveUsers().iterator();
            while (it.hasNext()) {
                int identifier = ((android.content.pm.UserInfo) it.next()).getUserHandle().getIdentifier();
                if (!hasUserSet(identifier)) {
                    if (!com.android.server.notification.NotificationManagerService.this.isNASMigrationDone(identifier)) {
                        resetDefaultFromConfig();
                        com.android.server.notification.NotificationManagerService.this.setNASMigrationDone(identifier);
                    }
                    android.util.Slog.d(this.TAG, "Approving default notification assistant for user " + identifier);
                    com.android.server.notification.NotificationManagerService.this.setDefaultAssistantForUser(identifier);
                }
            }
        }

        protected void resetDefaultFromConfig() {
            clearDefaults();
            loadDefaultsFromConfig();
        }

        protected void clearDefaults() {
            this.mDefaultComponents.clear();
            this.mDefaultPackages.clear();
        }

        @Override // com.android.server.notification.ManagedServices
        protected void setPackageOrComponentEnabled(java.lang.String str, int i, boolean z, boolean z2, boolean z3) {
            if (z2) {
                java.util.List<android.content.ComponentName> allowedComponents = getAllowedComponents(i);
                if (!allowedComponents.isEmpty()) {
                    android.content.ComponentName componentName = (android.content.ComponentName) com.android.internal.util.CollectionUtils.firstOrNull(allowedComponents);
                    if (componentName.flattenToString().equals(str)) {
                        return;
                    } else {
                        com.android.server.notification.NotificationManagerService.this.setNotificationAssistantAccessGrantedForUserInternal(componentName, i, false, z3);
                    }
                }
            }
            super.setPackageOrComponentEnabled(str, i, z, z2, z3);
        }

        private boolean isVerboseLogEnabled() {
            return android.util.Log.isLoggable("notification_assistant", 2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    public void notifyListenersPostedAndLogLocked(com.android.server.notification.NotificationRecord notificationRecord, com.android.server.notification.NotificationRecord notificationRecord2, @android.annotation.NonNull final com.android.server.notification.NotificationManagerService.PostNotificationTracker postNotificationTracker, @android.annotation.Nullable final com.android.server.notification.NotificationRecordLogger.NotificationReported notificationReported) {
        final java.util.List<java.lang.Runnable> prepareNotifyPostedLocked = this.mListeners.prepareNotifyPostedLocked(notificationRecord, notificationRecord2, true);
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.notification.NotificationManagerService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.notification.NotificationManagerService.this.lambda$notifyListenersPostedAndLogLocked$12(prepareNotifyPostedLocked, postNotificationTracker, notificationReported);
            }
        });
        if (com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.callstyleCallbackApi()) {
            notifyCallNotificationEventListenerOnPosted(notificationRecord);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyListenersPostedAndLogLocked$12(java.util.List list, com.android.server.notification.NotificationManagerService.PostNotificationTracker postNotificationTracker, com.android.server.notification.NotificationRecordLogger.NotificationReported notificationReported) {
        java.util.Iterator it = list.iterator();
        while (it.hasNext()) {
            ((java.lang.Runnable) it.next()).run();
        }
        long finish = postNotificationTracker.finish();
        if (notificationReported != null) {
            notificationReported.post_duration_millis = finish;
            this.mNotificationRecordLogger.logNotificationPosted(notificationReported);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    public void notifySystemUiListenerLifetimeExtendedListLocked(java.util.List<com.android.server.notification.NotificationRecord> list, int i) {
        for (int size = list.size() - 1; size >= 0; size--) {
            com.android.server.notification.NotificationRecord notificationRecord = list.get(size);
            maybeNotifySystemUiListenerLifetimeExtendedLocked(notificationRecord, notificationRecord.getSbn().getPackageName(), i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    public void maybeNotifySystemUiListenerLifetimeExtendedLocked(com.android.server.notification.NotificationRecord notificationRecord, java.lang.String str, int i) {
        if (notificationRecord != null && (notificationRecord.getSbn().getNotification().flags & 65536) > 0) {
            this.mHandler.post(new com.android.server.notification.NotificationManagerService.EnqueueNotificationRunnable(notificationRecord.getUser().getIdentifier(), notificationRecord, str != null && i == 100, this.mPostNotificationTrackerFactory.newTracker(null)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getPackageImportanceWithIdentity(java.lang.String str) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mActivityManager.getPackageImportance(str);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public class NotificationListeners extends com.android.server.notification.ManagedServices {
        static final java.lang.String ATT_COMPONENT = "component";
        static final java.lang.String ATT_PKG = "pkg";
        static final java.lang.String ATT_TYPES = "types";
        static final java.lang.String ATT_UID = "uid";
        static final java.lang.String FLAG_SEPARATOR = "\\|";
        static final java.lang.String TAG_APPROVED = "allowed";
        static final java.lang.String TAG_DISALLOWED = "disallowed";
        static final java.lang.String TAG_ENABLED_NOTIFICATION_LISTENERS = "enabled_listeners";
        static final java.lang.String TAG_REQUESTED_LISTENER = "listener";
        static final java.lang.String TAG_REQUESTED_LISTENERS = "request_listeners";
        static final java.lang.String XML_SEPARATOR = ",";
        private final boolean mIsHeadlessSystemUserMode;
        private final android.util.ArraySet<com.android.server.notification.ManagedServices.ManagedServiceInfo> mLightTrimListeners;

        @com.android.internal.annotations.GuardedBy({"mRequestedNotificationListeners"})
        private final android.util.ArrayMap<android.util.Pair<android.content.ComponentName, java.lang.Integer>, android.service.notification.NotificationListenerFilter> mRequestedNotificationListeners;

        @com.android.internal.annotations.GuardedBy({"mTrustedListenerUids"})
        private final android.util.ArraySet<java.lang.Integer> mTrustedListenerUids;

        public NotificationListeners(com.android.server.notification.NotificationManagerService notificationManagerService, android.content.Context context, java.lang.Object obj, com.android.server.notification.ManagedServices.UserProfiles userProfiles, android.content.pm.IPackageManager iPackageManager) {
            this(context, obj, userProfiles, iPackageManager, android.os.UserManager.isHeadlessSystemUserMode());
        }

        @com.android.internal.annotations.VisibleForTesting
        public NotificationListeners(android.content.Context context, java.lang.Object obj, com.android.server.notification.ManagedServices.UserProfiles userProfiles, android.content.pm.IPackageManager iPackageManager, boolean z) {
            super(context, obj, userProfiles, iPackageManager);
            this.mLightTrimListeners = new android.util.ArraySet<>();
            this.mTrustedListenerUids = new android.util.ArraySet<>();
            this.mRequestedNotificationListeners = new android.util.ArrayMap<>();
            this.mIsHeadlessSystemUserMode = z;
        }

        @Override // com.android.server.notification.ManagedServices
        protected void setPackageOrComponentEnabled(java.lang.String str, int i, boolean z, boolean z2, boolean z3) {
            super.setPackageOrComponentEnabled(str, i, z, z2, z3);
            java.lang.String packageName = getPackageName(str);
            if (com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.redactSensitiveNotificationsFromUntrustedListeners()) {
                int packageUid = com.android.server.notification.NotificationManagerService.this.mPackageManagerInternal.getPackageUid(packageName, 0L, i);
                if (!z2 && packageUid >= 0) {
                    synchronized (this.mTrustedListenerUids) {
                        this.mTrustedListenerUids.remove(java.lang.Integer.valueOf(packageUid));
                    }
                }
                if (z2 && packageUid >= 0 && isAppTrustedNotificationListenerService(packageUid, packageName)) {
                    synchronized (this.mTrustedListenerUids) {
                        this.mTrustedListenerUids.add(java.lang.Integer.valueOf(packageUid));
                    }
                }
            }
            this.mContext.sendBroadcastAsUser(new android.content.Intent("android.app.action.NOTIFICATION_LISTENER_ENABLED_CHANGED").addFlags(1073741824), android.os.UserHandle.of(i), null);
        }

        @Override // com.android.server.notification.ManagedServices
        protected void loadDefaultsFromConfig() {
            int i;
            java.lang.String string = this.mContext.getResources().getString(android.R.string.config_defaultCredentialManagerAutofillService);
            if (string != null) {
                java.lang.String[] split = string.split(":");
                for (int i2 = 0; i2 < split.length; i2++) {
                    if (!android.text.TextUtils.isEmpty(split[i2])) {
                        if (!this.mIsHeadlessSystemUserMode) {
                            i = 786432;
                        } else {
                            i = 4980736;
                        }
                        android.util.ArraySet<android.content.ComponentName> queryPackageForServices = queryPackageForServices(split[i2], i, 0);
                        for (int i3 = 0; i3 < queryPackageForServices.size(); i3++) {
                            addDefaultComponentOrPackage(queryPackageForServices.valueAt(i3).flattenToString());
                        }
                    }
                }
            }
        }

        @Override // com.android.server.notification.ManagedServices
        protected int getBindFlags() {
            return 83886337;
        }

        @Override // com.android.server.notification.ManagedServices
        protected com.android.server.notification.ManagedServices.Config getConfig() {
            com.android.server.notification.ManagedServices.Config config = new com.android.server.notification.ManagedServices.Config();
            config.caption = "notification listener";
            config.serviceInterface = "android.service.notification.NotificationListenerService";
            config.xmlTag = TAG_ENABLED_NOTIFICATION_LISTENERS;
            config.secureSettingName = "enabled_notification_listeners";
            config.bindPermission = "android.permission.BIND_NOTIFICATION_LISTENER_SERVICE";
            config.settingsAction = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";
            config.clientLabel = android.R.string.notification_channel_network_alerts;
            return config;
        }

        @Override // com.android.server.notification.ManagedServices
        protected android.os.IInterface asInterface(android.os.IBinder iBinder) {
            return android.service.notification.INotificationListener.Stub.asInterface(iBinder);
        }

        @Override // com.android.server.notification.ManagedServices
        protected boolean checkType(android.os.IInterface iInterface) {
            return iInterface instanceof android.service.notification.INotificationListener;
        }

        @Override // com.android.server.notification.ManagedServices
        public void onServiceAdded(com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo) {
            android.service.notification.NotificationRankingUpdate makeRankingUpdateLocked;
            if (android.app.Flags.lifetimeExtensionRefactor()) {
                managedServiceInfo.isSystemUi = !com.android.server.notification.NotificationManagerService.this.isCallerSystemOrPhone();
            }
            android.service.notification.INotificationListener iNotificationListener = managedServiceInfo.service;
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                makeRankingUpdateLocked = com.android.server.notification.NotificationManagerService.this.makeRankingUpdateLocked(managedServiceInfo);
                updateUriPermissionsForActiveNotificationsLocked(managedServiceInfo, true);
            }
            if (com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.redactSensitiveNotificationsFromUntrustedListeners() && isAppTrustedNotificationListenerService(managedServiceInfo.uid, managedServiceInfo.component.getPackageName())) {
                synchronized (this.mTrustedListenerUids) {
                    this.mTrustedListenerUids.add(java.lang.Integer.valueOf(managedServiceInfo.uid));
                }
            }
            try {
                iNotificationListener.onListenerConnected(makeRankingUpdateLocked);
            } catch (android.os.RemoteException e) {
            }
        }

        @Override // com.android.server.notification.ManagedServices
        @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
        protected void onServiceRemovedLocked(com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo) {
            updateUriPermissionsForActiveNotificationsLocked(managedServiceInfo, false);
            if (com.android.server.notification.NotificationManagerService.this.removeDisabledHints(managedServiceInfo)) {
                com.android.server.notification.NotificationManagerService.this.updateListenerHintsLocked();
                com.android.server.notification.NotificationManagerService.this.updateEffectsSuppressorLocked();
            }
            if (com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.redactSensitiveNotificationsFromUntrustedListeners()) {
                synchronized (this.mTrustedListenerUids) {
                    this.mTrustedListenerUids.remove(java.lang.Integer.valueOf(managedServiceInfo.uid));
                }
            }
            this.mLightTrimListeners.remove(managedServiceInfo);
        }

        @Override // com.android.server.notification.ManagedServices
        public void onUserRemoved(int i) {
            super.onUserRemoved(i);
            synchronized (this.mRequestedNotificationListeners) {
                try {
                    for (int size = this.mRequestedNotificationListeners.size() - 1; size >= 0; size--) {
                        if (((java.lang.Integer) this.mRequestedNotificationListeners.keyAt(size).second).intValue() == i) {
                            this.mRequestedNotificationListeners.removeAt(size);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.notification.ManagedServices
        protected boolean allowRebindForParentUser() {
            return true;
        }

        @Override // com.android.server.notification.ManagedServices
        public void onPackagesChanged(boolean z, java.lang.String[] strArr, int[] iArr) {
            super.onPackagesChanged(z, strArr, iArr);
            synchronized (this.mRequestedNotificationListeners) {
                if (z) {
                    for (int i = 0; i < strArr.length; i++) {
                        try {
                            java.lang.String str = strArr[i];
                            int userId = android.os.UserHandle.getUserId(iArr[i]);
                            for (int size = this.mRequestedNotificationListeners.size() - 1; size >= 0; size--) {
                                android.util.Pair<android.content.ComponentName, java.lang.Integer> keyAt = this.mRequestedNotificationListeners.keyAt(size);
                                if (((java.lang.Integer) keyAt.second).intValue() == userId && ((android.content.ComponentName) keyAt.first).getPackageName().equals(str)) {
                                    this.mRequestedNotificationListeners.removeAt(size);
                                }
                            }
                        } catch (java.lang.Throwable th) {
                            throw th;
                        }
                    }
                    for (int i2 = 0; i2 < strArr.length; i2++) {
                        java.lang.String str2 = strArr[i2];
                        for (int size2 = this.mRequestedNotificationListeners.size() - 1; size2 >= 0; size2--) {
                            this.mRequestedNotificationListeners.valueAt(size2).removePackage(new android.content.pm.VersionedPackage(str2, iArr[i2]));
                        }
                    }
                }
            }
        }

        @Override // com.android.server.notification.ManagedServices
        protected java.lang.String getRequiredPermission() {
            return null;
        }

        @Override // com.android.server.notification.ManagedServices
        protected boolean shouldReflectToSettings() {
            return true;
        }

        @Override // com.android.server.notification.ManagedServices
        protected void readExtraTag(java.lang.String str, com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            if (TAG_REQUESTED_LISTENERS.equals(str)) {
                int depth = typedXmlPullParser.getDepth();
                while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
                    if (TAG_REQUESTED_LISTENER.equals(typedXmlPullParser.getName())) {
                        int readIntAttribute = com.android.internal.util.XmlUtils.readIntAttribute(typedXmlPullParser, "user");
                        android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, ATT_COMPONENT));
                        android.util.ArraySet arraySet = new android.util.ArraySet();
                        int depth2 = typedXmlPullParser.getDepth();
                        int i = 15;
                        while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth2)) {
                            if (TAG_APPROVED.equals(typedXmlPullParser.getName())) {
                                i = com.android.internal.util.XmlUtils.readIntAttribute(typedXmlPullParser, ATT_TYPES);
                            } else if (TAG_DISALLOWED.equals(typedXmlPullParser.getName())) {
                                java.lang.String readStringAttribute = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, ATT_PKG);
                                int readIntAttribute2 = com.android.internal.util.XmlUtils.readIntAttribute(typedXmlPullParser, "uid");
                                if (!android.text.TextUtils.isEmpty(readStringAttribute)) {
                                    arraySet.add(new android.content.pm.VersionedPackage(readStringAttribute, readIntAttribute2));
                                }
                            }
                        }
                        android.service.notification.NotificationListenerFilter notificationListenerFilter = new android.service.notification.NotificationListenerFilter(i, arraySet);
                        synchronized (this.mRequestedNotificationListeners) {
                            this.mRequestedNotificationListeners.put(android.util.Pair.create(unflattenFromString, java.lang.Integer.valueOf(readIntAttribute)), notificationListenerFilter);
                        }
                    }
                }
            }
        }

        @Override // com.android.server.notification.ManagedServices
        protected void writeExtraXmlTags(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
            typedXmlSerializer.startTag((java.lang.String) null, TAG_REQUESTED_LISTENERS);
            synchronized (this.mRequestedNotificationListeners) {
                try {
                    for (android.util.Pair<android.content.ComponentName, java.lang.Integer> pair : this.mRequestedNotificationListeners.keySet()) {
                        android.service.notification.NotificationListenerFilter notificationListenerFilter = this.mRequestedNotificationListeners.get(pair);
                        typedXmlSerializer.startTag((java.lang.String) null, TAG_REQUESTED_LISTENER);
                        com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, ATT_COMPONENT, ((android.content.ComponentName) pair.first).flattenToString());
                        com.android.internal.util.XmlUtils.writeIntAttribute(typedXmlSerializer, "user", ((java.lang.Integer) pair.second).intValue());
                        typedXmlSerializer.startTag((java.lang.String) null, TAG_APPROVED);
                        com.android.internal.util.XmlUtils.writeIntAttribute(typedXmlSerializer, ATT_TYPES, notificationListenerFilter.getTypes());
                        typedXmlSerializer.endTag((java.lang.String) null, TAG_APPROVED);
                        java.util.Iterator it = notificationListenerFilter.getDisallowedPackages().iterator();
                        while (it.hasNext()) {
                            android.content.pm.VersionedPackage versionedPackage = (android.content.pm.VersionedPackage) it.next();
                            if (!android.text.TextUtils.isEmpty(versionedPackage.getPackageName())) {
                                typedXmlSerializer.startTag((java.lang.String) null, TAG_DISALLOWED);
                                com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, ATT_PKG, versionedPackage.getPackageName());
                                com.android.internal.util.XmlUtils.writeIntAttribute(typedXmlSerializer, "uid", versionedPackage.getVersionCode());
                                typedXmlSerializer.endTag((java.lang.String) null, TAG_DISALLOWED);
                            }
                        }
                        typedXmlSerializer.endTag((java.lang.String) null, TAG_REQUESTED_LISTENER);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            typedXmlSerializer.endTag((java.lang.String) null, TAG_REQUESTED_LISTENERS);
        }

        @android.annotation.Nullable
        protected android.service.notification.NotificationListenerFilter getNotificationListenerFilter(android.util.Pair<android.content.ComponentName, java.lang.Integer> pair) {
            android.service.notification.NotificationListenerFilter notificationListenerFilter;
            synchronized (this.mRequestedNotificationListeners) {
                notificationListenerFilter = this.mRequestedNotificationListeners.get(pair);
            }
            return notificationListenerFilter;
        }

        protected void setNotificationListenerFilter(android.util.Pair<android.content.ComponentName, java.lang.Integer> pair, android.service.notification.NotificationListenerFilter notificationListenerFilter) {
            synchronized (this.mRequestedNotificationListeners) {
                this.mRequestedNotificationListeners.put(pair, notificationListenerFilter);
            }
        }

        @Override // com.android.server.notification.ManagedServices
        protected void ensureFilters(android.content.pm.ServiceInfo serviceInfo, int i) {
            int typesFromStringList;
            java.lang.String obj;
            android.util.Pair<android.content.ComponentName, java.lang.Integer> create = android.util.Pair.create(serviceInfo.getComponentName(), java.lang.Integer.valueOf(i));
            synchronized (this.mRequestedNotificationListeners) {
                try {
                    android.service.notification.NotificationListenerFilter notificationListenerFilter = this.mRequestedNotificationListeners.get(create);
                    if (serviceInfo.metaData != null) {
                        if (notificationListenerFilter == null && serviceInfo.metaData.containsKey("android.service.notification.default_filter_types") && (obj = serviceInfo.metaData.get("android.service.notification.default_filter_types").toString()) != null) {
                            this.mRequestedNotificationListeners.put(create, new android.service.notification.NotificationListenerFilter(getTypesFromStringList(obj), new android.util.ArraySet()));
                        }
                        if (serviceInfo.metaData.containsKey("android.service.notification.disabled_filter_types") && (typesFromStringList = getTypesFromStringList(serviceInfo.metaData.get("android.service.notification.disabled_filter_types").toString())) != 0) {
                            android.service.notification.NotificationListenerFilter orDefault = this.mRequestedNotificationListeners.getOrDefault(create, new android.service.notification.NotificationListenerFilter());
                            orDefault.setTypes((~typesFromStringList) & orDefault.getTypes());
                            this.mRequestedNotificationListeners.put(create, orDefault);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private int getTypesFromStringList(java.lang.String str) {
            if (str == null) {
                return 0;
            }
            int i = 0;
            for (java.lang.String str2 : str.split(FLAG_SEPARATOR)) {
                if (!android.text.TextUtils.isEmpty(str2)) {
                    if (str2.equalsIgnoreCase("ONGOING")) {
                        i |= 8;
                    } else if (str2.equalsIgnoreCase("CONVERSATIONS")) {
                        i |= 1;
                    } else if (str2.equalsIgnoreCase("SILENT")) {
                        i |= 4;
                    } else if (str2.equalsIgnoreCase("ALERTING")) {
                        i |= 2;
                    } else {
                        try {
                            i |= java.lang.Integer.parseInt(str2);
                        } catch (java.lang.NumberFormatException e) {
                        }
                    }
                }
            }
            return i;
        }

        @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
        public void setOnNotificationPostedTrimLocked(com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo, int i) {
            if (i == 1) {
                this.mLightTrimListeners.add(managedServiceInfo);
            } else {
                this.mLightTrimListeners.remove(managedServiceInfo);
            }
        }

        public int getOnNotificationPostedTrim(com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo) {
            return this.mLightTrimListeners.contains(managedServiceInfo) ? 1 : 0;
        }

        public void onStatusBarIconsBehaviorChanged(final boolean z) {
            for (final com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo : getServices()) {
                com.android.server.notification.NotificationManagerService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.notification.NotificationManagerService$NotificationListeners$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.notification.NotificationManagerService.NotificationListeners.this.lambda$onStatusBarIconsBehaviorChanged$0(managedServiceInfo, z);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onStatusBarIconsBehaviorChanged$0(com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo, boolean z) {
            try {
                managedServiceInfo.service.onStatusBarIconsBehaviorChanged(z);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(this.TAG, "unable to notify listener (hideSilentStatusIcons): " + managedServiceInfo, e);
            }
        }

        @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
        @com.android.internal.annotations.VisibleForTesting
        void notifyPostedLocked(com.android.server.notification.NotificationRecord notificationRecord, com.android.server.notification.NotificationRecord notificationRecord2) {
            notifyPostedLocked(notificationRecord, notificationRecord2, true);
        }

        @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
        @com.android.internal.annotations.VisibleForTesting
        void notifyPostedLocked(com.android.server.notification.NotificationRecord notificationRecord, com.android.server.notification.NotificationRecord notificationRecord2, boolean z) {
            java.util.Iterator<java.lang.Runnable> it = prepareNotifyPostedLocked(notificationRecord, notificationRecord2, z).iterator();
            while (it.hasNext()) {
                com.android.server.notification.NotificationManagerService.this.mHandler.post(it.next());
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:52:0x00d5, code lost:
        
            r0 = r27.this$0.makeRankingUpdateLocked(r12);
            r2.add(new com.android.server.notification.NotificationManagerService$NotificationListeners$$ExternalSyntheticLambda8(r27, r12, r5, r0));
         */
        /* JADX WARN: Removed duplicated region for block: B:65:0x0128  */
        /* JADX WARN: Removed duplicated region for block: B:74:0x016b A[Catch: Exception -> 0x0167, TryCatch #1 {Exception -> 0x0167, blocks: (B:69:0x0137, B:72:0x0159, B:74:0x016b, B:75:0x0174, B:78:0x0170), top: B:68:0x0137 }] */
        /* JADX WARN: Removed duplicated region for block: B:78:0x0170 A[Catch: Exception -> 0x0167, TryCatch #1 {Exception -> 0x0167, blocks: (B:69:0x0137, B:72:0x0159, B:74:0x016b, B:75:0x0174, B:78:0x0170), top: B:68:0x0137 }] */
        /* JADX WARN: Removed duplicated region for block: B:83:0x012b A[Catch: Exception -> 0x002b, TryCatch #0 {Exception -> 0x002b, blocks: (B:8:0x001f, B:10:0x0026, B:11:0x0031, B:12:0x004d, B:14:0x0053, B:19:0x006f, B:24:0x007d, B:26:0x008b, B:36:0x00a6, B:38:0x00b0, B:42:0x00b5, B:44:0x00bb, B:47:0x00c3, B:49:0x00c9, B:52:0x00d5, B:56:0x00e7, B:59:0x00f4, B:87:0x0102, B:89:0x010a, B:90:0x0113, B:92:0x010f, B:63:0x0123, B:66:0x012d, B:83:0x012b), top: B:7:0x001f }] */
        @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
        @com.android.internal.annotations.VisibleForTesting
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        java.util.List<java.lang.Runnable> prepareNotifyPostedLocked(com.android.server.notification.NotificationRecord notificationRecord, com.android.server.notification.NotificationRecord notificationRecord2, boolean z) {
            boolean z2;
            boolean z3;
            java.util.Iterator<com.android.server.notification.ManagedServices.ManagedServiceInfo> it;
            final android.service.notification.NotificationRankingUpdate makeRankingUpdateLocked;
            if (com.android.server.notification.NotificationManagerService.this.isInLockDownMode(notificationRecord.getUser().getIdentifier())) {
                return new java.util.ArrayList();
            }
            java.util.ArrayList arrayList = new java.util.ArrayList();
            try {
                android.service.notification.StatusBarNotification sbn = notificationRecord.getSbn();
                android.service.notification.StatusBarNotification statusBarNotification = null;
                final android.service.notification.StatusBarNotification sbn2 = notificationRecord2 != null ? notificationRecord2.getSbn() : null;
                com.android.server.notification.NotificationManagerService.TrimCache trimCache = com.android.server.notification.NotificationManagerService.this.new TrimCache(sbn);
                boolean hasSensitiveContent = hasSensitiveContent(notificationRecord);
                boolean hasSensitiveContent2 = hasSensitiveContent(notificationRecord2);
                java.util.Iterator<com.android.server.notification.ManagedServices.ManagedServiceInfo> it2 = getServices().iterator();
                android.service.notification.StatusBarNotification statusBarNotification2 = null;
                com.android.server.notification.NotificationManagerService.TrimCache trimCache2 = null;
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    final com.android.server.notification.ManagedServices.ManagedServiceInfo next = it2.next();
                    boolean isUidTrusted = isUidTrusted(next.uid);
                    boolean z4 = com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.redactSensitiveNotificationsFromUntrustedListeners() && hasSensitiveContent && !isUidTrusted;
                    boolean z5 = com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.redactSensitiveNotificationsFromUntrustedListeners() && hasSensitiveContent2 && !isUidTrusted;
                    boolean z6 = hasSensitiveContent;
                    boolean isVisibleToListener = com.android.server.notification.NotificationManagerService.this.isVisibleToListener(sbn, notificationRecord.getNotificationType(), next);
                    try {
                        if (sbn2 == null) {
                            z2 = hasSensitiveContent2;
                        } else {
                            z2 = hasSensitiveContent2;
                            if (com.android.server.notification.NotificationManagerService.this.isVisibleToListener(sbn2, notificationRecord2.getNotificationType(), next)) {
                                z3 = true;
                                if (z3 && !isVisibleToListener) {
                                    it = it2;
                                } else {
                                    it = it2;
                                    if (notificationRecord.isHidden() || next.targetSdkVersion >= 28) {
                                        if (!android.app.Flags.lifetimeExtensionRefactor() && next.isSystemUi() && notificationRecord2 != null && notificationRecord2.getNotification() != null && (notificationRecord2.getNotification().flags & 65536) > 0) {
                                            break;
                                        }
                                        if (!z || next.targetSdkVersion < 28) {
                                            makeRankingUpdateLocked = com.android.server.notification.NotificationManagerService.this.makeRankingUpdateLocked(next);
                                            if (!z3 && !isVisibleToListener) {
                                                if (z5 && statusBarNotification == null) {
                                                    statusBarNotification = redactStatusBarNotification(sbn2);
                                                }
                                                final android.service.notification.StatusBarNotification cloneLight = z5 ? statusBarNotification.cloneLight() : sbn2.cloneLight();
                                                arrayList.add(new java.lang.Runnable() { // from class: com.android.server.notification.NotificationManagerService$NotificationListeners$$ExternalSyntheticLambda9
                                                    @Override // java.lang.Runnable
                                                    public final void run() {
                                                        com.android.server.notification.NotificationManagerService.NotificationListeners.this.lambda$prepareNotifyPostedLocked$2(next, cloneLight, makeRankingUpdateLocked);
                                                    }
                                                });
                                                it2 = it;
                                                hasSensitiveContent = z6;
                                                hasSensitiveContent2 = z2;
                                            } else {
                                                int i = next.userid != -1 ? 0 : next.userid;
                                                com.android.server.notification.NotificationManagerService.this.updateUriPermissions(notificationRecord, notificationRecord2, next.component.getPackageName(), i);
                                                com.android.server.notification.NotificationManagerService.this.mPackageManagerInternal.grantImplicitAccess(i, null, android.os.UserHandle.getAppId(next.uid), sbn.getUid(), false, false);
                                                if (z4 && statusBarNotification2 == null) {
                                                    android.service.notification.StatusBarNotification redactStatusBarNotification = redactStatusBarNotification(sbn);
                                                    statusBarNotification2 = redactStatusBarNotification;
                                                    trimCache2 = com.android.server.notification.NotificationManagerService.this.new TrimCache(redactStatusBarNotification);
                                                }
                                                final android.service.notification.StatusBarNotification ForListener = !z4 ? trimCache2.ForListener(next) : trimCache.ForListener(next);
                                                arrayList.add(new java.lang.Runnable() { // from class: com.android.server.notification.NotificationManagerService$NotificationListeners$$ExternalSyntheticLambda10
                                                    @Override // java.lang.Runnable
                                                    public final void run() {
                                                        com.android.server.notification.NotificationManagerService.NotificationListeners.this.lambda$prepareNotifyPostedLocked$3(next, ForListener, makeRankingUpdateLocked);
                                                    }
                                                });
                                                it2 = it;
                                                hasSensitiveContent = z6;
                                                hasSensitiveContent2 = z2;
                                            }
                                        }
                                    }
                                }
                                it2 = it;
                                hasSensitiveContent = z6;
                                hasSensitiveContent2 = z2;
                            }
                        }
                        com.android.server.notification.NotificationManagerService.this.updateUriPermissions(notificationRecord, notificationRecord2, next.component.getPackageName(), i);
                        com.android.server.notification.NotificationManagerService.this.mPackageManagerInternal.grantImplicitAccess(i, null, android.os.UserHandle.getAppId(next.uid), sbn.getUid(), false, false);
                        if (z4) {
                            android.service.notification.StatusBarNotification redactStatusBarNotification2 = redactStatusBarNotification(sbn);
                            statusBarNotification2 = redactStatusBarNotification2;
                            trimCache2 = com.android.server.notification.NotificationManagerService.this.new TrimCache(redactStatusBarNotification2);
                        }
                        if (!z4) {
                        }
                        arrayList.add(new java.lang.Runnable() { // from class: com.android.server.notification.NotificationManagerService$NotificationListeners$$ExternalSyntheticLambda10
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.notification.NotificationManagerService.NotificationListeners.this.lambda$prepareNotifyPostedLocked$3(next, ForListener, makeRankingUpdateLocked);
                            }
                        });
                        it2 = it;
                        hasSensitiveContent = z6;
                        hasSensitiveContent2 = z2;
                    } catch (java.lang.Exception e) {
                        e = e;
                        android.util.Slog.e(this.TAG, "Could not notify listeners for " + notificationRecord.getKey(), e);
                        return arrayList;
                    }
                    z3 = false;
                    if (z3) {
                    }
                    it = it2;
                    if (notificationRecord.isHidden()) {
                    }
                    if (!android.app.Flags.lifetimeExtensionRefactor()) {
                    }
                    if (!z) {
                    }
                    makeRankingUpdateLocked = com.android.server.notification.NotificationManagerService.this.makeRankingUpdateLocked(next);
                    if (!z3) {
                    }
                    if (next.userid != -1) {
                    }
                }
            } catch (java.lang.Exception e2) {
                e = e2;
            }
            return arrayList;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$prepareNotifyPostedLocked$2(com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo, android.service.notification.StatusBarNotification statusBarNotification, android.service.notification.NotificationRankingUpdate notificationRankingUpdate) {
            lambda$notifyRemovedLocked$4(managedServiceInfo, statusBarNotification, notificationRankingUpdate, null, 6);
        }

        boolean isAppTrustedNotificationListenerService(int i, java.lang.String str) {
            if (!com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.redactSensitiveNotificationsFromUntrustedListeners()) {
                return true;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                try {
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(this.TAG, "Failed to check trusted status of listener", e);
                }
                if (com.android.server.notification.NotificationManagerService.this.mPackageManager.checkUidPermission("android.permission.RECEIVE_SENSITIVE_NOTIFICATIONS", i) != 0 && !com.android.server.notification.NotificationManagerService.this.mPackageManagerInternal.isPlatformSigned(str)) {
                    java.util.List arrayList = new java.util.ArrayList();
                    if (com.android.server.notification.NotificationManagerService.this.mCompanionManager == null) {
                        com.android.server.notification.NotificationManagerService.this.mCompanionManager = com.android.server.notification.NotificationManagerService.this.getCompanionManager();
                    }
                    if (com.android.server.notification.NotificationManagerService.this.mCompanionManager != null) {
                        arrayList = com.android.server.notification.NotificationManagerService.this.mCompanionManager.getAllAssociationsForUser(android.os.UserHandle.getUserId(i));
                    }
                    for (int i2 = 0; i2 < arrayList.size(); i2++) {
                        android.companion.AssociationInfo associationInfo = (android.companion.AssociationInfo) arrayList.get(i2);
                        if (!associationInfo.isRevoked() && str.equals(associationInfo.getPackageName()) && associationInfo.getUserId() == android.os.UserHandle.getUserId(i)) {
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            return true;
                        }
                    }
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return false;
                }
                return true;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        android.service.notification.StatusBarNotification redactStatusBarNotification(android.service.notification.StatusBarNotification statusBarNotification) {
            java.lang.String packageName;
            if (!com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.redactSensitiveNotificationsFromUntrustedListeners()) {
                throw new java.lang.RuntimeException("redactStatusBarNotification called while flag is off");
            }
            android.content.pm.ApplicationInfo applicationInfo = (android.content.pm.ApplicationInfo) statusBarNotification.getNotification().extras.getParcelable("android.appInfo", android.content.pm.ApplicationInfo.class);
            if (applicationInfo != null) {
                packageName = applicationInfo.loadLabel(com.android.server.notification.NotificationManagerService.this.mPackageManagerClient).toString();
            } else {
                android.util.Slog.w(this.TAG, "StatusBarNotification " + statusBarNotification + " does not have ApplicationInfo. Did you pass in a 'cloneLight' notification?");
                packageName = statusBarNotification.getPackageName();
            }
            java.lang.String string = this.mContext.getString(android.R.string.private_space_set_up_screen_lock_message);
            android.app.Notification notification = statusBarNotification.getNotification();
            android.app.Notification notification2 = new android.app.Notification();
            notification.cloneInto(notification2, false);
            android.app.Notification.Builder builder = new android.app.Notification.Builder(com.android.server.notification.NotificationManagerService.this.getContext(), notification2);
            builder.setContentTitle(packageName);
            builder.setContentText(string);
            builder.setSubText(null);
            builder.setActions(new android.app.Notification.Action[0]);
            if (notification.actions != null) {
                for (int i = 0; i < notification.actions.length; i++) {
                    android.app.Notification.Action build = new android.app.Notification.Action.Builder(notification.actions[i]).build();
                    build.title = this.mContext.getString(android.R.string.private_profile_label_badge);
                    builder.addAction(build);
                }
            }
            if (notification.isStyle(android.app.Notification.MessagingStyle.class)) {
                android.app.Person build2 = new android.app.Person.Builder().setName("").build();
                android.app.Notification.MessagingStyle messagingStyle = new android.app.Notification.MessagingStyle(build2);
                messagingStyle.addMessage(new android.app.Notification.MessagingStyle.Message(string, java.lang.System.currentTimeMillis(), build2));
                builder.setStyle(messagingStyle);
            }
            android.app.Notification build3 = builder.build();
            if (build3.extras.containsKey("android.title.big")) {
                build3.extras.putString("android.title.big", packageName);
            }
            build3.extras.remove("android.subText");
            build3.extras.remove("android.textLines");
            build3.extras.remove("android.largeIcon.big");
            return statusBarNotification.cloneShallow(build3);
        }

        boolean hasSensitiveContent(com.android.server.notification.NotificationRecord notificationRecord) {
            if (notificationRecord == null || !com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.redactSensitiveNotificationsFromUntrustedListeners()) {
                return false;
            }
            return notificationRecord.hasSensitiveContent();
        }

        boolean isUidTrusted(int i) {
            boolean z;
            synchronized (this.mTrustedListenerUids) {
                try {
                    z = !com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.redactSensitiveNotificationsFromUntrustedListeners() || this.mTrustedListenerUids.contains(java.lang.Integer.valueOf(i));
                } finally {
                }
            }
            return z;
        }

        @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
        private void updateUriPermissionsForActiveNotificationsLocked(com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo, boolean z) {
            try {
                java.util.Iterator<com.android.server.notification.NotificationRecord> it = com.android.server.notification.NotificationManagerService.this.mNotificationList.iterator();
                while (it.hasNext()) {
                    com.android.server.notification.NotificationRecord next = it.next();
                    if (!z || com.android.server.notification.NotificationManagerService.this.isVisibleToListener(next.getSbn(), next.getNotificationType(), managedServiceInfo)) {
                        if (!next.isHidden() || managedServiceInfo.targetSdkVersion >= 28) {
                            int i = managedServiceInfo.userid == -1 ? 0 : managedServiceInfo.userid;
                            if (z) {
                                com.android.server.notification.NotificationManagerService.this.updateUriPermissions(next, null, managedServiceInfo.component.getPackageName(), i);
                            } else {
                                com.android.server.notification.NotificationManagerService.this.updateUriPermissions(null, next, managedServiceInfo.component.getPackageName(), i, true);
                            }
                        }
                    }
                }
            } catch (java.lang.Exception e) {
                java.lang.String str = this.TAG;
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("Could not ");
                sb.append(z ? "grant" : "revoke");
                sb.append(" Uri permissions to ");
                sb.append(managedServiceInfo.component);
                android.util.Slog.e(str, sb.toString(), e);
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:19:0x005a, code lost:
        
            if (r2.targetSdkVersion < 28) goto L48;
         */
        @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void notifyRemovedLocked(final com.android.server.notification.NotificationRecord notificationRecord, final int i, android.service.notification.NotificationStats notificationStats) {
            android.service.notification.StatusBarNotification statusBarNotification;
            if (com.android.server.notification.NotificationManagerService.this.isInLockDownMode(notificationRecord.getUser().getIdentifier())) {
                return;
            }
            android.service.notification.StatusBarNotification sbn = notificationRecord.getSbn();
            android.service.notification.StatusBarNotification cloneLight = sbn.cloneLight();
            boolean hasSensitiveContent = hasSensitiveContent(notificationRecord);
            java.util.Iterator<com.android.server.notification.ManagedServices.ManagedServiceInfo> it = getServices().iterator();
            android.service.notification.StatusBarNotification statusBarNotification2 = null;
            while (it.hasNext()) {
                final com.android.server.notification.ManagedServices.ManagedServiceInfo next = it.next();
                if (com.android.server.notification.NotificationManagerService.this.isVisibleToListener(sbn, notificationRecord.getNotificationType(), next) && (!notificationRecord.isHidden() || i == 14 || next.targetSdkVersion >= 28)) {
                    boolean z = com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.redactSensitiveNotificationsFromUntrustedListeners() && hasSensitiveContent && !isUidTrusted(next.uid);
                    if (z && statusBarNotification2 == null) {
                        statusBarNotification = redactStatusBarNotification(sbn);
                    } else {
                        statusBarNotification = statusBarNotification2;
                    }
                    final android.service.notification.NotificationStats notificationStats2 = com.android.server.notification.NotificationManagerService.this.mAssistants.isServiceTokenValidLocked(next.service) ? notificationStats : null;
                    final android.service.notification.StatusBarNotification statusBarNotification3 = z ? statusBarNotification : cloneLight;
                    final android.service.notification.NotificationRankingUpdate makeRankingUpdateLocked = com.android.server.notification.NotificationManagerService.this.makeRankingUpdateLocked(next);
                    com.android.server.notification.NotificationManagerService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.notification.NotificationManagerService$NotificationListeners$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.notification.NotificationManagerService.NotificationListeners.this.lambda$notifyRemovedLocked$4(next, statusBarNotification3, makeRankingUpdateLocked, notificationStats2, i);
                        }
                    });
                    statusBarNotification2 = statusBarNotification;
                }
            }
            com.android.server.notification.NotificationManagerService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.notification.NotificationManagerService$NotificationListeners$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.notification.NotificationManagerService.NotificationListeners.this.lambda$notifyRemovedLocked$5(notificationRecord);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyRemovedLocked$5(com.android.server.notification.NotificationRecord notificationRecord) {
            com.android.server.notification.NotificationManagerService.this.updateUriPermissions(null, notificationRecord, null, 0);
        }

        @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
        public void notifyRankingUpdateLocked(java.util.List<com.android.server.notification.NotificationRecord> list) {
            boolean z;
            boolean z2 = list != null && list.size() > 0;
            for (final com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo : getServices()) {
                if (managedServiceInfo.isEnabledForCurrentProfiles() && com.android.server.notification.NotificationManagerService.this.isInteractionVisibleToListener(managedServiceInfo, android.app.ActivityManager.getCurrentUser())) {
                    if (z2 && managedServiceInfo.targetSdkVersion >= 28) {
                        for (com.android.server.notification.NotificationRecord notificationRecord : list) {
                            if (com.android.server.notification.NotificationManagerService.this.isVisibleToListener(notificationRecord.getSbn(), notificationRecord.getNotificationType(), managedServiceInfo)) {
                                z = true;
                                break;
                            }
                        }
                    }
                    z = false;
                    if (z || !z2) {
                        final android.service.notification.NotificationRankingUpdate makeRankingUpdateLocked = com.android.server.notification.NotificationManagerService.this.makeRankingUpdateLocked(managedServiceInfo);
                        com.android.server.notification.NotificationManagerService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.notification.NotificationManagerService$NotificationListeners$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.notification.NotificationManagerService.NotificationListeners.this.lambda$notifyRankingUpdateLocked$6(managedServiceInfo, makeRankingUpdateLocked);
                            }
                        });
                    }
                }
            }
        }

        @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
        public void notifyListenerHintsChangedLocked(final int i) {
            for (final com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo : getServices()) {
                if (managedServiceInfo.isEnabledForCurrentProfiles() && com.android.server.notification.NotificationManagerService.this.isInteractionVisibleToListener(managedServiceInfo, android.app.ActivityManager.getCurrentUser())) {
                    com.android.server.notification.NotificationManagerService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.notification.NotificationManagerService$NotificationListeners$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.notification.NotificationManagerService.NotificationListeners.this.lambda$notifyListenerHintsChangedLocked$7(managedServiceInfo, i);
                        }
                    });
                }
            }
        }

        @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
        public void notifyHiddenLocked(java.util.List<com.android.server.notification.NotificationRecord> list) {
            if (list == null || list.size() == 0) {
                return;
            }
            notifyRankingUpdateLocked(list);
            int size = list.size();
            for (int i = 0; i < size; i++) {
                com.android.server.notification.NotificationRecord notificationRecord = list.get(i);
                com.android.server.notification.NotificationManagerService.this.mListeners.notifyRemovedLocked(notificationRecord, 14, notificationRecord.getStats());
            }
        }

        @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
        public void notifyUnhiddenLocked(java.util.List<com.android.server.notification.NotificationRecord> list) {
            if (list == null || list.size() == 0) {
                return;
            }
            notifyRankingUpdateLocked(list);
            int size = list.size();
            for (int i = 0; i < size; i++) {
                com.android.server.notification.NotificationRecord notificationRecord = list.get(i);
                notifyPostedLocked(notificationRecord, notificationRecord, false);
            }
        }

        public void notifyInterruptionFilterChanged(final int i) {
            for (final com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo : getServices()) {
                if (managedServiceInfo.isEnabledForCurrentProfiles() && com.android.server.notification.NotificationManagerService.this.isInteractionVisibleToListener(managedServiceInfo, android.app.ActivityManager.getCurrentUser())) {
                    com.android.server.notification.NotificationManagerService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.notification.NotificationManagerService$NotificationListeners$$ExternalSyntheticLambda7
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.notification.NotificationManagerService.NotificationListeners.this.lambda$notifyInterruptionFilterChanged$8(managedServiceInfo, i);
                        }
                    });
                }
            }
        }

        protected void notifyNotificationChannelChanged(final java.lang.String str, final android.os.UserHandle userHandle, final android.app.NotificationChannel notificationChannel, final int i) {
            if (notificationChannel == null) {
                return;
            }
            for (final com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo : getServices()) {
                if (managedServiceInfo.enabledAndUserMatches(android.os.UserHandle.getCallingUserId()) && com.android.server.notification.NotificationManagerService.this.isInteractionVisibleToListener(managedServiceInfo, android.os.UserHandle.getCallingUserId())) {
                    com.android.internal.os.BackgroundThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.notification.NotificationManagerService$NotificationListeners$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.notification.NotificationManagerService.NotificationListeners.this.lambda$notifyNotificationChannelChanged$9(managedServiceInfo, str, userHandle, notificationChannel, i);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyNotificationChannelChanged$9(com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo, java.lang.String str, android.os.UserHandle userHandle, android.app.NotificationChannel notificationChannel, int i) {
            if (managedServiceInfo.isSystem || com.android.server.notification.NotificationManagerService.this.hasCompanionDevice(managedServiceInfo) || com.android.server.notification.NotificationManagerService.this.isServiceTokenValid(managedServiceInfo.service)) {
                notifyNotificationChannelChanged(managedServiceInfo, str, userHandle, notificationChannel, i);
            }
        }

        protected void notifyNotificationChannelGroupChanged(final java.lang.String str, final android.os.UserHandle userHandle, final android.app.NotificationChannelGroup notificationChannelGroup, final int i) {
            if (notificationChannelGroup == null) {
                return;
            }
            for (final com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo : getServices()) {
                if (managedServiceInfo.enabledAndUserMatches(android.os.UserHandle.getCallingUserId()) && com.android.server.notification.NotificationManagerService.this.isInteractionVisibleToListener(managedServiceInfo, android.os.UserHandle.getCallingUserId())) {
                    com.android.internal.os.BackgroundThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.notification.NotificationManagerService$NotificationListeners$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.notification.NotificationManagerService.NotificationListeners.this.lambda$notifyNotificationChannelGroupChanged$10(managedServiceInfo, str, userHandle, notificationChannelGroup, i);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyNotificationChannelGroupChanged$10(com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo, java.lang.String str, android.os.UserHandle userHandle, android.app.NotificationChannelGroup notificationChannelGroup, int i) {
            if (managedServiceInfo.isSystem() || com.android.server.notification.NotificationManagerService.this.hasCompanionDevice(managedServiceInfo)) {
                notifyNotificationChannelGroupChanged(managedServiceInfo, str, userHandle, notificationChannelGroup, i);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: notifyPosted, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
        public void lambda$prepareNotifyPostedLocked$3(com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo, android.service.notification.StatusBarNotification statusBarNotification, android.service.notification.NotificationRankingUpdate notificationRankingUpdate) {
            try {
                managedServiceInfo.service.onNotificationPosted(new com.android.server.notification.NotificationManagerService.StatusBarNotificationHolder(statusBarNotification), notificationRankingUpdate);
            } catch (android.os.DeadObjectException e) {
                android.util.Slog.wtf(this.TAG, "unable to notify listener (posted): " + managedServiceInfo, e);
            } catch (android.os.RemoteException e2) {
                android.util.Slog.e(this.TAG, "unable to notify listener (posted): " + managedServiceInfo, e2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: notifyRemoved, reason: merged with bridge method [inline-methods] */
        public void lambda$notifyRemovedLocked$4(com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo, android.service.notification.StatusBarNotification statusBarNotification, android.service.notification.NotificationRankingUpdate notificationRankingUpdate, android.service.notification.NotificationStats notificationStats, int i) {
            android.service.notification.INotificationListener iNotificationListener = managedServiceInfo.service;
            com.android.server.notification.NotificationManagerService.StatusBarNotificationHolder statusBarNotificationHolder = new com.android.server.notification.NotificationManagerService.StatusBarNotificationHolder(statusBarNotification);
            try {
                if (!android.app.compat.CompatChanges.isChangeEnabled(com.android.server.notification.NotificationManagerService.NOTIFICATION_CANCELLATION_REASONS, managedServiceInfo.uid) && (i == 20 || i == 21)) {
                    i = 17;
                }
                if (!android.app.compat.CompatChanges.isChangeEnabled(com.android.server.notification.NotificationManagerService.NOTIFICATION_LOG_ASSISTANT_CANCEL, managedServiceInfo.uid) && i == 22) {
                    i = 10;
                }
                iNotificationListener.onNotificationRemoved(statusBarNotificationHolder, notificationRankingUpdate, notificationStats, i);
            } catch (android.os.DeadObjectException e) {
                android.util.Slog.wtf(this.TAG, "unable to notify listener (removed): " + managedServiceInfo, e);
            } catch (android.os.RemoteException e2) {
                android.util.Slog.e(this.TAG, "unable to notify listener (removed): " + managedServiceInfo, e2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: notifyRankingUpdate, reason: merged with bridge method [inline-methods] */
        public void lambda$notifyRankingUpdateLocked$6(com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo, android.service.notification.NotificationRankingUpdate notificationRankingUpdate) {
            try {
                managedServiceInfo.service.onNotificationRankingUpdate(notificationRankingUpdate);
            } catch (android.os.DeadObjectException e) {
                android.util.Slog.wtf(this.TAG, "unable to notify listener (ranking update): " + managedServiceInfo, e);
            } catch (android.os.RemoteException e2) {
                android.util.Slog.e(this.TAG, "unable to notify listener (ranking update): " + managedServiceInfo, e2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: notifyListenerHintsChanged, reason: merged with bridge method [inline-methods] */
        public void lambda$notifyListenerHintsChangedLocked$7(com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo, int i) {
            try {
                managedServiceInfo.service.onListenerHintsChanged(i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(this.TAG, "unable to notify listener (listener hints): " + managedServiceInfo, e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: notifyInterruptionFilterChanged, reason: merged with bridge method [inline-methods] */
        public void lambda$notifyInterruptionFilterChanged$8(com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo, int i) {
            try {
                managedServiceInfo.service.onInterruptionFilterChanged(i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(this.TAG, "unable to notify listener (interruption filter): " + managedServiceInfo, e);
            }
        }

        void notifyNotificationChannelChanged(com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo, java.lang.String str, android.os.UserHandle userHandle, android.app.NotificationChannel notificationChannel, int i) {
            try {
                managedServiceInfo.service.onNotificationChannelModification(str, userHandle, notificationChannel, i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(this.TAG, "unable to notify listener (channel changed): " + managedServiceInfo, e);
            }
        }

        private void notifyNotificationChannelGroupChanged(com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo, java.lang.String str, android.os.UserHandle userHandle, android.app.NotificationChannelGroup notificationChannelGroup, int i) {
            try {
                managedServiceInfo.getService().onNotificationChannelGroupModification(str, userHandle, notificationChannelGroup, i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(this.TAG, "unable to notify listener (channel group changed): " + managedServiceInfo, e);
            }
        }

        public boolean isListenerPackage(java.lang.String str) {
            if (str == null) {
                return false;
            }
            synchronized (com.android.server.notification.NotificationManagerService.this.mNotificationLock) {
                try {
                    java.util.Iterator<com.android.server.notification.ManagedServices.ManagedServiceInfo> it = getServices().iterator();
                    while (it.hasNext()) {
                        if (str.equals(it.next().component.getPackageName())) {
                            return true;
                        }
                    }
                    return false;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        boolean hasAllowedListener(java.lang.String str, int i) {
            if (str == null) {
                return false;
            }
            java.util.List<android.content.ComponentName> allowedComponents = getAllowedComponents(i);
            for (int i2 = 0; i2 < allowedComponents.size(); i2++) {
                if (allowedComponents.get(i2).getPackageName().equals(str)) {
                    return true;
                }
            }
            return false;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    private void broadcastToCallNotificationEventCallbacks(android.os.RemoteCallbackList<android.app.ICallNotificationEventCallback> remoteCallbackList, com.android.server.notification.NotificationRecord notificationRecord, boolean z) {
        if (remoteCallbackList != null) {
            int beginBroadcast = remoteCallbackList.beginBroadcast();
            for (int i = 0; i < beginBroadcast; i++) {
                if (z) {
                    try {
                        remoteCallbackList.getBroadcastItem(i).onCallNotificationPosted(notificationRecord.getSbn().getPackageName(), notificationRecord.getUser());
                    } catch (android.os.RemoteException e) {
                        throw new java.lang.RuntimeException(e);
                    }
                } else {
                    remoteCallbackList.getBroadcastItem(i).onCallNotificationRemoved(notificationRecord.getSbn().getPackageName(), notificationRecord.getUser());
                }
            }
            remoteCallbackList.finishBroadcast();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    void notifyCallNotificationEventListenerOnPosted(com.android.server.notification.NotificationRecord notificationRecord) {
        if (!notificationRecord.getNotification().isStyle(android.app.Notification.CallStyle.class)) {
            return;
        }
        synchronized (this.mCallNotificationEventCallbacks) {
            try {
                android.util.ArrayMap<java.lang.Integer, android.os.RemoteCallbackList<android.app.ICallNotificationEventCallback>> arrayMap = this.mCallNotificationEventCallbacks.get(notificationRecord.getSbn().getPackageName());
                if (arrayMap == null) {
                    return;
                }
                if (!notificationRecord.getUser().equals(android.os.UserHandle.ALL)) {
                    broadcastToCallNotificationEventCallbacks(arrayMap.get(java.lang.Integer.valueOf(notificationRecord.getUser().getIdentifier())), notificationRecord, true);
                    broadcastToCallNotificationEventCallbacks(arrayMap.get(-1), notificationRecord, true);
                } else {
                    java.util.Iterator<android.os.RemoteCallbackList<android.app.ICallNotificationEventCallback>> it = arrayMap.values().iterator();
                    while (it.hasNext()) {
                        broadcastToCallNotificationEventCallbacks(it.next(), notificationRecord, true);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mNotificationLock"})
    void notifyCallNotificationEventListenerOnRemoved(com.android.server.notification.NotificationRecord notificationRecord) {
        if (!notificationRecord.getNotification().isStyle(android.app.Notification.CallStyle.class)) {
            return;
        }
        synchronized (this.mCallNotificationEventCallbacks) {
            try {
                android.util.ArrayMap<java.lang.Integer, android.os.RemoteCallbackList<android.app.ICallNotificationEventCallback>> arrayMap = this.mCallNotificationEventCallbacks.get(notificationRecord.getSbn().getPackageName());
                if (arrayMap == null) {
                    return;
                }
                if (!notificationRecord.getUser().equals(android.os.UserHandle.ALL)) {
                    broadcastToCallNotificationEventCallbacks(arrayMap.get(java.lang.Integer.valueOf(notificationRecord.getUser().getIdentifier())), notificationRecord, false);
                    broadcastToCallNotificationEventCallbacks(arrayMap.get(-1), notificationRecord, false);
                } else {
                    java.util.Iterator<android.os.RemoteCallbackList<android.app.ICallNotificationEventCallback>> it = arrayMap.values().iterator();
                    while (it.hasNext()) {
                        broadcastToCallNotificationEventCallbacks(it.next(), notificationRecord, false);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    class RoleObserver implements android.app.role.OnRoleHoldersChangedListener {
        private final java.util.concurrent.Executor mExecutor;
        private final android.os.Looper mMainLooper;
        private android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.Integer, android.util.ArraySet<java.lang.String>>> mNonBlockableDefaultApps;
        private final android.content.pm.IPackageManager mPm;
        private final android.app.role.RoleManager mRm;
        private volatile android.util.ArraySet<java.lang.Integer> mTrampolineExemptUids = new android.util.ArraySet<>();

        RoleObserver(android.content.Context context, @android.annotation.NonNull android.app.role.RoleManager roleManager, @android.annotation.NonNull android.content.pm.IPackageManager iPackageManager, @android.annotation.NonNull android.os.Looper looper) {
            this.mRm = roleManager;
            this.mPm = iPackageManager;
            this.mExecutor = context.getMainExecutor();
            this.mMainLooper = looper;
        }

        public void init() {
            java.util.List userHandles = com.android.server.notification.NotificationManagerService.this.mUm.getUserHandles(true);
            this.mNonBlockableDefaultApps = new android.util.ArrayMap<>();
            for (int i = 0; i < com.android.server.notification.NotificationManagerService.NON_BLOCKABLE_DEFAULT_ROLES.length; i++) {
                android.util.ArrayMap<java.lang.Integer, android.util.ArraySet<java.lang.String>> arrayMap = new android.util.ArrayMap<>();
                this.mNonBlockableDefaultApps.put(com.android.server.notification.NotificationManagerService.NON_BLOCKABLE_DEFAULT_ROLES[i], arrayMap);
                for (int i2 = 0; i2 < userHandles.size(); i2++) {
                    java.lang.Integer valueOf = java.lang.Integer.valueOf(((android.os.UserHandle) userHandles.get(i2)).getIdentifier());
                    android.util.ArraySet<java.lang.String> arraySet = new android.util.ArraySet<>(this.mRm.getRoleHoldersAsUser(com.android.server.notification.NotificationManagerService.NON_BLOCKABLE_DEFAULT_ROLES[i], android.os.UserHandle.of(valueOf.intValue())));
                    android.util.ArraySet<android.util.Pair<java.lang.String, java.lang.Integer>> arraySet2 = new android.util.ArraySet<>();
                    java.util.Iterator<java.lang.String> it = arraySet.iterator();
                    while (it.hasNext()) {
                        java.lang.String next = it.next();
                        arraySet2.add(new android.util.Pair<>(next, java.lang.Integer.valueOf(getUidForPackage(next, valueOf.intValue()))));
                    }
                    arrayMap.put(valueOf, arraySet);
                    com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.updateDefaultApps(valueOf.intValue(), null, arraySet2);
                }
            }
            updateTrampolineExemptUidsForUsers((android.os.UserHandle[]) userHandles.toArray(new android.os.UserHandle[0]));
            this.mRm.addOnRoleHoldersChangedListenerAsUser(this.mExecutor, this, android.os.UserHandle.ALL);
        }

        @com.android.internal.annotations.VisibleForTesting
        public boolean isApprovedPackageForRoleForUser(java.lang.String str, java.lang.String str2, int i) {
            return this.mNonBlockableDefaultApps.get(str).get(java.lang.Integer.valueOf(i)).contains(str2);
        }

        @com.android.internal.annotations.VisibleForTesting
        public boolean isUidExemptFromTrampolineRestrictions(int i) {
            return this.mTrampolineExemptUids.contains(java.lang.Integer.valueOf(i));
        }

        public void onRoleHoldersChanged(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.UserHandle userHandle) {
            onRoleHoldersChangedForNonBlockableDefaultApps(str, userHandle);
            onRoleHoldersChangedForTrampolines(str, userHandle);
        }

        private void onRoleHoldersChangedForNonBlockableDefaultApps(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.UserHandle userHandle) {
            boolean z = false;
            int i = 0;
            while (true) {
                if (i >= com.android.server.notification.NotificationManagerService.NON_BLOCKABLE_DEFAULT_ROLES.length) {
                    break;
                }
                if (!com.android.server.notification.NotificationManagerService.NON_BLOCKABLE_DEFAULT_ROLES[i].equals(str)) {
                    i++;
                } else {
                    z = true;
                    break;
                }
            }
            if (!z) {
                return;
            }
            android.util.ArraySet<java.lang.String> arraySet = new android.util.ArraySet<>(this.mRm.getRoleHoldersAsUser(str, userHandle));
            android.util.ArrayMap<java.lang.Integer, android.util.ArraySet<java.lang.String>> orDefault = this.mNonBlockableDefaultApps.getOrDefault(str, new android.util.ArrayMap<>());
            android.util.ArraySet<java.lang.String> orDefault2 = orDefault.getOrDefault(java.lang.Integer.valueOf(userHandle.getIdentifier()), new android.util.ArraySet<>());
            android.util.ArraySet<java.lang.String> arraySet2 = new android.util.ArraySet<>();
            android.util.ArraySet<android.util.Pair<java.lang.String, java.lang.Integer>> arraySet3 = new android.util.ArraySet<>();
            java.util.Iterator<java.lang.String> it = orDefault2.iterator();
            while (it.hasNext()) {
                java.lang.String next = it.next();
                if (!arraySet.contains(next)) {
                    arraySet2.add(next);
                }
            }
            java.util.Iterator<java.lang.String> it2 = arraySet.iterator();
            while (it2.hasNext()) {
                java.lang.String next2 = it2.next();
                if (!orDefault2.contains(next2)) {
                    arraySet3.add(new android.util.Pair<>(next2, java.lang.Integer.valueOf(getUidForPackage(next2, userHandle.getIdentifier()))));
                }
            }
            orDefault.put(java.lang.Integer.valueOf(userHandle.getIdentifier()), arraySet);
            this.mNonBlockableDefaultApps.put(str, orDefault);
            com.android.server.notification.NotificationManagerService.this.mPreferencesHelper.updateDefaultApps(userHandle.getIdentifier(), arraySet2, arraySet3);
        }

        private void onRoleHoldersChangedForTrampolines(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.UserHandle userHandle) {
            if (!"android.app.role.BROWSER".equals(str)) {
                return;
            }
            updateTrampolineExemptUidsForUsers(userHandle);
        }

        private void updateTrampolineExemptUidsForUsers(android.os.UserHandle... userHandleArr) {
            com.android.internal.util.Preconditions.checkState(this.mMainLooper.isCurrentThread());
            android.util.ArraySet<java.lang.Integer> arraySet = this.mTrampolineExemptUids;
            android.util.ArraySet<java.lang.Integer> arraySet2 = new android.util.ArraySet<>();
            int size = arraySet.size();
            for (int i = 0; i < size; i++) {
                int intValue = arraySet.valueAt(i).intValue();
                if (!com.android.internal.util.ArrayUtils.contains(userHandleArr, android.os.UserHandle.of(android.os.UserHandle.getUserId(intValue)))) {
                    arraySet2.add(java.lang.Integer.valueOf(intValue));
                }
            }
            for (android.os.UserHandle userHandle : userHandleArr) {
                for (java.lang.String str : this.mRm.getRoleHoldersAsUser("android.app.role.BROWSER", userHandle)) {
                    int uidForPackage = getUidForPackage(str, userHandle.getIdentifier());
                    if (uidForPackage != -1) {
                        arraySet2.add(java.lang.Integer.valueOf(uidForPackage));
                    } else {
                        android.util.Slog.e(com.android.server.notification.NotificationManagerService.TAG, "Bad uid (-1) for browser package " + str);
                    }
                }
            }
            this.mTrampolineExemptUids = arraySet2;
        }

        private int getUidForPackage(java.lang.String str, int i) {
            try {
                return this.mPm.getPackageUid(str, 131072L, i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.notification.NotificationManagerService.TAG, "role manager has bad default " + str + " " + i);
                return -1;
            }
        }
    }

    public static final class DumpFilter {
        public java.lang.String pkgFilter;
        public boolean rvStats;
        public long since;
        public boolean stats;
        public boolean zen;
        public boolean filtered = false;
        public boolean redact = true;
        public boolean proto = false;
        public boolean criticalPriority = false;
        public boolean normalPriority = false;

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @android.annotation.NonNull
        public static com.android.server.notification.NotificationManagerService.DumpFilter parseFromArguments(java.lang.String[] strArr) {
            char c;
            com.android.server.notification.NotificationManagerService.DumpFilter dumpFilter = new com.android.server.notification.NotificationManagerService.DumpFilter();
            int i = 0;
            while (i < strArr.length) {
                java.lang.String str = strArr[i];
                if ("--proto".equals(str)) {
                    dumpFilter.proto = true;
                } else if ("--noredact".equals(str) || "--reveal".equals(str)) {
                    dumpFilter.redact = false;
                } else if ("p".equals(str) || "pkg".equals(str) || "--package".equals(str)) {
                    if (i < strArr.length - 1) {
                        i++;
                        dumpFilter.pkgFilter = strArr[i].trim().toLowerCase();
                        if (dumpFilter.pkgFilter.isEmpty()) {
                            dumpFilter.pkgFilter = null;
                        } else {
                            dumpFilter.filtered = true;
                        }
                    }
                } else if ("--zen".equals(str) || "zen".equals(str)) {
                    dumpFilter.filtered = true;
                    dumpFilter.zen = true;
                } else if ("--stats".equals(str)) {
                    dumpFilter.stats = true;
                    if (i < strArr.length - 1) {
                        i++;
                        dumpFilter.since = java.lang.Long.parseLong(strArr[i]);
                    } else {
                        dumpFilter.since = 0L;
                    }
                } else if ("--remote-view-stats".equals(str)) {
                    dumpFilter.rvStats = true;
                    if (i < strArr.length - 1) {
                        i++;
                        dumpFilter.since = java.lang.Long.parseLong(strArr[i]);
                    } else {
                        dumpFilter.since = 0L;
                    }
                } else if (com.android.server.utils.PriorityDump.PRIORITY_ARG.equals(str) && i < strArr.length - 1) {
                    i++;
                    java.lang.String str2 = strArr[i];
                    switch (str2.hashCode()) {
                        case -1986416409:
                            if (str2.equals(com.android.server.utils.PriorityDump.PRIORITY_ARG_NORMAL)) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case -1560189025:
                            if (str2.equals(com.android.server.utils.PriorityDump.PRIORITY_ARG_CRITICAL)) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
                    switch (c) {
                        case 0:
                            dumpFilter.criticalPriority = true;
                            break;
                        case 1:
                            dumpFilter.normalPriority = true;
                            break;
                    }
                }
                i++;
            }
            return dumpFilter;
        }

        public boolean matches(android.service.notification.StatusBarNotification statusBarNotification) {
            if (this.filtered && !this.zen) {
                return statusBarNotification != null && (matches(statusBarNotification.getPackageName()) || matches(statusBarNotification.getOpPkg()));
            }
            return true;
        }

        public boolean matches(android.content.ComponentName componentName) {
            if (this.filtered && !this.zen) {
                return componentName != null && matches(componentName.getPackageName());
            }
            return true;
        }

        public boolean matches(java.lang.String str) {
            if (this.filtered && !this.zen) {
                return str != null && str.toLowerCase().contains(this.pkgFilter);
            }
            return true;
        }

        public java.lang.String toString() {
            if (this.stats) {
                return "stats";
            }
            if (this.zen) {
                return "zen";
            }
            return '\'' + this.pkgFilter + '\'';
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void resetAssistantUserSet(int i) {
        checkCallerIsSystemOrShell();
        this.mAssistants.setUserSet(i, false);
        handleSavePolicyFile();
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    android.content.ComponentName getApprovedAssistant(int i) {
        checkCallerIsSystemOrShell();
        return (android.content.ComponentName) com.android.internal.util.CollectionUtils.firstOrNull(this.mAssistants.getAllowedComponents(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class StatusBarNotificationHolder extends android.service.notification.IStatusBarNotificationHolder.Stub {
        private android.service.notification.StatusBarNotification mValue;

        public StatusBarNotificationHolder(android.service.notification.StatusBarNotification statusBarNotification) {
            this.mValue = statusBarNotification;
        }

        public android.service.notification.StatusBarNotification get() {
            android.service.notification.StatusBarNotification statusBarNotification = this.mValue;
            this.mValue = null;
            return statusBarNotification;
        }
    }

    private void writeSecureNotificationsPolicy(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        typedXmlSerializer.startTag((java.lang.String) null, LOCKSCREEN_ALLOW_SECURE_NOTIFICATIONS_TAG);
        typedXmlSerializer.attributeBoolean((java.lang.String) null, LOCKSCREEN_ALLOW_SECURE_NOTIFICATIONS_VALUE, this.mLockScreenAllowSecureNotifications);
        typedXmlSerializer.endTag((java.lang.String) null, LOCKSCREEN_ALLOW_SECURE_NOTIFICATIONS_TAG);
    }

    protected android.app.Notification createReviewPermissionsNotification() {
        android.content.Intent intent = new android.content.Intent("android.settings.ALL_APPS_NOTIFICATION_SETTINGS_FOR_REVIEW");
        android.content.Intent intent2 = new android.content.Intent(REVIEW_NOTIF_ACTION_REMIND);
        android.content.Intent intent3 = new android.content.Intent(REVIEW_NOTIF_ACTION_DISMISS);
        android.content.Intent intent4 = new android.content.Intent(REVIEW_NOTIF_ACTION_CANCELED);
        android.app.Notification.Action build = new android.app.Notification.Action.Builder((android.graphics.drawable.Icon) null, getContext().getResources().getString(android.R.string.relative_time), android.app.PendingIntent.getBroadcast(getContext(), 0, intent2, android.hardware.audio.common.V2_0.AudioFormat.DTS_HD)).build();
        return new android.app.Notification.Builder(getContext(), com.android.internal.notification.SystemNotificationChannels.SYSTEM_CHANGES).setSmallIcon(android.R.drawable.stat_notify_sync_anim0).setContentTitle(getContext().getResources().getString(android.R.string.report)).setContentText(getContext().getResources().getString(android.R.string.replace)).setContentIntent(android.app.PendingIntent.getActivity(getContext(), 0, intent, android.hardware.audio.common.V2_0.AudioFormat.DTS_HD)).setStyle(new android.app.Notification.BigTextStyle()).setFlag(32, true).setAutoCancel(true).addAction(build).addAction(new android.app.Notification.Action.Builder((android.graphics.drawable.Icon) null, getContext().getResources().getString(android.R.string.relationTypeSpouse), android.app.PendingIntent.getBroadcast(getContext(), 0, intent3, android.hardware.audio.common.V2_0.AudioFormat.DTS_HD)).build()).setDeleteIntent(android.app.PendingIntent.getBroadcast(getContext(), 0, intent4, android.hardware.audio.common.V2_0.AudioFormat.DTS_HD)).build();
    }

    protected void maybeShowInitialReviewPermissionsNotification() {
        if (!this.mShowReviewPermissionsNotification) {
            return;
        }
        int i = android.provider.Settings.Global.getInt(getContext().getContentResolver(), "review_permissions_notification_state", -1);
        if (i == 0 || i == 3) {
            ((android.app.NotificationManager) getContext().getSystemService(android.app.NotificationManager.class)).notify(TAG, 71, createReviewPermissionsNotification());
        }
    }

    private class NotificationTrampolineCallback implements com.android.server.wm.BackgroundActivityStartCallback {
        private NotificationTrampolineCallback() {
        }

        @Override // com.android.server.wm.BackgroundActivityStartCallback
        public boolean isActivityStartAllowed(java.util.Collection<android.os.IBinder> collection, int i, java.lang.String str) {
            com.android.internal.util.Preconditions.checkArgument(!collection.isEmpty());
            java.util.Iterator<android.os.IBinder> it = collection.iterator();
            while (it.hasNext()) {
                if (it.next() != com.android.server.notification.NotificationManagerService.ALLOWLIST_TOKEN) {
                    return true;
                }
            }
            java.lang.String str2 = "Indirect notification activity start (trampoline) from " + str;
            if (blockTrampoline(i)) {
                android.util.Slog.e(com.android.server.notification.NotificationManagerService.TAG, str2 + " blocked");
                return false;
            }
            android.util.Slog.w(com.android.server.notification.NotificationManagerService.TAG, str2 + ", this should be avoided for performance reasons");
            return true;
        }

        private boolean blockTrampoline(int i) {
            if (com.android.server.notification.NotificationManagerService.this.mRoleObserver != null && com.android.server.notification.NotificationManagerService.this.mRoleObserver.isUidExemptFromTrampolineRestrictions(i)) {
                return android.app.compat.CompatChanges.isChangeEnabled(com.android.server.notification.NotificationManagerService.NOTIFICATION_TRAMPOLINE_BLOCK_FOR_EXEMPT_ROLES, i);
            }
            return android.app.compat.CompatChanges.isChangeEnabled(com.android.server.notification.NotificationManagerService.NOTIFICATION_TRAMPOLINE_BLOCK, i);
        }

        @Override // com.android.server.wm.BackgroundActivityStartCallback
        public boolean canCloseSystemDialogs(java.util.Collection<android.os.IBinder> collection, int i) {
            return collection.contains(com.android.server.notification.NotificationManagerService.ALLOWLIST_TOKEN) && !android.app.compat.CompatChanges.isChangeEnabled(com.android.server.notification.NotificationManagerService.NOTIFICATION_TRAMPOLINE_BLOCK, i);
        }
    }

    interface PostNotificationTrackerFactory {
        default com.android.server.notification.NotificationManagerService.PostNotificationTracker newTracker(@android.annotation.Nullable android.os.PowerManager.WakeLock wakeLock) {
            return new com.android.server.notification.NotificationManagerService.PostNotificationTracker(wakeLock);
        }
    }

    static class PostNotificationTracker {

        @android.annotation.Nullable
        private final android.os.PowerManager.WakeLock mWakeLock;
        private final long mStartTime = android.os.SystemClock.elapsedRealtime();
        private boolean mOngoing = true;

        @com.android.internal.annotations.VisibleForTesting
        PostNotificationTracker(@android.annotation.Nullable android.os.PowerManager.WakeLock wakeLock) {
            this.mWakeLock = wakeLock;
            if (com.android.server.notification.NotificationManagerService.DBG) {
                android.util.Slog.d(com.android.server.notification.NotificationManagerService.TAG, "PostNotification: Started");
            }
        }

        long getStartTime() {
            return this.mStartTime;
        }

        @com.android.internal.annotations.VisibleForTesting
        boolean isOngoing() {
            return this.mOngoing;
        }

        void cancel() {
            if (!isOngoing()) {
                android.util.Log.wtfStack(com.android.server.notification.NotificationManagerService.TAG, "cancel() called on already-finished tracker");
                return;
            }
            this.mOngoing = false;
            if (this.mWakeLock != null) {
                android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.notification.NotificationManagerService$PostNotificationTracker$$ExternalSyntheticLambda0
                    public final void runOrThrow() {
                        com.android.server.notification.NotificationManagerService.PostNotificationTracker.this.lambda$cancel$0();
                    }
                });
            }
            if (com.android.server.notification.NotificationManagerService.DBG) {
                android.util.Slog.d(com.android.server.notification.NotificationManagerService.TAG, android.text.TextUtils.formatSimple("PostNotification: Abandoned after %d ms", new java.lang.Object[]{java.lang.Long.valueOf(android.os.SystemClock.elapsedRealtime() - this.mStartTime)}));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$cancel$0() throws java.lang.Exception {
            this.mWakeLock.release();
        }

        long finish() {
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime() - this.mStartTime;
            if (!isOngoing()) {
                android.util.Log.wtfStack(com.android.server.notification.NotificationManagerService.TAG, "finish() called on already-finished tracker");
                return elapsedRealtime;
            }
            this.mOngoing = false;
            if (this.mWakeLock != null) {
                android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.notification.NotificationManagerService$PostNotificationTracker$$ExternalSyntheticLambda1
                    public final void runOrThrow() {
                        com.android.server.notification.NotificationManagerService.PostNotificationTracker.this.lambda$finish$1();
                    }
                });
            }
            if (com.android.server.notification.NotificationManagerService.DBG) {
                android.util.Slog.d(com.android.server.notification.NotificationManagerService.TAG, android.text.TextUtils.formatSimple("PostNotification: Finished in %d ms", new java.lang.Object[]{java.lang.Long.valueOf(elapsedRealtime)}));
            }
            return elapsedRealtime;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$finish$1() throws java.lang.Exception {
            this.mWakeLock.release();
        }
    }
}
