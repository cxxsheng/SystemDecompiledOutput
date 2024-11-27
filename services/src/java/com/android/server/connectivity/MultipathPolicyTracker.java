package com.android.server.connectivity;

/* loaded from: classes.dex */
public class MultipathPolicyTracker {
    private static final boolean DBG = false;
    private static final long MIN_THRESHOLD_BYTES = 2097152;
    private static final int OPQUOTA_USER_SETTING_DIVIDER = 20;
    private static java.lang.String TAG = com.android.server.connectivity.MultipathPolicyTracker.class.getSimpleName();
    private android.net.ConnectivityManager mCM;
    private final java.time.Clock mClock;
    private final com.android.server.connectivity.MultipathPolicyTracker.ConfigChangeReceiver mConfigChangeReceiver;
    private final android.content.Context mContext;
    private final com.android.server.connectivity.MultipathPolicyTracker.Dependencies mDeps;
    private final android.os.Handler mHandler;
    private android.net.ConnectivityManager.NetworkCallback mMobileNetworkCallback;
    private final java.util.concurrent.ConcurrentHashMap<android.net.Network, com.android.server.connectivity.MultipathPolicyTracker.MultipathTracker> mMultipathTrackers;
    private android.net.NetworkPolicyManager mNPM;
    private android.net.NetworkPolicyManager.Listener mPolicyListener;
    private final android.content.ContentResolver mResolver;

    @com.android.internal.annotations.VisibleForTesting
    final android.database.ContentObserver mSettingsObserver;
    private android.app.usage.NetworkStatsManager mStatsManager;
    private final android.content.Context mUserAllContext;

    public static class Dependencies {
        public java.time.Clock getClock() {
            return new android.os.BestClock(java.time.ZoneOffset.UTC, new java.time.Clock[]{android.os.SystemClock.currentNetworkTimeClock(), java.time.Clock.systemUTC()});
        }
    }

    public MultipathPolicyTracker(android.content.Context context, android.os.Handler handler) {
        this(context, handler, new com.android.server.connectivity.MultipathPolicyTracker.Dependencies());
    }

    public MultipathPolicyTracker(android.content.Context context, android.os.Handler handler, com.android.server.connectivity.MultipathPolicyTracker.Dependencies dependencies) {
        this.mMultipathTrackers = new java.util.concurrent.ConcurrentHashMap<>();
        this.mContext = context;
        this.mUserAllContext = context.createContextAsUser(android.os.UserHandle.ALL, 0);
        this.mHandler = handler;
        this.mClock = dependencies.getClock();
        this.mDeps = dependencies;
        this.mResolver = this.mContext.getContentResolver();
        this.mSettingsObserver = new com.android.server.connectivity.MultipathPolicyTracker.SettingsObserver(this.mHandler);
        this.mConfigChangeReceiver = new com.android.server.connectivity.MultipathPolicyTracker.ConfigChangeReceiver();
    }

    public void start() {
        this.mCM = (android.net.ConnectivityManager) this.mContext.getSystemService(android.net.ConnectivityManager.class);
        this.mNPM = (android.net.NetworkPolicyManager) this.mContext.getSystemService(android.net.NetworkPolicyManager.class);
        this.mStatsManager = (android.app.usage.NetworkStatsManager) this.mContext.getSystemService(android.app.usage.NetworkStatsManager.class);
        registerTrackMobileCallback();
        registerNetworkPolicyListener();
        this.mResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("network_default_daily_multipath_quota_bytes"), false, this.mSettingsObserver);
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.CONFIGURATION_CHANGED");
        this.mUserAllContext.registerReceiver(this.mConfigChangeReceiver, intentFilter, null, this.mHandler);
    }

    public void shutdown() {
        maybeUnregisterTrackMobileCallback();
        unregisterNetworkPolicyListener();
        java.util.Iterator<com.android.server.connectivity.MultipathPolicyTracker.MultipathTracker> it = this.mMultipathTrackers.values().iterator();
        while (it.hasNext()) {
            it.next().shutdown();
        }
        this.mMultipathTrackers.clear();
        this.mResolver.unregisterContentObserver(this.mSettingsObserver);
        this.mUserAllContext.unregisterReceiver(this.mConfigChangeReceiver);
    }

    public java.lang.Integer getMultipathPreference(android.net.Network network) {
        com.android.server.connectivity.MultipathPolicyTracker.MultipathTracker multipathTracker;
        if (network == null || (multipathTracker = this.mMultipathTrackers.get(network)) == null) {
            return null;
        }
        return java.lang.Integer.valueOf(multipathTracker.getMultipathPreference());
    }

    class MultipathTracker {
        private volatile long mMultipathBudget;
        private android.net.NetworkCapabilities mNetworkCapabilities;
        private final android.net.NetworkTemplate mNetworkTemplate;
        private long mQuota;
        private final android.app.usage.NetworkStatsManager mStatsManager;
        private final int mSubId;
        private final android.app.usage.NetworkStatsManager.UsageCallback mUsageCallback;
        private boolean mUsageCallbackRegistered = false;
        final android.net.Network network;
        final java.lang.String subscriberId;

        public MultipathTracker(final android.net.Network network, android.net.NetworkCapabilities networkCapabilities) {
            this.network = network;
            this.mNetworkCapabilities = new android.net.NetworkCapabilities(networkCapabilities);
            android.net.NetworkSpecifier networkSpecifier = networkCapabilities.getNetworkSpecifier();
            if (networkSpecifier instanceof android.net.TelephonyNetworkSpecifier) {
                this.mSubId = ((android.net.TelephonyNetworkSpecifier) networkSpecifier).getSubscriptionId();
                android.telephony.TelephonyManager telephonyManager = (android.telephony.TelephonyManager) com.android.server.connectivity.MultipathPolicyTracker.this.mContext.getSystemService(android.telephony.TelephonyManager.class);
                if (telephonyManager == null) {
                    throw new java.lang.IllegalStateException(java.lang.String.format("Missing TelephonyManager", new java.lang.Object[0]));
                }
                android.telephony.TelephonyManager createForSubscriptionId = telephonyManager.createForSubscriptionId(this.mSubId);
                if (createForSubscriptionId == null) {
                    throw new java.lang.IllegalStateException(java.lang.String.format("Can't get TelephonyManager for subId %d", java.lang.Integer.valueOf(this.mSubId)));
                }
                this.subscriberId = createForSubscriptionId.getSubscriberId();
                if (this.subscriberId == null) {
                    throw new java.lang.IllegalStateException("Null subscriber Id for subId " + this.mSubId);
                }
                this.mNetworkTemplate = new android.net.NetworkTemplate.Builder(1).setSubscriberIds(java.util.Set.of(this.subscriberId)).setMeteredness(1).setDefaultNetworkStatus(0).build();
                this.mUsageCallback = new android.app.usage.NetworkStatsManager.UsageCallback() { // from class: com.android.server.connectivity.MultipathPolicyTracker.MultipathTracker.1
                    @Override // android.app.usage.NetworkStatsManager.UsageCallback
                    public void onThresholdReached(int i, java.lang.String str) {
                        com.android.server.connectivity.MultipathPolicyTracker.MultipathTracker.this.updateMultipathBudget();
                    }
                };
                this.mStatsManager = (android.app.usage.NetworkStatsManager) com.android.server.connectivity.MultipathPolicyTracker.this.mContext.getSystemService(android.app.usage.NetworkStatsManager.class);
                this.mStatsManager.setPollOnOpen(false);
                updateMultipathBudget();
                return;
            }
            throw new java.lang.IllegalStateException(java.lang.String.format("Can't get subId from mobile network %s (%s)", network, networkCapabilities));
        }

        public void setNetworkCapabilities(android.net.NetworkCapabilities networkCapabilities) {
            this.mNetworkCapabilities = new android.net.NetworkCapabilities(networkCapabilities);
        }

        private long getDailyNonDefaultDataUsage() {
            java.time.ZonedDateTime ofInstant = java.time.ZonedDateTime.ofInstant(com.android.server.connectivity.MultipathPolicyTracker.this.mClock.instant(), java.time.ZoneId.systemDefault());
            return getNetworkTotalBytes(ofInstant.truncatedTo(java.time.temporal.ChronoUnit.DAYS).toInstant().toEpochMilli(), ofInstant.toInstant().toEpochMilli());
        }

        private long getNetworkTotalBytes(long j, long j2) {
            try {
                android.app.usage.NetworkStats.Bucket querySummaryForDevice = this.mStatsManager.querySummaryForDevice(this.mNetworkTemplate, j, j2);
                return querySummaryForDevice.getRxBytes() + querySummaryForDevice.getTxBytes();
            } catch (java.lang.RuntimeException e) {
                android.util.Log.w(com.android.server.connectivity.MultipathPolicyTracker.TAG, "Failed to get data usage: " + e);
                return -1L;
            }
        }

        private android.net.NetworkIdentity getTemplateMatchingNetworkIdentity(android.net.NetworkCapabilities networkCapabilities) {
            return new android.net.NetworkIdentity.Builder().setType(0).setSubscriberId(this.subscriberId).setRoaming(!networkCapabilities.hasCapability(18)).setMetered(!networkCapabilities.hasCapability(11)).setSubId(this.mSubId).build();
        }

        private long getRemainingDailyBudget(long j, android.util.Range<java.time.ZonedDateTime> range) {
            long epochMilli = range.getLower().toInstant().toEpochMilli();
            long epochMilli2 = range.getUpper().toInstant().toEpochMilli();
            long networkTotalBytes = getNetworkTotalBytes(epochMilli, epochMilli2);
            return (networkTotalBytes != -1 ? java.lang.Math.max(0L, j - networkTotalBytes) : 0L) / java.lang.Math.max(1L, (((epochMilli2 - com.android.server.connectivity.MultipathPolicyTracker.this.mClock.millis()) - 1) / java.util.concurrent.TimeUnit.DAYS.toMillis(1L)) + 1);
        }

        private long getUserPolicyOpportunisticQuotaBytes() {
            android.net.NetworkIdentity templateMatchingNetworkIdentity = getTemplateMatchingNetworkIdentity(this.mNetworkCapabilities);
            long j = Long.MAX_VALUE;
            for (android.net.NetworkPolicy networkPolicy : com.android.server.connectivity.MultipathPolicyTracker.this.mNPM.getNetworkPolicies()) {
                if (networkPolicy.hasCycle() && networkPolicy.template.matches(templateMatchingNetworkIdentity)) {
                    long epochMilli = ((java.time.ZonedDateTime) ((android.util.Range) networkPolicy.cycleIterator().next()).getLower()).toInstant().toEpochMilli();
                    long activeWarning = com.android.server.connectivity.MultipathPolicyTracker.getActiveWarning(networkPolicy, epochMilli);
                    if (activeWarning == -1) {
                        activeWarning = com.android.server.connectivity.MultipathPolicyTracker.getActiveLimit(networkPolicy, epochMilli);
                    }
                    if (activeWarning != -1 && activeWarning != -1) {
                        j = java.lang.Math.min(j, getRemainingDailyBudget(activeWarning, (android.util.Range) networkPolicy.cycleIterator().next()));
                    }
                }
            }
            if (j == com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
                return -1L;
            }
            return j / 20;
        }

        void updateMultipathBudget() {
            long subscriptionOpportunisticQuota = ((com.android.server.net.NetworkPolicyManagerInternal) com.android.server.LocalServices.getService(com.android.server.net.NetworkPolicyManagerInternal.class)).getSubscriptionOpportunisticQuota(this.network, 2);
            if (subscriptionOpportunisticQuota == -1) {
                subscriptionOpportunisticQuota = getUserPolicyOpportunisticQuotaBytes();
            }
            if (subscriptionOpportunisticQuota == -1) {
                subscriptionOpportunisticQuota = com.android.server.connectivity.MultipathPolicyTracker.this.getDefaultDailyMultipathQuotaBytes();
            }
            if (haveMultipathBudget() && subscriptionOpportunisticQuota == this.mQuota) {
                return;
            }
            this.mQuota = subscriptionOpportunisticQuota;
            long dailyNonDefaultDataUsage = getDailyNonDefaultDataUsage();
            long max = dailyNonDefaultDataUsage != -1 ? java.lang.Math.max(0L, subscriptionOpportunisticQuota - dailyNonDefaultDataUsage) : 0L;
            if (max > com.android.server.connectivity.MultipathPolicyTracker.MIN_THRESHOLD_BYTES) {
                setMultipathBudget(max);
            } else {
                clearMultipathBudget();
            }
        }

        public int getMultipathPreference() {
            if (haveMultipathBudget()) {
                return 3;
            }
            return 0;
        }

        public long getQuota() {
            return this.mQuota;
        }

        public long getMultipathBudget() {
            return this.mMultipathBudget;
        }

        private boolean haveMultipathBudget() {
            return this.mMultipathBudget > 0;
        }

        private void setMultipathBudget(long j) {
            maybeUnregisterUsageCallback();
            this.mStatsManager.registerUsageCallback(this.mNetworkTemplate, j, new java.util.concurrent.Executor() { // from class: com.android.server.connectivity.MultipathPolicyTracker$MultipathTracker$$ExternalSyntheticLambda0
                @Override // java.util.concurrent.Executor
                public final void execute(java.lang.Runnable runnable) {
                    com.android.server.connectivity.MultipathPolicyTracker.MultipathTracker.this.lambda$setMultipathBudget$0(runnable);
                }
            }, this.mUsageCallback);
            this.mUsageCallbackRegistered = true;
            this.mMultipathBudget = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setMultipathBudget$0(java.lang.Runnable runnable) {
            com.android.server.connectivity.MultipathPolicyTracker.this.mHandler.post(runnable);
        }

        private void maybeUnregisterUsageCallback() {
            if (this.mUsageCallbackRegistered) {
                this.mStatsManager.unregisterUsageCallback(this.mUsageCallback);
                this.mUsageCallbackRegistered = false;
            }
        }

        private void clearMultipathBudget() {
            maybeUnregisterUsageCallback();
            this.mMultipathBudget = 0L;
        }

        void shutdown() {
            clearMultipathBudget();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long getActiveWarning(android.net.NetworkPolicy networkPolicy, long j) {
        if (networkPolicy.lastWarningSnooze < j) {
            return networkPolicy.warningBytes;
        }
        return -1L;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long getActiveLimit(android.net.NetworkPolicy networkPolicy, long j) {
        if (networkPolicy.lastLimitSnooze < j) {
            return networkPolicy.limitBytes;
        }
        return -1L;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long getDefaultDailyMultipathQuotaBytes() {
        java.lang.String string = android.provider.Settings.Global.getString(this.mContext.getContentResolver(), "network_default_daily_multipath_quota_bytes");
        if (string != null) {
            try {
                return java.lang.Long.parseLong(string);
            } catch (java.lang.NumberFormatException e) {
            }
        }
        return this.mContext.getResources().getInteger(android.R.integer.config_navBarInteractionMode);
    }

    private void registerTrackMobileCallback() {
        android.net.NetworkRequest build = new android.net.NetworkRequest.Builder().addCapability(12).addTransportType(0).build();
        this.mMobileNetworkCallback = new android.net.ConnectivityManager.NetworkCallback() { // from class: com.android.server.connectivity.MultipathPolicyTracker.1
            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onCapabilitiesChanged(android.net.Network network, android.net.NetworkCapabilities networkCapabilities) {
                com.android.server.connectivity.MultipathPolicyTracker.MultipathTracker multipathTracker = (com.android.server.connectivity.MultipathPolicyTracker.MultipathTracker) com.android.server.connectivity.MultipathPolicyTracker.this.mMultipathTrackers.get(network);
                if (multipathTracker != null) {
                    multipathTracker.setNetworkCapabilities(networkCapabilities);
                    multipathTracker.updateMultipathBudget();
                    return;
                }
                try {
                    com.android.server.connectivity.MultipathPolicyTracker.this.mMultipathTrackers.put(network, com.android.server.connectivity.MultipathPolicyTracker.this.new MultipathTracker(network, networkCapabilities));
                } catch (java.lang.IllegalStateException e) {
                    android.util.Log.e(com.android.server.connectivity.MultipathPolicyTracker.TAG, "Can't track mobile network " + network + ": " + e.getMessage());
                }
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onLost(android.net.Network network) {
                com.android.server.connectivity.MultipathPolicyTracker.MultipathTracker multipathTracker = (com.android.server.connectivity.MultipathPolicyTracker.MultipathTracker) com.android.server.connectivity.MultipathPolicyTracker.this.mMultipathTrackers.get(network);
                if (multipathTracker != null) {
                    multipathTracker.shutdown();
                    com.android.server.connectivity.MultipathPolicyTracker.this.mMultipathTrackers.remove(network);
                }
            }
        };
        this.mCM.registerNetworkCallback(build, this.mMobileNetworkCallback, this.mHandler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAllMultipathBudgets() {
        java.util.Iterator<com.android.server.connectivity.MultipathPolicyTracker.MultipathTracker> it = this.mMultipathTrackers.values().iterator();
        while (it.hasNext()) {
            it.next().updateMultipathBudget();
        }
    }

    private void maybeUnregisterTrackMobileCallback() {
        if (this.mMobileNetworkCallback != null) {
            this.mCM.unregisterNetworkCallback(this.mMobileNetworkCallback);
        }
        this.mMobileNetworkCallback = null;
    }

    /* renamed from: com.android.server.connectivity.MultipathPolicyTracker$2, reason: invalid class name */
    class AnonymousClass2 extends android.net.NetworkPolicyManager.Listener {
        AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onMeteredIfacesChanged$0() {
            com.android.server.connectivity.MultipathPolicyTracker.this.updateAllMultipathBudgets();
        }

        public void onMeteredIfacesChanged(java.lang.String[] strArr) {
            com.android.server.connectivity.MultipathPolicyTracker.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.connectivity.MultipathPolicyTracker$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.connectivity.MultipathPolicyTracker.AnonymousClass2.this.lambda$onMeteredIfacesChanged$0();
                }
            });
        }
    }

    private void registerNetworkPolicyListener() {
        this.mPolicyListener = new com.android.server.connectivity.MultipathPolicyTracker.AnonymousClass2();
        this.mNPM.registerListener(this.mPolicyListener);
    }

    private void unregisterNetworkPolicyListener() {
        this.mNPM.unregisterListener(this.mPolicyListener);
    }

    private final class SettingsObserver extends android.database.ContentObserver {
        public SettingsObserver(android.os.Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            android.util.Log.wtf(com.android.server.connectivity.MultipathPolicyTracker.TAG, "Should never be reached.");
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri) {
            if (!android.provider.Settings.Global.getUriFor("network_default_daily_multipath_quota_bytes").equals(uri)) {
                android.util.Log.wtf(com.android.server.connectivity.MultipathPolicyTracker.TAG, "Unexpected settings observation: " + uri);
            }
            com.android.server.connectivity.MultipathPolicyTracker.this.updateAllMultipathBudgets();
        }
    }

    private final class ConfigChangeReceiver extends android.content.BroadcastReceiver {
        private ConfigChangeReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            com.android.server.connectivity.MultipathPolicyTracker.this.updateAllMultipathBudgets();
        }
    }

    public void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("MultipathPolicyTracker:");
        indentingPrintWriter.increaseIndent();
        for (com.android.server.connectivity.MultipathPolicyTracker.MultipathTracker multipathTracker : this.mMultipathTrackers.values()) {
            indentingPrintWriter.println(java.lang.String.format("Network %s: quota %d, budget %d. Preference: %s", multipathTracker.network, java.lang.Long.valueOf(multipathTracker.getQuota()), java.lang.Long.valueOf(multipathTracker.getMultipathBudget()), android.util.DebugUtils.flagsToString(android.net.ConnectivityManager.class, "MULTIPATH_PREFERENCE_", multipathTracker.getMultipathPreference())));
        }
        indentingPrintWriter.decreaseIndent();
    }
}
