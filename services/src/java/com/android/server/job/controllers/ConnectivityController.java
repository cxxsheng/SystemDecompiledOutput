package com.android.server.job.controllers;

/* loaded from: classes2.dex */
public final class ConnectivityController extends com.android.server.job.controllers.RestrictingController implements android.net.ConnectivityManager.OnNetworkActiveListener {
    private static final boolean DEBUG;
    private static final int MAX_NETWORK_CALLBACKS = 125;
    private static final long MIN_ADJUST_CALLBACK_INTERVAL_MS = 1000;
    private static final long MIN_STATS_UPDATE_INTERVAL_MS = 30000;
    private static final int MSG_ADJUST_CALLBACKS = 0;
    private static final int MSG_DATA_SAVER_TOGGLED = 2;
    private static final int MSG_PROCESS_ACTIVE_NETWORK = 4;
    private static final int MSG_UID_POLICIES_CHANGED = 3;
    private static final int MSG_UPDATE_ALL_TRACKED_JOBS = 1;
    private static final java.lang.String TAG = "JobScheduler.Connectivity";

    @com.android.internal.annotations.VisibleForTesting
    static final int TRANSPORT_AFFINITY_AVOID = 2;

    @com.android.internal.annotations.VisibleForTesting
    static final int TRANSPORT_AFFINITY_PREFER = 1;

    @com.android.internal.annotations.VisibleForTesting
    static final int TRANSPORT_AFFINITY_UNDEFINED = 0;
    private static final int UNBYPASSABLE_BG_BLOCKED_REASONS = -1;
    private static final int UNBYPASSABLE_EJ_BLOCKED_REASONS = -8;
    private static final int UNBYPASSABLE_FOREGROUND_BLOCKED_REASONS = -196616;
    private static final int UNBYPASSABLE_UI_BLOCKED_REASONS = -196616;
    public static final long UNKNOWN_TIME = -1;

    @com.android.internal.annotations.VisibleForTesting
    static final android.util.SparseIntArray sNetworkTransportAffinities;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<android.net.Network, com.android.server.job.controllers.ConnectivityController.CachedNetworkMetadata> mAvailableNetworks;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseBooleanArray mBackgroundMeteredAllowed;
    private final com.android.server.job.controllers.ConnectivityController.CcConfig mCcConfig;
    private final android.net.ConnectivityManager mConnManager;
    private final android.util.SparseArray<com.android.server.job.controllers.ConnectivityController.UidDefaultNetworkCallback> mCurrentDefaultNetworkCallbacks;
    private final android.net.ConnectivityManager.NetworkCallback mDefaultNetworkCallback;
    private final android.util.Pools.Pool<com.android.server.job.controllers.ConnectivityController.UidDefaultNetworkCallback> mDefaultNetworkCallbackPool;
    private final com.android.server.job.controllers.FlexibilityController mFlexibilityController;
    private final android.os.Handler mHandler;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mLastAllJobUpdateTimeElapsed;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mLastCallbackAdjustmentTimeElapsed;
    private final android.net.INetworkPolicyListener mNetPolicyListener;
    private final android.net.NetworkPolicyManager mNetPolicyManager;
    private final com.android.server.net.NetworkPolicyManagerInternal mNetPolicyManagerInternal;
    private final android.net.ConnectivityManager.NetworkCallback mNetworkCallback;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<android.util.ArraySet<com.android.server.job.controllers.JobStatus>> mRequestedWhitelistJobs;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<com.android.server.job.controllers.ConnectivityController.CellSignalStrengthCallback> mSignalStrengths;
    private final java.util.List<com.android.server.job.controllers.ConnectivityController.UidStats> mSortedStats;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.net.Network mSystemDefaultNetwork;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<android.util.ArraySet<com.android.server.job.controllers.JobStatus>> mTrackedJobs;
    private final android.util.SparseArray<com.android.server.job.controllers.ConnectivityController.UidStats> mUidStats;
    private final java.util.Comparator<com.android.server.job.controllers.ConnectivityController.UidStats> mUidStatsComparator;

    static {
        DEBUG = com.android.server.job.JobSchedulerService.DEBUG || android.util.Log.isLoggable(TAG, 3);
        sNetworkTransportAffinities = new android.util.SparseIntArray();
        sNetworkTransportAffinities.put(0, 2);
        sNetworkTransportAffinities.put(3, 1);
        sNetworkTransportAffinities.put(10, 2);
        sNetworkTransportAffinities.put(1, 1);
    }

    public ConnectivityController(com.android.server.job.JobSchedulerService jobSchedulerService, @android.annotation.NonNull com.android.server.job.controllers.FlexibilityController flexibilityController) {
        super(jobSchedulerService);
        this.mTrackedJobs = new android.util.SparseArray<>();
        this.mRequestedWhitelistJobs = new android.util.SparseArray<>();
        this.mAvailableNetworks = new android.util.ArrayMap<>();
        this.mCurrentDefaultNetworkCallbacks = new android.util.SparseArray<>();
        this.mUidStatsComparator = new java.util.Comparator<com.android.server.job.controllers.ConnectivityController.UidStats>() { // from class: com.android.server.job.controllers.ConnectivityController.1
            private int prioritizeExistenceOver(int i, int i2, int i3) {
                if (i2 > i && i3 > i) {
                    return 0;
                }
                if (i2 <= i && i3 <= i) {
                    return 0;
                }
                if (i2 > i) {
                    return -1;
                }
                return 1;
            }

            @Override // java.util.Comparator
            public int compare(com.android.server.job.controllers.ConnectivityController.UidStats uidStats, com.android.server.job.controllers.ConnectivityController.UidStats uidStats2) {
                int prioritizeExistenceOver = prioritizeExistenceOver(0, uidStats.runningJobs.size(), uidStats2.runningJobs.size());
                if (prioritizeExistenceOver == 0) {
                    int prioritizeExistenceOver2 = prioritizeExistenceOver(0, uidStats.numReadyWithConnectivity, uidStats2.numReadyWithConnectivity);
                    if (prioritizeExistenceOver2 == 0) {
                        int prioritizeExistenceOver3 = prioritizeExistenceOver(0, uidStats.numRequestedNetworkAvailable, uidStats2.numRequestedNetworkAvailable);
                        if (prioritizeExistenceOver3 != 0) {
                            return prioritizeExistenceOver3;
                        }
                        int prioritizeExistenceOver4 = prioritizeExistenceOver(39, uidStats.baseBias, uidStats2.baseBias);
                        if (prioritizeExistenceOver4 == 0) {
                            int prioritizeExistenceOver5 = prioritizeExistenceOver(0, uidStats.numUIJs, uidStats2.numUIJs);
                            if (prioritizeExistenceOver5 == 0) {
                                int prioritizeExistenceOver6 = prioritizeExistenceOver(0, uidStats.numEJs, uidStats2.numEJs);
                                if (prioritizeExistenceOver6 != 0) {
                                    return prioritizeExistenceOver6;
                                }
                                int prioritizeExistenceOver7 = prioritizeExistenceOver(34, uidStats.baseBias, uidStats2.baseBias);
                                if (prioritizeExistenceOver7 != 0) {
                                    return prioritizeExistenceOver7;
                                }
                                if (uidStats.earliestUIJEnqueueTime < uidStats2.earliestUIJEnqueueTime) {
                                    return -1;
                                }
                                if (uidStats.earliestUIJEnqueueTime > uidStats2.earliestUIJEnqueueTime) {
                                    return 1;
                                }
                                if (uidStats.earliestEJEnqueueTime < uidStats2.earliestEJEnqueueTime) {
                                    return -1;
                                }
                                if (uidStats.earliestEJEnqueueTime > uidStats2.earliestEJEnqueueTime) {
                                    return 1;
                                }
                                if (uidStats.baseBias != uidStats2.baseBias) {
                                    return uidStats2.baseBias - uidStats.baseBias;
                                }
                                if (uidStats.earliestEnqueueTime < uidStats2.earliestEnqueueTime) {
                                    return -1;
                                }
                                return uidStats.earliestEnqueueTime > uidStats2.earliestEnqueueTime ? 1 : 0;
                            }
                            return prioritizeExistenceOver5;
                        }
                        return prioritizeExistenceOver4;
                    }
                    return prioritizeExistenceOver2;
                }
                return prioritizeExistenceOver;
            }
        };
        this.mUidStats = new android.util.SparseArray<>();
        this.mDefaultNetworkCallbackPool = new android.util.Pools.SimplePool(125);
        this.mSortedStats = new java.util.ArrayList();
        this.mBackgroundMeteredAllowed = new android.util.SparseBooleanArray();
        this.mSignalStrengths = new android.util.SparseArray<>();
        this.mNetworkCallback = new android.net.ConnectivityManager.NetworkCallback() { // from class: com.android.server.job.controllers.ConnectivityController.2
            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onAvailable(android.net.Network network) {
                if (com.android.server.job.controllers.ConnectivityController.DEBUG) {
                    android.util.Slog.v(com.android.server.job.controllers.ConnectivityController.TAG, "onAvailable: " + network);
                }
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onCapabilitiesChanged(android.net.Network network, android.net.NetworkCapabilities networkCapabilities) {
                if (com.android.server.job.controllers.ConnectivityController.DEBUG) {
                    android.util.Slog.v(com.android.server.job.controllers.ConnectivityController.TAG, "onCapabilitiesChanged: " + network);
                }
                synchronized (com.android.server.job.controllers.ConnectivityController.this.mLock) {
                    try {
                        com.android.server.job.controllers.ConnectivityController.CachedNetworkMetadata cachedNetworkMetadata = (com.android.server.job.controllers.ConnectivityController.CachedNetworkMetadata) com.android.server.job.controllers.ConnectivityController.this.mAvailableNetworks.get(network);
                        if (cachedNetworkMetadata == null) {
                            cachedNetworkMetadata = new com.android.server.job.controllers.ConnectivityController.CachedNetworkMetadata();
                            cachedNetworkMetadata.capabilitiesFirstAcquiredTimeElapsed = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
                            com.android.server.job.controllers.ConnectivityController.this.mAvailableNetworks.put(network, cachedNetworkMetadata);
                        } else {
                            android.net.NetworkCapabilities networkCapabilities2 = cachedNetworkMetadata.networkCapabilities;
                            if (networkCapabilities2 != null) {
                                maybeUnregisterSignalStrengthCallbackLocked(networkCapabilities2);
                            }
                        }
                        cachedNetworkMetadata.networkCapabilities = networkCapabilities;
                        if (com.android.server.job.controllers.ConnectivityController.this.updateTransportAffinitySatisfaction(cachedNetworkMetadata)) {
                            maybeUpdateFlexConstraintLocked(cachedNetworkMetadata);
                        }
                        maybeRegisterSignalStrengthCallbackLocked(networkCapabilities);
                        com.android.server.job.controllers.ConnectivityController.this.updateTrackedJobsLocked(-1, network);
                        com.android.server.job.controllers.ConnectivityController.this.postAdjustCallbacks();
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onLost(android.net.Network network) {
                if (com.android.server.job.controllers.ConnectivityController.DEBUG) {
                    android.util.Slog.v(com.android.server.job.controllers.ConnectivityController.TAG, "onLost: " + network);
                }
                synchronized (com.android.server.job.controllers.ConnectivityController.this.mLock) {
                    try {
                        com.android.server.job.controllers.ConnectivityController.CachedNetworkMetadata cachedNetworkMetadata = (com.android.server.job.controllers.ConnectivityController.CachedNetworkMetadata) com.android.server.job.controllers.ConnectivityController.this.mAvailableNetworks.remove(network);
                        if (cachedNetworkMetadata != null) {
                            if (cachedNetworkMetadata.networkCapabilities != null) {
                                maybeUnregisterSignalStrengthCallbackLocked(cachedNetworkMetadata.networkCapabilities);
                            }
                            if (cachedNetworkMetadata.satisfiesTransportAffinities) {
                                maybeUpdateFlexConstraintLocked(null);
                            }
                        }
                        for (int i = 0; i < com.android.server.job.controllers.ConnectivityController.this.mCurrentDefaultNetworkCallbacks.size(); i++) {
                            com.android.server.job.controllers.ConnectivityController.UidDefaultNetworkCallback uidDefaultNetworkCallback = (com.android.server.job.controllers.ConnectivityController.UidDefaultNetworkCallback) com.android.server.job.controllers.ConnectivityController.this.mCurrentDefaultNetworkCallbacks.valueAt(i);
                            if (java.util.Objects.equals(uidDefaultNetworkCallback.mDefaultNetwork, network)) {
                                uidDefaultNetworkCallback.mDefaultNetwork = null;
                            }
                        }
                        com.android.server.job.controllers.ConnectivityController.this.updateTrackedJobsLocked(-1, network);
                        com.android.server.job.controllers.ConnectivityController.this.postAdjustCallbacks();
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }

            @com.android.internal.annotations.GuardedBy({"mLock"})
            private void maybeRegisterSignalStrengthCallbackLocked(@android.annotation.NonNull android.net.NetworkCapabilities networkCapabilities) {
                if (!networkCapabilities.hasTransport(0)) {
                    return;
                }
                android.telephony.TelephonyManager telephonyManager = (android.telephony.TelephonyManager) com.android.server.job.controllers.ConnectivityController.this.mContext.getSystemService(android.telephony.TelephonyManager.class);
                java.util.Iterator it = networkCapabilities.getSubscriptionIds().iterator();
                while (it.hasNext()) {
                    int intValue = ((java.lang.Integer) it.next()).intValue();
                    if (com.android.server.job.controllers.ConnectivityController.this.mSignalStrengths.indexOfKey(intValue) < 0) {
                        android.telephony.TelephonyManager createForSubscriptionId = telephonyManager.createForSubscriptionId(intValue);
                        com.android.server.job.controllers.ConnectivityController.CellSignalStrengthCallback cellSignalStrengthCallback = new com.android.server.job.controllers.ConnectivityController.CellSignalStrengthCallback();
                        createForSubscriptionId.registerTelephonyCallback(com.android.server.AppSchedulingModuleThread.getExecutor(), cellSignalStrengthCallback);
                        com.android.server.job.controllers.ConnectivityController.this.mSignalStrengths.put(intValue, cellSignalStrengthCallback);
                        android.telephony.SignalStrength signalStrength = createForSubscriptionId.getSignalStrength();
                        if (signalStrength != null) {
                            cellSignalStrengthCallback.signalStrength = signalStrength.getLevel();
                        }
                    }
                }
            }

            @com.android.internal.annotations.GuardedBy({"mLock"})
            private void maybeUnregisterSignalStrengthCallbackLocked(@android.annotation.NonNull android.net.NetworkCapabilities networkCapabilities) {
                if (!networkCapabilities.hasTransport(0)) {
                    return;
                }
                android.util.ArraySet arraySet = new android.util.ArraySet();
                int size = com.android.server.job.controllers.ConnectivityController.this.mAvailableNetworks.size();
                for (int i = 0; i < size; i++) {
                    com.android.server.job.controllers.ConnectivityController.CachedNetworkMetadata cachedNetworkMetadata = (com.android.server.job.controllers.ConnectivityController.CachedNetworkMetadata) com.android.server.job.controllers.ConnectivityController.this.mAvailableNetworks.valueAt(i);
                    if (cachedNetworkMetadata != null && cachedNetworkMetadata.networkCapabilities != null && cachedNetworkMetadata.networkCapabilities.hasTransport(0)) {
                        arraySet.addAll(cachedNetworkMetadata.networkCapabilities.getSubscriptionIds());
                    }
                }
                if (com.android.server.job.controllers.ConnectivityController.DEBUG) {
                    android.util.Slog.d(com.android.server.job.controllers.ConnectivityController.TAG, "Active subscription IDs: " + arraySet);
                }
                android.telephony.TelephonyManager telephonyManager = (android.telephony.TelephonyManager) com.android.server.job.controllers.ConnectivityController.this.mContext.getSystemService(android.telephony.TelephonyManager.class);
                java.util.Iterator it = networkCapabilities.getSubscriptionIds().iterator();
                while (it.hasNext()) {
                    int intValue = ((java.lang.Integer) it.next()).intValue();
                    if (!arraySet.contains(java.lang.Integer.valueOf(intValue))) {
                        android.telephony.TelephonyManager createForSubscriptionId = telephonyManager.createForSubscriptionId(intValue);
                        com.android.server.job.controllers.ConnectivityController.CellSignalStrengthCallback cellSignalStrengthCallback = (com.android.server.job.controllers.ConnectivityController.CellSignalStrengthCallback) com.android.server.job.controllers.ConnectivityController.this.mSignalStrengths.removeReturnOld(intValue);
                        if (cellSignalStrengthCallback != null) {
                            createForSubscriptionId.unregisterTelephonyCallback(cellSignalStrengthCallback);
                        } else {
                            android.util.Slog.wtf(com.android.server.job.controllers.ConnectivityController.TAG, "Callback for sub " + intValue + " didn't exist?!?!");
                        }
                    }
                }
            }

            @com.android.internal.annotations.GuardedBy({"mLock"})
            private void maybeUpdateFlexConstraintLocked(@android.annotation.Nullable com.android.server.job.controllers.ConnectivityController.CachedNetworkMetadata cachedNetworkMetadata) {
                boolean z = true;
                if (cachedNetworkMetadata != null && cachedNetworkMetadata.satisfiesTransportAffinities) {
                    com.android.server.job.controllers.ConnectivityController.this.mFlexibilityController.setConstraintSatisfied(268435456, true, com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis());
                    return;
                }
                int size = com.android.server.job.controllers.ConnectivityController.this.mAvailableNetworks.size() - 1;
                while (true) {
                    if (size < 0) {
                        z = false;
                        break;
                    }
                    com.android.server.job.controllers.ConnectivityController.CachedNetworkMetadata cachedNetworkMetadata2 = (com.android.server.job.controllers.ConnectivityController.CachedNetworkMetadata) com.android.server.job.controllers.ConnectivityController.this.mAvailableNetworks.valueAt(size);
                    if (cachedNetworkMetadata2 != null && cachedNetworkMetadata2.satisfiesTransportAffinities) {
                        break;
                    } else {
                        size--;
                    }
                }
                if (!z) {
                    com.android.server.job.controllers.ConnectivityController.this.mFlexibilityController.setConstraintSatisfied(268435456, false, com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis());
                }
            }
        };
        this.mDefaultNetworkCallback = new android.net.ConnectivityManager.NetworkCallback() { // from class: com.android.server.job.controllers.ConnectivityController.3
            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onAvailable(android.net.Network network) {
                if (com.android.server.job.controllers.ConnectivityController.DEBUG) {
                    android.util.Slog.v(com.android.server.job.controllers.ConnectivityController.TAG, "systemDefault-onAvailable: " + network);
                }
                synchronized (com.android.server.job.controllers.ConnectivityController.this.mLock) {
                    com.android.server.job.controllers.ConnectivityController.this.mSystemDefaultNetwork = network;
                }
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onLost(android.net.Network network) {
                if (com.android.server.job.controllers.ConnectivityController.DEBUG) {
                    android.util.Slog.v(com.android.server.job.controllers.ConnectivityController.TAG, "systemDefault-onLost: " + network);
                }
                synchronized (com.android.server.job.controllers.ConnectivityController.this.mLock) {
                    try {
                        if (network.equals(com.android.server.job.controllers.ConnectivityController.this.mSystemDefaultNetwork)) {
                            com.android.server.job.controllers.ConnectivityController.this.mSystemDefaultNetwork = null;
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        };
        this.mNetPolicyListener = new android.net.NetworkPolicyManager.Listener() { // from class: com.android.server.job.controllers.ConnectivityController.4
            public void onRestrictBackgroundChanged(boolean z) {
                if (com.android.server.job.controllers.ConnectivityController.DEBUG) {
                    android.util.Slog.v(com.android.server.job.controllers.ConnectivityController.TAG, "onRestrictBackgroundChanged: " + z);
                }
                com.android.server.job.controllers.ConnectivityController.this.mHandler.obtainMessage(2).sendToTarget();
            }

            public void onUidPoliciesChanged(int i, int i2) {
                if (com.android.server.job.controllers.ConnectivityController.DEBUG) {
                    android.util.Slog.v(com.android.server.job.controllers.ConnectivityController.TAG, "onUidPoliciesChanged: " + i);
                }
                com.android.server.job.controllers.ConnectivityController.this.mHandler.obtainMessage(3, i, com.android.server.job.controllers.ConnectivityController.this.mNetPolicyManager.getRestrictBackgroundStatus(i)).sendToTarget();
            }
        };
        this.mHandler = new com.android.server.job.controllers.ConnectivityController.CcHandler(com.android.server.AppSchedulingModuleThread.get().getLooper());
        this.mCcConfig = new com.android.server.job.controllers.ConnectivityController.CcConfig();
        this.mConnManager = (android.net.ConnectivityManager) this.mContext.getSystemService(android.net.ConnectivityManager.class);
        this.mNetPolicyManager = (android.net.NetworkPolicyManager) this.mContext.getSystemService(android.net.NetworkPolicyManager.class);
        this.mNetPolicyManagerInternal = (com.android.server.net.NetworkPolicyManagerInternal) com.android.server.LocalServices.getService(com.android.server.net.NetworkPolicyManagerInternal.class);
        this.mFlexibilityController = flexibilityController;
        this.mConnManager.registerNetworkCallback(new android.net.NetworkRequest.Builder().clearCapabilities().build(), this.mNetworkCallback);
        this.mNetPolicyManager.registerListener(this.mNetPolicyListener);
        if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.watch")) {
            sNetworkTransportAffinities.clear();
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public void startTrackingLocked() {
        com.android.server.job.Flags.batchConnectivityJobsPerNetwork();
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void maybeStartTrackingJobLocked(com.android.server.job.controllers.JobStatus jobStatus, com.android.server.job.controllers.JobStatus jobStatus2) {
        if (jobStatus.hasConnectivityConstraint()) {
            com.android.server.job.controllers.ConnectivityController.UidStats uidStats = getUidStats(jobStatus.getSourceUid(), jobStatus.getSourcePackageName(), false);
            if (wouldBeReadyWithConstraintLocked(jobStatus, 268435456)) {
                uidStats.numReadyWithConnectivity++;
            }
            android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet = this.mTrackedJobs.get(jobStatus.getSourceUid());
            if (arraySet == null) {
                arraySet = new android.util.ArraySet<>();
                this.mTrackedJobs.put(jobStatus.getSourceUid(), arraySet);
            }
            arraySet.add(jobStatus);
            jobStatus.setTrackingController(2);
            updateConstraintsSatisfied(jobStatus);
        }
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void prepareForExecutionLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        if (jobStatus.hasConnectivityConstraint()) {
            getUidStats(jobStatus.getSourceUid(), jobStatus.getSourcePackageName(), true).runningJobs.add(jobStatus);
        }
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void unprepareFromExecutionLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        if (jobStatus.hasConnectivityConstraint()) {
            getUidStats(jobStatus.getSourceUid(), jobStatus.getSourcePackageName(), true).runningJobs.remove(jobStatus);
            postAdjustCallbacks();
        }
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void maybeStopTrackingJobLocked(com.android.server.job.controllers.JobStatus jobStatus, com.android.server.job.controllers.JobStatus jobStatus2) {
        if (jobStatus.clearTrackingController(2)) {
            android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet = this.mTrackedJobs.get(jobStatus.getSourceUid());
            if (arraySet != null) {
                arraySet.remove(jobStatus);
            }
            com.android.server.job.controllers.ConnectivityController.UidStats uidStats = getUidStats(jobStatus.getSourceUid(), jobStatus.getSourcePackageName(), true);
            uidStats.numReadyWithConnectivity--;
            uidStats.runningJobs.remove(jobStatus);
            maybeRevokeStandbyExceptionLocked(jobStatus);
            postAdjustCallbacks();
        }
    }

    @Override // com.android.server.job.controllers.RestrictingController
    public void startTrackingRestrictedJobLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        if (jobStatus.hasConnectivityConstraint()) {
            updateConstraintsSatisfied(jobStatus);
        }
    }

    @Override // com.android.server.job.controllers.RestrictingController
    public void stopTrackingRestrictedJobLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        if (jobStatus.hasConnectivityConstraint()) {
            updateConstraintsSatisfied(jobStatus);
        }
    }

    @android.annotation.NonNull
    private com.android.server.job.controllers.ConnectivityController.UidStats getUidStats(int i, java.lang.String str, boolean z) {
        com.android.server.job.controllers.ConnectivityController.UidStats uidStats = this.mUidStats.get(i);
        if (uidStats == null) {
            if (z) {
                android.util.Slog.wtfStack(TAG, "UidStats was null after job for " + str + " was registered");
            }
            com.android.server.job.controllers.ConnectivityController.UidStats uidStats2 = new com.android.server.job.controllers.ConnectivityController.UidStats(i);
            this.mUidStats.append(i, uidStats2);
            return uidStats2;
        }
        return uidStats;
    }

    public boolean isNetworkAvailable(com.android.server.job.controllers.JobStatus jobStatus) {
        synchronized (this.mLock) {
            for (int i = 0; i < this.mAvailableNetworks.size(); i++) {
                try {
                    android.net.Network keyAt = this.mAvailableNetworks.keyAt(i);
                    com.android.server.job.controllers.ConnectivityController.CachedNetworkMetadata valueAt = this.mAvailableNetworks.valueAt(i);
                    android.net.NetworkCapabilities networkCapabilities = valueAt == null ? null : valueAt.networkCapabilities;
                    boolean isSatisfied = isSatisfied(jobStatus, keyAt, networkCapabilities, this.mConstants);
                    if (DEBUG) {
                        android.util.Slog.v(TAG, "isNetworkAvailable(" + jobStatus + ") with network " + keyAt + " and capabilities " + networkCapabilities + ". Satisfied=" + isSatisfied);
                    }
                    if (isSatisfied) {
                        return true;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return false;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    void requestStandbyExceptionLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        int sourceUid = jobStatus.getSourceUid();
        boolean isStandbyExceptionRequestedLocked = isStandbyExceptionRequestedLocked(sourceUid);
        android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet = this.mRequestedWhitelistJobs.get(sourceUid);
        if (arraySet == null) {
            arraySet = new android.util.ArraySet<>();
            this.mRequestedWhitelistJobs.put(sourceUid, arraySet);
        }
        if (!arraySet.add(jobStatus) || isStandbyExceptionRequestedLocked) {
            if (DEBUG) {
                android.util.Slog.i(TAG, "requestStandbyExceptionLocked found exception already requested.");
            }
        } else {
            if (DEBUG) {
                android.util.Slog.i(TAG, "Requesting standby exception for UID: " + sourceUid);
            }
            this.mNetPolicyManagerInternal.setAppIdleWhitelist(sourceUid, true);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    boolean isStandbyExceptionRequestedLocked(int i) {
        android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet = this.mRequestedWhitelistJobs.get(i);
        return arraySet != null && arraySet.size() > 0;
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void evaluateStateLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        if (!jobStatus.hasConnectivityConstraint()) {
            return;
        }
        com.android.server.job.controllers.ConnectivityController.UidStats uidStats = getUidStats(jobStatus.getSourceUid(), jobStatus.getSourcePackageName(), true);
        if (jobStatus.shouldTreatAsExpeditedJob() || jobStatus.shouldTreatAsUserInitiatedJob()) {
            if (!jobStatus.isConstraintSatisfied(268435456)) {
                updateConstraintsSatisfied(jobStatus);
            }
        } else if (((jobStatus.isRequestedExpeditedJob() && !jobStatus.shouldTreatAsExpeditedJob()) || (jobStatus.getJob().isUserInitiated() && !jobStatus.shouldTreatAsUserInitiatedJob())) && jobStatus.isConstraintSatisfied(268435456)) {
            updateConstraintsSatisfied(jobStatus);
        }
        if (wouldBeReadyWithConstraintLocked(jobStatus, 268435456) && isNetworkAvailable(jobStatus)) {
            if (DEBUG) {
                android.util.Slog.i(TAG, "evaluateStateLocked finds job " + jobStatus + " would be ready.");
            }
            uidStats.numReadyWithConnectivity++;
            requestStandbyExceptionLocked(jobStatus);
            return;
        }
        if (DEBUG) {
            android.util.Slog.i(TAG, "evaluateStateLocked finds job " + jobStatus + " would not be ready.");
        }
        maybeRevokeStandbyExceptionLocked(jobStatus);
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void reevaluateStateLocked(int i) {
        android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet = this.mTrackedJobs.get(i);
        if (arraySet == null) {
            return;
        }
        for (int size = arraySet.size() - 1; size >= 0; size--) {
            evaluateStateLocked(arraySet.valueAt(size));
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    void maybeRevokeStandbyExceptionLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        int sourceUid = jobStatus.getSourceUid();
        if (!isStandbyExceptionRequestedLocked(sourceUid)) {
            return;
        }
        android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet = this.mRequestedWhitelistJobs.get(sourceUid);
        if (arraySet == null) {
            android.util.Slog.wtf(TAG, "maybeRevokeStandbyExceptionLocked found null jobs array even though a standby exception has been requested.");
            return;
        }
        if (!arraySet.remove(jobStatus) || arraySet.size() > 0) {
            if (DEBUG) {
                android.util.Slog.i(TAG, "maybeRevokeStandbyExceptionLocked not revoking because there are still " + arraySet.size() + " jobs left.");
                return;
            }
            return;
        }
        revokeStandbyExceptionLocked(sourceUid);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void revokeStandbyExceptionLocked(int i) {
        if (DEBUG) {
            android.util.Slog.i(TAG, "Revoking standby exception for UID: " + i);
        }
        this.mNetPolicyManagerInternal.setAppIdleWhitelist(i, false);
        this.mRequestedWhitelistJobs.remove(i);
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onAppRemovedLocked(java.lang.String str, int i) {
        if (this.mService.getPackagesForUidLocked(i) == null) {
            this.mTrackedJobs.delete(i);
            this.mBackgroundMeteredAllowed.delete(i);
            com.android.server.job.controllers.ConnectivityController.UidStats uidStats = (com.android.server.job.controllers.ConnectivityController.UidStats) this.mUidStats.removeReturnOld(i);
            unregisterDefaultNetworkCallbackLocked(i, com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis());
            this.mSortedStats.remove(uidStats);
            registerPendingUidCallbacksLocked();
        }
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onUserRemovedLocked(int i) {
        long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
        for (int size = this.mUidStats.size() - 1; size >= 0; size--) {
            com.android.server.job.controllers.ConnectivityController.UidStats valueAt = this.mUidStats.valueAt(size);
            if (android.os.UserHandle.getUserId(valueAt.uid) == i) {
                unregisterDefaultNetworkCallbackLocked(valueAt.uid, millis);
                this.mSortedStats.remove(valueAt);
                this.mUidStats.removeAt(size);
            }
        }
        for (int size2 = this.mBackgroundMeteredAllowed.size() - 1; size2 >= 0; size2--) {
            if (android.os.UserHandle.getUserId(this.mBackgroundMeteredAllowed.keyAt(size2)) == i) {
                this.mBackgroundMeteredAllowed.removeAt(size2);
            }
        }
        postAdjustCallbacks();
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onUidBiasChangedLocked(int i, int i2, int i3) {
        com.android.server.job.controllers.ConnectivityController.UidStats uidStats = this.mUidStats.get(i);
        if (uidStats != null && uidStats.baseBias != i3) {
            uidStats.baseBias = i3;
            postAdjustCallbacks();
        }
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onBatteryStateChangedLocked() {
        this.mHandler.sendEmptyMessage(1);
    }

    @Override // com.android.server.job.controllers.StateController
    public void prepareForUpdatedConstantsLocked() {
        this.mCcConfig.mShouldReprocessNetworkCapabilities = false;
        this.mCcConfig.mFlexIsEnabled = this.mFlexibilityController.isEnabled();
    }

    @Override // com.android.server.job.controllers.StateController
    public void processConstantLocked(@android.annotation.NonNull android.provider.DeviceConfig.Properties properties, @android.annotation.NonNull java.lang.String str) {
        this.mCcConfig.processConstantLocked(properties, str);
    }

    @Override // com.android.server.job.controllers.StateController
    public void onConstantsUpdatedLocked() {
        if (this.mCcConfig.mShouldReprocessNetworkCapabilities || this.mFlexibilityController.isEnabled() != this.mCcConfig.mFlexIsEnabled) {
            com.android.server.AppSchedulingModuleThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.job.controllers.ConnectivityController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.job.controllers.ConnectivityController.this.lambda$onConstantsUpdatedLocked$0();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onConstantsUpdatedLocked$0() {
        synchronized (this.mLock) {
            boolean z = false;
            boolean z2 = false;
            for (int i = 0; i < this.mAvailableNetworks.size(); i++) {
                try {
                    com.android.server.job.controllers.ConnectivityController.CachedNetworkMetadata valueAt = this.mAvailableNetworks.valueAt(i);
                    if (valueAt != null) {
                        if (updateTransportAffinitySatisfaction(valueAt)) {
                            z = true;
                        }
                        z2 |= valueAt.satisfiesTransportAffinities;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (z) {
                this.mFlexibilityController.setConstraintSatisfied(268435456, z2, com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis());
                updateAllTrackedJobsLocked(false);
            }
        }
    }

    private boolean isUsable(android.net.NetworkCapabilities networkCapabilities) {
        return networkCapabilities != null && networkCapabilities.hasCapability(21);
    }

    private boolean isInsane(com.android.server.job.controllers.JobStatus jobStatus, android.net.Network network, android.net.NetworkCapabilities networkCapabilities, com.android.server.job.JobSchedulerService.Constants constants) {
        long maxJobExecutionTimeMs = this.mService.getMaxJobExecutionTimeMs(jobStatus);
        long minimumNetworkChunkBytes = jobStatus.getMinimumNetworkChunkBytes();
        if (minimumNetworkChunkBytes != -1) {
            long linkDownstreamBandwidthKbps = networkCapabilities.getLinkDownstreamBandwidthKbps();
            if (linkDownstreamBandwidthKbps > 0) {
                long calculateTransferTimeMs = calculateTransferTimeMs(minimumNetworkChunkBytes, linkDownstreamBandwidthKbps);
                if (calculateTransferTimeMs > maxJobExecutionTimeMs) {
                    android.util.Slog.w(TAG, "Minimum chunk " + minimumNetworkChunkBytes + " bytes over " + linkDownstreamBandwidthKbps + " kbps network would take " + calculateTransferTimeMs + "ms and job has " + maxJobExecutionTimeMs + "ms to run; that's insane!");
                    return true;
                }
            }
            long linkUpstreamBandwidthKbps = networkCapabilities.getLinkUpstreamBandwidthKbps();
            if (linkUpstreamBandwidthKbps > 0) {
                long calculateTransferTimeMs2 = calculateTransferTimeMs(minimumNetworkChunkBytes, linkUpstreamBandwidthKbps);
                if (calculateTransferTimeMs2 > maxJobExecutionTimeMs) {
                    android.util.Slog.w(TAG, "Minimum chunk " + minimumNetworkChunkBytes + " bytes over " + linkUpstreamBandwidthKbps + " kbps network would take " + calculateTransferTimeMs2 + "ms and job has " + maxJobExecutionTimeMs + "ms to run; that's insane!");
                    return true;
                }
                return false;
            }
            return false;
        }
        if (networkCapabilities.hasCapability(11) && this.mService.isBatteryCharging()) {
            return false;
        }
        long estimatedNetworkDownloadBytes = jobStatus.getEstimatedNetworkDownloadBytes();
        if (estimatedNetworkDownloadBytes != -1) {
            long linkDownstreamBandwidthKbps2 = networkCapabilities.getLinkDownstreamBandwidthKbps();
            if (linkDownstreamBandwidthKbps2 > 0) {
                long calculateTransferTimeMs3 = calculateTransferTimeMs(estimatedNetworkDownloadBytes, linkDownstreamBandwidthKbps2);
                if (calculateTransferTimeMs3 > maxJobExecutionTimeMs) {
                    android.util.Slog.w(TAG, "Estimated " + estimatedNetworkDownloadBytes + " download bytes over " + linkDownstreamBandwidthKbps2 + " kbps network would take " + calculateTransferTimeMs3 + "ms and job has " + maxJobExecutionTimeMs + "ms to run; that's insane!");
                    return true;
                }
            }
        }
        long estimatedNetworkUploadBytes = jobStatus.getEstimatedNetworkUploadBytes();
        if (estimatedNetworkUploadBytes != -1) {
            long linkUpstreamBandwidthKbps2 = networkCapabilities.getLinkUpstreamBandwidthKbps();
            if (linkUpstreamBandwidthKbps2 > 0) {
                long calculateTransferTimeMs4 = calculateTransferTimeMs(estimatedNetworkUploadBytes, linkUpstreamBandwidthKbps2);
                if (calculateTransferTimeMs4 > maxJobExecutionTimeMs) {
                    android.util.Slog.w(TAG, "Estimated " + estimatedNetworkUploadBytes + " upload bytes over " + linkUpstreamBandwidthKbps2 + " kbps network would take " + calculateTransferTimeMs4 + "ms and job has " + maxJobExecutionTimeMs + "ms to run; that's insane!");
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    private boolean isMeteredAllowed(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus, @android.annotation.NonNull android.net.NetworkCapabilities networkCapabilities) {
        if (networkCapabilities.hasCapability(11) || networkCapabilities.hasCapability(25)) {
            return true;
        }
        int sourceUid = jobStatus.getSourceUid();
        int uidProcState = this.mService.getUidProcState(sourceUid);
        int uidCapabilities = this.mService.getUidCapabilities(sourceUid);
        boolean z = uidProcState != -1 && uidProcState < 6 && android.net.NetworkPolicyManager.isProcStateAllowedWhileOnRestrictBackground(uidProcState, uidCapabilities);
        if (DEBUG) {
            android.util.Slog.d(TAG, "UID " + sourceUid + " current state allows metered network=" + z + " procState=" + android.app.ActivityManager.procStateToString(uidProcState) + " capabilities=" + android.app.ActivityManager.getCapabilitiesSummary(uidCapabilities));
        }
        if (z) {
            return true;
        }
        if ((jobStatus.getFlags() & 1) != 0) {
            int defaultProcessNetworkCapabilities = android.net.NetworkPolicyManager.getDefaultProcessNetworkCapabilities(4) | uidCapabilities;
            boolean isProcStateAllowedWhileOnRestrictBackground = android.net.NetworkPolicyManager.isProcStateAllowedWhileOnRestrictBackground(4, defaultProcessNetworkCapabilities);
            if (DEBUG) {
                android.util.Slog.d(TAG, "UID " + sourceUid + " willBeForeground flag allows metered network=" + isProcStateAllowedWhileOnRestrictBackground + " capabilities=" + android.app.ActivityManager.getCapabilitiesSummary(defaultProcessNetworkCapabilities));
            }
            if (isProcStateAllowedWhileOnRestrictBackground) {
                return true;
            }
        }
        if (jobStatus.shouldTreatAsUserInitiatedJob()) {
            int defaultProcessNetworkCapabilities2 = uidCapabilities | 32 | android.net.NetworkPolicyManager.getDefaultProcessNetworkCapabilities(6);
            boolean isProcStateAllowedWhileOnRestrictBackground2 = android.net.NetworkPolicyManager.isProcStateAllowedWhileOnRestrictBackground(6, defaultProcessNetworkCapabilities2);
            if (DEBUG) {
                android.util.Slog.d(TAG, "UID " + sourceUid + " UI job state allows metered network=" + isProcStateAllowedWhileOnRestrictBackground2 + " capabilities=" + defaultProcessNetworkCapabilities2);
            }
            if (isProcStateAllowedWhileOnRestrictBackground2) {
                return true;
            }
        }
        if (this.mBackgroundMeteredAllowed.indexOfKey(sourceUid) >= 0) {
            return this.mBackgroundMeteredAllowed.get(sourceUid);
        }
        boolean z2 = this.mNetPolicyManager.getRestrictBackgroundStatus(sourceUid) != 3;
        if (DEBUG) {
            android.util.Slog.d(TAG, "UID " + sourceUid + " allowed in data saver=" + z2);
        }
        this.mBackgroundMeteredAllowed.put(sourceUid, z2);
        return z2;
    }

    public long getEstimatedTransferTimeMs(com.android.server.job.controllers.JobStatus jobStatus) {
        android.net.NetworkCapabilities networkCapabilities;
        long estimatedNetworkDownloadBytes = jobStatus.getEstimatedNetworkDownloadBytes();
        long estimatedNetworkUploadBytes = jobStatus.getEstimatedNetworkUploadBytes();
        if ((estimatedNetworkDownloadBytes == -1 && estimatedNetworkUploadBytes == -1) || jobStatus.network == null || (networkCapabilities = getNetworkCapabilities(jobStatus.network)) == null) {
            return -1L;
        }
        long calculateTransferTimeMs = calculateTransferTimeMs(estimatedNetworkDownloadBytes, networkCapabilities.getLinkDownstreamBandwidthKbps());
        long calculateTransferTimeMs2 = calculateTransferTimeMs(estimatedNetworkUploadBytes, networkCapabilities.getLinkUpstreamBandwidthKbps());
        if (calculateTransferTimeMs == -1) {
            return calculateTransferTimeMs2;
        }
        if (calculateTransferTimeMs2 == -1) {
            return calculateTransferTimeMs;
        }
        return calculateTransferTimeMs + calculateTransferTimeMs2;
    }

    @com.android.internal.annotations.VisibleForTesting
    static long calculateTransferTimeMs(long j, long j2) {
        if (j == -1 || j2 <= 0) {
            return -1L;
        }
        return (j * 1000) / ((j2 * 1000) / 8);
    }

    private static boolean isCongestionDelayed(com.android.server.job.controllers.JobStatus jobStatus, android.net.Network network, android.net.NetworkCapabilities networkCapabilities, com.android.server.job.JobSchedulerService.Constants constants) {
        return !networkCapabilities.hasCapability(20) && jobStatus.getFractionRunTime() < constants.CONN_CONGESTION_DELAY_FRAC;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isStrongEnough(com.android.server.job.controllers.JobStatus jobStatus, android.net.NetworkCapabilities networkCapabilities, com.android.server.job.JobSchedulerService.Constants constants) {
        int effectivePriority = jobStatus.getEffectivePriority();
        if (effectivePriority >= 400 || !constants.CONN_USE_CELL_SIGNAL_STRENGTH || !networkCapabilities.hasTransport(0) || networkCapabilities.hasTransport(4)) {
            return true;
        }
        java.util.Iterator it = networkCapabilities.getSubscriptionIds().iterator();
        int i = 0;
        while (it.hasNext()) {
            int intValue = ((java.lang.Integer) it.next()).intValue();
            com.android.server.job.controllers.ConnectivityController.CellSignalStrengthCallback cellSignalStrengthCallback = this.mSignalStrengths.get(intValue);
            if (cellSignalStrengthCallback != null) {
                i = java.lang.Math.max(i, cellSignalStrengthCallback.signalStrength);
            } else {
                android.util.Slog.wtf(TAG, "Subscription ID " + intValue + " doesn't have a registered callback");
            }
        }
        if (DEBUG) {
            android.util.Slog.d(TAG, "Cell signal strength for job=" + i);
        }
        if (i <= 1) {
            if (effectivePriority > 300) {
                return true;
            }
            if (effectivePriority < 300) {
                return false;
            }
            return (this.mService.isBatteryCharging() && this.mService.isBatteryNotLow()) || jobStatus.getFractionRunTime() > constants.CONN_PREFETCH_RELAX_FRAC;
        }
        if (i > 2 || effectivePriority >= 200) {
            return true;
        }
        if (this.mService.isBatteryCharging() && this.mService.isBatteryNotLow()) {
            return true;
        }
        return getUidStats(jobStatus.getSourceUid(), jobStatus.getSourcePackageName(), true).runningJobs.contains(jobStatus);
    }

    private static android.net.NetworkCapabilities.Builder copyCapabilities(@android.annotation.NonNull android.net.NetworkRequest networkRequest) {
        android.net.NetworkCapabilities.Builder builder = new android.net.NetworkCapabilities.Builder();
        for (int i : networkRequest.getTransportTypes()) {
            builder.addTransportType(i);
        }
        for (int i2 : networkRequest.getCapabilities()) {
            builder.addCapability(i2);
        }
        return builder;
    }

    private static boolean isStrictSatisfied(com.android.server.job.controllers.JobStatus jobStatus, android.net.Network network, android.net.NetworkCapabilities networkCapabilities, com.android.server.job.JobSchedulerService.Constants constants) {
        if (jobStatus.getEffectiveStandbyBucket() == 5 && (!jobStatus.isConstraintSatisfied(16777216) || !jobStatus.isConstraintSatisfied(134217728))) {
            android.net.NetworkCapabilities.Builder copyCapabilities = copyCapabilities(jobStatus.getJob().getRequiredNetwork());
            copyCapabilities.addCapability(11);
            return copyCapabilities.build().satisfiedByNetworkCapabilities(networkCapabilities);
        }
        return jobStatus.getJob().getRequiredNetwork().canBeSatisfiedBy(networkCapabilities);
    }

    private boolean isRelaxedSatisfied(com.android.server.job.controllers.JobStatus jobStatus, android.net.Network network, android.net.NetworkCapabilities networkCapabilities, com.android.server.job.JobSchedulerService.Constants constants) {
        if (!jobStatus.getJob().isPrefetch() || jobStatus.getStandbyBucket() == 5) {
            return false;
        }
        long estimatedNetworkDownloadBytes = jobStatus.getEstimatedNetworkDownloadBytes();
        if (estimatedNetworkDownloadBytes <= 0) {
            return false;
        }
        com.android.server.job.Flags.relaxPrefetchConnectivityConstraintOnlyOnCharger();
        if (!this.mService.isBatteryCharging() || !this.mService.isBatteryNotLow()) {
            return false;
        }
        android.net.NetworkCapabilities.Builder copyCapabilities = copyCapabilities(jobStatus.getJob().getRequiredNetwork());
        copyCapabilities.removeCapability(11);
        if (!copyCapabilities.build().satisfiedByNetworkCapabilities(networkCapabilities) || jobStatus.getFractionRunTime() <= constants.CONN_PREFETCH_RELAX_FRAC) {
            return false;
        }
        long subscriptionOpportunisticQuota = this.mNetPolicyManagerInternal.getSubscriptionOpportunisticQuota(network, 1);
        long estimatedNetworkUploadBytes = jobStatus.getEstimatedNetworkUploadBytes();
        return subscriptionOpportunisticQuota >= estimatedNetworkDownloadBytes + (estimatedNetworkUploadBytes != -1 ? estimatedNetworkUploadBytes : 0L);
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isSatisfied(com.android.server.job.controllers.JobStatus jobStatus, android.net.Network network, android.net.NetworkCapabilities networkCapabilities, com.android.server.job.JobSchedulerService.Constants constants) {
        if (network == null || networkCapabilities == null || !isUsable(networkCapabilities) || isInsane(jobStatus, network, networkCapabilities, constants) || !isMeteredAllowed(jobStatus, networkCapabilities) || isCongestionDelayed(jobStatus, network, networkCapabilities, constants) || !isStrongEnough(jobStatus, networkCapabilities, constants)) {
            return false;
        }
        return isStrictSatisfied(jobStatus, network, networkCapabilities, constants) || isRelaxedSatisfied(jobStatus, network, networkCapabilities, constants);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean updateTransportAffinitySatisfaction(@android.annotation.NonNull com.android.server.job.controllers.ConnectivityController.CachedNetworkMetadata cachedNetworkMetadata) {
        boolean satisfiesTransportAffinities = satisfiesTransportAffinities(cachedNetworkMetadata.networkCapabilities);
        if (cachedNetworkMetadata.satisfiesTransportAffinities != satisfiesTransportAffinities) {
            cachedNetworkMetadata.satisfiesTransportAffinities = satisfiesTransportAffinities;
            return true;
        }
        return false;
    }

    private boolean satisfiesTransportAffinities(@android.annotation.Nullable android.net.NetworkCapabilities networkCapabilities) {
        if (!this.mFlexibilityController.isEnabled()) {
            return true;
        }
        if (networkCapabilities == null) {
            android.util.Slog.wtf(TAG, "Network constraint satisfied with null capabilities");
            return !this.mCcConfig.AVOID_UNDEFINED_TRANSPORT_AFFINITY;
        }
        if (sNetworkTransportAffinities.size() == 0) {
            return !this.mCcConfig.AVOID_UNDEFINED_TRANSPORT_AFFINITY;
        }
        int[] transportTypes = networkCapabilities.getTransportTypes();
        if (transportTypes.length == 0) {
            return !this.mCcConfig.AVOID_UNDEFINED_TRANSPORT_AFFINITY;
        }
        for (int i : transportTypes) {
            int i2 = sNetworkTransportAffinities.get(i, 0);
            if (DEBUG) {
                android.util.Slog.d(TAG, "satisfiesTransportAffinities transport=" + i + " aff=" + i2);
            }
            switch (i2) {
                case 0:
                    if (this.mCcConfig.AVOID_UNDEFINED_TRANSPORT_AFFINITY) {
                        return false;
                    }
                    break;
                case 2:
                    return false;
            }
        }
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void maybeRegisterDefaultNetworkCallbackLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        if (this.mCurrentDefaultNetworkCallbacks.contains(jobStatus.getSourceUid())) {
            return;
        }
        com.android.server.job.controllers.ConnectivityController.UidStats uidStats = getUidStats(jobStatus.getSourceUid(), jobStatus.getSourcePackageName(), true);
        if (!this.mSortedStats.contains(uidStats)) {
            this.mSortedStats.add(uidStats);
        }
        if (this.mCurrentDefaultNetworkCallbacks.size() >= 125) {
            postAdjustCallbacks();
        } else {
            registerPendingUidCallbacksLocked();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void registerPendingUidCallbacksLocked() {
        int size = this.mCurrentDefaultNetworkCallbacks.size();
        int size2 = this.mSortedStats.size();
        if (size2 < size) {
            android.util.Slog.wtf(TAG, "There are more registered callbacks than sorted UIDs: " + size + " vs " + size2);
        }
        while (size < size2 && size < 125) {
            com.android.server.job.controllers.ConnectivityController.UidStats uidStats = this.mSortedStats.get(size);
            com.android.server.job.controllers.ConnectivityController.UidDefaultNetworkCallback uidDefaultNetworkCallback = (com.android.server.job.controllers.ConnectivityController.UidDefaultNetworkCallback) this.mDefaultNetworkCallbackPool.acquire();
            if (uidDefaultNetworkCallback == null) {
                uidDefaultNetworkCallback = new com.android.server.job.controllers.ConnectivityController.UidDefaultNetworkCallback();
            }
            uidDefaultNetworkCallback.setUid(uidStats.uid);
            this.mCurrentDefaultNetworkCallbacks.append(uidStats.uid, uidDefaultNetworkCallback);
            this.mConnManager.registerDefaultNetworkCallbackForUid(uidStats.uid, uidDefaultNetworkCallback, this.mHandler);
            size++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postAdjustCallbacks() {
        postAdjustCallbacks(0L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postAdjustCallbacks(long j) {
        this.mHandler.sendEmptyMessageDelayed(0, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void maybeAdjustRegisteredCallbacksLocked() {
        this.mHandler.removeMessages(0);
        if (this.mUidStats.size() == this.mCurrentDefaultNetworkCallbacks.size()) {
            return;
        }
        long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
        if (millis - this.mLastCallbackAdjustmentTimeElapsed < 1000) {
            postAdjustCallbacks(1000L);
            return;
        }
        this.mLastCallbackAdjustmentTimeElapsed = millis;
        this.mSortedStats.clear();
        for (int i = 0; i < this.mUidStats.size(); i++) {
            com.android.server.job.controllers.ConnectivityController.UidStats valueAt = this.mUidStats.valueAt(i);
            android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet = this.mTrackedJobs.get(valueAt.uid);
            if (arraySet == null || arraySet.size() == 0) {
                unregisterDefaultNetworkCallbackLocked(valueAt.uid, millis);
            } else {
                if (valueAt.lastUpdatedElapsed + 30000 < millis) {
                    valueAt.earliestEnqueueTime = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
                    valueAt.earliestEJEnqueueTime = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
                    valueAt.earliestUIJEnqueueTime = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
                    valueAt.numReadyWithConnectivity = 0;
                    valueAt.numRequestedNetworkAvailable = 0;
                    valueAt.numRegular = 0;
                    valueAt.numEJs = 0;
                    valueAt.numUIJs = 0;
                    for (int i2 = 0; i2 < arraySet.size(); i2++) {
                        com.android.server.job.controllers.JobStatus valueAt2 = arraySet.valueAt(i2);
                        if (wouldBeReadyWithConstraintLocked(valueAt2, 268435456)) {
                            valueAt.numReadyWithConnectivity++;
                            if (isNetworkAvailable(valueAt2)) {
                                valueAt.numRequestedNetworkAvailable++;
                            }
                            valueAt.earliestEnqueueTime = java.lang.Math.min(valueAt.earliestEnqueueTime, valueAt2.enqueueTime);
                            if (valueAt2.shouldTreatAsExpeditedJob() || valueAt2.startedAsExpeditedJob) {
                                valueAt.earliestEJEnqueueTime = java.lang.Math.min(valueAt.earliestEJEnqueueTime, valueAt2.enqueueTime);
                            } else if (valueAt2.shouldTreatAsUserInitiatedJob()) {
                                valueAt.earliestUIJEnqueueTime = java.lang.Math.min(valueAt.earliestUIJEnqueueTime, valueAt2.enqueueTime);
                            }
                        }
                        if (valueAt2.shouldTreatAsExpeditedJob() || valueAt2.startedAsExpeditedJob) {
                            valueAt.numEJs++;
                        } else if (valueAt2.shouldTreatAsUserInitiatedJob()) {
                            valueAt.numUIJs++;
                        } else {
                            valueAt.numRegular++;
                        }
                    }
                    valueAt.lastUpdatedElapsed = millis;
                }
                this.mSortedStats.add(valueAt);
            }
        }
        this.mSortedStats.sort(this.mUidStatsComparator);
        android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet2 = new android.util.ArraySet<>();
        for (int size = this.mSortedStats.size() - 1; size >= 0; size--) {
            com.android.server.job.controllers.ConnectivityController.UidStats uidStats = this.mSortedStats.get(size);
            if (size >= 125) {
                if (unregisterDefaultNetworkCallbackLocked(uidStats.uid, millis)) {
                    arraySet2.addAll((android.util.ArraySet<? extends com.android.server.job.controllers.JobStatus>) this.mTrackedJobs.get(uidStats.uid));
                }
            } else if (this.mCurrentDefaultNetworkCallbacks.get(uidStats.uid) == null) {
                com.android.server.job.controllers.ConnectivityController.UidDefaultNetworkCallback uidDefaultNetworkCallback = (com.android.server.job.controllers.ConnectivityController.UidDefaultNetworkCallback) this.mDefaultNetworkCallbackPool.acquire();
                if (uidDefaultNetworkCallback == null) {
                    uidDefaultNetworkCallback = new com.android.server.job.controllers.ConnectivityController.UidDefaultNetworkCallback();
                }
                uidDefaultNetworkCallback.setUid(uidStats.uid);
                this.mCurrentDefaultNetworkCallbacks.append(uidStats.uid, uidDefaultNetworkCallback);
                this.mConnManager.registerDefaultNetworkCallbackForUid(uidStats.uid, uidDefaultNetworkCallback, this.mHandler);
            }
        }
        if (arraySet2.size() > 0) {
            this.mStateChangedListener.onControllerStateChanged(arraySet2);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean unregisterDefaultNetworkCallbackLocked(int i, long j) {
        com.android.server.job.controllers.ConnectivityController.UidDefaultNetworkCallback uidDefaultNetworkCallback = this.mCurrentDefaultNetworkCallbacks.get(i);
        boolean z = false;
        if (uidDefaultNetworkCallback == null) {
            return false;
        }
        this.mCurrentDefaultNetworkCallbacks.remove(i);
        this.mConnManager.unregisterNetworkCallback(uidDefaultNetworkCallback);
        this.mDefaultNetworkCallbackPool.release(uidDefaultNetworkCallback);
        uidDefaultNetworkCallback.clear();
        android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet = this.mTrackedJobs.get(i);
        if (arraySet != null) {
            for (int size = arraySet.size() - 1; size >= 0; size--) {
                z |= updateConstraintsSatisfied(arraySet.valueAt(size), j, null, null);
            }
        }
        return z;
    }

    @android.annotation.Nullable
    public android.net.NetworkCapabilities getNetworkCapabilities(@android.annotation.Nullable android.net.Network network) {
        com.android.server.job.controllers.ConnectivityController.CachedNetworkMetadata networkMetadata = getNetworkMetadata(network);
        if (networkMetadata == null) {
            return null;
        }
        return networkMetadata.networkCapabilities;
    }

    @android.annotation.Nullable
    private com.android.server.job.controllers.ConnectivityController.CachedNetworkMetadata getNetworkMetadata(@android.annotation.Nullable android.net.Network network) {
        com.android.server.job.controllers.ConnectivityController.CachedNetworkMetadata cachedNetworkMetadata;
        if (network == null) {
            return null;
        }
        synchronized (this.mLock) {
            cachedNetworkMetadata = this.mAvailableNetworks.get(network);
        }
        return cachedNetworkMetadata;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.net.Network getNetworkLocked(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus) {
        com.android.server.job.controllers.ConnectivityController.UidDefaultNetworkCallback uidDefaultNetworkCallback = this.mCurrentDefaultNetworkCallbacks.get(jobStatus.getSourceUid());
        if (uidDefaultNetworkCallback == null) {
            return null;
        }
        int i = -196616;
        if (this.mUidStats.get(jobStatus.getSourceUid()).baseBias >= 30 || (jobStatus.getFlags() & 1) != 0) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "Using FG bypass for " + jobStatus.getSourceUid());
            }
        } else if (jobStatus.shouldTreatAsUserInitiatedJob()) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "Using UI bypass for " + jobStatus.getSourceUid());
            }
        } else if (jobStatus.shouldTreatAsExpeditedJob() || jobStatus.startedAsExpeditedJob) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "Using EJ bypass for " + jobStatus.getSourceUid());
            }
            i = -8;
        } else {
            if (DEBUG) {
                android.util.Slog.d(TAG, "Using BG bypass for " + jobStatus.getSourceUid());
            }
            i = -1;
        }
        if ((uidDefaultNetworkCallback.mBlockedReasons & i) != 0) {
            return null;
        }
        return uidDefaultNetworkCallback.mDefaultNetwork;
    }

    private boolean updateConstraintsSatisfied(com.android.server.job.controllers.JobStatus jobStatus) {
        long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
        if (this.mCurrentDefaultNetworkCallbacks.get(jobStatus.getSourceUid()) == null) {
            maybeRegisterDefaultNetworkCallbackLocked(jobStatus);
            return updateConstraintsSatisfied(jobStatus, millis, null, null);
        }
        android.net.Network networkLocked = getNetworkLocked(jobStatus);
        return updateConstraintsSatisfied(jobStatus, millis, networkLocked, getNetworkMetadata(networkLocked));
    }

    private boolean updateConstraintsSatisfied(com.android.server.job.controllers.JobStatus jobStatus, long j, android.net.Network network, @android.annotation.Nullable com.android.server.job.controllers.ConnectivityController.CachedNetworkMetadata cachedNetworkMetadata) {
        android.net.NetworkCapabilities networkCapabilities = cachedNetworkMetadata == null ? null : cachedNetworkMetadata.networkCapabilities;
        boolean isSatisfied = isSatisfied(jobStatus, network, networkCapabilities, this.mConstants);
        boolean z = false;
        if (!isSatisfied && jobStatus.network != null && this.mService.isCurrentlyRunningLocked(jobStatus) && isSatisfied(jobStatus, jobStatus.network, getNetworkCapabilities(jobStatus.network), this.mConstants)) {
            if (DEBUG) {
                android.util.Slog.i(TAG, "Not reassigning network from " + jobStatus.network + " to " + network + " for running job " + jobStatus);
            }
            return false;
        }
        boolean connectivityConstraintSatisfied = jobStatus.setConnectivityConstraintSatisfied(j, isSatisfied);
        if (isSatisfied && cachedNetworkMetadata != null && cachedNetworkMetadata.satisfiesTransportAffinities) {
            z = true;
        }
        jobStatus.setTransportAffinitiesSatisfied(z);
        if (jobStatus.canApplyTransportAffinities()) {
            jobStatus.setFlexibilityConstraintSatisfied(j, this.mFlexibilityController.isFlexibilitySatisfiedLocked(jobStatus));
        }
        if (!connectivityConstraintSatisfied && isSatisfied && jobStatus.network != null && this.mService.isCurrentlyRunningLocked(jobStatus)) {
            this.mStateChangedListener.onNetworkChanged(jobStatus, network);
        }
        jobStatus.network = network;
        if (DEBUG) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("Connectivity ");
            sb.append(connectivityConstraintSatisfied ? "CHANGED" : "unchanged");
            sb.append(" for ");
            sb.append(jobStatus);
            sb.append(": usable=");
            sb.append(isUsable(networkCapabilities));
            sb.append(" satisfied=");
            sb.append(isSatisfied);
            android.util.Slog.i(TAG, sb.toString());
        }
        return connectivityConstraintSatisfied;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void updateAllTrackedJobsLocked(boolean z) {
        if (z) {
            long millis = (this.mLastAllJobUpdateTimeElapsed + this.mConstants.CONN_UPDATE_ALL_JOBS_MIN_INTERVAL_MS) - com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
            if (millis > 0) {
                this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1, 1, 0), millis);
                return;
            }
        }
        this.mHandler.removeMessages(1);
        updateTrackedJobsLocked(-1, (android.net.Network) null);
        this.mLastAllJobUpdateTimeElapsed = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void updateTrackedJobsLocked(int i, @android.annotation.Nullable android.net.Network network) {
        android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet;
        if (i == -1) {
            arraySet = new android.util.ArraySet<>();
            for (int size = this.mTrackedJobs.size() - 1; size >= 0; size--) {
                if (updateTrackedJobsLocked(this.mTrackedJobs.valueAt(size), network)) {
                    arraySet.addAll((android.util.ArraySet<? extends com.android.server.job.controllers.JobStatus>) this.mTrackedJobs.valueAt(size));
                }
            }
        } else if (updateTrackedJobsLocked(this.mTrackedJobs.get(i), network)) {
            arraySet = this.mTrackedJobs.get(i);
        } else {
            arraySet = null;
        }
        if (arraySet != null && arraySet.size() > 0) {
            this.mStateChangedListener.onControllerStateChanged(arraySet);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean updateTrackedJobsLocked(android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet, @android.annotation.Nullable android.net.Network network) {
        if (arraySet == null || arraySet.size() == 0 || this.mCurrentDefaultNetworkCallbacks.get(arraySet.valueAt(0).getSourceUid()) == null) {
            return false;
        }
        long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
        boolean z = false;
        for (int size = arraySet.size() - 1; size >= 0; size--) {
            com.android.server.job.controllers.JobStatus valueAt = arraySet.valueAt(size);
            android.net.Network networkLocked = getNetworkLocked(valueAt);
            if ((network == null || java.util.Objects.equals(network, networkLocked)) || !java.util.Objects.equals(valueAt.network, networkLocked)) {
                z |= updateConstraintsSatisfied(valueAt, millis, networkLocked, getNetworkMetadata(networkLocked));
            }
        }
        return z;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public boolean isNetworkInStateForJobRunLocked(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus) {
        if (jobStatus.network == null) {
            return false;
        }
        if (jobStatus.shouldTreatAsExpeditedJob() || jobStatus.shouldTreatAsUserInitiatedJob() || this.mService.getUidProcState(jobStatus.getSourceUid()) <= 5) {
            return true;
        }
        return isNetworkInStateForJobRunLocked(jobStatus.network);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    boolean isNetworkInStateForJobRunLocked(@android.annotation.NonNull android.net.Network network) {
        com.android.server.job.Flags.batchConnectivityJobsPerNetwork();
        return true;
    }

    @Override // android.net.ConnectivityManager.OnNetworkActiveListener
    public void onNetworkActive() {
        synchronized (this.mLock) {
            try {
                if (this.mSystemDefaultNetwork == null) {
                    android.util.Slog.wtf(TAG, "System default network is unknown but active");
                    return;
                }
                com.android.server.job.controllers.ConnectivityController.CachedNetworkMetadata cachedNetworkMetadata = this.mAvailableNetworks.get(this.mSystemDefaultNetwork);
                if (cachedNetworkMetadata == null) {
                    android.util.Slog.wtf(TAG, "System default network capabilities are unknown but active");
                    return;
                }
                long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
                cachedNetworkMetadata.defaultNetworkActivationLastCheckTimeElapsed = millis;
                cachedNetworkMetadata.defaultNetworkActivationLastConfirmedTimeElapsed = millis;
                this.mHandler.sendEmptyMessage(4);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private class CcHandler extends android.os.Handler {
        CcHandler(android.os.Looper looper) {
            super(looper);
        }

        /* JADX WARN: Code restructure failed: missing block: B:22:0x0023, code lost:
        
            r1 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:25:0x002c, code lost:
        
            throw r1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:74:0x002d, code lost:
        
            r7 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:77:0x00a1, code lost:
        
            throw r7;
         */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void handleMessage(android.os.Message message) {
            synchronized (com.android.server.job.controllers.ConnectivityController.this.mLock) {
                try {
                    switch (message.what) {
                        case 0:
                            synchronized (com.android.server.job.controllers.ConnectivityController.this.mLock) {
                                com.android.server.job.controllers.ConnectivityController.this.maybeAdjustRegisteredCallbacksLocked();
                            }
                            break;
                        case 1:
                            synchronized (com.android.server.job.controllers.ConnectivityController.this.mLock) {
                                com.android.server.job.controllers.ConnectivityController.this.updateAllTrackedJobsLocked(message.arg1 == 1);
                            }
                            break;
                        case 2:
                            removeMessages(2);
                            synchronized (com.android.server.job.controllers.ConnectivityController.this.mLock) {
                                com.android.server.job.controllers.ConnectivityController.this.mBackgroundMeteredAllowed.clear();
                                com.android.server.job.controllers.ConnectivityController.this.updateTrackedJobsLocked(-1, (android.net.Network) null);
                            }
                            break;
                        case 3:
                            int i = message.arg1;
                            boolean z = message.arg2 != 3;
                            synchronized (com.android.server.job.controllers.ConnectivityController.this.mLock) {
                                if (com.android.server.job.controllers.ConnectivityController.this.mBackgroundMeteredAllowed.get(i) != z) {
                                    com.android.server.job.controllers.ConnectivityController.this.mBackgroundMeteredAllowed.put(i, z);
                                    com.android.server.job.controllers.ConnectivityController.this.updateTrackedJobsLocked(i, (android.net.Network) null);
                                }
                            }
                            break;
                        case 4:
                            removeMessages(4);
                            synchronized (com.android.server.job.controllers.ConnectivityController.this.mLock) {
                                if (com.android.server.job.controllers.ConnectivityController.this.mSystemDefaultNetwork != null) {
                                    com.android.server.job.Flags.batchConnectivityJobsPerNetwork();
                                }
                            }
                            break;
                    }
                } finally {
                }
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    class CcConfig {
        private static final java.lang.String CC_CONFIG_PREFIX = "conn_";
        private static final boolean DEFAULT_AVOID_UNDEFINED_TRANSPORT_AFFINITY = false;
        private static final long DEFAULT_NETWORK_ACTIVATION_EXPIRATION_MS = 10000;
        private static final long DEFAULT_NETWORK_ACTIVATION_MAX_WAIT_TIME_MS = 1860000;

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_AVOID_UNDEFINED_TRANSPORT_AFFINITY = "conn_avoid_undefined_transport_affinity";
        private static final java.lang.String KEY_NETWORK_ACTIVATION_EXPIRATION_MS = "conn_network_activation_expiration_ms";
        private static final java.lang.String KEY_NETWORK_ACTIVATION_MAX_WAIT_TIME_MS = "conn_network_activation_max_wait_time_ms";
        private boolean mFlexIsEnabled = false;
        private boolean mShouldReprocessNetworkCapabilities = false;
        public boolean AVOID_UNDEFINED_TRANSPORT_AFFINITY = false;
        public long NETWORK_ACTIVATION_EXPIRATION_MS = 10000;
        public long NETWORK_ACTIVATION_MAX_WAIT_TIME_MS = DEFAULT_NETWORK_ACTIVATION_MAX_WAIT_TIME_MS;

        CcConfig() {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void processConstantLocked(@android.annotation.NonNull android.provider.DeviceConfig.Properties properties, @android.annotation.NonNull java.lang.String str) {
            char c;
            switch (str.hashCode()) {
                case -1221182095:
                    if (str.equals(KEY_NETWORK_ACTIVATION_EXPIRATION_MS)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 524268654:
                    if (str.equals(KEY_NETWORK_ACTIVATION_MAX_WAIT_TIME_MS)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1011552586:
                    if (str.equals(KEY_AVOID_UNDEFINED_TRANSPORT_AFFINITY)) {
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
                    boolean z = properties.getBoolean(str, false);
                    if (this.AVOID_UNDEFINED_TRANSPORT_AFFINITY != z) {
                        this.AVOID_UNDEFINED_TRANSPORT_AFFINITY = z;
                        this.mShouldReprocessNetworkCapabilities = true;
                        break;
                    }
                    break;
                case 1:
                    long j = properties.getLong(str, 10000L);
                    if (this.NETWORK_ACTIVATION_EXPIRATION_MS != j) {
                        this.NETWORK_ACTIVATION_EXPIRATION_MS = j;
                        break;
                    }
                    break;
                case 2:
                    long j2 = properties.getLong(str, DEFAULT_NETWORK_ACTIVATION_MAX_WAIT_TIME_MS);
                    if (this.NETWORK_ACTIVATION_MAX_WAIT_TIME_MS != j2) {
                        this.NETWORK_ACTIVATION_MAX_WAIT_TIME_MS = j2;
                        this.mShouldReprocessNetworkCapabilities = true;
                        break;
                    }
                    break;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.println();
            indentingPrintWriter.print(com.android.server.job.controllers.ConnectivityController.class.getSimpleName());
            indentingPrintWriter.println(":");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.print(KEY_AVOID_UNDEFINED_TRANSPORT_AFFINITY, java.lang.Boolean.valueOf(this.AVOID_UNDEFINED_TRANSPORT_AFFINITY)).println();
            indentingPrintWriter.print(KEY_NETWORK_ACTIVATION_EXPIRATION_MS, java.lang.Long.valueOf(this.NETWORK_ACTIVATION_EXPIRATION_MS)).println();
            indentingPrintWriter.print(KEY_NETWORK_ACTIVATION_MAX_WAIT_TIME_MS, java.lang.Long.valueOf(this.NETWORK_ACTIVATION_MAX_WAIT_TIME_MS)).println();
            indentingPrintWriter.decreaseIndent();
        }
    }

    private class UidDefaultNetworkCallback extends android.net.ConnectivityManager.NetworkCallback {
        private int mBlockedReasons;

        @android.annotation.Nullable
        private android.net.Network mDefaultNetwork;
        private int mUid;

        private UidDefaultNetworkCallback() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setUid(int i) {
            this.mUid = i;
            this.mDefaultNetwork = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clear() {
            this.mDefaultNetwork = null;
            this.mUid = com.android.server.am.ProcessList.INVALID_ADJ;
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onAvailable(android.net.Network network) {
            if (com.android.server.job.controllers.ConnectivityController.DEBUG) {
                android.util.Slog.v(com.android.server.job.controllers.ConnectivityController.TAG, "default-onAvailable(" + this.mUid + "): " + network);
            }
        }

        public void onBlockedStatusChanged(android.net.Network network, int i) {
            if (com.android.server.job.controllers.ConnectivityController.DEBUG) {
                android.util.Slog.v(com.android.server.job.controllers.ConnectivityController.TAG, "default-onBlockedStatusChanged(" + this.mUid + "): " + network + " -> " + i);
            }
            if (this.mUid == -10000) {
                return;
            }
            synchronized (com.android.server.job.controllers.ConnectivityController.this.mLock) {
                this.mDefaultNetwork = network;
                this.mBlockedReasons = i;
                com.android.server.job.controllers.ConnectivityController.this.updateTrackedJobsLocked(this.mUid, network);
            }
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLost(android.net.Network network) {
            if (com.android.server.job.controllers.ConnectivityController.DEBUG) {
                android.util.Slog.v(com.android.server.job.controllers.ConnectivityController.TAG, "default-onLost(" + this.mUid + "): " + network);
            }
            if (this.mUid == -10000) {
                return;
            }
            synchronized (com.android.server.job.controllers.ConnectivityController.this.mLock) {
                try {
                    if (java.util.Objects.equals(this.mDefaultNetwork, network)) {
                        this.mDefaultNetwork = null;
                        com.android.server.job.controllers.ConnectivityController.this.updateTrackedJobsLocked(this.mUid, network);
                        com.android.server.job.controllers.ConnectivityController.this.postAdjustCallbacks(1000L);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dumpLocked(android.util.IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.print("UID: ");
            indentingPrintWriter.print(this.mUid);
            indentingPrintWriter.print("; ");
            if (this.mDefaultNetwork == null) {
                indentingPrintWriter.print("No network");
            } else {
                indentingPrintWriter.print("Network: ");
                indentingPrintWriter.print(this.mDefaultNetwork);
                indentingPrintWriter.print(" (blocked=");
                indentingPrintWriter.print(android.net.NetworkPolicyManager.blockedReasonsToString(this.mBlockedReasons));
                indentingPrintWriter.print(")");
            }
            indentingPrintWriter.println();
        }
    }

    private static class CachedNetworkMetadata {
        public long capabilitiesFirstAcquiredTimeElapsed;
        public long defaultNetworkActivationLastCheckTimeElapsed;
        public long defaultNetworkActivationLastConfirmedTimeElapsed;
        public android.net.NetworkCapabilities networkCapabilities;
        public boolean satisfiesTransportAffinities;

        private CachedNetworkMetadata() {
        }

        public java.lang.String toString() {
            return "CNM{" + this.networkCapabilities.toString() + ", satisfiesTransportAffinities=" + this.satisfiesTransportAffinities + ", capabilitiesFirstAcquiredTimeElapsed=" + this.capabilitiesFirstAcquiredTimeElapsed + ", defaultNetworkActivationLastCheckTimeElapsed=" + this.defaultNetworkActivationLastCheckTimeElapsed + ", defaultNetworkActivationLastConfirmedTimeElapsed=" + this.defaultNetworkActivationLastConfirmedTimeElapsed + "}";
        }
    }

    private static class UidStats {
        public int baseBias;
        public long earliestEJEnqueueTime;
        public long earliestEnqueueTime;
        public long earliestUIJEnqueueTime;
        public long lastUpdatedElapsed;
        public int numEJs;
        public int numReadyWithConnectivity;
        public int numRegular;
        public int numRequestedNetworkAvailable;
        public int numUIJs;
        public final android.util.ArraySet<com.android.server.job.controllers.JobStatus> runningJobs;
        public final int uid;

        private UidStats(int i) {
            this.runningJobs = new android.util.ArraySet<>();
            this.uid = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dumpLocked(android.util.IndentingPrintWriter indentingPrintWriter, long j) {
            indentingPrintWriter.print("UidStats{");
            indentingPrintWriter.print(com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID, java.lang.Integer.valueOf(this.uid));
            indentingPrintWriter.print("pri", java.lang.Integer.valueOf(this.baseBias));
            indentingPrintWriter.print("#run", java.lang.Integer.valueOf(this.runningJobs.size()));
            indentingPrintWriter.print("#readyWithConn", java.lang.Integer.valueOf(this.numReadyWithConnectivity));
            indentingPrintWriter.print("#netAvail", java.lang.Integer.valueOf(this.numRequestedNetworkAvailable));
            indentingPrintWriter.print("#EJs", java.lang.Integer.valueOf(this.numEJs));
            indentingPrintWriter.print("#reg", java.lang.Integer.valueOf(this.numRegular));
            indentingPrintWriter.print("earliestEnqueue", java.lang.Long.valueOf(this.earliestEnqueueTime));
            indentingPrintWriter.print("earliestEJEnqueue", java.lang.Long.valueOf(this.earliestEJEnqueueTime));
            indentingPrintWriter.print("earliestUIJEnqueue", java.lang.Long.valueOf(this.earliestUIJEnqueueTime));
            indentingPrintWriter.print("updated=");
            android.util.TimeUtils.formatDuration(this.lastUpdatedElapsed - j, indentingPrintWriter);
            indentingPrintWriter.println("}");
        }
    }

    private class CellSignalStrengthCallback extends android.telephony.TelephonyCallback implements android.telephony.TelephonyCallback.SignalStrengthsListener {

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public int signalStrength;

        private CellSignalStrengthCallback() {
            this.signalStrength = 4;
        }

        @Override // android.telephony.TelephonyCallback.SignalStrengthsListener
        public void onSignalStrengthsChanged(@android.annotation.NonNull android.telephony.SignalStrength signalStrength) {
            synchronized (com.android.server.job.controllers.ConnectivityController.this.mLock) {
                try {
                    int level = signalStrength.getLevel();
                    if (com.android.server.job.controllers.ConnectivityController.DEBUG) {
                        android.util.Slog.d(com.android.server.job.controllers.ConnectivityController.TAG, "Signal strength changing from " + this.signalStrength + " to " + level);
                        for (android.telephony.CellSignalStrength cellSignalStrength : signalStrength.getCellSignalStrengths()) {
                            android.util.Slog.d(com.android.server.job.controllers.ConnectivityController.TAG, "CSS: " + cellSignalStrength.getLevel() + " " + cellSignalStrength);
                        }
                    }
                    if (this.signalStrength == level) {
                        return;
                    }
                    this.signalStrength = level;
                    com.android.server.job.controllers.ConnectivityController.this.mHandler.obtainMessage(1, 1, 0).sendToTarget();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    com.android.server.job.controllers.ConnectivityController.CcConfig getCcConfig() {
        return this.mCcConfig;
    }

    @Override // com.android.server.job.controllers.StateController
    public void dumpConstants(android.util.IndentingPrintWriter indentingPrintWriter) {
        this.mCcConfig.dump(indentingPrintWriter);
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void dumpControllerStateLocked(android.util.IndentingPrintWriter indentingPrintWriter, java.util.function.Predicate<com.android.server.job.controllers.JobStatus> predicate) {
        long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
        indentingPrintWriter.println("Aconfig flags:");
        indentingPrintWriter.increaseIndent();
        com.android.server.job.Flags.relaxPrefetchConnectivityConstraintOnlyOnCharger();
        indentingPrintWriter.print(com.android.server.job.Flags.FLAG_RELAX_PREFETCH_CONNECTIVITY_CONSTRAINT_ONLY_ON_CHARGER, true);
        indentingPrintWriter.println();
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        if (this.mRequestedWhitelistJobs.size() > 0) {
            indentingPrintWriter.print("Requested standby exceptions:");
            for (int i = 0; i < this.mRequestedWhitelistJobs.size(); i++) {
                indentingPrintWriter.print(" ");
                indentingPrintWriter.print(this.mRequestedWhitelistJobs.keyAt(i));
                indentingPrintWriter.print(" (");
                indentingPrintWriter.print(this.mRequestedWhitelistJobs.valueAt(i).size());
                indentingPrintWriter.print(" jobs)");
            }
            indentingPrintWriter.println();
        }
        if (this.mAvailableNetworks.size() > 0) {
            indentingPrintWriter.println("Available networks:");
            indentingPrintWriter.increaseIndent();
            for (int i2 = 0; i2 < this.mAvailableNetworks.size(); i2++) {
                indentingPrintWriter.print(this.mAvailableNetworks.keyAt(i2));
                indentingPrintWriter.print(": ");
                indentingPrintWriter.println(this.mAvailableNetworks.valueAt(i2));
            }
            indentingPrintWriter.decreaseIndent();
        } else {
            indentingPrintWriter.println("No available networks");
        }
        indentingPrintWriter.println();
        if (this.mSignalStrengths.size() > 0) {
            indentingPrintWriter.println("Subscription ID signal strengths:");
            indentingPrintWriter.increaseIndent();
            for (int i3 = 0; i3 < this.mSignalStrengths.size(); i3++) {
                indentingPrintWriter.print(this.mSignalStrengths.keyAt(i3));
                indentingPrintWriter.print(": ");
                indentingPrintWriter.println(this.mSignalStrengths.valueAt(i3).signalStrength);
            }
            indentingPrintWriter.decreaseIndent();
        } else {
            indentingPrintWriter.println("No cached signal strengths");
        }
        indentingPrintWriter.println();
        if (this.mBackgroundMeteredAllowed.size() > 0) {
            indentingPrintWriter.print("Background metered allowed: ");
            indentingPrintWriter.println(this.mBackgroundMeteredAllowed);
            indentingPrintWriter.println();
        }
        indentingPrintWriter.println("Current default network callbacks:");
        indentingPrintWriter.increaseIndent();
        for (int i4 = 0; i4 < this.mCurrentDefaultNetworkCallbacks.size(); i4++) {
            this.mCurrentDefaultNetworkCallbacks.valueAt(i4).dumpLocked(indentingPrintWriter);
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        indentingPrintWriter.println("UID Pecking Order:");
        indentingPrintWriter.increaseIndent();
        for (int i5 = 0; i5 < this.mSortedStats.size(); i5++) {
            indentingPrintWriter.print(i5);
            indentingPrintWriter.print(": ");
            this.mSortedStats.get(i5).dumpLocked(indentingPrintWriter, millis);
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        for (int i6 = 0; i6 < this.mTrackedJobs.size(); i6++) {
            android.util.ArraySet<com.android.server.job.controllers.JobStatus> valueAt = this.mTrackedJobs.valueAt(i6);
            for (int i7 = 0; i7 < valueAt.size(); i7++) {
                com.android.server.job.controllers.JobStatus valueAt2 = valueAt.valueAt(i7);
                if (predicate.test(valueAt2)) {
                    indentingPrintWriter.print("#");
                    valueAt2.printUniqueId(indentingPrintWriter);
                    indentingPrintWriter.print(" from ");
                    android.os.UserHandle.formatUid(indentingPrintWriter, valueAt2.getSourceUid());
                    indentingPrintWriter.print(": ");
                    indentingPrintWriter.print(valueAt2.getJob().getRequiredNetwork());
                    indentingPrintWriter.println();
                }
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void dumpControllerStateLocked(android.util.proto.ProtoOutputStream protoOutputStream, long j, java.util.function.Predicate<com.android.server.job.controllers.JobStatus> predicate) {
        long start = protoOutputStream.start(j);
        long start2 = protoOutputStream.start(1146756268035L);
        for (int i = 0; i < this.mRequestedWhitelistJobs.size(); i++) {
            protoOutputStream.write(2220498092035L, this.mRequestedWhitelistJobs.keyAt(i));
        }
        for (int i2 = 0; i2 < this.mTrackedJobs.size(); i2++) {
            android.util.ArraySet<com.android.server.job.controllers.JobStatus> valueAt = this.mTrackedJobs.valueAt(i2);
            for (int i3 = 0; i3 < valueAt.size(); i3++) {
                com.android.server.job.controllers.JobStatus valueAt2 = valueAt.valueAt(i3);
                if (predicate.test(valueAt2)) {
                    long start3 = protoOutputStream.start(2246267895810L);
                    valueAt2.writeToShortProto(protoOutputStream, 1146756268033L);
                    protoOutputStream.write(1120986464258L, valueAt2.getSourceUid());
                    protoOutputStream.end(start3);
                }
            }
        }
        protoOutputStream.end(start2);
        protoOutputStream.end(start);
    }
}
