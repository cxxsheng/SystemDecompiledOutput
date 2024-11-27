package com.android.server.net;

/* loaded from: classes2.dex */
public class NetworkPolicyManagerService extends android.net.INetworkPolicyManager.Stub {
    private static final java.lang.String ACTION_SNOOZE_RAPID = "com.android.server.net.action.SNOOZE_RAPID";
    private static final java.lang.String ACTION_SNOOZE_WARNING = "com.android.server.net.action.SNOOZE_WARNING";
    private static final int ALL_VALID_TRANSPORTS;
    private static final java.lang.String ATTR_APP_ID = "appId";

    @java.lang.Deprecated
    private static final java.lang.String ATTR_CYCLE_DAY = "cycleDay";
    private static final java.lang.String ATTR_CYCLE_END = "cycleEnd";
    private static final java.lang.String ATTR_CYCLE_PERIOD = "cyclePeriod";
    private static final java.lang.String ATTR_CYCLE_START = "cycleStart";

    @java.lang.Deprecated
    private static final java.lang.String ATTR_CYCLE_TIMEZONE = "cycleTimezone";
    private static final java.lang.String ATTR_INFERRED = "inferred";
    private static final java.lang.String ATTR_LAST_LIMIT_SNOOZE = "lastLimitSnooze";
    private static final java.lang.String ATTR_LAST_SNOOZE = "lastSnooze";
    private static final java.lang.String ATTR_LAST_WARNING_SNOOZE = "lastWarningSnooze";
    private static final java.lang.String ATTR_LIMIT_BEHAVIOR = "limitBehavior";
    private static final java.lang.String ATTR_LIMIT_BYTES = "limitBytes";
    private static final java.lang.String ATTR_LINEAGE_VERSION = "lineageVersion";
    private static final java.lang.String ATTR_METERED = "metered";
    private static final java.lang.String ATTR_NETWORK_ID = "networkId";
    private static final java.lang.String ATTR_NETWORK_TEMPLATE = "networkTemplate";
    private static final java.lang.String ATTR_NETWORK_TYPES = "networkTypes";
    private static final java.lang.String ATTR_OWNER_PACKAGE = "ownerPackage";
    private static final java.lang.String ATTR_POLICY = "policy";
    private static final java.lang.String ATTR_RESTRICT_BACKGROUND = "restrictBackground";
    private static final java.lang.String ATTR_SUBSCRIBER_ID = "subscriberId";
    private static final java.lang.String ATTR_SUBSCRIBER_ID_MATCH_RULE = "subscriberIdMatchRule";
    private static final java.lang.String ATTR_SUB_ID = "subId";
    private static final java.lang.String ATTR_SUMMARY = "summary";
    private static final java.lang.String ATTR_TEMPLATE_METERED = "templateMetered";
    private static final java.lang.String ATTR_TITLE = "title";
    private static final java.lang.String ATTR_UID = "uid";
    private static final java.lang.String ATTR_USAGE_BYTES = "usageBytes";
    private static final java.lang.String ATTR_USAGE_TIME = "usageTime";
    private static final java.lang.String ATTR_VERSION = "version";
    private static final java.lang.String ATTR_WARNING_BYTES = "warningBytes";
    private static final java.lang.String ATTR_XML_UTILS_NAME = "name";
    private static final int CHAIN_TOGGLE_DISABLE = 2;
    private static final int CHAIN_TOGGLE_ENABLE = 1;
    private static final int CHAIN_TOGGLE_NONE = 0;
    private static final int LINEAGE_VERSION_INIT = 1;
    private static final int LINEAGE_VERSION_LATEST = 2;
    private static final int LINEAGE_VERSION_REINSTATED_POLICY_REJECT_ALL = 2;
    private static final int MAX_TRANSPORT = 8;
    private static final int MIN_TRANSPORT = 0;
    private static final int MSG_ADVISE_PERSIST_THRESHOLD = 7;
    private static final int MSG_CLEAR_SUBSCRIPTION_PLANS = 22;
    private static final int MSG_LIMIT_REACHED = 5;
    private static final int MSG_METERED_IFACES_CHANGED = 2;
    private static final int MSG_METERED_RESTRICTED_PACKAGES_CHANGED = 17;
    private static final int MSG_POLICIES_CHANGED = 13;
    private static final int MSG_PROCESS_BACKGROUND_TRANSITIONING_UIDS = 24;
    private static final int MSG_REMOVE_INTERFACE_QUOTAS = 11;
    private static final int MSG_RESET_FIREWALL_RULES_BY_UID = 15;
    private static final int MSG_RESTRICT_BACKGROUND_CHANGED = 6;
    private static final int MSG_RULES_CHANGED = 1;
    private static final int MSG_SET_NETWORK_TEMPLATE_ENABLED = 18;
    private static final int MSG_STATS_PROVIDER_WARNING_OR_LIMIT_REACHED = 20;
    private static final int MSG_SUBSCRIPTION_OVERRIDE = 16;
    private static final int MSG_SUBSCRIPTION_PLANS_CHANGED = 19;
    private static final int MSG_UIDS_BLOCKED_REASONS_CHANGED = 23;
    private static final int MSG_UID_BLOCKED_REASON_CHANGED = 21;
    private static final int MSG_UPDATE_INTERFACE_QUOTAS = 10;
    public static final int OPPORTUNISTIC_QUOTA_UNKNOWN = -1;
    private static final java.lang.String PROP_SUB_PLAN_OWNER = "persist.sys.sub_plan_owner";
    private static final float QUOTA_FRAC_JOBS_DEFAULT = 0.5f;
    private static final float QUOTA_FRAC_MULTIPATH_DEFAULT = 0.5f;
    private static final float QUOTA_LIMITED_DEFAULT = 0.1f;
    static final java.lang.String TAG = "NetworkPolicy";
    private static final java.lang.String TAG_ALLOWLIST = "whitelist";
    private static final java.lang.String TAG_APP_POLICY = "app-policy";
    private static final java.lang.String TAG_NETWORK_POLICY = "network-policy";
    private static final java.lang.String TAG_POLICY_LIST = "policy-list";
    private static final java.lang.String TAG_RESTRICT_BACKGROUND = "restrict-background";
    private static final java.lang.String TAG_REVOKED_RESTRICT_BACKGROUND = "revoked-restrict-background";
    private static final java.lang.String TAG_UID_POLICY = "uid-policy";
    private static final java.lang.String TAG_XML_UTILS_INT_ARRAY = "int-array";

    @com.android.internal.annotations.VisibleForTesting
    public static final int TYPE_LIMIT = 35;

    @com.android.internal.annotations.VisibleForTesting
    public static final int TYPE_LIMIT_SNOOZED = 36;

    @com.android.internal.annotations.VisibleForTesting
    public static final int TYPE_RAPID = 45;

    @com.android.internal.annotations.VisibleForTesting
    public static final int TYPE_WARNING = 34;
    private static final java.lang.String UIDS_ALLOWED_ON_RESTRICTED_NETWORKS = "uids_allowed_on_restricted_networks";
    private static final int UID_MSG_GONE = 101;

    @com.android.internal.annotations.VisibleForTesting
    static final int UID_MSG_STATE_CHANGED = 100;
    private static final int VERSION_ADDED_CYCLE = 11;
    private static final int VERSION_ADDED_INFERRED = 7;
    private static final int VERSION_ADDED_METERED = 4;
    private static final int VERSION_ADDED_NETWORK_ID = 9;
    private static final int VERSION_ADDED_NETWORK_TYPES = 12;
    private static final int VERSION_ADDED_RESTRICT_BACKGROUND = 3;
    private static final int VERSION_ADDED_SNOOZE = 2;
    private static final int VERSION_ADDED_TIMEZONE = 6;
    private static final int VERSION_INIT = 1;
    private static final int VERSION_LATEST = 14;
    private static final int VERSION_REMOVED_SUBSCRIPTION_PLANS = 14;
    private static final int VERSION_SPLIT_SNOOZE = 5;
    private static final int VERSION_SUPPORTED_CARRIER_USAGE = 13;
    private static final int VERSION_SWITCH_APP_ID = 8;
    private static final int VERSION_SWITCH_UID = 10;
    private static final long WAIT_FOR_ADMIN_DATA_TIMEOUT_MS = 10000;
    private final com.android.server.net.NetworkPolicyManagerService.ActiveDataSubIdListener mActiveDataSubIdListener;

    @com.android.internal.annotations.GuardedBy({"mNetworkPoliciesSecondLock"})
    private final android.util.ArraySet<com.android.server.net.NetworkPolicyManagerService.NotificationId> mActiveNotifs;
    private final android.app.IActivityManager mActivityManager;
    private android.app.ActivityManagerInternal mActivityManagerInternal;
    private final java.util.concurrent.CountDownLatch mAdminDataAvailableLatch;
    private final android.net.INetworkManagementEventObserver mAlertObserver;

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    private final android.util.SparseBooleanArray mAppIdleTempWhitelistAppIds;
    private final android.app.AppOpsManager mAppOps;
    private com.android.server.usage.AppStandbyInternal mAppStandby;
    private boolean mBackgroundNetworkRestricted;

    @com.android.internal.annotations.VisibleForTesting
    long mBackgroundRestrictionDelayMs;

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    private final android.util.SparseLongArray mBackgroundTransitioningUids;
    private final android.telephony.CarrierConfigManager mCarrierConfigManager;
    private android.content.BroadcastReceiver mCarrierConfigReceiver;
    private final java.time.Clock mClock;
    private android.net.ConnectivityManager mConnManager;
    private android.content.BroadcastReceiver mConnReceiver;
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    private final android.util.SparseBooleanArray mDefaultRestrictBackgroundAllowlistUids;

    @android.annotation.NonNull
    private final com.android.server.net.NetworkPolicyManagerService.Dependencies mDeps;

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    volatile boolean mDeviceIdleMode;

    @com.android.internal.annotations.GuardedBy({"mDisallowedUidsDenylist"})
    final java.util.Set<java.lang.Integer> mDisallowedUidsDenylist;

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    final android.util.SparseBooleanArray mFirewallChainStates;
    final android.os.Handler mHandler;
    private final android.os.Handler.Callback mHandlerCallback;
    private final android.content.pm.IPackageManager mIPm;

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    private final android.util.SparseBooleanArray mInternetPermissionMap;
    private final android.os.RemoteCallbackList<android.net.INetworkPolicyListener> mListeners;
    private boolean mLoadedRestrictBackground;
    private final com.android.server.net.NetworkPolicyLogger mLogger;

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    volatile boolean mLowPowerStandbyActive;

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    private final android.util.SparseBooleanArray mLowPowerStandbyAllowlistUids;

    @com.android.internal.annotations.GuardedBy({"mNetworkPoliciesSecondLock"})
    private java.util.List<java.lang.String[]> mMergedSubscriberIds;

    @com.android.internal.annotations.GuardedBy({"mMeteredIfacesLock"})
    private android.util.ArraySet<java.lang.String> mMeteredIfaces;
    final java.lang.Object mMeteredIfacesLock;

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    private final android.util.SparseArray<java.util.Set<java.lang.Integer>> mMeteredRestrictedUids;
    private final com.android.server.connectivity.MultipathPolicyTracker mMultipathPolicyTracker;

    @com.android.internal.annotations.GuardedBy({"mNetworkPoliciesSecondLock"})
    private final android.util.SparseIntArray mNetIdToSubId;
    private final android.net.ConnectivityManager.NetworkCallback mNetworkCallback;
    private final android.os.INetworkManagementService mNetworkManager;
    private volatile boolean mNetworkManagerReady;

    @com.android.internal.annotations.GuardedBy({"mNetworkPoliciesSecondLock"})
    private final android.util.SparseBooleanArray mNetworkMetered;
    final java.lang.Object mNetworkPoliciesSecondLock;

    @com.android.internal.annotations.GuardedBy({"mNetworkPoliciesSecondLock"})
    final android.util.ArrayMap<android.net.NetworkTemplate, android.net.NetworkPolicy> mNetworkPolicy;

    @com.android.internal.annotations.GuardedBy({"mNetworkPoliciesSecondLock"})
    private final android.util.SparseBooleanArray mNetworkRoaming;
    private android.app.usage.NetworkStatsManager mNetworkStats;

    @com.android.internal.annotations.GuardedBy({"mNetworkPoliciesSecondLock"})
    private android.util.SparseSetArray<java.lang.String> mNetworkToIfaces;

    @com.android.internal.annotations.GuardedBy({"mNetworkPoliciesSecondLock"})
    private final android.util.ArraySet<android.net.NetworkTemplate> mOverLimitNotified;
    private final android.content.BroadcastReceiver mPackageReceiver;

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock", "mNetworkPoliciesSecondLock"})
    private final android.util.AtomicFile mPolicyFile;
    private android.os.PowerExemptionManager mPowerExemptionManager;
    private android.os.PowerManagerInternal mPowerManagerInternal;
    private final android.content.BroadcastReceiver mPowerSaveAllowlistReceiver;

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    private final android.util.SparseBooleanArray mPowerSaveTempWhitelistAppIds;

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    private final android.util.SparseBooleanArray mPowerSaveWhitelistAppIds;

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    private final android.util.SparseBooleanArray mPowerSaveWhitelistExceptIdleAppIds;

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    volatile boolean mRestrictBackground;

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    private final android.util.SparseBooleanArray mRestrictBackgroundAllowlistRevokedUids;
    private boolean mRestrictBackgroundBeforeBsm;

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    volatile boolean mRestrictBackgroundChangedInBsm;

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    private boolean mRestrictBackgroundLowPowerMode;

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    volatile boolean mRestrictPower;
    private com.android.server.net.NetworkPolicyManagerService.RestrictedModeObserver mRestrictedModeObserver;

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    volatile boolean mRestrictedNetworkingMode;

    @com.android.internal.annotations.GuardedBy({"mNetworkPoliciesSecondLock"})
    int mSetSubscriptionPlansIdCounter;

    @com.android.internal.annotations.GuardedBy({"mNetworkPoliciesSecondLock"})
    final android.util.SparseIntArray mSetSubscriptionPlansIds;
    private final android.content.BroadcastReceiver mSnoozeReceiver;
    public final com.android.internal.util.StatLogger mStatLogger;
    private final com.android.server.net.NetworkPolicyManagerService.StatsCallback mStatsCallback;

    @com.android.internal.annotations.GuardedBy({"mNetworkPoliciesSecondLock"})
    private final android.util.SparseArray<android.os.PersistableBundle> mSubIdToCarrierConfig;

    @com.android.internal.annotations.GuardedBy({"mNetworkPoliciesSecondLock"})
    private final android.util.SparseArray<java.lang.String> mSubIdToSubscriberId;

    @com.android.internal.annotations.GuardedBy({"mNetworkPoliciesSecondLock"})
    final android.util.SparseLongArray mSubscriptionOpportunisticQuota;

    @com.android.internal.annotations.GuardedBy({"mNetworkPoliciesSecondLock"})
    final android.util.SparseArray<android.telephony.SubscriptionPlan[]> mSubscriptionPlans;

    @com.android.internal.annotations.GuardedBy({"mNetworkPoliciesSecondLock"})
    final android.util.SparseArray<java.lang.String> mSubscriptionPlansOwner;
    private final boolean mSuppressDefaultPolicy;

    @com.android.internal.annotations.GuardedBy(anyOf = {"mUidRulesFirstLock", "mNetworkPoliciesSecondLock"})
    volatile boolean mSystemReady;

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    private final android.util.SparseArray<com.android.server.net.NetworkPolicyManagerService.UidBlockedState> mTmpUidBlockedState;

    @com.android.internal.annotations.GuardedBy({"mUidBlockedState"})
    private final android.util.SparseArray<com.android.server.net.NetworkPolicyManagerService.UidBlockedState> mUidBlockedState;

    @com.android.internal.annotations.VisibleForTesting
    final android.os.Handler mUidEventHandler;
    private final android.os.Handler.Callback mUidEventHandlerCallback;
    private final com.android.server.ServiceThread mUidEventThread;

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    final android.util.SparseIntArray mUidFirewallBackgroundRules;

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    final android.util.SparseIntArray mUidFirewallDozableRules;

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    final android.util.SparseIntArray mUidFirewallLowPowerStandbyModeRules;

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    final android.util.SparseIntArray mUidFirewallPowerSaveRules;

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    final android.util.SparseIntArray mUidFirewallRestrictedModeRules;

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    final android.util.SparseIntArray mUidFirewallStandbyRules;
    private final android.app.IUidObserver mUidObserver;

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    final android.util.SparseIntArray mUidPolicy;
    private final android.content.BroadcastReceiver mUidRemovedReceiver;
    final java.lang.Object mUidRulesFirstLock;

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    private final android.util.SparseArray<android.net.NetworkPolicyManager.UidState> mUidState;

    @com.android.internal.annotations.GuardedBy({"mUidStateCallbackInfos"})
    private final android.util.SparseArray<com.android.server.net.NetworkPolicyManagerService.UidStateCallbackInfo> mUidStateCallbackInfos;
    private android.app.usage.UsageStatsManagerInternal mUsageStats;
    private final android.os.UserManager mUserManager;
    private final android.content.BroadcastReceiver mUserReceiver;
    private final android.content.BroadcastReceiver mWifiReceiver;
    private static final boolean LOGD = com.android.server.net.NetworkPolicyLogger.LOGD;
    private static final boolean LOGV = com.android.server.net.NetworkPolicyLogger.LOGV;
    private static final long QUOTA_UNLIMITED_DEFAULT = android.util.DataUnit.MEBIBYTES.toBytes(20);

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ChainToggleType {
    }

    interface Stats {
        public static final int COUNT = 2;
        public static final int IS_UID_NETWORKING_BLOCKED = 1;
        public static final int UPDATE_NETWORK_ENABLED = 0;
    }

    static {
        int i = 0;
        for (int i2 = 0; i2 <= 8; i2++) {
            i |= 1 << i2;
        }
        ALL_VALID_TRANSPORTS = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class RestrictedModeObserver extends android.database.ContentObserver {
        private final android.content.Context mContext;
        private final com.android.server.net.NetworkPolicyManagerService.RestrictedModeObserver.RestrictedModeListener mListener;

        public interface RestrictedModeListener {
            void onChange(boolean z);
        }

        RestrictedModeObserver(android.content.Context context, com.android.server.net.NetworkPolicyManagerService.RestrictedModeObserver.RestrictedModeListener restrictedModeListener) {
            super(null);
            this.mContext = context;
            this.mListener = restrictedModeListener;
            this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Global.getUriFor("restricted_networking_mode"), false, this);
        }

        public boolean isRestrictedModeEnabled() {
            return android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "restricted_networking_mode", 0) != 0;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            this.mListener.onChange(isRestrictedModeEnabled());
        }
    }

    public NetworkPolicyManagerService(android.content.Context context, android.app.IActivityManager iActivityManager, android.os.INetworkManagementService iNetworkManagementService) {
        this(context, iActivityManager, iNetworkManagementService, android.app.AppGlobals.getPackageManager(), getDefaultClock(), getDefaultSystemDir(), false, new com.android.server.net.NetworkPolicyManagerService.Dependencies(context));
    }

    @android.annotation.NonNull
    private static java.io.File getDefaultSystemDir() {
        return new java.io.File(android.os.Environment.getDataDirectory(), "system");
    }

    @android.annotation.NonNull
    private static java.time.Clock getDefaultClock() {
        return new android.os.BestClock(java.time.ZoneOffset.UTC, new java.time.Clock[]{android.os.SystemClock.currentNetworkTimeClock(), java.time.Clock.systemUTC()});
    }

    @com.android.internal.annotations.VisibleForTesting
    android.net.NetworkPolicyManager.UidState getUidStateForTest(int i) {
        android.net.NetworkPolicyManager.UidState uidState;
        synchronized (this.mUidRulesFirstLock) {
            uidState = this.mUidState.get(i);
        }
        return uidState;
    }

    static class Dependencies {
        final android.content.Context mContext;
        final android.app.usage.NetworkStatsManager mNetworkStatsManager;

        Dependencies(android.content.Context context) {
            this.mContext = context;
            this.mNetworkStatsManager = (android.app.usage.NetworkStatsManager) this.mContext.getSystemService(android.app.usage.NetworkStatsManager.class);
            this.mNetworkStatsManager.setPollOnOpen(false);
        }

        long getNetworkTotalBytes(android.net.NetworkTemplate networkTemplate, long j, long j2) {
            android.os.Trace.traceBegin(2097152L, "getNetworkTotalBytes");
            try {
                try {
                    android.app.usage.NetworkStats.Bucket querySummaryForDevice = this.mNetworkStatsManager.querySummaryForDevice(networkTemplate, j, j2);
                    return querySummaryForDevice.getRxBytes() + querySummaryForDevice.getTxBytes();
                } catch (java.lang.RuntimeException e) {
                    android.util.Slog.w(com.android.server.net.NetworkPolicyManagerService.TAG, "Failed to read network stats: " + e);
                    android.os.Trace.traceEnd(2097152L);
                    return 0L;
                }
            } finally {
                android.os.Trace.traceEnd(2097152L);
            }
        }

        @android.annotation.NonNull
        java.util.List<android.app.usage.NetworkStats.Bucket> getNetworkUidBytes(@android.annotation.NonNull android.net.NetworkTemplate networkTemplate, long j, long j2) {
            android.os.Trace.traceBegin(2097152L, "getNetworkUidBytes");
            java.util.ArrayList arrayList = new java.util.ArrayList();
            try {
                try {
                    android.app.usage.NetworkStats querySummary = this.mNetworkStatsManager.querySummary(networkTemplate, j, j2);
                    while (querySummary.hasNextBucket()) {
                        android.app.usage.NetworkStats.Bucket bucket = new android.app.usage.NetworkStats.Bucket();
                        querySummary.getNextBucket(bucket);
                        arrayList.add(bucket);
                    }
                } catch (java.lang.RuntimeException e) {
                    android.util.Slog.w(com.android.server.net.NetworkPolicyManagerService.TAG, "Failed to read network stats: " + e);
                }
                return arrayList;
            } finally {
                android.os.Trace.traceEnd(2097152L);
            }
        }

        int getDefaultDataSubId() {
            return android.telephony.SubscriptionManager.getDefaultDataSubscriptionId();
        }

        int getActivateDataSubId() {
            return android.telephony.SubscriptionManager.getActiveDataSubscriptionId();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public NetworkPolicyManagerService(android.content.Context context, android.app.IActivityManager iActivityManager, android.os.INetworkManagementService iNetworkManagementService, android.content.pm.IPackageManager iPackageManager, java.time.Clock clock, java.io.File file, boolean z, com.android.server.net.NetworkPolicyManagerService.Dependencies dependencies) {
        this.mUidRulesFirstLock = new java.lang.Object();
        this.mNetworkPoliciesSecondLock = new java.lang.Object();
        this.mAdminDataAvailableLatch = new java.util.concurrent.CountDownLatch(1);
        this.mBackgroundRestrictionDelayMs = java.util.concurrent.TimeUnit.SECONDS.toMillis(5L);
        this.mNetworkPolicy = new android.util.ArrayMap<>();
        this.mSubscriptionPlans = new android.util.SparseArray<>();
        this.mSubscriptionPlansOwner = new android.util.SparseArray<>();
        this.mSetSubscriptionPlansIds = new android.util.SparseIntArray();
        this.mSetSubscriptionPlansIdCounter = 0;
        this.mSubscriptionOpportunisticQuota = new android.util.SparseLongArray();
        this.mUidPolicy = new android.util.SparseIntArray();
        this.mUidFirewallStandbyRules = new android.util.SparseIntArray();
        this.mUidFirewallDozableRules = new android.util.SparseIntArray();
        this.mUidFirewallPowerSaveRules = new android.util.SparseIntArray();
        this.mUidFirewallBackgroundRules = new android.util.SparseIntArray();
        this.mUidFirewallRestrictedModeRules = new android.util.SparseIntArray();
        this.mUidFirewallLowPowerStandbyModeRules = new android.util.SparseIntArray();
        this.mDisallowedUidsDenylist = new android.util.ArraySet();
        this.mFirewallChainStates = new android.util.SparseBooleanArray();
        this.mPowerSaveWhitelistExceptIdleAppIds = new android.util.SparseBooleanArray();
        this.mPowerSaveWhitelistAppIds = new android.util.SparseBooleanArray();
        this.mPowerSaveTempWhitelistAppIds = new android.util.SparseBooleanArray();
        this.mLowPowerStandbyAllowlistUids = new android.util.SparseBooleanArray();
        this.mAppIdleTempWhitelistAppIds = new android.util.SparseBooleanArray();
        this.mDefaultRestrictBackgroundAllowlistUids = new android.util.SparseBooleanArray();
        this.mRestrictBackgroundAllowlistRevokedUids = new android.util.SparseBooleanArray();
        this.mMeteredIfacesLock = new java.lang.Object();
        this.mMeteredIfaces = new android.util.ArraySet<>();
        this.mOverLimitNotified = new android.util.ArraySet<>();
        this.mActiveNotifs = new android.util.ArraySet<>();
        this.mUidState = new android.util.SparseArray<>();
        this.mUidBlockedState = new android.util.SparseArray<>();
        this.mTmpUidBlockedState = new android.util.SparseArray<>();
        this.mBackgroundTransitioningUids = new android.util.SparseLongArray();
        this.mNetworkMetered = new android.util.SparseBooleanArray();
        this.mNetworkRoaming = new android.util.SparseBooleanArray();
        this.mNetworkToIfaces = new android.util.SparseSetArray<>();
        this.mNetIdToSubId = new android.util.SparseIntArray();
        this.mSubIdToSubscriberId = new android.util.SparseArray<>();
        this.mMergedSubscriberIds = new java.util.ArrayList();
        this.mSubIdToCarrierConfig = new android.util.SparseArray<>();
        this.mMeteredRestrictedUids = new android.util.SparseArray<>();
        this.mListeners = new android.os.RemoteCallbackList<>();
        this.mLogger = new com.android.server.net.NetworkPolicyLogger();
        this.mInternetPermissionMap = new android.util.SparseBooleanArray();
        this.mUidStateCallbackInfos = new android.util.SparseArray<>();
        this.mStatLogger = new com.android.internal.util.StatLogger(new java.lang.String[]{"updateNetworkEnabledNL()", "isUidNetworkingBlocked()"});
        this.mUidObserver = new android.app.UidObserver() { // from class: com.android.server.net.NetworkPolicyManagerService.5
            @com.android.internal.annotations.GuardedBy({"mUidStateCallbackInfos"})
            private boolean isUidStateChangeRelevant(com.android.server.net.NetworkPolicyManagerService.UidStateCallbackInfo uidStateCallbackInfo, int i, long j, int i2) {
                if (uidStateCallbackInfo.procStateSeq == -1) {
                    return true;
                }
                if (j <= uidStateCallbackInfo.procStateSeq) {
                    return false;
                }
                int i3 = uidStateCallbackInfo.procState;
                if (com.android.server.net.NetworkPolicyManagerService.this.mBackgroundNetworkRestricted) {
                    if ((i3 >= 12) != (i >= 12)) {
                        return true;
                    }
                }
                if ((i3 <= 3) != (i <= 3)) {
                    return true;
                }
                return ((i3 <= 5) == (i <= 5) && (uidStateCallbackInfo.capability & 40) == (i2 & 40)) ? false : true;
            }

            public void onUidStateChanged(int i, int i2, long j, int i3) {
                com.android.server.net.NetworkPolicyManagerService.UidStateCallbackInfo uidStateCallbackInfo;
                synchronized (com.android.server.net.NetworkPolicyManagerService.this.mUidStateCallbackInfos) {
                    try {
                        com.android.server.net.NetworkPolicyManagerService.UidStateCallbackInfo uidStateCallbackInfo2 = (com.android.server.net.NetworkPolicyManagerService.UidStateCallbackInfo) com.android.server.net.NetworkPolicyManagerService.this.mUidStateCallbackInfos.get(i);
                        if (uidStateCallbackInfo2 != null) {
                            uidStateCallbackInfo = uidStateCallbackInfo2;
                        } else {
                            com.android.server.net.NetworkPolicyManagerService.UidStateCallbackInfo uidStateCallbackInfo3 = new com.android.server.net.NetworkPolicyManagerService.UidStateCallbackInfo();
                            com.android.server.net.NetworkPolicyManagerService.this.mUidStateCallbackInfos.put(i, uidStateCallbackInfo3);
                            uidStateCallbackInfo = uidStateCallbackInfo3;
                        }
                        if (isUidStateChangeRelevant(uidStateCallbackInfo, i2, j, i3)) {
                            uidStateCallbackInfo.update(i, i2, j, i3);
                            if (!uidStateCallbackInfo.isPending) {
                                com.android.server.net.NetworkPolicyManagerService.this.mUidEventHandler.obtainMessage(100, i, 0).sendToTarget();
                                uidStateCallbackInfo.isPending = true;
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }

            public void onUidGone(int i, boolean z2) {
                synchronized (com.android.server.net.NetworkPolicyManagerService.this.mUidStateCallbackInfos) {
                    com.android.server.net.NetworkPolicyManagerService.this.mUidStateCallbackInfos.remove(i);
                }
                com.android.server.net.NetworkPolicyManagerService.this.mUidEventHandler.obtainMessage(101, i, 0).sendToTarget();
            }
        };
        this.mPowerSaveAllowlistReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.net.NetworkPolicyManagerService.6
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                synchronized (com.android.server.net.NetworkPolicyManagerService.this.mUidRulesFirstLock) {
                    try {
                        com.android.server.net.NetworkPolicyManagerService.this.updatePowerSaveAllowlistUL();
                        if (com.android.server.net.NetworkPolicyManagerService.this.mBackgroundNetworkRestricted) {
                            com.android.server.net.NetworkPolicyManagerService.this.updateRulesForBackgroundChainUL();
                        }
                        com.android.server.net.NetworkPolicyManagerService.this.updateRulesForRestrictPowerUL();
                        com.android.server.net.NetworkPolicyManagerService.this.updateRulesForAppIdleUL();
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        };
        this.mPackageReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.net.NetworkPolicyManagerService.7
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                java.lang.String action = intent.getAction();
                int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                if (intExtra == -1) {
                    return;
                }
                if (intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                    if (com.android.server.net.NetworkPolicyManagerService.LOGV) {
                        android.util.Slog.v(com.android.server.net.NetworkPolicyManagerService.TAG, "ACTION_PACKAGE_ADDED Not new app, skip it uid=" + intExtra);
                        return;
                    }
                    return;
                }
                if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
                    synchronized (com.android.server.net.NetworkPolicyManagerService.this.mUidRulesFirstLock) {
                        try {
                            if (!com.android.server.net.NetworkPolicyManagerService.this.hasInternetPermissionUL(intExtra) && !com.android.server.net.NetworkPolicyManagerService.this.isSystemApp(intExtra)) {
                                android.util.Slog.i(com.android.server.net.NetworkPolicyManagerService.TAG, "ACTION_PACKAGE_ADDED for uid=" + intExtra + ", no INTERNET");
                                com.android.server.net.NetworkPolicyManagerService.this.addUidPolicy(intExtra, 262144);
                            }
                            com.android.server.net.NetworkPolicyManagerService.this.mInternetPermissionMap.delete(intExtra);
                            com.android.server.net.NetworkPolicyManagerService.this.updateRestrictionRulesForUidUL(intExtra);
                        } finally {
                        }
                    }
                }
            }
        };
        this.mUidRemovedReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.net.NetworkPolicyManagerService.8
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                if (intExtra == -1) {
                    return;
                }
                android.util.Slog.i(com.android.server.net.NetworkPolicyManagerService.TAG, "ACTION_UID_REMOVED for uid=" + intExtra);
                synchronized (com.android.server.net.NetworkPolicyManagerService.this.mUidRulesFirstLock) {
                    com.android.server.net.NetworkPolicyManagerService.this.onUidDeletedUL(intExtra);
                    synchronized (com.android.server.net.NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
                        com.android.server.net.NetworkPolicyManagerService.this.writePolicyAL();
                    }
                }
            }
        };
        this.mUserReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.net.NetworkPolicyManagerService.9
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                java.lang.String action = intent.getAction();
                char c = 65535;
                int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                if (intExtra == -1) {
                    return;
                }
                switch (action.hashCode()) {
                    case -2061058799:
                        if (action.equals("android.intent.action.USER_REMOVED")) {
                            c = 0;
                            break;
                        }
                        break;
                    case 1121780209:
                        if (action.equals("android.intent.action.USER_ADDED")) {
                            c = 1;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                    case 1:
                        synchronized (com.android.server.net.NetworkPolicyManagerService.this.mUidRulesFirstLock) {
                            try {
                                com.android.server.net.NetworkPolicyManagerService.this.removeUserStateUL(intExtra, true, false);
                                com.android.server.net.NetworkPolicyManagerService.this.mMeteredRestrictedUids.remove(intExtra);
                                if (action == "android.intent.action.USER_ADDED") {
                                    com.android.server.net.NetworkPolicyManagerService.this.addDefaultRestrictBackgroundAllowlistUidsUL(intExtra);
                                } else {
                                    synchronized (com.android.server.net.NetworkPolicyManagerService.this.mUidRulesFirstLock) {
                                        for (int i = 0; i < com.android.server.net.NetworkPolicyManagerService.this.mUidPolicy.size(); i++) {
                                            try {
                                                int keyAt = com.android.server.net.NetworkPolicyManagerService.this.mUidPolicy.keyAt(i);
                                                if (android.os.UserHandle.getUserId(keyAt) == intExtra) {
                                                    com.android.server.net.NetworkPolicyManagerService.this.mUidPolicy.delete(keyAt);
                                                }
                                            } finally {
                                            }
                                        }
                                    }
                                }
                                synchronized (com.android.server.net.NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
                                    com.android.server.net.NetworkPolicyManagerService.this.updateRulesForGlobalChangeAL(true);
                                }
                            } catch (java.lang.Throwable th) {
                                throw th;
                            }
                        }
                        return;
                    default:
                        return;
                }
            }
        };
        this.mStatsCallback = new com.android.server.net.NetworkPolicyManagerService.StatsCallback();
        this.mSnoozeReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.net.NetworkPolicyManagerService.10
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                android.net.NetworkTemplate networkTemplate = (android.net.NetworkTemplate) intent.getParcelableExtra("android.net.NETWORK_TEMPLATE", android.net.NetworkTemplate.class);
                if (com.android.server.net.NetworkPolicyManagerService.ACTION_SNOOZE_WARNING.equals(intent.getAction())) {
                    com.android.server.net.NetworkPolicyManagerService.this.performSnooze(networkTemplate, 34);
                } else if (com.android.server.net.NetworkPolicyManagerService.ACTION_SNOOZE_RAPID.equals(intent.getAction())) {
                    com.android.server.net.NetworkPolicyManagerService.this.performSnooze(networkTemplate, 45);
                }
            }
        };
        this.mWifiReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.net.NetworkPolicyManagerService.11
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                com.android.server.net.NetworkPolicyManagerService.this.upgradeWifiMeteredOverride();
                com.android.server.net.NetworkPolicyManagerService.this.mContext.unregisterReceiver(this);
            }
        };
        this.mNetworkCallback = new android.net.ConnectivityManager.NetworkCallback() { // from class: com.android.server.net.NetworkPolicyManagerService.12
            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onCapabilitiesChanged(@android.annotation.NonNull android.net.Network network, @android.annotation.NonNull android.net.NetworkCapabilities networkCapabilities) {
                synchronized (com.android.server.net.NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
                    try {
                        boolean z2 = !networkCapabilities.hasCapability(11);
                        boolean updateCapabilityChange = com.android.server.net.NetworkPolicyManagerService.updateCapabilityChange(com.android.server.net.NetworkPolicyManagerService.this.mNetworkMetered, z2, network);
                        boolean z3 = !networkCapabilities.hasCapability(18);
                        boolean updateCapabilityChange2 = com.android.server.net.NetworkPolicyManagerService.updateCapabilityChange(com.android.server.net.NetworkPolicyManagerService.this.mNetworkRoaming, z3, network);
                        boolean z4 = updateCapabilityChange || updateCapabilityChange2;
                        if (updateCapabilityChange) {
                            com.android.server.net.NetworkPolicyManagerService.this.mLogger.meterednessChanged(network.getNetId(), z2);
                        }
                        if (updateCapabilityChange2) {
                            com.android.server.net.NetworkPolicyManagerService.this.mLogger.roamingChanged(network.getNetId(), z3);
                        }
                        if (z4) {
                            com.android.server.net.NetworkPolicyManagerService.this.updateNetworkRulesNL();
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onLinkPropertiesChanged(@android.annotation.NonNull android.net.Network network, @android.annotation.NonNull android.net.LinkProperties linkProperties) {
                synchronized (com.android.server.net.NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
                    try {
                        android.util.ArraySet<java.lang.String> arraySet = new android.util.ArraySet<>(linkProperties.getAllInterfaceNames());
                        if (com.android.server.net.NetworkPolicyManagerService.this.updateNetworkToIfacesNL(network.getNetId(), arraySet)) {
                            com.android.server.net.NetworkPolicyManagerService.this.mLogger.interfacesChanged(network.getNetId(), arraySet);
                            com.android.server.net.NetworkPolicyManagerService.this.updateNetworkRulesNL();
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onLost(@android.annotation.NonNull android.net.Network network) {
                synchronized (com.android.server.net.NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
                    com.android.server.net.NetworkPolicyManagerService.this.mNetworkToIfaces.remove(network.getNetId());
                }
            }
        };
        this.mAlertObserver = new com.android.server.net.BaseNetworkObserver() { // from class: com.android.server.net.NetworkPolicyManagerService.13
            public void limitReached(java.lang.String str, java.lang.String str2) {
                android.net.NetworkStack.checkNetworkStackPermission(com.android.server.net.NetworkPolicyManagerService.this.mContext);
                if (!"globalAlert".equals(str)) {
                    com.android.server.net.NetworkPolicyManagerService.this.mHandler.obtainMessage(5, str2).sendToTarget();
                }
            }
        };
        this.mConnReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.net.NetworkPolicyManagerService.14
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                com.android.server.net.NetworkPolicyManagerService.this.updateNetworksInternal();
            }
        };
        this.mCarrierConfigReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.net.NetworkPolicyManagerService.15
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                if (!intent.hasExtra("android.telephony.extra.SUBSCRIPTION_INDEX")) {
                    return;
                }
                int intExtra = intent.getIntExtra("android.telephony.extra.SUBSCRIPTION_INDEX", -1);
                com.android.server.net.NetworkPolicyManagerService.this.updateSubscriptions();
                synchronized (com.android.server.net.NetworkPolicyManagerService.this.mUidRulesFirstLock) {
                    synchronized (com.android.server.net.NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
                        try {
                            java.lang.String str = (java.lang.String) com.android.server.net.NetworkPolicyManagerService.this.mSubIdToSubscriberId.get(intExtra, null);
                            if (str != null) {
                                com.android.server.net.NetworkPolicyManagerService.this.ensureActiveCarrierPolicyAL(intExtra, str);
                                com.android.server.net.NetworkPolicyManagerService.this.maybeUpdateCarrierPolicyCycleAL(intExtra, str);
                            } else {
                                android.util.Slog.wtf(com.android.server.net.NetworkPolicyManagerService.TAG, "Missing subscriberId for subId " + intExtra);
                            }
                            com.android.server.net.NetworkPolicyManagerService.this.handleNetworkPoliciesUpdateAL(true);
                        } catch (java.lang.Throwable th) {
                            throw th;
                        }
                    }
                }
            }
        };
        this.mHandlerCallback = new android.os.Handler.Callback() { // from class: com.android.server.net.NetworkPolicyManagerService.16
            @Override // android.os.Handler.Callback
            public boolean handleMessage(android.os.Message message) {
                long j;
                switch (message.what) {
                    case 1:
                        int i = message.arg1;
                        int i2 = message.arg2;
                        if (com.android.server.net.NetworkPolicyManagerService.LOGV) {
                            android.util.Slog.v(com.android.server.net.NetworkPolicyManagerService.TAG, "Dispatching rules=" + android.net.NetworkPolicyManager.uidRulesToString(i2) + " for uid=" + i);
                        }
                        int beginBroadcast = com.android.server.net.NetworkPolicyManagerService.this.mListeners.beginBroadcast();
                        for (int i3 = 0; i3 < beginBroadcast; i3++) {
                            com.android.server.net.NetworkPolicyManagerService.this.dispatchUidRulesChanged(com.android.server.net.NetworkPolicyManagerService.this.mListeners.getBroadcastItem(i3), i, i2);
                        }
                        com.android.server.net.NetworkPolicyManagerService.this.mListeners.finishBroadcast();
                        return true;
                    case 2:
                        java.lang.String[] strArr = (java.lang.String[]) message.obj;
                        int beginBroadcast2 = com.android.server.net.NetworkPolicyManagerService.this.mListeners.beginBroadcast();
                        for (int i4 = 0; i4 < beginBroadcast2; i4++) {
                            com.android.server.net.NetworkPolicyManagerService.this.dispatchMeteredIfacesChanged(com.android.server.net.NetworkPolicyManagerService.this.mListeners.getBroadcastItem(i4), strArr);
                        }
                        com.android.server.net.NetworkPolicyManagerService.this.mListeners.finishBroadcast();
                        return true;
                    case 3:
                    case 4:
                    case 8:
                    case 9:
                    case 12:
                    case 14:
                    default:
                        return false;
                    case 5:
                        java.lang.String str = (java.lang.String) message.obj;
                        synchronized (com.android.server.net.NetworkPolicyManagerService.this.mMeteredIfacesLock) {
                            try {
                                if (!com.android.server.net.NetworkPolicyManagerService.this.mMeteredIfaces.contains(str)) {
                                    return true;
                                }
                                com.android.server.net.NetworkPolicyManagerService.this.mNetworkStats.forceUpdate();
                                synchronized (com.android.server.net.NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
                                    com.android.server.net.NetworkPolicyManagerService.this.updateNetworkRulesNL();
                                    com.android.server.net.NetworkPolicyManagerService.this.updateNetworkEnabledNL();
                                    com.android.server.net.NetworkPolicyManagerService.this.updateNotificationsNL();
                                }
                                return true;
                            } finally {
                            }
                        }
                    case 6:
                        boolean z2 = message.arg1 != 0;
                        int beginBroadcast3 = com.android.server.net.NetworkPolicyManagerService.this.mListeners.beginBroadcast();
                        for (int i5 = 0; i5 < beginBroadcast3; i5++) {
                            com.android.server.net.NetworkPolicyManagerService.this.dispatchRestrictBackgroundChanged(com.android.server.net.NetworkPolicyManagerService.this.mListeners.getBroadcastItem(i5), z2);
                        }
                        com.android.server.net.NetworkPolicyManagerService.this.mListeners.finishBroadcast();
                        android.content.Intent intent = new android.content.Intent("android.net.conn.RESTRICT_BACKGROUND_CHANGED");
                        intent.setFlags(1073741824);
                        com.android.server.net.NetworkPolicyManagerService.this.mContext.sendBroadcastAsUser(intent, android.os.UserHandle.ALL);
                        return true;
                    case 7:
                        com.android.server.net.NetworkPolicyManagerService.this.mNetworkStats.setDefaultGlobalAlert(((java.lang.Long) message.obj).longValue() / 1000);
                        return true;
                    case 10:
                        com.android.server.net.NetworkPolicyManagerService.IfaceQuotas ifaceQuotas = (com.android.server.net.NetworkPolicyManagerService.IfaceQuotas) message.obj;
                        com.android.server.net.NetworkPolicyManagerService.this.removeInterfaceLimit(ifaceQuotas.iface);
                        com.android.server.net.NetworkPolicyManagerService.this.setInterfaceLimit(ifaceQuotas.iface, ifaceQuotas.limit);
                        com.android.server.net.NetworkPolicyManagerService.this.mNetworkStats.setStatsProviderWarningAndLimitAsync(ifaceQuotas.iface, ifaceQuotas.warning, ifaceQuotas.limit);
                        return true;
                    case 11:
                        java.lang.String str2 = (java.lang.String) message.obj;
                        com.android.server.net.NetworkPolicyManagerService.this.removeInterfaceLimit(str2);
                        com.android.server.net.NetworkPolicyManagerService.this.mNetworkStats.setStatsProviderWarningAndLimitAsync(str2, -1L, -1L);
                        return true;
                    case 13:
                        int i6 = message.arg1;
                        int i7 = message.arg2;
                        java.lang.Boolean bool = (java.lang.Boolean) message.obj;
                        int beginBroadcast4 = com.android.server.net.NetworkPolicyManagerService.this.mListeners.beginBroadcast();
                        int[] iArr = {i6};
                        long[] jArr = {com.android.server.net.NetworkPolicyManagerService.getAllowedTransportsPackedForUidPolicy(i7)};
                        for (int i8 = 0; i8 < beginBroadcast4; i8++) {
                            android.net.INetworkPolicyListener broadcastItem = com.android.server.net.NetworkPolicyManagerService.this.mListeners.getBroadcastItem(i8);
                            com.android.server.net.NetworkPolicyManagerService.this.dispatchUidPoliciesChanged(broadcastItem, i6, i7);
                            com.android.server.net.NetworkPolicyManagerService.this.dispatchUidsAllowedTransportsChanged(broadcastItem, iArr, jArr);
                        }
                        com.android.server.net.NetworkPolicyManagerService.this.mListeners.finishBroadcast();
                        if (bool.booleanValue()) {
                            com.android.server.net.NetworkPolicyManagerService.this.broadcastRestrictBackgroundChanged(i6, bool);
                        }
                        return true;
                    case 15:
                        com.android.server.net.NetworkPolicyManagerService.this.resetUidFirewallRules(message.arg1);
                        return true;
                    case 16:
                        com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
                        int intValue = ((java.lang.Integer) someArgs.arg1).intValue();
                        int intValue2 = ((java.lang.Integer) someArgs.arg2).intValue();
                        int intValue3 = ((java.lang.Integer) someArgs.arg3).intValue();
                        int[] iArr2 = (int[]) someArgs.arg4;
                        int beginBroadcast5 = com.android.server.net.NetworkPolicyManagerService.this.mListeners.beginBroadcast();
                        for (int i9 = 0; i9 < beginBroadcast5; i9++) {
                            com.android.server.net.NetworkPolicyManagerService.this.dispatchSubscriptionOverride(com.android.server.net.NetworkPolicyManagerService.this.mListeners.getBroadcastItem(i9), intValue, intValue2, intValue3, iArr2);
                        }
                        com.android.server.net.NetworkPolicyManagerService.this.mListeners.finishBroadcast();
                        return true;
                    case 17:
                        com.android.server.net.NetworkPolicyManagerService.this.setMeteredRestrictedPackagesInternal((java.util.Set) message.obj, message.arg1);
                        return true;
                    case 18:
                        com.android.server.net.NetworkPolicyManagerService.this.setNetworkTemplateEnabledInner((android.net.NetworkTemplate) message.obj, message.arg1 != 0);
                        return true;
                    case 19:
                        android.telephony.SubscriptionPlan[] subscriptionPlanArr = (android.telephony.SubscriptionPlan[]) message.obj;
                        int i10 = message.arg1;
                        int beginBroadcast6 = com.android.server.net.NetworkPolicyManagerService.this.mListeners.beginBroadcast();
                        for (int i11 = 0; i11 < beginBroadcast6; i11++) {
                            com.android.server.net.NetworkPolicyManagerService.this.dispatchSubscriptionPlansChanged(com.android.server.net.NetworkPolicyManagerService.this.mListeners.getBroadcastItem(i11), i10, subscriptionPlanArr);
                        }
                        com.android.server.net.NetworkPolicyManagerService.this.mListeners.finishBroadcast();
                        return true;
                    case 20:
                        com.android.server.net.NetworkPolicyManagerService.this.mNetworkStats.forceUpdate();
                        synchronized (com.android.server.net.NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
                            com.android.server.net.NetworkPolicyManagerService.this.updateNetworkRulesNL();
                            com.android.server.net.NetworkPolicyManagerService.this.updateNetworkEnabledNL();
                            com.android.server.net.NetworkPolicyManagerService.this.updateNotificationsNL();
                        }
                        return true;
                    case 21:
                        int i12 = message.arg1;
                        int i13 = message.arg2;
                        int intValue4 = ((java.lang.Integer) message.obj).intValue();
                        int beginBroadcast7 = com.android.server.net.NetworkPolicyManagerService.this.mListeners.beginBroadcast();
                        for (int i14 = 0; i14 < beginBroadcast7; i14++) {
                            com.android.server.net.NetworkPolicyManagerService.this.dispatchBlockedReasonChanged(com.android.server.net.NetworkPolicyManagerService.this.mListeners.getBroadcastItem(i14), i12, intValue4, i13);
                        }
                        com.android.server.net.NetworkPolicyManagerService.this.mListeners.finishBroadcast();
                        return true;
                    case 22:
                        synchronized (com.android.server.net.NetworkPolicyManagerService.this.mUidRulesFirstLock) {
                            synchronized (com.android.server.net.NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
                                try {
                                    int i15 = message.arg1;
                                    if (message.arg2 == com.android.server.net.NetworkPolicyManagerService.this.mSetSubscriptionPlansIds.get(i15)) {
                                        if (com.android.server.net.NetworkPolicyManagerService.LOGD) {
                                            android.util.Slog.d(com.android.server.net.NetworkPolicyManagerService.TAG, "Clearing expired subscription plans.");
                                        }
                                        com.android.server.net.NetworkPolicyManagerService.this.setSubscriptionPlansInternal(i15, new android.telephony.SubscriptionPlan[0], 0L, (java.lang.String) message.obj);
                                    } else if (com.android.server.net.NetworkPolicyManagerService.LOGD) {
                                        android.util.Slog.d(com.android.server.net.NetworkPolicyManagerService.TAG, "Ignoring stale CLEAR_SUBSCRIPTION_PLANS.");
                                    }
                                } finally {
                                }
                            }
                        }
                        return true;
                    case 23:
                        android.util.SparseArray sparseArray = (android.util.SparseArray) message.obj;
                        int size = sparseArray.size();
                        int beginBroadcast8 = com.android.server.net.NetworkPolicyManagerService.this.mListeners.beginBroadcast();
                        for (int i16 = 0; i16 < beginBroadcast8; i16++) {
                            android.net.INetworkPolicyListener broadcastItem2 = com.android.server.net.NetworkPolicyManagerService.this.mListeners.getBroadcastItem(i16);
                            for (int i17 = 0; i17 < size; i17++) {
                                int keyAt = sparseArray.keyAt(i17);
                                com.android.internal.os.SomeArgs someArgs2 = (com.android.internal.os.SomeArgs) sparseArray.valueAt(i17);
                                int i18 = someArgs2.argi1;
                                int i19 = someArgs2.argi2;
                                int i20 = someArgs2.argi3;
                                com.android.server.net.NetworkPolicyManagerService.this.dispatchBlockedReasonChanged(broadcastItem2, keyAt, i18, i19);
                                if (com.android.server.net.NetworkPolicyManagerService.LOGV) {
                                    android.util.Slog.v(com.android.server.net.NetworkPolicyManagerService.TAG, "Dispatching rules=" + android.net.NetworkPolicyManager.uidRulesToString(i20) + " for uid=" + keyAt);
                                }
                                com.android.server.net.NetworkPolicyManagerService.this.dispatchUidRulesChanged(broadcastItem2, keyAt, i20);
                            }
                        }
                        com.android.server.net.NetworkPolicyManagerService.this.mListeners.finishBroadcast();
                        for (int i21 = 0; i21 < size; i21++) {
                            ((com.android.internal.os.SomeArgs) sparseArray.valueAt(i21)).recycle();
                        }
                        return true;
                    case 24:
                        long uptimeMillis = android.os.SystemClock.uptimeMillis();
                        synchronized (com.android.server.net.NetworkPolicyManagerService.this.mUidRulesFirstLock) {
                            try {
                                j = Long.MAX_VALUE;
                                for (int size2 = com.android.server.net.NetworkPolicyManagerService.this.mBackgroundTransitioningUids.size() - 1; size2 >= 0; size2--) {
                                    long valueAt = com.android.server.net.NetworkPolicyManagerService.this.mBackgroundTransitioningUids.valueAt(size2);
                                    if (valueAt > uptimeMillis) {
                                        j = java.lang.Math.min(j, valueAt);
                                    } else {
                                        int keyAt2 = com.android.server.net.NetworkPolicyManagerService.this.mBackgroundTransitioningUids.keyAt(size2);
                                        com.android.server.net.NetworkPolicyManagerService.this.mBackgroundTransitioningUids.removeAt(size2);
                                        com.android.server.net.NetworkPolicyManagerService.this.updateRuleForBackgroundUL(keyAt2);
                                        com.android.server.net.NetworkPolicyManagerService.this.updateRulesForPowerRestrictionsUL(keyAt2, false);
                                    }
                                }
                            } finally {
                            }
                        }
                        if (j < com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
                            com.android.server.net.NetworkPolicyManagerService.this.mHandler.sendEmptyMessageAtTime(24, j);
                        }
                        return true;
                }
            }
        };
        this.mUidEventHandlerCallback = new android.os.Handler.Callback() { // from class: com.android.server.net.NetworkPolicyManagerService.17
            @Override // android.os.Handler.Callback
            public boolean handleMessage(android.os.Message message) {
                int i = message.arg1;
                switch (message.what) {
                    case 100:
                        com.android.server.net.NetworkPolicyManagerService.this.handleUidChanged(i);
                        break;
                    case 101:
                        com.android.server.net.NetworkPolicyManagerService.this.handleUidGone(i);
                        break;
                }
                return true;
            }
        };
        java.util.Objects.requireNonNull(context, "missing context");
        this.mContext = context;
        java.util.Objects.requireNonNull(iActivityManager, "missing activityManager");
        this.mActivityManager = iActivityManager;
        java.util.Objects.requireNonNull(iNetworkManagementService, "missing networkManagement");
        this.mNetworkManager = iNetworkManagementService;
        this.mPowerExemptionManager = (android.os.PowerExemptionManager) this.mContext.getSystemService(android.os.PowerExemptionManager.class);
        java.util.Objects.requireNonNull(clock, "missing Clock");
        this.mClock = clock;
        this.mUserManager = (android.os.UserManager) this.mContext.getSystemService("user");
        this.mCarrierConfigManager = (android.telephony.CarrierConfigManager) this.mContext.getSystemService(android.telephony.CarrierConfigManager.class);
        this.mIPm = iPackageManager;
        android.os.HandlerThread handlerThread = new android.os.HandlerThread(TAG);
        handlerThread.start();
        this.mHandler = new android.os.Handler(handlerThread.getLooper(), this.mHandlerCallback);
        this.mUidEventThread = new com.android.server.ServiceThread("NetworkPolicy.uid", -2, false);
        this.mUidEventThread.start();
        this.mUidEventHandler = new android.os.Handler(this.mUidEventThread.getLooper(), this.mUidEventHandlerCallback);
        this.mSuppressDefaultPolicy = z;
        java.util.Objects.requireNonNull(dependencies, "missing Dependencies");
        this.mDeps = dependencies;
        this.mActiveDataSubIdListener = new com.android.server.net.NetworkPolicyManagerService.ActiveDataSubIdListener();
        this.mPolicyFile = new android.util.AtomicFile(new java.io.File(file, "netpolicy.xml"), "net-policy");
        this.mAppOps = (android.app.AppOpsManager) context.getSystemService(android.app.AppOpsManager.class);
        this.mNetworkStats = (android.app.usage.NetworkStatsManager) context.getSystemService(android.app.usage.NetworkStatsManager.class);
        this.mMultipathPolicyTracker = new com.android.server.connectivity.MultipathPolicyTracker(this.mContext, this.mHandler);
        com.android.server.LocalServices.addService(com.android.server.net.NetworkPolicyManagerInternal.class, new com.android.server.net.NetworkPolicyManagerService.NetworkPolicyManagerInternalImpl());
        this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Global.getUriFor(UIDS_ALLOWED_ON_RESTRICTED_NETWORKS), false, new android.database.ContentObserver(this.mHandler) { // from class: com.android.server.net.NetworkPolicyManagerService.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z2) {
                synchronized (com.android.server.net.NetworkPolicyManagerService.this.mUidRulesFirstLock) {
                    com.android.server.net.NetworkPolicyManagerService.this.updateRestrictedModeAllowlistUL();
                }
            }
        });
    }

    public void bindConnectivityManager() {
        android.net.ConnectivityManager connectivityManager = (android.net.ConnectivityManager) this.mContext.getSystemService(android.net.ConnectivityManager.class);
        java.util.Objects.requireNonNull(connectivityManager, "missing ConnectivityManager");
        this.mConnManager = connectivityManager;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    public void updatePowerSaveAllowlistUL() {
        int[] allowListedAppIds = this.mPowerExemptionManager.getAllowListedAppIds(false);
        this.mPowerSaveWhitelistExceptIdleAppIds.clear();
        for (int i : allowListedAppIds) {
            this.mPowerSaveWhitelistExceptIdleAppIds.put(i, true);
        }
        int[] allowListedAppIds2 = this.mPowerExemptionManager.getAllowListedAppIds(true);
        this.mPowerSaveWhitelistAppIds.clear();
        for (int i2 : allowListedAppIds2) {
            this.mPowerSaveWhitelistAppIds.put(i2, true);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    boolean addDefaultRestrictBackgroundAllowlistUidsUL() {
        java.util.List users = this.mUserManager.getUsers();
        int size = users.size();
        boolean z = false;
        for (int i = 0; i < size; i++) {
            z = addDefaultRestrictBackgroundAllowlistUidsUL(((android.content.pm.UserInfo) users.get(i)).id) || z;
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    public boolean addDefaultRestrictBackgroundAllowlistUidsUL(int i) {
        com.android.server.SystemConfig systemConfig = com.android.server.SystemConfig.getInstance();
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        android.util.ArraySet<java.lang.String> allowInDataUsageSave = systemConfig.getAllowInDataUsageSave();
        boolean z = false;
        for (int i2 = 0; i2 < allowInDataUsageSave.size(); i2++) {
            java.lang.String valueAt = allowInDataUsageSave.valueAt(i2);
            if (LOGD) {
                android.util.Slog.d(TAG, "checking restricted background exemption for package " + valueAt + " and user " + i);
            }
            try {
                android.content.pm.ApplicationInfo applicationInfoAsUser = packageManager.getApplicationInfoAsUser(valueAt, 1048576, i);
                if (applicationInfoAsUser.isPrivilegedApp()) {
                    int uid = android.os.UserHandle.getUid(i, applicationInfoAsUser.uid);
                    this.mDefaultRestrictBackgroundAllowlistUids.append(uid, true);
                    if (LOGD) {
                        android.util.Slog.d(TAG, "Adding uid " + uid + " (user " + i + ") to default restricted background allowlist. Revoked status: " + this.mRestrictBackgroundAllowlistRevokedUids.get(uid));
                    }
                    if (!this.mRestrictBackgroundAllowlistRevokedUids.get(uid)) {
                        if (LOGD) {
                            android.util.Slog.d(TAG, "adding default package " + valueAt + " (uid " + uid + " for user " + i + ") to restrict background allowlist");
                        }
                        setUidPolicyUncheckedUL(uid, 4, false);
                        z = true;
                    }
                } else {
                    android.util.Slog.e(TAG, "addDefaultRestrictBackgroundAllowlistUidsUL(): skipping non-privileged app  " + valueAt);
                }
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                if (LOGD) {
                    android.util.Slog.d(TAG, "No ApplicationInfo for package " + valueAt);
                }
            }
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: initService, reason: merged with bridge method [inline-methods] */
    public void lambda$networkScoreAndNetworkManagementServiceReady$1(java.util.concurrent.CountDownLatch countDownLatch) {
        android.os.Trace.traceBegin(2097152L, "systemReady");
        int threadPriority = android.os.Process.getThreadPriority(android.os.Process.myTid());
        try {
            android.os.Process.setThreadPriority(-2);
            if (!isBandwidthControlEnabled()) {
                android.util.Slog.w(TAG, "bandwidth controls disabled, unable to enforce policy");
                return;
            }
            this.mUsageStats = (android.app.usage.UsageStatsManagerInternal) com.android.server.LocalServices.getService(android.app.usage.UsageStatsManagerInternal.class);
            this.mAppStandby = (com.android.server.usage.AppStandbyInternal) com.android.server.LocalServices.getService(com.android.server.usage.AppStandbyInternal.class);
            this.mActivityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
            synchronized (this.mUidRulesFirstLock) {
                synchronized (this.mNetworkPoliciesSecondLock) {
                    try {
                        updatePowerSaveAllowlistUL();
                        this.mPowerManagerInternal = (android.os.PowerManagerInternal) com.android.server.LocalServices.getService(android.os.PowerManagerInternal.class);
                        this.mPowerManagerInternal.registerLowPowerModeObserver(new android.os.PowerManagerInternal.LowPowerModeListener() { // from class: com.android.server.net.NetworkPolicyManagerService.2
                            public int getServiceType() {
                                return 6;
                            }

                            public void onLowPowerModeChanged(android.os.PowerSaveState powerSaveState) {
                                boolean z = powerSaveState.batterySaverEnabled;
                                if (com.android.server.net.NetworkPolicyManagerService.LOGD) {
                                    android.util.Slog.d(com.android.server.net.NetworkPolicyManagerService.TAG, "onLowPowerModeChanged(" + z + ")");
                                }
                                synchronized (com.android.server.net.NetworkPolicyManagerService.this.mUidRulesFirstLock) {
                                    try {
                                        if (com.android.server.net.NetworkPolicyManagerService.this.mRestrictPower != z) {
                                            com.android.server.net.NetworkPolicyManagerService.this.mRestrictPower = z;
                                            com.android.server.net.NetworkPolicyManagerService.this.updateRulesForRestrictPowerUL();
                                        }
                                    } catch (java.lang.Throwable th) {
                                        throw th;
                                    }
                                }
                            }
                        });
                        this.mRestrictPower = this.mPowerManagerInternal.getLowPowerState(6).batterySaverEnabled;
                        this.mRestrictedModeObserver = new com.android.server.net.NetworkPolicyManagerService.RestrictedModeObserver(this.mContext, new com.android.server.net.NetworkPolicyManagerService.RestrictedModeObserver.RestrictedModeListener() { // from class: com.android.server.net.NetworkPolicyManagerService$$ExternalSyntheticLambda2
                            @Override // com.android.server.net.NetworkPolicyManagerService.RestrictedModeObserver.RestrictedModeListener
                            public final void onChange(boolean z) {
                                com.android.server.net.NetworkPolicyManagerService.this.lambda$initService$0(z);
                            }
                        });
                        this.mRestrictedNetworkingMode = this.mRestrictedModeObserver.isRestrictedModeEnabled();
                        this.mSystemReady = true;
                        waitForAdminData();
                        readPolicyAL();
                        this.mRestrictBackgroundBeforeBsm = this.mLoadedRestrictBackground;
                        this.mRestrictBackgroundLowPowerMode = this.mPowerManagerInternal.getLowPowerState(10).batterySaverEnabled;
                        if (this.mRestrictBackgroundLowPowerMode && !this.mLoadedRestrictBackground) {
                            this.mLoadedRestrictBackground = true;
                        }
                        this.mPowerManagerInternal.registerLowPowerModeObserver(new android.os.PowerManagerInternal.LowPowerModeListener() { // from class: com.android.server.net.NetworkPolicyManagerService.3
                            public int getServiceType() {
                                return 10;
                            }

                            public void onLowPowerModeChanged(android.os.PowerSaveState powerSaveState) {
                                synchronized (com.android.server.net.NetworkPolicyManagerService.this.mUidRulesFirstLock) {
                                    com.android.server.net.NetworkPolicyManagerService.this.updateRestrictBackgroundByLowPowerModeUL(powerSaveState);
                                }
                            }
                        });
                        if (addDefaultRestrictBackgroundAllowlistUidsUL()) {
                            writePolicyAL();
                        }
                        com.android.server.net.Flags.networkBlockedForTopSleepingAndAbove();
                        this.mBackgroundNetworkRestricted = false;
                        if (this.mBackgroundNetworkRestricted) {
                            enableFirewallChainUL(6, true);
                        }
                        setRestrictBackgroundUL(this.mLoadedRestrictBackground, "init_service");
                        updateRulesForGlobalChangeAL(false);
                        updateNotificationsNL();
                    } finally {
                    }
                }
            }
            try {
                this.mActivityManagerInternal.registerNetworkPolicyUidObserver(this.mUidObserver, 35, this.mBackgroundNetworkRestricted ? -1 : 5, com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME);
                this.mNetworkManager.registerObserver(this.mAlertObserver);
            } catch (android.os.RemoteException e) {
            }
            this.mContext.registerReceiver(this.mPowerSaveAllowlistReceiver, new android.content.IntentFilter("android.os.action.POWER_SAVE_WHITELIST_CHANGED"), null, this.mHandler);
            this.mContext.registerReceiver(this.mConnReceiver, new android.content.IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"), "android.permission.NETWORK_STACK", this.mHandler);
            android.content.IntentFilter intentFilter = new android.content.IntentFilter();
            intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
            intentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
            this.mContext.registerReceiverForAllUsers(this.mPackageReceiver, intentFilter, null, this.mHandler);
            this.mContext.registerReceiverForAllUsers(this.mUidRemovedReceiver, new android.content.IntentFilter("android.intent.action.UID_REMOVED"), null, this.mHandler);
            android.content.IntentFilter intentFilter2 = new android.content.IntentFilter();
            intentFilter2.addAction("android.intent.action.USER_ADDED");
            intentFilter2.addAction("android.intent.action.USER_REMOVED");
            this.mContext.registerReceiver(this.mUserReceiver, intentFilter2, null, this.mHandler);
            java.util.concurrent.Executor handlerExecutor = new android.os.HandlerExecutor(this.mHandler);
            this.mNetworkStats.registerUsageCallback(new android.net.NetworkTemplate.Builder(1).build(), 0L, handlerExecutor, this.mStatsCallback);
            this.mNetworkStats.registerUsageCallback(new android.net.NetworkTemplate.Builder(4).build(), 0L, handlerExecutor, this.mStatsCallback);
            this.mContext.registerReceiver(this.mSnoozeReceiver, new android.content.IntentFilter(ACTION_SNOOZE_WARNING), "android.permission.MANAGE_NETWORK_POLICY", this.mHandler);
            this.mContext.registerReceiver(this.mSnoozeReceiver, new android.content.IntentFilter(ACTION_SNOOZE_RAPID), "android.permission.MANAGE_NETWORK_POLICY", this.mHandler);
            this.mContext.registerReceiver(this.mWifiReceiver, new android.content.IntentFilter("android.net.wifi.CONFIGURED_NETWORKS_CHANGE"), null, this.mHandler);
            this.mContext.registerReceiver(this.mCarrierConfigReceiver, new android.content.IntentFilter("android.telephony.action.CARRIER_CONFIG_CHANGED"), null, this.mHandler);
            this.mConnManager.registerNetworkCallback(new android.net.NetworkRequest.Builder().build(), this.mNetworkCallback);
            try {
                this.mConnManager.setRequireVpnForUids(true, getUidsWithLockdownPolicy());
            } catch (java.lang.RuntimeException e2) {
                android.util.Slog.wtf(TAG, "initService: setRequireVpnForUids failed", e2);
            }
            this.mAppStandby.addListener(new com.android.server.net.NetworkPolicyManagerService.NetPolicyAppIdleStateChangeListener());
            synchronized (this.mUidRulesFirstLock) {
                updateRulesForAppIdleParoleUL();
                sendUidsAllowedTransportsUL();
            }
            ((android.telephony.SubscriptionManager) this.mContext.getSystemService(android.telephony.SubscriptionManager.class)).addOnSubscriptionsChangedListener(new android.os.HandlerExecutor(this.mHandler), new android.telephony.SubscriptionManager.OnSubscriptionsChangedListener() { // from class: com.android.server.net.NetworkPolicyManagerService.4
                @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
                public void onSubscriptionsChanged() {
                    com.android.server.net.NetworkPolicyManagerService.this.updateNetworksInternal();
                }
            });
            ((android.telephony.TelephonyManager) this.mContext.getSystemService(android.telephony.TelephonyManager.class)).registerTelephonyCallback(handlerExecutor, this.mActiveDataSubIdListener);
            countDownLatch.countDown();
        } finally {
            android.os.Process.setThreadPriority(threadPriority);
            android.os.Trace.traceEnd(2097152L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initService$0(boolean z) {
        synchronized (this.mUidRulesFirstLock) {
            this.mRestrictedNetworkingMode = z;
            updateRestrictedModeAllowlistUL();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getAllowedTransportsPackedForUidPolicy(int i) {
        int i2 = ALL_VALID_TRANSPORTS;
        if ((i & 131072) == 131072) {
            i2 &= -17;
        }
        if ((i & 32768) == 32768) {
            i2 &= -3;
        }
        if ((i & 65536) == 65536) {
            return i2 & (-2);
        }
        return i2;
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    private void sendUidsAllowedTransportsUL() {
        int size = this.mUidPolicy.size();
        int[] iArr = new int[size];
        long[] jArr = new long[size];
        for (int i = 0; i < size; i++) {
            int keyAt = this.mUidPolicy.keyAt(i);
            int valueAt = this.mUidPolicy.valueAt(i);
            iArr[i] = keyAt;
            jArr[i] = getAllowedTransportsPackedForUidPolicy(valueAt);
        }
        dispatchUidsAllowedTransportsUL(iArr, jArr);
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    private void dispatchUidsAllowedTransportsUL(@android.annotation.NonNull int[] iArr, @android.annotation.NonNull long[] jArr) {
        int beginBroadcast = this.mListeners.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            dispatchUidsAllowedTransportsChanged(this.mListeners.getBroadcastItem(i), iArr, jArr);
        }
        this.mListeners.finishBroadcast();
    }

    public void notifyDenylistChanged(@android.annotation.NonNull int[] iArr, @android.annotation.NonNull int[] iArr2) {
        synchronized (this.mDisallowedUidsDenylist) {
            try {
                for (int i : iArr) {
                    this.mDisallowedUidsDenylist.add(java.lang.Integer.valueOf(i));
                }
                for (int i2 : iArr2) {
                    this.mDisallowedUidsDenylist.remove(java.lang.Integer.valueOf(i2));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public java.util.concurrent.CountDownLatch networkScoreAndNetworkManagementServiceReady() {
        this.mNetworkManagerReady = true;
        final java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.net.NetworkPolicyManagerService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.net.NetworkPolicyManagerService.this.lambda$networkScoreAndNetworkManagementServiceReady$1(countDownLatch);
            }
        });
        return countDownLatch;
    }

    public void systemReady(java.util.concurrent.CountDownLatch countDownLatch) {
        try {
            if (!countDownLatch.await(30L, java.util.concurrent.TimeUnit.SECONDS)) {
                throw new java.lang.IllegalStateException("Service NetworkPolicy init timeout");
            }
            this.mMultipathPolicyTracker.start();
        } catch (java.lang.InterruptedException e) {
            java.lang.Thread.currentThread().interrupt();
            throw new java.lang.IllegalStateException("Service NetworkPolicy init interrupted", e);
        }
    }

    private static final class UidStateCallbackInfo {
        public int capability;
        public boolean isPending;
        public int procState;
        public long procStateSeq;
        public int uid;

        private UidStateCallbackInfo() {
            this.procState = 20;
            this.procStateSeq = -1L;
        }

        public void update(int i, int i2, long j, int i3) {
            this.uid = i;
            this.procState = i2;
            this.procStateSeq = j;
            this.capability = i3;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("{");
            sb.append("uid=");
            sb.append(this.uid);
            sb.append(",");
            sb.append("proc_state=");
            sb.append(android.app.ActivityManager.procStateToString(this.procState));
            sb.append(",");
            sb.append("seq=");
            sb.append(this.procStateSeq);
            sb.append(",");
            sb.append("cap=");
            android.app.ActivityManager.printCapabilitiesSummary(sb, this.capability);
            sb.append(",");
            sb.append("pending=");
            sb.append(this.isPending);
            sb.append("}");
            return sb.toString();
        }
    }

    private class ActiveDataSubIdListener extends android.telephony.TelephonyCallback implements android.telephony.TelephonyCallback.ActiveDataSubscriptionIdListener {
        private int mActiveDataSubId;
        private int mDefaultDataSubId;

        private ActiveDataSubIdListener() {
            this.mDefaultDataSubId = com.android.server.net.NetworkPolicyManagerService.this.mDeps.getDefaultDataSubId();
            this.mActiveDataSubId = com.android.server.net.NetworkPolicyManagerService.this.mDeps.getActivateDataSubId();
        }

        @Override // android.telephony.TelephonyCallback.ActiveDataSubscriptionIdListener
        public void onActiveDataSubscriptionIdChanged(int i) {
            this.mActiveDataSubId = i;
            this.mDefaultDataSubId = com.android.server.net.NetworkPolicyManagerService.this.mDeps.getDefaultDataSubId();
            synchronized (com.android.server.net.NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
                com.android.server.net.NetworkPolicyManagerService.this.updateNotificationsNL();
            }
        }
    }

    private class StatsCallback extends android.app.usage.NetworkStatsManager.UsageCallback {
        private boolean mIsAnyCallbackReceived;

        private StatsCallback() {
            this.mIsAnyCallbackReceived = false;
        }

        @Override // android.app.usage.NetworkStatsManager.UsageCallback
        public void onThresholdReached(int i, java.lang.String str) {
            this.mIsAnyCallbackReceived = true;
            synchronized (com.android.server.net.NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
                com.android.server.net.NetworkPolicyManagerService.this.updateNetworkRulesNL();
                com.android.server.net.NetworkPolicyManagerService.this.updateNetworkEnabledNL();
                com.android.server.net.NetworkPolicyManagerService.this.updateNotificationsNL();
            }
        }

        public boolean isAnyCallbackReceived() {
            return this.mIsAnyCallbackReceived;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean updateCapabilityChange(android.util.SparseBooleanArray sparseBooleanArray, boolean z, android.net.Network network) {
        boolean z2 = sparseBooleanArray.get(network.getNetId(), false) != z || sparseBooleanArray.indexOfKey(network.getNetId()) < 0;
        if (z2) {
            sparseBooleanArray.put(network.getNetId(), z);
        }
        return z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mNetworkPoliciesSecondLock"})
    public boolean updateNetworkToIfacesNL(int i, @android.annotation.NonNull android.util.ArraySet<java.lang.String> arraySet) {
        android.util.ArraySet arraySet2 = this.mNetworkToIfaces.get(i);
        boolean z = true;
        if (arraySet2 != null && arraySet2.equals(arraySet)) {
            z = false;
        }
        if (z) {
            this.mNetworkToIfaces.remove(i);
            java.util.Iterator<java.lang.String> it = arraySet.iterator();
            while (it.hasNext()) {
                this.mNetworkToIfaces.add(i, it.next());
            }
        }
        return z;
    }

    @com.android.internal.annotations.GuardedBy({"mNetworkPoliciesSecondLock"})
    void updateNotificationsNL() {
        long j;
        if (LOGV) {
            android.util.Slog.v(TAG, "updateNotificationsNL()");
        }
        android.os.Trace.traceBegin(2097152L, "updateNotificationsNL");
        android.util.ArraySet arraySet = new android.util.ArraySet((android.util.ArraySet) this.mActiveNotifs);
        this.mActiveNotifs.clear();
        long millis = this.mClock.millis();
        for (int size = this.mNetworkPolicy.size() - 1; size >= 0; size--) {
            android.net.NetworkPolicy valueAt = this.mNetworkPolicy.valueAt(size);
            int findRelevantSubIdNL = findRelevantSubIdNL(valueAt.template);
            if (findRelevantSubIdNL != -1 && ((findRelevantSubIdNL == this.mActiveDataSubIdListener.mDefaultDataSubId || findRelevantSubIdNL == this.mActiveDataSubIdListener.mActiveDataSubId) && valueAt.hasCycle())) {
                android.util.Pair pair = (android.util.Pair) android.net.NetworkPolicyManager.cycleIterator(valueAt).next();
                long epochMilli = ((java.time.ZonedDateTime) pair.first).toInstant().toEpochMilli();
                long epochMilli2 = ((java.time.ZonedDateTime) pair.second).toInstant().toEpochMilli();
                long totalBytes = getTotalBytes(valueAt.template, epochMilli, epochMilli2);
                android.os.PersistableBundle persistableBundle = this.mSubIdToCarrierConfig.get(findRelevantSubIdNL);
                if (!android.telephony.CarrierConfigManager.isConfigForIdentifiedCarrier(persistableBundle)) {
                    if (LOGV) {
                        android.util.Slog.v(TAG, "isConfigForIdentifiedCarrier returned false");
                    }
                } else {
                    boolean booleanDefeatingNullable = getBooleanDefeatingNullable(persistableBundle, "data_warning_notification_bool", true);
                    boolean booleanDefeatingNullable2 = getBooleanDefeatingNullable(persistableBundle, "data_limit_notification_bool", true);
                    boolean booleanDefeatingNullable3 = getBooleanDefeatingNullable(persistableBundle, "data_rapid_notification_bool", true);
                    if (!booleanDefeatingNullable) {
                        j = totalBytes;
                    } else if (!valueAt.isOverWarning(totalBytes) || valueAt.isOverLimit(totalBytes)) {
                        j = totalBytes;
                    } else if (valueAt.lastWarningSnooze >= epochMilli) {
                        j = totalBytes;
                    } else {
                        j = totalBytes;
                        enqueueNotification(valueAt, 34, totalBytes, null);
                    }
                    if (booleanDefeatingNullable2) {
                        long j2 = j;
                        if (!valueAt.isOverLimit(j2)) {
                            notifyUnderLimitNL(valueAt.template);
                        } else if (valueAt.lastLimitSnooze >= epochMilli) {
                            enqueueNotification(valueAt, 36, j2, null);
                        } else {
                            enqueueNotification(valueAt, 35, j2, null);
                            notifyOverLimitNL(valueAt.template);
                        }
                    }
                    if (booleanDefeatingNullable3 && valueAt.limitBytes != -1) {
                        long millis2 = java.util.concurrent.TimeUnit.DAYS.toMillis(4L);
                        long j3 = millis - millis2;
                        long totalBytes2 = getTotalBytes(valueAt.template, j3, millis);
                        long j4 = ((epochMilli2 - epochMilli) * totalBytes2) / millis2;
                        long j5 = (valueAt.limitBytes * 3) / 2;
                        if (LOGD) {
                            android.util.Slog.d(TAG, "Rapid usage considering recent " + totalBytes2 + " projected " + j4 + " alert " + j5);
                        }
                        boolean z = valueAt.lastRapidSnooze >= millis - 86400000;
                        if (j4 > j5 && !z) {
                            enqueueNotification(valueAt, 45, 0L, findRapidBlame(valueAt.template, j3, millis));
                        }
                    }
                }
            }
        }
        for (int size2 = arraySet.size() - 1; size2 >= 0; size2--) {
            com.android.server.net.NetworkPolicyManagerService.NotificationId notificationId = (com.android.server.net.NetworkPolicyManagerService.NotificationId) arraySet.valueAt(size2);
            if (!this.mActiveNotifs.contains(notificationId)) {
                cancelNotification(notificationId);
            }
        }
        android.os.Trace.traceEnd(2097152L);
    }

    @android.annotation.Nullable
    private android.content.pm.ApplicationInfo findRapidBlame(android.net.NetworkTemplate networkTemplate, long j, long j2) {
        java.lang.String[] packagesForUid;
        if (!this.mStatsCallback.isAnyCallbackReceived()) {
            return null;
        }
        long j3 = 0;
        long j4 = 0;
        int i = 0;
        for (android.app.usage.NetworkStats.Bucket bucket : this.mDeps.getNetworkUidBytes(networkTemplate, j, j2)) {
            long rxBytes = bucket.getRxBytes() + bucket.getTxBytes();
            j4 += rxBytes;
            if (rxBytes > j3) {
                i = bucket.getUid();
                j3 = rxBytes;
            }
        }
        if (j3 > 0 && j3 > j4 / 2 && (packagesForUid = this.mContext.getPackageManager().getPackagesForUid(i)) != null && packagesForUid.length == 1) {
            try {
                return this.mContext.getPackageManager().getApplicationInfo(packagesForUid[0], 4989440);
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            }
        }
        return null;
    }

    @com.android.internal.annotations.GuardedBy({"mNetworkPoliciesSecondLock"})
    private int findRelevantSubIdNL(android.net.NetworkTemplate networkTemplate) {
        for (int i = 0; i < this.mSubIdToSubscriberId.size(); i++) {
            int keyAt = this.mSubIdToSubscriberId.keyAt(i);
            if (networkTemplate.matches(new android.net.NetworkIdentity.Builder().setType(0).setSubscriberId(this.mSubIdToSubscriberId.valueAt(i)).setMetered(true).setDefaultNetwork(true).setSubId(keyAt).build())) {
                return keyAt;
            }
        }
        return -1;
    }

    @com.android.internal.annotations.GuardedBy({"mNetworkPoliciesSecondLock"})
    private void notifyOverLimitNL(android.net.NetworkTemplate networkTemplate) {
        if (!this.mOverLimitNotified.contains(networkTemplate)) {
            this.mContext.startActivity(buildNetworkOverLimitIntent(this.mContext.getResources(), networkTemplate));
            this.mOverLimitNotified.add(networkTemplate);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mNetworkPoliciesSecondLock"})
    private void notifyUnderLimitNL(android.net.NetworkTemplate networkTemplate) {
        this.mOverLimitNotified.remove(networkTemplate);
    }

    private void enqueueNotification(android.net.NetworkPolicy networkPolicy, int i, long j, android.content.pm.ApplicationInfo applicationInfo) {
        java.lang.CharSequence text;
        java.lang.CharSequence string;
        com.android.server.net.NetworkPolicyManagerService.NotificationId notificationId = new com.android.server.net.NetworkPolicyManagerService.NotificationId(networkPolicy, i);
        android.app.Notification.Builder builder = new android.app.Notification.Builder(this.mContext, com.android.internal.notification.SystemNotificationChannels.NETWORK_ALERTS);
        builder.setOnlyAlertOnce(true);
        builder.setWhen(0L);
        builder.setColor(this.mContext.getColor(android.R.color.system_notification_accent_color));
        android.content.res.Resources resources = this.mContext.getResources();
        switch (i) {
            case 34:
                text = resources.getText(android.R.string.data_saver_description);
                string = resources.getString(android.R.string.csd_momentary_exposure_warning, android.text.format.Formatter.formatFileSize(this.mContext, j, 8));
                builder.setSmallIcon(android.R.drawable.stat_notify_error);
                builder.setDeleteIntent(android.app.PendingIntent.getBroadcast(this.mContext, 0, buildSnoozeWarningIntent(networkPolicy.template, this.mContext.getPackageName()), android.hardware.audio.common.V2_0.AudioFormat.DTS_HD));
                setContentIntent(builder, buildViewDataUsageIntent(resources, networkPolicy.template));
                break;
            case 35:
                switch (networkPolicy.template.getMatchRule()) {
                    case 1:
                    case 10:
                        text = resources.getText(android.R.string.country_detector);
                        break;
                    case 4:
                        text = resources.getText(android.R.string.data_saver_enable_title);
                        break;
                    default:
                        return;
                }
                string = resources.getText(android.R.string.conversation_single_line_name_display);
                builder.setOngoing(true);
                builder.setSmallIcon(android.R.drawable.spinner_white_76);
                setContentIntent(builder, buildNetworkOverLimitIntent(resources, networkPolicy.template));
                break;
            case 36:
                switch (networkPolicy.template.getMatchRule()) {
                    case 1:
                    case 10:
                        text = resources.getText(android.R.string.conversation_title_fallback_one_to_one);
                        break;
                    case 4:
                        text = resources.getText(android.R.string.data_saver_enable_button);
                        break;
                    default:
                        return;
                }
                string = resources.getString(android.R.string.conversation_title_fallback_group_chat, android.text.format.Formatter.formatFileSize(this.mContext, j - networkPolicy.limitBytes, 8));
                builder.setOngoing(true);
                builder.setSmallIcon(android.R.drawable.stat_notify_error);
                builder.setChannelId(com.android.internal.notification.SystemNotificationChannels.NETWORK_STATUS);
                setContentIntent(builder, buildViewDataUsageIntent(resources, networkPolicy.template));
                break;
            case 45:
                text = resources.getText(android.R.string.crossSimFormat_spn);
                if (applicationInfo != null) {
                    string = resources.getString(android.R.string.country_selection_title, applicationInfo.loadLabel(this.mContext.getPackageManager()));
                } else {
                    string = resources.getString(android.R.string.create_contact_using);
                }
                builder.setSmallIcon(android.R.drawable.stat_notify_error);
                builder.setDeleteIntent(android.app.PendingIntent.getBroadcast(this.mContext, 0, buildSnoozeRapidIntent(networkPolicy.template, this.mContext.getPackageName()), android.hardware.audio.common.V2_0.AudioFormat.DTS_HD));
                setContentIntent(builder, buildViewDataUsageIntent(resources, networkPolicy.template));
                break;
            default:
                return;
        }
        builder.setTicker(text);
        builder.setContentTitle(text);
        builder.setContentText(string);
        builder.setStyle(new android.app.Notification.BigTextStyle().bigText(string));
        ((android.app.NotificationManager) this.mContext.getSystemService(android.app.NotificationManager.class)).notifyAsUser(notificationId.getTag(), notificationId.getId(), builder.build(), android.os.UserHandle.ALL);
        this.mActiveNotifs.add(notificationId);
    }

    private void setContentIntent(android.app.Notification.Builder builder, android.content.Intent intent) {
        if (android.os.UserManager.isHeadlessSystemUserMode()) {
            builder.setContentIntent(android.app.PendingIntent.getActivityAsUser(this.mContext, 0, intent, android.hardware.audio.common.V2_0.AudioFormat.DTS_HD, null, android.os.UserHandle.CURRENT));
        } else {
            builder.setContentIntent(android.app.PendingIntent.getActivity(this.mContext, 0, intent, android.hardware.audio.common.V2_0.AudioFormat.DTS_HD));
        }
    }

    private void cancelNotification(com.android.server.net.NetworkPolicyManagerService.NotificationId notificationId) {
        ((android.app.NotificationManager) this.mContext.getSystemService(android.app.NotificationManager.class)).cancel(notificationId.getTag(), notificationId.getId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateNetworksInternal() {
        updateSubscriptions();
        synchronized (this.mUidRulesFirstLock) {
            synchronized (this.mNetworkPoliciesSecondLock) {
                ensureActiveCarrierPolicyAL();
                normalizePoliciesNL();
                updateNetworkEnabledNL();
                updateNetworkRulesNL();
                updateNotificationsNL();
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void updateNetworks() throws java.lang.InterruptedException {
        updateNetworksInternal();
        final java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.net.NetworkPolicyManagerService$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                countDownLatch.countDown();
            }
        });
        countDownLatch.await(5L, java.util.concurrent.TimeUnit.SECONDS);
    }

    @com.android.internal.annotations.VisibleForTesting
    android.os.Handler getHandlerForTesting() {
        return this.mHandler;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mNetworkPoliciesSecondLock"})
    public boolean maybeUpdateCarrierPolicyCycleAL(int i, java.lang.String str) {
        if (LOGV) {
            android.util.Slog.v(TAG, "maybeUpdateCarrierPolicyCycleAL()");
        }
        boolean z = false;
        android.net.NetworkIdentity build = new android.net.NetworkIdentity.Builder().setType(0).setSubscriberId(str).setMetered(true).setDefaultNetwork(true).setSubId(i).build();
        for (int size = this.mNetworkPolicy.size() - 1; size >= 0; size--) {
            if (this.mNetworkPolicy.keyAt(size).matches(build)) {
                z = updateDefaultCarrierPolicyAL(i, this.mNetworkPolicy.valueAt(size)) | z;
            }
        }
        return z;
    }

    @com.android.internal.annotations.VisibleForTesting
    int getCycleDayFromCarrierConfig(@android.annotation.Nullable android.os.PersistableBundle persistableBundle, int i) {
        if (persistableBundle == null) {
            return i;
        }
        int i2 = persistableBundle.getInt("monthly_data_cycle_day_int");
        if (i2 == -1) {
            return i;
        }
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        if (i2 < calendar.getMinimum(5) || i2 > calendar.getMaximum(5)) {
            android.util.Slog.e(TAG, "Invalid date in CarrierConfigManager.KEY_MONTHLY_DATA_CYCLE_DAY_INT: " + i2);
            return i;
        }
        return i2;
    }

    @com.android.internal.annotations.VisibleForTesting
    long getWarningBytesFromCarrierConfig(@android.annotation.Nullable android.os.PersistableBundle persistableBundle, long j) {
        if (persistableBundle == null) {
            return j;
        }
        long j2 = persistableBundle.getLong("data_warning_threshold_bytes_long");
        if (j2 == -2) {
            return -1L;
        }
        if (j2 == -1) {
            return getPlatformDefaultWarningBytes();
        }
        if (j2 < 0) {
            android.util.Slog.e(TAG, "Invalid value in CarrierConfigManager.KEY_DATA_WARNING_THRESHOLD_BYTES_LONG; expected a non-negative value but got: " + j2);
            return j;
        }
        return j2;
    }

    @com.android.internal.annotations.VisibleForTesting
    long getLimitBytesFromCarrierConfig(@android.annotation.Nullable android.os.PersistableBundle persistableBundle, long j) {
        if (persistableBundle == null) {
            return j;
        }
        long j2 = persistableBundle.getLong("data_limit_threshold_bytes_long");
        if (j2 == -2) {
            return -1L;
        }
        if (j2 == -1) {
            return getPlatformDefaultLimitBytes();
        }
        if (j2 < 0) {
            android.util.Slog.e(TAG, "Invalid value in CarrierConfigManager.KEY_DATA_LIMIT_THRESHOLD_BYTES_LONG; expected a non-negative value but got: " + j2);
            return j;
        }
        return j2;
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock", "mNetworkPoliciesSecondLock"})
    void handleNetworkPoliciesUpdateAL(boolean z) {
        if (z) {
            normalizePoliciesNL();
        }
        updateNetworkEnabledNL();
        updateNetworkRulesNL();
        updateNotificationsNL();
        writePolicyAL();
    }

    @com.android.internal.annotations.GuardedBy({"mNetworkPoliciesSecondLock"})
    void updateNetworkEnabledNL() {
        if (LOGV) {
            android.util.Slog.v(TAG, "updateNetworkEnabledNL()");
        }
        android.os.Trace.traceBegin(2097152L, "updateNetworkEnabledNL");
        long time = this.mStatLogger.getTime();
        int size = this.mNetworkPolicy.size() - 1;
        while (true) {
            boolean z = false;
            if (size >= 0) {
                android.net.NetworkPolicy valueAt = this.mNetworkPolicy.valueAt(size);
                if (valueAt.limitBytes == -1 || !valueAt.hasCycle()) {
                    setNetworkTemplateEnabled(valueAt.template, true);
                } else {
                    android.util.Pair pair = (android.util.Pair) android.net.NetworkPolicyManager.cycleIterator(valueAt).next();
                    long epochMilli = ((java.time.ZonedDateTime) pair.first).toInstant().toEpochMilli();
                    if (valueAt.isOverLimit(getTotalBytes(valueAt.template, epochMilli, ((java.time.ZonedDateTime) pair.second).toInstant().toEpochMilli())) && valueAt.lastLimitSnooze < epochMilli) {
                        z = true;
                    }
                    setNetworkTemplateEnabled(valueAt.template, !z);
                }
                size--;
            } else {
                this.mStatLogger.logDurationStat(0, time);
                android.os.Trace.traceEnd(2097152L);
                return;
            }
        }
    }

    private void setNetworkTemplateEnabled(android.net.NetworkTemplate networkTemplate, boolean z) {
        this.mHandler.obtainMessage(18, z ? 1 : 0, 0, networkTemplate).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setNetworkTemplateEnabledInner(android.net.NetworkTemplate networkTemplate, boolean z) {
        int i;
        if (networkTemplate.getMatchRule() == 1 || networkTemplate.getMatchRule() == 10) {
            android.util.IntArray intArray = new android.util.IntArray();
            synchronized (this.mNetworkPoliciesSecondLock) {
                for (int i2 = 0; i2 < this.mSubIdToSubscriberId.size(); i2++) {
                    try {
                        int keyAt = this.mSubIdToSubscriberId.keyAt(i2);
                        if (networkTemplate.matches(new android.net.NetworkIdentity.Builder().setType(0).setSubscriberId(this.mSubIdToSubscriberId.valueAt(i2)).setMetered(true).setDefaultNetwork(true).setSubId(keyAt).build())) {
                            intArray.add(keyAt);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
            android.telephony.TelephonyManager telephonyManager = (android.telephony.TelephonyManager) this.mContext.getSystemService(android.telephony.TelephonyManager.class);
            for (i = 0; i < intArray.size(); i++) {
                telephonyManager.createForSubscriptionId(intArray.get(i)).setPolicyDataEnabled(z);
            }
        }
    }

    private static void collectIfaces(android.util.ArraySet<java.lang.String> arraySet, android.net.NetworkStateSnapshot networkStateSnapshot) {
        arraySet.addAll(networkStateSnapshot.getLinkProperties().getAllInterfaceNames());
    }

    void updateSubscriptions() {
        if (LOGV) {
            android.util.Slog.v(TAG, "updateSubscriptions()");
        }
        android.os.Trace.traceBegin(2097152L, "updateSubscriptions");
        android.telephony.TelephonyManager telephonyManager = (android.telephony.TelephonyManager) this.mContext.getSystemService(android.telephony.TelephonyManager.class);
        java.util.List emptyIfNull = com.android.internal.util.CollectionUtils.emptyIfNull(((android.telephony.SubscriptionManager) this.mContext.getSystemService(android.telephony.SubscriptionManager.class)).getActiveSubscriptionInfoList());
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.util.SparseArray sparseArray = new android.util.SparseArray(emptyIfNull.size());
        android.util.SparseArray sparseArray2 = new android.util.SparseArray();
        java.util.Iterator it = emptyIfNull.iterator();
        while (it.hasNext()) {
            int subscriptionId = ((android.telephony.SubscriptionInfo) it.next()).getSubscriptionId();
            android.telephony.TelephonyManager createForSubscriptionId = telephonyManager.createForSubscriptionId(subscriptionId);
            java.lang.String subscriberId = createForSubscriptionId.getSubscriberId();
            if (!android.text.TextUtils.isEmpty(subscriberId)) {
                sparseArray.put(createForSubscriptionId.getSubscriptionId(), subscriberId);
            } else {
                android.util.Slog.wtf(TAG, "Missing subscriberId for subId " + createForSubscriptionId.getSubscriptionId());
            }
            arrayList.add(com.android.internal.util.ArrayUtils.defeatNullable(createForSubscriptionId.getMergedImsisFromGroup()));
            android.os.PersistableBundle configForSubId = this.mCarrierConfigManager.getConfigForSubId(subscriptionId);
            if (configForSubId != null) {
                sparseArray2.put(subscriptionId, configForSubId);
            } else {
                android.util.Slog.e(TAG, "Missing CarrierConfig for subId " + subscriptionId);
            }
        }
        synchronized (this.mNetworkPoliciesSecondLock) {
            try {
                this.mSubIdToSubscriberId.clear();
                for (int i = 0; i < sparseArray.size(); i++) {
                    this.mSubIdToSubscriberId.put(sparseArray.keyAt(i), (java.lang.String) sparseArray.valueAt(i));
                }
                this.mMergedSubscriberIds = arrayList;
                this.mSubIdToCarrierConfig.clear();
                for (int i2 = 0; i2 < sparseArray2.size(); i2++) {
                    this.mSubIdToCarrierConfig.put(sparseArray2.keyAt(i2), (android.os.PersistableBundle) sparseArray2.valueAt(i2));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        android.os.Trace.traceEnd(2097152L);
    }

    /* JADX WARN: Removed duplicated region for block: B:52:0x0177  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0187 A[LOOP:3: B:54:0x0185->B:55:0x0187, LOOP_END] */
    @com.android.internal.annotations.GuardedBy({"mNetworkPoliciesSecondLock"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void updateNetworkRulesNL() {
        int i;
        java.lang.String[] strArr;
        int subIdLocked;
        android.telephony.SubscriptionPlan primarySubscriptionPlanLocked;
        long max;
        int i2;
        android.net.NetworkPolicy networkPolicy;
        long j;
        long j2;
        long j3;
        int size;
        if (LOGV) {
            android.util.Slog.v(TAG, "updateNetworkRulesNL()");
        }
        android.os.Trace.traceBegin(2097152L, "updateNetworkRulesNL");
        java.util.List<android.net.NetworkStateSnapshot> allNetworkStateSnapshots = this.mConnManager.getAllNetworkStateSnapshots();
        this.mNetIdToSubId.clear();
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        java.util.Iterator it = allNetworkStateSnapshots.iterator();
        while (true) {
            i = 1;
            if (!it.hasNext()) {
                break;
            }
            android.net.NetworkStateSnapshot networkStateSnapshot = (android.net.NetworkStateSnapshot) it.next();
            this.mNetIdToSubId.put(networkStateSnapshot.getNetwork().getNetId(), networkStateSnapshot.getSubId());
            arrayMap.put(networkStateSnapshot, new android.net.NetworkIdentity.Builder().setNetworkStateSnapshot(networkStateSnapshot).setDefaultNetwork(true).build());
        }
        android.util.ArraySet<java.lang.String> arraySet = new android.util.ArraySet<>();
        android.util.ArraySet arraySet2 = new android.util.ArraySet();
        int size2 = this.mNetworkPolicy.size() - 1;
        long j4 = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
        while (true) {
            if (size2 < 0) {
                break;
            }
            android.net.NetworkPolicy valueAt = this.mNetworkPolicy.valueAt(size2);
            arraySet2.clear();
            for (int size3 = arrayMap.size() - i; size3 >= 0; size3--) {
                if (valueAt.template.matches((android.net.NetworkIdentity) arrayMap.valueAt(size3))) {
                    collectIfaces(arraySet2, (android.net.NetworkStateSnapshot) arrayMap.keyAt(size3));
                }
            }
            if (LOGD) {
                android.util.Slog.d(TAG, "Applying " + valueAt + " to ifaces " + arraySet2);
            }
            int i3 = valueAt.warningBytes != -1 ? i : 0;
            int i4 = valueAt.limitBytes != -1 ? i : 0;
            if (i4 == 0 && i3 == 0) {
                i2 = size2;
                networkPolicy = valueAt;
            } else if (!valueAt.hasCycle()) {
                i2 = size2;
                networkPolicy = valueAt;
            } else {
                android.util.Pair pair = (android.util.Pair) android.net.NetworkPolicyManager.cycleIterator(valueAt).next();
                long epochMilli = ((java.time.ZonedDateTime) pair.first).toInstant().toEpochMilli();
                i2 = size2;
                networkPolicy = valueAt;
                long totalBytes = getTotalBytes(valueAt.template, epochMilli, ((java.time.ZonedDateTime) pair.second).toInstant().toEpochMilli());
                if (i4 != 0 && networkPolicy.lastLimitSnooze < epochMilli) {
                    j = java.lang.Math.max(1L, networkPolicy.limitBytes - totalBytes);
                } else {
                    j = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
                }
                if (i3 != 0 && networkPolicy.lastWarningSnooze < epochMilli && !networkPolicy.isOverWarning(totalBytes)) {
                    j3 = java.lang.Math.max(1L, networkPolicy.warningBytes - totalBytes);
                    j2 = j;
                } else {
                    j2 = j;
                    j3 = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
                }
                if (i3 == 0 || i4 != 0 || networkPolicy.metered) {
                    if (arraySet2.size() > i) {
                        android.util.Slog.w(TAG, "shared quota unsupported; generating rule for each iface");
                    }
                    for (size = arraySet2.size() - i; size >= 0; size--) {
                        java.lang.String str = (java.lang.String) arraySet2.valueAt(size);
                        setInterfaceQuotasAsync(str, j3, j2);
                        arraySet.add(str);
                    }
                }
                if (i3 != 0 && networkPolicy.warningBytes < j4) {
                    j4 = networkPolicy.warningBytes;
                }
                if (i4 != 0 && networkPolicy.limitBytes < j4) {
                    j4 = networkPolicy.limitBytes;
                }
                size2 = i2 - 1;
                i = 1;
            }
            j3 = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
            j2 = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
            if (i3 == 0) {
            }
            if (arraySet2.size() > i) {
            }
            while (size >= 0) {
            }
            if (i3 != 0) {
                j4 = networkPolicy.warningBytes;
            }
            if (i4 != 0) {
                j4 = networkPolicy.limitBytes;
            }
            size2 = i2 - 1;
            i = 1;
        }
        for (android.net.NetworkStateSnapshot networkStateSnapshot2 : allNetworkStateSnapshots) {
            if (!networkStateSnapshot2.getNetworkCapabilities().hasCapability(11)) {
                arraySet2.clear();
                collectIfaces(arraySet2, networkStateSnapshot2);
                for (int size4 = arraySet2.size() - 1; size4 >= 0; size4--) {
                    java.lang.String str2 = (java.lang.String) arraySet2.valueAt(size4);
                    if (!arraySet.contains(str2)) {
                        setInterfaceQuotasAsync(str2, com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME, com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME);
                        arraySet.add(str2);
                    }
                }
            }
        }
        synchronized (this.mMeteredIfacesLock) {
            try {
                for (int size5 = this.mMeteredIfaces.size() - 1; size5 >= 0; size5--) {
                    java.lang.String valueAt2 = this.mMeteredIfaces.valueAt(size5);
                    if (!arraySet.contains(valueAt2)) {
                        removeInterfaceQuotasAsync(valueAt2);
                    }
                }
                this.mMeteredIfaces = arraySet;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
        boolean z = android.provider.Settings.Global.getInt(contentResolver, "netpolicy_quota_enabled", 1) != 0;
        long j5 = android.provider.Settings.Global.getLong(contentResolver, "netpolicy_quota_unlimited", QUOTA_UNLIMITED_DEFAULT);
        float f = android.provider.Settings.Global.getFloat(contentResolver, "netpolicy_quota_limited", QUOTA_LIMITED_DEFAULT);
        this.mSubscriptionOpportunisticQuota.clear();
        for (android.net.NetworkStateSnapshot networkStateSnapshot3 : allNetworkStateSnapshots) {
            if (z && networkStateSnapshot3.getNetwork() != null && (subIdLocked = getSubIdLocked(networkStateSnapshot3.getNetwork())) != -1 && (primarySubscriptionPlanLocked = getPrimarySubscriptionPlanLocked(subIdLocked)) != null) {
                long dataLimitBytes = primarySubscriptionPlanLocked.getDataLimitBytes();
                if (!networkStateSnapshot3.getNetworkCapabilities().hasCapability(18)) {
                    max = 0;
                } else if (dataLimitBytes == -1) {
                    max = -1;
                } else if (dataLimitBytes == com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
                    max = j5;
                } else {
                    android.util.Range<java.time.ZonedDateTime> next = primarySubscriptionPlanLocked.cycleIterator().next();
                    long epochMilli2 = next.getLower().toInstant().toEpochMilli();
                    long epochMilli3 = next.getUpper().toInstant().toEpochMilli();
                    long epochMilli4 = java.time.ZonedDateTime.ofInstant(this.mClock.instant(), next.getLower().getZone()).truncatedTo(java.time.temporal.ChronoUnit.DAYS).toInstant().toEpochMilli();
                    max = java.lang.Math.max(0L, (long) (((dataLimitBytes - (networkStateSnapshot3.getSubscriberId() == null ? 0L : getTotalBytes(buildTemplateCarrierMetered(r1), epochMilli2, epochMilli4))) / ((((epochMilli3 - r15.toEpochMilli()) - 1) / java.util.concurrent.TimeUnit.DAYS.toMillis(1L)) + 1)) * f));
                }
                this.mSubscriptionOpportunisticQuota.put(subIdLocked, max);
            }
        }
        synchronized (this.mMeteredIfacesLock) {
            strArr = (java.lang.String[]) this.mMeteredIfaces.toArray(new java.lang.String[this.mMeteredIfaces.size()]);
        }
        this.mHandler.obtainMessage(2, strArr).sendToTarget();
        this.mHandler.obtainMessage(7, java.lang.Long.valueOf(j4)).sendToTarget();
        android.os.Trace.traceEnd(2097152L);
    }

    @com.android.internal.annotations.GuardedBy({"mNetworkPoliciesSecondLock"})
    private void ensureActiveCarrierPolicyAL() {
        if (LOGV) {
            android.util.Slog.v(TAG, "ensureActiveCarrierPolicyAL()");
        }
        if (this.mSuppressDefaultPolicy) {
            return;
        }
        for (int i = 0; i < this.mSubIdToSubscriberId.size(); i++) {
            ensureActiveCarrierPolicyAL(this.mSubIdToSubscriberId.keyAt(i), this.mSubIdToSubscriberId.valueAt(i));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mNetworkPoliciesSecondLock"})
    public boolean ensureActiveCarrierPolicyAL(int i, java.lang.String str) {
        android.net.NetworkIdentity build = new android.net.NetworkIdentity.Builder().setType(0).setSubscriberId(str).setMetered(true).setDefaultNetwork(true).setSubId(i).build();
        for (int size = this.mNetworkPolicy.size() - 1; size >= 0; size--) {
            android.net.NetworkTemplate keyAt = this.mNetworkPolicy.keyAt(size);
            if (keyAt.matches(build)) {
                if (LOGD) {
                    android.util.Slog.d(TAG, "Found template " + keyAt + " which matches subscriber " + com.android.net.module.util.NetworkIdentityUtils.scrubSubscriberId(str));
                }
                return false;
            }
        }
        android.util.Slog.i(TAG, "No policy for subscriber " + com.android.net.module.util.NetworkIdentityUtils.scrubSubscriberId(str) + "; generating default policy");
        addNetworkPolicyAL(buildDefaultCarrierPolicy(i, str));
        return true;
    }

    private long getPlatformDefaultWarningBytes() {
        long integer = this.mContext.getResources().getInteger(android.R.integer.config_networkDefaultDailyMultipathQuotaBytes);
        if (integer == -1) {
            return -1L;
        }
        return android.util.DataUnit.MEBIBYTES.toBytes(integer);
    }

    private long getPlatformDefaultLimitBytes() {
        return -1L;
    }

    @com.android.internal.annotations.VisibleForTesting
    android.net.NetworkPolicy buildDefaultCarrierPolicy(int i, java.lang.String str) {
        android.net.NetworkPolicy networkPolicy = new android.net.NetworkPolicy(buildTemplateCarrierMetered(str), android.net.NetworkPolicy.buildRule(java.time.ZonedDateTime.now().getDayOfMonth(), java.time.ZoneId.systemDefault()), getPlatformDefaultWarningBytes(), getPlatformDefaultLimitBytes(), -1L, -1L, true, true);
        synchronized (this.mUidRulesFirstLock) {
            synchronized (this.mNetworkPoliciesSecondLock) {
                updateDefaultCarrierPolicyAL(i, networkPolicy);
            }
        }
        return networkPolicy;
    }

    public static android.net.NetworkTemplate buildTemplateCarrierMetered(@android.annotation.NonNull java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        return new android.net.NetworkTemplate.Builder(10).setSubscriberIds(java.util.Set.of(str)).setMeteredness(1).build();
    }

    @com.android.internal.annotations.GuardedBy({"mNetworkPoliciesSecondLock"})
    private boolean updateDefaultCarrierPolicyAL(int i, android.net.NetworkPolicy networkPolicy) {
        int i2;
        if (!networkPolicy.inferred) {
            if (LOGD) {
                android.util.Slog.d(TAG, "Ignoring user-defined policy " + networkPolicy);
            }
            return false;
        }
        android.net.NetworkPolicy networkPolicy2 = new android.net.NetworkPolicy(networkPolicy.template, networkPolicy.cycleRule, networkPolicy.warningBytes, networkPolicy.limitBytes, networkPolicy.lastWarningSnooze, networkPolicy.lastLimitSnooze, networkPolicy.metered, networkPolicy.inferred);
        android.telephony.SubscriptionPlan[] subscriptionPlanArr = this.mSubscriptionPlans.get(i);
        if (!com.android.internal.util.ArrayUtils.isEmpty(subscriptionPlanArr)) {
            android.telephony.SubscriptionPlan subscriptionPlan = subscriptionPlanArr[0];
            networkPolicy.cycleRule = subscriptionPlan.getCycleRule();
            long dataLimitBytes = subscriptionPlan.getDataLimitBytes();
            if (dataLimitBytes != -1) {
                if (dataLimitBytes == com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
                    networkPolicy.warningBytes = -1L;
                    networkPolicy.limitBytes = -1L;
                } else {
                    networkPolicy.warningBytes = (9 * dataLimitBytes) / 10;
                    switch (subscriptionPlan.getDataLimitBehavior()) {
                        case 0:
                        case 1:
                            networkPolicy.limitBytes = dataLimitBytes;
                            break;
                        default:
                            networkPolicy.limitBytes = -1L;
                            break;
                    }
                }
            } else {
                networkPolicy.warningBytes = getPlatformDefaultWarningBytes();
                networkPolicy.limitBytes = getPlatformDefaultLimitBytes();
            }
        } else {
            android.os.PersistableBundle persistableBundle = this.mSubIdToCarrierConfig.get(i);
            if (networkPolicy.cycleRule.isMonthly()) {
                i2 = networkPolicy.cycleRule.start.getDayOfMonth();
            } else {
                i2 = -1;
            }
            networkPolicy.cycleRule = android.net.NetworkPolicy.buildRule(getCycleDayFromCarrierConfig(persistableBundle, i2), java.time.ZoneId.systemDefault());
            networkPolicy.warningBytes = getWarningBytesFromCarrierConfig(persistableBundle, networkPolicy.warningBytes);
            networkPolicy.limitBytes = getLimitBytesFromCarrierConfig(persistableBundle, networkPolicy.limitBytes);
        }
        if (networkPolicy.equals(networkPolicy2)) {
            return false;
        }
        android.util.Slog.d(TAG, "Updated " + networkPolicy2 + " to " + networkPolicy);
        return true;
    }

    private void migrateToPolicyRejectAll() {
        java.util.Set uidsAllowedOnRestrictedNetworks = android.net.ConnectivitySettingsManager.getUidsAllowedOnRestrictedNetworks(this.mContext);
        android.util.ArraySet arraySet = new android.util.ArraySet();
        for (android.content.pm.UserInfo userInfo : android.os.UserManager.get(this.mContext).getUsers()) {
            try {
                for (android.content.pm.ApplicationInfo applicationInfo : this.mIPm.getInstalledApplications(8192L, userInfo.id).getList()) {
                    int i = applicationInfo.uid;
                    if (!(uidsAllowedOnRestrictedNetworks.contains(java.lang.Integer.valueOf(i)) || hasRestrictedModeAccess(i))) {
                        synchronized (this.mUidRulesFirstLock) {
                            try {
                                if (!applicationInfo.isSystemApp() || hasInternetPermissionUL(i)) {
                                    if ((applicationInfo.privateFlags & 1) == 1 || (applicationInfo.flags & 8388608) == 8388608) {
                                        arraySet.add(java.lang.Integer.valueOf(i));
                                    }
                                }
                            } finally {
                            }
                        }
                    }
                }
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "migrateToPolicyRejectAll: Failed to retrieve package UIDs for user " + userInfo.id, e);
            }
        }
        setPolicyForUids(arraySet, 262144, true);
        android.net.ConnectivitySettingsManager.setUidsAllowedOnRestrictedNetworks(this.mContext, java.util.Set.of());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isSystemApp(int i) {
        java.lang.String packageForUid = getPackageForUid(i);
        if (packageForUid == null) {
            return false;
        }
        try {
            return this.mContext.getPackageManager().getApplicationInfo(packageForUid, 8192).isSystemApp();
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private void setPolicyForUids(java.util.Set<java.lang.Integer> set, int i, boolean z) {
        int i2;
        synchronized (this.mUidRulesFirstLock) {
            try {
                java.util.Iterator<java.lang.Integer> it = set.iterator();
                while (it.hasNext()) {
                    int intValue = it.next().intValue();
                    int i3 = this.mUidPolicy.get(intValue, 0);
                    if (z != ((i3 & i) == i)) {
                        if (z) {
                            i2 = i3 | i;
                        } else {
                            i2 = i3 & (~i);
                        }
                        setUidPolicyUncheckedUL(intValue, i2, false);
                    }
                }
                synchronized (this.mNetworkPoliciesSecondLock) {
                    writePolicyAL();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock", "mNetworkPoliciesSecondLock"})
    private void readPolicyAL() {
        if (LOGV) {
            android.util.Slog.v(TAG, "readPolicyAL()");
        }
        this.mNetworkPolicy.clear();
        this.mSubscriptionPlans.clear();
        this.mSubscriptionPlansOwner.clear();
        this.mUidPolicy.clear();
        java.io.FileInputStream fileInputStream = null;
        try {
            try {
                try {
                    fileInputStream = this.mPolicyFile.openRead();
                    readPolicyXml(fileInputStream, false, -1);
                } catch (java.lang.Exception e) {
                    android.util.Log.wtf(TAG, "problem reading network policy", e);
                }
            } catch (java.io.FileNotFoundException e2) {
                upgradeDefaultBackgroundDataUL();
            }
        } finally {
            libcore.io.IoUtils.closeQuietly(fileInputStream);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void readPolicyXml(java.io.InputStream inputStream, boolean z, int i) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        int i2;
        int i3;
        java.lang.String str;
        int i4;
        int i5;
        java.lang.String str2;
        android.util.RecurrenceRule buildRule;
        long j;
        boolean z2;
        long j2;
        boolean z3;
        com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(inputStream);
        android.util.SparseBooleanArray sparseBooleanArray = new android.util.SparseBooleanArray();
        int i6 = 1;
        int i7 = 1;
        int i8 = 1;
        boolean z4 = false;
        while (true) {
            int next = resolvePullParser.next();
            if (next != i6) {
                java.lang.String name = resolvePullParser.getName();
                if (next == 2) {
                    if (TAG_POLICY_LIST.equals(name)) {
                        i8 = com.android.internal.util.XmlUtils.readIntAttribute(resolvePullParser, ATTR_VERSION);
                        int readIntAttribute = com.android.internal.util.XmlUtils.readIntAttribute(resolvePullParser, ATTR_LINEAGE_VERSION, i7);
                        this.mLoadedRestrictBackground = (i8 < 3 || !com.android.internal.util.XmlUtils.readBooleanAttribute(resolvePullParser, ATTR_RESTRICT_BACKGROUND)) ? 0 : i6;
                        i2 = readIntAttribute;
                        i3 = i6;
                    } else if (TAG_NETWORK_POLICY.equals(name)) {
                        int readIntAttribute2 = com.android.internal.util.XmlUtils.readIntAttribute(resolvePullParser, ATTR_NETWORK_TEMPLATE);
                        java.lang.String attributeValue = resolvePullParser.getAttributeValue((java.lang.String) null, ATTR_SUBSCRIBER_ID);
                        if (i8 >= 9) {
                            str = resolvePullParser.getAttributeValue((java.lang.String) null, ATTR_NETWORK_ID);
                        } else {
                            str = null;
                        }
                        if (i8 >= 13) {
                            i5 = com.android.internal.util.XmlUtils.readIntAttribute(resolvePullParser, ATTR_SUBSCRIBER_ID_MATCH_RULE);
                            i4 = com.android.internal.util.XmlUtils.readIntAttribute(resolvePullParser, ATTR_TEMPLATE_METERED);
                        } else if (readIntAttribute2 == i6) {
                            android.util.Log.d(TAG, "Update template match rule from mobile to carrier and force to metered");
                            readIntAttribute2 = 10;
                            i4 = i6;
                            i5 = 0;
                        } else {
                            i4 = -1;
                            i5 = 0;
                        }
                        if (i8 >= 11) {
                            i2 = i7;
                            buildRule = new android.util.RecurrenceRule(android.util.RecurrenceRule.convertZonedDateTime(com.android.internal.util.XmlUtils.readStringAttribute(resolvePullParser, ATTR_CYCLE_START)), android.util.RecurrenceRule.convertZonedDateTime(com.android.internal.util.XmlUtils.readStringAttribute(resolvePullParser, ATTR_CYCLE_END)), android.util.RecurrenceRule.convertPeriod(com.android.internal.util.XmlUtils.readStringAttribute(resolvePullParser, ATTR_CYCLE_PERIOD)));
                        } else {
                            i2 = i7;
                            int readIntAttribute3 = com.android.internal.util.XmlUtils.readIntAttribute(resolvePullParser, ATTR_CYCLE_DAY);
                            if (i8 >= 6) {
                                str2 = resolvePullParser.getAttributeValue((java.lang.String) null, ATTR_CYCLE_TIMEZONE);
                            } else {
                                str2 = "UTC";
                            }
                            buildRule = android.net.NetworkPolicy.buildRule(readIntAttribute3, java.time.ZoneId.of(str2));
                        }
                        long readLongAttribute = com.android.internal.util.XmlUtils.readLongAttribute(resolvePullParser, ATTR_WARNING_BYTES);
                        long readLongAttribute2 = com.android.internal.util.XmlUtils.readLongAttribute(resolvePullParser, ATTR_LIMIT_BYTES);
                        if (i8 >= 5) {
                            j = com.android.internal.util.XmlUtils.readLongAttribute(resolvePullParser, ATTR_LAST_LIMIT_SNOOZE);
                        } else if (i8 >= 2) {
                            j = com.android.internal.util.XmlUtils.readLongAttribute(resolvePullParser, ATTR_LAST_SNOOZE);
                        } else {
                            j = -1;
                        }
                        if (i8 >= 4) {
                            z2 = com.android.internal.util.XmlUtils.readBooleanAttribute(resolvePullParser, ATTR_METERED);
                        } else {
                            switch (readIntAttribute2) {
                                case 1:
                                    z2 = true;
                                    break;
                                default:
                                    z2 = false;
                                    break;
                            }
                        }
                        if (i8 >= 5) {
                            j2 = com.android.internal.util.XmlUtils.readLongAttribute(resolvePullParser, ATTR_LAST_WARNING_SNOOZE);
                        } else {
                            j2 = -1;
                        }
                        if (i8 >= 7) {
                            z3 = com.android.internal.util.XmlUtils.readBooleanAttribute(resolvePullParser, ATTR_INFERRED);
                        } else {
                            z3 = false;
                        }
                        android.net.NetworkTemplate.Builder meteredness = new android.net.NetworkTemplate.Builder(readIntAttribute2).setMeteredness(i4);
                        if (i5 == 0) {
                            android.util.ArraySet arraySet = new android.util.ArraySet();
                            arraySet.add(attributeValue);
                            meteredness.setSubscriberIds(arraySet);
                        }
                        if (str != null) {
                            meteredness.setWifiNetworkKeys(java.util.Set.of(str));
                        }
                        android.net.NetworkTemplate build = meteredness.build();
                        if (android.net.NetworkPolicy.isTemplatePersistable(build)) {
                            this.mNetworkPolicy.put(build, new android.net.NetworkPolicy(build, buildRule, readLongAttribute, readLongAttribute2, j2, j, z2, z3));
                        }
                        i3 = 1;
                    } else {
                        i2 = i7;
                        if (TAG_UID_POLICY.equals(name)) {
                            int readUidAttribute = readUidAttribute(resolvePullParser, z, i);
                            int readIntAttribute4 = com.android.internal.util.XmlUtils.readIntAttribute(resolvePullParser, ATTR_POLICY);
                            if (android.os.UserHandle.isApp(readUidAttribute)) {
                                setUidPolicyUncheckedUL(readUidAttribute, readIntAttribute4, false);
                            } else {
                                android.util.Slog.w(TAG, "unable to apply policy to UID " + readUidAttribute + "; ignoring");
                            }
                            i3 = 1;
                        } else if (!TAG_APP_POLICY.equals(name)) {
                            if (TAG_ALLOWLIST.equals(name)) {
                                i3 = 1;
                                z4 = true;
                            } else if (TAG_RESTRICT_BACKGROUND.equals(name) && z4) {
                                sparseBooleanArray.append(readUidAttribute(resolvePullParser, z, i), true);
                                i3 = 1;
                            } else if (!TAG_REVOKED_RESTRICT_BACKGROUND.equals(name) || !z4) {
                                i3 = 1;
                            } else {
                                i3 = 1;
                                this.mRestrictBackgroundAllowlistRevokedUids.put(readUidAttribute(resolvePullParser, z, i), true);
                            }
                        } else {
                            int readIntAttribute5 = com.android.internal.util.XmlUtils.readIntAttribute(resolvePullParser, ATTR_APP_ID);
                            int readIntAttribute6 = com.android.internal.util.XmlUtils.readIntAttribute(resolvePullParser, ATTR_POLICY);
                            int uid = android.os.UserHandle.getUid(0, readIntAttribute5);
                            if (android.os.UserHandle.isApp(uid)) {
                                setUidPolicyUncheckedUL(uid, readIntAttribute6, false);
                            } else {
                                android.util.Slog.w(TAG, "unable to apply policy to UID " + uid + "; ignoring");
                            }
                            i3 = 1;
                        }
                    }
                } else {
                    i2 = i7;
                    i3 = i6;
                    if (next == 3 && TAG_ALLOWLIST.equals(name)) {
                        z4 = false;
                    }
                }
                i6 = i3;
                i7 = i2;
            } else {
                int size = sparseBooleanArray.size();
                for (int i9 = 0; i9 < size; i9++) {
                    int keyAt = sparseBooleanArray.keyAt(i9);
                    int i10 = this.mUidPolicy.get(keyAt, 0);
                    if ((i10 & 1) != 0) {
                        android.util.Slog.w(TAG, "ignoring restrict-background-allowlist for " + keyAt + " because its policy is " + android.net.NetworkPolicyManager.uidPoliciesToString(i10));
                    } else if (android.os.UserHandle.isApp(keyAt)) {
                        int i11 = i10 | 4;
                        if (LOGV) {
                            android.util.Log.v(TAG, "new policy for " + keyAt + ": " + android.net.NetworkPolicyManager.uidPoliciesToString(i11));
                        }
                        setUidPolicyUncheckedUL(keyAt, i11, false);
                    } else {
                        android.util.Slog.w(TAG, "unable to update policy on UID " + keyAt);
                    }
                }
                return;
            }
        }
    }

    private void upgradeDefaultBackgroundDataUL() {
        this.mLoadedRestrictBackground = android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "default_restrict_background_data", 0) == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void upgradeWifiMeteredOverride() {
        int i;
        int i2;
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        synchronized (this.mNetworkPoliciesSecondLock) {
            int i3 = 0;
            while (i3 < this.mNetworkPolicy.size()) {
                try {
                    android.net.NetworkPolicy valueAt = this.mNetworkPolicy.valueAt(i3);
                    if (valueAt.template.getMatchRule() == 4 && !valueAt.inferred) {
                        this.mNetworkPolicy.removeAt(i3);
                        java.util.Set wifiNetworkKeys = valueAt.template.getWifiNetworkKeys();
                        arrayMap.put(wifiNetworkKeys.isEmpty() ? null : (java.lang.String) wifiNetworkKeys.iterator().next(), java.lang.Boolean.valueOf(valueAt.metered));
                    } else {
                        i3++;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        if (arrayMap.isEmpty()) {
            return;
        }
        android.net.wifi.WifiManager wifiManager = (android.net.wifi.WifiManager) this.mContext.getSystemService(android.net.wifi.WifiManager.class);
        java.util.List<android.net.wifi.WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
        for (i = 0; i < configuredNetworks.size(); i++) {
            android.net.wifi.WifiConfiguration wifiConfiguration = configuredNetworks.get(i);
            java.util.Iterator it = wifiConfiguration.getAllNetworkKeys().iterator();
            while (true) {
                if (it.hasNext()) {
                    java.lang.String str = (java.lang.String) it.next();
                    java.lang.Boolean bool = (java.lang.Boolean) arrayMap.get(str);
                    if (bool != null) {
                        android.util.Slog.d(TAG, "Found network " + str + "; upgrading metered hint");
                        if (bool.booleanValue()) {
                            i2 = 1;
                        } else {
                            i2 = 2;
                        }
                        wifiConfiguration.meteredOverride = i2;
                        wifiManager.updateNetwork(wifiConfiguration);
                    }
                }
            }
        }
        synchronized (this.mUidRulesFirstLock) {
            synchronized (this.mNetworkPoliciesSecondLock) {
                writePolicyAL();
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock", "mNetworkPoliciesSecondLock"})
    void writePolicyAL() {
        if (LOGV) {
            android.util.Slog.v(TAG, "writePolicyAL()");
        }
        java.io.FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = this.mPolicyFile.startWrite();
            writePolicyXml(fileOutputStream, false, -1);
            this.mPolicyFile.finishWrite(fileOutputStream);
        } catch (java.io.IOException e) {
            if (fileOutputStream != null) {
                this.mPolicyFile.failWrite(fileOutputStream);
            }
        }
    }

    private void writePolicyXml(java.io.OutputStream outputStream, boolean z, int i) throws java.io.IOException {
        com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(outputStream);
        resolveSerializer.startDocument((java.lang.String) null, true);
        if (!z || i == 0) {
            writeUidIndependentAttributes(resolveSerializer);
        }
        for (int i2 = 0; i2 < this.mUidPolicy.size(); i2++) {
            int keyAt = this.mUidPolicy.keyAt(i2);
            int valueAt = this.mUidPolicy.valueAt(i2);
            if ((!z || android.os.UserHandle.getUserId(keyAt) == i) && valueAt != 0) {
                resolveSerializer.startTag((java.lang.String) null, TAG_UID_POLICY);
                if (!z) {
                    com.android.internal.util.XmlUtils.writeIntAttribute(resolveSerializer, "uid", keyAt);
                } else {
                    com.android.internal.util.XmlUtils.writeStringAttribute(resolveSerializer, "name", getPackageForUid(keyAt));
                }
                com.android.internal.util.XmlUtils.writeIntAttribute(resolveSerializer, ATTR_POLICY, valueAt);
                resolveSerializer.endTag((java.lang.String) null, TAG_UID_POLICY);
            }
        }
        resolveSerializer.startTag((java.lang.String) null, TAG_ALLOWLIST);
        int size = this.mRestrictBackgroundAllowlistRevokedUids.size();
        for (int i3 = 0; i3 < size; i3++) {
            int keyAt2 = this.mRestrictBackgroundAllowlistRevokedUids.keyAt(i3);
            if (!z || android.os.UserHandle.getUserId(keyAt2) == i) {
                resolveSerializer.startTag((java.lang.String) null, TAG_REVOKED_RESTRICT_BACKGROUND);
                if (!z) {
                    com.android.internal.util.XmlUtils.writeIntAttribute(resolveSerializer, "uid", keyAt2);
                } else {
                    com.android.internal.util.XmlUtils.writeStringAttribute(resolveSerializer, "name", getPackageForUid(keyAt2));
                }
                resolveSerializer.endTag((java.lang.String) null, TAG_REVOKED_RESTRICT_BACKGROUND);
            }
        }
        resolveSerializer.endTag((java.lang.String) null, TAG_ALLOWLIST);
        resolveSerializer.endDocument();
    }

    private void writeUidIndependentAttributes(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        java.lang.String str;
        int i;
        typedXmlSerializer.startTag((java.lang.String) null, TAG_POLICY_LIST);
        com.android.internal.util.XmlUtils.writeIntAttribute(typedXmlSerializer, ATTR_VERSION, 14);
        com.android.internal.util.XmlUtils.writeIntAttribute(typedXmlSerializer, ATTR_LINEAGE_VERSION, 2);
        com.android.internal.util.XmlUtils.writeBooleanAttribute(typedXmlSerializer, ATTR_RESTRICT_BACKGROUND, this.mRestrictBackground);
        for (int i2 = 0; i2 < this.mNetworkPolicy.size(); i2++) {
            android.net.NetworkPolicy valueAt = this.mNetworkPolicy.valueAt(i2);
            android.net.NetworkTemplate networkTemplate = valueAt.template;
            if (android.net.NetworkPolicy.isTemplatePersistable(networkTemplate)) {
                typedXmlSerializer.startTag((java.lang.String) null, TAG_NETWORK_POLICY);
                com.android.internal.util.XmlUtils.writeIntAttribute(typedXmlSerializer, ATTR_NETWORK_TEMPLATE, networkTemplate.getMatchRule());
                if (networkTemplate.getSubscriberIds().isEmpty()) {
                    str = null;
                } else {
                    str = (java.lang.String) networkTemplate.getSubscriberIds().iterator().next();
                }
                if (str != null) {
                    typedXmlSerializer.attribute((java.lang.String) null, ATTR_SUBSCRIBER_ID, str);
                }
                if (networkTemplate.getSubscriberIds().isEmpty()) {
                    i = 1;
                } else {
                    i = 0;
                }
                com.android.internal.util.XmlUtils.writeIntAttribute(typedXmlSerializer, ATTR_SUBSCRIBER_ID_MATCH_RULE, i);
                if (!networkTemplate.getWifiNetworkKeys().isEmpty()) {
                    typedXmlSerializer.attribute((java.lang.String) null, ATTR_NETWORK_ID, (java.lang.String) networkTemplate.getWifiNetworkKeys().iterator().next());
                }
                com.android.internal.util.XmlUtils.writeIntAttribute(typedXmlSerializer, ATTR_TEMPLATE_METERED, networkTemplate.getMeteredness());
                com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, ATTR_CYCLE_START, android.util.RecurrenceRule.convertZonedDateTime(valueAt.cycleRule.start));
                com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, ATTR_CYCLE_END, android.util.RecurrenceRule.convertZonedDateTime(valueAt.cycleRule.end));
                com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, ATTR_CYCLE_PERIOD, android.util.RecurrenceRule.convertPeriod(valueAt.cycleRule.period));
                com.android.internal.util.XmlUtils.writeLongAttribute(typedXmlSerializer, ATTR_WARNING_BYTES, valueAt.warningBytes);
                com.android.internal.util.XmlUtils.writeLongAttribute(typedXmlSerializer, ATTR_LIMIT_BYTES, valueAt.limitBytes);
                com.android.internal.util.XmlUtils.writeLongAttribute(typedXmlSerializer, ATTR_LAST_WARNING_SNOOZE, valueAt.lastWarningSnooze);
                com.android.internal.util.XmlUtils.writeLongAttribute(typedXmlSerializer, ATTR_LAST_LIMIT_SNOOZE, valueAt.lastLimitSnooze);
                com.android.internal.util.XmlUtils.writeBooleanAttribute(typedXmlSerializer, ATTR_METERED, valueAt.metered);
                com.android.internal.util.XmlUtils.writeBooleanAttribute(typedXmlSerializer, ATTR_INFERRED, valueAt.inferred);
                typedXmlSerializer.endTag((java.lang.String) null, TAG_NETWORK_POLICY);
            }
        }
        typedXmlSerializer.endTag((java.lang.String) null, TAG_POLICY_LIST);
    }

    public byte[] getBackupPayload(int i) {
        enforceSystemCaller();
        if (LOGD) {
            android.util.Slog.d(TAG, "getBackupPayload u= " + i);
        }
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        try {
            writePolicyXml(byteArrayOutputStream, true, i);
            return byteArrayOutputStream.toByteArray();
        } catch (java.io.IOException e) {
            android.util.Slog.w(TAG, "getBackupPayload: error writing payload for user " + i, e);
            return null;
        }
    }

    public void applyRestore(byte[] bArr, int i) {
        enforceSystemCaller();
        if (LOGD) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("applyRestore u=");
            sb.append(i);
            sb.append(" payload=");
            sb.append(bArr != null ? new java.lang.String(bArr, java.nio.charset.StandardCharsets.UTF_8) : null);
            android.util.Slog.d(TAG, sb.toString());
        }
        if (bArr == null) {
            android.util.Slog.w(TAG, "applyRestore: no payload to restore for user " + i);
            return;
        }
        try {
            readPolicyXml(new java.io.ByteArrayInputStream(bArr), true, i);
        } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
            android.util.Slog.w(TAG, "applyRestore: error reading payload for user " + i, e);
        }
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_NETWORK_POLICY")
    public void setUidPolicy(int i, int i2) {
        setUidPolicy_enforcePermission();
        if (!android.os.UserHandle.isApp(i)) {
            throw new java.lang.IllegalArgumentException("cannot apply policy to UID " + i);
        }
        synchronized (this.mUidRulesFirstLock) {
            try {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    int i3 = this.mUidPolicy.get(i, 0);
                    if (i3 != i2) {
                        setUidPolicyUncheckedUL(i, i3, i2, true);
                        this.mLogger.uidPolicyChanged(i, i3, i2);
                    }
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_NETWORK_POLICY")
    public void addUidPolicy(int i, int i2) {
        addUidPolicy_enforcePermission();
        if (!android.os.UserHandle.isApp(i)) {
            throw new java.lang.IllegalArgumentException("cannot apply policy to UID " + i);
        }
        synchronized (this.mUidRulesFirstLock) {
            try {
                int i3 = this.mUidPolicy.get(i, 0);
                int i4 = i2 | i3;
                if (i3 != i4) {
                    setUidPolicyUncheckedUL(i, i3, i4, true);
                    this.mLogger.uidPolicyChanged(i, i3, i4);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_NETWORK_POLICY")
    public void removeUidPolicy(int i, int i2) {
        removeUidPolicy_enforcePermission();
        if (!android.os.UserHandle.isApp(i)) {
            throw new java.lang.IllegalArgumentException("cannot apply policy to UID " + i);
        }
        synchronized (this.mUidRulesFirstLock) {
            try {
                int i3 = this.mUidPolicy.get(i, 0);
                int i4 = (~i2) & i3;
                if (i3 != i4) {
                    setUidPolicyUncheckedUL(i, i3, i4, true);
                    this.mLogger.uidPolicyChanged(i, i3, i4);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private static boolean isLockdownPolicy(int i) {
        return (i & 229376) == 98304;
    }

    @android.annotation.NonNull
    private java.util.List<android.util.Range<java.lang.Integer>> getUidsWithLockdownPolicy() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mUidRulesFirstLock) {
            for (int i = 0; i < this.mUidPolicy.size(); i++) {
                try {
                    int keyAt = this.mUidPolicy.keyAt(i);
                    if (isLockdownPolicy(this.mUidPolicy.valueAt(i))) {
                        arrayList.add(new android.util.Range(java.lang.Integer.valueOf(keyAt), java.lang.Integer.valueOf(keyAt)));
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        return arrayList;
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    private void setUidPolicyUncheckedUL(int i, int i2, int i3, boolean z) {
        boolean z2 = false;
        setUidPolicyUncheckedUL(i, i3, false);
        boolean isLockdownPolicy = isLockdownPolicy(i2);
        boolean isLockdownPolicy2 = isLockdownPolicy(i3);
        if (isLockdownPolicy != isLockdownPolicy2) {
            try {
                this.mConnManager.setRequireVpnForUids(isLockdownPolicy2, java.util.List.of(new android.util.Range(java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i))));
            } catch (java.lang.RuntimeException e) {
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("setUidPolicyUncheckedUL: Setting VPN ");
                sb.append(isLockdownPolicy2 ? "required " : "not required");
                sb.append(" failed for uid ");
                sb.append(i);
                android.util.Slog.wtf(TAG, sb.toString(), e);
            }
        }
        long allowedTransportsPackedForUidPolicy = getAllowedTransportsPackedForUidPolicy(i2);
        long allowedTransportsPackedForUidPolicy2 = getAllowedTransportsPackedForUidPolicy(i3);
        if (allowedTransportsPackedForUidPolicy != allowedTransportsPackedForUidPolicy2) {
            dispatchUidsAllowedTransportsUL(new int[]{i}, new long[]{allowedTransportsPackedForUidPolicy2});
        }
        if (isUidValidForAllowlistRulesUL(i)) {
            boolean z3 = i2 == 1;
            boolean z4 = i3 == 1;
            boolean z5 = i2 == 4;
            boolean z6 = i3 == 4;
            boolean z7 = z3 || (this.mRestrictBackground && !z5);
            boolean z8 = z4 || (this.mRestrictBackground && !z6);
            if (z5 && ((!z6 || z4) && this.mDefaultRestrictBackgroundAllowlistUids.get(i) && !this.mRestrictBackgroundAllowlistRevokedUids.get(i))) {
                if (LOGD) {
                    android.util.Slog.d(TAG, "Adding uid " + i + " to revoked restrict background allowlist");
                }
                this.mRestrictBackgroundAllowlistRevokedUids.append(i, true);
            }
            z2 = z7 != z8;
        }
        this.mHandler.obtainMessage(13, i, i3, java.lang.Boolean.valueOf(z2)).sendToTarget();
        if (z) {
            synchronized (this.mNetworkPoliciesSecondLock) {
                writePolicyAL();
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    private void setUidPolicyUncheckedUL(int i, int i2, boolean z) {
        if (i2 == 0) {
            this.mUidPolicy.delete(i);
        } else {
            this.mUidPolicy.put(i, i2);
        }
        updateRestrictedModeForUidUL(i);
        lambda$updateRulesForRestrictBackgroundUL$6(i);
        if (z) {
            synchronized (this.mNetworkPoliciesSecondLock) {
                writePolicyAL();
            }
        }
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_NETWORK_POLICY")
    public int getUidPolicy(int i) {
        int i2;
        getUidPolicy_enforcePermission();
        synchronized (this.mUidRulesFirstLock) {
            i2 = this.mUidPolicy.get(i, 0);
        }
        return i2;
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_NETWORK_POLICY")
    public int[] getUidsWithPolicy(int i) {
        getUidsWithPolicy_enforcePermission();
        int[] iArr = new int[0];
        synchronized (this.mUidRulesFirstLock) {
            for (int i2 = 0; i2 < this.mUidPolicy.size(); i2++) {
                try {
                    int keyAt = this.mUidPolicy.keyAt(i2);
                    int valueAt = this.mUidPolicy.valueAt(i2);
                    if ((i == 0 && valueAt == 0) || (valueAt & i) != 0) {
                        iArr = com.android.internal.util.ArrayUtils.appendInt(iArr, keyAt);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        return iArr;
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    boolean removeUserStateUL(int i, boolean z, boolean z2) {
        this.mLogger.removingUserState(i);
        boolean z3 = false;
        for (int size = this.mRestrictBackgroundAllowlistRevokedUids.size() - 1; size >= 0; size--) {
            if (android.os.UserHandle.getUserId(this.mRestrictBackgroundAllowlistRevokedUids.keyAt(size)) == i) {
                this.mRestrictBackgroundAllowlistRevokedUids.removeAt(size);
                z3 = true;
            }
        }
        int[] iArr = new int[0];
        for (int i2 = 0; i2 < this.mUidPolicy.size(); i2++) {
            int keyAt = this.mUidPolicy.keyAt(i2);
            if (android.os.UserHandle.getUserId(keyAt) == i) {
                iArr = com.android.internal.util.ArrayUtils.appendInt(iArr, keyAt);
            }
        }
        if (iArr.length > 0) {
            for (int i3 : iArr) {
                this.mUidPolicy.delete(i3);
            }
            z3 = true;
        }
        synchronized (this.mNetworkPoliciesSecondLock) {
            if (z2) {
                try {
                    updateRulesForGlobalChangeAL(true);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (z && z3) {
                writePolicyAL();
            }
        }
        return z3;
    }

    private boolean checkAnyPermissionOf(java.lang.String... strArr) {
        for (java.lang.String str : strArr) {
            if (this.mContext.checkCallingOrSelfPermission(str) == 0) {
                return true;
            }
        }
        return false;
    }

    private void enforceAnyPermissionOf(java.lang.String... strArr) {
        if (!checkAnyPermissionOf(strArr)) {
            throw new java.lang.SecurityException("Requires one of the following permissions: " + java.lang.String.join(", ", strArr) + ".");
        }
    }

    private void enforceSystemCaller() {
        if (android.os.Binder.getCallingUid() != 1000) {
            throw new java.lang.SecurityException("Caller must be system");
        }
    }

    public void registerListener(@android.annotation.NonNull android.net.INetworkPolicyListener iNetworkPolicyListener) {
        java.util.Objects.requireNonNull(iNetworkPolicyListener);
        enforceAnyPermissionOf("android.permission.CONNECTIVITY_INTERNAL", "android.permission.OBSERVE_NETWORK_POLICY");
        this.mListeners.register(iNetworkPolicyListener);
    }

    public void unregisterListener(@android.annotation.NonNull android.net.INetworkPolicyListener iNetworkPolicyListener) {
        java.util.Objects.requireNonNull(iNetworkPolicyListener);
        enforceAnyPermissionOf("android.permission.CONNECTIVITY_INTERNAL", "android.permission.OBSERVE_NETWORK_POLICY");
        this.mListeners.unregister(iNetworkPolicyListener);
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_NETWORK_POLICY")
    public void setNetworkPolicies(android.net.NetworkPolicy[] networkPolicyArr) {
        setNetworkPolicies_enforcePermission();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mUidRulesFirstLock) {
                synchronized (this.mNetworkPoliciesSecondLock) {
                    normalizePoliciesNL(networkPolicyArr);
                    handleNetworkPoliciesUpdateAL(false);
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    void addNetworkPolicyAL(android.net.NetworkPolicy networkPolicy) {
        setNetworkPolicies((android.net.NetworkPolicy[]) com.android.internal.util.ArrayUtils.appendElement(android.net.NetworkPolicy.class, getNetworkPolicies(this.mContext.getOpPackageName()), networkPolicy));
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_NETWORK_POLICY")
    public android.net.NetworkPolicy[] getNetworkPolicies(java.lang.String str) {
        android.net.NetworkPolicy[] networkPolicyArr;
        getNetworkPolicies_enforcePermission();
        try {
            this.mContext.enforceCallingOrSelfPermission("android.permission.READ_PRIVILEGED_PHONE_STATE", TAG);
        } catch (java.lang.SecurityException e) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.READ_PHONE_STATE", TAG);
            if (this.mAppOps.noteOp(51, android.os.Binder.getCallingUid(), str) != 0) {
                return new android.net.NetworkPolicy[0];
            }
        }
        synchronized (this.mNetworkPoliciesSecondLock) {
            try {
                int size = this.mNetworkPolicy.size();
                networkPolicyArr = new android.net.NetworkPolicy[size];
                for (int i = 0; i < size; i++) {
                    networkPolicyArr[i] = this.mNetworkPolicy.valueAt(i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return networkPolicyArr;
    }

    @com.android.internal.annotations.GuardedBy({"mNetworkPoliciesSecondLock"})
    private void normalizePoliciesNL() {
        normalizePoliciesNL(getNetworkPolicies(this.mContext.getOpPackageName()));
    }

    @com.android.internal.annotations.GuardedBy({"mNetworkPoliciesSecondLock"})
    private void normalizePoliciesNL(android.net.NetworkPolicy[] networkPolicyArr) {
        this.mNetworkPolicy.clear();
        for (android.net.NetworkPolicy networkPolicy : networkPolicyArr) {
            if (networkPolicy != null) {
                networkPolicy.template = normalizeTemplate(networkPolicy.template, this.mMergedSubscriberIds);
                android.net.NetworkPolicy networkPolicy2 = this.mNetworkPolicy.get(networkPolicy.template);
                if (networkPolicy2 == null || networkPolicy2.compareTo(networkPolicy) > 0) {
                    if (networkPolicy2 != null) {
                        android.util.Slog.d(TAG, "Normalization replaced " + networkPolicy2 + " with " + networkPolicy);
                    }
                    this.mNetworkPolicy.put(networkPolicy.template, networkPolicy);
                }
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    static android.net.NetworkTemplate normalizeTemplate(@android.annotation.NonNull android.net.NetworkTemplate networkTemplate, @android.annotation.NonNull java.util.List<java.lang.String[]> list) {
        if (networkTemplate.getSubscriberIds().isEmpty()) {
            return networkTemplate;
        }
        for (java.lang.String[] strArr : list) {
            android.util.ArraySet arraySet = new android.util.ArraySet(strArr);
            if (arraySet.size() != strArr.length) {
                android.util.Log.wtf(TAG, "Duplicated merged list detected: " + java.util.Arrays.toString(strArr));
            }
            java.util.Iterator it = networkTemplate.getSubscriberIds().iterator();
            while (it.hasNext()) {
                if (com.android.net.module.util.CollectionUtils.contains(strArr, (java.lang.String) it.next())) {
                    return new android.net.NetworkTemplate.Builder(networkTemplate.getMatchRule()).setWifiNetworkKeys(networkTemplate.getWifiNetworkKeys()).setSubscriberIds(arraySet).setMeteredness(networkTemplate.getMeteredness()).build();
                }
            }
        }
        return networkTemplate;
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_NETWORK_POLICY")
    public void snoozeLimit(android.net.NetworkTemplate networkTemplate) {
        snoozeLimit_enforcePermission();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            performSnooze(networkTemplate, 35);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    void performSnooze(android.net.NetworkTemplate networkTemplate, int i) {
        long millis = this.mClock.millis();
        synchronized (this.mUidRulesFirstLock) {
            synchronized (this.mNetworkPoliciesSecondLock) {
                try {
                    android.net.NetworkPolicy networkPolicy = this.mNetworkPolicy.get(networkTemplate);
                    if (networkPolicy == null) {
                        throw new java.lang.IllegalArgumentException("unable to find policy for " + networkTemplate);
                    }
                    switch (i) {
                        case 34:
                            networkPolicy.lastWarningSnooze = millis;
                            break;
                        case 35:
                            networkPolicy.lastLimitSnooze = millis;
                            break;
                        case 45:
                            networkPolicy.lastRapidSnooze = millis;
                            break;
                        default:
                            throw new java.lang.IllegalArgumentException("unexpected type");
                    }
                    handleNetworkPoliciesUpdateAL(true);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    public void setRestrictBackground(boolean z) {
        android.os.Trace.traceBegin(2097152L, "setRestrictBackground");
        try {
            this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_NETWORK_POLICY", TAG);
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (this.mUidRulesFirstLock) {
                    setRestrictBackgroundUL(z, "uid:" + callingUid);
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } finally {
            android.os.Trace.traceEnd(2097152L);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    private void setRestrictBackgroundUL(boolean z, java.lang.String str) {
        android.os.Trace.traceBegin(2097152L, "setRestrictBackgroundUL");
        try {
            if (z == this.mRestrictBackground) {
                android.util.Slog.w(TAG, "setRestrictBackgroundUL: already " + z);
                return;
            }
            android.util.Slog.d(TAG, "setRestrictBackgroundUL(): " + z + "; reason: " + str);
            boolean z2 = this.mRestrictBackground;
            this.mRestrictBackground = z;
            updateRulesForRestrictBackgroundUL();
            try {
                if (!this.mNetworkManager.setDataSaverModeEnabled(this.mRestrictBackground)) {
                    android.util.Slog.e(TAG, "Could not change Data Saver Mode on NMS to " + this.mRestrictBackground);
                    this.mRestrictBackground = z2;
                    return;
                }
            } catch (android.os.RemoteException e) {
            }
            sendRestrictBackgroundChangedMsg();
            this.mLogger.restrictBackgroundChanged(z2, this.mRestrictBackground);
            if (this.mRestrictBackgroundLowPowerMode) {
                this.mRestrictBackgroundChangedInBsm = true;
            }
            synchronized (this.mNetworkPoliciesSecondLock) {
                updateNotificationsNL();
                writePolicyAL();
            }
        } finally {
            android.os.Trace.traceEnd(2097152L);
        }
    }

    private void sendRestrictBackgroundChangedMsg() {
        this.mHandler.removeMessages(6);
        this.mHandler.obtainMessage(6, this.mRestrictBackground ? 1 : 0, 0).sendToTarget();
    }

    @android.annotation.EnforcePermission("android.permission.ACCESS_NETWORK_STATE")
    public int getRestrictBackgroundByCaller() {
        getRestrictBackgroundByCaller_enforcePermission();
        return getRestrictBackgroundStatusInternal(android.os.Binder.getCallingUid());
    }

    public int getRestrictBackgroundStatus(int i) {
        com.android.net.module.util.PermissionUtils.enforceNetworkStackPermission(this.mContext);
        return getRestrictBackgroundStatusInternal(i);
    }

    private int getRestrictBackgroundStatusInternal(int i) {
        synchronized (this.mUidRulesFirstLock) {
            try {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    int uidPolicy = getUidPolicy(i);
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    int i2 = 3;
                    if (uidPolicy == 1) {
                        return 3;
                    }
                    if (!this.mRestrictBackground) {
                        return 1;
                    }
                    if ((this.mUidPolicy.get(i) & 4) != 0) {
                        i2 = 2;
                    }
                    return i2;
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            } finally {
            }
        }
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_NETWORK_POLICY")
    public boolean getRestrictBackground() {
        boolean z;
        getRestrictBackground_enforcePermission();
        synchronized (this.mUidRulesFirstLock) {
            z = this.mRestrictBackground;
        }
        return z;
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_NETWORK_POLICY")
    public void setDeviceIdleMode(boolean z) {
        setDeviceIdleMode_enforcePermission();
        android.os.Trace.traceBegin(2097152L, "setDeviceIdleMode");
        try {
            synchronized (this.mUidRulesFirstLock) {
                if (this.mDeviceIdleMode == z) {
                    android.os.Trace.traceEnd(2097152L);
                    return;
                }
                this.mDeviceIdleMode = z;
                this.mLogger.deviceIdleModeEnabled(z);
                if (this.mSystemReady) {
                    handleDeviceIdleModeChangedUL(z);
                }
                if (z) {
                    com.android.server.EventLogTags.writeDeviceIdleOnPhase("net");
                } else {
                    com.android.server.EventLogTags.writeDeviceIdleOffPhase("net");
                }
                android.os.Trace.traceEnd(2097152L);
            }
        } catch (java.lang.Throwable th) {
            android.os.Trace.traceEnd(2097152L);
            throw th;
        }
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_NETWORK_POLICY")
    public void setWifiMeteredOverride(java.lang.String str, int i) {
        setWifiMeteredOverride_enforcePermission();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.net.wifi.WifiManager wifiManager = (android.net.wifi.WifiManager) this.mContext.getSystemService(android.net.wifi.WifiManager.class);
            for (android.net.wifi.WifiConfiguration wifiConfiguration : wifiManager.getConfiguredNetworks()) {
                if (java.util.Objects.equals(android.net.NetworkPolicyManager.resolveNetworkId(wifiConfiguration), str)) {
                    wifiConfiguration.meteredOverride = i;
                    wifiManager.updateNetwork(wifiConfiguration);
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void enforceSubscriptionPlanAccess(int i, int i2, java.lang.String str) {
        this.mAppOps.checkPackage(i2, str);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.os.PersistableBundle configForSubId = this.mCarrierConfigManager.getConfigForSubId(i);
            android.telephony.TelephonyManager telephonyManager = (android.telephony.TelephonyManager) this.mContext.getSystemService(android.telephony.TelephonyManager.class);
            if (telephonyManager != null && telephonyManager.hasCarrierPrivileges(i)) {
                return;
            }
            if (configForSubId != null) {
                java.lang.String string = configForSubId.getString("config_plans_package_override_string", null);
                if (!android.text.TextUtils.isEmpty(string) && java.util.Objects.equals(string, str)) {
                    return;
                }
            }
            java.lang.String defaultCarrierServicePackageName = this.mCarrierConfigManager.getDefaultCarrierServicePackageName();
            if (!android.text.TextUtils.isEmpty(defaultCarrierServicePackageName) && java.util.Objects.equals(defaultCarrierServicePackageName, str)) {
                return;
            }
            java.lang.String str2 = android.os.SystemProperties.get("persist.sys.sub_plan_owner." + i, (java.lang.String) null);
            if (!android.text.TextUtils.isEmpty(str2) && java.util.Objects.equals(str2, str)) {
                return;
            }
            java.lang.String str3 = android.os.SystemProperties.get("fw.sub_plan_owner." + i, (java.lang.String) null);
            if (!android.text.TextUtils.isEmpty(str3) && java.util.Objects.equals(str3, str)) {
                return;
            }
            this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_SUBSCRIPTION_PLANS", TAG);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void enforceSubscriptionPlanValidity(android.telephony.SubscriptionPlan[] subscriptionPlanArr) {
        if (subscriptionPlanArr.length == 0) {
            android.util.Log.d(TAG, "Received empty plans list. Clearing existing SubscriptionPlans.");
            return;
        }
        int[] allNetworkTypes = android.telephony.TelephonyManager.getAllNetworkTypes();
        android.util.ArraySet arraySet = new android.util.ArraySet();
        addAll(arraySet, allNetworkTypes);
        android.util.ArraySet arraySet2 = new android.util.ArraySet();
        boolean z = false;
        for (android.telephony.SubscriptionPlan subscriptionPlan : subscriptionPlanArr) {
            int[] networkTypes = subscriptionPlan.getNetworkTypes();
            android.util.ArraySet arraySet3 = new android.util.ArraySet();
            for (int i = 0; i < networkTypes.length; i++) {
                if (arraySet.contains(java.lang.Integer.valueOf(networkTypes[i]))) {
                    if (!arraySet3.add(java.lang.Integer.valueOf(networkTypes[i]))) {
                        throw new java.lang.IllegalArgumentException("Subscription plan contains duplicate network types.");
                    }
                } else {
                    throw new java.lang.IllegalArgumentException("Invalid network type: " + networkTypes[i]);
                }
            }
            if (networkTypes.length == allNetworkTypes.length) {
                z = true;
            } else if (!addAll(arraySet2, networkTypes)) {
                throw new java.lang.IllegalArgumentException("Multiple subscription plans defined for a single network type.");
            }
        }
        if (!z) {
            throw new java.lang.IllegalArgumentException("No generic subscription plan that applies to all network types.");
        }
    }

    private static boolean addAll(@android.annotation.NonNull android.util.ArraySet<java.lang.Integer> arraySet, @android.annotation.NonNull int... iArr) {
        boolean z = true;
        for (int i : iArr) {
            z &= arraySet.add(java.lang.Integer.valueOf(i));
        }
        return z;
    }

    public android.telephony.SubscriptionPlan getSubscriptionPlan(@android.annotation.NonNull android.net.NetworkTemplate networkTemplate) {
        android.telephony.SubscriptionPlan primarySubscriptionPlanLocked;
        enforceAnyPermissionOf("android.permission.MAINLINE_NETWORK_STACK");
        synchronized (this.mNetworkPoliciesSecondLock) {
            primarySubscriptionPlanLocked = getPrimarySubscriptionPlanLocked(findRelevantSubIdNL(networkTemplate));
        }
        return primarySubscriptionPlanLocked;
    }

    public void notifyStatsProviderWarningOrLimitReached() {
        enforceAnyPermissionOf("android.permission.MAINLINE_NETWORK_STACK");
        synchronized (this.mNetworkPoliciesSecondLock) {
            try {
                if (this.mSystemReady) {
                    this.mHandler.obtainMessage(20).sendToTarget();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public android.telephony.SubscriptionPlan[] getSubscriptionPlans(int i, java.lang.String str) {
        enforceSubscriptionPlanAccess(i, android.os.Binder.getCallingUid(), str);
        java.lang.String str2 = android.os.SystemProperties.get("fw.fake_plan");
        if (!android.text.TextUtils.isEmpty(str2)) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            if ("month_hard".equals(str2)) {
                arrayList.add(android.telephony.SubscriptionPlan.Builder.createRecurringMonthly(java.time.ZonedDateTime.parse("2007-03-14T00:00:00.000Z")).setTitle("G-Mobile").setDataLimit(android.util.DataUnit.GIBIBYTES.toBytes(5L), 1).setDataUsage(android.util.DataUnit.GIBIBYTES.toBytes(1L), java.time.ZonedDateTime.now().minusHours(36L).toInstant().toEpochMilli()).build());
                arrayList.add(android.telephony.SubscriptionPlan.Builder.createRecurringMonthly(java.time.ZonedDateTime.parse("2017-03-14T00:00:00.000Z")).setTitle("G-Mobile Happy").setDataLimit(com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME, 1).setDataUsage(android.util.DataUnit.GIBIBYTES.toBytes(5L), java.time.ZonedDateTime.now().minusHours(36L).toInstant().toEpochMilli()).build());
                arrayList.add(android.telephony.SubscriptionPlan.Builder.createRecurringMonthly(java.time.ZonedDateTime.parse("2017-03-14T00:00:00.000Z")).setTitle("G-Mobile, Charged after limit").setDataLimit(android.util.DataUnit.GIBIBYTES.toBytes(5L), 1).setDataUsage(android.util.DataUnit.GIBIBYTES.toBytes(5L), java.time.ZonedDateTime.now().minusHours(36L).toInstant().toEpochMilli()).build());
            } else if ("month_soft".equals(str2)) {
                arrayList.add(android.telephony.SubscriptionPlan.Builder.createRecurringMonthly(java.time.ZonedDateTime.parse("2007-03-14T00:00:00.000Z")).setTitle("G-Mobile is the carriers name who this plan belongs to").setSummary("Crazy unlimited bandwidth plan with incredibly long title that should be cut off to prevent UI from looking terrible").setDataLimit(android.util.DataUnit.GIBIBYTES.toBytes(5L), 2).setDataUsage(android.util.DataUnit.GIBIBYTES.toBytes(1L), java.time.ZonedDateTime.now().minusHours(1L).toInstant().toEpochMilli()).build());
                arrayList.add(android.telephony.SubscriptionPlan.Builder.createRecurringMonthly(java.time.ZonedDateTime.parse("2017-03-14T00:00:00.000Z")).setTitle("G-Mobile, Throttled after limit").setDataLimit(android.util.DataUnit.GIBIBYTES.toBytes(5L), 2).setDataUsage(android.util.DataUnit.GIBIBYTES.toBytes(5L), java.time.ZonedDateTime.now().minusHours(1L).toInstant().toEpochMilli()).build());
                arrayList.add(android.telephony.SubscriptionPlan.Builder.createRecurringMonthly(java.time.ZonedDateTime.parse("2017-03-14T00:00:00.000Z")).setTitle("G-Mobile, No data connection after limit").setDataLimit(android.util.DataUnit.GIBIBYTES.toBytes(5L), 0).setDataUsage(android.util.DataUnit.GIBIBYTES.toBytes(5L), java.time.ZonedDateTime.now().minusHours(1L).toInstant().toEpochMilli()).build());
            } else if ("month_over".equals(str2)) {
                arrayList.add(android.telephony.SubscriptionPlan.Builder.createRecurringMonthly(java.time.ZonedDateTime.parse("2007-03-14T00:00:00.000Z")).setTitle("G-Mobile is the carriers name who this plan belongs to").setDataLimit(android.util.DataUnit.GIBIBYTES.toBytes(5L), 2).setDataUsage(android.util.DataUnit.GIBIBYTES.toBytes(6L), java.time.ZonedDateTime.now().minusHours(1L).toInstant().toEpochMilli()).build());
                arrayList.add(android.telephony.SubscriptionPlan.Builder.createRecurringMonthly(java.time.ZonedDateTime.parse("2017-03-14T00:00:00.000Z")).setTitle("G-Mobile, Throttled after limit").setDataLimit(android.util.DataUnit.GIBIBYTES.toBytes(5L), 2).setDataUsage(android.util.DataUnit.GIBIBYTES.toBytes(5L), java.time.ZonedDateTime.now().minusHours(1L).toInstant().toEpochMilli()).build());
                arrayList.add(android.telephony.SubscriptionPlan.Builder.createRecurringMonthly(java.time.ZonedDateTime.parse("2017-03-14T00:00:00.000Z")).setTitle("G-Mobile, No data connection after limit").setDataLimit(android.util.DataUnit.GIBIBYTES.toBytes(5L), 0).setDataUsage(android.util.DataUnit.GIBIBYTES.toBytes(5L), java.time.ZonedDateTime.now().minusHours(1L).toInstant().toEpochMilli()).build());
            } else if ("month_none".equals(str2)) {
                arrayList.add(android.telephony.SubscriptionPlan.Builder.createRecurringMonthly(java.time.ZonedDateTime.parse("2007-03-14T00:00:00.000Z")).setTitle("G-Mobile").build());
            } else if ("prepaid".equals(str2)) {
                arrayList.add(android.telephony.SubscriptionPlan.Builder.createNonrecurring(java.time.ZonedDateTime.now().minusDays(20L), java.time.ZonedDateTime.now().plusDays(10L)).setTitle("G-Mobile").setDataLimit(android.util.DataUnit.MEBIBYTES.toBytes(512L), 0).setDataUsage(android.util.DataUnit.MEBIBYTES.toBytes(100L), java.time.ZonedDateTime.now().minusHours(3L).toInstant().toEpochMilli()).build());
            } else if ("prepaid_crazy".equals(str2)) {
                arrayList.add(android.telephony.SubscriptionPlan.Builder.createNonrecurring(java.time.ZonedDateTime.now().minusDays(20L), java.time.ZonedDateTime.now().plusDays(10L)).setTitle("G-Mobile Anytime").setDataLimit(android.util.DataUnit.MEBIBYTES.toBytes(512L), 0).setDataUsage(android.util.DataUnit.MEBIBYTES.toBytes(100L), java.time.ZonedDateTime.now().minusHours(3L).toInstant().toEpochMilli()).build());
                arrayList.add(android.telephony.SubscriptionPlan.Builder.createNonrecurring(java.time.ZonedDateTime.now().minusDays(10L), java.time.ZonedDateTime.now().plusDays(20L)).setTitle("G-Mobile Nickel Nights").setSummary("5¢/GB between 1-5AM").setDataLimit(android.util.DataUnit.GIBIBYTES.toBytes(5L), 2).setDataUsage(android.util.DataUnit.MEBIBYTES.toBytes(15L), java.time.ZonedDateTime.now().minusHours(30L).toInstant().toEpochMilli()).build());
                arrayList.add(android.telephony.SubscriptionPlan.Builder.createNonrecurring(java.time.ZonedDateTime.now().minusDays(10L), java.time.ZonedDateTime.now().plusDays(20L)).setTitle("G-Mobile Bonus 3G").setSummary("Unlimited 3G data").setDataLimit(android.util.DataUnit.GIBIBYTES.toBytes(1L), 2).setDataUsage(android.util.DataUnit.MEBIBYTES.toBytes(300L), java.time.ZonedDateTime.now().minusHours(1L).toInstant().toEpochMilli()).build());
            } else if ("unlimited".equals(str2)) {
                arrayList.add(android.telephony.SubscriptionPlan.Builder.createNonrecurring(java.time.ZonedDateTime.now().minusDays(20L), java.time.ZonedDateTime.now().plusDays(10L)).setTitle("G-Mobile Awesome").setDataLimit(com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME, 2).setDataUsage(android.util.DataUnit.MEBIBYTES.toBytes(50L), java.time.ZonedDateTime.now().minusHours(3L).toInstant().toEpochMilli()).build());
            }
            return (android.telephony.SubscriptionPlan[]) arrayList.toArray(new android.telephony.SubscriptionPlan[arrayList.size()]);
        }
        synchronized (this.mNetworkPoliciesSecondLock) {
            try {
                java.lang.String str3 = this.mSubscriptionPlansOwner.get(i);
                if (!java.util.Objects.equals(str3, str) && android.os.UserHandle.getCallingAppId() != 1000 && android.os.UserHandle.getCallingAppId() != 1001) {
                    android.util.Log.w(TAG, "Not returning plans because caller " + str + " doesn't match owner " + str3);
                    return null;
                }
                return this.mSubscriptionPlans.get(i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void setSubscriptionPlans(int i, android.telephony.SubscriptionPlan[] subscriptionPlanArr, long j, java.lang.String str) {
        enforceSubscriptionPlanAccess(i, android.os.Binder.getCallingUid(), str);
        enforceSubscriptionPlanValidity(subscriptionPlanArr);
        for (android.telephony.SubscriptionPlan subscriptionPlan : subscriptionPlanArr) {
            java.util.Objects.requireNonNull(subscriptionPlan);
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            setSubscriptionPlansInternal(i, subscriptionPlanArr, j, str);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSubscriptionPlansInternal(int i, android.telephony.SubscriptionPlan[] subscriptionPlanArr, long j, java.lang.String str) {
        synchronized (this.mUidRulesFirstLock) {
            synchronized (this.mNetworkPoliciesSecondLock) {
                try {
                    this.mSubscriptionPlans.put(i, subscriptionPlanArr);
                    this.mSubscriptionPlansOwner.put(i, str);
                    java.lang.String str2 = this.mSubIdToSubscriberId.get(i, null);
                    if (str2 != null) {
                        ensureActiveCarrierPolicyAL(i, str2);
                        maybeUpdateCarrierPolicyCycleAL(i, str2);
                    } else {
                        android.util.Slog.wtf(TAG, "Missing subscriberId for subId " + i);
                    }
                    handleNetworkPoliciesUpdateAL(true);
                    android.content.Intent intent = new android.content.Intent("android.telephony.action.SUBSCRIPTION_PLANS_CHANGED");
                    intent.addFlags(1073741824);
                    intent.putExtra("android.telephony.extra.SUBSCRIPTION_INDEX", i);
                    this.mContext.sendBroadcast(intent, "android.permission.MANAGE_SUBSCRIPTION_PLANS");
                    this.mHandler.sendMessage(this.mHandler.obtainMessage(19, i, 0, subscriptionPlanArr));
                    int i2 = this.mSetSubscriptionPlansIdCounter;
                    this.mSetSubscriptionPlansIdCounter = i2 + 1;
                    this.mSetSubscriptionPlansIds.put(i, i2);
                    if (j > 0) {
                        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(22, i, i2, str), j);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    void setSubscriptionPlansOwner(int i, java.lang.String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.NETWORK_SETTINGS", TAG);
        android.os.SystemProperties.set("persist.sys.sub_plan_owner." + i, str);
    }

    public java.lang.String getSubscriptionPlansOwner(int i) {
        java.lang.String str;
        if (android.os.UserHandle.getCallingAppId() != 1000) {
            throw new java.lang.SecurityException();
        }
        synchronized (this.mNetworkPoliciesSecondLock) {
            str = this.mSubscriptionPlansOwner.get(i);
        }
        return str;
    }

    public void setSubscriptionOverride(int i, int i2, int i3, int[] iArr, long j, java.lang.String str) {
        enforceSubscriptionPlanAccess(i, android.os.Binder.getCallingUid(), str);
        android.util.ArraySet arraySet = new android.util.ArraySet();
        addAll(arraySet, android.telephony.TelephonyManager.getAllNetworkTypes());
        android.util.IntArray intArray = new android.util.IntArray();
        for (int i4 : iArr) {
            if (arraySet.contains(java.lang.Integer.valueOf(i4))) {
                intArray.add(i4);
            } else {
                android.util.Log.d(TAG, "setSubscriptionOverride removing invalid network type: " + i4);
            }
        }
        synchronized (this.mNetworkPoliciesSecondLock) {
            android.telephony.SubscriptionPlan primarySubscriptionPlanLocked = getPrimarySubscriptionPlanLocked(i);
            if ((i2 != 1 && primarySubscriptionPlanLocked == null) || primarySubscriptionPlanLocked.getDataLimitBehavior() == -1) {
                throw new java.lang.IllegalStateException("Must provide valid SubscriptionPlan to enable overriding");
            }
        }
        if ((android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "netpolicy_override_enabled", 1) != 0) || i3 == 0) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = java.lang.Integer.valueOf(i);
            obtain.arg2 = java.lang.Integer.valueOf(i2);
            obtain.arg3 = java.lang.Integer.valueOf(i3);
            obtain.arg4 = intArray.toArray();
            this.mHandler.sendMessage(this.mHandler.obtainMessage(16, obtain));
            if (j > 0) {
                obtain.arg3 = 0;
                this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(16, obtain), j);
            }
        }
    }

    public int getMultipathPreference(android.net.Network network) {
        com.android.net.module.util.PermissionUtils.enforceNetworkStackPermission(this.mContext);
        java.lang.Integer multipathPreference = this.mMultipathPolicyTracker.getMultipathPreference(network);
        if (multipathPreference != null) {
            return multipathPreference.intValue();
        }
        return 0;
    }

    @dalvik.annotation.optimization.NeverCompile
    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter)) {
            com.android.internal.util.IndentingPrintWriter indentingPrintWriter = new com.android.internal.util.IndentingPrintWriter(printWriter, "  ");
            android.util.ArraySet arraySet = new android.util.ArraySet(strArr.length);
            for (java.lang.String str : strArr) {
                arraySet.add(str);
            }
            synchronized (this.mUidRulesFirstLock) {
                synchronized (this.mNetworkPoliciesSecondLock) {
                    try {
                        if (arraySet.contains("--unsnooze")) {
                            for (int size = this.mNetworkPolicy.size() - 1; size >= 0; size--) {
                                this.mNetworkPolicy.valueAt(size).clearSnooze();
                            }
                            handleNetworkPoliciesUpdateAL(true);
                            indentingPrintWriter.println("Cleared snooze timestamps");
                            return;
                        }
                        indentingPrintWriter.print("System ready: ");
                        indentingPrintWriter.println(this.mSystemReady);
                        indentingPrintWriter.print("Restrict background: ");
                        indentingPrintWriter.println(this.mRestrictBackground);
                        indentingPrintWriter.print("Restrict power: ");
                        indentingPrintWriter.println(this.mRestrictPower);
                        indentingPrintWriter.print("Device idle: ");
                        indentingPrintWriter.println(this.mDeviceIdleMode);
                        indentingPrintWriter.print("Restricted networking mode: ");
                        indentingPrintWriter.println(this.mRestrictedNetworkingMode);
                        indentingPrintWriter.print("Low Power Standby mode: ");
                        indentingPrintWriter.println(this.mLowPowerStandbyActive);
                        synchronized (this.mMeteredIfacesLock) {
                            indentingPrintWriter.print("Metered ifaces: ");
                            indentingPrintWriter.println(this.mMeteredIfaces);
                        }
                        indentingPrintWriter.println();
                        indentingPrintWriter.println("Flags:");
                        indentingPrintWriter.println("Network blocked for TOP_SLEEPING and above: " + this.mBackgroundNetworkRestricted);
                        indentingPrintWriter.println();
                        indentingPrintWriter.println("mRestrictBackgroundLowPowerMode: " + this.mRestrictBackgroundLowPowerMode);
                        indentingPrintWriter.println("mRestrictBackgroundBeforeBsm: " + this.mRestrictBackgroundBeforeBsm);
                        indentingPrintWriter.println("mLoadedRestrictBackground: " + this.mLoadedRestrictBackground);
                        indentingPrintWriter.println("mRestrictBackgroundChangedInBsm: " + this.mRestrictBackgroundChangedInBsm);
                        indentingPrintWriter.println();
                        indentingPrintWriter.println("Network policies:");
                        indentingPrintWriter.increaseIndent();
                        for (int i = 0; i < this.mNetworkPolicy.size(); i++) {
                            indentingPrintWriter.println(this.mNetworkPolicy.valueAt(i).toString());
                        }
                        indentingPrintWriter.decreaseIndent();
                        indentingPrintWriter.println();
                        indentingPrintWriter.println("Subscription plans:");
                        indentingPrintWriter.increaseIndent();
                        for (int i2 = 0; i2 < this.mSubscriptionPlans.size(); i2++) {
                            indentingPrintWriter.println("Subscriber ID " + this.mSubscriptionPlans.keyAt(i2) + ":");
                            indentingPrintWriter.increaseIndent();
                            android.telephony.SubscriptionPlan[] valueAt = this.mSubscriptionPlans.valueAt(i2);
                            if (!com.android.internal.util.ArrayUtils.isEmpty(valueAt)) {
                                for (android.telephony.SubscriptionPlan subscriptionPlan : valueAt) {
                                    indentingPrintWriter.println(subscriptionPlan);
                                }
                            }
                            indentingPrintWriter.decreaseIndent();
                        }
                        indentingPrintWriter.decreaseIndent();
                        indentingPrintWriter.println();
                        indentingPrintWriter.println("Active subscriptions:");
                        indentingPrintWriter.increaseIndent();
                        for (int i3 = 0; i3 < this.mSubIdToSubscriberId.size(); i3++) {
                            indentingPrintWriter.println(this.mSubIdToSubscriberId.keyAt(i3) + "=" + com.android.net.module.util.NetworkIdentityUtils.scrubSubscriberId(this.mSubIdToSubscriberId.valueAt(i3)));
                        }
                        indentingPrintWriter.decreaseIndent();
                        indentingPrintWriter.println();
                        java.util.Iterator<java.lang.String[]> it = this.mMergedSubscriberIds.iterator();
                        while (it.hasNext()) {
                            indentingPrintWriter.println("Merged subscriptions: " + java.util.Arrays.toString(com.android.net.module.util.NetworkIdentityUtils.scrubSubscriberIds(it.next())));
                        }
                        indentingPrintWriter.println();
                        indentingPrintWriter.println("Policy for UIDs:");
                        indentingPrintWriter.increaseIndent();
                        int size2 = this.mUidPolicy.size();
                        for (int i4 = 0; i4 < size2; i4++) {
                            int keyAt = this.mUidPolicy.keyAt(i4);
                            int valueAt2 = this.mUidPolicy.valueAt(i4);
                            indentingPrintWriter.print("UID=");
                            indentingPrintWriter.print(keyAt);
                            indentingPrintWriter.print(" policy=");
                            indentingPrintWriter.print(android.net.NetworkPolicyManager.uidPoliciesToString(valueAt2));
                            indentingPrintWriter.println();
                        }
                        indentingPrintWriter.decreaseIndent();
                        int size3 = this.mPowerSaveWhitelistExceptIdleAppIds.size();
                        if (size3 > 0) {
                            indentingPrintWriter.println("Power save whitelist (except idle) app ids:");
                            indentingPrintWriter.increaseIndent();
                            for (int i5 = 0; i5 < size3; i5++) {
                                indentingPrintWriter.print("UID=");
                                indentingPrintWriter.print(this.mPowerSaveWhitelistExceptIdleAppIds.keyAt(i5));
                                indentingPrintWriter.print(": ");
                                indentingPrintWriter.print(this.mPowerSaveWhitelistExceptIdleAppIds.valueAt(i5));
                                indentingPrintWriter.println();
                            }
                            indentingPrintWriter.decreaseIndent();
                        }
                        int size4 = this.mPowerSaveWhitelistAppIds.size();
                        if (size4 > 0) {
                            indentingPrintWriter.println("Power save whitelist app ids:");
                            indentingPrintWriter.increaseIndent();
                            for (int i6 = 0; i6 < size4; i6++) {
                                indentingPrintWriter.print("UID=");
                                indentingPrintWriter.print(this.mPowerSaveWhitelistAppIds.keyAt(i6));
                                indentingPrintWriter.print(": ");
                                indentingPrintWriter.print(this.mPowerSaveWhitelistAppIds.valueAt(i6));
                                indentingPrintWriter.println();
                            }
                            indentingPrintWriter.decreaseIndent();
                        }
                        int size5 = this.mAppIdleTempWhitelistAppIds.size();
                        if (size5 > 0) {
                            indentingPrintWriter.println("App idle whitelist app ids:");
                            indentingPrintWriter.increaseIndent();
                            for (int i7 = 0; i7 < size5; i7++) {
                                indentingPrintWriter.print("UID=");
                                indentingPrintWriter.print(this.mAppIdleTempWhitelistAppIds.keyAt(i7));
                                indentingPrintWriter.print(": ");
                                indentingPrintWriter.print(this.mAppIdleTempWhitelistAppIds.valueAt(i7));
                                indentingPrintWriter.println();
                            }
                            indentingPrintWriter.decreaseIndent();
                        }
                        int size6 = this.mDefaultRestrictBackgroundAllowlistUids.size();
                        if (size6 > 0) {
                            indentingPrintWriter.println("Default restrict background allowlist uids:");
                            indentingPrintWriter.increaseIndent();
                            for (int i8 = 0; i8 < size6; i8++) {
                                indentingPrintWriter.print("UID=");
                                indentingPrintWriter.print(this.mDefaultRestrictBackgroundAllowlistUids.keyAt(i8));
                                indentingPrintWriter.println();
                            }
                            indentingPrintWriter.decreaseIndent();
                        }
                        int size7 = this.mRestrictBackgroundAllowlistRevokedUids.size();
                        if (size7 > 0) {
                            indentingPrintWriter.println("Default restrict background allowlist uids revoked by users:");
                            indentingPrintWriter.increaseIndent();
                            for (int i9 = 0; i9 < size7; i9++) {
                                indentingPrintWriter.print("UID=");
                                indentingPrintWriter.print(this.mRestrictBackgroundAllowlistRevokedUids.keyAt(i9));
                                indentingPrintWriter.println();
                            }
                            indentingPrintWriter.decreaseIndent();
                        }
                        int size8 = this.mLowPowerStandbyAllowlistUids.size();
                        if (size8 > 0) {
                            indentingPrintWriter.println("Low Power Standby allowlist uids:");
                            indentingPrintWriter.increaseIndent();
                            for (int i10 = 0; i10 < size8; i10++) {
                                indentingPrintWriter.print("UID=");
                                indentingPrintWriter.print(this.mLowPowerStandbyAllowlistUids.keyAt(i10));
                                indentingPrintWriter.println();
                            }
                            indentingPrintWriter.decreaseIndent();
                        }
                        int size9 = this.mBackgroundTransitioningUids.size();
                        if (size9 > 0) {
                            long uptimeMillis = android.os.SystemClock.uptimeMillis();
                            indentingPrintWriter.println("Uids transitioning to background:");
                            indentingPrintWriter.increaseIndent();
                            for (int i11 = 0; i11 < size9; i11++) {
                                indentingPrintWriter.print("UID=");
                                indentingPrintWriter.print(this.mBackgroundTransitioningUids.keyAt(i11));
                                indentingPrintWriter.print(", ");
                                android.util.TimeUtils.formatDuration(this.mBackgroundTransitioningUids.valueAt(i11), uptimeMillis, indentingPrintWriter);
                                indentingPrintWriter.println();
                            }
                            indentingPrintWriter.decreaseIndent();
                        }
                        android.util.SparseBooleanArray sparseBooleanArray = new android.util.SparseBooleanArray();
                        collectKeys(this.mUidState, sparseBooleanArray);
                        synchronized (this.mUidBlockedState) {
                            collectKeys(this.mUidBlockedState, sparseBooleanArray);
                        }
                        synchronized (this.mUidStateCallbackInfos) {
                            collectKeys(this.mUidStateCallbackInfos, sparseBooleanArray);
                        }
                        indentingPrintWriter.println("Status for all known UIDs:");
                        indentingPrintWriter.increaseIndent();
                        int size10 = sparseBooleanArray.size();
                        for (int i12 = 0; i12 < size10; i12++) {
                            int keyAt2 = sparseBooleanArray.keyAt(i12);
                            indentingPrintWriter.print("UID", java.lang.Integer.valueOf(keyAt2));
                            indentingPrintWriter.print("state", this.mUidState.get(keyAt2));
                            synchronized (this.mUidBlockedState) {
                                indentingPrintWriter.print("blocked_state", this.mUidBlockedState.get(keyAt2));
                            }
                            synchronized (this.mUidStateCallbackInfos) {
                                com.android.server.net.NetworkPolicyManagerService.UidStateCallbackInfo uidStateCallbackInfo = this.mUidStateCallbackInfos.get(keyAt2);
                                indentingPrintWriter.println();
                                indentingPrintWriter.increaseIndent();
                                indentingPrintWriter.print("callback_info", uidStateCallbackInfo);
                                indentingPrintWriter.decreaseIndent();
                            }
                            indentingPrintWriter.println();
                        }
                        indentingPrintWriter.decreaseIndent();
                        indentingPrintWriter.println();
                        indentingPrintWriter.println("Admin restricted uids for metered data:");
                        indentingPrintWriter.increaseIndent();
                        int size11 = this.mMeteredRestrictedUids.size();
                        for (int i13 = 0; i13 < size11; i13++) {
                            indentingPrintWriter.print("u" + this.mMeteredRestrictedUids.keyAt(i13) + ": ");
                            indentingPrintWriter.println(this.mMeteredRestrictedUids.valueAt(i13));
                        }
                        indentingPrintWriter.decreaseIndent();
                        indentingPrintWriter.println();
                        indentingPrintWriter.println("Network to interfaces:");
                        indentingPrintWriter.increaseIndent();
                        for (int i14 = 0; i14 < this.mNetworkToIfaces.size(); i14++) {
                            int keyAt3 = this.mNetworkToIfaces.keyAt(i14);
                            indentingPrintWriter.println(keyAt3 + ": " + this.mNetworkToIfaces.get(keyAt3));
                        }
                        indentingPrintWriter.decreaseIndent();
                        indentingPrintWriter.println();
                        indentingPrintWriter.print("Active notifications: ");
                        indentingPrintWriter.println(this.mActiveNotifs);
                        indentingPrintWriter.println();
                        this.mStatLogger.dump(indentingPrintWriter);
                        this.mLogger.dumpLogs(indentingPrintWriter);
                        indentingPrintWriter.println();
                        this.mMultipathPolicyTracker.dump(indentingPrintWriter);
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int handleShellCommand(@android.annotation.NonNull android.os.ParcelFileDescriptor parcelFileDescriptor, @android.annotation.NonNull android.os.ParcelFileDescriptor parcelFileDescriptor2, @android.annotation.NonNull android.os.ParcelFileDescriptor parcelFileDescriptor3, @android.annotation.NonNull java.lang.String[] strArr) {
        return new com.android.server.net.NetworkPolicyManagerShellCommand(this.mContext, this).exec(this, parcelFileDescriptor.getFileDescriptor(), parcelFileDescriptor2.getFileDescriptor(), parcelFileDescriptor3.getFileDescriptor(), strArr);
    }

    void setDebugUid(int i) {
        this.mLogger.setDebugUid(i);
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    @com.android.internal.annotations.VisibleForTesting
    boolean isUidForegroundOnRestrictBackgroundUL(int i) {
        android.net.NetworkPolicyManager.UidState uidState = this.mUidState.get(i);
        if (android.net.NetworkPolicyManager.isProcStateAllowedWhileOnRestrictBackground(uidState)) {
            return true;
        }
        synchronized (this.mUidStateCallbackInfos) {
            try {
                com.android.server.net.NetworkPolicyManagerService.UidStateCallbackInfo uidStateCallbackInfo = this.mUidStateCallbackInfos.get(i);
                long j = uidState != null ? uidState.procStateSeq : -1L;
                if (uidStateCallbackInfo != null && uidStateCallbackInfo.isPending && uidStateCallbackInfo.procStateSeq >= j) {
                    return android.net.NetworkPolicyManager.isProcStateAllowedWhileOnRestrictBackground(uidStateCallbackInfo.procState, uidStateCallbackInfo.capability);
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    @com.android.internal.annotations.VisibleForTesting
    boolean isUidForegroundOnRestrictPowerUL(int i) {
        android.net.NetworkPolicyManager.UidState uidState = this.mUidState.get(i);
        if (android.net.NetworkPolicyManager.isProcStateAllowedWhileIdleOrPowerSaveMode(uidState)) {
            return true;
        }
        synchronized (this.mUidStateCallbackInfos) {
            try {
                com.android.server.net.NetworkPolicyManagerService.UidStateCallbackInfo uidStateCallbackInfo = this.mUidStateCallbackInfos.get(i);
                long j = uidState != null ? uidState.procStateSeq : -1L;
                if (uidStateCallbackInfo != null && uidStateCallbackInfo.isPending && uidStateCallbackInfo.procStateSeq >= j) {
                    return android.net.NetworkPolicyManager.isProcStateAllowedWhileIdleOrPowerSaveMode(uidStateCallbackInfo.procState, uidStateCallbackInfo.capability);
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    private boolean isUidTop(int i) {
        return android.net.NetworkPolicyManager.isProcStateAllowedWhileInLowPowerStandby(this.mUidState.get(i));
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    private boolean isUidExemptFromBackgroundRestrictions(int i) {
        return this.mBackgroundTransitioningUids.indexOfKey(i) >= 0 || android.net.NetworkPolicyManager.isProcStateAllowedNetworkWhileBackground(this.mUidState.get(i));
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    private boolean updateUidStateUL(int i, int i2, long j, int i3) {
        boolean z;
        android.os.Trace.traceBegin(2097152L, "updateUidStateUL");
        try {
            android.net.NetworkPolicyManager.UidState uidState = this.mUidState.get(i);
            if (uidState != null && j < uidState.procStateSeq) {
                if (LOGV) {
                    android.util.Slog.v(TAG, "Ignoring older uid state updates; uid=" + i + ",procState=" + android.app.ActivityManager.procStateToString(i2) + ",seq=" + j + ",cap=" + i3 + ",oldUidState=" + uidState);
                }
                return false;
            }
            if (uidState != null && uidState.procState == i2 && uidState.capability == i3) {
                return false;
            }
            android.net.NetworkPolicyManager.UidState uidState2 = new android.net.NetworkPolicyManager.UidState(i, i2, j, i3);
            this.mUidState.put(i, uidState2);
            updateRestrictBackgroundRulesOnUidStatusChangedUL(i, uidState, uidState2);
            if (android.net.NetworkPolicyManager.isProcStateAllowedWhileIdleOrPowerSaveMode(uidState) != android.net.NetworkPolicyManager.isProcStateAllowedWhileIdleOrPowerSaveMode(uidState2)) {
                updateRuleForAppIdleUL(i, i2);
                if (this.mDeviceIdleMode) {
                    updateRuleForDeviceIdleUL(i);
                }
                if (this.mRestrictPower) {
                    updateRuleForRestrictPowerUL(i);
                }
                z = true;
            } else {
                z = false;
            }
            if (this.mBackgroundNetworkRestricted) {
                boolean isProcStateAllowedNetworkWhileBackground = android.net.NetworkPolicyManager.isProcStateAllowedNetworkWhileBackground(uidState);
                boolean isProcStateAllowedNetworkWhileBackground2 = android.net.NetworkPolicyManager.isProcStateAllowedNetworkWhileBackground(uidState2);
                if (!isProcStateAllowedNetworkWhileBackground && isProcStateAllowedNetworkWhileBackground2) {
                    this.mBackgroundTransitioningUids.delete(i);
                    updateRuleForBackgroundUL(i);
                    z = true;
                } else if (isProcStateAllowedNetworkWhileBackground && !isProcStateAllowedNetworkWhileBackground2) {
                    long uptimeMillis = android.os.SystemClock.uptimeMillis() + this.mBackgroundRestrictionDelayMs;
                    if (this.mBackgroundTransitioningUids.indexOfKey(i) < 0) {
                        this.mBackgroundTransitioningUids.put(i, uptimeMillis);
                    }
                    if (!this.mHandler.hasMessages(24)) {
                        this.mHandler.sendEmptyMessageAtTime(24, uptimeMillis);
                    }
                }
            }
            if (this.mLowPowerStandbyActive) {
                if (android.net.NetworkPolicyManager.isProcStateAllowedWhileInLowPowerStandby(uidState) != android.net.NetworkPolicyManager.isProcStateAllowedWhileInLowPowerStandby(uidState2)) {
                    updateRuleForLowPowerStandbyUL(i);
                    z = true;
                }
            }
            if (z) {
                updateRulesForPowerRestrictionsUL(i, i2);
            }
            return true;
        } finally {
            android.os.Trace.traceEnd(2097152L);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    private boolean removeUidStateUL(int i) {
        int indexOfKey = this.mUidState.indexOfKey(i);
        if (indexOfKey >= 0) {
            android.net.NetworkPolicyManager.UidState valueAt = this.mUidState.valueAt(indexOfKey);
            this.mUidState.removeAt(indexOfKey);
            if (valueAt != null) {
                updateRestrictBackgroundRulesOnUidStatusChangedUL(i, valueAt, null);
                if (this.mDeviceIdleMode) {
                    updateRuleForDeviceIdleUL(i);
                }
                if (this.mRestrictPower) {
                    updateRuleForRestrictPowerUL(i);
                }
                if (this.mBackgroundNetworkRestricted) {
                    this.mBackgroundTransitioningUids.delete(i);
                    updateRuleForBackgroundUL(i);
                }
                lambda$updateRulesForRestrictPowerUL$5(i);
                if (this.mLowPowerStandbyActive) {
                    updateRuleForLowPowerStandbyUL(i);
                    return true;
                }
                return true;
            }
            return false;
        }
        return false;
    }

    private void updateNetworkStats(int i, boolean z) {
        if (android.os.Trace.isTagEnabled(2097152L)) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("updateNetworkStats: ");
            sb.append(i);
            sb.append(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
            sb.append(z ? "F" : "B");
            android.os.Trace.traceBegin(2097152L, sb.toString());
        }
        try {
            this.mNetworkStats.noteUidForeground(i, z);
        } finally {
            android.os.Trace.traceEnd(2097152L);
        }
    }

    private void updateRestrictBackgroundRulesOnUidStatusChangedUL(int i, @android.annotation.Nullable android.net.NetworkPolicyManager.UidState uidState, @android.annotation.Nullable android.net.NetworkPolicyManager.UidState uidState2) {
        if (android.net.NetworkPolicyManager.isProcStateAllowedWhileOnRestrictBackground(uidState) != android.net.NetworkPolicyManager.isProcStateAllowedWhileOnRestrictBackground(uidState2)) {
            lambda$updateRulesForRestrictBackgroundUL$6(i);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isRestrictedModeEnabled() {
        boolean z;
        synchronized (this.mUidRulesFirstLock) {
            z = this.mRestrictedNetworkingMode;
        }
        return z;
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    @com.android.internal.annotations.VisibleForTesting
    void updateRestrictedModeAllowlistUL() {
        this.mUidFirewallRestrictedModeRules.clear();
        forEachUid("updateRestrictedModeAllowlist", new java.util.function.IntConsumer() { // from class: com.android.server.net.NetworkPolicyManagerService$$ExternalSyntheticLambda6
            @Override // java.util.function.IntConsumer
            public final void accept(int i) {
                com.android.server.net.NetworkPolicyManagerService.this.lambda$updateRestrictedModeAllowlistUL$3(i);
            }
        });
        if (this.mRestrictedNetworkingMode) {
            setUidFirewallRulesUL(4, this.mUidFirewallRestrictedModeRules);
        }
        enableFirewallChainUL(4, this.mRestrictedNetworkingMode);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateRestrictedModeAllowlistUL$3(int i) {
        synchronized (this.mUidRulesFirstLock) {
            try {
                int restrictedModeFirewallRule = getRestrictedModeFirewallRule(updateBlockedReasonsForRestrictedModeUL(i));
                if (restrictedModeFirewallRule != 0) {
                    this.mUidFirewallRestrictedModeRules.append(i, restrictedModeFirewallRule);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    @com.android.internal.annotations.VisibleForTesting
    void updateRestrictedModeForUidUL(int i) {
        int updateBlockedReasonsForRestrictedModeUL = updateBlockedReasonsForRestrictedModeUL(i);
        if (this.mRestrictedNetworkingMode) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                setUidFirewallRuleUL(4, i, getRestrictedModeFirewallRule(updateBlockedReasonsForRestrictedModeUL));
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    private int updateBlockedReasonsForRestrictedModeUL(int i) {
        int i2;
        int i3;
        int i4 = 0;
        boolean z = hasRestrictedModeAccess(i) || !isUidBlockedOnAllNetworks(i);
        synchronized (this.mUidBlockedState) {
            try {
                com.android.server.net.NetworkPolicyManagerService.UidBlockedState orCreateUidBlockedStateForUid = getOrCreateUidBlockedStateForUid(this.mUidBlockedState, i);
                i2 = orCreateUidBlockedStateForUid.effectiveBlockedReasons;
                if (this.mRestrictedNetworkingMode) {
                    orCreateUidBlockedStateForUid.blockedReasons |= 8;
                } else {
                    orCreateUidBlockedStateForUid.blockedReasons &= -9;
                }
                if (z || !hasInternetPermissionUL(i)) {
                    orCreateUidBlockedStateForUid.allowedReasons |= 16;
                } else {
                    orCreateUidBlockedStateForUid.allowedReasons &= -17;
                }
                orCreateUidBlockedStateForUid.updateEffectiveBlockedReasons();
                i3 = orCreateUidBlockedStateForUid.effectiveBlockedReasons;
                if (i2 != i3) {
                    i4 = orCreateUidBlockedStateForUid.deriveUidRules();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (i2 != i3) {
            handleBlockedReasonsChanged(i, i3, i2);
            postUidRulesChangedMsg(i, i4);
        }
        return i3;
    }

    private static int getRestrictedModeFirewallRule(int i) {
        if ((i & 8) != 0) {
            return 0;
        }
        return 1;
    }

    private boolean isUidBlockedOnAllNetworks(int i) {
        return (getUidPolicy(i) & 262144) == 262144;
    }

    private boolean hasRestrictedModeAccess(int i) {
        try {
            if (this.mIPm.checkUidPermission("android.permission.CONNECTIVITY_USE_RESTRICTED_NETWORKS", i) != 0 && this.mIPm.checkUidPermission("android.permission.NETWORK_STACK", i) != 0) {
                if (this.mIPm.checkUidPermission("android.permission.MAINLINE_NETWORK_STACK", i) != 0) {
                    return false;
                }
            }
            return true;
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    void updateRulesForPowerSaveUL() {
        android.os.Trace.traceBegin(2097152L, "updateRulesForPowerSaveUL");
        try {
            updateRulesForAllowlistedPowerSaveUL(this.mRestrictPower, 3, this.mUidFirewallPowerSaveRules);
        } finally {
            android.os.Trace.traceEnd(2097152L);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    void updateRuleForRestrictPowerUL(int i) {
        updateRulesForAllowlistedPowerSaveUL(i, this.mRestrictPower, 3);
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    void updateRulesForDeviceIdleUL() {
        android.os.Trace.traceBegin(2097152L, "updateRulesForDeviceIdleUL");
        try {
            updateRulesForAllowlistedPowerSaveUL(this.mDeviceIdleMode, 1, this.mUidFirewallDozableRules);
        } finally {
            android.os.Trace.traceEnd(2097152L);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    void updateRuleForDeviceIdleUL(int i) {
        updateRulesForAllowlistedPowerSaveUL(i, this.mDeviceIdleMode, 1);
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    private void updateRulesForAllowlistedPowerSaveUL(boolean z, int i, android.util.SparseIntArray sparseIntArray) {
        if (z) {
            sparseIntArray.clear();
            java.util.List users = this.mUserManager.getUsers();
            for (int size = users.size() - 1; size >= 0; size--) {
                android.content.pm.UserInfo userInfo = (android.content.pm.UserInfo) users.get(size);
                updateRulesForAllowlistedAppIds(sparseIntArray, this.mPowerSaveTempWhitelistAppIds, userInfo.id);
                updateRulesForAllowlistedAppIds(sparseIntArray, this.mPowerSaveWhitelistAppIds, userInfo.id);
                if (i == 3) {
                    updateRulesForAllowlistedAppIds(sparseIntArray, this.mPowerSaveWhitelistExceptIdleAppIds, userInfo.id);
                }
            }
            for (int size2 = this.mUidState.size() - 1; size2 >= 0; size2--) {
                if (android.net.NetworkPolicyManager.isProcStateAllowedWhileIdleOrPowerSaveMode(this.mUidState.valueAt(size2))) {
                    sparseIntArray.put(this.mUidState.keyAt(size2), 1);
                }
            }
            setUidFirewallRulesUL(i, sparseIntArray, 1);
            return;
        }
        setUidFirewallRulesUL(i, null, 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    public void updateRulesForBackgroundChainUL() {
        android.os.Trace.traceBegin(2097152L, "updateRulesForBackgroundChainUL");
        try {
            android.util.SparseIntArray sparseIntArray = this.mUidFirewallBackgroundRules;
            sparseIntArray.clear();
            java.util.List users = this.mUserManager.getUsers();
            for (int size = users.size() - 1; size >= 0; size--) {
                android.content.pm.UserInfo userInfo = (android.content.pm.UserInfo) users.get(size);
                updateRulesForAllowlistedAppIds(sparseIntArray, this.mPowerSaveTempWhitelistAppIds, userInfo.id);
                updateRulesForAllowlistedAppIds(sparseIntArray, this.mPowerSaveWhitelistAppIds, userInfo.id);
                updateRulesForAllowlistedAppIds(sparseIntArray, this.mPowerSaveWhitelistExceptIdleAppIds, userInfo.id);
            }
            for (int size2 = this.mUidState.size() - 1; size2 >= 0; size2--) {
                if (this.mBackgroundTransitioningUids.indexOfKey(this.mUidState.keyAt(size2)) >= 0 || android.net.NetworkPolicyManager.isProcStateAllowedNetworkWhileBackground(this.mUidState.valueAt(size2))) {
                    sparseIntArray.put(this.mUidState.keyAt(size2), 1);
                }
            }
            setUidFirewallRulesUL(6, sparseIntArray);
            android.os.Trace.traceEnd(2097152L);
        } catch (java.lang.Throwable th) {
            android.os.Trace.traceEnd(2097152L);
            throw th;
        }
    }

    private void updateRulesForAllowlistedAppIds(android.util.SparseIntArray sparseIntArray, android.util.SparseBooleanArray sparseBooleanArray, int i) {
        for (int size = sparseBooleanArray.size() - 1; size >= 0; size--) {
            if (sparseBooleanArray.valueAt(size)) {
                sparseIntArray.put(android.os.UserHandle.getUid(i, sparseBooleanArray.keyAt(size)), 1);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    void updateRulesForLowPowerStandbyUL() {
        android.os.Trace.traceBegin(2097152L, "updateRulesForLowPowerStandbyUL");
        try {
            if (this.mLowPowerStandbyActive) {
                this.mUidFirewallLowPowerStandbyModeRules.clear();
                for (int size = this.mUidState.size() - 1; size >= 0; size--) {
                    int keyAt = this.mUidState.keyAt(size);
                    int effectiveBlockedReasons = getEffectiveBlockedReasons(keyAt);
                    if (hasInternetPermissionUL(keyAt) && (effectiveBlockedReasons & 32) == 0) {
                        this.mUidFirewallLowPowerStandbyModeRules.put(keyAt, 1);
                    }
                }
                setUidFirewallRulesUL(5, this.mUidFirewallLowPowerStandbyModeRules, 1);
            } else {
                setUidFirewallRulesUL(5, null, 2);
            }
            android.os.Trace.traceEnd(2097152L);
        } catch (java.lang.Throwable th) {
            android.os.Trace.traceEnd(2097152L);
            throw th;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    void updateRuleForLowPowerStandbyUL(int i) {
        if (!hasInternetPermissionUL(i)) {
            return;
        }
        int effectiveBlockedReasons = getEffectiveBlockedReasons(i);
        if (this.mUidState.contains(i) && (effectiveBlockedReasons & 32) == 0) {
            this.mUidFirewallLowPowerStandbyModeRules.put(i, 1);
            setUidFirewallRuleUL(5, i, 1);
        } else {
            this.mUidFirewallLowPowerStandbyModeRules.delete(i);
            setUidFirewallRuleUL(5, i, 0);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    private boolean isAllowlistedFromPowerSaveUL(int i, boolean z) {
        int appId = android.os.UserHandle.getAppId(i);
        boolean z2 = true;
        boolean z3 = this.mPowerSaveTempWhitelistAppIds.get(appId) || this.mPowerSaveWhitelistAppIds.get(appId);
        if (!z) {
            if (!z3 && !isAllowlistedFromPowerSaveExceptIdleUL(i)) {
                z2 = false;
            }
            return z2;
        }
        return z3;
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    private boolean isAllowlistedFromPowerSaveExceptIdleUL(int i) {
        return this.mPowerSaveWhitelistExceptIdleAppIds.get(android.os.UserHandle.getAppId(i));
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    private boolean isAllowlistedFromLowPowerStandbyUL(int i) {
        return this.mLowPowerStandbyAllowlistUids.get(i);
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    private void updateRulesForAllowlistedPowerSaveUL(int i, boolean z, int i2) {
        if (z) {
            if (isAllowlistedFromPowerSaveUL(i, i2 == 1) || isUidForegroundOnRestrictPowerUL(i)) {
                setUidFirewallRuleUL(i2, i, 1);
            } else {
                setUidFirewallRuleUL(i2, i, 0);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    void updateRulesForAppIdleUL() {
        android.os.Trace.traceBegin(2097152L, "updateRulesForAppIdleUL");
        try {
            android.util.SparseIntArray sparseIntArray = this.mUidFirewallStandbyRules;
            sparseIntArray.clear();
            java.util.List users = this.mUserManager.getUsers();
            for (int size = users.size() - 1; size >= 0; size--) {
                for (int i : this.mUsageStats.getIdleUidsForUser(((android.content.pm.UserInfo) users.get(size)).id)) {
                    if (!this.mPowerSaveTempWhitelistAppIds.get(android.os.UserHandle.getAppId(i), false) && hasInternetPermissionUL(i) && !isUidForegroundOnRestrictPowerUL(i)) {
                        sparseIntArray.put(i, 2);
                    }
                }
            }
            setUidFirewallRulesUL(2, sparseIntArray, 0);
            android.os.Trace.traceEnd(2097152L);
        } catch (java.lang.Throwable th) {
            android.os.Trace.traceEnd(2097152L);
            throw th;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    void updateRuleForAppIdleUL(int i, int i2) {
        if (isUidValidForDenylistRulesUL(i)) {
            if (android.os.Trace.isTagEnabled(2097152L)) {
                android.os.Trace.traceBegin(2097152L, "updateRuleForAppIdleUL: " + i);
            }
            try {
                if (!this.mPowerSaveTempWhitelistAppIds.get(android.os.UserHandle.getAppId(i)) && isUidIdle(i, i2) && !isUidForegroundOnRestrictPowerUL(i)) {
                    setUidFirewallRuleUL(2, i, 2);
                    if (LOGD) {
                        android.util.Log.d(TAG, "updateRuleForAppIdleUL DENY " + i);
                    }
                } else {
                    setUidFirewallRuleUL(2, i, 0);
                    if (LOGD) {
                        android.util.Log.d(TAG, "updateRuleForAppIdleUL " + i + " to DEFAULT");
                    }
                }
                android.os.Trace.traceEnd(2097152L);
            } catch (java.lang.Throwable th) {
                android.os.Trace.traceEnd(2097152L);
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    void updateRuleForBackgroundUL(int i) {
        if (!isUidValidForAllowlistRulesUL(i)) {
            return;
        }
        android.os.Trace.traceBegin(2097152L, "updateRuleForBackgroundUL: " + i);
        try {
            if (!isAllowlistedFromPowerSaveUL(i, false) && !isUidExemptFromBackgroundRestrictions(i)) {
                setUidFirewallRuleUL(6, i, 0);
                if (LOGD) {
                    android.util.Log.d(TAG, "updateRuleForBackgroundUL " + i + " to DEFAULT");
                }
                android.os.Trace.traceEnd(2097152L);
            }
            setUidFirewallRuleUL(6, i, 1);
            if (LOGD) {
                android.util.Log.d(TAG, "updateRuleForBackgroundUL ALLOW " + i);
            }
            android.os.Trace.traceEnd(2097152L);
        } catch (java.lang.Throwable th) {
            android.os.Trace.traceEnd(2097152L);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    public void updateRulesForAppIdleParoleUL() {
        boolean isInParole = this.mAppStandby.isInParole();
        boolean z = !isInParole;
        int size = this.mUidFirewallStandbyRules.size();
        android.util.SparseIntArray sparseIntArray = new android.util.SparseIntArray();
        int i = 0;
        while (true) {
            if (i >= size) {
                break;
            }
            int keyAt = this.mUidFirewallStandbyRules.keyAt(i);
            if (isUidValidForDenylistRulesUL(keyAt)) {
                int blockedReasons = getBlockedReasons(keyAt);
                if (z || (blockedReasons & com.android.server.location.gnss.hal.GnssNative.GNSS_AIDING_TYPE_ALL) != 0) {
                    boolean z2 = !isInParole && isUidIdle(keyAt);
                    if (!z2 || this.mPowerSaveTempWhitelistAppIds.get(android.os.UserHandle.getAppId(keyAt)) || isUidForegroundOnRestrictPowerUL(keyAt)) {
                        this.mUidFirewallStandbyRules.put(keyAt, 0);
                    } else {
                        this.mUidFirewallStandbyRules.put(keyAt, 2);
                        sparseIntArray.put(keyAt, 2);
                    }
                    updateRulesForPowerRestrictionsUL(keyAt, z2);
                }
            }
            i++;
        }
        setUidFirewallRulesUL(2, sparseIntArray, z ? 1 : 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock", "mNetworkPoliciesSecondLock"})
    public void updateRulesForGlobalChangeAL(boolean z) {
        if (android.os.Trace.isTagEnabled(2097152L)) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("updateRulesForGlobalChangeAL: ");
            sb.append(z ? "R" : "-");
            android.os.Trace.traceBegin(2097152L, sb.toString());
        }
        try {
            if (this.mBackgroundNetworkRestricted) {
                updateRulesForBackgroundChainUL();
            }
            updateRulesForAppIdleUL();
            updateRulesForRestrictPowerUL();
            updateRulesForRestrictBackgroundUL();
            updateRestrictedModeAllowlistUL();
            if (z) {
                normalizePoliciesNL();
                updateNetworkRulesNL();
            }
            android.os.Trace.traceEnd(2097152L);
        } catch (java.lang.Throwable th) {
            android.os.Trace.traceEnd(2097152L);
            throw th;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    private void handleDeviceIdleModeChangedUL(boolean z) {
        android.os.Trace.traceBegin(2097152L, "updateRulesForRestrictPowerUL");
        try {
            updateRulesForDeviceIdleUL();
            if (z) {
                forEachUid("updateRulesForRestrictPower", new java.util.function.IntConsumer() { // from class: com.android.server.net.NetworkPolicyManagerService$$ExternalSyntheticLambda7
                    @Override // java.util.function.IntConsumer
                    public final void accept(int i) {
                        com.android.server.net.NetworkPolicyManagerService.this.lambda$handleDeviceIdleModeChangedUL$4(i);
                    }
                });
            } else {
                handleDeviceIdleModeDisabledUL();
            }
            android.os.Trace.traceEnd(2097152L);
        } catch (java.lang.Throwable th) {
            android.os.Trace.traceEnd(2097152L);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleDeviceIdleModeChangedUL$4(int i) {
        synchronized (this.mUidRulesFirstLock) {
            lambda$updateRulesForRestrictPowerUL$5(i);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    private void handleDeviceIdleModeDisabledUL() {
        android.os.Trace.traceBegin(2097152L, "handleDeviceIdleModeDisabledUL");
        try {
            android.util.SparseArray sparseArray = new android.util.SparseArray();
            synchronized (this.mUidBlockedState) {
                try {
                    int size = this.mUidBlockedState.size();
                    for (int i = 0; i < size; i++) {
                        int keyAt = this.mUidBlockedState.keyAt(i);
                        com.android.server.net.NetworkPolicyManagerService.UidBlockedState valueAt = this.mUidBlockedState.valueAt(i);
                        if ((valueAt.blockedReasons & 2) != 0) {
                            valueAt.blockedReasons &= -3;
                            int i2 = valueAt.effectiveBlockedReasons;
                            valueAt.updateEffectiveBlockedReasons();
                            if (LOGV) {
                                android.util.Log.v(TAG, "handleDeviceIdleModeDisabled(" + keyAt + "); newUidBlockedState=" + valueAt + ", oldEffectiveBlockedReasons=" + i2);
                            }
                            if (i2 != valueAt.effectiveBlockedReasons) {
                                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                                obtain.argi1 = i2;
                                obtain.argi2 = valueAt.effectiveBlockedReasons;
                                obtain.argi3 = valueAt.deriveUidRules();
                                sparseArray.append(keyAt, obtain);
                                this.mActivityManagerInternal.onUidBlockedReasonsChanged(keyAt, valueAt.effectiveBlockedReasons);
                            }
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (sparseArray.size() != 0) {
                this.mHandler.obtainMessage(23, sparseArray).sendToTarget();
            }
        } finally {
            android.os.Trace.traceEnd(2097152L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    public void updateRulesForRestrictPowerUL() {
        android.os.Trace.traceBegin(2097152L, "updateRulesForRestrictPowerUL");
        try {
            updateRulesForDeviceIdleUL();
            updateRulesForPowerSaveUL();
            forEachUid("updateRulesForRestrictPower", new java.util.function.IntConsumer() { // from class: com.android.server.net.NetworkPolicyManagerService$$ExternalSyntheticLambda3
                @Override // java.util.function.IntConsumer
                public final void accept(int i) {
                    com.android.server.net.NetworkPolicyManagerService.this.lambda$updateRulesForRestrictPowerUL$5(i);
                }
            });
        } finally {
            android.os.Trace.traceEnd(2097152L);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    private void updateRulesForRestrictBackgroundUL() {
        android.os.Trace.traceBegin(2097152L, "updateRulesForRestrictBackgroundUL");
        try {
            forEachUid("updateRulesForRestrictBackground", new java.util.function.IntConsumer() { // from class: com.android.server.net.NetworkPolicyManagerService$$ExternalSyntheticLambda4
                @Override // java.util.function.IntConsumer
                public final void accept(int i) {
                    com.android.server.net.NetworkPolicyManagerService.this.lambda$updateRulesForRestrictBackgroundUL$6(i);
                }
            });
        } finally {
            android.os.Trace.traceEnd(2097152L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void forEachUid(java.lang.String str, final java.util.function.IntConsumer intConsumer) {
        if (android.os.Trace.isTagEnabled(2097152L)) {
            android.os.Trace.traceBegin(2097152L, "forEachUid-" + str);
        }
        try {
            android.os.Trace.traceBegin(2097152L, "list-users");
            java.util.List users = this.mUserManager.getUsers();
            android.os.Trace.traceEnd(2097152L);
            android.os.Trace.traceBegin(2097152L, "iterate-uids");
            android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
            int size = users.size();
            for (int i = 0; i < size; i++) {
                final int i2 = ((android.content.pm.UserInfo) users.get(i)).id;
                final android.util.SparseBooleanArray sparseBooleanArray = new android.util.SparseBooleanArray();
                packageManagerInternal.forEachInstalledPackage(new java.util.function.Consumer() { // from class: com.android.server.net.NetworkPolicyManagerService$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.net.NetworkPolicyManagerService.lambda$forEachUid$7(sparseBooleanArray, i2, intConsumer, (com.android.server.pm.pkg.AndroidPackage) obj);
                    }
                }, i2);
            }
            android.os.Trace.traceEnd(2097152L);
        } catch (java.lang.Throwable th) {
            throw th;
        } finally {
            android.os.Trace.traceEnd(2097152L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$forEachUid$7(android.util.SparseBooleanArray sparseBooleanArray, int i, java.util.function.IntConsumer intConsumer, com.android.server.pm.pkg.AndroidPackage androidPackage) {
        int uid = androidPackage.getUid();
        if (androidPackage.getSharedUserId() != null) {
            if (sparseBooleanArray.indexOfKey(uid) < 0) {
                sparseBooleanArray.put(uid, true);
            } else {
                return;
            }
        }
        intConsumer.accept(android.os.UserHandle.getUid(i, uid));
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    public void updateRulesForTempAllowlistChangeUL(int i) {
        java.util.List users = this.mUserManager.getUsers();
        int size = users.size();
        for (int i2 = 0; i2 < size; i2++) {
            int uid = android.os.UserHandle.getUid(((android.content.pm.UserInfo) users.get(i2)).id, i);
            updateRuleForAppIdleUL(uid, -1);
            updateRuleForDeviceIdleUL(uid);
            updateRuleForRestrictPowerUL(uid);
            if (this.mBackgroundNetworkRestricted) {
                updateRuleForBackgroundUL(uid);
            }
            lambda$updateRulesForRestrictPowerUL$5(uid);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    private boolean isUidValidForDenylistRulesUL(int i) {
        if (i == 1013 || i == 1019 || isUidValidForAllowlistRulesUL(i)) {
            return true;
        }
        return false;
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    private boolean isUidValidForAllowlistRulesUL(int i) {
        return android.os.UserHandle.isApp(i) && hasInternetPermissionUL(i);
    }

    @com.android.internal.annotations.VisibleForTesting
    void setAppIdleWhitelist(int i, boolean z) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_NETWORK_POLICY", TAG);
        synchronized (this.mUidRulesFirstLock) {
            try {
                if (this.mAppIdleTempWhitelistAppIds.get(i) == z) {
                    return;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mLogger.appIdleWlChanged(i, z);
                    if (z) {
                        this.mAppIdleTempWhitelistAppIds.put(i, true);
                    } else {
                        this.mAppIdleTempWhitelistAppIds.delete(i);
                    }
                    updateRuleForAppIdleUL(i, -1);
                    lambda$updateRulesForRestrictPowerUL$5(i);
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

    @com.android.internal.annotations.VisibleForTesting
    int[] getAppIdleWhitelist() {
        int[] iArr;
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_NETWORK_POLICY", TAG);
        synchronized (this.mUidRulesFirstLock) {
            try {
                int size = this.mAppIdleTempWhitelistAppIds.size();
                iArr = new int[size];
                for (int i = 0; i < size; i++) {
                    iArr[i] = this.mAppIdleTempWhitelistAppIds.keyAt(i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return iArr;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isUidIdle(int i) {
        return isUidIdle(i, -1);
    }

    private boolean isUidIdle(int i, int i2) {
        synchronized (this.mUidRulesFirstLock) {
            if (i2 != -1) {
                try {
                    if (android.app.ActivityManager.isProcStateConsideredInteraction(i2)) {
                        return false;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (this.mAppIdleTempWhitelistAppIds.get(i)) {
                return false;
            }
            java.lang.String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(i);
            int userId = android.os.UserHandle.getUserId(i);
            if (packagesForUid != null) {
                for (java.lang.String str : packagesForUid) {
                    if (!this.mUsageStats.isAppIdle(str, i, userId)) {
                        return false;
                    }
                }
                return true;
            }
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    public boolean hasInternetPermissionUL(int i) {
        try {
            if (this.mInternetPermissionMap.get(i)) {
                return true;
            }
            boolean z = this.mIPm.checkUidPermission("android.permission.INTERNET", i) == 0;
            this.mInternetPermissionMap.put(i, z);
            return z;
        } catch (android.os.RemoteException e) {
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    public void onUidDeletedUL(int i) {
        synchronized (this.mUidBlockedState) {
            this.mUidBlockedState.delete(i);
        }
        this.mUidState.delete(i);
        this.mActivityManagerInternal.onUidBlockedReasonsChanged(i, 0);
        this.mUidPolicy.delete(i);
        this.mUidFirewallStandbyRules.delete(i);
        this.mUidFirewallDozableRules.delete(i);
        this.mUidFirewallPowerSaveRules.delete(i);
        this.mUidFirewallBackgroundRules.delete(i);
        this.mBackgroundTransitioningUids.delete(i);
        this.mPowerSaveWhitelistExceptIdleAppIds.delete(i);
        this.mPowerSaveWhitelistAppIds.delete(i);
        this.mPowerSaveTempWhitelistAppIds.delete(i);
        this.mAppIdleTempWhitelistAppIds.delete(i);
        this.mUidFirewallRestrictedModeRules.delete(i);
        this.mUidFirewallLowPowerStandbyModeRules.delete(i);
        synchronized (this.mUidStateCallbackInfos) {
            this.mUidStateCallbackInfos.remove(i);
        }
        this.mHandler.obtainMessage(15, i, 0).sendToTarget();
        java.util.Set uidsAllowedOnRestrictedNetworks = android.net.ConnectivitySettingsManager.getUidsAllowedOnRestrictedNetworks(this.mContext);
        uidsAllowedOnRestrictedNetworks.remove(java.lang.Integer.valueOf(i));
        android.net.ConnectivitySettingsManager.setUidsAllowedOnRestrictedNetworks(this.mContext, uidsAllowedOnRestrictedNetworks);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    public void updateRestrictionRulesForUidUL(int i) {
        updateRuleForDeviceIdleUL(i);
        updateRuleForAppIdleUL(i, -1);
        updateRuleForRestrictPowerUL(i);
        if (this.mBackgroundNetworkRestricted) {
            updateRuleForBackgroundUL(i);
        }
        updateRestrictedModeForUidUL(i);
        lambda$updateRulesForRestrictPowerUL$5(i);
        lambda$updateRulesForRestrictBackgroundUL$6(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: updateRulesForDataUsageRestrictionsUL, reason: merged with bridge method [inline-methods] */
    public void lambda$updateRulesForRestrictBackgroundUL$6(int i) {
        if (android.os.Trace.isTagEnabled(2097152L)) {
            android.os.Trace.traceBegin(2097152L, "updateRulesForDataUsageRestrictionsUL: " + i);
        }
        try {
            updateRulesForDataUsageRestrictionsULInner(i);
        } finally {
            android.os.Trace.traceEnd(2097152L);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    private void updateRulesForDataUsageRestrictionsULInner(int i) {
        int i2;
        int i3;
        int i4;
        int i5;
        if (!isUidValidForAllowlistRulesUL(i)) {
            if (LOGD) {
                android.util.Slog.d(TAG, "no need to update restrict data rules for uid " + i);
                return;
            }
            return;
        }
        int i6 = 0;
        int i7 = this.mUidPolicy.get(i, 0);
        boolean isUidForegroundOnRestrictBackgroundUL = isUidForegroundOnRestrictBackgroundUL(i);
        boolean isRestrictedByAdminUL = isRestrictedByAdminUL(i);
        boolean z = (i7 & 1) != 0;
        boolean z2 = (i7 & 4) != 0;
        int i8 = (isRestrictedByAdminUL ? 262144 : 0) | 0 | (this.mRestrictBackground ? 65536 : 0) | (z ? 131072 : 0);
        int i9 = (isUidForegroundOnRestrictBackgroundUL ? 262144 : 0) | (isSystem(i) ? 131072 : 0) | 0 | (z2 ? 65536 : 0);
        synchronized (this.mUidBlockedState) {
            try {
                com.android.server.net.NetworkPolicyManagerService.UidBlockedState orCreateUidBlockedStateForUid = getOrCreateUidBlockedStateForUid(this.mUidBlockedState, i);
                com.android.server.net.NetworkPolicyManagerService.UidBlockedState orCreateUidBlockedStateForUid2 = getOrCreateUidBlockedStateForUid(this.mTmpUidBlockedState, i);
                orCreateUidBlockedStateForUid2.copyFrom(orCreateUidBlockedStateForUid);
                orCreateUidBlockedStateForUid.blockedReasons = (orCreateUidBlockedStateForUid.blockedReasons & com.android.server.location.gnss.hal.GnssNative.GNSS_AIDING_TYPE_ALL) | i8;
                orCreateUidBlockedStateForUid.allowedReasons = (orCreateUidBlockedStateForUid.allowedReasons & com.android.server.location.gnss.hal.GnssNative.GNSS_AIDING_TYPE_ALL) | i9;
                orCreateUidBlockedStateForUid.updateEffectiveBlockedReasons();
                i2 = orCreateUidBlockedStateForUid2.effectiveBlockedReasons;
                i3 = orCreateUidBlockedStateForUid.effectiveBlockedReasons;
                int i10 = orCreateUidBlockedStateForUid2.allowedReasons;
                if (i2 != i3) {
                    i6 = orCreateUidBlockedStateForUid.deriveUidRules();
                }
                if (!LOGV) {
                    i4 = i6;
                    i5 = i10;
                } else {
                    i5 = i10;
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    i4 = i6;
                    sb.append("updateRuleForRestrictBackgroundUL(");
                    sb.append(i);
                    sb.append("): isForeground=");
                    sb.append(isUidForegroundOnRestrictBackgroundUL);
                    sb.append(", isDenied=");
                    sb.append(z);
                    sb.append(", isAllowed=");
                    sb.append(z2);
                    sb.append(", isRestrictedByAdmin=");
                    sb.append(isRestrictedByAdminUL);
                    sb.append(", oldBlockedState=");
                    sb.append(orCreateUidBlockedStateForUid2);
                    sb.append(", newBlockedState=");
                    sb.append(orCreateUidBlockedStateForUid);
                    sb.append(", newBlockedMeteredReasons=");
                    sb.append(android.net.NetworkPolicyManager.blockedReasonsToString(i8));
                    sb.append(", newAllowedMeteredReasons=");
                    sb.append(android.net.NetworkPolicyManager.allowedReasonsToString(i9));
                    android.util.Log.v(TAG, sb.toString());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (i2 != i3) {
            handleBlockedReasonsChanged(i, i3, i2);
            postUidRulesChangedMsg(i, i4);
        }
        if ((i2 & 393216) != 0 || (i3 & 393216) != 0) {
            setMeteredNetworkDenylist(i, (393216 & i3) != 0);
        }
        if ((i5 & 327680) != 0 || (i9 & 327680) != 0) {
            setMeteredNetworkAllowlist(i, (327680 & i9) != 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    /* renamed from: updateRulesForPowerRestrictionsUL, reason: merged with bridge method [inline-methods] */
    public void lambda$updateRulesForRestrictPowerUL$5(int i) {
        updateRulesForPowerRestrictionsUL(i, -1);
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    private void updateRulesForPowerRestrictionsUL(int i, int i2) {
        updateRulesForPowerRestrictionsUL(i, isUidIdle(i, i2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    public void updateRulesForPowerRestrictionsUL(int i, boolean z) {
        if (android.os.Trace.isTagEnabled(2097152L)) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("updateRulesForPowerRestrictionsUL: ");
            sb.append(i);
            sb.append(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
            sb.append(z ? "I" : "-");
            android.os.Trace.traceBegin(2097152L, sb.toString());
        }
        try {
            updateRulesForPowerRestrictionsULInner(i, z);
        } finally {
            android.os.Trace.traceEnd(2097152L);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    private void updateRulesForPowerRestrictionsULInner(int i, boolean z) {
        int i2;
        int i3;
        int i4;
        if (!isUidValidForDenylistRulesUL(i)) {
            if (LOGD) {
                android.util.Slog.d(TAG, "no need to update restrict power rules for uid " + i);
                return;
            }
            return;
        }
        boolean isUidForegroundOnRestrictPowerUL = isUidForegroundOnRestrictPowerUL(i);
        boolean isUidTop = isUidTop(i);
        boolean isAllowlistedFromPowerSaveUL = isAllowlistedFromPowerSaveUL(i, this.mDeviceIdleMode);
        synchronized (this.mUidBlockedState) {
            try {
                com.android.server.net.NetworkPolicyManagerService.UidBlockedState orCreateUidBlockedStateForUid = getOrCreateUidBlockedStateForUid(this.mUidBlockedState, i);
                com.android.server.net.NetworkPolicyManagerService.UidBlockedState orCreateUidBlockedStateForUid2 = getOrCreateUidBlockedStateForUid(this.mTmpUidBlockedState, i);
                orCreateUidBlockedStateForUid2.copyFrom(orCreateUidBlockedStateForUid);
                i2 = 0;
                int i5 = 2;
                int i6 = 32;
                int i7 = 4;
                int i8 = 8;
                int i9 = 64;
                int i10 = (this.mRestrictPower ? 1 : 0) | 0 | (this.mDeviceIdleMode ? 2 : 0) | (this.mLowPowerStandbyActive ? 32 : 0) | (z ? 4 : 0) | (orCreateUidBlockedStateForUid.blockedReasons & 8) | (this.mBackgroundNetworkRestricted ? 64 : 0);
                int i11 = (isSystem(i) ? 1 : 0) | 0;
                if (!isUidForegroundOnRestrictPowerUL) {
                    i5 = 0;
                }
                int i12 = i5 | i11;
                if (!isUidTop) {
                    i6 = 0;
                }
                int i13 = i12 | i6;
                if (!isAllowlistedFromPowerSaveUL(i, true)) {
                    i7 = 0;
                }
                int i14 = i13 | i7;
                if (!isAllowlistedFromPowerSaveExceptIdleUL(i)) {
                    i8 = 0;
                }
                int i15 = i14 | i8 | (orCreateUidBlockedStateForUid.allowedReasons & 16);
                if (!isAllowlistedFromLowPowerStandbyUL(i)) {
                    i9 = 0;
                }
                int i16 = i15 | i9 | ((this.mBackgroundNetworkRestricted && isUidExemptFromBackgroundRestrictions(i)) ? 128 : 0);
                orCreateUidBlockedStateForUid.blockedReasons = i10 | (orCreateUidBlockedStateForUid.blockedReasons & (-65536));
                orCreateUidBlockedStateForUid.allowedReasons = (orCreateUidBlockedStateForUid.allowedReasons & (-65536)) | i16;
                orCreateUidBlockedStateForUid.updateEffectiveBlockedReasons();
                if (LOGV) {
                    android.util.Log.v(TAG, "updateRulesForPowerRestrictionsUL(" + i + "), isIdle: " + z + ", mRestrictPower: " + this.mRestrictPower + ", mDeviceIdleMode: " + this.mDeviceIdleMode + ", isForeground=" + isUidForegroundOnRestrictPowerUL + ", isTop=" + isUidTop + ", isWhitelisted=" + isAllowlistedFromPowerSaveUL + ", oldUidBlockedState=" + orCreateUidBlockedStateForUid2 + ", newUidBlockedState=" + orCreateUidBlockedStateForUid);
                }
                i3 = orCreateUidBlockedStateForUid2.effectiveBlockedReasons;
                i4 = orCreateUidBlockedStateForUid.effectiveBlockedReasons;
                if (i3 != i4) {
                    i2 = orCreateUidBlockedStateForUid.deriveUidRules();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (i3 != i4) {
            handleBlockedReasonsChanged(i, i4, i3);
            postUidRulesChangedMsg(i, i2);
        }
    }

    private class NetPolicyAppIdleStateChangeListener extends com.android.server.usage.AppStandbyInternal.AppIdleStateChangeListener {
        private NetPolicyAppIdleStateChangeListener() {
        }

        public void onAppIdleStateChanged(java.lang.String str, int i, boolean z, int i2, int i3) {
            try {
                int packageUidAsUser = com.android.server.net.NetworkPolicyManagerService.this.mContext.getPackageManager().getPackageUidAsUser(str, 8192, i);
                synchronized (com.android.server.net.NetworkPolicyManagerService.this.mUidRulesFirstLock) {
                    com.android.server.net.NetworkPolicyManagerService.this.mLogger.appIdleStateChanged(packageUidAsUser, z);
                    com.android.server.net.NetworkPolicyManagerService.this.updateRuleForAppIdleUL(packageUidAsUser, -1);
                    com.android.server.net.NetworkPolicyManagerService.this.lambda$updateRulesForRestrictPowerUL$5(packageUidAsUser);
                }
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            }
        }

        public void onParoleStateChanged(boolean z) {
            synchronized (com.android.server.net.NetworkPolicyManagerService.this.mUidRulesFirstLock) {
                com.android.server.net.NetworkPolicyManagerService.this.mLogger.paroleStateChanged(z);
                com.android.server.net.NetworkPolicyManagerService.this.updateRulesForAppIdleParoleUL();
            }
        }
    }

    private void handleBlockedReasonsChanged(int i, int i2, int i3) {
        this.mActivityManagerInternal.onUidBlockedReasonsChanged(i, i2);
        postBlockedReasonsChangedMsg(i, i2, i3);
    }

    private void postBlockedReasonsChangedMsg(int i, int i2, int i3) {
        this.mHandler.obtainMessage(21, i, i2, java.lang.Integer.valueOf(i3)).sendToTarget();
    }

    private void postUidRulesChangedMsg(int i, int i2) {
        this.mHandler.obtainMessage(1, i, i2).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchUidRulesChanged(android.net.INetworkPolicyListener iNetworkPolicyListener, int i, int i2) {
        try {
            iNetworkPolicyListener.onUidRulesChanged(i, i2);
        } catch (android.os.RemoteException e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchMeteredIfacesChanged(android.net.INetworkPolicyListener iNetworkPolicyListener, java.lang.String[] strArr) {
        try {
            iNetworkPolicyListener.onMeteredIfacesChanged(strArr);
        } catch (android.os.RemoteException e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchRestrictBackgroundChanged(android.net.INetworkPolicyListener iNetworkPolicyListener, boolean z) {
        try {
            iNetworkPolicyListener.onRestrictBackgroundChanged(z);
        } catch (android.os.RemoteException e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchUidPoliciesChanged(android.net.INetworkPolicyListener iNetworkPolicyListener, int i, int i2) {
        try {
            iNetworkPolicyListener.onUidPoliciesChanged(i, i2);
        } catch (android.os.RemoteException e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchUidsAllowedTransportsChanged(android.net.INetworkPolicyListener iNetworkPolicyListener, int[] iArr, long[] jArr) {
        try {
            iNetworkPolicyListener.onAllowedTransportsChanged(iArr, jArr);
        } catch (android.os.RemoteException e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchSubscriptionOverride(android.net.INetworkPolicyListener iNetworkPolicyListener, int i, int i2, int i3, int[] iArr) {
        try {
            iNetworkPolicyListener.onSubscriptionOverride(i, i2, i3, iArr);
        } catch (android.os.RemoteException e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchSubscriptionPlansChanged(android.net.INetworkPolicyListener iNetworkPolicyListener, int i, android.telephony.SubscriptionPlan[] subscriptionPlanArr) {
        try {
            iNetworkPolicyListener.onSubscriptionPlansChanged(i, subscriptionPlanArr);
        } catch (android.os.RemoteException e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchBlockedReasonChanged(android.net.INetworkPolicyListener iNetworkPolicyListener, int i, int i2, int i3) {
        try {
            iNetworkPolicyListener.onBlockedReasonChanged(i, i2, i3);
        } catch (android.os.RemoteException e) {
        }
    }

    void handleUidChanged(int i) {
        boolean updateUidStateUL;
        android.os.Trace.traceBegin(2097152L, "onUidStateChanged");
        try {
            synchronized (this.mUidStateCallbackInfos) {
                com.android.server.net.NetworkPolicyManagerService.UidStateCallbackInfo uidStateCallbackInfo = this.mUidStateCallbackInfos.get(i);
                if (uidStateCallbackInfo == null) {
                    return;
                }
                int i2 = uidStateCallbackInfo.procState;
                long j = uidStateCallbackInfo.procStateSeq;
                int i3 = uidStateCallbackInfo.capability;
                uidStateCallbackInfo.isPending = false;
                synchronized (this.mUidRulesFirstLock) {
                    this.mLogger.uidStateChanged(i, i2, j, i3);
                    updateUidStateUL = updateUidStateUL(i, i2, j, i3);
                    this.mActivityManagerInternal.notifyNetworkPolicyRulesUpdated(i, j);
                }
                if (updateUidStateUL) {
                    updateNetworkStats(i, android.net.NetworkPolicyManager.isProcStateAllowedWhileOnRestrictBackground(i2, i3));
                }
            }
        } finally {
            android.os.Trace.traceEnd(2097152L);
        }
    }

    void handleUidGone(int i) {
        boolean removeUidStateUL;
        android.os.Trace.traceBegin(2097152L, "onUidGone");
        try {
            synchronized (this.mUidStateCallbackInfos) {
                if (this.mUidStateCallbackInfos.contains(i)) {
                    return;
                }
                synchronized (this.mUidRulesFirstLock) {
                    removeUidStateUL = removeUidStateUL(i);
                }
                if (removeUidStateUL) {
                    updateNetworkStats(i, false);
                }
            }
        } finally {
            android.os.Trace.traceEnd(2097152L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void broadcastRestrictBackgroundChanged(int i, java.lang.Boolean bool) {
        java.lang.String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(i);
        if (packagesForUid != null) {
            int userId = android.os.UserHandle.getUserId(i);
            for (java.lang.String str : packagesForUid) {
                android.content.Intent intent = new android.content.Intent("android.net.conn.RESTRICT_BACKGROUND_CHANGED");
                intent.setPackage(str);
                intent.setFlags(1073741824);
                this.mContext.sendBroadcastAsUser(intent, android.os.UserHandle.of(userId));
            }
        }
    }

    private static final class IfaceQuotas {

        @android.annotation.NonNull
        public final java.lang.String iface;
        public final long limit;
        public final long warning;

        private IfaceQuotas(@android.annotation.NonNull java.lang.String str, long j, long j2) {
            this.iface = str;
            this.warning = j;
            this.limit = j2;
        }
    }

    private void setInterfaceQuotasAsync(@android.annotation.NonNull java.lang.String str, long j, long j2) {
        this.mHandler.obtainMessage(10, new com.android.server.net.NetworkPolicyManagerService.IfaceQuotas(str, j, j2)).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setInterfaceLimit(java.lang.String str, long j) {
        try {
            this.mNetworkManager.setInterfaceQuota(str, j);
        } catch (android.os.RemoteException e) {
        } catch (java.lang.IllegalStateException e2) {
            android.util.Log.wtf(TAG, "problem setting interface quota", e2);
        }
    }

    private void removeInterfaceQuotasAsync(java.lang.String str) {
        this.mHandler.obtainMessage(11, str).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeInterfaceLimit(java.lang.String str) {
        try {
            this.mNetworkManager.removeInterfaceQuota(str);
        } catch (android.os.RemoteException e) {
        } catch (java.lang.IllegalStateException e2) {
            android.util.Log.wtf(TAG, "problem removing interface quota", e2);
        }
    }

    private void setMeteredNetworkDenylist(int i, boolean z) {
        if (LOGV) {
            android.util.Slog.v(TAG, "setMeteredNetworkDenylist " + i + ": " + z);
        }
        try {
            this.mNetworkManager.setUidOnMeteredNetworkDenylist(i, z);
            this.mLogger.meteredDenylistChanged(i, z);
            if (android.os.Process.isApplicationUid(i)) {
                int sdkSandboxUid = android.os.Process.toSdkSandboxUid(i);
                this.mNetworkManager.setUidOnMeteredNetworkDenylist(sdkSandboxUid, z);
                this.mLogger.meteredDenylistChanged(sdkSandboxUid, z);
            }
        } catch (android.os.RemoteException e) {
        } catch (java.lang.IllegalStateException e2) {
            android.util.Log.wtf(TAG, "problem setting denylist (" + z + ") rules for " + i, e2);
        }
    }

    private void setMeteredNetworkAllowlist(int i, boolean z) {
        if (LOGV) {
            android.util.Slog.v(TAG, "setMeteredNetworkAllowlist " + i + ": " + z);
        }
        try {
            this.mNetworkManager.setUidOnMeteredNetworkAllowlist(i, z);
            this.mLogger.meteredAllowlistChanged(i, z);
            if (android.os.Process.isApplicationUid(i)) {
                int sdkSandboxUid = android.os.Process.toSdkSandboxUid(i);
                this.mNetworkManager.setUidOnMeteredNetworkAllowlist(sdkSandboxUid, z);
                this.mLogger.meteredAllowlistChanged(sdkSandboxUid, z);
            }
        } catch (android.os.RemoteException e) {
        } catch (java.lang.IllegalStateException e2) {
            android.util.Log.wtf(TAG, "problem setting allowlist (" + z + ") rules for " + i, e2);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    private void setUidFirewallRulesUL(int i, @android.annotation.Nullable android.util.SparseIntArray sparseIntArray, int i2) {
        if (sparseIntArray != null) {
            setUidFirewallRulesUL(i, sparseIntArray);
        }
        if (i2 != 0) {
            enableFirewallChainUL(i, i2 == 1);
        }
    }

    private void addSdkSandboxUidsIfNeeded(android.util.SparseIntArray sparseIntArray) {
        int size = sparseIntArray.size();
        android.util.SparseIntArray sparseIntArray2 = new android.util.SparseIntArray();
        for (int i = 0; i < size; i++) {
            int keyAt = sparseIntArray.keyAt(i);
            int valueAt = sparseIntArray.valueAt(i);
            if (android.os.Process.isApplicationUid(keyAt)) {
                sparseIntArray2.put(android.os.Process.toSdkSandboxUid(keyAt), valueAt);
            }
        }
        for (int i2 = 0; i2 < sparseIntArray2.size(); i2++) {
            sparseIntArray.put(sparseIntArray2.keyAt(i2), sparseIntArray2.valueAt(i2));
        }
    }

    private void setUidFirewallRulesUL(int i, android.util.SparseIntArray sparseIntArray) {
        addSdkSandboxUidsIfNeeded(sparseIntArray);
        try {
            int size = sparseIntArray.size();
            int[] iArr = new int[size];
            int[] iArr2 = new int[size];
            for (int i2 = size - 1; i2 >= 0; i2--) {
                iArr[i2] = sparseIntArray.keyAt(i2);
                iArr2[i2] = sparseIntArray.valueAt(i2);
            }
            this.mNetworkManager.setFirewallUidRules(i, iArr, iArr2);
            this.mLogger.firewallRulesChanged(i, iArr, iArr2);
        } catch (android.os.RemoteException e) {
        } catch (java.lang.IllegalStateException e2) {
            android.util.Log.wtf(TAG, "problem setting firewall uid rules", e2);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    private void setUidFirewallRuleUL(int i, int i2, int i3) {
        if (android.os.Trace.isTagEnabled(2097152L)) {
            android.os.Trace.traceBegin(2097152L, "setUidFirewallRuleUL: " + i + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + i2 + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + i3);
        }
        try {
            if (i == 1) {
                this.mUidFirewallDozableRules.put(i2, i3);
            } else if (i == 2) {
                this.mUidFirewallStandbyRules.put(i2, i3);
            } else if (i == 3) {
                this.mUidFirewallPowerSaveRules.put(i2, i3);
            } else if (i == 4) {
                this.mUidFirewallRestrictedModeRules.put(i2, i3);
            } else if (i == 5) {
                this.mUidFirewallLowPowerStandbyModeRules.put(i2, i3);
            } else if (i == 6) {
                this.mUidFirewallBackgroundRules.put(i2, i3);
            }
            try {
                this.mNetworkManager.setFirewallUidRule(i, i2, i3);
                this.mLogger.uidFirewallRuleChanged(i, i2, i3);
                if (android.os.Process.isApplicationUid(i2)) {
                    int sdkSandboxUid = android.os.Process.toSdkSandboxUid(i2);
                    this.mNetworkManager.setFirewallUidRule(i, sdkSandboxUid, i3);
                    this.mLogger.uidFirewallRuleChanged(i, sdkSandboxUid, i3);
                }
            } catch (android.os.RemoteException e) {
            } catch (java.lang.IllegalStateException e2) {
                android.util.Log.wtf(TAG, "problem setting firewall uid rules", e2);
            }
            android.os.Trace.traceEnd(2097152L);
        } catch (java.lang.Throwable th) {
            android.os.Trace.traceEnd(2097152L);
            throw th;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    private void enableFirewallChainUL(int i, boolean z) {
        if (this.mFirewallChainStates.indexOfKey(i) >= 0 && this.mFirewallChainStates.get(i) == z) {
            return;
        }
        this.mFirewallChainStates.put(i, z);
        try {
            this.mNetworkManager.setFirewallChainEnabled(i, z);
            this.mLogger.firewallChainEnabled(i, z);
        } catch (android.os.RemoteException e) {
        } catch (java.lang.IllegalStateException e2) {
            android.util.Log.wtf(TAG, "problem enable firewall chain", e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetUidFirewallRules(int i) {
        try {
            this.mNetworkManager.setFirewallUidRule(1, i, 0);
            this.mNetworkManager.setFirewallUidRule(2, i, 0);
            this.mNetworkManager.setFirewallUidRule(3, i, 0);
            this.mNetworkManager.setFirewallUidRule(4, i, 0);
            this.mNetworkManager.setFirewallUidRule(5, i, 0);
            this.mNetworkManager.setFirewallUidRule(6, i, 0);
            this.mNetworkManager.setUidOnMeteredNetworkAllowlist(i, false);
            this.mLogger.meteredAllowlistChanged(i, false);
            this.mNetworkManager.setUidOnMeteredNetworkDenylist(i, false);
            this.mLogger.meteredDenylistChanged(i, false);
        } catch (android.os.RemoteException e) {
        } catch (java.lang.IllegalStateException e2) {
            android.util.Log.wtf(TAG, "problem resetting firewall uid rules for " + i, e2);
        }
        if (android.os.Process.isApplicationUid(i)) {
            resetUidFirewallRules(android.os.Process.toSdkSandboxUid(i));
        }
    }

    @java.lang.Deprecated
    private long getTotalBytes(android.net.NetworkTemplate networkTemplate, long j, long j2) {
        if (this.mStatsCallback.isAnyCallbackReceived()) {
            return this.mDeps.getNetworkTotalBytes(networkTemplate, j, j2);
        }
        return 0L;
    }

    private boolean isBandwidthControlEnabled() {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            boolean isBandwidthControlEnabled = this.mNetworkManager.isBandwidthControlEnabled();
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return isBandwidthControlEnabled;
        } catch (android.os.RemoteException e) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    private static android.content.Intent buildSnoozeWarningIntent(android.net.NetworkTemplate networkTemplate, java.lang.String str) {
        android.content.Intent intent = new android.content.Intent(ACTION_SNOOZE_WARNING);
        intent.addFlags(268435456);
        intent.putExtra("android.net.NETWORK_TEMPLATE", (android.os.Parcelable) networkTemplate);
        intent.setPackage(str);
        return intent;
    }

    private static android.content.Intent buildSnoozeRapidIntent(android.net.NetworkTemplate networkTemplate, java.lang.String str) {
        android.content.Intent intent = new android.content.Intent(ACTION_SNOOZE_RAPID);
        intent.addFlags(268435456);
        intent.putExtra("android.net.NETWORK_TEMPLATE", (android.os.Parcelable) networkTemplate);
        intent.setPackage(str);
        return intent;
    }

    private static android.content.Intent buildNetworkOverLimitIntent(android.content.res.Resources resources, android.net.NetworkTemplate networkTemplate) {
        android.content.Intent intent = new android.content.Intent();
        intent.setComponent(android.content.ComponentName.unflattenFromString(resources.getString(android.R.string.config_mainBuiltInDisplayCutoutRectApproximation)));
        intent.addFlags(268435456);
        intent.putExtra("android.net.NETWORK_TEMPLATE", (android.os.Parcelable) networkTemplate);
        return intent;
    }

    private static android.content.Intent buildViewDataUsageIntent(android.content.res.Resources resources, android.net.NetworkTemplate networkTemplate) {
        android.content.Intent intent = new android.content.Intent();
        intent.setComponent(android.content.ComponentName.unflattenFromString(resources.getString(android.R.string.config_customAdbPublicKeyConfirmationSecondaryUserComponent)));
        intent.addFlags(268435456);
        intent.putExtra("android.net.NETWORK_TEMPLATE", (android.os.Parcelable) networkTemplate);
        return intent;
    }

    @com.android.internal.annotations.VisibleForTesting
    void addIdleHandler(android.os.MessageQueue.IdleHandler idleHandler) {
        this.mHandler.getLooper().getQueue().addIdleHandler(idleHandler);
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    @com.android.internal.annotations.VisibleForTesting
    void updateRestrictBackgroundByLowPowerModeUL(android.os.PowerSaveState powerSaveState) {
        boolean z;
        boolean z2;
        if (this.mRestrictBackgroundLowPowerMode == powerSaveState.batterySaverEnabled) {
            return;
        }
        this.mRestrictBackgroundLowPowerMode = powerSaveState.batterySaverEnabled;
        boolean z3 = this.mRestrictBackgroundLowPowerMode;
        boolean z4 = this.mRestrictBackgroundChangedInBsm;
        if (this.mRestrictBackgroundLowPowerMode) {
            z = !this.mRestrictBackground;
            this.mRestrictBackgroundBeforeBsm = this.mRestrictBackground;
            z2 = false;
        } else {
            z = !this.mRestrictBackgroundChangedInBsm;
            z3 = this.mRestrictBackgroundBeforeBsm;
            z2 = z4;
        }
        if (z) {
            setRestrictBackgroundUL(z3, "low_power");
        }
        this.mRestrictBackgroundChangedInBsm = z2;
    }

    private static void collectKeys(android.util.SparseIntArray sparseIntArray, android.util.SparseBooleanArray sparseBooleanArray) {
        int size = sparseIntArray.size();
        for (int i = 0; i < size; i++) {
            sparseBooleanArray.put(sparseIntArray.keyAt(i), true);
        }
    }

    private static <T> void collectKeys(android.util.SparseArray<T> sparseArray, android.util.SparseBooleanArray sparseBooleanArray) {
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            sparseBooleanArray.put(sparseArray.keyAt(i), true);
        }
    }

    @android.annotation.EnforcePermission("android.permission.NETWORK_SETTINGS")
    public void factoryReset(java.lang.String str) {
        android.net.NetworkTemplate networkTemplate;
        factoryReset_enforcePermission();
        if (this.mUserManager.hasUserRestriction("no_network_reset")) {
            return;
        }
        android.net.NetworkPolicy[] networkPolicies = getNetworkPolicies(this.mContext.getOpPackageName());
        android.net.NetworkTemplate networkTemplate2 = null;
        if (str == null) {
            networkTemplate = null;
        } else {
            networkTemplate = buildTemplateCarrierMetered(str);
        }
        if (str != null) {
            networkTemplate2 = new android.net.NetworkTemplate.Builder(1).setSubscriberIds(java.util.Set.of(str)).setMeteredness(1).build();
        }
        for (android.net.NetworkPolicy networkPolicy : networkPolicies) {
            if (networkPolicy.template.equals(networkTemplate) || networkPolicy.template.equals(networkTemplate2)) {
                networkPolicy.limitBytes = -1L;
                networkPolicy.inferred = false;
                networkPolicy.clearSnooze();
            }
        }
        setNetworkPolicies(networkPolicies);
        setRestrictBackground(false);
        if (!this.mUserManager.hasUserRestriction("no_control_apps")) {
            for (int i : getUidsWithPolicy(1)) {
                setUidPolicy(i, 0);
            }
        }
    }

    public boolean isUidNetworkingBlocked(int i, boolean z) {
        int i2;
        boolean contains;
        long time = this.mStatLogger.getTime();
        this.mContext.enforceCallingOrSelfPermission("android.permission.OBSERVE_NETWORK_POLICY", TAG);
        synchronized (this.mUidBlockedState) {
            com.android.server.net.NetworkPolicyManagerService.UidBlockedState uidBlockedState = this.mUidBlockedState.get(i);
            i2 = uidBlockedState == null ? 0 : uidBlockedState.effectiveBlockedReasons;
            if (!z) {
                i2 &= com.android.server.location.gnss.hal.GnssNative.GNSS_AIDING_TYPE_ALL;
            }
            this.mLogger.networkBlocked(i, uidBlockedState);
        }
        this.mStatLogger.logDurationStat(1, time);
        if (i2 != 0) {
            return true;
        }
        synchronized (this.mDisallowedUidsDenylist) {
            contains = this.mDisallowedUidsDenylist.contains(java.lang.Integer.valueOf(i));
        }
        return contains;
    }

    @android.annotation.EnforcePermission("android.permission.OBSERVE_NETWORK_POLICY")
    public boolean isUidRestrictedOnMeteredNetworks(int i) {
        boolean z;
        isUidRestrictedOnMeteredNetworks_enforcePermission();
        synchronized (this.mUidBlockedState) {
            com.android.server.net.NetworkPolicyManagerService.UidBlockedState uidBlockedState = this.mUidBlockedState.get(i);
            z = ((uidBlockedState == null ? 0 : uidBlockedState.effectiveBlockedReasons) & (-65536)) != 0;
        }
        return z;
    }

    private static boolean isSystem(int i) {
        return i < 10000;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class NetworkPolicyManagerInternalImpl extends com.android.server.net.NetworkPolicyManagerInternal {
        private NetworkPolicyManagerInternalImpl() {
        }

        @Override // com.android.server.net.NetworkPolicyManagerInternal
        public void resetUserState(int i) {
            synchronized (com.android.server.net.NetworkPolicyManagerService.this.mUidRulesFirstLock) {
                try {
                    boolean z = true;
                    boolean removeUserStateUL = com.android.server.net.NetworkPolicyManagerService.this.removeUserStateUL(i, false, true);
                    if (!com.android.server.net.NetworkPolicyManagerService.this.addDefaultRestrictBackgroundAllowlistUidsUL(i) && !removeUserStateUL) {
                        z = false;
                    }
                    if (z) {
                        synchronized (com.android.server.net.NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
                            com.android.server.net.NetworkPolicyManagerService.this.writePolicyAL();
                        }
                    }
                } finally {
                }
            }
        }

        @Override // com.android.server.net.NetworkPolicyManagerInternal
        public void onTempPowerSaveWhitelistChange(int i, boolean z, int i2, @android.annotation.Nullable java.lang.String str) {
            synchronized (com.android.server.net.NetworkPolicyManagerService.this.mUidRulesFirstLock) {
                try {
                    if (com.android.server.net.NetworkPolicyManagerService.this.mSystemReady) {
                        com.android.server.net.NetworkPolicyManagerService.this.mLogger.tempPowerSaveWlChanged(i, z, i2, str);
                        if (z) {
                            com.android.server.net.NetworkPolicyManagerService.this.mPowerSaveTempWhitelistAppIds.put(i, true);
                        } else {
                            com.android.server.net.NetworkPolicyManagerService.this.mPowerSaveTempWhitelistAppIds.delete(i);
                        }
                        com.android.server.net.NetworkPolicyManagerService.this.updateRulesForTempAllowlistChangeUL(i);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.net.NetworkPolicyManagerInternal
        public android.telephony.SubscriptionPlan getSubscriptionPlan(android.net.Network network) {
            android.telephony.SubscriptionPlan primarySubscriptionPlanLocked;
            synchronized (com.android.server.net.NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
                primarySubscriptionPlanLocked = com.android.server.net.NetworkPolicyManagerService.this.getPrimarySubscriptionPlanLocked(com.android.server.net.NetworkPolicyManagerService.this.getSubIdLocked(network));
            }
            return primarySubscriptionPlanLocked;
        }

        @Override // com.android.server.net.NetworkPolicyManagerInternal
        public long getSubscriptionOpportunisticQuota(android.net.Network network, int i) {
            long j;
            synchronized (com.android.server.net.NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
                j = com.android.server.net.NetworkPolicyManagerService.this.mSubscriptionOpportunisticQuota.get(com.android.server.net.NetworkPolicyManagerService.this.getSubIdLocked(network), -1L);
            }
            if (j == -1) {
                return -1L;
            }
            if (i == 1) {
                return (long) (j * android.provider.Settings.Global.getFloat(com.android.server.net.NetworkPolicyManagerService.this.mContext.getContentResolver(), "netpolicy_quota_frac_jobs", 0.5f));
            }
            if (i == 2) {
                return (long) (j * android.provider.Settings.Global.getFloat(com.android.server.net.NetworkPolicyManagerService.this.mContext.getContentResolver(), "netpolicy_quota_frac_multipath", 0.5f));
            }
            return -1L;
        }

        @Override // com.android.server.net.NetworkPolicyManagerInternal
        public void onAdminDataAvailable() {
            com.android.server.net.NetworkPolicyManagerService.this.mAdminDataAvailableLatch.countDown();
        }

        @Override // com.android.server.net.NetworkPolicyManagerInternal
        public void setAppIdleWhitelist(int i, boolean z) {
            com.android.server.net.NetworkPolicyManagerService.this.setAppIdleWhitelist(i, z);
        }

        @Override // com.android.server.net.NetworkPolicyManagerInternal
        public void setMeteredRestrictedPackages(java.util.Set<java.lang.String> set, int i) {
            com.android.server.net.NetworkPolicyManagerService.this.setMeteredRestrictedPackagesInternal(set, i);
        }

        @Override // com.android.server.net.NetworkPolicyManagerInternal
        public void setMeteredRestrictedPackagesAsync(java.util.Set<java.lang.String> set, int i) {
            com.android.server.net.NetworkPolicyManagerService.this.mHandler.obtainMessage(17, i, 0, set).sendToTarget();
        }

        @Override // com.android.server.net.NetworkPolicyManagerInternal
        public void setLowPowerStandbyActive(boolean z) {
            android.os.Trace.traceBegin(2097152L, "setLowPowerStandbyActive");
            try {
                synchronized (com.android.server.net.NetworkPolicyManagerService.this.mUidRulesFirstLock) {
                    if (com.android.server.net.NetworkPolicyManagerService.this.mLowPowerStandbyActive == z) {
                        return;
                    }
                    com.android.server.net.NetworkPolicyManagerService.this.mLowPowerStandbyActive = z;
                    synchronized (com.android.server.net.NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
                        if (com.android.server.net.NetworkPolicyManagerService.this.mSystemReady) {
                            com.android.server.net.NetworkPolicyManagerService.this.forEachUid("updateRulesForRestrictPower", new java.util.function.IntConsumer() { // from class: com.android.server.net.NetworkPolicyManagerService$NetworkPolicyManagerInternalImpl$$ExternalSyntheticLambda0
                                @Override // java.util.function.IntConsumer
                                public final void accept(int i) {
                                    com.android.server.net.NetworkPolicyManagerService.NetworkPolicyManagerInternalImpl.this.lambda$setLowPowerStandbyActive$0(i);
                                }
                            });
                            com.android.server.net.NetworkPolicyManagerService.this.updateRulesForLowPowerStandbyUL();
                        }
                    }
                }
            } finally {
                android.os.Trace.traceEnd(2097152L);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setLowPowerStandbyActive$0(int i) {
            com.android.server.net.NetworkPolicyManagerService.this.lambda$updateRulesForRestrictPowerUL$5(i);
        }

        @Override // com.android.server.net.NetworkPolicyManagerInternal
        public void setLowPowerStandbyAllowlist(int[] iArr) {
            synchronized (com.android.server.net.NetworkPolicyManagerService.this.mUidRulesFirstLock) {
                try {
                    android.util.SparseBooleanArray sparseBooleanArray = new android.util.SparseBooleanArray();
                    for (int i = 0; i < com.android.server.net.NetworkPolicyManagerService.this.mLowPowerStandbyAllowlistUids.size(); i++) {
                        int keyAt = com.android.server.net.NetworkPolicyManagerService.this.mLowPowerStandbyAllowlistUids.keyAt(i);
                        if (!com.android.internal.util.ArrayUtils.contains(iArr, keyAt)) {
                            sparseBooleanArray.put(keyAt, true);
                        }
                    }
                    for (int i2 = 0; i2 < sparseBooleanArray.size(); i2++) {
                        com.android.server.net.NetworkPolicyManagerService.this.mLowPowerStandbyAllowlistUids.delete(sparseBooleanArray.keyAt(i2));
                    }
                    for (int i3 : iArr) {
                        if (com.android.server.net.NetworkPolicyManagerService.this.mLowPowerStandbyAllowlistUids.indexOfKey(i3) < 0) {
                            sparseBooleanArray.append(i3, true);
                            com.android.server.net.NetworkPolicyManagerService.this.mLowPowerStandbyAllowlistUids.append(i3, true);
                        }
                    }
                    if (com.android.server.net.NetworkPolicyManagerService.this.mLowPowerStandbyActive) {
                        synchronized (com.android.server.net.NetworkPolicyManagerService.this.mNetworkPoliciesSecondLock) {
                            if (com.android.server.net.NetworkPolicyManagerService.this.mSystemReady) {
                                for (int i4 = 0; i4 < sparseBooleanArray.size(); i4++) {
                                    int keyAt2 = sparseBooleanArray.keyAt(i4);
                                    com.android.server.net.NetworkPolicyManagerService.this.lambda$updateRulesForRestrictPowerUL$5(keyAt2);
                                    com.android.server.net.NetworkPolicyManagerService.this.updateRuleForLowPowerStandbyUL(keyAt2);
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

    /* JADX INFO: Access modifiers changed from: private */
    public void setMeteredRestrictedPackagesInternal(java.util.Set<java.lang.String> set, int i) {
        synchronized (this.mUidRulesFirstLock) {
            try {
                android.util.ArraySet arraySet = new android.util.ArraySet();
                java.util.Iterator<java.lang.String> it = set.iterator();
                while (it.hasNext()) {
                    int uidForPackage = getUidForPackage(it.next(), i);
                    if (uidForPackage >= 0) {
                        arraySet.add(java.lang.Integer.valueOf(uidForPackage));
                    }
                }
                java.util.Set<java.lang.Integer> set2 = this.mMeteredRestrictedUids.get(i);
                this.mMeteredRestrictedUids.put(i, arraySet);
                handleRestrictedPackagesChangeUL(set2, arraySet);
                this.mLogger.meteredRestrictedPkgsChanged(arraySet);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private int readUidAttribute(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, boolean z, int i) throws java.io.IOException {
        if (!z) {
            return com.android.internal.util.XmlUtils.readIntAttribute(typedXmlPullParser, "uid");
        }
        return getUidForPackage(com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "name"), i);
    }

    private java.lang.String getPackageForUid(int i) {
        try {
            return this.mContext.getPackageManager().getPackagesForUid(i)[0];
        } catch (java.lang.Exception e) {
            return null;
        }
    }

    private int getUidForPackage(java.lang.String str, int i) {
        try {
            return this.mContext.getPackageManager().getPackageUidAsUser(str, 4202496, i);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return -1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mNetworkPoliciesSecondLock"})
    public int getSubIdLocked(android.net.Network network) {
        return this.mNetIdToSubId.get(network.getNetId(), -1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mNetworkPoliciesSecondLock"})
    public android.telephony.SubscriptionPlan getPrimarySubscriptionPlanLocked(int i) {
        android.telephony.SubscriptionPlan[] subscriptionPlanArr = this.mSubscriptionPlans.get(i);
        if (!com.android.internal.util.ArrayUtils.isEmpty(subscriptionPlanArr)) {
            for (android.telephony.SubscriptionPlan subscriptionPlan : subscriptionPlanArr) {
                if (subscriptionPlan.getCycleRule().isRecurring()) {
                    return subscriptionPlan;
                }
                if (subscriptionPlan.cycleIterator().next().contains((android.util.Range<java.time.ZonedDateTime>) java.time.ZonedDateTime.now(this.mClock))) {
                    return subscriptionPlan;
                }
            }
            return null;
        }
        return null;
    }

    private void waitForAdminData() {
        if (this.mContext.getPackageManager().hasSystemFeature("android.software.device_admin")) {
            com.android.internal.util.ConcurrentUtils.waitForCountDownNoInterrupt(this.mAdminDataAvailableLatch, 10000L, "Wait for admin data");
        }
    }

    private void handleRestrictedPackagesChangeUL(java.util.Set<java.lang.Integer> set, java.util.Set<java.lang.Integer> set2) {
        if (!this.mNetworkManagerReady) {
            return;
        }
        if (set == null) {
            java.util.Iterator<java.lang.Integer> it = set2.iterator();
            while (it.hasNext()) {
                lambda$updateRulesForRestrictBackgroundUL$6(it.next().intValue());
            }
            return;
        }
        java.util.Iterator<java.lang.Integer> it2 = set.iterator();
        while (it2.hasNext()) {
            int intValue = it2.next().intValue();
            if (!set2.contains(java.lang.Integer.valueOf(intValue))) {
                lambda$updateRulesForRestrictBackgroundUL$6(intValue);
            }
        }
        java.util.Iterator<java.lang.Integer> it3 = set2.iterator();
        while (it3.hasNext()) {
            int intValue2 = it3.next().intValue();
            if (!set.contains(java.lang.Integer.valueOf(intValue2))) {
                lambda$updateRulesForRestrictBackgroundUL$6(intValue2);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mUidRulesFirstLock"})
    private boolean isRestrictedByAdminUL(int i) {
        java.util.Set<java.lang.Integer> set = this.mMeteredRestrictedUids.get(android.os.UserHandle.getUserId(i));
        return set != null && set.contains(java.lang.Integer.valueOf(i));
    }

    private static boolean getBooleanDefeatingNullable(@android.annotation.Nullable android.os.PersistableBundle persistableBundle, java.lang.String str, boolean z) {
        return persistableBundle != null ? persistableBundle.getBoolean(str, z) : z;
    }

    private static com.android.server.net.NetworkPolicyManagerService.UidBlockedState getOrCreateUidBlockedStateForUid(android.util.SparseArray<com.android.server.net.NetworkPolicyManagerService.UidBlockedState> sparseArray, int i) {
        com.android.server.net.NetworkPolicyManagerService.UidBlockedState uidBlockedState = sparseArray.get(i);
        if (uidBlockedState == null) {
            com.android.server.net.NetworkPolicyManagerService.UidBlockedState uidBlockedState2 = new com.android.server.net.NetworkPolicyManagerService.UidBlockedState();
            sparseArray.put(i, uidBlockedState2);
            return uidBlockedState2;
        }
        return uidBlockedState;
    }

    private int getEffectiveBlockedReasons(int i) {
        int i2;
        synchronized (this.mUidBlockedState) {
            com.android.server.net.NetworkPolicyManagerService.UidBlockedState uidBlockedState = this.mUidBlockedState.get(i);
            if (uidBlockedState == null) {
                i2 = 0;
            } else {
                i2 = uidBlockedState.effectiveBlockedReasons;
            }
        }
        return i2;
    }

    private int getBlockedReasons(int i) {
        int i2;
        synchronized (this.mUidBlockedState) {
            com.android.server.net.NetworkPolicyManagerService.UidBlockedState uidBlockedState = this.mUidBlockedState.get(i);
            if (uidBlockedState == null) {
                i2 = 0;
            } else {
                i2 = uidBlockedState.blockedReasons;
            }
        }
        return i2;
    }

    @com.android.internal.annotations.VisibleForTesting
    static final class UidBlockedState {
        public int allowedReasons;
        public int blockedReasons;
        public int effectiveBlockedReasons;
        private static final int[] BLOCKED_REASONS = {1, 2, 4, 8, 32, 64, 65536, 131072, 262144};
        private static final int[] ALLOWED_REASONS = {1, 2, 32, 4, 8, 16, 64, 128, 65536, 131072, 262144};

        private UidBlockedState(int i, int i2, int i3) {
            this.blockedReasons = i;
            this.allowedReasons = i2;
            this.effectiveBlockedReasons = i3;
        }

        UidBlockedState() {
            this(0, 0, 0);
        }

        void updateEffectiveBlockedReasons() {
            if (com.android.server.net.NetworkPolicyManagerService.LOGV && this.blockedReasons == 0) {
                android.util.Log.v(com.android.server.net.NetworkPolicyManagerService.TAG, "updateEffectiveBlockedReasons(): no blocked reasons");
            }
            this.effectiveBlockedReasons = getEffectiveBlockedReasons(this.blockedReasons, this.allowedReasons);
            if (com.android.server.net.NetworkPolicyManagerService.LOGV) {
                android.util.Log.v(com.android.server.net.NetworkPolicyManagerService.TAG, "updateEffectiveBlockedReasons(): blockedReasons=" + java.lang.Integer.toBinaryString(this.blockedReasons) + ", effectiveReasons=" + java.lang.Integer.toBinaryString(this.effectiveBlockedReasons));
            }
        }

        @com.android.internal.annotations.VisibleForTesting
        static int getEffectiveBlockedReasons(int i, int i2) {
            if (i == 0) {
                return i;
            }
            if ((i2 & 1) != 0) {
                i &= -65536;
            }
            if ((131072 & i2) != 0) {
                i &= com.android.server.location.gnss.hal.GnssNative.GNSS_AIDING_TYPE_ALL;
            }
            if ((i2 & 2) != 0) {
                i = i & (-2) & (-3) & (-5);
            }
            if ((262144 & i2) != 0) {
                i = i & (-65537) & (-131073);
            }
            if ((i2 & 32) != 0) {
                i &= -33;
            }
            if ((i2 & 4) != 0) {
                i = i & (-2) & (-3) & (-5) & (-65);
            }
            if ((i2 & 8) != 0) {
                i = i & (-2) & (-5) & (-65);
            }
            if ((i2 & 16) != 0) {
                i &= -9;
            }
            if ((65536 & i2) != 0) {
                i &= -65537;
            }
            if ((i2 & 64) != 0) {
                i &= -33;
            }
            if ((i2 & 128) != 0) {
                return i & (-65);
            }
            return i;
        }

        static int getAllowedReasonsForProcState(int i) {
            if (i <= 3) {
                return 262306;
            }
            if (i <= 5) {
                return 262274;
            }
            if (i < 12) {
                return 128;
            }
            return 0;
        }

        public java.lang.String toString() {
            return toString(this.blockedReasons, this.allowedReasons, this.effectiveBlockedReasons);
        }

        public static java.lang.String toString(int i, int i2, int i3) {
            return "{blocked=" + blockedReasonsToString(i) + ",allowed=" + allowedReasonsToString(i2) + ",effective=" + blockedReasonsToString(i3) + "}";
        }

        private static java.lang.String blockedReasonToString(int i) {
            switch (i) {
                case 0:
                    return "NONE";
                case 1:
                    return "BATTERY_SAVER";
                case 2:
                    return "DOZE";
                case 4:
                    return "APP_STANDBY";
                case 8:
                    return "RESTRICTED_MODE";
                case 32:
                    return "LOW_POWER_STANDBY";
                case 64:
                    return "APP_BACKGROUND";
                case 65536:
                    return "DATA_SAVER";
                case 131072:
                    return "METERED_USER_RESTRICTED";
                case 262144:
                    return "METERED_ADMIN_DISABLED";
                default:
                    android.util.Slog.wtfStack(com.android.server.net.NetworkPolicyManagerService.TAG, "Unknown blockedReason: " + i);
                    return java.lang.String.valueOf(i);
            }
        }

        private static java.lang.String allowedReasonToString(int i) {
            switch (i) {
                case 0:
                    return "NONE";
                case 1:
                    return "SYSTEM";
                case 2:
                    return "FOREGROUND";
                case 4:
                    return "POWER_SAVE_ALLOWLIST";
                case 8:
                    return "POWER_SAVE_EXCEPT_IDLE_ALLOWLIST";
                case 16:
                    return "RESTRICTED_MODE_PERMISSIONS";
                case 32:
                    return "TOP";
                case 64:
                    return "LOW_POWER_STANDBY_ALLOWLIST";
                case 128:
                    return "NOT_IN_BACKGROUND";
                case 65536:
                    return "METERED_USER_EXEMPTED";
                case 131072:
                    return "METERED_SYSTEM";
                case 262144:
                    return "METERED_FOREGROUND";
                default:
                    android.util.Slog.wtfStack(com.android.server.net.NetworkPolicyManagerService.TAG, "Unknown allowedReason: " + i);
                    return java.lang.String.valueOf(i);
            }
        }

        public static java.lang.String blockedReasonsToString(int i) {
            int i2 = 0;
            if (i == 0) {
                return blockedReasonToString(0);
            }
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            int[] iArr = BLOCKED_REASONS;
            int length = iArr.length;
            while (true) {
                if (i2 >= length) {
                    break;
                }
                int i3 = iArr[i2];
                if ((i & i3) != 0) {
                    sb.append(sb.length() == 0 ? "" : "|");
                    sb.append(blockedReasonToString(i3));
                    i &= ~i3;
                }
                i2++;
            }
            if (i != 0) {
                sb.append(sb.length() == 0 ? "" : "|");
                sb.append(java.lang.String.valueOf(i));
                android.util.Slog.wtfStack(com.android.server.net.NetworkPolicyManagerService.TAG, "Unknown blockedReasons: " + i);
            }
            return sb.toString();
        }

        public static java.lang.String allowedReasonsToString(int i) {
            int i2 = 0;
            if (i == 0) {
                return allowedReasonToString(0);
            }
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            int[] iArr = ALLOWED_REASONS;
            int length = iArr.length;
            while (true) {
                if (i2 >= length) {
                    break;
                }
                int i3 = iArr[i2];
                if ((i & i3) != 0) {
                    sb.append(sb.length() == 0 ? "" : "|");
                    sb.append(allowedReasonToString(i3));
                    i &= ~i3;
                }
                i2++;
            }
            if (i != 0) {
                sb.append(sb.length() == 0 ? "" : "|");
                sb.append(java.lang.String.valueOf(i));
                android.util.Slog.wtfStack(com.android.server.net.NetworkPolicyManagerService.TAG, "Unknown allowedReasons: " + i);
            }
            return sb.toString();
        }

        public void copyFrom(com.android.server.net.NetworkPolicyManagerService.UidBlockedState uidBlockedState) {
            this.blockedReasons = uidBlockedState.blockedReasons;
            this.allowedReasons = uidBlockedState.allowedReasons;
            this.effectiveBlockedReasons = uidBlockedState.effectiveBlockedReasons;
        }

        public int deriveUidRules() {
            int i;
            if ((this.effectiveBlockedReasons & 8) == 0) {
                i = 0;
            } else {
                i = 1024;
            }
            if ((this.effectiveBlockedReasons & 103) != 0) {
                i |= 64;
            } else if ((this.blockedReasons & 103) != 0) {
                i |= 32;
            }
            if ((this.effectiveBlockedReasons & 393216) != 0) {
                i |= 4;
            } else if ((this.blockedReasons & 131072) != 0 && (this.allowedReasons & 262144) != 0) {
                i |= 2;
            } else if ((this.blockedReasons & 65536) != 0) {
                if ((this.allowedReasons & 65536) != 0) {
                    i |= 32;
                } else if ((this.allowedReasons & 262144) != 0) {
                    i |= 2;
                }
            }
            if (com.android.server.net.NetworkPolicyManagerService.LOGV) {
                android.util.Slog.v(com.android.server.net.NetworkPolicyManagerService.TAG, "uidBlockedState=" + this + " -> uidRule=" + android.net.NetworkPolicyManager.uidRulesToString(i));
            }
            return i;
        }
    }

    private static class NotificationId {
        private final int mId;
        private final java.lang.String mTag;

        NotificationId(android.net.NetworkPolicy networkPolicy, int i) {
            this.mTag = buildNotificationTag(networkPolicy, i);
            this.mId = i;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof com.android.server.net.NetworkPolicyManagerService.NotificationId) {
                return java.util.Objects.equals(this.mTag, ((com.android.server.net.NetworkPolicyManagerService.NotificationId) obj).mTag);
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(this.mTag);
        }

        private static java.lang.String buildNotificationTag(android.net.NetworkPolicy networkPolicy, int i) {
            return "NetworkPolicy:" + networkPolicy.template.hashCode() + ":" + i;
        }

        public java.lang.String getTag() {
            return this.mTag;
        }

        public int getId() {
            return this.mId;
        }

        public java.lang.String toString() {
            return this.mTag;
        }
    }
}
